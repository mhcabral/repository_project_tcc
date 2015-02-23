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
                    $("#formProfessor").submit(); 
                }); 
                $("#formProfessor").validate({
                    rules:{
                        "professor.usuario.cpf":{
                            required:true,
                            maxlength: 14,
                            cpf: 'both'
                        },
                        "professor.usuario.nome":{
                            required: true,
                            maxlength: 255
                        },
                        "professor.usuario.email":{
                            required: true,
                            email: true,
                            maxlength: 255
                        }
                    },
                    messages:{
                        "professor.usuario.cpf":{
                            required: "Informe o CPF do professor",
                            maxlength: "O CPF deve conter no máximo 11 caracteres"
                        },
                        "professor.usuario.nome":{
                            required: "Informe o nome do professor",
                            maxlength: "O nome deve conter no máximo 255 caracteres"
                        },
                        "professor.usuario.email":{
                            required: "Informe o e-mail do professor",
                            email: "Informe um e-mail válido",
                            maxlength: "O e-mail deve conter no máximo 255 caracteres"
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
            
            $(document).ready(function(){
                $("#campo-cpf").mask("999.999.999-99");
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
                                        <a href="${pageContext.request.contextPath}/professores">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Professor</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Professor</h2></div>
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

        <form id="formProfessor" name="formProfessor" method="POST" action="<c:url value="/professores"/>"> 
            <p>
                <c:if test="${not empty professor.id}">
                    <input type="hidden" name="professor.id" value="${professor.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p> 
            <p>
                <input hidden="true" type="text" name="professor.usuario.id" value="${professor.usuario.id}"/>
            </p>            
            <p>
                <label for="campo-nome">Nome*:</label><br/>
                <input size="50" type="text" id="campo-nome" name="professor.usuario.nome" value="${professor.usuario.nome}"/><br/>
            </p>
            <p>
                <label for="campo-cpf">CPF*:</label><br/>
                <input size="14" type="text" id="campo-cpf" name="professor.usuario.cpf" value="${professor.usuario.cpf}"/><br/>
            </p>
            <p>
                <label for="campo-email">E-mail*:</label><br/>
                <input size="50" type="text" id="campo-email" name="professor.usuario.email" value="${professor.usuario.email}"/><br/>
            </p>
        </form>
    </body>
</html>