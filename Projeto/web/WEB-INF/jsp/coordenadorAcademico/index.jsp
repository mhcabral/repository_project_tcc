<%-- 
    Document   : index
    Created on : 19/03/2013, 16:47:46
    Author     : Thiago Santos
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Listando Coordenadores Acadêmicos</title>

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
                var table = document.getElementById('formCoordAcademico');
                var Radio = null;

                Radio = table.RadioGroup1;
                if(Radio.checked){
                    switch (variavel){
                        case 'edit':
                            document.location.href = "${pageContext.request.contextPath}/coordAcademico/" + Radio.value + "/edit";                          
                            return;
                        case 'remove':    
                            decisao = confirm("Deseja realmente remover o coordenador acadêmico?");
                            if (decisao){
                                document.location.href = "${pageContext.request.contextPath}/coordAcademico/" + Radio.value + "/remove";                                                              
                            } else {
                                alert ("Nenhum coordenador acadêmico foi removido");
                            }
                            return;
                        case 'show':
                            document.location.href="${pageContext.request.contextPath}/coordAcademico/"+Radio.value + "/show";
                            return;
                        }
                    } else {
                        for(var i = 0; i < Radio.length; i++) {
                            if(Radio[i].checked) {                    
                                switch (variavel){
                                    case 'edit':
                                        document.location.href="${pageContext.request.contextPath}/coordAcademico/" + Radio[i].value + "/edit";                          
                                        return;
                                    case 'remove':
                                        decisao = confirm("Deseja realmente remover o coordenador acadêmico?");
                                        if (decisao){
                                            document.location.href="${pageContext.request.contextPath}/coordAcademico/"+Radio[i].value + "/remove";                                                              
                                        } else {
                                            alert ("Nenhum coordenador acadêmico foi removido");
                                        }                            
                                        return;
                                    case 'show':
                                        document.location.href="${pageContext.request.contextPath}/coordAcademico/" + Radio[i].value + "/show";                          
                                        return;
                                    }
                                }
                            }
                            alert('Você precisa selecionar um dos coordenadores acadêmicos');
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
                                        <a href="${pageContext.request.contextPath}/coordAcademico/create" id="new">
                                            <span width="32" height="32" border="0" class="icon-32-new"></span>Novo
                                        </a>
                                    </li>
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
                                        <a href="javascript:radioHab('remove')">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
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
                <div class="pagetitle icon-48-article"><h2>Listagem de<br/>Coordenadores Acadêmicos</h2></div>
            </div>
        </div>
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        Coordenador Acadêmico ${success} com sucesso!
                    </p>
                </div>
                <br/>
            </c:if>
        </div>
        <div>
            <c:if test="${not empty errors}">
                <div class="ui-widget">
                    <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                        <ul>
                            <c:forEach items="${errors}" var="error">
                                <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                                <li style="color: #cd0a0a">${error.message}</li>
                            </c:forEach>        
                        </ul>
                    </div>
                </div>   
            </c:if>
        </div>
        <br/>
        <div id="demo">
            <form id="formCoordAcademico">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>
                            <th></th>                        
                            <th class="left">Professor</th>                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${coordenadorAcademicoList}" var="coordAcademico">
                            <tr>
                                <td class="center"><input type="radio" name="RadioGroup1" value="${coordAcademico.id}"/></td>                            
                                <td class="left">${coordAcademico.professor.usuario.nome}</td>                                
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>                        
                            <th class="left">Professor</th>                            
                        </tr>
                    </tfoot>
                </table>
            </form>           
        </div>
    </body>
</html>