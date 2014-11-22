<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gerenciamento de Cursos</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formCurso").submit(); 
                }); 
                $("#formCurso").validate({
                    rules:{
                        "curso.codigo":{
                            required: true,
                            maxlength: 10
                        },
                        "curso.curso":{
                            required: true,
                            maxlength: 150
                        },
                        "curso.qntd_horas":{
                            required: true,
                            min: 1
                        }
                    },
                    messages:{
                        "curso.codigo":{
                            required: "Informe o código do curso",
                            maxlength: "O código deve conter no máximo 10 caracteres"
                        },
                        "curso.curso":{
                            required: "Informe o nome do curso",
                            maxlength: "O curso deve conter no máximo 150 caracteres"
                        },
                        "curso.qntd_horas":{
                            required: "Informe a quantidade de horas complementares do curso",
                            min: "O mínimo de horas deve ser maior que 0"
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
                                        <a href="${pageContext.request.contextPath}/cursos">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Curso</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Curso</h2></div>
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

        <form id="formCurso" name="formCurso" method="POST" action="<c:url value="/cursos"/>"> 
            <p>
                <c:if test="${not empty curso.id}">
                    <input type="hidden" name="curso.id" value="${curso.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p> <p>
                <label for="campo-codigo">Codigo*:</label></br>
                <input size="10" type="text" id="campo-codigo" name="curso.codigo" value="${curso.codigo}"/></br>
            </p><p>
                <label for="campo-curso">Curso*:</label></br>
                <input size="50" type="text" id="campo-curso" name="curso.curso" value="${curso.curso}"/></br>                         
            </p><p>
                <label for="campo-maximoHoras">Horas Complementares*:</label></br>
                <input onKeyUp="javascript:somente_numero(this);" size="10" type="text" id="campo-maximoHoras" name="curso.qntd_horas" value="${curso.qntd_horas}"/></br>
            </p>
        </form>
    </body>
</html>