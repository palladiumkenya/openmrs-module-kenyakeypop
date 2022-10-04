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
		        Date.class), new Parameter("location", "Sub County", String.class), new Parameter("dateBasedReporting", "",
		        String.class));
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays
		        .asList(ReportUtils.map(kpDataSet(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
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
		
		String indParams = "startDate=${startDate},endDate=${endDate},location=${subCounty}";
		
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
		/*
		cohortDsd.addColumn("Screened_HCV_FSW", "HCV Screening Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForHCVFsw(), indParams), "");
		cohortDsd.addColumn("Screened_HCV_MSM", "HCV Screening Msm",
		    ReportUtils.map(moh731bIndicators.screenedForHCVMsm(), indParams), "");
		cohortDsd.addColumn("Screened_HCV_MSW", "HCV Screening Msw",
		    ReportUtils.map(moh731bIndicators.screenedForHCVMsw(), indParams), "");
		cohortDsd.addColumn("Screened_HCV_PWID", "HCV Screening Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForHCVPwid(), indParams), "");
		cohortDsd.addColumn("Screened_HCV_PWUD", "HCV Screening Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForHCVPwud(), indParams), "");
		cohortDsd.addColumn("Screened_HCV_Transgender", "HCV Screening Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForHCVTransgender(), indParams), "");
		
		cohortDsd.addColumn("Screened_HBV_FSW", "HBV Screening Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForHBVFsw(), indParams), "");
		cohortDsd.addColumn("Screened_HBV_MSM", "HBV Screening Msm",
		    ReportUtils.map(moh731bIndicators.screenedForHBVMsm(), indParams), "");
		cohortDsd.addColumn("Screened_HBV_MSW", "HBV Screening Msw",
		    ReportUtils.map(moh731bIndicators.screenedForHBVMsw(), indParams), "");
		cohortDsd.addColumn("Screened_HBV_PWID", "HBV Screening Per Need Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForHBVPwid(), indParams), "");
		cohortDsd.addColumn("Screened_HBV_PWUD", "HBV Screening Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForHBVPwud(), indParams), "");
		cohortDsd.addColumn("Screened_HBV_Transgender", "HBV Screening Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForHBVTransgender(), indParams), "");
		
		cohortDsd.addColumn("Positive_HBV_FSW", "HBV Positive Fsw",
		    ReportUtils.map(moh731bIndicators.positiveHBVFsw(), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSM", "HBV Positive Msm",
		    ReportUtils.map(moh731bIndicators.positiveHBVMsm(), indParams), "");
		cohortDsd.addColumn("Positive_HBV_MSW", "HBV Positive Msw",
		    ReportUtils.map(moh731bIndicators.positiveHBVMsw(), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWID", "HBV Positive Pwid",
		    ReportUtils.map(moh731bIndicators.positiveHBVPwid(), indParams), "");
		cohortDsd.addColumn("Positive_HBV_PWUD", "HBV Positive Pwud",
		    ReportUtils.map(moh731bIndicators.positiveHBVPwud(), indParams), "");
		cohortDsd.addColumn("Positive_HBV_Transgender", "HBV Positive Transgender",
		    ReportUtils.map(moh731bIndicators.positiveHBVTransgender(), indParams), "");
		
		cohortDsd.addColumn("Treated_HBV_FSW", "HBV Treated Fsw",
		    ReportUtils.map(moh731bIndicators.treatedHBVFsw(), indParams), "");
		cohortDsd.addColumn("Treated_HBV_MSM", "HBV Treated Msm",
		    ReportUtils.map(moh731bIndicators.treatedHBVMsm(), indParams), "");
		cohortDsd.addColumn("Treated_HBV_MSW", "HBV Treated Msw",
		    ReportUtils.map(moh731bIndicators.treatedHBVMsw(), indParams), "");
		cohortDsd.addColumn("Treated_HBV_PWID", "HBV Treated Pwid",
		    ReportUtils.map(moh731bIndicators.treatedHBVPwid(), indParams), "");
		cohortDsd.addColumn("Treated_HBV_PWUD", "HBV Treated Pwud",
		    ReportUtils.map(moh731bIndicators.treatedHBVPwud(), indParams), "");
		cohortDsd.addColumn("Treated_HBV_Transgender", "HBV Treated Transgender",
		    ReportUtils.map(moh731bIndicators.treatedHBVTransgender(), indParams), "");
		
		*//*   cohortDsd.addColumn("NegativeHBV_Vaccinated_FSW", "Negative HBV Vaccinated Fsw", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedFsw(), indParams),"");
		  cohortDsd.addColumn("NegativeHBV_Vaccinated_MSM", "Negative HBV Vaccinated Msm", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedMsm(), indParams),"");
		  cohortDsd.addColumn("NegativeHBV_Vaccinated_MSW", "Negative HBV Vaccinated Msw", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedMsw(), indParams),"");
		  cohortDsd.addColumn("NegativeHBV_Vaccinated_PWID", "Negative HBV Vaccinated Pwid", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedPwid(), indParams),"");
		  cohortDsd.addColumn("NegativeHBV_Vaccinated_PWUD", "Negative HBV Vaccinated Pwud", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedPwud(), indParams),"");
		  cohortDsd.addColumn("NegativeHBV_Vaccinated_Transgender", "Negative HBV Vaccinated Transgender", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedTransgender(), indParams),"");
		  *//*
		    cohortDsd.addColumn("Screened_TB_FSW", "Screened TB Fsw",
		      ReportUtils.map(moh731bIndicators.screenedTBFsw(), indParams), "");
		    cohortDsd.addColumn("Screened_TB_MSM", "Screened TB Msm",
		      ReportUtils.map(moh731bIndicators.screenedTBMsm(), indParams), "");
		    cohortDsd.addColumn("Screened_TB_MSW", "Screened TB Msw",
		      ReportUtils.map(moh731bIndicators.screenedTBMsw(), indParams), "");
		    cohortDsd.addColumn("Screened_TB_PWID", "Screened TB Pwid",
		      ReportUtils.map(moh731bIndicators.screenedTBPwid(), indParams), "");
		    cohortDsd.addColumn("Screened_TB_PWUD", "Screened TB Pwud",
		      ReportUtils.map(moh731bIndicators.screenedTBPwud(), indParams), "");
		    cohortDsd.addColumn("Screened_TB_Transgender", "Screened TB Transgender",
		      ReportUtils.map(moh731bIndicators.screenedTBTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Diagnosed_TB_FSW", "Diagnosed TB Fsw",
		      ReportUtils.map(moh731bIndicators.diagnosedTBFsw(), indParams), "");
		    cohortDsd.addColumn("Diagnosed_TB_MSM", "Diagnosed TB Msm",
		      ReportUtils.map(moh731bIndicators.diagnosedTBMsm(), indParams), "");
		    cohortDsd.addColumn("Diagnosed_TB_MSW", "Diagnosed TB Msw",
		      ReportUtils.map(moh731bIndicators.diagnosedTBMsw(), indParams), "");
		    cohortDsd.addColumn("Diagnosed_TB_PWID", "Diagnosed TB Pwid",
		      ReportUtils.map(moh731bIndicators.diagnosedTBPwid(), indParams), "");
		    cohortDsd.addColumn("Diagnosed_TB_PWUD", "Diagnosed TB Pwud",
		      ReportUtils.map(moh731bIndicators.diagnosedTBPwud(), indParams), "");
		    cohortDsd.addColumn("Diagnosed_TB_Transgender", "Diagnosed TB Transgender",
		      ReportUtils.map(moh731bIndicators.diagnosedTBTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Started_on_TB_TX_FSW", "Started on TB TX Fsw",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxFsw(), indParams), "");
		    cohortDsd.addColumn("Started_on_TB_TX_MSM", "Started on TB TX Msm",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxMsm(), indParams), "");
		    cohortDsd.addColumn("Started_on_TB_TX_MSW", "Started on TB TX Msw",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxMsw(), indParams), "");
		    cohortDsd.addColumn("Started_on_TB_TX_PWID", "Started on TB TX Pwid",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxPwid(), indParams), "");
		    cohortDsd.addColumn("Started_on_TB_TX_PWUD", "Started on TB TX Pwud",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxPwud(), indParams), "");
		    cohortDsd.addColumn("Started_on_TB_TX_Transgender", "Started on TB TX Transgender",
		      ReportUtils.map(moh731bIndicators.startedOnTBTxTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("TB_Clients_on_HAART_FSW", "TB Clients on HAART TX Fsw",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTFsw(), indParams), "");
		    cohortDsd.addColumn("TB_Clients_on_HAART_MSM", "TB Clients on HAART TX Msm",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTMsm(), indParams), "");
		    cohortDsd.addColumn("TB_Clients_on_HAART_MSW", "TB Clients on HAART TX Msw",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTMsw(), indParams), "");
		    cohortDsd.addColumn("TB_Clients_on_HAART_PWID", "TB Clients on HAART TX Pwid",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTPwid(), indParams), "");
		    cohortDsd.addColumn("TB_Clients_on_HAART_PWUD", "TB Clients on HAART TX Pwud",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTPwud(), indParams), "");
		    cohortDsd.addColumn("TB_Clients_on_HAART_Transgender", "TB Clients on HAART TX Transgender",
		      ReportUtils.map(moh731bIndicators.tbClientsOnHAARTTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Initiated_PrEP_FSW", "Initiated PrEP Fsw",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPFsw(), indParams), "");
		    cohortDsd.addColumn("Initiated_PrEP_MSM", "Initiated PrEP Msm",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPMsm(), indParams), "");
		    cohortDsd.addColumn("Initiated_PrEP_MSW", "Initiated PrEP Msw",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPMsw(), indParams), "");
		    cohortDsd.addColumn("Initiated_PrEP_PWID", "Initiated PrEP Pwid",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPPwid(), indParams), "");
		    cohortDsd.addColumn("Initiated_PrEP_PWUD", "Initiated PrEP Pwud",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPPwud(), indParams), "");
		    cohortDsd.addColumn("Initiated_PrEP_Transgender", "Initiated PrEP Transgender",
		      ReportUtils.map(moh731bIndicators.initiatedPrEPTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Current_on_PrEP_FSW", "Current on PrEP Fsw",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPFsw(), indParams), "");
		    cohortDsd.addColumn("Current_on_PrEP_MSM", "Current on PrEP Msm",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPMsm(), indParams), "");
		    cohortDsd.addColumn("Current_on_PrEP_MSW", "Current on PrEP Msw",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPMsw(), indParams), "");
		    cohortDsd.addColumn("Current_on_PrEP_PWID", "Current on PrEP Pwid",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPPwid(), indParams), "");
		    cohortDsd.addColumn("Current_on_PrEP_PWUD", "Current on PrEP Pwud",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPPwud(), indParams), "");
		    cohortDsd.addColumn("Current_on_PrEP_Transgender", "Current on PrEP Transgender",
		      ReportUtils.map(moh731bIndicators.currentOnPrEPTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Turning_Positive_While_On_FSW", "Turning HIV+ While on PrEP Fsw",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPFsw(), indParams), "");
		    cohortDsd.addColumn("Turning_Positive_While_On_MSM", "Turning HIV+ While on PrEP Msm",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPMsm(), indParams), "");
		    cohortDsd.addColumn("Turning_Positive_While_On_MSW", "Turning HIV+ While on PrEP Msw",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPMsw(), indParams), "");
		    cohortDsd.addColumn("Turning_Positive_While_On_PWID", "Turning HIV+ While on PrEP Pwid",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPPwid(), indParams), "");
		    cohortDsd.addColumn("Turning_Positive_While_On_PWUD", "Turning HIV+ While on PrEP Pwud",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPPwud(), indParams), "");
		    cohortDsd.addColumn("Turning_Positive_While_On_Transgender", "Turning HIV+ While on PrEP Transgender",
		      ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEPTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Experiencing_Violence_FSW", "Experiencing Violence Fsw",
		      ReportUtils.map(moh731bIndicators.experiencingViolenceFsw(), indParams), "");
		    cohortDsd.addColumn("Experiencing_Violence_MSM", "Experiencing Violence Msm",
		      ReportUtils.map(moh731bIndicators.experiencingViolenceMsm(), indParams), "");
		    cohortDsd.addColumn("Experiencing_Violence_MSW", "Experiencing Violence Msw",
		      ReportUtils.map(moh731bIndicators.experiencingViolenceMsw(), indParams), "");
		    cohortDsd.addColumn("Experiencing_Violence_PWID", "Experiencing Violence Pwid",
		      ReportUtils.map(moh731bIndicators.experiencingViolencePwid(), indParams), "");
		    cohortDsd.addColumn("Experiencing_Violence_PWUD", "Experiencing Violence Pwud",
		      ReportUtils.map(moh731bIndicators.experiencingViolencePwud(), indParams), "");
		    cohortDsd.addColumn("Experiencing_Violence_Transgender", "Experiencing Violence Transgender",
		      ReportUtils.map(moh731bIndicators.experiencingViolenceTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("receiving_Violence_Support_FSW", "Receiving Violence Support Fsw",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportFsw(), indParams), "");
		    cohortDsd.addColumn("receiving_Violence_Support_MSM", "Receiving Violence Support Msm",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportMsm(), indParams), "");
		    cohortDsd.addColumn("receiving_Violence_Support_MSW", "Receiving Violence Support Msw",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportMsw(), indParams), "");
		    cohortDsd.addColumn("receiving_Violence_Support_PWID", "Receiving Violence Support Pwid",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportPwid(), indParams), "");
		    cohortDsd.addColumn("receiving_Violence_Support_PWUD", "Receiving Violence Support Pwud",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportPwud(), indParams), "");
		    cohortDsd.addColumn("receiving_Violence_Support_Transgender", "Receiving Violence Support Transgender",
		      ReportUtils.map(moh731bIndicators.receivingViolenceSupportTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Number_Exposed_FSW", "Number Exposed Fsw",
		      ReportUtils.map(moh731bIndicators.numberExposedFsw(), indParams), "");
		    cohortDsd.addColumn("Number_Exposed_MSM", "Number Exposed Msm",
		      ReportUtils.map(moh731bIndicators.numberExposedMsm(), indParams), "");
		    cohortDsd.addColumn("Number_Exposed_MSW", "Number Exposed Msw",
		      ReportUtils.map(moh731bIndicators.numberExposedMsw(), indParams), "");
		    cohortDsd.addColumn("Number_Exposed_PWID", "Number Exposed Pwid",
		      ReportUtils.map(moh731bIndicators.numberExposedPwid(), indParams), "");
		    cohortDsd.addColumn("Number_Exposed_PWUD", "Number Exposed Pwud",
		      ReportUtils.map(moh731bIndicators.numberExposedPwud(), indParams), "");
		    cohortDsd.addColumn("Number_Exposed_Transgender", "Number Exposed Transgender",
		      ReportUtils.map(moh731bIndicators.numberExposedTransgender(), indParams), "");
		    
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_FSW", "Receiving PEP Within 72 hours Fsw",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsFsw(), indParams), "");
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_MSM", "Receiving PEP Within 72 hours Msm",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsMsm(), indParams), "");
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_MSW", "Receiving PEP Within 72 hours Msw",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsMsw(), indParams), "");
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_PWID", "Receiving PEP Within 72 hours Pwid",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsPwid(), indParams), "");
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_PWUD", "Receiving PEP Within 72 hours Pwud",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsPwud(), indParams), "");
		    cohortDsd.addColumn("Receiving_PEP_Within_72hrs_Transgender", "Receiving PEP Within 72 hours Transgender",
		      ReportUtils.map(moh731bIndicators.receivingPEPWithin72HrsTransgender(), indParams), "");
		    */
		/*
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_FSW", "Completed PEP within 28 days Fsw", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysFsw(), indParams),"");
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSM", "Completed PEP within 28 days Msm", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysMsm(), indParams),"");
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSW", "Completed PEP within 28 days Msw", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysMsw(), indParams),"");
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWID", "Completed PEP within 28 days Pwid", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysPwid(), indParams),"");
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWUD", "Completed PEP within 28 days Pwud", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysPwud(), indParams),"");
		        cohortDsd.addColumn("Completed_PEP_Within_28_Days_Transgender", "Completed PEP within 28 days Transgender", ReportUtils.map(moh731bIndicators.completedPEPWith28DaysTransgender(), indParams),"");


		MOH731BPlusSubCountyBasedCohortDefinition cd = new MOH731BPlusSubCountyBasedCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", Date.class));

		cohortDsd.addRowFilter(cd, paramMapping);
		*/
		
		return cohortDsd;
		
	}
	
}
