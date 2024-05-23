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
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator hivTestedKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.hivTestedKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator htsNumberTestedAtFacilityKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.htsNumberTestedAtFacilityKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator htsNumberTestedAtCommunityKPs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.htsNumberTestedAtCommunityKPs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator kpsNewlyTestedForHIV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kpsNewlyTestedForHIV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator testedRepeat(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedRepeat(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator selfTestedForHIV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.selfTestedForHIV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator knownPositive(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.knownPositive(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivedPositiveHIVResults(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPositiveHIVResults(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator linked(String kpType) {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.linked(kpType), "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator positiveMonthsAgo(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.testedHIVPositiveMonthsAgo(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingCondoms(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondoms(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingCondomsPerNeedPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingCondomsPerNeedPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringes(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringes(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingNeedlesAndSyringesPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingNeedlesAndSyringesPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingLubricants(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricants(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingLubricantsPerNeed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingLubricantsPerNeed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingSelfTestKits(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingSelfTestKits(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForSTI(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForSTI(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForHCV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHCV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedForHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedForHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedWithHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedWithHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator treatedForHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.treatedForHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator vaccinatedAgainstHBV(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.vaccinatedAgainstHBV(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator screenedTB(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.screenedTB(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator diagnosedTB(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.diagnosedTB(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator startedTBTX(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.startedTBTX(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator tbClientsOnHAART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.tbClientsOnHAART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator initiatedPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.initiatedPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator currentOnPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentOnPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator turningPositiveWhileOnPrEP(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.turningPositiveWhileOnPrEP(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencingViolence(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencingViolence(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingViolenceSupport(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingViolenceSupport(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator numberExposed(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.numberExposed(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivingPEPWithin72Hrs(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivingPEPWithin72Hrs(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator completedPEPWith28Days(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.completedPEPWith28Days(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * KPs reached within the last 3 months
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortIndicator kpsReachedWithinLastThreeMonths(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kpsReachedWithinLastThreeMonths(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * KPs reached with the minimum package within the last 3 months
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortIndicator kpsReachedWithinLastThreeMonthsDefinedPackage(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kpsReachedWithinLastThreeMonthsDefinedPackage(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	/**
	 * KPs receiving peer education within reporting period
	 * 
	 * @param kpType
	 * @return
	 */
	public CohortIndicator kpsReceivingPeerEducationWithinReportingPeriod(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.kpsReceivingPeerEducationWithinReportingPeriod(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivedPeerEducation(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedPeerEducation(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator receivedClinicalServices(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.receivedClinicalServices(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator onSitePreART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.onSitePreART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator offSitePreART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.offSitePreART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator totalOnPreART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.totalOnPreART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator onSiteStartingART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.onSiteStartingART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator offSiteStartingART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.offSiteStartingART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator totalStartingART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.totalStartingART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator currentlyOnARTOnSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentlyOnARTOnSite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator currentlyOnARTOffSite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.currentlyOnARTOffSite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator totalCurrentlyOnART(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.totalCurrentlyOnART(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator onARTAt12MonthsOnsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.onARTAt12MonthsOnsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator netCohortAt12MonthsOnsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.netCohortAt12MonthsOnsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator viralLoad12MonthsOnsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.viralLoad12MonthsOnsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator suppressedViralLoad12MonthsCohortOnsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.suppressedViralLoad12MonthsCohortOnsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator viralLoad12MonthsOffsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.viralLoad12MonthsOffsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator suppressedViralLoad12MonthsCohortOffsite(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.suppressedViralLoad12MonthsCohortOffsite(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencedOverdose(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencedOverdose(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator experiencedOverdoseGivenNaloxone(String kpType) {
		return cohortIndicator("", ReportUtils.map(moh731BCohorts.experiencedOverdoseGivenNaloxone(kpType),
		    "startDate=${startDate},endDate=${endDate},location=${location}"));
	}
	
	public CohortIndicator overdoseDeaths() {
		return cohortIndicator("",
		    ReportUtils.map(moh731BCohorts.overdoseDeaths(), "startDate=${startDate},endDate=${endDate}"));
	}
}
