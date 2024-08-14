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
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.moh731PlusKP" })
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
	 * kps Dataset
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
		cohortDsd.addDimension("kvpType", ReportUtils.map(commonDimensions.kvpType()));
		cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
		
		String indParams = "startDate=${startDate},endDate=${endDate},location=${location}";
		
		// 1.0 Number of kps Reached
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// 2.0 Number kps Reached with Defined Package
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(PWID), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(PWUD), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(TRANSWOMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(TRANSMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		// 2.1 Number kps Receiving Peer Education
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(MSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(PWID), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(PWUD), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(TRANSWOMAN), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(TRANSMAN), indParams),
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
		 * Number receiving needles & syringes: number of individuals in each kps type who received
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
		cohortDsd.addColumn("Number of TRANSMAN Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(TRANSWOMAN), indParams), "");
		
		/**
		 * Number receiving needles & syringes per need number of individuals in each kps type who
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
		 * Number receiving lubricants Number of individuals in each kps type who received
		 * lubricants based on their requirements.
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
		 * Number receiving lubricants per need Number of individuals in each kps type who received
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
		 * Experience violence: number of people in each kps type who experienced sexual violence in
		 * the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Experiencing Sexual Violence", "",
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
		    ReportUtils.map(moh731bIndicators.experiencingSexualViolence(TRANSWOMAN), indParams), "");
		/**
		 * programme when they experienced Physical violence in the reporting period by kps type
		 */
		cohortDsd.addColumn("Number of FSW Experiencing Physical Violence", "",
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
		    ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(TRANSWOMAN), indParams), "");
		
		/**
		 * Number of KPs experiencing emotional or psychological violence
		 */
		cohortDsd.addColumn("Number of FSW Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(PWID), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(PWUD), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(TRANSWOMAN), indParams), "");
		
		/**
		 * Number of KPs who received violence support
		 */
		cohortDsd.addColumn("Number of FSW Received Violence Support", "",
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
		    ReportUtils.map(moh731bIndicators.receivedViolenceSupport(TRANSWOMAN), indParams), "");
		
		// 2.4 STI screening
		/**
		 * Number screened_STI number of individuals in each kps type who were screened for STI in
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
		 * Diagnosed_STI: number of individuals in each kps type who were diagnosed with STI in the
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
		 * Treated_STI: number of individuals in each kps type who were treated for STI in the
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
		
		// 2.5 HCV
		/**
		 * Number screened_HCV: number of individuals in each kps type who were screened for HCV in
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
		 * Positive_HCV: number of individuals in each kps type who were diagnosed with HCV in the
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
		 * Treated_HCV: number of individuals in each kps type who were treated for HCV in the
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
		
		//2.6 HBV (Hepatitis B)
		/**
		 * Number screened_HBV: number of individuals in each kps type who were screened for HBV in
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
		 * Negative_HBV_vaccinated: number of individuals in each kps type who were vaccinated for
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
		 * Positive_HBV: number of individuals in each kps type who were diagnosed with HBV in the
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
		 * Treated_HBV: number of individuals in each kps type who were treated for HBV in the
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
		
		// 2.7 TB KEY POPULATIONS
		/**
		 * Number screened: number of individuals in each kps type who were screened for TB in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(PWID), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(PWUD), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRANSWOMAN), indParams), "");
		
		/**
		 * Number diagnosed: number of individuals in each kps type who were diagnose with TB in the
		 * reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(MSW), indParams), "");
		EmrReportingUtils
		        .addRow(cohortDsd, "Number of PWID Diagnosed With TB", "",
		            ReportUtils.map(moh731bIndicators.diagnosedTB(PWID), indParams), genderDisaggregation,
		            Arrays.asList("01", "02"));
		EmrReportingUtils
		        .addRow(cohortDsd, "Number of PWUD Diagnosed With TB", "",
		            ReportUtils.map(moh731bIndicators.diagnosedTB(PWUD), indParams), genderDisaggregation,
		            Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRANSWOMAN), indParams), "");
		
		/**
		 * Number Started on TB TX: number of individuals in each kps type who were found positive
		 * with TB and started on treatment in the reporting period
		 */
		cohortDsd.addColumn("Number of FSW Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(MSW), indParams), "");
		EmrReportingUtils
		        .addRow(cohortDsd, "Number of PWID Started on TB Tx", "",
		            ReportUtils.map(moh731bIndicators.startedTBTX(PWID), indParams), genderDisaggregation,
		            Arrays.asList("01", "02"));
		EmrReportingUtils
		        .addRow(cohortDsd, "Number of PWUD Started on TB Tx", "",
		            ReportUtils.map(moh731bIndicators.startedTBTX(PWUD), indParams), genderDisaggregation,
		            Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRANSWOMAN), indParams), "");
		
		/**
		 * This is the count of TB patients who are HIV Postive in each active kps type. Calculate
		 * and enter the sum of all those TB patients who were already HIV Postive at the time of TB
		 * diagnosis and TB patients diagnosed with HIV during the reporting month
		 */
		cohortDsd.addColumn("Number of HIV+ FSW Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(FSW), indParams), "");
		cohortDsd.addColumn("Number of HIV+ MSM Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(MSM), indParams), "");
		cohortDsd.addColumn("Number of HIV+ MSW Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ PWID Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ PWUD Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of HIV+ TRANSMAN Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of HIV+ TRANSWOMAN Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(TRANSWOMAN), indParams), "");
		
		/**
		 * Number of individuals in each kps type who were issued with TPT in the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Given TPT", "", ReportUtils.map(moh731bIndicators.givenTPT(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Given TPT", "", ReportUtils.map(moh731bIndicators.givenTPT(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Given TPT", "", ReportUtils.map(moh731bIndicators.givenTPT(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(PWID), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(PWUD), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(TRANSWOMAN), indParams), "");
		/**
		 * TB clients on HAART: This is the count of TB patients who are receiving ART in each kps
		 * type. Calculate and enter the sum of all those TB patients who were already on HAART at
		 * the time of TB diagnosis and TB patients diagnosed with HIV who are started on HAART
		 * during the reporting month.
		 */
		cohortDsd.addColumn("Number of FSW With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(TRANSWOMAN), indParams), "");
		
		//2.8 PrEP
		/**
		 * Initiated PrEP: number of HIV negative persons in each kps type who have been started on
		 * PrEP during the reporting month after meeting the eligibility criteria for PrEP.
		 */
		cohortDsd.addColumn("Number of FSW Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRANSWOMAN), indParams), "");
		
		/**
		 * Turning HIV positive while on PrEP: number of people on PrEP in each kps type who tested
		 * positive for HIV in the reporting period.
		 */
		cohortDsd.addColumn("Number of FSW Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRANSWOMAN), indParams), "");
		
		/**
		 * Number of people on PrEP in each kps type who were diagonised with an STI in the
		 * reporting period
		 */
		cohortDsd.addColumn("Number of FSW on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(TRANSWOMAN), indParams), "");
		
		// 2.9 Mental Health
		
		//Number Screened for Mental Health
		cohortDsd.addColumn("Number of FSW Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(TRANSWOMAN), indParams), "");
		
		//Number Diagnosed Mental Health
		cohortDsd.addColumn("Number of FSW Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(TRANSWOMAN), indParams), "");
		
		//Number Treated within the Facility for Mental Health
		cohortDsd.addColumn("Number of FSW Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(TRANSWOMAN), indParams), "");
		
		// 3.0 PEP KEY POPULATIONS
		// Number Exposed to HIV
		cohortDsd.addColumn("Number of FSW Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Exposed to HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsExposedToHIV(TRANSWOMAN), indParams), "");
		
		//Number Receive PEP <72hrs
		cohortDsd.addColumn("Number of FSW Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN Received PEP In Under 72 hrs", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivedPEPWithin72hrs(TRANSWOMAN), indParams), "");
		
		// 3.1 HIV Testing KEY POPULATIONS
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//3.2 Receiving HIV Positive Results KEY POPULATIONS
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// 3.3 Total Number Reached Living with HIV (Onsite & Offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN KPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// 3.4 Starting_ART KEY   (Both onsite & Offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN KPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// 3.5 Current_ART (BOTH ONSITE & offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FSW Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSM Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of MSW Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(MSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(PWID), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(PWUD), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSWOMAN Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRANSWOMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRANSMAN Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRANSMAN), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//3.6  Viral load tracking  (Both Onsite & Offsite)
		
		//Viral load result in the last 12 months
		cohortDsd.addColumn("Number of FSW With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(TRANSWOMAN), indParams), "");
		
		//Suppressed-< 200 copies
		cohortDsd.addColumn("Number of FSW With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(PWID), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(PWUD), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN With Suppressed VL Below 200 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder200CpsPerMlWithinLast12Months(TRANSWOMAN), indParams), "");
		
		//Suppressed-< 50 copies
		cohortDsd.addColumn("Number of FSW With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(FSW), indParams), "");
		cohortDsd.addColumn("Number of MSM With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(MSM), indParams), "");
		cohortDsd.addColumn("Number of MSW With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(MSW), indParams), "");
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(PWID), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(PWUD), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		cohortDsd.addColumn("Number of TRANSMAN With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(TRANSMAN), indParams), "");
		cohortDsd.addColumn("Number of TRANSWOMAN With Suppressed VL Below 50cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(TRANSWOMAN), indParams), "");
		
		//3.7  Overdose PWID/PWUD
		/**
		 * Number of PWID/PWUD who experienced overdose in the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Experienced Overdose", "",
		    ReportUtils.map(moh731bIndicators.experiencedOverdose(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Experienced Overdose", "",
		    ReportUtils.map(moh731bIndicators.experiencedOverdose(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		/**
		 * Number of PWID/PWUD who had overdose and received naloxone
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Given Naloxone", "",
		    ReportUtils.map(moh731bIndicators.experiencedOverdoseGivenNaloxone(PWID), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Given Naloxone", "",
		    ReportUtils.map(moh731bIndicators.experiencedOverdoseGivenNaloxone(PWUD), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number of deaths due to overdose in the reporting period
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of PWID Died of Overdose", "",
		    ReportUtils.map(moh731bIndicators.overdoseDeaths(), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of PWUD Died of Overdose", "",
		    ReportUtils.map(moh731bIndicators.overdoseDeaths(), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		
		return cohortDsd;
		
	}
}
