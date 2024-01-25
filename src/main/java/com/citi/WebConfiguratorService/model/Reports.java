package com.citi.WebConfiguratorService.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="REPORT_WORK_TABLE")
@NoArgsConstructor 
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reports{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", nullable = false)
	private long id;
	
	@Column(name = "REPORTID", nullable = false)
	private String reportId;
	
	@Column(name = "REPORTNAME", nullable = false)
	private String reportName;
	
	@Column(name = "CATEGORYID", nullable = false)
	private String categoryId;
	
	@Column(name = "VERSION", nullable = false)
	private String version;
	
	@Column(name = "REPORTXML", columnDefinition = "BLOB",nullable = false)
    @Lob
    @Basic(fetch = FetchType.LAZY)
	private String reportXML;
	
	@Column(name = "IMAGEPATH", columnDefinition = "BLOB",nullable = false)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String imagePath;
	
	@Column(name = "ISPUBLISHED", nullable = false)
	private String isPublished;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity=Customer.class)
    @JoinColumn(name = "CREATEDBYID", referencedColumnName = "customerId") 
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="customerId")
    private Customer customer;
	
}