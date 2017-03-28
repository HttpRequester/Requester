package ir.bpadashi.requester;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.support.v4.app.Fragment;

import ir.bpadashi.requester.model.Params;
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
        private IRequestHandler aRequestHandler;
        private List<Params> paramList;
        private Method aMethod;
        private boolean isPlainText;

        //Getter

        public Context getContext(){
            return context ;
        }

        public Fragment getFragment(){
            return fragment;
        }


        public boolean isFragment(){
            return isFragment;
        }


        public String getUrl(){
            return url;
        }


        public String getSoapAction(){
            return SoapAction ;
        }


        public String getMethodName(){
            return MethodName ;
        }


        public String getNamespace(){
            return Namespace ;
        }


        public Class getTypeClass(){
            return typeClass;
        }


        public IRequestHandler getIRequestHandler(){
            return aRequestHandler ;
        }


        public Method getMethod(){
            return aMethod;
        }


        public boolean isPlainText(){
            return isPlainText ;
        }


        public List<Params> getParamList(){
            return paramList ;
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


        public RequesterBuilder setModel(final Class typeClass) {
            this.typeClass = typeClass;
            return this;
        }

        public RequesterBuilder dontCache() {
            this.isPlainText = true;
            return this;
        }

        public RequesterBuilder addRequestHandler(final IRequestHandler aRequestHandler) {
            this.aRequestHandler = aRequestHandler;
            return this;
        }

        public RequesterBuilder addParam(final String key, Object value) {

            if (this.paramList == null)
                this.paramList = new ArrayList<>();

            this.paramList.add(new Params(key, value));
            return this;
        }

        public RequesterBuilder setMethod(Method aMethod) {
            this.aMethod = aMethod;
            return this;
        }

        public Requester build() {
            return new Requester(this);
        }

    }

}
