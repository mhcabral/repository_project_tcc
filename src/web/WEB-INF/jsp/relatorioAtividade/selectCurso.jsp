<%-- 
    Document   : show
    Created on : 19/03/2013, 22:06:46
    Author     : Thiago Santos
--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Seleção de Curso</title>
    <script type="text/javascript">
        function radioHab() {
            var select = document.getElementById('idCurso');                                 
            
            if(select.value != ""){
                document.location.href="${pageContext.request.contextPath}/regras/relatorioAtividade/" + select.value;                          
            }else{
                alert('Por favor! Selecione um curso');
            }            
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
                                <li>
                                    <a onclick="javascript:radioHab()" id="gerar">
                                        <span width="32" height="32" border="0" class="icon-32-stats"></span>Gerar
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
            <div class="pagetitle icon-48-info"><h2>Relatório de atividades</h2></div>
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

    <b><p>Filtrar Por:</p>
    </b>
    <b>Curso: *</b>
    <select name="idCurso" id="idCurso">
        <option value="">Selecione um curso</option>
        <c:forEach items="${cursoList}" var="curso">
            <option value="${curso.id}">${curso}</option>
        </c:forEach>
    </select>
</body>