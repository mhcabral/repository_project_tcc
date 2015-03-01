<%-- 
    Document   : index
    Created on : 16/02/2013, 13:03:29
    Author     : Bruna
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bem Vindo</title>
        <style type="text/css">
            .notificacao { padding-left:12px; font-family:sans-serif; font-weight:bold; color:darkcyan  ;float: none; padding-top:2px; padding-bottom:2px;  vertical-align: top; font-size: 14px }
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

                            <div class="pagetitle icon-48-frontpage"><h2>Página Inicial</h2></div>

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

                            <c:forEach items="${notificacaoList}" var="notificacao">
                                <div class="notificacao" id="notificacao${notificacao.id}">Lembrete: ${notificacao.notificacao} <a href="#" style="color: green; background-color:#d1ffd1  ;font-weight: bold; font-family: sans-serif " onclick="remover(${notificacao.id})">Não exibir mais</a> </div>                                                                                                     
                            </c:forEach>

                            <br/>

                            <c:if test="${sessionData.usuario.perfil == 1}">                               
                                <div class="icon-wrapper" <c:if test="${sessionData.periodoCadMon == null}">title="Opção indisponível! Período de cadastro de monitoria fechado"</c:if>>
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/monitorias" <c:if test="${sessionData.periodoCadMon == null}">class="desativa"</c:if>>
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-article-add.png" alt>
                                            <span>Cadastro<br/>de Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/regras/grupos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-links-cat.png" alt>
                                            <span>Regra de Grupo</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analise/final/estagios">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-search.png" alt>
                                            <span>Análise Final do Estágio</span>
                                        </a>
                                    </div>
                                </div>
                                
                            </c:if>                            
                            <c:if test="${sessionData.usuario.perfil == 1 || sessionData.usuario.perfil == 2}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analise">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-notice.png" alt>
                                            <span>Solicitações de Aproveitamento</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analise/estagios">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-alert.png" alt>
                                            <span>Solicitações de Estágio</span>
                                        </a>
                                    </div>
                                </div>

                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analise/historico">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-banner-categories.png" alt>
                                            <span>Histórico de Solicitações</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/regras/relatorioAtividade/selecao">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-stats.png" alt>
                                            <span>Relação de Atividades do Curso</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/historico">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-contacts-categories.png" alt>
                                            <span>Histórico Aluno</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/monitorias/analisadas">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-banner-client.png" alt>
                                            <span>Histórico de Monitorias</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/historico/periodo">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-levels.png" alt>
                                            <span>Histórico de Atividades Solicitadas</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/consulta/estagio">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-menumgr.png" alt>
                                            <span>Consulta de Estágios</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/index">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-tcc.png" alt>
                                            <span>TCC</span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 0}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/solicitacoes">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-newcategory.png" alt>
                                            <span>Atividades Complementares</span>
                                        </a>
                                    </div>
                                </div>
                                <!--<div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/solicitacoes/historico">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-stats.png" alt>
                                            <span>Histórico</span>
                                        </a>
                                    </div>
                                </div>-->
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/estagios/index">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-banner-client.png" alt>
                                            <span>Estágio</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/inscricoes/index" >
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-contacts-categories.png" alt>
                                            <span>Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/regras/relatorioAtividade/selecao">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-stats.png" alt>
                                            <span>Relação de Atividades do Curso</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/index">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-tcc.png" alt>
                                            <span>TCC</span>
                                        </a>
                                    </div>
                                </div>
                                <!--
                    <div class="icon-wrapper" <c:if test="${sessionData.periodoInsMon == null}">title="Opção indisponível! Período de inscrição em monitoria fechado"</c:if>>
                            <div class="icon">
                                <a href="${pageContext.request.contextPath}/InsMonitoria" <c:if test="${sessionData.periodoInsMon == null}">class="desativa"</c:if> >
                                <img src="${pageContext.request.contextPath}/css/images/header/icon-48-contacts-categories.png" alt>
                                <span>Monitoria</span>
                            </a>
                        </div>
                    </div>                                
                    <div class="icon-wrapper">
                        <div class="icon">
                            <a href="${pageContext.request.contextPath}/inscricoes/frequencia">
                                <img src="${pageContext.request.contextPath}/css/images/header/icon-48-send.png" alt>
                                <span>Enviar Frequência</span>
                            </a>
                        </div>
                    </div>
                    <div class="icon-wrapper">
                        <div class="icon">
                            <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal">
                                <img src="${pageContext.request.contextPath}/css/images/header/icon-48-send.png" alt>
                                <span>Enviar Relatório Final</span>
                            </a>
                        </div>
                    </div>-->
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 3}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/periodosIns">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-contacts.png" alt>
                                            <span>Período Inscrição<br/>em Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/periodosLetivo">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-calendar.png" alt>
                                            <span>Calendário Letivo</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/cursos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-category.png" alt>
                                            <span>Curso</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/grupos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-content.png" alt>
                                            <span>Grupo</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/atividades">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-article.png" alt>
                                            <span>Atividade</span>
                                        </a>
                                    </div>
                                </div> 
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/disciplinas">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-module.png" alt>
                                            <span>Disciplina</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/alunos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-user.png" alt>
                                            <span>Aluno</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/professores">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-groups.png" alt>
                                            <span>Professor</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/coordCurso">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-groups-add.png" alt>
                                            <span>Coordenador<br/>de Curso</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/coordAcademico">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-user-add.png" alt>
                                            <span>Coordenador<br/>Acadêmico</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/secretarias">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-levels-add.png" alt>
                                            <span>Secretaria</span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 4}">
                                <div class="icon-wrapper" <c:if test="${sessionData.periodoCadMon == null}">title="Opção indisponível! Período de cadastro de monitoria fechado"</c:if>>
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/monitorias" <c:if test="${sessionData.periodoCadMon == null}">class="desativa"</c:if>>
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-menu-add.png" alt>
                                            <span>Análise de Cadastro de Monitorias</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/periodos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-calendar.png" alt>
                                            <span>Período de Cadastro da Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analiseInscricao">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-banner.png" alt>
                                            <span>Análise de Inscrições<br/>em Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/analiseFinal">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-article.png" alt>
                                            <span>Análise Final de Monitoria</span>
                                        </a>
                                    </div>
                                </div>
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/monitorias/analisadas">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-stats.png" alt>
                                            <span>Histórico de Monitorias</span>
                                        </a>
                                    </div>
                                </div>                                
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 2 || sessionData.usuario.perfil == 4}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/historicoMonitorias/alunos">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-contacts-categories.png" alt>
                                            <span>Monitorias de Aluno</span>
                                        </a>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${sessionData.usuario.perfil == 5}">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <a href="${pageContext.request.contextPath}/tcc/index">
                                            <img src="${pageContext.request.contextPath}/css/images/header/icon-48-tcc.png" alt>
                                            <span>TCC</span>
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
        <script>
            
            function remover(id){
                var url = "${pageContext.request.contextPath}/"+id+"/rticket";                                                        
                $.post(url, null, null, null);                    
                var e = "#notificacao"+id;                                        
                $(e).remove();
                
            }
            
        </script>

    </body>    
</html>
