package org.example.jdbcexamples.dto;

public record GithubOptionType(String propName, String operator, Object value) {
    public static final String GREAT_EQUAL = ">=";
    public static final String LESS_EQUAL = "<=";
    public static final String EQUAL = "=";
}

