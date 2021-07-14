<%
	config.require("encounters")

	if (config.encounters && config.encounters.size() > 0) {
		config.encounters.each { encounter ->
			def title = ui.format(encounter.form ?: encounter.encounterType)

			title += " (" + kenyaui.formatDateAuto(encounter.encounterDatetime) + ")"

			def providers = encounter.providersByRoles.values().collectNested { ui.format(it) }.flatten().join(", ")

			def onClick = config.onEncounterClick instanceof Closure ? config.onEncounterClick(encounter) : config.onEncounterClick

%>
<style>
.ke-navigable-form {
	cursor: pointer;
}
.ke-navigable-form:hover {
	text-decoration: underline;
}
</style>
<div class="ke-stack-item ke-navigable-form" onclick="${ onClick }" >
	<input type="hidden" name="encounterId" value="${ encounter.encounterId }"/>
	<i class="fa fa-file-text-o" style="color:yellow"></i>
	<b>${ title }</b> by ${ providers }<br/>
</div>
<%
		}
	} else {
%>
<i>None</i>
<% } %>