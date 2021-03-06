<%-- 
    Document   : show
    Created on : 13/04/2013, 01:56:37
    Author     : Bruna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Estágio [Exibição]</title>
        <script type="text/javascript">
            $(function(){
                $('#aprovar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente aprovar o Estágio?");
                    if (decisao){                        
                        $("#analise").attr('value', 'aprovado');
                        $("#formAnalise").attr('action', '${pageContext.request.contextPath}/analise/estagios/avaliacao').submit();
                    }
                });
            });
            
            $(function(){
                $('#reprovar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente reprovar o Estágio?");
                    if (decisao){                
                        $("#analise").attr('value', 'reprovado');
                        $("#formAnalise").attr('action', '${pageContext.request.contextPath}/analise/estagios/avaliacao').submit();
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
                                        <a href="javascript:void(0);" id="aprovar">
                                            <span width="32" height="32" border="0" class="icon-32-assign"></span>Aprovar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="javascript:void(0);" id="reprovar">
                                            <span width="32" height="32" border="0" class="icon-32-deny"></span>Reprovar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/analise/estagios" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informações do Estágio</h2></div>
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
                <a href="${pageContext.request.contextPath}/analise/estagios/download/${estagio.termoCompromisso}" target="_blank" >${estagio.termoCompromisso}</a>
            </p>
            <p>
                <b>Seguro:</b>
                <a href="${pageContext.request.contextPath}/analise/estagios/download/${estagio.seguro}" target="_blank" >${estagio.seguro}</a>
            </p>
            <c:if test="${estagio.convenio == false}">
                <p>
                    <b>Ficha de Cadastro da Empresa:</b>
                    <a href="${pageContext.request.contextPath}/analise/estagios/download/${estagio.fichaCadastroEmpresa}" target="_blank" >${estagio.fichaCadastroEmpresa}</a>
                </p>
            </c:if>
        </c:if>
        <form id="formAnalise" name="formAnalise" method="POST">
            <input type="hidden" name="estagio.id" value="${estagio.id}"/>
            <input type="hidden" name="_method" value="PUT"/>  
            <input type="hidden" id="analise" name="analise"/>
        </form>
    </body>
</html>