
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TCC</title>
        <style type="text/css">
            .desativa {
                pointer-events: none;
                cursor: default;
                box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                background: #f2f5f7;
            }
            a{
                background: #fff;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <div id="element-box">
                <div class="m">
                    <div class="adminform">                        
                        <div class="cpanel">
                            <div class="pagetitle icon-48-frontpage"><h2>TCC</h2></div>
                            <br/>
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
                                </div> <br><br>  
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 1 || sessionData.usuario.perfil == 2 || sessionData.usuario.perfil == 5}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/relatorio/notas">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Relatório de<br/>Notas</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/relatorio/avaliacao">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Formulário de Avaliação</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/relatorio/presenca">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Lista de Presença</span>
                                        </a>
                                    </div>
                                </div>
                                
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tccdeclaracao">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Declarações</span>
                                        </a>
                                    </div>
                                </div>
                                
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 1 || sessionData.usuario.perfil == 2 || sessionData.usuario.perfil == 5 || sessionData.usuario.perfil == 0}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/relatorio/atividades">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Calendário de Atividades</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/relatorio/workshop">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Calendário de Workshop</span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
            </div>
        </div>
    </body>
</html>
