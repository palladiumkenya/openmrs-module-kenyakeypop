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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.Visit;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.appframework.service.AppFrameworkService;
import org.openmrs.module.kenyacore.form.FormDescriptor;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaemr.metadata.TbMetadata;
import org.openmrs.module.kenyakeypop.KpConstant;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageContext;
import org.openmrs.ui.framework.page.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.openmrs.module.kenyaemr.metadata.CommonMetadata._Form.HTS_CONFIRMATORY_TEST;
import static org.openmrs.module.kenyaemr.metadata.CommonMetadata._Form.HTS_INITIAL_TEST;
import static org.openmrs.module.kenyaemr.metadata.TbMetadata._Form.TB_SCREENING;
import static org.openmrs.module.kenyakeypop.KpConstant.MODULE_ID;
import static org.openmrs.module.kenyakeypop.metadata.KpMetadata._Form.*;

/**
 * Patient forms
 */
@AppPage(KpConstant.APP_KP_PROVIDER)
public class ActionsPanelFragmentController {
	
	protected static final Log log = LogFactory.getLog(ActionsPanelFragmentController.class);
	
	public void controller(FragmentModel model, @FragmentParam(value = "visit", required = false) Visit visit, UiUtils ui,
	        PageRequest request, PageContext pageContext, @SpringBean FormManager formManager,
	        @SpringBean KenyaUiUtils kenyaUi) {
		
		User loggedInUser = Context.getUserContext().getAuthenticatedUser();
		Set<Role> userRoles = loggedInUser.getAllRoles();
		String userApp = null;
		Set<String> userRolesStr = new HashSet<String>();
		for (Role userRole : userRoles) {
			userRolesStr.add(userRole.getName());
		}
		
		//Mapping forms to kp tools
		List<SimpleObject> communityOutreachObj = new ArrayList<SimpleObject>();
		List<SimpleObject> clinicalObj = new ArrayList<SimpleObject>();
		List<SimpleObject> programLevelObj = new ArrayList<SimpleObject>();
		List<SimpleObject> commodityObj = new ArrayList<SimpleObject>();
		List<SimpleObject> summaryReportingObj = new ArrayList<SimpleObject>();
		
		String communityTools[] = new String[] { KP_CLIENT_TRACING_FORM, KP_PEER_OVERDOSE_REPORTING_FORM, KP_REFERRAL_FORM,
		        KP_PEER_CALENDAR_FORM };
		String clinicalTools[] = new String[] { KP_CLIENT_ENROLLMENT, KP_STI_TREATMENT_FORM, KP_ALCOHOL_SCREENING_FORM,
		        KP_DEPRESSION_SCREENING_FORM, KP_HCW_OVERDOSE_REPORTING_FORM, TB_SCREENING, HTS_INITIAL_TEST,
		        HTS_CONFIRMATORY_TEST, KP_CLINICAL_VISIT_FORM, TB_SCREENING };
		String programtools[] = new String[] { KP_VIOLENCE_SCREENING_FORM };
		String commodityTools[] = new String[] {};
		String summaryReportingTools[] = new String[] {};
		
		Set<String> communityOutreachForms = new HashSet(Arrays.asList(communityTools));
		Set<String> clinicalForms = new HashSet(Arrays.asList(clinicalTools));
		Set<String> programLevelForms = new HashSet(Arrays.asList(programtools));
		Set<String> commodityForms = new HashSet(Arrays.asList(commodityTools));
		Set<String> summaryReportingForms = new HashSet(Arrays.asList(summaryReportingTools));
		
		AppDescriptor currentApp = null;
		String MODEL_ATTR_CURRENT_APP = "currentApp";
		String REQUEST_ATTR_CURRENT_APP = MODULE_ID + ".current-app";
		currentApp = Context.getService(AppFrameworkService.class).getApp(KpConstant.APP_KP_PROVIDER);
		pageContext.getRequest().getRequest().setAttribute(REQUEST_ATTR_CURRENT_APP, currentApp);
		pageContext.getModel().addAttribute(MODEL_ATTR_CURRENT_APP, currentApp);
		
		List<FormDescriptor> completedForms = new ArrayList<FormDescriptor>();
		List<SimpleObject> otherForms = new ArrayList<SimpleObject>();
		
		List<Encounter> encounters = new ArrayList<Encounter>();
		
		if (visit != null) {
			List<Encounter> allEncounters = new ArrayList<Encounter>(visit.getEncounters());
			completedForms = formManager.getCompletedFormsForVisit(currentApp, visit);
			for (Encounter encounter : allEncounters) {
				Form form = encounter.getForm();
				
				if (encounter.isVoided() || form == null) {
					continue;
				}
				
				FormDescriptor descriptor = formManager.getFormDescriptor(form);
				
				if (completedForms.contains(descriptor)) {
					encounters.add(encounter);
				}
			}
			
			for (FormDescriptor descriptor : formManager.getAllUncompletedFormsForVisit(currentApp, visit)) {
				
				if (communityOutreachForms.contains(descriptor.getTarget().getUuid())) {
					
					communityOutreachObj.add(ui.simplifyObject(descriptor.getTarget()));
				} else if (clinicalForms.contains(descriptor.getTarget().getUuid())) {
					clinicalObj.add(ui.simplifyObject(descriptor.getTarget()));
				} else if (programLevelForms.contains(descriptor.getTarget().getUuid())) {
					programLevelObj.add(ui.simplifyObject(descriptor.getTarget()));
				} else
					otherForms.add(ui.simplifyObject(descriptor.getTarget()));
			}
			
			Collections.sort(encounters, new Comparator<Encounter>() {
				
				@Override
				public int compare(Encounter left, Encounter right) {
					return left.getEncounterDatetime().compareTo(right.getEncounterDatetime());
				}
			});
			
		}
		model.addAttribute("otherForms", otherForms);
		model.addAttribute("communityOutreachForms", communityOutreachObj);
		model.addAttribute("clinicalForms", clinicalObj);
		model.addAttribute("programLevelForms", programLevelObj);
		model.addAttribute("encounters", encounters);
	}
	
}
