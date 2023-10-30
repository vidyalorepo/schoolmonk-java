package com.dcc.schoolmonk.common;

import java.util.ArrayList;
import java.util.List;

import com.dcc.schoolmonk.vo.CodeValueVo;

public class RomanNumber {
    /*public static void main(String[] args) {
        for (int n = 1; n <= 10; n++) {
            String roman = RomanNumber.toRoman(n);
            int number = RomanNumber.toNumber(roman);

            System.out.println(number + " = " + roman);
        }
    }*/
    
    public static List<CodeValueVo> getIntToRoman(int startNo, int endNo) {
    	List<CodeValueVo> res = new ArrayList<CodeValueVo>();
        for (int n = startNo; n <= endNo; n++) {
        	CodeValueVo vo = new CodeValueVo();
            String roman = RomanNumber.toRoman(n);
            int number = RomanNumber.toNumber(roman);
            System.out.println(number + " = " + roman);
            if(number != 10 && number != 12){
            	vo.setCode(String.valueOf(number));
                vo.setValue(roman);
            	res.add(vo);
            }
        }
        return res;
    }
    
    public static List<CodeValueVo> getIntToRomanAll(int startNo, int endNo) {
    	List<CodeValueVo> res = new ArrayList<CodeValueVo>();
        for (int n = startNo; n <= endNo; n++) {
        	CodeValueVo vo = new CodeValueVo();
            String roman = RomanNumber.toRoman(n);
            int number = RomanNumber.toNumber(roman);
            System.out.println(number + " = " + roman);
        	vo.setCode(String.valueOf(number));
            vo.setValue(roman);
        	res.add(vo);
        }
        return res;
    }

    private static String toRoman(int number) {
        return String.valueOf(new char[number]).replace('\0', 'I')
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }

    private static Integer toNumber(String roman) {
        return roman.replace("CM", "DCD")
                .replace("M", "DD")
                .replace("CD", "CCCC")
                .replace("D", "CCCCC")
                .replace("XC", "LXL")
                .replace("C", "LL")
                .replace("XL", "XXXX")
                .replace("L", "XXXXX")
                .replace("IX", "VIV")
                .replace("X", "VV")
                .replace("IV", "IIII")
                .replace("V", "IIIII").length();
    }
}