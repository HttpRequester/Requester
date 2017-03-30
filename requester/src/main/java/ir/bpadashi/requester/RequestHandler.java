package ir.bpadashi.requester;

import ir.bpadashi.requester.model.ParentContext;
import ir.bpadashi.requester.model.ResponseString;

public interface RequestHandler {
	
	public  void onStart();
	public  void onCache(ParentContext context,Object responseObj);
	public  void onResponse(ParentContext context,ResponseString responseString);
	public  void onSuccess(ParentContext context, Object responseObj, boolean hasCache);
	public  void onError(ParentContext context, Exception exception, String exceptionFarsi);
	
	
	
    
}
