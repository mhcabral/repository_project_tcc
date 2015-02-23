<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Período de Cadastro de Monitorias</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>        

        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>

        <script type='text/javascript' src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js'></script>
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css' />
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css' media='print' />   
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
            #calendar {
                width: 500px;
                margin: 0 auto;
            }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formMonitoria").submit(); 
                }); 
                $("#formMonitoria").validate({
                    rules:{
                        "monitoria.curso.id":{
                            required: true                            
                        },
                        "monitoria.disciplina.id":{
                            required: true                            
                        },
                        "monitoria.professor.id":{
                            required: true
                        },
                        "monitoria.turma":{
                            required: true,
                            min: 1
                        },
                        "monitoria.vagas":{
                            required: true,
                            min: 1
                        }
                        
                    },
                    messages:{
                        "monitoria.curso.id":{
                            required: "Selecione o curso"
                        },
                        "monitoria.disciplina.id":{
                            required: "Selecione uma disciplina"
                        },
                        "monitoria.professor.id":{
                            required: "Selecione um professor"
                        },
                        "monitoria.turma":{
                            required: "Informe a turma",
                            min: "A turma deve ser um número positivo"
                        },
                        "monitoria.vagas":{
                            required: "Informe o número de vagas",
                            min: "O mínimo de vagas permitidas é 1"
                        }
                    }
                });
                
                var calendar = $('#calendar').fullCalendar({
                    theme: true,		
                    selectable: true,
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
                    events: "${pageContext.request.contextPath}/monitorias/list.json",
                    select: function(start, end, allDay) {
                        $("#start-time").val($.fullCalendar.formatDate(start, 'dd/MM/yyyy HH:mm'));
                        $("#end-time").val($.fullCalendar.formatDate(end, 'dd/MM/yyyy HH:mm'));
                        
                        $.ajax({
                            url: '<c:url value="/monitorias/inclui.json"/>',
                            type: 'post',
                            data: 'start='+$("#start-time").val()+'&end='+$("#end-time").val(),
                            error : function(txt) { 
                                alert('Não foi possível preencher a frequencia do dia');
                            },
                            success: function(data, textStatus, jqXHR){
                                $('#calendar').fullCalendar('refetchEvents');
                                $('#calendar').fullCalendar( 'render' );
                            }
                        });
                        
                        calendar.fullCalendar('unselect');
                    },
                    eventClick: function(calEvent, jsEvent, view) {
                        decisao = confirm("Deseja remover o horário?");
                        if (decisao) {
                            $.ajax({
                                url: '<c:url value="/monitorias/exclui.json"/>',
                                type: 'post',				    	  			   
                                data: 'idHorario='+calEvent.id,
                                error : function(txt) { 
                                    alert('Não foi possível remover o horário'); 
                                },
                                success: function(data, textStatus, jqXHR){
                                    $('#calendar').fullCalendar('refetchEvents');
                                    $('#calendar').fullCalendar( 'render' );
                                }
                            });
                            $('#calendar').fullCalendar('refetchEvents');
                            $('#calendar').fullCalendar( 'render' );
                        }
                    }
                });
                $('#calendar').fullCalendar('gotoDate', ${ano} , ${mes}, ${dia});
            });
            
            function somente_numero(campo){  
                var digits="0123456789"  
                var campo_temp   
                for (var i=0;i<campo.value.length;i++){  
                    campo_temp=campo.value.substring(i,i+1)   
                    if (digits.indexOf(campo_temp)==-1){  
                        campo.value = campo.value.substring(0,i);  
                    }  
                }  
            }            
            
        </script>   

        <script type="text/javascript"></script>


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
                                        <a href="javascript:void(0);" id="save" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-save"></span>Salvar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/monitorias">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Monitoria</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Monitoria</h2></div>
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
                    </p>
                </div>
            </div>   
        </c:if>

        <form id="formMonitoria" name="formMonitoria" method="POST" action="<c:url value="/monitorias"/>">
            <input type="hidden" id="start-time" name="start-time">
            <input type="hidden" id="end-time" name="end-time">
            <p>
                <c:if test="${not empty monitoria.id}">
                    <input type="hidden" name="monitoria.id" value="${monitoria.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                    <input type="hidden" name="monitoria.curso.id" value="${monitoria.curso.id}"/>
                </c:if>
            </p>
            <c:if test="${sessionData.usuario.perfil == 4}">
                <p>
                    Curso: {$curso}
                </p>
            </c:if>
            <p>
                <label for="campo-disciplina">Disciplina*:</label><br/>
                <select id="campo-disciplina" name="monitoria.disciplina.id" value="${monitoria.disciplina}" >
                    <option value="">Selecione uma Disciplina</option>
                    <c:forEach var="disciplina" items="${disciplinaList}">
                        <option value="${disciplina.id}" <c:if test = "${disciplina.id == monitoria.disciplina.id}"> selected</c:if>>${disciplina}</option>
                    </c:forEach>
                </select>
            </p> <p>
                <label for="campo-professor">Professor*:</label><br/>
                <select id="campo-professor" name="monitoria.professor.id" value="${monitoria.professor}" >
                    <option value="">Selecione um Professor</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option value="${professor.id}" <c:if test = "${professor.id == monitoria.professor.id}"> selected</c:if>>${professor}</option>
                    </c:forEach>
                </select>
            </p><p>
                <label for="campo-turma">Turma*:</label></br>
                <input onKeyUp="javascript:somente_numero(this);" size="10" type="text" id="campo-turma" name="monitoria.turma" value="${monitoria.turma}"/></br>
            </p><p>
                <label for="campo-vagas">Número de Vagas*:</label></br>
                <input onKeyUp="javascript:somente_numero(this);" size="10" type="text" id="campo-vagas" name="monitoria.vagas" value="${monitoria.vagas}"/></br>
            </p>
        </form>   

        <label for="quadro-horarios">Horários da Turma:</label></br>
        <p>Clique e arreste para preencher o horário de aulas da turma.</p>
        <div id='calendar'></div>
        <p>Obs.: Para remover um horário, selecione-o e confirme sua remoção.</p>
    </body>
</html>