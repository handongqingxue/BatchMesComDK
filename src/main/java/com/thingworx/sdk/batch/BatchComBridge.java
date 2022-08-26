package com.thingworx.sdk.batch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  com.jacob.activeX.ActiveXComponent;
import  com.jacob.com.Dispatch;

public class BatchComBridge {
	private static final Logger LOG = LoggerFactory.getLogger(BatchConnectClient.class);
	private ActiveXComponent batchserver = null;
	private Dispatch server = null;	
	
    private static class BatchComBridgeHolder{
        private static BatchComBridge instance=new BatchComBridge();
    }
    private BatchComBridge(){
    	try {
	    	batchserver = new ActiveXComponent( "BatchControl.BatchServer" );
			server  =  (Dispatch)(batchserver.getObject());
    	}
    	catch(Exception e) {
    		LOG.error(e.toString());
    	}
    }
    public static BatchComBridge getInstance(){
        return BatchComBridgeHolder.instance;
    }
	
	public synchronized String callGetItem(String item) {
		try {
			return Dispatch.call(server,"GetItem",item).getString();
		}catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.toString());
			return null;
		}
	}
	
	public synchronized String callExecute(String command) {
		try {
			return Dispatch.call(server,"Execute",command).getString();
		}catch(Exception e) {
			LOG.error(e.toString());
			return null;
		}
	}

}