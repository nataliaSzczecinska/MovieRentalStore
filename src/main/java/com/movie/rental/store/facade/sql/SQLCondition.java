package com.movie.rental.store.facade.sql;

public class SQLCondition {
    public static String sqlCondition(String text) {
        return ("%" + text + "%");
    }
}