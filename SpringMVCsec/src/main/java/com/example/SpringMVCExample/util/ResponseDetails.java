package com.example.SpringMVCExample.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.Date;
import java.util.List;

/**
 * The Class ErrorDetails.
 */
public class ResponseDetails {
	  
  	/** The timestamp. */
  	private Date timestamp;
	  
  	/** The message. */
  	private String message;
	  
  	/** The details. */
  	private String details;
  	
  	/** The statusCode. */
  	private String statusCode;
  	
  	private String batchCount = "0";
  	
  	private String jobId = "0";
  	
	private int userErrorCount;
  	
  	private String itemDetail;

	/**
	 * Array holding list of invalid entities in error response
	 */
	private List<InvalidEntity> invalidEntityList;

	  /**
  	 * Instantiates a new error details.
  	 *
  	 * @param timestamp the timestamp
  	 * @param message the message
  	 * @param details the details
  	 */
  	public ResponseDetails(Date timestamp, String message, String details, String statusCode, String itemDetail) {
	    super();
	    this.timestamp = timestamp;
	    this.message = message;
	    this.details = details;
	    this.statusCode = statusCode;
	    this.itemDetail = itemDetail;
	  }

	  /**
  	 * Gets the timestamp.
  	 *
  	 * @return the timestamp
  	 */
  	public Date getTimestamp() {
	    return timestamp;
	  }

	  /**
  	 * Gets the message.
  	 *
  	 * @return the message
  	 */
  	public String getMessage() {
	    return message;
	  }

	  /**
  	 * Gets the details.
  	 *
  	 * @return the details
  	 */
  	public String getDetails() {
	    return details;
	  }

	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the itemDetail
	 */
	public String getItemDetail() {
		return itemDetail;
	}

	/**
	 * @param itemDetail the itemDetail to set
	 */
	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
	
	

	/**
	 * @return the batchCount
	 */
	public String getBatchCount() {
		return batchCount;
	}

	/**
	 * @param batchCount the batchCount to set
	 */
	public void setBatchCount(String batchCount) {
		this.batchCount = batchCount;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	
	

	/**
	 * @return the jobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public List<InvalidEntity> getInvalidEntityList() {
		return invalidEntityList;
	}

	public void setInvalidEntityList(List<InvalidEntity> invalidEntityList) {
		this.invalidEntityList = invalidEntityList;
	}
	
	

	/**
	 * @return the userErrorCount
	 */
	public int getUserErrorCount() {
		return userErrorCount;
	}

	/**
	 * @param userErrorCount the userErrorCount to set
	 */
	public void setUserErrorCount(int userErrorCount) {
		this.userErrorCount = userErrorCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseDetails [timestamp=").append(timestamp).append(", message=").append(message)
				.append(", details=").append(details).append(", statusCode=").append(statusCode).append(", batchCount=")
				.append(batchCount).append(", jobId=").append(jobId).append(", userErrorCount=").append(userErrorCount)
				.append(", itemDetail=").append(itemDetail).append(", invalidEntityList=").append(invalidEntityList)
				.append("]");
		return builder.toString();
	}


	}