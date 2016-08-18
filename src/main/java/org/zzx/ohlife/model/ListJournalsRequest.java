package org.zzx.ohlife.model;


public class ListJournalsRequest {
	
	private Boolean scanIndexForward;
	private String exclusiveStartDate;

	public Boolean getScanIndexForward() {
		return scanIndexForward;
	}

	public void setScanIndexForward(Boolean scanIndexForward) {
		this.scanIndexForward = scanIndexForward;
	}
	
	public ListJournalsRequest withScanIndexForward(Boolean scanIndexForward) {
		setScanIndexForward(scanIndexForward);
		return this;
	}

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
