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

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Created by dev on 04/06/19.
 */

/**
 * Library of KVP related indicator definitions. All indicators require parameters ${startDate} and
 * ${endDate}String kvpType
 */
@Component
public class ETLMoh731PlusIndicatorLibrary {
	
	@Autowired
	private ETLMoh731PlusCohortLibrary moh731BCohorts;
	
	public CohortIndicator activeKVPs(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.activeKVPs(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingCondoms(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondoms(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPerNeed(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPerNeed(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringes(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringes(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeed(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeed(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingLubricants(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricants(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeed(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeed(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencingSexualViolence(String kvpType) {
		return cohortIndicator("Number of Kps Experiencing sexual violence", ReportUtils.map(
		    moh731BCohorts.experiencingSexualViolence(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencingPhysicalViolence(String kvpType) {
		return cohortIndicator("Number of Kps Experiencing physical violence", ReportUtils.map(
		    moh731BCohorts.experiencingPhysicalViolence(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencingEmotionalOrPsychologicalViolence(String kvpType) {
		return cohortIndicator("Number of Kps Experiencing emotional/psychological violence", ReportUtils.map(
		    moh731BCohorts.experiencingEmotionalOrPsychologicalViolence(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivedViolenceSupport(String kvpType) {
		return cohortIndicator("Received violence support", ReportUtils.map(moh731BCohorts.receivedViolenceSupport(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForSTI(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForSTI(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithSTI(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithSTI(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForSTI(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForSTI(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForHCV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithHCV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHCV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForHCV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHCV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForHBV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithHBV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHBV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForHBV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHBV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator vaccinatedAgainstHBV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.vaccinatedAgainstHBV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedTB(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTB(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedTB(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTB(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator startedTBTX(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedTBTX(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator hivPosDiagnosedWithTB(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivPosDiagnosedWithTB(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator givenTPT(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.givenTPT(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator tbClientsOnHAART(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAART(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator initiatedPrEP(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEP(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEP(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEP(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator onPrEPDiagnosedWithSTI(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.onPrEPDiagnosedWithSTI(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForMentalHealth(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForMentalHealth(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithMentalHealth(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithMentalHealth(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForMentalHealth(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForMentalHealth(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator kvpsTestedForHIV(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvpsTestedForHIV(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator kvpsReceivingHIVPosTestResults(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvpsReceivingHIVPosTestResults(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator kvplhivReached(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvplhivReached(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * KVPs reached within the last 3 months
	 * 
	 * @param kvpType
	 * @return
	 */
	public CohortIndicator kvpsReachedWithinLastThreeMonths(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvpsReachedWithinLastThreeMonths(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * kvps reached with the minimum package within the last 3 months
	 * 
	 * @param kvpType
	 * @return
	 */
	public CohortIndicator kvpsReachedWithinLastThreeMonthsDefinedPackage(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvpsReachedWithinLastThreeMonthsDefinedPackage(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * kvps receiving peer education within reporting period
	 * 
	 * @param kvpType
	 * @return
	 */
	public CohortIndicator kvpsReceivingPeerEducationWithinReportingPeriod(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kvpsReceivingPeerEducationWithinReportingPeriod(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator totalKPLHIVStartingART(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.totalKPLHIVStartingART(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator totalCurrentlyOnART(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.totalCurrentlyOnART(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator viralLoadResultsWithinLast12Months(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.viralLoadResultsWithinLast12Months(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator suppressedVLUnder200CpsPerMlWithinLast12Months(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.suppressedVLUnder200CpsPerMlWithinLast12Months(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator suppressedVLUnder50CpsPerMlWithinLast12Months(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.suppressedVLUnder50CpsPerMlWithinLast12Months(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator eligibleForVLWithinLast12Months(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.eligibleForVLWithinLast12Months(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator viralLoadTestDoneWithinLast12Months(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.viralLoadTestDoneWithinLast12Months(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencedOverdose(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencedOverdose(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencedOverdoseGivenNaloxone(String kvpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencedOverdoseGivenNaloxone(kvpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator overdoseDeaths() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.overdoseDeaths(), "startDate=${startDate},endDate=${endDate}"));
	}
}
