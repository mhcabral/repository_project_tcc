<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"[]>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US" xml:lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><decorator:title default="Atividades Extracurriculares"/></title>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/application.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css"/>
        <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/application.js"></script>

        <link href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" rel="stylesheet"/>
        <decorator:head/>
    </head>
    <body>
        <div id="art-page-background-glare-wrapper">
            <div id="art-page-background-glare"></div>
        </div>
        <div id="art-main">
            <div class="cleared reset-box"></div>
            <div class="art-header">
                <div class="art-header-position">
                    <div class="art-header-wrapper">
                        <div class="cleared reset-box"></div>
                        <a href="${pageContext.request.contextPath}/"><img width="100%" src="${pageContext.request.contextPath}/css/images/banner.png"/></a>
                    </div>
                </div>
            </div>

            <div class="cleared reset-box"></div>

            <div class="art-box art-sheet">
                <div class="art-box-body art-sheet-body">
                    <div class="art-layout-wrapper">
                        <div class="art-content-layout">
                            <div class="art-content-layout-row">
                                <div class="art-layout-cell art-sidebar1">
                                    <div class="art-box art-block">
                                        <div class="art-box-body art-block-body">
                                            <div class="art-bar art-blockheader">

                                                <h3 class="t">
                                                    <c:if test="${sessionData.logado}">Bem-vindo(a)!</c:if>
                                                </h3>
                                            </div>
                                            <div class="art-box art-blockcontent">
                                                <div class="art-box-body art-blockcontent-body">
                                                    <c:if test="${sessionData.logado}">
                                                        Ol� <i>${sessionData.nome}</i>, bem vindo(a)!<br/>
                                                        Seu perfil atual � 
                                                        <c:if test="${sessionData.usuario.perfil == 0}">
                                                            <i>Aluno</i>
                                                        </c:if>
                                                        <c:if test="${sessionData.usuario.perfil == 1}">
                                                            <i>Coordenador de Curso</i>
                                                        </c:if>
                                                        <c:if test="${sessionData.usuario.perfil == 2}">
                                                            <i>Secret�ria</i>
                                                        </c:if>
                                                        <c:if test="${sessionData.usuario.perfil == 3}">
                                                            <i>Administrador</i>
                                                        </c:if>
                                                        <c:if test="${sessionData.usuario.perfil == 4}">
                                                            <i>Coordenador Acad�mico</i>
                                                        </c:if>
                                                        <c:if test="${sessionData.usuario.perfil == 5}">
                                                            <i>Professor</i>
                                                        </c:if>
                                                        <br/> Para sair do sistema fa�a 
                                                        <a href="<c:url value="/logout"/>"><i>Logout</i></a>
                                                    </c:if>

                                                </div>
                                            </div>
                                            <div class="cleared"></div>
                                        </div>
                                    </div>
                                    <div class="art-box art-vmenublock">
                                        <div class="art-box-body art-vmenublock-body">
                                            <div class="art-bar art-vmenublockheader">

                                                <h3 class="t">
                                                    <c:if test="${sessionData.logado}">Menu</c:if>
                                                </h3>
                                            </div>

                                            <div class="art-box art-vmenublockcontent">
                                                <div id ="menu" class="art-box-body art-vmenublockcontent-body">
                                                    <c:if test="${sessionData.logado}">
                                                        <ul class="art-vmenu">
                                                            <li>
                                                                <a href="${pageContext.request.contextPath}/">P�gina Inicial</a>
                                                            </li>
                                                            <c:if test="${sessionData.usuario.perfil == 1}"> 
                                                                <c:if test="${sessionData.periodoCadMon != null}">
                                                                    <li>
                                                                        <a href="${pageContext.request.contextPath}/monitorias">Cadastro de Monitoria</a>
                                                                    </li>
                                                                </c:if>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/regras/grupos">Regra de Grupo</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analise/final/estagios">An�lise Final do Est�gio</a>
                                                                </li>                                                                
                                                            </c:if>                                                            
                                                            <c:if test="${sessionData.usuario.perfil == 1 || sessionData.usuario.perfil == 2}">
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analise">Solicita��es de Aproveitamento</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analise/estagios">Solicita��es de Est�gio</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analise/historico">Hist�rico de Solicita��es</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/historico">Hist�rico Aluno</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/monitorias/analisadas">Hist�rico de Monitorias</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/historico/periodo">Hist�rico de Atividades Solicitadas</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/regras/relatorioAtividade/selecao">Rela��o de Atividades do Curso</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/consulta/estagio">Consulta de Est�gios</a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${sessionData.usuario.perfil == 0}">
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/solicitacoes">Atividades Complementares</a>
                                                                </li>
                                                                <!--<li>
                                                                    <a href="${pageContext.request.contextPath}/solicitacoes/historico">Hist�rico</a>
                                                                </li>-->
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/estagios/index">Est�gio</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/inscricoes/index">Monitoria</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/regras/relatorioAtividade/selecao">Rela��o de Atividades do Curso</a>
                                                                </li>
                                                                <!--
                                                                <c:if test="${sessionData.periodoInsMon != null}">
                                                                    <li>
                                                                        <a href="${pageContext.request.contextPath}/inscricoes/index">Monitoria</a>
                                                                    </li>
                                                                </c:if>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/inscricoes/frequencia">Enviar Frequ�ncia</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal">Enviar Relat�rio Final</a>
                                                                </li>-->
                                                            </c:if> 
                                                            <c:if test="${sessionData.usuario.perfil == 3}">
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/periodosIns">Per�odo Inscri��o em Monitoria</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/periodosLetivo">Per�odo Letivo</a>
                                                                </li>
                                                                <!--<li>
                                                                    <a href="${pageContext.request.contextPath}/usuarios/novo">Novo Usu�rio</a>
                                                                </li>-->
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/cursos">Curso</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/grupos">Grupo</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/atividades">Atividade</a>
                                                                </li> 
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/disciplinas">Disciplina</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/alunos">Aluno</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/professores">Professor</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/coordCurso">Coordenador de Curso</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/coordAcademico">Coordenador Acad�mico</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/secretarias">Secretaria</a>
                                                                </li>
                                                            </c:if>
                                                            <c:if test="${sessionData.usuario.perfil == 4}">
                                                                <c:if test="${sessionData.periodoCadMon != null}">
                                                                    <li>
                                                                        <a href="${pageContext.request.contextPath}/monitorias">An�lise de Cadastro de Monitorias</a>
                                                                    </li>
                                                                </c:if>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/periodos">Per�odo de Cadastro da Monitoria</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analiseInscricao">An�lise de Inscri��es em Monitoria</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/analiseFinal">An�lise Final de Monitoria</a>
                                                                </li>
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/monitorias/analisadas">Hist�rico de Monitorias</a>
                                                                </li>
                                                            </c:if>                                                            

                                                            <c:if test="${sessionData.usuario.perfil == 2 || sessionData.usuario.perfil == 4}">
                                                                <li>
                                                                    <a href="${pageContext.request.contextPath}/historicoMonitorias/alunos">Monitorias de Aluno</a>
                                                                </li>
                                                            </c:if>
                                                        </ul>
                                                    </c:if>

                                                    <div class="cleared"></div>
                                                </div>
                                            </div>

                                            <div class="cleared"></div>
                                        </div>
                                    </div>

                                    <div class="cleared"></div>
                                </div>

                                <div class="art-layout-cell art-content">
                                    <div class="art-box art-post">
                                        <div class="art-box-body art-post-body">
                                            <div class="art-post-inner art-article">
                                                <div class="art-postcontent">
                                                    <div class="art-content-layout">
                                                        <decorator:body/> 
                                                    </div>
                                                </div>
                                                <div class="cleared"></div>
                                            </div>

                                            <div class="cleared"></div>
                                        </div>
                                    </div>

                                    <div class="cleared"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cleared"></div>
                    <div class="art-footer">
                        <div class="art-footer-body">
                            <div class="art-footer-text">
                                <p>� ICOMP - Instituto de Computa��o</p>
                                <p>Desenvolvido no contexto da disciplina IEC112 - 2012/02</p>
                            </div>
                            <div class="cleared"></div>
                        </div>
                    </div>
                    <div class="cleared"></div>
                </div>
            </div>
        </div>
    </body>
</html>
