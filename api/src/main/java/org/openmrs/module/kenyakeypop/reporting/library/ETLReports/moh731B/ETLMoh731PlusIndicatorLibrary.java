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
public class ETLMoh731PlusIndicatorLibrary {
	
	@Autowired
	private ETLMoh731PlusCohortLibrary moh731BCohorts;
	
	public CohortIndicator activeKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
		public CohortIndicator activeMsm(String kpType) {
			return cohortIndicator("",
			    ReportUtils.map(moh731BCohorts.activeMsm(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}

		public CohortIndicator activeMsw(String kpType) {
			return cohortIndicator("",
			    ReportUtils.map(moh731BCohorts.activeMsw(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}

		public CohortIndicator activePwid(String kpType) {
			return cohortIndicator("",
			    ReportUtils.map(moh731BCohorts.activePwid(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}*/
	
	/*public CohortIndicator activePwud(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.activePwud(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator activeTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator activePrisonersAndClossedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activePrisonersAndClossedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator hivTestedKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator hivTestedMsm(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedMsm(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator hivTestedMsw(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.hivTestedMsw(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator hivTestedPwid(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedPwid(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator hivTestedPwud(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedPwud(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator hivTestedTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator hivTestedPrisonersAndClosedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedPrisonersAndClosedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator htsNumberTestedAtFacilityKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.htsNumberTestedAtFacilityKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*	public CohortIndicator testedAtFacilityMsm(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityMsm(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtFacilityMsw(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityMsw(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtFacilityPwid(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityPwid(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtFacilityPwud(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityPwud(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtFacilityTransgender(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityTransgender(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtFacilityPrisonersClosedSettings(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtFacilityPrisonersClosedSettings(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}*/
	
	public CohortIndicator htsNumberTestedAtCommunityKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.htsNumberTestedAtCommunityKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*	public CohortIndicator testedAtCommunityMsm(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityMsm(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtCommunityMsw(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityMsw(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtCommunityPwid(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityPwid(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtCommunityPwud(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityPwud(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtCommunityTransgender(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityTransgender(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtCommunityPrisonersClosedSettings(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtCommunityPrisonersClosedSettings(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}*/
	
	public CohortIndicator kpsNewlyTestedForHIV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kpsNewlyTestedForHIV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*	
		public CohortIndicator testedNewMsm(String kpType) {
			return cohortIndicator("",
			    ReportUtils.map(moh731BCohorts.testedNewMsm(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedNewMsw(String kpType) {
			return cohortIndicator("",
			    ReportUtils.map(moh731BCohorts.testedNewMsw(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedNewPwid(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedNewPwid(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedNewPwud(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedNewPwud(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator testedAtNewTransgender(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedAtNewTransgender(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}
		
		public CohortIndicator newlyTestedprisonersClosedSettings(String kpType) {
			return cohortIndicator("", ReportUtils.map(moh731BCohorts.newlyTestedprisonersClosedSettings(kpType),
			    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
		}*/
	
	public CohortIndicator testedRepeat(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeat(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator testedRepeatMsm(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatMsm(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator testedRepeatMsw(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatMsw(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator testedRepeatPwid(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatPwid(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator testedRepeatPwud(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatPwud(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator testedRepeatTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator testedRepeatPrisonersAndClosedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeatPrisonersAndClosedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator selfTestedForHIV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator selfTestedForHIVMsm(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVMsm(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator selfTestedForHIVMsw(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVMsw(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator selfTestedForHIVPwid(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVPwid(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator selfTestedForHIVPwud(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVPwud(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator selfTestedForHIVTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator selfTestedForHIVPrisonersAndClosedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIVPrisonersAndClosedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator knownPositive(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositive(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator knownPositiveMsm(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositiveMsm(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator knownPositiveMsw(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositiveMsw(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator knownPositivePwid(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositivePwid(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator knownPositivePwud(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositivePwud(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator knownPositiveTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositiveTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator knownPositivePrisonersClosedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositivePrisonersClosedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator receivedPositiveHIVResults(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveHIVResults(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator receivedPositiveResultsMsm(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsMsm(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPositiveResultsMsw(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsMsw(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPositiveResultsPwid(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsPwid(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPositiveResultsPwud(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsPwud(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPositiveResultsTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPositiveResultsPrisonersClosedSettings(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveResultsPrisonersClosedSettings(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}*/
	
	public CohortIndicator linked(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linked(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	/*
	public CohortIndicator linkedMsm(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedMsm(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator linkedMsw(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedMsw(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator linkedPwid(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedPwid(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator linkedPwud(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linkedPwud(kpType), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator linkedTransgender(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.linkedTransgender(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	*/
	public CohortIndicator positiveMonthsAgo(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedHIVPositiveMonthsAgo(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingCondoms(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondoms(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringes(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringes(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingLubricants(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricants(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingSelfTestKits(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingSelfTestKits(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedWithSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedForSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHCVTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCVTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBVTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBVTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator positiveHBVTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.positiveHBVTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedHBVTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedHBVTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator negativeHBVVaccinatedTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.negativeHBVVaccinatedTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTBTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTBTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTBTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTBTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedOnTBTxTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedOnTBTxTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAARTTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAARTTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEPTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEPTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEPTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEPTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEPTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEPTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolenceFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolenceFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolenceMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolenceMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolenceMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolenceMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolencePwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolencePwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolencePwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolencePwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolenceTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolenceTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupportTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupportTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposedTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposedTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsFsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsFsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsMsm() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsMsm(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsMsw() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsMsw(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsPwid() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsPwid(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsPwud() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsPwud(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72HrsTransgender() {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72HrsTransgender(),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	/* public CohortIndicator completedPEPWith28DaysFsw() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysFsw(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }

	 public CohortIndicator completedPEPWith28DaysMsm() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysMsm(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }
	 public CohortIndicator completedPEPWith28DaysMsw() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysMsw(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }
	 public CohortIndicator completedPEPWith28DaysPwid() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysPwid(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }

	 public CohortIndicator completedPEPWith28DaysPwud() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysPwud(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }
	 public CohortIndicator completedPEPWith28DaysTransgender() {
	     return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28DaysTransgender(), "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	 }*/
}
