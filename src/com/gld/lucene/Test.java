package com.gld.lucene;

public class Test {
	public static void main(String[] args) throws Exception {
//		ProductItem productItem = new ProductItem();
//		java.lang.reflect.Field[] fields = productItem.getClass().getDeclaredFields();
//		for (Field field : fields) {
//			System.out.print(field.getName() + "  ");
//			System.out.println(field.getType());
//			productItem.getClass()
//			.getMethod("setId",new Class[] { String.class });
//		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("set");
		stringBuffer.append("Method");
		stringBuffer.delete(0, stringBuffer.length());
		System.out.println(stringBuffer.toString());
	}
}
