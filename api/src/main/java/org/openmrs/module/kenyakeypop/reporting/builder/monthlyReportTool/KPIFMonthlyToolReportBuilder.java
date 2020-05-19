/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.builder.monthlyReportTool;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyakeypop.reporting.library.ETLReports.monthlyReport.MonthlyReportIndicatorLibrary;
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
 * Report builder for KPIF Monthly report
 */
@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.monthlyReport" })
public class KPIFMonthlyToolReportBuilder extends AbstractReportBuilder {
	
	@Autowired
	private CommonKpDimensionLibrary commonDimensions;
	
	@Autowired
	private MonthlyReportIndicatorLibrary monthlyReportIndicator;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	ColumnParameters below_15 = new ColumnParameters(null, "<15", "age=<15");
	
	ColumnParameters kp15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
	
	ColumnParameters kp20_to_24 = new ColumnParameters(null, "20-24", "age=20-24");
	
	ColumnParameters kp25_and_above = new ColumnParameters(null, "25+", "age=25+");
	
	ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
	
	List<ColumnParameters> kpAgeDisaggregation = Arrays.asList(below_15, kp15_to_19, kp20_to_24, kp25_and_above, colTotal);
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(kpDataSet(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	/**
	 * KP Dataset
	 * 
	 * @return the dataset
	 */
	protected DataSetDefinition kpDataSet() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.setName("monthlyReportingDataset");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.monthlyReportAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("KPType", ReportUtils.map(commonDimensions.kpType()));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";
		
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_FSW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_MSM", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_MSW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_PWUD", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_PWID", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_Transman", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_Transwoman", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_FSW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_MSM", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_MSW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_PWUD", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_PWID", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_Transman", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_Transwoman", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_FSW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_MSM", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_MSW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_PWUD", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_PWID", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_Transman", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_Transwoman", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_FSW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_MSM", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_MSW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_PWUD", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_PWID", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_Transman", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_Transwoman", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_FSW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_MSM", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_MSW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_PWUD", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_PWID", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_Transman", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_Transwoman", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_FSW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_MSM", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_MSW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_PWUD", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_PWID", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_Transman", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_Transwoman", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_FSW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_MSM", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_MSW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_PWUD", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_PWID", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_Transman", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_Transwoman", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_FSW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_MSM", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_MSW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_PWUD", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_PWID", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_Transman", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_Transwoman", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//STI_SCREEN
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_FSW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_MSM", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_MSW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_PWUD", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_PWID", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_Transman", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_Transwoman", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//HTS_SELF_ASSISTED
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_FSW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_MSM", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_MSW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_PWUD", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_PWID", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_Transman", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_Transwoman", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//HTS_SELF_UNASSISTED
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_FSW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_MSM", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_MSW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_PWUD", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_PWID", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_Transman", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_Transwoman", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//HTS_SELF_CONFIRMED_+VE
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_FSW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_MSM", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_MSW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_PWUD", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_PWID", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_Transman", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_Transwoman", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		//STI_SCREEN_POS
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_FSW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_MSM", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_MSW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_PWUD", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_PWID", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_Transman", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_Transwoman", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//STI_TX
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_FSW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_MSM", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_MSW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_PWUD", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_PWID", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_Transman", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_Transwoman", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//GBV_SCREEN
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_FSW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_MSM", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_MSW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_PWUD", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_PWID", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_Transman", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_Transwoman", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//GBV_POS
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_FSW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_MSM", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_MSW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_PWUD", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_PWID", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_Transman", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_Transwoman", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//GBV_LEGAL_SUPPORT
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_FSW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_MSM", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_MSW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_PWUD", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_PWID", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_Transman", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_Transwoman", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_NEW_DICE
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_FSW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_MSM", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_MSW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_PWUD", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_PWID", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_Transman", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_Transwoman", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_CURR_DICE
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_FSW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_MSM", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_MSW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_PWUD", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_PWID", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_Transman", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_Transwoman", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_RTT
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_FSW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_MSM", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_MSW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_PWUD", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_PWID", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_Transman", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_Transwoman", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//RETEST_ELIGIBLE
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_FSW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_MSM", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_MSW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_PWUD", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_PWID", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_Transman", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_Transwoman", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//RETEST_HTS_TST
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_FSW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_MSM", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_MSW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_PWUD", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_PWID", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_Transman", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_Transwoman", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//RETEST_HTS_TST_POS
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_FSW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_MSM", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_MSW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_PWUD", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_PWID", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_Transman", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_Transwoman", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//PNS_OFFERED
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_FSW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_MSM", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_MSW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_PWUD", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_PWID", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_Transman", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_Transwoman", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//PNS_ACCEPTED
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_FSW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_MSM", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_MSW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_PWUD", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_PWID", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_Transman", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_Transwoman", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//PNS_ELICITED
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_FSW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_MSM", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_MSW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_PWUD", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_PWID", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_Transman", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_Transwoman", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//KNOWN_POSITIVE_AT_ENTRY
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_FSW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_MSM", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_MSW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_PWUD", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_PWID", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_Transman", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_Transwoman", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//PNS_NEW_HIV_POS
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_FSW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_MSM", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_MSW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_PWUD", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_PWID", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_Transman", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_Transwoman", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		// PNS_NEW_HIV_NEG
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_FSW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_MSM", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_MSW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_PWUD", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_PWID", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_Transman", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_Transwoman", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//SUPPORT_GROUPS
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_FSW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_MSM", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_MSW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_PWUD", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_PWID", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_Transman", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_Transwoman", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		//GBV_CLINICAL
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_FSW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_MSM", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_MSW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_PWUD", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_PWID", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_Transman", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_Transwoman", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_DICE_(N)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_FSW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_MSM", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_MSW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_PWUD", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_PWID", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_Transman", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_Transwoman", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_DICE_(D)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_FSW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_MSM", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_MSW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_PWUD", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_PWID", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_Transman", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("Transman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_Transwoman", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("Transwoman"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_VERIFY_PEPFAR_SITE_(N)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with suppressed VL result  from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("FSW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("MSM"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_MSW",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("MSW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_PWUD",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("PWUD"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("PWID"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_Transman",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("Transman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_Transwoman",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_VERIFY_PEPFAR_SITE_(D)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_MSW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_PWUD",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("PWUD"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("PWID"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_Transman",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("Transman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_Transwoman",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("FSW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("MSM"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_MSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("MSW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_PWUD",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("PWUD"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("PWID"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_Transman",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("Transman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_Transwoman",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		//TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("FSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("MSM"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_MSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("MSW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_PWUD",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("PWUD"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("PWID"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_Transman",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("Transman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_Transwoman",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("Transwoman"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04", "05"));
		
		return cohortDsd;
		
	}
	
}
