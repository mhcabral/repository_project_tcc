<%-- 
    Document   : lembrar
    Created on : 18/02/2013, 16:57:11
    Author     : leonardo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script laguage="Javascript" type="text/javascript">
    $(function() {
        $('#solicitarsenha').click(function(e) {
            e.preventDefault();
            $("#senha").submit();
        });

        $("#senha").validate({
            rules: {
                "email": {
                    required: true
                }
            },
            messages: {
                "email": {
                    required: "Informe seu email"
                }
            }
        });
    });
</script>

<table><tr><td>
            <form id="senha" name="senha" action="<c:url value='/solicitarDados'/>" method="POST" style="">

                <fieldset>
                    <legend>Informe e-mail de cadastro</legend>
                    <label for="email">e-mail: </label>
                    <input id="email" type="text" name="email"/>
                    <button id="solicitarsenha">Solicitar Dados</button>
                </fieldset>
            </form></div>
            <c:if test="${resultado == 'não encontrado'}">
                <font size="2">Email não encontrado</font>
            </c:if>

        </td></tr></table>