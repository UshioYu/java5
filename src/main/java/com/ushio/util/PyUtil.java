package com.ushio.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼接工具类
 */
public class PyUtil {

    public static String getChineseToPinyin(String str) {
        // 中文字符串转换为字符数组
        char[] chars = str.toCharArray();
        // 设置转换格式
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // 小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // 无声调，WITH_TONE_NUMBER表示用数字表示声调, WITH_TONE_MARK表示用声调符号表示
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 特殊拼音ü的的显示格式
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder result = new StringBuilder();
        try {
            for (char c : chars) {
                // 通过正则判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] s = PinyinHelper.toHanyuPinyinStringArray(c, format);
                    result.append(s[0]);
                } else {
                    result.append(c);
                }
            }
            return result.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return result.toString();
    }
}
