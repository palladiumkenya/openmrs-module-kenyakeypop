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
	
	public static final String FSW = "FSW";
	
	public static final String MSM = "MSM";
	
	public static final String PWID = "PWID";
	
	public static final String TG = "Transgender";
	
	public static final String IN_PRISONS = "People in prison and other closed settings";
	
	ColumnParameters female = new ColumnParameters(null, "F", "gender=F");
	
	ColumnParameters male = new ColumnParameters(null, "M", "gender=M");
	
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
	
	List<ColumnParameters> kpGenderDisaggregation = Arrays.asList(female, male);
	
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
		
		//1. KP_CONTACT_ALL - r8QR7Iqit3z
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_ALL_FSW", "All KP Contact fsw",
		    ReportUtils.map(monthlyReportIndicator.contactAll(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_ALL_MSM", "All KP Contact msm",
		    ReportUtils.map(monthlyReportIndicator.contactAll(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_ALL_PWID", "All KP Contact PWID",
		    ReportUtils.map(monthlyReportIndicator.contactAll(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_ALL_IN_PRISONS", "All KP Contact people in prisons",
		    ReportUtils.map(monthlyReportIndicator.contactAll(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_ALL_TG", "All KP Contact Transgender",
		    ReportUtils.map(monthlyReportIndicator.contactAll(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		//2. KP_EVER_ENROL - azaGW41sWgz
		
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_FSW", "Ever enrolled FSWs",
		    ReportUtils.map(monthlyReportIndicator.everEnroll(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_MSM", "Ever enrolled MSMs",
		    ReportUtils.map(monthlyReportIndicator.everEnroll(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_PWID", "Ever enrolled PWIDs",
		    ReportUtils.map(monthlyReportIndicator.everEnroll(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_IN_PRISONS", "Ever enrolled People in Prisons",
		    ReportUtils.map(monthlyReportIndicator.everEnroll(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_ENROLLED_TG", "Ever enrolled Transgenders",
		    ReportUtils.map(monthlyReportIndicator.everEnroll(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//3. KP_PREV
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_FSW", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_MSM", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_PWID", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_IN_PRISONS", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_PREV_TG", "Received care for the first time this year",
		    ReportUtils.map(monthlyReportIndicator.kpPrev(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//4. CONTACT_NEW - nFg8SCUal7w
		
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_FSW", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_MSM", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_PWID", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_IN_PRISONS", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_NEW_TG", "New KP Contact",
		    ReportUtils.map(monthlyReportIndicator.contactNew(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//5. CONTACT_HCW - WnS2CYAnhhg
		
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_HCW_FSW", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_HCW_MSM", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_HCW_PWID", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_HCW_IN_PRISONS", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CONTACT_HCW_TG", "Had contact with a health care worker",
		    ReportUtils.map(monthlyReportIndicator.contactHCW(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//6. KP_NET_ENROL n35ZQZJ9qYj
		
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROL_FSW", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROL_MSM", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROL_PWID", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROL_IN_PRISONS", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_NET_ENROL_TG", "Net Enrolled KP",
		    ReportUtils.map(monthlyReportIndicator.netEnroll(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//7. ENROL_KNOWN POSITIVE - pbIycq1Q1aR
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_FSW", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_MSM", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_PWID", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_IN_PRISONS", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_KNOWN_POSITIVE_TG", "Known positive at enrolment",
		    ReportUtils.map(monthlyReportIndicator.kpKnownPositiveEnrolled(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//8. ENROL_NEW - VhJ7miYpzzZ
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_FSW", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_MSM", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_PWID", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_IN_PRISONS", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_NEW_TG", "Newly Enrolled",
		    ReportUtils.map(monthlyReportIndicator.enrollNew(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//9. HTS_TST_NEG - K4NPVo3Ee1E
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_FSW", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_MSM", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_PWID", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_IN_PRISONS", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_TST_NEG_TG", "Tested HIV -ve",
		    ReportUtils.map(monthlyReportIndicator.htsTestedNegative(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//10. ENROL_HTS_TST - cakCs9wAFh1
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_FSW", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_MSM", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_PWID", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_IN_PRISONS", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_TG", "Newly enrolled and Tested for HIV",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTst(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//11.ENROL_HTS_TST_POS - cakCs9wAFh1
		
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_FSW", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_MSM", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_PWID", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_IN_PRISONS", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "ENROL_HTS_TST_POS_TG", "Newly enrolled and Tested for HIV+",
		    ReportUtils.map(monthlyReportIndicator.enrollHtsTstPos(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//12.STI_SCREEN - D1UmxuQdovX
		
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_FSW", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_MSM", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_PWID", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_IN_PRISONS", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_TG", "Screened for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedForSTI(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//13.HTS_SELF_ASSISTED - tePCfFFkby5
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_FSW", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_MSM", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_PWID", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_IN_PRISONS", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_ASSISTED_TG", "Self Assisted HIV Testing",
		    ReportUtils.map(monthlyReportIndicator.assistedSelfTested(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//14. HTS_SELF_UNASSISTED - f9yUfz3UW7m
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_FSW", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_MSM", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_PWID", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_IN_PRISONS", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_UNASSISTED_TG", "Unassisted HIV self testing",
		    ReportUtils.map(monthlyReportIndicator.unAssistedSelfTested(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//15. HTS_SELF_CONFIRMED_+VE - oeX7WkboEik
		
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_FSW", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_MSM", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_PWID", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_IN_PRISONS", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "HTS_SELF_CONFIRMED_+VE_TG", "Confirmed HIV+ on Self Test",
		    ReportUtils.map(monthlyReportIndicator.htsTstSelfConfirmedPositive(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//16.STI_SCREEN_POS - cccAY22KB4P
		
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_FSW", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_MSM", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_PWID", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_IN_PRISONS", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_SCREEN_POS_TG", "Screened +ve for STI",
		    ReportUtils.map(monthlyReportIndicator.screenedPositiveForSTI(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//17. STI_TX - EbkN2jKcxym
		
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_FSW", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_MSM", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_PWID", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_IN_PRISONS", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "STI_TX_TG", "Started STI Treatment",
		    ReportUtils.map(monthlyReportIndicator.startedSTITx(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//18. GEND_GBV_SCREEN - mrSyW3UFeWI
		
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_SCREEN_FSW", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_SCREEN_MSM", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_SCREEN_PWID", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_SCREEN_IN_PRISONS", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_SCREEN_TG", "Screened for GBV",
		    ReportUtils.map(monthlyReportIndicator.screenedForGbv(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//19. GEND_GBV_POS - J8GBNrQbDs7
		
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_POS_FSW", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_POS_MSM", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_POS_PWID", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_POS_IN_PRISONS", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_POS_TG", "Experienced GBV",
		    ReportUtils.map(monthlyReportIndicator.experiencedGbv(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//34. GBV_GBV - ZgJZbVBlRTP
		
		EmrReportingUtils.addRow(cohortDsd, "GBV_GBV_FSW", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_GBV_MSM", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_GBV_PWID", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_GBV_IN_PRISONS", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GBV_GBV_TG", "Received Clinical services for GBV case",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvClinicalCare(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//20. GEND_GBV_LEGAL_SUPPORT - vY1Uy4cUKiG
		
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_LEGAL_SUPPORT_FSW", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_LEGAL_SUPPORT_MSM", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_LEGAL_SUPPORT_PWID", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_LEGAL_SUPPORT_IN_PRISONS", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "GEND_GBV_LEGAL_SUPPORT_TG", "Received Legal support for GBV",
		    ReportUtils.map(monthlyReportIndicator.receivedGbvLegalSupport(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//48. KPLHIV_CURR - U8ah8a3Up1f
		
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_FSW", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_MSM", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_PWID", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_IN_PRISONS", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KPLHIV_CURR_TG", "KP - Current living with HIV",
		    ReportUtils.map(monthlyReportIndicator.kpLHIVCurr(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//21. TX_NEW_DICE - ayMFkwavWB7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_FSW", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_MSM", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_PWID", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_IN_PRISONS", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_DICE_TG", "Started ART this month",
		    ReportUtils.map(monthlyReportIndicator.newOnARTKP(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//22. TX_CURR_DICE - bVnurJnr7SM
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_FSW", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_MSM", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_PWID", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_IN_PRISONS", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_DICE_TG", "Current on ART",
		    ReportUtils.map(monthlyReportIndicator.currOnARTKP(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//23. TX_RTT - Hm9jOlXPqlx
		
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_FSW", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_MSM", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_PWID", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_IN_PRISONS", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_RTT_TG", "Returned to treatment",
		    ReportUtils.map(monthlyReportIndicator.kplhivTXRtt(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//24. RETEST_ELIGIBLE - kOmj7azOXf0
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_FSW", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_MSM", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_PWID", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_IN_PRISONS", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_ELIGIBLE_TG", "Eligible for Retest",
		    ReportUtils.map(monthlyReportIndicator.eligibleForRetest(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//25. RETEST_HTS_TST - KxPBLMcMfXX
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_FSW", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_MSM", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_PWID", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_IN_PRISONS", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_TG", "Retested for HIV",
		    ReportUtils.map(monthlyReportIndicator.htsTstEligibleRetested(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//26. RETEST_HTS_TST_POS - wHTtM3KK8xt
		
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_FSW", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_MSM", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_PWID", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_IN_PRISONS", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "RETEST_HTS_TST_POS_TG", "Retested HIV Positive",
		    ReportUtils.map(monthlyReportIndicator.retestedHIVPositive(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//27. PNS_OFFERED - IRLMFsFMPeE
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_FSW", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_MSM", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_PWID", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_IN_PRISONS", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_OFFERED_TG", "Offered PNS",
		    ReportUtils.map(monthlyReportIndicator.offeredPNS(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//28. PNS_ACCEPTED - Aj6vP6Zlw7A
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_FSW", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_MSM", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_PWID", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_IN_PRISONS", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ACCEPTED_TG", "Accepted PNS",
		    ReportUtils.map(monthlyReportIndicator.acceptedPNS(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//29. PNS_ELICITED - Om2TkuDV50S
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_FSW", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_MSM", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_PWID", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_IN_PRISONS", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_ELICITED_TG", "Elicited PNS",
		    ReportUtils.map(monthlyReportIndicator.elicitedPNS(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//30. PNS_KNOWN_POSITIVE_ENTRY - KMxOHc1sq6A
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_FSW", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_MSM", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_PWID", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_IN_PRISONS", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_KNOWN_POSITIVE_ENTRY_TG", "PNS known positive at entry",
		    ReportUtils.map(monthlyReportIndicator.pnsKnownPositiveAtEntry(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//31. PNS_NEW_HIV_POS - FZJvTrHEG9I
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_FSW", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_MSM", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_PWID", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_IN_PRISONS", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_POS_TG", "PNS tested Positive",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedPositive(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//32. PNS_NEW_HIV_NEG - zJTUyNwTvCX
		
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_FSW", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_MSM", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_PWID", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_IN_PRISONS", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PNS_NEW_HIV_NEG_TG", "PNS tested Negative",
		    ReportUtils.map(monthlyReportIndicator.pnsTestedNegative(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//33. TX_SUPPORT_GROUPS - zfiXKEUJgR8
		
		EmrReportingUtils.addRow(cohortDsd, "TX_SUPPORT_GROUPS_FSW", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_SUPPORT_GROUPS_MSM", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_SUPPORT_GROUPS_PWID", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_SUPPORT_GROUPS_IN_PRISONS", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_SUPPORT_GROUPS_TG", "Enrolled in support group",
		    ReportUtils.map(monthlyReportIndicator.kpEnrolledInARTSupportGroup(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//41. PrEP_CURR_VERIFY_PEPFAR_SITE - CCdVD9plUwm
		
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_FSW",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_MSM",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_PWID",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_IN_PRISONS",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_PEPFAR_SITE_TG",
		    "Clients initiated on PrEP from another PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPPepfar(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//42. PrEP_CURR_VERIFY_NON_PEPFAR_SITE - nWMnSfWQw3F
		
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_FSW",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_MSM",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_PWID",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_VERIFY_NON_PEPFAR_SITE_TG",
		    "Clients initiated on PrEP from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.referredAndInitiatedPrEPNonPepfar(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//43. TX_NEW_VERIFY_PEPFAR_SITE - bVPCYIWpJAs
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_FSW",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_MSM",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_PWID",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_IN_PRISONS",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_PEPFAR_SITE_TG",
		    "Clients initiated on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTOtherPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//44. TX_NEW_VERIFY_NON_PEPFAR_SITE - zYZe7ERpD0Z
		
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_SITE_FSW",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_SITE_MSM",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_SITE_PWID",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_NEW_VERIFY_NON_PEPFAR_SITE_TG",
		    "Clients initiated on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivInitiatedARTNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//46. TX_CURR_VERIFY_NON_PEPFAR_SITE - UG1nFQQ7Yz2
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_FSW",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_MSM",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_PWID",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_NON_PEPFAR_SITE_TG",
		    "Clients Current on ART from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//44. TX_CURR_VERIFY_PEPFAR_SITE - mKinaTjSI8O
		
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_FSW",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_MSM",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_PWID",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_IN_PRISONS",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_CURR_VERIFY_PEPFAR_SITE_TG",
		    "Clients Current on ART from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivCurrentOnARTOtherPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//47. KP_CURR - IfZnCTNMiec
		
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_FSW", "Current on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_MSM", "Current on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_PWID", "Current on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_IN_PRISONS", "Current on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_CURR_TG", "Current on KP",
		    ReportUtils.map(monthlyReportIndicator.kpCurr(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//49. TX_LTFU_RECENT - ayMFkwavWB7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_FSW", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_MSM", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_PWID", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_IN_PRISONS", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_LTFU_RECENT_TG", "Recently lost to followup",
		    ReportUtils.map(monthlyReportIndicator.kplhivLTFURecently(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//50. KP_EVER_POS - vKtYvZGWdQ3
		
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_FSW", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_MSM", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_PWID", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_IN_PRISONS", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "KP_EVER_POS_TG", " KP Ever Positive",
		    ReportUtils.map(monthlyReportIndicator.kpEverPos(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//51. TX_EVER_DICE - PhOOi3jpyU5
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_FSW", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_MSM", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_PWID", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_IN_PRISONS", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_DICE_TG", "Ever on ART in this DICE",
		    ReportUtils.map(monthlyReportIndicator.txEverDice(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//52. TX_EVER_VERIFY_PEPFAR_SITE - E26PZb2eocw
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_FSW", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_MSM", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_PWID", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_IN_PRISONS", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_PEPFAR_SITE_TG", "Ever on ART in other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//53. TX_EVER_VERIFY_NON_PEPFAR_SITE - hotT1X2G7Ss
		
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_FSW", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_MSM", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_PWID", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_EVER_VERIFY_NON_PEPFAR_SITE_TG", "Ever on ART in other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.txEverVerifyNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//54. TX_PVLS_ELIGIBLE_DICE - wEATMdiockB
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_FSW",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_MSM",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_PWID",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_IN_PRISONS",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DICE_TG",
		    "On ART in this DICE and Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDice(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//36. TX_PVLS_DICE (D) - Ob0tw9E09m6
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_FSW", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_MSM", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_PWID", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_IN_PRISONS", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(D)_TG", "KPLHIV with VL result ",
		    ReportUtils.map(monthlyReportIndicator.kplhivWithVlResult(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//35. TX_PVLS_DICE_(N) - gKzh3U8KiEF
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_FSW", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_MSM", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_PWID", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_IN_PRISONS", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_DICE_(N)_TG", "KPLHIV with Suppressed VL ",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVl(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//56.TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE - tcKlzWxQG6w
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_FSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_MSM",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_PWID",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_IN_PRISONS",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_PEPFAR_SITE_TG",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//38. TX_PVLS_VERIFY_PEPFAR_SITE_(D) - FNVxVWLWSlP
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_IN_PRISONS",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(D)_TG",
		    "KPLHIV with VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtOtherPEPFARSite(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//37. TX_PVLS_VERIFY_PEPFAR_SITE_(N) - HDX9dYK1S8C
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with suppressed VL result  from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite(FSW), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite(MSM), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite(PWID), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_IN_PRISONS",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_PEPFAR_SITE_(N)_TG",
		    "KPLHIV with suppressed VL result from other PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtOtherPEPFARSite(TG), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		
		//58. TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE - b5pkOaXA4d7
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_FSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_MSM",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_PWID",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_VERIFY_NON_PEPFAR_SITE_TG",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleVerifyNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//40. TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D) - MyaBDZA5l8o
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_IN_PRISONS",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(D)_TG",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivVlResultArtNonPEPFARSite(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//39.TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N) -  YFVW39TeCC5
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_FSW",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_MSM",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_PWID",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite(PWID), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_IN_PRISONS",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_VERIFY_NON_PEPFAR_SITE_(N)_TG",
		    "KPLHIV with VL result from other non PEPFAR site",
		    ReportUtils.map(monthlyReportIndicator.kplhivSuppressedVlArtNonPEPFARSite(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//55. TX_PVLS_ELIGIBLE_DONE_DICE- mhkO6IPf1nE
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_FSW",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_MSM",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_PWID",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_IN_PRISONS",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_DICE_TG",
		    "On ART in this DICE and Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneDice(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//57. TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE - cn1u70K6fMZ
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_FSW",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_MSM",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_PWID",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_IN_PRISONS",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_PEPFAR_SITE_TG",
		    "On ART in other PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//59. TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE - O4M0FcApmzi
		
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_FSW",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_MSM",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_PWID",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_IN_PRISONS",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR(IN_PRISONS), indParams),
		    kpAgeDisaggregation, Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_PVLS_ELIGIBLE_DONE_VERIFY_NON_PEPFAR_SITE_TG",
		    "On ART in other non PEPFAR sites Eligible for VL within last 12 months whose samples were taken",
		    ReportUtils.map(monthlyReportIndicator.txPvlsEligibleDoneVerifyNonPEPFAR(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		
		//60. TX_MMD
		EmrReportingUtils.addRow(cohortDsd, "TX_MMD_FSW", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_MMD_MSM", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_MMD_PWID", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_MMD_IN_PRISONS", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "TX_MMD_TG", "Multi month appointments",
		    ReportUtils.map(monthlyReportIndicator.kpOnMultiMonthART(TG), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		//61. PrEP
		EmrReportingUtils.addRow(cohortDsd, "PrEP_SCREEN_FSW", "HIV negative FSW KP Screened PrEP",
		    ReportUtils.map(monthlyReportIndicator.kpPrepScreened(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_SCREEN_MSM", "HIV negative MSM KP Screened PrEP",
		    ReportUtils.map(monthlyReportIndicator.kpPrepScreened(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_SCREEN_PWID", "HIV negative PWID KP Screened PrEP",
		    ReportUtils.map(monthlyReportIndicator.kpPrepScreened(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_SCREEN_IN_PRISONS", "HIV negative prisoner KP Screened PrEP",
		    ReportUtils.map(monthlyReportIndicator.kpPrepScreened(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_SCREEN_TG", "HIV negative TG KP Screened PrEP",
				ReportUtils.map(monthlyReportIndicator.kpPrepScreened(TG), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_ELIGIBLE_FSW", "Eligible FSW for PrEP among screened",
		    ReportUtils.map(monthlyReportIndicator.kpPrepEligible(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_ELIGIBLE_MSM", "Eligible MSM for PrEP among screened",
		    ReportUtils.map(monthlyReportIndicator.kpPrepEligible(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_ELIGIBLE_PWID", "Eligible PWID for PrEP among screened",
		    ReportUtils.map(monthlyReportIndicator.kpPrepEligible(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_ELIGIBLE_IN_PRISONS", "Eligible prisoner for PrEP among screened",
		    ReportUtils.map(monthlyReportIndicator.kpPrepEligible(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_ELIGIBLE_TG", "Eligible transgender for PrEP among screened",
				ReportUtils.map(monthlyReportIndicator.kpPrepEligible(TG), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_NEW_DICE_FSW", "Started FSW on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepNewDice(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_NEW_DICE_MSM", "Started MSM on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepNewDice(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_NEW_DICE_PWID", "Started PWID on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepNewDice(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_NEW_DICE_IN_PRISONS", "Prisoner Started on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepNewDice(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_NEW_DICE_IN_TG", "Transgender Started on PrEP in this DICE",
				ReportUtils.map(monthlyReportIndicator.kpPrepNewDice(TG), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_DICE_FSW", "FSW KPs currently on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepCurrDice(FSW), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_DICE_MSM", "MSM KPs currently on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepCurrDice(MSM), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_DICE_PWID", "PWID KPs currently on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepCurrDice(PWID), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_DICE_IN_PRISONS", "Prisoner KPs currently on PrEP in this DICE",
		    ReportUtils.map(monthlyReportIndicator.kpPrepCurrDice(IN_PRISONS), indParams), kpAgeDisaggregation,
		    Arrays.asList("01", "02", "03", "04"));
		EmrReportingUtils.addRow(cohortDsd, "PrEP_CURR_DICE_IN_TG", "Transgender KPs currently on PrEP in this DICE",
				ReportUtils.map(monthlyReportIndicator.kpPrepCurrDice(TG), indParams), kpAgeDisaggregation,
				Arrays.asList("01", "02", "03", "04"));
		//62. MAT
		EmrReportingUtils.addRow(cohortDsd, "MAT_EVER", "Number ever put on MAT",
		    ReportUtils.map(monthlyReportIndicator.kpEverOnMat(PWID), indParams), kpGenderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "MAT_PREPARED", "Number prepared for induction of MAT",
		    ReportUtils.map(monthlyReportIndicator.kpMatPrepared(PWID), indParams), kpGenderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "MAT_ELIGIBLE", "Number of PWID eligible for MAT",
		    ReportUtils.map(monthlyReportIndicator.kpMatEligible(PWID), indParams), kpGenderDisaggregation,
		    Arrays.asList("01", "02"));
		EmrReportingUtils.addRow(cohortDsd, "MAT_NEW", "Number newly started on MAT",
		    ReportUtils.map(monthlyReportIndicator.kpMatNew(PWID), indParams), kpGenderDisaggregation,
		    Arrays.asList("01", "02"));
		
		return cohortDsd;
	}
}
