<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Informações de Monitorias [Exibição]</title>

</head>
<body>
    <div id="toolbar-box">
        <div class="m">
            <div class="toolbar-list" id="toolbar">
                <div class="cpanel2">
                    <div class="icon-wrapper">
                        <div class="icon">
                            <ul>
                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/monitorias/analisadas" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Monitoria</h2></div>
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
    <c:if test="${not empty inscricao.id}">
        <p>
            <b>Curso:</b>
            ${inscricao.monitoria.curso}
        </p>
        <p>
            <b>Disciplina:</b>
            ${inscricao.monitoria.disciplina}
        </p> 
        <p>
            <b>Professor:</b>
            ${inscricao.monitoria.professor}
        </p>
        <p>
            <b>Turma:</b>
            ${inscricao.monitoria.turma}
        </p>                 
        <p>
            <b>Vagas:</b>
            ${inscricao.monitoria.vagas}
        </p>
        <p>
            <b>Aluno:</b>
            ${inscricao.inscrito}
        </p>
        <p>
            <b>Status da Monitoria:</b>
            ${inscricao.statusAtual}
        </p>
    </c:if>
</body>