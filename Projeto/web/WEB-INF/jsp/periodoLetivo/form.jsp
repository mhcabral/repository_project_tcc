<%-- 
    Document   : form
    Created on : 04/03/2013, 08:18:48
    Author     : Bruna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Período Letivo</title>
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
                        "periodoLetivo.codigo": {
                            required: true,
                            maxlength: 6
                        },
                        "periodoLetivo.dtInicio": {
                            required: true
                        },
                        "periodoLetivo.dtFim": {
                            required: true
                        },
                        "periodoLetivo.dtInicioMatricula": {
                            required: true
                        },
                        "periodoLetivo.dtFimMatricula": {
                            required: true
                        },
                        "periodoLetivo.dtInicioEstagio": {
                            required: true
                        },
                        "periodoLetivo.dtFimEstagio": {
                            required: true
                        }
                    },
                    messages: {
                        "periodoLetivo.codigo": {
                            required: "Informe o código do período",
                            maxlength: "O código deve conter no máximo 6 caracteres"
                        },
                        "periodoLetivo.dtInicio": {
                            required: "Informe a data de início do período letivo"
                        },
                        "periodoLetivo.dtFim": {
                            required: "Informe a data de término do período letivo"
                        },
                        "periodoLetivo.dtInicioMatricula": {
                            required: "Informe a data de início do período de matrícula"
                        },
                        "periodoLetivo.dtFimMatricula": {
                            required: "Informe a data de término do período de matrícula"
                        },
                        "periodoLetivo.dtInicioEstagio": {
                            required: "Informe a data de início do período de inscrição em estágio"
                        },
                        "periodoLetivo.dtFimEstagio": {
                            required: "Informe a data de término do período inscrição em estágio"
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
            $(function() {
                var dates = $("#campo-inicio, #campo-termino, #campo-inicio-matricula, #campo-termino-matricula ,#campo-inicio-estagio, #campo-termino-estagio").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    option: jQuery.datepicker.regional['pt-PT'],
                    beforeShow: function(input, inst) {
                        instance = $(this).data("datepicker");

                        today = getDataAtual();

                        date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, today, instance.settings);

                        if (this.id == "campo-inicio") {
                            $("#campo-inicio").datepicker("option", "maxDate", $("#campo-termino").datepicker("getDate"));

                            $("#campo-inicio").datepicker("option", "minDate", date);

                            return;
                        }
                        if (this.id == "campo-termino") {
                            $("#campo-termino").datepicker("option", "minDate", $("#campo-inicio").datepicker("getDate"));

                            return;
                        }
                        if (this.id == "campo-inicio-matricula") {
                            if ($("#campo-termino-matricula").datepicker("getDate")) {
                                $("#campo-inicio-matricula").datepicker("option", "maxDate", $("#campo-termino-matricula").datepicker("getDate"));
                            } else {
                                $("#campo-inicio-matricula").datepicker("option", "maxDate", $("#campo-termino").datepicker("getDate"));
                            }

                            $("#campo-inicio-matricula").datepicker("option", "minDate", $("#campo-inicio").datepicker("getDate"));

                            return;
                        }
                        if (this.id == "campo-termino-matricula") {
                            $("#campo-termino-matricula").datepicker("option", "maxDate", $("#campo-termino").datepicker("getDate"));

                            $("#campo-termino-matricula").datepicker("option", "minDate", $("#campo-inicio-matricula").datepicker("getDate"));

                            return;
                        }
                        if (this.id == "campo-inicio-estagio") {
                            if ($("#campo-termino-estagio").datepicker("getDate")) {
                                $("#campo-inicio-estagio").datepicker("option", "maxDate", $("#campo-termino-estagio").datepicker("getDate"));
                            } else {
                                $("#campo-inicio-estagio").datepicker("option", "maxDate", $("#campo-termino").datepicker("getDate"));
                            }

                            $("#campo-inicio-estagio").datepicker("option", "minDate", $("#campo-inicio").datepicker("getDate"));

                            return;
                        }
                        if (this.id == "campo-termino-estagio") {
                            $("#campo-termino-estagio").datepicker("option", "maxDate", $("#campo-termino").datepicker("getDate"));

                            $("#campo-termino-estagio").datepicker("option", "minDate", $("#campo-inicio-estagio").datepicker("getDate"));

                            return;
                        }
                    },
                    onSelect: function(selectedDate) {
                        habilitaDataTermino();
                        habilitaDataInicioMatricula();
                        habilitaDataTerminoMatricula();
                        habilitaDataInicioEstagio();
                        habilitaDataTerminoEstagio();
                        if (this.id == "campo-inicio") {
                            var option = "minDate";
                        } else if (this.id == "campo-termino") {
                            var option = "maxDate";
                        }
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
                    document.getElementById("campo-inicio-matricula").disabled = true;
                    document.getElementById("campo-termino-matricula").disabled = true;
                }
            }
            function habilitaDataInicioMatricula() {
                if (document.getElementById("campo-termino").value) {
                    document.getElementById("campo-inicio-matricula").disabled = false;
                } else {
                    document.getElementById("campo-inicio-matricula").disabled = true;
                    document.getElementById("campo-termino-matricula").disabled = true;
                }
            }
            function habilitaDataTerminoMatricula() {
                if (document.getElementById("campo-inicio-matricula").value) {
                    document.getElementById("campo-termino-matricula").disabled = false;
                } else {
                    document.getElementById("campo-termino-matricula").disabled = true;
                }
            }
            function habilitaDataInicioEstagio() {
                if (document.getElementById("campo-termino").value) {
                    document.getElementById("campo-inicio-estagio").disabled = false;
                } else {
                    document.getElementById("campo-inicio-estagio").disabled = true;
                    document.getElementById("campo-termino-estagio").disabled = true;
                }
            }
            function habilitaDataTerminoEstagio() {
                if (document.getElementById("campo-inicio-estagio").value) {
                    document.getElementById("campo-termino-estagio").disabled = false;
                } else {
                    document.getElementById("campo-termino-estagio").disabled = true;
                }
            }
        </script>
        <script>
            function mascara(src, mask) {
                var i = src.value.length;
                var saida = mask.substring(0, 1);
                var texto = mask.substring(i)
                if (texto.substring(0, 1) != saida)
                {
                    src.value += texto.substring(0, 1);
                }
            }
        </script>
        <script language="javascript">
            function somente_numero(campo) {
                var digits = "0123456789"
                var campo_temp
                for (var i = 0; i < campo.value.length; i++) {
                    campo_temp = campo.value.substring(i, i + 1)
                    if (i != 4 && digits.indexOf(campo_temp) == -1) {
                        campo.value = campo.value.substring(0, i);
                    }
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
                                        <a href="${pageContext.request.contextPath}/periodosLetivo">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Período Letivo</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Período Letivo</h2></div>
                </c:if>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">                    
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

        <form id="formPeriodo" name="formPeriodo" method="POST" action="<c:url value="/periodosLetivo"/>">
            <p>
                <c:if test="${not empty periodoLetivo.id}">
                    <input type="hidden" name="periodoLetivo.id" value="${periodoLetivo.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            <br/><b>Período Letivo:</b>
            <p>
                <label for="campo-codigo">Código*:</label>
                <input onKeyUp="javascript:somente_numero(this);" onkeypress="javascript:mascara(this, '####/##')" maxlength="6" size="6" id="campo-codigo" name="periodoLetivo.codigo" value="${periodoLetivo.codigo}"/>
                <label for="campo-inicio">Início do Período*:</label>
                <input size="10" readonly="true" onKeyUp="javascript:habilitaDataTermino();" id="campo-inicio" name="periodoLetivo.dtInicio" value="<fmt:formatDate value="${periodoLetivo.dtInicio}" pattern="dd/MM/yyyy"/>"/>
                <label for="campo-termino">Término do Período*:</label>
                <input size="10" readonly="true" onKeyUp="javascript:habilitaDataInicioMatricula();" <c:if test="${empty periodoLetivo.id}">disabled="true"</c:if> id="campo-termino" name="periodoLetivo.dtFim" value="<fmt:formatDate value="${periodoLetivo.dtFim}" pattern="dd/MM/yyyy"/>"/>
                </p>

                <br/><b>Período de Aproveitamento:</b>
                <p>
                    <label for="campo-inicio-matricula">Início do Período*:</label>
                    <input size="10" readonly="true" onKeyUp="javascript:habilitaDataTerminoMatricula();" <c:if test="${empty periodoLetivo.id}">disabled="true"</c:if> id="campo-inicio-matricula" name="periodoLetivo.dtInicioMatricula" value="<fmt:formatDate value="${periodoLetivo.dtInicioMatricula}" pattern="dd/MM/yyyy"/>"/>
                    <label for="campo-termino-matricula">Término do Período*:</label>
                    <input size="10" readonly="true" onKeyUp="javascript:habilitaDataInicioEstagio();" <c:if test="${empty periodoLetivo.id}">disabled="true"</c:if> id="campo-termino-matricula" name="periodoLetivo.dtFimMatricula" value="<fmt:formatDate value="${periodoLetivo.dtFimMatricula}" pattern="dd/MM/yyyy"/>"/>
                </p>

                <br/><b>Período de Estágio:</b>
                <p>
                    <label for="campo-inicio-estagio">Início do Período*:</label>
                    <input size="10" readonly="true" onKeyUp="javascript:habilitaDataTerminoEstagio();" <c:if test="${empty periodoLetivo.id}">disabled="true"</c:if> id="campo-inicio-estagio" name="periodoLetivo.dtInicioEstagio" value="<fmt:formatDate value="${periodoLetivo.dtInicioEstagio}" pattern="dd/MM/yyyy"/>"/>
                    <label for="campo-termino-estagio">Término do Período*:</label>
                    <input size="10" readonly="true" <c:if test="${empty periodoLetivo.id}">disabled="true"</c:if> id="campo-termino-estagio" name="periodoLetivo.dtFimEstagio" value="<fmt:formatDate value="${periodoLetivo.dtFimEstagio}" pattern="dd/MM/yyyy"/>"/>
            </p>            
        </form>            
    </body>
</html>