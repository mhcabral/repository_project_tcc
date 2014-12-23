/**
 *
 * @author andre
 */
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Tema [Exibição]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover o tema?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/tcctema/${tccTema.id}/remove";                                                              
            } else {
                alert ("Nenhum tema foi removido.");
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
                                    <a href="${pageContext.request.contextPath}/tcctema/${tccTema.id}/edit" id="edit" class="toolbar">
                                        <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:remove()">
                                        <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/tcctemas" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações do Tema</h2></div>
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
    <c:if test="${not empty tccTema.id}">
        <p>
            <b>Área:</b>
            ${tccTema.area}
        </p>
        <p>
            <b>Sigla:</b>
            ${tccTema.sigla}
        </p>
        <%--
        <p>
            <b>Cursos:</b><p/>
            <c:forEach items="${tccTema.curso}" var="tccTemaCurso">
                <tr>
                    <td class="center">${tccTemaCurso.curso}</td><p/>
                </tr>
            </c:forEach>
        </p>
        --%>
        <p>
            <b>Título:</b>
            ${tccTema.titulo}
        </p>
        <p>
            <b>Descrição:</b>
            ${tccTema.descricao}
        </p>
        <p>
            <b>Perfil:</b>
            ${tccTema.perfil}
        </p>
        <p>
            <b>Professor:</b>
            ${tccTema.professor.usuario.nome}
        </p>
        <p>
            <b>Estado:</b>
            ${tccTema.estado}
        </p> 
    </c:if>
</body>