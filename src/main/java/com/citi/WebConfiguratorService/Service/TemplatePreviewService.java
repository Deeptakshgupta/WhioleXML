package com.citi.WebConfiguratorService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.citi.WebConfiguratorService.model.Reports;
import com.citi.WebConfiguratorService.repository.ReportWorkTableRepository;
import com.citi.WebConfiguratorService.exceptionHandler.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplatePreviewService {

	private final ReportWorkTableRepository reportRepository;
	
	
	public List<Reports> previewTemplate(String reportName) throws ResourceNotFoundException
	{
		List<Reports> reportLists = reportRepository.findAll();
		if( reportLists == null)
			throw(new ResourceNotFoundException(reportName));
		else
			return reportLists;
		
	}
}
