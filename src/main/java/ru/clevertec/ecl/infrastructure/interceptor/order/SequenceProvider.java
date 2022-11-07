package ru.clevertec.ecl.infrastructure.interceptor.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.api.order.OrderReadDto;
import ru.clevertec.ecl.api.order.OrderSequenceDto;
import ru.clevertec.ecl.domain.order.OrderService;
import ru.clevertec.ecl.infrastructure.interceptor.order.url.PaginationSerializer;
import ru.clevertec.ecl.infrastructure.interceptor.order.url.UrlPathParser;
import ru.clevertec.ecl.infrastructure.node.Node;

@Log4j2
@Component
@RequiredArgsConstructor
public class SequenceProvider {

    public static final String CURRENT_NODE = "NODE";
    private final RestTemplate restTemplate;
    private final Node node;
    private final OrderService orderService;

    /**
     * Retrieving all sub nodes and main node for finding url with min sequence. If main node has mix sequence then
     * return "NODE" constant instead of URL.
     *
     * @return String of URL.
     */
    public String findUrlWithMinSequence() {
        final List<Pair<String, OrderSequenceDto>> sequenceWithUrls = findAllSequence();
        return isEmptyNodesExisted(sequenceWithUrls)
                ? findUrlByMinSequence(sequenceWithUrls, sequence -> !sequence.isFirstOrderSaved())
                : findUrlByMinSequence(sequenceWithUrls, sequence -> true);
    }

    public ResponseEntity<List<OrderReadDto>> findPaginationEntities(UrlPathParser urlPathParser) {
        PaginationSerializer paginationSerializer = new PaginationSerializer(urlPathParser);
        List<OrderReadDto> internalEntity = new ArrayList<>(
                orderService.findAllById(
                        paginationSerializer.minPaginationValue(), paginationSerializer.maxPaginationValue()
                )
        );
        node.getSubNodes().stream()
                .map(Node.SubNode::getUrl)
                .map(clientRequestToSubNode(paginationSerializer))
                .filter(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
                .map(ResponseEntity::getBody)
                .flatMap(Stream::of)
                .forEach(internalEntity::add);
        return ResponseEntity.ok(
                internalEntity.stream()
                        .sorted(Comparator.comparing(OrderReadDto::getId))
                        .collect(Collectors.toList())
        );
    }

    private List<Pair<String, OrderSequenceDto>> findAllSequence() {
        List<Pair<String, OrderSequenceDto>> sequences = new ArrayList<>();
        sequences.add(Pair.of(CURRENT_NODE, orderService.sequence()));
        node.getSubNodes()
                .stream()
                .map(Node.SubNode::getUrl)
                .map(url ->
                        Pair.of(
                                url,
                                restTemplate.getForObject(url + "/api/v1/orders/sequence", OrderSequenceDto.class)
                        )
                )
                .forEach(sequences::add);
        return sequences;
    }

    private String findUrlByMinSequence(
            List<Pair<String, OrderSequenceDto>> sequenceWithUrls, Predicate<OrderSequenceDto> isFirstOrderSaved
    ) {
        final Integer minSequence = findMinSequence(sequenceWithUrls, isFirstOrderSaved);
        return sequenceWithUrls.stream()
                .filter(pair -> pair.getSecond().getSequence().equals(minSequence))
                .findFirst()
                .orElseThrow(UnsupportedOperationException::new)
                .getFirst();
    }

    private Integer findMinSequence(
            List<Pair<String, OrderSequenceDto>> sequences, Predicate<OrderSequenceDto> predicate
    ) {
        return sequences.stream()
                .map(Pair::getSecond)
                .filter(predicate)
                .map(OrderSequenceDto::getSequence)
                .min(Integer::compare)
                .orElse(0);
    }

    private boolean isEmptyNodesExisted(List<Pair<String, OrderSequenceDto>> sequenceWithUrls) {
        return sequenceWithUrls.stream()
                .map(Pair::getSecond)
                .anyMatch(sequence -> !sequence.isFirstOrderSaved());
    }

    private Function<String, ResponseEntity<OrderReadDto[]>> clientRequestToSubNode(
            PaginationSerializer paginationSerializer
    ) {
        return url -> {
            try {
                return restTemplate.getForEntity(
                        url + paginationSerializer.generateEndPointForPagination(), OrderReadDto[].class
                );
            } catch (HttpClientErrorException httpClientErrorException) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        };
    }

}
