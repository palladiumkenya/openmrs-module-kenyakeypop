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
import org.openmrs.module.kenyakeypop.reporting.library.ETLReports.moh731B.ETLMoh731PlusIndicatorLibrary;
import org.openmrs.module.kenyakeypop.reporting.library.shared.common.CommonKpDimensionLibrary;
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
	
	public static final String TRANSMAN = "TRANSMAN";
	
	public static final String TRANSWOMAN = "TRANSWOMAN";
	
	ColumnParameters kp15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
	
	ColumnParameters kpFemale15_to_19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
	
	ColumnParameters kpMale15_to_19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
	
	ColumnParameters kp20_to_24 = new ColumnParameters(null, "20-24", "age=20-24");
	
	ColumnParameters kpFemale20_to_24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
	
	ColumnParameters kpMale20_to_24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
	
	ColumnParameters kp25_to_29 = new ColumnParameters(null, "25-29", "age=25-29");
	
	ColumnParameters kpFemale25_to_29 = new ColumnParameters(null, "25-29, Female", "gender=F|age=25-29");
	
	ColumnParameters kpMale25_to_29 = new ColumnParameters(null, "25-29, Male", "gender=M|age=25-29");
	
	ColumnParameters kp30_and_above = new ColumnParameters(null, "30+", "age=30+");
	
	ColumnParameters kpFemale30_and_above = new ColumnParameters(null, "30+, Female", "gender=F|age=30+");
	
	ColumnParameters kpMale30_and_above = new ColumnParameters(null, "30+, Male", "gender=M|age=30+");
	
	ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
	
	ColumnParameters males = new ColumnParameters(null, "Male", "gender=M");
	
	ColumnParameters females = new ColumnParameters(null, "Female", "gender=F");
	
	List<ColumnParameters> genderDisaggregation = Arrays.asList(males, females);
	
	List<ColumnParameters> kpAgeDisaggregation = Arrays.asList(kp15_to_19, kp20_to_24, kp25_to_29, kp30_and_above, colTotal);
	
	List<ColumnParameters> kpAgeGenderDisaggregation = Arrays.asList(kpFemale15_to_19, kpMale15_to_19, kpFemale20_to_24,
	    kpMale20_to_24, kpFemale25_to_29, kpMale25_to_29, kpFemale30_and_above, kpMale30_and_above, colTotal);
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("location", "Sub-County", String.class), new Parameter("dateBasedReporting", "",
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
		cohortDsd.addParameter(new Parameter("location", "Sub-County", String.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731BAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("KPType", ReportUtils.map(commonDimensions.kpType()));
		cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
		
		String indParams = "startDate=${startDate},endDate=${endDate},location=${location}";
		
		// 1.0 Number of KP Reached
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Reached", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonths(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// 2.0 Number KP Reached with Defined Package
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(PWID), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(PWUD), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(TRANSWOMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kpsReachedWithinLastThreeMonthsDefinedPackage(TRANSMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		// 2.1 Number KPS Receiving Peer Education
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(PWID), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(PWUD), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(TRANSWOMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kpsReceivingPeerEducationWithinReportingPeriod(TRANSMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		// 2.2 Number of Receiving Commodities
		// Number receiving Condoms
		cohortDsd.addColumn("Number of FSW Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(TRANSWOMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSMAN Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(TRANSMAN), indParams), "");
		
		// Receiving Condoms per need
		cohortDsd.addColumn("Number of FSW Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(TRANSWOMAN), indParams), "");
		
		/**
		 * Number receiving needles & syringes: number of individuals in each KP type who received
		 * at least one needle & syringe, irrespective of service provision point.
		 */
		cohortDsd.addColumn("Number of FSW Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Receiving Needles and Syringes", "Receiving needles and syringes Transman",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(TRANSWOMAN), indParams), "");
		
		/**
		 * Number receiving needles & syringes per need number of individuals in each KP type who
		 * received needles & syringes based on their requirements derived from estimated number of
		 * injecting episodes per month.
		 */
		cohortDsd.addColumn("Number of FSW Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(TRANSWOMAN), indParams), "");
		
		/**
		 * Number receiving lubricants Number of individuals in each KP type who received lubricants
		 * based on their requirements.
		 */
		cohortDsd.addColumn("Number of FSW Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(TRANSWOMAN), indParams), "");
		
		/**
		 * Number receiving lubricants per need Number of individuals in each KP type who received
		 * lubricants based on their requirements
		 */
		cohortDsd.addColumn("Number of FSW Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(TRANSWOMAN), indParams), "");
		
		//4.6 Violence support
		/**
		 * Experience violence: number of people in each KP type who experienced sexual violence in
		 * the reporting period.
		 */
		/*cohortDsd.addColumn("Number of FSW Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(PWID), indParams), genderDisaggregation,
				Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(PWUD), indParams), genderDisaggregation,
				Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Experiencing Sexual Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingSexualViolence(TRANSWOMAN), indParams), "");*/
		/**
		 * programme when they experienced Physical violence in the reporting period by KP type
		 */
		/*cohortDsd.addColumn("Number of FSW Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(PWID), indParams), genderDisaggregation,
				Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(PWUD), indParams), genderDisaggregation,
				Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Experiencing Physical Violence", "",
				ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(TRANSWOMAN), indParams), "");*/
		
		/**
		 * Number of KPs experiencing emotional or psychological violence
		 */
		/*
				cohortDsd.addColumn("Number of FSW Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(FSW), indParams), "");
				cohortDsd.addColumn("Number of MSM Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(MSM), indParams), "");
				cohortDsd.addColumn("Number of MSW Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(MSW), indParams), "");
				EmrReportingUtils.addRow(cohortDsd, "Number of PWID Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(PWID), indParams), genderDisaggregation,
						Arrays.asList("01", "02"));
				EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(PWUD), indParams), genderDisaggregation,
						Arrays.asList("01", "02"));
				cohortDsd.addColumn("Number of TRANSMAN Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(TRANSMAN), indParams), "");
				cohortDsd.addColumn("Number of TRANSWOMAN Experiencing Emotional/Psychological Violence", "",
						ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(TRANSWOMAN), indParams), "");
		*/
		
		/**
		 * Number of KPs who received violence support
		 */
		/*	cohortDsd.addColumn("Number of FSW Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(FSW), indParams), "");
			cohortDsd.addColumn("Number of MSM Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(MSM), indParams), "");
			cohortDsd.addColumn("Number of MSW Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(MSW), indParams), "");
			EmrReportingUtils.addRow(cohortDsd, "Number of PWID Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(PWID), indParams), genderDisaggregation,
					Arrays.asList("01", "02"));
			EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(PWUD), indParams), genderDisaggregation,
					Arrays.asList("01", "02"));
			cohortDsd.addColumn("Number of TRANSMAN Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(TRANSMAN), indParams), "");
			cohortDsd.addColumn("Number of TRANSWOMAN Received Violence Support", "",
					ReportUtils.map(moh731bIndicators.receivedViolenceSupport(TRANSWOMAN), indParams), "");*/
		// 4.1 STI screening
		/**
		 * Number screened_STI number of individuals in each KP type who were screened for STI in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(TRANSWOMAN), indParams), "");
		
		/**
		 * Diagnosed_STI: number of individuals in each KP type who were diagnosed with STI in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Diagnosed With STI", "",
		
		ReportUtils.map(moh731bIndicators.diagnosedWithSTI(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(TRANSWOMAN), indParams), "");
		
		/**
		 * Treated_STI: number of individuals in each KP type who were treated for STI in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(TRANSWOMAN), indParams), "");
		
		//Screened for HCV
		/**
		 * Number screened_HCV: number of individuals in each KP type who were screened for HCV in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(TRANSWOMAN), indParams), "");
		/**
		 * Positive_HCV: number of individuals in each KP type who were diagnosed with HCV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(TRANSWOMAN), indParams), "");
		
		/**
		 * Treated_HCV: number of individuals in each KP type who were treated for HCV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(TRANSWOMAN), indParams), "");
		
		//4.3 HBV (Hepatitis B)
		/**
		 * Number screened_HBV: number of individuals in each KP type who were screened for HBV in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(TRANSWOMAN), indParams), "");
		/**
		 * Negative_HBV_vaccinated: number of individuals in each KP type who were vaccinated for
		 * HBV in the reporting period
		 */
		cohortDsd.addColumn("Number of Negative FSW Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(FSW), indParams), "");
		cohortDsd.addColumn("Number of Negative MSM Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(MSM), indParams), "");
		cohortDsd.addColumn("Number of Negative MSW Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of Negative PWID Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of Negative PWUD Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of Negative TRANSMAN Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of Negative TRANSWOMAN Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(TRANSWOMAN), indParams), "");
		/**
		 * Positive_HBV: number of individuals in each KP type who were diagnosed with HBV in the
		 * reporting period
		 */
		cohortDsd.addColumn("Number of FSW Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(TRANSWOMAN), indParams), "");
		
		/**
		 * Treated_HBV: number of individuals in each KP type who were treated for HBV in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(TRANSWOMAN), indParams), "");
		
		/**
		 * Active KPs disaggregated by KP type
		 */
		EmrReportingUtils.addRow(cohortDsd, "Active_FSW", "Active FSW",
		    ReportUtils.map(moh731bIndicators.activeKPs(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_MSM", "Active MSM",
		    ReportUtils.map(moh731bIndicators.activeKPs(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_MSW", "", ReportUtils.map(moh731bIndicators.activeKPs(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_PWID", "Active PWID",
		    ReportUtils.map(moh731bIndicators.activeKPs(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_PWUD", "Active PWUD",
		    ReportUtils.map(moh731bIndicators.activeKPs(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_Transman", "Active Transman",
		    ReportUtils.map(moh731bIndicators.activeKPs(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Active_Transwoman", "Active Transwoman",
		    ReportUtils.map(moh731bIndicators.activeKPs(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * KPs tested for HIV disaggregated by KP type
		 */
		EmrReportingUtils.addRow(cohortDsd, "Tested_FSW", "Tested FSW",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_MSM", "Tested MSM",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_MSW", "Tested MSW",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_PWID", "Tested PWID",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_PWUD", "Tested PWUD",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(PWUD), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_Transman", "Tested Transman",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Tested_Transwoman", "Tested Transwoman",
		    ReportUtils.map(moh731bIndicators.hivTestedKPs(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		cohortDsd.addColumn("Tested_Facility_Transman", "Tested Facility Transman",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Tested_Facility_Transwoman", "Tested Facility Transwoman",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtFacilityKPs(TRANSWOMAN), indParams), "");
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
		cohortDsd.addColumn("Tested_Community_Transman", "Tested Community Transman",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Tested_Community_Transwoman", "Tested Community Transwoman",
		    ReportUtils.map(moh731bIndicators.htsNumberTestedAtCommunityKPs(TRANSWOMAN), indParams), "");
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
		cohortDsd.addColumn("Tested_New_Transman", "Tested New Transman",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Tested_New_Transwoman", "Tested New Transwoman",
		    ReportUtils.map(moh731bIndicators.kpsNewlyTestedForHIV(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Tested_Repeat_Transman", "Tested Repeat Transman",
		    ReportUtils.map(moh731bIndicators.testedRepeat(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Tested_Repeat_Transwoman", "Tested Repeat Transwoman",
		    ReportUtils.map(moh731bIndicators.testedRepeat(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Self_Tested_Transman", "Self Tested Transman",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Self_Tested_Transwoman", "Self Tested Transwoman",
		    ReportUtils.map(moh731bIndicators.selfTestedForHIV(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Known_Positive_Transman", "Known Positive Transman",
		    ReportUtils.map(moh731bIndicators.knownPositive(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Known_Positive_Transwoman", "Known Positive Transwoman",
		    ReportUtils.map(moh731bIndicators.knownPositive(TRANSWOMAN), indParams), "");
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
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_Transman", "Received Positive Results Transman",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Received_Positive_Results_Transwoman", "Received Positive Results Transwoman",
		    ReportUtils.map(moh731bIndicators.receivedPositiveHIVResults(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * 2.3 HIV positive 3 months ago linked to care/treatment. This set of data element Refers
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
		EmrReportingUtils.addRow(cohortDsd, "Linked_Transman", "HIV+ 3 Months ago and Linked Transman",
		    ReportUtils.map(moh731bIndicators.linked(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Linked_Transwoman", "HIV+ 3 Months ago and Linked Transwoman",
		    ReportUtils.map(moh731bIndicators.linked(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		/**
		 * Total positive 3 months ago: Total number of KPs in each KP type who were HIV positive
		 * three months ago.
		 */
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_FSW", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(FSW), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_MSM", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(MSM), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_MSW", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(MSW), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_PWID", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(PWID), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_PWUD", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(PWUD), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_Transman", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Total_Positive_3_Months_Ago_Transwoman", "HIV+ 3 Months ago",
		    ReportUtils.map(moh731bIndicators.positiveMonthsAgo(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Receiving_Self_Test_Kits_Transman", "Receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Receiving_Self_Test_Kits_Transwoman", "Transwoman receiving Self Test Kits",
		    ReportUtils.map(moh731bIndicators.receivingSelfTestKits(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Screened_TB_Transman", "Screened TB Transman",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Screened_TB_Transwoman", "Screened TB Transwoman",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Diagnosed_TB_Transman", "Diagnosed with TB Transman",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Diagnosed_TB_Transwoman", "Transwoman diagnosed with TB",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Started_TB_Tx_Transman", "Started TB Tx Transman",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Started_TB_Tx_Transwoman", "Transwoman Started TB Tx",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSWOMAN), indParams), "");
		
		/**
		 * TB clients on HAART: This is the count of TB patients who are receiving ART in each KP
		 * type. Calculate and enter the sum of all those TB patients who were already on HAART at
		 * the time of TB diagnosis and TB patients diagnosed with HIV who are started on HAART
		 * during the reporting month.
		 */
		cohortDsd.addColumn("TB_Clients_On_HAART_FSW", "TB clients on HAART Fsw",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(FSW), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_MSM", "TB clients on HAART Msm",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(MSM), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_MSW", "TB clients on HAART Msw",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(MSW), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_PWID", "TB clients on HAART Pwid",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(PWID), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_PWUD", "TB clients on HAART Pwud",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(PWUD), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_Transman", "TB clients on HAART Transman",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(TRANSMAN), indParams), "");
		cohortDsd.addColumn("TB_Clients_On_HAART_Transwoman", "TB clients on HAART Transwoman",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Initiated_PrEP_Transman", "Initiated PrEP Transman",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Initiated_PrEP_Transwoman", "Transwoman Initiated PrEP",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRANSWOMAN), indParams), "");
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
		cohortDsd.addColumn("Current_on_PrEP_Transman", "Current on PrEP Transman",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Current_on_PrEP_Transwoman", "Transwoman current on PrEP",
		    ReportUtils.map(moh731bIndicators.currentOnPrEP(TRANSWOMAN), indParams), "");
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
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_Transman", "Turning HIV+ While on PrEP Transman",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Turning_Positive_While_On_PrEP_Transwoman", "Transwoman turning Positive while on PrEP",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRANSWOMAN), indParams), "");
		
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
		cohortDsd.addColumn("Number_Exposed_To_HIV_Transman", "Number Exposed Transman",
		    ReportUtils.map(moh731bIndicators.numberExposed(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number_Exposed_To_HIV_Transwoman", "Transwoman exposed to HIV",
		    ReportUtils.map(moh731bIndicators.numberExposed(TRANSWOMAN), indParams), "");
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
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_Transman", "Receiving PEP Within 72 hours Transman",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Receiving_PEP_Within_72hrs_Transwoman","Transwoman receiving PEP within 72 hrs",
		    ReportUtils.map(moh731bIndicators.receivingPEPWithin72Hrs(TRANSWOMAN), indParams), "");*/
		/**
		 * Number completed PEP within 28 days: Number of people in each KP type who completed
		 * taking PEP within 28 days.
		 */
		/*	cohortDsd.addColumn("Completed_PEP_Within_28_Days_FSW", "Completed PEP within 28 days Fsw", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(FSW), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSM", "Completed PEP within 28 days Msm", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(MSM), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_MSW", "Completed PEP within 28 days Msw", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(MSW), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWID", "Completed PEP within 28 days Pwid", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(PWID), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_PWUD", "Completed PEP within 28 days Pwud", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(PWUD), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_Transman", "Completed PEP within 28 days Transman", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(TRANSMAN), indParams),"");
			cohortDsd.addColumn("Completed_PEP_Within_28_Days_Transwoman", "Transwoman completing PEP within 72 hrs", ReportUtils.map(moh731bIndicators.completedPEPWith28Days(TRANSWOMAN), indParams),"");
		*/
		//4.8 Peer education
		/**
		 * Receiving peer education: Number of people in each KP type who received peer education in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Received_Peer_education_FSW", "Received peer education Fsw",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(FSW), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_MSM", "Received peer education Msm",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(MSM), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_MSW", "Received peer education Msw",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(MSW), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_PWID", "Received peer education Pwid",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(PWID), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_PWUD", "Received peer education Pwud",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(PWUD), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_Transman", "Received peer education Transman",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Received_Peer_education_Transwoman", "Received peer education Transwoman",
		    ReportUtils.map(moh731bIndicators.receivedPeerEducation(TRANSWOMAN), indParams), "");
		/**
		 * Receiving peer education: Number of people in each KP type who received peer education in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Received_Clinical_services_FSW", "Received clinical services Fsw",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(FSW), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_MSM", "Received clinical services Msm",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(MSM), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_MSW", "Received clinical services Msw",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(MSW), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_PWID", "Received clinical services Pwid",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(PWID), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_PWUD", "Received clinical services Pwud",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(PWUD), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_Transman", "Received clinical services Transman",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Received_Clinical_services_Transwoman", "Received clinical services Transwoman",
		    ReportUtils.map(moh731bIndicators.receivedClinicalServices(TRANSWOMAN), indParams), "");
		
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
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_Transman", "On pre-ART Transman",
		    ReportUtils.map(moh731bIndicators.onSitePreART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_pre-ART_Transwoman", "On pre-ART people in closed settings",
		    ReportUtils.map(moh731bIndicators.onSitePreART(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_Transman", "Off site pre-ART Transman",
		    ReportUtils.map(moh731bIndicators.offSitePreART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_pre-ART_Transwoman", "Off site pre-ART people in closed settings",
		    ReportUtils.map(moh731bIndicators.offSitePreART(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		cohortDsd.addColumn("Total_pre_ART_Transman", "Transman total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Total_pre_ART_Transwoman", "Transwoman total on Pre-ART",
		    ReportUtils.map(moh731bIndicators.totalOnPreART(TRANSWOMAN), indParams), "");
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
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_Transman",
		    "Transman starting ART within the period on site",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_Starting_ART_Transwoman",
		    "Transwoman starting ART within the period on site ",
		    ReportUtils.map(moh731bIndicators.onSiteStartingART(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_Transman",
		    "Transman starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_Starting_ART_Transwoman",
		    "Transwoman starting ART within the period off-site",
		    ReportUtils.map(moh731bIndicators.offSiteStartingART(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		cohortDsd.addColumn("Total_starting_ART_Transman", "Transman total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Total_starting_ART_Transwoman", "Transwoman total starting ART",
		    ReportUtils.map(moh731bIndicators.totalStartingART(TRANSWOMAN), indParams), "");
		
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
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_Transman",
		    "Transman currently on ART on site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "On_site_currently_on_ART_Transwoman",
		    "Transwoman currently on ART on site within the period ",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOnSite(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_Transman",
		    "Transman currently on ART off site within the period",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Off_site_currently_on_ART_Transwoman",
		    "Transwoman currently on ART off site within the period ",
		    ReportUtils.map(moh731bIndicators.currentlyOnARTOffSite(TRANSWOMAN), indParams), kpAgeDisaggregation,
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
		cohortDsd.addColumn("Total_currently_ART_Transman", "Total Transman currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Total_currently_ART_Transwoman", "Total Transwoman currently on ART",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRANSWOMAN), indParams), "");
		
		// 5.4 Retention on ART
		/**
		 * 5.4 Retention on ART: On-site On ART at 12 months: Number of clients still on ART 12
		 * months after starting ART regardless of regimen.
		 */
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_FSW", "Fsw on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(FSW), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_MSM", "Msm on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(MSM), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_MSW", "Msw on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(MSW), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_PWID", "Pwid on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(PWID), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_PWUD", "Pwud on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(PWUD), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_Transman", "Transman on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("On_ART_At_12_Months_Onsite_Transwoman", "Transwoman on ART at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.onARTAt12MonthsOnsite(TRANSWOMAN), indParams), "");
		
		/**
		 * Net cohort at 12 months : On-site This refers to the number of clients started ART in the
		 * same month plus transfer ins and minus transfer outs. Take the number of patients in the
		 * original cohort, add the Transfers In (TIs), and subtract the Transfers Out (TOs) to get
		 * the net cohort
		 */
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_FSW", "Fsw net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(FSW), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_MSM", "Msm net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(MSM), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_MSW", "Msw net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(MSW), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_PWID", "Pwid net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(PWID), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_PWUD", "Pwud net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(PWUD), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_Transman", "Transman net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Net_Cohort_At_12_Months_Onsite_Transwoman", "Transwoman net cohort at 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.netCohortAt12MonthsOnsite(TRANSWOMAN), indParams), "");
		
		// 5.5 Viral Load Tracking
		/**
		 * Viral load result_12mths: On-site Number of people in the 12-month cohort in each KP type
		 * who had a viral load test on site at 12 months and whose results were available at the
		 * time of analysis
		 */
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_FSW", "Fsw viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(FSW), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_MSM", "Msm viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(MSM), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_MSW", "Msw viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(MSW), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_PWID", "Pwid viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(PWID), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_PWUD", "Pwud viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(PWUD), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_Transman", "Transman viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Onsite_Transwoman", "Transwoman viral load 12 months - Onsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOnsite(TRANSWOMAN), indParams), "");
		
/**
		 * Viral load <1000_12mths: On-site
		 * number of people in the 12-month cohort in each KP type whose 12-month viral load test
		 * was done on site and who have a viral load less than 1000 copies per ML.
		 */
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_FSW",
		    "Fsw viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(FSW), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_MSM",
		    "Msm viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(MSM), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_MSW",
		    "Msw viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(MSW), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_PWID",
		    "Pwid viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(PWID), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_PWUD",
		    "Pwud viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(PWUD), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_Transman",
		    "Transman viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Onsite_Transwoman",
		    "Transwoman viral load < 1000 cps/ml 12 months cohort - Onsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOnsite(TRANSWOMAN), indParams), "");
		
		/**
		 * Viral load result_12mths: Off-site Number of people in the 12-month cohort in each KP
		 * type who had a viral load test off site at 12 months and whose results were available at
		 * the time of analysis
		 */
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_FSW", "Fsw viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(FSW), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_MSM", "Msm viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(MSM), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_MSW", "Msw viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(MSW), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_PWID", "Pwid viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(PWID), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_PWUD", "Pwud viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(PWUD), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_Transman", "Transman viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Viral_Load_12_Months_Offsite_Transwoman", "Transwoman viral load 12 months - Offsite",
		    ReportUtils.map(moh731bIndicators.viralLoad12MonthsOffsite(TRANSWOMAN), indParams), "");
		
/**
		 * Viral load <1000_12mths: Off-site
		 * number of people in the 12-month cohort in each KP type whose 12-month viral load test
		 * was done on site and who have a viral load less than 1000 copies per ML.
		 */
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_FSW",
		    "Fsw viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(FSW), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_MSM",
		    "Msm viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(MSM), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_MSW",
		    "Msw viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(MSW), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_PWID",
		    "Pwid viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(PWID), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_PWUD",
		    "Pwud viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(PWUD), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_Transman",
		    "Transman viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Suppressed_Viral_Load_12_Months_Offsite_Transwoman",
		    "Transwoman viral load < 1000 cps/ml 12 months cohort - Offsite",
		    ReportUtils.map(moh731bIndicators.suppressedViralLoad12MonthsCohortOffsite(TRANSWOMAN), indParams), "");
		
		// 7.0 Overdose
		/**
		 * Number of PWID/PWUD who experienced overdose in the reporting period.
		 */
		cohortDsd.addColumn("Experienced_Overdose_PWID", "Pwid who experienced overdose",
		    ReportUtils.map(moh731bIndicators.experiencedOverdose(PWID), indParams), "");
		cohortDsd.addColumn("Experienced_Overdose_PWUD", "Pwud who experienced overdose",
		    ReportUtils.map(moh731bIndicators.experiencedOverdose(PWUD), indParams), "");
		
		/**
		 * Number of PWID/PWUD who had overdose and received naloxone
		 */
		cohortDsd.addColumn("Overdose_Given_Naloxone_PWID", "Pwid who had overdose and received naloxone",
		    ReportUtils.map(moh731bIndicators.experiencedOverdoseGivenNaloxone(PWID), indParams), "");
		cohortDsd.addColumn("Overdose_Given_Naloxone_PWUD", "Pwud who had overdose and received naloxone",
		    ReportUtils.map(moh731bIndicators.experiencedOverdoseGivenNaloxone(PWUD), indParams), "");
		
		/**
		 * Number of deaths due to overdose in the reporting period
		 */
		cohortDsd.addColumn("Overdose_Deaths", "Number of deaths due to overdose",
		    ReportUtils.map(moh731bIndicators.overdoseDeaths(), indParams), "");
		
		return cohortDsd;
		
	}
}
