package org.zzx.ohlife.function;

import org.zzx.ohlife.model.ListJournalsRequest;
import org.zzx.ohlife.model.ListJournalsResult;
import org.zzx.ohlife.utils.Constants;
import org.zzx.ohlife.utils.DynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListJournals implements RequestHandler<ListJournalsRequest, ListJournalsResult> {
	
	public ListJournalsResult handleRequest(ListJournalsRequest req, Context context) {
		AmazonDynamoDB dynamoDB = DynamoDB.getClient();
		
		ScanResult scanResult = dynamoDB.scan(new ScanRequest()
				.withConsistentRead(true)
				.withTableName(Constants.TABLE_JOURNAL));	
		
		return new ListJournalsResult()
			.withItems(scanResult.getItems());
	}
}
