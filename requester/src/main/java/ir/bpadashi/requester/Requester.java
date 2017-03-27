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

	private Context context;
	private Fragment fragment;
	private boolean isFragment;

	private String url;
	private String method_WSDL;
	private Class typeClass;
	private IRequestHandler aRequestHandler;
	private List<Params> paramList;
	private Method aMethod;
	private boolean isPlainText = true;

	public Requester(RequesterBuilder builder) {

        this.fragment = builder.fragment;
        this.context = builder.context;
        this.isFragment = builder.isFragment;
        
        this.url = builder.url;
        this.method_WSDL = builder.method_WSDL;
        this.typeClass = builder.typeClass;
        this.isPlainText = builder.isPlainText;
        this.aRequestHandler = builder.aRequestHandler;
        this.paramList=builder.paramList;
        this.aMethod = builder.aMethod;
        
    }

	public void executeSync() {

		if (isFragment)
			queue.add(new RequesterRunnable(fragment, isFragment, url, typeClass, aRequestHandler, paramList, isPlainText,
					method_WSDL, aMethod));
		else
			queue.add(new RequesterRunnable(context, isFragment, url, typeClass, aRequestHandler, paramList, isPlainText,
					method_WSDL, aMethod));

		while (queue.size() > 0) {
			ConnectionThreadSingle.getInstance().AddTask(queue.poll());
		}

	}

	public void executeAnSync() {

		if (isFragment)
			queue.add(new RequesterRunnable(fragment, isFragment, url, typeClass, aRequestHandler, paramList, isPlainText,
					method_WSDL, aMethod));
		else
			queue.add(new RequesterRunnable(context, isFragment, url, typeClass, aRequestHandler, paramList, isPlainText,
					method_WSDL, aMethod));

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
		private String method_WSDL;
		private Class typeClass;
		private IRequestHandler aRequestHandler;
		private List<Params> paramList;
		private Method aMethod;
		private boolean isPlainText;
		

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

		public RequesterBuilder setWsdlMethod(final String method_WSDL) {
			this.method_WSDL = method_WSDL;
			return this;
		}

		public RequesterBuilder setModel(final Class typeClass) {
			this.typeClass = typeClass;
			return this;
		}

		public RequesterBuilder responseIsPlainText() {
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
