package com.springboot.demo.common;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@Log4j2
public abstract class AbstractLogAspect {

	public static final String C_PARAM_NAME = "controllerParam";
	public static final String C_RESULT = "controllerResult";
	public static final String ID = "id";
	public static final String S_PARAM_NAME = "serviceParam";
	public static final String S_RESULT = "serviceResult";
	public static final String METHOD = "method";
	public static final String STIME = "stime";
	public static final String CTIME = "ctime";
	public static final String DURATION = "duration";
	public static final String EXCEPTION = "exception";
	public static final String BASE_PACKAGE = "com.springboot.demo";
	private static String base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";

	private boolean isBase64(String str) {
	    return Pattern.matches(base64Pattern, str);
	}
	
	private Set<Class<?>> ignoreParams = new HashSet<>();

	private Set<Class<?>> baseParams = new HashSet<>();
	
	protected void addIgnore(Class<?> cls) {
		ignoreParams.add(cls);
	}

	public void init() {
		// 忽略的类型
		ignoreParams.add(HttpServletRequest.class);
		ignoreParams.add(HttpServletResponse.class);
		ignoreParams.add(HttpSession.class);
		ignoreParams.add(BindingResult.class);
		ignoreParams.add(MultipartFile.class);

		// 直接处理类型
		baseParams.add(Integer.class);
		baseParams.add(Byte.class);
		baseParams.add(Character.class);
		baseParams.add(Float.class);
		baseParams.add(Long.class);
		baseParams.add(Double.class);
		baseParams.add(Short.class);
		baseParams.add(Boolean.class);
		baseParams.add(String.class);
	}

	public Object controllerLogRecord(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = null;
		Exception te = null;
		long s = System.currentTimeMillis();
		try {
			obj = pjp.proceed();
		} catch (Exception e) {
			te = e;
		}
		long time = System.currentTimeMillis() - s;
		//分开写保证日志记录
		try {
			Map<String, Object> temp = LogUtil.get();
			if (temp == null) {
				temp = new HashMap<>();
			}
			Integer ctime = (Integer) temp.get(CTIME);
			if (ctime == null) {
				ctime = 1;
			}
			Map<String, Object> flag = new HashMap<>();
			flag.put(METHOD, pjp.getSignature().toString());
			flag.put(DURATION, time);
			flag.put(C_PARAM_NAME, parseParams(getNameAndValue(pjp)));
			flag.put(C_RESULT, obj);
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        String uri = request.getRequestURI();
			flag.put("uri", uri);
			temp.put("controller_" + ctime, flag);
			temp.put(CTIME, ++ctime);
			String str = StringUtils.replace(JsonUtils.getInstance(true).writeValueAsString(temp), "\\n", "\n");
			log.debug(StringUtils.replace(str, "\\t", "\t"), te);
		} catch (Exception e) {
			log.error("log record fail", e);
		} finally {
			LogUtil.remove();
		}
		if (null != te) {
			throw te;
		}
		return obj;
	}
	
	/**
	 * 
	 * desc: 如果开启histrix调用，暂时此处无法记录 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 * Object
	 */
	public Object serviceLogRecord(ProceedingJoinPoint pjp) throws Throwable {
		Object obj = null;
		Exception te = null;
		long s = System.currentTimeMillis();
		try {
			obj = pjp.proceed();
		} catch (Exception e) {
			te = e;
		}
		long time = System.currentTimeMillis() - s;
		//分开写保证日志记录
		try {
			Map<String, Object> temp = getTmp();
			Integer sTime = getTime(temp);
			Map<String, Object> flag = putStr(pjp, time, obj);
			putExceptionStr(flag, te);
			temp.put("service_" + sTime, flag);
			temp.put(STIME, ++sTime);
			LogUtil.set(temp);
		} catch (Exception e) {
			log.error("log record fail", e);
		}
		if (null != te) {
			throw te;
		}
		return obj;
	}
	protected Map<String, Object> getTmp()
	{
		Map<String, Object> temp = LogUtil.get();
		if (null == temp) {
			return new HashMap<>();
		}
		return temp;
	}

	protected Integer getTime(Map<String, Object> temp) {
		Integer sTime = (Integer) temp.get(STIME);
		if (null == sTime) {
			return 1;
		}
		return sTime;
	}
	protected Map<String, Object> putStr(ProceedingJoinPoint pjp, long time, Object obj) {
		Map<String, Object> flag = new HashMap<>();
		flag.put(ID, UUID.randomUUID().toString());
		flag.put(METHOD, pjp.getSignature().toString());
		flag.put(DURATION, time);
		flag.put(S_PARAM_NAME, parseParams(getNameAndValue(pjp)));
		flag.put(S_RESULT, obj);
		return flag;
	}
	protected void putExceptionStr(Map<String, Object> flag, Exception te)
	{
		if (null != te) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			te.printStackTrace(pw);
			String str = sw.toString();
			StringBuilder exStr = new StringBuilder("\n\t   ");
			boolean first = true;
			for(String ex : str.split("\\r\\n|\\r|\\n")) {
				if(first) {
					exStr.append(ex).append('\n');
					first = false;
				} else {
					if (ex.contains(BASE_PACKAGE)) {
						exStr.append(ex).append('\n');
					}
				}
			}
			flag.put(EXCEPTION, exStr);
		}
	}

	protected Map<String, Object> getNameAndValue(ProceedingJoinPoint joinPoint) {
		Map<String, Object> param = new HashMap<>();
		Object[] paramValues = joinPoint.getArgs();
		String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
		for (int i = 0; i < paramNames.length; i++) {
			param.put(paramNames[i], paramValues[i]);
		}
		return param;
	}

	protected Map<String, String> parseParams(Map<String, Object> params) {
		Map<String, String> result = new HashMap<>();
		for (Entry<String, Object> en : params.entrySet()) {
			addResult(result, en);
		}
		return result;
	}

	private void addResult(Map<String, String> result, Entry<String, Object> en)
	{
		Object v = en.getValue();
		String k = en.getKey();
		if(null != v && !(v instanceof BindingResult) && !judge(ignoreParams, v.getClass())) {
			if (judge(baseParams, v.getClass())) {
				if (v instanceof String && isBase64(v.toString()) && v.toString().length() > 50) { //这里对base64判断可能会出现不准确的情况，暂时解决方案如下
					result.put(k, JsonUtils.objectToJsonString(SerializeUtil.deserialize(v.toString())));
				} else {
					result.put(k, v.toString());
				}
			} else {
				result.put(k, JsonUtils.objectToJsonString(v));
			}
		}
	}

	protected boolean judge(Set<Class<?>> orig, Class<?> classes) {
		for (Class<?> t : orig) {
			if (t.isAssignableFrom(classes)) {
				return true;
			}
		}
		return false;
	}
}
