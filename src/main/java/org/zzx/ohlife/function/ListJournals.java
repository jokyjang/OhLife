package org.zzx.ohlife.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zzx.ohlife.model.ListJournalsRequest;
import org.zzx.ohlife.model.ListJournalsResult;
import org.zzx.ohlife.utils.Constants;
import org.zzx.ohlife.utils.DynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class ListJournals implements RequestHandler<ListJournalsRequest, ListJournalsResult> {
	
	public ListJournalsResult handleRequest(ListJournalsRequest req, Context context) {
		AmazonDynamoDB dynamoDB = DynamoDB.getClient();
		
		Map<String, AttributeValue> exclusiveStartKey = null;
		if (req.getExclusiveStartDate() != null) {
			exclusiveStartKey = new HashMap<String, AttributeValue>();
			exclusiveStartKey.put(Constants.ATTR_JOURNAL_USERNAME, new AttributeValue().withS(Constants.DEFAULT_USER_NAME));
			exclusiveStartKey.put(Constants.ATTR_JOURNAL_DATE, new AttributeValue().withS(req.getExclusiveStartDate()));
		}
		
		@SuppressWarnings("serial")
		QueryResult queryResult = dynamoDB.query(new QueryRequest()
				.withConsistentRead(true)
				.withTableName(Constants.TABLE_JOURNAL)
				.withExclusiveStartKey(exclusiveStartKey)
				.withLimit(Constants.SCAN_LIMIT)
				.withScanIndexForward(req.getScanIndexForward())
				.withKeyConditionExpression("#p = :p AND #s < :s")
				.withExpressionAttributeNames(new HashMap<String, String>(){{
					put("#p", Constants.ATTR_JOURNAL_USERNAME);
					put("#s", Constants.ATTR_JOURNAL_DATE);
				}})
				.withExpressionAttributeValues(new HashMap<String, AttributeValue>(){{
					put(":p", new AttributeValue().withS(Constants.DEFAULT_USER_NAME));
					put(":s", new AttributeValue().withS(Constants.DEFAULT_FUTURE_DATE));
				}}));
		
		String firstEvaluatedDate = null;
		String lastEvaluatedDate = null;
		
		List<Map<String, AttributeValue>> items = queryResult.getItems();
		if (!items.isEmpty()) {
			firstEvaluatedDate = items.get(0).get(Constants.ATTR_JOURNAL_DATE).getS();
			lastEvaluatedDate = items.get(items.size()-1).get(Constants.ATTR_JOURNAL_DATE).getS();
			if (req.getScanIndexForward() != null && req.getScanIndexForward() == false) {
				String tempDate = firstEvaluatedDate;
				firstEvaluatedDate = lastEvaluatedDate;
				lastEvaluatedDate = tempDate;
			}
		}
		
		return new ListJournalsResult()
			.withItems(queryResult.getItems())
			.withFirstEvaluatedDate(firstEvaluatedDate)
			.withLastEvaluatedDate(lastEvaluatedDate);
	}
}
