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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ReceivedPostViolenceSupportDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.StatusInProgramDataDefinition;
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
@Handler(supports = StatusInProgramDataDefinition.class, order = 50)
public class StatusInProgramDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select r.patient_id,\n"
		        + "(case r.dead when 0 then (coalesce(IFNULL((case when timestampdiff(Month,max(date(p.visit_date)),date(curdate()))<=3  then \"A\" when timestampdiff(Month,max(date(p.visit_date)),date(curdate())) between 4 and 9 then \"DT\"\n"
		        + "when timestampdiff(Month,max(date(p.visit_date)),date(curdate())) > 9 then \"LTFU\" else null end),(case when timestampdiff(Month,max(date(v.visit_date)),date(curdate()))<=3  then \"A\" when timestampdiff(Month,max(date(v.visit_date)),date(curdate())) between 4 and 9 then \"DT\"\n"
		        + "when timestampdiff(Month,max(date(v.visit_date)),date(curdate())) > 9 then \"LTFU\" else \"\" end)))) when 1 then \"D\" else null end) as status_in_program from kenyaemr_etl.etl_patient_demographics r left join kenyaemr_etl.etl_peer_calendar p on r.patient_id = p.client_id\n"
		        + "left join kenyaemr_etl.etl_clinical_visit v\n"
		        + "on r.patient_id = v.client_id where p.client_id is not null or v.client_id is not null group by r.patient_id;\n";
		
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
