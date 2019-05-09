/*
 * *********************************************************************************************
 *    Copyright (c) 2019. YNT CO., LTD. All Rights Reserved.
 *    FileName    : JsonUtils.java
 *    Date        : 2019-4-29
 *    Author      : 梁宇 （Eid Leung）
 *    Module      : JsonUtils
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.springboot.demo.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class JsonUtils {

	private JsonUtils() {
	}

	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static {
		init(objectMapper, false);
	}

	private static void init(ObjectMapper objectMapper, boolean indent) {
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule().addSerializer(LocalDateTime.class,
				new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT))));
		if (indent) {
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
	}

	public static ObjectMapper getInstance(boolean indent) {
		ObjectMapper objectMapper = new ObjectMapper();
		init(objectMapper, indent);
		return objectMapper;
	}

	/**
	 *
	 * desc: 对象转JsonString
	 *
	 * @param value
	 * @return String
	 */
	public static String objectToJsonString(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			log.error("Json parse string err", e);
		}
		return null;
	}

	/**
	 *
	 * desc: 对象转json byte数组
	 *
	 * @param value
	 * @return byte[]
	 */
	public static byte[] objectToJsonByte(Object value) {
		try {
			return objectMapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			log.error("json parse byte err", e);
		}
		return new byte[0];
	}

	/**
	 *
	 * desc: json字符串转对象
	 *
	 * @param jsonString
	 * @param clazz
	 * @return T
	 */
	public static <T> T jsonStringToObject(String jsonString, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			log.error("json parse obj", e);
		}
		return null;
	}

	/**
	 *
	 * desc: json byte数组转对象
	 *
	 * @param jsonByte
	 * @param clazz
	 * @return T
	 */
	public static <T> T jsonBytesToObject(byte[] jsonByte, Class<T> clazz) {
		try {
			return objectMapper.readValue(jsonByte, clazz);
		} catch (IOException e) {
			log.error("json parse obj", e);
		}
		return null;
	}

	/**
	 *
	 * desc: 从流读取数据转对象
	 *
	 * @param in
	 * @param clazz
	 * @return T
	 */
	public static <T> T inStreamToObject(InputStream in, Class<T> clazz) {
		try {
			return objectMapper.readValue(in, clazz);
		} catch (IOException e) {
			log.error("json parse obj err", e);
		}
		return null;
	}

	/**
	 * 
	 * desc: 对象以json格式输出到流
	 * 
	 * @param out
	 * @param value void
	 */
	public static void objectToOutStream(OutputStream out, Object value) {
		try {
			objectMapper.writeValue(out, value);
		} catch (IOException e) {
			log.error("json parse stream err", e);
		}
	}

	/**
	 * 
	 * desc: josnString转JsonNode
	 * 
	 * @param jsonString
	 * @return JsonNode
	 */
	public static JsonNode jsonStringToJsonNode(String jsonString) {
		try {
			return objectMapper.readTree(jsonString);
		} catch (IOException e) {
			log.error("json parse jsonNode err", e);
		}
		return null;
	}

	/**
	 * 
	 * desc: object转JsonNode
	 * 
	 * @param object
	 * @return JsonNode
	 */
	public static JsonNode objectToJsonNode(Object object) {
		try {
			return objectMapper.readTree(objectToJsonByte(object));
		} catch (IOException e) {
			log.error("json parse jsonNode err", e);
		}
		return null;
	}

}
