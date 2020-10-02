package org.openmrs.module.kenyakeypop.metadata;

import org.openmrs.api.UserService;
import org.openmrs.module.kenyaemr.EmrConstants;
import org.openmrs.module.kenyaemr.metadata.SecurityMetadata;
import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.CoreConstructors;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

/**
 * Implementation of access control to the app.
 */
@Component
@Requires(org.openmrs.module.kenyaemr.metadata.SecurityMetadata.class)
public class KeypopSecurityMetadata extends AbstractMetadataBundle {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public static final class _Privilege {
		
		public static final String KP_MODULE_APP = "App: kenyaemrkeypop.home";
		
		public static final String KP_PROVIDER_APP = "App: kenyakeypop.keypopulation.provider";
		
		public static final String PEER_CALENDAR_APP = "App: kenyakeypop.keypopulation.calendar";
	}
	
	public static final class _Role {
		
		public static final String PEER_EDUCATOR = "Peer Educator";
		
		public static final String APPLICATION_KP_MODULE = "Key Population Module";
		
	}
	
	/**
	 * @see org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		
		install(privilege(_Privilege.KP_MODULE_APP, "Able to access Key Population module features"));
		install(privilege(_Privilege.KP_PROVIDER_APP, "Able to access KP Provider module features"));
		install(privilege(_Privilege.PEER_CALENDAR_APP, "Able to access Peer Calendar features"));
		
		install(role(_Role.APPLICATION_KP_MODULE, "Can access Key Population module App",
		    idSet(org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT),
		    idSet(_Privilege.KP_PROVIDER_APP, _Privilege.KP_MODULE_APP, _Privilege.PEER_CALENDAR_APP)));
		
		install(role(_Role.PEER_EDUCATOR, "Can access Key Population module App",
		    idSet(org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT),
		    idSet(_Privilege.KP_PROVIDER_APP, _Privilege.KP_MODULE_APP, _Privilege.PEER_CALENDAR_APP)));
		
	}
}
