package ir.bpadashi.requester;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;


import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import ir.bpadashi.requester.model.Params;
import ir.bpadashi.requester.model.ParentContext;
import ir.bpadashi.requester.model.ResponseString;
import ir.bpadashi.requester.statics.Method;
import ir.bpadashi.requester.statics.ResponseType;
import ir.bpadashi.requester.util.JsonMapper;
import ir.bpadashi.requester.util.NetworkUtil;
import ir.bpadashi.requester.util.Serialize;
import ir.bpadashi.requester.util.TextUtil;


public class RequesterRunnable implements Runnable {

    private Context context;
    private Fragment fragment;
    private boolean isFragment;
    private IRequestHandler aRequestHandler;
    private String url;
    private Class typeClass;
    private List<Params> paramList;
    public String SoapAction;
    public String MethodName;
    public String Namespace;
    private Method aMethod;
    private ResponseType responseType;


    public RequesterRunnable(Requester.RequesterBuilder requester) {

        this.context = requester.getContext();
        this.fragment = requester.getFragment();

        if (fragment != null)
            this.context = fragment.getActivity();

        this.aRequestHandler = requester.getIRequestHandler();
        this.url = requester.getUrl();
        this.typeClass = requester.getTypeClass();
        this.paramList = requester.getParamList();
        this.responseType = requester.getResponseType();
        this.SoapAction = requester.getSoapAction();
        this.MethodName = requester.getMethodName();
        this.Namespace = requester.getNamespace();
        this.aMethod = requester.getMethod();
    }


    public void onErrorUi(final IRequestHandler aRequestHandler, final Exception exception,
                          final String exceptionFarsi) {

        if (this.isFragment) {
            onErrorFragment(aRequestHandler, exception, exceptionFarsi);
            return;
        } else if (context == null)
            return;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onError(new ParentContext(context), exception, exceptionFarsi);
            }
        });
    }

    public void onResponseUi(final IRequestHandler aRequestHandler, final Object response) {

        if (this.isFragment) {
            onResponseFragment(aRequestHandler, response);
            return;
        } else if (context == null)
            return;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onResponse(new ParentContext(context), new ResponseString(response));
            }
        });
    }

    public void onCacheUi(final IRequestHandler aRequestHandler, final Object model) {

        if (this.isFragment) {
            onCacheFragment(aRequestHandler, model);
            return;
        } else if (context == null)
            return;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onCache(new ParentContext(context), model);
            }
        });
    }

    public void onSuccessUi(final IRequestHandler aRequestHandler, final Object model, final boolean hasCache) {

        if (this.isFragment) {
            onSuccessFragment(aRequestHandler, model, hasCache);
            return;
        } else if (context == null)
            return;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onSuccess(new ParentContext(context), model, hasCache);
            }
        });
    }

    public void onErrorFragment(final IRequestHandler aRequestHandler, final Exception exception,
                                final String exceptionFarsi) {
        if (fragment == null || fragment.getActivity() == null || fragment.getView() == null || !fragment.isAdded())
            return;
        ((Activity) fragment.getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onError(new ParentContext(fragment), exception, exceptionFarsi);
            }
        });
    }

    public void onResponseFragment(final IRequestHandler aRequestHandler, final Object response) {
        if (fragment == null || fragment.getActivity() == null || fragment.getView() == null || !fragment.isAdded())
            return;
        ((Activity) fragment.getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onResponse(new ParentContext(fragment), new ResponseString(response));
            }
        });
    }

    public void onCacheFragment(final IRequestHandler aRequestHandler, final Object model) {
        if (fragment == null || fragment.getActivity() == null || fragment.getView() == null || !fragment.isAdded())
            return;
        ((Activity) fragment.getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onCache(new ParentContext(fragment), model);
            }
        });
    }

    public void onSuccessFragment(final IRequestHandler aRequestHandler, final Object model, final boolean hasCache) {
        if (fragment == null || fragment.getActivity() == null || fragment.getView() == null || !fragment.isAdded())
            return;
        ((Activity) fragment.getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onSuccess(new ParentContext(fragment), model, hasCache);
            }
        });
    }

    @Override
    public void run() {

        if (context == null)
            return;

        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                aRequestHandler.onStart();
            }
        });

        boolean hasCache = true;
        try {
            Object obj = new Serialize().readFromFile(context, getRequestId(url));
            if (obj != null) {
                hasCache = true;
                onCacheUi(aRequestHandler, obj);
            }
        } catch (NoSuchAlgorithmException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_CACHE_DATA);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_CACHE_DATA);
            e.printStackTrace();
        }

		/*
         * try { Thread.sleep(5000); } catch (InterruptedException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */

        NetworkUtil aNetworkUtil = new NetworkUtil(context);
        if (!aNetworkUtil.getConnectivityStatus()) {
            onErrorUi(aRequestHandler, new Exception("no internet connection"), TextUtil.NO_INTERNET);
            System.out.println(TextUtil.NO_INTERNET);
            return;

        }

        Object response = null;
        try {
            switch (aMethod) {
                case GET:
                    response = getRequestGet(url);
                    break;
                case POST:
                    response = getRequestPost(url);
                    break;
                case SOAP:
                    response = getRequestSoap(url);
                    break;
                default:
                    response = getRequestGet(url);
            }
        } catch (IOException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_GET_DATA_FROM_SERVER);
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_GET_DATA_FROM_SERVER);
            e.printStackTrace();
        }
        onResponseUi(aRequestHandler, response);

        Object obj = null;

        switch (responseType){

            case TEXT:
                obj = response;
                onSuccessUi(aRequestHandler, response, hasCache);
                break;

            case JSON:
                JsonMapper jsonMapper = new JsonMapper();
                try {
                    obj = jsonMapper.map(new StringBuilder(response.toString()), typeClass);
                    onSuccessUi(aRequestHandler, obj, hasCache);
                } catch (JSONException e) {
                    onErrorUi(aRequestHandler, e, TextUtil.INVALID_SERVER_DATA);
                    e.printStackTrace();
                } catch (Exception e) {
                    onErrorUi(aRequestHandler, e, TextUtil.INVALID_SERVER_DATA);
                    e.printStackTrace();
                }
                break;

            case XML:

                Serializer serializer = new Persister();
                try {
                    obj = serializer.read(typeClass, response.toString());
                    onSuccessUi(aRequestHandler, obj, hasCache);
                } catch (Exception e) {
                    onErrorUi(aRequestHandler, e, TextUtil.INVALID_SERVER_DATA);
                    e.printStackTrace();
                }

                break;

        }

        try {
            new Serialize().saveToFile(context, obj, getRequestId(url));
        } catch (NoSuchAlgorithmException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_CACHE_DATA);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            onErrorUi(aRequestHandler, e, TextUtil.ERROR_ON_CACHE_DATA);
            e.printStackTrace();
        }




    }

    private StringBuilder getRequestGet(String urlString) throws IOException {

        StringBuilder postData = new StringBuilder();

        if (paramList != null)
            for (Params param : paramList) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.key, "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.value), "UTF-8"));
            }

        URL url = new URL(urlString + "?" + postData);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        if (conn.getResponseCode() != 200) {
            onErrorUi(aRequestHandler, new Exception(TextUtil.ERROR_ON_RESPONSE_CODE), TextUtil.ERROR_ON_GET_DATA_FROM_SERVER);
            return response;
        }

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }

    private StringBuilder getRequestPost(String urlString) throws IOException {

        URL url = new URL(urlString);

        StringBuilder postData = new StringBuilder();

        if (paramList != null)
            for (Params param : paramList) {
                if (postData.length() != 0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.key, "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.value), "UTF-8"));
            }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);

        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);

        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder response = new StringBuilder();

        if (conn.getResponseCode() != 200) {
            onErrorUi(aRequestHandler, new Exception(TextUtil.ERROR_ON_RESPONSE_CODE), TextUtil.ERROR_ON_GET_DATA_FROM_SERVER);
            return response;
        }

        for (int c; (c = in.read()) >= 0; )
            response.append((char) c);
        return response;

    }

    private Object getRequestSoap(String urlString) throws IOException, XmlPullParserException {

        String SOAP_ACTION = SoapAction;
        String METHOD_NAME = MethodName;
        String NAMESPACE = Namespace;
        String URL = urlString;

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        if (paramList != null)
            for (Params param : paramList) {
                request.addProperty(param.key, param.value);
            }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        ht.debug=true;
        ht.call(SOAP_ACTION, envelope);


        Object response = ht.responseDump;

        return response;


    }

    private String getRequestId(String url) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        StringBuilder aStringBuilder = new StringBuilder();

        aStringBuilder.append(url);

        if (paramList != null)
            for (Params param : paramList) {
                aStringBuilder.append(param.key);
                aStringBuilder.append(param.value.toString());
            }

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(aStringBuilder.toString().getBytes("UTF-8"));
        return new BigInteger(1, md.digest()).toString(16);
    }



}
