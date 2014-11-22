<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <title>Per�odo de Cadastro de Monitorias [Exibi��o]</title>
    <script>
        function remove(){
            decisao = confirm("Deseja realmente remover o Per�odo?");
            if (decisao){
                document.location.href="${pageContext.request.contextPath}/periodos/${periodoCadMon.id}/remove";                                                              
            } else {
                alert ("Nenhum Per�odo foi removido");
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
                                    <a href="${pageContext.request.contextPath}/periodos/${periodoCadMon.id}/edit" id="edit" class="toolbar">
                                        <span width="32" height="32" border="0" class="icon-32-edit"></span>Editar
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:remove()">
                                        <span width="32" height="32" border="0" class="icon-32-delete"></span>Remover
                                    </a>
                                </li>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/periodos" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informa��es do Per�odo</h2></div>
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
    <c:if test="${not empty periodoCadMon.id}">
        <p>
            <b>Curso:</b>
            ${periodoCadMon.curso}
        </p>
        <p>
            <b>Data de In�cio:</b>
            <fmt:formatDate value="${periodoCadMon.dtInicio}" pattern="dd/MM/yyyy" />
        </p> 
        <p>
            <b>Data de T�rmino:</b>
            <fmt:formatDate value="${periodoCadMon.dtFim}" pattern="dd/MM/yyyy" />
        </p>         
        <p>
            <b>Status:</b>
            <c:if test = "${periodoCadMon.status==true}"> Ativo</c:if><c:if test = "${periodoCadMon.status==false}"> Inativo</c:if>
        </p>                 
    </c:if>
</body>