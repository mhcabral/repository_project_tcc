<%-- 
    Document   : index
    Author     : tammy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Lançar notas</title>            
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
        
        <script language="JavaScript" type="text/javascript">
            function radioHab(variavel) {
                    
                var table = document.getElementById("formTccNotas");
                var Radio=null;

                Radio = table.RadioGroup1;
                if(Radio.checked){
                    switch (variavel){
                        case 'edit':
                            document.location.href="${pageContext.request.contextPath}/tccnotas/"+Radio.value + "/edit";
                            return;
                        case "show":
                            document.location.href="${pageContext.request.contextPath}/tccnotas/"+Radio.value + "/show";                          
                            return;
                        }
                    } else {
                        for(var i=0;i<Radio.length;i++) 
                        {
                            if(Radio[i].checked) 
                            {
                                switch (variavel){
                                    case 'edit':
                                        document.location.href="${pageContext.request.contextPath}/tccnotas/"+Radio[i].value + "/edit";                          
                                        return;  
                                    case "show":
                                        document.location.href="${pageContext.request.contextPath}/tccnotas/"+Radio[i].value + "/show";
                                        return;
                                    }
                                }
                            }
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
                                        <li class="button" id="toolbar-apply">
                                            <a href="javascript:radioHab('edit')">
                                                <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                            </a>
                                        </li>
                                        <li>                                
                                        <li class="button" id="toolbar-cancel">
                                            <a href="${pageContext.request.contextPath}/tcc/index" id="back">
                                                <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="clr"></div>
                    </div>
                    <div class="pagetitle icon-48-article"><h2>Notas</h2></div>
                </div>
            </div>
            <br/>
            <p>
            
        </p>

        <div id="demo">
            <form id="formTccNotas">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                            
                            <th></th>
                            <th>Aluno</th>               
                            <th>Título</th>		
                            <th>Nota 1</th>
                            <th>Nota 2</th>
                            <th>Nota 3</th>
                        
                    </thead>
                    <tbody>
                        <c:forEach items="${tccNotasList}" var="tccNotas">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${tccNotas.id}"/></td>
                                <td class="center">${tccNotas.tcctcc.aluno}</td>   
                                <td class="center">${tccNotas.tcctcc.titulo}</td>   
                                <td class="center">${tccNotas.nota1}</td>
                                <td class="center">${tccNotas.nota2}</td>
                                <td class="center">${tccNotas.nota3}</td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>Aluno</th>      
                            <th>Título</th>
                            <th>Nota 1</th>
                            <th>Nota 2</th>
                            <th>Nota 3</th>
                        </tr>
                    </tfoot>
                </table>                        
            </form>           
        </div>                             
    </div>
</body>
</html>