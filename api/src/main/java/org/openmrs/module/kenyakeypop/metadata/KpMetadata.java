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
import org.openmrs.module.kenyaemr.metadata.KpMetadata;
import org.openmrs.module.kenyaemr.metadata.KpMetadata;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.*;

/**
 * KP metadata bundle
 */
@Component
public class KpMetadata extends AbstractMetadataBundle {


	public static String kp_concept = "bf850dd4-309b-4cbd-9470-9d8110ea5550";

	public static final class _EncounterType {
		public static final String KP_CLIENT_ENROLLMENT = "c7f47a56-207b-11e9-ab14-d663bd873d93";
		public static final String KP_CLIENT_DISCONTINUATION = "d7142400-2495-11e9-ab14-d663bd873d93";
		public static final String KP_CLIENT_TRACING = "ce841b19-0acd-46fd-b223-2ca9b5356237";
		public static final String KP_CLIENT_HIV_STATUS = "999792ec-8854-11e9-bc42-526af7764f64";

		public static final String KP_ABSCESS_SCREENING = "ffbf3e1a-2e6e-4ee2-9e31-2288e88f2e0b";
		public static final String KP_ALCOHOL_SCREENING = "a3ce2705-d72d-458a-a76c-dae0f93398e7";
		public static final String KP_APPOINTMENT_CREATION = "66609dee-3438-11e9-b210-d663bd873d93";
		public static final String KP_CHRONIC_ILLNESS = "26bb869b-b569-4acd-b455-02c853e9f1e6";
		public static final String KP_CLINICAL_NOTES = "bcbf6e3f-a2fc-421b-90a3-473a3158c796";
		public static final String KP_COMPLAINTS = "2c3cf276-3676-11e9-b210-d663bd873d93";
		public static final String KP_CURRENT_MEDICATION = "89767cca-13b9-411a-aa59-98656b096fd9";
		public static final String KP_DIAGNOSIS_TREATMENT = "928ea6b2-3425-4ee9-854d-daa5ceaade03";
		public static final String KP_DRUG_REACTIONS = "d7cfa460-2944-11e9-b210-d663bd873d93";
		public static final String KP_HEPATITIS_SCREENING = "5c05a229-51b4-4b73-be13-0d93765a2a96";
		public static final String KP_IMMUNIZATION_SCREENING = "9b8c17cc-3420-11e9-b210-d663bd873d93";
		public static final String KP_ALLERGIES_SCREENING = "119362fb-6af6-4462-9fb2-7a09c43c9874";
		public static final String KP_COUNSELLING_SERVICES = "28883f27-dfd1-4ce5-89f0-2a4f87974d15";
		public static final String KP_OVERDOSE_SCREENING = "c3fb7831-f8fc-4b71-bd54-f23cdd77e305";
		public static final String KP_PREP_PEP_SCREENING = "b06625d4-dfe4-458c-93fa-e878c8370733";
		public static final String KP_PREGNANCY_AND_FP_SCREENING = "55d0b03e-8977-4d3e-8941-3333712b1afe";
		public static final String KP_RISK_REDUCION_SCREENING = "dd06ac5c-70b4-4d8e-96a3-05edc9019f8f";
		public static final String KP_STI_TREATMENT = "83610d13-d4fc-42c3-8c1d-a403cd6dd073";
		public static final String KP_STI_DETAILED_TREATMENT = "2cc8c535-bbfa-4668-98c7-b12e3550ee7b";
		public static final String KP_TB_SCREENING = "32e5ac6f-80cf-4908-aa88-200e3e199c68";
		public static final String KP_SYSTEMS_EXAMINATION = "5568ab72-e951-4683-875e-c5781b6f7b81";
		public static final String KP_VIOLENCE_SCREENING = "7b69daf5-b567-4384-9d29-f020c408d613";
		public static final String KP_PSYCHOSOCIAL_SCREENING = "981c1420-4e83-4656-beb1-2461c45de532";
		public static final String KP_DEPRESSION_SCREENING = "84220f19-9071-4745-9045-3b2f8d3dc128";
		public static final String KP_PEER_OVERDOSE_REPORTING = "383974fe-58ef-488f-bdff-8962f4dd7518";
		public static final String KP_HCW_OVERDOSE_REPORTING = "bd64b3b0-7bc9-4541-a813-8a917f623e2e";
		public static final String KP_CONTACT = "ea68aad6-4655-4dc5-80f2-780e33055a9e";
		public static final String KP_HEALTH_EDUCATION = "65c15b13-a795-4400-8791-159309b1c3bb";
		public static final String KP_REFERRAL = "596f878f-5adf-4f8e-8829-6a87aaeda9a3";
		public static final String KP_CLINICAL_VISIT_FORM = "92e03f22-9686-11e9-bc42-526af7764f64";
		public static final String KP_PEER_CALENDAR = "c4f9db39-2c18-49a6-bf9b-b243d673c64d";
		public static final String KP_DIAGNOSIS  = "119217a4-06d6-11ea-8d71-362b9e155667";

	}

	public static final class _Form {
		public static final String KP_CLIENT_ENROLLMENT = "c7f47cea-207b-11e9-ab14-d663bd873d93";
		public static final String KP_CLIENT_DISCONTINUATION = "1f76643e-2495-11e9-ab14-d663bd873d93";
		public static final String KP_CLIENT_TRACING_FORM = "63917c60-3fea-11e9-b210-d663bd873d93";
		public static final String KP_CLIENT_HIV_STATUS_FORM = "99979576-8854-11e9-bc42-526af7764f64";

		public static final String KP_ABSCESS_SCREENING_FORM = "721c1c74-f54d-447a-9b10-66bf364eec04";
		public static final String KP_ALCOHOL_SCREENING_FORM = "7ba743c8-d8e6-44ad-aeed-8d2ff9e985db";
		public static final String KP_APPOINTMENT_CREATION_FORM = "7587529e-9d84-4947-953e-afe5643cc816";
		public static final String KP_CHRONIC_ILLNESS_FORM = "458a1a0a-fb8e-4a37-a836-d47e63673b60";
		public static final String KP_CLINICAL_NOTES_FORM = "7167549f-196d-4b7c-81d4-bbd11df704b1";
		public static final String KP_COMPLAINTS_FORM = "da8a2442-5602-4bcd-986c-963f2a00bdcc";
		public static final String KP_CURRENT_MEDICATION_FORM = "c18987d9-1a7a-4fbf-96d9-7b9b62ec26e0";
		public static final String KP_DIAGNOSIS_TREATMENT_FORM = "30905fa8-5a35-4d11-a7a3-8e1016b8dc8f";
		public static final String KP_DRUG_REACTIONS_FORM = "4464390d-025d-47bd-9619-64cb1d89a1da";
		public static final String KP_HEPATITIS_SCREENING_FORM = "c0151dfc-6097-4eb1-8226-484303dcdc88";
		public static final String KP_IMMUNIZATION_SCREENING_FORM = "2c2f1dea-1715-442e-9abe-71bc89ace1af";
		public static final String KP_ALLERGIES_SCREENING_FORM = "cdad5adb-e352-4ecf-882d-b76b71be9c9d";
		public static final String KP_COUNSELLING_SERVICES_FORM = "f78165e6-d2df-40ec-a0dd-2d63ef1cfc59";
		public static final String KP_OVERDOSE_SCREENING_FORM = "9ce5b2d6-7c22-4fa8-8cca-edaad872c467";
		public static final String KP_PREP_PEP_SCREENING_FORM = "a455aef4-56c8-4386-902e-b599c572f33f";
		public static final String KP_PREGNANCY_AND_FP_SCREENING_FORM = "c534e64c-698f-47a9-b551-56e59ea5c6a0";
		public static final String KP_RISK_REDUCION_SCREENING_FORM = "01d46fa8-a648-461c-a66f-5e8a125c8a54";
		public static final String KP_STI_SCREENING_FORM = "d80d1c52-6a79-4c3d-b322-63eead834089";
		public static final String KP_STI_TREATMENT_FORM = "318ad7be-e4da-481f-bcdd-0368cb7601c8";
		public static final String KP_TB_SCREENING_FORM = "7719e79e-2e39-409c-8190-4b54b4a76cca";
		public static final String KP_SYSTEMS_EXAMINATION_FORM = "20cf6054-a119-4168-a49a-8ce09830899d";
		public static final String KP_VIOLENCE_SCREENING_FORM = "10cd2ca0-8d25-4876-b97c-b568a912957e";
		public static final String KP_PSYCHOSOCIAL_SCREENING_FORM = "74acd5bb-1565-4261-a642-2bbc0d847e08";
		public static final String KP_DEPRESSION_SCREENING_FORM = "5fe533ee-0c40-4a1f-a071-dc4d0fbb0c17";
		public static final String KP_PEER_OVERDOSE_REPORTING_FORM = "92fd9c5a-c84a-483b-8d78-d4d7a600db30";
		public static final String KP_HCW_OVERDOSE_REPORTING_FORM = "d753bab3-0bbb-43f5-9796-5e95a5d641f3";
		public static final String KP_CONTACT_FORM = "185dec84-df6f-4fc7-a370-15aa8be531ec";
		public static final String KP_HEALTH_EDUCATION_FORM = "67a224a5-907c-49e0-a1ea-e7c38b7a489d";
		public static final String KP_REFERRAL_FORM = "bd12f98a-fcfe-4472-a858-17f28457932b";
		public static final String KP_CLINICAL_VISIT_FORM = "92e041ac-9686-11e9-bc42-526af7764f64";
		public static final String KP_PEER_CALENDAR_FORM = "7492cffe-5874-4144-a1e6-c9e455472a35";
		public static final String KP_DIAGNOSIS_FORM = "119214e8-06d6-11ea-8d71-362b9e155667";


	}

	public static final class _PatientIdentifierType {
		public static final String KP_UNIQUE_PATIENT_NUMBER = "b7bfefd0-239b-11e9-ab14-d663bd873d93";


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
		install(encounterType("KP Enrollment", "Handles KP client enrollment", KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT));
		install(encounterType("KP Discontinuation", "Records information about KP client discontinuation", KpMetadata._EncounterType.KP_CLIENT_DISCONTINUATION));
		install(encounterType("KP Tracing", "Records information about KP tracing", KpMetadata._EncounterType.KP_CLIENT_TRACING));
		install(encounterType("KP HIV Status", "Records information about KP Hiv Status", KpMetadata._EncounterType.KP_CLIENT_HIV_STATUS));

		install(encounterType("KP Abscess screening", "Handles KP Abscess screening", KpMetadata._EncounterType.KP_ABSCESS_SCREENING));
		install(encounterType("KP Alcohol screening", "Handles KP Alcohol screening", KpMetadata._EncounterType.KP_ALCOHOL_SCREENING));
		install(encounterType("KP Appointment creation", "Handles KP Appointment creation", KpMetadata._EncounterType.KP_APPOINTMENT_CREATION));
		install(encounterType("KP Chronic Illness Screening", "Handles KP Chronic Illness", KpMetadata._EncounterType.KP_CHRONIC_ILLNESS));
		install(encounterType("KP Clinical notes", "Handles KP clinical notes", KpMetadata._EncounterType.KP_CLINICAL_NOTES));
		install(encounterType("KP Complaints", "Handles KP complaints", KpMetadata._EncounterType.KP_COMPLAINTS));
		install(encounterType("KP Current medication", "Handles KP current medication", KpMetadata._EncounterType.KP_CURRENT_MEDICATION));
		install(encounterType("KP Diagnosis and Treatment", "Handles KP Diagnosis and treatment plan", KpMetadata._EncounterType.KP_DIAGNOSIS_TREATMENT));
		install(encounterType("KP Adverse drug reactions", "Handles KP Adverse drug reactions", KpMetadata._EncounterType.KP_DRUG_REACTIONS));
		install(encounterType("KP Hepatitis screening", "Handles KP Hepatits B screening", KpMetadata._EncounterType.KP_HEPATITIS_SCREENING));
		install(encounterType("KP Immunization screening", "Handles KP Immunization screening", KpMetadata._EncounterType.KP_IMMUNIZATION_SCREENING));
		install(encounterType("KP Allergies screening", "Handles KP Allergies screening", KpMetadata._EncounterType.KP_ALLERGIES_SCREENING));
		install(encounterType("KP Counselling services", "Handles KP Counselling Services", KpMetadata._EncounterType.KP_COUNSELLING_SERVICES));
		install(encounterType("KP Peer Overdose screening", "Handles KP Peer Overdose screening", KpMetadata._EncounterType.KP_OVERDOSE_SCREENING));
		install(encounterType("KP Prophylaxis screening", "Handles KP Prophylaxis screening", KpMetadata._EncounterType.KP_PREP_PEP_SCREENING));
		install(encounterType("KP Pregnancy and FP screening", "Handles KP Pregnancy and FP screening", KpMetadata._EncounterType.KP_PREGNANCY_AND_FP_SCREENING));
		install(encounterType("KP Risk reduction screening", "Handles KP Risk reduction screening", KpMetadata._EncounterType.KP_RISK_REDUCION_SCREENING));
		install(encounterType("KP STI treatment", "Handles KP STI treatment", KpMetadata._EncounterType.KP_STI_TREATMENT));
		install(encounterType("KP STI Detailed treatment", "Handles KP STI Detailed treatment", KpMetadata._EncounterType.KP_STI_DETAILED_TREATMENT));
		install(encounterType("KP TB screening", "Handles KP TB screening", KpMetadata._EncounterType.KP_TB_SCREENING));
		install(encounterType("KP Systems examinations", "Handles KP systems examination", KpMetadata._EncounterType.KP_SYSTEMS_EXAMINATION));
		install(encounterType("KP Violence screening", "Handles KP violence screening", KpMetadata._EncounterType.KP_VIOLENCE_SCREENING));
		install(encounterType("KP Alcohol,drugs and risk reduction screening", "Handles KP alcohol,drugs and risk reduction screening", KpMetadata._EncounterType.KP_PSYCHOSOCIAL_SCREENING));
		install(encounterType("KP Depression screening", "Handles KP depression screening", KpMetadata._EncounterType.KP_DEPRESSION_SCREENING));
		install(encounterType("KP Peer Overdose reporting", "Handles KP Peer Overdose reporting", KpMetadata._EncounterType.KP_PEER_OVERDOSE_REPORTING));
		install(encounterType("KP HCW Overdose reporting", "Handles KP HCW Overdose reporting", KpMetadata._EncounterType.KP_HCW_OVERDOSE_REPORTING));
		install(encounterType("KP Contact", "Handles extra registration details for kp", KpMetadata._EncounterType.KP_CONTACT));
		install(encounterType("KP Health Education", "Handles KP Health Education", KpMetadata._EncounterType.KP_HEALTH_EDUCATION));
		install(encounterType("KP Referral", "Handles KP Referral", KpMetadata._EncounterType.KP_REFERRAL));
		install(encounterType("KP Clinic visit form", "Handles KP Referral", KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM));
		install(encounterType("KP Peer Calendar", "Handles KP Peer Calendar", KpMetadata._EncounterType.KP_PEER_CALENDAR));
		install(encounterType("KP Diagnosis", "Handles KP Diagnosis", KpMetadata._EncounterType.KP_DIAGNOSIS));

		// Installing forms

		install(form("KP Enrollment", null, KpMetadata._EncounterType.KP_CLIENT_ENROLLMENT, "1", KpMetadata._Form.KP_CLIENT_ENROLLMENT));
		install(form("Discontinuation", null, KpMetadata._EncounterType.KP_CLIENT_DISCONTINUATION, "1", KpMetadata._Form.KP_CLIENT_DISCONTINUATION));
		install(form("Follow Up Tracking Form", null, KpMetadata._EncounterType.KP_CLIENT_TRACING, "1", KpMetadata._Form.KP_CLIENT_TRACING_FORM));
		install(form("HIV Status", null, KpMetadata._EncounterType.KP_CLIENT_HIV_STATUS, "1", KpMetadata._Form.KP_CLIENT_HIV_STATUS_FORM));

		install(form("Abscess Screening", null, KpMetadata._EncounterType.KP_ABSCESS_SCREENING, "1", KpMetadata._Form.KP_ABSCESS_SCREENING_FORM));
		install(form("Alcohol Abuse Screening Tool", null, KpMetadata._EncounterType.KP_ALCOHOL_SCREENING, "1", KpMetadata._Form.KP_ALCOHOL_SCREENING_FORM));
		install(form("Appointment Creation", null, KpMetadata._EncounterType.KP_APPOINTMENT_CREATION, "1", KpMetadata._Form.KP_APPOINTMENT_CREATION_FORM));
		install(form("Chronic Illness", null, KpMetadata._EncounterType.KP_CHRONIC_ILLNESS, "1", KpMetadata._Form.KP_CHRONIC_ILLNESS_FORM));
		install(form("Clinical Notes", null, KpMetadata._EncounterType.KP_CLINICAL_NOTES, "1", KpMetadata._Form.KP_CLINICAL_NOTES_FORM));
		install(form("Complaints", null, KpMetadata._EncounterType.KP_COMPLAINTS, "1", KpMetadata._Form.KP_COMPLAINTS_FORM));
		install(form("Current Medication Form", null, KpMetadata._EncounterType.KP_CURRENT_MEDICATION, "1", KpMetadata._Form.KP_CURRENT_MEDICATION_FORM));
		install(form("Diagnosis and Treatment Plan", null, KpMetadata._EncounterType.KP_DIAGNOSIS_TREATMENT, "1", KpMetadata._Form.KP_DIAGNOSIS_TREATMENT_FORM));
		install(form("Adverse Drug Reactions", null, KpMetadata._EncounterType.KP_DRUG_REACTIONS, "1", KpMetadata._Form.KP_DRUG_REACTIONS_FORM));
		install(form("Hepatitis Screening", null, KpMetadata._EncounterType.KP_HEPATITIS_SCREENING, "1", KpMetadata._Form.KP_HEPATITIS_SCREENING_FORM));
		install(form("Immunization Screening", null, KpMetadata._EncounterType.KP_IMMUNIZATION_SCREENING, "1", KpMetadata._Form.KP_IMMUNIZATION_SCREENING_FORM));
		install(form("Allergies Screening", null, KpMetadata._EncounterType.KP_ALLERGIES_SCREENING, "1", KpMetadata._Form.KP_ALLERGIES_SCREENING_FORM));
		install(form("Counselling Services", null, KpMetadata._EncounterType.KP_COUNSELLING_SERVICES, "1", KpMetadata._Form.KP_COUNSELLING_SERVICES_FORM));
		install(form("Drug Overdose Screening", null, KpMetadata._EncounterType.KP_OVERDOSE_SCREENING, "1", KpMetadata._Form.KP_OVERDOSE_SCREENING_FORM));
		install(form("Prep/Pep Services", null, KpMetadata._EncounterType.KP_PREP_PEP_SCREENING, "1", KpMetadata._Form.KP_PREP_PEP_SCREENING_FORM));
		install(form("Pregnancy, FP and CaCx Screening", null, KpMetadata._EncounterType.KP_PREGNANCY_AND_FP_SCREENING, "1", KpMetadata._Form.KP_PREGNANCY_AND_FP_SCREENING_FORM));
		install(form("Risk Reduction Screening", null, KpMetadata._EncounterType.KP_RISK_REDUCION_SCREENING, "1", KpMetadata._Form.KP_RISK_REDUCION_SCREENING_FORM));
		install(form("STI Screening", null, KpMetadata._EncounterType.KP_STI_TREATMENT, "1", KpMetadata._Form.KP_STI_SCREENING_FORM));
		install(form("STI Treatment", "Form for adding STI treatment details", KpMetadata._EncounterType.KP_STI_DETAILED_TREATMENT, "1", KpMetadata._Form.KP_STI_TREATMENT_FORM));
		install(form("TB screening", null, KpMetadata._EncounterType.KP_TB_SCREENING, "1", KpMetadata._Form.KP_TB_SCREENING_FORM));
		install(form("Review of Body systems", null, KpMetadata._EncounterType.KP_SYSTEMS_EXAMINATION, "1", KpMetadata._Form.KP_SYSTEMS_EXAMINATION_FORM));
		install(form("Violence Reporting Form", "Violence Reporting tool", KpMetadata._EncounterType.KP_VIOLENCE_SCREENING, "1", KpMetadata._Form.KP_VIOLENCE_SCREENING_FORM));
		install(form("Alcohol, Drugs and Risk Reduction Screening", null, KpMetadata._EncounterType.KP_PSYCHOSOCIAL_SCREENING, "1", KpMetadata._Form.KP_PSYCHOSOCIAL_SCREENING_FORM));
		install(form("Depression Screening PHQ-9", null, KpMetadata._EncounterType.KP_DEPRESSION_SCREENING, "1", KpMetadata._Form.KP_DEPRESSION_SCREENING_FORM));
		install(form("Peer Overdose Reporting Tool", "Peer Overdose Reporting Tool", KpMetadata._EncounterType.KP_PEER_OVERDOSE_REPORTING, "1", KpMetadata._Form.KP_PEER_OVERDOSE_REPORTING_FORM));
		install(form("HCW Overdose Reporting Tool", "HCW Overdose Reporting Tool", KpMetadata._EncounterType.KP_HCW_OVERDOSE_REPORTING, "1", KpMetadata._Form.KP_HCW_OVERDOSE_REPORTING_FORM));
		install(form("Contact form", null, KpMetadata._EncounterType.KP_CONTACT, "1", KpMetadata._Form.KP_CONTACT_FORM));
		install(form("Peer Education", "Form for adding Health education", KpMetadata._EncounterType.KP_HEALTH_EDUCATION, "1", KpMetadata._Form.KP_HEALTH_EDUCATION_FORM));
		install(form("Referral", "Form for adding referrals", KpMetadata._EncounterType.KP_REFERRAL, "1", KpMetadata._Form.KP_REFERRAL_FORM));
		install(form("Clinic visit form", "Form for adding referrals", KpMetadata._EncounterType.KP_CLINICAL_VISIT_FORM, "1", KpMetadata._Form.KP_CLINICAL_VISIT_FORM));
		install(form("Peer Calendar", "Form for updating peer calendar", KpMetadata._EncounterType.KP_PEER_CALENDAR, "1", KpMetadata._Form.KP_PEER_CALENDAR_FORM));
		install(form("KP Diagnosis", "Form for updating diagnosis", KpMetadata._EncounterType.KP_DIAGNOSIS, "1", KpMetadata._Form.KP_DIAGNOSIS_FORM));

		install(relationshipType("Peer-educator", "Peer", "One that follows up peers", KpMetadata._RelationshipType.PEER_EDUCATOR));


		install(patientIdentifierType("KP unique Number", "Unique Number assigned to KP client upon enrollment",
				null, null, null,
				PatientIdentifierType.LocationBehavior.NOT_USED, false, KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER));


		install(program("Key Population", "Treatment for Key Population clients", kp_concept, _Program.KEY_POPULATION));
	}
}