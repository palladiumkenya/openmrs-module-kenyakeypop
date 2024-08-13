package org.openmrs.module.kenyakeypop.calculation.library.kp;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.Program;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.metadata.HivMetadata;
import org.openmrs.module.kenyakeypop.metadata.KpMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EligibleForKPPLHIVLinkTrackingForm extends AbstractPatientCalculation {
	
	@Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params,
                                         PatientCalculationContext context) {
        CalculationResultMap ret = new CalculationResultMap();
        Set<Integer> alive = Filters.alive(cohort, context);
        Program hivProgram = MetadataUtils.existing(Program.class, HivMetadata._Program.HIV);
		Set<Integer> inHivProgram = Filters.inProgram(hivProgram, alive, context);
        //PLHIV Link Facility Documentation Tracking

        for (int ptId : alive) {
            boolean eligible = false;

            EncounterService encounterService = Context.getEncounterService();
            Patient patient = Context.getPatientService().getPatient(ptId);
            List<PatientProgram> programs = Context.getProgramWorkflowService().getPatientPrograms(patient, Context.getProgramWorkflowService().getProgramByUuid(KpMetadata._Program.KEY_POPULATION), null, null, null, null, false);
            List<PatientProgram> activeProgram = programs.stream().filter(p ->  p.getDateCompleted() == null).collect(Collectors.toList());

            // Enrolled in KP program but not in HIV program in this facility
            if (!activeProgram.isEmpty() && !inHivProgram.contains(ptId)) {
                eligible = true;
            }

            ret.put(ptId, new BooleanResult(eligible, this));

        }

        return ret;
    }
}
