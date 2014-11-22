<%-- 
    Document   : analiseFrequencia
    Created on : 14/04/2013, 01:49:42
    Author     : Bruna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Análise Frequencia</title>

        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-ui.js'></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js'></script>
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css' />
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css' media='print' />   

        <script>
            $(function() {
                $('#aprovar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente aprovar a frequência?");
                    if (decisao){
                        $("#analise").attr('value', 'aprova');
                        $("#form").attr('action', '${pageContext.request.contextPath}/estagios/analise/frequencia').submit();
                    }
                });
                $('#reprovar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente reprovar a frequência?");
                    if (decisao){
                        $("#analise").attr('value', 'reprova');
                        $("#form").attr('action', '${pageContext.request.contextPath}/estagios/analise/frequencia').submit();
                    }
                });
            });
        </script>

        <style>
            #calendar {
                width: 75%;
                margin: 0 auto;
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
                        left: '',
                        center: 'title',
                        right: ''
                    },                    
                    events: "${pageContext.request.contextPath}/estagios/frequencia/list.json/${estagio.id}"
                });
                
                $('#calendar').fullCalendar('gotoDate', ${ano} , ${mes});
            });
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
                                <c:if test="${empty analisada}">
                                    <ul>
                                        <li>
                                            <a href="javascript:void(0);" id="aprovar">
                                                <span width="32" height="32" border="0" class="icon-32-publish"></span>Aprovar
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" id="reprovar">
                                                <span width="32" height="32" border="0" class="icon-32-deny"></span>Reprovar
                                            </a>
                                        </li>
                                    </ul>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-calendar"><h2>Análise da Frequência do Estágio</h2></div>
            </div>
        </div>
        <c:if test="${not empty success}">
            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                    Frequência ${success} com sucesso!
                </p>
            </div>
            <br/>
        </c:if>
        <c:if test="${not empty analisada && empty success}">
            <div class="ui-corner-all alert" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    Obs.: Frequência já foi avaliada!
                </p>
            </div>
            <br/>
        </c:if>
        <c:if test="${empty analisda && not empty naoAprova && empty success}">
            <div class="ui-corner-all alert" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    Obs.: Você não está cadastrado com aprovador da frequência!
                </p>
            </div>
            <br/>
        </c:if>
        <c:if test="${empty analisada && empty naoAprova}">
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

            <form id="form" name="form" method="POST">
                <input type="hidden" id="idEstagio" name="idEstagio" value="${estagio.id}"/>
                <input type="hidden" id="mes" name="mes" value="${mes}"/>
                <input type="hidden" id="analise" name="analise"/>
                <input type="hidden" id="email" name="usuario.email" value="${usuario.email}"/>
                <div id='calendar'></div>
            </form>
        </c:if>
    </body>
</html>