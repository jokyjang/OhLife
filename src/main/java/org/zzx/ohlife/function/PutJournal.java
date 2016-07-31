package org.zzx.ohlife.function;

import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_CONTENT;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DATE;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DAYOFWEEK;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_USERNAME;
import static org.zzx.ohlife.utils.Constants.DEFAULT_USER_NAME;
import static org.zzx.ohlife.utils.Constants.TABLE_JOURNAL;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.zzx.ohlife.model.PutJournalRequest;
import org.zzx.ohlife.model.PutJournalResult;
import org.zzx.ohlife.utils.DynamoDB;
import org.zzx.ohlife.utils.Utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PutJournal implements
		RequestHandler<PutJournalRequest, PutJournalResult> {

	public PutJournalResult handleRequest(final PutJournalRequest req,
			Context context) {
		AmazonDynamoDB dynamoDB = DynamoDB.getClient();
		String dayOfWeek = "";
		try {
			dayOfWeek = Utils.parseDateToDayOfWeek(req.getDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Map<String, AttributeValue> items = new HashMap<String, AttributeValue>();
		items.put(ATTR_JOURNAL_USERNAME, new AttributeValue().withS(DEFAULT_USER_NAME));
		items.put(ATTR_JOURNAL_DATE, new AttributeValue().withS(req.getDate()));
		items.put(ATTR_JOURNAL_CONTENT,
				new AttributeValue().withS(req.getContent()));
		items.put(ATTR_JOURNAL_DAYOFWEEK, new AttributeValue().withS(dayOfWeek));
		dynamoDB.putItem(new PutItemRequest().withTableName(TABLE_JOURNAL)
				.withItem(items));
		return new PutJournalResult();
	}

}
