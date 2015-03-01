<%-- 
    Document   : show
    Created on : 19/03/2013, 16:56:02
    Author     : Thiago Santos
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Coordenador Acadêmico [Exibição]</title>
        <script>
            function remove(){
                decisao = confirm("Deseja realmente remover o coordenador acadêmico?");
                if (decisao){
                    document.location.href="${pageContext.request.contextPath}/coordAcademico/${coordenadorAcademico.id}/remove";                                                              
                } else {
                    alert ("Nenhum coordenador acadêmico foi removido");
                }                            
                return;
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
                                        <a href="${pageContext.request.contextPath}/coordAcademico/${coordenadorAcademico.id}/edit" id="edit" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:remove()">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/coordAcademico" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informações do Coordenador Acadêmico</h2></div>
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
        <c:if test="${not empty coordenadorAcademico.id}">
            <p>
                <b>Professor:</b>
                ${coordenadorAcademico.professor.usuario.nome}
            </p>
            <p>
                <b>Coordena o(s) Curso(s):</b>
            <ul>
                <c:forEach items="${coordenadorAcademico.cursos}" var="curso">
                    <li>${curso}</li>
                </c:forEach>                                
            </ul>
        </p>
    </c:if>
</body>
</html>