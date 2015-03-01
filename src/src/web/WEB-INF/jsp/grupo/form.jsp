<%-- 
    Document   : form
    Created on : 17/02/2013, 15:06:19
    Author     : Bruna
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
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formGrupo").submit(); 
                }); 
                $("#formGrupo").validate({
                    rules:{
                        "grupo.codigo":{
                            required: true,
                            maxlength: 10
                        },
                        "grupo.nome":{
                            required: true,
                            maxlength: 150
                        }
                    },
                    messages:{
                        "grupo.codigo":{
                            required: "Informe o código do grupo",
                            maxlength: "O código deve conter no máximo 10 caracteres"
                        },
                        "grupo.nome":{
                            required: "Informe o nome do grupo",
                            maxlength: "O grupo deve conter no máximo 150 caracteres"
                        }
                    }
                });
            });
            
        </script>
        <script language="javascript">  
            function somente_numero(campo){  
                var digits="0123456789"  
                var campo_temp   
                for (var i=0;i<campo.value.length;i++){  
                    campo_temp=campo.value.substring(i,i+1)   
                    if (digits.indexOf(campo_temp)==-1){  
                        campo.value = campo.value.substring(0,i);  
                    }  
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
                                    <li class="button" id="toolbar-apply">
                                        <a href="javascript:void(0);" id="save" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-save"></span>Salvar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/grupos">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Grupo</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Grupo</h2></div>
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

        <form id="formGrupo" name="formGrupo" method="POST" action="<c:url value="/grupos"/>"> 
            <p>
                <c:if test="${not empty grupo.id}">
                    <input type="hidden" name="grupo.id" value="${grupo.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p> <p>
                <label for="campo-codigo">Codigo*:</label></br>
                <input size="10" type="text" id="campo-codigo" name="grupo.codigo" value="${grupo.codigo}"/></br>
            </p><p>
                <label for="campo-grupo">Grupo*:</label></br>
                <input size="50" type="text" id="campo-grupo" name="grupo.nome" value="${grupo.nome}"/></br>                         
            </p>
        </form>
    </body>
</html>