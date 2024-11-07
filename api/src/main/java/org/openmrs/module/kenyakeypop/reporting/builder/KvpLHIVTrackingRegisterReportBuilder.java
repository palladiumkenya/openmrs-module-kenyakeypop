/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.builder;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardIndicatorLibrary;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.KvpLHIVTrackingRegisterCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.cohort.analysis.lhivTrackerOffsite" })
public class KvpLHIVTrackingRegisterReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String DATE_FORMAT_YYMM = "MM/yyyy";
	
	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return allPeersCohort();
	}
	
	protected Mapped<CohortDefinition> allPeersCohort() {
		CohortDefinition cd = new KvpLHIVTrackingRegisterCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setName("KP Peer Tracking Register(Key and vulnerable population)");
		return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		
		PatientDataSetDefinition allPeers = kpPeerTrackingDataSetDefinition();
		allPeers.addRowFilter(allPeersCohort());
		//allPatients.addRowFilter(buildCohort(descriptor));
		DataSetDefinition allPatientsDSD = allPeers;
		
		return Arrays.asList(ReportUtils.map(allPatientsDSD, "startDate=${startDate},endDate=${endDate}"));
	}
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}
	
	protected PatientDataSetDefinition kpPeerTrackingDataSetDefinition() {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("lHIVTrackerOffsite");
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		String paramMapping = "startDate=${startDate},endDate=${endDate}";
		DataConverter ageFormatter = new ObjectFormatter("{age}");
		DataDefinition ageDef = new ConvertedPersonDataDefinition("age", new AgeDataDefinition(), ageFormatter);
		//PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		KeyPopTypeDataDefinition kpType = new KeyPopTypeDataDefinition();
		kpType.addParameter(new Parameter("startDate", "Start Date", Date.class));
		kpType.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		CD4DateDataDefinition cd4DateDataDefinition = new CD4DateDataDefinition();
		cd4DateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd4DateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		CD4CountDataDefinition cd4CountDataDefinition = new CD4CountDataDefinition();
		cd4CountDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd4CountDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ViralLoadDataDefinition viralLoadDataDefinition = new ViralLoadDataDefinition();
		viralLoadDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		viralLoadDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ViralLoadDateDataDefinition viralLoadDateDataDefinition = new ViralLoadDateDataDefinition();
		viralLoadDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		viralLoadDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		IPTStartedDateDataDefinition iptStartedDateDataDefinition = new IPTStartedDateDataDefinition();
		iptStartedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		iptStartedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		IPTCompletedDateDataDefinition iptCompletedDateDataDefinition = new IPTCompletedDateDataDefinition();
		iptCompletedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		iptCompletedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		PersonDisclosedToDataDefinition personDisclosedToDataDefinition = new PersonDisclosedToDataDefinition();
		personDisclosedToDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		personDisclosedToDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		HIVDiagnosedDateDataDefinition hivDiagnosedDateDataDefinition = new HIVDiagnosedDateDataDefinition();
		hivDiagnosedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		hivDiagnosedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		DisclosedStatusDataDefinition disclosedStatusDataDefinition = new DisclosedStatusDataDefinition();
		disclosedStatusDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		disclosedStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		NameOfPeerEducatorDataDefinition nameOfPeerEducatorDataDefinition = new NameOfPeerEducatorDataDefinition();
		nameOfPeerEducatorDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		nameOfPeerEducatorDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ARTStartDateDataDefinition artStartDateDataDefinition = new ARTStartDateDataDefinition();
		artStartDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		artStartDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ARTCurrentRegimenDataDefinition artCurrentRegimenDataDefinition = new ARTCurrentRegimenDataDefinition();
		artCurrentRegimenDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		artCurrentRegimenDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		FacilityAccessingARTDataDefinition facilityAccessingARTDataDefinition = new FacilityAccessingARTDataDefinition();
		facilityAccessingARTDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		facilityAccessingARTDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		OppotunisticInfectiousDiagnosedDateDataDefinition oppotunisticInfectiousDiagnosedDateDataDefinition = new OppotunisticInfectiousDiagnosedDateDataDefinition();
		oppotunisticInfectiousDiagnosedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		oppotunisticInfectiousDiagnosedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		OppotunisticInfectiousInitiatedDateDataDefinition oppotunisticInfectiousInitiatedDateDataDefinition = new OppotunisticInfectiousInitiatedDateDataDefinition();
		oppotunisticInfectiousInitiatedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		oppotunisticInfectiousInitiatedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		OppotunisticInfectiousCompletedDateDataDefinition oppotunisticInfectiousCompletedDateDataDefinition = new OppotunisticInfectiousCompletedDateDataDefinition();
		oppotunisticInfectiousCompletedDateDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		oppotunisticInfectiousCompletedDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		CommentDataDefinition commentDataDefinition = new CommentDataDefinition();
		commentDataDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		commentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        upn.getName(), upn), identifierFormatter);
		DataDefinition CCCidentifierDef = new ConvertedPatientDataDefinition("identifier",
		        new PatientIdentifierDataDefinition(upn.getName(), upn), new IdentifierConverter());
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Serial Number", new KpSerialNumberDataDefinition(), "");
		dsd.addColumn("Unique Identifier Code", identifierDef, "");
		dsd.addColumn("CCC Number", CCCidentifierDef, "");
		dsd.addColumn("Age", ageDef, "");
		dsd.addColumn("Key Population Type", kpType, paramMapping);
		dsd.addColumn("HIV Diagnosed Date", hivDiagnosedDateDataDefinition, paramMapping,
		    new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("Disclosed Status", disclosedStatusDataDefinition, paramMapping);
		dsd.addColumn("Person Disclosed To", personDisclosedToDataDefinition, paramMapping);
		dsd.addColumn("Peer Educator", nameOfPeerEducatorDataDefinition, paramMapping);
		dsd.addColumn("Art Start Date and Start Regimen", artStartDateDataDefinition, paramMapping);
		dsd.addColumn("Current ART Regimen", artCurrentRegimenDataDefinition, paramMapping);
		dsd.addColumn("Facility Accessing ART", facilityAccessingARTDataDefinition, paramMapping);
		dsd.addColumn("CD4 Count", cd4CountDataDefinition, paramMapping);
		dsd.addColumn("CD4 Date", cd4DateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("Viral Load", viralLoadDataDefinition, paramMapping);
		dsd.addColumn("Viral Load Date", viralLoadDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("IPT Started Month", iptStartedDateDataDefinition, paramMapping, new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("IPT Completed Month", iptCompletedDateDataDefinition, paramMapping, new DateConverter(
		        DATE_FORMAT_YYMM));
		dsd.addColumn("OI Diagnosed Date", oppotunisticInfectiousDiagnosedDateDataDefinition, paramMapping,
		    new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("OI Initiated Date", oppotunisticInfectiousInitiatedDateDataDefinition, paramMapping,
		    new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("OI Completed Date", oppotunisticInfectiousCompletedDateDataDefinition, paramMapping,
		    new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("Comment", commentDataDefinition, paramMapping);
		return dsd;
	}
}
