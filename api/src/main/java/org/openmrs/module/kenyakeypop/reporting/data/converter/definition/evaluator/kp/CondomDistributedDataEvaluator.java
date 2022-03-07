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
import org.openmrs.module.reporting.data.person.EvaluatedPersonData;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.data.person.evaluator.PersonDataEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * api Evaluates a PersonDataDefinition
 */
@Handler(supports = CondomDistributedDataDefinition.class, order = 50)
public class CondomDistributedDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		String reportDate = DATE_FORMAT.format(context.getEvaluationDate());
		
		String qry = "select c.client_id, sum(ifnull(v1.v_condoms_dist,0)+ifnull(p1.pc_condoms_dist,0)+ifnull(t1.t_condoms_dist,0)) as condoms_distributed from kenyaemr_etl.etl_kp_contact c\n"
		        + "left join (select t.client_id,sum(ifnull(t.no_of_condoms,0)) as t_condoms_dist from kenyaemr_etl.etl_kp_sti_treatment t where t.visit_date between date_sub(\""
		        + reportDate
		        + "\", INTERVAL 30 DAY) and \""
		        + reportDate
		        + "\" group by t.client_id )t1 on c.client_id = t1.client_id\n"
		        + "left join (select v.client_id,sum((ifnull(v.female_condoms_no,0)+ifnull(v.male_condoms_no,0))) as v_condoms_dist from kenyaemr_etl.etl_kp_clinical_visit v where v.visit_date between date_sub(\""
		        + reportDate
		        + "\", INTERVAL 30 DAY) and \""
		        + reportDate
		        + "\" group by v.client_id)v1 on c.client_id = v1.client_id\n"
		        + "left join (select p.client_id, p.monthly_male_condoms_distributed,p.monthly_female_condoms_distributed,sum(ifnull(p.monthly_male_condoms_distributed,0)+ifnull(p.monthly_female_condoms_distributed,0)) as pc_condoms_dist from kenyaemr_etl.etl_kp_peer_calendar p where p.visit_date between date_sub(\""
		        + reportDate
		        + "\", INTERVAL 30 DAY) and \""
		        + reportDate
		        + "\" group by p.client_id)p1 on c.client_id = p1.client_id\n" + "group by c.client_id;";
		
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
