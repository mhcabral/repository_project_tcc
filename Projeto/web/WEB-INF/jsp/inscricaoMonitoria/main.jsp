<%-- 
    Document   : main
    Created on : 14/04/2013, 04:28:04
    Author     : Bruna
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                            <div class="pagetitle icon-48-frontpage"><h2>Monitoria</h2></div>
                            <br/>
                            <div class="icon-wrapper" <c:if test="${sessionData.periodoInsMon == null}">title="Opção indisponível! Período de inscrição em monitoria fechado"</c:if>>
                                <div class="icon">
                                    <a href="${pageContext.request.contextPath}/InsMonitoria" <c:if test="${sessionData.periodoInsMon == null}">class="desativa"</c:if> >
                                        <img src="${pageContext.request.contextPath}/css/images/header/icon-48-writemess.png" alt>
                                        <span>Inscrição<br/>em Monitoria</span>
                                    </a>
                                </div>
                            </div>                                
                            <div class="icon-wrapper">
                                <div class="icon">
                                    <a href="${pageContext.request.contextPath}/inscricoes/frequencia">
                                        <img src="${pageContext.request.contextPath}/css/images/header/icon-48-calendar.png" alt>
                                        <span>Enviar Frequência</span>
                                    </a>
                                </div>
                            </div>
                            <div class="icon-wrapper">
                                <div class="icon">
                                    <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal">
                                        <img src="${pageContext.request.contextPath}/css/images/header/icon-48-featured.png" alt>
                                        <span>Enviar Relatório Final</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
            </div>
        </div>
    </body>
</html>
