<%
	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	def heading = config.heading ?: "Matching Peer Educators"
%>
<div class="ke-panel-frame" ng-controller="PeerSearchResults" ng-init="init('${ currentApp.id }', '${ config.pageProvider }', '${ config.page }')">
	<div class="ke-panel-heading">${ heading }</div>
	<div class="ke-panel-content">
		<div class="ke-stack-item ke-navigable" ng-repeat="patient in results" ng-click="onPeerEducatorResultClick(patient)">
			${ ui.includeFragment("kenyakeypop", "peerSearch/peerResult.full") }
		</div>
		<div ng-if="results.length == 0" style="text-align: center; font-style: italic">None</div>
		</div>

</div>