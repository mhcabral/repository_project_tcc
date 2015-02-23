<%-- 
    Document   : show
    Created on : 03/02/2013, 23:45:09
    Author     : Bruna
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Regra de Atividade [Exibi��o]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover a regra de atividade?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/regras/${regra.id}/remove";
            } else {
                alert ("Nenhuma regra de atividade foi removida");
            }                            
            return;
        }
    </script>
</head>
<body>
    <div id="content-box">
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li class="button" id="toolbar-apply">
                                        <a href="${pageContext.request.contextPath}/regras/${regra.id}/edit" id="edit" class="toolbar">
                                            <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:remove()">
                                            <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/regras" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Informa��es da Regra de Atividade</h2></div>
            </div>
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
    <c:if test="${not empty regra.id}">
        <p>
            <b>Atividade:</b>
            ${regra.atividade}
        </p>
        <p>
            <b>M�ximo de Horas:</b>
            ${regra.maximoHoras}
        </p>
    </c:if>
</body>