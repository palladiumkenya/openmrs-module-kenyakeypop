/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.data.converter.definition.evaluator.kp;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.OppotunisticInfectiousDiagnosedDateDataDefinition;
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Evaluates a PersonDataDefinition
 */
@Handler(supports = OppotunisticInfectiousDiagnosedDateDataDefinition.class, order = 50)
public class OppotunisticInfectiousDiagnosedDateDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "SELECT  t1.client_id, COALESCE(t1.oi_diagnosis_date, t2.date_diagnosed) AS oi_diagnosis_date\n"
		        + "FROM (\n"
		        + "SELECT v.client_id, MID(MAX(CONCAT(v.visit_date, v.oi_diagnosis_date)), 11) AS oi_diagnosis_date\n"
		        + "FROM kenyaemr_etl.etl_treatment_verification v\n"
		        + "WHERE DATE(v.visit_date) between date(:startDate) and date(:endDate)) AS t1 LEFT JOIN (\n"
		        + "SELECT d.patient_id, MID(MAX(CONCAT(d.visit_date, d.date_diagnosed)), 11) AS date_diagnosed\n"
		        + "FROM kenyaemr_etl.etl_link_facility_tracking d\n"
		        + "WHERE DATE(d.visit_date) between date(:startDate) and date(:endDate)\n"
		        + ") AS t2 ON t1.client_id = t2.patient_id;";
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		queryBuilder.addParameter("endDate", endDate);
		queryBuilder.addParameter("startDate", startDate);
		queryBuilder.append(qry);
		Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
		c.setData(data);
		return c;
	}
}