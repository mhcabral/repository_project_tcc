<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Monitoria [Exibição]</title>
    <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type='text/javascript' src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
    <script type='text/javascript' src="${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js"></script>
    <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css" />
    <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css" media="print" />   

    <script>
        function deferir(id){
            if(confirm("Deseja realmente APROVAR a monitoria?")){
                document.location.href="${pageContext.request.contextPath}/analiseFinal/" + id + "/defere";
            } 
        }
        
        function indeferir(id){
            if(confirm("Deseja realmente Reprovar a monitoria?")){
                document.location.href="${pageContext.request.contextPath}/analiseFinal/" + id + "/indefere";
            } 
        }        
        
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
                events: "${pageContext.request.contextPath}/frequencia/list.json/${inscricaoMonitoria.id}",
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
        
        function getMonth(i){
            var months = new Array('Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro');
            return months[i];
        }
        
        function showCalendar(){
            $('#calendar').show();
            $('#calendar').fullCalendar( 'render' );
        }
    </script>
    <style>
        #calendar {
            width: 40%;
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
                                <li>
                                    <a href="javascript:deferir(${inscricaoMonitoria.id})" if="deferir">
                                        <span width="32" height="32" border="0" class="icon-32-publish"></span>Aprovar
                                    </a>
                                </li>

                                <li>
                                    <a href="javascript:indeferir(${inscricaoMonitoria.id})" id="indeferir">
                                        <span width="32" height="32" border="0" class="icon-32-deny"></span>Reprovar
                                    </a>
                                </li>

                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/analiseFinal" id="back">
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
    <div>
        <c:if test="${not empty success}">
            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                    ${success}!
                </p>
            </div>
        </c:if>
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
    <br/>
    <c:if test="${not empty inscricaoMonitoria.id}">

        <h4><p>Informações da Monitoria</p></h4>
        <p>
            <b>Curso:</b>
            ${inscricaoMonitoria.monitoria.curso}
        </p>
        <p>
            <b>Disciplina:</b>
            ${inscricaoMonitoria.monitoria.disciplina}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricaoMonitoria.monitoria.professor}
        </p>
        <p>
            <b>Turma:</b>
            ${inscricaoMonitoria.monitoria.turma}
        </p>

        <br>

        <c:if test="${!inscricao.bolsista}">
            <h4><p>Não Bolsista</p></h4>
        </c:if>
        <br/>
        <c:if test="${inscricao.bolsista}">

            <h4><p>Bolsista</p></h4>


            <h4><p>Informações Bancárias</p></h4>
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

        <br>

        <h4><p>Informações do Aluno</p></h4>
        <p>
            <b>Matrícula:</b>
            ${inscricaoMonitoria.inscrito.id}
        </p>
        <p>
            <b>Aluno:</b>
            ${inscricaoMonitoria.inscrito}
        </p>
        <p>
            <b>Curso:</b>
            ${inscricaoMonitoria.monitoria.curso}
        </p>
        <br/>
        <p>
            <b>Relatório Final:</b>
            <c:if test="${not empty inscricaoMonitoria.relatorioFinal}">
                <a href="${inscricaoMonitoria.relatorioFinal.relatoriaFinal}" target="_blank">Download</a>
            </c:if>
        </p>
        <div>
            <c:if test="${empty inscricaoMonitoria.relatorioFinal}">
                RELATÓRIO FINAL NÃO ENVIADO
            </c:if>
            <c:if test="${not empty inscricaoMonitoria.relatorioFinal}">
                <table>
                    <tr style="border: solid 1px #8099B3">
                        <td style="border: solid 1px #8099B3">
                            <b>Aprovador</b>
                        </td>
                        <td style="border: solid 1px #8099B3">
                            <b>Análise</b>
                        </td>
                        <c:forEach items="${inscricaoMonitoria.relatorioFinal.aprovacoes}" var="aprovacao">
                            <td style="border: solid 1px #8099B3">
                                aprovacao.nomeAprovador
                            </td>
                            <td style="border: solid 1px #8099B3">
                                <c:if test="${aprovacao.aprovou == true}">
                                    Aprovado
                                </c:if>
                                <c:if test="${aprovacao.aprovou == false}">
                                    Rejeitado ou Pendente
                                </c:if>
                            </td>
                        </c:forEach>
                    </tr>
                </table>
            </c:if>
        </div>

        <h4>Frequências</h4>
        <br/>
        <div>
            <table width="15%" style="border: solid 1px #8099B3">
                <tr>
                    <td width="5%" style="border: solid 1px #8099B3">
                        <b>Mês</b>
                    </td>
                    <td width="5%" style="border: solid 1px #8099B3">
                        <b>Análise do Professor</b>
                    </td>
                </tr>
                <c:forEach items="${freqAnalisadas}" var="frequencia">
                    <tr>
                        <c:if test="${frequencia.status == 'VAZIA' || frequencia.status == 'SALVA'}">
                            <td colspan="2">
                                FREQUÊNCIA NÃO ENVIADA
                            </td>
                        </c:if>
                        <c:if test="${frequencia.status != 'VAZIA' && frequencia.status != 'SALVA'}">
                            <td style="border: solid 1px #8099B3">
                                <script>
                                document.write( getMonth(${frequencia.mes}));
                                </script>
                            </td>
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
        <input type="hidden" id="inicio" name="inicio" value="${inicioPeriodo}"/>
        <input type="hidden" id="fim" name="fim" value="${fimPeriodo}"/>
    </c:if>
</body>