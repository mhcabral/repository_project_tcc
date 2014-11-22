<%-- 
    Document   : show
    Created on : 11/03/2013, 11:03:50
    Author     : Bruna
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Período Letivo [Exibição]</title>
        <script>
            function remove() {
                decisao = confirm("Deseja realmente remover o Período?");
                if (decisao) {
                    document.location.href = "${pageContext.request.contextPath}/periodosLetivo/${periodoLetivo.id}/remove";
                } else {
                    alert("Nenhum Período foi removido");
                }
                return;
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
                                    <li class="button" id="toolbar-apply">
                                        <a href="${pageContext.request.contextPath}/periodosLetivo/${periodoLetivo.id}/edit" id="edit" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:remove()">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/periodosLetivo" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informações do Período Letivo</h2></div>
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
        <c:if test="${not empty periodoLetivo.id}">
            <p>
                <b>Código:</b>
                ${periodoLetivo.codigo}
            </p> 
            <p>
                <b>Data de Início:</b>
                <fmt:formatDate value="${periodoLetivo.dtInicio}" pattern="dd/MM/yyyy"/>
            </p> 
            <p>
                <b>Data de Término:</b>
                <fmt:formatDate value="${periodoLetivo.dtFim}" pattern="dd/MM/yyyy"/>
            </p>
            <p>
                <b>Data de Início Matrícula:</b>
                <fmt:formatDate value="${periodoLetivo.dtInicioMatricula}" pattern="dd/MM/yyyy"/>
            </p> 
            <p>
                <b>Data de Término Matrícula:</b>
                <fmt:formatDate value="${periodoLetivo.dtFimMatricula}" pattern="dd/MM/yyyy"/>
            </p>
            <p>
                <b>Data de Início Estágio:</b>
                <fmt:formatDate value="${periodoLetivo.dtInicioEstagio}" pattern="dd/MM/yyyy"/>
            </p> 
            <p>
                <b>Data de Término Estágio:</b>
                <fmt:formatDate value="${periodoLetivo.dtFimEstagio}" pattern="dd/MM/yyyy"/>
            </p>            
            <p>
                <b>Status:</b>
                ${periodoLetivo.status}
            </p>                 
        </c:if>
    </body>
</html>
