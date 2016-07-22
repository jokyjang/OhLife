package org.zzx.ohlife.function;

import java.io.IOException;

import org.zzx.ohlife.model.GetPageRequest;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.IOUtils;

public class GetPage implements RequestHandler<GetPageRequest, String> {

	public String handleRequest(GetPageRequest req, Context context) {
		String page;
		try {
			page = IOUtils.toString(getClass().getResourceAsStream("/resources/pages/ohlife-page.html"));
		} catch (IOException e) {
			e.printStackTrace();
			page = "exception";
		}
		return page;
	}
}
