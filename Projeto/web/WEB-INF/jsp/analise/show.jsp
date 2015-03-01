<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Solicitação [Exibição]</title>
    <style type="text/css">
        label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
    </style>
    <script>
        function deferir(id){
            if('${operacao}' == 'Deferir'){
                decisao = confirm("Deseja realmente deferir a solicitação?");
            } else {
                decisao = confirm("Deseja realmente aprovar a solicitação?");
            }
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/analise/" + id + "/defere";
            } else {
                if('${operacao}' == 'Deferir'){
                    alert ("Nenhuma solicitação foi deferida");
                } else {
                    alert ("Nenhuma solicitação foi aprovada");
                }
            }
        }                
        
        $(document).ready(function() {
            $('#indeferir').click(function(e){ 
                e.preventDefault();
                $('#formDeferir').submit();                
            });
            $("#formDeferir").validate({                               
                submitHandler: function(form){
                    confirmacao = confirm("Deseja realmente indeferir essa solicitação?");
                    
                    if(confirmacao){
                        form.submit();    
                    }                    
                },
                rules:{                
                    "mudanca.observacao":{
                        maxlength: 255,
                        required: true
                    }
                },
                messages:{                
                    "mudanca.observacao":{
                        maxlength: "A justificativa deve conter no máximo 255 caracteres",
                        required: "Informe justificativa do indeferimento da solicitacao"
                    }
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
                                <c:if test="${solicitacao.statusAtual == 'NOVA' || solicitacao.statusAtual == 'REENVIADA' || (solicitacao.statusAtual == 'PREAPROVADA' && perfil == 'COORDENADOR')}">
                                    <li>
                                        <a href="javascript:deferir(${solicitacao.id})">
                                            <span width="32" height="32" border="0" class="icon-32-publish"></span>${operacao}
                                        </a>
                                    </li>

                                    <!--alterei aqui-->

                                    <li>
                                        <a href="javascript:void(0);" id="indeferir">
                                            <span width="32" height="32" border="0" class="icon-32-deny"></span>Indeferir
                                        </a>
                                    </li>

                                    <!------------------------->

                                    <li>
                                        <a href="${pageContext.request.contextPath}/analise/${solicitacao.id}/altera">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Ajustar
                                        </a>
                                    </li>
                                </c:if>

                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/analise" id="back">
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
    <c:if test="${not empty solicitacao.id}">
        <h4><p>Status Atual: ${solicitacao.statusAtual.nome}</p></h4>
        <br/>
        <h4><p>Informações do Aluno</p></h4>
        <p>
            <b>Matrícula:</b>
            ${solicitacao.solicitante.matricula}
        </p>
        <p>
            <b>Aluno:</b>
            ${solicitacao.solicitante.usuario.nome}
        </p>
        <p>
            <b>Curso:</b>
            ${solicitacao.solicitante.curso}
        </p>
        <br>

        <h4><p>Informações da Atividade</p></h4>
        <p>
            <b>Atividade:</b>
            ${solicitacao.atividade}
        </p>
        <p>
            <b>Descrição:</b>
            ${solicitacao.descricao}
        </p>
        <p>
            <b>Quantidades de Horas:</b>
            ${solicitacao.qntd_horas}
        </p>
        <p>
            <b>Observações:</b>
            ${solicitacao.observacoes}
        </p>
        <p>
            <b>Data Início:</b>
            ${solicitacao.dataInicio}
        </p>
        <p>
            <b>Data Término:</b>
            ${solicitacao.dataTermino}
        </p>
        <p>
            <b>Anexos:</b>
        </p>
        <ul>
            <c:forEach items="${solicitacao.anexos}" var="anexo">
                <li><a href="${pageContext.request.contextPath}/analise/download/${anexo}" target="_blank">${anexo}</a></li>                    
            </c:forEach>
        </ul>
        <c:if test="${solicitacao.statusAtual == 'NOVA' || solicitacao.statusAtual == 'REENVIADA' || solicitacao.statusAtual == 'PREAPROVADA' || solicitacao.statusAtual == 'INDEFERIDA'}">
            <form id="formDeferir" method="POST" action="${pageContext.request.contextPath}/analise/${solicitacao.id}/indeferir">
                <p>
                    <label for="campo-justificativa">Justificativa do indeferimento*:</label><c:if test="${solicitacao.statusAtual != 'INDEFERIDA' && solicitacao.statusAtual != 'DEFERIDA'}"><img src="${pageContext.request.contextPath}/img/edit_lapis.png" width="16" height="16"></c:if><br/>
                    <textarea rows="5" cols="51" id="campo-justificativa" name="mudanca.observacao" <c:if test="${solicitacao.statusAtual == 'INDEFERIDA' || solicitacao.statusAtual == 'DEFERIDA'}"> disabled="true"</c:if>>${mudanca.observacao}</textarea>
                </p> 
            </form>
        </c:if>
    </c:if>
</body>