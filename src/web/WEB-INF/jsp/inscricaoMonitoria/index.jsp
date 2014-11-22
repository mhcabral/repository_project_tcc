<%-- 
    Document   : index
    Created on : 07/03/2013, 18:01:54
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Listando Solicitações</title>            

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
                $('#example2').dataTable({
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
        <script>
            function enviarTodas(lista) {
                if (lista) {
                    decisao = confirm("Deseja realmente enviar todas as solicitações?");
                    if (decisao) {
                        document.location.href = "${pageContext.request.contextPath}/solicitacoes/enviarTodas";
                    } else {
                        alert("Nenhuma solicitação foi enviada");
                    }
                } else {
                    alert("Nenhuma solicitação aguardando envio");
                }
            }
            ;
        </script>
        <script language="JavaScript" type="text/javascript">
            function radioHab(variavel) {

                var table = document.getElementById('formInscricao');
                var Radio = null;

                Radio = table.RadioGroup1;
                if (Radio.checked) {
                    switch (variavel) {
                        case 'inscricao':
                            document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio.value + "/nova";
                            return;
                        case 'show':
                            document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio.value + "/show";
                            return;
                        }
                    } else {
                        for (var i = 0; i < Radio.length; i++)
                        {
                            if (Radio[i].checked)
                            {
                                switch (variavel) {
                                    case 'inscricao':
                                        document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio[i].value + "/nova";
                                        return;
                                    case 'show':
                                        document.location.href = "${pageContext.request.contextPath}/inscriceos/" + Radio[i].value + "/show";
                                        return;
                                    }
                                }
                            }

                            alert('Você precisa selecionar uma das monitorias ofertadas');
                        }
                    }
        </script>
        <script language="JavaScript" type="text/javascript">
                    function radioHab2(variavel) {

                        var table = document.getElementById('formInscricao');
                        var Radio = null;

                        Radio2 = table.RadioGroup2;
                        if (Radio2.checked) {
                            switch (variavel) {
                                case 'show':
                                    document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2.value + "/show";
                                    return;
                                case 'edit':
                                    document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2.value + "/edit";
                                    return;
                            
                                case 'remove':
                                    decisao = confirm("Deseja realmente remover a inscrição?");
                                    if (decisao){
                                        document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2.value + "/remove";
                                    } else {
                                        alert ("Nenhuma Solicitação foi removida");
                                    }
                                    return;
                                }
                            } else {
                                for (var i = 0; i < Radio2.length; i++)
                                {
                                    if (Radio2[i].checked)
                                    {
                                        switch (variavel) {
                                            case 'show':
                                                document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2[i].value + "/show";
                                                return;
                                        
                                            case 'edit':
                                                document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2[i].value + "/edit";
                                                return;
                                        
                                            case 'remove':
                                                decisao = confirm("Deseja realmente remover a inscrição?");
                                                if (decisao){
                                                    document.location.href = "${pageContext.request.contextPath}/inscricoes/" + Radio2[i].value + "/remove";
                                                } else {
                                                    alert ("Nenhuma Inscrição foi removida");
                                                }
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
        <div id="demo">
            <h4>Total de monitorias feitas: ${totalComputado}</h4>
            <div id="container">
                <br/>
                <form id="formInscricao">
                    <div id="toolbar-box">
                        <div class="m">
                            <div class="toolbar-list" id="toolbar">
                                <div class="cpanel2">
                                    <div class="icon-wrapper">
                                        <div class="icon">
                                            <ul>
                                                <li class="button" id="toolbar-apply">
                                                    <a href="javascript:radioHab('inscricao')"  id="new">
                                                        <span width="32" height="32" border="0" class="icon-32-new"></span>Inscrição
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
                            <div class="pagetitle icon-48-notice"><h2>Monitorias Ofertadas</h2></div>
                        </div>
                    </div>
                    <div>
                        <c:if test="${not empty success}">
                            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                                <p>
                                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                                    Inscrição ${success} com sucesso!
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
                                <th>Vagas</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${monitoriaDisponivelList}" var="monitoria">
                                <tr>
                                    <td><input type="radio" name="RadioGroup1" value="${monitoria.id}"/></td>
                                    <td class="center">${monitoria.disciplina}</td>
                                    <td class="center">${monitoria.professor}</td>
                                    <td class="center">${monitoria.turma}</td>
                                    <td class="center">${monitoria.vagas}</td>
                                </tr>
                            </c:forEach>                    
                        </tbody>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>Disciplina</th>
                                <th>Professor</th>
                                <th>Turma</th>
                                <th>Vagas</th>                            
                            </tr>
                        </tfoot>
                    </table>
                    <br><br><br>
                    <div id="toolbar-box">
                        <div class="m">
                            <div class="toolbar-list" id="toolbar">
                                <div class="cpanel2">
                                    <div class="icon-wrapper">
                                        <div class="icon">
                                            <ul>
                                                <li class="button" id="toolbar-apply">
                                                    <a href="javascript:radioHab2('show')">
                                                        <span width="32" height="32" border="0" class="icon-32-preview"></span>Visualizar
                                                    </a>
                                                </li>
                                                <li class="button" id="toolbar-apply">
                                                    <a href="javascript:radioHab2('edit')">
                                                        <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                                    </a>
                                                </li>
                                                <li class="button" id="toolbar-apply">
                                                    <a href="javascript:radioHab2('remove')">
                                                        <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="clr"></div>
                            </div>
                            <div class="pagetitle icon-messaging"><h2>Inscrições Solicitadas</h2></div>
                        </div>
                    </div>
                    <br/><br/>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="example2" width="100%">
                        <thead>
                            <tr>
                                <th></th>
                                <th>Disciplina</th>
                                <th>Professor</th>
                                <th>Turma</th>
                                <th>Vagas</th>
                                <th>Visualizar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${monitoriaSolicitadaList}" var="inscricao">
                                <tr>
                                    <td><input type="radio" name="RadioGroup2" value="${inscricao.id}"/></td>
                                    <td class="center">${inscricao.monitoria.disciplina}</td>
                                    <td class="center">${inscricao.monitoria.professor}</td>
                                    <td class="center">${inscricao.monitoria.turma}</td>
                                    <td class="center">${inscricao.monitoria.vagas}</td>
                                    <td class="center"><a href="${pageContext.request.contextPath}/inscricoes/${inscricao.id}/show"><img src="${pageContext.request.contextPath}/img/show.png"/></a></td>
                                </tr>
                            </c:forEach>                    
                        </tbody>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>Disciplina</th>
                                <th>Professor</th>
                                <th>Turma</th>
                                <th>Vagas</th>
                                <th>Visualizar</th>
                            </tr>
                        </tfoot>
                    </table>
                </form>           
            </div>                             
        </div>
    </body>
</html>
