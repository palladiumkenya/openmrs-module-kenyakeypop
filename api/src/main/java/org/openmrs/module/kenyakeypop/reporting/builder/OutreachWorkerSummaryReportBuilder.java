/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
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
//import org.openmrs.module.kenyakeypop.metadata.CommonMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.OutreachWorkerCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.outreachworkersummary.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for ETL Outreach Worker Summary for Key Population
 */

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.outreachWorkerSummary" })
public class OutreachWorkerSummaryReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return allClientsCohort();
	}
	
	protected Mapped<CohortDefinition> allClientsCohort() {
		CohortDefinition cd = new OutreachWorkerCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setName("KP Outreach Worker Summary");
		return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		
		PatientDataSetDefinition allClients = peDataSetDefinition();
		allClients.addRowFilter(allClientsCohort());
		//allPatients.addRowFilter(buildCohort(descriptor));
		DataSetDefinition allPatientsDSD = allClients;
		
		return Arrays.asList(ReportUtils.map(allPatientsDSD, "startDate=${startDate},endDate=${endDate}"));
	}
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}
	
	protected PatientDataSetDefinition peDataSetDefinition() {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("OutreachWorkerSummary");
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		//PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		//DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
		
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Serial Number", new OutreachWorkerSerialNumberDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		//dsd.addColumn("Unique Identifier code", identifierDef, "");
		dsd.addColumn("DOB", new BirthdateDataDefinition(), "");
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Number of Peers Under PE", new NumberOfPeersUnderPEDataDefinition(), "");
		dsd.addColumn("Number of Peers Who Received Peer Education", new NumberOfPeersReceivedPeerEducationDataDefinition(),
		    "");
		dsd.addColumn("Condom Requirements Per Month", new CondomRequirementsPerMonthDataDefinition(), "");
		dsd.addColumn("Number of Male Condoms Distributed", new NumberOfMaleCondomsDistributedDataDefinition(), "");
		dsd.addColumn("Number of Female Condoms Distributed", new NumberOfFemaleCondomsDistributedDataDefinition(), "");
		dsd.addColumn("Lubricants Requirements Per Month", new LubesRequirementsPerMonthDataDefinition(), "");
		dsd.addColumn("Lubricants Distributed", new NumberOfLubesDistributedDataDefinition(), "");
		dsd.addColumn("Needles and Syringe Requirements Per Month", new NSRequirementsPerMonthDataDefinition(), "");
		dsd.addColumn("Needles and Syringe Distributed", new NumberOfNSDistributedDataDefinition(), "");
		dsd.addColumn("Number Referred To Clinic", new NumberReferredToClinicDataDefinition(), "");
		dsd.addColumn("Number of HIV Self Testing Kits Distributed",
		    new NumberOfHIVSelfTestingKitsDistributedDataDefinition(), "");
		dsd.addColumn("Number Visited the Clinic", new NumberVisitedClinicDataDefinition(), "");
		dsd.addColumn("Number Reported Violence", new NumberReportedViolenceDataDefinition(), "");
		dsd.addColumn("Number Addressed Violence", new NumberAddressedViolenceDataDefinition(), "");
		dsd.addColumn("Remarks", new KpRemarksDataDefinition(), "");
		
		return dsd;
	}
}
