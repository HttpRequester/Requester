package bpadashi.ir.requester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.bpadashi.requester.IRequestHandler;
import ir.bpadashi.requester.Method;
import ir.bpadashi.requester.Requester;
import ir.bpadashi.requester.test.Company;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
