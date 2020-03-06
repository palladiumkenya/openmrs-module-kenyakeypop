<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient, visit: currentVisit ])

	def defaultEncounterDate = currentVisit ? currentVisit.startDatetime : new Date()
%>
<div class="ke-page-content">
	${ ui.includeFragment("kenyakeypop", "form/enterHtmlForm", [
			patient: currentPatient,
			formUuid: formUuid,
			visit: currentVisit,
			defaultEncounterDate: defaultEncounterDate,
			returnUrl: returnUrl
	]) }
</div>