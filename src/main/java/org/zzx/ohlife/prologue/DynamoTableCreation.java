package org.zzx.ohlife.prologue;

import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_DATE;
import static org.zzx.ohlife.utils.Constants.ATTR_JOURNAL_USERNAME;
import static org.zzx.ohlife.utils.Constants.ATTR_TAG_NAME;
import static org.zzx.ohlife.utils.Constants.ATTR_TAG_USERNAME;
import static org.zzx.ohlife.utils.Constants.ATTR_USER_USERNAME;
import static org.zzx.ohlife.utils.Constants.TABLE_JOURNAL;
import static org.zzx.ohlife.utils.Constants.TABLE_TAG;
import static org.zzx.ohlife.utils.Constants.TABLE_USER;
import static org.zzx.ohlife.utils.DynamoDB.createTable;

import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class DynamoTableCreation {

	public static void createOhlifeUser() throws Exception {
		createTable(TABLE_USER, 10L, 10L, ATTR_USER_USERNAME,
				ScalarAttributeType.S);
	}

	public static void createOhlifeJournal() throws Exception {
		createTable(TABLE_JOURNAL, 10L, 10L, ATTR_JOURNAL_USERNAME,
				ScalarAttributeType.S, ATTR_JOURNAL_DATE,
				ScalarAttributeType.S);
	}

	public static void createOhlifeTag() throws Exception {
		createTable(TABLE_TAG, 10L, 10L, ATTR_TAG_USERNAME,
				ScalarAttributeType.S, ATTR_TAG_NAME, ScalarAttributeType.S);
	}

}
