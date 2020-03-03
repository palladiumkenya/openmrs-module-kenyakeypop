<%
	ui.includeCss("kenyaemrorderentry", "font-awesome.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.min.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.css.map")
	ui.includeCss("kenyaemrorderentry", "fontawesome-webfont.svg")
%>

<div class="action-container column">
	<div class="action-section">

		${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit ])}

		<ul  id ="community-outreach-tools">

			<h3>Community Outreach Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onCOFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: communityOutreachForms, onFormClick: onCOFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="other-tools">

			<h3>Other Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onOtherFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: otherForms, onFormClick: onOtherFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id = "clinical-tools">

			<h3>Clinical Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onCFFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: clinicalForms, onFormClick: onCFFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="program-level-tools">

			<h3>Program Level Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onPLFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyaui", "widget/formStack", [ forms: programLevelForms, onFormClick: onPLFormClick ]) }

			</a>
			</li>

		</ul>



		<ul>
			<h3>Completed Forms</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onEncounterClick = { encounter ->
						"""kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
					}
				%>
				${ ui.includeFragment("kenyaemr", "widget/encounterStack", [ encounters: encounters, onEncounterClick: onEncounterClick ]) }
			</a>
			</li>

		</ul>

	</div>
</div>
<script type="text/javascript">

	//On ready
	jQuery(function () {
		//defaults

		<% if (programLevelForms) { %>
		jQuery("#program-level-tools").show();
		<% } else { %>
		jQuery("#program-level-tools").hide();
		<% } %>

		<% if (clinicalForms) { %>
		jQuery("#clinical-tools").show();
		<% } else { %>
		jQuery("#clinical-tools").hide();
		<% } %>

		<% if (communityOutreachForms) { %>
		jQuery("#community-outreach-tools").show();
		<% } else { %>
		jQuery("#community-outreach-tools").hide();
		<% } %>

		<% if (otherForms) { %>
		jQuery("#other-tools").show();
		<% } else { %>
		jQuery("#other-tools").hide();
		<% } %>
	});
</script>