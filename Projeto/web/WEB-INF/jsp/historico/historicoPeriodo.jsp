<%-- 
    Document   : historicoPeriodo
    Created on : 22/03/2013, 23:37:04
    Author     : Bruna
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <title>Histórico de Atividades Solicitadas por Período</title>            

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">    
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>

        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
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
            function adjust() {
                var periodoValue = $('#campo-periodo').val();
                window.location = "${pageContext.request.contextPath}/historico/periodo/"+periodoValue+"/search";
            }
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
                                
                                adjust();
                            },                            
                            
                            change: function( event, ui ) {     
                                
                                adjust();
                        
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
                        .attr( "title", "Todos os registros" )
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
                $( "#campo-periodo" ).combobox();
            });
        </script>

        <script type="text/javascript">
            function isEmpty(obj) {
                for(var prop in obj) {
                    if(obj.hasOwnProperty(prop))
                        return false;
                }
                return true;
            }
            
            google.load("visualization", "1", {packages:["corechart"]});
            google.setOnLoadCallback(drawChart);
                        
            function drawChart() {
                var periodoValue = $('#campo-periodo').val();
                
                var options = {
                    title: 'Atividades Solicitadas no Período'
                };

                $.getJSON("${pageContext.request.contextPath}/historico/periodo/buscaAtividades", {
                    idPeriodoLetivo : periodoValue
                },function(json) {
                    if(!isEmpty(json)){
                        var data = new google.visualization.DataTable();
                        data.addColumn('string', 'Atividade');
                        data.addColumn('number', 'Solicitações');
                    
                        $.each(json, function(i, item) {
                            data.addRow([ item.codigo+" "+item.nome, item.totalSolicitada ]);
                        });

                        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
                        chart.draw(data, options);
                    } else{
                        /*
                         * Se a lista retornada for vazia,
                         * esconde o gráfico e mostra uma msg informando q não há atividades para o periodo
                         */
                        $("#chart_div").hide();
                        $("#sumarizacao").hide();
                        document.getElementById("erro").style.visibility = "visible"; 
                    }
                });
            }
        </script>
    </head>
    <body id="dt_example">
        <div id="container">
            <div id="toolbar-box">
                <div class="m">
                    <div class="toolbar-list" id="toolbar">
                        <div class="cpanel2">
                            <div class="icon-wrapper">
                                <div class="icon">
                                    <ul>
                                        <li class="button" id="toolbar-cancel">
                                            <a href="${pageContext.request.contextPath}/" id="back">
                                                <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <div class="pagetitle icon-48-stats"><h2>Atividades Solicitadas por Período</h2></div>
                </div>
            </div>
            <div id="demo">
                <div class="ui-widget">
                    <label for="campo-periodo">Período Letivo:</label>
                    <select id="campo-periodo" name="campo-periodo">
                        <option value="0"></option>
                        <c:forEach var="periodo" items="${periodoList}">
                            <option value="${periodo.id}" <c:if test = "${periodo.id == idPeriodo}"> selected</c:if>>${periodo.codigo}</option>
                        </c:forEach>
                    </select>
                    <div id="sumarizacao">
                        <p>
                            <label for="campo-total">Total de solicitações:</label>
                            <input size="5" disabled="true" id="total" name="campo-total" value="${total}">
                        </p>
                    </div>
                </div>
            </div>

            <div id="chart_div" style="width: 900px; height: 500px;"></div>

            <div id="erro" name="erro" style="visibility:hidden">
                <div class="ui-widget">
                    <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <p>
                            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>Nenhuma atividade foi solicitada neste período!
                        </p>
                    </div>
                </div>   
            </div>
        </div>
    </body>
</html>