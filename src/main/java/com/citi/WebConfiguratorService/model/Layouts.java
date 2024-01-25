/*
 * Created by : Hamid Raza Siddiqui
 * Date : 17/04/2023
 * 
 * Fix 1.0.0
 * 		Date : 23/05/2023
 * 		Context : The table relation was destroyed so fixed added unique constraint to name with @ID mapping at entity level.
 * */
package com.citi.WebConfiguratorService.model;

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
@Table(name="LAYOUTMAST")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Layouts  {

	@Column(name = "LAYOUTID", nullable = false)
	private long layoutId;
	
	@Id
	@Column(name = "LAYOUTNAME", nullable = false)
    private String layoutName;
   
    
    @Column(name = "VERSION", nullable = false)
    private String layoutVersion;
    
    @Column(name = "IMAGEPATH", nullable = false)
    @Lob
    private byte[] imagePath;
    
    @Column(name = "IMAGETIFF", nullable = false)
    @Lob
    private byte[] imageTiff;
    
    @Column(name = "LAYOUTXML", nullable = false)
    @Lob
    private byte[] layoutXml;
    
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;
    
    @Column(name = "ISPUBLISHED", nullable = false)
	private String isPublished;
    
    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity=Customer.class)
    @JoinColumn(name = "CREATEDBYID",referencedColumnName = "customerId") 
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="customerId")
    private Customer layCustomer;
    
    @Transient
    private String message;
}
