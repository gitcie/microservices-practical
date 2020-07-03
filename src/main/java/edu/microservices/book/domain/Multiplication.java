/******************************************************************************
 * Copyright (C) 2017 ShenZhen Powerdata Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳博安达开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
 * 复制、修改或发布本软件.
 *****************************************************************************/

package edu.microservices.book.domain;

/**
 * 乘法对象
 * @author Siyi Lu
 * @since 2020/7/1
 */
public class Multiplication {

    private int factorA;
    private int factorB;

    private int result;

    public Multiplication(int factorA, int factorB){
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = factorA * factorB;
    }

    public int getFactorA() {
        return factorA;
    }

    public int getFactorB() {
        return factorB;
    }

    public int getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Multiplication { factorA=" + factorA + ", factorB=" + factorB + ", result(A*B)=" + result + "}";
    }
}
