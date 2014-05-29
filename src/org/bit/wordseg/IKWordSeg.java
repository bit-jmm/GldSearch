package org.bit.wordseg;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKWordSeg {
	public ArrayList<String> wordSeg(String sentence){
		ArrayList<String> arrayList = new ArrayList<String>();
        //创建分词对象  
        Analyzer analyzer = new IKAnalyzer(true);       
        StringReader reader = new StringReader(sentence);  
        //分词  
        TokenStream ts = analyzer.tokenStream("", reader);  
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);  
        //遍历分词数据  
        try {
			while(ts.incrementToken()){  
			    arrayList.add(term.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        reader.close();
        analyzer.close();
        return arrayList;
	}
	
	public static void main(String [] args) throws IOException {
		IKWordSeg ikWordSeg = new IKWordSeg();
		ArrayList<String> arrayList = ikWordSeg.wordSeg("304不锈钢");
		for (String string : arrayList) {
			System.out.println(string);
		}
	}
}
