package com.sonder.demo.test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author likun
 * @Date
 **/
public class MyParse {

    static Map<String, Integer> optrOrder;

    static {
        optrOrder = new HashMap<String, Integer>();
        optrOrder.put("(", 0);
        optrOrder.put("*", 1);
        optrOrder.put("/", 1);
        optrOrder.put("%", 1);
        optrOrder.put("+", 2);
        optrOrder.put("-", 2);
        optrOrder.put("^", 3);
        optrOrder.put("#", 3);
    }

    public static void main(String[] args) {
        String str = "500 * (100 - 20)";
        //"+2* (-2+3*4)+-5"

        List<String> tokens;
        try {
            //词法分析
            tokens = lex(str);
            //中缀转后缀
            tokens = toRpn(tokens);
            //计算结果
            System.out.println(calcRpn(tokens));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将输入串转换为操作符串
     *
     * @param sExpres
     * @return
     */
    public static List<String> lex(String sExpres) {
        List<String> tokens = new ArrayList<String>();
        //将表达式分割成符号序列
        String sRegExp = "(((?<=^|\\(|\\+|-|\\*|/|%)(\\+|-))?\\d+(\\.\\d+)?)"
                + "|\\(|\\)|\\*|/|\\+|-";
        Pattern p = Pattern.compile(sRegExp);
        Matcher m = p.matcher(sExpres.replaceAll("\\s+", ""));
        while (m.find()) {
            tokens.add(m.group());
        }
        tokens.add("#");
        return tokens;
    }

    /**
     * 将中缀表单时转化为后缀表达式
     *
     * @param tokens
     * @return
     */
    public static List<String> toRpn(List<String> tokens)
            throws Exception {
        List<String> rpnList = new ArrayList<String>();
        Stack<String> optrStack = new Stack<String>();
        optrStack.add("^");
        for (String token : tokens) {
            if (token.matches("^(\\+|-)?\\d+(\\.\\d+)?$")) {
                rpnList.add(token);
            } else {
                outputOptr(token, optrStack, rpnList);
            }
        }
        if (!optrStack.isEmpty()
                && optrStack.lastElement().equals("#")) {
            return rpnList;
        } else {
            throw new Exception("后缀表达式转化错误!");
        }
    }

    /**
     * 计算后缀表达式的值
     *
     * @param rpnTokens
     * @return
     * @throws Exception
     */
    public static double calcRpn(List<String> rpnTokens)
            throws Exception {
        NumberFormat nf = NumberFormat.getInstance();
        Stack<Double> numStack = new Stack<Double>();
        for (String token : rpnTokens) {
            if (token.matches("^(\\+|-)?\\d+(.\\d+)?$")) {
                token = token.indexOf('+') == 0
                        ? token.substring(1)
                        : token;
                numStack.add(nf.parse(token).doubleValue());
            } else {
                doCalcByOptr(token, numStack);
            }
        }
        if (!numStack.isEmpty() && numStack.size() == 1) {
            return numStack.lastElement();
        } else {
            throw new Exception("计算错误！");
        }
    }

    /**
     * 将运算符输出到后缀表达式序列.
     *
     * @param optr
     * @param optrStack
     * @param rpnList
     * @throws Exception
     */
    public static void outputOptr(String optr,
                                  Stack<String> optrStack,
                                  List<String> rpnList)
            throws Exception {
        String preOptr;
        if (optr.equals("(")) {//处理左括号
            optrStack.push(optr);
            return;
        }
        if (optr.equals(")")) {//处理右括号
            while (!optrStack.isEmpty()) {
                preOptr = optrStack.pop();
                if (!preOptr.equals("(")) {
                    rpnList.add(preOptr);
                } else {
                    break;
                }
            }
            if (optrStack.isEmpty()) {
                throw new Exception("括号未闭合!");
            }
            return;
        }
        /*按优先级处理其他运算符，若当前运算符优先级较高
         * 直接入栈，否则将栈中运算符出战直至栈顶运算符
         * 低于当前运算符
         */
        preOptr = optrStack.lastElement();
        if (optrCmp(optr, preOptr) < 0) {
            optrStack.push(optr);
        } else {
            while (!preOptr.equals("(")
                    && !optrStack.isEmpty()
                    && optrCmp(optr, preOptr) >= 0) {
                preOptr = optrStack.pop();
                if (!preOptr.equals("^")) {
                    rpnList.add(preOptr);
                }
            }
            optrStack.push(optr);
        }
    }

    /**
     * 运算符优先级比较函数,optr1优先级大于optr2返回小于0值,
     * 优先级相等返回0,optr1小于optr2返回大于0值.
     *
     * @param optr1
     * @param optr2
     * @return
     */
    public static int optrCmp(String optr1, String optr2) {
        int order1 = optrOrder.get(optr1);
        int order2 = optrOrder.get(optr2);
        return order1 - order2;
    }

    /**
     * 根据运算符对数据栈中的内容进行操作.
     *
     * @param optr
     * @param numStack
     */
    public static void doCalcByOptr(String optr, Stack<Double> numStack) {
        double n1, n2;
        n2 = numStack.pop();
        n1 = numStack.pop();
        if (optr.equals("+")) {
            numStack.push(n1 + n2);
        } else if (optr.equals("-")) {
            numStack.push(n1 - n2);
        } else if (optr.equals("*")) {
            numStack.push(n1 * n2);
        } else if (optr.equals("/")) {
            numStack.push(n1 / n2);
        } else if (optr.equals("%")) {
            numStack.push(n1 % n2);
        }
    }

}
