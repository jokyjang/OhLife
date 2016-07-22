package org.zzx.ohlife.function;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.zzx.ohlife.model.GetPageRequest;

public class PutJournalPageTest {
	
	private static final GetPage putJournalPage = new GetPage();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String page = putJournalPage.handleRequest(new GetPageRequest(), null);
		System.out.println(page);
	}

}
