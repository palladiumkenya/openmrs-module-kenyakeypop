<%
	//ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])
	def kpVisitForm = "7492cffe-5874-4144-a1e6-c9e455472a35"

%>

<style>
.simple-table {
	border: solid 1px #DDEEEE;
	border-collapse: collapse;
	border-spacing: 0;
	font: normal 13px Arial, sans-serif;
}

.simple-table thead th {
	background-color: #DDEFEF;
	border: solid 1px #DDEEEE;
	color: #336B6B;
	padding: 5px;
	text-align: left;
	text-shadow: 1px 1px 1px #fff;
}

.simple-table td {
	border: solid 1px #DDEEEE;
	color: #333;
	padding: 5px;
	text-shadow: 1px 1px 1px #fff;
}
</style>

<div class="ke-page-sidebar">

</div>
<div class="ke-page-content">
<div class="ke-panel-frame">
	<div class="ke-panel-heading">Peers</div>
<div class="ke-panel-content">
	<div>
		<tbody>
		<% if (!peers) { %>
		<div>0 Peers found!</div>
		<% } else { %>
		<table class="simple-table">
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Gender</th>
				<th>Age</th>
				<th>Actions</th>
			</tr>
		<% peers.eachWithIndex { peer, index, indexPlusOne = index + 1  -> %>
		<tr >
			<td>${ indexPlusOne }</td>
			<td>${ peer.name }</td>
			<td>${ peer.gender }</td>
			<td>${ peer.age }</td>
			<td>
				<% if (!peer.encounter) { %>
				<button type="button"
						onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: peer.id, effectiveDate: effectiveDate,
						formUuid: kpVisitForm,appId:"kenyaemr.medicalEncounter", returnUrl: ui.thisUrl() ])}')">
					 Enter Form
				</button>
				<% } else { %>
				<button type="button"
						onclick="ui.navigate('${ ui.pageLink("kenyaemr", "editForm", [ patientId: peer.id,encounterId: peer.encounter,
						formUuid: kpVisitForm,appId:"kenyaemr.medicalEncounter", returnUrl: ui.thisUrl() ])}')">
					Edit Form
				</button>
				<% } %>

			</td>
		</tr>
		<% }} %>
		</table>
		</tbody>

	</div>
</div>

</div>
</div>

