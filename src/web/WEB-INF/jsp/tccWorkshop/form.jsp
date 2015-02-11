<%-- 
    Document   : form
    Created on : 19/01/2015, 11:39:38
    Author     : mhcabral
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>
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
                
                $("#formTccWorkshop").validate({
                    rules:{
                        "tccWorkshop.data":{
                            required:true
                        },
                        "tccWorkshop.tcclocais.id":{
                            required: true
                        },
                        "tccWorkshop.avaliador1":{
                            required: true
                        },
                        "tccWorkshop.avaliador2":{
                            required: true
                        }
                    },
                    messages:{
                        "tccWorkshop.data":{
                            required: "Informe a data"
                        },
                        "tccWorkshop.tcclocais.id":{
                            required: "Selecione um local"
                        },
                        "tccWorkshop.avaliador1":{
                            required: "Selecione um avaliador1"
                        },
                        "tccWorkshop.avaliador2":{
                            required: "Selecione um avaliador2"
                        }
                    }
                });
            });
        </script>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formTccWorkshop").submit(); 
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
                                        <a href="${pageContext.request.contextPath}/tccworkshop">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} do Workshop</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} do Workshop</h2></div>
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

        <form id="formTccWorkshop" name="formTccWorkshop" method="POST" action="<c:url value="/tccworkshop"/>"> 
            <p>
                <c:if test="${not empty tccWorkshop.id}">
                    <input type="hidden" name="tccWorkshop.id" value="${tccWorkshop.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            <input type="hidden" name="tccWorkshop.tcctcc.id" value="${tccWorkshop.tcctcc.id}"/>
            <p>
                <label for="data">Data*:</label>
                <input name="tccWorkshop.data" type="text" id="data" value="<fmt:formatDate value="${tccWorkshop.data}" pattern="dd/MM/yyyy"/>"  class="data"> <br/>
            </p>
            <p>
                <label for="locais">Local*:</label><br/>
                <select id="locais" name="tccWorkshop.tcclocais.id" value="${tccWorkshop.tcclocais}" style="width: 500px">
                    <option value="" >Selecione um Local</option>
                    <c:forEach var="locaisList" items="${tccLocais}">
                        <option  value="${locaisList.id}" <c:if test = "${locaisList.id == tccWorkshop.tcclocais.id}"> selected="true" </c:if> >${locaisList.nome}</option>
                    </c:forEach>
                </select>
            </p>
            <p>
                <label for="avaliador1">Primeiro Avaliador*:</label><br/>
                <select id="avaliador1" name="tccWorkshop.avaliador1" value="${tccWorkshop.avaliador1}" style="width: 500px">
                    <option value="" >Selecione um Avaliador</option>
                    <c:forEach var="avaliadorList" items="${tccAvaliadores}">
                        <option  value="${avaliadorList.nome}" <c:if test = "${avaliadorList.nome == tccWorkshop.avaliador1}"> selected="true" </c:if> >${avaliadorList.nome}</option>
                    </c:forEach>
                </select>
            </p>
            <p>
                <label for="avaliador2">Segundo Avaliador*:</label><br/>
                <select id="avaliador2" name="tccWorkshop.avaliador2" value="${tccWorkshop.avaliador2}" style="width: 500px">
                    <option value="" >Selecione um Avaliador</option>
                    <c:forEach var="avaliadorList" items="${tccAvaliadores}">
                        <option  value="${avaliadorList.nome}" <c:if test = "${avaliadorList.nome == tccWorkshop.avaliador2}"> selected="true" </c:if> >${avaliadorList.nome}</option>
                    </c:forEach>
                </select>
            </p>
        </form>
    </body>
</html>