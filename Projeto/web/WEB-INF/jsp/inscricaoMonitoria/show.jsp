<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <title>Solicitação [Exibição]</title>
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
        #calendar {
            width: 500px;
            margin: 0 auto;
        }
    </style>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover a inscrição?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/inscricoes/${inscricao.id}/remove";
            } else {
                alert ("Nenhuma Solicitação foi removida");
            }
        }
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
    <style type="text/css">
        .desativa {
            pointer-events: none;
            cursor: default;
            box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            background: #f2f5f7;
        }
        a{
            background: #fff;
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
                                <c:if test="${inscricao.statusAtual == 'SALVA'}">
                                    <li class="button" id="toolbar-apply">
                                        <a href="${pageContext.request.contextPath}/inscricoes/${inscricao.id}/edit" id="edit" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:remove()">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                </c:if>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/InsMonitoria" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Inscrição</h2></div>
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
    <c:if test="${not empty inscricao.id}">
        <c:if test="${inscricao.statusAtual == 'SALVA'}">
            <c:if test="${inscricao.monitoriasComputadas == 4}">
                <div class="ui-widget">
                    <div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p>
                            <span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
                            Obs.: Atingido o máximo de monitorias possíveis.
                        </p>
                    </div>
                </div>
            </c:if>
            <c:if test="${inscricao.monitoriasComputadas > 0 && inscricao.monitoriasComputadas <= 4 }">
                <div class="ui-widget">
                    <div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p>
                            <span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
                            Obs.: Máximo de monitorias feitas.
                        </p>
                    </div>
                </div>
            </c:if>
        </c:if>
        <p>
            <b>Status Atual:</b>
            ${inscricao.statusAtual.nome}
        </p>
        <p>
            <b>Disciplina:</b>
            ${inscricao.monitoria.disciplina}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricao.monitoria.professor}
        </p>
        <c:if test="${!inscricao.bolsista}">
            <p>
                <b>Interesse em bolsa: </b>
                Não
            </p>
        </c:if>
        <c:if test="${inscricao.bolsista}">
            <p>
                <b>Interesse em bolsa: </b>
                Sim
            </p>
            <p>
                <b>Banco:</b>
                ${inscricao.banco}
            </p>
            <p>
                <b>Agência:</b>
                ${inscricao.agencia}
            </p>
            <p>
                <b>Conta:</b>
                ${inscricao.conta}
            </p>
        </c:if>
        <p>
            Horários da Turma:
        </p>    
        <div id='calendar'></div>
        <br>
    </c:if>
</body>