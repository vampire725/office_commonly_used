package com.sinosoft.ap.component.logging.aop;

import com.sinosoft.ap.util.result.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.print.attribute.standard.ReferenceUriSchemesSupported;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.util.StringUtil;
import com.sinosoft.ap.component.logging.level.DefaultLogLevel;
import com.sinosoft.ap.component.logging.level.SystemConfig;
import com.sinosoft.ap.util.result.ResultConstant;

@Component
@Aspect
public class AopOperation {

	private static final Logger logger = LogManager.getLogger(AopOperation.class);

    @Pointcut("execution (* com.sinosoft..*..domain.*.*(..))")
	public void daoPointcut() {
	}

	@Pointcut("execution (* com.sinosoft..*..service.*.*(..))")
	public void servicePointcut() {
	}

	@Pointcut("execution (* com.sinosoft..*..web.*.*(..))")
	public void controllerPointcut() {
	}

    @Value("${logging.web.enable:true}")
    private boolean loggingWebEnable;

    @Value("${logging.service.enable:true}")
    private boolean loggingServiceEnable;

    @Value("${logging.dao.enable:true}")
    private boolean loggingDaoEnable;

    @Value("${spring.application.name:}")
    private String serverName;

 /*   @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
    }

    @After("pointCut()")
    public void doAfter(JoinPoint joinPoint){
    }*/

    @AfterReturning(pointcut="controllerPointcut()",returning="returnVal")
    public void afterReturn(JoinPoint joinPoint,Object returnVal){
    }

    @AfterThrowing(pointcut="controllerPointcut()",throwing="error")
    public void afterControllerThrowing(JoinPoint joinPoint,Throwable error){
    	Map<String, String> userInfo = getUserInfo();
        String username = userInfo.getOrDefault("username","");
        String organName = userInfo.getOrDefault("organName","");
        String systemName = userInfo.getOrDefault("systemName","");
        String className = joinPoint.getTarget().getClass().getName();
//		String username = userInfo.get("username");
//		String organName = userInfo.get("organName");
//		String systemName = userInfo.get("systemName");
		String logLevel = DefaultLogLevel.getLogLevel().getCode();
		if(StringUtil.isEmpty(logLevel)){
			logLevel = "DEBUG";
		}
//		logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+ex.toString()+"]");
        logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[控制层]-["+className+"]-["+error.toString()+"]");
    }

//    @AfterThrowing(pointcut="servicePointcut()",throwing="error")
//    public void afterServiceThrowing(JoinPoint joinPoint,Throwable error){
//    	Map<String, String> userInfo = getUserInfo();
//        String username = userInfo.getOrDefault("username","");
//        String organName = userInfo.getOrDefault("organName","");
//        String systemName = userInfo.getOrDefault("systemName","");
//        String className = joinPoint.getTarget().getClass().getName();
//		String logLevel = DefaultLogLevel.getLogLevel().getCode();
//		if(StringUtil.isEmpty(logLevel)){
//			logLevel = "DEBUG";
//		}
//		logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[服务层]-["+className+"]-["+error.toString()+"]");
//    }

    @AfterThrowing(pointcut="daoPointcut()",throwing="error")
    public void afterDaoThrowing(JoinPoint joinPoint,Throwable error){
    	Map<String, String> userInfo = getUserInfo();
        String username = userInfo.getOrDefault("username","");
        String organName = userInfo.getOrDefault("organName","");
        String systemName = userInfo.getOrDefault("systemName","");
        String className = joinPoint.getTarget().getClass().getName();
//		String username = userInfo.get("username");
//		String organName = userInfo.get("organName");
//		String systemName = userInfo.get("systemName");
		String logLevel = DefaultLogLevel.getLogLevel().getCode();
		if(StringUtil.isEmpty(logLevel)){
			logLevel = "DEBUG";
		}
        logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[实体类层]-["+className+"]-["+error.toString()+"]");
//		logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+error.toString()+"]");
    }

    @Around("controllerPointcut()")
    public Object  controllerPointcut(ProceedingJoinPoint pjp) throws Throwable{
        Object retVal = null ;
        String methodName = pjp.getSignature().getName();
		Map<String, String> userInfo = getUserInfo();
        String username = userInfo.getOrDefault("username","");
        String organName = userInfo.getOrDefault("organName","");
        String systemName = userInfo.getOrDefault("systemName","");
//		String username = userInfo.get("username");
//		String organName = userInfo.get("organName");
//		String systemName = userInfo.get("systemName");
		String targetName = pjp.getTarget().getClass().toString();
		Object[] arguments = pjp.getArgs();
		Class<?>[] argumentTypes = getMethod(pjp);
		String logLevel = DefaultLogLevel.getLogLevel().getCode();
		if(StringUtil.isEmpty(logLevel)){
			logLevel = "DEBUG";
		}
		logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[控制层]"+"["+targetName+"]");
		logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[控制层]"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]");
		Long firstMillis = System.currentTimeMillis();
        retVal = pjp.proceed();
//		try {
//			 retVal = pjp.proceed();
//		} catch (Throwable e) {
//			StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw, true));
//			logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+sw.toString()+"]");
//			return new Result(ResultConstant.FAIL_STATU,e.toString());
//		}
		logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[控制层]"+"["+targetName+"]-"+"["+buildMethodString(methodName, argumentTypes, arguments));
		logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"[控制层]"+"["+targetName+"]-"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]-["+ (System.currentTimeMillis() - firstMillis) + "ms]");
		return retVal;
    }
//    @Around("servicePointcut()")
//    public Object  servicePointcut(ProceedingJoinPoint pjp) throws Throwable{
//        if(loggingServiceEnable){
//            Map<String, String> userInfo = getUserInfo();
////        String username = userInfo.get("username");
////        String organName = userInfo.get("organName");
////        String systemName = userInfo.get("systemName");
//            String username = userInfo.getOrDefault("username","");
//            String organName = userInfo.getOrDefault("organName","");
//            String systemName = userInfo.getOrDefault("systemName","");
//            Object retVal = null ;
//            String targetName = pjp.getTarget().getClass().toString();
//            String methodName = pjp.getSignature().getName();
//            Object[] arguments = pjp.getArgs();
//            Class<?>[] argumentTypes = getMethod(pjp);
//            String logLevel = DefaultLogLevel.getLogLevel().getCode();
//            if(StringUtil.isEmpty(logLevel)){
//                logLevel = "DEBUG";
//            }
//            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]");
//            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]");
//            Long firstMillis = System.currentTimeMillis();
//            retVal = pjp.proceed();
////        try {
////             retVal = pjp.proceed();
////        } catch (Throwable e) {
////            StringWriter sw = new StringWriter();
////            e.printStackTrace(new PrintWriter(sw, true));
////            logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+sw.toString()+"]");
////        }
//            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]-"+"["+buildMethodString(methodName, argumentTypes, arguments));
//            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]-"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]-["+ (System.currentTimeMillis() - firstMillis) + "ms]");
//            return retVal;
//        }else{
//            return pjp.proceed();
//        }
//    }

    @Around("daoPointcut()")
    public Object  daoPointcut(ProceedingJoinPoint pjp) throws Throwable{
        if(loggingDaoEnable){
            Map<String, String> userInfo = getUserInfo();
            String username = userInfo.getOrDefault("username","");
            String organName = userInfo.getOrDefault("organName","");
            String systemName = userInfo.getOrDefault("systemName","");
//        String username = userInfo.get("username");
//        String organName = userInfo.get("organName");
//        String systemName = userInfo.get("systemName");
            Object retVal = null ;
            String targetName = pjp.getTarget().getClass().toString();
            String methodName = pjp.getSignature().getName();
            Object[] arguments = pjp.getArgs();
            String logLevel = DefaultLogLevel.getLogLevel().getCode();
            if(StringUtil.isEmpty(logLevel)){
                logLevel = "DEBUG";
            }
            Class<?>[] argumentTypes = getMethod(pjp);
            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]");
            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]");
            Long firstMillis = System.currentTimeMillis();
            retVal = pjp.proceed();
//        try {
//             retVal = pjp.proceed();
//        } catch (Throwable e) {
//            StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw, true));
//            logger.log(Level.getLevel("ERROR"),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+sw.toString()+"]");
//            return new Result(ResultConstant.FAIL_STATU,e.toString());
//        }
            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]-"+"[" + targetName+ "." + buildMethodString(methodName, argumentTypes, arguments)+ "" );
            logger.log(Level.getLevel(logLevel),"["+systemName+"]-"+"["+organName+"]-"+"["+username+"]-"+"["+targetName+"]-"+"["+buildMethodString(methodName,argumentTypes,arguments)+"]-["+ (System.currentTimeMillis() - firstMillis) + "ms]");
            return retVal;
        }else{
            return pjp.proceed();
        }
    }

    /**
	 * 获取拦截方法
	 *
	 * @param joinPoint
	 * @return
	 */
	public Class<?>[] getMethod(ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		Method[] methods = joinPoint.getTarget().getClass().getMethods();
		for (Method m : methods) {
			if (m.getName().equals(methodName)) {
				return m.getParameterTypes();
			}
		}
		return null;
	}
	public String buildMethodString(String methodName,Class<?>[] argumentTypes, Object[] arguments) {
		String result = new String(methodName + "(");
		if (arguments != null && arguments.length > 0 && argumentTypes != null
				&& argumentTypes.length == arguments.length){
            for (int i = 0; i < arguments.length; i++) {
                String paramName = argumentTypes[i].getName();
                paramName = paramName.substring(paramName.lastIndexOf(".") + 1,
                        paramName.length());
                String prefix = "";
                if (i == 0) {
                    prefix = "";
                } else {
                    prefix = ",";
                }
                result += prefix + paramName + " " + "param" + (i + 1);
            }
        }
		return result + ")";
	}
	// 该方法使用HS256算法和Secret:secret生成signKey
	private static Key getKeyInstance(String secret) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}

	private Map<String,String> getUserInfo(){
		Map<String,String> userInfoMap = new HashMap<String,String>();
//		System.out.println("==="+((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()));
		if(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()) == null){
			return userInfoMap;
		}
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token  = "";
		token = request.getHeader("authorization");
		if(null==token || token.equals("")){
            Cookie[] cookies = request.getCookies();
            if(null==cookies){
                return userInfoMap;
            }
            for (Cookie cookie : cookies) {
                switch(cookie.getName()){
                    case "token":
                        token = cookie.getValue();
                        break;
                    default:
                        break;
                }
            }
        }
		String username = "";
		String organName ="";
		if(!StringUtil.isEmpty(token)){
			Map<String, Object> userInfo = Jwts.parser().setSigningKey(getKeyInstance("15620952172")).parseClaimsJws(token).getBody();
			username = (String) userInfo.get(ResultConstant.USER_NAME);
			organName = (String) userInfo.get(ResultConstant.ORGAN_NAME);
		}
//		String systemName = SystemConfig.getSystemConfig();
		userInfoMap.put("username", username);
		userInfoMap.put("organName", organName);
		userInfoMap.put("systemName", serverName);
		return userInfoMap;
	}
}