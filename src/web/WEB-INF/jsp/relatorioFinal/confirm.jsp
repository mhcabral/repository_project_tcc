<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Análise de Relatório</title>
    <script laguage="Javascript" type="text/javascript">
        $(function() {
            $('#aprovar').click(function(e) {
                e.preventDefault();
                decisao = confirm("Deseja realmente aprovar o relatório final?");
                if (decisao){
                    $("#analise").attr('value', 'aprova');
                    $("#formRelatorio").attr('action', '${pageContext.request.contextPath}/inscricoes/relatorioFinal/analiseAprovador').submit();
                }
            });
            $('#reprovar').click(function(e) {
                e.preventDefault();
                decisao = confirm("Deseja realmente reprovar o relatório final?");
                if (decisao){
                    $("#analise").attr('value', 'reprova');
                    $("#formRelatorio").attr('action', '${pageContext.request.contextPath}/inscricoes/relatorioFinal/analiseAprovador').submit();
                }
            });
        });
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
                                <li class="button" id="toolbar-cancel">
                                    <a href="javascript:void(0);" id="aprovar" class="toolbar">
                                        <span width="32" height="32" border="0" class="icon-32-publish"></span>Aprovar
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="javascript:void(0);" id="reprovar">
                                        <span width="32" height="32" border="0" class="icon-32-deny"></span>Reprovar
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
            <b>Disciplina:</b>
            ${inscricaoMonitoria.monitoria.disciplina}

            <b style="padding-left:15px">Turma:</b>
            ${inscricaoMonitoria.monitoria.turma}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricaoMonitoria.monitoria.professor}
        </p>
        <p>
            <b>Aluno:</b>
            ${inscricaoMonitoria.inscrito.usuario.nome}
        </p>

        <c:if test="${not empty inscricaoMonitoria.relatorioFinal}">
            <p>
                <b>Relatório Final:</b>
                <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal/${inscricaoMonitoria.relatorioFinal.relatorioFinal}/download" target="_blank">${inscricaoMonitoria.relatorioFinal.relatorioFinal}</a>
            </p>
        </c:if>
    </c:if>
    <form method="POST" id="formRelatorio" name="formRelatorio">
        <input type="hidden" id="analise" name="analise"/>
        <input type="hidden" id="idInscricao" name="inscricaoMonitoria.id" value="${inscricaoMonitoria.id}"/>
        <input type="hidden" id="idUsuario" name="usuario.email" value="${usuario.email}"/>
    </form>
</body>