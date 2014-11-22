<%-- 
    Document   : form
    Created on : 03/02/2013, 23:45:52
    Author     : Bruna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Regras de Atividade</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $.validator.addMethod("menorMaximo",
            function (value, element, param) {
                var $max = $(param);
                return parseInt(value) <= parseInt($max.val());
            });
            
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formRegra").submit(); 
                });
                $("#formRegra").validate({
                    rules:{
                        "regra.maximoHoras":{
                            required: true,
                            min: 1.0,
                            menorMaximo: '#info-grupo'
                        },
                        "regra.atividade.id":{
                            required: true
                        },
                        "campo-grupo":{
                            required: true
                        }
                    },
                    messages:{
                        "regra.maximoHoras":{
                            required: "Informe o máximo de horas",
                            min: "O mínimo de horas deve ser maior que 0",
                            menorMaximo: "O máximo de horas deve ser menor ou igual ao do grupo selecionado"
                        },
                        "regra.atividade.id":{
                            required: "Selecione uma Atividade"
                        },
                        "campo-grupo":{
                            required: "Selecione um Grupo"
                        }
                    }
                });
            });
            
            $(function(){
                $('#campo-grupo').change(function(){
                    adjustCampoAtividade();
                }).change();
            });
            
            function adjustCampoAtividade() {     
                var grupoValue = $('#campo-grupo').val();
                var dropdownSet = $('#campo-atividade');
                if (grupoValue.length == 0)
                {
                    dropdownSet.attr("disabled", true);
                    dropdownSet.emptySelect();
                }
                else
                {
                    dropdownSet.attr("disabled", false);
                    $.getJSON('${pageContext.request.contextPath}/regras/buscaAtividade.json', {
                        id : grupoValue
                    }, function(data) {
                        dropdownSet.loadSelect(data);
                    });
                }
            }
            
            (function($) {
                $.fn.emptySelect = function() {
                    return this.each(function(){
                        if (this.tagName=='SELECT') this.options.length = 0;
                    });
                }

                $.fn.loadSelect = function(data) {
                    return this.emptySelect().each(function(){
                        if (this.tagName=='SELECT') {
                            var selectElement = this;
                            var option = new Option("Selecione uma atividade", "");
                            
                            selectElement.add(option);
                            
                            $.each(data,function(index,optionData){
                                var option = new Option("["+optionData.id+"]"+optionData.codigo + " - " + optionData.nome, optionData.id);
                                
                                if(${not empty regra.id} && $('#hidden-atividade').val() == optionData.id ){
                                    option.selected = true;
                                }
                                
                                if ($.browser.msie) {
                                    selectElement.add(option);
                                }
                                else {
                                    selectElement.add(option,null);
                                }
                            });
                        }
                    });
                }
            })(jQuery);
            
            function muda(){
                if($('#campo-grupo').val()){
                    $.getJSON('${pageContext.request.contextPath}/regras/buscaGrupo.json', {
                        id : $('#campo-grupo').val()
                    }, function(data) {
                        document.getElementById("info-grupo").value = data.maximoHoras;
                    });
                    document.getElementById("label-info").style.visibility = "visible"; 
                    document.getElementById("info-grupo").style.visibility = "visible";
                } else {
                    document.getElementById("label-info").style.visibility = "hidden"; 
                    document.getElementById("info-grupo").style.visibility = "hidden";
                    document.getElementById("info-grupo").value = "";
                }
            }
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
                                        <a href="${pageContext.request.contextPath}/regras">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Regra</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Regra</h2></div>
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

        <form id="formRegra" name="formRegra" method="POST" action="<c:url value="/regras"/>"> 
            <c:if test="${not empty regra.id}">
                <input type="hidden" name="regra.id" value="${regra.id}"/>
                <input type="hidden" name="_method" value="put"/>
                <input type="hidden" id="hidden-atividade" value="${regra.atividade.id}"/>
            </c:if>

            <p>
                <label id="label-info" for="info-grupo"> Máximo de horas do '${regraGrupoInfo.regraGrupo.grupo}'</label>
                <input type="text" class="number" id="info-grupo" name="info-grupo" disabled="true" size="4" value="${regraGrupoInfo.regraGrupo.maximoHoras}"/>
            </p><p>
                <label for="campo-atividade">Atividade*:</label><br/>
                <select id="campo-atividade" name="regra.atividade.id"  value="${regra.atividade.id}" >
                    <option value="">Selecione uma Atividade</option>
                    <c:forEach var="atividade" items="${atividadeList}">
                        <option  value="${atividade.id}" <c:if test = "${atividade.id == regra.atividade.id}"> selected="true" </c:if>>${atividade}</option>
                    </c:forEach>
                </select><br/>
            </p><p>
                <label for="campo-maximoHoras">Máximo de Horas*:</label></br>
                <input size="10" onKeyUp="javascript:somente_numero(this);" type="text" id="campo-id" name="regra.maximoHoras" value="${regra.maximoHoras}"/><br/>
            </p>
        </form>
    </body>
</html>