<%
    ui.decorateWith("kenyaemr", "standardPage", [ layout: "sidebar" ])

    def menuItemsNew = [
            [ label: "Seen Clients", iconProvider: "kenyaui", icon: "buttons/patients.png", href: ui.pageLink("kenyaemr", "clinician/clinicianSearchSeen") ]
    ]
%>

<div class="ke-page-sidebar">
    ${ ui.includeFragment("kenyakeypop", "peerSearch/peerSearchForm", [ defaultWhich: "checked-in" ]) }
</div>

<div class="ke-page-content">
    ${ ui.includeFragment("kenyakeypop", "peerSearch/peerSearchResults", [ pageProvider: "kenyakeypop", page: "peerCalender/peerViewClients" ]) }

</div>

<script type="text/javascript">
    jQuery(function() {
        jQuery('input[name="query"]').focus();
    });
</script>