package tools;

import javax.servlet.ServletRequest;

public class RequestUtil {
	
	private RequestUtil() {
		
	}
	
	public static String getString(ServletRequest request, String paramName, String alternativeValue) {
		String paramValue = request.getParameter(paramName);
		if (Utility.isEmptyString(paramValue)) {
			return alternativeValue;
		} else {
			return paramValue;
		}
	}
	
	public static Integer getInteger(ServletRequest request, String paramName, Integer alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (!Utility.isEmptyString(paramValue)) {
			return Integer.parseInt(paramValue);
		}
		return alternativeValue;
	}
	
	public static float getFloat(ServletRequest request, String paramName, float alternativeValue){
		String paramValue = request.getParameter(paramName);
		
		if (!Utility.isEmptyString(paramValue)) {
			return Float.parseFloat(paramValue);
			
		}
		return alternativeValue;
	}
	
	public static Integer getPositiveInteger(ServletRequest request, String paramName, Integer alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (!Utility.isEmptyString(paramValue)) {
			int tempValue = Integer.parseInt(paramValue);
			if (tempValue >= 0) {
				return tempValue;
			}
			
		}
		return alternativeValue;
	}
	
	public static Boolean getBoolean(ServletRequest request, String paramName, Boolean alternativeValue) {
		String paramValue = request.getParameter(paramName);
		
		if (!Utility.isEmptyString(paramValue)) {
				return Boolean.parseBoolean(paramValue);
		}
		
		return alternativeValue;
	}
}
