package org.openmrs.module.kenyakeypop.calculation.library.kp;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.calculation.BaseEmrCalculation;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;
import org.openmrs.module.kenyaemr.util.EncounterBasedRegimenUtils;
import org.openmrs.ui.framework.SimpleObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class InitialArtStartDateCalculation extends BaseEmrCalculation {
	
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
	
	/**
	 * @see org.openmrs.calculation.patient.PatientCalculation#evaluate(java.util.Collection,
	 *      java.util.Map, org.openmrs.calculation.patient.PatientCalculationContext)
	 * @should return null for patients who have not started ART
	 * @should return start date for patients who have started ART
	 */
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
	        PatientCalculationContext context) {
		
		CalculationResultMap ret = new CalculationResultMap();
		CalculationResultMap tiArtStartDate = Calculations.firstObs(
		    Dictionary.getConcept(Dictionary.ANTIRETROVIRAL_TREATMENT_START_DATE), cohort, context);
		
		for (Integer ptId : cohort) {
			
			Date startDate = null;
			Date dateTiStartedArt = null;
			Obs tiStartDate = EmrCalculationUtils.obsResultForPatient(tiArtStartDate, ptId);
			if (tiStartDate != null) {
				dateTiStartedArt = tiStartDate.getValueDatetime();
			}
			
			Encounter firstDrugRegimenEditorEncounter = EncounterBasedRegimenUtils.getFirstEncounterForCategory(Context
			        .getPatientService().getPatient(ptId), "ARV"); //last DRUG_REGIMEN_EDITOR encounter
			
			if (firstDrugRegimenEditorEncounter != null) {
				SimpleObject o = EncounterBasedRegimenUtils.buildRegimenChangeObject(
				    firstDrugRegimenEditorEncounter.getAllObs(), firstDrugRegimenEditorEncounter);
				if (o != null) {
					try {
						if (o.get("startDate") != null && StringUtils.isNotBlank(o.get("startDate").toString())) {
							startDate = DATE_FORMAT.parse(o.get("startDate").toString());
						}
					}
					catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			
			if (startDate != null && dateTiStartedArt != null) {
				if (startDate.after(dateTiStartedArt)) {
					ret.put(ptId, new SimpleResult(dateTiStartedArt, this, context));
				} else if (startDate.before(dateTiStartedArt)) {
					ret.put(ptId, new SimpleResult(startDate, this, context));
				} else {
					ret.put(ptId, new SimpleResult(startDate, this, context));
				}
			} else if (startDate != null && dateTiStartedArt == null) {
				ret.put(ptId, new SimpleResult(startDate, this, context));
			} else if (dateTiStartedArt != null && startDate == null) {
				ret.put(ptId, new SimpleResult(dateTiStartedArt, this, context));
			} else {
				ret.put(ptId, null);
			}
		}
		return ret;
	}
}
