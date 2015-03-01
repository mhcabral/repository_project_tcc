<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Envio de Documentos</title>
    <script laguage="Javascript" type="text/javascript">
            
        $(function () { 
            $('#enviar').click(function(e) {
                e.preventDefault();
                $("#formAtividade").submit(); 
            });
            $("#formAtividade").validate({                    
                rules:{                        
                    "relatorio":{
                        required: true
                    }
                },
                messages:{
                    "relatorio":{
                        required: "Por favor! Anexe o relátorio final."
                    }                        
                }
            });
        });
            
    </script>

    <style type="text/css">
        label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        .desativa {
            pointer-events: none;
            cursor: default;
            box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -moz-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            -webkit-box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.5);
            background: #f2f5f7;
        }
        a{
            background: #fff;
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
                                <li <c:if test="${not empty bloqueioRF}"> title="Opção indisponível! Fora do período de envio do relatório final"</c:if>>
                                    <a href="javascript:void(0);" id="enviar" class="toolbar" <c:if test="${not empty bloqueioRF}"> class="desativa"</c:if>>
                                            <span width="32" height="32" border="0" class="icon-32-send"></span>Enviar
                                        </a>
                                    </li>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal" id="back">
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
    <div>
        <c:if test="${not empty success}">
            <div class="ui-corner-all alert-success" style="margin-top: 20px; padding: 0 .7em;">
                <p>
                    <span class="ui-icon ui-icon-check" style="float: left; margin-right: .3em;"></span>
                    ${success}!
                </p>
            </div>
        </c:if>
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
    <br/>
    <c:if test="${not empty inscricaoMonitoria.id}">

        <p>
            <b>Disciplina:</b>
            ${inscricaoMonitoria.monitoria.disciplina}

            <b style="padding-left:15px">Turma:</b>
            ${inscricaoMonitoria.monitoria.turma}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricaoMonitoria.monitoria.professor}
        </p>      

        <br/><h4><p>Informações Bancárias</p></h4>
        <p>
            <b>Bolsista:</b>
            <c:if test="${inscricaoMonitoria.bolsista==true}">Sim
                <b style="padding-left:15px">Banco:</b>
                ${inscricaoMonitoria.banco}

                <b style="padding-left:15px">Agência:</b>
                ${inscricaoMonitoria.agencia}

                <b style="padding-left:15px">Conta:</b>
                ${inscricaoMonitoria.conta}
            </c:if>
            <c:if test="${inscricaoMonitoria.bolsista==false}">Não</c:if>
            </p>

        <c:if test="${not empty inscricaoMonitoria.relatorioFinal}">
            <br/><h4><p>Relatório Final:</p></h4>
            <p>            
                <a href="${pageContext.request.contextPath}/inscricoes/relatorioFinal/${inscricaoMonitoria.relatorioFinal.relatorioFinal}/download" target="_blank">${inscricaoMonitoria.relatorioFinal.relatorioFinal}</a>
            </p>        
        </c:if>

        <div style="left:150px; top:20px">
            <br/><h4><p>Enviar Novo Relatório Final:</p></h4>

            <form name="formAtividade" id="formAtividade" method="POST" action="${pageContext.request.contextPath}/inscricoes/relatorioFinal/upload" enctype="multipart/form-data">
                <input type="hidden" value="${inscricaoMonitoria.id}" name="inscricaoMonitoria.id"/> 
                <input id="campo-anexo" type="file" name="relatorio"/>
            </form>        
        </div>
    </c:if>
</body>