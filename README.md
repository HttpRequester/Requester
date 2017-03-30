Requester
======

An HTTP & HTTP/2 client for Android . SOAP/WebApi webservices with JSON/XML mapper


Download
--------

Download [the latest aar][3] or grab via Maven:
```xml

<dependency>
  <groupId>ir.bpadashi.requester</groupId>
  <artifactId>requester</artifactId>
  <version>1.0.6</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
compile 'ir.bpadashi.requester:requester:1.0.6'
```
Add to build.gradle of your app:
```groovy

repositories {
    maven { url  "https://dl.bintray.com/httprequester/maven" }
    maven { url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases' }

}
```

Permission
```xml
    <uses-permission android:name="android.permission.INTERNET" /> 
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
```

SOAP webservice sample
```java
 
        Requester aRequester = new Requester.RequesterBuilder(this)

                //For soap webservices
                .setUrl("")
                .setMethodName("")
                .setNamespace("")
                .setSoapAction("")
                
                //For web api webserivces
                .setUrl("")

                //add content to send 
                // for SOAP webservices content add to PropertyInfo ,you can use neasted Soapobject and PropertyInfo and pass to         
                //addParam method
                //for GET/POST method content add to body content
                .addParam(String name, Object value)
                
                //create POJO class that implements Serializable for XML/JSON mapping
                .setModel(ModelSoap.class)
                 
                 //defind SOAP for webservices and GET or POST for webApi
                .setMethod(Method.SOAP)
                
                //define webservices reponse content type XML,JSON,TEXT
                .setResponseType(ResponseType.XML)


                .addRequestHandler(new IRequestHandler() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {

                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {

                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {

                    }


                }).build();

        aRequester.executeAnSync();
```

SOAP webservice sample
```java
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
```

Create Model class for XML mapping .
For more info about create XML mapping model  refer http://simple.sourceforge.net/
```java    
@Root(name = "soap:Envelope", strict = false)
public class ModelSoap implements Serializable {

    static final long serialVersionUID =8740213115075839093L;

    @ElementList(entry = "Entity", inline = true,required = false)
    @Path("Body/GetProductsResponse/GetProductsResult")
    private List<Entity> entities;

    public List<Entity> getEntity(){
        return  entities;
    }
}

@Root(strict=false)
class Entity implements Serializable{

    @Element
    private int id;

    @Element
    private String name;

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }
}
```

Web Api webservice sample
```java
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
```

Create Model class for json mapping
```java    
class ModelJson implements Serializable {

    public int id;
    public String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```


License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


 [1]: http://square.github.io/okhttp
 [2]: https://github.com/square/okhttp/wiki
 [3]: https://dl.bintray.com/httprequester/maven/ir/bpadashi/requester/requester/1.0.6/requester-1.0.4.aar
 [4]: https://search.maven.org/remote_content?g=com.squareup.okhttp3&a=mockwebserver&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
