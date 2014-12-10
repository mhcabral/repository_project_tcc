<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Solicitação [Exibição]</title>
    <style type="text/css">
        label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
    </style>
    <script>
        function alterar(estado){
            if (estado === 0) {
                decisao = confirm("Deseja realmente indeferir a solicitação?");
            } else {
                decisao = confirm("Deseja realmente deferir a solicitação?");
            }
            if (decisao){
                if (estado == 0) {
                    $('#campo-estado').val('Indeferido');
                } else {
                    $('#campo-estado').val('Deferido');
                }
                $('#formSolicitacao').submit();
            } else {
                alert ("Nenhuma solicitação foi alterada");
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
                                
                                <li>
                                    <a href="javascript:alterar(1);">
                                        <span width="32" height="32" border="0" class="icon-32-publish"></span>Deferir
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:alterar(0);">
                                        <span width="32" height="32" border="0" class="icon-32-deny"></span>Indeferir
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/tccsolicitacoes" id="back">
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
    <c:if test="${not empty tccSolicitacao.id}">
        <h4><p>Estado: ${tccSolicitacao.estado}</p></h4>
        <h4><p>Tipo: ${tccSolicitacao.tipo}</p></h4>
        <h4><p>Solicitação: ${tccSolicitacao.descricao}</p></h4>
        <br/>
        <h4><p>Informações do Aluno</p></h4>
        <p>
            <b>Matrícula:</b>
            ${tccSolicitacao.tcc.aluno.matricula}
        </p>
        <p>
            <b>Aluno:</b>
            ${tccSolicitacao.tcc.aluno.usuario.nome}
        </p>
        <p>
            <b>Curso:</b>
            ${tccSolicitacao.tcc.aluno.curso}
        </p>
        <br>

        <h4><p>Informações do TCC</p></h4>
        <p>
            <b>Título:</b>
            ${tccSolicitacao.tcc.titulo}
        </p>
        <p>
            <b>Descrição:</b>
            ${tccSolicitacao.tcc.descricao}
        </p>
        <p>
            <b>Anexos:</b>
        </p>
        <ul>
            <c:forEach items="${solicitacao.anexos}" var="anexo">
                <li><a href="${pageContext.request.contextPath}/analise/download/${anexo}" target="_blank">${anexo}</a></li>                    
            </c:forEach>
        </ul>
        <form id="formSolicitacao" method="POST" action="${pageContext.request.contextPath}/tccsolicitacoes">
            <p>
                <label for="campo-observacao">Observação:</label><img src="${pageContext.request.contextPath}/img/edit_lapis.png" width="16" height="16"><br/>
                <textarea rows="5" cols="51" id="campo-observacao" name="tccSolicitacao.observacao">${tccSolicitacao.observacao}</textarea>
                <input type="hidden" id="campo-id" name="tccSolicitacao.id" value="${tccSolicitacao.id}">
                <input type="hidden" id="campo-descricao" name="tccSolicitacao.descricao" value="${tccSolicitacao.descricao}">
                <input type="hidden" id="campo-estado" name="tccSolicitacao.estado" value="${tccSolicitacao.estado}">
                <input type="hidden" id="campo-tcc" name="tccSolicitacao.tcc.id" value="${tccSolicitacao.tcc.id}">
                <input type="hidden" id="campo-tipo" name="tccSolicitacao.tipo" value="${tccSolicitacao.tipo}">
            </p> 
        </form>
        
    </c:if>
</body>