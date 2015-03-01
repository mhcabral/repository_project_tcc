<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Cadastro de Monitorias [Exibição]</title>

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>        

        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>

        <script type='text/javascript' src="${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js"></script>
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css" />
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css" media="print" />  
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
            #calendar {
                width: 500px;
                margin: 0 auto;
            }
        </style>
        <script>
            function remove(){
                decisao = confirm("Deseja realmente remover a Monitoria?");
                if (decisao){
                    document.location.href="${pageContext.request.contextPath}/monitorias/${monitoria.id}/remove";                                                              
                } else {
                    alert ("Nenhuma Monitoria foi removida");
                }                            
                return;
            }
        </script>

        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                var calendar = $('#calendar').fullCalendar({
                    theme: true,		
                    selectable: false,
                    weekends: false,
                    defaultView: 'agendaWeek',
                    allDaySlot: false,
                    maxTime: 22,
                    minTime: 6,
                    axisFormat: 'H:mm',
                    header: {
                        left: '',
                        center: '',
                        right: ''
                    },                    
                    columnFormat: {
                        week: 'ddd'
                    },
                    events: "${pageContext.request.contextPath}/monitorias/list.json"
                });
                $('#calendar').fullCalendar('gotoDate', ${ano} , ${mes}, ${dia});
            });
        </script> 
        <style>
            .grid-notas td{
                border-bottom:1px solid #74B2E2;
            }
            .wc-cal-event {
                cursor:auto;
            }
        </style>
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
                                        <a href="${pageContext.request.contextPath}/monitorias/${monitoria.id}/edit" id="edit" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:remove()">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/monitorias" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informações da Monitoria</h2></div>
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
        <c:if test="${not empty monitoria.id}">
            <c:if test="${sessionData.usuario.perfil == 4}">
                <p>
                    <b>Curso:</b>
                    ${curso}
                </p>
            </c:if>
            <p>
                <b>Disciplina:</b>
                ${monitoria.disciplina}
            </p> 
            <p>
                <b>Professor:</b>
                ${monitoria.professor}
            </p>         
            <p>
                <b>Turma:</b>
                ${monitoria.turma}
            </p>                 
            <p>
                <b>Vagas:</b>
                ${monitoria.vagas}
            </p>
            <p>
                <b>Horários da Turma:</b>
            </p>    
            <div id='calendar'></div>
        </c:if>
    </body>
</html>