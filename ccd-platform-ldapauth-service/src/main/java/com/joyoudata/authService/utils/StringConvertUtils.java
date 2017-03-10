package com.joyoudata.authService.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringConvertUtils {
	
	public static Set<String> stringToSet(String source) {
		if (source != null) {
			String[] split = source.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			Set<String> set = new HashSet<>(Arrays.asList(split));
			return set;
		}else{
			return null;
		}
	}
}
