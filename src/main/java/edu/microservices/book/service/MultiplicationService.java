/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.service;

import edu.microservices.book.domain.Multiplication;
import edu.microservices.book.domain.MultiplicationResultAttempt;

import java.util.List;

/**
 * 乘法业务服务
 * @author Siyi Lu
 * @since 2020/7/1
 */
public interface MultiplicationService {

    Multiplication createRandomMultiplication();

    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);

    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

}
