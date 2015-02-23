<%-- 
    Document   : form
    Created on : 30/01/2013, 17:57:04
    Author     : Thiago Santos
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de Atividades</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formAtividade").submit(); 
                });
                $("#formAtividade").validate({                    
                    rules:{
                        "atividade.codigo":{
                            required: true,
                            maxlength: 10
                        },
                        "atividade.nome":{
                            required: true,
                            maxlength: 255
                        },
                        "atividade.documentacao":{
                            required: true,
                            maxlength: 255
                        },
                        "atividade.grupo.id":{
                            required: true
                        },
                        "atividade.categoria":{
                            required: true
                        }
                    },
                    messages:{
                        "atividade.codigo":{
                            required: "Informe o código da atividade",
                            maxlength: "O código deve conter no máximo 10 caracteres"
                        },
                        "atividade.nome":{
                            required: "Informe o nome da atividade",
                            maxlength: "O nome deve conter no máximo 255 caracteres"
                        },
                        "atividade.documentacao":{
                            required: "Informe a documentação da atividade",
                            maxlength: "A documentação deve conter no máximo 255 caracteres"
                        },
                        "atividade.grupo.id":{
                            required: "Selecione um Grupo"
                        },
                        "atividade.categoria":{
                            required: "Selecione uma Categoria"
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
                                        <a href="${pageContext.request.contextPath}/atividades" id="cancel">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Atividade</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Atividade</h2></div>
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

        <form id="formAtividade" name="formAtividade" method="POST" action="<c:url value="/atividades"/>"> 
            <c:if test="${not empty atividade.id}">
                <input type="hidden" name="atividade.id" value="${atividade.id}"/>
                <input type="hidden" name="_method" value="put"/>
            </c:if>

            <!--   <div id="toolbar-box">
            <div class="m">
            <div class="toolbar-list" id="toolbar">
                           <div class="cpanel2">
                               <div class="icon" id="toolbar-cancel">
                                   <a> <span class="icon-48-article"></span>Botao</a></div></div><div class="clr"></div></div><div class="pagetitle icon-48-contact" align="center"><h1>OPAAAAAAA</h1></div></div></div>
   
               <div class="cpanel">
                   <div class="icon-wrapper">
                       <div class="icon">
                           <a href="linkDaFuncionalidade ">
                               <img width="32" height="32" border="0" src="imagem">Funcionalidade</a>
                       </div>
                   </div>
                   <div class="icon-wrapper">
                       <div class="icon">
                           <a href="linkDaFuncionalidade ">
                               <img width="32" height="32" border="0" src="imagem">Funcionalidade 2</a>
                       </div>
                   </div>
               </div>-->
            <!--  <table width="100%">
                  <tr>
                      <td style="border-style:hidden" width="80%">
                          <h2 class="art-postheader">${operacao} de Atividade</h2>
                      </td>
                      <td style="border-style:hidden" width="10%"><input type="image" src="${pageContext.request.contextPath}/img/save.png">Salvar</input></td>
                      <td style="border-style:hidden" width="10%"><input type="image" src="${pageContext.request.contextPath}/img/back.png" onclick="voltar()">Voltar</input></td>
                  </tr>
                  <tr>
                      
                      <td>
                          <a href="javascript:void(0);" id="button" class="button"><img src="${pageContext.request.contextPath}/img/save.png">Salvar</a>
                      </td>
                      <td>
                          <a href="${pageContext.request.contextPath}/atividades"><img src="${pageContext.request.contextPath}/img/back.png">Voltar</a>
                      </td>
  
                  </tr>
              </table>-->
            <p>
                <label for="campo-categoria">Categoria*:</label></br>
                <select id="campo-categoria" name="atividade.categoria" value="${atividade.categoria}" >		
                    <option value="">Selecione uma categoria</option>
                    <c:forEach var="categoria" items="${categoriaList}">
                        <option value="${categoria}" <c:if test = "${categoria == atividade.categoria}"> selected</c:if>>${categoria.nome}</option>
                    </c:forEach>
                </select></br>
            </p><p>
                <label for="campo-grupo">Grupo*:</label></br>
                <select id="campo-grupo" name="atividade.grupo.id" value="${atividade.grupo}" >		
                    <option value="">Selecione um grupo</option>
                    <c:forEach var="grupo" items="${grupoList}">
                        <option value="${grupo.id}" <c:if test = "${grupo.id == atividade.grupo.id}"> selected</c:if>>${grupo}</option>
                    </c:forEach>
                </select></br>
            </p><p>
                <label for="campo-codigo">Codigo*:</label></br>
                <input size="10" type="text" id="campo-codigo" name="atividade.codigo" value="${atividade.codigo}"/></br>
            </p><p>
                <label for="campo-nome">Nome*:</label></br>
                <input size="70" type="text" id="campo-nome" name="atividade.nome" value="${atividade.nome}"/></br>
            </p><p>
                <label for="campo-documentacao">Documentação*:</label></br>
                <textarea rows="5" cols="51" id="campo-documentacao" name="atividade.documentacao">${atividade.documentacao}</textarea></br>
            </p>
        </form>
    </body>
</html>