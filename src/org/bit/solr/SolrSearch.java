package org.bit.solr;

import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class SolrSearch {
	private static String tomcat_solr_url = "http://localhost:8080/solr/gld_search_final";
	private static HttpSolrServer solrServer = null;

	// 初始化solr服务
	public SolrSearch() {
		try {
			solrServer = new HttpSolrServer(tomcat_solr_url);
			solrServer.setConnectionTimeout(5000);
			solrServer.setDefaultMaxConnectionsPerHost(100);
			solrServer.setMaxTotalConnections(100);
			// solrServer.ping();
		} catch (Exception e) {
			System.out.println("请检查tomcat服务器或端口是否开启!");
			e.printStackTrace();
		}
	}

	// 删除所有索引
	public void DeleteAllIndex() {
		try {
			solrServer.deleteByQuery("*:*");
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws SolrServerException {
		SolrSearch solrSearch = new SolrSearch();
		SolrQuery query = new SolrQuery();
		query.setQuery("304不锈钢管管件");
		query.setStart(0);
		query.setRows(20);
//		query.set("fl", "*,score");
//		query.set("debug", true);
//		query.set("defType", "dismax");
//		query.set("qf", "exactmatch_name^1 product_name^0.5");
//		query.set(
//				"bf",
//				"product(if(exists(picture_id),1.3,1),if(sub(max(ms(NOW,product_updated_at),2.592e9),2.592e9),if(sub(max(ms(NOW,product_updated_at),7.776e9),7.776e9),1,1.3),1.5))");
//		query.addSort("score", SolrQuery.ORDER.desc);
		// query.addSort("product_updated_at", SolrQuery.ORDER.desc);
		QueryResponse rsp = solrServer.query(query);

		SolrDocumentList resultDocs = rsp.getResults();
		Map<String, String> explainMap = rsp.getExplainMap();
		Map<String, Object> debugMap = rsp.getDebugMap();
		System.err.println(debugMap.get("parsedquery"));
		// System.out.println(resultDocs.getMaxScore());
		for (SolrDocument doc : resultDocs) {

			System.out.println(doc.getFieldValue("id") + "\t"
					+ doc.getFieldValue("score") + "\t"
					+ doc.getFieldValue("product_name") + "\t"
					+ doc.getFieldValue("brand_name") + "\t"
					+ doc.getFieldValue("picture_id") + "\t"
					+ doc.getFieldValue("product_updated_at"));

			System.err.println(explainMap.get(doc.getFieldValue("id").toString()));
		}
	}
}