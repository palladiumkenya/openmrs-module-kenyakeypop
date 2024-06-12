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
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.ViralLoadResultCalculation;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.ObsDatetimeConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.ObsValueNumericConverter;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.RDQASimpleObjectRegimenConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.IdentifierConverter;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.defaulterTracing.BookingDateDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.defaulterTracing.FinalOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.defaulterTracing.MissedAppointmentTracingFinalOutcomeDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.otz.ARTStartDateRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.otz.CurrentARTRegimenDataDefinition;
import org.openmrs.module.kenyaemr.reporting.data.converter.definition.otz.TransitionDataDefinition;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardIndicatorLibrary;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.KvpLHIVTrackingRegisterCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.*;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.peerTrackingRegister.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.common.TimeQualifier;
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

//import org.openmrs.module.kenyakeypop.metadata.CommonMetadata;
;

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.cohort.analysis.lhivTrackerOffsite" })
public class KvpLHIVTrackingRegisterReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public static final String DATE_FORMAT_YYMM = "MM/yyyy";
	
	@Autowired
	private ETLMoh731GreenCardIndicatorLibrary moh731GreenCardIndicators;
	
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
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("lhivTrackerOffsite");
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		DataConverter ageFormatter = new ObjectFormatter("{age}");
		DataDefinition ageDef = new ConvertedPersonDataDefinition("age", new AgeDataDefinition(), ageFormatter);
		//PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
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
		dsd.addColumn("Key Population Type", new KeyPopTypeDataDefinition(), "");
		dsd.addColumn("CCC Number", CCCidentifierDef, "");
		dsd.addColumn("Age", ageDef, "");
		dsd.addColumn("Transition", new FinalTransitionOutcomeDataDefinition(), "");
		dsd.addColumn("Return to Care Date", new ReturnToCareDateDataDefinition(), "", new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("HIV Diagnosed Date", new HIVDiagnosedDateDataDefinition(), "", new DateConverter(DATE_FORMAT_YYMM));
		dsd.addColumn("Disclosed Status", new DisclosedStatusDataDefinition(), "");
		dsd.addColumn("Person Disclosed to", new PersonDisclosedToDataDefinition(), "");
		dsd.addColumn("Art Start Date and Start Regimen", new ARTStartDateRegimenDataDefinition(), "");
		dsd.addColumn("Current ART Regimen", new CurrentARTRegimenDataDefinition(), "");
		dsd.addColumn("Facility Accessing ART", new FacilityAccessingARTDataDefinition(), "");
		dsd.addColumn(
		    "First CD4 Count",
		    new ObsForPersonDataDefinition("First CD4 Count", TimeQualifier.FIRST, Dictionary
		            .getConcept(Dictionary.CD4_COUNT), null, null), "", new ObsValueNumericConverter(1));
		dsd.addColumn("First CD4 Count Date", new ObsForPersonDataDefinition("First CD4 Count Date", TimeQualifier.FIRST,
		        Dictionary.getConcept(Dictionary.CD4_COUNT), null, null), "", new ObsDatetimeConverter());
		dsd.addColumn("Recent Viral Load Result", new CalculationDataDefinition("Recent Viral Load Result",
		        new ViralLoadResultCalculation("last")), "", new RDQASimpleObjectRegimenConverter("data"));
		dsd.addColumn("Recent Viral Load Result Date", new CalculationDataDefinition("Recent Viral Load Result Date",
		        new ViralLoadResultCalculation("last")), "", new RDQASimpleObjectRegimenConverter("date"));
		dsd.addColumn("IPT Started Year", new IPTStartedDateDataDefinition(), "", new DateConverter(DATE_FORMAT_YYMM));
		
		return dsd;
	}
}
