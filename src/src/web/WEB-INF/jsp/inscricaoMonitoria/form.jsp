<%-- 
    Document   : form
    Created on : 07/03/2013, 13:13:42
    Author     : Leonardo
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <!--<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui.css" />-->
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.fileupload_1.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/cupertino/theme.css"/>
        <script type='text/javascript' src="${pageContext.request.contextPath}/js/fullcalendar/fullcalendar.js"></script>
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.css" />
        <link rel='stylesheet' type='text/css' href="${pageContext.request.contextPath}/css/fullcalendar/fullcalendar.print.css" media="print" />  
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
            #calendar {
                width: 500px;
                margin: 0 auto;
            }
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
                    $("#formInscricao").submit(); 
                }); 
                $("#formInscricao").validate({
                    rules:{
                        "inscricao.banco":{
                            required: true
                        },
                        "inscricao.agencia":{
                            required: true
                        },
                        "inscricao.conta":{
                            required: true
                        },
                        "comprovante":{
                            required: true
                        },
                        "historico":{
                            required: true
                        }
                    },
                    messages:{
                        "inscricao.banco":{
                            required: "Informe o Banco para deposito"
                        },
                        "inscricao.conta":{
                            required: "Informe conta para deposito"
                        },
                        "inscricao.agencia":{
                            required: "Informe agência para depósito"
                        },
                        "historico":{
                            required: "Adicione o histórico"
                        },
                        "comprovante":{
                            required: "Adicione a documentação comprobatória"
                        }
                    }
                });
                var calendar = $('#calendar').fullCalendar({
                    theme: true,		
                    selectable: false,
                    weekends: false,
                    defaultView: 'agendaWeek',
                    allDaySlot: false,
                    maxTime: 22,
                    minTime: 6,
                    axisFormat: 'H:mm',
                    header: {
                        left: '',
                        center: '',
                        right: ''
                    },                    
                    columnFormat: {
                        week: 'ddd'
                    },
                    events: "${pageContext.request.contextPath}/monitorias/list.json"
                });
                $('#calendar').fullCalendar('gotoDate', ${ano} , ${mes}, ${dia});
                
                if(${not empty inscricao.id}){
                    $("#upload-historico").hide();
                    $("#upload-comprovante").hide();
                }
                
                if(${empty inscricao.id}){
                    $("#download-historico").hide();
                    $("#download-comprovante").hide();
                }
                
                if(${not empty errors}){
                    $("#download-historico").hide();
                    $("#download-comprovante").hide();
                }
            });
            
            function hide_download_historico(){
                $("#download-historico").hide();
                $("#upload-historico").show();
            }
            
            function hide_download_comprovante(){
                $("#download-comprovante").hide();
                $("#upload-comprovante").show();
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

        <script language="JavaScript" type="text/javascript">
            function Test(rad){
                var rads=document.getElementsByName(rad.name);

                document.getElementById('dadosbancarios').style.display=(rads[1].checked)?'block':'none';
              
            }

        </script>
        <script>
            /* Portuguese initialisation for the jQuery UI date picker plugin. */
            jQuery(function ($) {
                $.datepicker.regional['pt-PT'] = {
                    closeText: 'Fechar',
                    prevText: 'Anterior',
                    nextText: 'Seguinte',
                    currentText: 'Hoje',
                    monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                        'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
                    monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                        'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
                    dayNames: ['Domingo', 'Segunda-feira', 'Terça-feira', 'Quarta-feira', 'Quinta-feira', 'Sexta-feira', 'Sábado'],
                    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    dayNamesMin: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'],
                    weekHeader: 'Sem',
                    dateFormat: 'dd/mm/yy',
                    firstDay: 0,
                    isRTL: false,
                    showMonthAfterYear: false,
                    yearSuffix: ''
                };
                $.datepicker.setDefaults($.datepicker.regional['pt-PT']);
            });
        </script>

        <script>
            function getDataAtual(){
                hoje = new Date()
                dia = hoje.getDate()
                mes = hoje.getMonth()
                ano = hoje.getFullYear()
                mes = mes+1
                if (dia < 10)
                    dia = "0" + dia
                if (mes < 10)
                    mes = "0" + mes
                if (ano < 2000)
                    ano = "19" + ano

                //O mes começa em Zero, então soma-se 1
                            
                today = dia+"/"+(mes)+"/"+ano;
                return today;
            }
            var dataSetada = false;
            $(function() {
                var dates = $( "#campo-inicio, #campo-termino" ).datepicker({
                    changeMonth: true,  
                    changeYear: true,
                    option: jQuery.datepicker.regional['pt-PT'],
                    beforeShow: function (input, inst) {
                        if (!dataSetada) {
                            //Pega a instancia do datepicker clicado
                            instance = $(this).data("datepicker");
                            
                            today = getDataAtual();
                            //alert(today);
                            date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, today, instance.settings);
                            dateIngresso = '${dataIngresso}';

                            //Se o campo clicado não for o data Final, será descartado
                            if (this.id != "campo-termino") {
                                $("#campo-inicio").datepicker("option", "maxDate", date);
                                $("#campo-inicio").datepicker("option", "minDate", dateIngresso);
                                return;
                            }

                            //Seta data maxima e minima no datePicker DataTermino
                            $("#campo-termino").datepicker("option", "maxDate", date);
                            // $("#campo-termino").datepicker("option", "minDate", dateIngresso);
                            
                            $("#campo-inicio").datepicker("option", "maxDate", date);

                            dataSetada = true;
                        }
                    },
                    onSelect: function (selectedDate) {
                        habilitaDataTermino();
                        var option = this.id == "campo-inicio" ? "minDate" : "maxDate";
                        instance = $(this).data("datepicker");
                        date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                        dates.not(this).datepicker("option", option, date);
                    }
                });
            });
            function habilitaDataTermino(){
                if(document.getElementById("campo-inicio").value){
                    document.getElementById("campo-termino").disabled = false;
                } else {
                    document.getElementById("campo-termino").disabled = true;
                }
            }
            
            $(document).ready(function(){
                $("#deleteAll").click(function(){
                    $("#lista").hide();
                                
                    $("deleteAll").hide();
                                
                    $("#apagar").attr('checked', true);
                });                                                              
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
                                        <a href="${pageContext.request.contextPath}/InsMonitoria" id="cancel">
                                            <span width="32" height="32" border="0" class="icon-32-cancel"></span>Cancelar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de Inscrição em Monitoria</h2></div>
                </c:if>
                <c:if test="${operacao == 'Inscrição'}">
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} em Monitoria</h2></div>
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
        <c:if test="${operacao=='Edição'}">
            <form id="formInscricao" name="formInscricao" method="POST" action="${pageContext.request.contextPath}/alteracao" enctype="multipart/form-data">
                <c:if test="${not empty inscricao.id}">
                    <input type="hidden" name="inscricao.id" value="${inscricao.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
            </c:if>
            <c:if test="${operacao!='Edição'}">
                <form id="formInscricao" name="formInscricao" method="POST" action="${pageContext.request.contextPath}/inscricoes/${monitoriaId}" enctype="multipart/form-data">
                </c:if>
                <div class="ui-widget">
                    <table >
                        <tr><br/>
                        <br/>
                        <td style="border-style:hidden">
                            <font size="2">
                            <label for="campo-atividade">Deseja candidatar-se a bolsa?:</label><br/>
                            </font>
                        </td>
                        <td><input name="bolsista" type="radio" value="false" <c:if test="${!inscricao.bolsista}"> checked="checked" </c:if> onclick="Test(this);"/>Não</td>
                        <td><input name="bolsista" type="radio" value="true" <c:if test="${inscricao.bolsista}"> checked="checked" </c:if> onclick="Test(this);">Sim</td>

                        </tr>

                    </table> 
                    <div <c:if test="${!inscricao.bolsista}">hidden="true"</c:if> id="dadosbancarios">
                        <table >
                            <tr><br/>
                            <br/>
                            <td style="border-style:hidden">
                                <font size="3">
                                <label for="campo-atividade">Dados Bancários</label><br/>
                                </font>
                            </td>
                            </tr>
                            <tr>
                                <td>
                                    <p>
                                        <label for="campo-banco">Banco*:</label><br/>
                                        <input style="height: 20px;" size="20" type="text" id="campo-banco" name="inscricao.banco" value="${inscricao.banco}"/>
                                    </p><p>
                                        <label for="campo-horas">Agência*:</label><br/>
                                        <input style="height: 20px;" size="10" type="text" id="campo-agencia" name="inscricao.agencia" value="${inscricao.agencia}"/>
                                    </p><p>
                                        <label for="campo-conta">Conta Corrente*:</label><br/>
                                        <input style="height: 20px;" size="10" type="text" id="campo-conta" name="inscricao.conta" value="${inscricao.conta}"/>
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </div>
                    </p>
                    <div id="outra">
                        <label for="campo-historico">Histórico Escolar*:</label>
                        <br/>
                        <div id="download-historico">
                            <p>
                                <a href="${pageContext.request.contextPath}/analise/download/${inscricao.historico}" target="_blank" >${inscricao.historico}</a>
                                <a href="javascript:hide_download_historico()">Remover</a>
                            </p>
                        </div>
                        <div id="upload-historico">
                            <p>
                                <input id="campo-his" type="file" name="historico" id="campo-historico"/>
                            </p>
                        </div>
                        <br/>
                        <label for="campo-comprovante">Comprovante de Matrícula*:</label>
                        <br/>
                        <div id="download-comprovante">
                            <p>
                                <a href="${pageContext.request.contextPath}/analise/download/${inscricao.comprovante}" target="_blank" >${inscricao.comprovante}</a>
                                <a href="javascript:hide_download_comprovante()">Remover</a>
                            </p>
                        </div>
                        <div id="upload-comprovante">
                            <p>
                                <input id="campo-com" type="file" name="comprovante" id="campo-comprovante"/>
                            </p>
                        </div>
                    </div>

                    <p>
                        Horários da Turma:
                    </p>    
                    <div id='calendar'></div>
                </div>

                <!-- Flag utilizada para apagar todos ou adicionar arquivos na lista-->
                <input type="checkbox" hidden="true" name="apagar" id="apagar"/>
            </form>
    </body>
</html>
