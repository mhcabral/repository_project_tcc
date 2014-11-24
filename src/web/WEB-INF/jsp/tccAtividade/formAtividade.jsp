<%-- 
    Document   : formAtividade
    Created on : 19/11/2014, 18:48:00
    Author     : mhcabral
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">

        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>        
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>

        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript"> 
     $(function() {
    
    $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formAtividade").submit(); 
                });
                $("#formAtividade").validate({
                    rules:{
                        "tccAtividade.datalimite":{
                            required: true,
                            minDate: 'today'
                        },                        
                        "tccAtividade.responsavel":{
                            required: true
                        },
                        "tccAtividade.descricao":{
                            required: true
                        },
                        "tccAtividade.id":{
                            required: true
                        }
                    },
                    messages:{
                        "tccAtividade.datalimite":{
                            required: "Informe a data limite",
                            minDate: "A data minima deve ser a data atual"
                        },                        
                        "tccAtividade.responsavel":{
                            required: "Selecione um responsavel"
                        },
                        "tccAtividade.descricao":{
                            required: "Descreva a atividade"
                        }
                    }
                });
});
        </script>
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
                            },change: function (event, ui){    
                                                                
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
                        .attr( "title", "Todos os registros")
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
                $("#campo-orientador").combobox();
            });
        </script>
        <script>
        $(function(){
        $("#calendario").datepicker({dateFormat: 'yy-mm-dd',
        minDate: 'today',
        maxDate: "+5M",
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'],
        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez']
    });
    $("#calendarioprorrogacao").datepicker({dateFormat: 'yy-mm-dd',
        minDate: 'today',
        maxDate: "+5M",
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado','Domingo'],
        dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez']
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Atividades</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Atividades</h2></div>
                </c:if>
            </div>
        </div>
                <form id="formAtividade" name="formAtividade" method="POST" action="<c:url value="/tccAtividade/atividade"/>">
                <c:if test="${not empty formAtividade.id}">
                    <input type="hidden" name="formAtividade.id" value="${formAtividade.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <div>    
                    <p>
                        <label>Data Limite:</label>
                        <input type="text" name="data" id="calendario" value="${tccAtividade.datalimite}"><br/>
                    </p>
                    <p>
                        <label for="cbAtividadeResponsavel">Responsável:</label><br/>
                        <select  id="cbAtividadeResponsavel" name="atividade-responsavel" value="${tccAtividade.responsavel}">
                            <option value="">Selecione um responsável</option>
                            <c:forEach var="string" items="${optList}">
                            <option  value="${string}" <c:if test = "${string == tccAtividade.responsavel}"> selected="true" </c:if>>${string}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label>Descricao:</label>
                        <input type="text" name="descricao" size="100" id="campo-descricao" value="${tccAtividade.descricao}"/><br/>
                    </p>
                    <p>
                        <label>Data da Prorrogacao:</label>
                        <input type="text" name="data" id="calendarioprorrogacao" value="${tccAtividade.dataprorrogacao}"><br/>
                    </p>
                    <p>
                        <b>Estado:</b>
                        ${tccAtividade.estado}
                    </p>
                </div>
                </form>
   </body>
</html>
