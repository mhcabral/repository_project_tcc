<%-- 
    Document   : form
    Created on : 19/03/2013, 17:03:47
    Author     : Bruna
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
                    $("#formCoordCurso").submit(); 
                }); 
                $("#formCoordCurso").validate({
                    rules:{
                        "coordenadorCurso.professor.id":{
                            required: true
                        },
                        "coordenadorCurso.curso.id":{
                            required: true
                        }
                    },
                    messages:{
                        "coordenadorCurso.professor.id":{
                            required: "Selecione um Professor"
                        },
                        "coordenadorCurso.curso.id":{
                            required: "Selecione um Curso"
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
                                        <a href="${pageContext.request.contextPath}/coordCurso">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Coordenador de Curso</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Coordenador de Curso</h2></div>
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

        <form id="formCoordCurso" name="formCoordCurso" method="POST" action="<c:url value="/coordCurso"/>"> 
            <p>
                <c:if test="${not empty coordenadorCurso.id}">
                    <input type="hidden" name="coordenadorCurso.id" value="${coordenadorCurso.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            <p>
                <font size="2">
                <label for="campo-professor">Professor*:</label><br/>
                <select id="campo-professor" name="coordenadorCurso.professor.id" value="${coordenadorCurso.professor.usuario.nome}" >
                    <option value="">Selecione um Professor</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option value="${professor.id}" <c:if test = "${professor.id == coordenadorCurso.professor.id}"> selected</c:if>>${professor.usuario.nome}</option>
                    </c:forEach>
                </select>
                </font><br/>
            </p>
            <p>
                <font size="2">
                <label for="campo-curso">Curso*:</label><br/>
                <select id="campo-curso" name="coordenadorCurso.curso.id" value="${coordenadorCurso.curso}" >
                    <option value="">Selecione um Curso</option>
                    <c:forEach var="curso" items="${cursoList}">
                        <option value="${curso.id}" <c:if test = "${curso.id == coordenadorCurso.curso.id}"> selected</c:if>>${curso}</option>
                    </c:forEach>
                </select>
                </font><br/>
            </p>
        </form>
    </body>
</html>