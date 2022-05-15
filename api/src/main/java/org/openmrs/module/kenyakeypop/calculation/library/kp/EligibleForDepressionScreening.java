/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.calculation.library.kp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EligibleForDepressionScreening extends BaseEmrCalculation {
	
	protected static final Log log = LogFactory.getLog(EligibleForDepressionScreening.class);
	
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		
		PatientService patientService = Context.getPatientService();
		
		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> isAlive = new HashSet<Integer>();
		isAlive.addAll(alive);
		
		CalculationResultMap ret = new CalculationResultMap();
		
		for (int ptId : cohort) {
			boolean eligible = false;
			
			Patient patient = patientService.getPatient(ptId);
			boolean living = isAlive.contains(ptId);
			if (living && patient.getAge() > 4) {
				eligible = true;
			}
			ret.put(ptId, new BooleanResult(eligible, this));
		}
		return ret;
	}
	
}
