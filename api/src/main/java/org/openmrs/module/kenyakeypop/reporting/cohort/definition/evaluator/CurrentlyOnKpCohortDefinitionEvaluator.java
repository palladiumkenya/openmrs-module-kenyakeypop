/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.CurrentlyOnKpCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for currentlyOnKpCohortDefinition Active on KP Criteria ------------ Contact visit
 * within last 3 months Clinic visit within last 3 months Peer calendar visit within last 3 months
 * Enrollment visit within last 3 months
 */
@Handler(supports = { CurrentlyOnKpCohortDefinition.class })
public class CurrentlyOnKpCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {
		
		CurrentlyOnKpCohortDefinition definition = (CurrentlyOnKpCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();
		
		String qry = "select c.client_id from kenyaemr_etl.etl_contact c\n"
		        + "  left join (select p.client_id from kenyaemr_etl.etl_peer_calendar p where p.voided = 0 group by p.client_id having max(p.visit_date) between date_sub(date(:endDate), INTERVAL 3 MONTH ) and date(:endDate)) cp on c.client_id=cp.client_id\n"
		        + "  left join (select v.client_id from kenyaemr_etl.etl_clinical_visit v where v.voided = 0 group by v.client_id having max(v.visit_date) between date_sub(date(:endDate), INTERVAL 3 MONTH ) and date(:endDate)) cv on c.client_id=cv.client_id\n"
		        + "  left join (select e.client_id from kenyaemr_etl.etl_client_enrollment e where e.voided = 0 group by e.client_id having max(e.visit_date) between date_sub(date(:endDate), INTERVAL 3 MONTH ) and date(:endDate)) ev on c.client_id=ev.client_id\n"
		        + "  left join (select d.patient_id, max(d.visit_date) latest_visit from kenyaemr_etl.etl_patient_program_discontinuation d where d.program_name='KP') d on c.client_id = d.patient_id\n"
		        + "where (d.patient_id is null or d.latest_visit > date(:endDate))\n"
		        + "      or (cp.client_id is not null or cv.client_id is not null or ev.client_id is not null)\n"
		        + "      or c.visit_date between date_sub(date(:endDate), INTERVAL 3 MONTH ) and date(:endDate)\n"
		        + "group by c.client_id;";
		
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		builder.addParameter("startDate", startDate);
		builder.addParameter("endDate", endDate);
		
		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		return new EvaluatedCohort(newCohort, definition, context);
	}
}
