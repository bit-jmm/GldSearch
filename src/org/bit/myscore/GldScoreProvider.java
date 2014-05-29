package org.bit.myscore;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.function.CustomScoreProvider;
import org.bit.wordseg.IKWordSeg;

public class GldScoreProvider extends CustomScoreProvider {
	String keyword = null;
	public GldScoreProvider(IndexReader reader, String keyword) {
		super(reader);
		this.keyword = keyword;
	}
	
	public boolean isTermSortInProductName(String productName) {
		IKWordSeg ikWordSeg = new IKWordSeg();
		ArrayList<String> keywordSeg = ikWordSeg.wordSeg(keyword);
		ArrayList<String> productNameSeg = ikWordSeg.wordSeg(productName);
		int lastIndex = -1;
		int termInProduceNameIndex = 0;
		for (String term : keywordSeg) {
			termInProduceNameIndex = productNameSeg.indexOf(term);
			if (termInProduceNameIndex != -1 && lastIndex > termInProduceNameIndex) {
				return false;
			}
		}
		return true;
	}
	
	// 如何根据doc获取相应的field的值
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore)
			throws IOException {
		// return super.customScore(doc, subQueryScore, valSrcScore);
		
		Document document = reader.document(doc);
		String productName = document.get("name");
		System.out.println(doc + ":" + productName);
		
		if (!isTermSortInProductName(productName)) {
			return subQueryScore / 1.1f;
		}
		return subQueryScore;
	}
}
