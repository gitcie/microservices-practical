/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * class summary
 * <p>
 * class detail description
 *
 * @author Siyi Lu
 * @since 2020/7/1
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class User {

    private final String alias;

    protected User(){
        alias = null;
    }

}
