<%-- 
    Document   : index
    Created on : 12/04/2013, 00:56:22
    Author     : Bruna
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listando Estágios</title>

        <style type="text/css" title="currentStyle">
            @import "${pageContext.request.contextPath}/css/demo_page.css";
            @import "${pageContext.request.contextPath}/css/demo_table.css";
        </style>
        <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
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
            } );
        </script>   

        <script language="JavaScript" type="text/javascript">
            function radioHab(variavel) {
                var table = document.getElementById('formEstagio');
                var Radio = null;

                Radio = table.RadioGroup1;
                if(Radio.checked){
                    switch (variavel){
                        case 'show':
                            document.location.href="${pageContext.request.contextPath}/estagios/"+Radio.value + "/show";
                            return;
                        }
                    } else {
                        for(var i = 0; i < Radio.length; i++) {
                            if(Radio[i].checked) {                    
                                switch (variavel){
                                    case 'show':
                                        document.location.href="${pageContext.request.contextPath}/estagios/" + Radio[i].value + "/show";                          
                                        return;
                                    }
                                }
                            }
                            alert('Você precisa selecionar uma das solicitações de estágio');
                        }
                    }
        </script>
    </head>
    <body id="dt_example">
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
                                        <a href="${pageContext.request.contextPath}/estagios/index" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-article"><h2>Listagem de Solicitações de Estágios</h2></div>
            </div>
        </div>
        <div id="demo">
            <form id="formEstagio">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>
                            <th></th>                        
                            <th>Período</th>
                            <th>Empresa</th>
                            <th>Situação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${estagioList}" var="estagio">
                            <tr>
                                <td class="center"><input type="radio" name="RadioGroup1" value="${estagio.id}"/></td>                            
                                <td class="center">${estagio.periodo}</td>
                                <td class="center">${estagio.empresa}</td>
                                <td class="center">${estagio.status.nome}</td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>Período</th>
                            <th>Empresa</th>
                            <th>Situação</th>
                        </tr>
                    </tfoot>
                </table>
            </form>           
        </div>
    </body>
</html>