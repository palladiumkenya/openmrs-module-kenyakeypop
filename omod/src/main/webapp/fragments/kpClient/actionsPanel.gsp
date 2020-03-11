<%
	ui.includeCss("kenyaemrorderentry", "font-awesome.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.min.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.css.map")
	ui.includeCss("kenyaemrorderentry", "fontawesome-webfont.svg")

%>
<style>
.action-container {
	display: inline;
	float: left;
	width: 99.9%;
	margin: 0 1.04167%;
}
.action-section {
	margin-top: 2px;
	background: #7f7b72 !important; ;
	border: 1px solid #dddddd;
}
.float-left {
	float: left;
	clear: left;
	width: 97.91666%;
	background: #7f7b72 !important;
	color: white;
}

.action-section a:link {
	color: white;!important;
}

.action-section a:hover {
	color: white;
}

.action-section a:visited {
	color: white;
}
.action-section h3 {
	margin: 0;
	color: white;
	border-bottom: 1px solid white;
	margin-bottom: 5px;
	font-size: 1.5em;
	margin-top: 5px;
}
.action-section ul {
	background: #7f7b72 !important; ;
	color: white;
	padding: 5px;
}

.action-section li {
	font-size: 1.1em;
}
.action-section i {
	font-size: 1.1em;
	margin-left: 8px;
}
</style>

<div class="action-container">
	<div class="action-section">

		${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit ])}

		<ul class="float-left">
			<h3>Visit Actions</h3>

			<li class="float-left" style="margin-top: 7px">
				<a href="${ ui.pageLink("kenyaemrorderentry", "drugOrders", [patientId: currentPatient]) }" class="float-left">
					<i class="fa fa-medkit fa-2x"></i>
					Drug Orders
				</a>
			</li>
			<li class="float-left" style="margin-top: 7px">
				<a href="${ ui.pageLink("kenyaemrorderentry", "labOrders", [patientId: currentPatient]) }" class="float-left">
					<i class="fa fa-flask fa-2x"></i>
					Lab Orders
				</a>
			</li>

		</ul>

		<ul  id ="community-outreach-tools" class="float-left">

			<h3>Community Outreach Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<a>
				<%
					def onCOFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: communityOutreachForms, onFormClick: onCOFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id = "clinical-tools" class="float-left">

			<h3>Clinical Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<a>
				<%
					def onCFFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: clinicalForms, onFormClick: onCFFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="program-level-tools" class="float-left">

			<h3>Program Level Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<a>
				<%
					def onPLFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: programLevelForms, onFormClick: onPLFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="other-tools" class="float-left">

			<h3>Other Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<a>
					<%
						def onOtherFormClick = { form ->
							def visitId = currentVisit ? currentVisit.id : activeVisit.id
							def opts = [ appId: currentApp.id, visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
							"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
						}
					%>

					${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: otherForms, onFormClick: onOtherFormClick ]) }

				</a>
			</li>

		</ul>



		<ul class="float-left">
			<h3>Completed Forms</h3>
			<li class="float-left" style="margin-top: 7px">
				<a>
				<%
					def onEncounterClick = { encounter ->
						"""kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
					}
				%>
				${ ui.includeFragment("kenyakeypop", "widget/encounterLightStack", [ encounters: encounters, onEncounterClick: onEncounterClick ]) }
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