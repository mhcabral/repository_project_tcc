<%-- 
    Document   : form
    Created on : 03/02/2013, 23:45:52
    Author     : Thiago Santos
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
            $.validator.addMethod("menorMaximo",
            function (value, element, param) {
                var $max = $(param);
                return parseInt(value) <= parseInt($max.val());
            });
            
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formTccTema").submit(); 
                });
                $("#formTccTema").validate({
                    rules:{
                        "regraGrupo.maximoHoras":{
                            required: true,
                            min: 1.0,
                            menorMaximo: '#info-grupo'
                        },                        
                        "regraGrupo.grupo.id":{
                            required: true
                        }
                    },
                    messages:{
                        "regraGrupo.maximoHoras":{
                            required: "Informe o máximo de horas",
                            min: "O mínimo de horas deve ser maior que 0",
                            menorMaximo: "O máximo de horas deve ser menor ou igual ao do curso"
                        },                        
                        "regraGrupo.grupo.id":{
                            required: "Selecione um Grupo"
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
                $("#campo-grupo").combobox();
                $("#campo-curso").combobox();
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
                                        <a href="${pageContext.request.contextPath}/regras/grupos">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} do Tema</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} do Tema</h2></div>
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

        <h:form id="formTccTema" name="formTccTema" method="POST" action="<c:url value="/tccTema/salvar"/>"> 
            <c:if test="${not empty tccTema.id}">
                <input type="hidden" name="tccTema.id" value="${tccTema.id}"/>
                <input type="hidden" name="_method" value="put"/>
            </c:if>
            <p>
                <label id="lbTemaArea" for="cdTemaArea">Área*:</label><br/>
                <select  id="cbTemaArea" name="tema-area" value="${tccTema.area}">
                    <option value="">Selecione uma Área</option>
                    <option value="Banco de Dados e Recuperação de Informação">Banco de Dados e Recuperação de Informação</option>
                    <option value="Engenharia de Software">Engenharia de Software</option>
                    <option value="Informática na Educação">Informática na Educação</option>
                    <option value="Inteligência Artificial">Inteligência Artificial</option>
                    <option value="Otimização, Algorítmos e Complexidade Computacional">Otimização, Algorítmos e Complexidade Computacional</option>
                    <option value="Redes de Computadores e Sistemas Distribuídos">Redes de Computadores e Sistemas Distribuídos</option>
                    <option value="Sistemas Embarcados">Sistemas Embarcados</option>
                    <option value="Visão Computacional e Robótica">Visão Computacional e Robótica</option>
                    <option value="Outra">Outra</option>
                </select><br/>
            </p>
            <p>
                <label id="lbTemaSigla" for="edTemaSigla">Sigla*:</label>
                <input type="text" id="edTemaSigla" name="edTemaSigla" value="${tccTema.sigla}" size="4"/>
            </p>
            <p>
                <label id="lbTemaCurso" for="selTemaCurso">Cursos:</label>
                <SELECT NAME = "selTemaCurso" SIZE=5 MULTIPLE>
                    <c:forEach items="${cursosList}" var="tccTemaCurso">
                        <OPTION value="${tccTemaCurso.id}" >${tccTemaCurso.curso}</option>
                    </c:forEach>

                </SELECT>
            </p>
        </h:form>
    </body>
</html>