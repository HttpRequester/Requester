package bpadashi.ir.requester;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import java.util.List;

import ir.bpadashi.requester.IRequestHandler;
import ir.bpadashi.requester.Requester;
import ir.bpadashi.requester.model.ParentContext;
import ir.bpadashi.requester.model.ResponseString;
import ir.bpadashi.requester.statics.Method;
import ir.bpadashi.requester.statics.ResponseType;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void soapTest(View v) {

        final ProgressDialog progress = new ProgressDialog(this);

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

                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(true);
                        progress.show();

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {

                        ModelSoap aModel = (ModelSoap) responseObj;
                        setListAdapter(new SoapArrayAdapter(context.getActivity(), aModel.getEntity()));
                        progress.dismiss();
                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {

                        Log.i("Info", responseString.getResponse());

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {

                        if (!hasCache) {
                            ModelSoap aModel = (ModelSoap) responseObj;
                            setListAdapter(new SoapArrayAdapter(context.getActivity(), aModel.getEntity()));
                            progress.dismiss();
                        }



                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {

                        new AlertDialog.Builder(context.getActivity())
                                .setTitle("Error")
                                .setMessage(exception.getMessage()+" "+".load list from cache if any")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


                }).build();

        aRequester.executeAnSync();

    }

    public void webApiTest(View v) {

        final ProgressDialog progress = new ProgressDialog(this);

        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://whoyou-marketgen.rhcloud.com/restful/services/getinfo")

                .addParam("low", 0)
                .addParam("high", 10)

                .setModel(ModelJson.class)

                .setMethod(Method.GET)
                .setResponseType(ResponseType.JSON)

                .addRequestHandler(new IRequestHandler() {

                    @Override
                    public void onStart() {


                        progress.setTitle("Loading");
                        progress.setMessage("Wait while loading...");
                        progress.setCancelable(true);
                        progress.show();

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {

                        List<ModelJson> modelJsons = (List<ModelJson>) responseObj;
                        setListAdapter(new JsonArrayAdapter(context.getActivity(), modelJsons));
                        progress.dismiss();

                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {

                        Log.i("Info", responseString.getResponse());

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {


                    if(!hasCache){
                        List<ModelJson> modelJsons = (List<ModelJson>) responseObj;
                        setListAdapter(new JsonArrayAdapter(context.getActivity(), modelJsons));
                        progress.dismiss();
                    }



                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {

                        Log.e("Error", exception.getMessage());

                        new AlertDialog.Builder(context.getActivity())
                                .setTitle("Error")
                                .setMessage(exception.getMessage()+" "+".load list from cache if any")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }


                }).build();

        aRequester.executeAnSync();
    }


}
