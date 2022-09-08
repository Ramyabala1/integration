package com.emanas.middleware.models;

public class Binary {
	public String resourceType;
    public String id;
    public String contentType;
    public String data;
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "Binary [resourceType=" + resourceType + ", id=" + id + ", contentType=" + contentType + ", data=" + data
				+ "]";
	}
    
    
	

}
