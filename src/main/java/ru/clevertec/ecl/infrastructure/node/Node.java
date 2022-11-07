package ru.clevertec.ecl.infrastructure.node;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "node")
public class Node {

    private int number;
    private int capacity;
    private List<SubNode> subNodes = new ArrayList<>();

    @Data
    public static class SubNode {
        private int number;
        private String url;
    }

}
