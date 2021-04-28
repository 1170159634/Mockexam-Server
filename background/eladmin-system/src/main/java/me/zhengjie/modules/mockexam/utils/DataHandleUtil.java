package me.zhengjie.modules.mockexam.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author :         Mingxuan_x
 * @version :        1.0
 * @Description: 处理数据的工具类
 * @Telephone :      15135964789
 * @createDate :     2020/11/25 15:51
 * @updateUser :     Mingxuan_x
 * @updateDate :     2020/11/25 15:51
 * @updateRemark :   修改内容
 **/
public class DataHandleUtil {
    /**
     * 占比计算保留小数的位数方法
     * 转成百分数
     * 当前数除以总数
     *
     * @param divide ,beDivided  divide/beDivided
     * @return rate  保留2位小数的
     */
    public static BigDecimal calculationPercentage(BigDecimal divide, BigDecimal beDivided) {
        if (divide == null || divide.equals(BigDecimal.ZERO)) {
            divide = new BigDecimal(0);
        }
        if (beDivided == null || beDivided.equals(BigDecimal.ZERO)) {
            beDivided = new BigDecimal(0);
        }


        double num1 = divide.doubleValue();
        double num2 = beDivided.doubleValue();
        String rate = "0.00%";
        //定义格式化起始位数
        String format = "0.00";
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2 * 100) + "%";
            while (true) {
                if (rate.equals(format + "%")) {
                    format = format + "0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2 * 100) + "%";
                } else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100%";
        }
        //需要获取字符串类型加%直接返回rate即可
        String decimal = rate.substring(0, rate.indexOf("%"));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        bigDecimal.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;

    }


    /**
     *  占比计算保留小数的位数方法
     *  转成百分数
     *  当前数除以总数
     *  t1/t2
     * @param t1 int
     * @param t2 int
     * @return 计算结果保留两位小数
     */
    public static BigDecimal calculationPercentage(Integer t1, Integer t2) {
        double num1 = t1;
        double num2 = t2;

        if (num1 == 0 && num2 == 0) {
            return new BigDecimal(0);
        }
        String rate = "0.00%";
        //定义格式化起始位数
        String format = "0.00";
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2 * 100) + "%";
            while (true) {
                if (rate.equals(format + "%")) {
                    format = format + "0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2 * 100) + "%";
                } else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100%";
        }
        //需要获取字符串类型加%直接返回rate即可
        String decimal = rate.substring(0, rate.indexOf("%"));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        bigDecimal.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;

    }


    /**
     *  占比计算保留小数的位数方法
     *  转成百分数
     *  当前数除以总数
     *  t1/t2
     * @param t1 long
     * @param t2 long
     * @return 计算结果保留两位小数
     */
    public static BigDecimal calculationPercentage(Long t1, Long t2) {
        double num1 = t1;
        double num2 = t2;

        if (num1 == 0 && num2 == 0) {
            return new BigDecimal(0);
        }
        String rate = "0.00%";
        //定义格式化起始位数
        String format = "0.00";
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2 * 100) + "%";
            while (true) {
                if (rate.equals(format + "%")) {
                    format = format + "0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2 * 100) + "%";
                } else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100%";
        }
        //需要获取字符串类型加%直接返回rate即可
        String decimal = rate.substring(0, rate.indexOf("%"));
        BigDecimal bigDecimal = new BigDecimal(decimal);
        bigDecimal.divide(new BigDecimal("100"), 4, BigDecimal.ROUND_HALF_UP);
        return bigDecimal;

    }

    /**
     * 取整（Integer）
     * @date 2020/12/14
     * @return 转换后的整数
     */
    public static String getRoundNumber(Integer number){
        char[] chars = String.valueOf(number).toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = chars.length-1; i >= 0 ; i--) {
            if (i == 0){
                int num = Integer.parseInt(chars[i] + "") + 1;
                stringBuilder.insert(0,num);
            }else {
                stringBuilder.append(0);
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 取整（BigDecimal）
     * @date 2020/12/14
     * @return 转换后的整数
     */
    public static String getRoundNumber(BigDecimal number){
        char[] chars = String.valueOf(number.intValue()).toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = chars.length-1; i >= 0 ; i--) {
            if (i == 0){
                int num = Integer.parseInt(chars[i] + "") + 1;
                stringBuilder.insert(0,num);
            }else {
                stringBuilder.append(0);
            }
        }
        return stringBuilder.toString();
    }
    /**
     * 取整（Long）
     * @date 2020/12/14
     * @return 转换后的整数
     */
    public static String getRoundNumber(Long number){
        char[] chars = String.valueOf(number.intValue()).toCharArray();
        StringBuilder stringBuilder= new StringBuilder();
        for (int i = chars.length-1; i >= 0 ; i--) {
            if (i == 0){
                int num = Integer.parseInt(chars[i] + "") + 1;
                stringBuilder.insert(0,num);
            }else {
                stringBuilder.append(0);
            }
        }
        return stringBuilder.toString();
    }

}
