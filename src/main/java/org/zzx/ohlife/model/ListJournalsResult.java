package org.zzx.ohlife.model;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class ListJournalsResult {
	
	private List<Map<String, AttributeValue>> items;
	
	private String firstEvaluatedDate;
	private String lastEvaluatedDate;
	
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
	
	public String getFirstEvaluatedDate() {
		return firstEvaluatedDate;
	}

	public void setFirstEvaluatedDate(String firstEvaluatedDate) {
		this.firstEvaluatedDate = firstEvaluatedDate;
	}
	
	public ListJournalsResult withFirstEvaluatedDate(String firstEvaluatedDate) {
		setFirstEvaluatedDate(firstEvaluatedDate);
		return this;
	}

	public String getLastEvaluatedDate() {
		return lastEvaluatedDate;
	}

	public void setLastEvaluatedDate(String lastEvaluatedDate) {
		this.lastEvaluatedDate = lastEvaluatedDate;
	}
	
	public ListJournalsResult withLastEvaluatedDate(String lastEvaluatedDate) {
		setLastEvaluatedDate(lastEvaluatedDate);
		return this;
	}

}
