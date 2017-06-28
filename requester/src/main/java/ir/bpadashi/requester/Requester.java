package ir.bpadashi.requester;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.support.v4.app.Fragment;

import ir.bpadashi.requester.model.Param;
import ir.bpadashi.requester.statics.RequestMethod;
import ir.bpadashi.requester.statics.ContentType;
import ir.bpadashi.requester.threadpool.ConnectionThreadPool;
import ir.bpadashi.requester.threadpool.ConnectionThreadSingle;

public class Requester {

    Queue<RequesterRunnable> queue = new ConcurrentLinkedQueue<RequesterRunnable>();

    RequesterBuilder builder;

    public Requester(RequesterBuilder builder) {

        this.builder = builder;

    }

    public void executeSync() {

        queue.add(new RequesterRunnable(builder));

        while (queue.size() > 0) {
            ConnectionThreadSingle.getInstance().AddTask(queue.poll());
        }

    }

    public void executeAnSync() {

        queue.add(new RequesterRunnable(builder));

        while (queue.size() > 0) {
            ConnectionThreadPool.getInstance().AddTask(queue.poll());
        }

    }

    public static class RequesterBuilder {

        Queue<RequesterRunnable> queue = new ConcurrentLinkedQueue<RequesterRunnable>();

        private Context context;
        private Fragment fragment;
        private boolean isFragment;

        private String url;

        private String SoapAction;
        private String MethodName;
        private String Namespace;

        private Class typeClass;
        private RequestHandler aRequestHandler;

        private List<Param> bodyParams;
        private List<Param> headerParams;
        private Object bodyParam;

        private int timeout;

        private RequestMethod aRequestMethod;
        private ContentType contentType;


        //Getter

        public Context getContext() {
            return context;
        }

        public Fragment getFragment() {
            return fragment;
        }


        public boolean isFragment() {
            return isFragment;
        }


        public String getUrl() {
            return url;
        }


        public String getSoapAction() {
            return SoapAction;
        }


        public String getMethodName() {
            return MethodName;
        }


        public String getNamespace() {
            return Namespace;
        }


        public Class getTypeClass() {
            return typeClass;
        }


        public RequestHandler getIRequestHandler() {
            return aRequestHandler;
        }


        public RequestMethod getMethod() {
            return aRequestMethod;
        }

        public ContentType getContentType() {
            return contentType;
        }


        public List<Param> getBodyParams() {
            return bodyParams;
        }

        public List<Param> getHeaderParams() {
            return headerParams;
        }

        public int getTimeout() {
            return timeout;
        }

        public Object getBodyParam() {
            return bodyParam;
        }


        //Setter

        public RequesterBuilder(Context context) {
            this.context = context;
        }

        public RequesterBuilder(Fragment fragment) {

            this.fragment = fragment;
            this.context = fragment.getActivity();
            this.isFragment = true;
        }

        public RequesterBuilder setUrl(final String url) {
            this.url = url;
            return this;
        }

        public RequesterBuilder setSoapAction(final String SoapAction) {
            this.SoapAction = SoapAction;
            return this;
        }

        public RequesterBuilder setMethodName(final String MethodName) {
            this.MethodName = MethodName;
            return this;
        }

        public RequesterBuilder setNamespace(final String Namespace) {
            this.Namespace = Namespace;
            return this;
        }


        public RequesterBuilder addMapClass(final Class typeClass) {
            this.typeClass = typeClass;
            return this;
        }


        public RequesterBuilder addRequestHandler(final RequestHandler aRequestHandler) {
            this.aRequestHandler = aRequestHandler;
            return this;
        }

        public RequesterBuilder addBodyParams(final String key, Object value) {

            if (this.bodyParams == null)
                this.bodyParams = new ArrayList<>();

            this.bodyParams.add(new Param(key, value));
            return this;
        }

        public RequesterBuilder addHeaderParam(final String key, Object value) {

            if (this.headerParams == null)
                this.headerParams = new ArrayList<>();

            this.headerParams.add(new Param(key, value));
            return this;
        }

        public RequesterBuilder setRequestMethod(RequestMethod aRequestMethod) {
            this.aRequestMethod = aRequestMethod;
            return this;
        }

        public RequesterBuilder setResponseContentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        public RequesterBuilder addBodyParam(final Object bodyParam) {
            this.bodyParam = bodyParam;
            return this;
        }

        public RequesterBuilder setTimeout(final int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Requester build() {
            return new Requester(this);
        }


    }

}
