<%-- 
    Document   : show
    Created on : 13/04/2013, 01:56:37
    Author     : Bruna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estágio [Exibição]</title>
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-ui.js'></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js'></script>
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css' />
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css' media='print' />   

        <style>
            #calendar {
                width: 75%;
            }
        </style>
        <script type='text/javascript'>
            $(document).ready(function() {
                var inicioPeriodo = new Date($("#inicio").val()); 
                var fimPeriodo = new Date($("#fim").val());
                
                var calendar = $('#calendar').fullCalendar({
                    theme: true,		
                    selectable: false,
                    weekends: false,
                    header: {
                        left: 'prev, today',
                        center: 'title',
                        right: 'next'
                    },                    
                    events: "${pageContext.request.contextPath}/estagios/frequencia/list.json/${estagio.id}",
                    viewDisplay   : function(view) {
                        var a = new Date();
                        var start = new Date($("#inicio").val()); 
                        var end = new Date($("#fim").val());

                        var cal_date_string = view.start.getMonth()+'/'+view.start.getFullYear();
                        var cur_date_string = start.getMonth()+'/'+start.getFullYear();
                        var end_date_string = end.getMonth()+'/'+end.getFullYear();

                        if(cal_date_string == cur_date_string) {
                            jQuery('.fc-button-prev').addClass("ui-state-disabled");
                        }
                        else {
                            jQuery('.fc-button-prev').removeClass("ui-state-disabled");
                        }

                        if(end_date_string == cal_date_string) {
                            jQuery('.fc-button-next').addClass("ui-state-disabled");
                        }
                        else {
                            jQuery('.fc-button-next').removeClass("ui-state-disabled");
                        }
                    }
                });
                calendar.hide();
            });
            
            function showCalendar(){
                $('#calendar').show();
                $('#calendar').fullCalendar( 'render' );
            }
            
            function getMonth(i){
                var months = new Array('Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro');
                return months[i];
            }
        </script>
    </head>
    <body>
        <input type="hidden" id="inicio" name="inicio" value="${inicioPeriodo}"/>
        <input type="hidden" id="fim" name="fim" value="${fimPeriodo}"/>
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/analise/final/estagios" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informações do Estágio</h2></div>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li style="color: #cd0a0a">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>${error.message}
                            </li>
                        </c:forEach>        
                    </ul>
                </div>
            </div>   
        </c:if>
        <c:if test="${not empty estagio.id}">
            <h4>Informações do Estagio</h4>
            <p>
                <b>Período:</b>
                ${estagio.periodo}
            </p>
            <p>
                <b>Situação:</b>
                ${estagio.status.nome}
            </p>
            <br/>
            <h4>Professor Orientador</h4>
            <p>
                ${estagio.orientador}
            </p>
            <br/>
            <h4>Dados da Empresa</h4>
            <p>
                <b>Nome da empresa:</b>
                ${estagio.empresa}
            </p>
            <p>
                <b>Nome do supervisor:</b>
                ${estagio.nomeSupervisor}
            </p>
            <p>
                <b>Email do supervisor:</b>
                ${estagio.emailSupervisor}
            </p>
            <p>
                <c:if test="${estagio.convenio == true}">Empresa <b>possui</b> convênio com a UFAM</c:if>
                <c:if test="${estagio.convenio == false}">Empresa <b>não possui</b> convênio com a UFAM</c:if>
                </p>
                <br/>
                <h4>Documentação</h4>
                <p>
                    <b>Termo de Compromisso:</b>
                    <a href="${pageContext.request.contextPath}/analise/final/estagios/download/${estagio.termoCompromisso}" target="_blank" >${estagio.termoCompromisso}</a>
            </p>
            <p>
                <b>Seguro:</b>
                <a href="${pageContext.request.contextPath}/analise/final/estagios/download/${estagio.seguro}" target="_blank" >${estagio.seguro}</a>
            </p>
            <c:if test="${estagio.convenio == false}">
                <p>
                    <b>Ficha de Cadastro da Empresa:</b>
                    <a href="${pageContext.request.contextPath}/analise/final/estagios/download/${estagio.fichaCadastroEmpresa}" target="_blank" >${estagio.fichaCadastroEmpresa}</a>
                </p>
            </c:if>
            <br/>
            <h4>Relatório Final</h4>
            <p>
                <a href="${pageContext.request.contextPath}/analise/final/estagios/download/${estagio.relatorioFinal.relatorioFinal}" target="_blank" >${estagio.relatorioFinal.relatorioFinal}</a>
            </p>
            <br/>
            <h4>Frequências</h4>
            <br/>
            <div>
                <table width="15%" style="border: solid 1px #8099B3">
                    <tr>
                        <td width="5%" style="border: solid 1px #8099B3">
                            <b>Mês</b>
                        </td>
                        <td width="5%" style="border: solid 1px #8099B3">
                            <b>Status</b>
                        </td>
                    </tr>
                    <c:forEach items="${freqAnalisadas}" var="frequencia">
                        <tr>
                            <td style="border: solid 1px #8099B3">
                                <script>
                                    document.write( getMonth(${frequencia.mes}));
                                </script>
                            </td>
                            <c:if test="${frequencia.status == 'VAZIA' || frequencia.status == 'SALVA'}">
                                <td colspan="4">
                                    FREQUÊNCIA NÃO ENVIADA
                                </td>
                            </c:if>
                            <c:if test="${frequencia.status != 'VAZIA' && frequencia.status != 'SALVA'}">
                                <td style="border: solid 1px #8099B3">
                                    ${frequencia.status.nome}
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
                <a href="javascript:showCalendar()"><img src="${pageContext.request.contextPath}/img/show.png"/>Detalhar</a>
            </div>
            <br/>
            <div id='calendar'></div>
        </c:if>
        <form id="formAnalise" name="formAnalise" method="POST">
            <input type="hidden" name="estagio.id" value="${estagio.id}"/>
            <input type="hidden" name="_method" value="PUT"/>  
            <input type="hidden" id="analise" name="analise"/>
        </form>
    </body>
</html>