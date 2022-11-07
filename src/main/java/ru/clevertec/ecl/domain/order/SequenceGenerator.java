package ru.clevertec.ecl.domain.order;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ru.clevertec.ecl.infrastructure.node.Node;

@Log4j2
@Component
@RequiredArgsConstructor
public class SequenceGenerator implements IdentifierGenerator {

    private final Node node;
    private final AtomicBoolean isCalled = new AtomicBoolean(false);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return isCalled.getAndSet(true)
                ? increment(session, node.getCapacity())
                : node.getNumber();
    }

    private int increment(SharedSessionContractImplementor session, int incrementValue) {
        final String idValue = "(select orders_id_seq.last_value from orders_id_seq) + :increment";
        return ((BigInteger) session.createNativeQuery("select setval('orders_id_seq', " + idValue + ")")
                .setParameter("increment", incrementValue)
                .getSingleResult()).intValue();
    }

}
