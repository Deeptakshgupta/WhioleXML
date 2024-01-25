package com.citi.WebConfiguratorService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.citi.WebConfiguratorService.model.Layouts;

@Repository
public interface LayoutRepository extends JpaRepository<Layouts, String> {

	//Layouts findTopBylayoutNameContainingIgnoreCaseAndIsPublishedOrderByLayoutVersionDesc(String layoutName,String published);
	
	@Query(value ="select DISTINCT * from LAYOUTMAST wc1 where lower(wc1.LAYOUTNAME) like ?1 escape '\\' and wc1.ISPUBLISHED = 'Y'  "
			+ "and rownum  >= ?2 and rownum <= ?3 and wc1.CREATEDBYID = ?4 "
			+ " and  wc1.VERSION in (SELECT max(VERSION) FROM LAYOUTMAST wc2 where "
			+ "wc2.LAYOUTNAME=wc1.LAYOUTNAME )",nativeQuery = true)
	List<Layouts> getResultListLayoutName(String layoutName,int first,int last,String customeId);

	//List<Layouts> findBylayCustomerAndIsPublishedContaining(Customer customer,String published);
	
	@Query(value ="select * from LAYOUTMAST wc1 where wc1.CREATEDBYID like ?1 escape '\\' and wc1.ISPUBLISHED = 'Y'  "
			+ "and rownum  >= ?2 and rownum <= ?3 "
			+ " and  wc1.VERSION in (SELECT max(VERSION) FROM LAYOUTMAST wc2 where "
			+ "wc2.LAYOUTNAME=wc1.LAYOUTNAME )",nativeQuery = true)
	List<Layouts> getResultList(String customeId,int first,int last);
	
	
}
