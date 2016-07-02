package org.zzx.ohlife.model;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class ListJournalsResult {
	
	private List<Map<String, AttributeValue>> items;
	
	public void setItems(List<Map<String, AttributeValue>> items) {
		this.items = items;
	}
	
	public List<Map<String, AttributeValue>> getItems() {
		return this.items;
	}

	public ListJournalsResult withItems(List<Map<String, AttributeValue>> items) {
		setItems(items);
		return this;
	}

}
