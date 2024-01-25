package com.citi.WebConfiguratorService.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.citi.WebConfiguratorService.model.Reports;

public interface ReportWorkTableRepository extends JpaRepository<Reports, Long>{

	public Reports findByReportName(String reportName);

	
}
