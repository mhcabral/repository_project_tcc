<%-- 
    Document   : analiseFrequencia
    Created on : 06/04/2013, 20:05:05
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
                        $("#form").attr('action', '${pageContext.request.contextPath}/analise/frequencia').submit();
                    }
                });
                $('#reprovar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente reprovar a frequência?");
                    if (decisao){
                        $("#analise").attr('value', 'reprova');
                        $("#form").attr('action', '${pageContext.request.contextPath}/analise/frequencia').submit();
                    }
                });
            });
        </script>

        <style>
            #calendarBolsista {
                width: 400px;
                margin: 0 auto;
            }
            #calendarNaoBolsista {
                width: 400px;
                margin: 0 auto;
            }
        </style>

        <script type='text/javascript'>
            $(document).ready(function() {
                var inicioPeriodo = new Date($("#inicio").val()); 
                var fimPeriodo = new Date($("#fim").val());
                
                var calendar = $('#calendarBolsista').fullCalendar({
                    theme: true,		
                    selectable: false,
                    weekends: false,
                    header: {
                        left: '',
                        center: 'title',
                        right: ''
                    },                    
                    events: "${pageContext.request.contextPath}/frequencia/list.json/${inscricao.id}"
                });

                var calendar2 = $('#calendarNaoBolsista').fullCalendar({
                    theme: true,		
                    selectable: false,
                    weekends: false,
                    header: {
                        left: 'prev',
                        center: 'title',
                        right: 'next'
                    },
                    events: "${pageContext.request.contextPath}/frequencia/list.json/${inscricao.id}",
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
                
                $('#calendarBolsista').fullCalendar('gotoDate', ${ano} , ${mes});
                $('#calendarNaoBolsista').fullCalendar('gotoDate', ${ano} , ${mes});
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
                <div class="pagetitle icon-48-calendar"><h2>Análise da${s} Frequência${s} da Monitoria</h2></div>
            </div>
        </div>
        <c:if test="${not empty success}">
            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                    Frequencia${s} ${success} com sucesso!
                </p>
            </div>
            <br/>
        </c:if>
        <c:if test="${not empty analisada && empty success}">
            <div class="ui-corner-all alert" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                    Obs.: Frequencia já foi avaliada!
                </p>
            </div>
            <br/>
        </c:if>
        <c:if test="${empty analisada}">
            <p>
                <b>Disciplina:</b>
                ${inscricao.monitoria.disciplina}
            </p>
            <p>
                <b>Professor:</b>
                ${inscricao.monitoria.professor}
            </p>
            <p>
                <b>Turma:</b>
                ${inscricao.monitoria.turma}
            </p>
            <p>
                <b>Curso: </b>
                ${inscricao.monitoria.curso}
            </p>

            <form id="form" name="form" method="POST">
                <input type="hidden" id="idInscricao" name="idInscricao" value="${inscricao.id}"/>
                <input type="hidden" id="mes" name="mes" value="${mes}"/>
                <input type="hidden" id="analise" name="analise"/>
                <c:if test="${inscricao.bolsista == true}">
                    <div id='calendarBolsista'></div>
                </c:if>
                <c:if test="${inscricao.bolsista == false}">
                    <div id='calendarNaoBolsista'></div>
                </c:if>
            </form>
        </c:if>
    </body>
</html>