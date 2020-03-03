<%
    ui.includeCss("kenyaemr", "referenceapplication.css", 100)
%>

<div class="info-body">
                            <% if (vitals) { %>
                                <table>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <th>Value</th>
                                    </tr>
                                    <tr>
                                        <td>Weight</td>
                                        <td>${vitals.weight}</td>
                                    </tr>
                                    <tr>
                                        <td>Height</td>
                                        <td>${vitals.height}</td>
                                    </tr>
                                    <tr>
                                        <td>Temperature</td>
                                        <td>${vitals.temperature}  &#176;C</td>
                                    </tr>
                                    <tr>
                                        <td>Pulse Rate</td>
                                        <td>${vitals.pulse}</td>
                                    </tr>
                                    <tr>
                                        <td>BP</td>
                                        <td>${vitals.bp}</td>
                                    </tr>
                                    <tr>
                                        <td>Respiratory Rate</td>
                                        <td>${vitals.resp_rate}</td>
                                    </tr>
                                    <tr>
                                        <td>Oxygen Saturation</td>
                                        <td>${vitals.oxygen_saturation}</td>
                                    </tr>
                                    <tr>
                                        <td>MUAC</td>
                                        <td>${vitals.muac}</td>
                                    </tr>
                                    <tr>
                                        <td>LMP</td>
                                        <td>${vitals.lmp}</td>
                                    </tr>

                        </table>

                            <% } else { %>
                                No vitals for this visit
                            <% } %>
                            <!-- <a class="view-more">Show more info ></a> //-->
                        </div>
