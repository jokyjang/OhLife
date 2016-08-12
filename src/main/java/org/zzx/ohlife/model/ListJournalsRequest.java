package org.zzx.ohlife.model;


public class ListJournalsRequest {
	
	private String exclusiveStartDate;

	public String getExclusiveStartDate() {
		return exclusiveStartDate;
	}

	public void setExclusiveStartDate(String exclusiveStartDate) {
		this.exclusiveStartDate = exclusiveStartDate;
	}
	
	public ListJournalsRequest withExclusiveStartDate(String exclusiveStartDate) {
		setExclusiveStartDate(exclusiveStartDate);
		return this;
	}
}
