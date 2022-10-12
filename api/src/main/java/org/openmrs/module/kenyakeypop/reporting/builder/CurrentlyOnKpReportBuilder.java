/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.builder;

import org.openmrs.PatientIdentifierType;
import org.openmrs.module.kenyacore.report.HybridReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractHybridReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.CurrentlyOnKpCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.AppointmentDateDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.DateOfEnrollmentDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ActiveKpHIVStatusDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ActiveKpHIVTestDateDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.HotspotDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.KeyPopTypeDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.ActiveKpLastVisitDateDataDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.SubCountyOfImplementationDataDefinition;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.AgeDataDefinition;
import org.openmrs.module.reporting.data.person.definition.BirthdateDataDefinition;
import org.openmrs.module.reporting.data.person.definition.ConvertedPersonDataDefinition;
import org.openmrs.module.reporting.data.person.definition.GenderDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonIdDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PreferredNameDataDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.CurrentlyOnKpLineList" })
public class CurrentlyOnKpReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	@Override
	protected void addColumns(HybridReportDescriptor report, PatientDataSetDefinition dsd) {
	}
	
	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return null;
	}
	
	protected Mapped<CohortDefinition> allClientsCohort() {
		CohortDefinition cd = new CurrentlyOnKpCohortDefinition();
		cd.setName("Clients currently on KP");
		return ReportUtils.map(cd, "");
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		
		PatientDataSetDefinition allVisits = currentlyOnKpDataSetDefinition("currentlyOnKp");
		allVisits.addRowFilter(allClientsCohort());
		DataSetDefinition allClientsDSD = allVisits;
		
		return Arrays.asList(ReportUtils.map(allClientsDSD, ""));
	}
	
	protected PatientDataSetDefinition currentlyOnKpDataSetDefinition(String datasetName) {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition(datasetName);
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        upn.getName(), upn), identifierFormatter);
		
		DataConverter formatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), formatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("DOB", new BirthdateDataDefinition(), "");
		dsd.addColumn("Age", new AgeDataDefinition(), "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "", null);
		dsd.addColumn("UIC number", identifierDef, "");
		dsd.addColumn("Date of Enrollment", new DateOfEnrollmentDataDefinition(), "");
		dsd.addColumn("Key Population Type", new KeyPopTypeDataDefinition(), "");
		dsd.addColumn("Last Visit Date", new ActiveKpLastVisitDateDataDefinition(), "");
		dsd.addColumn("Next appointment Date", new AppointmentDateDataDefinition(), "");
		dsd.addColumn("Subcounty", new SubCountyOfImplementationDataDefinition(), "");
		dsd.addColumn("Hotspot", new HotspotDataDefinition(), "");
		dsd.addColumn("HIV Status", new ActiveKpHIVStatusDataDefinition(), "");
		dsd.addColumn("HIV Test Date", new ActiveKpHIVTestDateDataDefinition(), "");
		
		return dsd;
	}
}
