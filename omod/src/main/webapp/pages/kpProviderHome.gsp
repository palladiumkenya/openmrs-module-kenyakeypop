<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

    def menuItemsNew = [
            [ label: "Peer Calendar", iconProvider: "kenyakeypop", icon: "buttons/kpcalendar.png", href: ui.pageLink("kenyakeypop", "kenyakeypopulationPeerCalendarHome") ]
    ]
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchForm", [ defaultWhich: "checked-in" ]) }
    ${ ui.includeFragment("kenyaui", "widget/panelMenu", [ heading: "", items: menuItemsNew ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("kenyaemr", "patient/patientSearchResults", [ pageProvider: "kenyakeypop", page: "patientProfile" ]) }
</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>