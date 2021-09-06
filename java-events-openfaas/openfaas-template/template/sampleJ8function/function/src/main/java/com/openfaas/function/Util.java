package com.openfaas.function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.LambdaLogger;
//import com.openfaas.

import com.google.gson.Gson;


public class Util {
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
  public static void logEnvironment(Object event, Gson gson)
  {
	  //logEnvironment(Object event, Context context, Gson gson)
   // LambdaLogger logger = context.getLogger();
    // log execution details
    logger.info("ENVIRONMENT VARIABLES: " + gson.toJson(System.getenv()));
    logger.info("CONTEXT: " + gson.toJson(""));
    // log event details// logger.log
    logger.info("EVENT: " + gson.toJson(event));
    logger.info("EVENT TYPE: " + event.getClass().toString());
  }
}