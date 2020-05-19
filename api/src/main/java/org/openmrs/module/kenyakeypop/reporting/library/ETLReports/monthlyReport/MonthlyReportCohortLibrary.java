/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.library.ETLReports.monthlyReport;

import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by dev on 1/14/17.
 */

/**
 * Library of cohort definitions used specifically in the monthly reporting tool.
 */
@Component
//activeFsw
public class MonthlyReportCohortLibrary {
	
	public CohortDefinition contactAll(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id from kenyaemr_etl.etl_peer_calendar p inner join kenyaemr_etl.etl_contact c on p.client_id = c.client_id where date(c.visit_date) between date(:startDate) and date(:endDate) and c.key_population_type ='"
		        + kpType + "' group by c.client_id;";
		cd.setName("contactAll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("contactAll");
		
		return cd;
	}
	
	//everEnroll
	public CohortDefinition everEnroll(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "    where e.voided = 0 and c.key_population_type ='"
		        + kpType
		        + "' group by e.client_id having max(date(e.visit_date)) <= DATE(:endDate);";
		cd.setName("everEnroll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("everEnroll");
		
		return cd;
	}
	
	public CohortDefinition contactNew(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select p.client_id from kenyaemr_etl.etl_peer_calendar p inner join kenyaemr_etl.etl_contact c on c.client_id = p.client_id\n"
		        + "where c.key_population_type ='" + kpType + "' group by p.client_id having count(p.client_id)=1;";
		cd.setName("contactNew");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("contactNew");
		
		return cd;
	}
	
	public CohortDefinition netEnroll(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "left join (select d.patient_id, mid(max(concat(date(d.visit_date),d.discontinuation_reason)),11) as latest_disc_reason from kenyaemr_etl.etl_patient_program_discontinuation d group by d.patient_id)d  on d.patient_id = e.client_id\n"
		        + "where e.voided = 0 and c.key_population_type ='"
		        + kpType
		        + "' and (d.patient_id is null or d.latest_disc_reason!=160034) group by e.client_id having max(date(e.visit_date)) <= DATE(:endDate);\n";
		cd.setName("netEnroll");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("netEnroll");
		
		return cd;
	}
	
	public CohortDefinition kpPrev(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kpPrev");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpPrev");
		
		return cd;
	}
	
	public CohortDefinition kpCurr(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kpCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpCurr");
		
		return cd;
	}
	
	public CohortDefinition enrollNew(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "where c.key_population_type ='"
		        + kpType
		        + "' and c.voided = 0 group by e.client_id  having max(date(e.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("enrollNew");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollNew");
		
		return cd;
	}
	
	public CohortDefinition kpKnownPositiveEnrolled(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "where c.key_population_type ='"
		        + kpType
		        + "' and e.share_test_results='Yes I tested positive' and c.voided = 0 group by e.client_id  having max(date(e.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpKnownPositiveEnrolled");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpKnownPositiveEnrolled");
		
		return cd;
	}
	
	public CohortDefinition enrollHtsTst(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "inner join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id  where c.key_population_type ='"
		        + kpType
		        + "' and e.voided = 0\n"
		        + "group by e.client_id\n"
		        + "having(max(t.visit_date) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.patient_given_result)),11)='Yes'\n"
		        + "and  mid(max(concat(t.visit_date,t.voided)),11)= 0 and max(e.visit_date) between date(:startDate) and date(:endDate));";
		cd.setName("enrollHtsTst");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollHtsTst");
		
		return cd;
	}
	
	public CohortDefinition enrollHtsTstPos(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select e.client_id from kenyaemr_etl.etl_client_enrollment e inner join kenyaemr_etl.etl_contact c on e.client_id = c.client_id\n"
		        + "left join kenyaemr_etl.etl_hts_test t on e.client_id = t.patient_id  where e.voided = 0 and c.key_population_type ='"
		        + kpType
		        + "'\n"
		        + "group by e.client_id\n"
		        + "having((max(t.visit_date) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.patient_given_result)),11)='Yes'\n"
		        + "and mid(max(concat(t.visit_date,t.final_test_result)),11)='Positive') or\n"
		        + "(mid(max(concat(e.visit_date,e.share_test_results)),11)='Yes I tested positive' and mid(max(concat(e.visit_date,e.test_type)),11)='Self Test' )\n"
		        + "and  mid(max(concat(t.visit_date,t.voided)),11)= 0 and max(e.visit_date) between date(:startDate) and date(:endDate));";
		cd.setName("enrollHtsTstPos");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("enrollHtsTstPos");
		
		return cd;
	}
	
	public CohortDefinition assistedSelfTested(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type= '"
		        + kpType
		        + "' group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.self_test_education)),11)='Yes' and mid(max(concat(v.visit_date,v.self_use_kits)),11) >0 and mid(max(concat(v.visit_date,v.self_tested)),11)='Y'\n"
		        + "and max(v.visit_date) between date(:startDate) and date(:endDate);\n";
		cd.setName("assistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("assistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition unAssistedSelfTested(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type= '"
		        + kpType
		        + "' group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.self_test_education)),11)='No' and mid(max(concat(v.visit_date,v.distribution_kits)),11) >0\n"
		        + " and max(v.visit_date) between date(:startDate) and date(:endDate);\n";
		cd.setName("unAssistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("unAssistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition htsTstSelfConfirmedPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "where c.key_population_type= '"
		        + kpType
		        + "' group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.self_tested)),11)='Y' and  mid(max(concat(v.visit_date,v.test_confirmatory_results)),11) ='Positive'\n"
		        + " and max(v.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("htsTstSelfConfirmedPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTstSelfConfirmedPositive");
		
		return cd;
	}
	
	public CohortDefinition htsTestedNegative(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id\n"
		        + "where c.key_population_type ='"
		        + kpType
		        + "' and c.voided = 0  group by c.client_id\n"
		        + "having max(t.visit_date) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.final_test_result)),11)='Negative'\n"
		        + "and mid(max(concat(t.visit_date,t.voided)),11)= 0;";
		cd.setName("htsTestedNegative");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTestedNegative");
		
		return cd;
	}
	
	public CohortDefinition kpLHIVCurr(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kpLHIVCurr");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpLHIVCurr");
		
		return cd;
	}
	
	public CohortDefinition newOnARTKP(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c left join\n"
		        + "(select v.client_id,mid(max(concat(v.visit_date,v.initiated_art_this_month)),11) as started_art_this_month from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 group by v.client_id)v on c.client_id = v.client_id\n"
		        + "left join (select d.patient_id,min(d.date_started) as date_started_art from kenyaemr_etl.etl_drug_event d group by d.patient_id)d on c.client_id = d.patient_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0\n"
		        + "and  date(d.date_started_art) between date(:startDate) and date(:endDate) or v.started_art_this_month='Yes' group by c.client_id;";
		cd.setName("newOnARTKP");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("newOnARTKP");
		
		return cd;
	}
	
	public CohortDefinition currOnARTKP(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "left join (select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 group by v.client_id having mid(max(concat(v.visit_date,v.active_art)),11)='Yes')v on c.client_id = v.client_id\n"
		        + "left join(select fup.visit_date,fup.patient_id, min(e.visit_date) as enroll_date,\n"
		        + "      max(fup.visit_date) as latest_vis_date,\n"
		        + "      mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca,\n"
		        + "      max(d.visit_date) as date_discontinued,\n"
		        + "      d.patient_id as disc_patient,\n"
		        + "      de.patient_id as started_on_drugs\n"
		        + "from kenyaemr_etl.etl_patient_hiv_followup fup\n"
		        + "      join kenyaemr_etl.etl_patient_demographics p on p.patient_id=fup.patient_id\n"
		        + "      join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n"
		        + "      left outer join kenyaemr_etl.etl_drug_event de on e.patient_id = de.patient_id and de.program='HIV' and date(date_started) <= date(:endDate)\n"
		        + "      left outer JOIN\n"
		        + "        (select patient_id, visit_date from kenyaemr_etl.etl_patient_program_discontinuation\n"
		        + "         where date(visit_date) <= date(:endDate) and program_name='HIV'\n"
		        + "         group by patient_id\n"
		        + "        ) d on d.patient_id = fup.patient_id\n"
		        + "where fup.visit_date <= date(:endDate)\n"
		        + "group by patient_id\n"
		        + "having (started_on_drugs is not null and started_on_drugs <> \"\") and (\n"
		        + "   ( (disc_patient is null and date_add(date(latest_tca), interval 30 DAY)  >= date(:endDate)) or (date(latest_tca) > date(date_discontinued) and date(latest_vis_date)> date(date_discontinued) and date_add(date(latest_tca), interval 30 DAY)  >= date(:endDate) ))\n"
		        + "   )\n" + ") t\n" + " on c.client_id = t.patient_id where c.key_population_type = '" + kpType + "';";
		cd.setName("currOnARTKP");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currOnARTKP");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTI(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.sti_screened)),11)= 'Y' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("screenedForSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTI");
		
		return cd;
	}
	
	public CohortDefinition screenedPositiveForSTI(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.sti_screened)),11)= 'Y' and mid(max(concat(v.visit_date,v.sti_results)),11)= 'Positive' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("screenedPositiveForSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedPositiveForSTI");
		
		return cd;
	}
	
	public CohortDefinition startedSTITx(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.sti_screened)),11)= 'Y' and mid(max(concat(v.visit_date,v.sti_treated)),11)= 'Yes' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("startedSTITx");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedSTITx");
		
		return cd;
	}
	
	public CohortDefinition screenedForGbv(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.violence_screened)),11)= 'Yes' and max(date(v.visit_date)) between date(:startDate) and date(:endDate);\n";
		cd.setName("screenedForGbv");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForGbv");
		
		return cd;
	}
	
	//experiencedGbv
	public CohortDefinition experiencedGbv(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.violence_screened)),11)= 'Yes' and mid(max(concat(v.visit_date,v.violence_results)),11) in ('Harrasment','Illegal arrest','Verbal Abuse','Rape/Sexual assault','Discrimination','Assault/Physical abuse')\n"
		        + "and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("experiencedGbv");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("experiencedGbv");
		
		return cd;
	}
	
	//receivedGbvClinicalCare
	public CohortDefinition receivedGbvClinicalCare(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_gender_based_violence v on c.client_id = v.client_id\n"
		        + "where v.help_outcome in ('Counselling','PrEP given','Emergency pills','Hiv testing','STI Prophylaxis','Treatment','PEP given','Post rape care') and c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0 group by c.client_id\n"
		        + "having  max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("receivedGbvClinicalCare");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedGbvClinicalCare");
		
		return cd;
	}
	
	//receivedGbvLegalSupport
	public CohortDefinition receivedGbvLegalSupport(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "inner join kenyaemr_etl.etl_gender_based_violence v on c.client_id = v.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and v.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(v.visit_date,v.help_outcome)),11) in ('Investigation done','Matter presented to court','P3 form issued','Perpetrator arrested','Reconciliation','Statement taken')\n"
		        + "and max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("receivedGbvLegalSupport");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("receivedGbvLegalSupport");
		
		return cd;
	}
	
	//eligibleForRetest
	public CohortDefinition eligibleForRetest(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.patient_id,max(t.visit_date) as last_test_date,mid(max(concat(t.visit_date,t.final_test_result)),11) as last_test_result from kenyaemr_etl.etl_hts_test t where voided = 0 group by t.patient_id)t  on c.client_id = t.patient_id\n"
		        + "where datediff(date(:startDate),date(t.last_test_date))>90 and t.last_test_result='Negative' and c.key_population_type = '"
		        + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("eligibleForRetest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("eligibleForRetest");
		
		return cd;
	}
	
	//htsTstEligibleForRetest
	public CohortDefinition htsTstEligibleRetested(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select pt.patient_id from (select pt.patient_id,max(pt.visit_date) as prev_test_date,mid(max(concat(pt.visit_date,pt.final_test_result)),11) as prev_test_result from kenyaemr_etl.etl_hts_test pt\n"
		        + "group by pt.patient_id having datediff(date(:startDate),date(max(pt.visit_date)))>90 and mid(max(concat(pt.visit_date,pt.final_test_result)),11)='Negative')pt\n"
		        + "inner join(select ct.patient_id,max(ct.visit_date) as curr_test_date,mid(max(concat(ct.visit_date,ct.final_test_result)),11) as curr_test_result from kenyaemr_etl.etl_hts_test ct\n"
		        + "group by ct.patient_id having max(ct.visit_date) between date(:startDate) and date(:endDate))ct  on pt.patient_id = ct.patient_id) hts on c.client_id = hts.patient_id \n"
		        + "where c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("htsTstEligibleRetested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTstEligibleRetested");
		
		return cd;
	}
	
	//retestedHIVPositive
	public CohortDefinition retestedHIVPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select pt.patient_id from (select pt.patient_id,max(pt.visit_date) as prev_test_date,mid(max(concat(pt.visit_date,pt.final_test_result)),11) as prev_test_result from kenyaemr_etl.etl_hts_test pt\n"
		        + "  group by pt.patient_id having datediff(date(:startDate),date(max(pt.visit_date)))>90 and mid(max(concat(pt.visit_date,pt.final_test_result)),11)='Negative')pt\n"
		        + "inner join(select ct.patient_id,max(ct.visit_date) as curr_test_date,mid(max(concat(ct.visit_date,ct.final_test_result)),11) as curr_test_result from kenyaemr_etl.etl_hts_test ct\n"
		        + "group by ct.patient_id having max(ct.visit_date) between date(:startDate) and date(:endDate) and mid(max(concat(ct.visit_date,ct.final_test_result)),11)='Positive')ct  on pt.patient_id = ct.patient_id) hts on c.client_id = hts.patient_id\n"
		        + "where c.key_population_type = '" + kpType + "' group by c.client_id;";
		cd.setName("retestedHIVPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("retestedHIVPositive");
		
		return cd;
	}
	
	//referredAndInitiatedPrEPPepfar
	public CohortDefinition referredAndInitiatedPrEPPepfar(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("referredAndInitiatedPrEPPepfar");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("referredAndInitiatedPrEPPepfar");
		
		return cd;
	}
	
	//referredAndInitiatedPrEPNonPepfar
	public CohortDefinition referredAndInitiatedPrEPNonPepfar(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("referredAndInitiatedPrEPNonPepfar");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("referredAndInitiatedPrEPNonPepfar");
		
		return cd;
	}
	
	//kplhivInitiatedARTOtherPEPFAR
	public CohortDefinition kplhivInitiatedARTOtherPEPFAR(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kplhivInitiatedARTOtherPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivInitiatedARTOtherPEPFAR");
		
		return cd;
	}
	
	//kplhivInitiatedARTNonPEPFAR
	public CohortDefinition kplhivInitiatedARTNonPEPFAR(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kplhivInitiatedARTNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("testedNewMsw");
		
		return cd;
	}
	
	//kplhivCurrentOnARTOtherPEPFAR
	public CohortDefinition kplhivCurrentOnARTOtherPEPFAR(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kplhivCurrentOnARTOtherPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivCurrentOnARTOtherPEPFAR");
		
		return cd;
	}
	
	//kplhivCurrentOnARTNonPEPFAR
	public CohortDefinition kplhivCurrentOnARTNonPEPFAR(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kplhivCurrentOnARTNonPEPFAR");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivCurrentOnARTNonPEPFAR");
		
		return cd;
	}
	
	//kplhivLTFURecently
	public CohortDefinition kplhivLTFURecently(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kplhivLTFURecently");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivLTFURecently");
		
		return cd;
	}
	
	//kplhivTXRtt
	public CohortDefinition kplhivTXRtt(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select k.patient_id from (select e.patient_id,e.latest_tca,e.latest_vis_date,e.date_discontinued,f.visit_date as rtt_date,r.visit_date as ltca_after_return\n"
		        + "from (\n"
		        + "select fup.visit_date,fup.patient_id, min(e.visit_date) as enroll_date,\n"
		        + "max(fup.visit_date) as latest_vis_date,\n"
		        + "mid(max(concat(fup.visit_date,fup.next_appointment_date)),11) as latest_tca,\n"
		        + "date_sub(:startDate, INTERVAL 30 DAY),\n"
		        + "datediff(date_sub(:startDate, INTERVAL 30 DAY), date(mid(max(concat(fup.visit_date,fup.next_appointment_date)),11))) as 'start_date-30 - ltca' ,\n"
		        + "max(d.visit_date) as date_discontinued,\n"
		        + "d.patient_id as disc_patient\n"
		        + "from kenyaemr_etl.etl_patient_hiv_followup fup\n"
		        + "join kenyaemr_etl.etl_patient_demographics p on p.patient_id=fup.patient_id\n"
		        + "join kenyaemr_etl.etl_hiv_enrollment e on fup.patient_id=e.patient_id\n"
		        + "left outer JOIN\n"
		        + "(select patient_id, visit_date from kenyaemr_etl.etl_patient_program_discontinuation\n"
		        + " where date(visit_date) <= date_sub(:startDate, INTERVAL 30 DAY)  and program_name='HIV'\n"
		        + " group by patient_id -- check if this line is necessary\n"
		        + ") d on d.patient_id = fup.patient_id\n"
		        + "where fup.visit_date <= date_sub(:startDate, INTERVAL 30 DAY)\n"
		        + "group by patient_id\n"
		        + "having (\n"
		        + "(((date(latest_tca) < date_sub(:startDate, INTERVAL 30 DAY)) and (date(latest_vis_date) < date(latest_tca))) ) and ((date(latest_tca) > date(date_discontinued) and date(latest_vis_date) > date(date_discontinued)) or disc_patient is null)\n"
		        + ")\n"
		        + ") e inner join kenyaemr_etl.etl_patient_hiv_followup f on f.patient_id=e.patient_id and date(f.visit_date) between date(latest_tca) and date(:endDate)\n"
		        + " inner join kenyaemr_etl.etl_patient_hiv_followup r on r.patient_id=e.patient_id and date(r.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "group by e.patient_id)k where k.rtt_date between date(:startDate) and date(:endDate))rtt on c.client_id = rtt.patient_id\n"
		        + "where c.key_population_type = '" + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivTXRtt");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivTXRtt");
		
		return cd;
	}
	
	//kplhivSuppressedVl
	public CohortDefinition kplhivSuppressedVl(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "kenyaemr_etl.etl_drug_event e on c.client_id = e.patient_id\n"
		        + "inner join(select\n"
		        + "x.patient_id,\n"
		        + "max(x.visit_date) as latest_vl_date,\n"
		        + "mid(max(concat(x.visit_date,if(x.lab_test = 856, x.test_result, if(x.lab_test=1305 and x.test_result = 1302, 'LDL','')))),11) as latest_vl_result\n"
		        + "from kenyaemr_etl.etl_laboratory_extract x\n"
		        + "where x.lab_test in (1305, 856)\n"
		        + "group by x.patient_id\n"
		        + ") vl on c.client_id= vl.patient_id where vl.latest_vl_result <1000 or vl.latest_vl_result = 'LDL' and vl.latest_vl_date between date(:startDate) and date(:endDate)\n"
		        + "and c.key_population_type = '" + kpType + "' and c.voided = 0\n" + "group by c.client_id;";
		cd.setName("kplhivSuppressedVl");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVl");
		
		return cd;
	}
	
	//kplhivWithVlResult
	public CohortDefinition kplhivWithVlResult(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "kenyaemr_etl.etl_drug_event e on c.client_id = e.patient_id\n"
		        + "inner join(select\n"
		        + "x.patient_id,\n"
		        + "max(x.visit_date) as latest_vl_date,\n"
		        + "mid(max(concat(x.visit_date,if(x.lab_test = 856, x.test_result, if(x.lab_test=1305 and x.test_result = 1302, 'LDL','')))),11) as latest_vl_result\n"
		        + "from kenyaemr_etl.etl_laboratory_extract x\n"
		        + "where x.lab_test in (1305, 856)\n"
		        + "group by x.patient_id\n"
		        + ") vl on c.client_id= vl.patient_id where vl.latest_vl_result is not null or vl.latest_vl_result !='' and vl.latest_vl_date between date(:startDate) and date(:endDate)\n"
		        + "and c.key_population_type = '" + kpType + "' and c.voided = 0\n" + "group by c.client_id;";
		cd.setName("kplhivWithVlResult");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivWithVlResult");
		
		return cd;
	}
	
	//kplhivSuppressedVlArtOtherPEPFARSite
	public CohortDefinition kplhivSuppressedVlArtOtherPEPFARSite(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) <1000 or UPPER(mid(max(concat(t.visit_date,t.viral_load)),11))='LDL') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivSuppressedVlArtOtherPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVlArtOtherPEPFARSite");
		
		return cd;
	}
	
	//kplhivVlResultArtOtherPEPFARSite
	public CohortDefinition kplhivVlResultArtOtherPEPFARSite(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='Yes'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) is not null or (mid(max(concat(t.visit_date,t.viral_load)),11))!='') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivVlResultArtOtherPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivVlResultArtOtherPEPFARSite");
		
		return cd;
	}
	
	//kplhivSuppressedVlArtNonPEPFARSite
	public CohortDefinition kplhivSuppressedVlArtNonPEPFARSite(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) <1000 or UPPER(mid(max(concat(t.visit_date,t.viral_load)),11))='LDL') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivSuppressedVlArtNonPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVlArtNonPEPFARSite");
		
		return cd;
	}
	
	public CohortDefinition kplhivVlResultArtNonPEPFARSite(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select t.client_id from kenyaemr_etl.etl_treatment_verification t group by t.client_id having mid(max(concat(t.visit_date,t.is_pepfar_site)),11)='No'\n"
		        + "and mid(max(concat(t.visit_date,t.vl_test_date)),11) between date(:startDate) and date(:endDate) and mid(max(concat(t.visit_date,t.viral_load)),11) is not null or (mid(max(concat(t.visit_date,t.viral_load)),11))!='') v\n"
		        + "on c.client_id = v.client_id where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 group by c.client_id;";
		cd.setName("kplhivVlResultArtNonPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivVlResultArtNonPEPFARSite");
		
		return cd;
	}
	
	public CohortDefinition kpOnMultiMonthART(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("kpOnMultiMonthART");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpOnMultiMonthART");
		
		return cd;
	}
	
	//kpEnrolledInARTSupportGroup
	public CohortDefinition kpEnrolledInARTSupportGroup(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_treatment_verification v on c.client_id = v.client_id\n"
		        + "where v.in_support_group = 'Yes' and c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0 group by c.client_id\n"
		        + "having max(date(v.visit_date)) between date(:startDate) and date(:endDate);";
		cd.setName("kpEnrolledInARTSupportGroup");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kpEnrolledInARTSupportGroup");
		
		return cd;
	}
	
	//offeredPNS
	public CohortDefinition offeredPNS(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_related_to from kenyaemr_hiv_testing_patient_contact c where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617)\n"
		        + "group by c.patient_related_to having max(date(c.date_created)) between date(:startDate) and date(:endDate))pns on c.client_id = pns.patient_related_to where c.key_population_type = '"
		        + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("offeredPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("offeredPNS");
		
		return cd;
	}
	
	//acceptedPNS
	public CohortDefinition acceptedPNS(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_related_to from kenyaemr_hiv_testing_patient_contact c inner join kenyaemr_etl.etl_hts_test t\n"
		        + "on c.patient_related_to = t.patient_id where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617) and t.voided=0 and c.voided = 0 and t.test_type = 2 and t.visit_date between date(:startDate) and date(:endDate)\n"
		        + "group by c.patient_related_to having max(date(c.date_created)) between date(:startDate) and date(:endDate))pns on c.client_id = pns.patient_related_to where c.key_population_type = '"
		        + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("acceptedPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("acceptedPNS");
		
		return cd;
	}
	
	//elicitedPNS
	public CohortDefinition elicitedPNS(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_id from kenyaemr_hiv_testing_patient_contact c\n"
		        + "where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617)\n"
		        + "and c.voided = 0 and date(c.date_created) between date(:startDate) and date(:endDate)) pns on c.client_id = pns.patient_id\n"
		        + "where c.key_population_type = '" + kpType + "' and c.voided = 0 group by c.client_id;";
		cd.setName("elicitedPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("elicitedPNS ");
		
		return cd;
	}
	
	//pnsKnownPositiveAtEntry
	public CohortDefinition pnsKnownPositiveAtEntry(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_id from kenyaemr_hiv_testing_patient_contact c\n"
		        + " where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617)\n"
		        + "and c.voided = 0 and date(c.date_created) between date(:startDate) and date(:endDate)) pns on c.client_id = pns.patient_id\n"
		        + "inner join kenyaemr_etl.etl_client_enrollment e on pns.patient_id = c.client_id\n"
		        + "where c.key_population_type = '" + kpType
		        + "' and c.voided = 0 and e.share_test_results = 'Yes I tested positive' group by c.client_id;";
		cd.setName("pnsKnownPositiveAtEntry");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsKnownPositiveAtEntry");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_id from kenyaemr_hiv_testing_patient_contact c\n"
		        + "where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617)\n"
		        + "and c.voided = 0 and date(c.date_created) between date(:startDate) and date(:endDate)) pns on c.client_id = pns.patient_id\n"
		        + "inner join kenyaemr_etl.etl_hts_test t on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(t.visit_date,t.final_test_result)),11)='Positive' and max(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("pnsTestedPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedPositive");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedNegative(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join\n"
		        + "(select c.patient_id from kenyaemr_hiv_testing_patient_contact c\n"
		        + " where c.relationship_type in(971, 972, 1528, 162221, 163565, 970, 5617)\n"
		        + " and c.voided = 0 and date(c.date_created) between date(:startDate) and date(:endDate)) pns on c.client_id = pns.patient_id\n"
		        + " inner join kenyaemr_etl.etl_hts_test t on t.patient_id = c.client_id\n"
		        + "where c.key_population_type = '"
		        + kpType
		        + "' and c.voided = 0 group by c.client_id\n"
		        + "having mid(max(concat(t.visit_date,t.final_test_result)),11)='Negative' and max(t.visit_date) between date(:startDate) and date(:endDate);";
		cd.setName("pnsTestedNegative");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedNegative");
		
		return cd;
	}
	
}
