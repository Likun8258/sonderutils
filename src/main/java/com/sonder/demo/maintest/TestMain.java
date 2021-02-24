package com.sonder.demo.maintest;

import java.math.BigDecimal;

/**
 * @Author likun
 * @Date
 **/
public class TestMain {

    public static void main(String[] args) {
        System.out.println(new BigDecimal("5.000").compareTo(new BigDecimal("5")));

        System.out.println(new BigDecimal("0").setScale(6,BigDecimal.ROUND_DOWN).setScale(6,BigDecimal.ROUND_DOWN));

    }

}
