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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.CurrentCareFacilityDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.CurrentlyOnARTDataDefinition;
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
@Handler(supports = CurrentCareFacilityDataDefinition.class, order = 50)
public class CurrentCareFacilityDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select r.patient_id r, case coalesce(max(t.final_test_result),max(v.self_test_results),max(v.hiv_self_rep_status)) when 'Positive'  then\n"
		        + "  (case when max(v.active_art) = 'Yes' then (case when max(v.hiv_care_facility)='Provided here' then (select FacilityName from kenyaemr_etl.etl_default_facility_info)\n"
		        + "                                             when max(v.hiv_care_facility)='Provided elsewhere' then v.hiv_care_facility else '' end)\n"
		        + "    else '' end)  else '' end as facility_of_care\n"
		        + "from kenyaemr_etl.etl_patient_demographics r left outer join kenyaemr_etl.etl_hts_test t on r.patient_id = t.patient_id  left outer join kenyaemr_etl.etl_clinical_visit v on r.patient_id = v.client_id\n"
		        + "where v.client_id is not null or t.patient_id is not null group by r.patient_id;";
		
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
