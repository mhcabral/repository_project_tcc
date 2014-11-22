<%-- 
    Document   : success
    Created on : 14/04/2013, 10:14:46
    Author     : Thiago Santos
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Estágio</title>
</head>
<body>
    <div id="toolbar-box">
        <div class="m">
            <div class="toolbar-list" id="toolbar">
                <div class="cpanel2">                    
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Estágio Supervisionado</h2></div>
        </div>
    </div>

    <c:if test="${not empty success}">
        <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
            <p>
                <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                Relatório Final de Estágio ${success} com sucesso!
            </p>
        </div>
    </c:if>
</body>

