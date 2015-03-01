
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <title>Listando Atividades</title>            

    <style type="text/css" title="currentStyle">
        @import "css/demo_page.css";
        @import "css/demo_table.css";
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
                    
            var table = document.getElementById('formAtividade');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'edit':
                        document.location.href="${pageContext.request.contextPath}/atividades/"+Radio.value + "/edit";                          
                        return;
                    case 'remove':    
                        decisao = confirm("Deseja realmente remover a atividade?");
                        if (decisao){
                            document.location.href="${pageContext.request.contextPath}/atividades/"+Radio.value + "/remove";                                                              
                        } else {
                            alert ("Nenhuma Atividade foi removida");
                        }                            
                        return;
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/atividades/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'edit':
                                    document.location.href="${pageContext.request.contextPath}/atividades/"+Radio[i].value + "/edit";                          
                                    return;
                                case 'remove':    
                                    decisao = confirm("Deseja realmente remover a atividade?");
                                    if (decisao){
                                        document.location.href="${pageContext.request.contextPath}/atividades/"+Radio[i].value + "/remove";                                                              
                                    } else {
                                        alert ("Nenhuma Atividade foi removida");
                                    }                            
                                    return;
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/atividades/"+Radio[i].value + "/show";                          
                                    return;
                                }                    
                            }                
                        }
                    
                        alert('Você precisa selecionar uma das atividades');
                    }
                }
    </script>
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
                                        <a href="${pageContext.request.contextPath}/atividades/create" id="new">
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
                <div class="pagetitle icon-48-article"><h2>Listagem de Atividades</h2></div>
            </div>
        </div>
        <!-- <table width="100%">
             <tr>
                 <td style="border-style:hidden" width="60%"><h2 class="art-postheader">Listagem de Atividades</h2></td>
                 <td style="border-style:hidden" width="10%"><a href="${pageContext.request.contextPath}/atividades/create"><img src="${pageContext.request.contextPath}/img/create.png" >Novo</img></td>
                 <td style="border-style:hidden" width="10%"><a href="javascript:radioHab('show')"><img src="${pageContext.request.contextPath}/img/show.png">Visualizar</img></a></td>
                 <td style="border-style:hidden" width="10%"><a href="javascript:radioHab('edit')"><img src="${pageContext.request.contextPath}/img/edit.png">Editar</img></a></td>
                 <td style="border-style:hidden" width="10%"><a href="javascript:radioHab('remove')"><img src="${pageContext.request.contextPath}/img/remove.png">Excluir</img></td>
             </tr>
         </table>-->
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        Atividade ${success} com sucesso!
                    </p>
                </div>
            </c:if>
        </div>
        <br/>
        <div id="demo">
            <form id="formAtividade">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Grupo</th>
                            <th class="left">Atividade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${atividadeList}" var="atividade">
                            <tr>
                                <td class="center"><input type="radio" name="RadioGroup1" value="${atividade.id}"/></td>                            
                                <td class="center">Grupo ${atividade.grupo.id}</td>
                                <td class="left">${atividade.codigo} ${atividade.nome}</td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>Grupo</th>
                            <th class="left">Atividade</th>
                        </tr>
                    </tfoot>
                </table>
            </form>           
        </div>                             
    </div>
</body>
