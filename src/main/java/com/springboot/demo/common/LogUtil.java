package com.springboot.demo.common;

import java.util.Map;

public class LogUtil {
	private LogUtil(){}

	public static final ThreadLocal<Map<String, Object>> LOG = new ThreadLocal<>();

	public static Map<String, Object> get() {
		return LOG.get();
	}
	
	public static void set(Map<String, Object> map) {
		LOG.set(map);
	}

	public static void remove() {
		LOG.remove();
	}
}
