<%-- 
    Document   : /tccAtividade/index
    Created on : 18/11/2014, 08:00:00
    Author     : André Bemfica 21003782
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Listando Atividades</title>            

        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/css/demo_page.css";
            @import "${pageContext.request.contextPath}/css/demo_table.css";
        </style>        

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">

        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>

        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#example').dataTable({
                    "bFilter": false,
                    "oLanguage": {
                        "sProcessing":   "Processando...",
                        "sLengthMenu":   "Mostrar _MENU_ registros",
                        "sZeroRecords":  "Não foram encontrados resultados",
                        "sInfo":         "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                        "sInfoEmpty":    "Mostrando de 0 até 0 de 0 registros",
                        "sInfoFiltered": "(filtrado de _MAX_ registros no total)",
                        "sInfoPostFix":  "",
                        "sSearch":       "Buscar:",
                        "sUrl":          "",
                        "oPaginate": {
                            "sFirst":    "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext":     "Seguinte",
                            "sLast":     "Último"
                        }
                    }
                });            
            } );
        </script>
        <script language="JavaScript" type="text/javascript">
            function radioHab(variavel) {
                    
                var table = document.getElementById("formTccAtividade");
                var Radio=null;

                Radio = table.RadioGroup1;
                if(Radio.checked){
                    switch (variavel){
                        case 'edit':
                            document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio.value + "/edit";
                            return;
                        case 'remove':
                            decisao = confirm("Deseja realmente remover a atividade?");
                            if (decisao){
                                document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio.value + "/remove";
                            } else {
                                alert ("Nenhuma atividade foi removida");
                            }
                            return;
                        case "show":
                            document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio.value + "/show";                          
                            return;
                        }
                    } else {
                        for(var i=0;i<Radio.length;i++) 
                        {
                            if(Radio[i].checked) 
                            {
                                switch (variavel){
                                    case 'edit':
                                        document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio[i].value + "/edit";                          
                                        return;
                                    case 'remove':    
                                        decisao = confirm("Deseja realmente remover a atividade?");
                                        if (decisao){
                                            document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio[i].value + "/remove";
                                        } else {
                                            alert ("Nenhuma atividade foi removida");
                                        }                            
                                        return;
                                    case "show":
                                        document.location.href="${pageContext.request.contextPath}/tccAtividade/"+Radio[i].value + "/show";
                                        return;
                                    }
                                }
                            }

                            alert('Você precisa selecionar uma atividade');
                        }
                    }
                    
                    function criarAtividades() {
                        var periodo = document.getElementById('campo-periodo');
                        
                        document.location.href="${pageContext.request.contextPath}/tccAtividade/"+periodo.value + "/create";
                    }
                
                    function adjustVagas() {
                        var idPeriodo =$('#campo-periodo').val();
                    
                        window.location = "${pageContext.request.contextPath}/tccAtividade/"+idPeriodo+"/index";
                        
                    }
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

                                adjustVagas();
                            },                            

                            change: function( event, ui ) {     
                                adjustVagas();
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
                $("#campo-periodo").combobox();
            });
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
                                        <li class="button" id="toolbar-apply">
                                            <a href="javascript:criarAtividades()">
                                                <span width="32" height="32" border="0" class="icon-32-new"></span>Gerar
                                            </a>
                                        </li>
                                        <li class="button" id="toolbar-apply">
                                            <a href="javascript:radioHab('show')">
                                                <span width="32" height="32" border="0" class="icon-32-preview"></span>Visualizar
                                            </a>
                                        </li>
                                        <li class="button" id="toolbar-apply">
                                            <a href="javascript:radioHab('edit')">
                                                <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                            </a>
                                        </li>                                   
                                        <li class="button" id="toolbar-cancel">
                                            <a href="${pageContext.request.contextPath}/tcc/index" id="back">
                                                <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <div class="pagetitle icon-48-article"><h2>Atividades de TCC</h2></div>
                </div>
                <div>
                    <c:if test="${not empty errors}">
                        <div class="ui-widget">
                            <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                <ul>
                                    <c:forEach items="${errors}" var="error">
                                        <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                        <li style="color: #cd0a0a">${error.message}</li>
                                        </c:forEach>        
                                </ul>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div> <br>
            
           
            <p>
            <h4>Filtrar por:</h4> <br>
                <div class="ui-widget">
                    <label for="campo-periodo">Periodo:</label>
                    <select id="campo-periodo" name="campo-periodo">
                        <c:forEach var="periodo" items="${periodoLetivoList}">
                            <option value="${periodo.id}" <c:if test = "${periodo.id == idPeriodo}"> selected="true" </c:if>>${periodo}</option>
                        </c:forEach>
                    </select>
                </div>
            </p>
            

            <div id="demo">
                <form id="formTccAtividade">
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                        <thead>
                            <tr>   
                                <th></th>
                                <th>Data Limite</th>
                                <th>Responsável</th>
                                <th>Descrição</th>
                                <th>Prorrogação</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>

                            <c:forEach items="${tccAtividadeList}" var="tccAtividade">
                                <tr>
                                    <td><input type="radio" name="RadioGroup1" value="${tccAtividade.id}"/></td>
                                    <td class="center"><fmt:formatDate value="${tccAtividade.datalimite}" pattern="dd/MM/yyyy"/></td>
                                    <td class="center">${tccAtividade.responsavel}</td>
                                    <td class="center" style="width: 30%">${tccAtividade.descricao}</td>
                                    <td class="center"><fmt:formatDate value="${tccAtividade.dataprorrogacao}" pattern="dd/MM/yyyy"/></td>
                                    <td class="center">${tccAtividade.estado}</td>
                                </tr>
                            </c:forEach>  

                        </tbody>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>Data Limite</th>
                                <th>Responsável</th>
                                <th>Descrição</th>
                                <th>Prorrogação</th>
                                <th>Estado</th>
                            </tr>
                        </tfoot>
                    </table>                        
                </form>           
            </div>                             
        </div>
    </body>
</html>
