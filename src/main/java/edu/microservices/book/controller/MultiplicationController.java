/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.controller;

import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.service.MultiplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/1
 */
@RestController
@RequestMapping("/multiplications")
final class MultiplicationController {

    @Autowired
    private MultiplicationService multiplicationService;

    @GetMapping("/random")
    Multiplication getRandomMultiplication(){
        return multiplicationService.createRandomMultiplication();
    }

}
