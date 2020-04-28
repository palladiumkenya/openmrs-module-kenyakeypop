/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.calculation.library.kp;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by codehub on 23/06/15.
 */
public class NextAppointmentDateCalculation extends AbstractPatientCalculation {
	
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> map,
	        PatientCalculationContext context) {
		
		Integer nextAppointmentDate = 5096;
		CalculationResultMap ret = new CalculationResultMap();
		
		Form kpClinicalVisit = MetadataUtils.existing(Form.class, KpMetadata._Form.KP_CLINICAL_VISIT_FORM);
		
		EncounterType kpFollowup = MetadataUtils.existing(EncounterType.class, HivMetadata._EncounterType.HIV_CONSULTATION);
		
		for (Integer ptId : cohort) {
			Date nextVisitDate = null;
			
			Encounter lastKPVisitEncounter = EmrUtils.lastEncounter(Context.getPatientService().getPatient(ptId),
			    kpFollowup, kpClinicalVisit); //last hiv followup form
			if (lastKPVisitEncounter != null) {
				for (Obs obs : lastKPVisitEncounter.getObs()) {
					if (obs.getConcept().getConceptId().equals(nextAppointmentDate)) {
						
						nextVisitDate = obs.getValueDatetime();
						ret.put(ptId, new SimpleResult(nextVisitDate, this));
					}
				}
			}
		}
		return ret;
}
	
}
