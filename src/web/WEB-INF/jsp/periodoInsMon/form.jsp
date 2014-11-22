<%-- 
    Document   : form
    Created on : 04/03/2013, 08:18:48
    Author     : Leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Período de Inscrição de Monitorias</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>        
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function() {
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formPeriodo").submit();
                });
                $("#formPeriodo").validate({
                    rules: {
                        "periodoInsMon.dtInicio": {
                            required: true
                        },
                        "periodoInsMon.dtFim": {
                            required: true
                        }},
                    messages: {
                        "periodoInsMon.dtInicio": {
                            required: "Informe a data de início do período",
                        },
                        "periodoInsMon.dtFim": {
                            required: "Informe a data de término do período",
                        }
                    }

                });
            });

        </script>
        <script>
            /* Portuguese initialisation for the jQuery UI date picker plugin. */
            jQuery(function($) {
                $.datepicker.regional['pt-PT'] = {
                    closeText: 'Fechar',
                    prevText: 'Anterior',
                    nextText: 'Seguinte',
                    currentText: 'Hoje',
                    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                        'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                    dayNames: ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'],
                    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    weekHeader: 'Sem',
                    dateFormat: 'dd/mm/yy',
                    firstDay: 0,
                    isRTL: false,
                    showMonthAfterYear: false,
                    yearSuffix: ''
                };
                $.datepicker.setDefaults($.datepicker.regional['pt-PT']);
            });
        </script>
        <script>
            function getDataAtual() {
                hoje = new Date()
                dia = hoje.getDate()
                mes = hoje.getMonth()
                ano = hoje.getFullYear()
                mes = mes + 1
                if (dia < 10)
                    dia = "0" + dia
                if (mes < 10)
                    mes = "0" + mes
                if (ano < 2000)
                    ano = "19" + ano

                //O mes começa em Zero, então soma-se 1

                today = dia + "/" + (mes) + "/" + ano;
                return today;
            }
            var dataSetada = false;
            $(function() {
                var dates = $("#campo-inicio, #campo-termino").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    option: jQuery.datepicker.regional['pt-PT'],
                    minDate: getDataAtual(),
                    beforeShow: function(input, inst) {
                        if (!dataSetada) {
                            //Pega a instancia do datepicker clicado
                            instance = $(this).data("datepicker");

                            today = getDataAtual();
                            //alert(today);
                            date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, today, instance.settings);
                            dateIngresso = '${dataIngresso}';

                            //Se o campo clicado não for o data Final, será descartado
                            if (this.id != "campo-termino") {
                                //$("#campo-inicio").datepicker("option", "maxDate", date);
                                //$("#campo-inicio").datepicker("option", "minDate", dateIngresso);
                                return;
                            }

                            //Seta data maxima e minima no datePicker DataTermino
                            //$("#campo-termino").datepicker("option", "maxDate", date);
                            // $("#campo-termino").datepicker("option", "minDate", dateIngresso);

                            //$("#campo-inicio").datepicker("option", "maxDate", date);

                            dataSetada = true;
                        }
                    },
                    onSelect: function(selectedDate) {
                        habilitaDataTermino();
                        var option = this.id == "campo-inicio" ? "minDate" : "maxDate";
                        instance = $(this).data("datepicker");
                        date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                        dates.not(this).datepicker("option", option, date);
                    }
                });
            });
            function habilitaDataTermino() {
                if (document.getElementById("campo-inicio").value) {
                    document.getElementById("campo-termino").disabled = false;
                } else {
                    document.getElementById("campo-termino").disabled = true;
                }
            }
        </script>
    </head>
    <body>
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li class="button" id="toolbar-apply">
                                        <a href="javascript:void(0);" id="save" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-save"></span>Salvar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/periodosIns">
                                            <span width="32" height="32" border="0" class="icon-32-cancel"></span>Cancelar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <c:if test="${operacao == 'Cadastro'}">
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Período</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Período</h2></div>
                </c:if>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                        <strong>Situações não permitidas:</strong>
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li style="color: #cd0a0a">${error.message}</li>
                            </c:forEach>        
                    </ul>
                </div>
            </div>   
        </c:if>

        <form id="formPeriodo" name="formPeriodo" method="POST" action="<c:url value="/periodosIns"/>"> 
            <p>
                <c:if test="${not empty periodoInsMon.id}">
                    <input type="hidden" name="periodoInsMon.id" value="${periodoInsMon.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p><p>
                <label  for="campo-inicio">Data de Início*:</label><br/>
                <input onKeyUp="javascript:habilitaDataTermino();" id="campo-inicio" name="periodoInsMon.dtInicio" value="<fmt:formatDate value="${periodoInsMon.dtInicio}" pattern="dd/MM/yyyy"/>"/>
            </p><p>
                <label  for="campo-termino">Data de Término*:</label><br/>                             
                <input <c:if test="${empty periodoInsMon.id}">disabled="true"</c:if> id="campo-termino" name="periodoInsMon.dtFim" value="<fmt:formatDate value="${periodoInsMon.dtFim}" pattern="dd/MM/yyyy"/>"/>
            </p> 
        </form>            
    </body>
</html>
