package com.thingworx.sdk.batch;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.thingworx.communications.client.things.filetransfer.FileTransferVirtualThing;
import com.thingworx.relationships.RelationshipTypes;
import com.thingworx.types.primitives.LocationPrimitive;
import org.joda.time.DateTime;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.metadata.FieldDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinition;
import com.thingworx.metadata.annotations.ThingworxEventDefinitions;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import com.thingworx.metadata.annotations.ThingworxServiceDefinition;
import com.thingworx.metadata.annotations.ThingworxServiceParameter;
import com.thingworx.metadata.annotations.ThingworxServiceResult;
import com.thingworx.metadata.collections.FieldDefinitionCollection;
import com.thingworx.types.BaseTypes;
import com.thingworx.types.InfoTable;
import com.thingworx.types.collections.ValueCollection;
import com.thingworx.types.constants.CommonPropertyNames;
import com.thingworx.types.primitives.StringPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

// Refer to the "Steam Sensor Example" section of the documentation
// for a detailed explanation of this example's operation

// Property Definitions
@SuppressWarnings("serial")
@ThingworxPropertyDefinitions(properties = {
        @ThingworxPropertyDefinition(name = "testname", description = "name for test",
                baseType = "STRING", category = "Status", aspects = { "isReadOnly:false" })
		})

// Steam Thing virtual thing class that simulates a Steam Sensor
public class BatchConnectThing extends FileTransferVirtualThing implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(BatchConnectThing.class);
    private final Appender<ILoggingEvent> fileLogAppender;
    private Thread _shutdownThread = null;
    private boolean readyToSend = false;
    private final File logDirectory;
    private BatchComBridge bridge = null;

    public BatchConnectThing(String name, String description, String identifier,
                      ConnectedThingClient client, Appender<ILoggingEvent> fileLogAppender) throws Exception {

        super(name, description, identifier, client);

        // Create and share for file transfer, a directory containing this application's
        // logs
        logDirectory = new File("./logs");
        if(!logDirectory.exists())
            logDirectory.mkdir();
        addVirtualDirectory("logs", logDirectory.getCanonicalPath());
        this.fileLogAppender=fileLogAppender;
        this.bridge = BatchComBridge.getInstance();
        

    }

//     This method will get called when a bind or a configuration of the bound properties of this thing has changed on
//     the thingworx platform
//     Until this event occurs for the first time after binding no property pushes should be made because they
//     will not get sent to the platform
    public void synchronizeState() {
        readyToSend = true;
        // Send the property values to ThingWorx when a synchronization is required
        // This is more important for a solution that does not push its properties on a regular basis
        super.syncProperties();
    }

    @ThingworxServiceDefinition(name = "AddNumbers", description = "Add Two Numbers")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "NUMBER")
    public Double AddNumbers(
            @ThingworxServiceParameter(name = "a", description = "Value 1",
                    baseType = "NUMBER") Double a,
            @ThingworxServiceParameter(name = "b", description = "Value 2",
                    baseType = "NUMBER") Double b)
            throws Exception {

        return a + b;
    }

    @ThingworxServiceDefinition(name = "StartLogging", description = "starts creating local log files.")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "NOTHING")
    public void StartLogging() throws Exception {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger("ROOT");
        logger.addAppender(fileLogAppender);
        LOG.info("Started Logging to File.");
    }

    @ThingworxServiceDefinition(name = "StopLogging", description = "stops creating local log files.")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "NOTHING")
    public void StopLogging() throws Exception {
        LOG.info("Stopped Logging to File.");
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger("ROOT");
        logger.detachAppender("ROLLING");
    }

    @ThingworxServiceDefinition(name = "helloWorld", description = "say hello to thingworx")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "STRING")
    public String helloWorld(@ThingworxServiceParameter(name = "name", description = "Value cao xi",
            baseType = "STRING") String in) {
    	if (in == null)    	
    		return "Hello World" + getCurrentPropertyValue("testname");
    	else
    		return "Hello World Caoxi";
    }
    
    @ThingworxServiceDefinition(name = "getItem", description = "use COM component to get batch infomation")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "STRING")
    public String getItem(@ThingworxServiceParameter(name = "item", description = "item which u want to get",
            baseType = "STRING") String item) {
    	if (item != null)    	
    		return bridge.callGetItem(item);
    	else
    		return "{}";
    }
    
    @ThingworxServiceDefinition(name = "doExecute", description = "use COM component to execute batch command")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "Result",
            baseType = "STRING")
    public String doExecute(@ThingworxServiceParameter(name = "command", description = "command which u want to execute",
            baseType = "STRING") String command) {
    	if (command != null)    	
    		return bridge.callExecute(command);
    	else
    		return "{}";
    }

    @ThingworxServiceDefinition(name = "Shutdown", description = "Shutdown the client")
    @ThingworxServiceResult(name = CommonPropertyNames.PROP_RESULT, description = "",
            baseType = "NOTHING")
    public synchronized void Shutdown() throws Exception {
        // Should not have to do this, but guard against this method being called more than once.
        if (this._shutdownThread == null) {
            // Create a thread for shutting down and start the thread
            this._shutdownThread = new Thread(this);
            this._shutdownThread.start();
        }
    }

    @Override
    public void run() {
        try {
            // Delay for a period to verify that the Shutdown service will return
            Thread.sleep(1000);
            // Shutdown the client
            this.getClient().shutdown();
        } catch (Exception x) {
            // Not much can be done if there is an exception here
            // In the case of production code should at least log the error
        }
    }
}
