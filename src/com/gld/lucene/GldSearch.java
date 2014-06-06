package com.gld.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.bit.myscore.GldScoreQuery;
import org.bit.wordseg.IKWordSeg;
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
            /*
             * public void setDefaultFieldSortScoring(boolean doTrackScores,
                                       boolean doMaxScore)
				By default, no scores are computed when sorting by field (using Searcher.search(Query,Filter,int,Sort)). 
				You can change that, per IndexSearcher instance, by calling this method. Note that this will incur a CPU cost.
			Parameters:
				doTrackScores - If true, then scores are returned for every matching document in TopFieldDocs.
				doMaxScore - If true, then the max score for all matching docs is computed.
             */
            isearcher.setDefaultFieldSortScoring(true, false);
            
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
            
            // 对keyword分词
            IKWordSeg ikWordSeg = new IKWordSeg();
            
            GldScoreQuery gldScoreQuery = new GldScoreQuery(query, keyword);
            
//            ScoreDoc[] hits = isearcher.search(gldScoreQuery, returnDocNum ,new Sort(
//					        new SortField[]{
//					        		SortField.FIELD_SCORE,
//					                new SortField("updated_at", SortField.STRING, true)
//					            }
//					        )).scoreDocs;
            ScoreDoc[] hits = isearcher.search(gldScoreQuery, returnDocNum).scoreDocs;
//            ScoreDoc[] hits = isearcher.search(query, returnDocNum).scoreDocs;
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("文档内容=" + hitDoc.get("name") + "  文档评分=" + hits[i].score + "   日期=" + hitDoc.get("updated_at"));
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
//		directory = new RAMDirectory(directory);
		// 使用JcsegAnalyzer分词器
		Analyzer analyzer = new IKAnalyzer(true);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		String keyword = null;
		List<ProductItem> result = null;
		while (true) {
			
	        System.out.println("Enter query: ");
	        keyword = in.readLine();
	        if (keyword == null || keyword.length() == -1) {
	        	break;
	        }
	        keyword = keyword.trim();
	        if (keyword.length() == 0) {
	        	break;
	        }

	        result = gldSearch.searchIndexer(analyzer, directory, keyword, 100);
		
//			FileWriter fileWriter = new FileWriter(new File(
//					"H:/lucene/search_result/" + keyword + ".txt"));
//			for (ProductItem productItem : result) {
//				fileWriter.write(productItem.getName() + "\n");
//			}
//			fileWriter.flush();
//			fileWriter.close();
		}
		
		directory.close();
	
	}

}
