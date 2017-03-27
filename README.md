Requester
======

An HTTP & HTTP/2 client for Android . include SOAP WSDL,GET,POST


Download
--------

Download [the latest aar][3] or grab via Maven:
```xml

<dependency>
  <groupId>ir.bpadashi</groupId>
  <artifactId>requester</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy

compile 'ir.bpadashi:requester:1.0.1'
```
Add to build.gradle of your app:
```groovy

repositories {
    maven { url  "http://bpadashi.bintray.com/maven" }
    maven { url 'https://oss.sonatype.org/content/repositories/ksoap2-android-releases' }

}
```


SOAP webservice sample
```java


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
        
      . }



    }).build();
     
     aRequester.executeAnSync();
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
 [3]: https://dl.bintray.com/bpadashi/maven/ir/bpadashi/requester/1.0.1/:requester-1.0.1.aar
 [4]: https://search.maven.org/remote_content?g=com.squareup.okhttp3&a=mockwebserver&v=LATEST
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
