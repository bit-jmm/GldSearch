package org.bit.myscore;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermPositions;
import org.apache.lucene.search.function.CustomScoreProvider;

public class GldScoreProvider extends CustomScoreProvider {
//	ArrayList<String> keywordSeg = null;
//	ArrayList<String> productNameSeg = null;
//	IKWordSeg ikWordSeg = null;
	String keyword = null;
	public GldScoreProvider(IndexReader reader, String keyword) {
		super(reader);
		this.keyword = keyword;
//		this.keywordSeg = keywordSeg;
//		this.ikWordSeg = new IKWordSeg();
	}
	
	//得到term在doc中的位置
	public int termPositionAtProductName(String term, int doc) throws IOException {
		TermPositions termPositions = reader.termPositions(new Term("name", term));
		while (termPositions.next()) {
			if(termPositions.doc() == doc) {
				return termPositions.nextPosition();
			}
		}
		return -1;
	}
	//关键词term在productName中位置是否顺序排序
	public boolean isTermSortInProductName(String productName) {
		
//		productNameSeg = ikWordSeg.wordSeg(productName);
//		int lastIndex = -1;
//		int termInProduceNameIndex = 0;
//		for (String term : keywordSeg) {
//			termInProduceNameIndex = productNameSeg.indexOf(term);
//			if (termInProduceNameIndex != -1 && lastIndex > termInProduceNameIndex) {
//				return false;
//			}
//			if (termInProduceNameIndex != -1) {
//				lastIndex = termInProduceNameIndex;
//			}
//		}
		int index = productName.indexOf(keyword);
		if ( index != -1 && index < 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// 如何根据doc获取相应的field的值
	@Override
	public float customScore(int doc, float subQueryScore, float valSrcScore)
			throws IOException {
		// return super.customScore(doc, subQueryScore, valSrcScore);
		
		Document document = reader.document(doc);
		String productName = document.get("name");
		if (!isTermSortInProductName(productName)) {
			return subQueryScore / 1.1f;
		}
		return subQueryScore;
	}
}
