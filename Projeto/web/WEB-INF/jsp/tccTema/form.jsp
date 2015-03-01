<%-- 
    Document   : form
    Created on : 19/01/2015, 11:39:38
    Author     : mhcabral
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
                    $("#formTccTema").submit(); 
                }); 
                $("#formTccTema").validate({
                    rules:{
                        "tccTema.area":{
                            required:true
                        },
                        "tccTema.sigla":{
                            required: true
                        },
                        "tccTema.titulo":{
                            required: true
                        },
                        "tccTema.descricao":{
                            required: true
                        },
                        "tccTema.perfil":{
                            required: true
                        },
                        "tccTema.professor.id":{
                            required: true
                        }
                    },
                    messages:{
                        "tccTema.area":{
                            required: "Selecione a área"
                        },
                        "tccTema.sigla":{
                            required: "Informe a sigla"
                        },
                        "tccTema.titulo":{
                            required: "Informe o título"
                        },
                        "tccTema.descricao":{
                            required: "Informe a descrição"
                        },
                        "tccTema.perfil":{
                            required: "Informe o perfil"
                        },
                        "tccTema.professor.id":{
                            required: "Informe o professor"
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
                                        <a href="${pageContext.request.contextPath}/tcctemas">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Tema</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Tema</h2></div>
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

        <form id="formTccTema" name="formTccTema" method="POST" action="<c:url value="/tcctemas"/>"> 
            <p>
                <c:if test="${not empty tccTema.id}">
                    <input type="hidden" name="tccTema.id" value="${tccTema.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <input type="hidden" name="tccTema.estado" value="${tccTema.estado}"/>
            </p> 
            <p>
                <label for="area">Área*:</label><br/>
                <select id="area" name="tccTema.area" value="${tccTema.area}">
                    <option value="">Selecione uma área</option>
                    <c:forEach var="string" items="${areaList}">
                        <option  value="${string}" <c:if test = "${string == tccTema.area}"> selected="true" </c:if>>${string}</option>
                    </c:forEach>
                </select><br/>
            </p>
            <p>
                <label for="sigla">Sigla*:</label>
                <input type="text" id="sigla" name="tccTema.sigla" value="${tccTema.sigla}" size="4"/>
            </p>
            <p>
                <label for="titulo" >Título*:</label>
                <input type="text" id="titulo" name="tccTema.titulo" value="${tccTema.titulo}" size="100" />
            </p>
            <p>
                <label for="descricao">Descrição*:</label>
                <textarea rows="4" cols="70" id="descricao" name="tccTema.descricao" >${tccTema.descricao} </textarea>
            </p>
            <p>
                <label for="Perfil">Perfil*:</label>
                <textarea rows="4" cols="70" id="perfil" name="tccTema.perfil" >${tccTema.perfil} </textarea>
            </p>
            <p>
                <label for="professor">Professor*:</label>
                <select id="professor" name="tccTema.professor.id" value="${tccTema.professor}">
                    <option value="">Selecione um professor</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option  value="${professor.id}" <c:if test = "${professor.id == tccTema.professor.id}"> selected="true" </c:if>>${professor}</option>
                    </c:forEach>
                </select><br/>
            </p>
            <%--
            <p>
                <label for="selTemaCurso">Cursos:</label>
                <select name="tcctema.cursos" size=5 id="campo-cursos" value="tccTema.cursos" multiple >
                    <c:forEach items="${cursosList}" var="tccTemaCurso">
                        <option value="" >${tccTemaCurso.curso}</option>
                    </c:forEach>
                </select>
            </p>
            --%>
            <p>
                <label for="estado1">Estado*:</label>
                <input id="estado1" type="text" name="campo-estado" value="${tccTema.estado}" size="30" disabled="true"/>
            </p>
        </form>
    </body>
</html>