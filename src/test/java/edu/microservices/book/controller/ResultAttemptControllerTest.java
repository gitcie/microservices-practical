package edu.microservices.book.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.domain.MultiplicationResultAttempt;
import edu.microservices.book.domain.User;
import edu.microservices.book.service.MultiplicationService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/2
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ResultAttemptController.class)
public class ResultAttemptControllerTest {

    @MockBean
    private MultiplicationService multiplicationService;

    @Autowired
    private MockMvc mvc;

    private JacksonTester<MultiplicationResultAttempt> jsonResult;

    private JacksonTester<List<MultiplicationResultAttempt>> resultAttemptsValidator;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnCorrect() throws Exception{
        genericParameterizedTest(true);
    }

    @Test
    public void postResultReturnWrong() throws Exception {
        genericParameterizedTest(false);
    }

    private void genericParameterizedTest(final boolean correct) throws Exception{
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
                .willReturn(correct);

        User user = new User("lusiyi");
        Multiplication multiplication = new Multiplication(50, 60);
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, correct);

        MockHttpServletResponse response = mvc.perform(
                post("/results")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonResult.write(resultAttempt).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResult.write(new MultiplicationResultAttempt(
                        resultAttempt.getUser(),
                        resultAttempt.getMultiplication(),
                        resultAttempt.getResultAttempt(),
                        correct
                )).getJson()
        );
    }

    @Test
    public void getUserStatsTest() throws Exception{
        User user = new User("lusiyi");
        Multiplication multiplication = new Multiplication(50, 60);
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(
                user,
                multiplication,
                3000,
                true
        );
        List<MultiplicationResultAttempt> resultAttempts = Lists.newArrayList(resultAttempt, resultAttempt);

        given(multiplicationService.getStatsForUser("lusiyi"))
                .willReturn(resultAttempts);

        MockHttpServletResponse response = mvc.perform(
                get("/results").param("alias", "lusiyi")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                resultAttemptsValidator.write(resultAttempts).getJson()
        );
    }
}