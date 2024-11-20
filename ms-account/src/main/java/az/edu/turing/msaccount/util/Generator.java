package az.edu.turing.msaccount.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Generator {
    public static String randomNumberGenerator() {
        return IntStream.range(0, 10)
                .mapToObj(i -> {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        return String.valueOf((char) ('a' + random.nextInt(26)));
                    } else {
                        return String.valueOf(random.nextInt(10));
                    }
                })
                .collect(Collectors.joining());
    }
}

