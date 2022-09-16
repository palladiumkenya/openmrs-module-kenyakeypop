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
		        + "where (d.patient_id is null or d.latest_visit > date(:endDate))\n" + "  and c.voided = 0\n"
		        + "group by c.client_id;";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
	/**
	 * FSW
	 * 
	 * @return
	 */
	public CohortDefinition fsw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'FSW';";
		cd.setName("fsw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("fsw");
		
		return cd;
	}
	
	/**
	 * MSM
	 * 
	 * @return
	 */
	public CohortDefinition msm() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSM';";
		cd.setName("msm");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("msm");
		
		return cd;
	}
	
	/**
	 * MSW
	 * 
	 * @return
	 */
	public CohortDefinition msw() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'MSW';";
		cd.setName("msw");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("msw");
		
		return cd;
	}
	
	/**
	 * PWID
	 * 
	 * @return
	 */
	public CohortDefinition pwid() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWID';";
		cd.setName("pwid");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("pwid");
		
		return cd;
	}
	
	/**
	 * PWUD
	 * 
	 * @return
	 */
	public CohortDefinition pwud() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'PWUD';";
		cd.setName("pwud");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("pwud");
		
		return cd;
	}
	
	/**
	 * Transgender
	 * 
	 * @return
	 */
	public CohortDefinition transgender() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n" + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'Transgender';";
		cd.setName("transgender");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("transgender");
		
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings
	 * 
	 * @return
	 */
	public CohortDefinition prisonersClosedSettings() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "where date(c.visit_date) <= date(:endDate) and c.implementation_subcounty = trim(:subCounty)\n"
		        + "group by c.client_id\n"
		        + "having mid(max(concat(date(c.visit_date), c.key_population_type)), 11) = 'People in prison and other closed settings';";
		cd.setName("prisonersClosedSettings");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.setDescription("Prisoners and people in closed settings");
		
		return cd;
	}
	
	/**
	 * FSW active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activeFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND fsw");
		return cd;
	}
	
	/**
	 * MSM active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activeMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND msm");
		return cd;
	}
	
	/**
	 * MSW active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activeMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND msw");
		return cd;
	}
	
	/**
	 * PWID active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activePwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND pwid");
		return cd;
	}
	
	/**
	 * PWUD active in KP (Received atleast one service in the past 3 months going up to effective
	 * date)
	 * 
	 * @return
	 */
	public CohortDefinition activePwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND pwud");
		return cd;
	}
	
	/**
	 * Transgender active in KP (Received atleast one service in the past 3 months going up to
	 * effective date)
	 * 
	 * @return
	 */
	public CohortDefinition activeTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND transgender");
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings active in KP (Received atleast one service in the
	 * past 3 months going up to effective date)
	 * 
	 * @return
	 */
	public CohortDefinition activePrisonersAndClossedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND prisonersClosedSettings");
		return cd;
	}
	
	/**
	 * KPs tested For HIV
	 * 
	 * @return
	 */
	public CohortDefinition htsAllNumberTestedKeyPopulation() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n" + "from kenyaemr_etl.etl_hts_test\n" + "where test_type = 1\n"
		        + "  and population_type = 'Key Population'\n"
		        + "  and visit_date between date(:startDate) and date(:endDate);";
		cd.setName("htsAllNumberTestedKeyPopulation");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("KPs tested For HIV");
		
		return cd;
	}
	
	/**
	 * FSW tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * MSM tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * MSW tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * PWID tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * PWUD tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * Transgender tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings tested for HIV during the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition hivTestedPrisonersAndClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsAllNumberTestedKeyPopulation",
		    ReportUtils.map(htsAllNumberTestedKeyPopulation(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND htsAllNumberTestedKeyPopulation");
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the facility/dice
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtFacility() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n" + "from kenyaemr_etl.etl_hts_test\n" + "where test_type = 1\n"
		        + "  and population_type = 'Key Population'\n" + "  and setting = 'Facility'\n"
		        + "  and visit_date between :startDate and :endDate;";
		cd.setName("htsNumberTestedAtFacility");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("KPs tested For HIV at the facility");
		
		return cd;
	}
	
	/**
	 * FSWs Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND htsNumberTestedAtFacility");
		return cd;
	}
	
	/**
	 * MSMs Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND htsNumberTestedAtFacility");
		return cd;
	}
	
	/**
	 * MSWs Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND htsNumberTestedAtFacility");
		
		return cd;
	}
	
	/**
	 * PWIDs Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND htsNumberTestedAtFacility");
		
		return cd;
	}
	
	/**
	 * PWUDs Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND htsNumberTestedAtFacility");
		
		return cd;
	}
	
	/**
	 * Transgender Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND htsNumberTestedAtFacility");
		
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings Tested at facility/Dice level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtFacilityPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtFacility",
		    ReportUtils.map(htsNumberTestedAtFacility(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND htsNumberTestedAtFacility");
		
		return cd;
	}
	
	/**
	 * KPs tested For HIV at the community level
	 * 
	 * @return
	 */
	public CohortDefinition htsNumberTestedAtCommunity() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n" + "from kenyaemr_etl.etl_hts_test\n" + "where test_type = 1\n"
		        + "  and population_type = 'Key Population'\n" + "  and setting = 'Community'\n"
		        + "  and date(visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("htsNumberTestedAtCommunity");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("KPs tested For HIV at the community level");
		
		return cd;
	}
	
	/**
	 * FSWs Tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * MSMs tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * MSWs tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * PWIDs tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * PWUD tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * Transgenders tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings tested at community level
	 * 
	 * @return
	 */
	public CohortDefinition testedAtCommunityPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("htsNumberTestedAtCommunity",
		    ReportUtils.map(htsNumberTestedAtCommunity(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND htsNumberTestedAtCommunity");
		
		return cd;
	}
	
	/**
	 * Newly Tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition newlyTestedForHIV() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id, first_test_date\n" + "from (\n" + "         SELECT patient_id,\n"
		        + "                min(visit_date) AS first_test_date\n" + "         FROM kenyaemr_etl.etl_hts_test\n"
		        + "         WHERE test_type = 1\n" + "         GROUP BY patient_id\n" + "     ) t\n"
		        + "group by patient_id\n" + "having first_test_date between :startDate and :endDate;";
		cd.setName("newlyTestedForHIV");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * FSWs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedNewFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * MSMs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedNewMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * MSWs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedNewMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * PWIDs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedNewPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * MSMs newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedNewPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * Transgenders newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition testedAtNewTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * Prisoners and people in closed settings newly tested for HIV
	 * 
	 * @return
	 */
	public CohortDefinition newlyTestedprisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("newlyTestedForHIV", ReportUtils.map(newlyTestedForHIV(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND newlyTestedForHIV");
		
		return cd;
	}
	
	/**
	 * Clients with repeat HIV tests within the period
	 * 
	 * @return
	 */
	public CohortDefinition repeatHIVTest() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n"
		        + "            from (\n"
		        + "                   SELECT\n"
		        + "                     patient_id,\n"
		        + "                     visit_date      AS test_date,\n"
		        + "                     max(visit_date) AS latest_test_date,\n"
		        + "                     min(visit_date) AS first_test_date,\n"
		        + "                          datediff(max(visit_date),min(visit_date))\n"
		        + "                   FROM kenyaemr_etl.etl_hts_test\n"
		        + "                   WHERE test_type = 1\n"
		        + "                   GROUP BY patient_id\n"
		        + "                 ) t\n"
		        + "            where date(test_date) between date(:startDate) and date(:endDate) and date(first_test_date) < date(latest_test_date)\n"
		        + "            GROUP BY patient_id;";
		cd.setName("repeatHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for FSW within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND repeatHIVTest");
		return cd;
	}
	
	/**
	 * Repeat HIV tests for MSM within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for MSWs within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for PWID within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for PWUD within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for Transgenders within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * Repeat HIV tests for Prisoners and people in closed settings within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition testedRepeatPrisonersAndClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("repeatHIVTest", ReportUtils.map(repeatHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND repeatHIVTest");
		
		return cd;
	}
	
	/**
	 * KP Client who had a self HIV test within the reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfHIVTest() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select patient_id\n" + "from kenyaemr_etl.etl_hts_test\n"
		        + "where patient_had_hiv_self_test = 'Yes'\n" + "  and population_type = 'Key Population'\n"
		        + "  and visit_date between :startDate and :endDate;";
		cd.setName("selfHIVTest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for FSW within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for MSM within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for MSWs within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for PWID within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for PWUD within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for Transgenders within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND selfHIVTest");
		
		return cd;
	}
	
	/**
	 * Self HIV tests for Prisoners and people in closed settings within reporting period
	 * 
	 * @return
	 */
	public CohortDefinition selfTestedForHIVPrisonersAndClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("selfHIVTest", ReportUtils.map(selfHIVTest(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND selfHIVTest");
		
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
	public CohortDefinition knownPositiveFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND fsw AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR MSMs Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs",
		    ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.setCompositionString("kpCurr AND msm AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR MSWs Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND msw AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR PWIDs Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositivePwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND pwid AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR PWUD Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositivePwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND pwud AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR Transgender Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositiveTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND transgender AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KP_CURR Prisoners and people in closed settings Living with HIV
	 * 
	 * @return
	 */
	public CohortDefinition knownPositivePrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("kpCurr", ReportUtils.map(kpCurr(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("knownPositiveKPs", ReportUtils.map(knownPositiveKPs(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("kpCurr AND prisonersClosedSettings AND knownPositiveKPs");
		
		return cd;
	}
	
	/**
	 * KPs who received HTS and received a positive result
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveHIVResults() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n" + "where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population'\n" + "  and t.patient_given_result = 'Yes'\n"
		        + "  and t.final_test_result = 'Positive'\n"
		        + "  and t.visit_date between date(:startDate) and date(:endDate);";
		cd.setName("receivedPositiveHIVResults");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * FSWs who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * MSMs who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * MSWs who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * PWID who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * PWUD who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * Transgender who tested Positive within the reporting period and were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND receivedPositiveHIVResults");
		
		return cd;
	}

	/**
	 * Prisoners and people in closed settings who tested Positive within the reporting period and
	 * were given results
	 * 
	 * @return
	 */
	public CohortDefinition receivedPositiveResultsPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivedPositiveHIVResults",
		    ReportUtils.map(receivedPositiveHIVResults(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND receivedPositiveHIVResults");
		
		return cd;
	}
	
	/**
	 * Tested for HIV 3 months ago, before effective reporting date
	 * 
	 * @return
	 */
	public CohortDefinition testedHIVPositive3MonthsAgo() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select t.patient_id\n" + "from kenyaemr_etl.etl_hts_test t\n" + "where t.test_type = 2\n"
		        + "  and t.population_type = 'Key Population'\n" + "  and t.final_test_result = 'Positive'\n"
		        + "  and t.visit_date between date_sub(date(:startDate), interval 3 MONTH)\n"
		        + "      and date_sub(date(:endDate), interval 3 MONTH);";
		cd.setName("testedHIVPositive3MonthsAgo");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
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
		String sqlQuery = "select r.patient_id\n" + "from kenyaemr_etl.etl_hts_referral_and_linkage r\n"
		        + "where (r.ccc_number != '' or r.ccc_number IS NOT NULL)\n"
		        + "  and (r.facility_linked_to != '' or r.facility_linked_to IS NOT NULL)\n"
		        + "and r.visit_date <= date(:endDate);";
		cd.setName("linkedHTS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
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
		String sqlQuery = "select v.client_id\n"
		        + "from kenyaemr_etl.etl_clinical_visit v\n"
		        + "where (v.hiv_care_facility is not null or v.other_hiv_care_facility is not null or v.self_test_link_facility is not null)\n"
		        + "and v.visit_date between date(:startDate) and date(:endDate);";
		cd.setName("linkedClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
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
		String sqlQuery = "select e.patient_id from kenyaemr_etl.etl_hiv_enrollment e where e.visit_date <= date(:endDate);";
		cd.setName("enrolledInHIVProgram");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrolledInHIVProgram");
		
		return cd;
	}
	
	/**
	 * Linked FSWs
	 * 
	 * @return
	 */
	public CohortDefinition linkedFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw", ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked MSMs
	 * 
	 * @return
	 */
	public CohortDefinition linkedMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm", ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked MSWs
	 * 
	 * @return
	 */
	public CohortDefinition linkedMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw", ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked PWIDs
	 * 
	 * @return
	 */
	public CohortDefinition linkedPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid", ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked PWUDs
	 * 
	 * @return
	 */
	public CohortDefinition linkedPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud", ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked Transgenders
	 * 
	 * @return
	 */
	public CohortDefinition linkedTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",
		    ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}
	
	/**
	 * Linked Prisoners and people in closed settings
	 * 
	 * @return
	 */
	public CohortDefinition linkedPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",
		    ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("testedHIVPositive3MonthsAgo",
		    ReportUtils.map(testedHIVPositive3MonthsAgo(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedHTS", ReportUtils.map(linkedHTS(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("linkedClinicalVisit",
		    ReportUtils.map(linkedClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("enrolledInHIVProgram",
		    ReportUtils.map(enrolledInHIVProgram(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND testedHIVPositive3MonthsAgo AND (linkedHTS OR linkedClinicalVisit OR enrolledInHIVProgram)");
		
		return cd;
	}

	/**
	 * Received condoms from clinical visit
	 * @return
	 */
	public CohortDefinition receivingCondomsFromClinicalVisit() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select v.client_id\n" +
				"from kenyaemr_etl.etl_clinical_visit v\n" +
				"where date(v.visit_date) between date(:startDate) and date(:endDate)\n" +
				"group by v.client_id\n" +
				"having (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0)) > 0;";
		cd.setName("receivingCondomsFromClinicalVisit");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsFromClinicalVisit");

		return cd;
	}

	/**
	 * Received condoms from sti treatment
	 * @return
	 */
	public CohortDefinition receivingCondomsFromSTITreatment() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select s.client_id\n" +
				"from kenyaemr_etl.etl_sti_treatment s\n" +
				"where s.visit_date between date(:startDate) and date(:endDate)\n" +
				"group by s.client_id\n" +
				"having coalesce(sum(s.no_of_condoms), 0) > 0;";
		cd.setName("receivingCondomsFromSTITreatment");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsFromSTITreatment");

		return cd;
	}

	/**
	 * Received condoms from peer outreach
	 * @return
	 */
	public CohortDefinition receivingCondomsFromPeerOutreach() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id\n" +
				"from kenyaemr_etl.etl_peer_calendar p\n" +
				"where date(p.visit_date) between date(:startDate) and date(:endDate)\n" +
				"group by p.client_id\n" +
				"  having (coalesce(sum(p.monthly_male_condoms_distributed), 0) +\n" +
				"       coalesce(sum(p.monthly_female_condoms_distributed), 0)) > 0;";
		cd.setName("receivingCondomsFromPeerOutreach");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsFromPeerOutreach");

		return cd;
	}

	/**
	 * FSWs received Condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw",ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}
	/**
	 * MSMs who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm",ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}
	/**
	 * MSWs who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw",ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}
	/**
	 * PWIDs who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid",ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}
	/**
	 * PWUDs who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud",ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}
	/**
	 * Transgenders who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}

	/**
	 * Prisoners and people in closed settings who received condoms within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsFromClinicalVisit",ReportUtils.map(receivingCondomsFromClinicalVisit(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromPeerOutreach", ReportUtils.map(receivingCondomsFromPeerOutreach(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("receivingCondomsFromSTITreatment",ReportUtils.map(receivingCondomsFromSTITreatment(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND (receivingCondomsFromClinicalVisit OR receivingCondomsFromPeerOutreach OR receivingCondomsFromSTITreatment)");

		return cd;
	}

	/**
	 * Received condoms per need within the reporting period
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeed() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select k.client_id\n" +
				"from (select c.client_id,\n" +
				"             (coalesce(sum(v.female_condoms_no), 0) + coalesce(sum(v.male_condoms_no), 0) +\n" +
				"              coalesce(sum(s.no_of_condoms), 0) + coalesce(p.monthly_male_condoms_distributed, 0) +\n" +
				"              coalesce(p.monthly_male_condoms_distributed, 0))\n" +
				"                                                                                                       as condoms_distributed,\n" +
				"             greatest(coalesce(p.monthly_condoms_required, 0),\n" +
				"                      coalesce(c.avg_weekly_sex_acts * 4, 0))                                          as monthly_condom_requirement\n" +
				"      from kenyaemr_etl.etl_contact c\n" +
				"               left outer join\n" +
				"           kenyaemr_etl.etl_peer_calendar p\n" +
				"           on c.client_id = p.client_id and date(p.visit_date) between date(:startDate) and date(:endDate)\n" +
				"               left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id and\n" +
				"                                                                    date(v.visit_date) between date(:startDate) and date(:endDate)\n" +
				"               left outer join kenyaemr_etl.etl_sti_treatment s on c.client_id = s.client_id and\n" +
				"                                                                   date(s.visit_date) between date(:startDate) and date(:endDate)\n" +
				"      group by c.client_id\n" +
				"      having condoms_distributed >= monthly_condom_requirement\n" +
				"         and condoms_distributed > 0) k;";
		cd.setName("receivingCondomsPerNeedPerNeed");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivingCondomsPerNeedPerNeed");

		return cd;
	}

	/**
	 * FSWs who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPerNeedFsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("fsw",ReportUtils.map(fsw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("fsw AND receivingCondomsPerNeedPerNeed");

		return cd;
	}

	/**
	 * MSMs who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedMsm() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msm",ReportUtils.map(msm(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msm AND receivingCondomsPerNeedPerNeed");

		return cd;
	}
	/**
	 * MSWs who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedMsw() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("msw",ReportUtils.map(msw(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("msw AND receivingCondomsPerNeedPerNeed");

		return cd;
	}
	/**
	 * PWID who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPwid() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwid",ReportUtils.map(pwid(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwid AND receivingCondomsPerNeedPerNeed");

		return cd;
	}
	/**
	 * PWUD who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPwud() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("pwud",ReportUtils.map(pwud(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("pwud AND receivingCondomsPerNeedPerNeed");

		return cd;
	}
	/**
	 * Transgenders who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedTransgender() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("transgender",ReportUtils.map(transgender(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("transgender AND receivingCondomsPerNeedPerNeed");

		return cd;
	}

	/**
	 * Prisoners and people in closed settings who received condoms per need
	 * @return
	 */
	public CohortDefinition receivingCondomsPerNeedPrisonersClosedSettings() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("subCounty", "Sub County", String.class));
		cd.addSearch("prisonersClosedSettings",ReportUtils.map(prisonersClosedSettings(), "startDate=${startDate},endDate=${endDate},subCounty=${subCounty}"));
		cd.addSearch("receivingCondomsPerNeedPerNeed",ReportUtils.map(receivingCondomsPerNeedPerNeed(), "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("prisonersClosedSettings AND receivingCondomsPerNeedPerNeed");

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
