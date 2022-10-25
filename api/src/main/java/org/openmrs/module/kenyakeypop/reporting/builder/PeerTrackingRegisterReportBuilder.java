/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
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
import org.openmrs.module.kenyakeypop.reporting.cohort.definition.PeerTrackingRegisterCohortDefinition;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.*;
import org.openmrs.module.kenyakeypop.reporting.data.converter.definition.kp.peerTrackingRegister.*;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.common.SortCriteria;
import org.openmrs.module.reporting.data.DataDefinition;
import org.openmrs.module.reporting.data.converter.DataConverter;
import org.openmrs.module.reporting.data.converter.DateConverter;
import org.openmrs.module.reporting.data.converter.ObjectFormatter;
import org.openmrs.module.reporting.data.patient.definition.ConvertedPatientDataDefinition;
import org.openmrs.module.reporting.data.patient.definition.PatientIdentifierDataDefinition;
import org.openmrs.module.reporting.data.person.definition.*;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.PatientDataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

//import org.openmrs.module.kenyakeypop.metadata.CommonMetadata;
;

@Component
@Builds({ "kenyaemr.kenyakeypop.kenyakeypop.report.cohort.analysis.kpPeerTrackingRegister" })
public class PeerTrackingRegisterReportBuilder extends AbstractHybridReportBuilder {
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	@Override
	protected Mapped<CohortDefinition> buildCohort(HybridReportDescriptor descriptor, PatientDataSetDefinition dsd) {
		return allPeersCohort();
	}
	
	protected Mapped<CohortDefinition> allPeersCohort() {
		CohortDefinition cd = new PeerTrackingRegisterCohortDefinition();
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setName("KP Peer Tracking Register");
		return ReportUtils.map(cd, "startDate=${startDate},endDate=${endDate}");
	}
	
	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor descriptor, ReportDefinition report) {
		
		PatientDataSetDefinition allPeers = kpPeerTrackingDataSetDefinition();
		allPeers.addRowFilter(allPeersCohort());
		//allPatients.addRowFilter(buildCohort(descriptor));
		DataSetDefinition allPatientsDSD = allPeers;
		
		return Arrays.asList(ReportUtils.map(allPatientsDSD, "startDate=${startDate},endDate=${endDate}"));
	}
	
	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}
	
	protected PatientDataSetDefinition kpPeerTrackingDataSetDefinition() {
		
		PatientDataSetDefinition dsd = new PatientDataSetDefinition("PeerTrackingRegister");
		dsd.addSortCriteria("DOBAndAge", SortCriteria.SortDirection.DESC);
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		
		//PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class, CommonMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		PatientIdentifierType upn = MetadataUtils.existing(PatientIdentifierType.class,
		    KpMetadata._PatientIdentifierType.KP_UNIQUE_PATIENT_NUMBER);
		DataConverter identifierFormatter = new ObjectFormatter("{identifier}");
		DataDefinition identifierDef = new ConvertedPatientDataDefinition("identifier", new PatientIdentifierDataDefinition(
		        upn.getName(), upn), identifierFormatter);
		
		DataConverter nameFormatter = new ObjectFormatter("{familyName}, {givenName}");
		DataDefinition nameDef = new ConvertedPersonDataDefinition("name", new PreferredNameDataDefinition(), nameFormatter);
		dsd.addColumn("id", new PersonIdDataDefinition(), "");
		/*dsd.addColumn("Serial Number", new KpSerialNumberDataDefinition(), "");*/
		dsd.addColumn("Unique Identifier Code", identifierDef, "");
		dsd.addColumn("Name", nameDef, "");
		dsd.addColumn("Sex", new GenderDataDefinition(), "");
		dsd.addColumn("Key Population Type", new KeyPopTypeDataDefinition(), "");
		dsd.addColumn("Phone Number", new KpPhoneNumberDataDefinition(), "");
		dsd.addColumn("Hotspot", new HotspotDataDefinition(), "");
		dsd.addColumn("Hotspot Typology", new HotspotTypologyDataDefinition(), "");
		dsd.addColumn("Sub County", new SubCountyOfImplementationDataDefinition(), "");
		dsd.addColumn("Ward", new WardDataDefinition(), "");
		dsd.addColumn("1st Tracing attempt date", new DateOfFirstTraceDataDefinition(), "", new DateConverter(DATE_FORMAT));
		dsd.addColumn("1st Tracing Type", new FirstTracingTypeDataDefinition(), "");
		dsd.addColumn("1st Tracing Outcome", new FirstTracingOutcomeDataDefinition(), "");
		
		dsd.addColumn("2nd Tracing attempt date", new DateOfSecondTraceDataDefinition(), "", new DateConverter(DATE_FORMAT));
		dsd.addColumn("2nd Tracing Type", new SecondTracingTypeDataDefinition(), "");
		dsd.addColumn("2nd Tracing Outcome", new SecondTracingOutcomeDataDefinition(), "");
		
		dsd.addColumn("3rd Tracing attempt date", new DateOfThirdTraceDataDefinition(), "", new DateConverter(DATE_FORMAT));
		dsd.addColumn("3rd Tracing Type", new ThirdTracingTypeDataDefinition(), "");
		dsd.addColumn("3rd Tracing Outcome", new ThirdTracingOutcomeDataDefinition(), "");
		
		dsd.addColumn("Tracing Outcome status", new TracingOutcomeStatusDataDefinition(), "");
		dsd.addColumn("Status in programme", new StatusInProgrammeDataDefinition(), "");
		// dsd.addColumn("Outcome", new OutcomeDataDefinition(), "");
		
		return dsd;
	}
}
