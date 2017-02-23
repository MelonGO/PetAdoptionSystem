package tools;

public class Utility {

	private Utility() {}
	
	public static boolean isEmptyString(String src){
		return src == null || src.trim().isEmpty();
	}
	
}
