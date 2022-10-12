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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ActiveKpHIVStatusDataDefinition;
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
@Handler(supports = ActiveKpHIVStatusDataDefinition.class, order = 50)
public class ActiveKpHIVStatusDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select c.client_id,coalesce((case v.test_results when \"Positive\" then \"P\" when \"Negative\" then \"N\" when \"Known Positive\" then \"P\" else \"\" end ),\n"
		        + "                            (case t.final_test_result when \"Positive\" then \"P\" when \"Negative\" then \"N\" else \"\" end)) as hiv_status\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "  left outer join kenyaemr_etl.etl_clinical_visit v on v.client_id = c.client_id and v.visit_date between date_sub(date(curdate()), INTERVAL 3 MONTH ) and date(curdate())\n"
		        + "  left outer join kenyaemr_etl.etl_hts_test t on t.patient_id = c.client_id and t.visit_date between date_sub(date(curdate()), INTERVAL 3 MONTH ) and date(curdate())\n"
		        + "where v.client_id is not null or t.patient_id is not null\n" + "group by c.client_id;";
		
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
