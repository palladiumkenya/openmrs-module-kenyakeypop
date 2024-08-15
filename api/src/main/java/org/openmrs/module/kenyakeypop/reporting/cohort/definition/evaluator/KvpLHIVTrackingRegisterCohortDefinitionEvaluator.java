/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.cohort.definition.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.KvpLHIVTrackingRegisterCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for KP Cohort Register
 */
@Handler(supports = { KvpLHIVTrackingRegisterCohortDefinition.class })
public class KvpLHIVTrackingRegisterCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {
		
		KvpLHIVTrackingRegisterCohortDefinition definition = (KvpLHIVTrackingRegisterCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();
		
		context = ObjectUtil.nvl(context, new EvaluationContext());
		
		String qry = "select \n" + "    v.client_id, \n"
		        + "    mid(max(concat(tv.visit_date, tv.date_diagnosed_with_hiv)), 11) as date_diagnosed_with_hiv,\n"
		        + "    mid(max(concat(lft.visit_date, lft.date_diagnosed)), 11) as date_diagnosed\n" + "from \n"
		        + "    kenyaemr_etl.etl_clinical_visit v\n"
		        + "    inner join kenyaemr_etl.etl_contact c on c.client_id = v.client_id\n" + "    left join (\n"
		        + "        select \n" + "            v.client_id, \n" + "            v.visit_date, \n"
		        + "            v.date_diagnosed_with_hiv \n" + "        from \n"
		        + "            kenyaemr_etl.etl_treatment_verification v\n" + "        where \n"
		        + "            date(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n"
		        + "    ) tv on v.client_id = tv.client_id\n" + "    left join (\n" + "        select \n"
		        + "            v.patient_id as client_id, \n" + "            v.visit_date, \n"
		        + "            v.date_diagnosed \n" + "        from \n"
		        + "            kenyaemr_etl.etl_link_facility_tracking v\n" + "        where  \n"
		        + "            date(v.visit_date) BETWEEN DATE(:startDate) AND DATE(:endDate)\n"
		        + "    ) lft on v.client_id = lft.client_id\n" + "where  \n" + "    c.voided = 0 \n"
		        + "    and (lft.client_id is not null or tv.client_id is not null)\n" + "group by \n" + "    v.client_id;";
		SqlQueryBuilder builder = new SqlQueryBuilder();
		builder.append(qry);
		Date startDate = (Date) context.getParameterValue("startDate");
		Date endDate = (Date) context.getParameterValue("endDate");
		builder.addParameter("endDate", endDate);
		builder.addParameter("startDate", startDate);
		
		List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
		newCohort.setMemberIds(new HashSet<Integer>(ptIds));
		
		return new EvaluatedCohort(newCohort, definition, context);
	}
	
}
