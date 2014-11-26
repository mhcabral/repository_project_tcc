
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
                            <c:if test="${sessionData.usuario.perfil == 1 || sessionData.usuario.perfil == 2}">
                                <div class="icon-wrapper" <c:if test="${sessionData.periodoInsMon == null}">title="Opção indisponível! Período de inscrição em monitoria fechado"</c:if>>
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/locais" <c:if test="${sessionData.periodoInsMon == null}">class="desativa"</c:if> >
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Locais de<br/>Apresentação</span>
                                        </a>
                                    </div>
                                </div>                                
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/area">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Área de Pesquisa</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tccAtividade/0/index">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-calendar.png" alt>
                                            <span>Atividades</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcctemas">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Temas</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/atividades">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Workshop</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/atividades">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Relatórios</span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 0}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcctcc">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-tcc-edit.png" alt>
                                            <span>Definir TCC</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/edit">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Enviar Documento</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/edit">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Exibir Notas</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/edit">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-info.png" alt>
                                            <span>Solicitar Aproveitamente</span>
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
