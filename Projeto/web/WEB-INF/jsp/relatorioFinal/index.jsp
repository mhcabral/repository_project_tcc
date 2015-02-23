<%-- 
    Document   : index
    Created on : 07/02/2013, 21:32:33
    Author     : Janderson
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <title>Listagem de Monitorias</title>            

    <style type="text/css" title="currentStyle">
        @import "${pageContext.request.contextPath}/css/demo_page.css";
        @import "${pageContext.request.contextPath}/css/demo_table.css";
    </style>          
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">    
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
            $('#example2').dataTable({
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
                    
            var table = document.getElementById('formInscricao');            
            var Radio=null;

            Radio= table.RadioGroup1;
            if(Radio.checked){
                switch (variavel){
                    case 'show':
                        document.location.href="${pageContext.request.contextPath}/inscricoes/"+Radio.value + "/relatorioFinal";                          
                        return;
                    }
                } else {
                    for(var i=0;i<Radio.length;i++) 
                    {
                        if(Radio[i].checked) 
                        {                    
                            switch (variavel){
                                case 'show':
                                    document.location.href="${pageContext.request.contextPath}/inscricoes/"+Radio[i].value + "/relatorioFinal";                          
                                    return;
                                }                    
                            
                            
                            
                            }                
                        }
                                                                    
                        alert('Você precisa selecionar uma das inscrições');
                    }
                }
          
    </script>
    <script language="JavaScript" type="text/javascript">
                function radioHab2(variavel) {
                    
                    var table = document.getElementById('formInscricao');

                    Radio2= table.RadioGroup2;
                    if(Radio2.checked){
                        switch (variavel){
                            case 'show':
                                document.location.href="${pageContext.request.contextPath}/inscricoes/"+Radio2.value + "/relatorioFinal";                          
                                return;
                            }
                        } else {
                            for(var i=0;i<Radio2.length;i++) 
                            {
                                if(Radio2[i].checked) 
                                {                    
                                    switch (variavel){
                                        case 'show':
                                            document.location.href="${pageContext.request.contextPath}/inscricoes/"+Radio2[i].value + "/relatorioFinal";                          
                                            return;
                                        }                    
                                    }                
                                }
                                alert('Você precisa selecionar uma das inscrições');
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
                <div class="pagetitle icon-48-featured"><h2>Envio de Relatório Final de Monitoria</h2></div>
            </div>
        </div>
        <div>
            <c:if test="${not empty success}">
                <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                         Relatório Final enviado com sucesso!
                    </p>
                </div>
            </c:if>
        </div>
        <br/>
        <div id="demo">
            <form id="formInscricao">
                <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
                    <thead>
                        <tr>   
                            <th></th>
                            <th>Disciplina</th>
                            <th>Professor</th>
                            <th>Turma</th>
                            <th>Detalhar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${inscricaoMonitoriaList}" var="inscricao">
                            <tr>
                                <td><input type="radio" name="RadioGroup1" value="${inscricao.id}"/></td>
                                <td class="center">${inscricao.monitoria.disciplina}</td>
                                <td class="center">${inscricao.monitoria.professor}</td>
                                <td class="center">${inscricao.monitoria.turma}</td>                                
                                <td class="center"><a href="${pageContext.request.contextPath}/inscricoes/${inscricao.id}/relatorioFinal"><img src="${pageContext.request.contextPath}/img/show.png"></img></a></td>
                            </tr>
                        </c:forEach>                    
                    </tbody>
                    <tfoot>
                        <tr>   
                            <th></th>
                            <th>Disciplina</th>
                            <th>Professor</th>
                            <th>Turma</th>
                            <th>Detalhar</th>
                        </tr>                        

                    </tfoot>
                </table>
                <br/><br/>

            </form> 
        </div>                             
    </div>
</body>