<%-- 
    Document   : index
    Created on : 07/02/2013, 21:32:33
    Author     : Bruna
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <title>Listando Análise de Estágios</title>            

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
                    
            var table = document.getElementById('formAnalise');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/analise/final/estagios/"+Radio.value + "/show";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {
                            switch (variavel){
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/analise/final/estagios/"+Radio[i].value + "/show";
                                    return;
                                }
                            }
                        }

                        alert('Você precisa selecionar um dos estágios');
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
                                        <a href="javascript:radioHab('show')">
                                            <span width="32" height="32" border="0" class="icon-32-preview"></span>Visualizar
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
                <div class="pagetitle icon-48-notice"><h2>Listagem de Estágios</h2></div>
            </div>
        </div>
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                        Estágio ${success} com sucesso!
                    </p>
                </div>
            </c:if>
        </div>
        <br/>

        <div id="demo">
            <form id="formAnalise">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>   
                            <th></th>
                            <th>Período</th>
                            <th>Aluno</th>                           
                            <th>Empresa</th>
                            <th>Professor Orientador</th>
                            <th>Visualizar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${estagioList}" var="estagio">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${estagio.id}"/></td>
                                <td class="center">${estagio.periodo.codigo}</td>
                                <td class="center">${estagio.aluno.usuario.nome}</td>                                
                                <td class="center" style="width: 30%">${estagio.empresa}</td>
                                <td class="center">${estagio.orientador.usuario.nome}</td>
                                <td class="center"><a href="${pageContext.request.contextPath}/analise/final/estagios/${estagio.id}/show"><img src="${pageContext.request.contextPath}/img/show.png"></img></a></td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>                        
                            <th>Período</th>
                            <th>Aluno</th>
                            <c:if test="${sessionData.usuario.perfil != 1}"><th>Curso</th></c:if>
                            <th>Empresa</th>
                            <th>Professor Orientador</th>
                            <th>Visualizar</th>
                        </tr>
                    </tfoot>
                </table>                        
            </form>           
        </div>                             
    </div>
</body>