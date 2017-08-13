package ir.bpadashi.requester.test;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import java.util.List;

import ir.bpadashi.requester.RequestHandler;
import ir.bpadashi.requester.Requester;
import ir.bpadashi.requester.model.ParentContext;
import ir.bpadashi.requester.model.ResponseString;
import ir.bpadashi.requester.statics.ContentType;
import ir.bpadashi.requester.statics.RequestMethod;

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

                .addBodyParams("companyId", 20)

                .addMapClass(ModelSoap.class)

                .setRequestMethod(RequestMethod.SOAP)
                .setResponseContentType(ContentType.XML)


                .addRequestHandler(new RequestHandler() {

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
                    public void onError(ParentContext context, Exception exception, String exceptionEn, String exceptionFa) {

                        Log.e("Error",exception.toString());

                        progress.dismiss();


                        new AlertDialog.Builder(context.getActivity())
                                .setTitle("Error")
                                .setMessage(exceptionEn)
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

                .setUrl("http://toplinks.me/api/ApiServices/GetStateOrCity/1")

                .addMapClass(ModelJson.class)

                .setRequestMethod(RequestMethod.GET)
                .setResponseContentType(ContentType.JSON)

                .addRequestHandler(new RequestHandler() {

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
                        if (modelJsons != null)
                        setListAdapter(new JsonArrayAdapter(context.getActivity(), modelJsons));
                        progress.dismiss();

                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {

                        Log.i("Info", responseString.getResponse());

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {


                        if (!hasCache) {
                            List<ModelJson> modelJsons = (List<ModelJson>) responseObj;
                            setListAdapter(new JsonArrayAdapter(context.getActivity(), modelJsons));
                            progress.dismiss();
                        }


                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionEn, String exceptionFa) {

                        Log.e("Error",exception.toString());

                        progress.dismiss();

                        new AlertDialog.Builder(context.getActivity())
                                .setTitle("Error")
                                .setMessage(exceptionEn)
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
