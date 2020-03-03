<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

    def menuItemsNew = [
            [ label: "Seen Clients", iconProvider: "kenyaui", icon: "buttons/patients.png", href: ui.pageLink("kenyaemr", "clinician/clinicianSearchSeen") ]
    ]
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaemr", "kpClient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "", items: menuItemsNew ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("kenyaemr", "kpClient/patientSearchResults", [ pageProvider: "kenyakeypop", page: "patientProfile" ]) }
</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>