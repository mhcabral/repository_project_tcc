<%-- 
    Document   : form
    Created on : 12/12/2014, 22:53:47
    Author     : TAMMY
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formTccAvaliador").submit(); 
                }); 
                $("#formTccAvaliador").validate({
                    rules:{
                        "tccAvaliador.nome":{
                            required:true
                        },
                        "tccAvaliador.email":{
                            required: true
                        }
                    },
                    messages:{
                        "tccAvaliador.nome":{
                            required: "Informe o nome do avaliador"
                        },
                        "tccAvaliador.email":{
                            required: "Informe o e-mail do avaliador"
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
                                    <li class="button" id="toolbar-apply">
                                        <a href="javascript:void(0);" id="save" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-save"></span>Salvar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/tccavaliador">
                                            <span width="32" height="32" border="0" class="icon-32-cancel"></span>Cancelar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <c:if test="${operacao == 'Cadastro'}">
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Avaliador</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Avaliador</h2></div>
                </c:if>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                        <strong>Situações não permitidas:</strong>
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li style="color: #cd0a0a">${error.message}</li>
                        </c:forEach>        
                    </ul>
                    </p>
                </div>
            </div>   
        </c:if>

        <form id="formTccAvaliador" name="formTccAvaliador" method="POST" action="<c:url value="/tccavaliador"/>"> 
            <p>
                <c:if test="${not empty tccAvaliador.id}">
                    <input type="hidden" name="tccAvaliador.id" value="${tccAvaliador.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p> 
            <p>
                <label for="nome">Nome Completo, sem abreviações*:</label><br>
                <input type="text" id="nome" name="tccAvaliador.nome" value="${tccAvaliador.nome}" size="100"/>
            </p>
            <p>
                <label for="email">E-mail*:</label>
                <input type="text" id="descricao" name="tccAvaliador.email" value="${tccAvaliador.email}" size="100" />
            </p>
        </form>
    </body>
</html>