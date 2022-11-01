package ru.clevertec.ecl.infrastructure.node;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "node")
public class Node {

    private int number;
    private List<String> urls;
    private String nextNodeUrl;

    public boolean isLast() {
        return number == getNumber();
    }

    public boolean isMain() {
        return number == 1;
    }

    @Data
    public static class Ports {
        private int firstPort;
        private int secondPort;
        private int thirdPort;
    }

    public int getCapacity() {
        return urls.size() + 1;
    }
}
