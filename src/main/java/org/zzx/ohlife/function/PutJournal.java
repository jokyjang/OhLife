package org.zzx.ohlife.function;

import java.util.HashMap;

import org.zzx.ohlife.model.PutJournalRequest;
import org.zzx.ohlife.model.PutJournalResult;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PutJournal implements RequestHandler<PutJournalRequest, PutJournalResult>{
	private static final String TABLE_NAME = "ohlife-journal";

	public PutJournalResult handleRequest(final PutJournalRequest req, Context context) {
		AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient();
		dynamoDB.setRegion(RegionUtils.getRegion("us-west-2"));
		dynamoDB.putItem(new PutItemRequest()
				.withTableName(TABLE_NAME)
				.withItem(new HashMap<String, AttributeValue>(){
					private static final long serialVersionUID = 1L;
				{
					put("create-date", new AttributeValue().withN(req.getCreateDate()));
					put("content", new AttributeValue().withS(req.getContent()));
				}}));
		return new PutJournalResult();
	}

}
