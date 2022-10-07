/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.library.ETLReports.moh731B;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of cohort definitions used specifically in the MOH731 report based on ETL tables. It has
 * incorporated green card components
 */
@Component
public class ETLMoh731PlusCohortLibrary {
	
	@Autowired
	private ETLMoh731GreenCardCohortLibrary moh731Cohorts;
	
	/**
	 * KPs who received atleast 1 service within the last 3 months from the effective date
	 * 
	 * @return
	 */
	public CohortDefinition kpCurr() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "         join kenyaemr_etl.etl_client_enrollment e on c.client_id = e.client_id and e.voided = 0\n"
		        + "         join (select p.client_id\n"
		        + "               from kenyaemr_etl.etl_peer_calendar p\n"
		        + "               where p.voided = 0\n"
		        + "               group by p.client_id\n"
		        + "               having max(p.visit_date) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) p\n"
		        + "              on c.client_id = p.client_id\n"
		        + "         left join (select d.patient_id, max(d.visit_date) latest_visit\n"
		        + "                    from kenyaemr_etl.etl_patient_program_discontinuation d\n"
		        + "                    where d.program_name = 'KP') d on c.client_id = d.patient_id\n"
		        + "where (d.patient_id is null or d.latest_visit > date(:endDate))   and c.voided = 0\n"
		        + "group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
	/**
	 * Returns clients who belongs to a certain kp type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpType(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
		        + "group by c.client_id\n" + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = '"
		        + kpType + "';";
		cd.setName("kpType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("kpType");
		return cd;
	}
	
	/*
		*//**
	 * MSM
	 * 
	 * @return
	 */
	/*
	public CohortDefinition msm() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSM';";
	cd.setName("msm");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("msm");
	
	return cd;
	}
	
	*//**
	 * MSW
	 * 
	 * @return
	 */
	/*
	public CohortDefinition msw() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSW';";
	cd.setName("msw");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("msw");
	
	return cd;
	}
	
	*//**
	 * PWID
	 * 
	 * @return
	 */
	/*
	public CohortDefinition pwid() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWID';";
	cd.setName("pwid");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("pwid");
	
	return cd;
	}
	
	*//**
	 * PWUD
	 * 
	 * @return
	 */
	/*
	public CohortDefinition pwud() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWUD';";
	cd.setName("pwud");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("pwud");
	
	return cd;
	}
	
	*//**
	 * Transgender
	 * 
	 * @return
	 */
	/*
	public CohortDefinition transgender() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
	cd.setName("transgender");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("transgender");
	
	return cd;
	}
	
	*//**
	 * Prisoners and people in closed settings
	 * 
	 * @return
	 */
	/*
	public CohortDefinition prisonersClosedSettings() {
	SqlCohortDefinition cd = new SqlCohortDefinition();
	String sqlQuery = "select c.client_id\n"
	        + "from kenyaemr_etl.etl_contact c\n"
	        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:location)\n"
	        + "group by c.client_id\n"
	        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'People in prison and other closed settings';";
	cd.setName("prisonersClosedSettings");
	cd.setQuery(sqlQuery);
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.setDescription("Prisoners and people in closed settings");
	
	return cd;
	}*/
	
	/**
	 * FSW active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activeKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.setCompositionString("kpCurr AND kpType");
		return cd;
	}
	
	/**
	 * MSM active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activeMsm(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND msm");
	return cd;
	}
	
	*//**
	 * MSW active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activeMsw(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND msw");
	return cd;
	}
	
	*//**
	 * PWID active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activePwid(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND pwid");
	return cd;
	}
	
	*//**
	 * PWUD active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activePwud(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND pwud");
	return cd;
	}
	
	*//**
	 * Transgender active in KP (Received atleast one service in the past 3 months going up to
	 * effective date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activeTransgender(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("transgender",
	    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND transgender");
	return cd;
	}
	
	*//**
	 * Prisoners and people in closed settings active in KP (Received atleast one service in the
	 * past 3 months going up to effective date)
	 * 
	 * @return
	 */
	/*
	public CohortDefinition activePrisonersAndClossedSettings(String kpType) {
	CompositionCohortDefinition cd = new CompositionCohortDefinition();
	cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	cd.addParameter(new Parameter("location", "Sub County", String.class));
	cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
	cd.addSearch("prisonersClosedSettings",
	    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	cd.setCompositionString("kpCurr AND prisonersClosedSettings");
	return cd;
	}*/
	
	/**
	 * KPs tested For HIV
	 * 
	 * @return
	 */
	public CohortDefinition htsAllNumberTestedKeyPopulation() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("htsAllNumberTestedKeyPopulation");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV");
		
		return cd;
	}
	
	/**
	 * Tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the facility/dice
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtFacility() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population' and setting = 'Facility'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("htsNumberTestedAtFacility");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV at the facility");
		
		return cd;
	}
	
	/**
	 * KPs tested at the facility/dice by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtFacilityKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsNumberTestedAtFacility");
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the community level
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtCommunity() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test where test_type = 1\n"
		        + "  and population_type = 'Key Population' and setting = 'Community'\n"
		        + "  and date(visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("htsNumberTestedAtCommunity");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("KPs tested For HIV at the community level");
		
		return cd;
	}
	
	/**
	 * KPs Tested at community level by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtCommunityKPs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * Newly Tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition newlyTestedForHIV() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "SELECT patient_id\n" + "FROM kenyaemr_etl.etl_hts_test t\n" + "WHERE test_type = 1\n"
		        + "GROUP BY patient_id\n" + "having min(visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("newlyTestedForHIV");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * FSWs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition kpsNewlyTestedForHIV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * Returns clients with repeat HTS tests within reporting period. For a KP, a test is counted as
	 * repeat if the client reports that they have ever been tested before
	 * 
	 * @return
	 */
	public CohortDefinition repeatHTSTestWithinReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.visit_date between date(:startDate) and date(:endDate)\n"
		        + "and t.ever_tested_for_hiv = 'Yes' and t.population_type = 'Key Population';";
		cd.setName("repeatHTSTestWithinReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("repeatHTSTestWithinReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Clients with reported repeat HIV test during clinical visit within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition reportedRepeatHIVTest() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.hiv_tested = 'Yes' and v.test_frequency = 'Repeat'\n"
		        + "and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("reportedRepeatHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("reportedRepeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests within reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeat(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("repeatHTSTestWithinReportingPeriod",
		    ReportUtils.map(repeatHTSTestWithinReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("reportedRepeatHIVTest",
		    ReportUtils.map(reportedRepeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (reportedRepeatHIVTest OR repeatHTSTestWithinReportingPeriod)");
		return cd;
	}
	
	/**
	 * KP Client who reported self HIV test within the reporting period in HTS
	 * 
	 * @return
	 */
	public CohortDefinition selfHIVTestInHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id from kenyaemr_etl.etl_hts_test\n"
		        + "where patient_had_hiv_self_test = 'Yes' and population_type = 'Key Population'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("selfHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("selfHIVTest");
		
		return cd;
	}
	
	/**
	 * KP Client who reported self HIV test within the reporting period in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition selfHIVTestClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.self_tested = 'Y' and v.self_test_date between date(:startDate) and date(:endDate);";
		cd.setName("selfHIVTestClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("selfHIVTestClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for within reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("selfHIVTestClinicalVisit",
		    ReportUtils.map(selfHIVTestClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND selfHIVTestClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Known Positive KPs
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveKPs() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.kp_client\n"
		        + "from (select c.client_id           as kp_client,\n"
		        + "             max(c.visit_date)     as latest_kp_enrollment,\n"
		        + "             e.client_id           as kp_known_pos,\n"
		        + "             e.kp_enrollment_date  as kp_enrollment_date,\n"
		        + "             h.patient_id          as hiv_enrolled,\n"
		        + "             h.hiv_enrollment_date as hiv_enroll_date,\n"
		        + "             t.patient_id          as hts_positive_patient,\n"
		        + "             t.hts_date            as hts_date\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left join (select e.client_id, e.visit_date as kp_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          where e.visit_date <= date(:endDate)\n"
		        + "                            and e.share_test_results = 'Yes I tested positive') e\n"
		        + "                         on c.client_id = e.client_id\n"
		        + "               left join (select h.patient_id, h.visit_date as hiv_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_hiv_enrollment h\n"
		        + "                          where h.visit_date <= date(:endDate)) h on c.client_id = h.patient_id\n"
		        + "               left join (select t.patient_id, t.visit_date as hts_date\n"
		        + "                          from kenyaemr_etl.etl_hts_test t\n"
		        + "                          where t.population_type= 'Key Population'\n"
		        + "                            and t.visit_date <= date(:endDate)\n"
		        + "                            and t.test_type = 2\n"
		        + "                            and t.final_test_result = 'Positive') t on c.client_id = t.patient_id\n"
		        + "      group by c.client_id\n"
		        + "      having latest_kp_enrollment <= date(:endDate)\n"
		        + "         and (kp_known_pos is not null or (hiv_enrolled is not null and hiv_enroll_date <= latest_kp_enrollment)\n"
		        + "          or (hts_positive_patient is not null and hts_date <= latest_kp_enrollment))) a;";
		cd.setName("knownPositiveKPs");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR FSWs Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositive(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND kpType AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KPs who received HTS and received a positive result
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveHTSResults() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population' and t.patient_given_result = 'Yes'\n"
		        + "  and t.final_test_result = 'Positive'\n"
		        + "  and date(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivedPositiveHTSResults");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivedPositiveHTSResults");
		
		return cd;
	}
	
	/**
	 * KPs who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveHIVResults(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivedPositiveHTSResults",
		    ReportUtils.map(receivedPositiveHTSResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPositiveHTSResults");
		
		return cd;
	}
	
	/**
	 * Tested for HIV 3 months ago, before effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositive3MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population' and t.final_test_result = 'Positive'\n"
		        + "  and date(t.visit_date) between date_sub(date(:startDate), interval 3 MONTH)\n"
		        + "    and last_day(date_sub(:endDate, interval 3 MONTH));";
		cd.setName("testedHIVPositive3MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("testedHIVPositive3MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Linked in HTS
	 * 
	 * @return
	 */
	public CohortDefinition linkedHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.patient_id from kenyaemr_etl.etl_hts_referral_and_linkage r\n"
		        + "where (r.ccc_number != '' or r.ccc_number IS NOT NULL)\n"
		        + "  and (r.facility_linked_to != '' or r.facility_linked_to IS NOT NULL)\n"
		        + "  and date(r.visit_date) between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate);";
		cd.setName("linkedHTS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("linkedHTS");
		
		return cd;
	}
	
	/**
	 * Reported Linked in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition linkedClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null or\n"
		        + "       v.self_test_link_facility is not null)\n"
		        + "  and v.visit_date between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate)";
		cd.setName("linkedClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("linkedClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Enrolled in HIV program
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInHIVProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e \n"
		        + "where e.visit_date between date_sub(date(:startDate), interval 3 MONTH) and date(:endDate);";
		cd.setName("enrolledInHIVProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("enrolledInHIVProgram");
		
		return cd;
	}
	
	/**
	 * Linked to care/treatment
	 * 
	 * @return
	 */
	public CohortDefinition linked(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Returns clients who tested HIV+ 3 months ago
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition testedHIVPositiveMonthsAgo(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND testedHIVPositive3MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Received condoms from clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id\n"
		        + "having (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0)) > 0;";
		cd.setName("receivingCondomsFromClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Received condoms from sti treatment
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromSTITreatment() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select s.client_id from kenyaemr_etl.etl_sti_treatment s\n"
		        + "where s.visit_date between date(:startDate) and date(:endDate) group by s.client_id\n"
		        + "having coalesce(sum(s.no_of_condoms), 0) > 0;";
		cd.setName("receivingCondomsFromSTITreatment");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromSTITreatment");
		
		return cd;
	}
	
	/**
	 * Received condoms from peer outreach
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsFromPeerOutreach() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id from kenyaemr_etl.etl_peer_calendar p\n"
		        + "where date(p.visit_date) between date(:startDate) and date(:endDate) group by p.client_id\n"
		        + "  having (coalesce(sum(p.monthly_male_condoms_distributed), 0) +\n"
		        + "       coalesce(sum(p.monthly_female_condoms_distributed), 0)) > 0;";
		cd.setName("receivingCondomsFromPeerOutreach");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromPeerOutreach");
		
		return cd;
	}
	
	/**
	 * KPs received Condoms within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondoms(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",
		    ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach",
		    ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",
		    ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");
		
		return cd;
	}
	
	/**
	 * Received condoms per need within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeedSql() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id,\n"
		        + "             (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0) +\n"
		        + "              coalesce(sum(s.no_of_condoms), 0) + coalesce(p.monthly_male_condoms_distributed, 0) +\n"
		        + "              coalesce(p.monthly_male_condoms_distributed, 0))\n"
		        + "                                                                                                       as condoms_distributed,\n"
		        + "             greatest(coalesce(p.monthly_condoms_required, 0),\n"
		        + "                      coalesce(c.avg_weekly_sex_acts * 4, 0))                                          as monthly_condom_requirement\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p\n"
		        + "           on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                                    date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and\n"
		        + "                                                                   date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      group by c.client_id       having condoms_distributed >= monthly_condom_requirement\n"
		        + "         and condoms_distributed > 0) k;";
		cd.setName("receivingCondomsPerNeedPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsPerNeedPerNeed");
		
		return cd;
	}
	
	/**
	 * FSWs who received condoms per need
	 * 
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeed(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeedSql",
		    ReportUtils.map(receivingCondomsPerNeedPerNeedSql(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingCondomsPerNeedPerNeedSql");
		
		return cd;
	}
	
	/**
	 * Number receiving needles & syringes
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingNeedlesAndSyringes(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = '"
		        + kpType
		        + "' and c.implementation_subcounty = trim(:location) group by c.client_id having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringes");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingNeedlesAndSyringes");
		
		return cd;
	}
	
	/**
	 * Number receiving needles & syringes per need
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingNeedlesAndSyringesPerNeed(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "and c.implementation_subcounty = trim(:location) group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeed ");
		
		return cd;
	}
	
	/**
	 * Number receiving lubricants
	 * 
	 * @return
	 */
	public CohortDefinition receivingLubricants(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = '"
		        + kpType
		        + "' and c.implementation_subcounty = trim(:location) group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricants");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingLubricants");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeed(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = '"
		        + kpType
		        + "'\n"
		        + "  and c.implementation_subcounty = trim(:location) group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingLubricantsPerNeed");
		
		return cd;
	}
	
	/**
	 * Number receiving self-test kits
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingSelfTestKits(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id, (coalesce(sum(v.self_test_kits_given), 0) + IFNULL(p.monthly_self_test_kits_distributed,0)) as self_test_kits_given\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "       left outer join\n"
		        + "   kenyaemr_etl.etl_peer_calendar p\n"
		        + "   on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "       left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                            date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "       left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and\n"
		        + "                                                           date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = '" + kpType
		        + "' and c.implementation_subcounty = trim(:location) group by c.client_id\n"
		        + "having self_test_kits_given >= 1) k;\n";
		cd.setName("receivingSelfTestKits");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingSelfTestKits");
		
		return cd;
	}
	
	/**
	 * Returns number of clients screened for STI
	 * 
	 * @return
	 */
	public CohortDefinition numberScreenedForSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.sti_screened = 'Y' and v.visit_date\n"
		        + "      between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("numberScreenedForSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberScreenedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number screened_STI
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("numberScreenedForSTISQL",
		    ReportUtils.map(numberScreenedForSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND numberScreenedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Returns number of clients diagnosed with STI
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.sti_screened = 'Y' and v.sti_results = 'Positive'\n"
		        + "and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithSTISQL");
		
		return cd;
	}
	
	/**
	 * Number Diagnosed with STI
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("diagnosedWithSTISQL",
		    ReportUtils.map(diagnosedWithSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithSTISQL");
		
		return cd;
	}
	
	/**
	 * Returns number of clients treated for STI
	 * 
	 * @return
	 */
	public CohortDefinition treatedForSTISQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.sti_screened = 'Y' and v.sti_results = 'Positive'\n" + "  and sti_treated = 'Yes'\n"
		        + "  and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "  and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForSTISQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number treated for STI
	 * 
	 * @return
	 */
	public CohortDefinition treatedForSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("treatedForSTISQL", ReportUtils.map(treatedForSTISQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForSTISQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition screenedForHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("screenedForHCVSQL", ReportUtils.map(screenedForHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y' and v.hepatitisC_results = 'P'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedWithHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("diagnosedWithHCVSQL",
		    ReportUtils.map(diagnosedWithHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis C
	 * 
	 * @return
	 */
	public CohortDefinition treatedForHCVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisC_screened = 'Y' and v.hepatitisC_results = 'N'\n"
		        + "      and v.hepatitisC_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForHCVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis C by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition treatedForHCV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("treatedForHCVSQL", ReportUtils.map(treatedForHCVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForHCVSQL");
		
		return cd;
	}
	
	/**
	 * Number Screened for Hepatitis B
	 * 
	 * @return
	 */
	public CohortDefinition screenedForHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedForHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("screenedForHBVSQL", ReportUtils.map(screenedForHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis B
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedWithHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'P'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedWithHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedWithHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedWithHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("diagnosedWithHBVSQL",
		    ReportUtils.map(diagnosedWithHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedWithHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for HBV
	 * 
	 * @return
	 */
	public CohortDefinition treatedForHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'P'\n"
		        + "      and v.hepatitisB_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("treatedForHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number treated for Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition treatedForHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("treatedForHBVSQL", ReportUtils.map(treatedForHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number vaccinated against HBV
	 * 
	 * @return
	 */
	public CohortDefinition vaccinatedAgainstHBVSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.hepatitisB_screened = 'Y' and v.hepatitisB_results = 'N'\n"
		        + "      and v.hepatitisB_treated = 'Vaccinated'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("vaccinatedAgainstHBVSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vaccinatedAgainstHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened negative and vaccinated against Hepatitis B by KP Type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition vaccinatedAgainstHBV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("vaccinatedAgainstHBVSQL",
		    ReportUtils.map(vaccinatedAgainstHBVSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND vaccinatedAgainstHBVSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for TB
	 * 
	 * @return
	 */
	public CohortDefinition screenedTBSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.tb_screened = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number screened for TB by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition screenedTB(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("screenedTBSQL", ReportUtils.map(screenedTBSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND screenedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with TB
	 * 
	 * @return
	 */
	public CohortDefinition diagnosedTBSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.tb_screened = 'Y' and v.tb_results = 'Positive'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number diagnosed with TB by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition diagnosedTB(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("diagnosedTBSQL", ReportUtils.map(diagnosedTBSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedTBSQL");
		
		return cd;
	}
	
	/**
	 * Number started TB treatment
	 * 
	 * @return
	 */
	public CohortDefinition startedTBTXSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "    where v.tb_screened = 'Y' and v.tb_results = 'Positive'\n" + "      and v.tb_treated = 'Y'\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("startedTBTXSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedTBTXSQL");
		
		return cd;
	}
	
	/**
	 * Number started TB treatment by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition startedTBTX(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("startedTBTXSQL", ReportUtils.map(startedTBTXSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedTBTXSQL");
		
		return cd;
	}
	
	/**
	 * Number of TB clients on HAART
	 * 
	 * @return
	 */
	public CohortDefinition tbClientsOnHAARTSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "        where v.tb_screened = 'Y' and v.tb_results = 'Positive'\n"
		        + "          and (v.active_art = 'Yes' or initiated_art_this_month = 'Yes')\n"
		        + "          and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "          and v.voided = 0 group by v.client_id;";
		cd.setName("tbClientsOnHAARTSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTSQL ");
		
		return cd;
	}
	
	/**
	 * Number of TB clients on HAART by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition tbClientsOnHAART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("tbClientsOnHAARTSQL",
		    ReportUtils.map(tbClientsOnHAARTSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND tbClientsOnHAARTSQL");
		
		return cd;
	}
	
	/**
	 * Initiated PrEP within the reporting month as recorded in clinical visit form
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPReportingPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "        where v.prep_screened = 'Y' and v.prep_results = 'Eligible'\n"
		        + "          and v.prep_treated = 'Y'\n"
		        + "          and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "          and v.voided = 0 group by v.client_id;";
		cd.setName("initiatedPrEPReportingPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPReportingPeriodClinicalVisit");
		return cd;
	}
	
	/**
	 * Initiated PrEP before the reporting month as recorded in clinical visit form
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPBeforeReportingPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.prep_screened = 'Y' and v.prep_results = 'Eligible'\n" + "  and v.prep_treated = 'Y'\n"
		        + "  and v.visit_date < date(:startDate)\n" + "  and v.voided = 0 group by v.client_id;";
		cd.setName("initiatedPrEPBeforeReportingPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPBeforeReportingPeriodClinicalVisit");
		return cd;
	}
	
	/**
	 * Clients initiated on PrEP for the first time during the reporting period in PrEP program
	 * 
	 * @return
	 */
	public CohortDefinition initiatedPrEPReportingPeriodProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_prep_enrolment e\n" + "       group by e.patient_id\n"
		        + "       having min(date(e.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("initiatedPrEPReportingPeriodProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPReportingPeriodProgram");
		return cd;
	}
	
	/**
	 * Initiated PrEP:number of HIV negative persons in each KP type who have been started on PrEP
	 * during the reporting month after meeting the eligibility criteria for PrEP.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition initiatedPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("initiatedPrEPReportingPeriodClinicalVisit",
		    ReportUtils.map(initiatedPrEPReportingPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEPBeforeReportingPeriodClinicalVisit",
		    ReportUtils.map(initiatedPrEPBeforeReportingPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEPReportingPeriodProgram",
		    ReportUtils.map(initiatedPrEPReportingPeriodProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((initiatedPrEPReportingPeriodClinicalVisit AND NOT initiatedPrEPBeforeReportingPeriodClinicalVisit) OR initiatedPrEPReportingPeriodProgram)");
		
		return cd;
	}
	
	/**
	 * Currently on PrEP as recorded in clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition currentOnPrEPClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.prep_screened = 'Ongoing'\n" + "  and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "  and v.voided = 0 group by v.client_id;";
		cd.setName("currentOnPrEPClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPClinicalVisit ");
		
		return cd;
	}
	
	/**
	 * Clients with a PrEP visit within the reporting month (in PrEP program)
	 * 
	 * @return
	 */
	public CohortDefinition currentPrEPFollowupVisitProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select f.patient_id from kenyaemr_etl.etl_prep_followup f\n"
		        + "where date(f.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("currentPrEPFollowupVisitProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentPrEPFollowupVisitProgram ");
		
		return cd;
	}
	
	/**
	 * Clients with a PrEP refill appointment date that is within or beyond the reporting month, or
	 * given drugs to cover the reporting month
	 * 
	 * @return
	 */
	public CohortDefinition currentOrFuturePrEPRefillAppDateProgram() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.patient_id\n" + "from kenyaemr_etl.etl_prep_monthly_refill r\n"
		        + "where r.prescribed_prep_today = 'Yes'\n"
		        + "  and (DATE_ADD(date(r.visit_date), INTERVAL r.prescribed_regimen_months MONTH) >= date(:startDate)\n"
		        + "    or date(r.next_appointment) >= date(:startDate));";
		cd.setName("currentOrFuturePrEPRefillAppDateProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOrFuturePrEPRefillAppDateProgram ");
		
		return cd;
	}
	
	/**
	 * Current on PrEP: Number of people in each KP type who are currently receiving PrEP. This
	 * includes persons newly started on PrEP in the reporting month, persons who come for their
	 * PrEP refills in the reporting month, and those who do not come for medicine in the reporting
	 * month but have been given PrEP medications to take them through the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentOnPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("currentOnPrEPClinicalVisit",
		    ReportUtils.map(currentOnPrEPClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEP", ReportUtils.map(initiatedPrEP(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentPrEPFollowupVisitProgram",
		    ReportUtils.map(currentPrEPFollowupVisitProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOrFuturePrEPRefillAppDateProgram",
		    ReportUtils.map(currentOrFuturePrEPRefillAppDateProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (currentOnPrEPClinicalVisit OR initiatedPrEP OR currentPrEPFollowupVisitProgram OR currentOrFuturePrEPRefillAppDateProgram)");
		
		return cd;
	}
	
	/**
	 * Tested Positive for HIV during the reporting period in HTS
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveWithinPeriodHTS() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hts.patient_id\n" + "from kenyaemr_etl.etl_hts_test hts\n" + "where hts.test_type = 2\n"
		        + "  and hts.final_test_result = 'Positive'\n" + "  and hts.voided = 0\n"
		        + "  and hts.visit_date between date(:startDate) and date(:endDate)\n" + "group by hts.patient_id;";
		cd.setName("testedHIVPositiveWithinPeriodHTS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriodHTS ");
		
		return cd;
	}
	
	/**
	 * Tested Positive for HIV during the reporting period - Self test reported in Clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveWithinPeriodClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(coalesce(v.self_test_date, v.visit_date)) between date(:startDate) and date(:endDate)\n"
		        + "  and v.test_confirmatory_results = 'Positive'\n" + "  and v.voided = 0;";
		cd.setName("testedHIVPositiveWithinPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriodClinicalVisit ");
		
		return cd;
	}
	
	/**
	 * Turning HIV positive while on PrEP: Number of people on PrEP in each KP type who tested
	 * positive for HIV in the reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition turningPositiveWhileOnPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("currentOnPrEP",
		    ReportUtils.map(currentOnPrEP(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("testedHIVPositiveWithinPeriodHTS",
		    ReportUtils.map(testedHIVPositiveWithinPeriodHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVPositiveWithinPeriodClinicalVisit",
		    ReportUtils.map(testedHIVPositiveWithinPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentOnPrEP AND (testedHIVPositiveWithinPeriodHTS OR testedHIVPositiveWithinPeriodClinicalVisit)");
		
		return cd;
	}
	
	/**
	 * Number of people, who experienced violence in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition experiencingViolenceSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.violence_screened = 'Yes'\n"
		        + "  and v.violence_results in\n"
		        + "      ('Harrasment', 'Assault', 'Illegal arrest', 'Verbal Abuse', 'Rape/Sexual assault', 'Discrimination')\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("experiencingViolenceSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceSQL");
		
		return cd;
	}
	
	/**
	 * Number of people, who experienced violence in the reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencingViolence(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("experiencingViolenceSQL",
		    ReportUtils.map(experiencingViolenceSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencingViolenceSQL");
		
		return cd;
	}
	
	/**
	 * Number of people in each KP type who were supported by the programme when they experienced
	 * violence in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivingViolenceSupportSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.violence_treated = 'Supported'\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivingViolenceSupportSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportSQL");
		
		return cd;
	}
	
	/**
	 * Receiving violence support:Number of people in each KP type who were supported by the
	 * programme when they experienced violence in the reporting period by KP type
	 * 
	 * @return
	 */
	public CohortDefinition receivingViolenceSupport(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivingViolenceSupportSQL",
		    ReportUtils.map(experiencingViolenceSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingViolenceSupportSQL");
		
		return cd;
	}
	
	/**
	 * Number exposed: Number of people in each KP type who reported that they were exposed to HIV
	 * within 72 hours of exposure.
	 * 
	 * @return
	 */
	public CohortDefinition numberExposedSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n" + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.pep_eligible ='Y' and v.exposure_type in ('Rape', 'Condom burst', 'Others')\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("numberExposedSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedSQL");
		
		return cd;
	}
	
	/**
	 * Number exposed: Number of people in each KP type who reported that they were exposed to HIV
	 * within 72 hours of exposure by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition numberExposed(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("numberExposedSQL", ReportUtils.map(numberExposedSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND numberExposedSQL");
		
		return cd;
	}
	
	/**
	 * Number receiving PEP<72hrs: Number of people in each KP type who were initiated on PEP within
	 * 72 hours of HIV exposure.
	 * 
	 * @return
	 */
	public CohortDefinition receivingPEPWithin72HrsSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("receivingPEPWithin72HrsSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsSQL");
		
		return cd;
	}
	
	/**
	 * Number receiving PEP<72hrs: Number of people in each KP type who were initiated on PEP within
	 * 72 hours of HIV exposure by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivingPEPWithin72Hrs(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivingPEPWithin72HrsSQL",
		    ReportUtils.map(receivingPEPWithin72HrsSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivingPEPWithin72HrsSQL");
		
		return cd;
	}
	
	/**
	 * Number completed PEP within 28 days: Number of people in each KP type who completed taking
	 * PEP within 28 days.
	 * 
	 * @return
	 */
	public CohortDefinition completedPEPWith28DaysSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("completedPEPWith28DaysSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("completedPEPWith28DaysSQL");
		
		return cd;
	}
	
	/**
	 * Number completed PEP within 28 days: Number of people in each KP type who completed taking
	 * PEP within 28 days by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition completedPEPWith28Days(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("completedPEPWith28DaysSQL",
		    ReportUtils.map(completedPEPWith28DaysSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND completedPEPWith28DaysSQL");
		
		return cd;
	}
	
	/**
	 * Receiving peer education: Number of people in each KP type who received peer education in the
	 * reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition receivedPeerEducationSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("receivedPeerEducationSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPeerEducationSQL");
		
		return cd;
	}
	
	/**
	 * Receiving peer education: Number of people in each KP type who received peer education in the
	 * reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivedPeerEducation(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivedPeerEducationSQL",
		    ReportUtils.map(receivedPeerEducationSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPeerEducationSQL");
		
		return cd;
	}

	/**
	 * Number of people of each KP type who were enrolled in care this month or in a previous month and made a clinical
	 * visit on site in preparation for ART but have not started on ART during this visit. They should be counted only if
	 * there is no intention to start them on ART during the reporting month in this site.
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onPreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
				ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		cd.addSearch("receivedPeerEducationSQL",
				ReportUtils.map(receivedPeerEducationSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPeerEducationSQL");

		return cd;
	}
}
