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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH731Greencard.ETLMoh731GreenCardCohortLibrary;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.RevisedDatim.DatimCohortLibrary;
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
	
	private static final Logger log = LoggerFactory.getLogger(ETLMoh731PlusCohortLibrary.class);
	
	@Autowired
	private ETLMoh731GreenCardCohortLibrary moh731Cohorts;
	
	@Autowired
	private DatimCohortLibrary datimCohorts;
	
	/**
	 * Returns clients who belongs to a certain kp type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpType(String kpType) {
		
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		
		if (kpType.equals("TRANSMAN")) {
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = c.client_id\n"
			        + "         where d.gender = 'F' and date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "         group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		} else if (kpType.equals("TRANSWOMAN")) {
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = c.client_id\n"
			        + "         where d.gender = 'M' and date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "         group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		} else
			sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
			        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = :location\n"
			        + "group by c.client_id having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = '"
			        + kpType + "';";
		
		cd.setName("kpType");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("kpType");
		return cd;
	}
	
	/**
	 * KPs who received atleast 1 service within the last 3 months from the effective date either
	 * through clinical service encounter or peer educator/calendar
	 * 
	 * @return
	 */
	public CohortDefinition kpCurr() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "         left join (select p.client_id, max(date(visit_date)) as latest_peer_date\n"
		        + "                           from kenyaemr_etl.etl_peer_calendar p\n"
		        + "                           where p.voided = 0 and date(p.visit_date) <= date(:endDate)\n"
		        + "                           group by p.client_id\n"
		        + "                           having max(date(p.visit_date)) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) p\n"
		        + "               on c.client_id = p.client_id\n"
		        + "                 left join (select v.client_id, max(visit_date) as latest_visit_date\n"
		        + "                            from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                            where v.voided = 0 and date(v.visit_date) <= date(:endDate)\n"
		        + "                            group by v.client_id\n"
		        + "                            having max(date(v.visit_date)) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) v\n"
		        + "               on c.client_id = v.client_id\n"
		        + "         left join (select d.patient_id, max(date(d.visit_date)) latest_disc_date\n"
		        + "                    from kenyaemr_etl.etl_patient_program_discontinuation d\n"
		        + "                    where d.program_name = 'KP') d on c.client_id = d.patient_id\n"
		        + "where (d.patient_id is null or p.latest_peer_date > d.latest_disc_date or v.latest_visit_date > d.latest_disc_date) and c.voided = 0\n"
		        + "            and (p.client_id is not null or v.client_id is not null) group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("kpCurr AND kpType");
		return cd;
	}
	
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "SELECT patient_id FROM kenyaemr_etl.etl_hts_test t WHERE test_type = 1\n"
		        + "GROUP BY patient_id having min(visit_date) between date(:startDate) and date(:endDate);";
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n"
		        + "where date(t.visit_date) <= date(:endDate)\n" + "group by t.patient_id\n"
		        + "having max(t.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "   and mid(max(concat(date(t.visit_date), t.test_type)), 11) = 1\n"
		        + "   and mid(max(concat(date(t.visit_date), t.population_type)), 11) = 'Key Population'\n"
		        + "   and min(t.visit_date) < max(t.visit_date) and\n"
		        + "mid(min(concat(date(t.visit_date),t.test_type)),11) = 1;";
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		        + "               left join (select h.patient_id, coalesce(h.date_confirmed_hiv_positive,coalesce(h.date_first_enrolled_in_care,h.visit_date)) as hiv_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_hiv_enrollment h\n"
		        + "                          where h.visit_date <= date(:endDate)) h on c.client_id = h.patient_id\n"
		        + "               left join (select t.patient_id, t.visit_date as hts_date\n"
		        + "                          from kenyaemr_etl.etl_hts_test t\n"
		        + "                          where t.population_type = 'Key Population'\n"
		        + "                            and t.visit_date <= date(:endDate)\n"
		        + "                            and t.test_type = 1\n"
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		        + "' and c.implementation_subcounty =  :location group by c.client_id having needles_and_syringes_given >=1 )k;";
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
		        + "and c.implementation_subcounty =  :location group by c.client_id\n"
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
		        + "' and c.implementation_subcounty =  :location group by c.client_id\n"
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
		        + "  and c.implementation_subcounty =  :location group by c.client_id\n"
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
	 * Number experiencing sexual violence
	 * 
	 * @return
	 */
	public CohortDefinition sexualViolence() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id\n"
		        + "from kenyaemr_etl.etl_patient_demographics d\n"
		        + "         left join\n"
		        + "     (select r.patient_id\n"
		        + "      from kenyaemr_etl.etl_violence_reporting r\n"
		        + "      where find_in_set('Rape/Sexual Assault', r.form_of_incident) > 0\n"
		        + "        and date(r.visit_date) between date(:startDate) and date(:endDate)) r on d.patient_id = r.patient_id\n"
		        + "         left join (select gbvs.patient_id\n"
		        + "                    from kenyaemr_etl.etl_gbv_screening gbvs\n"
		        + "                    where gbvs.sexual_ipv = 152370\n"
		        + "                      and date(gbvs.visit_date) between date(:startDate) and date(:endDate)) gbvs\n"
		        + "                   on d.patient_id = gbvs.patient_id\n" + "         left join (select v.client_id\n"
		        + "                    from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                    where v.violence_results = 'Rape/Sexual assault'\n"
		        + "                      and date(v.visit_date) between date(:startDate) and date(:endDate)) v\n"
		        + "                   on d.patient_id = v.client_id where r.patient_id is not null\n"
		        + "   or gbvs.patient_id is not null or v.client_id is not null;";
		cd.setName("sexualViolence");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("sexualViolence");
		
		return cd;
	}
	
	/**
	 * Number experiencing Physical Violence
	 * 
	 * @return
	 */
	public CohortDefinition physicalViolence() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id\n"
		        + "from kenyaemr_etl.etl_patient_demographics d\n"
		        + "         left join\n"
		        + "     (select r.patient_id\n"
		        + "      from kenyaemr_etl.etl_violence_reporting r\n"
		        + "      where find_in_set('Assault/physical abuse', r.form_of_incident) > 0\n"
		        + "        and date(r.visit_date) between date(:startDate) and date(:endDate)) r on d.patient_id = r.patient_id\n"
		        + "         left join (select gbvs.patient_id\n"
		        + "                    from kenyaemr_etl.etl_gbv_screening gbvs\n"
		        + "                    where gbvs.sexual_ipv = 158358\n"
		        + "                      and date(gbvs.visit_date) between date(:startDate) and date(:endDate)) gbvs\n"
		        + "                   on d.patient_id = gbvs.patient_id\n" + "         left join (select v.client_id\n"
		        + "                    from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                    where v.violence_results = 'Physical'\n"
		        + "                      and date(v.visit_date) between date(:startDate) and date(:endDate)) v\n"
		        + "                   on d.patient_id = v.client_id where r.patient_id is not null\n"
		        + "   or gbvs.patient_id is not null or v.client_id is not null;";
		cd.setName("physicalViolence");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("physicalViolence");
		
		return cd;
	}
	
	/**
	 * Number experiencing emotional / psychological violence
	 * 
	 * @return
	 */
	public CohortDefinition emotionalPsychologicalViolence() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id\n"
		        + "from kenyaemr_etl.etl_patient_demographics d\n"
		        + "         left join\n"
		        + "     (SELECT r.patient_id\n"
		        + "      FROM kenyaemr_etl.etl_violence_reporting r\n"
		        + "      WHERE FIND_IN_SET('Harrasment', r.form_of_incident) > 0\n"
		        + "         OR FIND_IN_SET('Verbal Abuse', r.form_of_incident) > 0\n"
		        + "         OR FIND_IN_SET('Discrimination', r.form_of_incident) > 0\n"
		        + "         OR FIND_IN_SET('Illegal arrest', r.form_of_incident) > 0\n"
		        + "        and date(r.visit_date) between date(:startDate) and date(:endDate)) r on d.patient_id = r.patient_id\n"
		        + "         left join (select gbvs.patient_id\n"
		        + "                    from kenyaemr_etl.etl_gbv_screening gbvs\n"
		        + "                    where gbvs.sexual_ipv = 118688\n"
		        + "                      and date(gbvs.visit_date) between date(:startDate) and date(:endDate)) gbvs\n"
		        + "                   on d.patient_id = gbvs.patient_id\n" + "         left join (select v.client_id\n"
		        + "                    from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                    where v.violence_results = 'Emotional & Psychological'\n"
		        + "                      and date(v.visit_date) between date(:startDate) and date(:endDate)) v\n"
		        + "                   on d.patient_id = v.client_id where r.patient_id is not null\n"
		        + "   or gbvs.patient_id is not null or v.client_id is not null;";
		cd.setName("emotionalPsychologicalViolence");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("emotionalPsychologicalViolence");
		
		return cd;
	}
	
	/**
	 * Number of KPs experiencing sexual Violence
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencingSexualViolence(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("sexualViolence", ReportUtils.map(sexualViolence(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND sexualViolence");
		return cd;
	}
	
	/*	public CohortDefinition receivedViolenceSupport(String kpType) {
			CompositionCohortDefinition cd = new CompositionCohortDefinition();
			cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
			cd.addParameter(new Parameter("endDate", "End Date", Date.class));
			cd.addParameter(new Parameter("location", "Sub County", String.class));
			cd.addSearch("experiencingEmotionalOrPsychologicalViolence", ReportUtils.map(
					experiencingEmotionalOrPsychologicalViolence(kpType),
					"startDate=${startDate},endDate=${endDate},location=${location}"));
			cd.addSearch("experiencingSexualViolence", ReportUtils.map(experiencingSexualViolence(kpType),
					"startDate=${startDate},endDate=${endDate},location=${location}"));
			cd.addSearch("experiencingPhysicalViolence", ReportUtils.map(experiencingPhysicalViolence(kpType),
					"startDate=${startDate},endDate=${endDate},location=${location}"));
			cd.addSearch("violenceSupport", ReportUtils.map(violenceSupport(), "startDate=${startDate},endDate=${endDate}"));
			cd.setCompositionString("(experiencingEmotionalOrPsychologicalViolence OR experiencingSexualViolence OR experiencingPhysicalViolence) AND violenceSupport");

			return cd;
		}*/
	
	/**
	 * Number of KPs experiencing physical Violence
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencingPhysicalViolence(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("physicalViolence", ReportUtils.map(physicalViolence(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND physicalViolence");
		
		return cd;
	}
	
	/**
	 * Number of KPs experiencing emotional/psychological Violence
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencingEmotionalOrPsychologicalViolence(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("emotionalPsychologicalViolence",
		    ReportUtils.map(emotionalPsychologicalViolence(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND emotionalPsychologicalViolence");
		
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
		        + "' and c.implementation_subcounty =  :location group by c.client_id\n"
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
	 * Clients who received violence support
	 * 
	 * @return
	 */
	public CohortDefinition violenceSupport() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id\n"
		        + "from kenyaemr_etl.etl_patient_demographics d\n"
		        + "         left join\n"
		        + "     (select r.patient_id\n"
		        + "      from kenyaemr_etl.etl_violence_reporting r\n"
		        + "      where r.hiv_testing_duration is not null\n"
		        + "         or r.duration_on_emergency_contraception is not null\n"
		        + "         or r.psychosocial_trauma_counselling_duration is not null\n"
		        + "         or r.pep_provided_duration is not null\n"
		        + "         or r.sti_screening_and_treatment_duration is not null\n"
		        + "         or r.legal_support_duration is not null\n"
		        + "         or r.medical_examination_duration is not null\n"
		        + "         or r.prc_form_file_duration is not null\n"
		        + "         or r.medical_services_and_care_duration is not null\n"
		        + "         or r.psychosocial_trauma_counselling_durationA is not null\n"
		        + "         or r.duration_of_none_sexual_legal_support is not null\n"
		        + "          and date(r.visit_date) between date(:startDate) and date(:endDate)) r on d.patient_id = r.patient_id\n"
		        + "         left join(select v.client_id\n"
		        + "                   from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                   where v.violence_treated = 'Supported'\n"
		        + "                     and date(v.visit_date) between date(:startDate) and date(:endDate)) v on d.patient_id = v.client_id\n"
		        + "         left join (select s.patient_id\n"
		        + "                    from kenyaemr_etl.etl_gbv_screening s\n"
		        + "                             inner join kenyaemr_etl.etl_gbv_screening_action a\n"
		        + "                                        on s.patient_id = a.patient_id and s.encounter_id = s.encounter_id\n"
		        + "                    where a.action_taken in\n"
		        + "                          (165070, 160570, 1356, 130719, 135914, 165228, 165171, 165192, 127910, 165203, 5618, 165093,\n"
		        + "                           165274, 165180,\n" + "                           165200, 165184, 1185)\n"
		        + "                      and date(a.visit_date) between date(:startDate) and date(:endDate)) s\n"
		        + "                   on d.patient_id = s.patient_id\n" + "where r.patient_id is not null\n"
		        + "   or v.client_id is not null\n" + "   or s.patient_id is not null;";
		cd.setName("violenceSupport");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("violenceSupport");
		
		return cd;
	}
	
	/**
	 * KPs Received violence support
	 * experiencingEmotionalOrPsychologicalViolence,experiencingSexualViolence
	 * ,experiencingPhysicalViolence
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivedViolenceSupport(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("experiencingEmotionalOrPsychologicalViolence", ReportUtils.map(
		    experiencingEmotionalOrPsychologicalViolence(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencingSexualViolence", ReportUtils.map(experiencingSexualViolence(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencingPhysicalViolence", ReportUtils.map(experiencingPhysicalViolence(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("violenceSupport", ReportUtils.map(violenceSupport(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("(experiencingEmotionalOrPsychologicalViolence OR experiencingSexualViolence OR experiencingPhysicalViolence) AND violenceSupport");
		
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "         left join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "         left join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id\n"
		        + "where (v.client_id is not null and v.sti_screened = 'Y' and v.sti_results = 'Positive' and sti_treated = 'Yes'\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate))\n"
		        + "   or (t.client_id is not null and date(t.visit_date) between date(:startDate) and date(:endDate));";
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
	 * Screened for TB within period
	 * 
	 * @return
	 */
	public CohortDefinition screenedForTbWithinPeriod() {
		String sqlQuery = "select tb.patient_id\n" + "from kenyaemr_etl.etl_tb_screening tb\n"
		        + "where tb.visit_date between date(:startDate) and date(:endDate)\n" + "  and tb.person_present = 978\n"
		        + "  and tb.resulting_tb_status in (1660, 1662, 142177);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("tbScreening");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Screened for TB");
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("screenedForTbWithinPeriod",
		    ReportUtils.map(screenedForTbWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
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
		        + "    where v.tb_screened = 'Y' and v.tb_results in ('Diagnosed with TB','Positive')\n"
		        + "      and v.visit_date between date(:startDate) and date(:endDate)\n"
		        + "      and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBSQL");
		
		return cd;
	}
	
	public CohortDefinition diagnosedWithTBWithinPeriod() {
		String sqlQuery = "select tb.patient_id\n" + "from kenyaemr_etl.etl_tb_screening tb\n"
		        + "where tb.visit_date between date(:startDate) and date(:endDate)\n" + "  and tb.person_present = 978\n"
		        + "  and tb.resulting_tb_status = 1662;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("tbScreening");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Screened for TB");
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedTBSQL", ReportUtils.map(diagnosedTBSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("diagnosedWithTBWithinPeriod",
		    ReportUtils.map(diagnosedWithTBWithinPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (diagnosedTBSQL OR diagnosedWithTBWithinPeriod)");
		
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
		        + "    where v.tb_screened = 'Y' and v.tb_results in ('Diagnosed with TB','Positive') and v.tb_treated = 'Y'\n"
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedTBTXSQL", ReportUtils.map(startedTBTXSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedTBTXSQL");
		
		return cd;
	}
	
	/**
	 * No. of HIV+ diagnosed with TB
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition hivPosDiagnosedWithTB(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedTB", ReportUtils.map(diagnosedTB(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND diagnosedTB AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * No. of KPs started on TPT
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition givenTPT(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedOnIPT",
		    ReportUtils.map(moh731Cohorts.startedOnIPT(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedOnIPT");
		
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
		        + "        where v.tb_screened = 'Y' and v.tb_results in ('Diagnosed with TB','Positive')\n"
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("tbClientsOnHAARTSQL",
		    ReportUtils.map(tbClientsOnHAARTSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("txCurr", ReportUtils.map(moh731Cohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("tbEnrollment",
		    ReportUtils.map(moh731Cohorts.tbEnrollment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((txCurr AND tbEnrollment) OR tbClientsOnHAARTSQL)");
		
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
		        + "where v.prep_screened = 'Y' and v.prep_results = 'Eligible'   and v.prep_treated = 'Y'\n"
		        + "  and v.visit_date < date(:startDate)   and v.voided = 0 group by v.client_id;";
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
		String sqlQuery = "select e.patient_id\n" + "from kenyaemr_etl.etl_prep_enrolment e\n"
		        + "where e.patient_type = 'New Patient'\n" + "  and e.population_type = 164929\n"
		        + "  and date(e.visit_date) between date(:startDate) and date(:endDate);";
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("initiatedPrEPReportingPeriodClinicalVisit",
		    ReportUtils.map(initiatedPrEPReportingPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("initiatedPrEPReportingPeriodProgram",
		    ReportUtils.map(initiatedPrEPReportingPeriodProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (initiatedPrEPReportingPeriodClinicalVisit OR initiatedPrEPReportingPeriodProgram)");
		
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
		        + "where v.prep_screened = 'Ongoing'   and v.visit_date between date(:startDate) and date(:endDate)\n"
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
		String sqlQuery = "select r.patient_id from kenyaemr_etl.etl_prep_monthly_refill r\n"
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "select hts.patient_id from kenyaemr_etl.etl_hts_test hts where hts.test_type = 2\n"
		        + "  and hts.final_test_result = 'Positive'   and hts.voided = 0\n"
		        + "  and hts.visit_date between date(:startDate) and date(:endDate) group by hts.patient_id;";
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(coalesce(v.self_test_date, v.visit_date)) between date(:startDate) and date(:endDate)\n"
		        + "  and v.test_confirmatory_results = 'Positive'   and v.voided = 0;";
		cd.setName("testedHIVPositiveWithinPeriodClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveWithinPeriodClinicalVisit ");
		
		return cd;
	}
	
	public CohortDefinition htsPositives() {
		String sqlQuery = "select patient_id\n" + "from ((select av.patient_id, av.encounter_id\n"
		        + "       from kenyaemr_etl.etl_mch_antenatal_visit av\n"
		        + "       where av.visit_date between date(:startDate) and date(:endDate)\n"
		        + "         and av.final_test_result = 'Positive')\n" + "      union\n"
		        + "      (select d.patient_id, d.encounter_id\n" + "       from kenyaemr_etl.etl_mchs_delivery d\n"
		        + "       where d.visit_date between date(:startDate) and date(:endDate)\n"
		        + "         and d.final_test_result = 'Positive')\n" + "      union\n"
		        + "      (select p.patient_id, p.encounter_id\n" + "       from kenyaemr_etl.etl_mch_postnatal_visit p\n"
		        + "       where p.visit_date between date(:startDate) and date(:endDate)\n"
		        + "         and p.final_test_result = 'Positive')\n" + "      union\n"
		        + "      (select t.patient_id, t.encounter_id\n" + "       from kenyaemr_etl.etl_hts_test t\n"
		        + "                inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = t.patient_id\n"
		        + "       where t.final_test_result = 'Positive'\n" + "         and t.voided = 0\n"
		        + "         and t.visit_date between date(:startDate) and date(:endDate))) a;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("hivPositives");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivPositives");
		return cd;
	}
	
	/**
	 * /** Turning HIV positive while on PrEP: Number of people on PrEP in each KP type who tested
	 * positive for HIV in the reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition turningPositiveWhileOnPrEP(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsPositives", ReportUtils.map(htsPositives(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnPrEP", ReportUtils.map(currentOnPrEP(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVPositiveWithinPeriodClinicalVisit",
		    ReportUtils.map(testedHIVPositiveWithinPeriodClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((currentOnPrEP AND htsPositives) OR testedHIVPositiveWithinPeriodClinicalVisit)");
		
		return cd;
	}
	
	/**
	 * Current on PrEP diagnosed with STI
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onPrEPDiagnosedWithSTI(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("currentOnPrEP", ReportUtils.map(currentOnPrEP(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("diagnosedWithSTI",
		    ReportUtils.map(diagnosedWithSTI(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("currentOnPrEP AND diagnosedWithSTI");
		
		return cd;
	}
	
	public CohortDefinition screenedForMentalHealthClinicalEnc() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where c.mental_health_results in\n"
		        + "      (\"Mild depression\", \"Moderate depression\", \"Moderate-severe depression\", \"Severe Depression\", \"Depression unlikely\")\n"
		        + "  and date(c.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("screenedForMentalHealthClinicalEnc");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screened For Mental Health During Clinical visit");
		
		return cd;
	}
	
	public CohortDefinition screenedForMentalHealthPHQ9() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id from kenyaemr_etl.etl_depression_screening d where date(d.visit_date) between date(:startDate) and date(:endDate) and d.PHQ_9_rating in (1115,157790,134011,134017,126627);";
		cd.setName("screenedForMentalHealthPHQ9");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screened For Mental Health With PHQ9");
		
		return cd;
	}
	
	public CohortDefinition screenedForMentalHealth(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("screenedForMentalHealthClinicalEnc",
		    ReportUtils.map(screenedForMentalHealthClinicalEnc(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("screenedForMentalHealthPHQ9",
		    ReportUtils.map(screenedForMentalHealthPHQ9(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (screenedForMentalHealthClinicalEnc OR screenedForMentalHealthPHQ9)");
		
		return cd;
	}
	
	public CohortDefinition diagnosedWithMentalHealthClinicalEnc() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where c.mental_health_results in\n"
		        + "      (\"Moderate depression\", \"Moderate-severe depression\", \"Severe Depression\")\n"
		        + "  and date(c.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("diagnosedWithMentalHealthClinicalEnc");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Diagnosed with Mental Health During Clinical visit");
		
		return cd;
	}
	
	public CohortDefinition diagnosedWithMentalHealthPHQ9() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id from kenyaemr_etl.etl_depression_screening d where date(d.visit_date) between date(:startDate) and date(:endDate) and d.PHQ_9_rating in (134011,134017,126627) ;";
		cd.setName("diagnosedWithMentalHealthPHQ9");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Diagnosed with Mental Health With PHQ9");
		
		return cd;
	}
	
	public CohortDefinition diagnosedWithMentalHealth(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("diagnosedWithMentalHealthClinicalEnc",
		    ReportUtils.map(diagnosedWithMentalHealthClinicalEnc(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("diagnosedWithMentalHealthPHQ9",
		    ReportUtils.map(diagnosedWithMentalHealthPHQ9(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (diagnosedWithMentalHealthClinicalEnc OR diagnosedWithMentalHealthPHQ9)");
		
		return cd;
	}
	
	public CohortDefinition treatedForMentalHealthWithinFacility() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where c.mental_health_support = 'Supported'\n"
		        + "    and date(c.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("treatedForMentalHealthWithinFacility");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Treated For Mental Health Within facility");
		
		return cd;
	}
	
	public CohortDefinition treatedForMentalHealth(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("treatedForMentalHealthWithinFacility",
		    ReportUtils.map(treatedForMentalHealthWithinFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND treatedForMentalHealthWithinFacility");
		
		return cd;
	}
	
	public CohortDefinition htsAllNumberTested() {
		String sqlQuery = "select patient_id\n" + "from kenyaemr_etl.etl_hts_test\n"
		        + "where final_test_result in ('Positive', 'Negative')\n" + "  and test_type = 1\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("htsAllNumberTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Hiv Number Tested");
		return cd;
	}
	
	public CohortDefinition kpsTestedForHIV(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsAllNumberTested",
		    ReportUtils.map(htsAllNumberTested(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsAllNumberTested");
		
		return cd;
	}
	
	public CohortDefinition htsReceivingHIVPosResults() {
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n"
		        + "where final_test_result = 'Positive' and test_type = 1\n"
		        + "  and t.patient_given_result = 'Yes' and visit_date between date(:startDate) and date(:endDate);";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("htsReceivingHIVPosResults");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Hiv Number Tested and received +ve HIV results");
		return cd;
	}
	
	/**
	 * KPS tested and received HIV Positive results
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpsReceivingHIVPosTestResults(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("htsReceivingHIVPosResults",
		    ReportUtils.map(htsReceivingHIVPosResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND htsReceivingHIVPosResults");
		
		return cd;
	}
	
	/**
	 * KPs living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition kpLWHIV() {
		String sqlQuery = "select a.kp_client\n"
		        + "from (select c.client_id  as kp_client,\n"
		        + "             e.client_id  as kp_known_pos,\n"
		        + "             h.patient_id as hiv_enrolled,\n"
		        + "             t.patient_id as hts_positive_patient\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left join (select e.client_id, e.visit_date as kp_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          where e.visit_date <= date(:endDate)\n"
		        + "                            and e.share_test_results = 'Yes I tested positive') e\n"
		        + "                         on c.client_id = e.client_id\n"
		        + "               left join (select h.patient_id,\n"
		        + "                                 coalesce(h.date_confirmed_hiv_positive,\n"
		        + "                                          coalesce(h.date_first_enrolled_in_care, h.visit_date)) as hiv_enrollment_date\n"
		        + "                          from kenyaemr_etl.etl_hiv_enrollment h\n"
		        + "                          where h.visit_date <= date(:endDate)) h on c.client_id = h.patient_id\n"
		        + "               left join (select t.patient_id, t.visit_date as hts_date\n"
		        + "                          from kenyaemr_etl.etl_hts_test t\n"
		        + "                          where t.visit_date <= date(:endDate)\n"
		        + "                            and t.test_type = 1\n"
		        + "                            and t.final_test_result = 'Positive') t on c.client_id = t.patient_id\n"
		        + "      group by c.client_id) a\n" + "where kp_known_pos is not null\n"
		        + "   or hiv_enrolled is not null\n" + "   or hts_positive_patient is not null;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("kpLWHIV");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("KPs Living with HIV");
		return cd;
	}
	
	/**
	 * KPs Living with HIV who were reached during the period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kplhivReached(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("kpLWHIV", ReportUtils.map(kpLWHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("kpsReachedWithinLastThreeMonths",
		    ReportUtils.map(kpsReachedWithinLastThreeMonths(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND kpLWHIV AND kpsReachedWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPLHIV Starting ART onsite and offsite
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kplhivStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOffSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOffSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOnSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOnSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("startedARTReportingPeriod",
		    ReportUtils.map(datimCohorts.startedOnART(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (hadClinicalVisitReportingPeriodAndStartingARTOffSite OR hadClinicalVisitReportingPeriodAndStartingARTOnSite OR startedARTReportingPeriod)");
		
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where v.pep_eligible ='Y' and v.exposure_type in ('Rape', 'Condom burst', 'Others')\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
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
	public CohortDefinition receivedPeerEducationWithinLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "         inner join kenyaemr_etl.etl_peer_calendar p\n"
		        + "                    on c.client_id = p.client_id\n"
		        + "where date(p.visit_date) between DATE_ADD( DATE_SUB(date(:endDate), INTERVAL 3 MONTH),INTERVAL 1 DAY) and date(:endDate);";
		cd.setName("receivedPeerEducationWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPeerEducationWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPs received peer education within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition receivedPeerEducationWithinReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "         inner join kenyaemr_etl.etl_peer_calendar p\n"
		        + "                    on c.client_id = p.client_id\n"
		        + "where date(p.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("receivedPeerEducationWithinReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPeerEducationWithinReportingPeriod");
		
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
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPeerEducationWithinLastThreeMonths",
		    ReportUtils.map(receivedPeerEducationWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPeerEducationWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPs reached within the last 3 months
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpsReachedWithinLastThreeMonths(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPeerEducationWithinLastThreeMonths",
		    ReportUtils.map(receivedPeerEducationWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivedClinicalServicesSQL",
		    ReportUtils.map(receivedClinicalServicesSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (receivedPeerEducationWithinLastThreeMonths OR receivedClinicalServicesSQL)");
		
		return cd;
	}
	
	/**
	 * Screened for STI within last 3 months
	 * 
	 * @return
	 */
	public CohortDefinition screenedForSTIWithinLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.sti_screened = 'Y' and v.visit_date\n"
		        + "      between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH),INTERVAL 1 DAY) and date(:endDate);";
		cd.setName("screenedForSTIWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPs Tested for HIV within last 3 months
	 * 
	 * @return
	 */
	public CohortDefinition testedForHIVWithinLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.kp_client\n"
		        + "from (select c.client_id  as kp_client,\n"
		        + "             v.client_id  as kp_visit_client,\n"
		        + "             t.patient_id as hts_client\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left join (select t.patient_id\n"
		        + "                          from kenyaemr_etl.etl_hts_test t\n"
		        + "                          where t.population_type = 'Key Population'\n"
		        + "                            and t.final_test_result in ('Positive', 'Negative')\n"
		        + "                            and t.visit_date between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)) t\n"
		        + "                         on c.client_id = t.patient_id\n"
		        + "               left join (select v.client_id\n"
		        + "                          from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          where v.visit_date between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "                            and v.hiv_tested = 'Yes') v on c.client_id = v.client_id\n"
		        + "      where c.visit_date <= date(:endDate)) a\n"
		        + "where (a.hts_client is not null or a.kp_visit_client is not null);";
		cd.setName("testedForHIVWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedForHIVWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * Screened for TB within last 3 months
	 * 
	 * @return
	 */
	public CohortDefinition screenedForTBWithinLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.tb_screened = 'Y' and date(v.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH),INTERVAL 1 DAY) and date(:endDate);";
		cd.setName("screenedForTBWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForTBWithinLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsFromClinicalVisitLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "group by v.client_id\n"
		        + "having (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0)) > 0;";
		cd.setName("receivingCondomsFromClinicalVisitLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromClinicalVisitLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsFromSTITxLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select s.client_id\n"
		        + "from kenyaemr_etl.etl_sti_treatment s\n"
		        + "where s.visit_date between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "group by s.client_id\n" + "having coalesce(sum(s.no_of_condoms), 0) > 0;";
		cd.setName("receivingCondomsFromSTITxLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromSTITxLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsFromPeerEduLastThreeMonths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id\n"
		        + "from kenyaemr_etl.etl_peer_calendar p\n"
		        + "where date(p.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "group by p.client_id\n" + "having (coalesce(sum(p.monthly_male_condoms_distributed), 0) +\n"
		        + "        coalesce(sum(p.monthly_female_condoms_distributed), 0)) > 0;";
		cd.setName("receivingCondomsFromPeerEduLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingCondomsFromPeerEduLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsWithinLastThreeMonths() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("receivingCondomsFromClinicalVisitLastThreeMonths",
		    ReportUtils.map(receivingCondomsFromClinicalVisitLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITxLastThreeMonths",
		    ReportUtils.map(receivingCondomsFromSTITxLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerEduLastThreeMonths",
		    ReportUtils.map(receivingCondomsFromPeerEduLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("receivingCondomsFromSTITxLastThreeMonths OR receivingCondomsFromPeerEduLastThreeMonths OR receivingCondomsFromClinicalVisitLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsWithinLastThreeMonths(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id, (coalesce(sum(v.lubes_no), 0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p\n"
		        + "           on c.client_id = p.client_id and\n"
		        + "              date(p.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                                    date(v.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and\n"
		        + "                                                                   date(v.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "      where c.key_population_type = '" + kpType + "'\n"
		        + "        and c.implementation_subcounty = :location group by c.client_id\n"
		        + "      having lubes_given >= 1) k;";
		cd.setName("receivingLubricantsWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingLubricantsWithinLastThreeMonths");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesWithinLastThreeMonths(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n"
		        + "from (select c.client_id,\n"
		        + "             (coalesce(sum(v.syringes_needles_no), 0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given\n"
		        + "      from kenyaemr_etl.etl_contact c\n"
		        + "               left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p\n"
		        + "           on c.client_id = p.client_id and date(p.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "               left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n"
		        + "                                                                    date(v.visit_date) between DATE_ADD(DATE_SUB(date(:endDate), INTERVAL 3 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "      where c.key_population_type = '" + kpType + "'\n"
		        + "        and c.implementation_subcounty = :location group by c.client_id\n"
		        + "      having needles_and_syringes_given >= 1) k;";
		cd.setName("receivingNeedlesWithinLastThreeMonths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.setDescription("receivingNeedlesWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPs reached within last three months with define package Defined package:Peer Education, STI
	 * screening, Knowledge of HIV status, TB screening, distribution of Condoms, Lubricants or
	 * Needle and Syringe.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpsReachedWithinLastThreeMonthsDefinedPackage(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPeerEducationWithinLastThreeMonths",
		    ReportUtils.map(receivedPeerEducationWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsWithinLastThreeMonths",
		    ReportUtils.map(receivingCondomsWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingLubricantsWithinLastThreeMonths", ReportUtils.map(
		    receivingLubricantsWithinLastThreeMonths(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivingNeedlesAndSyringesWithinLastThreeMonths", ReportUtils.map(
		    receivingNeedlesAndSyringesWithinLastThreeMonths(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedForHIVWithinLastThreeMonths",
		    ReportUtils.map(testedForHIVWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("screenedForTBWithinLastThreeMonths",
		    ReportUtils.map(screenedForTBWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("screenedForSTIWithinLastThreeMonths",
		    ReportUtils.map(screenedForSTIWithinLastThreeMonths(), "startDate=${startDate},endDate=${endDate}"));
		
		cd.setCompositionString("kpType AND kpCurr AND receivedPeerEducationWithinLastThreeMonths AND (receivingCondomsWithinLastThreeMonths OR receivingLubricantsWithinLastThreeMonths OR receivingNeedlesAndSyringesWithinLastThreeMonths) AND (testedForHIVWithinLastThreeMonths OR knownPositiveKPs) AND screenedForTBWithinLastThreeMonths AND screenedForSTIWithinLastThreeMonths");
		
		return cd;
	}
	
	/**
	 * KPs receiving peer education within the reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition kpsReceivingPeerEducationWithinReportingPeriod(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedPeerEducationWithinReportingPeriod",
		    ReportUtils.map(receivedPeerEducationWithinReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedPeerEducationWithinReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Receiving clinical services: Number of people in each KP type who received clinical services
	 * in the reporting period.
	 * 
	 * @return
	 */
	public CohortDefinition receivedClinicalServicesSQL() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v\n"
		        + "on c.client_id = v.client_id where date(v.visit_date) between DATE_ADD( DATE_SUB(date(:endDate), INTERVAL 3 MONTH),INTERVAL 1 DAY) and date(:endDate);";
		cd.setName("receivedClinicalServicesSQL");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedClinicalServicesSQL");
		
		return cd;
	}
	
	/**
	 * Receiving clinical services: Number of people in each KP type who received clinical services
	 * in the reporting period by KP type
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition receivedClinicalServices(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("receivedClinicalServicesSQL",
		    ReportUtils.map(receivedClinicalServicesSQL(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND receivedClinicalServicesSQL");
		
		return cd;
	}
	
	/**
	 * Clients enrolled in HIV care program within the reporting period - in this Facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInCareReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e\n"
		        + "where e.visit_date between date(:startDate)\n" + "and date(:endDate);";
		cd.setName("enrolledInCareReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrolledInCareReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Clients enrolled in HIV care program before reporting period - in this Facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition enrolledInCareBeforeReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n" + "from (select e.patient_id,\n"
		        + "             max(e.visit_date)     as enroll_date,\n"
		        + "             d.effective_disc_date as disc_date,\n"
		        + "             d.patient_id          as disc_patient\n" + "      from kenyaemr_etl.etl_hiv_enrollment e\n"
		        + "               left join (\n" + "          select patient_id,\n"
		        + "                 coalesce(date(effective_discontinuation_date), visit_date) visit_date,\n"
		        + "                 max(date(effective_discontinuation_date)) as               effective_disc_date\n"
		        + "          from kenyaemr_etl.etl_patient_program_discontinuation\n"
		        + "          where date(visit_date) <= date(:endDate)\n" + "            and program_name = 'HIV'\n"
		        + "          group by patient_id\n" + "      ) d on e.patient_id = d.patient_id\n"
		        + "      where e.visit_date < date(:startDate)\n" + "      group by e.patient_id\n"
		        + "      having enroll_date > disc_date\n" + "          or disc_patient is null) a\n"
		        + "group by a.patient_id;";
		cd.setName("enrolledInCarePreviousBeforePeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrolledInCareBeforeReportingPeriod");
		
		return cd;
	}
	
	/**
	 * ART clients - This facility/on-site
	 * 
	 * @return
	 */
	public CohortDefinition artPatients() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select d.patient_id from kenyaemr_etl.etl_drug_event d where date(d.date_started) <=date(:endDate)\n"
		        + "and d.program = 'HIV';";
		cd.setName("artPatients");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("artPatients");
		
		return cd;
	}
	
	/**
	 * Clients with Clinical visits within reporting period and not started on ART
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and v.initiated_art_this_month != 'Yes' and v.active_art !='Yes';\n";
		cd.setName("hadClinicalVisitReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Pre-ART Clients with Clinical visits within reporting period and receiving care elsewhere
	 * 
	 * @return
	 */
	public CohortDefinition preARTReceivingCareElsewhere() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and v.initiated_art_this_month != 'Yes' and v.active_art !='Yes' and v.hiv_care_facility ='Provided elsewhere';";
		cd.setName("preARTReceivingCareElsewhere");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("preARTReceivingCareElsewhere");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type who were enrolled in care this month or in a previous month
	 * and made a clinical visit on site in preparation for ART but have not started on ART during
	 * this visit. They should be counted only if there is no intention to start them on ART during
	 * the reporting month in this site.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onSitePreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("enrolledInCareReportingPeriod",
		    ReportUtils.map(enrolledInCareReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInCareBeforeReportingPeriod",
		    ReportUtils.map(enrolledInCareBeforeReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("artPatients", ReportUtils.map(artPatients(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("hadClinicalVisitReportingPeriod",
		    ReportUtils.map(hadClinicalVisitReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((enrolledInCareBeforeReportingPeriod AND hadClinicalVisitReportingPeriod) OR enrolledInCareReportingPeriod) AND NOT artPatients");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type who were enrolled in care this month or in a previous month
	 * and made a clinical visit off site in preparation for ART but have not started on ART during
	 * this visit. They should be counted only if there is no intention to start them on ART during
	 * the reporting month in another site.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition offSitePreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("preARTReceivingCareElsewhere",
		    ReportUtils.map(preARTReceivingCareElsewhere(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND preARTReceivingCareElsewhere");
		
		return cd;
	}
	
	/**
	 * Total Pre-ART = Onsite + Offsite
	 * 
	 * @return
	 */
	public CohortDefinition totalOnPreART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("offSitePreART",
		    ReportUtils.map(offSitePreART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("onSitePreART",
		    ReportUtils.map(onSitePreART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("onSitePreART OR offSitePreART");
		
		return cd;
	}
	
	/**
	 * Clients who had a clinical visit within the reporting period and were recorded to starting
	 * ART during the visit - This facility/on site
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriodAndStartingARTOnSite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and v.initiated_art_this_month = 'Yes' and hiv_care_facility = 'Provided here';";
		cd.setName("hadClinicalVisitReportingPeriodAndStartingARTOnSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriodAndStartingARTOnSite");
		
		return cd;
	}
	
	/**
	 * Clients who had a clinical visit within the reporting period and were recorded to starting
	 * ART during the visit - off site
	 * 
	 * @return
	 */
	public CohortDefinition hadClinicalVisitReportingPeriodAndStartingARTOffSite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and v.initiated_art_this_month = 'Yes' and hiv_care_facility = 'Provided elsewhere';";
		cd.setName("hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type started on HAART for treatment at this site within the
	 * reporting period - This facility/On-site
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onSiteStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedARTReportingPeriod",
		    ReportUtils.map(datimCohorts.startedOnART(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOnSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOnSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (startedARTReportingPeriod OR hadClinicalVisitReportingPeriodAndStartingARTOnSite)");
		
		return cd;
	}
	
	/**
	 * Number of people of each KP type started on HAART for treatment in another site within the
	 * reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition offSiteStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("hadClinicalVisitReportingPeriodAndStartingARTOffSite", ReportUtils.map(
		    hadClinicalVisitReportingPeriodAndStartingARTOffSite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND hadClinicalVisitReportingPeriodAndStartingARTOffSite");
		
		return cd;
	}
	
	/**
	 * Total starting ART = On site + off site
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition totalStartingART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("onSiteStartingART",
		    ReportUtils.map(onSiteStartingART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("offSiteStartingART",
		    ReportUtils.map(offSiteStartingART(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("onSiteStartingART OR offSiteStartingART");
		
		return cd;
	}
	
	/**
	 * Current ART - On site Number of people in each KP type who: 1. started therapy on site this
	 * month or 2. started therapy on site before this month but made a visit to collect drugs this
	 * month or 3. started therapy on site before this month but did not make a visit to the
	 * facility during this month because they had been given enough drugs during visits before this
	 * month to cover the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentlyOnARTOnSite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnArt",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND currentlyOnArt");
		
		return cd;
	}
	
	/**
	 * Current on ART elsewhere as reported during clinical visit within the reporting period
	 * Includes: 1.started therapy this month off site 2.started therapy off site before this month
	 * but visited the offsite clinic to collect drugs this month Should include <3.started therapy
	 * off site before this month but did not make a visit to the facility during this month because
	 * they had been given enough drugs during visits before this month to cover the reporting
	 * month> when hiv followup tca is collected during a KP clinical visit
	 * 
	 * @return
	 */
	public CohortDefinition reportedCurrentOnARTElsewhereClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "      and hiv_care_facility = 'Provided elsewhere' and (v.initiated_art_this_month = 'Yes' or v.active_art = 'Yes');";
		cd.setName("reportedCurrentOnARTElsewhereClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("reportedCurrentOnARTElsewhereClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Current ART - Off site Number of people in each KP type who: 1. started therapy on site this
	 * month or 2. started therapy on site before this month but made a visit to collect drugs this
	 * month or 3. started therapy on site before this month but did not make a visit to the
	 * facility during this month because they had been given enough drugs during visits before this
	 * month to cover the reporting month.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition currentlyOnARTOffSite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("reportedCurrentOnARTElsewhereClinicalVisit",
		    ReportUtils.map(reportedCurrentOnARTElsewhereClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND reportedCurrentOnARTElsewhereClinicalVisit");
		
		return cd;
	}
	
	/**
	 * Current ART = On site + offsite
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition totalCurrentlyOnART(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("currentlyOnARTOnSite",
		    ReportUtils.map(currentlyOnARTOnSite(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnARTOffSite",
		    ReportUtils.map(currentlyOnARTOffSite(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.setCompositionString("currentlyOnARTOnSite OR currentlyOnARTOffSite");
		
		return cd;
	}
	
	/**
	 * Started ART 12 months ago
	 * 
	 * @return
	 */
	public CohortDefinition startedART12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n"
		        + "from (select e.patient_id,\n"
		        + "             e.date_started\n"
		        + "      from (select dr.patient_id,\n"
		        + "                   min(date(dr.date_started)) as date_started\n"
		        + "            from kenyaemr_etl.etl_drug_event dr\n"
		        + "                     join kenyaemr_etl.etl_hiv_enrollment e on dr.patient_id = e.patient_id\n"
		        + "                     join kenyaemr_etl.etl_patient_demographics p on p.patient_id = dr.patient_id and p.voided = 0\n"
		        + "            where dr.program = 'HIV'\n"
		        + "            group by dr.patient_id) e\n"
		        + "      where date(e.date_started) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date_sub(date(:endDate), INTERVAL 12 MONTH)\n"
		        + "      group by e.patient_id) a;";
		cd.setName("startedART12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedART12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Transfered out within last 12 months to effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition transferOutsSince12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n"
		        + "from (select d.patient_id,\n"
		        + "             coalesce(mid(max(concat(date(d.visit_date), date(d.transfer_date))), 11),\n"
		        + "                      mid(max(concat(date(d.visit_date), date(date(d.effective_discontinuation_date)))), 11),\n"
		        + "                      max(date(d.visit_date))) as to_date\n"
		        + "      from kenyaemr_etl.etl_patient_program_discontinuation d\n" + "      where program_name = 'HIV'\n"
		        + "        and date(d.visit_date) <= date(:endDate)\n" + "        and d.discontinuation_reason = 159492\n"
		        + "      group by d.patient_id\n"
		        + "      having to_date between date_sub(date(:startDate), INTERVAL 12 MONTH) and (date(:endDate))) a;";
		cd.setName("transferOutsSince12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("transferOutsSince12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * Transfered in within last 12 months to effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition transferInsSince12MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n" + "from (select e.patient_id,\n"
		        + "             mid(max(concat(date(e.visit_date), e.patient_type)), 11) as patient_type,\n"
		        + "             coalesce(mid(max(concat(date(e.visit_date), date(e.transfer_in_date))), 11),\n"
		        + "                      max(date(e.visit_date)))                        as ti_date\n"
		        + "      from kenyaemr_etl.etl_hiv_enrollment e\n" + "      group by e.patient_id\n"
		        + "      having patient_type = 160563\n"
		        + "         and ti_date between date_sub(date(:startDate), INTERVAL 12 MONTH) and (date(:endDate))) a;";
		cd.setName("transferInsSince12MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("transferInsSince12MonthsAgo");
		
		return cd;
	}
	
	/**
	 * 5.4 Retention on ART: On-site On ART at 12 months: Number of clients still on ART 12 months
	 * after starting ART regardless of regimen.
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition onARTAt12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentlyOnArt",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("startedART12MonthsAgo",
		    ReportUtils.map(startedART12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedART12MonthsAgo AND currentlyOnArt");
		
		return cd;
	}
	
	/**
	 * Net cohort at 12 months : This refers to the number of clients started ART in the same month
	 * plus transfer ins and minus transfer outs. Take the number of patients in the original
	 * cohort, add the Transfers In (TIs), and subtract the Transfers Out (TOs) to get the net
	 * cohort
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition netCohortAt12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedART12MonthsAgo",
		    ReportUtils.map(startedART12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transferInsSince12MonthsAgo",
		    ReportUtils.map(transferInsSince12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transferOutsSince12MonthsAgo",
		    ReportUtils.map(transferOutsSince12MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND ((startedART12MonthsAgo AND NOT transferOutsSince12MonthsAgo) OR (startedART12MonthsAgo AND transferInsSince12MonthsAgo))");
		
		return cd;
	}
	
	/**
	 * Viral load result_12mths: On-site Number of people in the 12-month cohort in each KP type who
	 * had a viral load test on site at 12 months and whose results were available at the time of
	 * analysis
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition viralLoad12MonthsOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("patientsWithVLResultsLast12Months",
		    ReportUtils.map(moh731Cohorts.patientsWithVLResultsLast12Months(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND patientsWithVLResultsLast12Months");
		
		return cd;
	}
	
	/**
	 * ith VL results within last 12 Months
	 * 
	 * @return
	 */
	public CohortDefinition withVLResultsLast12Months() {
		
		String sqlQuery = "select e.patient_id\n"
		        + "                from (\n"
		        + "                select  net.patient_id as patient_id\n"
		        + "                  from ( \n"
		        + "                  select e.patient_id,e.date_started, d.visit_date as dis_date, if(d.visit_date is not null and d.discontinuation_reason=159492, 1, 0) as TOut, d.date_died,\n"
		        + "                  mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca, \n"
		        + "                  if(enr.transfer_in_date is not null, 1, 0) as TIn, max(fup.visit_date) as latest_vis_date\n"
		        + "                   from (select e.patient_id,p.dob,p.Gender,min(e.date_started) as date_started\n"
		        + "                   from kenyaemr_etl.etl_drug_event e \n"
		        + "                   join kenyaemr_etl.etl_patient_demographics p on p.patient_id=e.patient_id \n"
		        + "                   where e.program='HIV' \n"
		        + "                   group by e.patient_id) e \n"
		        + "                   left outer join kenyaemr_etl.etl_patient_program_discontinuation d on d.patient_id=e.patient_id and d.program_uuid='2bdada65-4c72-4a48-8730-859890e25cee' \n"
		        + "                   left outer join kenyaemr_etl.etl_hiv_enrollment enr on enr.patient_id=e.patient_id \n"
		        + "                   left outer join kenyaemr_etl.etl_patient_hiv_followup fup on fup.patient_id=e.patient_id \n"
		        + "                   where  date(e.date_started) between date_sub(date(:startDate) , interval 1 year) and date_sub(date(:endDate) , interval 1 year) \n"
		        + "                   group by e.patient_id\n"
		        + "                  having   (dis_date>date(:endDate) or dis_date is null or TOut=0 ) and (\n"
		        + "                      (date(latest_tca) > date(:endDate) and (date(latest_tca) > date(dis_date) or dis_date is null ))  or\n"
		        + "                      (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_tca) >= date(latest_vis_date)) ) ) or\n"
		        + "                      (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_vis_date) >= date(latest_tca)) or date(latest_tca) > curdate()) ) and\n"
		        + "                      (date(latest_tca) > date(dis_date) or dis_date is null )\n"
		        + "                      )\n"
		        + "                   )net ) e\n"
		        + "                 inner join\n"
		        + "                 (\n"
		        + "                  select\n"
		        + "                    patient_id,\n"
		        + "                    visit_date,\n"
		        + "                    if(lab_test = 856, test_result, if(lab_test=1305 and test_result = 1302, 'LDL','')) as vl_result,\n"
		        + "                    urgency\n"
		        + "                  from kenyaemr_etl.etl_laboratory_extract\n"
		        + "                  where lab_test in (1305, 856)  and visit_date between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + "                  ) vl_result on vl_result.patient_id = e.patient_id\n"
		        + "                group by e.patient_id\n" + "                ;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsWithVLResultsLast12Months");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on ART with VL results within last 12 Months");
		return cd;
	}
	
	/**
	 * Suppressed VL < 200 cps/ml
	 * 
	 * @return
	 */
	public CohortDefinition suppressedVlBelow200CopiesPerMlOnsite() {
		
		String sqlQuery = "select e.patient_id\n"
		        + "from (\n"
		        + "select  net.patient_id as patient_id\n"
		        + "from (\n"
		        + "select e.patient_id,e.date_started, d.visit_date as dis_date, if(d.visit_date is not null and d.discontinuation_reason=159492, 1, 0) as TOut, d.date_died,\n"
		        + "mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca,\n"
		        + "if(enr.transfer_in_date is not null, 1, 0) as TIn, max(fup.visit_date) as latest_vis_date\n"
		        + " from (select e.patient_id,p.dob,p.Gender,min(e.date_started) as date_started\n"
		        + " from kenyaemr_etl.etl_drug_event e\n"
		        + " join kenyaemr_etl.etl_patient_demographics p on p.patient_id=e.patient_id\n"
		        + " where e.program='HIV'\n"
		        + " group by e.patient_id) e\n"
		        + " left outer join kenyaemr_etl.etl_patient_program_discontinuation d on d.patient_id=e.patient_id and d.program_uuid='2bdada65-4c72-4a48-8730-859890e25cee'\n"
		        + " left outer join kenyaemr_etl.etl_hiv_enrollment enr on enr.patient_id=e.patient_id\n"
		        + " left outer join kenyaemr_etl.etl_patient_hiv_followup fup on fup.patient_id=e.patient_id\n"
		        + " where  date(e.date_started) between date_sub(date(:startDate) , interval 1 year) and date_sub(date(:endDate) , interval 1 year)\n"
		        + " group by e.patient_id\n"
		        + "having   (dis_date>date(:endDate) or dis_date is null or TOut=0 ) and (\n"
		        + "    (date(latest_tca) > date(:endDate) and (date(latest_tca) > date(dis_date) or dis_date is null ))  or\n"
		        + "    (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_tca) >= date(latest_vis_date)) ) ) or\n"
		        + "    (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_vis_date) >= date(latest_tca)) or date(latest_tca) > curdate()) ) and\n"
		        + "    (date(latest_tca) > date(dis_date) or dis_date is null )\n"
		        + "    )\n"
		        + " )net ) e\n"
		        + "inner join\n"
		        + "(\n"
		        + "select\n"
		        + "       b.patient_id,\n"
		        + "       max(b.visit_date) as vl_date,\n"
		        + "       mid(max(concat(b.visit_date,b.lab_test)),11) as lab_test,\n"
		        + "       if(mid(max(concat(b.visit_date,b.lab_test)),11) = 856, mid(max(concat(b.visit_date,b.test_result)),11), if(mid(max(concat(b.visit_date,b.lab_test)),11)=1305 and mid(max(concat(visit_date,test_result)),11) = 1302, \"LDL\",\"\")) as vl_result,\n"
		        + "       mid(max(concat(b.visit_date,b.urgency)),11) as urgency\n"
		        + "from (select x.patient_id as patient_id,x.visit_date as visit_date,x.lab_test as lab_test, x.test_result as test_result,urgency as urgency\n"
		        + "      from kenyaemr_etl.etl_laboratory_extract x where x.lab_test in (1305,856)\n"
		        + "      group by x.patient_id,x.visit_date order by visit_date desc)b\n"
		        + "group by patient_id\n"
		        + "having max(visit_date) between\n"
		        + "           date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + ") vl_result on vl_result.patient_id = e.patient_id\n"
		        + "group by e.patient_id\n"
		        + "having mid(max(concat(vl_result.vl_date, vl_result.vl_result)), 11)='LDL' or mid(max(concat(vl_result.vl_date, vl_result.vl_result)), 11)<200;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsWithSuppressedVlLast12Months");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on ART with Suppressed VL within last 12 Months");
		return cd;
	}
	
	/**
	 * Suppressed VL < 50 cps/ml
	 * 
	 * @return
	 */
	public CohortDefinition suppressedVlBelow50CopiesPerMlOnsite() {
		
		String sqlQuery = "select e.patient_id\n"
		        + "from (\n"
		        + "select  net.patient_id as patient_id\n"
		        + "from (\n"
		        + "select e.patient_id,e.date_started, d.visit_date as dis_date, if(d.visit_date is not null and d.discontinuation_reason=159492, 1, 0) as TOut, d.date_died,\n"
		        + "mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca,\n"
		        + "if(enr.transfer_in_date is not null, 1, 0) as TIn, max(fup.visit_date) as latest_vis_date\n"
		        + " from (select e.patient_id,p.dob,p.Gender,min(e.date_started) as date_started\n"
		        + " from kenyaemr_etl.etl_drug_event e\n"
		        + " join kenyaemr_etl.etl_patient_demographics p on p.patient_id=e.patient_id\n"
		        + " where e.program='HIV'\n"
		        + " group by e.patient_id) e\n"
		        + " left outer join kenyaemr_etl.etl_patient_program_discontinuation d on d.patient_id=e.patient_id and d.program_uuid='2bdada65-4c72-4a48-8730-859890e25cee'\n"
		        + " left outer join kenyaemr_etl.etl_hiv_enrollment enr on enr.patient_id=e.patient_id\n"
		        + " left outer join kenyaemr_etl.etl_patient_hiv_followup fup on fup.patient_id=e.patient_id\n"
		        + " where  date(e.date_started) between date_sub(date(:startDate) , interval 1 year) and date_sub(date(:endDate) , interval 1 year)\n"
		        + " group by e.patient_id\n"
		        + "having   (dis_date>date(:endDate) or dis_date is null or TOut=0 ) and (\n"
		        + "    (date(latest_tca) > date(:endDate) and (date(latest_tca) > date(dis_date) or dis_date is null ))  or\n"
		        + "    (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_tca) >= date(latest_vis_date)) ) ) or\n"
		        + "    (((date(latest_tca) between date(:startDate) and date(:endDate)) and (date(latest_vis_date) >= date(latest_tca)) or date(latest_tca) > curdate()) ) and\n"
		        + "    (date(latest_tca) > date(dis_date) or dis_date is null )\n"
		        + "    )\n"
		        + " )net ) e\n"
		        + "inner join\n"
		        + "(\n"
		        + "select\n"
		        + "       b.patient_id,\n"
		        + "       max(b.visit_date) as vl_date,\n"
		        + "       mid(max(concat(b.visit_date,b.lab_test)),11) as lab_test,\n"
		        + "       if(mid(max(concat(b.visit_date,b.lab_test)),11) = 856, mid(max(concat(b.visit_date,b.test_result)),11), if(mid(max(concat(b.visit_date,b.lab_test)),11)=1305 and mid(max(concat(visit_date,test_result)),11) = 1302, \"LDL\",\"\")) as vl_result,\n"
		        + "       mid(max(concat(b.visit_date,b.urgency)),11) as urgency\n"
		        + "from (select x.patient_id as patient_id,x.visit_date as visit_date,x.lab_test as lab_test, x.test_result as test_result,urgency as urgency\n"
		        + "      from kenyaemr_etl.etl_laboratory_extract x where x.lab_test in (1305,856)\n"
		        + "      group by x.patient_id,x.visit_date order by visit_date desc)b\n"
		        + "group by patient_id\n"
		        + "having max(visit_date) between\n"
		        + "           date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1 DAY) and date(:endDate)\n"
		        + ") vl_result on vl_result.patient_id = e.patient_id\n"
		        + "group by e.patient_id\n"
		        + "having mid(max(concat(vl_result.vl_date, vl_result.vl_result)), 11)='LDL' or mid(max(concat(vl_result.vl_date, vl_result.vl_result)), 11)<50;";
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("patientsWithSuppressedVlLast12Months");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Patients on ART with Suppressed VL within last 12 Months");
		return cd;
	}
	
	public CohortDefinition viralLoadResultsWithinLast12Months(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("withVLResultsLast12Months",
		    ReportUtils.map(withVLResultsLast12Months(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("viralLoad12MonthsOffsite",
		    ReportUtils.map(viralLoad12MonthsOffsite(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (withVLResultsLast12Months OR viralLoad12MonthsOffsite)");
		
		return cd;
	}
	
	public CohortDefinition suppressedVLUnder200CpsPerMlWithinLast12Months(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("suppressedVlBelow200CopiesPerMlOnsite",
		    ReportUtils.map(suppressedVlBelow200CopiesPerMlOnsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("suppressedVlUnder200CpPerMlWithinLast12MonthsOffsite", ReportUtils.map(
		    suppressedVlUnder200CpPerMlWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (suppressedVlBelow200CopiesPerMlOnsite OR (currentOnArtOffsite AND suppressedVlUnder200CpPerMlWithinLast12MonthsOffsite))");
		
		return cd;
	}
	
	public CohortDefinition suppressedVLUnder50CpsPerMlWithinLast12Months(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("suppressedVlBelow50CopiesPerMlOnsite",
		    ReportUtils.map(suppressedVlBelow50CopiesPerMlOnsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("suppressedVlUnder50CpPerMlWithinLast12MonthsOffsite", ReportUtils.map(
		    suppressedVlUnder50CpPerMlWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (suppressedVlBelow50CopiesPerMlOnsite OR (currentOnArtOffsite AND suppressedVlUnder50CpPerMlWithinLast12MonthsOffsite))");
		
		return cd;
	}
	
/**
	 * Viral load <1000_12mths: On-site
	 * 	number of people in the 12-month cohort in each KP type whose 12-month viral load test
	 *  was done on site and who have a viral load less than 1000 copies per ML.
 	 * @param kpType
	 * @return
	 */
	public CohortDefinition suppressedViralLoad12MonthsCohortOnsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("patientsWithSuppressedVlLast12Months", ReportUtils.map(
		    moh731Cohorts.patientsWithSuppressedVlLast12Months(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND patientsWithSuppressedVlLast12Months");
		
		return cd;
	}
	
	/**
	 * Had a offsite vl test done within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition vlTestDoneWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "  and c.vl_test_done = 'Y';";
		cd.setName("vlTestDoneWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vlTestDoneWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * VL test done within the last 12 months on-site
	 * 
	 * @return
	 */
	public CohortDefinition vlTestDoneWithinLast12MonthsOnsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select l.patient_id\n" + "from kenyaemr_etl.etl_laboratory_extract l\n"
		        + "where date(l.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "  and l.lab_test in (856, 1305);";
		cd.setName("vlTestDoneWithinLast12MonthsOnsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vlTestDoneWithinLast12MonthsOnsite");
		
		return cd;
	}
	
	/**
	 * Pregnant or BF from greencard eligible for VL
	 * 
	 * @return
	 */
	public CohortDefinition eligibleForVLPregnantOrBreastfeeding() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n" + "from (select f.patient_id,\n"
		        + "             mid(max(concat(f.visit_date, f.pregnancy_status)), 11) as pregnancy_status,\n"
		        + "             mid(max(concat(f.visit_date, f.breastfeeding)), 11)    as breastfeeding\n"
		        + "      from kenyaemr_etl.etl_patient_hiv_followup f\n"
		        + "      where f.visit_date between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "      group by patient_id) a\n"
		        + "where (a.breastfeeding = 1065 or a.pregnancy_status = 1065);";
		cd.setName("eligibleForVLPregnantOrBreastfeeding");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("eligibleForVLPregnantOrBreastfeeding");
		
		return cd;
	}
	
	/**
	 * Started ART eligible for VL
	 * 
	 * @return
	 */
	public CohortDefinition startedARTEligibleForVl() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select net.patient_id\n"
		        + "from (select e.patient_id,\n"
		        + "             e.date_started,\n"
		        + "             d.visit_date                                                    as dis_date,\n"
		        + "             if(d.visit_date is not null, 1, 0)                              as TOut,\n"
		        + "             e.regimen,\n"
		        + "             e.regimen_line,\n"
		        + "             e.alternative_regimen,\n"
		        + "             mid(max(concat(fup.visit_date, fup.next_appointment_date)), 11) as latest_tca,\n"
		        + "             max(if(enr.date_started_art_at_transferring_facility is not null and\n"
		        + "                    enr.facility_transferred_from is not null, 1, 0))        as TI_on_art,\n"
		        + "             max(if(enr.transfer_in_date is not null, 1, 0))                 as TIn,\n"
		        + "             max(enr.patient_type)                                           as latest_patient_type,\n"
		        + "             max(fup.visit_date)                                             as latest_vis_date\n"
		        + "      from (select e.patient_id,\n"
		        + "                   min(e.date_started)                                  as date_started,\n"
		        + "                   mid(min(concat(e.date_started, e.regimen_name)), 11) as regimen,\n"
		        + "                   mid(min(concat(e.date_started, e.regimen_line)), 11) as regimen_line,\n"
		        + "                   max(if(discontinued, 1, 0))                          as alternative_regimen\n"
		        + "            from kenyaemr_etl.etl_drug_event e\n"
		        + "                     join kenyaemr_etl.etl_patient_demographics p on p.patient_id = e.patient_id\n"
		        + "            where e.program = 'HIV'\n"
		        + "            group by e.patient_id) e\n"
		        + "               left outer join kenyaemr_etl.etl_patient_program_discontinuation d on d.patient_id = e.patient_id\n"
		        + "               left outer join kenyaemr_etl.etl_hiv_enrollment enr on enr.patient_id = e.patient_id\n"
		        + "               left outer join kenyaemr_etl.etl_patient_hiv_followup fup on fup.patient_id = e.patient_id\n"
		        + "      where date(e.date_started) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                                  DAY) and date(:endDate)\n"
		        + "      group by e.patient_id\n" + "      having TI_on_art = 0\n"
		        + "         and latest_patient_type in (164144, 160563, 159833)) net;";
		cd.setName("startedARTEligibleForVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedARTEligibleForVl");
		
		return cd;
	}
	
	/**
	 * Last VL suppressed (<200 cps/ml) and client was 0-24 yrs old
	 * 
	 * @return
	 */
	public CohortDefinition lastVLSuppressed0To24YearsOldsEligibleForVl() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n"
		        + "from (select l.patient_id,\n"
		        + "             mid(max(concat(date(l.visit_date), coalesce(date(l.date_test_requested), date(l.visit_date)))),\n"
		        + "                 11) as last_vl_date,\n"
		        + "             mid(max(concat(date(visit_date),\n"
		        + "                            if(lab_test = 856, test_result, if(lab_test = 1305 and test_result = 1302, 'LDL', '')),\n"
		        + "                            '')),\n" + "                 11) as vl_result,\n" + "             d.dob\n"
		        + "      from kenyaemr_etl.etl_laboratory_extract l\n"
		        + "               inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = l.patient_id\n"
		        + "      where lab_test in (1305, 856)\n"
		        + "        and coalesce(date(date_test_requested), date(visit_date)) <= date(:endDate)\n"
		        + "      GROUP BY patient_id) a\n" + "where timestampdiff(YEAR, a.DOB, a.last_vl_date) between 0 and 24\n"
		        + "  and (a.vl_result < 200 or a.vl_result = 'LDL')\n"
		        + "  and last_vl_date < date_sub(date(:endDate), INTERVAL 6 MONTH);";
		cd.setName("lastVLSuppressed0To24YearsOldsEligibleForVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("lastVLSuppressed0To24YearsOldsEligibleForVl");
		
		return cd;
	}
	
	/**
	 * Last VL unsuppressed and now it is over 3 months to end date since last VL
	 * 
	 * @return
	 */
	public CohortDefinition lastVLUnsuppressedEligibleForVl() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n"
		        + "from (select l.patient_id,\n"
		        + "             mid(max(concat(date(l.visit_date), coalesce(date(l.date_test_requested), date(l.visit_date)))),\n"
		        + "                 11) as last_vl_date,\n"
		        + "             mid(max(concat(date(visit_date),\n"
		        + "                            if(lab_test = 856, test_result, if(lab_test = 1305 and test_result = 1302, 'LDL', '')),\n"
		        + "                            '')),\n" + "                 11) as vl_result,\n" + "             d.dob\n"
		        + "      from kenyaemr_etl.etl_laboratory_extract l\n"
		        + "               inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = l.patient_id\n"
		        + "      where lab_test in (1305, 856)\n"
		        + "        and coalesce(date(date_test_requested), date(visit_date)) <= date(:endDate)\n"
		        + "      GROUP BY patient_id) a\n" + "where a.vl_result >= 200\n"
		        + "  and timestampdiff(DAY, last_vl_date, date(:endDate)) > 92;";
		cd.setName("lastVLUnsuppressedEligibleForVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("lastVLUnsuppressedEligibleForVl");
		
		return cd;
	}
	
	/**
	 * If last VL was suppressed (< 200) and age >= 25 years
	 * 
	 * @return
	 */
	public CohortDefinition lastVLSuppressedAged25AndAboveEligibleForVl() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select a.patient_id\n"
		        + "from (select l.patient_id,\n"
		        + "             mid(max(concat(date(l.visit_date), coalesce(date(l.date_test_requested), date(l.visit_date)))),\n"
		        + "                 11) as last_vl_date,\n"
		        + "             mid(max(concat(date(visit_date),\n"
		        + "                            if(lab_test = 856, test_result, if(lab_test = 1305 and test_result = 1302, 'LDL', '')),\n"
		        + "                            '')),\n" + "                 11) as vl_result,\n" + "             d.dob\n"
		        + "      from kenyaemr_etl.etl_laboratory_extract l\n"
		        + "               inner join kenyaemr_etl.etl_patient_demographics d on d.patient_id = l.patient_id\n"
		        + "      where lab_test in (1305, 856)\n"
		        + "        and coalesce(date(date_test_requested), date(visit_date)) <= date(:endDate)\n"
		        + "      GROUP BY patient_id) a\n" + "where timestampdiff(YEAR, a.DOB, a.last_vl_date) >= 25\n"
		        + "  and (a.vl_result < 200 or vl_result = 'LDL')\n"
		        + "  and last_vl_date < date_sub(date(:endDate), INTERVAL 12 MONTH);";
		cd.setName("lastVLSuppressedAged25AndAboveEligibleForVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("lastVLSuppressedAged25AndAboveEligibleForVl");
		
		return cd;
	}
	
	/**
	 * Tested +ve and 3 months later, no VL test
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositiveEligibleForVl() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n"
		        + "where t.final_test_result = 'Positive'\n" + "  and t.test_type = 1\n"
		        + "  and date(t.visit_date) <= date(:endDate)\n"
		        + "  and date(t.visit_date) >= date_add(date_sub(date(:endDate), INTERVAL 12 + 3 MONTH), INTERVAL 1 DAY);";
		cd.setName("testedHIVPositiveEligibleForVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedHIVPositiveEligibleForVl");
		
		return cd;
	}
	
	/**
	 * Eligible for VL within the last 12 months offsite
	 * 
	 * @return
	 */
	public CohortDefinition eligibleForVLLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "  and c.eligible_vl = 'Yes';";
		cd.setName("eligibleForVLLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("eligibleForVLLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Eligible for VL withing the last 12 months
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition eligibleForVLWithinLast12Months(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentlyOnArtOnSite",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("eligibleForVLLast12MonthsOffsite",
		    ReportUtils.map(eligibleForVLLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("eligibleForVLPregnantOrBreastfeeding",
		    ReportUtils.map(eligibleForVLPregnantOrBreastfeeding(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("startedARTEligibleForVl",
		    ReportUtils.map(startedARTEligibleForVl(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("lastVLSuppressed0To24YearsOldsEligibleForVl",
		    ReportUtils.map(lastVLSuppressed0To24YearsOldsEligibleForVl(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("lastVLUnsuppressedEligibleForVl",
		    ReportUtils.map(lastVLUnsuppressedEligibleForVl(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("lastVLSuppressedAged25AndAboveEligibleForVl",
		    ReportUtils.map(lastVLSuppressedAged25AndAboveEligibleForVl(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("testedHIVPositiveEligibleForVl",
		    ReportUtils.map(testedHIVPositiveEligibleForVl(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (currentlyOnArtOnSite AND currentOnArtOffsite) AND (eligibleForVLLast12MonthsOffsite OR eligibleForVLPregnantOrBreastfeeding OR startedARTEligibleForVl "
		        + "OR lastVLSuppressed0To24YearsOldsEligibleForVl OR lastVLUnsuppressedEligibleForVl OR lastVLSuppressedAged25AndAboveEligibleForVl OR testedHIVPositiveEligibleForVl)");
		
		return cd;
	}
	
	/**
	 * VL test done within the last 12 months
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition viralLoadTestDoneWithinLast12Months(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentlyOnArtOnSite",
		    ReportUtils.map(datimCohorts.currentlyOnArt(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("vlTestDoneWithinLast12MonthsOnsite",
		    ReportUtils.map(vlTestDoneWithinLast12MonthsOnsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("vlTestDoneWithinLast12MonthsOffsite",
		    ReportUtils.map(vlTestDoneWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND (currentlyOnArtOnSite AND currentOnArtOffsite) AND (vlTestDoneWithinLast12MonthsOnsite OR vlTestDoneWithinLast12MonthsOffsite)");
		
		return cd;
	}
	
	/**
	 * Had a offsite vl test results within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition vlTestResultsWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.vl_results)), 11) is not null;";
		cd.setName("vlTestResultsWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("vlTestResultsWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Had a suppressed VL test result < 200 cps/ml within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition suppressedVlUnder200CpPerMlWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.vl_results)), 11) = 'LDL'\n"
		        + "    or mid(max(concat(date(c.visit_date), c.vl_results)), 11) < 200;";
		cd.setName("suppresedVlUnder200CpPerMlWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("suppresedVlUnder200CpPerMlWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Had a suppressed VL test result < 50 cps/ml within the last 12 months
	 * 
	 * @return
	 */
	public CohortDefinition suppressedVlUnder50CpPerMlWithinLast12MonthsOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_add(date_sub(date(:endDate), INTERVAL 12 MONTH), INTERVAL 1\n"
		        + "                                          DAY) and date(:endDate)\n" + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.vl_results)), 11) = 'LDL'\n"
		        + "    or mid(max(concat(date(c.visit_date), c.vl_results)), 11) < 50;";
		cd.setName("suppresedVlUnder50CpPerMlWithinLast12MonthsOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("suppresedVlUnder50CpPerMlWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Current on ART elsewhere : Has latest clinical visit on or before reporting date and next
	 * appointment date >= reporting start date
	 * 
	 * @return
	 */
	public CohortDefinition currentOnArtOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) <= date(:endDate)\n"
		        + "  and (c.active_art = 'Yes' or c.initiated_art_this_month = 'Yes')\n" + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), date(c.appointment_date))), 11) >= date(:startDate);";
		cd.setName("currentOnArtOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnArtOffsite");
		
		return cd;
	}
	
	/**
	 * Started ART 12 months ago elsewhere
	 * 
	 * @return
	 */
	public CohortDefinition startedArt12MonthsAgoOffsite() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit c\n"
		        + "where date(c.visit_date) between date_sub(date(:startDate), INTERVAL 12 MONTH) and date_sub(date(:endDate), INTERVAL 12 MONTH)\n"
		        + "  and c.hiv_care_facility = 'Provided elsewhere'\n" + "  and c.initiated_art_this_month = 'Yes';";
		cd.setName("startedArt12MonthsAgoOffsite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedArt12MonthsAgoOffsite");
		
		return cd;
	}
	
	/**
	 * Viral load result_12mths: Off-site Number of people in the 12-month cohort in each KP type
	 * who had a viral load test on site at 12 months and whose results were available at the time
	 * of analysis
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition viralLoad12MonthsOffsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("vlTestResultsWithinLast12MonthsOffsite",
		    ReportUtils.map(vlTestResultsWithinLast12MonthsOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND currentOnArtOffsite AND vlTestResultsWithinLast12MonthsOffsite");
		
		return cd;
	}
	
/**
	 * Viral load <1000_12mths: Off-site
	 * 	number of people in the 12-month cohort in each KP type whose 12-month viral load test
	 *  was done off site and who have a viral load less than 1000 copies per ML.
	 * @param kpType
	 * @return
	 */
	public CohortDefinition suppressedViralLoad12MonthsCohortOffsite(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("startedArt12MonthsAgoOffsite",
		    ReportUtils.map(startedArt12MonthsAgoOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("currentOnArtOffsite",
		    ReportUtils.map(currentOnArtOffsite(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("suppresedVlWithinLast12MonthsOffsite",
		    ReportUtils.map(suppressedViralLoad12MonthsCohortOnsite(kpType), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND startedArt12MonthsAgoOffsite AND currentOnArtOffsite AND suppresedVlWithinLast12MonthsOffsite");
		
		return cd;
	}
	
	/**
	 * Number experienced overdose in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition experiencedOverdoseReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id\n" + "from kenyaemr_etl.etl_overdose_reporting r\n"
		        + "where date(r.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("experiencedOverdoseReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedOverdoseReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of PWID/PWUD who experienced overdose in the reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencedOverdose(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencedOverdoseReportingPeriod",
		    ReportUtils.map(experiencedOverdoseReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencedOverdoseReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number had overdose and received naloxone
	 * 
	 * @return
	 */
	public CohortDefinition experiencedOverdoseRcvdNaloxoneReportingPeriod() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id\n" + "from kenyaemr_etl.etl_overdose_reporting r\n"
		        + "where date(r.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and r.naloxone_provided = 1065;";
		cd.setName("experiencedOverdoseRcvdNaloxoneReportingPeriod");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedOverdoseRcvdNaloxoneReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of PWID/PWUD who had overdose and received naloxone
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortDefinition experiencedOverdoseGivenNaloxone(String kpType) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("location", "Sub County", String.class));
		cd.addSearch("kpType",
		    ReportUtils.map(kpType(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
		cd.addSearch("experiencedOverdoseRcvdNaloxoneReportingPeriod",
		    ReportUtils.map(experiencedOverdoseRcvdNaloxoneReportingPeriod(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpType AND experiencedOverdoseRcvdNaloxoneReportingPeriod");
		
		return cd;
	}
	
	/**
	 * Number of deaths due to overdose in the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition overdoseDeaths() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select r.client_id from kenyaemr_etl.etl_overdose_reporting r where date(r.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "and r.outcome = 160034;";
		cd.setName("overdoseDeaths");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("overdoseDeaths");
		
		return cd;
	}
}
