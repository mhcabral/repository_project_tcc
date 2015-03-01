<%-- 
    Document   : main
    Created on : 13/04/2013, 02:57:13
    Author     : Bruna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estagio [Principal]</title>
        <script>
            function remove(){
                decisao = confirm("Deseja realmente remover a solicitação de estágio?");
                if (decisao){
                    document.location.href="${pageContext.request.contextPath}/estagios/${estagio.id}/remove";                                                              
                } else {
                    alert ("Nenhuma solicitação foi removida");
                }                            
                return;
            }

            $(function(){
                $('#enviar').click(function(e){
                    e.preventDefault();
                    $("#formRelatorioFinal").submit();
                });
                $("#formRelatorioFinal").validate({
                    submitHandler: function(form){
                        decisao = confirm("Deseja realmente enviar o relatório final?");

                        if(decisao){
                            form.submit();
                        }
                    },
                    rules:{
                        "relatorioFinal":{
                            required: true
                        }
                    },
                    messages:{
                        "relatorioFinal":{
                            required:"Adicione o relatório final."
                        }
                    }
                });
            });
        </script>
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>

        <style type="text/css">
            .desativa {
                pointer-events: none;
                cursor: default;
                box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                background: #f2f5f7;
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
                                    <li class="button" id="toolbar-apply" <c:if test="${empty sessionData.periodoEstagio || empty estagio.id || estagio.status != 'PENDENTE'}">title="Opção indisponível! Fora do período de inscrição em estágio ou nenhuma inscrição realizada"</c:if>>
                                        <a href="${pageContext.request.contextPath}/estagios/${estagio.id}/edit" id="edit" <c:if test="${empty sessionData.periodoEstagio || empty estagio.id || estagio.status != 'PENDENTE'}">class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-apply" <c:if test="${empty sessionData.periodoEstagio || empty estagio.id || estagio.status != 'PENDENTE'}">title="Opção indisponível! Fora do período de inscrição em estágio ou nenhuma inscrição realizada"</c:if>>
                                        <a href="javascript:remove()" <c:if test="${empty sessionData.periodoEstagio || empty estagio.id || estagio.status != 'PENDENTE'}">class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-apply" 
                                        <c:if test="${empty sessionData.periodoEstagio || not empty estagio.id}">title="Opção indisponível! Fora do período de inscrição em estágio ou inscrição já realizada"</c:if>>
                                        <a href="${pageContext.request.contextPath}/estagios/create" id="create" <c:if test="${empty sessionData.periodoEstagio || not empty estagio.id}">class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-new"></span>Novo
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-apply" <c:if test="${empty estagio.id || estagio.status != 'EMANDAMENTO'}">title="Opção indisponível! Nenhum estágio em andamento"</c:if>>
                                        <a href="javascript:void(0);" id="enviar" <c:if test="${empty estagio.id || estagio.status != 'EMANDAMENTO' || not empty bloqueio}"> class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-article"></span>Relatório<br/>Final
                                        </a>
                                    </li>

                                    <li class="button" id="toolbar-apply" <c:if test="${empty estagio.id || estagio.status != 'EMANDAMENTO'}">title="Opção indisponível! Nenhum estágio em andamento"</c:if>>
                                        <a href="${pageContext.request.contextPath}/estagios/${estagio.id}/frequencia" <c:if test="${empty estagio.id || estagio.status != 'EMANDAMENTO'}"> class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-calendar"></span>Frequência
                                        </a>
                                    </li>


                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/estagios" id="historico">
                                            <span width="32" height="32" border="0" class="icon-32-archive"></span>Todas as<br/>Inscrições
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-banners-clients"><h2>Estágio</h2></div>
            </div>
        </div>
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        ${success} de inscrição em estágio realizada com sucesso!
                    </p>
                </div>
                <br/>
            </c:if>
            <c:if test="${not empty email}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        Frequência foi enviada para análise do Supervisor e do Professor Orientador com sucesso!
                    </p>
                </div>
                <br/>
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

        <c:if test="${not empty sessionData.periodoEstagio}">
            <c:if test="${empty estagio.id}">
                <h4><p>Nenhuma solicitação de inscrição em estágio para este período</p></h4>
            </c:if>
        </c:if>
        <c:if test="${empty sessionData.periodoEstagio}">
            <c:if test="${empty estagio.id}">
                <h4><p>Nenhum estágio concluído</p></h4>
            </c:if>
        </c:if>

        <c:if test="${not empty estagio.id}">
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
            <p>
                <c:if test="${estagio.convenio == true}">Empresa <b>possui</b> convênio com a UFAM</c:if>
                <c:if test="${estagio.convenio == false}">Empresa <b>não possui</b> convênio com a UFAM</c:if>
            </p>
            <br/>   
            <h4>Documentação</h4>
            <p>
                <b>Termo de Compromisso:</b>
                <a href="${pageContext.request.contextPath}/estagios/download/${estagio.termoCompromisso}" target="_blank" >${estagio.termoCompromisso}</a>
            </p>
            <p>
                <b>Seguro:</b>
                <a href="${pageContext.request.contextPath}/estagios/download/${estagio.seguro}" target="_blank" >${estagio.seguro}</a>
            </p>
            <c:if test="${estagio.convenio == false}">
                <p>
                    <b>Ficha de Cadastro da Empresa:</b>
                    <a href="${pageContext.request.contextPath}/estagios/download/${estagio.fichaCadastroEmpresa}" target="_blank" >${estagio.fichaCadastroEmpresa}</a>
                </p>
            </c:if>
            <c:if test="${not empty estagio.relatorioFinal.relatorioFinal}">
                <p>
                    <b>Relatório Final:</b>
                    <br/><p>Obs.: O anexo deve contém o Relatório Final e as 3 fichas de avaliação.</p><br/>
                    <a href="${pageContext.request.contextPath}/estagios/download/${estagio.relatorioFinal.relatorioFinal}" target="_blank" >${estagio.relatorioFinal.relatorioFinal}</a><br/>
                </p>

                <br/><br/><h4>Aprovadores do Relatório Final</h4>

                <c:forEach items="${estagio.relatorioFinal.aprovacoes}" var="aprovacao">
                    <p>
                        <b>Nome:</b> ${aprovacao.nomeAprovador} <b>Status:</b> <c:if test="${aprovacao.aprovou == null}">Em análise</c:if><c:if test="${aprovacao.aprovou == true}">Aprovado</c:if><c:if test="${aprovacao.aprovou == false}">Reprovado</c:if>
                    </p>
                </c:forEach>            
            </c:if>
            <c:if test="${estagio.status == 'EMANDAMENTO'}">                
                <b>Novo Relatório Final:</b>
                <form id="formRelatorioFinal" method="POST" action="${pageContext.request.contextPath}/estagios/uploadRelatorio" enctype="multipart/form-data">
                    <input type="file" name="relatorioFinal"/>
                    <input type="hidden" name="estagio.id" value="${estagio.id}"/>
                </form>           
            </c:if>
        </c:if>
    </body>
</html>
