package com.bench.training.util;

public class Formatter {
	public static String capitalize(String s) {
        if (s.length() == 0) {
        	return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
