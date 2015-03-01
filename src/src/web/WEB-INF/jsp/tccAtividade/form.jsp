<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%--
 
  @author andre
--%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/js/ui/jquery-ui.css">
        <script src="${pageContext.request.contextPath}/js/ui/jquery-ui.js"></script>
                       
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(document).ready(function(){
                $(".data").datepicker({
                    dateFormat: 'dd/mm/yy',
                    dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'],
                    dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
                    dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
                    monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
                    monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
                    nextText: 'Próximo',
                    prevText: 'Anterior'
                });
            });
        </script>
        <script laguage="Javascript" type="text/javascript">
            
            $(function () { 
               
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formTccAtividade").submit(); 
                }); 
                $("#formTccAtividade").validate({
                    rules:{
                        "tccAtividade.responsavel":{
                            required:true
                        },
                        "tccAtividade.descricao":{
                            required: true
                        }
                    },
                    messages:{
                        "tccAtividade.responsavel":{
                            required: "Selecione um responsável"
                        },
                        "tccAtividade.descricao":{
                            required: "Informe a descrição"
                        }
                    }
                });
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
                                        <a href="${pageContext.request.contextPath}/tccAtividade/0/index">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Atividade</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Atividade</h2></div>
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
                            <li style="color:#cd0a0a">${error.message}</li>
                        </c:forEach>        
                    </ul>
                    </p>
                </div>
            </div>   
        </c:if>

        <form id="formTccAtividade" name="formTccAtividade" method="POST" action="<c:url value="/tccAtividade/0/index"/>"> 
            <p>
                <c:if test="${not empty tccAtividade.id}">
                    <input type="hidden" name="tccAtividade.id" value="${tccAtividade.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <input type="hidden" name="tccAtividade.estado" value="${tccAtividade.estado}"/>
                <input type="hidden" name="tccAtividade.periodo.id" value="${tccAtividade.periodo.id}"/>
                <input type="hidden" name="tccAtividade.ordem" value="${tccAtividade.ordem}"/>
                <input type="hidden" name="tccAtividade.responsavel" value="${tccAtividade.responsavel}"/>
                <input type="hidden" name="tccAtividade.descricao" value="${tccAtividade.descricao}"/>
            </p> 
            <p>
                <label for="datalimite">Data Limite:</label>
                <input name="tccAtividade.datalimite" type="text" id="datalimite" value="<fmt:formatDate value="${tccAtividade.datalimite}" pattern="dd/MM/yyyy"/>"  class="data"> <br/>
                
            </p>
            <p>
                <label for="dataprorrogacao">Data da Prorrogacao:</label>
                <input type="text" name="tccAtividade.dataprorrogacao" id="dataprorrogacao" value="<fmt:formatDate value="${tccAtividade.dataprorrogacao}" pattern="dd/MM/yyyy"/>" class="data"> <br/>
            </p>
            <p>
                <label >Responsável:</label><br/>
                <input type="text" value="${tccAtividade.responsavel}" disabled="true">
            </p>
            <p>
                <label>Descricao:</label><br/>
                <textarea disabled="true" cols="50" rows="4">${tccAtividade.descricao}</textarea><br/>
            </p>
            <p>
                <label for="estado1">Estado*:</label>
                <input id="estado1" type="text" name="campo-estado" value="${tccAtividade.estado}" size="30" disabled="true"/>
            </p>
        </form>
    </body>
</html>