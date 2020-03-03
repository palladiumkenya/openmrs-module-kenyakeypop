<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

    def menuItems = [
            [ label: "Back KP Provider", iconProvider: "kenyaui", icon: "buttons/back.png", label: "Back KP Provider", href: ui.pageLink("kenyakeypop", "kpProviderHome") ]
    ]
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "", items: menuItems ]) }

</div>

<div class="ke-page-content">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchResults", [ pageProvider: "kenyaemr", page: "clinician/clinicianViewPatient" ]) }
</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>