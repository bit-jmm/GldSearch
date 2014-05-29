package com.gld.lucene;

import java.io.Serializable;
 
public class ProductItem implements Serializable{
 
    private static final long serialVersionUID = 1L;
    private String id;
    private String company_id;
    private String base_material_id; 
    private String notes; 
    private String description; 
    private String created_at;
    private String updated_at;
    private String unit;  
    private String status;   
    private String name;   
    private String brand_id;    
    private String import_status_id;    
    private String product_status; 
    private String product_code ;
    private String base_material_type_id;
    private String sepecification;
    private String regulate_degree;
    private String batch;
    private String spec_detail;
    private String in_package;
    private String in_magazine;
    private String import_product_id;
    private String magazine_stage;
    private String into_review_time;
    private String data_type;
    private String square_material_id;
    private String sh_material_id;
    private String sh_type_code;
    private String sh_brand_material_code;
    private String review_type;
    private String section;
 
    public ProductItem() {
 
    }

	public ProductItem(String id, String company_id, String base_material_id,
			String notes, String description, String created_at,
			String updated_at, String unit, String status, String name,
			String brand_id, String import_status_id, String product_status,
			String product_code, String base_material_type_id,
			String sepecification, String regulate_degree, String batch,
			String spec_detail, String in_package, String in_magazine,
			String import_product_id, String magazine_stage,
			String into_review_time, String data_type,
			String square_material_id, String sh_material_id,
			String sh_type_code, String sh_brand_material_code,
			String review_type, String section) {
		super();
		this.id = id;
		this.company_id = company_id;
		this.base_material_id = base_material_id;
		this.notes = notes;
		this.description = description;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.unit = unit;
		this.status = status;
		this.name = name;
		this.brand_id = brand_id;
		this.import_status_id = import_status_id;
		this.product_status = product_status;
		this.product_code = product_code;
		this.base_material_type_id = base_material_type_id;
		this.sepecification = sepecification;
		this.regulate_degree = regulate_degree;
		this.batch = batch;
		this.spec_detail = spec_detail;
		this.in_package = in_package;
		this.in_magazine = in_magazine;
		this.import_product_id = import_product_id;
		this.magazine_stage = magazine_stage;
		this.into_review_time = into_review_time;
		this.data_type = data_type;
		this.square_material_id = square_material_id;
		this.sh_material_id = sh_material_id;
		this.sh_type_code = sh_type_code;
		this.sh_brand_material_code = sh_brand_material_code;
		this.review_type = review_type;
		this.section = section;
	}

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

	public String getBase_material_id() {
		return base_material_id;
	}

	public void setBase_material_id(String base_material_id) {
		this.base_material_id = base_material_id;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public String getImport_status_id() {
		return import_status_id;
	}

	public void setImport_status_id(String import_status_id) {
		this.import_status_id = import_status_id;
	}

	public String getProduct_status() {
		return product_status;
	}

	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getBase_material_type_id() {
		return base_material_type_id;
	}

	public void setBase_material_type_id(String base_material_type_id) {
		this.base_material_type_id = base_material_type_id;
	}

	public String getSepecification() {
		return sepecification;
	}

	public void setSepecification(String sepecification) {
		this.sepecification = sepecification;
	}

	public String getRegulate_degree() {
		return regulate_degree;
	}

	public void setRegulate_degree(String regulate_degree) {
		this.regulate_degree = regulate_degree;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getSpec_detail() {
		return spec_detail;
	}

	public void setSpec_detail(String spec_detail) {
		this.spec_detail = spec_detail;
	}

	public String getIn_package() {
		return in_package;
	}

	public void setIn_package(String in_package) {
		this.in_package = in_package;
	}

	public String getIn_magazine() {
		return in_magazine;
	}

	public void setIn_magazine(String in_magazine) {
		this.in_magazine = in_magazine;
	}

	public String getImport_product_id() {
		return import_product_id;
	}

	public void setImport_product_id(String import_product_id) {
		this.import_product_id = import_product_id;
	}

	public String getMagazine_stage() {
		return magazine_stage;
	}

	public void setMagazine_stage(String magazine_stage) {
		this.magazine_stage = magazine_stage;
	}

	public String getInto_review_time() {
		return into_review_time;
	}

	public void setInto_review_time(String into_review_time) {
		this.into_review_time = into_review_time;
	}

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public String getSquare_material_id() {
		return square_material_id;
	}

	public void setSquare_material_id(String square_material_id) {
		this.square_material_id = square_material_id;
	}

	public String getSh_material_id() {
		return sh_material_id;
	}

	public void setSh_material_id(String sh_material_id) {
		this.sh_material_id = sh_material_id;
	}

	public String getSh_type_code() {
		return sh_type_code;
	}

	public void setSh_type_code(String sh_type_code) {
		this.sh_type_code = sh_type_code;
	}

	public String getSh_brand_material_code() {
		return sh_brand_material_code;
	}

	public void setSh_brand_material_code(String sh_brand_material_code) {
		this.sh_brand_material_code = sh_brand_material_code;
	}

	public String getReview_type() {
		return review_type;
	}

	public void setReview_type(String review_type) {
		this.review_type = review_type;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}