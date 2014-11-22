<%-- 
    Document   : index
    Created on : 11/03/2013, 02:36:08
    Author     : Leonardo
--%>

%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de Monitorias</title>

        <style type="text/css">
            @import "${pageContext.request.contextPath}/css/demo_table.css";
            @import "${pageContext.request.contextPath}/css/demo_page.css";
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>


        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">    
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/vendor/jquery.ui.widget.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.iframe-transport.js"></script>      
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>

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

        <script>
            (function($) {
                $.widget("ui.combobox", {
                    _create: function() {
                        var input,
                        that = this,
                        wasOpen = false,
                        select = this.element.hide(),
                        selected = select.children(":selected"),
                        value = selected.val() ? selected.text() : "",
                        wrapper = this.wrapper = $("<span>")
                        .addClass("ui-combobox")
                        .insertAfter(select);

                        function removeIfInvalid(element) {
                            var value = $(element).val(),
                            matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(value) + "$", "i"),
                            valid = false;
                            select.children("option").each(function() {
                                if ($(this).text().match(matcher)) {
                                    this.selected = valid = true;
                                    return false;
                                }
                            });

                            if (!valid) {
                                // remove invalid value, as it didn't match anything
                                $(element)
                                .val("")
                                .attr("title", value + " não encontrado")
                                .tooltip("open");
                                select.val("");
                                setTimeout(function() {
                                    input.tooltip("close").attr("title", "");
                                }, 2500);
                                input.data("ui-autocomplete").term = "";
                            }
                        }

                        input = $("<input>")
                        .appendTo(wrapper)
                        .val(value)
                        .attr("title", "")
                        .addClass("ui-state-default ui-combobox-input")
                        .autocomplete({
                            delay: 0,
                            minLength: 0,
                            source: function(request, response) {
                                var matcher = new RegExp($.ui.autocomplete.escapeRegex(request.term), "i");
                                response(select.children("option").map(function() {
                                    var text = $(this).text();
                                    if (this.value && (!request.term || matcher.test(text)))
                                        return {
                                            label: text.replace(
                                            new RegExp(
                                            "(?![^&;]+;)(?!<[^<>]*)(" +
                                                $.ui.autocomplete.escapeRegex(request.term) +
                                                ")(?![^<>]*>)(?![^&;]+;)", "gi"
                                        ), "<strong>$1</strong>"),
                                            value: text,
                                            option: this
                                        };
                                }));
                            },
                            select: function(event, ui) {
                                ui.item.option.selected = true;
                                that._trigger("selected", event, {
                                    item: ui.item.option
                                });
                            }, change: function(event, ui) {

                                if ($('#campo-item').val()) {
                                    document.getElementById("label-info").style.visibility = "visible";
                                    document.getElementById("info-grupo").style.visibility = "visible";

                                    $.getJSON('${pageContext.request.contextPath}/cursos/buscaCurso.json', {
                                        id: $('#campo-item').val()
                                    }, function(data) {
                                        document.getElementById("info-grupo").value = data.maximoHoras;
                                    });
                                } else {
                                    document.getElementById("label-info").style.visibility = "hidden";
                                    document.getElementById("info-grupo").style.visibility = "hidden";
                                    document.getElementById("info-grupo").value = "";
                                }
                            }
                        })
                        .addClass("ui-widget ui-widget-content ui-corner-left");

                        input.data("ui-autocomplete")._renderItem = function(ul, item) {
                            return $("<li>")
                            .append("<a>" + item.label + "</a>")
                            .appendTo(ul);
                        };

                        $("<a>")
                        .attr("tabIndex", -1)
                        .attr("title", "Todas os Itens")
                        .tooltip()
                        .appendTo(wrapper)
                        .button({
                            icons: {
                                primary: "ui-icon-triangle-1-s"
                            },
                            text: false
                        })
                        .removeClass("ui-corner-all")
                        .addClass("ui-corner-right ui-combobox-toggle")
                        .mousedown(function() {
                            wasOpen = input.autocomplete("widget").is(":visible");
                        })
                        .click(function() {
                            input.focus();

                            // close if already visible
                            if (wasOpen) {
                                return;
                            }

                            // pass empty string as value to search for, displaying all results
                            input.autocomplete("search", "");
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
            })(jQuery);

            $(function() {
                $("#campo-curso").combobox();
            });

            $(function() {
                $("#campo-professor").combobox();
            });

            $(function() {
                $("#campo-disciplina").combobox();
            });

            $(function() {
                $("#campo-aluno").combobox();
            });

            $(function() {
                $("#campo-periodo").combobox();
            });
        </script>
        <script language="javascript">
            function somente_numero(campo) {
                var digits = "0123456789"
                var campo_temp
                for (var i = 0; i < campo.value.length; i++) {
                    campo_temp = campo.value.substring(i, i + 1)
                    if (digits.indexOf(campo_temp) == -1) {
                        campo.value = campo.value.substring(0, i);
                    }
                }
            }
        </script> 
        <script>
            /* Portuguese initialisation for the jQuery UI date picker plugin. */
            jQuery(function($) {
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

        <script laguage="Javascript" type="text/javascript">
            $(function() {
                $('#imprimir').click(function(e) {
                    e.preventDefault();
                    $("#formHisMonAnalise").attr('target', '_blank').attr('action', '${pageContext.request.contextPath}/monitorias/historico').submit();
                });
                $('#gerar').click(function(e) {
                    e.preventDefault();
                    $("#formHisMonAnalise").attr('action', '${pageContext.request.contextPath}/monitorias/analisadas').submit();
                });
                $("#formHisMonAnalise").validate({
                    rules: {
                        "monitoria.curso.id": {
                            required: true
                        },
                        "monitoria.disciplina.id": {
                            required: true
                        },
                        "monitoria.professor.id": {
                            required: true
                        },
                        "monitoria.turma": {
                            required: true,
                            min: 1
                        },
                        "monitoria.vagas": {
                            required: true,
                            min: 1
                        }

                    },
                    messages: {
                        "monitoria.curso.id": {
                            required: "Selecione o curso"
                        },
                        "monitoria.disciplina.id": {
                            required: "Selecione uma disciplina"
                        },
                        "monitoria.professor.id": {
                            required: "Selecione um professor"
                        },
                        "monitoria.turma": {
                            required: "Informe a turma",
                            min: "A turma deve ser um número positivo"
                        },
                        "monitoria.vagas": {
                            required: "Informe o número de vagas",
                            min: "O mínimo de vagas permitidas é 1"
                        }
                    }
                });
            });

            function somente_numero(campo) {
                var digits = "0123456789"
                var campo_temp
                for (var i = 0; i < campo.value.length; i++) {
                    campo_temp = campo.value.substring(i, i + 1)
                    if (digits.indexOf(campo_temp) == -1) {
                        campo.value = campo.value.substring(0, i);
                    }
                }
            }

        </script>  

        <script>
            function getDataAtual() {
                hoje = new Date()
                dia = hoje.getDate()
                mes = hoje.getMonth()
                ano = hoje.getFullYear()
                mes = mes + 1
                if (dia < 10)
                    dia = "0" + dia
                if (mes < 10)
                    mes = "0" + mes
                if (ano < 2000)
                    ano = "19" + ano

                //O mes começa em Zero, então soma-se 1

                today = dia + "/" + (mes) + "/" + ano;
                return today;
            }
            var dataSetada = false;
            $(function() {
                var dates = $("#campo-inicio, #campo-termino").datepicker({
                    changeMonth: true,
                    changeYear: true,
                    option: jQuery.datepicker.regional['pt-PT'],
                    beforeShow: function(input, inst) {
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
                    onSelect: function(selectedDate) {
                        habilitaDataTermino();
                        var option = this.id == "campo-inicio" ? "minDate" : "maxDate";
                        instance = $(this).data("datepicker");
                        date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                        dates.not(this).datepicker("option", option, date);
                    }
                });
            });
            function habilitaDataTermino() {
                if (document.getElementById("campo-inicio").value) {
                    document.getElementById("campo-termino").disabled = false;
                } else {
                    document.getElementById("campo-termino").disabled = true;
                }
            }

            $(document).ready(function() {
                $("#deleteAll").click(function() {
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
                                        <a href="javascript:void(0);" id="gerar" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-48-article-add"></span>Gerar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-apply">
                                        <a href="javascript:void(0);" id="imprimir" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-print"></span>Imprimir
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/" id="cancel">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-article-add"><h2>Listagem de Monitorias</h2></div>
            </div>
        </div>
        <form id="formHisMonAnalise"  name="formHisMonAnalise" method="POST" enctype="multipart/form-data">
            <div class="ui-widget">
                <table width="100%">
                    <tr><br/>
                    <br/>

                    <td style="border-style:hidden" <c:if test="${sessionData.usuario.perfil == 1}">hidden="true"</c:if>>
                        <c:if test="${sessionData.usuario.perfil != 1}">
                            <font size="2">
                            <label for="campo-curso">Curso:</label><br/>
                            <select id="campo-curso" name="inscricao.monitoria.curso.id" value="${inscricao.monitoria.curso}" >
                                <option value="">Selecione um Curso</option>
                                <c:forEach var="item" items="${cursoList}">
                                    <option value="${item.id}" <c:if test = "${item.id == inscricao.monitoria.curso.id}"> selected</c:if>>${item}</option>
                                </c:forEach>
                            </select>
                            </font><br/>
                        </c:if>
                    </td>

                    <td style="border-style:hidden">
                        <font size="2">
                        <label for="campo-professor">Professor:</label><br/>
                        <select id="campo-professor" name="inscricao.monitoria.professor.id" value="${inscricao.monitoria.professor.id}" >
                            <option value="">Selecione um Professor</option>
                            <c:forEach var="item" items="${professorList}">
                                <option value="${item.id}" <c:if test = "${item.id == inscricao.monitoria.professor.id}"> selected</c:if>>${item}</option>
                            </c:forEach>
                        </select>
                        </font><br/>
                    </td>            

                    <td style="border-style:hidden">
                        <font size="2">
                        <label for="campo-aluno">Aluno:</label><br/>
                        <select id="campo-aluno" name="inscricao.inscrito.id" value="${inscricao.inscrito.id}" >
                            <option value="">Selecione uma Disciplina</option>
                            <c:forEach var="item" items="${alunoList}">
                                <option value="${item.id}" <c:if test = "${item.id == inscricao.inscrito.id}"> selected</c:if>>${item}</option>
                            </c:forEach>
                        </select>
                        </font><br/>
                    </td>   
                    </tr>
                    <tr><br/>
                    <td style="border-style:hidden">
                        <font size="2">
                        <label for="campo-periodo">Período:</label><br/>
                        <select id="campo-periodo" name="inscricao.periodo.id" value="${inscricao.monitoria.periodo}" >
                            <option value="">Selecione um Período</option>
                            <c:forEach var="item" items="${periodoList}">
                                <option value="${item.id}" <c:if test = "${atividade.id == inscricao.monitoria.periodo.id}"> selected</c:if>>${item}</option>
                            </c:forEach>
                        </select>
                        </font><br/>
                    </td>
                    <td style="border-style:hidden">
                        <font size="2">
                        <label for="campo-disciplina">Disciplina:</label><br/>
                        <select id="campo-disciplina" name="inscricao.monitoria.disciplina.id" value="${inscricao.monitoria.disciplina}" >
                            <option value="">Selecione uma Disciplina</option>
                            <c:forEach var="item" items="${disciplinaList}">
                                <option value="${item.id}" <c:if test = "${item.id == inscricao.monitoria.disciplina.id}"> selected</c:if>>${item}</option>
                            </c:forEach>
                        </select>
                        </font><br/>
                    </td>   
                    </tr>
                </table>                        
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

            <br/>
            <div id="dt_example">        
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>
                            <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                            <th>Professor</th>
                            <th>Disciplina</th>
                            <th>Turma</th>
                            <th>Vagas</th>
                            <th>Detalhar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${inscricaoList}" var="inscricao">
                            <tr>
                                <c:if test="${sessionData.usuario.perfil != 1}"><td class="center">${inscricao.monitoria.curso}</td></c:if>
                                <td class="center">${inscricao.monitoria.professor}</td>
                                <td class="center">${inscricao.monitoria.disciplina}</td>
                                <td class="center">${inscricao.monitoria.turma}</td>
                                <td class="center">${inscricao.monitoria.vagas}</td>
                                <td class="center"><a href="${pageContext.request.contextPath}/monitorias/${inscricao.id}/detalhes"><img src="${pageContext.request.contextPath}/img/show.png"/></a></td>
                            </tr>
                        </c:forEach>
                    <tfoot>
                        <tr>
                            <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                            <th>Professor</th>
                            <th>Disciplina</th>
                            <th>Turma</th>
                            <th>Vagas</th>
                            <th>Detalhar</th>
                        </tr>
                    </tfoot>
                    </tbody>
                </table>
            </div>

        </form>
</html>
