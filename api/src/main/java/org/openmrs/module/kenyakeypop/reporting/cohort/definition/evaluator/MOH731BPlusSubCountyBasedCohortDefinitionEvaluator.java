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

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.MOH731BPlusSubCountyBasedCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.common.ObjectUtil;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Evaluator for MOH731BPlus SubCounty Based Cohort Definition
 */
@Handler(supports = { MOH731BPlusSubCountyBasedCohortDefinition.class })
public class MOH731BPlusSubCountyBasedCohortDefinitionEvaluator implements CohortDefinitionEvaluator {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	EvaluationService evaluationService;
	
	@Override
	public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {
		
		MOH731BPlusSubCountyBasedCohortDefinition definition = (MOH731BPlusSubCountyBasedCohortDefinition) cohortDefinition;
		
		if (definition == null)
			return null;
		
		Cohort newCohort = new Cohort();
		
		context = ObjectUtil.nvl(context, new EvaluationContext());
		
		String qry = "";
		SqlQueryBuilder builder = new SqlQueryBuilder();
		
		String subCounty = (String) context.getParameterValue("subCounty");
		
		if (subCounty != null && !subCounty.equals("All Counties")) {
			qry = "Select client_id from kenyaemr_etl.etl_contact  where implementation_subcounty=:subCounty\n"
			        + " and date(visit_date) between (:startDate) and (:endDate);\n";
			builder.addParameter("subCounty", subCounty);
			
		} else {
			qry = "Select client_id from kenyaemr_etl.etl_contact  where date(visit_date) between (:startDate) and (:endDate);";
		}
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