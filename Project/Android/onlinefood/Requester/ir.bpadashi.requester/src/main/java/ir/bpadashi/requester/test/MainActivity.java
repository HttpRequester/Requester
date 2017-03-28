package ir.bpadashi.requester.test;

import android.app.Activity;
import android.os.Bundle;
import ir.bpadashi.requester.IRequestHandler;
import ir.bpadashi.requester.Method;
import ir.bpadashi.requester.Requester;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		 Requester aRequester = new Requester.RequesterBuilder(this)
		.setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
        .setWsdlMethod("GetCompanyInfo")
        .addParam("companyId", 20)
		.setModel(Company.class)
		.setMethod(Method.SOAP)
		.addRequestHandler(new IRequestHandler() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCache(Object context, Object model) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onResponse(Object context, StringBuilder response) {


				System.out.println(response);
				
			}

			@Override
			public void onSuccess(Object context, Object model, boolean hasCache) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(Object context, Exception exception, String exceptionFarsi) {
				// TODO Auto-generated method stub
				
			}



		}).build();
		 
		 aRequester.executeAnSync();
	}

}
