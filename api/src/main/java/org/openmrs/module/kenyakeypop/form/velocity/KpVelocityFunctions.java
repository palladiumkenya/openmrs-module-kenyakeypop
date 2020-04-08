/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.form.velocity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.calculation.result.CalculationResult;
import org.openmrs.module.htmlformentry.FormEntrySession;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyakeypop.calculation.library.kp.KpVelocityCalculation;

/**
 * Velocity functions for adding logic to HTML forms
 */
public class KpVelocityFunctions {
	
	private FormEntrySession session;
	
	protected static final Log log = LogFactory.getLog(KpVelocityFunctions.class);
	
	/**
	 * Constructs a new functions provider
	 * 
	 * @param session the form entry session
	 */
	public KpVelocityFunctions(FormEntrySession session) {
		this.session = session;
	}
	
	/**
	 * Fetches a concept from its identifier
	 * 
	 * @param conceptIdentifier the concept identifier
	 * @return the concept
	 * @throws org.openmrs.module.metadatadeploy.MissingMetadataException if no such concept exists
	 */
	public Concept getConcept(String conceptIdentifier) {
		return Dictionary.getConcept(conceptIdentifier);
	}
	
	public String KpVelocityCalculation() {
		
		CalculationResult kpVelocity = EmrCalculationUtils.evaluateForPatient(KpVelocityCalculation.class, null,
		    session.getPatient());
		return (String) kpVelocity.getValue();
		
	}
	
}
