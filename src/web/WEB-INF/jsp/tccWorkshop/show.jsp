<%-- 
    Document   : show
    Created on : 19/01/2015, 11:04:00
    Author     : mhcabral
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Workshop [Exibição]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover esse workshop?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/tccworkshop/${tccWorkshop.id}/remove";                                                              
            } else {
                alert ("Nenhum workshop foi removido.");
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
                                    <a href="${pageContext.request.contextPath}/tccWorkshop/${tccLocais.id}/edit" id="edit" class="toolbar">
                                        <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:remove()">
                                        <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/tccworkshop" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Defesa</h2></div>
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
    <c:if test="${not empty tccWorkshop.id}">
        <p>
            <b>Trabalho:</b>
            ${tccWorkshop.tcctcc.titulo} 
        </p>
        <p>
            <b>Aluno:</b>
            ${tccWorkshop.tcctcc.aluno.nome}
        </p>
        <p>
            <b>Orientador:</b>
            ${tccWorkshop.tcctcc.professor.usuario.nome}
        </p>
        <p>
            <b>Primeiro Avaliador:</b>
            ${tccWorkshop.avaliador1}
        </p> 
        <p>
            <b>Segundo Avaliador:</b>
            ${tccWorkshop.avaliador2}
        </p>
        <p>
            <b>Local:</b>
            ${tccWorkshop.tcclocais.nome}
        </p>
        <p>
            <b>Data:</b>
            ${tccWorkshop.data}
        </p>
    </c:if>
</body>