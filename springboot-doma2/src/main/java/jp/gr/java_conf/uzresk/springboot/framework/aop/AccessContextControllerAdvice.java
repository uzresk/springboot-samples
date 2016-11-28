package jp.gr.java_conf.uzresk.springboot.framework.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AccessContextControllerAdvice {

	@Around("bean(*Controller)")
	public Object accessContext(ProceedingJoinPoint jp) throws Throwable {

		Method method = ((MethodSignature) jp.getSignature()).getMethod();
		Annotation[] annotations = AnnotationUtils.getAnnotations(method);

		// move @GetMapping,@PostMapping, and *Mapping only
		if (Arrays.stream(annotations).anyMatch(a -> a.toString().indexOf("Mapping") > -1)) {
			AccessContext.setUserName();
			AccessContext.setSystemDate();
		}

		try {
			Object result = jp.proceed();
			return result;
		} catch (Throwable e) {
			throw e;
		} finally {
			AccessContext.destroy();
		}
	}
}
