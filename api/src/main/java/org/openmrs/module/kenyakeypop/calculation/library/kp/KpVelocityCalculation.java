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
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;

import java.util.Collection;
import java.util.Map;

public class KpVelocityCalculation extends BaseEmrCalculation {
	
	protected static final Log log = LogFactory.getLog(KpVelocityCalculation.class);
	
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		
		CalculationResultMap ret = new CalculationResultMap();
		StringBuilder sb = new StringBuilder();
		for (Integer ptId : cohort) {
			PersonAttribute kpAlias = null;
			PersonAttributeType pt = Context.getPersonService().getPersonAttributeTypeByUuid(
			    KpMetadata._PersonAttributeType.KP_CLIENT_ALIAS);
			PersonService personService = Context.getPersonService();
			kpAlias = personService.getPerson(ptId).getAttribute(pt.getId());
			
			sb.append("kpAlias:").append(kpAlias);
			ret.put(ptId, new SimpleResult(sb.toString(), this, context));
		}
		return ret;
	}
	
}
