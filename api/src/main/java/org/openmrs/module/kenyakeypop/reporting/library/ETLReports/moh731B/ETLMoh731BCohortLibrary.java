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

import java.util.Date;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of cohort definitions used specifically in the MOH731 report based on ETL tables. It has
 * incorporated green card components
 */
@Component
//activeFsw
public class ETLMoh731BCohortLibrary {
	
	public CohortDefinition activeFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"  and en.voided = 0\n"
		        + "group by en.client_id\n"
		        + "    having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activeFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activeFsw");
		
		return cd;
	}
	
	//activeMsm
	public CohortDefinition activeMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"  and en.voided = 0\n"
		        + "group by en.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activeMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activeMsm");
		
		return cd;
	}
	
	public CohortDefinition activeMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"  and en.voided = 0\n"
		        + "group by en.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activeMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activeMsw");
		
		return cd;
	}
	
	public CohortDefinition activePwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"  and en.voided = 0\n"
		        + "group by en.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activePwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activePwid");
		
		return cd;
	}
	
	public CohortDefinition activePwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"  and en.voided = 0\n"
		        + "group by en.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activePwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activePwud");
		
		return cd;
	}
	
	public CohortDefinition activeTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"  and en.voided = 0\n"
		        + "group by en.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activeTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activeTransman");
		
		return cd;
	}
	
	public CohortDefinition activeTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select  en.client_id  from kenyaemr_etl.etl_client_enrollment en  inner join kenyaemr_etl.etl_clinical_visit v on en.client_id = v.client_id\n"
		        + "                                                            inner join kenyaemr_etl.etl_contact c on en.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"  and en.voided = 0\n"
		        + "group by en.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("activeTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("activeTranswoman");
		
		return cd;
	}
	
	public CohortDefinition hivTestedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "                where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Female sex worker\"\n"
		        + "    group by h.client_id;";
		cd.setName("hivTestedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedFsw");
		
		return cd;
	}
	
	public CohortDefinition hivTestedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Men who have sex with men\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedMsm");
		
		return cd;
	}
	
	public CohortDefinition hivTestedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Male sex worker\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedMsw");
		
		return cd;
	}
	
	public CohortDefinition hivTestedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"People who inject drugs\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedPwid");
		
		return cd;
	}
	
	public CohortDefinition hivTestedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"People who use drugs\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedPwud");
		
		return cd;
	}
	
	public CohortDefinition hivTestedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Transman\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedTransman");
		
		return cd;
	}
	
	public CohortDefinition hivTestedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Transwoman\"\n"
		        + "group by h.client_id;";
		cd.setName("hivTestedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedTranswoman");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                                     inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityFsw");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityMsm");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityMsw");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityPwid");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("diagnosedSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Diagnosed for STI");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityTransman");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.client_id;";
		cd.setName("testedAtFacilityTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityTranswoman");
		
		return cd;
	}
	
	//testedAtCommunityFsw
	public CohortDefinition testedAtCommunityFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                                     inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityFsw");
		
		return cd;
	}
	
	//testedAtCommunityMsm
	public CohortDefinition testedAtCommunityMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityMsm");
		
		return cd;
	}
	
	//testedAtCommunityMsw
	public CohortDefinition testedAtCommunityMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityMsw");
		
		return cd;
	}
	
	//testedAtCommunityPwid
	public CohortDefinition testedAtCommunityPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityPwid");
		
		return cd;
	}
	
	//testedAtCommunityPwud
	public CohortDefinition testedAtCommunityPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityPwud");
		
		return cd;
	}
	
	//testedAtCommunityTransman
	public CohortDefinition testedAtCommunityTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityTransman");
		
		return cd;
	}
	
	//testedAtCommunityTranswoman
	public CohortDefinition testedAtCommunityTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.client_id;";
		cd.setName("testedAtCommunityTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityTranswoman");
		
		return cd;
	}
	
	//testedNewFsw
	public CohortDefinition testedNewFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewFsw");
		
		return cd;
	}
	
	//testedNewMsm
	public CohortDefinition testedNewMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewMsm");
		
		return cd;
	}
	
	//testedNewMsw
	public CohortDefinition testedNewMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewMsw");
		
		return cd;
	}
	
	//testedNewPwid
	public CohortDefinition testedNewPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewPwid");
		
		return cd;
	}
	
	//testedNewPwud
	public CohortDefinition testedNewPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewPwud");
		
		return cd;
	}
	
	//testedAtNewTransman
	public CohortDefinition testedAtNewTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedAtNewTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtNewTransman");
		
		return cd;
	}
	
	//testedAtNewTranswoman
	public CohortDefinition testedAtNewTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "group by t.client_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedAtNewTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtNewTranswoman");
		
		return cd;
	}
	
	//testedRepeatFsw
	public CohortDefinition testedRepeatFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatFsw");
		
		return cd;
	}
	
	//testedRepeatMsm
	public CohortDefinition testedRepeatMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatMsm");
		
		return cd;
	}
	
	//testedRepeatMsw
	public CohortDefinition testedRepeatMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatMsw");
		
		return cd;
	}
	
	//testedRepeatPwid
	public CohortDefinition testedRepeatPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatPwid");
		
		return cd;
	}
	
	//testedRepeatPwud
	public CohortDefinition testedRepeatPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatPwud");
		
		return cd;
	}
	
	public CohortDefinition testedRepeatTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatTransman");
		
		return cd;
	}
	
	public CohortDefinition testedRepeatTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.client_id = e.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "group by t.client_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatTranswoman");
		
		return cd;
	}
	
	//knownPositiveFsw
	public CohortDefinition knownPositiveFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveFsw");
		
		return cd;
	}
	
	//knownPositiveMsm
	public CohortDefinition knownPositiveMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveMsm");
		
		return cd;
	}
	
	//knownPositiveMsw
	public CohortDefinition knownPositiveMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                        t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveMsw");
		
		return cd;
	}
	
	//knownPositivePwid
	public CohortDefinition knownPositivePwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                              t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositivePwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositivePwid ");
		
		return cd;
	}
	
	//knownPositivePwud
	public CohortDefinition knownPositivePwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                      t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositivePwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositivePwud");
		
		return cd;
	}
	
	public CohortDefinition knownPositiveTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"Transman\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                      t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveTransman");
		
		return cd;
	}
	
	public CohortDefinition knownPositiveTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.client_id\n"
		        + "where c.key_population_type = \"Transwoman\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                      t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveTranswoman");
		
		return cd;
	}
	
	//receivedPositiveResultsFsw
	public CohortDefinition receivedPositiveResultsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "                                              where c.key_population_type = \"Female sex worker\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsFsw");
		
		return cd;
	}
	
	//receivedPositiveResultsMsm
	public CohortDefinition receivedPositiveResultsMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male who have sex with Men\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsMsm");
		
		return cd;
	}
	
	//receivedPositiveResultsMsw
	public CohortDefinition receivedPositiveResultsMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsMsw");
		
		return cd;
	}
	
	//receivedPositiveResultsPwid
	public CohortDefinition receivedPositiveResultsPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsPwid");
		
		return cd;
	}
	
	//receivedPositiveResultsPwud
	public CohortDefinition receivedPositiveResultsPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsPwud");
		
		return cd;
	}
	
	//receivedPositiveResultsTransman
	public CohortDefinition receivedPositiveResultsTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsTransman");
		
		return cd;
	}
	
	//receivedPositiveResultsTranswoman
	public CohortDefinition receivedPositiveResultsTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.client_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.client_id;";
		cd.setName("receivedPositiveResultsTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsTranswoman");
		
		return cd;
	}
	
	//linkedFsw
	public CohortDefinition linkedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + " and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Female sex worker\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3 and\n"
		        + "       timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedFsw");
		
		return cd;
	}
	
	//linkedMsm
	public CohortDefinition linkedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Men who have sex with men\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedMsm");
		
		return cd;
	}
	
	//linkedMsw
	public CohortDefinition linkedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + " and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Male sex worker\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedMsw");
		
		return cd;
	}
	
	//linkedPwid
	public CohortDefinition linkedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"People who inject drugs\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedPwid");
		
		return cd;
	}
	
	//linkedPwud
	public CohortDefinition linkedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"People who use drugs\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedPwud");
		
		return cd;
	}
	
	//linkedTransman
	public CohortDefinition linkedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Transman\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedTransman");
		
		return cd;
	}
	
	//linkedTranswoman
	public CohortDefinition linkedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Transwoman\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Female sex worker\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Men who have sex with men\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsMsm ");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Male sex worker\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who inject drugs\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who use drugs\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transman\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transwoman\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedPerNeedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedPerNeedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedPerNeedFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Female sex worker\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Men who have sex with men\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Male sex worker\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"People who inject drugs\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"People who use drugs\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Transman\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Transwoman\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Female sex worker\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Men who have sex with men\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedPerNeedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedPerNeedMsm ");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Male sex worker\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedPerNeedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedPerNeedMsw ");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"People who inject drugs\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedPerNeedPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"People who use drugs\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Transman\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Transwoman\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Female sex worker\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Men who have sex with men\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Male sex worker\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who inject drugs\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who use drugs\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transman\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transwoman\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Female sex worker\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Men who have sex with men\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Male sex worker\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who inject drugs\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"People who use drugs\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transman\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transwoman\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedTranswoman");
		
		return cd;
	}
	
	/*screenedForSTIFsw*/
	public CohortDefinition screenedForSTIFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIFsw");
		
		return cd;
	}
	
	//screenedForSTIMsm
	public CohortDefinition screenedForSTIMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIMsm");
		
		return cd;
	}
	
	//screenedForSTIMsw
	public CohortDefinition screenedForSTIMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIMsw");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTIPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIPwid");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTIPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIPwud");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTITransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTITransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTITransman");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTITranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTITranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTITranswoman");
		
		return cd;
	}
	
	//screenedForHCVFsw
	public CohortDefinition screenedForHCVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVFsw");
		
		return cd;
	}
	
	//screenedForHCVMsm
	public CohortDefinition screenedForHCVMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVMsm");
		
		return cd;
	}
	
	//screenedForHCVMsw
	public CohortDefinition screenedForHCVMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVMsw");
		
		return cd;
	}
	
	//screenedForHCVPwid
	public CohortDefinition screenedForHCVPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVPwid");
		
		return cd;
	}
	
	//screenedForHCVPwud
	public CohortDefinition screenedForHCVPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVPwud ");
		
		return cd;
	}
	
	//screenedForHCVTransman
	public CohortDefinition screenedForHCVTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVTransman");
		
		return cd;
	}
	
	//screenedForHCVTranswoman
	public CohortDefinition screenedForHCVTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVTranswoman");
		
		return cd;
	}
	
	//screenedForHBVFsw
	public CohortDefinition screenedForHBVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVFsw");
		
		return cd;
	}
	
	//screenedForHBVMsm
	public CohortDefinition screenedForHBVMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVMsm");
		
		return cd;
	}
	
	//screenedForHBVMsw
	public CohortDefinition screenedForHBVMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVMsw");
		
		return cd;
	}
	
	//screenedForHBVPwid
	public CohortDefinition screenedForHBVPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVPwid");
		
		return cd;
	}
	
	//screenedForHBVPwud
	public CohortDefinition screenedForHBVPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVPwud ");
		
		return cd;
	}
	
	//screenedForHBVTransman
	public CohortDefinition screenedForHBVTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVTransman");
		
		return cd;
	}
	
	//screenedForHBVTranswoman
	public CohortDefinition screenedForHBVTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVTranswoman");
		
		return cd;
	}
	
	//positiveHBVFsw
	public CohortDefinition positiveHBVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("positiveHBVFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVFsw");
		
		return cd;
	}
	
	//positiveHBVMsm
	public CohortDefinition positiveHBVMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("positiveHBVMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVMsm");
		
		return cd;
	}
	
	//positiveHBVMsw
	public CohortDefinition positiveHBVMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("positiveHBVMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVMsw");
		
		return cd;
	}
	
	//positiveHBVPwid
	public CohortDefinition positiveHBVPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("completedPEPWithin28Days");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVPwid");
		
		return cd;
	}
	
	//positiveHBVPwud
	public CohortDefinition positiveHBVPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("positiveHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVPwud ");
		
		return cd;
	}
	
	//positiveHBVTransman
	public CohortDefinition positiveHBVTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("completedPEPWithin28Days");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Completed PEP within 28 days");
		
		return cd;
	}
	
	//positiveHBVTranswoman
	public CohortDefinition positiveHBVTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("completedPEPWithin28Days");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Completed PEP within 28 days");
		
		return cd;
	}
	
	//treatedHBVFsw
	public CohortDefinition treatedHBVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVFsw");
		
		return cd;
	}
	
	//treatedHBVMsm
	public CohortDefinition treatedHBVMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVMsm");
		
		return cd;
	}
	
	//treatedHBVMsw
	public CohortDefinition treatedHBVMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVMsw ");
		
		return cd;
	}
	
	//treatedHBVPwid
	public CohortDefinition treatedHBVPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVPwid");
		
		return cd;
	}
	
	//treatedHBVPwud
	public CohortDefinition treatedHBVPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVPwud");
		
		return cd;
	}
	
	public CohortDefinition treatedHBVTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVTransman");
		
		return cd;
	}
	
	public CohortDefinition treatedHBVTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVTranswoman");
		
		return cd;
	}
	
	//negativeHBVVaccinatedFsw
	public CohortDefinition negativeHBVVaccinatedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Female sex worker\" and hp.voided = 0\n"
		        + "  and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedFsw");
		
		return cd;
	}
	
	//negativeHBVVaccinatedMsm
	public CohortDefinition negativeHBVVaccinatedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Male who have sex with Men\" and hp.voided = 0\n"
		        + "  and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedMsm");
		
		return cd;
	}
	
	//negativeHBVVaccinatedMsw
	public CohortDefinition negativeHBVVaccinatedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Male sex worker\" and hp.voided = 0\n"
		        + "  and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedMsw ");
		
		return cd;
	}
	
	//negativeHBVVaccinatedPwid
	public CohortDefinition negativeHBVVaccinatedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"People who inject drugs\" and hp.voided = 0\n"
		        + "and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedPwid ");
		
		return cd;
	}
	
	//negativeHBVVaccinatedPwud
	public CohortDefinition negativeHBVVaccinatedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"People who use drugs\" and hp.voided = 0\n"
		        + "and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedPwud");
		
		return cd;
	}
	
	public CohortDefinition negativeHBVVaccinatedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "                \"where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Transman\" and hp.voided = 0\n"
		        + "                \"and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedTransman");
		
		return cd;
	}
	
	public CohortDefinition negativeHBVVaccinatedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "                \"where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Transwoman\" and hp.voided = 0\n"
		        + "                \"and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedTranswoman");
		
		return cd;
	}
	
	//screenedTBFsw
	public CohortDefinition screenedTBFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBFsw ");
		
		return cd;
	}
	
	//screenedTBMsm
	public CohortDefinition screenedTBMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBMsm");
		
		return cd;
	}
	
	//screenedTBMsw
	public CohortDefinition screenedTBMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBMsw");
		
		return cd;
	}
	
	//screenedTBPwid
	public CohortDefinition screenedTBPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBPwid ");
		
		return cd;
	}
	
	//screenedTBPwud
	public CohortDefinition screenedTBPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("completedPEPWithin28Days");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBPwud ");
		
		return cd;
	}
	
	public CohortDefinition screenedTBTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBTransman");
		
		return cd;
	}
	
	public CohortDefinition screenedTBTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBTransman");
		
		return cd;
	}
	
	//diagnosedTBFsw
	public CohortDefinition diagnosedTBFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBFsw");
		
		return cd;
	}
	
	//diagnosedTBMsm
	public CohortDefinition diagnosedTBMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBMsm ");
		
		return cd;
	}
	
	//diagnosedTBMsw
	public CohortDefinition diagnosedTBMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBMsw");
		
		return cd;
	}
	
	//diagnosedTBPwid
	public CohortDefinition diagnosedTBPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBPwid");
		
		return cd;
	}
	
	//diagnosedTBPwud
	public CohortDefinition diagnosedTBPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBPwud");
		
		return cd;
	}
	
	//diagnosedTBTransman
	public CohortDefinition diagnosedTBTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBTransman ");
		
		return cd;
	}
	
	//diagnosedTBTranswoman
	public CohortDefinition diagnosedTBTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBTranswoman ");
		
		return cd;
	}
	
	//startedOnTBTxFsw
	public CohortDefinition startedOnTBTxFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxFsw");
		
		return cd;
	}
	
	//startedOnTBTxMsm
	public CohortDefinition startedOnTBTxMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxMsm");
		
		return cd;
	}
	
	//startedOnTBTxMsw
	public CohortDefinition startedOnTBTxMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxMsw ");
		
		return cd;
	}
	
	//startedOnTBTxPwid
	public CohortDefinition startedOnTBTxPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxPwid");
		
		return cd;
	}
	
	//startedOnTBTxPwud
	public CohortDefinition startedOnTBTxPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxPwud");
		
		return cd;
	}
	
	//startedOnTBTxTransman
	public CohortDefinition startedOnTBTxTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transman\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxTransman");
		
		return cd;
	}
	
	//startedOnTBTxTranswoman
	public CohortDefinition startedOnTBTxTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxTranswoman");
		
		return cd;
	}
	
	//tbClientsOnHAARTFsw
	public CohortDefinition tbClientsOnHAARTFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Female sex worker\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTFsw ");
		
		return cd;
	}
	
	//tbClientsOnHAARTMsm
	public CohortDefinition tbClientsOnHAARTMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Men who have sex with men\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTMsm ");
		
		return cd;
	}
	
	//tbClientsOnHAARTMsw
	public CohortDefinition tbClientsOnHAARTMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Male sex worker\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTMsw ");
		
		return cd;
	}
	
	//tbClientsOnHAARTPwid
	public CohortDefinition tbClientsOnHAARTPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"People who inject drugs\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTPwid");
		
		return cd;
	}
	
	//tbClientsOnHAARTPwud
	public CohortDefinition tbClientsOnHAARTPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"People who use drugs\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTPwud ");
		
		return cd;
	}
	
	public CohortDefinition tbClientsOnHAARTTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Transman\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTTransman");
		
		return cd;
	}
	
	public CohortDefinition tbClientsOnHAARTTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Transwoman\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTTranswoman");
		
		return cd;
	}
	
	//initiatedPrEPFsw
	public CohortDefinition initiatedPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPFsw");
		return cd;
	}
	
	//initiatedPrEPMsm
	public CohortDefinition initiatedPrEPMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPMsm ");
		
		return cd;
	}
	
	//initiatedPrEPMsw
	public CohortDefinition initiatedPrEPMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPMsw");
		
		return cd;
	}
	
	//initiatedPrEPPwid
	public CohortDefinition initiatedPrEPPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPPwid ");
		
		return cd;
	}
	
	//initiatedPrEPPwud
	public CohortDefinition initiatedPrEPPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPPwud ");
		
		return cd;
	}
	
	public CohortDefinition initiatedPrEPTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPTransman");
		
		return cd;
	}
	
	public CohortDefinition initiatedPrEPTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPTranswoman");
		
		return cd;
	}
	
	//currentOnPrEPFsw
	public CohortDefinition currentOnPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPFsw");
		
		return cd;
	}
	
	//currentOnPrEPMsm
	public CohortDefinition currentOnPrEPMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPMsm");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPMsw");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPPwid");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPPwud");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPTransman");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transwoman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPTranswoman");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPFsw
	public CohortDefinition turningPositiveWhileOnPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Female sex worker\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPFsw");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPMsm
	public CohortDefinition turningPositiveWhileOnPrEPMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Men who have sex with men\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPMsm ");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPMsw
	public CohortDefinition turningPositiveWhileOnPrEPMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Male sex worker\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPMsw");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPPwid
	public CohortDefinition turningPositiveWhileOnPrEPPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who inject drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPPwid");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPPwud
	public CohortDefinition turningPositiveWhileOnPrEPPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"People who use drugs\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPPwud");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPTransman
	public CohortDefinition turningPositiveWhileOnPrEPTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPTransman");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPTranswoman
	public CohortDefinition turningPositiveWhileOnPrEPTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.client_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transman\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPTranswoman");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Female sex worker\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceFsw");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Men who have sex with men\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceMsm");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Male sex worker\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceMsw");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolencePwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"People who inject drugs\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolencePwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolencePwid");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolencePwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"People who use drugs\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolencePwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolencePwud");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Transman\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceTransman");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Transwoman\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n"
		        + "  and c.key_population_type =\"Men who have sex with men\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"Male sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n"
		        + "  and c.key_population_type =\"People who inject drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"People who use drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"Transman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"Transwoman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportTranswoman");
		
		return cd;
	}
	
	public CohortDefinition numberExposedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Female sex worker\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedFsw");
		
		return cd;
	}
	
	public CohortDefinition numberExposedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Men who have sex with men\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedMsm");
		
		return cd;
	}
	
	public CohortDefinition numberExposedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Male sex worker\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedMsw");
		
		return cd;
	}
	
	public CohortDefinition numberExposedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"People who inject drugs\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedPwid");
		
		return cd;
	}
	
	public CohortDefinition numberExposedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"People who use drugs\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedPwud");
		
		return cd;
	}
	
	public CohortDefinition numberExposedTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Transman\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedTransman");
		
		return cd;
	}
	
	public CohortDefinition numberExposedTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Transwoman\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedTranswoman");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Female sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsFsw");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Men who have sex with men\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsMsm");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Male sex worker\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsMsw");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"People who inject drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsPwid");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"People who use drugs\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsTransman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Transman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsTransman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsTransman");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsTranswoman() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Transwoman\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsTranswoman");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsTranswoman");
		
		return cd;
	}
	
	/*    public CohortDefinition completedPEPWith28DaysFsw(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysFsw");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysFsw");

	        return cd;
	    }
	    public CohortDefinition completedPEPWith28DaysMsm(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysMsm");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysMsm");

	        return cd;
	    }

	    public CohortDefinition completedPEPWith28DaysMsw(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysMsw");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysMsw");

	        return cd;
	    }

	    public CohortDefinition completedPEPWith28DaysPwid(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysPwid");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysPwid");

	        return cd;
	    }

	    public CohortDefinition completedPEPWith28DaysPwud(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysPwud");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysPwud");

	        return cd;
	    }

	    public CohortDefinition completedPEPWith28DaysTransman(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysTransman");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysTransman");

	        return cd;
	    }*/
	
}
