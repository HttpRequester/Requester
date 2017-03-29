package bpadashi.ir.requester;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ir.bpadashi.requester.IRequestHandler;
import ir.bpadashi.requester.Requester;
import ir.bpadashi.requester.model.ParentContext;
import ir.bpadashi.requester.model.ResponseString;
import ir.bpadashi.requester.statics.Method;
import ir.bpadashi.requester.statics.ResponseType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soapTest();
        webApiTest();

    }

    public void soapTest(){
        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
                .setMethodName("GetProducts")
                .setNamespace("http://tempuri.org/")
                .setSoapAction("http://tempuri.org/GetProducts")

                .addParam("companyId", 20)

                .setModel(ModelSoap.class)

                .setMethod(Method.SOAP)
                .setResponseType(ResponseType.XML)


                .addRequestHandler(new IRequestHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {

                        ModelSoap aModel = (ModelSoap) responseObj;
                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString response) {

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {

                        ModelSoap aModel = (ModelSoap) responseObj;
                        System.out.println(aModel.getId());

                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {
                        // TODO Auto-generated method stub

                    }


                }).build();

        aRequester.executeAnSync();

    }

    public void webApiTest(){

        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://whoyou-marketgen.rhcloud.com/restful/services/reg")

                .addParam("email","email@cc.com")
                .addParam("password","123456")

                .setModel(ModelJson.class)

                .setMethod(Method.GET)
                .setResponseType(ResponseType.JSON)

                .addRequestHandler(new IRequestHandler() {

                    @Override
                    public void onStart() {
                        // Setup your preloader here!!!

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {

                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {

                        System.out.println(responseString.getResponse());

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {

                        ModelJson aModel=(ModelJson) responseObj;

                        System.out.println(aModel.getStatusCode());
                        System.out.println(aModel.getStatusDes());



                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {
                        // TODO Auto-generated method stub

                        System.out.println(exception.getMessage());

                    }


                }).build();

        aRequester.executeAnSync();
    }





}
