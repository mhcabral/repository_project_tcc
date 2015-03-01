<%-- 
    Document   : envioDocumentacao
    Created on : 06/04/2013, 20:02:55
    Author     : Thiago Santos
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    </head>
    <body>
        <form id="formEnvio" method="POST" action="${pageContext.request.contextPath}/envioDocumento/analise">
            <input type="hidden" name="inscricaoMonitoria.id" value="8"/>
            <input type="hidden" name="usuario.id" value="8"/>            
            <a id="acessar" href="javascript:void(0);">Acessar Solicitação de Análise de Relatório Final de Monitoria.</a>
        </form>

        <script>
            $('#acessar').click(function() {
                $('#formEnvio').submit();
            }); 
        </script>                
    </body>        
</html>

