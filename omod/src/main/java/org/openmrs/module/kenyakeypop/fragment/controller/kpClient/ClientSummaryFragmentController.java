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

import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Patient;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Program;
import org.openmrs.Person;
import org.openmrs.PersonAttribute;
import org.openmrs.api.ConceptService;
import org.openmrs.api.ObsService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.calculation.library.hiv.LastReturnVisitDateCalculation;
import org.openmrs.module.kenyaemr.calculation.library.models.PatientSummary;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyakeypop.calculation.library.kp.NextAppointmentDateCalculation;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.common.DateUtil;
import org.openmrs.module.reporting.common.DurationUnit;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.http.impl.cookie.DateUtils.formatDate;

/**
 * Patient summary fragment
 */
public class ClientSummaryFragmentController {
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	ObsService obsService = Context.getObsService();
	
	ConceptService conceptService = Context.getConceptService();
	
	public void controller(@FragmentParam("patient") Patient patient, @SpringBean FormManager formManager,
	        @SpringBean KenyaUiUtils kenyaUi, PageRequest pageRequest, UiUtils ui, FragmentModel model) {
		
		EncounterType encKPClientTracing = MetadataUtils.existing(EncounterType.class,
		    KpMetadata._EncounterType.KP_CLIENT_TRACING);
		Form formKPClientTracing = MetadataUtils.existing(Form.class, KpMetadata._Form.KP_CLIENT_TRACING_FORM);
		Program kpProgram = MetadataUtils.existing(Program.class, KpMetadata._Program.KEY_POPULATION);
		
		/*List<Encounter> encs = EmrUtils.AllEncounters(patient, encSocialStatus, formSocialStatus);
		List<SimpleObject> simplifiedEncData = new ArrayList<SimpleObject>();
		for (Encounter e : encs) {
			SimpleObject o = buildEncounterData(e.getObs(), e);
			simplifiedEncData.add(o);
		}*/
		List<Encounter> encs = EmrUtils.AllEncounters(patient, encKPClientTracing, formKPClientTracing);
		List<SimpleObject> simplifiedEncData = new ArrayList<SimpleObject>();
		for (Encounter e : encs) {
			SimpleObject o = buildEncounterData(e.getObs());
			simplifiedEncData.add(o);
		}
		
		CalculationResult nextAppointmentResults = EmrCalculationUtils.evaluateForPatient(
		    NextAppointmentDateCalculation.class, null, patient);
		String nextVisitDate;
		
		nextVisitDate = this.formatDate((Date) nextAppointmentResults.getValue());
		/*	if (nextAppointmentResults.isEmpty()) {
				//System.out.println("=======================" + nextAppointmentDate.getValue());
				nextVisitDate = "None";
			} else {
				nextVisitDate = formatDate((Date) nextAppointmentResults.getValue());
			}*/
		
		model.addAttribute("patient", patient);
		model.addAttribute("encounters", simplifiedEncData);
		model.addAttribute("nextVisitDate", nextVisitDate);
	}
	
	private String formatDate(Date date) {
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		return date == null ? "" : dateFormatter.format(date);
	}
	
	public static SimpleObject buildEncounterData(Set<Obs> obsList) {
		/*			CalculationResultMap nextAppointmentMap = Calculations.lastObs(Dictionary.getConcept(Dictionary.RETURN_VISIT_DATE), cohort, context);
					Obs nextOfVisitObs = EmrCalculationUtils.obsResultForPatient(nextAppointmentMap, ptId);
					Date notAlostToFollowPatient = DateUtil.adjustDate(nextOfVisitObs.getValueDatetime(), 90, DurationUnit.DAYS);*/
		
		int STATUS_IN_PROGRAM = 161641;
		
		String statusInProgram = null;
		/*String nextAppointmentDate = null;
		String frequentedHotspot = null;
		Integer weeklySexActs = null;
		Integer weeklyAnalSexActs = null;
		Integer dailyDrugInjections = null;
		Integer weeklyDrugInjections = null;*/
		// next appointment date
		/*	CalculationResult returnVisitResults = EmrCalculationUtils.evaluateForPatient(LastReturnVisitDateCalculation.class, null, patient);
			if(returnVisitResults != null){
				patientSummary.setNextAppointmentDate(formatDate((Date) returnVisitResults.getValue()));
			}
			else {
				patientSummary.setNextAppointmentDate("");
			}*/
		
		//String encDate = e != null ? DATE_FORMAT.format(e.getEncounterDatetime()) : "";
		
		for (Obs obs : obsList) {
			if (obs.getConcept().getConceptId().equals(STATUS_IN_PROGRAM)) {
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
		return SimpleObject.create("statusInProgram", statusInProgram != null ? statusInProgram : "");
		
	}
}
