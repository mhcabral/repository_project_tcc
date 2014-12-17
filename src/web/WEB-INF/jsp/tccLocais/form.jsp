<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
/**
 *
 * @author andre
 */
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
                    $("#formTccLocais").submit(); 
                }); 
                $("#formTccLocais").validate({
                    rules:{
                        "tccLocais.nome":{
                            required:true
                        },
                        "tccLocais.descricao":{
                            required: true
                        }
                    },
                    messages:{
                        "tccLocais.nome":{
                            required: "Informe o nome do local"
                        },
                        "tccLocais.descricao":{
                            required: "Informe a descricao do local"
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
                                        <a href="${pageContext.request.contextPath}/tcclocais">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Locais</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Locais</h2></div>
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

        <form id="formTccLocais" name="formTccLocais" method="POST" action="<c:url value="/tcclocais"/>"> 
            <p>
                <c:if test="${not empty tccLocais.id}">
                    <input type="hidden" name="tccLocais.id" value="${tccLocais.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <input type="hidden" name="tccLocais.estado" value="${tccLocais.estado}"/>
            </p> 
            <p>
                <label for="nome">Nome*:</label>
                <input type="text" id="nome" name="tccLocais.nome" value="${tccLocais.nome}" size="100"/>
            </p>
            <p>
                <label for="descricao">Descrição*:</label>
                <input type="text" id="descricao" name="tccLocais.descricao" value="${tccLocais.descricao}" size="100" />
            </p>
            <p>
                <label for="estado1">Estado*:</label>
                <input id="estado1" type="text" name="estado1" value="${tccLocais.estado}" size="30" disabled="true"/>
            </p>
        </form>
    </body>
</html>