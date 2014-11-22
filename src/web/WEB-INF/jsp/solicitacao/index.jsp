<%-- 
    Document   : index
    Created on : 07/02/2013, 21:32:33
    Author     : Bruna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <title>Listando Solicitações</title>            

    <style type="text/css" title="currentStyle">
        @import "${pageContext.request.contextPath}/css/demo_page.css";
        @import "${pageContext.request.contextPath}/css/demo_table.css";
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

        .desativa {
            pointer-events: none;
            cursor: default;
            box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            background: #f2f5f7;
        }
    </style>


    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>       

    <!--Não tinha-->
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
            $('#example2').dataTable({
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
    <script>
        function enviarTodas(lista){
            if(lista){
                decisao = confirm("Deseja realmente enviar todas as solicitações?");
                if (decisao){
                    document.location.href="${pageContext.request.contextPath}/solicitacoes/enviarTodas";
                } else {
                    alert ("Nenhuma solicitação foi enviada");
                }
            } else {
                alert ("Nenhuma solicitação aguardando envio");
            }
        };
    </script>
    <script language="JavaScript" type="text/javascript">
        function radioHab(variavel) {
                    
            var table = document.getElementById('formSolicitacao');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'edit':
                        document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio.value + "/edit";                          
                        return;
                    case 'remove':    
                        decisao = confirm("Deseja realmente remover a solicitação?");
                        if (decisao){
                            document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio.value + "/remove";                                                              
                        } else {
                            alert ("Nenhuma Solicitação foi removida");
                        }                            
                        return;
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'edit':
                                    document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio[i].value + "/edit";                          
                                    return;
                                case 'remove':    
                                    decisao = confirm("Deseja realmente remover a solicitação?");
                                    if (decisao){
                                        document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio[i].value + "/remove";                                                              
                                    } else {
                                        alert ("Nenhuma Solicitação foi removida");
                                    }                            
                                    return;
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio[i].value + "/show";                          
                                    return;
                                }                    
                            }                
                        }
                    
                        alert('Você precisa selecionar uma das solicitações');
                    }
                }
                
                function adjustVagas() {
                    var atividadeValue = $('#campo-atividade').val();
                    var peridoLetivoValue = $('#campo-periodoLetivo').val();
            
                    window.location = "${pageContext.request.contextPath}/solicitacoes/"+atividadeValue+"/"+peridoLetivoValue+"/search";                        
                }
    </script>
    <script language="JavaScript" type="text/javascript">
                function radioHab2(variavel) {
                    
                    var table = document.getElementById('formSolicitacao');            
                    var Radio=null;

                    Radio2= table.RadioGroup2;
                    if(Radio2.checked){
                        switch (variavel){
                            case 'show':
                                document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio2.value + "/show";                          
                                return;
                            }
                        } else {
                            for(var i=0;i<Radio2.length;i++) 
                            {
                                if(Radio2[i].checked) 
                                {                    
                                    switch (variavel){
                                        case 'show':
                                            document.location.href="${pageContext.request.contextPath}/solicitacoes/"+Radio2[i].value + "/show";                          
                                            return;
                                        }                    
                                    }                
                                }
                                alert('Você precisa selecionar uma das solicitações');
                            }
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
                            $( "#campo-atividade" ).combobox();
                        });                                    
                        
                        $(function() {
                            $( "#campo-periodoLetivo" ).combobox();
                        });                                                                               
    </script>
</head>
<body id="dt_example">
    <div id="demo">
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/solicitacoes/historico">
                                            <span width="32" height="32" border="0" class="icon-32-stats"></span>Histórico
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
                <div class="pagetitle icon-48-newcategory">
                    <h2>Atividades Complementares</h2>
                    <h5>Total de horas complementares computadas: ${totalComputado}h</h5>
                </div>
            </div>
        </div>
        <div id="container">
            <br/>
            <form id="formSolicitacao">
                <div id="toolbar-box">
                    <div class="m">
                        <div class="toolbar-list" id="toolbar">
                            <div class="cpanel2">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <ul>
                                            <li class="button" id="toolbar-apply" <c:if test="${sessionData.periodoMatricula == null}">title="Opção indisponível! Período de aproveitamento de atividades complementares fechado"</c:if>>
                                                <a href="${pageContext.request.contextPath}/solicitacoes/create" id="new" <c:if test="${sessionData.periodoMatricula == null}">class="desativa"</c:if>>
                                                    <span width="32" height="32" border="0" class="icon-32-new"></span>Novo
                                                </a>
                                            </li>
                                            <li class="button" id="toolbar-apply" <c:if test="${sessionData.periodoMatricula == null}">title="Opção indisponível! Período de aproveitamento de atividades complementares fechado"</c:if>>
                                                <a href="javascript:radioHab('show')" <c:if test="${sessionData.periodoMatricula == null}">class="desativa"</c:if>>
                                                    <span width="32" height="32" border="0" class="icon-32-preview"></span>Visualizar
                                                </a>
                                            </li>
                                            <li class="button" id="toolbar-apply" <c:if test="${sessionData.periodoMatricula == null}">title="Opção indisponível! Período de aproveitamento de atividades complementares fechado"</c:if>>
                                                <a href="javascript:radioHab('edit')" <c:if test="${sessionData.periodoMatricula == null}">class="desativa"</c:if>>
                                                    <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                                </a>
                                            </li>
                                            <li <c:if test="${sessionData.periodoMatricula == null}">title="Opção indisponível! Período de aproveitamento de atividades complementares fechado"</c:if>>
                                                <a href="javascript:radioHab('remove')" <c:if test="${sessionData.periodoMatricula == null}">class="desativa"</c:if>>
                                                    <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                                </a>
                                            </li>
                                            <li <c:if test="${sessionData.periodoMatricula == null}">title="Opção indisponível! Período de aproveitamento de atividades complementares fechado"</c:if>>
                                                <a href="javascript:enviarTodas(${not empty solicitacaoEditavelList})" <c:if test="${sessionData.periodoMatricula == null}">class="desativa"</c:if>>
                                                    <span width="32" height="32" border="0" class="icon-32-send"></span>Enviar<br/>Todas
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="clr"></div>
                        </div>
                        <div class="pagetitle icon-48-notice"><h2>Solicitações em edição</h2></div>
                    </div>
                </div>
                <div>
                    <c:if test="${not empty success}">
                        <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                            <p>
                                <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                                Solicitação ${success} com sucesso!
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${not empty enviada}">
                        <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                            <p>
                                <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                                Solicitações enviadas com sucesso!
                            </p>
                        </div>
                    </c:if>
                </div>
                <br/>
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Status</th>
                            <th>Atividade</th>
                            <th>Descrição</th>
                            <th>Quantidade de Horas</th>
                            <th>Data</th>
                            <th>Visualizar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${solicitacaoEditavelList}" var="solicitacao">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${solicitacao.id}"/></td>
                                <td class="center">${solicitacao.statusAtual.nome}</td>
                                <td class="center">${solicitacao.atividade}</td>
                                <td class="center">${solicitacao.descricao}</td>
                                <td class="center">${solicitacao.qntd_horas}</td>
                                <td class="center">${solicitacao.dataAlteracao}</td>                                
                                <td class="center"><a href="${pageContext.request.contextPath}/solicitacoes/${solicitacao.id}/show"><img src="${pageContext.request.contextPath}/img/show.png"/></a></td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>Status</th>
                            <th>Atividade</th>
                            <th>Descrição</th>
                            <th>Quantidade de Horas</th>
                            <th>Data</th>
                            <th>Visualizar</th>
                        </tr>
                    </tfoot>
                </table>
                <br><br><br>
                <div id="toolbar-box">
                    <div class="m">
                        <div class="toolbar-list" id="toolbar">
                            <div class="cpanel2">
                                <div class="icon-wrapper">
                                    <div class="icon">
                                        <ul>
                                            <li class="button" id="toolbar-apply">
                                                <a href="javascript:radioHab2('show')">
                                                    <span width="32" height="32" border="0" class="icon-32-preview"></span>Visualizar
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="clr"></div>
                        </div>
                        <div class="pagetitle icon-messaging"><h2>Solicitações enviadas</h2></div>
                    </div>
                </div>
                <br/>

                <p>
                <h4>Filtrar por:</h4><br/>
                <div class="ui-widget">
                    <label style="padding-left:80px" for="campo-atividade">Atividade:</label>
                    <select id="campo-atividade" name="campo-atividade">
                        <option value="0">Todas</option>
                        <c:forEach var="atividade" items="${atividadeList}">
                            <option value="${atividade.id}" <c:if test = "${atividade.id == idAtividade}"> selected</c:if>>${atividade}</option>
                        </c:forEach>
                    </select>

                    <label style="padding-left:80px" for="campo-periodoLetivo">Período:</label>
                    <select id="campo-periodoLetivo" name="campo-periodoLetivo">
                        <option value="0">Todos</option>
                        <c:forEach var="periodoLetivo" items="${periodoLetivoList}">
                            <option value="${periodoLetivo.id}" <c:if test = "${periodoLetivo.id == idPeriodoLetivo}"> selected="true" </c:if>>${periodoLetivo.codigo}</option>
                        </c:forEach>
                    </select>
                </div>
                </p>
                <br/>

                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example2" width="100%">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Status</th>
                            <th>Atividade</th>
                            <th>Descrição</th>
                            <th>Quantidade de Horas Computadas</th>
                            <th>Data</th>
                            <th>Visualizar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${solicitacaoNaoEditavelList}" var="solicitacao">
                            <tr>
                                <td><input type="radio" name="RadioGroup2" value="${solicitacao.id}"/></td>
                                <td class="center">${solicitacao.statusAtual.nome}</td>
                                <td class="center">${solicitacao.atividade}</td>
                                <td class="center">${solicitacao.descricao}</td>
                                <td class="center">${solicitacao.horasComputadas}</td>
                                <td class="center">${solicitacao.dataAlteracao}</td>
                                <td class="center"><a href="${pageContext.request.contextPath}/solicitacoes/${solicitacao.id}/show"><img src="${pageContext.request.contextPath}/img/show.png"/></a></td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>Status</th>
                            <th>Atividade</th>
                            <th>Descrição</th>
                            <th>Quantidade de Horas Computadas</th>
                            <th>Data</th>
                            <th>Visualizar</th>
                        </tr>
                    </tfoot>
                </table>
            </form>           
        </div>                             
    </div>
</body>