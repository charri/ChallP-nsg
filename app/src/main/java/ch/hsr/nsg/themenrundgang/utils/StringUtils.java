package ch.hsr.nsg.themenrundgang.utils;

public class StringUtils {
	public static String join(String[] arr, String delimeter) {
		StringBuilder builder = new StringBuilder();
		
		for(int i=0; i < arr.length; i++) {
			builder.append(arr[i]);
			
			if(i != arr.length - 1) builder.append(delimeter);
		}
		return builder.toString();
	}
	
	public interface Func<T> {
		public String func(T value);
	}
	
	public static <T> String join(T[] arr, Func<T> op, String delimeter) {
		String[] stringArr = new String[arr.length];
		for(int i=0; i < arr.length; i++) {
			stringArr[i] = op.func(arr[i]);
		}
		return join(stringArr, delimeter);
	}
}
