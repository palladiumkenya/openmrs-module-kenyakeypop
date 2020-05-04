<%
	def clientFormUuid = "63917c60-3fea-11e9-b210-d663bd873d93"
	def appId = "kenyaemr.medicalEncounter"
	def addClientContactFormLink = ui.pageLink("kenyaemr", "enterForm", [patientId: currentPatient.patientId, appId: appId, formUuid: clientFormUuid, returnUrl: ui.thisUrl()])
%>
<div class="ke-panel-content">
	<div class="ke-stack-item">

		<table>
			<tr>
				<th rowspan="2">Status In program</th>
				<th rowspan="2">ART Status/Latest viral load</th>
				<th rowspan="2">Next Appointment Date</th>
			</tr>
	<tr></tr>

			<tr>
			 <td width="140px">${statusInProgram}</td>
			<td>${artStatus}</td>
			<td>${nextAppointmentDate}</td>

			</tr>
		</table>
	</div>
</div>
