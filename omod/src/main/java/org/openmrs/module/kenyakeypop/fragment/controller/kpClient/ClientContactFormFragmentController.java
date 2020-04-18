/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
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
import org.openmrs.PatientProgram;
import org.openmrs.Person;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Concept;
import org.openmrs.User;

import org.openmrs.api.*;
import org.openmrs.api.context.Context;
import org.openmrs.PersonAttribute;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.util.EmrUtils;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyakeypop.KpConstant;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Set;
import java.util.Date;

/**
 * Patient summary fragment
 */
public class ClientContactFormFragmentController {
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	private AdministrationService administrationService;
	
	public void controller(@FragmentParam("patient") Patient patient, @SpringBean FormManager formManager,
	        @SpringBean KenyaUiUtils kenyaUi, PageRequest pageRequest, UiUtils ui, FragmentModel model) {
		
		EncounterType encSocialStatus = MetadataUtils.existing(EncounterType.class, KpMetadata._EncounterType.KP_CONTACT);
		Form formSocialStatus = MetadataUtils.existing(Form.class, KpMetadata._Form.KP_CONTACT_FORM);
		
		List<Encounter> encs = EmrUtils.AllEncounters(patient, encSocialStatus, formSocialStatus);
		List<SimpleObject> simplifiedEncData = new ArrayList<SimpleObject>();
		for (Encounter e : encs) {
			SimpleObject o = buildEncounterData(e.getObs(), e);
			simplifiedEncData.add(o);
		}
		model.addAttribute("patient", patient);
		model.addAttribute("encounters", simplifiedEncData);
	}
	
	public static SimpleObject buildEncounterData(Set<Obs> obsList, Encounter e) {
		
		int KPTYPE = 164929;
		int HOTSPOT = 165005;
		int SEX_ACTS_PER_WEEK = 165007;
		int ANAL_SEX_ACTS_PER_WEEK = 165008;
		int DAILY_DRUG_INJECTIONS = 165009;
		int WEEKLY_DRUG_INJECTIONS = 165010;
		
		String kpType = null;
		String frequentedHotspot = null;
		Integer weeklySexActs = null;
		Integer weeklyAnalSexActs = null;
		Integer dailyDrugInjections = null;
		Integer weeklyDrugInjections = null;
		String encDate = e != null ? DATE_FORMAT.format(e.getEncounterDatetime()) : "";
		
		for (Obs obs : obsList) {
			
			if (obs.getConcept().getConceptId().equals(KPTYPE)) {
				if (obs.getValueCoded().getConceptId().equals(165083)) {
					kpType = "FSW";
				} else if (obs.getValueCoded().getConceptId().equals(160578)) {
					kpType = "MSM";
				} else if (obs.getValueCoded().getConceptId().equals(165084)) {
					kpType = "MSW";
				} else if (obs.getValueCoded().getConceptId().equals(165085)) {
					kpType = "Drug User";
				} else if (obs.getValueCoded().getConceptId().equals(105)) {
					kpType = "Drug Injector";
				} else if (obs.getValueCoded().getConceptId().equals(165108)) {
					kpType = "Transman";
				} else if (obs.getValueCoded().getConceptId().equals(165107)) {
					kpType = "Transwoman";
				}
			} else if (obs.getConcept().getConceptId().equals(HOTSPOT)) {
				frequentedHotspot = obs.getValueCoded() != null ? obs.getValueCoded().getName().getName() : "";
			} else if (obs.getConcept().getConceptId().equals(SEX_ACTS_PER_WEEK)) {
				weeklySexActs = obs.getValueNumeric().intValue();
			} else if (obs.getConcept().getConceptId().equals(ANAL_SEX_ACTS_PER_WEEK)) {
				weeklyAnalSexActs = obs.getValueNumeric().intValue();
			} else if (obs.getConcept().getConceptId().equals(DAILY_DRUG_INJECTIONS)) {
				dailyDrugInjections = obs.getValueNumeric().intValue();
			} else if (obs.getConcept().getConceptId().equals(WEEKLY_DRUG_INJECTIONS)) {
				weeklyDrugInjections = obs.getValueNumeric().intValue();
			}
		}
		
		return SimpleObject.create("encDate", encDate, "encId", e.getEncounterId(), "kpType", kpType != null ? kpType : "",
		    "hotspot", frequentedHotspot != null ? frequentedHotspot : "", "weeklySexActs",
		    weeklySexActs != null ? weeklySexActs : "", "weeklyAnalSexActs", weeklyAnalSexActs != null ? weeklyAnalSexActs
		            : "", "dailyDrugInjections", dailyDrugInjections != null ? dailyDrugInjections : "",
		    "weeklyDrugInjections", weeklyDrugInjections != null ? weeklyDrugInjections : ""
		
		);
	}
	
	public SimpleObject saveOrUpdateClientAlias(@SpringBean("personService") PersonService personService,
	        @RequestParam(value = "patientId") Patient patient, @RequestParam(value = "alias") String alias)
	        throws Exception {
		Person person = patient.getPerson();
		PersonAttribute personAttribute = new PersonAttribute();
		personAttribute.setAttributeType(personService
		        .getPersonAttributeTypeByUuid(KpMetadata._PersonAttributeType.KP_CLIENT_ALIAS));
		personAttribute.setValue(alias);
		person.addAttribute(personAttribute);
		try {
			personService.savePerson(person);
			return SimpleObject.create("status", "Success", "message", "Client alias saved successfully");
		}
		catch (Exception e) {
			return SimpleObject.create("status", "Error", "message", "There was an error updating Client alias");
		}
		
	}
	
	public SimpleObject getGeneratedIdentifier(@SpringBean("personService") PersonService personService,
	        @SpringBean("encounterService") EncounterService encounterService,
	        @RequestParam(value = "patientId") Patient patient) {
		StringBuilder sb = new StringBuilder();
		String sql = "SELECT count(*) FROM patient_program pp\n" + "join program p on p.program_id = pp.program_id\n"
		        + "where p.uuid ='7447305a-18a7-11e9-ab14-d663bd873d93' ;";
		List<List<Object>> everEnrolled = Context.getAdministrationService().executeSQL(sql, true);
		Long everEnrolledTotal = (Long) everEnrolled.get(0).get(0);
		Integer kpSerialNumber = everEnrolledTotal.intValue() != 0 ? everEnrolledTotal.intValue() + 1 : 1;
		Program kpProgram = MetadataUtils.existing(Program.class, KpMetadata._Program.KEY_POPULATION);
		String hotSpotCode = null;
		String kpTypeCode = null;
		administrationService = Context.getAdministrationService();
		String county = administrationService.getGlobalProperty(KpConstant.GP_COUNTY_CODE);
		String subCounty = administrationService.getGlobalProperty(KpConstant.GP_SUB_COUNTY_CODE);
		String ward = administrationService.getGlobalProperty(KpConstant.GP_WARD_CODE);
		String implementingPartner = administrationService.getGlobalProperty(KpConstant.GP_IMPLEMENTING_PARTNER_CODE);
		StringBuilder identifier = new StringBuilder();
		
		Encounter lastEnc = EmrUtils.lastEncounter(patient,
		    encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_IDENTIFIER));
		if (lastEnc != null) {
			for (Obs obs : lastEnc.getObs()) {
				if (obs.getConcept().getConceptId() == 165006) {
					hotSpotCode = obs.getValueText();
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 165083) {
					kpTypeCode = "01";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 160578) {
					kpTypeCode = "02";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 105) {
					kpTypeCode = "03";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 165085) {
					kpTypeCode = "04";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 165108) {
					kpTypeCode = "05";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 165107) {
					kpTypeCode = "06";
				} else if (obs.getConcept().getConceptId() == 164929 && obs.getValueCoded().getConceptId() == 165084) {
					kpTypeCode = "07";
				}
			}
		}

		identifier.append(county);
		identifier.append(subCounty);
		identifier.append(ward);
		identifier.append(implementingPartner.toUpperCase());
		identifier.append(hotSpotCode);
		identifier.append(kpTypeCode);
		
		Date birthDate = personService.getPerson(patient.getId()).getBirthdate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(birthDate);
		int month = cal.get(Calendar.MONTH) + 1;
		String middleName = personService.getPerson(patient.getId()).getMiddleName() != null ? personService
		        .getPerson(patient.getId()).getMiddleName().substring(0, 2).toUpperCase() : "";
		String lastName = personService.getPerson(patient.getId()).getFamilyName().substring(0, 2).toUpperCase();
		String firstName = personService.getPerson(patient.getId()).getGivenName().substring(0, 2).toUpperCase();
		identifier.append(firstName).append(middleName).append(lastName);
		identifier.append(month);
		String serialNumber = String.format("%04d", kpSerialNumber);
		identifier.append(serialNumber);
		
		ProgramWorkflowService service = Context.getProgramWorkflowService();
		List<PatientProgram> programs = service.getPatientPrograms(Context.getPatientService().getPatient(patient.getId()),
		    kpProgram, null, null, null, null, true);
		
		if (programs.size() > 0) {
			PatientIdentifierType pit = MetadataUtils.existing(PatientIdentifierType.class,
			    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
			PatientIdentifier pObject = patient.getPatientIdentifier(pit);
			sb.append("identifier:").append(pObject.getIdentifier()).append(",");
		}
		
		if (lastEnc != null) {
			sb.append("identifier:").append(identifier).append(",");
			sb.append("kpTypeCode:").append(kpTypeCode);
		}
		
		if (lastEnc == null && programs.size() <= 0) {
			sb.append("identifier:").append(" ").append(",");
		}
		
		return SimpleObject.create("identifier", sb.toString());
		
	}
	
	public SimpleObject createIdentifier(@RequestParam("patientId") Patient patient,
	        @RequestParam("userId") User loggedInUser, @RequestParam("encounterDate") Date encounterDate,
	        @RequestParam("hotSpot") String hotSpot, @RequestParam("kpType") Concept kpType) {
		
		Integer hotSpotConcept = 165006;
		Integer KpTypeConcept = 164929;
		ConceptService conceptService = Context.getConceptService();
		
		Encounter enc = new Encounter();
		enc.setLocation(Context.getService(KenyaEmrService.class).getDefaultLocation());
		EncounterService encounterService = Context.getEncounterService();
		enc.setEncounterType(encounterService.getEncounterTypeByUuid(KpMetadata._EncounterType.KP_IDENTIFIER));
		enc.setEncounterDatetime(encounterDate);
		enc.setPatient(patient);
		enc.setCreator(loggedInUser);
		
		// set obs
		
		if (hotSpot != null) {
			Obs implementingPartnerObs = new Obs();
			implementingPartnerObs.setConcept(conceptService.getConcept(hotSpotConcept));
			implementingPartnerObs.setDateCreated(new Date());
			implementingPartnerObs.setCreator(loggedInUser);
			implementingPartnerObs.setLocation(enc.getLocation());
			implementingPartnerObs.setObsDatetime(enc.getEncounterDatetime());
			implementingPartnerObs.setPerson(patient);
			implementingPartnerObs.setValueText(hotSpot);
			enc.addObs(implementingPartnerObs);
		}
		
		if (kpType != null) {
			Obs kpTypeObs = new Obs();
			kpTypeObs.setConcept(conceptService.getConcept(KpTypeConcept));
			kpTypeObs.setDateCreated(new Date());
			kpTypeObs.setCreator(loggedInUser);
			kpTypeObs.setLocation(enc.getLocation());
			kpTypeObs.setObsDatetime(enc.getEncounterDatetime());
			kpTypeObs.setPerson(patient);
			kpTypeObs.setValueCoded(kpType);
			enc.addObs(kpTypeObs);
		}
		
		try {
			encounterService.saveEncounter(enc);
			return SimpleObject.create("status", "Success", "message", "Identifier generated successfully");
		}
		catch (Exception e) {
			return SimpleObject.create("status", "Error", "message", "There was an error generating identifier details");
		}
	}
	
}
