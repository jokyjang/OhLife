package org.zzx.ohlife.model;

import java.util.List;

public class BatchCreateMissingJournalsResult {
	private List<String> missingDates;

	public List<String> getMissingDates() {
		return missingDates;
	}

	public void setMissingDates(List<String> missingDates) {
		this.missingDates = missingDates;
	}
}
