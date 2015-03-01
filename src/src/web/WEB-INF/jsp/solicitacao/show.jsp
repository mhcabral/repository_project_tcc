<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <title>Solicitação [Exibição]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover a solicitação?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/solicitacoes/${solicitacao.id}/remove";
            } else {
                alert ("Nenhuma Solicitação foi removida");
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
                                <c:if test="${solicitacao.statusAtual == 'SALVA' || solicitacao.statusAtual == 'INDEFERIDA'}">
                                    <li class="button" id="toolbar-apply">
                                        <a href="${pageContext.request.contextPath}/solicitacoes/${solicitacao.id}/edit" id="edit" class="toolbar">
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
                                    <a href="${pageContext.request.contextPath}/solicitacoes" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Solicitação</h2></div>
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
    <c:if test="${not empty solicitacao.id}">
        <c:if test="${solicitacao.statusAtual == 'DEFERIDA'}">
            <c:if test="${solicitacao.horasComputadas == 0}">
                <div class="ui-widget">
                    <div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p>
                            <span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
                            Obs.: Atingido o máximo de horas possíveis para o Curso ou Grupo da atividade.
                        </p>
                    </div>
                </div>
            </c:if>
            <c:if test="${solicitacao.horasComputadas > 0 && solicitacao.horasComputadas < solicitacao.qntd_horas}">
                <div class="ui-widget">
                    <div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p>
                            <span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
                            Obs.: Máximo de horas que pode ser computado pelo Curso ou Grupo da atividade.
                        </p>
                    </div>
                </div>
            </c:if>
        </c:if>
        <p>
            <b>Status Atual:</b>
            ${solicitacao.statusAtual.nome}
        </p>
        <p>
            <b>Atividade:</b>
            ${solicitacao.atividade}
        </p>
        <p>
            <b>Descrição:</b>
            ${solicitacao.descricao}
        </p>
        <p>
            <b>Quantidades de Horas Solicitadas:</b>
            ${solicitacao.qntd_horas}
        </p>
        <c:if test="${solicitacao.statusAtual == 'DEFERIDA'}">
            <p>
                <b>Quantidade de Horas Computadas:</b>
                ${solicitacao.horasComputadas}
            </p>
        </c:if>
        <p>
            <b>Observações:</b>
            ${solicitacao.observacoes}
        </p>
        <p>
            <b>Data Início:</b>
            <fmt:formatDate value="${solicitacao.dataInicio}" pattern="dd/MM/yyyy"/>
        </p>
        <p>
            <b>Data Término:</b>
            <fmt:formatDate value="${solicitacao.dataTermino}" pattern="dd/MM/yyyy"/>
        </p>
        <p>
            <b>Anexos:</b>                
        <ul>
            <c:forEach items="${solicitacao.anexos}" var="anexo">
                <li><a href="${pageContext.request.contextPath}/analise/download/${anexo}" target="_blank" >${anexo}</a></li>                    
            </c:forEach>
        </ul>
    </p>
    <br>
    <h4><p>Histórico de Alterações</p></h4>
    <table style="border: solid 1px #8099B3" width="100%">
        <tr>
            <td width="10%" style="border: solid 1px #8099B3">
                <b>Data</b>
            </td>
            <td width="10%" style="border: solid 1px #8099B3">
                <b>Status</b>
            </td>
            <td width="25%" style="border: solid 1px #8099B3">
                <b>Responsável</b>
            </td>
            <td width="55%" style="border: solid 1px #8099B3">
                <b>Observação</b>
            </td>
        </tr>
        <c:forEach items="${solicitacao.mudancas}" var="mudanca">
            <tr>
                <td style="border: solid 1px #8099B3">
                    <fmt:formatDate value="${mudanca.dataMudanca}" pattern="dd/MM/yyyy"/>
                </td>
                <td style="border: solid 1px #8099B3">
                    ${mudanca.status}
                </td>
                <td style="border: solid 1px #8099B3">
                    ${mudanca.responsavel}
                </td>
                <td style="border: solid 1px #8099B3">
                    ${mudanca.observacao}
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>