package com.gld.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.bit.myscore.GldScoreQuery;
import org.wltea.analyzer.lucene.IKAnalyzer;


public class GldSearch {
	
	/**
     * 首字母转大写
     * @param str
     * @return
     */
    public static String toFirstLetterUpperCase(String str) {  
        if(str == null || str.length() < 2){  
            return str;  
        }  
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());  
    }
    
	/**
     * 根据keyword搜索索引
     * @param analyzer
     * @param directory
     * @param keyword
     * @return
     */
    public List<ProductItem> searchIndexer(Analyzer analyzer, Directory directory, String keyword, int returnDocNum) {
        IndexReader ireader = null;
        IndexSearcher isearcher = null;
        List<ProductItem> result = new ArrayList<ProductItem>();
        try {
            // 设定搜索目录
            ireader = IndexReader.open(directory);
            isearcher = new IndexSearcher(ireader);

            // 对多field进行搜索
//            java.lang.reflect.Field[] fields = ProductItem.class.getDeclaredFields();
//            int length = fields.length;
//            String[] multiFields = new String[length];
//            
//            for (int i = 0; i < length; i++) {
//                multiFields[i] = fields[i].getName();
//            }
            String[] multiFields = new String[1];
            multiFields[0] = "name";
            MultiFieldQueryParser parser = new MultiFieldQueryParser(
                    Version.LUCENE_36, multiFields, analyzer);

            // 设定具体的搜索词
            Query query = parser.parse(keyword);
            System.out.println(query);
            GldScoreQuery gldScoreQuery = new GldScoreQuery(query, keyword);
            
            ScoreDoc[] hits = isearcher.search(gldScoreQuery, null, returnDocNum).scoreDocs;

            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("文档编号=" + hits[i].doc + "  文档评分=" + hits[i].score + "    ");
                ProductItem product = new ProductItem();
                for (String field : multiFields) {
                    String setMethodName = "set"+toFirstLetterUpperCase(field);
                    product.getClass().getMethod(setMethodName, String.class).invoke(product, hitDoc.get(field));
                }
                result.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
            	if (ireader != null) {
 					ireader.close();
 				}
                 if (isearcher != null) {
 					isearcher.close();
 				}
                 directory.close();
            } catch (IOException e) {
            }
        }
        return result;
    }
    
	public static void main(String args[]) throws IOException{
		
		GldSearch gldSearch = new GldSearch();
		// 存放索引的文件夹
		File indxeFile = new File("H:/lucene/gld");
		// 创建Directory对象
		Directory directory = FSDirectory.open(indxeFile);
		// 使用JcsegAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
		String keyword = "304不锈钢";
		
		List<ProductItem> result = gldSearch.searchIndexer(analyzer, directory, keyword, 100);
		
//		FileWriter fileWriter = new FileWriter(new File("H:/lucene/search_result/1/" + keyword + ".txt" ));
//		for (ProductItem productItem : result) {
//			fileWriter.write(productItem.getName() + "\n");
//		}
//		fileWriter.flush();
//		fileWriter.close();
//		
//		System.out.println(result.size());
	}

}
