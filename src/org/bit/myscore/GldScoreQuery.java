package org.bit.myscore;

import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.function.CustomScoreQuery;

@SuppressWarnings("serial")
public class GldScoreQuery extends CustomScoreQuery {
	String keyword = null;
	public GldScoreQuery(Query subQuery, String keyword) {  
        super(subQuery);
        this.keyword = keyword;
    }  

    @Override  
    protected GldScoreProvider getCustomScoreProvider(IndexReader reader)  
            throws IOException {  
//      return super.getCustomScoreProvider(reader);  
        return new GldScoreProvider(reader, keyword);  
    }  
}
