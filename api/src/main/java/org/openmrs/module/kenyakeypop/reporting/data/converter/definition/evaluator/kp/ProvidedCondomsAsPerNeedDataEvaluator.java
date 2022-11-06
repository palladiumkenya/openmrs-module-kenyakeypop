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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.CondomDistributedDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ProvidedCondomsAsPerNeedDataDefinition;
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
@Handler(supports = ProvidedCondomsAsPerNeedDataDefinition.class, order = 50)
public class ProvidedCondomsAsPerNeedDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select c.client_id as client_id ,if((coalesce(pc.monthly_condoms_required,0)) <= (coalesce(pc.monthly_male_condoms_distributed,0) + coalesce(pc.monthly_female_condoms_distributed,0)\n"
		        + "                                                                                   + coalesce(v.male_condoms_no,0) + coalesce(v.female_condoms_no,0)),'Y','N') as condoms_distributed_as_per_need\n"
		        + "from  kenyaemr_etl.etl_contact c\n"
		        + "  left join kenyaemr_etl.etl_peer_calendar pc on c.client_id = pc.client_id\n"
		        + "  left join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "  where (pc.client_id is not null or c.client_id is not null) and pc.monthly_condoms_required > 0\n"
		        + "group by c.client_id;";
		
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
