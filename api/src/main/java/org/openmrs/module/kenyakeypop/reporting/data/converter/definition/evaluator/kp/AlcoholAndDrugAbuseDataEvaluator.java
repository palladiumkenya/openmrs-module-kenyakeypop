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
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.AlcoholAndDrugAbuseDataDefinition;
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
@Handler(supports = AlcoholAndDrugAbuseDataDefinition.class, order = 50)
public class AlcoholAndDrugAbuseDataEvaluator implements PersonDataEvaluator {
	
	@Autowired
	private EvaluationService evaluationService;
	
	public EvaluatedPersonData evaluate(PersonDataDefinition definition, EvaluationContext context)
	        throws EvaluationException {
		EvaluatedPersonData c = new EvaluatedPersonData(definition, context);
		
		String qry = " SELECT \n" + "    v.patient_id,\n" + "    TRIM(BOTH ', ' FROM CONCAT(\n"
		        + "        COALESCE(alcohol.alcohol_drinking_frequency, ''),\n" + "        CASE \n"
		        + "            WHEN COALESCE(alcohol.alcohol_drinking_frequency, '') <> '' \n"
		        + "                 AND COALESCE(smoking.smoking_frequency, '') <> '' THEN ', ' \n"
		        + "            ELSE '' \n" + "        END,\n" + "        COALESCE(smoking.smoking_frequency, ''),\n"
		        + "        CASE \n" + "            WHEN (COALESCE(alcohol.alcohol_drinking_frequency, '') <> '' \n"
		        + "                  OR COALESCE(smoking.smoking_frequency, '') <> '') \n"
		        + "                  AND COALESCE(drugs.drugs_use_frequency, '') <> '' THEN ', ' \n"
		        + "            ELSE '' \n" + "        END,\n" + "        COALESCE(drugs.drugs_use_frequency, '')\n"
		        + "    )) AS combined_frequency\n" + "FROM \n" + "    (SELECT DISTINCT patient_id \n"
		        + "     FROM kenyaemr_etl.etl_alcohol_drug_abuse_screening \n"
		        + "     WHERE date(visit_date) between date(:startDate) and date(:endDate)) v\n" + "LEFT JOIN \n"
		        + "    (SELECT \n" + "        v.patient_id, \n"
		        + "        CASE MID(MAX(CONCAT(v.visit_date, v.alcohol_drinking_frequency)), 11)\n"
		        + "            WHEN '1090' THEN 'Never'\n" + "            WHEN '1091' THEN 'Monthly or less'\n"
		        + "            WHEN '1092' THEN '2 to 4 times a month'\n"
		        + "            WHEN '1093' THEN '2 to 3 times a week'\n"
		        + "            WHEN '1094' THEN '4 or More Times a Week'\n" + "            ELSE '' \n"
		        + "        END AS alcohol_drinking_frequency\n" + "    FROM \n"
		        + "        kenyaemr_etl.etl_alcohol_drug_abuse_screening v \n" + "    WHERE \n"
		        + "        date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "    GROUP BY \n"
		        + "        v.patient_id) alcohol ON v.patient_id = alcohol.patient_id\n" + "LEFT JOIN \n" + "    (SELECT \n"
		        + "        v.patient_id, \n" + "        CASE MID(MAX(CONCAT(v.visit_date, v.smoking_frequency)), 11)\n"
		        + "            WHEN '1090' THEN 'Never smoked'\n"
		        + "            WHEN '156358' THEN 'Former cigarette smoker'\n"
		        + "            WHEN '163197' THEN 'Current some day smoker'\n"
		        + "            WHEN '163196' THEN 'Current light tobacco smoker'\n"
		        + "            WHEN '163195' THEN 'Current heavy tobacco smoker'\n"
		        + "            WHEN '163200' THEN 'Unknown if ever smoked'\n" + "            ELSE '' \n"
		        + "        END AS smoking_frequency\n" + "    FROM \n"
		        + "        kenyaemr_etl.etl_alcohol_drug_abuse_screening v \n" + "    WHERE \n"
		        + "        date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "    GROUP BY \n"
		        + "        v.patient_id) smoking ON v.patient_id = smoking.patient_id\n" + "LEFT JOIN \n" + "    (SELECT \n"
		        + "        v.patient_id, \n" + "        CASE MID(MAX(CONCAT(v.visit_date, v.drugs_use_frequency)), 11)\n"
		        + "            WHEN '1090' THEN 'Never'\n" + "            WHEN '1091' THEN 'Monthly or less'\n"
		        + "            WHEN '1092' THEN '2 to 4 times a month'\n"
		        + "            WHEN '1093' THEN '2 to 3 times a week'\n"
		        + "            WHEN '1094' THEN '4 or More Times a Week'\n" + "            ELSE '' \n"
		        + "        END AS drugs_use_frequency\n" + "    FROM \n"
		        + "        kenyaemr_etl.etl_alcohol_drug_abuse_screening v \n" + "    WHERE \n"
		        + "        date(v.visit_date) between date(:startDate) and date(:endDate)\n" + "    GROUP BY \n"
		        + "        v.patient_id) drugs ON v.patient_id = drugs.patient_id;";
		
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
