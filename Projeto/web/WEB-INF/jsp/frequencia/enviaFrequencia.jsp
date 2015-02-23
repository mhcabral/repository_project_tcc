<%-- 
    Document   : enviaFrequencia
    Created on : 24/03/2013, 18:51:00
    Author     : Bruna
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listagem de Monitorias Inscritas</title>

        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/css/demo_page.css";
            @import "${pageContext.request.contextPath}/css/demo_table.css";
        </style>        

        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/theme.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#example').dataTable({
                    "oLanguage": {
                        "sProcessing": "Processando...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "Não foram encontrados resultados",
                        "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                        "sInfoFiltered": "(filtrado de _MAX_ registros no total)",
                        "sInfoPostFix": "",
                        "sSearch": "Buscar:",
                        "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext": "Seguinte",
                            "sLast": "Último"
                        }
                    }
                });
            });
        </script>   
        <script language="JavaScript" type="text/javascript">
            function radioHab(variavel) {
                var table = document.getElementById('formMonitoria');
                var Radio = table.RadioGroup1;

                if (Radio.checked) {
                    switch (variavel) {
                        case 'frequencia':
                            document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio.value + "/frequencia";
                            return;
                        };
                    } else {
                        for (var i = 0; i < Radio.length; i++) {
                            if (Radio[i].checked) {
                                switch (variavel) {
                                    case 'frequencia':
                                        document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio[i].value + "/frequencia";
                                        return;
                                    };
                                };
                            };
                            alert('Você precisa selecionar uma das monitorias');
                        };
                    };
        </script>
    </head>
    <body id="dt_example">
        <div id="demo">
            <div id="container">
                <form id="formMonitoria">
                    <div id="toolbar-box">
                        <div class="m">
                            <div class="toolbar-list" id="toolbar">
                                <div class="cpanel2">
                                    <div class="icon-wrapper">
                                        <div class="icon">
                                            <ul>
                                                <li class="button" id="toolbar-apply">
                                                    <a href="javascript:radioHab('frequencia')"  id="new">
                                                        <span width="32" height="32" border="0" class="icon-32-new"></span>Preencher<br/>Frequência
                                                    </a>
                                                </li>
                                                <li class="button" id="toolbar-cancel">
                                                    <a href="${pageContext.request.contextPath}/inscricoes/index" id="back">
                                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="clr"></div>
                            </div>
                            <div class="pagetitle icon-48-calendar"><h2>Envio de Frequencia</h2></div>
                        </div>
                    </div>
                    <div>
                        <c:if test="${not empty success}">
                            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                                <p>
                                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                                    Frequência${s} ${success} com sucesso!
                                </p>
                            </div>
                        </c:if>
                    </div>
                    <br/>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Disciplina</th>
                                <th>Professor</th>
                                <th>Turma</th>
                                <th>Curso</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${monitoriaList}" var="monitoria">
                                <tr>
                                    <td><input type="radio" name="RadioGroup1" value="${monitoria.id}"/></td>
                                    <td class="center">${monitoria.monitoria.disciplina}</td>
                                    <td class="center">${monitoria.monitoria.professor}</td>
                                    <td class="center">${monitoria.monitoria.turma}</td>
                                    <td class="center">${monitoria.monitoria.curso}</td>
                                </tr>
                            </c:forEach>                    
                        </tbody>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>Disciplina</th>
                                <th>Professor</th>
                                <th>Turma</th>
                                <th>Curso</th>                           
                            </tr>
                        </tfoot>
                    </table>
                </form>           
            </div>                             
        </div>
    </body>
</html>