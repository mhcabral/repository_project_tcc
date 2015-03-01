<%-- 
    Document   : form
    Created on : 17/03/2013, 22:13:28
    Author     : Thiago Santos
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>
        
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formAluno").submit(); 
                }); 
                
                $("#formAluno").validate({
                    rules:{                        
                        "aluno.matricula":{
                            required: true                      
                        },
                        "aluno.curso.id":{
                            required: true
                        },
                        "aluno.dataIngresso":{
                            required: true
                        },
                        "aluno.usuario.cpf":{
                            required:true                           
                        },
                        "aluno.usuario.nome":{
                            required: true,
                            maxlength: 255
                        },
                        "aluno.usuario.email":{
                            required: true,
                            email: true,
                            maxlength: 255
                        },
                        "periodo":{
                            required: true
                        }                       
                    },
                    messages:{
                        "aluno.matricula":{
                            required: "Informe a matrícula do aluno"                            
                        },                        
                        "aluno.curso.id":{
                            required: "Selecione um Curso"
                        },
                        "aluno.dataIngresso":{
                            required: "Informe a data de ingrersso do aluno"
                        },
                        "aluno.usuario.cpf":{
                            required: "Informe o CPF do aluno"                            
                        },
                        "aluno.usuario.nome":{
                            required: "Informe o nome do aluno",
                            maxlength: "O nome deve conter no máximo 255 caracteres"
                        },
                        "periodo":{
                            required: "Selecione o período de ingresso"
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
                        .attr( "title", "Todos os Cursos" )
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
                $( "#campo-periodo" ).combobox();
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
            
            $(document).ready(function(){
                $("#campo-cpf").mask("999.999.999-99");
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
                                        <a href="${pageContext.request.contextPath}/alunos">
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
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de Aluno</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Aluno</h2></div>
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

        <form id="formAluno" name="formAluno" method="POST" action="<c:url value="/alunos"/>"> 
            <p>
                <c:if test="${not empty aluno.id}">
                    <input type="hidden" name="aluno.id" value="${aluno.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </p>
            <p>
                <input hidden="true" type="text" name="aluno.usuario.id" value="${aluno.usuario.id}"/>
            </p>
            <p>
                <label for="campo-matricula">Matrícula*:</label><br/>
                <input onKeyUp="javascript:somente_numero(this);" size="8" maxlength="8" type="text" id="campo-matricula" name="aluno.matricula" value="${aluno.matricula}"/><br/>
            </p>
            <p>
                <font size="2">
                <label for="campo-curso">Curso*:</label><br/>
                <select id="campo-curso" name="aluno.curso.id" value="${aluno.curso}">
                    <option value="">Selecione um Curso</option>
                    <c:forEach var="curso" items="${cursoList}">
                        <option value="${curso.id}" <c:if test = "${curso.id == aluno.curso.id}"> selected</c:if>>${curso}</option>
                    </c:forEach>
                </select>
                </font><br/>
            </p>
            <p>
                <font size="2">
                <label for="campo-periodo">Período de Ingresso*:</label><br/>
                <select id="campo-periodo" name="periodo" value="${periodoLetivo}">
                    <option value="">Selecione um Período</option>
                    <c:forEach var="periodo" items="${periodoList}">
                        <option value="${periodo.id}" <c:if test = "${periodo.id == periodoLetivo.id}"> selected</c:if>>${periodo}</option>
                    </c:forEach>
                </select>
                </font><br/>
            </p>
            <p>
                <label for="campo-nome">Nome*:</label><br/>
                <input size="50" type="text" id="campo-nome" name="aluno.usuario.nome" value="${aluno.usuario.nome}"/><br/>
            </p>
            <p>
                <label for="campo-cpf">CPF*:</label><br/>
                <input size="13" type="text" id="campo-cpf" name="aluno.usuario.cpf" value="${aluno.usuario.cpf}"/><br/>
            </p>
            <p>
                <label for="campo-email">E-mail*:</label><br/>
                <input size="50" type="text" id="campo-email" name="aluno.usuario.email" value="${aluno.usuario.email}"/><br/>
            </p>
        </form>
    </body>
</html>