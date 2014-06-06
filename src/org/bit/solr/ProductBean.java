package org.bit.solr;
import org.apache.solr.client.solrj.beans.Field;

public class ProductBean {

	@Field
	private String id;
	@Field
	private String company_id;
	@Field
	private String product_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public ProductBean(String id, String company_id, String product_name) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.product_name = product_name;
	}
	
}