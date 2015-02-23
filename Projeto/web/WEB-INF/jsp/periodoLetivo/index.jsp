<%-- 
    Document   : index
    Created on : 11/03/2013, 00:37:53
    Author     : Bruna
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <title>Per�odos Letivos</title>            

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
                    "sZeroRecords":  "N�o foram encontrados resultados",
                    "sInfo":         "Mostrando de _START_ at� _END_ de _TOTAL_ registros",
                    "sInfoEmpty":    "Mostrando de 0 at� 0 de 0 registros",
                    "sInfoFiltered": "(filtrado de _MAX_ registros no total)",
                    "sInfoPostFix":  "",
                    "sSearch":       "Buscar:",
                    "sUrl":          "",
                    "oPaginate": {
                        "sFirst":    "Primeiro",
                        "sPrevious": "Anterior",
                        "sNext":     "Seguinte",
                        "sLast":     "�ltimo"
                    }
                }
            });
        } );
    </script>   

    <script language="JavaScript" type="text/javascript">
        function radioHab(variavel) {
                    
            var table = document.getElementById('formPeriodo');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'edit':
                        document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio.value + "/edit";                          
                        return;
                    case 'remove':    
                        decisao = confirm("Deseja realmente remover o per�odo?");
                        if (decisao){
                            document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio.value + "/remove";                                                              
                        } else {
                            alert ("Nenhum Per�odo foi removido");
                        }                            
                        return;
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'edit':
                                    document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio[i].value + "/edit";                          
                                    return;
                                case 'remove':    
                                    decisao = confirm("Deseja realmente remover o per�odo?");
                                    if (decisao){
                                        document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio[i].value + "/remove";                                                              
                                    } else {
                                        alert ("Nenhum Per�odo foi removido");
                                    }                            
                                    return;
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/periodosLetivo/"+Radio[i].value + "/show";                          
                                    return;
                                }                    
                            }                
                        }
                    
                        alert('Voc� precisa selecionar um dos per�odos');
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
                                    <a href="${pageContext.request.contextPath}/periodosLetivo/create" id="new">
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
            <div class="pagetitle icon-48-article"><h2>Listagem de Per�odos Letivos</h2></div>
        </div>
    </div>
    <div>
        <c:if test="${not empty success}">
            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                    Per�odo ${success} com sucesso!
                </p>
            </div>
            <br/>
        </c:if>
    </div>
    <c:if test="${not empty errors}">
        <div class="ui-widget">
            <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                <ul>
                    <c:forEach items="${errors}" var="error">
                        <li style="color: #cd0a0a">
                            <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>${error.message}
                        </li>
                    </c:forEach>        
                </ul>
            </div>
        </div>   
    </c:if>
    <br/>
    <div id="demo">        
        <form id="formPeriodo">
            <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                <thead>
                    <tr>
                        <th></th>
                        <th>C�digo</th>
                        <th>In�cio Letivo</th>
                        <th>T�rmino Letivo</th>
                        <th>In�cio Matr�cula</th>
                        <th>Fim Matr�cula</th>
                        <th>In�cio Est�gio</th>
                        <th>Fim Est�gio</th>                        
                        <th>Status</th>
                    </tr>
                </thead>                
                <tbody>                    
                    <c:forEach items="${periodoLetivoList}" var="periodo">
                        <tr>
                            <td class="center"><input type="radio" name="RadioGroup1" value="${periodo.id}"/></td>
                            <td class="center">${periodo.codigo}</td>
                            <td class="center"><fmt:formatDate value="${periodo.dtInicio}" pattern="dd/MM/yyyy"/></td>
                            <td class="center"><fmt:formatDate value="${periodo.dtFim}" pattern="dd/MM/yyyy"/></td>
                            <td class="center"><fmt:formatDate value="${periodo.dtInicioMatricula}" pattern="dd/MM/yyyy"/></td>
                            <td class="center"><fmt:formatDate value="${periodo.dtFimMatricula}" pattern="dd/MM/yyyy"/></td>
                            <td class="center"><fmt:formatDate value="${periodo.dtInicioEstagio}" pattern="dd/MM/yyyy"/></td>
                            <td class="center"><fmt:formatDate value="${periodo.dtFimEstagio}" pattern="dd/MM/yyyy"/></td>                            
                            <td class="center">${periodo.status}</td>
                        </tr>
                    </c:forEach>                    
                </tbody>
                <tfoot>
                    <tr>
                        <th></th>
                        <th>C�digo</th>
                        <th>In�cio Letivo</th>
                        <th>T�rmino Letivo</th>
                        <th>In�cio Matr�cula</th>
                        <th>Fim Matr�cula</th>
                        <th>In�cio Est�gio</th>
                        <th>Fim Est�gio</th>                                                
                        <th>Status</th>
                    </tr>
                </tfoot>  
            </table>
        </form>           
    </div>                             
</body>