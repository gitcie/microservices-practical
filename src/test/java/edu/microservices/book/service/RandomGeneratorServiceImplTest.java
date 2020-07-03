package edu.microservices.book.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/1
 */
public class RandomGeneratorServiceImplTest {

    private RandomGeneratorServiceImpl randomGeneratorService;

    @Before
    public void initService(){
        this.randomGeneratorService = new RandomGeneratorServiceImpl();
    }

    @Test
    public void generateRandomFactor() {
        List<Integer> randomFactors = IntStream.range(0, 1000)
                .map(i -> randomGeneratorService.generateRandomFactor())
                .boxed()
                .collect(Collectors.toList());
        assertThat(randomFactors).containsOnlyElementsOf(
                IntStream.range(11, 100)
                        .boxed()
                        .collect(Collectors.toList())
        );
    }
}