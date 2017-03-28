Requester
======

<<<<<<< HEAD
<<<<<<< HEAD
An HTTP & HTTP/2 client for Android . include SOAP WSDL,Web Api webservices And json mapper
=======
An HTTP & HTTP/2 client for Android . include SOAP,WebApi webservices And json mapper
>>>>>>> 5baf1c5b678b6101edf96800dd524a5424daae5f
=======
An HTTP & HTTP/2 client for Android . include SOAP,WebApi webservices And json mapper
>>>>>>> 0990c06836badec81df49a08442c2aa1e53d710f


Download
--------

Download [the latest aar][3] or grab via Maven:
```xml

<dependency>
  <groupId>ir.bpadashi.requester</groupId>
  <artifactId>requester</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy

<<<<<<< HEAD
<<<<<<< HEAD
compile 'ir.bpadashi.requester:requester:1.0.1'
=======
compile 'ir.bpadashi.requester:requester:1.0.3'
>>>>>>> 5baf1c5b678b6101edf96800dd524a5424daae5f
=======
compile 'ir.bpadashi.requester:requester:1.0.3'
>>>>>>> 0990c06836badec81df49a08442c2aa1e53d710f
```
Add to build.gradle of your app:
```groovy

repositories {
    maven { url  "https://dl.bintray.com/repobpadashi/maven/" }
    maven { url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases' }

}
```


SOAP webservice sample
```java


Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://onlinepakhsh.com/A_onlinepakhshService.asmx?WSDL")
                .setMethodName("GetProducts")
                .setNamespace("http://tempuri.org/")
                .setSoapAction("http://tempuri.org/GetProducts")

                .addParam("companyId", 20)

                .setReturnType(ReturnType.SOAP_OBJECT)
                .setMethod(Method.SOAP)
                
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

                        SoapObject soapObject = (SoapObject) responseObj;
                        System.out.println(soapObject.getPropertyCount());


                    }

                    @Override
                    public void onError(ParentContext context, Exception exception, String exceptionFarsi) {
                        // TODO Auto-generated method stub

                    }


                }).build();

        aRequester.executeAnSync();
```

Web Api webservice sample
```java
Requester aRequester = new Requester.RequesterBuilder(this)

                .setUrl("http://whoyou-marketgen.rhcloud.com/restful/services/reg")
                .addParam("email","email@cc.com")
                .addParam("password","123456")
                .setMethod(Method.GET)
                .setModel(Model.class)
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

<<<<<<< HEAD
        aRequester.executeAnSync();
<<<<<<< HEAD
        
        
=======

```

Create class Model for Json Mapping
```java

>>>>>>> 5baf1c5b678b6101edf96800dd524a5424daae5f
=======
        aRequester.executeAnSync();        
```
Create class for json mapping
```java
>>>>>>> 0990c06836badec81df49a08442c2aa1e53d710f
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
 [3]: https://dl.bintray.com/repobpadashi/maven/ir/bpadashi/requester/requester/1.0.1/requester-1.0.1.aar
 [4]: https://search.maven.org/remote_content?g=com.squareup.okhttp3&a=mockwebserver&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
