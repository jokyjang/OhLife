package org.zzx.ohlife.function;

import org.zzx.ohlife.model.ListJournalsRequest;
import org.zzx.ohlife.model.ListJournalsResult;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListJournals implements RequestHandler<ListJournalsRequest, ListJournalsResult> {
	
	private static final String TABLE_NAME = "ohlife-journal";

	public ListJournalsResult handleRequest(ListJournalsRequest req, Context context) {
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient();
		dynamoDB.setRegion(RegionUtils.getRegion("us-west-2"));
		ScanResult scanResult = dynamoDB.scan(new ScanRequest()
				.withConsistentRead(true)
				.withTableName(TABLE_NAME));
		
		return new ListJournalsResult()
			.withItems(scanResult.getItems());
	}
}
