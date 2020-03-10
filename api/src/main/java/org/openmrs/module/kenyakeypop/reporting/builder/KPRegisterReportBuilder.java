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

import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
//import org.openmrs.module.kenyakeypop.metadata.CommonMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.KPRegisterCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.*;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
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

;

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.cohort.analysis.kpRegister" })
public class KPRegisterReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return allClientsCohort();
	}
	
	protected Mapped<CohortDefinition> allClientsCohort() {
		CohortDefinition cd = new KPRegisterCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setName("KP Cohort Register");
		return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		
		PatientDataSetDefinition allClients = kpDataSetDefinition();
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
	
	protected PatientDataSetDefinition kpDataSetDefinition() {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("KPRegister");
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		// PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		//DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(upn.getName(), upn), identifierFormatter);
		
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Serial Number", new KpSerialNumberDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Hotspot", new HotspotDataDefinition(), "");
		dsd.addColumn("Hotspot Typology", new HotspotTypologyDataDefinition(), "");
		dsd.addColumn("Ward", new WardDataDefinition(), "");
		dsd.addColumn("Phone Number", new KpPhoneNumberDataDefinition(), "");
		//dsd.addColumn("Unique Identifier code", identifierDef, "");
		dsd.addColumn("Key Population Type", new KeyPopTypeDataDefinition(), "");
		dsd.addColumn("DOB", new BirthdateDataDefinition(), "");
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Date of First Contact", new DateOfFirstContactDataDefinition(), "");
		dsd.addColumn("Date of Enrollment", new DateOfEnrollmentDataDefinition(), "");
		dsd.addColumn("HIV Status at Enrollment", new HIVStatusAtEnrollmentDataDefinition(), "");
		dsd.addColumn("Peer Educator Code", new PeerEducatorCodeDataDefinition(), "");
		dsd.addColumn("Received Peer Education", new ReceivedPeerEducationDataDefinition(), "");
		dsd.addColumn("Provided with clinical services", new ProvidedWithClinicalServicesDataDefinition(), "");
		dsd.addColumn("Tested for HIV", new TestedForHIVDataDefinition(), "");
		dsd.addColumn("HTS delivery point", new HTSDeliveryPointDataDefinition(), "");
		dsd.addColumn("Frequency of HIV Test", new HIVTestFrequencyDataDefinition(), "");
		dsd.addColumn("HIV Status", new KpHIVStatusDataDefinition(), "");
		dsd.addColumn("HIV Self Test", new HIVSelfTestDataDefinition(), "");
		dsd.addColumn("Pre-ART", new PreARTDataDefinition(), "");
		dsd.addColumn("Started ART", new KpStartedARTDataDefinition(), "");
		dsd.addColumn("Currently on ART", new CurrentlyOnARTDataDefinition(), "");
		dsd.addColumn("Current care facility", new CurrentCareFacilityDataDefinition(), "");
		dsd.addColumn("HIV Care Outcome", new HIVCareOutcomeDataDefinition(), "");
		dsd.addColumn("ART Outcome", new ARTOutcomeDataDefinition(), "");
		dsd.addColumn("VL Done", new VLDoneDataDefinition(), "");
		dsd.addColumn("Due date for VL", new VLDueDateDataDefinition(), "");
		dsd.addColumn("Received VL results", new ReceivedVLResultsDataDefinition(), "");
		dsd.addColumn("Attained Viral Load Suppression", new VLSuppressedDataDefinition(), "");
		dsd.addColumn("Screened for TB", new ScreenedTBDataDefinition(), "");
		dsd.addColumn("Diagnosed with TB", new DiagnosedWithTBDataDefinition(), "");
		dsd.addColumn("Started on TB Treatment", new StartedTBTreatmentDataDefinition(), "");
		dsd.addColumn("Completed TB Treatment", new CompletedTBTreatmentDataDefinition(), "");
		dsd.addColumn("On IPT", new OnIPTDataDefinition(), "");
		dsd.addColumn("Reported HIV Exposure within 72 Hours", new ReportedHIVExposureWithin72HRSDataDefinition(), "");
		dsd.addColumn("Provided with PEP within 72 Hours", new ProvidedPEPWithin72HRSDataDefinition(), "");
		dsd.addColumn("Completed PEP", new CompletedPEPDataDefinition(), "");
		dsd.addColumn("Condom Requirements", new CondomRequirementsDataDefinition(), "");
		dsd.addColumn("Condom Distributed", new CondomDistributedDataDefinition(), "");
		dsd.addColumn("Provided Condoms as per Need", new ProvidedCondomsAsPerNeedDataDefinition(), "");
		dsd.addColumn("Lubricant Requirements", new LubricantRequirementsDataDefinition(), "");
		dsd.addColumn("Lubricant Distributed", new LubricantDistributedDataDefinition(), "");
		dsd.addColumn("Provided Lubricants as per Need", new ProvidedLubricantsAsPerNeedDataDefinition(), "");
		dsd.addColumn("Needles And Syringes Requirements", new NeedlesAndSyringesRequirementsDataDefinition(), "");
		dsd.addColumn("Needles And Syringes Distributed", new NeedlesAndSyringesDistributedDataDefinition(), "");
		dsd.addColumn("Received Needles And Syringes as per Need", new ReceivedNeedlesAndSyringessAsPerNeedDataDefinition(),
		    "");
		dsd.addColumn("Screened for HEP C", new ScreenedForHepCDataDefinition(), "");
		dsd.addColumn("HEP C status", new HEPCStatusDataDefinition(), "");
		dsd.addColumn("Treated for HEP C", new HEPCTreatedDataDefinition(), "");
		dsd.addColumn("Screened for HEP B", new ScreenedForHepBDataDefinition(), "");
		dsd.addColumn("HEP B status", new HEPBStatusDataDefinition(), "");
		dsd.addColumn("On HEP B Treatment", new OnHEPBTreatmentDataDefinition(), "");
		dsd.addColumn("HEP B Vaccination", new HEPBVaccinationDataDefinition(), "");
		dsd.addColumn("Screened for STIs", new ScreenedForSTIDataDefinition(), "");
		dsd.addColumn("Diagnosed with STIs", new DiagnosedWithSTIDataDefinition(), "");
		dsd.addColumn("Treated for STIs", new TreatedForSTIDataDefinition(), "");
		dsd.addColumn("Screened for Drug and Alcohol use", new ScreenedForDrugsAndAlcoholDataDefinition(), "");
		dsd.addColumn("Initiated PrEP", new InitiatedPrEPDataDefinition(), "");
		dsd.addColumn("Currently on PrEP", new CurrentlyOnPrEPDataDefinition(), "");
		dsd.addColumn("Provided modern FP Methods", new ProvidedModernFPMethodsDataDefinition(), "");
		dsd.addColumn("Provided with Risk Reduction Counselling", new RiskReductionCounsellingDataDefinition(), "");
		dsd.addColumn("Reached with EBI", new ReachedWithEBIDataDefinition(), "");
		dsd.addColumn("Experienced Violence", new ExperiencedViolenceDataDefinition(), "");
		dsd.addColumn("Received Post Violence support", new ReceivedPostViolenceSupportDataDefinition(), "");
		dsd.addColumn("Status in Program", new StatusInProgramDataDefinition(), "");
		dsd.addColumn("Next appointment Date", new AppointmentDateDataDefinition(), "");
		dsd.addColumn("Remarks", new RemarksDataDefinition(), "");
		
		return dsd;
	}
}
