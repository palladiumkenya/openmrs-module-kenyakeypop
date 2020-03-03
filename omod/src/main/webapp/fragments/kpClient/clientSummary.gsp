
<div class="ke-panel-content">
	<div class="ke-stack-item">
		<% if (recordedAsDeceased) { %>
		<div class="ke-warning" style="margin-bottom: 5px">
			Client has been recorded as deceased in a program form. Please update the registration form.
		</div>
		<% } %>

		<% patient.activeAttributes.each { %>
		<% if (it.attributeType.name == 'Unknown kpClient' && (patient.familyName != "UNKNOWN" || patient.givenName != "UNKNOWN")) { %>
		<% } else { %>
		${ ui.includeFragment("kenyaui", "widget/dataPoint", [ label: ui.format(it.attributeType), value: it ]) }
		<% } %>
		<% } %>
	</div>
</div>
