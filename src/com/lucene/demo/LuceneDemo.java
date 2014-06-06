package com.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.bit.myscore.GldScoreQuery;
import org.bit.wordseg.IKWordSeg;
import org.wltea.analyzer.lucene.IKAnalyzer;


/**
 * Lucene Demo
 * Author: herui
 * DateTime: 2014-3-31 下午3:39:54
 */
public class LuceneDemo {
    
    /**
     * 根据内容，构建索引
     * @param analyzer
     * @param directory
     * @param items
     * @return
     */
    private boolean buildIndexer(Analyzer analyzer, Directory directory, List<Item> items) {
        IndexWriter iwriter = null;
        try {
            // 配置索引
            iwriter = new IndexWriter(directory, new IndexWriterConfig(
                    Version.LUCENE_36, analyzer));
            // 删除所有document
            iwriter.deleteAll();
            // 将文档信息存入索引
            Document doc[] = new Document[items.size()];
            for (int i = 0; i < items.size(); i++) {
                doc[i] = new Document();
                
                Item item = items.get(i);
                java.lang.reflect.Field[] fields = item.getClass().getDeclaredFields();
                for (java.lang.reflect.Field field : fields) {
                    String fieldName = field.getName();
                    String getMethodName = "get"+toFirstLetterUpperCase(fieldName);
                    Object obj = item.getClass().getMethod(getMethodName).invoke(item);
                    doc[i].add(new Field(fieldName, (String)obj, Field.Store.YES, Field.Index.ANALYZED));
                }
//                doc[i].add(new Field("content", item.getContent(), Field.Store.YES, Field.Index.ANALYZED));
//                System.out.println(doc[i].get("content"));
                iwriter.addDocument(doc[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
            	iwriter.commit();
                iwriter.close();
            } catch (IOException e) {
            }
        }
        return true;
    }
    
    /**
     * 根据keyword搜索索引
     * @param analyzer
     * @param directory
     * @param keyword
     * @return
     */
    public List<Item> searchIndexer(Analyzer analyzer, Directory directory, String keyword) {
        IndexReader ireader = null;
        IndexSearcher isearcher = null;
        List<Item> result = new ArrayList<Item>();
        try {
            // 设定搜索目录
            ireader = IndexReader.open(directory);
            isearcher = new IndexSearcher(ireader);


            java.lang.reflect.Field[] fields = Item.class.getDeclaredFields();
            int length = fields.length;
            String[] multiFields = new String[length];
            for (int i = 0; i < length; i++) {
                multiFields[i] = fields[i].getName();
            }
            
            QueryParser parser = new QueryParser(
                    Version.LUCENE_36, "content", analyzer);

            // 设定具体的搜索词
            Query query = parser.parse(keyword);
            System.out.println(query.toString());
            
            // 对keyword分词
            IKWordSeg ikWordSeg = new IKWordSeg();
            
            GldScoreQuery gldScoreQuery = new GldScoreQuery(query, keyword);
            
            ScoreDoc[] hits = isearcher.search(gldScoreQuery, null, 10).scoreDocs;

            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = isearcher.doc(hits[i].doc);
                System.out.println("文档编号=" + hits[i].doc + "  文档评分=" + hits[i].score + "    ");
                Item item = new Item();
                for (String field : multiFields) {
                    String setMethodName = "set"+toFirstLetterUpperCase(field);
                    item.getClass().getMethod(setMethodName, String.class).invoke(item, hitDoc.get(field));
                }
                result.add(item);
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
    
    public static void main(String[] args) throws Exception {
        LuceneDemo demo = new LuceneDemo();
//        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
//        Analyzer analyzer = new JcsegAnalyzer4X(JcsegTaskConfig.SIMPLE_MODE);
        
        Analyzer analyzer = new IKAnalyzer(true);
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("0", "first", "304不锈钢"));
        items.add(new Item("1", "second", "304不锈钢"));
        items.add(new Item("2", "third", "不锈钢304"));
        items.add(new Item("3", "four", "不锈钢304"));
        items.add(new Item("4", "five", "304不锈钢"));
        items.add(new Item("5", "five", "304"));
        items.add(new Item("6", "five", "不锈钢"));
        
        // 索引存到内存中的目录
//        Directory directory = new RAMDirectory();
        // 索引存储到硬盘
        File file = new File("E:/lucene");
        Directory directory = FSDirectory.open(file);
        demo.buildIndexer(analyzer, directory, items);
        List<Item> result = demo.searchIndexer(analyzer, directory, "304不锈钢");
        
        for (Item item : result) {
            System.out.println(item.toString());
        }
    }
}
