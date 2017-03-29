package bpadashi.ir.requester;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

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


       /* Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
                .setMethodName("GetProducts")
                .setNamespace("http://tempuri.org/")
                .setSoapAction("http://tempuri.org/GetProducts")

                .addParam("companyId", 20)

                .setModel(Model.class)

                .setMethod(Method.SOAP)
                .setReturnType(ResponseType.XML)


                .addRequestHandler(new IRequestHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {
                        // TODO Auto-generated method stub



                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString response) {


                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {


                        Model aModel = (Model) responseObj;
                        System.out.println(aModel.getId());

                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {
                        // TODO Auto-generated method stub

                    }


                }).build();

        aRequester.executeAnSync();*/

        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://whoyou-marketgen.rhcloud.com/restful/services/reg")

                .addParam("email","email@cc.com")
                .addParam("password","123456")

                .setModel(Model.class)

                .setMethod(Method.GET)
                .setReturnType(ResponseType.JSON)

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

                        Model aModel=(Model)responseObj;

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

    class Model implements Serializable {

        public int statusCode;
        public String statusDes;

        public int getStatusCode() {
            return statusCode;
        }

        public String getStatusDes() {
            return statusDes;
        }

    }



}
