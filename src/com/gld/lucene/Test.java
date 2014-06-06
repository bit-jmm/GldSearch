package com.gld.lucene;

import java.io.File;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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
//		StringBuffer stringBuffer = new StringBuffer();
//		stringBuffer.append("set");
//		stringBuffer.append("Method");
//		stringBuffer.delete(0, stringBuffer.length());
//		System.out.println(stringBuffer.toString());
		File indxeFile = new File("H:/lucene/gld");
		// 创建Directory对象
		Directory directory = FSDirectory.open(indxeFile);
		IndexReader ireader = IndexReader.open(directory);
		int docsNum = ireader.numDocs();
		System.err.println(docsNum);
		TermPositions termPositions = ireader.termPositions(new Term("name","304"));
		int i = 0;
		while (termPositions.next()) {
			if (i > 1000) {
				break;
			}
			System.err.println(termPositions.doc() + "   " + ireader.document(termPositions.doc()).get("name") + "   " + termPositions.nextPosition());
			i++;
		}
//		Document document = ireader.document(10000);
//		System.err.println(document.get("name"));
	}
}
