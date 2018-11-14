package cn.cjf.gateway.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangwei
 * @date 2018/9/20 0020 17:30.
 */
public final class NumberFormatUtil {

    private NumberFormatUtil() {
    }

    private static final String[] STRING_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static final String[] STRING_UNIT_1 = {"", "十", "百", "千"};
    private static final String[] STRING_UNIT_2 = {"", "万", "亿", "兆"};


    /**
     * @Description 数字转化为中文 不支持小于0的数字
     * @Author wangwei
     * @Date 2018/9/20 0020 下午 5:30
     * @param number 数字
     * @return 中文字符串
     */
    public static String num2String(int number) {
        if (number < 0) {
            return String.valueOf(number);
        }
        if (number == 0) {
            return STRING_NUM[number];
        }
        // 将数字四个一组，倒序分开
        List<List<Integer>> splitNumGroup = new ArrayList<>();
        splitNumByGroup(number, splitNumGroup);
        List<String> numStrGroup = new ArrayList<>();
        boolean previousGroupIsZero = true;
        for (int group = 0; group < splitNumGroup.size(); group++) {
            List<Integer> splitNumList = splitNumGroup.get(group);
            List<String> numStrList = new ArrayList<>();
            boolean previousNumIsZero = true;
            //将数字转换为四位数中文
            for (int i = 0; i < splitNumList.size(); i++) {
                int num = splitNumList.get(i);
                if (num != 0) {
                    // 十 不现实为 一十
                    if (splitNumList.size() == 2 && i == 1 && group == splitNumGroup.size() - 1 && num == 1) {
                        numStrList.add(STRING_UNIT_1[i]);
                    } else {
                        numStrList.add(STRING_NUM[num] + STRING_UNIT_1[i]);
                    }
                } else if (!previousNumIsZero) {
                    numStrList.add(STRING_NUM[num]);
                }
                previousNumIsZero = num == 0;
            }
            Collections.reverse(numStrList);
            String str = numStrList.stream().reduce("", String::concat);
            if (!"".equals(str)) {
                // 判断数字前是否加 零
                if (group != splitNumGroup.size() - 1 && splitNumList.size() < 4) {
                    str = STRING_NUM[0] + str;
                    previousGroupIsZero = true;
                } else {
                    previousGroupIsZero = false;
                }
                // 加单位
                str += STRING_UNIT_2[group];
            } else if (!previousGroupIsZero) {
                str += STRING_NUM[0];
                previousGroupIsZero = true;
            }

            numStrGroup.add(str);
        }
        Collections.reverse(numStrGroup);
        String numStr = numStrGroup.stream().reduce("", String::concat);
        return  numStr;
    }

    private static List<List<Integer>> splitNumByGroup(int number, List<List<Integer>> numGroup) {
        if (number > 0) {
            List<Integer> numberList = new ArrayList<>();
            splitNum(number % 10000, numberList);
            numGroup.add(numberList);
            return splitNumByGroup(number / 10000, numGroup);
        } else {
            return numGroup;
        }
    }

    private static List<Integer> splitNum(int number, List<Integer> numList) {
        if (number > 0) {
            numList.add(subLast(number));
            return splitNum(number / 10, numList);
        } else {
            return numList;
        }
    }
    private static int subLast(int number) {
        return number % 10;
    }
}
