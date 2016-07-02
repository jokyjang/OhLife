package org.zzx.ohlife.model;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class PutJournalRequest {
	
	private Map<String, AttributeValue> item;
	
	public void setItem(Map<String, AttributeValue> item) {
		this.item = item;
	}

	public Map<String, AttributeValue> getItem() {
		return this.item;
	}
	
	public PutJournalRequest withItem(Map<String, AttributeValue> item) {
		setItem(item);
		return this;
	}

}
