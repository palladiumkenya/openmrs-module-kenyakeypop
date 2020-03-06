<%
	ui.includeCss("kenyaemrorderentry", "font-awesome.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.min.css")
	ui.includeCss("kenyaemrorderentry", "font-awesome.css.map")
	ui.includeCss("kenyaemrorderentry", "fontawesome-webfont.svg")

	def onEncounterClick = { encounter ->
		"""kenyaemr.openEncounterDialog('${ currentApp.id }', ${ encounter.id });"""
	}

%>

<div class="action-container column">
	<div class="action-section">

		<ul  id ="community-outreach-tools">

			<h3>Community Outreach Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onCOFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: communityOutreachForms, onFormClick: onCOFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="other-tools">

			<h3>Other Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onOtherFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: otherForms, onFormClick: onOtherFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id = "clinical-tools">

			<h3>Clinical Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onCFFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: clinicalForms, onFormClick: onCFFormClick ]) }

			</a>
			</li>

		</ul>
		<ul id="program-level-tools">

			<h3>Program Level Tools</h3>
			<li class="float-left" style="margin-top: 7px">
				<%
					def onPLFormClick = { form ->
						def visitId = currentVisit ? currentVisit.id : activeVisit.id
						def opts = [ visitId: visitId, formUuid: form.formUuid, returnUrl: ui.thisUrl() ]
						"""ui.navigate('${ ui.pageLink('kenyaemr', 'enterForm', opts) }');"""
					}
				%>

				${ ui.includeFragment("kenyakeypop", "widget/formLightStack", [ forms: programLevelForms, onFormClick: onPLFormClick ]) }

			</a>
			</li>

		</ul>



		<ul>
			<h3>Completed Forms</h3>
			<li class="float-left" style="margin-top: 7px">

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