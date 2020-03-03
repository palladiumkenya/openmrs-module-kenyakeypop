
<%
	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	//def heading = config.heading ?: "Matching Peers"
%>
<div>
<table style="width: 100%" ng-repeat="patient in clientResults">
	<tr class="ke-stack-item ke-navigable" ng-click="onPeerResultClick(patient)">
		<td style="width: 32px; vertical-align: top; padding-right: 5px">
			<img width="32" height="32" ng-src="${ ui.resourceLink("kenyaui", "images/buttons/patient_") }{{ patient.personA.gender }}.png" />
		</td>
		<td style="text-align: left; vertical-align: top; width: 33%">
			<strong>{{ patient.personA.display }}</strong><br/>
			{{ patient.personA.age }} <small>(DOB {{ patient.personA.birthdate }})</small>
		</td>
		<td style="text-align: center; vertical-align: top; width: 33%">
			<div ng-repeat="identifier in patient.personA.identifiers">
				<span class="ke-identifier-type">{{ identifier.identifierType }}</span>
				<span class="ke-identifier-value">{{ identifier.identifier }}</span>
			</div>
		</td>

	</tr>
</table>
</div>
<div ng-if="clientResults.length == 0" style="text-align: center; font-style: italic">None</div>