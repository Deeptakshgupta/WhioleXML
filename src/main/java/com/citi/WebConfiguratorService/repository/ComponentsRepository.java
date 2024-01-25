package com.citi.WebConfiguratorService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.citi.WebConfiguratorService.model.Components;

@Repository
public interface ComponentsRepository extends JpaRepository<Components, String> {

	/** To get the published Component w.r.t the ComponentId and CategoryID*/
	//Components findTopByIdOrderByComponentsVersionDesc(Long id);
	Components findTopBycomponentNameContainingIgnoreCaseAndIsPublishedOrderByVersionDesc(String componentName,String published);
	
	/** To get List of All published Components **/
	@Query(value ="select DISTINCT * from COMPONENTMAST wc1 "
			+ "where lower(wc1.COMPONENTNAME) like ?1 escape '\\'  and wc1.ISPUBLISHED = 'Y' and rownum  >= ?2 and rownum <= ?3 "
			+ " and wc1.CREATEDBYID = ?4 and wc1.VERSION in (SELECT max(VERSION) "
			+ "FROM COMPONENTMAST wc2 where wc2.COMPONENTNAME=wc1.COMPONENTNAME )",nativeQuery=true)
	List<Components> getResultListcomponentName(String componentName,int first,int last,String customeId);
	
	/** To get All Components W.r.t the provided customerId**/
	//List<Components> findTopBycustomerAndIsPublishedContainingOrderByVersionDesc(Customer customer,String published);
	
	@Query(value ="select * from COMPONENTMAST wc1 "
			+ "where wc1.CREATEDBYID like ?1 escape '\\' and wc1.ISPUBLISHED = 'Y' and rownum  >= ?2 and rownum <= ?3 "
			+ "and wc1.VERSION in (SELECT max(VERSION) "
			+ "FROM COMPONENTMAST wc2 where wc2.COMPONENTNAME=wc1.COMPONENTNAME )",nativeQuery=true)
	List<Components> getResultList(String customeId,int first,int last);
}

