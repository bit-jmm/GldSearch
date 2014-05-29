package com.gld.lucene;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IndexCreater {

	private List<ProductItem> productList;

	/**
	 * 首字母转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(str.substring(0, 1).toUpperCase());
		stringBuffer.append(str.substring(1, str.length()));
		return stringBuffer.toString();
	}

	public void createIndexForProduct() throws IOException,
			ClassNotFoundException {
		// 存放索引的文件夹
		File indxeFile = new File("H:/lucene/gld");
		// 创建Directory对象
		Directory directory = FSDirectory.open(indxeFile);
		// 使用IKAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		// 创建IndexWriterConfig
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				Version.LUCENE_36, analyzer);
		// 创建IndexWriter
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		// 删除所有document
		indexWriter.deleteAll();
		String fieldName;
		StringBuffer setMethodName = new StringBuffer();
		StringBuffer getMethodName = new StringBuffer();
		// 从数据库中读取出所有的记录以便进行索引的创建
		try {
			DBSource dbSource = DBSource.getInstance();
			Connection conn = dbSource.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			productList = new ArrayList<ProductItem>();
			int j = 0;
			int l = 0;
			for (int k = 0; k < 80; k++) {
				String sql = "SELECT * FROM products where id > "
						+ String.valueOf(k * 100000) + " and id <= "
						+ String.valueOf((k + 1) * 100000);
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					j++;
					if (j % 10000 == 0) {
						System.out.println("select:" + j);
					}
					ProductItem productItem = new ProductItem();
					java.lang.reflect.Field[] fields = productItem.getClass()
							.getDeclaredFields();
					for (java.lang.reflect.Field field : fields) {
						if (field.getName().equals("serialVersionUID")) {
							continue;
						}
						fieldName = field.getName();
						setMethodName.delete(0, setMethodName.length());
						setMethodName.append("set");
						setMethodName.append(toFirstLetterUpperCase(fieldName));
						Object obj = productItem.getClass()
								.getMethod(setMethodName.toString(), new Class[] { String.class })
								.invoke(productItem, rs.getString(fieldName));
					}
					productList.add(productItem);
				}
				
				// 将文档信息存入索引

				for (int i = 0; i < productList.size(); i++) {
					ProductItem product = productList.get(i);
					// 建立一个lucene文档
					Document doc = new Document();
					l++;
					if (l % 10000 == 0) {
						System.out.println("index:" + l);
					}
					java.lang.reflect.Field[] fields = product.getClass()
							.getDeclaredFields();
					for (java.lang.reflect.Field field : fields) {
						if (field.getName().equals("serialVersionUID")) {
							continue;
						}
						fieldName = field.getName();
						getMethodName.delete(0, getMethodName.length());
						getMethodName.append("get");
						getMethodName.append(toFirstLetterUpperCase(fieldName));
						Object obj = product.getClass()
								.getMethod(getMethodName.toString()).invoke(product);
						if (obj == null) {
							obj = "";
						}
						doc.add(new Field(fieldName, (String) obj,
								Field.Store.YES, Field.Index.ANALYZED));
					}

					indexWriter.addDocument(doc);
				}
				productList.clear();
				rs.close();
				stmt.close();
			}
			indexWriter.commit();
			indexWriter.close();
			dbSource.closeAll(rs, stmt, conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		IndexCreater indexCreater = new IndexCreater();
		indexCreater.createIndexForProduct();
	}
}
