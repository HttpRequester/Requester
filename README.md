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
  <version>1.1.9</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
compile 'ir.bpadashi.requester:requester:1.1.9'
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
How To Implement
```java
 
        Requester aRequester = new Requester.RequesterBuilder(this)

                //for soap webservices.
                .setUrl("")
                .setMethodName("")
                .setNamespace("")
                .setSoapAction("")
                
                //for web api webserivces.
                .setUrl("")

                //add content to send addBodyParams(key,value)
                //for SOAP webservices content add to PropertyInfo .
                //you can use neasted Soapobject and PropertyInfo and pass to addBodyParam(soapobject) instead addBodyParams(key,value) 
                //for GET/POST method content add to body content.
                //if your content is bytes use addBodyParam(byte[]).
                .addBodyParams(name,value)
                
                //create POJO class that implements Serializable for XML/JSON mapping.
                //create create XML mapping class implements Serializable.  refer http://simple.sourceforge.net/
                .addMapClass(Model.class)
                 
                 //defind SOAP for webservices and GET or POST for webApi.
                .setRequestMethod(RequestMethod)
                
                //define webservices reponse content type XML,JSON,TEXT,BYTE.
                .setResponseContentType(ContentType)


                .addRequestHandler(new RequestHandler() {

                    @Override
                    public void onStart() {
                    
                    //call on start of process to get response , good place to put Preloader .

                    }

                    @Override
                    public void onCache(ParentContext context, Object responseObj) {
                    
                    //return object of class you pass in setMode() from last response result , that store in database
                    //good for using when no internet connection available, to get last webserivce response result.

                    }

                    @Override
                    public void onResponse(ParentContext context, ResponseString responseString) {
                    
                    //call when webservice response , and you can see result in string.

                    }

                    @Override
                    public void onSuccess(ParentContext context, Object responseObj, boolean hasCache) {
                    
                    //return object of class you pass in setMode() , you just need to cast responseObj to class you define in  setMode()
                    //and then use field and method 
                    //Example : for Model.class  Model model=(Model)responseObj;

                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionEn, String exceptionFa) {
                    
                    //call when exception happen, use exceptionEn in yout error dialog

                    }


                }).build();

        //run this task in parallel of other task in deferent Thread 
        aRequester.executeAnSync();
        
        //run this task in queue of others task run by executeSync , and FIFO (first in first out)
        aRequester.executeSync();
```

SOAP webservice sample
```java
        final ProgressDialog progress = new ProgressDialog(this);

        Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
                .setMethodName("GetProducts1")
                .setNamespace("http://tempuri.org/")
                .setSoapAction("http://tempuri.org/GetProducts1")

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
```

Create class for XML mapping that implements Serializable .
For more info about create XML mapping class  refer http://simple.sourceforge.net/
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

                .addBodyParams("low", 0)
                .addBodyParams("high", 10)

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
```

Create POJO class that implements Serializable for json mapping
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
Note:
----
- All mapping class must implements Serializable.
- If your param is bytes use addBodyParam(byte[]) . you also use Base64 to convert to String and put in addBodyParams(key,base64)
- For more info about create XML mapping class  refer http://simple.sourceforge.net/
- For SOAP and nested SoapObject use addBodyParam(soapObject) <br />
  example:
```java    
  SoapObject users = new SoapObject(NAMESPACE, "users");
  SoapObject john = new SoapObject(NAMESPACE, "user");
    john.addProperty("name", "john");
    john.addProperty("age", 12);
  SoapObject marie = new SoapObject(NAMESPACE, "user");
    marie.addProperty("name", "marie");
    marie.addProperty("age", 27);
  users.addSoapObject(john);
  users.addSoapObject(marie);
  .addBodyParam(users)
```
- For set request header or cookie use addHeaderParam(key,value).
- For set request timeout for both Soap and webapi use setTimeout(int millisecond) .


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

 [3]: https://dl.bintray.com/httprequester/maven/ir/bpadashi/requester/requester/1.1.9/requester-1.1.9.aar
