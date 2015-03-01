/**
 *
 * @author mhcabral
 */
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Listando Workshop</title>            

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
                    
                var table = document.getElementById("formTccWorkshop");
                var Radio=null;

                Radio = table.RadioGroup1;
                if(Radio.checked){
                    switch (variavel){
                        case 'edit':
                            document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio.value + "/edit";
                            return;
                        case 'remove':
                            decisao = confirm("Deseja realmente remover esse workshop?");
                            if (decisao){
                                document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio.value + "/remove";
                            } else {
                                alert ("Nenhum workshop foi removido");
                            }
                            return;
                        case "show":
                            document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio.value + "/show";                          
                            return;
                        }
                    } else {
                        for(var i=0;i<Radio.length;i++) 
                        {
                            if(Radio[i].checked) 
                            {
                                switch (variavel){
                                    case 'edit':
                                        document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio[i].value + "/edit";                          
                                        return;
                                    case 'remove':    
                                        decisao = confirm("Deseja realmente remover o workshop?");
                                        if (decisao){
                                            document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio[i].value + "/remove";
                                        } else {
                                            alert ("Nenhuma workshop foi removida");
                                        }                            
                                        return;
                                    case "show":
                                        document.location.href="${pageContext.request.contextPath}/tccworkshop/"+Radio[i].value + "/show";
                                        return;
                                    }
                                }
                            }

                            alert('Você precisa selecionar um workshop');
                        }
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
                                        
                                       
                                    },                            
                            
                                    change: function( event, ui ) {     
                                       
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
                    <div class="pagetitle icon-48-article"><h2>Workshop</h2></div>
                </div>
            </div>
            <br/>
            <p>
            
        </p>

        <div id="demo">
            <form id="formTccWorkshop">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>   
                            <th></th>
                            <th>Data</th>
                            <th>Trabalho</th>
                            <th>Aluno</th>
                            <th>Local</th>
                            <th>Avaliador1</th>
                            <th>Avaliador2</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tccWorkshopList}" var="tccWorkshop">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${tccWorkshop.id}"/></td>
                                <td class="center">${tccWorkshop.data}</td>
                                <td class="center">${tccWorkshop.tcctcc.titulo}</td>
                                <td class="center">${tccWorkshop.tcctcc.aluno.usuario.nome}</td>
                                <td class="center">${tccWorkshop.tcclocais.nome}</td>
                                <td class="center">${tccWorkshop.avaliador1}</td>
                                <td class="center">${tccWorkshop.avaliador2}</td>
                                
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>   
                            <th></th>
                            <th>Data</th>
                            <th>Trabalho</th>
                            <th>Aluno</th>
                            <th>Local</th>
                            <th>Avaliador1</th>
                            <th>Avaliador2</th>
                        </tr>
                    </tfoot>
                </table>                        
            </form>           
        </div>                             
    </div>
</body>
</html>
