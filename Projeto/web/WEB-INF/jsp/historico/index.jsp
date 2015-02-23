<%-- 
    Document   : index
    Created on : 14/02/2013, 16:35:49
    Author     : Bruna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <title>Listando Alunos</title>            

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
            var table = document.getElementById('formHistorico');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/historico/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/historico/"+Radio[i].value + "/show";                          
                                    return;
                                }                    
                            }                
                        }
                    
                        alert('Você precisa selecionar um dos alunos');
                    }
                }
                
                function adjustVagas() {
                    var peridoLetivoValue = $('#campo-periodoLetivo').val();
                    var cursoValue = $('#campo-curso').val();
                    var alunoValue = 0;
                            
                    window.location = "${pageContext.request.contextPath}/historico/"+peridoLetivoValue+"/"+ cursoValue+"/search";
                }
                     
                $(function() {
                    $("#campo-periodoLetivo").combobox();
                });
    </script>
    <c:if test="${sessionData.usuario.perfil != 1}">
        <script>
                    $(function() {
                        $("#campo-curso").combobox();
                    }); 
        </script>
    </c:if>
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
</head>
<body id="dt_example">
    <c:if test="${sessionData.usuario.perfil == 1}">
        <input type="hidden" id="campo-curso" name="campo-curso" value="${idCurso}"/>
    </c:if>
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
            <div class="pagetitle icon-48-article"><h2>Histórico - Atividades Complementares</h2></div>
        </div>
    </div>
    <br/>
    <div>
        <div class="ui-widget">
            <table>
                <tr>
                    <td><label style="padding-left:80px" for="campo-periodoLetivo"><h4>Contabilizar horas do período:&nbsp;</h4></label></td>
                    <td>
                        <select id="campo-periodoLetivo" name="campo-periodoLetivo">
                            <c:forEach var="periodoLetivo" items="${periodoLetivoList}">
                                <option value="${periodoLetivo.id}" <c:if test = "${periodoLetivo.id == idPeriodoLetivo}"> selected="true" </c:if>>${periodoLetivo.codigo}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table><br/>
        </div>
        <div class="ui-widget">
            <c:if test="${sessionData.usuario.perfil != 1}">
                <table>
                    <tr>
                        <td>
                            <h4>Filtrar por curso:&nbsp;</h4>
                        </td>
                        <td>    
                            <select id="campo-curso" name="campo-curso">
                                <option value="0">Todos</option>
                                <c:forEach var="curso" items="${cursoList}">
                                    <option value="${curso.id}" <c:if test = "${curso.id == idCurso}"> selected="true"</c:if>>${curso}</option>
                                </c:forEach>                            
                            </select>
                        </td>

                    </tr>
                </table><br/>
            </c:if>
        </div>
    </div>
    <div id="demo">
        <form id="formHistorico">
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                <thead>
                    <tr>     
                        <th></th>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                        <th>Ensino</th>
                        <th>Pesquisa</th>
                        <th>Extensão</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${alunoList}" var="aluno">
                        <tr> 
                            <td><input type="radio" name="RadioGroup1" value="${aluno.id}"/></td>
                            <td class="center">${aluno.matricula}</td>
                            <td class="center">${aluno}</td>
                            <c:if test="${sessionData.usuario.perfil != 1}"><td class="center">${aluno.curso}</td></c:if>
                            <td class="center">${aluno.ensino}</td>
                            <td class="center">${aluno.pesquisa}</td>
                            <td class="center">${aluno.extensao}</td>
                        </tr>
                    </c:forEach>                    
                </tbody>
                <tfoot>
                    <tr>      
                        <th></th>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                        <th>Ensino</th>
                        <th>Pesquisa</th>
                        <th>Extensão</th>
                    </tr>
                </tfoot>
            </table>
        </form>           
    </div>                             
</body>