package com.gabrisal.api.common.util;

import org.apache.commons.lang3.StringUtils;

public class MaskingUtil {

    private static final String MASKING_CHARACTER = "*";

    /**
     * 첫 글자와 마지막 글자를 제외한 이름은 모두 “*”로 마스킹 한다.
     * ex) 홍길동 → 홍*동, SangHoon → S******n
     * 이름이 두 글자인 경우에는 마지막 글자를 마스킹 한다.
     * ex) 길동 → 길*
     * @param name
     * @return masked name
     */
    public static String getMaskedName(String name) {
        if (StringUtils.isEmpty(name)) { return name; }
        if (name.length() > 2) {
            return name.replaceAll("(?<=.{1}).(?=.{1})", MASKING_CHARACTER);
        }
        return name.replaceAll("(?<=.{1}).", MASKING_CHARACTER);
    }
}
