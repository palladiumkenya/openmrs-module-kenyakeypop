<% programs.each { descriptor -> %>
${ ui.includeFragment("prep", "program/programHistory", [ patient: patient, program: descriptor.target, showClinicalData: showClinicalData ]) }
<% } %>