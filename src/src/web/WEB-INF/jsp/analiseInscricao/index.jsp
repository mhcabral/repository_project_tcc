<%-- 
    Document   : index
    Created on : 07/02/2013, 21:32:33
    Author     : Janderson
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <title>Análise das Inscrições em Monitoria</title>            

    <style type="text/css" title="currentStyle">
        @import "${pageContext.request.contextPath}/css/demo_page.css";
        @import "${pageContext.request.contextPath}/css/demo_table.css";
    </style>          
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">    
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>      
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
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
            $('#example2').dataTable({
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
                    
            var table = document.getElementById('formInscricao');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/analiseInscricao/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/analiseInscricao/"+Radio[i].value + "/show";                          
                                    return;
                                }                    
                            
                            
                            
                            }                
                        }
                                                                    
                        alert('Você precisa selecionar uma das inscrições');
                    }
                }
          

                function adjustVagas() {
                    var cursoValue = $('#campo-curso').val();
                    var disciplinaValue = $('#campo-disciplina').val();                           
            
                    window.location = "${pageContext.request.contextPath}/analiseInscricao/"+cursoValue+"/"+disciplinaValue+"/search";
                        
                }
    </script>
    <script language="JavaScript" type="text/javascript">
                function radioHab2(variavel) {
                    
                    var table = document.getElementById('formInscricao');

                    Radio2= table.RadioGroup2;
                    if(Radio2.checked){
                        switch (variavel){
                            case 'show':
                                document.location.href="${pageContext.request.contextPath}/analiseInscricao/"+Radio2.value + "/show";                          
                                return;
                            }
                        } else {
                            for(var i=0;i<Radio2.length;i++) 
                            {
                                if(Radio2[i].checked) 
                                {                    
                                    switch (variavel){
                                        case 'show':
                                            document.location.href="${pageContext.request.contextPath}/analiseInscricao/"+Radio2[i].value + "/show";                          
                                            return;
                                        }                    
                                    }                
                                }
                                alert('Você precisa selecionar uma das inscrições');
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
                            $( "#campo-curso" ).combobox();
                        });
            
                        $(function() {
                            $( "#campo-disciplina" ).combobox();
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
                <div class="pagetitle icon-48-notice"><h2>Análise de Inscrições</h2></div>
            </div>
        </div>
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        Inscrição ${success} com sucesso!
                    </p>
                </div>
            </c:if>
        </div>
        <br/>
        <div id="demo"> 

            <p>
            <h3>Filtrar por:</h3> <br/>     
            <div class="ui-widget">
                <c:if test="${sessionData.usuario.perfil != 1}">
                    <label for="campo-curso">Curso:</label>
                    <select id="campo-curso" name="campo-curso">
                        <option value="0"></option>
                        <c:forEach var="curso" items="${cursoList}">
                            <option value="${curso.id}" <c:if test = "${curso.id == idCurso}"> selected</c:if>>${curso}</option>
                        </c:forEach>
                    </select>
                </c:if>

                <label style="padding-left:80px" for="campo-disciplina">Disciplina:</label>
                <select id="campo-disciplina" name="campo-disciplina">
                    <option value="0"></option>
                    <c:forEach var="disciplina" items="${disciplinaList}">
                        <option value="${disciplina.id}" <c:if test = "${disciplina.id == idDisciplina}"> selected</c:if>>${disciplina}</option>
                    </c:forEach>
                </select>
            </div>

            <div id="adicional" style="padding-top: 10px" <c:choose> <c:when test="${monitoria.vagas>0}"></c:when><c:otherwise>hidden="true"</c:otherwise></c:choose> >
                        <label for="campo-professor">Professor:</label>
                            <input type="text" id="campo-professor" name="campo-professor" value="${monitoria.professor}"  disabled="true"/>                
                <label for="campo-vagas">Vagas:</label>
                <input type="text" id="campo-vagas" name="campo-vagas" value="${monitoria.vagas}" style="width: 35px" disabled="true"/>                
            </div>
            </p>
            <br/>

            <form id="formInscricao">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>   
                            <th></th>
                            <th>Matrícula</th>
                            <th>Aluno</th>
                            <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                                <th>Disciplina</th>
                                <th>Detalhar</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${inscricaoMonitoriaList}" var="inscricao">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${inscricao.id}"/></td>
                                <td class="center">${inscricao.inscrito.matricula}</td>
                                <td class="center">${inscricao.inscrito.usuario.nome}</td>
                                <c:if test="${sessionData.usuario.perfil != 1}"><td class="center">${inscricao.monitoria.curso}</td></c:if>
                                <td class="center">${inscricao.monitoria.disciplina}</td>
                                <td class="center"><a href="${pageContext.request.contextPath}/analiseInscricao/${inscricao.id}/show"><img src="${pageContext.request.contextPath}/img/show.png"></img></a></td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>   
                            <th></th>
                            <th>Matrícula</th>
                            <th>Aluno</th>
                            <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                            <th>Disciplina</th>
                            <th>Detalhar</th>
                        </tr>                        

                    </tfoot>
                </table>
                <br/><br/>

            </form> 
        </div>                             
    </div>
</body>