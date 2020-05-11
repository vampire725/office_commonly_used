package com.sinosoft.ap.util.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实体类工具，用于处理实体类内部属性
 *
 */
public class EntityUtil {
	
	private EntityUtil () {}

	/**
	 * 遍历Entity内的所有属性，并且判断是否需要模糊查询
	 *
	 */
	public static Object deal(Object object) throws Exception{
		Field[] files = object.getClass().getDeclaredFields();
		for(int i=0;i<files.length;i++){
			String name = files[i].getName();
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
			String type = files[i].getGenericType().toString();
			if(type.equals("class java.lang.String")){
				if(name.endsWith("Name")||name.endsWith("Desc")){
					Method[] methods = object.getClass().getMethods();
					Method setMethod = String.class.getMethod("length");;
					Method getMethod = String.class.getMethod("length");;
					for(Method method:methods){
						if(method.getName().endsWith("get"+name)){
							getMethod = method;
						}
						if(method.getName().endsWith("set"+name)){
							setMethod = method;
						}
					}
					String value = (String) getMethod.invoke(object);
					if(null!=value&&value.endsWith(":f")){
						setMethod.invoke(object, "LIKE '%"+value.substring(0, value.length()-2)+"%'");
					}
					else if(null!=value&&value!=""){
						setMethod.invoke(object, "= '"+value+"'");
					}
				}
			}
			if(type.equals("class java.util.Date")){
				
			}
		}
		return object;	
	} 
	
	/**
	 * 用于遍历传入实体类内部属性，除去以“ID”结尾属性，如有非空，则为true
	 * @param object
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean isEmpty(Object object) throws Exception{
		Field[] files = object.getClass().getDeclaredFields();
		boolean flag =false ;
		for(int i=0;i<files.length;i++){
			String name = files[i].getName();
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
			if(name.endsWith("Id")){
				continue;
			}
			Method[] methods = object.getClass().getMethods();
			Method getMethod = String.class.getMethod("length");;
			for(Method method:methods){
				if(method.getName().endsWith("get"+name)){
					getMethod = method;
				}
			}
			if(null!=getMethod.invoke(object)){
				return true;
			}
		}
		return flag;	
	} 
	
	/**
	 * 
	 * @param object
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static boolean isNull(Object object) throws Exception
//	NoSuchMethodException, 
//	SecurityException, 
//	IllegalAccessException, 
//	IllegalArgumentException, 
//	InvocationTargetException
	{
		Field[] files = object.getClass().getDeclaredFields();
		boolean flag =false ;
		for(int i=0;i<files.length;i++){
			String name = files[i].getName();
			name = name.substring(0, 1).toUpperCase()+name.substring(1);
			if(name.endsWith("Id")){
				continue;
			}
			Method[] methods = object.getClass().getMethods();
			Method getMethod = String.class.getMethod("length");;
			for(Method method:methods){
				if(method.getName().endsWith("get"+name)){
					getMethod = method;
				}
			}
			if(null==getMethod.invoke(object)){
				continue;
			}
			if(null==getMethod.invoke(object)){
				continue;
			}
		}
		return flag;	
	} 
}
