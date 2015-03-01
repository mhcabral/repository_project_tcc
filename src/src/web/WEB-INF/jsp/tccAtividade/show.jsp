<%-- 
    Document   : show
    Created on : 24/03/2013, 21:06:19
    Author     : Thiago Santos
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Atividade [Exibição]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover a atividade?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/tccAtividade/${tccAtividade.id}/remove";                                                              
            } else {
                alert ("Nenhuma atividade foi removida.");
            }                            
            return;
        }
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
                                    <a href="${pageContext.request.contextPath}/tccAtividade/${tccAtividade.id}/edit" id="edit" class="toolbar">
                                        <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/tccAtividade/0/index" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Atividade</h2></div>
        </div>
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
    <c:if test="${not empty tccAtividade.id}">
        <p>
            <b>Data Limite:</b>
            ${tccAtividade.datalimite}
        </p>
        <p>
            <b>Responsável:</b>
            ${tccAtividade.responsavel}
        </p>
        <p>
            <b>Descrição:</b>
            ${tccAtividade.descricao}
        </p>
        <p>
            <b>Prorrogação:</b>
            ${tccAtividade.dataprorrogacao}
        </p>
        <p>
            <b>Estado:</b>
            ${tccAtividade.estado}
        </p> 
    </c:if>
</body>