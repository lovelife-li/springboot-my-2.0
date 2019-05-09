package com.springboot.demo.common;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Aspect
@Log4j2
public class LogAspect extends AbstractLogAspect {
	@Override
	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	@Around(value = "within(com.springboot.demo.controller..*) && (@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))")
	public Object controllerLogRecord(ProceedingJoinPoint pjp) throws Throwable {
		return super.controllerLogRecord(pjp);
	}
	
	/**
	 * 
	 * desc: 如果开启histrix调用，暂时此处无法记录 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 * Object
	 */
	@Override
	@Around(value = "execution(* com.springboot.demo.service.impl..*.*(..)) || execution(* com.baomidou.mybatisplus.extension.service.impl.ServiceImpl.*(..))")
	public Object serviceLogRecord(ProceedingJoinPoint pjp) throws Throwable {
		return super.serviceLogRecord(pjp);
	}
}
