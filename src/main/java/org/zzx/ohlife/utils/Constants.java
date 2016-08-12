package org.zzx.ohlife.utils;

public interface Constants {
	
	String DEFAULT_USER_NAME = "jokyjang";
	
	String TABLE_USER = "ohlife-user";
	String TABLE_JOURNAL = "ohlife-journal";
	String TABLE_TAG = "ohlife-tag";
	
	String ATTR_USER_USERNAME = "username";
	String ATTR_USER_PASSWORD = "password";
	String ATTR_USER_CREATEDATE = "create-date";
	
	String ATTR_JOURNAL_USERNAME = "username";
	String ATTR_JOURNAL_DATE = "date";
	String ATTR_JOURNAL_DAYOFWEEK = "day-of-week";
	String ATTR_JOURNAL_CONTENT = "content";
	String ATTR_JOURNAL_TAGS = "tags";
	
	String ATTR_TAG_USERNAME = "username";
	String ATTR_TAG_NAME = "name";
	
	/****************************************************************/
	
	Integer SCAN_LIMIT = 7;
}
