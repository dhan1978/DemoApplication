package com.openfaas.function;



import java.sql.*;
import java.io.IOException;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.Serializable;
import java.lang.management.ManagementFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import org.postgresql.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mysql.jdbc.*;
import java.sql.Connection;

import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import com.pusher.rest.Pusher;
import com.sun.javafx.event.EventQueue;
import com.mysql.jdbc.jdbc2.optional.*;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer.CompatibilityMode;
import com.github.shyiko.mysql.binlog.network.SSLMode;
//import com.pusher.pushnotifications.PushNotifications;
public class Handler extends com.openfaas.model.AbstractHandler {
	  private static final Logger logger = LoggerFactory.getLogger(Handler.class);
	  private EventQueue counters;
	  Supplier<BinaryLogClient> preConnect;
	  private static String PRODUCT_TABLE_NAME = "products";
    public IResponse Handle(IRequest req) {
    //	LoggingProfilerEventHandler lpeh=new LoggingProfilerEventHandler();
    	 final Map<String, Long> tableMap = new HashMap<String, Long>();
    	 Map<String, Object> configuration=new HashMap<String, Object>();
    	//lpeh.init(conn, props);
    	// PushNotifications beamsClient = new PushNotifications(instanceId, "926a61b08c9a34fc6685");
  //BinaryLogClient client = new BinaryLogClient("dhananjaytestmysql.mysql.database.azure.com", 3306, "mysql","dhananjay@dhananjaytestmysql", "Dhanuki@1234");
    	 configuration.put("mysql.hostname", "172.30.47.135");
    	 configuration.put("mysql.schema","LambdaTest");
    	 configuration.put("mysql.username","Dhananjay");
    	 configuration.put("mysql.password","Dhanuki@1234");
    	
    	Pusher pusher = 
                new Pusher("1264375", "926a61b08c9a34fc6685", "84d6a3ad5df3c6339589");
    	
    		pusher.setCluster("us2");
            pusher.setEncrypted(true);
            Connection conn = null;
          //  System.setProperty("javax.net.ssl.keyStore", "/path/to/keystore.jks");
            //System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
          String  transitUri = "mysql://" + "172.30.47.135" + ":" + 3306;
          try {
			conn =
				       DriverManager.getConnection("jdbc:mysql://172.30.47.135/LambdaTest?" +
				                                   "user=Dhananjay&password=Dhanuki@1234");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
         
            BinaryLogClient client = new BinaryLogClient("172.30.47.135", 3306, "LambdaTest","Dhananjay", "Dhanuki@1234");
           // client = new BinaryLogClient(config.getHostname(), config.getPort(), "replicator", "replpass");
            client.setServerId(client.getServerId() - 1); // avoid clashes between BinaryLogClient instances
            client.setKeepAlive(false);
           client.setSSLMode(SSLMode.DISABLED);
           EventDeserializer eventDeserializer = new EventDeserializer();
           eventDeserializer.setEventDataDeserializer(EventType.STOP, (EventDataDeserializer) new TableMapEventData());
           client.setEventDeserializer(eventDeserializer);
           if (preConnect != null) {
           if (preConnect != null) preConnect.get();
           }
          //  client.setSSLMode(SSLMode.REQUIRED);
           // client.setSSLMode(SSLMode.VERIFY_IDENTITY);
            //   client.setSSLMode(SSLMode.VERIFY_IDENTITY); 
//            Eventserializer ventserializer= new Eventserializer()
            
           // client.registerEventListener(new TraceEventListener());
            //client.registerEventListener(eventListener = new CountDownEventListener());
            //client.registerLifecycleListener(new TraceLifecycleListener());
            //client.connect(DEFAULT_TIMEOUT);
           
            client.registerEventListener(event -> {
                EventData data = event.getData();
                System.out.println(event);
                if(data instanceof TableMapEventData) {
                    TableMapEventData tableData = (TableMapEventData)data;
                    tableMap.put(tableData.getTable(), tableData.getTableId());
                } else if(data instanceof WriteRowsEventData) {
                    WriteRowsEventData eventData = (WriteRowsEventData)data;
                    if(eventData.getTableId() == tableMap.get(PRODUCT_TABLE_NAME)) {
                        for(Object[] product: eventData.getRows()) {
                            pusher.trigger(PRODUCT_TABLE_NAME, "insert", getProductMap(product));
                            
                        }
                    }
                } else if(data instanceof UpdateRowsEventData) {
                    UpdateRowsEventData eventData = (UpdateRowsEventData)data;
                    if(eventData.getTableId() == tableMap.get(PRODUCT_TABLE_NAME)) {
                        for(Map.Entry<Serializable[], Serializable[]> row : eventData.getRows()) {
                            pusher.trigger(PRODUCT_TABLE_NAME, "update", getProductMap(row.getValue()));
                        }
                    }
                } else if(data instanceof DeleteRowsEventData) {
                    DeleteRowsEventData eventData = (DeleteRowsEventData)data;
                    if(eventData.getTableId() == tableMap.get(PRODUCT_TABLE_NAME)) {
                        for(Object[] product: eventData.getRows()) {
                            pusher.trigger(PRODUCT_TABLE_NAME, "delete", product[0]);
                        }
                    }
                }
            });
		  try {
			 // client.registerLifecycleListener(new TraceLifecycleListener());
		
			  //eventDeserializer.setEventDataDeserializer(EventType
			  
			  client.connect(Long.MAX_VALUE); // does not block
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	        // connect to Pusher
	        
    	/*try {
    		 
			Connection conn=dbDataSource().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	//Notification no=new Notification();
    	
        Response res = new Response();
            res.setBody("Hello, world!");

            return res;
    }
    static Map<String, String> getProductMap(Object[] product) {
        Map<String, String> map = new HashMap<>();
        map.put("id", java.lang.String.valueOf(product[0]));
        map.put("name", java.lang.String.valueOf(product[1]));
        map.put("price", java.lang.String.valueOf(product[2]));

        return map;
    }
  //  @Override
   // public String handleRequest(Notification event, Context context)
    public String handleRequest(Notification event)
    {
      String response = new String("200 OK");
   //   for (DynamodbStreamRecord record : event.getRecords()){
       // logger.info(record.getEventID());
        //logger.info(record.getEventName());
        //logger.info(record.getDynamodb().toString());
      //}
      logger.info(""+ event.getPID());
      logger.info(event.getName());
      logger.info(event.getParameter());
      return response;
}
    /**
     * Returns the current process ID
     * @return
     */
    private static String getPID() {
        return ManagementFactory
                .getRuntimeMXBean()
                .getName()
                .split("@")[0];
    }
    
   

}
