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
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
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
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EligibleForClinicalServices extends BaseEmrCalculation {

	protected static final Log log = LogFactory.getLog(EligibleForClinicalServices.class);

	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
										 PatientCalculationContext context) {

		Program kPProgram = MetadataUtils.existing(Program.class, KpMetadata._Program.KEY_POPULATION);
		CalculationResultMap ret = new CalculationResultMap();
		Set<Integer> activeInKpProgram = Filters.inProgram(kPProgram, cohort, context);
		ProgramWorkflowService service = Context.getProgramWorkflowService();

		EncounterService encounterService = Context.getEncounterService();
		PatientService patientService = Context.getPatientService();
		FormService formService = Context.getFormService();

		StringBuilder sb = new StringBuilder();
		boolean eligible = false;

		for (Integer ptId : cohort) {
			Patient patient = patientService.getPatient(ptId);
			Date lastKpenrollmentDate = null;
			int priorityPopQuestionConcept = 138643;

			if (activeInKpProgram.contains(ptId)) {
				List<PatientProgram> programs = service.getPatientPrograms(Context.getPatientService().getPatient(ptId),
						kPProgram, null, null, null, null, true);
				if (programs.size() > 0) {
					lastKpenrollmentDate = programs.get(programs.size() - 1).getDateEnrolled();

					Encounter lastClinicalContactEnc = EmrUtils.lastEncounter(patient,
							encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CONTACT),
							formService.getFormByUuid(KpMetadata._Form.KP_CONTACT_FORM));
					if (lastClinicalContactEnc != null) {
						for (Obs obs : lastClinicalContactEnc.getObs()) {
							if (obs.getConcept().getConceptId() == priorityPopQuestionConcept
									&& obs.getValueCoded().getConceptId() != null) {
								eligible = true;
							}
						}
					}

					Encounter lastClinicalEnrolmentEnc = EmrUtils.lastEncounter(patient,
							encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT),
							formService.getFormByUuid(KpMetadata._Form.KP_CLIENT_ENROLLMENT));

					if (lastClinicalEnrolmentEnc != null
							&& lastKpenrollmentDate.before(lastClinicalEnrolmentEnc.getEncounterDatetime())) {
						eligible = true;
					}
				}
			}
			ret.put(ptId, new BooleanResult(eligible, this, context));
		}
		return ret;
	}

}
