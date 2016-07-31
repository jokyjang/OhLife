package org.zzx.ohlife.adhoc;

import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DATE;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DAYOFWEEK;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_USERNAME;
import static org.zzx.ohlife.utils.Constants.ATTR_TAG_NAME;
import static org.zzx.ohlife.utils.Constants.ATTR_TAG_USERNAME;
import static org.zzx.ohlife.utils.Constants.DEFAULT_USER_NAME;
import static org.zzx.ohlife.utils.Constants.TABLE_JOURNAL;
import static org.zzx.ohlife.utils.Constants.TABLE_TAG;
import static org.zzx.ohlife.utils.DynamoDB.createTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import org.zzx.ohlife.prologue.DynamoTableCreation;
import org.zzx.ohlife.utils.DynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class OhlifeJournalTableUpdate {
	
	private static final AmazonDynamoDB dynamo = DynamoDB.getClient();
	
	private static final String[] WEEK = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
	
	public static void updateTableTag() throws Exception {
		final String originalTableTag = "ohlife-tags";
		final String tempTableTag = TABLE_TAG + "-1469949431516";
		
//		System.out.println("Creating temp tag table...");
//		createTable(tempTableTag, 10L, 10L, ATTR_TAG_USERNAME,
//				ScalarAttributeType.S, ATTR_TAG_NAME, ScalarAttributeType.S);
//		
//		System.out.println("Scanning " + originalTableTag + " table items...");
//		ScanResult result = dynamo.scan(new ScanRequest()
//				.withTableName(originalTableTag));
//		
//		System.out.println("Migrating data...");
//		for (Map<String, AttributeValue> item : result.getItems()) {
//			item.put(ATTR_TAG_USERNAME, new AttributeValue().withS(DEFAULT_USER_NAME));
//			dynamo.putItem(new PutItemRequest().withTableName(tempTableTag)
//					.withItem(item));
//		}
//		System.out.println("Done!");
		
//		com.amazonaws.services.dynamodbv2.document.DynamoDB client = new com.amazonaws.services.dynamodbv2.document.DynamoDB(dynamo);
//		Table table = client.getTable(originalTableTag);
//		table.delete();
//		table.waitForDelete();
		
//		DynamoTableCreation.createOhlifeTag();
		for (Map<String, AttributeValue> item : dynamo.scan(new ScanRequest().withTableName(tempTableTag)).getItems()) {
			dynamo.putItem(new PutItemRequest().withTableName(TABLE_TAG).withItem(item));
		}
		System.out.println("Done!");
	}
	
	public static void updateTableJournal() throws Exception {
		final String originalTableJournal = "ohlife-journal";
		final String tempTableJournal = TABLE_JOURNAL + "-1469949666532";
		
//		System.out.println("Creating temp table...");
//		createTable(tempTableJournal, 10L, 10L, ATTR_JOURNAL_USERNAME,
//				ScalarAttributeType.S, ATTR_JOURNAL_DATE,
//				ScalarAttributeType.S);
//		
//		System.out.println("Scanning original table...");
//		ScanResult result = dynamo.scan(new ScanRequest().withTableName(originalTableJournal));
//		
//		System.out.println("Migrating data ...");
//		for (Map<String, AttributeValue> item : result.getItems()) {
//			item.put(ATTR_JOURNAL_USERNAME, new AttributeValue().withS(DEFAULT_USER_NAME));
//			int date = Integer.parseInt(item.get("create-date").getN());
//			item.put(ATTR_JOURNAL_DATE, new AttributeValue().withS(parseIntDate(date)));
//			item.put(ATTR_JOURNAL_DAYOFWEEK, new AttributeValue().withS(parseIntDateToDow(date)));
//			item.remove("create-date");
//			dynamo.putItem(new PutItemRequest().withTableName(tempTableJournal)
//					.withItem(item));
//		}
//		System.out.println("Done!");
//		
//		com.amazonaws.services.dynamodbv2.document.DynamoDB client = new com.amazonaws.services.dynamodbv2.document.DynamoDB(dynamo);
//		Table table = client.getTable(originalTableJournal);
//		table.delete();
//		table.waitForDelete();
		
		DynamoTableCreation.createOhlifeJournal();
		for (Map<String, AttributeValue> item : dynamo.scan(new ScanRequest().withTableName(tempTableJournal)).getItems()) {
			dynamo.putItem(new PutItemRequest().withTableName(TABLE_JOURNAL).withItem(item));
		}
		System.out.println("Done!");
	}
	
	private static String parseIntDateToDow(int date) {
		Calendar calendar = parseIntDateToCalendar(date);
		
		return WEEK[calendar.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	private static String parseIntDate(int date) throws ParseException {
		Calendar calendar = parseIntDateToCalendar(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(calendar.getTime());
		
		return strDate;
	}
	
	private static Calendar parseIntDateToCalendar(int date) {
		int day = date % 100;
		int month = (date % 10000) / 100;
		int year = date / 10000;
		
		return new GregorianCalendar(year, month-1, day);
	}
	
	public static void main(String[] args) throws Exception {
		//updateTableTag();
		updateTableJournal();
//		System.out.println(parseIntDate(20160101));
//		System.out.println(parseIntDate(20160813));
//		System.out.println(parseIntDate(20161201));
	}
}
