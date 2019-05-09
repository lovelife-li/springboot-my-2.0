/*
 * *********************************************************************************************
 *    Copyright (c) 2019. YNT CO., LTD. All Rights Reserved.
 *    FileName    : SerializeUtil.java
 *    Date        : 2019-4-20
 *    Author      : 梁宇 （Eid Leung）
 *    Module      : SerializeUtil
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.springboot.demo.common;

import lombok.extern.slf4j.Slf4j;
import sun.applet.Main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class SerializeUtil {

	private SerializeUtil(){}
	public static <T> T deserialize(String str) {
		return deserialize(Base64.getDecoder().decode(str.getBytes()));
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(byte[] bytes) {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
			return (T) objectInputStream.readObject();
		} catch (IOException |ClassNotFoundException e) {
			log.error("deserialize err", e);
		}
		try {
			byteArrayInputStream.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public static byte[] serializeToByte(Object obj) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
			objectOutputStream.writeObject(obj);
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			log.error("serialize err", e);
		}
		try {
			byteArrayOutputStream.close();
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
		return new byte[0];
	}
	
	public static String serializeToUrlSafeString(Object obj) throws UnsupportedEncodingException {
		byte[] bytes = serializeToByte(obj);
		if (null == bytes) {
			return null;
		}
		return URLEncoder.encode(Base64.getEncoder().encodeToString(bytes), StandardCharsets.UTF_8.name());
	}
	
	public static String serializeToString(Object obj) {
		byte[] bytes = serializeToByte(obj);
		if (null == bytes) {
			return null;
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static void main(String[] args) {
		System.out.println(StandardCharsets.UTF_8.name());
	}

}
