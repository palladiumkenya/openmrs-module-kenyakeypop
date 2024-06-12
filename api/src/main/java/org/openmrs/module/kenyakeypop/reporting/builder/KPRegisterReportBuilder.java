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
import org.openmrs.module.kenyacore.report.data.patient.definition.CalculationDataDefinition;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.InitialArtStartDateCalculation;
import org.openmrs.module.kenyaemr.metadata.CommonMetadata;
import org.openmrs.module.kenyaemr.reporting.calculation.converter.DateArtStartDateConverter;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.KPRegisterCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.BirthdateConverter;
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
		cd.setName("KVP Activity Register");
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
		PatientIdentifierType nupi = MetadataUtils.existing(PatientIdentifierType.class,
		    CommonMetadata._PatientIdentifierType.NATIONAL_UNIQUE_PATIENT_IDENTIFIER);
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("KPRegister");
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        upn.getName(), upn), identifierFormatter);
		DataDefinition nupiDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        nupi.getName(), nupi), identifierFormatter);
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		HEPCTreatedDataDefinition hEPCTreatedDataDefinition = new HEPCTreatedDataDefinition();
		hEPCTreatedDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReceivedPeerEducationDataDefinition receivedPeerEducationDataDefinition = new ReceivedPeerEducationDataDefinition();
		receivedPeerEducationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ProvidedWithClinicalServicesDataDefinition providedWithClinicalServicesDataDefinition = new ProvidedWithClinicalServicesDataDefinition();
		providedWithClinicalServicesDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		TestedForHIVDataDefinition testedForHIVDataDefinition = new TestedForHIVDataDefinition();
		testedForHIVDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReportedHIVExposureWithin72HRSDataDefinition reportedHIVExposureWithin72HRSDataDefinition = new ReportedHIVExposureWithin72HRSDataDefinition();
		reportedHIVExposureWithin72HRSDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ProvidedPEPWithin72HRSDataDefinition providedPEPWithin72HRSDataDefinition = new ProvidedPEPWithin72HRSDataDefinition();
		providedPEPWithin72HRSDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForHepCDataDefinition screenedForHepCDataDefinition = new ScreenedForHepCDataDefinition();
		screenedForHepCDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		HEPCStatusDataDefinition hEPCStatusDataDefinition = new HEPCStatusDataDefinition();
		hEPCStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForHepBDataDefinition screenedForHepBDataDefinition = new ScreenedForHepBDataDefinition();
		screenedForHepBDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		HEPBStatusDataDefinition hEPBStatusDataDefinition = new HEPBStatusDataDefinition();
		hEPBStatusDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		OnHEPBTreatmentDataDefinition onHEPBTreatmentDataDefinition = new OnHEPBTreatmentDataDefinition();
		onHEPBTreatmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		HEPBVaccinationDataDefinition hEPBVaccinationDataDefinition = new HEPBVaccinationDataDefinition();
		hEPBVaccinationDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForSTIDataDefinition screenedForSTIDataDefinition = new ScreenedForSTIDataDefinition();
		screenedForSTIDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForDrugsAndAlcoholDataDefinition screenedForDrugsAndAlcoholDataDefinition = new ScreenedForDrugsAndAlcoholDataDefinition();
		screenedForDrugsAndAlcoholDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		InitiatedPrEPDataDefinition initiatedPrEPDataDefinition = new InitiatedPrEPDataDefinition();
		initiatedPrEPDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		CurrentlyOnPrEPDataDefinition currentlyOnPrEPDataDefinition = new CurrentlyOnPrEPDataDefinition();
		currentlyOnPrEPDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ProvidedModernFPMethodsDataDefinition providedModernFPMethodsDataDefinition = new ProvidedModernFPMethodsDataDefinition();
		providedModernFPMethodsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		RiskReductionCounsellingDataDefinition riskReductionCounsellingDataDefinition = new RiskReductionCounsellingDataDefinition();
		riskReductionCounsellingDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReachedWithEBIDataDefinition reachedWithEBIDataDefinition = new ReachedWithEBIDataDefinition();
		reachedWithEBIDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReceivedPostViolenceSupportDataDefinition receivedPostViolenceSupportDataDefinition = new ReceivedPostViolenceSupportDataDefinition();
		receivedPostViolenceSupportDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		AppointmentDateDataDefinition appointmentDateDataDefinition = new AppointmentDateDataDefinition();
		appointmentDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		VLDoneDataDefinition vLDoneDataDefinition = new VLDoneDataDefinition();
		vLDoneDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReceivedVLResultsDataDefinition receivedVLResultsDataDefinition = new ReceivedVLResultsDataDefinition();
		receivedVLResultsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedTBDataDefinition screenedTBDataDefinition = new ScreenedTBDataDefinition();
		screenedTBDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		DiagnosedWithTBDataDefinition diagnosedWithTBDataDefinition = new DiagnosedWithTBDataDefinition();
		diagnosedWithTBDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		StartedTBTreatmentDataDefinition startedTBTreatmentDataDefinition = new StartedTBTreatmentDataDefinition();
		startedTBTreatmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		CompletedTBTreatmentDataDefinition completedTBTreatmentDataDefinition = new CompletedTBTreatmentDataDefinition();
		completedTBTreatmentDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ReceivedVLResultsDateDataDefinition receivedVLResultsDateDataDefinition = new ReceivedVLResultsDateDataDefinition();
		AlcoholAndDrugAbuseDataDefinition alcoholAndDrugAbuseDataDefinition = new AlcoholAndDrugAbuseDataDefinition();
		alcoholAndDrugAbuseDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		TypeOfViolenceDataDefinition typeOfViolenceDataDefinition = new TypeOfViolenceDataDefinition();
		typeOfViolenceDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		receivedVLResultsDateDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForAnalCancerDataDefinition screenedForAnalCancerDataDefinition = new ScreenedForAnalCancerDataDefinition();
		screenedForAnalCancerDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForAnalCancerResultsDataDefinition screenedForAnalCancerResultsDataDefinition = new ScreenedForAnalCancerResultsDataDefinition();
		screenedForAnalCancerResultsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForCervicalCancerDataDefinition screenedForCervicalCancerDataDefinition = new ScreenedForCervicalCancerDataDefinition();
		screenedForCervicalCancerDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForMentalHealthDataDefinition screenedForMentalHealthDataDefinition = new ScreenedForMentalHealthDataDefinition();
		screenedForMentalHealthDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedTBResultsDataDefinition screenedTBResultsDataDefinition = new ScreenedTBResultsDataDefinition();
		screenedTBResultsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForHepCConfirmatoryPCRDataDefinition screenedForHepCConfirmatoryPCRDataDefinition = new ScreenedForHepCConfirmatoryPCRDataDefinition();
		screenedForHepCConfirmatoryPCRDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		ScreenedForHepBConfirmatoryResultsDataDefinition screenedForHepBConfirmatoryResultsDataDefinition = new ScreenedForHepBConfirmatoryResultsDataDefinition();
		screenedForHepBConfirmatoryResultsDataDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Serial Number", new KpSerialNumberDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Hotspot", new HotspotDataDefinition(), "");
		dsd.addColumn("Hotspot Typology", new HotspotTypologyDataDefinition(), "");
		dsd.addColumn("Sub County", new SubCountyOfImplementationDataDefinition(), "");
		dsd.addColumn("Ward", new WardDataDefinition(), "");
		dsd.addColumn("Phone Number", new KpPhoneNumberDataDefinition(), "");
		dsd.addColumn("UIC number", identifierDef, "");
		dsd.addColumn("NUPi", nupiDef, "");
		dsd.addColumn("Key Population Type", new KeyPopTypeDataDefinition(), "");
		dsd.addColumn("Date of Birth", new BirthdateDataDefinition(), "", new BirthdateConverter(DATE_FORMAT));
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Date of First Contact", new DateOfFirstContactDataDefinition(), "");
		dsd.addColumn("Date of Enrollment", new DateOfEnrollmentDataDefinition(), "");
		dsd.addColumn("HIV Status at Enrollment", new HIVStatusAtEnrollmentDataDefinition(), "");
		dsd.addColumn("Peer Educator Code", new PeerEducatorCodeDataDefinition(), "");
		
		dsd.addColumn("Received Peer Education", receivedPeerEducationDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Provided with clinical services", providedWithClinicalServicesDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Tested for HIV", testedForHIVDataDefinition, "endDate=${endDate}");
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
		dsd.addColumn("VL Done", vLDoneDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Due date for VL", new VLDueDateDataDefinition(), "");
		dsd.addColumn("Received VL results", receivedVLResultsDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Attained Viral Load Suppression", new VLSuppressedDataDefinition(), "");
		dsd.addColumn("Screened for TB", screenedTBDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Diagnosed with TB", diagnosedWithTBDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Started on TB Treatment", startedTBTreatmentDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Completed TB Treatment", completedTBTreatmentDataDefinition, "endDate=${endDate}");
		dsd.addColumn("On IPT", new OnIPTDataDefinition(), "");
		dsd.addColumn("Screened for Alcohol and Drug Abuse Results", alcoholAndDrugAbuseDataDefinition, "endDate=${endDate}");
		
		dsd.addColumn("Reported HIV Exposure within 72 Hours", reportedHIVExposureWithin72HRSDataDefinition,
		    "endDate=${endDate}");
		dsd.addColumn("Provided with PEP within 72 Hours", providedPEPWithin72HRSDataDefinition, "endDate=${endDate}");
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
		dsd.addColumn("Type of Violence", typeOfViolenceDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for HEP C", screenedForHepCDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for HEP C Confirmatory PCR", screenedForHepCConfirmatoryPCRDataDefinition,
		    "endDate=${endDate}");
		dsd.addColumn("HEP C status", hEPCStatusDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Treated for HEP C", hEPCTreatedDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for HEP B", screenedForHepBDataDefinition, "endDate=${endDate}");
		dsd.addColumn("HEP B status", hEPBStatusDataDefinition, "endDate=${endDate}");
		dsd.addColumn("On HEP B Treatment", onHEPBTreatmentDataDefinition, "endDate=${endDate}");
		dsd.addColumn("HEP B Vaccination", hEPBVaccinationDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for HEP B Confirmatory Results", screenedForHepBConfirmatoryResultsDataDefinition,
		    "endDate=${endDate}");
		dsd.addColumn("Screened for STIs", screenedForSTIDataDefinition, "endDate=${endDate}");
		dsd.addColumn("ART Start Date",
		    new CalculationDataDefinition("ART Start Date", new InitialArtStartDateCalculation()), "",
		    new DateArtStartDateConverter());
		dsd.addColumn("Screened for Anal Cancer", screenedForAnalCancerDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for Anal Cancer Results", screenedForAnalCancerResultsDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for Cervical Cancer", screenedForCervicalCancerDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for Mental Health", screenedForMentalHealthDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Screened for TB Results", screenedTBResultsDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Received VL Results Date", receivedVLResultsDateDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Diagnosed with STIs", new DiagnosedWithSTIDataDefinition(), "");
		dsd.addColumn("Treated for STIs", new TreatedForSTIDataDefinition(), "");
		dsd.addColumn("Screened for Drug and Alcohol use", screenedForDrugsAndAlcoholDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Initiated PrEP", initiatedPrEPDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Currently on PrEP", currentlyOnPrEPDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Provided modern FP Methods", providedModernFPMethodsDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Provided with Risk Reduction Counselling", riskReductionCounsellingDataDefinition,
		    "endDate=${endDate}");
		dsd.addColumn("Reached with EBI", reachedWithEBIDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Experienced Violence", new ExperiencedViolenceDataDefinition(), "");
		dsd.addColumn("Received Post Violence support", receivedPostViolenceSupportDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Status in Program", new StatusInProgramDataDefinition(), "");
		dsd.addColumn("Next appointment Date", appointmentDateDataDefinition, "endDate=${endDate}");
		dsd.addColumn("Remarks", new RemarksDataDefinition(), "");
		
		return dsd;
	}
}
