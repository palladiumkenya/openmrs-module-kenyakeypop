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
	
	ColumnParameters below_15_f = new ColumnParameters(null, "<15, Female", "gender=F|age=<15");
	
	ColumnParameters below_15_m = new ColumnParameters(null, "<15, Male", "gender=M|age=<15");
	
	ColumnParameters kp15_to_19 = new ColumnParameters(null, "15-19", "age=15-19");
	
	ColumnParameters kp15_to_19_f = new ColumnParameters(null, "15-19, Female", "gender=F|age=15-19");
	
	ColumnParameters kp15_to_19_m = new ColumnParameters(null, "15-19, Male", "gender=M|age=15-19");
	
	ColumnParameters kp20_to_24 = new ColumnParameters(null, "20-24", "age=20-24");
	
	ColumnParameters kp20_to_24_f = new ColumnParameters(null, "20-24, Female", "gender=F|age=20-24");
	
	ColumnParameters kp20_to_24_m = new ColumnParameters(null, "20-24, Male", "gender=M|age=20-24");
	
	ColumnParameters kp25_and_above = new ColumnParameters(null, "25+", "age=25+");
	
	ColumnParameters kp25_and_above_f = new ColumnParameters(null, "25+, Female", "gender=F|age=25+");
	
	ColumnParameters kp25_and_above_m = new ColumnParameters(null, "25+, Male", "gender=M|age=25+");
	
	ColumnParameters colTotal = new ColumnParameters(null, "Total", "");
	
	List<ColumnParameters> kpAgeDisaggregation = Arrays.asList(below_15, kp15_to_19, kp20_to_24, kp25_and_above);
	
	List<ColumnParameters> kpAgeGenderDisaggregation = Arrays.asList(below_15_f, below_15_m, kp15_to_19_f, kp15_to_19_m,
	    kp20_to_24_f, kp20_to_24_m, kp25_and_above_f, kp25_and_above_m);
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(kpDataset(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	protected DataSetDefinition kpDataset() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.setName("1");
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.addDimension("age", ReportUtils.map(commonDimensions.monthlyReportAgeGroups(), "onDate=${endDate}"));
		cohortDsd.addDimension("gender", ReportUtils.map(commonDimensions.gender()));
		cohortDsd.addDimension("KPType", ReportUtils.map(commonDimensions.kpType()));
		
		String indParams = "startDate=${startDate},endDate=${endDate}";
		
		//1. CONTACT_ALL - r8QR7Iqit3z
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_FSW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_MSM", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_MSW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_PWUD", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_PWID", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_TG_SW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_ALL_TG_NOT_SW", "All KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactAll("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		//2. KP_EVER_ENROL - azaGW41sWgz
		
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_FSW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_MSM", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_MSW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_PWUD", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_PWID", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_TG_SW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_TG_NOT_SW", "Ever enrolled",
		    ReportUtils.map(monthlyReportIndicator.everEnroll("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//3. KP_PREV
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_FSW", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_MSM", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_MSW", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_PWUD", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_PWID", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_TG_SW", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_TG_NOT_SW", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//4. CONTACT_NEW - nFg8SCUal7w
		
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_FSW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_MSM", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_MSW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_PWUD", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_PWID", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_TG_SW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_TG_NOT_SW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//5. CONTACT_HCW - WnS2CYAnhhg
		
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_FSW", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_MSM", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_MSW", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_PWUD", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_PWID", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_TG_SW", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "CONTACT_HCW_TG_NOT_SW", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//6. KP_NET_ENROLLED n35ZQZJ9qYj
		
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_FSW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_MSM", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_MSW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_PWUD", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_PWID", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_TG_SW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROLLED_TG_NOT_SW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//7. ENROL_KNOWN POSITIVE - pbIycq1Q1aR
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_FSW", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_MSM", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_MSW", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_PWUD", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_PWID", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_TG_SW", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_TG_NOT_SW", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//8. ENROL_NEW - VhJ7miYpzzZ
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_FSW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_MSM", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_MSW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_PWUD", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_PWID", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_TG_SW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_TG_NOT_SW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//9. HTS_TST_NEG - K4NPVo3Ee1E
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_FSW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_MSM", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_MSW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_PWUD", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_PWID", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_TG_SW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_TG_NOT_SW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//10. ENROL_HTS_TST - cakCs9wAFh1
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_FSW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_MSM", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_MSW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_PWUD", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_PWID", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_TG_SW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_TG_NOT_SW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//11.ENROL_HTS_TST_POS - cakCs9wAFh1
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_FSW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_MSM", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_MSW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_PWUD", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_PWID", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_TG_SW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_TG_NOT_SW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//12.STI_SCREEN - D1UmxuQdovX
		
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_FSW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_MSM", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_MSW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_PWUD", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_PWID", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_TG_SW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_TG_NOT_SW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//13.HTS_SELF_ASSISTED - tePCfFFkby5
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_FSW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_MSM", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_MSW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_PWUD", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_PWID", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_TG_SW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_TG_NOT_SW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//14. HTS_SELF_UNASSISTED - f9yUfz3UW7m
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_FSW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_MSM", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_MSW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_PWUD", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_PWID", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_TG_SW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_TG_NOT_SW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//15. HTS_SELF_CONFIRMED_+VE - oeX7WkboEik
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_FSW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_MSM", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_MSW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_PWUD", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_PWID", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_TG_SW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_TG_NOT_SW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//16.STI_SCREEN_POS - cccAY22KB4P
		
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_FSW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_MSM", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_MSW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_PWUD", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_PWID", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_TG_SW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_TG_NOT_SW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//17. STI_TX - EbkN2jKcxym
		
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_FSW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_MSM", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_MSW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_PWUD", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_PWID", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_TG_SW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_TG_NOT_SW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//18. GBV_SCREEN - mrSyW3UFeWI
		
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_FSW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_MSM", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_MSW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_PWUD", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_PWID", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_TG_SW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_SCREEN_TG_NOT_SW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//19. GBV_POS - J8GBNrQbDs7
		
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_FSW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_MSM", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_MSW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_PWUD", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_PWID", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_TG_SW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_POS_TG_NOT_SW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//20. GBV_LEGAL_SUPPORT - vY1Uy4cUKiG
		
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_FSW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_MSM", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_MSW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_PWUD", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_PWID", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_TG_SW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_LEGAL_SUPPORT_TG_NOT_SW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//21. TX_NEW_DICE - ayMFkwavWB7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_FSW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_MSM", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_MSW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_PWUD", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_PWID", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_TG_SW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_TG_NOT_SW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//22. TX_CURR_DICE - bVnurJnr7SM
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_FSW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_MSM", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_MSW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_PWUD", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_PWID", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_TG_SW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_TG_NOT_SW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//23. TX_RTT - Hm9jOlXPqlx
		
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_FSW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_MSM", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_MSW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_PWUD", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_PWID", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_TG_SW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_TG_NOT_SW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//24. RETEST_ELIGIBLE - kOmj7azOXf0
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_FSW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_MSM", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_MSW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_PWUD", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_PWID", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_TG_SW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_TG_NOT_SW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//25. RETEST_HTS_TST - KxPBLMcMfXX
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_FSW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_MSM", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_MSW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_PWUD", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_PWID", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_TG_SW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_TG_NOT_SW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//26. RETEST_HTS_TST_POS - wHTtM3KK8xt
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_FSW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_MSM", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_MSW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_PWUD", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_PWID", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_TG_SW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_TG_NOT_SW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//27. PNS_OFFERED - IRLMFsFMPeE
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_FSW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_MSM", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_MSW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_PWUD", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_PWID", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_TG_SW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_TG_NOT_SW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//28. PNS_ACCEPTED - Aj6vP6Zlw7A
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_FSW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_MSM", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_MSW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_PWUD", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_PWID", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_TG_SW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_TG_NOT_SW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//29. PNS_ELICITED - Om2TkuDV50S
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_FSW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_MSM", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_MSW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_PWUD", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_PWID", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_TG_SW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_TG_NOT_SW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//30. PNS_KNOWN_POSITIVE_ENTRY - KMxOHc1sq6A
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_FSW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_MSM", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_MSW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_PWUD", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_PWID", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_TG_SW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_TG_NOT_SW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//31. PNS_NEW_HIV_POS - FZJvTrHEG9I
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_FSW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_MSM", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_MSW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_PWUD", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_PWID", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_TG_SW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_TG_NOT_SW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//32. PNS_NEW_HIV_NEG - zJTUyNwTvCX
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_FSW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_MSM", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_MSW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_PWUD", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_PWID", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_TG_SW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_TG_NOT_SW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//33. SUPPORT_GROUPS - zfiXKEUJgR8
		
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_FSW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_MSM", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_MSW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_PWUD", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_PWID", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_TG_SW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "SUPPORT_GROUPS_TG_NOT_SW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//34. GBV_CLINICAL - ZgJZbVBlRTP
		
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_FSW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_MSM", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_MSW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_PWUD", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_PWID", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_TG_SW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_CLINICAL_TG_NOT_SW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//35. TX_PVLS_DICE_(N) - gKzh3U8KiEF
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_FSW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_MSM", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_MSW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_PWUD", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_PWID", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_TG_SW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_TG_NOT_SW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//36. TX_PVLS_DICE (D) - Ob0tw9E09m6
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_FSW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_MSM", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_MSW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_PWUD", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_PWID", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_TG_SW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_TG_NOT_SW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//37. TX_PVLS_VERIFY_PEPFAR_SITE_(N) - HDX9dYK1S8C
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with suppressed VL result  from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_MSW",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_PWUD",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_TG_SW",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_TG_NOT_SW",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//38. TX_PVLS_VERIFY_PEPFAR_SITE_(D) - FNVxVWLWSlP
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_MSW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_PWUD",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_TG_SW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_TG_NOT_SW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//39.TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N) -  YFVW39TeCC5
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_MSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_PWUD",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_TG_SW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_TG_NOT_SW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//40. TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D) - MyaBDZA5l8o
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_MSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_PWUD",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_TG_SW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_TG_NOT_SW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//41. PrEP_CURR_VERIFY_PEPFAR_SITE - CCdVD9plUwm
		
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_FSW",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_MSM",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_MSW",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_PWUD",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_PWID",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_TG_SW",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_TG_NOT_SW",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//42. PrEP_CURR_VERIFY_NON_PEPFAR_SITE - nWMnSfWQw3F
		
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_FSW",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_MSM",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_MSW",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_PWUD",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_PWID",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_TG_SW",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_TG_NOT_SW",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//43. TX_NEW_VERIFY_PEPFAR_SITE - bVPCYIWpJAs
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_FSW",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_MSM",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_MSW",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_PWUD",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_PWID",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_TG_SW",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_TG_NOT_SW",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//44. TX_NEW_VERIFY_NON_PEPFAR - zYZe7ERpD0Z
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_FSW",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_MSM",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_MSW",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_PWUD",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_PWID",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_TG_SW",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_TG_NOT_SW",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//45. TX_CURR_VERIFY_PEPFAR_SITE - mKinaTjSI8O
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_FSW",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_MSM",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_MSW",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_PWUD",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_PWID",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_TG_SW",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_TG_NOT_SW",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//46. TX_CURR_VERIFY_NON_PEPFAR_SITE - UG1nFQQ7Yz2
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_FSW",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_MSM",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_MSW",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_PWUD",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_PWID",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_TG_SW",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_TG_NOT_SW",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//47. KP_CURR - IfZnCTNMiec
		
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_FSW", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_MSM", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_MSW", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_PWUD", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_PWID", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_TG_SW", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_TG_NOT_SW", "Cuurent on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//48. KPLHIV_CURR - U8ah8a3Up1f
		
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_FSW", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_MSM", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_MSW", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_PWUD", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_PWID", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_TG_SW", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_TG_NOT_SW", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//49. TX_LTFU_RECENT - ayMFkwavWB7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_FSW", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_MSM", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_MSW", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_PWUD", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_PWID", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_TG_SW", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_TG_NOT_SW", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//50. KP_EVER_POS - vKtYvZGWdQ3
		
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_FSW", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_MSM", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_MSW", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_PWUD", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_PWID", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_TG_SW", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_TG_NOT_SW", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//51. TX_EVER_DICE - PhOOi3jpyU5
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_FSW", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_MSM", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_MSW", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_PWUD", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_PWID", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_TG_SW", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_TG_NOT_SW", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//52. TX_EVER_VERIFY_PEPFAR_SITE - E26PZb2eocw
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_FSW", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_MSM", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_MSW", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_PWUD", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_PWID", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_TG_SW", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_TG_NOT_SW", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//53. TX_EVER_VERIFY_NON_PEPFAR_SITE - hotT1X2G7Ss
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_FSW", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_MSM", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_MSW", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_PWUD", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_PWID", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_TG_SW", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_TG_NOT_SW",
		    "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//54. TX_PVLS_ELIGIBLE_DICE - wEATMdiockB
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_FSW",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_MSM",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_MSW",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_PWUD",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_PWID",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_TG_SW",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_TG_NOT_SW",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//55. TX_PVLS_ELIGIBLE_DONE_DICE- mhkO6IPf1nE
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_FSW",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_MSM",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_MSW",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_PWUD",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_PWID",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_TG_SW",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_TG_NOT_SW",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//56.TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE - tcKlzWxQG6w
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_FSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_MSM",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_MSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_PWUD",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_PWID",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_TG_SW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_TG_NOT_SW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//57. TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE - cn1u70K6fMZ
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_FSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_MSM",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_MSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_PWUD",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_PWID",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_TG_SW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_TG_NOT_SW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//58. TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE - b5pkOaXA4d7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_FSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_MSM",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_MSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_PWUD",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_PWID",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_TG_SW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_TG_NOT_SW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//59. TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE - O4M0FcApmzi
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_FSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("(\"FSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_MSM",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("(\"MSM\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_MSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("(\"MSW\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_PWUD",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("(\"PWUD\")"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_PWID",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("(\"PWID\")"), indParams),
		    kpAgeGenderDisaggregation, Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_TG_SW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("TRANSGENDER_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_TG_NOT_SW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR("TRANSGENDER_NOT_SW"), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//60. MMD
		EmrReportingUtils.addRow(cohortDsd, "MMD_FSW", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("(\"FSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_MSM", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("(\"MSM\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_MSW", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("(\"MSW\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_PWUD", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("(\"PWUD\")"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_PWID", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("(\"PWID\")"), indParams), kpAgeGenderDisaggregation,
		    Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_TG_SW", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("TRANSGENDER_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "MMD_TG_NOT_SW", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART("TRANSGENDER_NOT_SW"), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		return cohortDsd;
	}
}
