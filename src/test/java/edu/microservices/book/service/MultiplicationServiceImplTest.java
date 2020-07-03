package edu.microservices.book.service;

import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.domain.MultiplicationResultAttempt;
import edu.microservices.book.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/1
 */
public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationService;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Before
    public void initService(){
        MockitoAnnotations.initMocks(this);
        this.multiplicationService = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    public void createRandomMultiplication() {
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
        assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("lusiyi");
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);

        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);
        assertThat(attemptResult).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("lusiyi");
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 2000, false);

        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);
        assertThat(attemptResult).isFalse();
    }
}