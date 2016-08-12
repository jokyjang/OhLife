package org.zzx.ohlife.function;

import java.util.HashMap;
import java.util.Map;

import org.zzx.ohlife.model.ListJournalsRequest;
import org.zzx.ohlife.model.ListJournalsResult;
import org.zzx.ohlife.utils.Constants;
import org.zzx.ohlife.utils.DynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
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
		
		ScanResult scanResult = dynamoDB.scan(new ScanRequest()
				.withConsistentRead(true)
				.withTableName(Constants.TABLE_JOURNAL)
				.withExclusiveStartKey(exclusiveStartKey)
				.withLimit(Constants.SCAN_LIMIT));
		
		String lastEvaluatedDate = scanResult.getLastEvaluatedKey().isEmpty() ? 
				"" : scanResult.getLastEvaluatedKey().get(Constants.ATTR_JOURNAL_DATE).getS();
		
		return new ListJournalsResult()
			.withItems(scanResult.getItems())
			.withLastEvaluatedKey(lastEvaluatedDate);
	}
}
