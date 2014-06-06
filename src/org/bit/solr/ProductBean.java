package org.bit.solr;
import org.apache.solr.client.solrj.beans.Field;

public class ProductBean {

	@Field
	private String id;
	@Field
	private Long company_id;
	@Field
	private String product_name;
	@Field
	private Long _version_;
	@Field
	private String exactmatch_name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Long company_id) {
		this.company_id = company_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Long get_version_() {
		return _version_;
	}
	public void set_version_(Long _version_) {
		this._version_ = _version_;
	}
	public String getExactmatch_name() {
		return exactmatch_name;
	}
	public void setExactmatch_name(String exactmatch_name) {
		this.exactmatch_name = exactmatch_name;
	}
	public ProductBean(String id, Long company_id, String product_name,
			Long _version_, String exactmatch_name) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.product_name = product_name;
		this._version_ = _version_;
		this.exactmatch_name = exactmatch_name;
	}
	
	public ProductBean(Long company_id, String id, String exactmatch_name, String product_name,
			Long _version_) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.product_name = product_name;
		this._version_ = _version_;
		this.exactmatch_name = exactmatch_name;
	}
}