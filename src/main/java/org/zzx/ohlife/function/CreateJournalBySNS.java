package org.zzx.ohlife.function;

import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DATE;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DAYOFWEEK;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_USERNAME;
import static org.zzx.ohlife.utils.Constants.DEFAULT_USER_NAME;
import static org.zzx.ohlife.utils.Constants.TABLE_JOURNAL;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.zzx.ohlife.model.CreateJournalBySNSResult;
import org.zzx.ohlife.utils.DynamoDB;
import org.zzx.ohlife.utils.Utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

public class CreateJournalBySNS implements RequestHandler<SNSEvent, CreateJournalBySNSResult> {

	public CreateJournalBySNSResult handleRequest(SNSEvent event, Context context) {
		AmazonDynamoDB dynamo = DynamoDB.getClient();
		
		String date = Utils.parseSeattleDateString(
				event.getRecords().get(0).getSNS().getTimestamp());
		String dayOfWeek = "";
		try {
			dayOfWeek = Utils.parseDayOfWeek(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Map<String, AttributeValue> items = new HashMap<String, AttributeValue>();
		items.put(ATTR_JOURNAL_USERNAME, new AttributeValue().withS(DEFAULT_USER_NAME));
		items.put(ATTR_JOURNAL_DATE, new AttributeValue().withS(date));
		items.put(ATTR_JOURNAL_DAYOFWEEK, new AttributeValue().withS(dayOfWeek));
		dynamo.putItem(new PutItemRequest()
				.withTableName(TABLE_JOURNAL)
				.withItem(items));
		return new CreateJournalBySNSResult();
	}

}
