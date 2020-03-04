<%
	config.require("forms")

	if (config.forms && config.forms.size() > 0) {
		config.forms.each { form ->
			def onClick = config.onFormClick instanceof Closure ? config.onFormClick(form) : config.onFormClick
%>
<style>
.ke-navigable-form {
	cursor: pointer;
}
.ke-navigable-form:hover {
	text-decoration: underline;
}
</style>
<div class="ke-stack-item ke-navigable-form" onclick="${ onClick }">
	<i class="fa fa-file-text-o"></i>
	<b>${ form.name }</b>
	<div style="clear: both"></div>
</div>
<%
		}
	} else {
%>
<i>None</i>
<% } %>