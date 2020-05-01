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
import org.openmrs.*;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.PatientService;
import org.openmrs.api.ProgramWorkflowService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.*;

public class EligibleForPrEPTreatmentVerificationForm extends BaseEmrCalculation {
	
	protected static final Log log = LogFactory.getLog(EligibleForPrEPTreatmentVerificationForm.class);
	
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		String PREP_Program = "214cad1c-bb62-4d8e-b927-810a046daf62";
		PatientService patientService = Context.getPatientService();
		CalculationResultMap ret = new CalculationResultMap();
		Set<Integer> alive = Filters.alive(cohort, context);
		Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
		Program kPProgram = MetadataUtils.existing(Program.class, KpMetadata._Program.KEY_POPULATION);
		Set<Integer> activeInKpProgram = Filters.inProgram(kPProgram, alive, context);
		Program prepProgram = MetadataUtils.existing(Program.class, PREP_Program);
		Set<Integer> inPrepProgram = Filters.inProgram(prepProgram, alive, context);
		EncounterService encounterService = Context.getEncounterService();
		FormService formService = Context.getFormService();
		ProgramWorkflowService service = Context.getProgramWorkflowService();
		
		for (int ptId : cohort) {
			Patient patient = patientService.getPatient(ptId);
			Date lastKpEnrollmentDate = null;
			Encounter lastClinicalEnrolmentEnc = EmrUtils.lastEncounter(patient,
			    encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT),
			    formService.getFormByUuid(KpMetadata._Form.KP_CLIENT_ENROLLMENT));
			boolean eligible = false;
			String hivStatus = null;
			
			int negativeConcept = 664;
			
			int hivStatusQuestionConcept = 165153;
			if (lastClinicalEnrolmentEnc != null) {
				for (Obs obs : lastClinicalEnrolmentEnc.getObs()) {
					if (obs.getConcept().getConceptId() == hivStatusQuestionConcept
					        && obs.getValueCoded().getConceptId() == negativeConcept) {
						
						hivStatus = "NEGATIVE";
					}
				}
			}
			
			if (!inHivProgram.contains(ptId) && activeInKpProgram.contains(ptId) && !inPrepProgram.contains(ptId)) {
				List<PatientProgram> programs = service.getPatientPrograms(Context.getPatientService().getPatient(ptId),
				    kPProgram, null, null, null, null, true);
				
				if (programs.size() > 0) {
					lastKpEnrollmentDate = programs.get(programs.size() - 1).getDateEnrolled();
					if (lastClinicalEnrolmentEnc != null && hivStatus != null
					        && lastKpEnrollmentDate.before(lastClinicalEnrolmentEnc.getEncounterDatetime())
					        && hivStatus.equalsIgnoreCase("NEGATIVE")) {
						eligible = true;
					}
				}
			}
			
			ret.put(ptId, new BooleanResult(eligible, this));
		}
		
		return ret;
		
	}
	
}
