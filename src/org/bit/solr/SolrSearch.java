package org.bit.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;


public class SolrSearch {
	private static String tomcat_solr_url = "http://10.108.21.71:8080/solr/gld_search";
	private static HttpSolrServer solrServer = null;

	// 初始化solr服务
	public SolrSearch() {
		try {
			solrServer = new HttpSolrServer(tomcat_solr_url);
			solrServer.setConnectionTimeout(5000);
			solrServer.setDefaultMaxConnectionsPerHost(100);
			solrServer.setMaxTotalConnections(100);
			solrServer.ping();
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
	    query.setQuery("product_name:304 不锈钢 管 管件");
	    query.setStart(0);
	    query.setRows(10000);
	    QueryResponse rsp = solrServer.query(query);
	    SolrDocumentList resultDocs = rsp.getResults();
	    for (SolrDocument doc : resultDocs) {
			System.out.println(doc.getFieldValue("id") + "\t" + doc.getFieldValue("company_id") + "\t" + doc.getFieldValue("product_name"));
		}
	}
}