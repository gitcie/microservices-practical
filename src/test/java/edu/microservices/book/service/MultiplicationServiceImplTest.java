package edu.microservices.book.service;

import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.domain.MultiplicationResultAttempt;
import edu.microservices.book.domain.User;
import edu.microservices.book.repository.ResultAttemptRepository;
import edu.microservices.book.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;

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

    @Mock
    private ResultAttemptRepository resultAttemptRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void initService(){
        MockitoAnnotations.initMocks(this);
        this.multiplicationService = new MultiplicationServiceImpl(
                randomGeneratorService,
                resultAttemptRepository,
                userRepository
        );
    }

    @Test
    public void createRandomMultiplication() {
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
    }

    @Test
    public void checkCorrectAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("lusiyi");
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        given(userRepository.findByAlias("lusiyi")).willReturn(Optional.empty());

        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);
        assertThat(attemptResult).isTrue();
        verify(resultAttemptRepository).save(verifiedAttempt);

    }

    @Test
    public void checkWrongAttemptTest() {
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("lusiyi");
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 2000, false);
        given(userRepository.findByAlias("lusiyi"))
                .willReturn(Optional.empty());

        boolean attemptResult = multiplicationService.checkAttempt(resultAttempt);
        assertThat(attemptResult).isFalse();
        verify(resultAttemptRepository).save(resultAttempt);
    }

    @Test
    public void retrieveStatsTest(){
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("lusiyi");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(
                user, multiplication, 3010, false
        );
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(
                user, multiplication, 3050, false
        );

        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2);
        given(userRepository.findByAlias("lusiyi"))
                .willReturn(Optional.empty());
        given(resultAttemptRepository.findTop5ByUserAliasOrderByIdDesc("lusiyi"))
                .willReturn(latestAttempts);

        List<MultiplicationResultAttempt> latestAttemptsResult =
                multiplicationService.getStatsForUser("lusiyi");

        assertThat(latestAttemptsResult).isEqualTo(latestAttempts);
    }
}