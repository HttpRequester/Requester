package ir.bpadashi.requester;

public interface IRequestHandler  {
	
	public  void onStart();
	public  void onCache(Object context,Object model);
	public  void onResponse(Object context,StringBuilder response);
	public  void onSuccess(Object context,Object model,boolean hasCache);
	public  void onError(Object context, Exception exception, String exceptionFarsi);
	
	
	
    
}
