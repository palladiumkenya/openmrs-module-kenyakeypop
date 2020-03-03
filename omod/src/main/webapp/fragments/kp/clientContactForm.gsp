<%
	def clientFormUuid = "185dec84-df6f-4fc7-a370-15aa8be531ec"
	def appId = "kenyaemr.medicalEncounter"
	def addClientContactFormLink = ui.pageLink("kenyaemr", "enterForm", [patientId: currentPatient.patientId, appId: appId, formUuid: clientFormUuid, returnUrl: ui.thisUrl()])
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">

		<% if(encounters) { %>
		<table>
			<tr>
				<th rowspan="2">Date</th>
				<th rowspan="2">KP Type</th>
				<th rowspan="2">Hotspot</th>
				<th rowspan="2">Sex Acts<br>per week</th>
				<th rowspan="2">Anal Sex Acts<br>per week</th>
				<th colspan="2">Drug Injection <br>Episodes</th>
				<th> </th>
			</tr>
			<tr>
				<td>Daily</td>
				<td>Weekly average</td>
				<td></td>
			</tr>
		<% encounters.each { %>
			<tr>
				<td width="80px">${it.encDate}</td>
				<td>${it.kpType}</td>
				<td>${it.hotspot}</td>
				<td>${it.weeklySexActs}</td>
				<td>${it.weeklyAnalSexActs}</td>
				<td>${it.dailyDrugInjections}</td>
				<td>${it.weeklyDrugInjections}</td>
				<td><a href="${ui.pageLink("kenyaemr", "editForm", [patientId: currentPatient.patientId, appId: appId, encounterId: it.encId, returnUrl: ui.thisUrl()])}"> Edit </a> </td>
			</tr>
		<% } %>
		</table>
		<% } else { %>
		<i class="fa fa-plus-square right" style="color: steelblue" title="Add/Edit Contact Form" onclick="location.href = '${addClientContactFormLink}'"></i>

		None
		<% } %>
	</div>
</div>
