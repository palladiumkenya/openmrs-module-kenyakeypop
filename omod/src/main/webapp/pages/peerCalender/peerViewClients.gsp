<%
	//ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
	ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])
	def kpVisitForm = "7492cffe-5874-4144-a1e6-c9e455472a35"
	def completed = 0
	def pending = 0
	peers.each {
		if(it.encounter != null){
			completed ++
		}
	}

	peers.each {
		if(it.encounter == null){
			pending ++
		}
	}


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
.report-status-table {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	padding-top: 5px;

}
.report-status-table td {
	border: 1px solid gray;
	line-height: 20px;
	padding: 2px 2px 2px 5px;
	}
.button {
	height: 30px !important;
	width: 120px !important;
	padding: 10px 15px !important;

	}
.checkmark {
	display:inline-block;
	width: 22px;
	height:22px;
	-ms-transform: rotate(45deg); /* IE 9 */
	-webkit-transform: rotate(45deg); /* Chrome, Safari, Opera */
	transform: rotate(45deg);
}

.checkmark_circle {
	position: absolute;
	width:22px;
	height:22px;
	background-color: green;
	border-radius:11px;
	left:0;
	top:0;
}
.checkmark_stem {
	position: absolute;
	width:3px;
	height:9px;
	background-color:#ccc;
	left:11px;
	top:6px;
}

.checkmark_kick {
	position: absolute;
	width:3px;
	height:3px;
	background-color:#ccc;
	left:8px;
	top:12px;
}

#reportingPeriod {
	border-radius: 5px;
	padding: 1px 10px 10px 10px;
	border: 2px gray solid;
	color: green;
	font-size: 1.5em;
}

#completionStatus {
	border-radius: 5px;
	padding: 1px 10px 10px 5px;
	margin-top: 5px;
	border: 2px gray solid;
	font-size: 1.2em;
}
</style>

<div class="ke-page-sidebar">
<div id="reportingPeriod">
	<h3>Reporting period:</h3>
	<h4>${ reportingPeriod }</h4>
</div>
<div id="completionStatus">
	<h3>Completion Status</h3>
	<table class="report-status-table">
	<tr>
		<td width="10%">Total</td>
		<td width="5%" >${ TotalPeers } </td>
	</tr>
	<tr>
		<td width="10%">Completed</td>
		<td width="5%">${completed}</td>
	</tr>
	<tr>
		<td width="10%">Pending</td>
		<td width="5%">${pending}</td>
	</tr>

</table>
</div>


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
				<td width="5%"><b>#</b></td>
				<td width="40%"> <b>Name</b></td>
				<td width="5%"><b>Gender</b></td>
				<td width="5%"><b>Age</b></td>
				<td width="10%"><b>Actions</b></td>
			</tr>
		<% peers.eachWithIndex { peer, index, indexPlusOne = index + 1  -> %>
		<tr >
			<td>${ indexPlusOne }</td>
			<td>${ peer.name }</td>
			<td>${ peer.gender }</td>
			<td>${ peer.age }</td>
			<td>
				<% if (!peer.encounter) { %>
				<button type="button" class="button"
						onclick="ui.navigate('${ ui.pageLink("kenyaemr", "enterForm", [ patientId: peer.id, effectiveDate: effectiveDate,
						formUuid: kpVisitForm,appId:"kenyaemr.medicalEncounter", returnUrl: ui.thisUrl() ])}')">
					 Enter Form
				</button>
				<% } else { %>
				<button type="button" class="button"
						onclick="ui.navigate('${ ui.pageLink("kenyaemr", "editForm", [ patientId: peer.id,encounterId: peer.encounter,
						formUuid: kpVisitForm,appId:"kenyaemr.medicalEncounter", returnUrl: ui.thisUrl() ])}')">
					Edit Form
				</button>
				<span class="checkmark">
					<div class="checkmark_circle"></div>
					<div class="checkmark_stem"></div>
					<div class="checkmark_kick"></div>
				</span>
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

