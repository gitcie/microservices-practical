/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.service;

import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.domain.MultiplicationResultAttempt;
import edu.microservices.book.domain.User;
import edu.microservices.book.repository.ResultAttemptRepository;
import edu.microservices.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/1
 */
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomGeneratorService randomGeneratorService;

    private ResultAttemptRepository resultAttemptRepository;

    private UserRepository userRepository;

    @Autowired
    public MultiplicationServiceImpl(
            final RandomGeneratorService randomGeneratorService,
            final ResultAttemptRepository resultAttemptRepository,
            final UserRepository userRepository){
        this.randomGeneratorService = randomGeneratorService;
        this.resultAttemptRepository = resultAttemptRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());


        Assert.isTrue(!resultAttempt.isCorrect(), "你不能发送一个尝试包含正确标志");

        boolean correct = resultAttempt.getResultAttempt() ==
                resultAttempt.getMultiplication().getFactorA() *
                        resultAttempt.getMultiplication().getFactorB();

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(
                user.orElse(resultAttempt.getUser()),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(),
                correct
        );
        resultAttemptRepository.save(checkedAttempt);
        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias){
        return resultAttemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
