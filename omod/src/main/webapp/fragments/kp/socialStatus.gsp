
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
			</tr>
			<tr>
				<td>Daily</td>
				<td>Weekly average</td>
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
			</tr>
		<% } %>
		</table>
		<% } %>
	</div>
</div>
