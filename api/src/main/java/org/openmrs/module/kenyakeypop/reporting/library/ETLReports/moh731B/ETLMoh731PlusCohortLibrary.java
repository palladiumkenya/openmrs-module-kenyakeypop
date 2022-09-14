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

public class ETLMoh731PlusCohortLibrary {
	/**
	 * KPs who received atleast 1 service within the last 3 months from the effective date
	 * @return
	 */
	public CohortDefinition kpCurr() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"         join kenyaemr_etl.etl_client_enrollment e on c.client_id = e.client_id and e.voided = 0\n" +
				"         join (select p.client_id\n" +
				"               from kenyaemr_etl.etl_peer_calendar p\n" +
				"               where p.voided = 0\n" +
				"               group by p.client_id\n" +
				"               having max(p.visit_date) between DATE_SUB(date(date_sub(date(:endDate), interval 3 MONTH)), INTERVAL - 1 DAY) and date(:endDate)) p\n" +
				"              on c.client_id = p.client_id\n" +
				"         left join (select d.patient_id, max(d.visit_date) latest_visit\n" +
				"                    from kenyaemr_etl.etl_patient_program_discontinuation d\n" +
				"                    where d.program_name = 'KP') d on c.client_id = d.patient_id\n" +
				"where (d.patient_id is null or d.latest_visit > date(:endDate))\n" +
				"  and c.voided = 0\n" +
				"group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");

		return cd;
	}

	/**
	 * FSW
	 * @return
	 */
	public CohortDefinition fsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'FSW';";
		cd.setName("fsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("fsw");

		return cd;
	}

	/**
	 * MSM
	 * @return
	 */
	public CohortDefinition msm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSM';";
		cd.setName("msm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("msm");

		return cd;
	}

	/**
	 * MSW
	 * @return
	 */
	public CohortDefinition msw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSW';";
		cd.setName("msw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("msw");

		return cd;
	}

	/**
	 * PWID
	 * @return
	 */
	public CohortDefinition pwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWID';";
		cd.setName("pwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pwid");

		return cd;
	}

	/**
	 * PWUD
	 * @return
	 */
	public CohortDefinition pwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWUD';";
		cd.setName("pwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pwud");

		return cd;
	}

	/**
	 * Transgender
	 * @return
	 */
	public CohortDefinition transgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		cd.setName("transgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("transgender");

		return cd;
	}
	/**
	 * Prisoners and people in closed settings
	 * @return
	 */
	public CohortDefinition prisonsersClosedSettings() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" +
				"from kenyaemr_etl.etl_contact c\n" +
				"where date(c.visit_date) <= date(:endDate)\n" +
				"group by c.client_id\n" +
				"having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'People in prison and other closed settings';";
		cd.setName("prisonsersClosedSettings");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Prisoners and people in closed settings");

		return cd;
	}

	/**
	 * FSW active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activeFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("fsw",ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND fsw");
		return cd;
	}

	/**
	 * MSM active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activeMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("msm",ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND msm");
		return cd;
	}

	/**
	 * MSW active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activeMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("msw",ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND msw");
		return cd;
	}
	/**
	 * PWID active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activePwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwid",ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND pwid");
		return cd;
	}
	/**
	 * PWUD active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activePwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwud",ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND pwud");
		return cd;
	}
	/**
	 * Transgender active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activeTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transgender",ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND transgender");
		return cd;
	}
	/**
	 * Prisoners and people in closed settings active in KP (Received atleast one service in the past 3 months going up to effective date)
	 * @return
	 */
	public CohortDefinition activePrisonersAndClossedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("kpCurr",ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("prisonsersClosedSettings",ReportUtils.map(prisonsersClosedSettings(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND prisonsersClosedSettings");
		return cd;
	}
	
	public CohortDefinition hivTestedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "                where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"FSW\"\n"
		        + "    group by h.patient_id;";
		cd.setName("hivTestedFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedFsw");
		
		return cd;
	}
	
	public CohortDefinition hivTestedMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"MSM\"\n"
		        + "group by h.patient_id;";
		cd.setName("hivTestedMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedMsm");
		
		return cd;
	}
	
	public CohortDefinition hivTestedMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"MSW\"\n"
		        + "group by h.patient_id;";
		cd.setName("hivTestedMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedMsw");
		
		return cd;
	}
	
	public CohortDefinition hivTestedPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"PWID\"\n"
		        + "group by h.patient_id;";
		cd.setName("hivTestedPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedPwid");
		
		return cd;
	}
	
	public CohortDefinition hivTestedPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"PWUD\"\n"
		        + "group by h.patient_id;";
		cd.setName("hivTestedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedPwud");
		
		return cd;
	}
	
	public CohortDefinition hivTestedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where date(h.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type = \"Transgender\"\n"
		        + "group by h.patient_id;";
		cd.setName("hivTestedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("hivTestedTransgender");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                                     inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("testedAtFacilityFsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityFsw");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityMsm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSM\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("testedAtFacilityMsm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityMsm");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityMsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSW\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("testedAtFacilityMsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityMsw");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityPwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWID\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("testedAtFacilityPwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityPwid");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityPwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("diagnosedSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("Diagnosed for STI");
		
		return cd;
	}
	
	public CohortDefinition testedAtFacilityTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"In Patient Department(IPD)\",\n"
		        + "                            \"Out Patient Department(OPD)Peadiatric Clinic\",\n"
		        + "                            \"Nutrition Clinic\",\n"
		        + "                            \"PMTCT\",\n"
		        + "                            \"TB\",\n"
		        + "                            \"CCC\",\n"
		        + "                            \"VCT\")\n" + "group by h.patient_id;";
		cd.setName("testedAtFacilityTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtFacilityTransgender");
		
		return cd;
	}
	
	//testedAtCommunityFsw
	public CohortDefinition testedAtCommunityFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                                     inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSM\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSW\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWID\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
		cd.setName("testedAtCommunityPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityPwud");
		
		return cd;
	}
	
	//testedAtCommunityTransgender
	public CohortDefinition testedAtCommunityTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "  and date(h.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "  and h.hts_entry_point in (\"Home Based Testing\",\n"
		        + "                            \"Mobile Outreach\",\n"
		        + "                            \"Other\")\n"
		        + "group by h.patient_id;";
		cd.setName("testedAtCommunityTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtCommunityTransgender");
		
		return cd;
	}
	
	//testedNewFsw
	public CohortDefinition testedNewFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n"
		        + "group by t.patient_id\n"
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSM\"\n"
		        + "group by t.patient_id\n"
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSW\"\n"
		        + "group by t.patient_id\n"
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWID\"\n"
		        + "group by t.patient_id\n"
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
		        + "group by t.patient_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedNewPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewPwud");
		
		return cd;
	}
	
	//testedAtNewTransgender
	public CohortDefinition testedAtNewTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "group by t.patient_id\n"
		        + "having min(date(t.visit_date)) between  date(:startDate) and date(:endDate);";
		cd.setName("testedAtNewTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedAtNewTransgender");
		
		return cd;
	}
	
	//testedRepeatFsw
	public CohortDefinition testedRepeatFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n" + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"MSM\"\n" + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"MSW\"\n" + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"PWID\"\n"
		        + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
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
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
		        + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatPwud");
		
		return cd;
	}
	
	public CohortDefinition testedRepeatTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id from kenyaemr_etl.etl_hts_test t inner join kenyaemr_etl.etl_client_enrollment e on t.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on t.patient_id = e.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "group by t.patient_id having count(distinct(t.visit_id)) >1;";
		cd.setName("testedRepeatTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedRepeatTransgender");
		
		return cd;
	}
	
	//knownPositiveFsw
	public CohortDefinition knownPositiveFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"FSW\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
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
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"MSM\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
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
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"MSW\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
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
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"PWID\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
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
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"PWUD\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                      t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositivePwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositivePwud");
		
		return cd;
	}
	
	public CohortDefinition knownPositiveTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_clinical_visit v on e.client_id = v.client_id\n"
		        + "                          left outer join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id\n"
		        + "where c.key_population_type = \"Transgender\" and  coalesce(v.test_results = \"Positive\" ,v.hiv_self_rep_status=\"Positive\",e.share_test_results =\"Yes I tested positive\",\n"
		        + "                                                                      t.final_test_result =\"Positive\")\n"
		        + "group by e.client_id\n" + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("knownPositiveTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("knownPositiveTransgender");
		
		return cd;
	}
	
	//receivedPositiveResultsFsw
	public CohortDefinition receivedPositiveResultsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "                                              where c.key_population_type = \"FSW\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"Male who have sex with Men\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"MSW\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWID\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
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
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
		cd.setName("receivedPositiveResultsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsPwud");
		
		return cd;
	}
	
	//receivedPositiveResultsTransgender
	public CohortDefinition receivedPositiveResultsTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select h.patient_id from kenyaemr_etl.etl_hts_test h inner join kenyaemr_etl.etl_client_enrollment e on h.patient_id = e.client_id\n"
		        + "                                              inner join kenyaemr_etl.etl_contact c on h.patient_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\" and h.final_test_result =\"Positive\" and h.patient_given_result = \"Yes\"\n"
		        + "  and  date(h.visit_date) between date(:startDate) and date(:endDate)\n" + "group by h.patient_id;";
		cd.setName("receivedPositiveResultsTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveResultsTransgender");
		
		return cd;
	}
	
	//linkedFsw
	public CohortDefinition linkedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + " and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"FSW\"\n" + "group by v.client_id\n"
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
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"MSM\"\n" + "group by v.client_id\n"
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
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + " and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"MSW\"\n" + "group by v.client_id\n"
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
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"PWID\"\n" + "group by v.client_id\n"
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
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"PWUD\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedPwud");
		
		return cd;
	}
	
	//linkedTransgender
	public CohortDefinition linkedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "                          inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                         and (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null)\n"
		        + "where c.key_population_type =\"Transgender\"\n" + "group by v.client_id\n"
		        + "having timestampdiff(MONTH,Max(date(v.visit_date)),date(:endDate))<=3;";
		cd.setName("linkedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("linkedTransgender");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"FSW\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"MSM\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"MSW\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"PWID\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"PWUD\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + "                             as condoms_distributed from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                              kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                              left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transgender\" group by c.client_id\n"
		        + "                         having condoms_distributed >=1 )k;";
		cd.setName("receivingCondomsTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsTransgender");
		
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
		        + "where c.key_population_type = \"FSW\"\n"
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
		        + "where c.key_population_type = \"MSM\"\n"
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
		        + "where c.key_population_type = \"MSW\"\n"
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
		        + "where c.key_population_type = \"PWID\"\n"
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
		        + "where c.key_population_type = \"PWUD\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingCondomsPerNeedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.female_condoms_no),0)+ coalesce(sum(v.male_condoms_no),0)+coalesce(sum(s.no_of_condoms),0)+coalesce(p.monthly_male_condoms_distributed,0)+coalesce(p.monthly_male_condoms_distributed,0))\n"
		        + " as condoms_distributed,\n"
		        + "       coalesce(coalesce(p.monthly_condoms_required,0),coalesce(c.avg_weekly_sex_acts*4,0)) as monthly_condom_requirement\n"
		        + "           from kenyaemr_etl.etl_contact c left outer join\n"
		        + "           kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "           left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and  date(s.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + " group by c.client_id\n"
		        + "having condoms_distributed>=monthly_condom_requirement and condoms_distributed !=0) k;";
		cd.setName("receivingCondomsPerNeedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedTransgender");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"FSW\" group by c.client_id\n"
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
		        + "                                             where c.key_population_type = \"MSM\" group by c.client_id\n"
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
		        + "                                             where c.key_population_type = \"MSW\" group by c.client_id\n"
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
		        + "                                             where c.key_population_type = \"PWID\" group by c.client_id\n"
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
		        + "                                             where c.key_population_type = \"PWUD\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0) + p.monthly_n_and_s_distributed) as needles_and_syringes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                             where c.key_population_type = \"Transgender\" group by c.client_id\n"
		        + "    having needles_and_syringes_given >=1 )k;";
		cd.setName("receivingNeedlesAndSyringesTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesTransgender");
		
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
		        + "                                                   where c.key_population_type = \"FSW\"\n"
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
		        + "                                                   where c.key_population_type = \"MSM\"\n"
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
		        + "                                                   where c.key_population_type = \"MSW\"\n"
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
		        + "                                                   where c.key_population_type = \"PWID\"\n"
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
		        + "                                                   where c.key_population_type = \"PWUD\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingNeedlesAndSyringesPerNeedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.syringes_needles_no),0)+coalesce(p.monthly_n_and_s_distributed,0))\n"
		        + "                                                                                                                     as needles_syringes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_syringes_required,0),coalesce(c.avg_daily_drug_injections*28,0)) as monthly_needles_syringes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   where c.key_population_type = \"Transgender\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having needles_syringes_distributed>=monthly_needles_syringes_requirements and needles_syringes_distributed !=0 ) k;";
		cd.setName("receivingNeedlesAndSyringesPerNeedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingNeedlesAndSyringesPerNeedTransgender");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"FSW\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"MSM\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"MSW\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"PWID\" group by c.client_id\n"
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
		        + "                         where c.key_population_type = \"PWUD\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0) + p.monthly_lubes_distributed + t.no_of_lubes) as lubes_given from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                                                                                                                                            kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                                                                                                                                             left outer join kenyaemr_etl.etl_sti_treatment t on c.client_id = t.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transgender\" group by c.client_id\n"
		        + "                         having lubes_given >=1 )k;";
		cd.setName("receivingLubricantsTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsTransgender");
		
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
		        + "                         where c.key_population_type = \"FSW\"\n"
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
		        + "                         where c.key_population_type = \"MSM\"\n"
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
		        + "                         where c.key_population_type = \"MSW\"\n"
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
		        + "                         where c.key_population_type = \"PWID\"\n"
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
		        + "                         where c.key_population_type = \"PWUD\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingLubricantsPerNeedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id from (select c.client_id,(coalesce(sum(v.lubes_no),0)+coalesce(p.monthly_lubes_distributed,0))\n"
		        + "                                                                                                                             as lubes_distributed,\n"
		        + "                                coalesce(coalesce(p.monthly_lubes_required,0),coalesce(c.avg_weekly_anal_sex_acts*4,0)) as monthly_lubes_requirements\n"
		        + "                         from kenyaemr_etl.etl_contact c left outer join\n"
		        + "                                  kenyaemr_etl.etl_peer_calendar p on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                                                   left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "                         where c.key_population_type = \"Transgender\"\n"
		        + "                         group by c.client_id\n"
		        + "                         having lubes_distributed >=monthly_lubes_requirements and lubes_distributed !=0 ) k;";
		cd.setName("receivingLubricantsPerNeedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingLubricantsPerNeedTransgender");
		
		return cd;
	}
	
	/*screenedForSTIFsw*/
	public CohortDefinition screenedForSTIFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.sti_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.sti_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.sti_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.sti_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTIPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTIPwud");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTITransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.sti_screened = \"Y\" and v.visit_date\n"
		        + "between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForSTITransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTITransgender");
		
		return cd;
	}
	
	//screenedForHCVFsw
	public CohortDefinition screenedForHCVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVPwud ");
		
		return cd;
	}
	
	//screenedForHCVTransgender
	public CohortDefinition screenedForHCVTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.hepatitisC_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHCVTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHCVTransgender");
		
		return cd;
	}
	
	//screenedForHBVFsw
	public CohortDefinition screenedForHBVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVPwud ");
		
		return cd;
	}
	
	//screenedForHBVTransgender
	public CohortDefinition screenedForHBVTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.hepatitisB_screened = \"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedForHBVTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForHBVTransgender");
		
		return cd;
	}
	
	//positiveHBVFsw
	public CohortDefinition positiveHBVFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("positiveHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("positiveHBVPwud ");
		
		return cd;
	}
	
	//positiveHBVTransgender
	public CohortDefinition positiveHBVTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.hepatitisB_results=\"P\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"FSW\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVPwud");
		
		return cd;
	}
	
	public CohortDefinition treatedHBVTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.hepatitisB_treated=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("treatedHBVTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("treatedHBVTransgender");
		
		return cd;
	}
	
	//negativeHBVVaccinatedFsw
	public CohortDefinition negativeHBVVaccinatedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"FSW\" and hp.voided = 0\n"
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
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"MSW\" and hp.voided = 0\n"
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
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"PWID\" and hp.voided = 0\n"
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
		        + "where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"PWUD\" and hp.voided = 0\n"
		        + "and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedPwud");
		
		return cd;
	}
	
	public CohortDefinition negativeHBVVaccinatedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select hp.client_id from kenyaemr_etl.etl_hepatitis_screening hp inner join kenyaemr_etl.etl_contact c on ss.client_id = hp.client_id\n"
		        + "                \"where hp.hepatitis_screening_done = \"Hepatitis B\" and results =\"Vaccinated\" and treated =\"Yes\" and c.key_population_type = \"Transgender\" and hp.voided = 0\n"
		        + "                \"and date(hp.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("negativeHBVVaccinatedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("negativeHBVVaccinatedTransgender");
		
		return cd;
	}
	
	//screenedTBFsw
	public CohortDefinition screenedTBFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.tb_screened=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.tb_screened=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.tb_screened=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.tb_screened=\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("completedPEPWithin28Days");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBPwud ");
		
		return cd;
	}
	
	public CohortDefinition screenedTBTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.tb_screened=\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("screenedTBTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedTBTransgender");
		
		return cd;
	}
	
	//diagnosedTBFsw
	public CohortDefinition diagnosedTBFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.tb_results =\"Positive\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.tb_results =\"Positive\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.tb_results =\"Positive\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.tb_results =\"Positive\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBPwud");
		
		return cd;
	}
	
	//diagnosedTBTransgender
	public CohortDefinition diagnosedTBTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.tb_results =\"Positive\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("diagnosedTBTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("diagnosedTBTransgender ");
		
		return cd;
	}
	
	//startedOnTBTxFsw
	public CohortDefinition startedOnTBTxFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"FSW\" and v.tb_treated =\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSM\" and v.tb_treated =\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"MSW\" and v.tb_treated =\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWID\" and v.tb_treated =\"Y\" and v.visit_date\n"
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
		        + "where c.key_population_type=\"PWUD\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxPwud");
		
		return cd;
	}
	
	//startedOnTBTxTransgender
	public CohortDefinition startedOnTBTxTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c\n"
		        + "                            on c.client_id = v.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and v.tb_treated =\"Y\" and v.visit_date\n"
		        + "    between date(:startDate) and date(:endDate) and v.voided = 0 group by v.client_id;";
		cd.setName("startedOnTBTxTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedOnTBTxTransgender");
		
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
		        + "                                                               and c.key_population_type = \"FSW\"\n"
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
		        + "                                                               and c.key_population_type = \"MSM\"\n"
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
		        + "                                                               and c.key_population_type = \"MSW\"\n"
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
		        + "                                                               and c.key_population_type = \"PWID\"\n"
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
		        + "                                                               and c.key_population_type = \"PWUD\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTPwud ");
		
		return cd;
	}
	
	public CohortDefinition tbClientsOnHAARTTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where (v.tb_results = \"Positive\" and v.active_art =\"Yes\") or (v.tb_results = \"Positive\" and v.initiated_art_this_month = \"Yes\")\n"
		        + "                                                               and c.key_population_type = \"Transgender\"\n"
		        + "                                                               and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("tbClientsOnHAARTTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("tbClientsOnHAARTTransgender");
		
		return cd;
	}
	
	//initiatedPrEPFsw
	public CohortDefinition initiatedPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
	
	public CohortDefinition initiatedPrEPTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("initiatedPrEPTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("initiatedPrEPTransgender");
		
		return cd;
	}
	
	//currentOnPrEPFsw
	public CohortDefinition currentOnPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n"
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
		        + "where c.key_population_type = \"MSM\"\n"
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
		        + "where c.key_population_type = \"MSW\"\n"
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
		        + "where c.key_population_type = \"PWID\"\n"
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
		        + "where c.key_population_type = \"PWUD\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPPwud");
		
		return cd;
	}
	
	public CohortDefinition currentOnPrEPTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "           inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id\n"
		        + "having max(v.prep_treated =\"Y\");";
		cd.setName("currentOnPrEPTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currentOnPrEPTransgender");
		
		return cd;
	}
	
	//turningPositiveWhileOnPrEPFsw
	public CohortDefinition turningPositiveWhileOnPrEPFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"FSW\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"MSM\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"MSW\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWID\"\n"
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
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"PWUD\"\n"
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
	
	//turningPositiveWhileOnPrEPTransgender
	public CohortDefinition turningPositiveWhileOnPrEPTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v inner join kenyaemr_etl.etl_hts_test t on v.client_id = t.patient_id\n"
		        + "                                                    inner join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type = \"Transgender\"\n"
		        + "  and v.prep_treated =\"Y\"\n"
		        + "  and date(t.visit_date) >date(v.visit_date) and t.final_test_result=\"Positive\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "group by v.client_id;";
		cd.setName("turningPositiveWhileOnPrEPTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("turningPositiveWhileOnPrEPTransgender");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"FSW\"\n"
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
		        + "  and c.key_population_type =\"MSM\"\n"
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
		        + "  and c.key_population_type =\"MSW\"\n"
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
		        + "  and c.key_population_type =\"PWID\"\n"
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
		        + "  and c.key_population_type =\"PWUD\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolencePwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolencePwud");
		
		return cd;
	}
	
	public CohortDefinition experiencingViolenceTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_results in (\"Verbal abuse\",\"Physical abuse\",\"Discrimination\",\"Sexual abuse/Rape\",\"illegal arrest\",\"other\")\n"
		        + "  and c.key_population_type =\"Transgender\"\n"
		        + "    and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("experiencingViolenceTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencingViolenceTransgender");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"FSW\"\n"
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
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"MSM\"\n"
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
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"MSW\"\n"
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
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"PWID\"\n"
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
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"PWUD\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingViolenceSupportTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.violence_treated = \"Supported\"\n" + "  and c.key_population_type =\"Transgender\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("receivingViolenceSupportTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingViolenceSupportTransgender");
		
		return cd;
	}
	
	public CohortDefinition numberExposedFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"FSW\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
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
		        + "where c.key_population_type=\"MSM\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
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
		        + "where c.key_population_type=\"MSW\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
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
		        + "where c.key_population_type=\"PWID\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
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
		        + "where c.key_population_type=\"PWUD\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedPwud");
		
		return cd;
	}
	
	public CohortDefinition numberExposedTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where c.key_population_type=\"Transgender\" and (v.exposure_type in (\"Rape\",\"Condom burst\",\"Others\") or v.other_exposure_type is not null)  and c.key_population_type =\"FSW\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;";
		cd.setName("numberExposedTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("numberExposedTransgender");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsFsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"FSW\"\n"
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
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"MSM\"\n"
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
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"MSW\"\n"
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
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"PWID\"\n"
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
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"PWUD\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsPwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsPwud");
		
		return cd;
	}
	
	public CohortDefinition receivingPEPWithin72HrsTransgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id from kenyaemr_etl.etl_clinical_visit v\n"
		        + "                          join kenyaemr_etl.etl_contact c on v.client_id = c.client_id\n"
		        + "where v.pep_eligible= \"Y\"\n" + "  and c.key_population_type =\"Transgender\"\n"
		        + "  and date(v.visit_date) between date(:startDate) and date(:endDate) group by v.client_id;\n";
		cd.setName("receivingPEPWithin72HrsTransgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingPEPWithin72HrsTransgender");
		
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

	    public CohortDefinition completedPEPWith28DaysTransgender(){
	        SqlCohortDefinition cd = new SqlCohortDefinition();
	        String sqlQuery ="";
	        cd.setName("completedPEPWith28DaysTransgender");
	        cd.setQuery(sqlQuery);
	        cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
	        cd.addParameter(new Parameter("endDate", "End Date", Date.class));
	        cd.setDescription("completedPEPWith28DaysTransgender");

	        return cd;
	    }*/
	
}
