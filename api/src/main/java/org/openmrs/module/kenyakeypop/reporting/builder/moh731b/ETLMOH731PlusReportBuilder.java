/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.builder.moh731b;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.MOH731BPlusSubCountyBasedCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.library.ETLReports.moh731B.ETLMoh731PlusIndicatorLibrary;
import org.openmrs.module.kenyakeypop.reporting.library.shared.common.CommonKpDimensionLibrary;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for ETL MOH 731B for Key Population
 */
@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.moh731b" })
public class ETLMOH731PlusReportBuilder extends AbstractReportBuilder {
	
	@Autowired
	private CommonKpDimensionLibrary commonDimensions;
	
	@Autowired
	private ETLMoh731PlusIndicatorLibrary moh731bIndicators;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String FSW = "FSW";
	
	public static final String MSM = "MSM";
	
	public static final String MSW = "MSW";
	
	public static final String PWID = "PWID";
	
	public static final String PWUD = "PWUD";
	
	public static final String TRANSGENDER = "Transgender";
	
	public static final String IN_PRISONS = "People in prison and other closed settings";
	
	ColumnParameters kp15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
	
	ColumnParameters kp20_to_24 = new ColumnParameters(null, "20-24", "age=20-24");
	
	ColumnParameters kp25_to_29 = new ColumnParameters(null, "25-29", "age=25-29");
	
	ColumnParameters kp30_and_above = new ColumnParameters(null, "30+", "age=30+");
	
	ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
	
	List<ColumnParameters> kpAgeDisaggregation = Arrays.asList(kp15_to_19, kp20_to_24, kp25_to_29, kp30_and_above, colTotal);
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("subCounty", "Sub County", String.class), new Parameter("dateBasedReporting", "",
		        String.class));
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(kpDataSet(), "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * KP Dataset
	 * 
	 * @return the dataset
	 */
	protected DataSetDefinition kpDataSet() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.setName("4");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.addParameter(new Parameter("location", "Sub County", String.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731BAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("KPType", ReportUtils.map(commonDimensions.kpType()));
		
		String indParams = "startDate=${startDate},endDate=${endDate},location=${location}";
		
		/**
		 * Active KPs disaggregated by KP type
		 */
		EmrReportingUtils.addRow(cohortDsd, "Active FSW", "", ReportUtils.map(moh731bIndicators.activeKPs(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active MSM", "", ReportUtils.map(moh731bIndicators.activeKPs(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active MSW", "", ReportUtils.map(moh731bIndicators.activeKPs(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active PWID", "",
		    ReportUtils.map(moh731bIndicators.activeKPs(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active PWUD", "",
		    ReportUtils.map(moh731bIndicators.activeKPs(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active Transgender", "",
		    ReportUtils.map(moh731bIndicators.activeKPs(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active prisoners and people in closed settings", "",
		    ReportUtils.map(moh731bIndicators.activeKPs(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * KPs tested for HIV disaggregated by KP type
		 */
		EmrReportingUtils.addRow(cohortDsd, "Tested FSW", "",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested MSM", "Tested MSM",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested MSW", "Tested MSW",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested PWID", "Tested PWID",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested PWUD", "Tested PWUD",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested Transgender", "Tested Transgender",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested prisoners and people in closed settings", "",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * KPs tested for HIV at the facility level disaggregated by KP type
		 */
		cohortDsd.addColumn("Tested_Facility_FSW", "Tested Facility Fsw",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(FSW), indParams), "");
		cohortDsd.addColumn("Tested_Facility_MSM", "Tested Facility Msm",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(MSM), indParams), "");
		cohortDsd.addColumn("Tested_Facility_MSW", "Tested Facility Msw",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(MSW), indParams), "");
		cohortDsd.addColumn("Tested_Facility_PWID", "Tested Facility Pwid",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(PWID), indParams), "");
		cohortDsd.addColumn("Tested_Facility_PWUD", "Tested Facility Pwud",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(PWUD), indParams), "");
		cohortDsd.addColumn("Tested_Facility_Transgender", "Tested Facility Transgender",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Tested_Facility_Prisoners_Closed_settings", "Prisoners & people in closed settings",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(IN_PRISONS), indParams), "");
		/**
		 * KPs tested for HIV at the community level disaggregated by KP type
		 */
		cohortDsd.addColumn("Tested_Community_FSW", "Tested Community Fsw",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(FSW), indParams), "");
		cohortDsd.addColumn("Tested_Community_MSM", "Tested Community Msm",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(MSM), indParams), "");
		cohortDsd.addColumn("Tested_Community_MSW", "Tested Community Msw",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(MSW), indParams), "");
		cohortDsd.addColumn("Tested_Community_PWID", "Tested Community Pwid",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(PWID), indParams), "");
		cohortDsd.addColumn("Tested_Community_PWUD", "Tested Community Pwud",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(PWUD), indParams), "");
		cohortDsd.addColumn("Tested_Community_Transgender", "Tested Community Transgender",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Tested_Community_Prisoners_Closed_Settings",
		    "Tested Community Prisoners & people in closed settings",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(IN_PRISONS), indParams), "");
		/**
		 * Tested new (1st testers): KPS newly tested for HIV disaggregated by KP type
		 */
		cohortDsd.addColumn("Tested_New_FSW", "Tested New Fsw",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(FSW), indParams), "");
		cohortDsd.addColumn("Tested_New_MSM", "Tested New Msm",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(MSM), indParams), "");
		cohortDsd.addColumn("Tested_New_MSW", "Tested New Msw",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(MSW), indParams), "");
		cohortDsd.addColumn("Tested_New_PWID", "Tested New Pwid",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(PWID), indParams), "");
		cohortDsd.addColumn("Tested_New_PWUD", "Tested New Pwud",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(PWUD), indParams), "");
		cohortDsd.addColumn("Tested_New_Transgender", "Tested New Transgender",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Tested_New_Prisoners_Closed_Settings", "Tested New Prisoners & people in closed settings",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(IN_PRISONS), indParams), "");
		
		/**
		 * Tested repeat:Clients with repeat HIV tests within the period
		 */
		cohortDsd.addColumn("Tested_Repeat_FSW", "Tested Repeat Fsw",
		    ReportUtils.map(moh731bIndicators.testedRepeat(FSW), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_MSM", "Tested Repeat Msm",
		    ReportUtils.map(moh731bIndicators.testedRepeat(MSM), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_MSW", "Tested Repeat Msw",
		    ReportUtils.map(moh731bIndicators.testedRepeat(MSW), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_PWID", "Tested Repeat Pwid",
		    ReportUtils.map(moh731bIndicators.testedRepeat(PWID), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_PWUD", "Tested Repeat Pwud",
		    ReportUtils.map(moh731bIndicators.testedRepeat(PWUD), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_Transgender", "Tested Repeat Transgender",
		    ReportUtils.map(moh731bIndicators.testedRepeat(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_Prisoners_Closed_Settings",
		    "Tested Repeat Prisoners & people in closed settings",
		    ReportUtils.map(moh731bIndicators.testedRepeat(IN_PRISONS), indParams), "");
		
		/**
		 * Self-tested for HIV: Number of clients who self tested for HIV within the reporting
		 * period
		 */
		cohortDsd.addColumn("Self_Tested_FSW", "Self Tested Fsw",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(FSW), indParams), "");
		cohortDsd.addColumn("Self_Tested_MSM", "Self Tested Msm",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(MSM), indParams), "");
		cohortDsd.addColumn("Self_Tested_MSW", "Self Tested Msw",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(MSW), indParams), "");
		cohortDsd.addColumn("Self_Tested_PWID", "Self Tested Pwid",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(PWID), indParams), "");
		cohortDsd.addColumn("Self_Tested_PWUD", "Self Tested Pwud",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(PWUD), indParams), "");
		cohortDsd.addColumn("Self_Tested_Transgender", "Self Tested Transgender",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Self_Tested_Prisoners_Closed_Settings", "Self Tested Prisoners & people in closed settings",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(IN_PRISONS), indParams), "");
		
		/**
		 * Known positives (Active) : number of individuals living with HIV among the active
		 * population within a programme
		 */
		cohortDsd.addColumn("Known_Positive_FSW", "Known Positive Fsw",
		    ReportUtils.map(moh731bIndicators.knownPositive(FSW), indParams), "");
		cohortDsd.addColumn("Known_Positive_MSM", "Known Positive Msm",
		    ReportUtils.map(moh731bIndicators.knownPositive(MSM), indParams), "");
		cohortDsd.addColumn("Known_Positive_MSW", "Known Positive Msw",
		    ReportUtils.map(moh731bIndicators.knownPositive(MSW), indParams), "");
		cohortDsd.addColumn("Known_Positive_PWID", "Known Positive Pwid",
		    ReportUtils.map(moh731bIndicators.knownPositive(PWID), indParams), "");
		cohortDsd.addColumn("Known_Positive_PWUD", "Known Positive Pwud",
		    ReportUtils.map(moh731bIndicators.knownPositive(PWUD), indParams), "");
		cohortDsd.addColumn("Known_Positive_Transgender", "Known Positive Transgender",
		    ReportUtils.map(moh731bIndicators.knownPositive(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Known_Positive_Prisoners_Closed_Settings",
		    "Known Positive Prisoners and People in closed settings",
		    ReportUtils.map(moh731bIndicators.knownPositive(IN_PRISONS), indParams), "");
		/**
		 * 2.2 Receiving positive results: clients who tested positive and were made aware of their
		 * HIV positive result after the test.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_FSW", "Received Positive Results Fsw",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_MSM", "Received Positive Results Msm",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_MSW", "Received Positive Results Msw",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_PWID", "Received Positive Results Pwid",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_PWUD", "Received Positive Results Pwud",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_Transgender",
		    "Received Positive Results Transgender",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_Prisoners_Closed_Settings",
		    "Received Positive Results Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * 2.3 HIV positive 3 months ago linked to care/treatment. This set of data elements refers
		 * to patients who were diagnosed HIV positive three months ago and have been linked to
		 * care/ treatment.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Linked_FSW", "HIV+ 3 Months ago and Linked Fsw",
		    ReportUtils.map(moh731bIndicators.linked(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_MSM", "HIV+ 3 Months ago and Linked Msm",
		    ReportUtils.map(moh731bIndicators.linked(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_MSW", "HIV+ 3 Months ago and Linked Msw",
		    ReportUtils.map(moh731bIndicators.linked(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_PWID", "HIV+ 3 Months ago and Linked Pwid",
		    ReportUtils.map(moh731bIndicators.linked(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_PWUD", "HIV+ 3 Months ago and Linked Pwud",
		    ReportUtils.map(moh731bIndicators.linked(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_Transgender", "HIV+ 3 Months ago and Linked Transgender",
		    ReportUtils.map(moh731bIndicators.linked(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_Prisoners_Closed_Settings",
		    "HIV+ 3 Months ago and Linked Prisoners and closed settings",
		    ReportUtils.map(moh731bIndicators.linked(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Total positive 3 months ago: Total number of KPs in each KP type who were HIV positive
		 * three months ago.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_FSW", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_MSM", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_MSW", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_PWID", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_PWUD", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_Transgender", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Total_Positive_3_Months_Ago_Prisoners_Closed_Settings",
		    "HIV+ 3 Months ago and.positiveMonthsAgo Prisoners and closed settings",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Receiving condoms
		 */
		cohortDsd.addColumn("Receiving_Condoms_FSW", "Receiving Condoms Fsw",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_MSM", "Receiving Condoms Msm",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_MSW", "Receiving Condoms Msw",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_PWID", "Receiving Condoms Pwid",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_PWUD", "Receiving Condoms Pwud",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Transgender", "Receiving Condoms Transgender",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Prisoners_closed_settings",
		    "Receiving Condoms Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving condoms per need: number of individuals in each KP type who received
		 * condoms based on their requirements derived from estimated number of sex acts per month
		 */
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_FSW", "Receiving Condoms Per need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_MSM", "Receiving Condoms Per need Msm",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_MSW", "Receiving Condoms Per need Msw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_PWID", "Receiving Condoms Per need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_PWUD", "Receiving Condoms Per need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_Transgender", "Receiving Condoms Per need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_Prisoners_closed_settings",
		    "Receiving Condoms Per need Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving needles & syringes: number of individuals in each KP type who received
		 * at least one needle & syringe, irrespective of service provision point.
		 */
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_FSW", "Receiving needles and syringes Fsw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_MSM", "Receiving needles and syringes Msm",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_MSW", "Receiving needles and syringes Msw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_PWID", "Receiving needles and syringes Pwid",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_PWUD", "Receiving needles and syringes Pwud",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Transgender", "Receiving needles and syringes Transgender",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(IN_PRISONS), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving needles and syringes",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving needles & syringes per need number of individuals in each KP type who
		 * received needles & syringes based on their requirements derived from estimated number of
		 * injecting episodes per month.
		 */
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_FSW", "Receiving needs & syringes per need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_MSM", "Receiving needs & syringes per need Msm",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_MSW", "Receiving needs & syringes per need Msw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_PWID", "Receiving needs & syringes per need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_PWUD", "Receiving needs & syringes per need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_Transgender",
		    "Receiving needs & syringes per need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(IN_PRISONS), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving needs & syringes per need",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving lubricants Number of individuals in each KP type who received lubricants
		 * based on their requirements.
		 */
		cohortDsd.addColumn("Receiving_Lubricants_FSW", "Receiving Lubricants Fsw",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_MSM", "Receiving Lubricants Msm",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_MSW", "Receiving Lubricants Msw",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_PWID", "Receiving Lubricants Pwid",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_PWUD", "Receiving Lubricants Pwud",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Transgender", "Receiving Lubricants Transgender",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving Lubricants",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving lubricants per need Number of individuals in each KP type who received
		 * lubricants based on their requirements
		 */
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_FSW", "Receiving Lubricants Per Need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_MSM", "Receiving Lubricants Per Need Msm",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_MSW", "Receiving Lubricants Per Need Msw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_PWID", "Receiving Lubricants Per Need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_PWUD", "Receiving Lubricants Per Need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_Transgender", "Receiving Lubricants Per Need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving Lubricants per need",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(IN_PRISONS), indParams), "");
		
		/**
		 * Number receiving self-test kits: number of individuals in each KP type who received an
		 * HIV self-test kit in the reporting period.
		 */
		cohortDsd.addColumn("Receiving_Self_Test_Kits_FSW", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_MSM", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_MSW", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_PWID", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_PWUD", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_Transgender", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(IN_PRISONS), indParams), "");
		
		// 4.1 STI screening
		
		/**
		 * Number screened_STI number of individuals in each KP type who were screened for STI in
		 * the reporting period.
		 */
		cohortDsd.addColumn("STI_Screening_FSW", "STI Screening Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(FSW), indParams), "");
		cohortDsd.addColumn("STI_Screening_MSM", "STI Screening Msm",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(MSM), indParams), "");
		cohortDsd.addColumn("STI_Screening_MSW", "STI Screening Msw",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(MSW), indParams), "");
		cohortDsd.addColumn("STI_Screening_PWID", "STI Screening  Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(PWID), indParams), "");
		cohortDsd.addColumn("STI_Screening_PWUD", "STI Screening Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(PWUD), indParams), "");
		cohortDsd.addColumn("STI_Screening_Transgender", "STI Screening Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("STI_Screening_Prisoners_closed_settings",
		    "Prisoners and people in closed settings screening for STI",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(IN_PRISONS), indParams), "");
		/**
		 * Diagnosed_STI: number of individuals in each KP type who were diagnosed with STI in the
		 * reporting period.
		 */
		cohortDsd.addColumn("STI_Diagnosed_FSW", "MSM diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(FSW), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_MSM", "Msm diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(MSM), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_MSW", "Msw diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(MSW), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_PWID", "Pwid diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(PWID), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_PWUD", "Pwud diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(PWUD), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_Transgender", "Transgenders diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("STI_Diagnosed_Prisoners_closed_settings",
		    "Prisoners and people in closed settings diagnosed with STI",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(IN_PRISONS), indParams), "");
		
		/**
		 * Treated_STI: number of individuals in each KP type who were treated for STI in the
		 * reporting period.
		 */
		cohortDsd.addColumn("STI_Treated_FSW", "FSW treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(FSW), indParams), "");
		cohortDsd.addColumn("STI_Treated_MSM", "MSM treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(MSM), indParams), "");
		cohortDsd.addColumn("STI_Treated_MSW", "MSW treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(MSW), indParams), "");
		cohortDsd.addColumn("STI_Treated_PWID", "PWID treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(PWID), indParams), "");
		cohortDsd.addColumn("STI_Treated_PWUD", "PWUD treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(PWUD), indParams), "");
		cohortDsd.addColumn("STI_Treated_Transgender", "Transgender treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("STI_Treated_Prisoners_closed_settings",
		    "Prisoners and people in closed settings Treated for STI",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(IN_PRISONS), indParams), "");
		
		//Screened for HCV
		/**
		 * Number screened_HCV: number of individuals in each KP type who were screened for HCV in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Screened_HCV_FSW", "HCV Screening Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(FSW), indParams), "");
		cohortDsd.addColumn("Screened_HCV_MSM", "HCV Screening Msm",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(MSM), indParams), "");
		cohortDsd.addColumn("Screened_HCV_MSW", "HCV Screening Msw",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(MSW), indParams), "");
		cohortDsd.addColumn("Screened_HCV_PWID", "HCV Screening Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(PWID), indParams), "");
		cohortDsd.addColumn("Screened_HCV_PWUD", "HCV Screening Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(PWUD), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Transgender", "HCV Screening Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings",
		    "HCV Screened Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(IN_PRISONS), indParams), "");
		/**
		 * Positive_HCV: number of individuals in each KP type who were diagnosed with HCV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Positive_HBV_FSW", "HBV Positive Fsw",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(FSW), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSM", "HBV Positive Msm",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(MSM), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSW", "HBV Positive Msw",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(MSW), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWID", "HBV Positive Pwid",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(PWID), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWUD", "HBV Positive Pwud",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(PWUD), indParams), "");
		cohortDsd.addColumn("Positive_HBV_Transgender", "HBV Positive Transgender",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings",
		    "HBV Positive Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(IN_PRISONS), indParams), "");
		
		/**
		 * Treated_HCV: number of individuals in each KP type who were treated for HCV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Treated_HCV_FSW", "HCV Treated Fsw",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(FSW), indParams), "");
		cohortDsd.addColumn("Treated_HCV_MSM", "HCV Treated Msm",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(MSM), indParams), "");
		cohortDsd.addColumn("Treated_HCV_MSW", "HCV Treated Msw",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(MSW), indParams), "");
		cohortDsd.addColumn("Treated_HCV_PWID", "HCV Treated Pwid",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(PWID), indParams), "");
		cohortDsd.addColumn("Treated_HCV_PWUD", "HCV Treated Pwud",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(PWUD), indParams), "");
		cohortDsd.addColumn("Treated_HCV_Transgender", "HCV Treated Transgender",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings", "Prisoners and people in closed settings HCV treated",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(IN_PRISONS), indParams), "");
		
		//4.3 HBV (Hepatitis B)
		/**
		 * Number screened_HBV: number of individuals in each KP type who were screened for HBV in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Screened_HBV_FSW", "HBV Screened Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(FSW), indParams), "");
		cohortDsd.addColumn("Screened_HBV_MSM", "HBV Screened Msm",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(MSM), indParams), "");
		cohortDsd.addColumn("Screened_HBV_MSW", "HBV Screened Msw",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(MSW), indParams), "");
		cohortDsd.addColumn("Screened_HBV_PWID", "HBV Screened Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(PWID), indParams), "");
		cohortDsd.addColumn("Screened_HBV_PWUD", "HBV Screened Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(PWUD), indParams), "");
		cohortDsd.addColumn("Screened_HBV_Transgender", "HBV Screened Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings",
		    "HBV screened Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(IN_PRISONS), indParams), "");
		
		/**
		 * Positive_HBV: number of individuals in each KP type who were diagnosed with HBV in the
		 * reporting period
		 */
		cohortDsd.addColumn("Positive_HBV_FSW", "HBV Positive Fsw",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(FSW), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSM", "HBV Positive Msm",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(MSM), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSW", "HBV Positive Msw",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(MSW), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWID", "HBV Positive Pwid",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(PWID), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWUD", "HBV Positive Pwud",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(PWUD), indParams), "");
		cohortDsd.addColumn("Positive_HBV_Transgender", "HBV Positive Transgender",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings",
		    "HBV Positive Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(IN_PRISONS), indParams), "");
		
		/**
		 * Treated_HBV: number of individuals in each KP type who were treated for HBV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Treated_HBV_FSW", "HBV Treated Fsw",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(FSW), indParams), "");
		cohortDsd.addColumn("Treated_HBV_MSM", "HBV Treated Msm",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(MSM), indParams), "");
		cohortDsd.addColumn("Treated_HBV_MSW", "HBV Treated Msw",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(MSW), indParams), "");
		cohortDsd.addColumn("Treated_HBV_PWID", "HBV Treated Pwid",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(PWID), indParams), "");
		cohortDsd.addColumn("Treated_HBV_PWUD", "HBV Treated Pwud",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(PWUD), indParams), "");
		cohortDsd.addColumn("Treated_HBV_Transgender", "HBV Treated Transgender",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings", "HBV treated Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(IN_PRISONS), indParams), "");
		
		/**
		 * Negative_HBV_vaccinated: number of individuals in each KP type who were vaccinated for
		 * HBV in the reporting period
		 */
		cohortDsd.addColumn("NegativeHBV_Vaccinated_FSW", "Negative HBV Vaccinated Fsw",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(FSW), indParams), "");
		cohortDsd.addColumn("NegativeHBV_Vaccinated_MSM", "Negative HBV Vaccinated Msm",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(MSM), indParams), "");
		cohortDsd.addColumn("NegativeHBV_Vaccinated_MSW", "Negative HBV Vaccinated Msw",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(MSW), indParams), "");
		cohortDsd.addColumn("NegativeHBV_Vaccinated_PWID", "Negative HBV Vaccinated Pwid",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(PWID), indParams), "");
		cohortDsd.addColumn("NegativeHBV_Vaccinated_PWUD", "Negative HBV Vaccinated Pwud",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(PWUD), indParams), "");
		cohortDsd.addColumn("NegativeHBV_Vaccinated_Transgender", "Negative HBV Vaccinated Transgender",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(IN_PRISONS), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Prisoners_closed_settings",
		    "Negative HBV Vaccinated Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(IN_PRISONS), indParams), "");
		
		//4.4 TB screening
		/**
		 * Number screened: number of individuals in each KP type who were screened for TB in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Screened_TB_FSW", "Screened TB Fsw",
		    ReportUtils.map(moh731bIndicators.screenedTB(FSW), indParams), "");
		cohortDsd.addColumn("Screened_TB_MSM", "Screened TB Msm",
		    ReportUtils.map(moh731bIndicators.screenedTB(MSM), indParams), "");
		cohortDsd.addColumn("Screened_TB_MSW", "Screened TB Msw",
		    ReportUtils.map(moh731bIndicators.screenedTB(MSW), indParams), "");
		cohortDsd.addColumn("Screened_TB_PWID", "Screened TB Pwid",
		    ReportUtils.map(moh731bIndicators.screenedTB(PWID), indParams), "");
		cohortDsd.addColumn("Screened_TB_PWUD", "Screened TB Pwud",
		    ReportUtils.map(moh731bIndicators.screenedTB(PWUD), indParams), "");
		cohortDsd.addColumn("Screened_TB_Transgender", "Screened TB Transgender",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Screened_TB_Prisoners_closed_settings", "Screened TB Prisoners and people in closed settings",
		    ReportUtils.map(moh731bIndicators.screenedTB(IN_PRISONS), indParams), "");
		
		/**
		 * Number diagnosed: number of individuals in each KP type who were screened for TB in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Diagnosed_TB_FSW", "Diagnosed with TB Fsw",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(FSW), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_MSM", "Diagnosed with TB Msm",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(MSM), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_MSW", "Diagnosed with TB Msw",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(MSW), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_PWID", "Diagnosed with TB Pwid",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(PWID), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_PWUD", "Diagnosed with TB Pwud",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(PWUD), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_Transgender", "Diagnosed with TB Transgender",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_Prisoners_closed_settings",
		    "Prisoners and people in closed settings diagnosed with TB",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(IN_PRISONS), indParams), "");
		
		/**
		 * Number Started on TB TX: number of individuals in each KP type who were found positive
		 * with TB and started on treatment in the reporting period
		 */
		cohortDsd.addColumn("Started_TB_Tx_FSW", "Started TB Tx Fsw",
		    ReportUtils.map(moh731bIndicators.startedTBTX(FSW), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_MSM", "Started TB Tx Msm",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSM), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_MSW", "Started TB Tx Msw",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSW), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_PWID", "Started TB Tx Pwid",
		    ReportUtils.map(moh731bIndicators.startedTBTX(PWID), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_PWUD", "Started TB Tx Pwud",
		    ReportUtils.map(moh731bIndicators.startedTBTX(PWUD), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_Transgender", "Started TB Tx Transgender",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_Prisoners_closed_settings",
		    "Prisoners and people in closed settings Started TB Tx",
		    ReportUtils.map(moh731bIndicators.startedTBTX(IN_PRISONS), indParams), "");
		
		/**
		 * TB clients on HAART: This is the count of TB patients who are receiving ART in each KP
		 * type. Calculate and enter the sum of all those TB patients who were already on HAART at
		 * the time of TB diagnosis and TB patients diagnosed with HIV who are started on HAART
		 * during the reporting month.
		 */
		cohortDsd.addColumn("Started_TB_Tx_FSW", "Started TB Tx Fsw",
		    ReportUtils.map(moh731bIndicators.startedTBTX(FSW), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_MSM", "Started TB Tx Msm",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSM), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_MSW", "Started TB Tx Msw",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSW), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_PWID", "Started TB Tx Pwid",
		    ReportUtils.map(moh731bIndicators.startedTBTX(PWID), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_PWUD", "Started TB Tx Pwud",
		    ReportUtils.map(moh731bIndicators.startedTBTX(PWUD), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_Transgender", "Started TB Tx Transgender",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_Prisoners_closed_settings",
		    "Prisoners and people in closed settings Started TB Tx",
		    ReportUtils.map(moh731bIndicators.startedTBTX(IN_PRISONS), indParams), "");
		
		//4.5 PrEP
		/**
		 * Initiated PrEP: number of HIV negative persons in each KP type who have been started on
		 * PrEP during the reporting month after meeting the eligibility criteria for PrEP.
		 */
		cohortDsd.addColumn("Initiated_PrEP_FSW", "Initiated PrEP Fsw",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(FSW), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_MSM", "Initiated PrEP Msm",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(MSM), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_MSW", "Initiated PrEP Msw",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(MSW), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_PWID", "Initiated PrEP Pwid",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(PWID), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_PWUD", "Initiated PrEP Pwud",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(PWUD), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_Transgender", "Initiated PrEP Transgender",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_Prisoners_closed_settings",
		    "Prisoners and people in closed settings Initiated PrEP",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(IN_PRISONS), indParams), "");
		/**
		 * Current on PrEP: number of people in each KP type who are currently receiving PrEP. This
		 * includes persons newly started on PrEP in the reporting month, persons who come for their
		 * PrEP refills in the reporting month, and those who do not come for medicine in the
		 * reporting month but have been given PrEP medications to take them through the reporting
		 * month
		 */
		cohortDsd.addColumn("Current_on_PrEP_FSW", "Current on PrEP Fsw",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(FSW), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_MSM", "Current on PrEP Msm",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(MSM), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_MSW", "Current on PrEP Msw",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(MSW), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_PWID", "Current on PrEP Pwid",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(PWID), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_PWUD", "Current on PrEP Pwud",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(PWUD), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_Transgender", "Current on PrEP Transgender",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_Prisoners_closed_settings",
		    "Prisoners and people in closed settings current on PrEP",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(IN_PRISONS), indParams), "");
		/**
		 * Turning HIV positive while on PrEP: number of people on PrEP in each KP type who tested
		 * positive for HIV in the reporting period.
		 */
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_FSW", "Turning HIV+ While on PrEP Fsw",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(FSW), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_MSM", "Turning HIV+ While on PrEP Msm",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(MSM), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_MSW", "Turning HIV+ While on PrEP Msw",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(MSW), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_PWID", "Turning HIV+ While on PrEP Pwid",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(PWID), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_PWUD", "Turning HIV+ While on PrEP Pwud",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(PWUD), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_Transgender", "Turning HIV+ While on PrEP Transgender",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_Prisoners_closed_settings",
		    "Prisoners and people in closed settings turning Positive while on PrEP",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(IN_PRISONS), indParams), "");
		
		//4.6 Violence support
		/**
		 * Experience violence: number of people in each KP type who experienced violence in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Experiencing_Violence_FSW", "Experiencing Violence Fsw",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(FSW), indParams), "");
		cohortDsd.addColumn("Experiencing_Violence_MSM", "Experiencing Violence Msm",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(MSM), indParams), "");
		cohortDsd.addColumn("Experiencing_Violence_MSW", "Experiencing Violence Msw",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(MSW), indParams), "");
		cohortDsd.addColumn("Experiencing_Violence_PWID", "Experiencing Violence Pwid",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(PWID), indParams), "");
		cohortDsd.addColumn("Experiencing_Violence_PWUD", "Experiencing Violence Pwud",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(PWUD), indParams), "");
		cohortDsd.addColumn("Experiencing_Violence_Transgender", "Experiencing Violence Transgender",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_Prisoners_closed_settings",
		    "Prisoners and people in closed settings turning Positive while on PrEP",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(IN_PRISONS), indParams), "");
		/**
		 * Receiving violence support:Number of people in each KP type who were supported by the
		 * programme when they experienced violence in the reporting period by KP type
		 */
		cohortDsd.addColumn("Receiving_Violence_Support_FSW", "Receiving Violence Support Fsw",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_MSM", "Receiving Violence Support Msm",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_MSW", "Receiving Violence Support Msw",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_PWID", "Receiving Violence Support Pwid",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_PWUD", "Receiving Violence Support Pwud",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_Transgender", "Receiving Violence Support Transgender",
		    ReportUtils.map(moh731bIndicators.receivingViolenceSupport(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_Violence_Support_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving violence support",
		    ReportUtils.map(moh731bIndicators.experiencingViolence(IN_PRISONS), indParams), "");
		
		//4.7 PEP
		/**
		 * Number exposed: Number of people in each KP type who reported that they were exposed to
		 * HIV within 72 hours of exposure.
		 */
		cohortDsd.addColumn("Number_Exposed_To_HIV_FSW", "Number Exposed Fsw",
		    ReportUtils.map(moh731bIndicators.numberExposed(FSW), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_MSM", "Number Exposed Msm",
		    ReportUtils.map(moh731bIndicators.numberExposed(MSM), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_MSW", "Number Exposed Msw",
		    ReportUtils.map(moh731bIndicators.numberExposed(MSW), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_PWID", "Number Exposed Pwid",
		    ReportUtils.map(moh731bIndicators.numberExposed(PWID), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_PWUD", "Number Exposed Pwud",
		    ReportUtils.map(moh731bIndicators.numberExposed(PWUD), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_Transgender", "Number Exposed Transgender",
		    ReportUtils.map(moh731bIndicators.numberExposed(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_Prisoners_closed_settings",
		    "Prisoners and people in closed settings exposed to HIV",
		    ReportUtils.map(moh731bIndicators.numberExposed(IN_PRISONS), indParams), "");
		/**
		 * Number receiving PEP<72hrs: Number of people in each KP type who were initiated on PEP
		 * within 72 hours of HIV exposure.
		 */
		/*cohortDsd.addColumn("Receiving_PEP_Within_72hrs_FSW", "Receiving PEP Within 72 hours Fsw",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(FSW), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_MSM", "Receiving PEP Within 72 hours Msm",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(MSM), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_MSW", "Receiving PEP Within 72 hours Msw",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(MSW), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_PWID", "Receiving PEP Within 72 hours Pwid",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(PWID), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_PWUD", "Receiving PEP Within 72 hours Pwud",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(PWUD), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_Transgender", "Receiving PEP Within 72 hours Transgender",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_Prisoners_closed_settings",
		    "Prisoners and people in closed settings receiving PEP within 72 hrs",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(IN_PRISONS), indParams), "");*/
		/**
		 * Number completed PEP within 28 days: Number of people in each KP type who completed
		 * taking PEP within 28 days.
		 */
		/*	cohortDsd.addColumn("Completed_PEP_Within_28_Days_FSW", "Completed PEP within 28 days Fsw", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(FSW), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSM", "Completed PEP within 28 days Msm", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(MSM), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSW", "Completed PEP within 28 days Msw", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(MSW), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWID", "Completed PEP within 28 days Pwid", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(PWID), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWUD", "Completed PEP within 28 days Pwud", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(PWUD), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_Transgender", "Completed PEP within 28 days Transgender", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(TRANSGENDER), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_Prisoners_closed_settings", "Prisoners and people in closed settings completing PEP within 72 hrs", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(IN_PRISONS), indParams),"");
		*/
		//4.8 Peer education
		/**
		 * Receiving peer education: Number of people in each KP type who received peer education in
		 * the reporting period.
		 */
		/*	cohortDsd.addColumn("Received_Peer_education_FSW", "Received peer education Fsw", ReportUtils.map(moh731bIndicators.receivedPeerEducation(FSW), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_MSM", "Received peer education Msm", ReportUtils.map(moh731bIndicators.receivedPeerEducation(MSM), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_MSW", "Received peer education Msw", ReportUtils.map(moh731bIndicators.receivedPeerEducation(MSW), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_PWID", "Received peer education Pwid", ReportUtils.map(moh731bIndicators.receivedPeerEducation(PWID), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_PWUD", "Received peer education Pwud", ReportUtils.map(moh731bIndicators.receivedPeerEducation(PWUD), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_Transgender", "Received peer education Transgender", ReportUtils.map(moh731bIndicators.receivedPeerEducation(TRANSGENDER), indParams),"");
			cohortDsd.addColumn("Received_Peer_education_Prisoners_closed_settings", "Prisoners and people in closed settings received peer education", ReportUtils.map(moh731bIndicators.receivedPeerEducation(IN_PRISONS), indParams),"");
		*/
		// 5.0 Care and treatment
		
		/**
		 * 5.1 Current on Pre-ART On site On pre-ART disaggregated by age 15-19, 20-24,25-29,30+
		 * number of people of each KP type who were enrolled in care this month or in a previous
		 * month and made a clinical visit on site in preparation for ART but have not started on
		 * ART during this visit. They should be counted only if there is no intention to start them
		 * on ART during the reporting month in this site.
		 */
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_FSW", "On site pre-ART Fsw",
		    ReportUtils.map(moh731bIndicators.onSitePreART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_MSM", "On site pre-ART Msm",
		    ReportUtils.map(moh731bIndicators.onSitePreART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_MSW", "On site pre-ART Msw",
		    ReportUtils.map(moh731bIndicators.onSitePreART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_PWID", "On pre-ART Pwid",
		    ReportUtils.map(moh731bIndicators.onSitePreART(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_PWUD", "On pre-ART Pwud",
		    ReportUtils.map(moh731bIndicators.onSitePreART(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_Transgender", "On pre-ART Transgender",
		    ReportUtils.map(moh731bIndicators.onSitePreART(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_Prisoners_closed_settings",
		    "On pre-ART people in closed settings received peer education",
		    ReportUtils.map(moh731bIndicators.onSitePreART(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * 5.1 Current on Pre-ART Off site Number of people of each KP type who were enrolled in
		 * care this month or in a previous month and made a clinical visit off site in preparation
		 * for ART but have not started on ART during this visit. They should be counted only if
		 * there is no intention to start them on ART during the reporting month in another site.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_FSW", "Off site pre-ART Fsw",
		    ReportUtils.map(moh731bIndicators.offSitePreART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_MSM", "Off site pre-ART Msm",
		    ReportUtils.map(moh731bIndicators.offSitePreART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_MSW", "Off site pre-ART Msw",
		    ReportUtils.map(moh731bIndicators.offSitePreART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_PWID", "Off site pre-ART Pwid",
		    ReportUtils.map(moh731bIndicators.offSitePreART(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_PWUD", "Off site pre-ART Pwud",
		    ReportUtils.map(moh731bIndicators.offSitePreART(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_Transgender", "Off site pre-ART Transgender",
		    ReportUtils.map(moh731bIndicators.offSitePreART(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_Prisoners_closed_settings",
		    "Off site pre-ART people in closed settings received peer education",
		    ReportUtils.map(moh731bIndicators.offSitePreART(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Total on Pre-ART
		 */
		cohortDsd.addColumn("Total_pre_ART_FSW", "Fsw total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(FSW), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_MSM", "Msm total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(MSM), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_MSW", "Msw total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(MSW), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_PWID", "Pwid total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(PWID), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_PWUD", "Pwud total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(PWUD), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_Transgender", "Transgender total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_Prisoners_closed_settings",
		    "Prisoners and people in closed settings total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(IN_PRISONS), indParams), "");
		/**
		 * 5.2 Starting ART On site Number of people of each KP type starting HAART for treatment at
		 * this site within the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_FSW", "Fsw starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_MSM", "Msm starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_MSW", "Msw starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_PWID", "Pwid starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_PWUD", "Pwud starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_Transgender",
		    "Transgender starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_Prisoners_closed_settings",
		    "People in prisons and closed settings starting ART within the period on site ",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		/**
		 * 5.2 Starting ART Off site Number of people of each KP type starting HAART for treatment
		 * off site within the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_FSW", "Fsw starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_MSM", "Msm starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_MSW", "Msw starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_PWID", "Pwid starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_PWUD", "Pwud starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_Transgender",
		    "Transgender starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_Prisoners_closed_settings",
		    "People in prisons and closed settings starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Total Starting ART
		 */
		cohortDsd.addColumn("Total_starting_ART_FSW", "Fsw total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(FSW), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_MSM", "Msm total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(MSM), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_MSW", "Msw total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(MSW), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_PWID", "Pwid total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(PWID), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_PWUD", "Pwud total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(PWUD), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_Transgender", "Transgender total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_Prisoners_closed_settings",
		    "Prisoners and people in closed settings total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(IN_PRISONS), indParams), "");
		
		/**
		 * 5.3 Current ART On site Number of people in each KP type who: 1. started therapy on site
		 * this month or 2. started therapy on site before this month but made a visit to collect
		 * drugs this month or 3. started therapy on site before this month but did not make a visit
		 * to the facility during this month because they had been given enough drugs during visits
		 * before this month to cover the reporting month.
		 */
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_FSW",
		    "Fsw currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_MSM",
		    "Msm currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_MSW",
		    "Msw currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_PWID",
		    "Pwid currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_PWUD",
		    "Pwud currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_Transgender",
		    "Transgender currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_Prisoners_closed_settings",
		    "People in prisons and closed settings currently on ART on site within the period ",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * 5.3 Current ART Off site Number of people in each KP type who: 1. started therapy this
		 * month off site or 2. started therapy off site before this month but visited the offsite
		 * clinic to collect drugs this month or 3. started therapy off site before this month but
		 * did not make a visit to the facility during this month because they had been given enough
		 * drugs during visits before this month to cover the reporting month.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_FSW",
		    "Fsw currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_MSM",
		    "Msm currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_MSW",
		    "Msw currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_PWID",
		    "Pwid currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_PWUD",
		    "Pwud currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_Transgender",
		    "Transgender currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(TRANSGENDER), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_Prisoners_closed_settings",
		    "People in prisons and closed settings currently on ART off site within the period ",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Total currently on ART
		 */
		cohortDsd.addColumn("Total_currently_ART_FSW", "Total Fsw currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(FSW), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_MSM", "Total Msm currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(MSM), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_MSW", "Total Msw currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(MSW), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_PWID", "Total Pwid currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(PWID), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_PWUD", "Total Pwud currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(PWUD), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_Transgender", "Total Transgender currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRANSGENDER), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_Prisoners_closed_settings",
		    "Total Prisoners and people in closed settings currently on ART ",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(IN_PRISONS), indParams), "");
		/*

				MOH731BPlusSubCountyBasedCohortDefinition cd = new MOH731BPlusSubCountyBasedCohortDefinition();
				cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
				cd.addParameter(new Parameter("endDate", "End Date", Date.class));
				cd.addParameter(new Parameter("subCounty", "Sub County", Date.class));

				cohortDsd.addRowFilter(cd, paramMapping);
				*/
		
		return cohortDsd;
		
	}
}
