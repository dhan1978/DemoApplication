/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.SpringMVCExample.util;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sube.si
 */

public class GroupPropertiesFeature implements Serializable {

    private static final long serialVersionUID = 1L;
    
   
    private String id;
   
    private String name;
    
    private String email;
    
    public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	private String active;
    
    private String description;
   
   	private String createdBy = "SX_Integration_User";
    
   	private String lastUpdatedBy = "SX_Integration_User";
    
   	private long createDate = Instant.now().toEpochMilli();
    
   	private long lastUpdatedDate = Instant.now().toEpochMilli();
    
    private String managerId;
    
    private String supportGroup;
	
    private String groupEmailAddress;
    
    private String emailAddress;
    
    private String groupType;
    
    private String repStatus;
    
    private String repType;
    
    private String external_referenceId;
    
	
    public GroupPropertiesFeature() {
    }


	
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}


	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}


	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	/**
	 * @return the lastUpdatedBy
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	/**
	 * @param lastUpdatedBy the lastUpdatedBy to set
	 */
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}


	/**
	 * @return the createDate
	 */
	public long getCreateDate() {
		return createDate;
	}


	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}


	/**
	 * @return the lastUpdatedDate
	 */
	public long getLastUpdatedDate() {
		return lastUpdatedDate;
	}


	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(long lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}


	/**
	 * @return the managerId
	 */
	public String getManagerId() {
		return managerId;
	}


	/**
	 * @param managerId the managerId to set
	 */
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}


	/**
	 * @return the supportGroup
	 */
	public String getSupportGroup() {
		return supportGroup;
	}


	/**
	 * @param supportGroup the supportGroup to set
	 */
	public void setSupportGroup(String supportGroup) {
		this.supportGroup = supportGroup;
	}


	/**
	 * @return the groupEmailAddress
	 */
	public String getGroupEmailAddress() {
		return groupEmailAddress;
	}


	/**
	 * @param groupEmailAddress the groupEmailAddress to set
	 */
	public void setGroupEmailAddress(String groupEmailAddress) {
		this.groupEmailAddress = groupEmailAddress;
	}


	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}


	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}


	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}


	/**
	 * @return the repStatus
	 */
	public String getRepStatus() {
		return repStatus;
	}


	/**
	 * @param repStatus the repStatus to set
	 */
	public void setRepStatus(String repStatus) {
		this.repStatus = repStatus;
	}


	/**
	 * @return the repType
	 */
	public String getRepType() {
		return repType;
	}


	/**
	 * @param repType the repType to set
	 */
	public void setRepType(String repType) {
		this.repType = repType;
	}


	/**
	 * @return the external_referenceId
	 */
	public String getExternal_referenceId() {
		return external_referenceId;
	}


	/**
	 * @param external_referenceId the external_referenceId to set
	 */
	public void setExternal_referenceId(String external_referenceId) {
		this.external_referenceId = external_referenceId;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupPropertiesFeature [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", active=");
		builder.append(active);
		builder.append(", description=");
		builder.append(description);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastUpdatedBy=");
		builder.append(lastUpdatedBy);
		builder.append(", createDate=");
		builder.append(createDate);
		builder.append(", lastUpdatedDate=");
		builder.append(lastUpdatedDate);
		builder.append(", managerId=");
		builder.append(managerId);
		builder.append(", supportGroup=");
		builder.append(supportGroup);
		builder.append(", groupEmailAddress=");
		builder.append(groupEmailAddress);
		builder.append(", emailAddress=");
		builder.append(emailAddress);
		builder.append(", groupType=");
		builder.append(groupType);
		builder.append(", repStatus=");
		builder.append(repStatus);
		builder.append(", repType=");
		builder.append(repType);
		builder.append(", external_referenceId=");
		builder.append(external_referenceId);
		builder.append("]");
		return builder.toString();
	}


}
