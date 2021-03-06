<%-- 
    Document   : form
    Created on : 07/02/2013, 20:47:55
    Author     : Bruna
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" />
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload_1.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
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
                    $("#formSolicitacao").submit(); 
                }); 
                $("#formSolicitacao").validate({
                    submitHandler: function(form){                        
                        confirmacao = confirm("Deseja realmente salvar as modificações?");
                        
                        if(confirmacao){
                            form.submit();
                        }                        
                    },
                    rules:{
                        "campo-atividade":{
                            required: true
                        },
                        "solicitacao.qntd_horas":{
                            required: true,
                            min: 1.0,
                            menorMaximo: '#info-grupo'
                        },
                        "mudanca.observacao":{
                            maxlength: 255,
                            required: true
                        }
                    },
                    messages:{
                        "campo-atividade":{
                            required: "Selecione uma Atividade"
                        },
                        "solicitacao.qntd_horas":{
                            required: "Informe a quantidade de horas da solicitacao",
                            min: "A quantidade de horas deve ser maior que 0",
                            menorMaximo: "O máximo de horas deve ser menor ou igual ao da atividade"
                        },
                        "mudanca.observacao":{
                            maxlength: "A justificativa deve conter no máximo 255 caracteres",
                            required: "Informe justificativa do indeferimento da solicitacao"
                        }
                    }
                });
            });
        </script>
        <style>
            .ui-combobox {
                position: relative;
                display: inline-block;
            }
            .ui-combobox-toggle {
                position: absolute;
                top: 0;
                bottom: 0;
                margin-left: -1px;
                padding: 0;
                /* support: IE7 */

            }
            .ui-combobox-input {
                margin: 0;
                padding: 0.3em;
            }
        </style>
        <script>
            (function( $ ) {
                $.widget( "ui.combobox", {
                    _create: function() {
                        var input,
                        that = this,
                        wasOpen = false,
                        select = this.element.hide(),
                        selected = select.children( ":selected" ),
                        value = selected.val() ? selected.text() : "",
                        wrapper = this.wrapper = $( "<span>" )
                        .addClass( "ui-combobox" )
                        .insertAfter( select );
 
                        function removeIfInvalid( element ) {
                            var value = $( element ).val(),
                            matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( value ) + "$", "i" ),
                            valid = false;
                            select.children( "option" ).each(function() {
                                if ( $( this ).text().match( matcher ) ) {
                                    this.selected = valid = true;
                                    return false;
                                }
                            });
 
                            if ( !valid ) {
                                // remove invalid value, as it didn't match anything
                                $( element )
                                .val( "" )
                                .attr( "title", value + " não encontrado" )
                                .tooltip( "open" );
                                select.val( "" );
                                setTimeout(function() {
                                    input.tooltip( "close" ).attr( "title", "" );
                                }, 2500 );
                                input.data( "ui-autocomplete" ).term = "";
                            }
                        }
 
                        input = $( "<input>" )
                        .appendTo( wrapper )
                        .val( value )
                        .attr( "title", "" )
                        .addClass( "ui-state-default ui-combobox-input" )
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                            source: function( request, response ) {
                                var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                                response( select.children( "option" ).map(function() {
                                    var text = $( this ).text();
                                    if ( this.value && ( !request.term || matcher.test(text) ) )
                                        return {
                                            label: text.replace(
                                            new RegExp(
                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                                $.ui.autocomplete.escapeRegex(request.term) +
                                                ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                        ), "<strong>$1</strong>" ),
                                            value: text,
                                            option: this
                                        };
                                }) );
                            },
                            select: function( event, ui ) {
                                ui.item.option.selected = true;
                                that._trigger( "selected", event, {
                                    item: ui.item.option
                                });
                            },
                            change: function( event, ui ) {                                
                                if($('#campo-atividade').val()){
                                    document.getElementById("label-info").style.visibility = "visible"; 
                                    document.getElementById("info-grupo").style.visibility = "visible";
                    
                                    $.getJSON('${pageContext.request.contextPath}/regras/buscaAtividadePorAtividadeCurso.json', {
                                        id : $('#campo-atividade').val(),
                                        idSolicitante : ${solicitacao.solicitante.id}
                                    }, function(data) {
                                        document.getElementById("info-grupo").value = data.maximoHoras;
                                    });
                                } else {
                                    document.getElementById("label-info").style.visibility = "hidden"; 
                                    document.getElementById("info-grupo").style.visibility = "hidden";
                                    document.getElementById("info-grupo").value = "";
                                }
                            }
                        })
                        .addClass( "ui-widget ui-widget-content ui-corner-left" );
 
                        input.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
                            return $( "<li>" )
                            .append( "<a>" + item.label + "</a>" )
                            .appendTo( ul );
                        };
 
                        $( "<a>" )
                        .attr( "tabIndex", -1 )
                        .attr( "title", "Todas as Atividades" )
                        .tooltip()
                        .appendTo( wrapper )
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass( "ui-corner-all" )
                        .addClass( "ui-corner-right ui-combobox-toggle" )
                        .mousedown(function() {
                            wasOpen = input.autocomplete( "widget" ).is( ":visible" );
                        })
                        .click(function() {
                            input.focus();
 
                            // close if already visible
                            if ( wasOpen ) {
                                return;
                            }
 
                            // pass empty string as value to search for, displaying all results
                            input.autocomplete( "search", "" );
                        });
 
                        input.tooltip({
                            tooltipClass: "ui-state-highlight"
                        });
                    },
 
                    _destroy: function() {
                        this.wrapper.remove();
                        this.element.show();
                    }
                });
            })( jQuery );
 
            $(function() {
                $( "#campo-atividade" ).combobox();
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
        </script> 
        <script>
            /* Portuguese initialisation for the jQuery UI date picker plugin. */
            jQuery(function ($) {
                $.datepicker.regional['pt'] = {
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
                $.datepicker.setDefaults($.datepicker.regional['pt']);
            });
        </script>
        <script>
            var dataSetada = false;
            $(function() {
                var dates = $( "#campo-inicio, #campo-termino" ).datepicker({
                    changeMonth: true,  
                    changeYear: true,
                    option: jQuery.datepicker.regional['pt-BR'],
                    beforeShow: function (input, inst) {
                        if (!dataSetada) {
                            //Pega a instancia do datepicker clicado
                            instance = $(this).data("datepicker");
                            //Converte a data
                            date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, new Date().toLocaleString().split(" ")[0], instance.settings);
                            dateIngresso = '${dataIngresso}';

                            //Se o campo clicado não for o data Final, será descartado
                            if (this.id != "campo-termino") {
                                $("#campo-inicio").datepicker("option", "maxDate", date);
                                $("#campo-inicio").datepicker("option", "minDate", dateIngresso);
                                return;
                            }

                            //Seta data maxima e minima no datePicker DataTermino
                            $("#campo-termino").datepicker("option", "maxDate", date);
                            $("#campo-termino").datepicker("option", "minDate", dateIngresso);

                            dataSetada = true;
                        }
                    },
                    onSelect: function (selectedDate) {
                        var option = this.id == "campo-inicio" ? "minDate" : "maxDate";
                        instance = $(this).data("datepicker");
                        date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                        dates.not(this).datepicker("option", option, date);

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
                                        <a href="${pageContext.request.contextPath}/analise/${solicitacao.id}/show" id="cancel">
                                            <span width="32" height="32" border="0" class="icon-32-cancel"></span>Cancelar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-article-edit"><h2>Ajuste da Solicitação</h2></div>
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

        <form id="formSolicitacao" name="formSolicitacao" method="POST" action="${pageContext.request.contextPath}/analise" enctype="multipart/form-data">

            <input type="hidden" name="solicitacao.id" value="${solicitacao.id}"/>
            <input type="hidden" name="_method" value="put"/>
            <p>
                <label for="campo-matricula">Matrícula:</label><br/>
                <input style="height: 20px;" size="10" type="text" id="campo-matricula" name="aluno.id" value="${aluno.matricula}" disabled="true"/><br/>
                <label for="campo-aluno">Aluno:</label><br/>
                <input style="height: 20px;" size="50" type="text" id="campo-aluno" name="aluno.nomeAluno" value="${aluno.usuario.nome}" disabled="true"/><br/>
                <label for="campo-curso">Curso:</label><br/>
                <input style="height: 20px;" size="50" type="text" id="campo-curso" name="aluno.curso" value="${aluno.curso}" disabled="true"/><br/>
            </p>
            <div class="ui-widget">
                <table width="100%">
                    <tr>
                        <td style="border-style:hidden">
                            <font size="2">
                            <label for="campo-atividade">Atividade*:</label><img src="${pageContext.request.contextPath}/img/edit_lapis.png" width="16" height="16"><br/>
                            <select id="campo-atividade" name="solicitacao.atividade.id" value="${solicitacao.atividade}" >
                                <option value="">Selecione uma Atividade</option>
                                <c:forEach var="atividade" items="${atividadeList}">
                                    <option value="${atividade.id}" <c:if test = "${atividade.id == solicitacao.atividade.id}"> selected</c:if>>${atividade}</option>
                                </c:forEach>
                            </select>
                            </font>
                        </td>
                        <td style="border-style:hidden">
                            <label <c:if test="${empty solicitacao.id}">style="visibility:hidden;"</c:if> id="label-info" for="info-grupo"> Máximo de horas da atividade:</label>
                            <input <c:if test="${empty solicitacao.id}">style="visibility:hidden;"</c:if> type="text" class="number" id="info-grupo" name="info-grupo" disabled="true" size="4" <c:if test="${not empty solicitacao.id}">value="${horaMaxima}</c:if>"/>
                            </td>
                        </tr>
                    </table>                        
                </div>
                <p>
                    <label for="campo-descricao">Descrição:</label><br/>
                        <input style="height: 20px;" size="50" type="text" id="campo-descricao" name="solicitacao.descricao" value="${solicitacao.descricao}" disabled="true"/>
            </p><p>
                <label for="campo-horas">Quantidade de Horas*:</label><img src="${pageContext.request.contextPath}/img/edit_lapis.png" width="16" height="16"><br/>
                <input onKeyUp="javascript:somente_numero(this);" style="height: 20px;" size="10" type="text" id="campo-horas" name="solicitacao.qntd_horas" value="${solicitacao.qntd_horas}"/>
            </p><p>
                <label  for="campo-inicio">Data de Início:</label><br/>
                <input id="campo-inicio" name="solicitacao.dataInicio" value="${solicitacao.dataInicio}" disabled="true"/>
            </p><p>
                <label  for="campo-termino">Data de Término:</label><br/>
                <input id="campo-termino" name="solicitacao.dataTermino" value="${solicitacao.dataTermino}" disabled="true"/>

            </p><p>
                <label for="campo-observacoes">Observações:</label><br/>
                <textarea rows="5" cols="51" id="campo-observacoes" name="solicitacao.observacoes" disabled="true">${solicitacao.observacoes}</textarea>
            </p>                                             
            <label for="campo-arquivo">Anexos:</label><br/>            
            <ul>
                <c:forEach items="${solicitacao.anexos}" var="anexo">
                    <li><a href="${pageContext.request.contextPath}/analise/download/${anexo}">${anexo}</a></li>                    
                </c:forEach>
            </ul>                                
        </form>
    </body>
</html>