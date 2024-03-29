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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.HTSDeliveryPointDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.TestedForHIVDataDefinition;
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
@Handler(supports = HTSDeliveryPointDataDefinition.class, order = 50)
public class HTSDeliveryPointDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select c.client_id, (case  t.hts_entry_point\n"
		        + "                     when 5485 then 'DIC'\n"
		        + "                     when 160542 then 'DIC'\n"
		        + "                     when 162181 then 'DIC'\n"
		        + "                     when 160552 then 'DIC'\n"
		        + "                     when 160538 then 'DIC'\n"
		        + "                     when 160456 then 'DIC'\n"
		        + "                     when 1623 then 'DICC'\n"
		        + "                     when 160541 then 'DIC'\n"
		        + "                     when 162050 then 'DIC'\n"
		        + "                     when 159940 then 'DIC'\n"
		        + "                     when 159938 then 'CO'\n"
		        + "                     when 159939 then 'CO'\n"
		        + "                     when 162223 then 'DIC'\n"
		        + "                     when 160546 then 'DIC'\n"
		        + "                     when 160522 then 'DIC'\n"
		        + "                     when 163096 then 'CO'\n"
		        + "                     when 5622 then 'DIC'\n"
		        + "                     else ''  end ) as hts_entry_point from kenyaemr_etl.etl_contact c left join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id\n"
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
