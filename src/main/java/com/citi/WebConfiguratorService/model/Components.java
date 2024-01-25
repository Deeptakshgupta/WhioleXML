/*
 * Created by : Hamid Raza Siddiqui
 * Date : 17/04/2023
 * 
 * Fix 1.0.0
 * 		Date : 23/05/2023
 * 		Context : Added unique constraint to name with @ID mapping at entity level.
 * */

package com.citi.WebConfiguratorService.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="COMPONENTMAST")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Components {
    
	@Column(name = "COMPONENTID")
	private long componentId;
	
	@Id
	@Column(name = "COMPONENTNAME", nullable = false)
	private String componentName;
	
	@Column(name = "VERSION", nullable = false)
	private String version;
	
	@Column(name = "CATEGORYID", nullable = false)
	private long categoryId;
	
	@Column(name = "AUTHOR", nullable = false)
	private String author;
	
//	@Column(name = "customer_name")
//	private LocalDateTime creationDate;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	
	@Column(name = "ISPUBLISHED", nullable = false)
	private String isPublished;
	
	@Column(name = "IMAGEPATH", columnDefinition = "BLOB",nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
    private byte[]  imagePath;
    
    @Column(name = "COMPONENTXML", columnDefinition = "BLOB",nullable = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[]  componentXml;
    
    @ManyToOne(fetch = FetchType.LAZY,targetEntity=Customer.class)
    @JoinColumn(name = "CREATEDBYID", referencedColumnName = "customerId") 
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="customerId")
    private Customer customer;
    
    @Transient
    private String message;
}
