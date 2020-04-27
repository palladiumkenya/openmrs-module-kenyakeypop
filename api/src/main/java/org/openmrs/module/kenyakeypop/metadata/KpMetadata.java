/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.metadata;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.encounterType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.form;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.patientIdentifierType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.program;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.relationshipType;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.personAttributeType;

/**
 * KP metadata bundle
 */
@Component
public class KpMetadata extends AbstractMetadataBundle {
	
	public static String kp_concept = "bf850dd4-309b-4cbd-9470-9d8110ea5550";
	
	public static final class _EncounterType {
		
		public static final String KP_ALCOHOL_SCREENING = "a3ce2705-d72d-458a-a76c-dae0f93398e7";
		
		public static final String KP_APPOINTMENT_CREATION = "66609dee-3438-11e9-b210-d663bd873d93";
		
		public static final String KP_CLIENT_ENROLLMENT = "c7f47a56-207b-11e9-ab14-d663bd873d93";
		
		public static final String KP_CLIENT_TRACING = "ce841b19-0acd-46fd-b223-2ca9b5356237";
		
		public static final String KP_CLINICAL_VISIT_FORM = "92e03f22-9686-11e9-bc42-526af7764f64";
		
		public static final String KP_CONTACT = "ea68aad6-4655-4dc5-80f2-780e33055a9e";
		
		public static final String KP_DEPRESSION_SCREENING = "84220f19-9071-4745-9045-3b2f8d3dc128";
		
		public static final String KP_DIAGNOSIS = "119217a4-06d6-11ea-8d71-362b9e155667";
		
		public static final String KP_DIAGNOSIS_TREATMENT = "928ea6b2-3425-4ee9-854d-daa5ceaade03";
		
		public static final String KP_CLIENT_DISCONTINUATION = "d7142400-2495-11e9-ab14-d663bd873d93";
		
		public static final String KP_OVERDOSE_SCREENING = "c3fb7831-f8fc-4b71-bd54-f23cdd77e305";
		
		public static final String KP_PEER_OVERDOSE_REPORTING = "383974fe-58ef-488f-bdff-8962f4dd7518";
		
		public static final String KP_CLIENT_HIV_STATUS = "999792ec-8854-11e9-bc42-526af7764f64";
		
		public static final String KP_PEER_CALENDAR = "c4f9db39-2c18-49a6-bf9b-b243d673c64d";
		
		public static final String KP_HCW_OVERDOSE_REPORTING = "bd64b3b0-7bc9-4541-a813-8a917f623e2e";
		
		public static final String KP_REFERRAL = "596f878f-5adf-4f8e-8829-6a87aaeda9a3";
		
		public static final String KP_STI_TREATMENT = "83610d13-d4fc-42c3-8c1d-a403cd6dd073";
		
		public static final String KP_STI_DETAILED_TREATMENT = "2cc8c535-bbfa-4668-98c7-b12e3550ee7b";
		
		public static final String KP_VIOLENCE_SCREENING = "7b69daf5-b567-4384-9d29-f020c408d613";
		
		public static final String KP_TREATMENT_VERIFICATION = "a70a1056-75b3-11ea-bc55-0242ac130003";
		
		public static final String KP_PREP_TREATMENT_VERIFICATION = "5c64e368-7fdc-11ea-bc55-0242ac130003";
		
		public static final String KP_GENDER_BASED_VIOLENCE = "94eebf1a-83a1-11ea-bc55-0242ac130003";
		
	}
	
	public static final class _Form {
		
		public static final String KP_ALCOHOL_SCREENING_FORM = "7ba743c8-d8e6-44ad-aeed-8d2ff9e985db";
		
		public static final String KP_APPOINTMENT_CREATION_FORM = "7587529e-9d84-4947-953e-afe5643cc816";
		
		public static final String KP_CLIENT_ENROLLMENT = "c7f47cea-207b-11e9-ab14-d663bd873d93";
		
		public static final String KP_CLIENT_TRACING_FORM = "63917c60-3fea-11e9-b210-d663bd873d93";
		
		public static final String KP_CLINICAL_VISIT_FORM = "92e041ac-9686-11e9-bc42-526af7764f64";
		
		public static final String KP_CONTACT_FORM = "185dec84-df6f-4fc7-a370-15aa8be531ec";
		
		public static final String KP_DEPRESSION_SCREENING_FORM = "5fe533ee-0c40-4a1f-a071-dc4d0fbb0c17";
		
		public static final String KP_DIAGNOSIS_FORM = "119214e8-06d6-11ea-8d71-362b9e155667";
		
		public static final String KP_DIAGNOSIS_TREATMENT_FORM = "30905fa8-5a35-4d11-a7a3-8e1016b8dc8f";
		
		public static final String KP_CLIENT_DISCONTINUATION = "1f76643e-2495-11e9-ab14-d663bd873d93";
		
		public static final String KP_PEER_CALENDAR_FORM = "7492cffe-5874-4144-a1e6-c9e455472a35";
		
		public static final String KP_PEER_OVERDOSE_REPORTING_FORM = "92fd9c5a-c84a-483b-8d78-d4d7a600db30";
		
		public static final String KP_HCW_OVERDOSE_REPORTING_FORM = "d753bab3-0bbb-43f5-9796-5e95a5d641f3";
		
		public static final String KP_STI_TREATMENT_FORM = "318ad7be-e4da-481f-bcdd-0368cb7601c8";
		
		public static final String KP_VIOLENCE_SCREENING_FORM = "10cd2ca0-8d25-4876-b97c-b568a912957e";
		
		public static final String KP_REFERRAL_FORM = "bd12f98a-fcfe-4472-a858-17f28457932b";
		
		public static final String KP_TREATMENT_VERIFICATION_FORM = "a70a1132-75b3-11ea-bc55-0242ac130003";
		
		public static final String KP_PREP_TREATMENT_VERIFICATION_FORM = "5c64e61a-7fdc-11ea-bc55-0242ac130003";
		
		public static final String KP_GENDER_BASED_VIOLENCE_FORM = "94eec122-83a1-11ea-bc55-0242ac130003";
		
	}
	
	public static final class _PatientIdentifierType {
		
		public static final String KP_UNIQUE_PATIENT_NUMBER = "b7bfefd0-239b-11e9-ab14-d663bd873d93";
		
	}
	
	public static final class _PersonAttributeType {
		
		public static final String KP_CLIENT_ALIAS = "572d667b-ec71-4041-8add-ad8d1fe97af1";
	}
	
	public static final class _RelationshipType {
		
		public static final String PEER_EDUCATOR = "96adecc2-e7cd-41d0-b577-08eb4834abcb";
	}
	
	public static final class _Program {
		
		public static final String KEY_POPULATION = "7447305a-18a7-11e9-ab14-d663bd873d93";
	}
	
	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		install(encounterType("KP Enrollment", "Handles KP client enrollment",
		    KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT));
		install(encounterType("KP Discontinuation", "Records information about KP client discontinuation",
		    KpMetadata._EncounterType.KP_CLIENT_DISCONTINUATION));
		install(encounterType("KP Tracing", "Records information about KP tracing",
		    KpMetadata._EncounterType.KP_CLIENT_TRACING));
		install(encounterType("KP HIV Status", "Records information about KP Hiv Status",
		    KpMetadata._EncounterType.KP_CLIENT_HIV_STATUS));
		
		install(encounterType("KP Alcohol screening", "Handles KP Alcohol screening",
		    KpMetadata._EncounterType.KP_ALCOHOL_SCREENING));
		install(encounterType("KP Appointment creation", "Handles KP Appointment creation",
		    KpMetadata._EncounterType.KP_APPOINTMENT_CREATION));
		
		install(encounterType("KP Diagnosis and Treatment", "Handles KP Diagnosis and treatment plan",
		    KpMetadata._EncounterType.KP_DIAGNOSIS_TREATMENT));
		
		install(encounterType("KP Peer Overdose screening", "Records KP Peer Overdose screening",
		    KpMetadata._EncounterType.KP_OVERDOSE_SCREENING));
		
		install(encounterType("KP Peer Overdose reporting", "Records KP Peer Overdose reporting",
		    KpMetadata._EncounterType.KP_PEER_OVERDOSE_REPORTING));
		
		install(encounterType("KP STI treatment", "Records KP STI treatment", KpMetadata._EncounterType.KP_STI_TREATMENT));
		install(encounterType("KP STI Detailed treatment", "Records KP STI Detailed treatment",
		    KpMetadata._EncounterType.KP_STI_DETAILED_TREATMENT));
		
		install(encounterType("KP Violence screening", "Records KP violence screening",
		    KpMetadata._EncounterType.KP_VIOLENCE_SCREENING));
		
		install(encounterType("KP Depression screening", "Records KP depression screening",
		    KpMetadata._EncounterType.KP_DEPRESSION_SCREENING));
		install(encounterType("KP HCW Overdose reporting", "Records KP HCW Overdose reporting",
		    KpMetadata._EncounterType.KP_HCW_OVERDOSE_REPORTING));
		install(encounterType("KP Contact", "Records extra registration details for kp",
		    KpMetadata._EncounterType.KP_CONTACT));
		install(encounterType("KP Referral", "Records KP Referral", KpMetadata._EncounterType.KP_REFERRAL));
		install(encounterType("KP Clinic visit form", "Records KP Referral",
		    KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM));
		install(encounterType("KP Peer Calendar", "Records KP Peer Calendar", KpMetadata._EncounterType.KP_PEER_CALENDAR));
		install(encounterType("KP Diagnosis", "Records KP Diagnosis", KpMetadata._EncounterType.KP_DIAGNOSIS));
		install(encounterType("KP kpTreatmentVerification", "Treatment Verification",
		    KpMetadata._EncounterType.KP_TREATMENT_VERIFICATION));
		install(encounterType("KP PrEP Treatment Verification", "Prep Treatment Verification",
		    KpMetadata._EncounterType.KP_PREP_TREATMENT_VERIFICATION));
		install(encounterType("KP Gender Based Violence", "Gender based violence", _EncounterType.KP_GENDER_BASED_VIOLENCE));
		
		// Installing forms
		
		install(form("Clinical Enrollment", null, KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT, "1",
		    KpMetadata._Form.KP_CLIENT_ENROLLMENT));
		install(form("Discontinuation", null, KpMetadata._EncounterType.KP_CLIENT_DISCONTINUATION, "1",
		    KpMetadata._Form.KP_CLIENT_DISCONTINUATION));
		install(form("Peer Tracking Form", null, KpMetadata._EncounterType.KP_CLIENT_TRACING, "1",
		    KpMetadata._Form.KP_CLIENT_TRACING_FORM));
		
		install(form("Alcohol Abuse Screening Tool", null, KpMetadata._EncounterType.KP_ALCOHOL_SCREENING, "1",
		    KpMetadata._Form.KP_ALCOHOL_SCREENING_FORM));
		install(form("Appointment Creation", null, KpMetadata._EncounterType.KP_APPOINTMENT_CREATION, "1",
		    KpMetadata._Form.KP_APPOINTMENT_CREATION_FORM));
		
		install(form("Diagnosis and Treatment Plan", null, KpMetadata._EncounterType.KP_DIAGNOSIS_TREATMENT, "1",
		    KpMetadata._Form.KP_DIAGNOSIS_TREATMENT_FORM));
		
		install(form("STI Treatment", "Form for adding STI treatment details",
		    KpMetadata._EncounterType.KP_STI_DETAILED_TREATMENT, "1", KpMetadata._Form.KP_STI_TREATMENT_FORM));
		
		install(form("Violence Reporting Form", "Violence Reporting tool", KpMetadata._EncounterType.KP_VIOLENCE_SCREENING,
		    "1", KpMetadata._Form.KP_VIOLENCE_SCREENING_FORM));
		
		install(form("Depression Screening PHQ-9", null, KpMetadata._EncounterType.KP_DEPRESSION_SCREENING, "1",
		    KpMetadata._Form.KP_DEPRESSION_SCREENING_FORM));
		
		install(form("Peer Overdose Reporting Tool", "Peer Overdose Reporting Tool",
		    KpMetadata._EncounterType.KP_PEER_OVERDOSE_REPORTING, "1", KpMetadata._Form.KP_PEER_OVERDOSE_REPORTING_FORM));
		
		install(form("HCW Overdose Reporting Tool", "HCW Overdose Reporting Tool",
		    KpMetadata._EncounterType.KP_HCW_OVERDOSE_REPORTING, "1", KpMetadata._Form.KP_HCW_OVERDOSE_REPORTING_FORM));
		
		install(form("Contact form", null, KpMetadata._EncounterType.KP_CONTACT, "1", KpMetadata._Form.KP_CONTACT_FORM));
		
		install(form("Clinic visit form", "Form for adding referrals", KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM,
		    "1", KpMetadata._Form.KP_CLINICAL_VISIT_FORM));
		install(form("Peer Calendar", "Form for updating peer calendar", KpMetadata._EncounterType.KP_PEER_CALENDAR, "1",
		    KpMetadata._Form.KP_PEER_CALENDAR_FORM));
		install(form("KP Diagnosis", "Form for updating diagnosis", KpMetadata._EncounterType.KP_DIAGNOSIS, "1",
		    KpMetadata._Form.KP_DIAGNOSIS_FORM));
		
		install(form("Referral", "Form for adding referrals", KpMetadata._EncounterType.KP_REFERRAL, "1",
		    KpMetadata._Form.KP_REFERRAL_FORM));
		
		install(form("KP Treatment Verification", "treatment verification form",
		    KpMetadata._EncounterType.KP_TREATMENT_VERIFICATION, "1", _Form.KP_TREATMENT_VERIFICATION_FORM));
		
		install(form("PrEP Treatment Verification", "prep treatment verification form",
		    KpMetadata._EncounterType.KP_PREP_TREATMENT_VERIFICATION, "1", _Form.KP_PREP_TREATMENT_VERIFICATION_FORM));
		
		install(form("Gender Based Violence", "Gender Based Violence form", _EncounterType.KP_GENDER_BASED_VIOLENCE, "1",
		    _Form.KP_GENDER_BASED_VIOLENCE_FORM));
		
		install(relationshipType("Peer-educator", "Peer", "One that follows up peers",
		    KpMetadata._RelationshipType.PEER_EDUCATOR));
		
		install(patientIdentifierType("KP unique Number", "Unique Number assigned to KP client upon enrollment", null, null,
		    null, PatientIdentifierType.LocationBehavior.NOT_USED, false,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER));
		
		//Installing person attributes
		install(personAttributeType("KP Client Alias", "KP client Alias name", String.class, null, true, 4.3,
		    _PersonAttributeType.KP_CLIENT_ALIAS));
		
		install(program("Key Population", "Treatment for Key Population clients", kp_concept, _Program.KEY_POPULATION));
	}
}
