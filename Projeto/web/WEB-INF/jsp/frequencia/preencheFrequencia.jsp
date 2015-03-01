<%-- 
    Document   : showFrequencia
    Created on : 24/03/2013, 19:18:43
    Author     : Bruna
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Preenchimento Frequencia</title>

        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/jquery-ui.js'></script>
        <script type='text/javascript' src='${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js'></script>
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css' />
        <link rel='stylesheet' type='text/css' href='${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css' media='print' />   

        <script>
            $(function() {
                $('#salvar').click(function(e) {
                    e.preventDefault();
                    $("#formFrequencia").attr('target', '_blank').attr('action', '${pageContext.request.contextPath}/monitorias/historico').submit();
                });
                $('#enviar').click(function(e) {
                    e.preventDefault();
                    decisao = confirm("Deseja realmente enviar a frequência?");
                    if (decisao){
                        $(window.location).attr('href', '${pageContext.request.contextPath}/frequencia/enviar/${inscricao.id}');
                    }
                });
            });
        </script>

        <style>
            body { font-size: 62.5%; }
            input.text { margin-bottom:12px; width:95%; padding: .4em; }
            .ui-dialog { padding: .3em; }
            .validateTips { border: 1px solid transparent; padding: 0.3em; }
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }

            #calendarBolsista {
                width: 400px;
                margin: 0 auto;
            }
            #calendarNaoBolsista {
                width: 400px;
                margin: 0 auto;
            }
        </style>

        <script>
            $(function() {
                var carga = $( "#carga" ),
                allFields = $( [] ).add( carga ),
                tips = $( ".validateTips" );
 
                function updateTips( t ) {
                    tips
                    .text( t )
                    .addClass( "ui-state-error" );
                    /*setTimeout(function() {
                        tips.removeClass( "ui-state-error", 1500 );
                    }, 500 );*/
                }
 
                function vazio( o, n ) {
                    if ( o.val() == "") {
                        updateTips( n );
                        return false;
                    } else {
                        return true;
                    }
                }
                
                function limite( o, n ) {
                    if ( o.val() >= 24) {
                        updateTips( n );
                        return false;
                    } else {
                        return true;
                    }
                }
                
                function setTitle(o){
                    document.getElementById("title").value = o.val();
                    return;
                }
                
                $( "#dialog-form" ).dialog({
                    autoOpen: false,
                    height: 200,
                    width: 200,
                    modal: true,
                    buttons: {
                        "Salvar": function() {
                            var bValid = true;
                            
                            bValid = bValid && vazio( carga, "Carga horária deve ser informada." );
                            
                            bValid = bValid && limite( carga, "Carga horária deve menor que 24 horas." );
                            
                            setTitle(carga);
 
                            if ( bValid ) {
                                $( this ).dialog( "close" );
                                $.ajax({
                                    url: '<c:url value="/frequencia/inclui.json"/>',
                                    type: 'post',
                                    data: 'id='+${inscricao.id}+'&start='+$("#start").val()+'&title='+$("#title").val(),
                                    error : function(txt) { 
                                        alert('Não foi possível preencher a frequencia do dia');
                                    },
                                    success: function(data, textStatus, jqXHR){
                                        $('#calendarBolsista').fullCalendar('refetchEvents');
                                        $('#calendarBolsista').fullCalendar( 'render' );
                                        $('#calendarNaoBolsista').fullCalendar('refetchEvents');
                                        $('#calendarNaoBolsista').fullCalendar( 'render' );
                                    }
                                });
                            };
                        },
                        "Cancelar": function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        allFields.val( "" ).removeClass( "ui-state-error" );
                    }
                });
            });
        </script>

        <script type='text/javascript'>
            $(document).ready(function() {
                var inicioPeriodo = new Date($("#inicio").val()); 
                var fimPeriodo = new Date($("#fim").val());
                        
                var calendar = $('#calendarBolsista').fullCalendar({
                    theme: true,		
                    selectable: true,
                    weekends: false,
                    header: {
                        left: 'today',
                        center: 'title',
                        right: ''
                    },                    
                    select: function(start, end, allDay) {
                        if(start < inicioPeriodo) {
                            alert("Não é possível frequencia para este dia.\nDia ANTERIOR ao início do Período Letivo.");
                        }else if(start > fimPeriodo){
                            alert("Não é possível frequencia para este dia.\nDia POSTERIOR ao fim do Período Letivo.");
                        } else {
                            $("#start").val($.fullCalendar.formatDate(start, 'dd/MM/yyyy'));

                            $( "#dialog-form" ).dialog( "open" );
                        }
                        
                        calendar.fullCalendar('unselect');
                    },
                    events: "${pageContext.request.contextPath}/frequencia/list.json/${inscricao.id}",
                    eventClick: function(calEvent, jsEvent, view) {
                        decisao = confirm("Deseja remover a frequência desse dia?");
                        if (decisao) {
                            $.ajax({
                                url: '<c:url value="/frequencia/exclui.json"/>',
                                type: 'post',				    	  			   
                                data: 'idFrequencia='+calEvent.id+'&id='+${inscricao.id},
                                error : function(txt) { 
                                    alert('Não foi possível remover a frequencia do dia'); 
                                },
                                success: function(data, textStatus, jqXHR){
                                    $('#calendarBolsista').fullCalendar('refetchEvents');
                                    $('#calendarBolsista').fullCalendar( 'render' );
                                }
                            });
                            $('#calendarBolsista').fullCalendar('refetchEvents');
                            $('#calendarBolsista').fullCalendar( 'render' );
                        }
                    }
                });

                var calendar2 = $('#calendarNaoBolsista').fullCalendar({
                    theme: true,		
                    selectable: true,
                    weekends: false,
                    header: {
                        left: 'prev, today',
                        center: 'title',
                        right: 'next'
                    },
                    select: function(start, end, allDay) {                        
                        if(start < inicioPeriodo) {
                            alert("Não é possível frequencia para este dia.\nDia ANTERIOR ao início do Período Letivo.");
                        }else if(start > fimPeriodo){
                            alert("Não é possível frequencia para este dia.\nDia POSTERIOR ao fim do Período Letivo.");
                        } else {
                            $("#start").val($.fullCalendar.formatDate(start, 'dd/MM/yyyy'));

                            $( "#dialog-form" ).dialog( "open" );
                        }
                        
                        calendar2.fullCalendar('unselect');
                    },
                    events: "${pageContext.request.contextPath}/frequencia/list.json/${inscricao.id}",
                    eventClick: function(calEvent, jsEvent, view) {
                        decisao = confirm("Deseja remover a frequência desse dia?");
                        if (decisao) {
                            $.ajax({
                                url: '<c:url value="/frequencia/exclui.json"/>',
                                type: 'post',				    	  			   
                                data: 'idFrequencia='+calEvent.id+'&id='+${inscricao.id},
                                error : function(txt) { 
                                    alert('Não foi possível remover a frequencia do dia'); 
                                },
                                success: function(data, textStatus, jqXHR){
                                    $('#calendarNaoBolsista').fullCalendar('refetchEvents');
                                    $('#calendarNaoBolsista').fullCalendar( 'render' );
                                }
                            });
                            $('#calendarNaoBolsista').fullCalendar('refetchEvents');
                            $('#calendarNaoBolsista').fullCalendar( 'render' );
                        }
                    },
                    viewDisplay   : function(view) {
                        var a = new Date();
                        var start = new Date($("#inicio").val()); 
                        var end = new Date($("#fim").val());

                        var cal_date_string = view.start.getMonth()+'/'+view.start.getFullYear();
                        var cur_date_string = start.getMonth()+'/'+start.getFullYear();
                        var end_date_string = end.getMonth()+'/'+end.getFullYear();

                        if(cal_date_string == cur_date_string) {
                            jQuery('.fc-button-prev').addClass("ui-state-disabled");
                        }
                        else {
                            jQuery('.fc-button-prev').removeClass("ui-state-disabled");
                        }

                        if(end_date_string == cal_date_string) {
                            jQuery('.fc-button-next').addClass("ui-state-disabled");
                        }
                        else {
                            jQuery('.fc-button-next').removeClass("ui-state-disabled");
                        }
                    }
                });

                //$('#calendarNaoBolsista').fullCalendar( 'render' );
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
        <style type="text/css">
            .desativa {
                pointer-events: none;
                cursor: default;
                box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                background: #f2f5f7;
            }
            a{
                background: #fff;
            }
        </style>
    </head>
    <body>
        <input type="hidden" id="inicio" name="inicio" value="${inicioPeriodo}"/>
        <input type="hidden" id="fim" name="fim" value="${fimPeriodo}"/>
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li <c:if test="${not empty bloqueioFreq}"> title="Opção indisponível! Fora do período de envio da frequência" </c:if>>
                                        <a href="javascript:void(0);" id="enviar" <c:if test="${not empty bloqueioFreq}"> class="desativa"</c:if>>
                                                <span width="32" height="32" border="0" class="icon-32-send"></span>Enviar
                                            </a>
                                        </li>
                                        <li class="button" id="toolbar-cancel">
                                            <a href="${pageContext.request.contextPath}/inscricoes/frequencia" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-calendar"><h2>Frequência da Monitoria</h2></div>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                    <!--<ul>-->
                    <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>Não foi possível enviar a frequência</p>
                    <c:forEach items="${errors}" var="error">
                        <!--<li style="color: #cd0a0a">-->
                        <p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>${error.message}</p>
                        <!--</li>-->
                    </c:forEach>        
                    <!--</ul>-->
                </div>
            </div>   
        </c:if>
        <p>
            <b>Disciplina:</b>
            ${inscricao.monitoria.disciplina}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricao.monitoria.professor}
        </p>
        <p>
            <b>Turma:</b>
            ${inscricao.monitoria.turma}
        </p>
        <p>
            <b>Curso:</b>
            ${inscricao.monitoria.curso}
        </p>

        <c:if test="${inscricao.bolsista == true}">
            <div id='calendarBolsista'></div>
        </c:if>
        <c:if test="${inscricao.bolsista == false}">
            <div id='calendarNaoBolsista'></div>
        </c:if>
        <div id="dialog-form" title="Frequência do Dia">
            <form>
                <input type="hidden" id="start" name="start">
                <input type="hidden" id="title" name="title">
                <p class="validateTips"></p>
                <p>
                    <label for="carga">Carga Horária:</label>
                    <input style="text-align:center" onKeyUp="javascript:somente_numero(this);" required="true" max="24" maxlength="2" size="3" type="text" name="carga" id="carga" class="number ui-widget-content ui-corner-all" />
                </p>
            </form>
        </div>
    </body>
</html>