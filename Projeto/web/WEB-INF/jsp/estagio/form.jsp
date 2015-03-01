<%-- 
    Document   : form
    Created on : 12/04/2013, 01:01:30
    Author     : Bruna
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload_1.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>


        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">           
            $(function () { 
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formEstagio").submit(); 
                });
                
                $('#save').click(function(e) {
                    e.preventDefault();
                    $("#formSolicitacao").submit(); 
                }); 
                $("#formEstagio").validate({
                    rules:{
                        "estagio.empresa":{
                            required: true,
                            maxlength: 255
                        },
                        "estagio.nomeSupervisor":{
                            required: true,
                            maxlength: 255
                        },
                        "estagio.emailSupervisor":{
                            required: true,
                            maxlength: 255
                        },
                        "termoCompromisso":{
                            required: true
                        },
                        "seguro":{
                            required: true
                        },
                        "fichaCadastro":{
                            required: true
                        }
                    },
                    messages:{
                        "estagio.empresa":{
                            required: "Informe o nome da empresa",
                            maxlength: "O nome da empresa deve conter no máximo 255 caracteres"
                        },
                        "estagio.nomeSupervisor":{
                            required: "Informe o nome do supervisor",
                            maxlength: "O nome do supervisor deve conter no máximo 255 caracteres"
                        },
                        "estagio.emailSupervisor":{
                            required: "Informe o email do supervisor",
                            maxlength: "O email do supervisor deve conter no máximo 255 caracteres"
                        },
                        "termoCompromisso":{
                            required: "Adicione o termo de compromisso"
                        },
                        "seguro":{
                            required: "Adicione o seguro"
                        },
                        "fichaCadastro":{
                            required: "Adicione a ficha de cadastro"
                        }
                    }
                });
                
                $( "#radio" ).buttonset();

                $("#ficha-cadastro").hide();
                
            
                if(${not empty estagio.id}){
                    $("#upload-fichaCadastro").hide();
                    $("#upload-termoCompromisso").hide();
                    $("#upload-seguro").hide();
                    
                    if(${estagio.convenio == false}){
                        show();
                        $("#upload-fichaCadastro").hide();
                        $("#download-fichaCadastro").show();
                    }  else {
                        hide();
                    }
                } 
                
            });
            
            function hide_download_fichaCadastro(){
                $("#download-fichaCadastro").hide();
                $("#upload-fichaCadastro").show();
            }
            
            function hide_download_termoCompromisso(){
                $("#download-termoCompromisso").hide();
                $("#upload-termoCompromisso").show();
            }
            
            function hide_download_seguro(){
                $("#download-seguro").hide();
                $("#upload-seguro").show();
            }
        </script>
        <script>
            function show(){
                $("#ficha-cadastro").show();
            }
        
            function hide(){
                $("#ficha-cadastro").hide();
            }
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
                        .attr( "title", "Todas os Professores" )
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
                $( "#campo-professor" ).combobox();
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
                                        <a href="${pageContext.request.contextPath}/estagios/index">
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
                    <div class="pagetitle icon-48-article-add"><h2>Solicitação de Estágio</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} da solicitação de Estágio</h2></div>
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

        <form id="formEstagio" name="formEstagio" method="POST" action="<c:url value="/estagios"/>" enctype="multipart/form-data"> 
            <c:if test="${not empty estagio.id}">
                <input type="hidden" name="estagio.id" value="${estagio.id}"/>
                <input type="hidden" name="_method" value="put"/>
            </c:if>

            <legend><h4>Professor Orientador</h4></legend>
            <p>
                <select id="campo-professor" name="estagio.orientador.id" value="${estagio.orientador}" >
                    <option value="">Selecione um Professor</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option value="${professor.id}" <c:if test = "${professor.id == estagio.orientador.id}"> selected</c:if>>${professor}</option>
                    </c:forEach>
                </select>
            </p>

            <br/>
            <legend><h4>Dados da Empresa</h4></legend>
            <p>
                <label for="campo-empresa">Nome da empresa*:</label><br/>
                <input size="100" type="text" id="campo-empresa" name="estagio.empresa" value="${estagio.empresa}"/><br/>
            </p>
            <p>
                <label for="campo-supervisor">Nome do supervisor*:</label><br/>
                <input size="100" type="text" id="campo-supervisor" name="estagio.nomeSupervisor" value="${estagio.nomeSupervisor}"/><br/>
            </p>
            <p>
                <label for="campo-email-supervisor">Email do supervisor*:</label><br/>
                <input size="50" type="text" id="campo-email-supervisor" name="estagio.emailSupervisor" value="${estagio.emailSupervisor}"/><br/>
            </p>
            <div id="radio">
                <p>
                    Empresa possui convênio com a UFAM?*:
                    <c:if test="${empty estagio.id}">
                        <input value="TRUE" onclick="javascript:hide()" type="radio" id="radio1" name="estagio.convenio" checked="checked"/><label for="radio1">SIM</label>
                        <input value="FALSE" onclick="javascript:show()" type="radio" id="radio2" name="estagio.convenio"/><label for="radio2">NÃO</label>
                    </c:if>
                    <c:if test="${not empty estagio.id}">
                        <input value="TRUE" onclick="javascript:hide()" type="radio" id="radio1" name="estagio.convenio" <c:if test="${estagio.convenio == true}">checked="checked"</c:if>/><label for="radio1">SIM</label>
                        <input value="FALSE" onclick="javascript:show()" type="radio" id="radio2" name="estagio.convenio" <c:if test="${estagio.convenio == false}">checked="checked"</c:if>/><label for="radio2">NÃO</label>
                    </c:if>
                </p>
            </div>
            <br/>   
            <legend><h4>Documentação</h4></legend>
            <p>
                <label for="campo-termo">Termo de Compromisso*:</label>
            <div id="download-termoCompromisso">
                <c:if test="${not empty estagio.termoCompromisso}">
                    <a href="${pageContext.request.contextPath}/estagios/download/${estagio.termoCompromisso}" target="_blank" >${estagio.termoCompromisso}</a>
                    <a href="javascript:hide_download_termoCompromisso()" target="_blank" >Remover</a>
                    <br/>
                </c:if>
            </div>
            <div id="upload-termoCompromisso">
                <input type="file" id="campo-termo" name="termoCompromisso" accept="application/pdf"/><br/>
            </div>
            <p>
                <label for="campo-seguro">Seguro*:</label>
            <div id="download-seguro">
                <c:if test="${not empty estagio.seguro}">
                    <a href="${pageContext.request.contextPath}/estagios/download/${estagio.seguro}" target="_blank" >${estagio.seguro}</a>
                    <a href="javascript:hide_download_seguro()" target="_blank" >Remover</a>
                    <br/>
                </c:if>
            </div>
            <div id="upload-seguro">
                <input type="file" id="campo-seguro" name="seguro" accept="application/pdf"/><br/>
            </div>
            <div id="ficha-cadastro">
                <p>
                    <label for="campo-ficha-cadastro">Ficha de Cadastro da Empresa*:</label> 
                <div id="download-fichaCadastro">
                    <c:if test="${not empty estagio.fichaCadastroEmpresa}">
                        <a href="${pageContext.request.contextPath}/estagios/download/${estagio.fichaCadastroEmpresa}" target="_blank" >${estagio.fichaCadastroEmpresa}</a>
                        <a href="javascript:hide_download_fichaCadastro()" target="_blank" >Remover</a>
                        <br/>
                    </c:if>
                </div>
                <div id="upload-fichaCadastro">
                    <input type="file" id="campo-ficha-cadastro" name="fichaCadastro"  accept="application/pdf"/><br/>
                    <br/>
                    Obs.: Ficha de Cadastro da Empresa é obrigatório caso a empresa não possua convênio com a UFAM.
                </div>
            </div>
        </form>
    </body>
</html>