<%-- 
    Document   : index
    Created on : 17/12/2014, 11:24:30
    Author     : TAMMY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html> 
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
                    $("#formTccNotas").submit(); 
                }); 
                $("#formTccNotas").validate({
                    rules:{
                        "tccNotas.nota1":{   
                            required:true
                        },
                        "tccNotas.nota2":{ 
                            required: true
                        },
                        "tccNotas.nota3":{ 
                            required: true
                        }
                    } /**
                    messages:{
                        "tccLocais.nome":{
                            required: "Informe o nome do local"
                        },
                        "tccLocais.descricao":{
                            required: "Informe a descricao do local"
                        }
                    }  **/
                });
            });
            
        </script>
        <script language="javascript">  
            function somente_numero(campo){  
                var digits="0123456789";  
                var campo_temp;   
                for (var i=0;i<campo.value.length;i++){  
                    campo_temp=campo.value.substring(i,i+1);  
                    if ( digits.indexOf(campo_temp)== -1 ){  
                        campo.value = campo.value.substring(0,i);  
                    }  
                }  
            }
            
            $(document).ready(function(){
                $("#campo-cpf").mask("999.999.999-99");
            });
        </script> 
    

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
                                        <a href="${pageContext.request.contextPath}/tccnotas">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Notas</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Notas</h2></div>
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

        <form id="formTccNotas" name="formTccNotas" method="POST" action="<c:url value="/tccnotas"/>"> 
            <p>
                <c:if test="${not empty tccNotas.id}">
                    <input type="hidden" name="tccNotas.id" value="${tccNotas.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p> 
            <p>
                <label for="nome">Nome:</label>  
                <input type="text" id="nome" name="tccNotas.tcctcc.aluno" value="${tccNotas.tcctcc.aluno}" size="100"/>
            </p>
            <p>
                <label for="descricao">Título:</label>         
                <input type="text" id="descricao" name="tccNotas.tcctcc.titulo" value="${tccNotas.tcctcc.titulo}" size="100" />
            </p>
            <p>
                <label for="nota1">Nota 1:</label>         
                <input type="text" id="nota1" name="tccNotas.nota1" value="${tccNotas.nota1}" size="100" />
            </p>
            <p>
                <label for="nota2">Nota 2:</label>         
                <input type="text" id="nota2" name="tccNotas.nota2" value="${tccNotas.nota2}" size="100" />
            </p>
            <p>
                <label for="nota3">Nota 3:</label>         
                <input type="text" id="nota3" name="tccNotas.nota3" value="${tccNotas.nota3}" size="100" />
            </p>
        </form>
    </body>
</html>