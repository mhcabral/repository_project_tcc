<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"[]>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en-US" xml:lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Atividades Extracurriculares</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/application.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css"/>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css"/>
        <link href="${pageContext.request.contextPath}/css/smoothness/jquery-ui-1.10.1.custom.css" rel="stylesheet"/>
        <script src="${pageContext.request.contextPath}/js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function() {
                $('#entrar').click(function(e) {
                    e.preventDefault();
                    $("#formLogin").submit();
                });

                $("#formLogin").validate({
                    rules: {
                        "usuario.cpf": {
                            required: true
                        },
                        "usuario.pwd": {
                            required: true
                        }
                    },
                    messages: {
                        "usuario.cpf": {
                            required: "Informe seu CPF"
                        },
                        "usuario.pwd": {
                            required: "Informe sua senha"
                        }
                    }
                });
            });

            $(document).keypress(function(e) {
                if (e.which === 13) {
                    e.preventDefault();
                    $("#formLogin").submit();
                }
            });
        </script>
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
            <div class="art-box art-sheet">
                <div class="art-box-body art-sheet-body">
                    <div class="art-layout-wrapper">
                        <div class="art-content-layout">
                            <div class="art-content-layout-row">
                                <div class="art-layout-cell art-sidebar1">
                                    <div class="art-box art-block">
                                        <div class="art-box-body art-block-body">
                                            <div class="art-bar art-blockheader">
                                                <div class="art-box art-blockcontent">
                                                    <div class="art-box-body">
                                                        <!--<div class="art-box-body art-blockcontent-body">-->
                                                        <table>
                                                            <tr>
                                                                <td>
                                                                    <div id="section-box">
                                                                        <div class="m">
                                                                            <form id="formLogin" name="formLogin" action="<c:url value='/login'/>" method="POST" style="">
                                                                                <fieldset>
                                                                                    <legend>Login</legend>
                                                                                    <c:if test="${not empty errors}">
                                                                                        <div class="ui-widget">
                                                                                            <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                                                                                <p>
                                                                                                    <ul>
                                                                                                        <c:forEach items="${errors}" var="error">
                                                                                                            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                                                                                            <li style="color: #cd0a0a">${error.message}</li>
                                                                                                        </c:forEach>        
                                                                                                    </ul>
                                                                                                </p>
                                                                                            </div>
                                                                                        </div>
                                                                                        <br/>
                                                                                    </c:if>
                                                                                    <table>
                                                                                        <tr>
                                                                                            <td><label for="login">Login:</label></td>
                                                                                            <td><input id="login" size="17" maxlength="16" type="text" name="usuario.cpf"/></td>
                                                                                        </tr>
                                                                                        <tr>
                                                                                            <td><label for="senha">Senha:</label></td>
                                                                                            <td><input id="senha" size="15" maxlength="30" type="password" name="usuario.pwd"/></td>
                                                                                        </tr>

                                                                                        <tr>
                                                                                            <td colspan="2"><center>
                                                                                                    <div class="button-holder">
                                                                                                        <div class="button1">
                                                                                                            <div class="next">
                                                                                                                <a id="entrar">Entrar</a>
                                                                                                            </div>
                                                                                                        </div>
                                                                                                    </div>
                                                                                                </center></td>
                                                                                        </tr>

                                                                                    </table>
                                                                                    <c:if test="${resultado == 'encontrado'}">
                                                                                        <font size="2">Uma nova senha foi enviada pra o seu e-mail</font>
                                                                                    </c:if>

                                                                                    <a href="${pageContext.request.contextPath}/lembrar" id=""><font size="1" style="margin-left: 50px">Esqueci minha senha</font></a>
                                                                                    <!--<button class="" type="submit">Entrar</button>-->
                                                                                </fieldset>
                                                                            </form>
                                                                            <h5>
                                                                                <!--<a href="<c:url value='/lembrar'/>">Esqueci minha senha!</a>-->
                                                                            </h5>   
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="cleared"></div>
                                        </div>
                                        <div class="cleared"></div>
                                    </div>
                                    <div class="cleared"></div>
                                </div>
                                <div class="cleared"></div>
                            </div>
                            <div class="cleared"></div>
                        </div>
                        <div class="cleared"></div>
                    </div>
                    <div class="cleared"></div>
                    <div class="art-footer">
                        <div class="art-footer-body">
                            <div class="art-footer-text">
                                <p>© ICOMP - Instituto de Computação</p>
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