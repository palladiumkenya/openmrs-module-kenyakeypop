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
 * Report builder for ETL MOH 731B Plus for Vulnerable Population
 */
@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.moh731PlusVP" })
public class ETLMOH731PlusBVulnerablePopReportBuilder extends AbstractReportBuilder {
	
	@Autowired
	private CommonKpDimensionLibrary commonDimensions;
	
	@Autowired
	private ETLMoh731PlusIndicatorLibrary moh731bIndicators;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String INMATE = "Prison Inmate";
	
	public static final String FISHER_FOLK = "Truck Driver";
	
	public static final String DISCORDANT_COUPLE = "Discordant Couple";
	
	public static final String TRUCK_DRIVER = "Truck Driver";
	
	ColumnParameters vpFemale15_to_19 = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
	
	ColumnParameters vpMale15_to_19 = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
	
	ColumnParameters vpFemale20_to_24 = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
	
	ColumnParameters vpMale20_to_24 = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
	
	ColumnParameters vpFemale25_to_29 = new ColumnParameters(null, "25-29, Female", "gender=F|age=25-29");
	
	ColumnParameters vpMale25_to_29 = new ColumnParameters(null, "25-29, Male", "gender=M|age=25-29");
	
	ColumnParameters vpFemale30_and_above = new ColumnParameters(null, "30+, Female", "gender=F|age=30+");
	
	ColumnParameters vpMale30_and_above = new ColumnParameters(null, "30+, Male", "gender=M|age=30+");
	
	ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
	
	ColumnParameters males = new ColumnParameters(null, "Male", "gender=M");
	
	ColumnParameters females = new ColumnParameters(null, "Female", "gender=F");
	
	List<ColumnParameters> genderDisaggregation = Arrays.asList(males, females);
	
	List<ColumnParameters> vpAgeGenderDisaggregation = Arrays.asList(vpFemale15_to_19, vpMale15_to_19, vpFemale20_to_24,
	    vpMale20_to_24, vpFemale25_to_29, vpMale25_to_29, vpFemale30_and_above, vpMale30_and_above, colTotal);
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("location", "Sub-County", String.class), new Parameter("dateBasedReporting", "",
		        String.class));
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(vpDataSet(), "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * VP Dataset
	 * 
	 * @return the dataset
	 */
	protected DataSetDefinition vpDataSet() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.setName("4");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.addParameter(new Parameter("location", "Sub-County", String.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.moh731BAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("kvpType", ReportUtils.map(commonDimensions.kvpType()));
		cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
		
		String indParams = "startDate=${startDate},endDate=${endDate},location=${location}";
		
		// 4.0 Number of VP Reached
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(FISHER_FOLK), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(INMATE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(DISCORDANT_COUPLE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Reached", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonths(TRUCK_DRIVER), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 4.1 Number KVP Reached with Defined Package
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(FISHER_FOLK), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(INMATE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(DISCORDANT_COUPLE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Reached With Defined Package", "",
		    ReportUtils.map(moh731bIndicators.kvpsReachedWithinLastThreeMonthsDefinedPackage(TRUCK_DRIVER), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 4.2 Number VkvpS Receiving Peer Education
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(FISHER_FOLK), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(INMATE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils
		        .addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Peer Education", "", ReportUtils.map(
		            moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(DISCORDANT_COUPLE), indParams),
		            vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Peer Education", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingPeerEducationWithinReportingPeriod(TRUCK_DRIVER), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 4.3 Number of Receiving Commodities
		// Number receiving Condoms
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Condoms", "",
		    ReportUtils.map(moh731bIndicators.receivingCondoms(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// Receiving Condoms per need
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Condoms Per need", "",
		    ReportUtils.map(moh731bIndicators.receivingCondomsPerNeedPerNeed(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		/**
		 * Number receiving needles & syringes: number of individuals in each VP type who received
		 * at least one needle & syringe, irrespective of service provision point.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Needles and Syringes", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringes(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number receiving needles & syringes per need number of individuals in each VP type who
		 * received needles & syringes based on their requirements derived from estimated number of
		 * injecting episodes per month.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Needles and Syringes Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingNeedlesAndSyringesPerNeed(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		/**
		 * Number receiving lubricants Number of individuals in each VP type who received lubricants
		 * based on their requirements.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Lubricants", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricants(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number receiving lubricants per need Number of individuals in each VP type who received
		 * lubricants based on their requirements
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving Lubricants Per Need", "",
		    ReportUtils.map(moh731bIndicators.receivingLubricantsPerNeed(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		//4.4 Violence Prevention and support
		/**
		 * Experience violence: number of people in each VP type who experienced sexual violence in
		 * the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Experiencing Sexual Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingSexualViolence(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Experiencing Sexual Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingSexualViolence(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Experiencing Sexual Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingSexualViolence(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Experiencing Sexual Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingSexualViolence(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		/**
		 * programme when they experienced Physical violence in the reporting period by VP type
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Experiencing Physical Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Experiencing Physical Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Experiencing Physical Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Experiencing Physical Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingPhysicalViolence(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number of VPs experiencing emotional or psychological violence
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(INMATE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Experiencing Emotional/Psychological Violence", "",
		    ReportUtils.map(moh731bIndicators.experiencingEmotionalOrPsychologicalViolence(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		/**
		 * Number of VPs who received violence support
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Received Violence Support", "",
		    ReportUtils.map(moh731bIndicators.receivedViolenceSupport(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Received Violence Support", "",
		    ReportUtils.map(moh731bIndicators.receivedViolenceSupport(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Received Violence Support", "",
		    ReportUtils.map(moh731bIndicators.receivedViolenceSupport(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Received Violence Support", "",
		    ReportUtils.map(moh731bIndicators.receivedViolenceSupport(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// 4.5 STI screening
		/**
		 * Number screened_STI number of individuals in each VP type who were screened for STI in
		 * the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Screened for STI", "",
		    ReportUtils.map(moh731bIndicators.screenedForSTI(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Diagnosed_STI: number of individuals in each VP type who were diagnosed with STI in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Diagnosed With STI", "",
		
		ReportUtils.map(moh731bIndicators.diagnosedWithSTI(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithSTI(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Treated_STI: number of individuals in each VP type who were treated for STI in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Treated for STI", "",
		    ReportUtils.map(moh731bIndicators.treatedForSTI(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// 4.6 HCV
		/**
		 * Number screened_HCV: number of individuals in each VP type who were screened for HCV in
		 * the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Screened For HCV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHCV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Positive_HCV: number of individuals in each VP type who were diagnosed with HCV in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Diagnosed with HCV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHCV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Treated_HCV: number of individuals in each VP type who were treated for HCV in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Treated For HCV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHCV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		//4.7 HBV (Hepatitis B)
		/**
		 * Number screened_HBV: number of individuals in each VP type who were screened for HBV in
		 * the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Screened For HBV", "",
		    ReportUtils.map(moh731bIndicators.screenedForHBV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Negative_HBV_vaccinated: number of individuals in each VP type who were vaccinated for
		 * HBV in the reporting period
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Vaccinated For HBV", "",
		    ReportUtils.map(moh731bIndicators.vaccinatedAgainstHBV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Positive_HBV: number of individuals in each VP type who were diagnosed with HBV in the
		 * reporting period
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Diagnosed with HBV", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithHBV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Treated_HBV: number of individuals in each VP type who were treated for HBV in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Treated For HBV", "",
		    ReportUtils.map(moh731bIndicators.treatedForHBV(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// 4.8 TB KEY POPULATIONS
		/**
		 * Number screened: number of individuals in each VP type who were screened for TB in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Screened For TB", "",
		    ReportUtils.map(moh731bIndicators.screenedTB(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number diagnosed: number of individuals in each VP type who were diagnose with TB in the
		 * reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ FISHER FOLK Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ INMATE Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ DISCORDANT COUPLE Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ TRUCK DRIVER Diagnosed With TB", "",
		    ReportUtils.map(moh731bIndicators.diagnosedTB(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number Started on TB TX: number of individuals in each VP type who were found positive
		 * with TB and started on treatment in the reporting period
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Started on TB Tx", "",
		    ReportUtils.map(moh731bIndicators.startedTBTX(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number of individuals in each VP type who were issued with TPT in the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(INMATE), indParams), genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Given TPT", "",
		    ReportUtils.map(moh731bIndicators.givenTPT(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * This is the count of TB patients who are HIV Postive in each active VP type. Calculate
		 * and enter the sum of all those TB patients who were already HIV Postive at the time of TB
		 * diagnosis and TB patients diagnosed with HIV during the reporting month
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ FISHER FOLK Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ INMATE Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ DISCORDANT COUPLE Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of HIV+ TRUCK DRIVER Dignosed With TB", "",
		    ReportUtils.map(moh731bIndicators.hivPosDiagnosedWithTB(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * TB clients on HAART: This is the count of TB patients who are receiving ART in each VP
		 * type. Calculate and enter the sum of all those TB patients who were already on HAART at
		 * the time of TB diagnosis and TB patients diagnosed with HIV who are started on HAART
		 * during the reporting month.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER With TB And on HAART", "",
		    ReportUtils.map(moh731bIndicators.tbClientsOnHAART(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		//4.9 PrEP
		/**
		 * Initiated PrEP: number of HIV negative persons in each VP type who have been started on
		 * PrEP during the reporting month after meeting the eligibility criteria for PrEP.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Initiated on PrEP", "",
		    ReportUtils.map(moh731bIndicators.initiatedPrEP(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Turning HIV positive while on PrEP: number of people on PrEP in each VP type who tested
		 * positive for HIV in the reporting period.
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Turning Positive While on PrEP", "",
		    ReportUtils.map(moh731bIndicators.turningPositiveWhileOnPrEP(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		/**
		 * Number of people on PrEP in each VP type who were diagonised with an STI in the reporting
		 * period
		 */
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER on PrEP Diagnosed With STI", "",
		    ReportUtils.map(moh731bIndicators.onPrEPDiagnosedWithSTI(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// 5.0 Mental Health
		//Number Screened for Mental Health
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Screened For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.screenedForMentalHealth(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		//Number Diagnosed Mental Health
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Diagnosed With Mental Health", "",
		    ReportUtils.map(moh731bIndicators.diagnosedWithMentalHealth(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		//Number Treated within the Facility for Mental Health
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(FISHER_FOLK), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(DISCORDANT_COUPLE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Treated For Mental Health", "",
		    ReportUtils.map(moh731bIndicators.treatedForMentalHealth(TRUCK_DRIVER), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		
		// 5.1 PEP
		// 5.2 HIV Testing KEY POPULATIONS
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(FISHER_FOLK), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(INMATE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(DISCORDANT_COUPLE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Tested For HIV", "",
		    ReportUtils.map(moh731bIndicators.kvpsTestedForHIV(TRUCK_DRIVER), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		//5.3 Receiving HIV Positive Results Vulnerable Populations
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(FISHER_FOLK), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(INMATE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(DISCORDANT_COUPLE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Receiving HIV+ Test Results", "",
		    ReportUtils.map(moh731bIndicators.kvpsReceivingHIVPosTestResults(TRUCK_DRIVER), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 5.4 Total Number Reached Living with HIV (Onsite & Offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK VPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(FISHER_FOLK), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE VPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(INMATE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE VPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(DISCORDANT_COUPLE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER VPLHIV Reached", "",
		    ReportUtils.map(moh731bIndicators.kvplhivReached(TRUCK_DRIVER), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 5.5 Starting_ART KEY   (Both onsite & Offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK VPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(FISHER_FOLK), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE VPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(INMATE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE VPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(DISCORDANT_COUPLE), indParams),
		    vpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER VPLHIV Starting ART", "",
		    ReportUtils.map(moh731bIndicators.totalKPLHIVStartingART(TRUCK_DRIVER), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		// 5.6 Current_ART (BOTH ONSITE & offsite)
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(FISHER_FOLK), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(INMATE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(DISCORDANT_COUPLE), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Current on ART", "",
		    ReportUtils.map(moh731bIndicators.totalCurrentlyOnART(TRUCK_DRIVER), indParams), vpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09"));
		
		//5.7 Viral load tracking  (Both Onsite & Offsite)
		
		//Eligible for VL
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK Eligible for VL Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.eligibleForVLWithinLast12Months(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE Eligible for VL Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.eligibleForVLWithinLast12Months(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE Eligible for VL Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.eligibleForVLWithinLast12Months(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER Eligible for VL Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.eligibleForVLWithinLast12Months(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		//Viral load tests in the last 12 months
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK With VL Tests Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadTestDoneWithinLast12Months(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE With VL Tests Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadTestDoneWithinLast12Months(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE With VL Tests Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadTestDoneWithinLast12Months(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER With VL Tests Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadTestDoneWithinLast12Months(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		//Viral load result in the last 12 months
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(INMATE), indParams), genderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of DISCORDANT COUPLE With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of TRUCK DRIVER With VL Results Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.viralLoadResultsWithinLast12Months(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		//Suppressed-< 50 copies
		EmrReportingUtils.addRow(cohortDsd, "Number of FISHER FOLK With Suppressed VL Below 50 cp/ml Within Last 12 Months",
		    "", ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(FISHER_FOLK), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "Number of INMATE With Suppressed VL Below 50 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(INMATE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd,
		    "Number of DISCORDANT COUPLE With Suppressed VL Below 50 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(DISCORDANT_COUPLE), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd,
		    "Number of TRUCK DRIVER With Suppressed VL Below 50 cp/ml Within Last 12 Months", "",
		    ReportUtils.map(moh731bIndicators.suppressedVLUnder50CpsPerMlWithinLast12Months(TRUCK_DRIVER), indParams),
		    genderDisaggregation, Arrays.asList("01", "02"));
		
		return cohortDsd;
		
	}
}
