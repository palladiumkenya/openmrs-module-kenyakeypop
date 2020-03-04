<% programs.each { descriptor -> %>
${ ui.includeFragment("kenyakeypop", "program/programHistory", [ patient: patient, program: descriptor.target, showClinicalData: showClinicalData ]) }
<% } %>