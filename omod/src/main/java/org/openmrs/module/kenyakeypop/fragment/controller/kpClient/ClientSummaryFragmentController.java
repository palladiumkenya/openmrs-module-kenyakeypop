/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.fragment.controller.kpClient;

import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.PatientArtOutComeCalculation;
import org.openmrs.module.kenyaemr.calculation.library.hiv.art.ViralLoadAndLdlCalculation;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Patient summary fragment
 */
public class ClientSummaryFragmentController {
	
	Integer APPOINTMENT_DATE_CONCEPT = 5096;
	
	Date nextAppointmentDate = null;
	
	String vlResults = "";
	
	String artStatus = "";
	
	Integer STATUS_IN_PROGRAM_CONCEPT = 161641;
	
	Integer VL_RESULTS_CONCEPT = 165246;
	
	Integer ACTIVE_ON_ART_CONCEPT = 160119;
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	EncounterService encounterService = Context.getEncounterService();
	
	FormService formService = Context.getFormService();
	
	public void controller(@FragmentParam("patient") Patient patient, @SpringBean FormManager formManager,
	        @SpringBean KenyaUiUtils kenyaUi, PageRequest pageRequest, UiUtils ui, FragmentModel model) {
		
		String statusInProgram = null;
		
		Encounter lastTracingEnc = EmrUtils.lastEncounter(patient,
		    encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CLIENT_TRACING),
		    formService.getFormByUuid(KpMetadata._Form.KP_CLIENT_TRACING_FORM));
		
		if (lastTracingEnc != null) {
			
			for (Obs obs : lastTracingEnc.getObs()) {
				if (obs.getConcept().getConceptId().equals(STATUS_IN_PROGRAM_CONCEPT)) {
					if (obs.getValueCoded().getConceptId().equals(5240)) {
						statusInProgram = "Lost to follow up";
					} else if (obs.getValueCoded().getConceptId().equals(160031)) {
						statusInProgram = "Defaulted";
					} else if (obs.getValueCoded().getConceptId().equals(161636)) {
						statusInProgram = "Active";
					} else if (obs.getValueCoded().getConceptId().equals(160432)) {
						statusInProgram = "Dead";
					}
				}
			}
		} else {
			
			statusInProgram = "Undocumented";
		}
		
		Encounter lastVisitEnc = EmrUtils.lastEncounter(patient,
		    encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM),
		    formService.getFormByUuid(KpMetadata._Form.KP_CLINICAL_VISIT_FORM));
		
		if (lastVisitEnc != null) {
			
			for (Obs obs : lastVisitEnc.getObs()) {
				if (obs.getConcept().getConceptId().equals(APPOINTMENT_DATE_CONCEPT)) {
					nextAppointmentDate = obs.getValueDatetime();
				}
				
			}
		}
		
		model.addAttribute("patient", patient);
		model.addAttribute("statusInProgram", statusInProgram);
		model.addAttribute("artStatus", getArtStatus(patient));
		model.addAttribute("nextAppointmentDate", formatDate(nextAppointmentDate));
	}
	
	private String formatDate(Date date) {
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		return date == null ? "" : dateFormatter.format(date);
	}
	
	public boolean enrolledForHiv(Patient patient) {
		Encounter enrolledForHIVenc = EmrUtils.lastEncounter(patient,
		    encounterService.getEncounterTypeByUuid(HivMetadata._EncounterType.HIV_ENROLLMENT),
		    formService.getFormByUuid(HivMetadata._Form.HIV_ENROLLMENT));
		return (enrolledForHIVenc != null ? true : false);
		
	}
	
	private String getArtStatus(Patient patient) {
		String artResultValue = "None";
		String viralLoadValue = "None";
		String viralLoadDate = "None";
		String pattern;
		String toDate;
		String dateSplit;
		
		if (enrolledForHiv(patient)) {
			CalculationResult vlResults = EmrCalculationUtils.evaluateForPatient(ViralLoadAndLdlCalculation.class,
			    (String) null, patient);
			CalculationResult artStatusResults = EmrCalculationUtils.evaluateForPatient(PatientArtOutComeCalculation.class,
			    (String) null, patient);
			
			if (!vlResults.isEmpty()) {
				String viralLoad = vlResults.getValue().toString();
				pattern = viralLoad.replaceAll("\\{", "").replaceAll("\\}", "");
				if (!pattern.isEmpty()) {
					String[] splitByEqualSign = pattern.split("=");
					viralLoadValue = splitByEqualSign[0];
					String dateSplitedBySpace = splitByEqualSign[1].split(" ")[0].trim();
					toDate = dateSplitedBySpace.split("-")[0].trim();
					dateSplit = dateSplitedBySpace.split("-")[1].trim();
					String dayPart = dateSplitedBySpace.split("-")[2].trim();
					Calendar calendar = Calendar.getInstance();
					calendar.set(1, Integer.parseInt(toDate));
					calendar.set(2, Integer.parseInt(dateSplit) - 1);
					calendar.set(5, Integer.parseInt(dayPart));
					viralLoadDate = this.formatDate(calendar.getTime());
				}
				if (!artStatusResults.isEmpty()) {
					artResultValue = artStatusResults.getValue().toString();
				} else {
					artResultValue = "No ART outcome";
				}
				
				return new StringBuilder().append(artResultValue).append(" - ").append(viralLoadValue).append(" on ")
				        .append(viralLoadDate).toString();
			} else {
				
				return new StringBuilder().append(artResultValue).append(viralLoadValue).append(viralLoadDate).toString();
			}
		} else {
			StringBuilder sb = new StringBuilder();
			Encounter lastVisitEnc = EmrUtils.lastEncounter(patient,
			    encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM),
			    formService.getFormByUuid(KpMetadata._Form.KP_CLINICAL_VISIT_FORM));
			if (lastVisitEnc != null) {
				for (Obs obs : lastVisitEnc.getObs()) {
					
					if (obs.getConcept().getConceptId().equals(VL_RESULTS_CONCEPT)) {
						
						if (obs.getValueCoded().getConceptId().equals(165244)) {
							vlResults = "Suppressed";
						} else if (obs.getValueCoded().getConceptId().equals(165244)) {
							vlResults = "Not Suppressed";
						} else if (obs.getValueCoded().getConceptId().equals(165244)) {
							vlResults = "Awaiting Results";
						} else if (obs.getValueCoded().getConceptId().equals(165244)) {
							vlResults = "N/A";
						}
					} else {
						vlResults = "-";
					}
					if (obs.getConcept().getConceptId().equals(ACTIVE_ON_ART_CONCEPT)) {
						if (obs.getValueCoded().getConceptId().equals(1065)) {
							artStatus = "Active";
						} else if (obs.getValueCoded().getConceptId().equals(1066)) {
							artStatus = "Inactive";
						} else if (obs.getValueCoded().getConceptId().equals(1175)) {
							artStatus = "N/A";
						}
					} else {
						artStatus = "-";
					}
					
				}
			}
			return sb.append(artStatus).append(":").append(vlResults).toString();
		}
	}
	
}
