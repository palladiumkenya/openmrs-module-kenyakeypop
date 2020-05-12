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
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
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
		        + "\t\t    where e.voided = 0 and c.key_population_type ='"
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
		String sqlQuery = "";
		cd.setName("assistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("assistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition unAssistedSelfTested(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("unAssistedSelfTested");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("unAssistedSelfTested");
		
		return cd;
	}
	
	public CohortDefinition htsTstSelfConfirmedPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
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
		String sqlQuery = "";
		cd.setName("newOnARTKP");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("newOnARTKP");
		
		return cd;
	}
	
	public CohortDefinition currOnARTKP(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("currOnARTKP");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("currOnARTKP");
		
		return cd;
	}
	
	public CohortDefinition screenedForSTI(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "select c.client_id from kenyaemr_etl.etl_contact c inner join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id where v.sti_screened = 'Y'\n"
		        + "and c.key_population_type ='"
		        + kpType
		        + "' and v.voided = 0 and date(v.visit_date) between date(:startDate) and date(:endDate)\n"
		        + "group by c.client_id;";
		cd.setName("screenedForSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedForSTI");
		
		return cd;
	}
	
	public CohortDefinition screenedPositiveForSTI(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("screenedPositiveForSTI");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("screenedPositiveForSTI");
		
		return cd;
	}
	
	public CohortDefinition startedSTITx(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("startedSTITx");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("startedSTITx");
		
		return cd;
	}
	
	public CohortDefinition screenedForGbv(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
		cd.setName("eligibleForRetest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("eligibleForRetest");
		
		return cd;
	}
	
	//htsTstEligibleForRetest
	public CohortDefinition htsTstEligibleForRetest(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("htsTstEligibleForRetest");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("htsTstEligibleForRetest");
		
		return cd;
	}
	
	//retestedHIVPositive
	public CohortDefinition retestedHIVPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
		cd.setName("kplhivSuppressedVlArtNonPEPFARSite");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("kplhivSuppressedVlArtNonPEPFARSite");
		
		return cd;
	}
	
	public CohortDefinition kplhivVlResultArtNonPEPFARSite(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
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
		String sqlQuery = "";
		cd.setName("acceptedPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("acceptedPNS");
		
		return cd;
	}
	
	//elicitedAndAcceptedPNS
	public CohortDefinition elicitedAndAcceptedPNS(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("elicitedAndAcceptedPNS");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("elicitedAndAcceptedPNS ");
		
		return cd;
	}
	
	//pnsKnownPositieAtEntry
	public CohortDefinition pnsKnownPositieAtEntry(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("pnsKnownPositieAtEntry");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsKnownPositieAtEntry");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedPositive(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("pnsTestedPositive");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedPositive");
		
		return cd;
	}
	
	public CohortDefinition pnsTestedNegative(String kpType) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		String sqlQuery = "";
		cd.setName("pnsTestedNegative");
		cd.setQuery(sqlQuery);
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setDescription("pnsTestedNegative");
		
		return cd;
	}
	
}
