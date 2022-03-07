/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.data.converter.definition.evaluator.kp.outreachworkersummary;

import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.outreachworkersummary.NumberOfMaleCondomsDistributedDataDefinition;
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
 * Number Of Male Condoms Distributed DataDefinition column
 */
@Handler(supports = NumberOfMaleCondomsDistributedDataDefinition.class, order = 50)
public class NumberOfMaleCondomsDistributedDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select r.person_a as peer_educator,(sum(a.monthly_male_condoms_distributed)+b.monthly_male_condoms_distributed) as monthly_male_condoms_distributed from relationship r\n"
		        + "inner join (select pc.client_id,IFNULL(pc.monthly_male_condoms_distributed,0) as monthly_male_condoms_distributed from kenyaemr_etl.etl_kp_peer_calendar pc GROUP BY pc.client_id) a\n"
		        + "on r.person_b = a.client_id\n"
		        + "inner join (select pc.client_id,IFNULL(pc.monthly_male_condoms_distributed,0) as monthly_male_condoms_distributed from kenyaemr_etl.etl_kp_peer_calendar pc GROUP BY pc.client_id) b\n"
		        + "on r.person_a = b.client_id where r.voided = 0 group by r.person_a;";
		
		SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
		queryBuilder.append(qry);
		Map<Integer, Object> data = evaluationService.evaluateToMap(queryBuilder, Integer.class, Object.class, context);
		c.setData(data);
		return c;
	}
}
