package com.subscrdetails.subscrdetails.utils;

import java.util.HashMap;
import java.util.Map;

public class FiTranslit1 {

    //private final String USER_AGENT = "Mozilla/5.0";

    private static Map<Character, String> _chars = null;

    static {
        _chars = new HashMap<Character, String>();
        _chars.put('À', "ა");
        _chars.put('Á',"ბ");
        _chars.put('Â',"გ");
        _chars.put('Ã',"დ");
        _chars.put('Ä',"ე");
        _chars.put('Å', "ვ");
        _chars.put('Æ', "ზ");
        _chars.put('È', "თ");
        _chars.put('É', "ი");
        _chars.put('Ê', "კ");
        _chars.put('Ë', "ლ");
        _chars.put('Ì', "მ");
        _chars.put('Í', "ნ");
        _chars.put('Ï', "ო");
        _chars.put('Ð', "პ");
        _chars.put('Ñ', "ჟ");
        _chars.put('Ò', "რ");
        _chars.put('Ó', "ს");
        _chars.put('Ô', "ტ");
        _chars.put('Ö', "უ");
        _chars.put('×', "ფ");
        _chars.put('Ø', "ქ");
        _chars.put('Ù', "ღ");
        _chars.put('Ú', "ყ");
        _chars.put('Û', "შ");
        _chars.put('Ü', "ჩ");
        _chars.put('Ý', "ც");
        _chars.put('Þ', "ძ");
        _chars.put('ß', "წ");
        _chars.put('à', "ჭ");
        _chars.put('á', "ხ");
        _chars.put('ã', "ჯ");
        _chars.put('ä', "ჰ");
    }

    public static String translit(String str_msg) {

        StringBuilder sb = new StringBuilder();

        for (char c : str_msg.toCharArray()) {
            if (_chars.containsKey(c)) {
                sb.append(_chars.get(c));
            }
            else {
                sb.append(c);
            }
        }

        return sb.toString();

    }

}