package com.joyoudata.authService.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
	
	public static List<String> stringToList(String source) {
		if (source != null) {
			String[] split = source.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "").split(",");
			List<String> list = new ArrayList<>(Arrays.asList(split));
			return list;
		}else{
			return null;
		}
	}
}
