<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
    ui.includeCss("kenyaemr", "referenceapplication.css", 100)
    ui.includeCss("kenyaemrorderentry", "font-awesome.css")
    ui.includeCss("kenyaemrorderentry", "font-awesome.min.css")
    ui.includeCss("kenyaemrorderentry", "font-awesome.css.map")
    ui.includeCss("kenyaemrorderentry", "../fontawesome-webfont.svg")

    def editContactInfoLink =  ui.pageLink("registrationapp", "editSection", [ patientId: patient.id,sectionId: "contactInfo", appId:"referenceapplication.registrationapp.registerPatient", returnUrl: ui.thisUrl() ])
    def editAliasLink = ui.pageLink("registrationapp", "editSection", [ patientId: patient.id,sectionId: "alias", appId:"referenceapplication.registrationapp.registerPatient", returnUrl: ui.thisUrl() ])
    def addTriageFormLink = ui.pageLink("htmlformentryui", "htmlform/enterHtmlFormWithSimpleUi", [patientId: currentPatient.patientId, definitionUiResource: "kenyaemr:simpleuiforms/triage.xml", returnUrl: ui.thisUrl()])

%>
<script type="text/javascript">


    jq(function(){

    });


</script>

<div class="clear"></div>

<div id="content" class="container">

    <div class="clear"></div>
    <div class="container">
        <div class="dashboard clear">
            <div class="info-container column">

                <div class="info-section">
                    <div class="info-header">
                        <i class="icon-diagnosis"></i>
                        <h3>Registration Info</h3>
                        <span class="right"><i class="fa fa-pencil" style="font-size:20px;color: steelblue;"></i> &nbsp;<a href="${editAliasLink}">Edit Client Alias</a> &nbsp;&nbsp;&nbsp; <i class="fa fa-pencil" style="font-size:20px;color: steelblue">&nbsp;</i> <a href="${editContactInfoLink}">Edit Contact Info</a> </span>
                    </div>
                    <div class="info-body">
                        ${ ui.includeFragment("kenyakeypop", "kpClient/patientSummary", [ patient: currentPatient ]) }
                    </div>
                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-support"></i>
                        <h3>Peer - Contact Form</h3>
                    </div>

                    ${ ui.includeFragment("kenyakeypop", "kpClient/clientContactForm", [patient: currentPatient]) }

                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-support"></i>
                        <h3>Peer - Peer Educator Relationship</h3>
                    </div>

                    ${ ui.includeFragment("kenyakeypop", "relationship/patientRelationships", [patient: currentPatient]) }

                </div>

                <div class="info-section">
                    <div class="info-header">
                        <i class="fa fa-history"></i>
                        <h3>KP Program History</h3>
                    </div>
                    <div class="info-body">
                        ${ ui.includeFragment("kenyakeypop", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
                    </div>
                </div>

            </div>

                <div class="info-section allergies">
                    <div class="info-header">
                        <i class="icon-stethoscope"></i>
                        <h3>Vitals</h3>
                        <i class="fa fa-plus-square right" style="color: steelblue" title="Add vitals" onclick="location.href = '${addTriageFormLink}'"></i>
                    </div>
                    <div class="info-body">
                        ${ ui.includeFragment("kenyakeypop", "kpClient/currentVitals", [ patient: currentPatient]) }
                    </div>
                </div>
            </div>

            ${ ui.includeFragment("kenyakeypop", "kpClient/actionsPanel", [visit: visit]) }

        </div>
    </div>
</div>
