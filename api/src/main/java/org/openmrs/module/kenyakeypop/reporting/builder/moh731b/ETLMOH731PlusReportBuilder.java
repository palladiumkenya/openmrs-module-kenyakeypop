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
		return Arrays.asList(ReportUtils
		        .map(kpDataSet(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
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
		cohortDsd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731BAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("KPType", ReportUtils.map(commonDimensions.kpType()));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";
		
		EmrReportingUtils.addRow(cohortDsd, "Active FSW", "",
		    ReportUtils.map(moh731bIndicators.activeFsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active MSM", "",
		    ReportUtils.map(moh731bIndicators.activeMsm(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active MSW", "",
		    ReportUtils.map(moh731bIndicators.activeMsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active PWID", "",
		    ReportUtils.map(moh731bIndicators.activePwid(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active PWUD", "",
		    ReportUtils.map(moh731bIndicators.activePwud(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active Transgender", "",
		    ReportUtils.map(moh731bIndicators.activeTransgender(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active prisoners and people in closed settings", "",
				ReportUtils.map(moh731bIndicators.activePrisonersAndClossedSettings(), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "Tested FSW", "",
		    ReportUtils.map(moh731bIndicators.hivTestedFsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested MSM", "Tested MSM",
		    ReportUtils.map(moh731bIndicators.hivTestedMsm(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested MSW", "Tested MSW",
		    ReportUtils.map(moh731bIndicators.hivTestedMsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested PWID", "Tested PWID",
		    ReportUtils.map(moh731bIndicators.hivTestedPwid(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested PWUD", "Tested PWUD",
		    ReportUtils.map(moh731bIndicators.hivTestedPwud(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested Transgender", "Tested Transgender",
		    ReportUtils.map(moh731bIndicators.hivTestedTransgender(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested prisoners and people in closed settings", "",
				ReportUtils.map(moh731bIndicators.hivTestedPrisonersAndClossedSettings(), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04", "05"));
		
		cohortDsd.addColumn("Tested_Facility_FSW", "Tested Facility Fsw",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityFsw(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_MSM", "Tested Facility Msm",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityMsm(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_MSW", "Tested Facility Msw",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityMsw(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_PWID", "Tested Facility Pwid",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityPwid(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_PWUD", "Tested Facility Pwud",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityPwud(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_Transgender", "Tested Facility Transgender",
		    ReportUtils.map(moh731bIndicators.testedAtFacilityTransgender(), indParams), "");
		cohortDsd.addColumn("Tested_Facility_Prisoners_Closed_settings", "Prisoners & people in closed settings",
				ReportUtils.map(moh731bIndicators.testedAtFacilityPrisonsersClosedSettings(), indParams), "");

		cohortDsd.addColumn("Tested_Community_FSW", "Tested Community Fsw",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityFsw(), indParams), "");
		cohortDsd.addColumn("Tested_Community_MSM", "Tested Community Msm",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityMsm(), indParams), "");
		cohortDsd.addColumn("Tested_Community_MSW", "Tested Community Msw",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityMsw(), indParams), "");
		cohortDsd.addColumn("Tested_Community_PWID", "Tested Community Pwid",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityPwid(), indParams), "");
		cohortDsd.addColumn("Tested_Community_PWUD", "Tested Community Pwud",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityPwud(), indParams), "");
		cohortDsd.addColumn("Tested_Community_Transgender", "Tested Community Transgender",
		    ReportUtils.map(moh731bIndicators.testedAtCommunityTransgender(), indParams), "");
		cohortDsd.addColumn("Tested_Community_Prisoners_Closed_Settings", "Tested Community Prisoners & people in closed settings",
				ReportUtils.map(moh731bIndicators.testedAtCommunityPrisonsersClosedSettings(), indParams), "");
		
		cohortDsd.addColumn("Tested_New_FSW", "Tested New Fsw",
		    ReportUtils.map(moh731bIndicators.testedNewFsw(), indParams), "");
		cohortDsd.addColumn("Tested_New_MSM", "Tested New Msm",
		    ReportUtils.map(moh731bIndicators.testedNewMsm(), indParams), "");
		cohortDsd.addColumn("Tested_New_MSW", "Tested New Msw",
		    ReportUtils.map(moh731bIndicators.testedNewMsw(), indParams), "");
		cohortDsd.addColumn("Tested_New_PWID", "Tested New Pwid",
		    ReportUtils.map(moh731bIndicators.testedNewPwid(), indParams), "");
		cohortDsd.addColumn("Tested_New_PWUD", "Tested New Pwud",
		    ReportUtils.map(moh731bIndicators.testedNewPwud(), indParams), "");
		cohortDsd.addColumn("Tested_New_Transgender", "Tested New Transgender",
		    ReportUtils.map(moh731bIndicators.testedAtNewTransgender(), indParams), "");
		cohortDsd.addColumn("Tested_New_Prisoners_Closed_Settings", "Tested New Prisoners & people in closed settings",
				ReportUtils.map(moh731bIndicators.newlyTestedprisonsersClosedSettings(), indParams), "");

		cohortDsd.addColumn("Tested_Repeat_FSW", "Tested Repeat Fsw",
		    ReportUtils.map(moh731bIndicators.testedRepeatFsw(), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_MSM", "Tested Repeat Msm",
		    ReportUtils.map(moh731bIndicators.testedRepeatMsm(), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_MSW", "Tested Repeat Msw",
		    ReportUtils.map(moh731bIndicators.testedRepeatMsw(), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_PWID", "Tested Repeat Pwid",
		    ReportUtils.map(moh731bIndicators.testedRepeatPwid(), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_PWUD", "Tested Repeat Pwud",
		    ReportUtils.map(moh731bIndicators.testedRepeatPwud(), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_Transgender", "Tested Repeat Transgender",
		    ReportUtils.map(moh731bIndicators.testedRepeatTransgender(), indParams), "");
			cohortDsd.addColumn("Tested_Repeat_Prisoners_Closed_Settings", "Tested Repeat Prisoners & people in closed settings",
					ReportUtils.map(moh731bIndicators.testedRepeatPrisonersAndClosedSettings(), indParams), "");


		cohortDsd.addColumn("Self_Tested_FSW", "Self Tested Fsw",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVFsw(), indParams), "");
		cohortDsd.addColumn("Self_Tested_MSM", "Self Tested Msm",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVMsm(), indParams), "");
		cohortDsd.addColumn("Self_Tested_MSW", "Self Tested Msw",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVMsw(), indParams), "");
		cohortDsd.addColumn("Self_Tested_PWID", "Self Tested Pwid",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVPwid(), indParams), "");
		cohortDsd.addColumn("Self_Tested_PWUD", "Self Tested Pwud",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVPwud(), indParams), "");
		cohortDsd.addColumn("Self_Tested_Transgender", "Self Tested Transgender",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVTransgender(), indParams), "");
		cohortDsd.addColumn("Self_Tested_Prisoners_Closed_Settings", "Self Tested Prisoners & people in closed settings",
				ReportUtils.map(moh731bIndicators.selfTestedForHIVPrisonersAndClosedSettings(), indParams), "");

		cohortDsd.addColumn("Known_Positive_FSW", "Known Positive Fsw",
		    ReportUtils.map(moh731bIndicators.knownPositiveFsw(), indParams), "");
		cohortDsd.addColumn("Known_Positive_MSM", "Known Positive Msm",
		    ReportUtils.map(moh731bIndicators.knownPositiveMsm(), indParams), "");
		cohortDsd.addColumn("Known_Positive_MSW", "Known Positive Msw",
		    ReportUtils.map(moh731bIndicators.knownPositiveMsw(), indParams), "");
		cohortDsd.addColumn("Known_Positive_PWID", "Known Positive Pwid",
		    ReportUtils.map(moh731bIndicators.knownPositivePwid(), indParams), "");
		cohortDsd.addColumn("Known_Positive_PWUD", "Known Positive Pwud",
		    ReportUtils.map(moh731bIndicators.knownPositivePwud(), indParams), "");
		cohortDsd.addColumn("Known_Positive_Transgender", "Known Positive Transgender",
		    ReportUtils.map(moh731bIndicators.knownPositivePrisonersClosedSettings(), indParams), "");
		cohortDsd.addColumn("Known_Positive_Prisoners_Closed_Settings", "Known Positive Prisoners and People in closed settings",
				ReportUtils.map(moh731bIndicators.knownPositivePrisonersClosedSettings(), indParams), "");

		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_FSW", "Received Positive Results Fsw",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsFsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_MSM", "Received Positive Results Msm",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsMsm(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_MSW", "Received Positive Results Msw",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsMsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_PWID", "Received Positive Results Pwid",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsPwid(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_PWUD", "Received Positive Results Pwud",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsPwud(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_Transgender",
		    "Received Positive Results Transgender",
		    ReportUtils.map(moh731bIndicators.receivedPositiveResultsTransgender(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "Linked_FSW", "HIV+ 3 Months ago and Linked Fsw",
		    ReportUtils.map(moh731bIndicators.linkedFsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_MSM", "HIV+ 3 Months ago and Linked Msm",
		    ReportUtils.map(moh731bIndicators.linkedMsm(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_MSW", "HIV+ 3 Months ago and Linked Msw",
		    ReportUtils.map(moh731bIndicators.linkedMsw(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_PWID", "HIV+ 3 Months ago and Linked Pwid",
		    ReportUtils.map(moh731bIndicators.linkedPwid(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_PWUD", "HIV+ 3 Months ago and Linked Pwud",
		    ReportUtils.map(moh731bIndicators.linkedPwud(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_Transgender", "HIV+ 3 Months ago and Linked Transgender",
		    ReportUtils.map(moh731bIndicators.linkedTransgender(), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		cohortDsd.addColumn("Receiving_Condoms_FSW", "Receiving Condoms Fsw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_MSM", "Receiving Condoms Msm",
		    ReportUtils.map(moh731bIndicators.receivingCondomsMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_MSW", "Receiving Condoms Msw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_PWID", "Receiving Condoms Pwid",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_PWUD", "Receiving Condoms Pwud",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Transgender", "Receiving Condoms Transgender",
		    ReportUtils.map(moh731bIndicators.receivingCondomsTransgender(), indParams), "");
		
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_FSW", "Receiving Condoms Per need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeedFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_MSM", "Receiving Condoms Per need Msm",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_MSW", "Receiving Condoms Per need Msw",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_PWID", "Receiving Condoms Per need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_PWUD", "Receiving Condoms Per need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Condoms_Per_Need_Transgender", "Receiving Condoms Per need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedTransgender(), indParams), "");
		
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_FSW", "Receiving needles and syringes Fsw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_MSM", "Receiving needles and syringes Msm",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_MSW", "Receiving needles and syringes Msw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_PWID", "Receiving needles and syringes Pwid",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_PWUD", "Receiving needles and syringes Pwud",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Transgender", "Receiving needles and syringes Transgender",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesTransgender(), indParams), "");
		
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_FSW", "Receiving needs & syringes per need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_MSM", "Receiving needs & syringes per need Msm",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_MSW", "Receiving needs & syringes per need Msw",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_PWID", "Receiving needs & syringes per need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_PWUD", "Receiving needs & syringes per need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Needles_and_Syringes_Per_Need_Transgender",
		    "Receiving needs & syringes per need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeedTransgender(), indParams), "");
		
		cohortDsd.addColumn("Receiving_Lubricants_FSW", "Receiving Lubricants Fsw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_MSM", "Receiving Lubricants Msm",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_MSW", "Receiving Lubricants Msw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_PWID", "Receiving Lubricants Pwid",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_PWUD", "Receiving Lubricants Pwud",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Transgender", "Receiving Lubricants Transgender",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsTransgender(), indParams), "");
		
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_FSW", "Receiving Lubricants Per Need Fsw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedFsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_MSM", "Receiving Lubricants Per Need Msm",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedMsm(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_MSW", "Receiving Lubricants Per Need Msw",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedMsw(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_PWID", "Receiving Lubricants Per Need Pwid",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedPwid(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_PWUD", "Receiving Lubricants Per Need Pwud",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedPwud(), indParams), "");
		cohortDsd.addColumn("Receiving_Lubricants_Per_Need_Transgender", "Receiving Lubricants Per Need Transgender",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeedTransgender(), indParams), "");
		
		cohortDsd.addColumn("STI_Screening_FSW", "STI Screening Fsw",
		    ReportUtils.map(moh731bIndicators.screenedForSTIFsw(), indParams), "");
		cohortDsd.addColumn("STI_Screening_MSM", "STI Screening Msm",
		    ReportUtils.map(moh731bIndicators.screenedForSTIMsm(), indParams), "");
		cohortDsd.addColumn("STI_Screening_MSW", "STI Screening Msw",
		    ReportUtils.map(moh731bIndicators.screenedForSTIMsw(), indParams), "");
		cohortDsd.addColumn("STI_Screening_PWID", "STI Screening  Pwid",
		    ReportUtils.map(moh731bIndicators.screenedForSTIPwid(), indParams), "");
		cohortDsd.addColumn("STI_Screening_PWUD", "STI Screening Pwud",
		    ReportUtils.map(moh731bIndicators.screenedForSTIPwud(), indParams), "");
		cohortDsd.addColumn("STI_Screening_Transgender", "STI Screening Transgender",
		    ReportUtils.map(moh731bIndicators.screenedForSTITransgender(), indParams), "");
		
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
		
		/*   cohortDsd.addColumn("NegativeHBV_Vaccinated_FSW", "Negative HBV Vaccinated Fsw", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedFsw(), indParams),"");
		   cohortDsd.addColumn("NegativeHBV_Vaccinated_MSM", "Negative HBV Vaccinated Msm", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedMsm(), indParams),"");
		   cohortDsd.addColumn("NegativeHBV_Vaccinated_MSW", "Negative HBV Vaccinated Msw", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedMsw(), indParams),"");
		   cohortDsd.addColumn("NegativeHBV_Vaccinated_PWID", "Negative HBV Vaccinated Pwid", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedPwid(), indParams),"");
		   cohortDsd.addColumn("NegativeHBV_Vaccinated_PWUD", "Negative HBV Vaccinated Pwud", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedPwud(), indParams),"");
		   cohortDsd.addColumn("NegativeHBV_Vaccinated_Transgender", "Negative HBV Vaccinated Transgender", ReportUtils.map(moh731bIndicators.negativeHBVVaccinatedTransgender(), indParams),"");
		*/
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
