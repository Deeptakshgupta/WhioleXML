package com.citi.WebConfiguratorService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="WCS_CLIENT_DETAIL")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Customer {

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientSeq")
//    @SequenceGenerator(sequenceName = "client_detail_seq", allocationSize = 1, name = "clientSeq")
//    private long id;

	@Id
	@Column(name = "customerId",columnDefinition = "nvarchar2(127)")
	private String customerId;
	
	@Column(name = "customer_name",columnDefinition = "nvarchar2(127)")
    private String customerName;
    
    @Column(name = "customer_type" ,columnDefinition = "nvarchar2(127)")
    private String customerType;
    
    @Column(name = "customer_address" ,columnDefinition = "nvarchar2(127)")
    private String customerAddress;
    
    @Column(name = "customer_email" ,columnDefinition = "nvarchar2(127)")
    private String customerEmail;
    
    @Column(name = "customer_phone" ,columnDefinition = "nvarchar2(127)")
    private String customerPhone;
    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy="customer",fetch = FetchType.LAZY)
//    private Set<Components> componentsCust;
//    
//    @OneToMany(cascade = CascadeType.ALL,mappedBy="layCustomer",fetch = FetchType.LAZY)
//    private Set<Layouts> layoutsCust;
     
    @Transient
    private String message;

}
