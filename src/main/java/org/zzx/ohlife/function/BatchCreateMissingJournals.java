package org.zzx.ohlife.function;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.zzx.ohlife.model.BatchCreateMissingJournalsRequest;
import org.zzx.ohlife.model.BatchCreateMissingJournalsResult;
import org.zzx.ohlife.utils.Constants;
import org.zzx.ohlife.utils.DynamoDB;
import org.zzx.ohlife.utils.Utils;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class BatchCreateMissingJournals implements
		RequestHandler<BatchCreateMissingJournalsRequest, BatchCreateMissingJournalsResult> {

	public BatchCreateMissingJournalsResult handleRequest(
			BatchCreateMissingJournalsRequest request, Context context) {

		AmazonDynamoDB dynamoDB = DynamoDB.getClient();
		BatchCreateMissingJournalsResult result = new BatchCreateMissingJournalsResult();

		List<String> missingDates = new ArrayList<String>();
		for (LocalDate date = Utils.parseLocalDate(request.getFromDate()); date
				.isBefore(Utils.parseLocalDate(request.getToDate())); date = date
				.plusDays(1)) {
			String dateString = date.toString();

			GetItemResult getItemResult = dynamoDB.getItem(new GetItemRequest()
					.withTableName(Constants.TABLE_JOURNAL).withKey(
							Utils.getTableJournalKey(dateString)));

			if (getItemResult.getItem() == null) {
				missingDates.add(dateString);
				dynamoDB.putItem(new PutItemRequest().withTableName(
						Constants.TABLE_JOURNAL).withItem(
						Utils.getTableJournalItem(dateString, null)));
			}
		}
		result.setMissingDates(missingDates);
		return result;
	}

}
