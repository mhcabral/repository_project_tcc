<%-- 
    Document   : show
    Created on : 17/12/2014, 11:25:12
    Author     : TAMMY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Notas [Exibição]</title> 
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
                                    <a href="${pageContext.request.contextPath}/tccnotas/index" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>   
            <div class="pagetitle icon-48-info"><h2>Tabela de notas</h2></div>
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
    <c:if test="${not empty tccNotas.id}"> 
        <p>
            <b>Nome: </b>
            ${tccNotas.tcctcc.aluno}
        </p>
        <p>
            <b>Titulo: </b>
            ${tccNotas.tcctcc.titulo}
        </p>
        <p>
            <b>Nota Primeira entrega: </b>
            ${tccNotas.nota1}
        </p>
        <p>
            <b>Nota Segunda entrega: </b>
            ${tccNotas.nota2}
        </p> 
        <p>
            <b>Nota Workshop: </b>
            ${tccNotas.nota3}
        </p> 
        <p>
            <b>Media Final: </b>
            ${tccNotas.media}
        </p> 
    </c:if>
</body>
</html>