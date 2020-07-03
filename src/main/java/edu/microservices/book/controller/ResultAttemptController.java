/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.controller;

import edu.microservices.book.domain.MultiplicationResultAttempt;
import edu.microservices.book.service.MultiplicationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/2
 */
@RestController
@RequestMapping("/results")
class ResultAttemptController {

    @Autowired
    private MultiplicationService multiplicationService;

    @PostMapping
    ResponseEntity<MultiplicationResultAttempt> postResult(@RequestBody MultiplicationResultAttempt resultAttempt){
        boolean isCorrect = multiplicationService.checkAttempt(resultAttempt);
        MultiplicationResultAttempt response = new MultiplicationResultAttempt(
                resultAttempt.getUser(),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(),
                isCorrect
        );
        return ResponseEntity.ok(response);
    }

    @Getter
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    static final class ResultResponse{
        private final boolean correct;
    }

}
