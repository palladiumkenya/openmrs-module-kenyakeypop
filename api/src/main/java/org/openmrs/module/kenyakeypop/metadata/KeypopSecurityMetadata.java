package org.openmrs.module.kenyakeypop.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

/**
 * Implementation of access control to the app.
 */
@Component
@Requires(org.openmrs.module.kenyaemr.metadata.SecurityMetadata.class)
public class KeypopSecurityMetadata extends AbstractMetadataBundle{

    public static class _Privilege {
        public static final String APP_KP_MODULE_APP = "App: kenyaemrkeypop.home";
    }

    public static final class _Role {
        public static final String APPLICATION_KP_MODULE = "Key Population Module";
    }

    /**
     * @see AbstractMetadataBundle#install()
     */
    @Override
    public void install() {

        install(privilege(_Privilege.APP_KP_MODULE_APP, "Able to access Key Population module features"));
        install(role(_Role.APPLICATION_KP_MODULE, "Can access Key Population module App", idSet(
                org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT
        ), idSet(
                _Privilege.APP_KP_MODULE_APP
        )));
    }
}
