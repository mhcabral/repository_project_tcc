<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Informações Inscrição em Monitoria[Exibição]</title>
    <script type="text/javascript" language="javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.js"></script>

    <script>
        function deferir(id){
            if(confirm("Deseja realmente DEFERIR a inscrição?")){
                var bolsista = document.getElementById("radio1").checked;                
                document.location.href="${pageContext.request.contextPath}/analiseInscricao/" + id + "/" + bolsista + "/defere";
            }
        }
        
        function indeferir(id){
            if(confirm("Deseja realmente INDEFERIR a inscrição?")){
                document.location.href="${pageContext.request.contextPath}/analiseInscricao/" + id + "/indefere";
            } 
        }        
        $(function() {
            $( "#radio" ).buttonset();

            if(${inscricaoMonitoria.bolsista == false}){
                $("#radio1").button({disabled:true});
                $("#dados-bancarios").hide();
            }
        });
        
        function showDadosBancarios(){
            $("#dados-bancarios").show();
        }
        
        function hideDadosBancarios(){
            $("#dados-bancarios").hide();
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
                                    <a href="javascript:deferir(${inscricaoMonitoria.id})" if="deferir">
                                        <span width="32" height="32" border="0" class="icon-32-publish"></span>Deferir
                                    </a>
                                </li>

                                <li>
                                    <a href="javascript:indeferir(${inscricaoMonitoria.id})" id="indeferir">
                                        <span width="32" height="32" border="0" class="icon-32-deny"></span>Indeferir
                                    </a>
                                </li>

                                <li class="button" id="toolbar-cancel">
                                    <a href="${pageContext.request.contextPath}/analiseInscricao" id="back">
                                        <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clr"></div>
            </div>
            <div class="pagetitle icon-48-info"><h2>Informações da Inscrição na Monitoria</h2></div>
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

        <h4><p>Informações da Monitoria</p></h4>
        <p>
            <b>Disciplina:</b>
            ${inscricaoMonitoria.monitoria.disciplina}
        </p>
        <p>
            <b>Professor:</b>
            ${inscricaoMonitoria.monitoria.professor}
        </p>
        <p>
            <b>Turma:</b>
            ${inscricaoMonitoria.monitoria.turma}
        </p>

        <br>
        <p>
        <h4><p>Bolsista:</p></h4>
        <form id="formDadosBancarios" method="POST">
            <div id="radio">
                <input value="sim" onclick="javascript:showDadosBancarios()" type="radio" id="radio1" name="radio" <c:if test="${inscricaoMonitoria.bolsista == true}">checked="checked"</c:if> /><label for="radio1">SIM</label>
                <input value="nao" onclick="javascript:hideDadosBancarios()" type="radio" id="radio2" name="radio" <c:if test="${inscricaoMonitoria.bolsista == false}">checked="checked"</c:if>/><label for="radio2">NÃO</label>
            </div>
        </form>
    </p>
    <div id="dados-bancarios">
        <h4><p>Informações Bancárias</p></h4>
        <p>
            <b>Banco:</b>
            ${inscricaoMonitoria.banco}
        </p>
        <p>
            <b>Agência:</b>
            ${inscricaoMonitoria.agencia}
        </p>
        <p>
            <b>Conta:</b>
            ${inscricaoMonitoria.conta}
        </p>
    <%--</c:if>--%>
    
</div>


<br>

<h4><p>Informações do Aluno</p></h4>
<p>
    <b>Matrícula:</b>
    ${inscricaoMonitoria.inscrito.id}
</p>
<p>
    <b>Aluno:</b>
    ${inscricaoMonitoria.inscrito}
</p>
<p>
    <b>Curso:</b>
    ${inscricaoMonitoria.monitoria.curso}
</p>
<p>
    <b>Comprovante:</b>
    <a href="${pageContext.request.contextPath}/analiseInscricao/download/${inscricaoMonitoria.comprovante}" target="_blank">${inscricaoMonitoria.comprovante}</a>
</p>
<p>
    <b>Histórico Escolar:</b>           
    <a href="${pageContext.request.contextPath}/analiseInscricao/download/${inscricaoMonitoria.historico}" target="_blank">${inscricaoMonitoria.historico}</a>            
</p>
</c:if>
</body>
