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
		
		return cohortDsd;
		
	}
	
}
