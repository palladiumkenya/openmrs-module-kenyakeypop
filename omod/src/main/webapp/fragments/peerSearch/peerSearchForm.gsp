<%
	ui.decorateWith("kenyaui", "panel", [ heading: "Search for a Peer Educator" ])

	ui.includeJavascript("kenyaemr", "controllers/patient.js")

	def defaultWhich = config.defaultWhich ?: "all"

	def id = config.id ?: ui.generateId();
%>

<script type="text/javascript">
	jQuery(function() {
		jQuery('.date-picker').datepicker( {
				changeMonth: true,
				changeYear: true,
				showButtonPanel: true,
				dateFormat: 'MM-yy',
				onClose: function(dateText, inst) {
					jQuery(this).datepicker('setDate', new Date(inst.selectedYear, inst.selectedMonth, 1));
				}
			});
		});
</script>
<style>
.ui-datepicker-calendar {
	display: none;
}
</style>

<form id="${ id }" ng-controller="PeerSearchForm" ng-init="init()">
	<label  class="ke-field-label">Select Date</label>
	<span class="ke-field-content">
		<input name="startDate" id="startDate" class="date-picker" />
	</span>
	<label  class="ke-field-label">Which peer Educators</label>

	<label class="ke-field-label">ID or name (3 chars min)</label>
	<span class="ke-field-content">
		<input type="text" name="query" ng-model="query" ng-change="updateSearch()" style="width: 260px" />
	</span>
</form>