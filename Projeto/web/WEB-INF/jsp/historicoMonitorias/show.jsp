<%-- 
    Document   : show
    Created on : 13/03/2013, 00:40:31
    Author     : Bruna
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            .desativa {
                pointer-events: none;
                cursor: default;
                box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
                background: #f2f5f7;
            }
        </style>
    </head>
    <body>
        <div id="toolbar-box">
            <div class="m">
                <div class="toolbar-list" id="toolbar">
                    <div class="cpanel2">
                        <div class="icon-wrapper">
                            <div class="icon">
                                <ul>
                                    <li <c:if test="${empty monitoriasInscritasList}">title="Opção indisponível! Nenhuma monitoria foi realizada"</c:if>>
                                        <a href="${pageContext.request.contextPath}/historicoMonitorias/alunos/${aluno.id}/relatorio" <c:if test="${empty monitoriasInscritasList}">class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-stats"></span>Gerar<br/>Relatório
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/historicoMonitorias/alunos" id="back">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <div class="pagetitle icon-48-info"><h2>Monitorias do Aluno</h2></div>
            </div>
        </div>
        <p>
            <b>Curso:</b>
            ${aluno.curso}
        </p>
        <p>
            <b>Matrícula:</b>
            ${aluno.matricula}
        </p>
        <p>
            <b>Nome:</b>
            ${aluno.usuario.nome}
        </p>

        <br/>
        <c:if test="${empty monitoriasInscritasList}">
            <h4><p>Nenhuma monitoria realizada</p></h4>
        </c:if>
        <c:if test="${not empty monitoriasInscritasList}">
            <br/>
            <h4><p>Monitorias Realizadas</p></h4>
            <table style="border: solid 1px #8099B3" width="100%">
                <tr>
                    <td style="border: solid 1px #8099B3">
                        <b>Curso</b>
                    </td>
                    <td style="border: solid 1px #8099B3">
                        <b>Professor</b>
                    </td>
                    <td style="border: solid 1px #8099B3">
                        <b>Disciplina</b>
                    </td>
                    <td style="border: solid 1px #8099B3">
                        <b>Turma</b>
                    </td>
                    <td style="border: solid 1px #8099B3">
                        <b>Bolsita</b>
                    </td>
                    <td style="border: solid 1px #8099B3">
                        <b>Status</b>
                    </td>
                </tr>
                <c:forEach items="${monitoriasInscritasList}" var="monitoriasInscritas">
                    <tr>
                        <td style="border: solid 1px #8099B3">
                            ${monitoriasInscritas.monitoria.curso}
                        </td>
                        <td style="border: solid 1px #8099B3">
                            ${monitoriasInscritas.monitoria.disciplina}
                        </td>
                        <td style="border: solid 1px #8099B3">
                            ${monitoriasInscritas.monitoria.turma}
                        </td>
                        <td style="border: solid 1px #8099B3">
                            <c:if test="${monitoriasInscritas.bolsista}">Sim</c:if>
                            <c:if test="${!monitoriasInscritas.bolsista}">Não</c:if>
                            </td>
                            <td style="border: solid 1px #8099B3">
                            ${monitoriasInscritas.statusAtual}
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
        </table>
    </body>
</html>