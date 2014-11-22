<%-- 
    Document   : form
    Created on : 19/03/2013, 17:03:47
    Author     : Thiago Santos
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
        <style type="text/css">

        </style>

        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formCoordAcademico").submit(); 
                }); 
                $("#formCoordAcademico").validate({
                    rules:{
                        "coordenadorAcademico.professor.id":{
                            required: true
                        },
                        "coordenadorAcademico.cursos[].id":{
                            required: true
                        }
                    },
                    messages:{
                        "coordenadorAcademico.professor.id":{
                            required: "Selecione um Professor"
                        },
                        "coordenadorAcademico.cursos[].id":{
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
                                        <a href="${pageContext.request.contextPath}/coordAcademico">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Coordenador Acadêmico</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Coordenador Acadêmico</h2></div>
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

        <form id="formCoordAcademico" name="formCoordAcademico" method="POST" action="<c:url value="/coordAcademico"/>"> 
            <p>
                <c:if test="${not empty coordenadorAcademico.id}">
                    <input type="hidden" name="coordenadorAcademico.id" value="${coordenadorAcademico.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            <p>
                <font size="2">
                <label for="campo-professor">Professor*:</label><br/>
                <select id="campo-professor" name="coordenadorAcademico.professor.id" value="${coordenadorAcademico.professor.usuario.nome}" >
                    <option value="">Selecione um Professor</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option value="${professor.id}" <c:if test = "${professor.id == coordenadorAcademico.professor.id}"> selected</c:if>>${professor.usuario.nome}</option>
                    </c:forEach>
                </select>
                </font><br/>
            </p>
            <p>
                <font size="2">
                <label for="campo-curso">Curso(s)*:</label><br/>

                <c:if test="${empty coordenadorAcademico.id}">
                <ul style="height: 100px; overflow: auto; width: 400px; border: 1px solid #000; list-style-type: none; margin: 0; padding: 0; overflow-x: hidden;">
                    <c:forEach items="${cursoList}" var="curso" varStatus="s">
                        <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="coordenadorAcademico.cursos[${s.index}].id" id="${curso.id}" value="${curso.id}"/>${curso}</label></li>
                            </c:forEach>
                </ul>
            </c:if>

            <c:if test="${not empty coordenadorAcademico.id}">
                <%boolean flag = false;%>                
                <ul style="height: 100px; overflow: auto; width: 400px; border: 1px solid #000; list-style-type: none; margin: 0; padding: 0; overflow-x: hidden;">
                    <c:forEach items="${cursoList}" var="curso" varStatus="s">
                        <c:forEach items="${coordenadorAcademico.cursos}" var="cursoCoordenador">
                            <%if (flag != true) {%>
                            <c:if test="${curso.id == cursoCoordenador.id}">
                                <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="coordenadorAcademico.cursos[${s.index}].id" id="${curso.id}" checked="true" value="${curso.id}"/>${curso}</label></li>
                                        <%flag = true;%>
                                    </c:if>
                                    <%}%>
                                </c:forEach>                          
                                <%if (flag != true) {%>
                        <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="coordenadorAcademico.cursos[${s.index}].id" id="${curso.id}" value="${curso.id}"/>${curso}</label></li>
                                <%}
                                    flag = false;%>
                            </c:forEach>
                </ul>
            </c:if>                        
            </font><br/>
        </p>
    </form>
</body>
</html>