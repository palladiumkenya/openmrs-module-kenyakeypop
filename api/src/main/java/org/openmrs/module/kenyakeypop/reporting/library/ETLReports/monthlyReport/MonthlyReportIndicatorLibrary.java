/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyakeypop.reporting.library.ETLReports.monthlyReport;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyakeypop.reporting.library.ETLReports.moh731B.ETLMoh731PlusCohortLibrary;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Created by dev on 04/06/19.
 */

/**
 * Library of KP related Monthly report indicator definitions. All indicators require parameters
 * ${startDate} and ${endDate}
 */
@Component
public class MonthlyReportIndicatorLibrary {
	
	@Autowired
	private MonthlyReportCohortLibrary monthlyReportCohortLibrary;
	
	public CohortIndicator contactAll(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.contactAll(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator everEnroll(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.everEnroll(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator contactNew(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.contactNew(kpType), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator contactHCW(String kpType) {
		return cohortIndicator("",
				ReportUtils.map(monthlyReportCohortLibrary.contactHCW(kpType), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator netEnroll(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.netEnroll(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpPrev(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.kpPrev(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpCurr(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.kpCurr(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator enrollNew(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.enrollNew(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpKnownPositiveEnrolled(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kpKnownPositiveEnrolled(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator enrollHtsTst(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.enrollHtsTst(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator enrollHtsTstPos(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.enrollHtsTstPos(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator assistedSelfTested(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.assistedSelfTested(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator unAssistedSelfTested(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.unAssistedSelfTested(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator htsTstSelfConfirmedPositive(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.htsTstSelfConfirmedPositive(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator htsTestedNegative(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.htsTestedNegative(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpLHIVCurr(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.kpLHIVCurr(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator newOnARTKP(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.newOnARTKP(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator currOnARTKP(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.currOnARTKP(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForSTI(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.screenedForSTI(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedPositiveForSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.screenedPositiveForSTI(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator startedSTITx(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.startedSTITx(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator screenedForGbv(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.screenedForGbv(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator experiencedGbv(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.experiencedGbv(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedGbvClinicalCare(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.receivedGbvClinicalCare(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator receivedGbvLegalSupport(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.receivedGbvLegalSupport(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator eligibleForRetest(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.eligibleForRetest(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator htsTstEligibleRetested(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.htsTstEligibleRetested(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator retestedHIVPositive(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.retestedHIVPositive(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator referredAndInitiatedPrEPPepfar(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.referredAndInitiatedPrEPPepfar(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator referredAndInitiatedPrEPNonPepfar(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.referredAndInitiatedPrEPNonPepfar(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivInitiatedARTOtherPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivInitiatedARTOtherPEPFAR(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivInitiatedARTNonPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivInitiatedARTNonPEPFAR(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivCurrentOnARTOtherPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivCurrentOnARTOtherPEPFAR(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivCurrentOnARTNonPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivCurrentOnARTNonPEPFAR(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivLTFURecently(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivLTFURecently(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivTXRtt(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.kplhivTXRtt(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivSuppressedVl(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivSuppressedVl(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivWithVlResult(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivWithVlResult(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivSuppressedVlArtOtherPEPFARSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivSuppressedVlArtOtherPEPFARSite(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivVlResultArtOtherPEPFARSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivVlResultArtOtherPEPFARSite(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivSuppressedVlArtNonPEPFARSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivSuppressedVlArtNonPEPFARSite(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kplhivVlResultArtNonPEPFARSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kplhivVlResultArtNonPEPFARSite(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpOnMultiMonthART(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kpOnMultiMonthART(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator kpEnrolledInARTSupportGroup(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kpEnrolledInARTSupportGroup(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator offeredPNS(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.offeredPNS(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator acceptedPNS(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.acceptedPNS(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator elicitedPNS(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(monthlyReportCohortLibrary.elicitedPNS(kpType), "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator pnsKnownPositiveAtEntry(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.pnsKnownPositiveAtEntry(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator pnsTestedPositive(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.pnsTestedPositive(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}
	
	public CohortIndicator pnsTestedNegative(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.pnsTestedNegative(kpType),
		    "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator kpEverPos(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.kpEverPos(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txEverDice(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txEverDice(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txEverVerifyPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txEverVerifyPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txEverVerifyNonPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txEverVerifyNonPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleDice(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleDice(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleDoneDice(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleDoneDice(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleVerifyPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleVerifyPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleDoneVerifyPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleDoneVerifyPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleVerifyNonPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleVerifyNonPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator txPvlsEligibleDoneVerifyNonPEPFAR(String kpType) {
		return cohortIndicator("", ReportUtils.map(monthlyReportCohortLibrary.txPvlsEligibleDoneVerifyNonPEPFAR(kpType),
				"startDate=${startDate},endDate=${endDate}"));
	}
	
}
