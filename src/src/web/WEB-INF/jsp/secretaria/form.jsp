<%-- 
    Document   : form
    Created on : 21/03/2013, 09:00:28
    Author     : Thiago Santos
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>        
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formSecretaria").submit(); 
                }); 
                $("#formSecretaria").validate({
                    rules:{                        
                        "secretaria.cursos[].id":{
                            required: true
                        },                        
                        "secretaria.usuario.cpf":{
                            required:true
                        },
                        "secretaria.usuario.nome":{
                            required: true,
                            maxlength: 255
                        },
                        "secretaria.usuario.email":{
                            required: true,
                            email: true,
                            maxlength: 255
                        },
                        "secretaria.usuario.cpf" : {  
                            cpf: 'both'
                        }  
                    },
                    messages:{                                                
                        "secretaria.cursos[].id":{
                            required: "Selecione um Curso"
                        },                        
                        "secretaria.usuario.cpf":{
                            required: "Informe o CPF do secretaria"
                        },
                        "secretaria.usuario.nome":{
                            required: "Informe o nome do secretaria",
                            maxlength: "O nome deve conter no máximo 255 caracteres"
                        },           
                        "secretaria.usuario.email":{
                            required: "Informe o e-mail do secretaria",
                            email: "Informe um e-mail válido",
                            maxlength: "O e-mail deve conter no máximo 255 caracteres"
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
        <script>
            /* Portuguese initialisation for the jQuery UI date picker plugin. */
            jQuery(function ($) {
                $.datepicker.regional['pt-PT'] = {
                    closeText: 'Fechar',
                    prevText: 'Anterior',
                    nextText: 'Seguinte',
                    currentText: 'Hoje',
                    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                        'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                    dayNames: ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'],
                    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    weekHeader: 'Sem',
                    dateFormat: 'dd/mm/yy',
                    firstDay: 0,
                    isRTL: false,
                    showMonthAfterYear: false,
                    yearSuffix: ''
                };
                $.datepicker.setDefaults($.datepicker.regional['pt-PT']);
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
                                        <a href="${pageContext.request.contextPath}/secretarias">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Secretaria</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Secretaria</h2></div>
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

        <form id="formSecretaria" name="formSecretaria" method="POST" action="<c:url value="/secretarias"/>"> 
            <p>
                <c:if test="${not empty secretaria.id}">
                    <input type="hidden" name="secretaria.id" value="${secretaria.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            
            <input hidden="true" type="text" name="secretaria.usuario.id" value="${secretaria.usuario.id}"/>
            
            <p>
                <label for="campo-nome">Nome*:</label><br/>
                <input size="50" type="text" id="campo-nome" name="secretaria.usuario.nome" value="${secretaria.usuario.nome}"/><br/>
            </p>
            <p>
                <label for="campo-cpf">CPF*:</label><br/>
                <input size="13" type="text" id="campo-cpf" name="secretaria.usuario.cpf" value="${secretaria.usuario.cpf}"/><br/>
            </p>
            <p>
                <label for="campo-email">E-mail*:</label><br/>
                <input size="50" type="text" id="campo-email" name="secretaria.usuario.email" value="${secretaria.usuario.email}"/><br/>
            </p>                                    
            
            <p>
                <font size="2">
                <label for="campo-curso">Curso(s)*:</label><br/>

                <c:if test="${empty secretaria.id}">
                <ul style="height: 100px; overflow: auto; width: 400px; border: 1px solid #000; list-style-type: none; margin: 0; padding: 0; overflow-x: hidden;">
                    <c:forEach items="${cursoList}" var="curso" varStatus="s">
                        <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="secretaria.cursos[${s.index}].id" id="${curso.id}" value="${curso.id}"/>${curso}</label></li>
                            </c:forEach>
                </ul>
            </c:if>

            <c:if test="${not empty secretaria.id}">
                <%boolean flag = false;%>                
                <ul style="height: 100px; overflow: auto; width: 400px; border: 1px solid #000; list-style-type: none; margin: 0; padding: 0; overflow-x: hidden;">
                    <c:forEach items="${cursoList}" var="curso" varStatus="s">
                        <c:forEach items="${secretaria.cursos}" var="cursoSecretaria">
                            <%if (flag != true) {%>
                            <c:if test="${curso.id == cursoSecretaria.id}">
                                <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="secretaria.cursos[${s.index}].id" id="${curso.id}" checked="true" value="${curso.id}"/>${curso}</label></li>
                                        <%flag = true;%>
                                    </c:if>
                                    <%}%>
                                </c:forEach>                          
                                <%if (flag != true) {%>
                        <li style="margin: 0; padding: 0;"><label for="${curso.id}"><input type="checkbox" name="secretaria.cursos[${s.index}].id" id="${curso.id}" value="${curso.id}"/>${curso}</label></li>
                                <%}
                                    flag = false;%>
                            </c:forEach>
                </ul>
            </c:if>                        
            </font><br/>
        </p>
                                                                                                                                                            
        </form>
    </body>
</html>