package com.emanas.middleware.bmr;

public class ConfigList {
	String id;
	String key;
	String lastUpdatedTime;
	String list_name;
	String order;
	String status;
	String userName;
	String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	@Override
	public String toString() {
		return "ConfigList [id=" + id + ", key=" + key + ", lastUpdatedTime=" + lastUpdatedTime + ", list_name="
				+ list_name + ", order=" + order + ", status=" + status + ", userName=" + userName + ", value=" + value
				+ "]";
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public String getList_name() {
		return list_name;
	}

	public void setList_name(String list_name) {
		this.list_name = list_name;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
