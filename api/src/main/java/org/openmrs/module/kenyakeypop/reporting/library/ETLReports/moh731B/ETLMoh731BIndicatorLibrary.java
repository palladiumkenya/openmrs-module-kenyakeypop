/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.library.ETLReports.moh731B;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Created by dev on 04/06/19.
 */

/**
 * Library of KP related indicator definitions. All indicators require parameters ${startDate} and
 * ${endDate}
 */
@Component
public class ETLMoh731BIndicatorLibrary {
	
	@Autowired
	private ETLMoh731BCohortLibrary moh731BCohorts;
	
	public CohortIndicator activeFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activeMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activeMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activePwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activePwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activePwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activePwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activeTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.activeTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator activeTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.activeTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator hivTestedTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtFacilityTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtFacilityTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtCommunityTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtCommunityTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedNewFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedNewFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedNewMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedNewMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedNewMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedNewMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedNewPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedNewPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedNewPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedNewPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtNewTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtNewTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedAtNewTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedAtNewTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator testedRepeatTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.testedRepeatTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositiveFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositiveFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositiveMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositiveMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositiveMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositiveMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositivePwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositivePwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositivePwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositivePwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositiveTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositiveTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator knownPositiveTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.knownPositiveTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedPositiveResultsTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivedPositiveResultsTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator linkedTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPerNeedFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPerNeedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesTransman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesTransman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesTranswoman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesTranswoman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedFsw(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedMsm(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedMsw(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedPwid(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedPwud(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedTransman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedTransman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeedTranswoman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeedTranswoman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedTransman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedTransman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeedTranswoman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeedTranswoman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTIFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTIFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTIMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTIMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTIMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTIMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTIPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTIPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTIPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTIPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTITransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTITransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTITranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForSTITranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHCVTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHCVTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForHBVTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedForHBVTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator positiveHBVTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.positiveHBVTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator treatedHBVTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.treatedHBVTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedTBTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.screenedTBTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator diagnosedTBTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.diagnosedTBTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedOnTBTxTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.startedOnTBTxTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator tbClientsOnHAARTTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.tbClientsOnHAARTTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator initiatedPrEPTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.initiatedPrEPTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currentOnPrEPTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.currentOnPrEPTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPTransman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPTransman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPTranswoman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPTranswoman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolenceFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolenceFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolenceMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolenceMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolenceMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolenceMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolencePwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolencePwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolencePwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolencePwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolenceTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolenceTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencingViolenceTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.experiencingViolenceTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingViolenceSupportTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingViolenceSupportTranswoman() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportTranswoman(),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator numberExposedTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.numberExposedTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsFsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsFsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsMsm() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsMsm(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsMsw() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsMsw(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsPwid() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsPwid(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsPwud() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsPwud(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsTransman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsTransman(), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsTranswoman() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsTranswoman(), "startDate=${startDate},endDate=${endDate}"));
	}
	/* public CohortIndicator completedPEPWith28DaysFsw() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysFsw(), "startDate=${startDate},endDate=${endDate}"));
	 }

	 public CohortIndicator completedPEPWith28DaysMsm() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysMsm(), "startDate=${startDate},endDate=${endDate}"));
	 }
	 public CohortIndicator completedPEPWith28DaysMsw() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysMsw(), "startDate=${startDate},endDate=${endDate}"));
	 }
	 public CohortIndicator completedPEPWith28DaysPwid() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysPwid(), "startDate=${startDate},endDate=${endDate}"));
	 }

	 public CohortIndicator completedPEPWith28DaysPwud() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysPwud(), "startDate=${startDate},endDate=${endDate}"));
	 }
	 public CohortIndicator completedPEPWith28DaysTransman() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysTransman(), "startDate=${startDate},endDate=${endDate}"));
	 }*/
}
