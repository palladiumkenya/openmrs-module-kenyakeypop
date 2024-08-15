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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.TreatmentForCervicalCancerDataDefinition;
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
@Handler(supports = TreatmentForCervicalCancerDataDefinition.class, order = 50)
public class TreatmentForCervicalCancerDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "  SELECT patient_id,\n"
		        + "CASE\n"
		        + "WHEN cervical_cancer = 'Yes'\n"
		        + "AND hpv_screening_method = 'HPV'\n"
		        + "AND via_vili_screening_method = 'VIA' THEN\n"
		        + "CASE\n"
		        + "WHEN hpv_screening_result = 'Positive'\n"
		        + "AND via_vili_screening_result = 'Positive'\n"
		        + "AND colposcopy_treatment_method IS NOT NULL\n"
		        + "AND colposcopy_treatment_method <> ''\n"
		        + "THEN 'Y'\n"
		        + "WHEN hpv_screening_result IN ('Positive', 'Negative')\n"
		        + "AND (colposcopy_treatment_method IS NULL\n"
		        + "OR colposcopy_treatment_method = '')\n"
		        + "THEN 'N'\n"
		        + "ELSE 'NA'\n"
		        + "END\n"
		        + "ELSE 'NA'\n"
		        + "END AS result"
		        + "FROM kenyaemr_etl.etl_cervical_cancer_screening where date(visit_date) between date(:startDate) and date(:endDate)\n"
		        + "group by patient_id;";
		
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
