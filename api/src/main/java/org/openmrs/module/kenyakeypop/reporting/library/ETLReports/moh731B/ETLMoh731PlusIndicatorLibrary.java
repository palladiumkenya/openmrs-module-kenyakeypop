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
	
	public CohortIndicator screenedForHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedWithHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedForHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedForHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedWithHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator treatedForHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator vaccinatedAgainstHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.vaccinatedAgainstHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator screenedTB(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTB(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator diagnosedTB(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTB(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator startedTBTX(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedTBTX(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator tbClientsOnHAART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator initiatedPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator currentOnPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator experiencingViolence(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolence(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingViolenceSupport(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupport(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator numberExposed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivingPEPWithin72Hrs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72Hrs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator completedPEPWith28Days(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28Days(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
	
	public CohortIndicator receivedPeerEducation(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPeerEducation(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}

	public CohortIndicator onPreART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.onPreART(kpType),
				"startDate=${startDate},endDate=${endDate},location=${subCounty}"));
	}
}
