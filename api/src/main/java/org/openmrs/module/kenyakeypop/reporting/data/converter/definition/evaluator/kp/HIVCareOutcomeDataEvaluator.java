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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.CurrentlyOnARTDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.HIVCareOutcomeDataDefinition;
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
@Handler(supports = HIVCareOutcomeDataDefinition.class, order = 50)
public class HIVCareOutcomeDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = "select c.client_id, case when TIMESTAMPDIFF(DAY,max(date(hf.latest_tca)),max(date(hf.latest_hiv_visit))) >= 90 then 'LTFU'\n"
		        + "                         when TIMESTAMPDIFF(DAY,max(date(hf.latest_tca)),max(date(hf.latest_hiv_visit))) between 1 and 30 then 'DT'\n"
		        + "                         when d.patient_id is not null and d.discontinuation_reason=159492 then 'TO'\n"
		        + "                         when d.patient_id is not null and d.discontinuation_reason=160034 then 'D'\n"
		        + "                         when d.patient_id is null and max(date(hf.latest_tca)) > max(date(hf.latest_hiv_visit)) then 'A' else '' end as hiv_outcome\n"
		        + "from kenyaemr_etl.etl_contact c\n"
		        + "  left outer join kenyaemr_etl.etl_hts_test t on c.client_id = t.patient_id  left outer join kenyaemr_etl.etl_clinical_visit v on c.client_id = v.client_id\n"
		        + "  left join (select d.patient_id, date(max(d.visit_date)) latest_visit,d.discontinuation_reason as discontinuation_reason from kenyaemr_etl.etl_patient_program_discontinuation d where d.program_name='HIV' group by d.patient_id) d on c.client_id = d.patient_id\n"
		        + "  left join (select h.patient_id, date(max(h.visit_date)) latest_hiv_visit,date(max(h.next_appointment_reason)) as latest_tca from kenyaemr_etl.etl_patient_hiv_followup h group by h.patient_id) hf on c.client_id = hf.patient_id\n"
		        + "where (v.client_id is not null or d.patient_id is not null or hf.patient_id is not null) group by c.client_id;";
		
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
