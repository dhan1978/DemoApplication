package com.openfaas.function;


import com.mysql.jdbc.profiler.LoggingProfilerEventHandler;
import com.mysql.jdbc.profiler.ProfilerEvent;
import com.openfaas.model.IHandler;

import java.io.IOException;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.postgresql.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;
import com.pusher.rest.Pusher;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.mysql.jdbc.log.Log;
public class Handler extends com.openfaas.model.AbstractHandler {
	  private static final Logger logger = LoggerFactory.getLogger(Handler.class);
	  private static String PRODUCT_TABLE_NAME = "products";
    public IResponse Handle(IRequest req) {
    //	LoggingProfilerEventHandler lpeh=new LoggingProfilerEventHandler();
    	 final Map<String, Long> tableMap = new HashMap<String, Long>();
    	//lpeh.init(conn, props);
    	BinaryLogClient client =
                new BinaryLogClient("dhananjaytestmysql.mysql.database.azure.com", 3306, "dhananjay@dhananjaytestmysql", "Dhanuki@1234");
    	Pusher pusher = 
                new Pusher("1261183", "550f92570f22ffe3b2bf", "d457dac4f7f7bd815cb9");
    		pusher.setCluster("us2");
            pusher.setEncrypted(true);
            client.registerEventListener(event -> {
                EventData data = event.getData();

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
			client.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
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
