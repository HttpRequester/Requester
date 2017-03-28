package bpadashi.ir.requester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.ksoap2.serialization.SoapObject;

import ir.bpadashi.requester.IRequestHandler;
import ir.bpadashi.requester.Method;
import ir.bpadashi.requester.Requester;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
                .setMethodName("GetProducts")
                .setNamespace("http://tempuri.org/")
                .setSoapAction("http://tempuri.org/GetProducts")

                .dontCache()

                .addParam("companyId", 20)
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
                    public void onResponse(Object context, Object response) {


                    }

                    @Override
                    public void onSuccess(Object context, Object model, boolean hasCache) {

                        SoapObject soapObject=(SoapObject)model;
                        System.out.println(soapObject.getPropertyCount());


                    }

                    @Override
                    public void onError(Object context, Exception exception, String exceptionFarsi) {
                        // TODO Auto-generated method stub

                    }


                }).build();

        aRequester.executeAnSync();
    }
}
