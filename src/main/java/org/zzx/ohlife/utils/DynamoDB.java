package org.zzx.ohlife.utils;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;

public class DynamoDB {
	
	private static final String DYNAMODB_REGION = "us-west-2";
	private static AmazonDynamoDB client;
	
	public static AmazonDynamoDB getClient() {
		if (client == null) {
			client = new AmazonDynamoDBClient();
			client.setRegion(RegionUtils.getRegion(DYNAMODB_REGION));
		}
		return client;
	}
	
	public static void createTable(final String tableName, long readCapacity,
			long writeCapacity, final String hashKeyName,
			ScalarAttributeType hashKeyType) throws Exception {
		createTable(tableName, readCapacity, writeCapacity, hashKeyName,
				hashKeyType, null, null);
	}

	public static void createTable(final String tableName, long readCapacity,
			long writeCapacity, final String hashKeyName,
			ScalarAttributeType hashKeyType, final String rangeKeyName,
			ScalarAttributeType rangeKeyType) throws Exception {
		if (hashKeyName == null || hashKeyType == null) {
			throw new Exception("Hash key cannot be null!");
		}

		List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName(
				hashKeyName).withAttributeType(hashKeyType));
		if (rangeKeyName != null && rangeKeyType != null) {
			attributeDefinitions.add(new AttributeDefinition()
					.withAttributeName(rangeKeyName).withAttributeType(
							rangeKeyType));
		}

		List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		keySchema.add(new KeySchemaElement().withAttributeName(hashKeyName)
				.withKeyType(KeyType.HASH));
		if (rangeKeyName != null && rangeKeyType != null) {
			keySchema
					.add(new KeySchemaElement().withAttributeName(rangeKeyName)
							.withKeyType(KeyType.RANGE));
		}
		
		AmazonDynamoDB dynamo = getClient();

		dynamo.createTable(new CreateTableRequest()
				.withTableName(tableName)
				.withProvisionedThroughput(
						new ProvisionedThroughput(readCapacity, writeCapacity))
				.withAttributeDefinitions(attributeDefinitions)
				.withKeySchema(keySchema));
		TableUtils.waitUntilActive(dynamo, tableName);
	}

}
