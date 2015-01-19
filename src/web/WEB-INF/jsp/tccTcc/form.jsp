<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
/**
 *
 * @author andre
 */
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_1.css">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/bluestork/css/template_css.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>
        <style type="text/css">
            label.error { float: none; color: red; margin: 0 .5em 0 0; vertical-align: top; font-size: 10px }
        </style>
        <script laguage="Javascript" type="text/javascript">
            window.onload = function() {
                var btn = document.getElementById("aproveitar");
                
                btn.onclick = function () {
                    var temai, tit, desc, orie, temat, tam, orie_imp;
                    
                    temai = document.getElementById("temas").selectedIndex;
                    temat = document.getElementById("temat").rows[temai];
                    tit = document.getElementById("titulo");
                    desc = document.getElementById("descricao");
                    orie = document.getElementById("orientador");
                    orie_imp = temat.cells[3].innerHTML;
                    tam =  orie.length;
                    
                    tit.setAttribute("value",temat.cells[2].innerHTML);
                    desc.setAttribute("value",temat.cells[1].innerHTML);
                    for(i=0;i<tam;i++) {
                        if (orie.options[i].innerHTML == orie_imp) {
                            orie.selectedIndex = i;
                        }
                    }
                };
            };
        </script>
        <script laguage="Javascript" type="text/javascript">
            
            $(function () { 
                $('#save').click(function(e) {
                                        
                    e.preventDefault();
                    $("#formTccTcc").submit(); 
                });
                $("#formTccTcc").validate({
                    rules:{
                        "tccTema.area":{
                            required:true
                        },
                        "tccTema.sigla":{
                            required: true
                        },
                        "tccTema.titulo":{
                            required: true
                        },
                        "tccTema.descricao":{
                            required: true
                        },
                        "tccTema.perfil":{
                            required: true
                        },
                        "tccTema.professor.id":{
                            required: true
                        }
                    },
                    messages:{
                        "tccTema.area":{
                            required: "Selecione a área"
                        },
                        "tccTema.sigla":{
                            required: "Informe a sigla"
                        },
                        "tccTema.titulo":{
                            required: "Informe o título"
                        },
                        "tccTema.descricao":{
                            required: "Informe a descrição"
                        },
                        "tccTema.perfil":{
                            required: "Informe o perfil"
                        },
                        "tccTema.professor.id":{
                            required: "Informe o professor"
                        }
                    }
                });
            });
            
        </script>
        <script language="javascript">  
            function somente_numero(campo){  
                var digits="0123456789"  
                var campo_temp   
                for (var i=0;i<campo.value.length;i++){  
                    campo_temp=campo.value.substring(i,i+1)   
                    if (digits.indexOf(campo_temp)==-1){  
                        campo.value = campo.value.substring(0,i);  
                    }  
                }  
            }
            
            $(document).ready(function(){
                $("#campo-cpf").mask("999.999.999-99");
            });
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
                                    <c:if test = "${podeSalvarTema}">
                                        <li class="button" id="toolbar-apply" >
                                            <a href="javascript:void(0);" id="save" class="toolbar">
                                                <span width="32" height="32" border="0" class="icon-32-save"></span>Salvar
                                            </a>
                                        </li>
                                    </c:if>
                                    <li class="button" id="toolbar-cancel">
                                        <a href="${pageContext.request.contextPath}/tcc/index">
                                            <span width="32" height="32" border="0" class="icon-32-back"></span>Voltar
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clr"></div>
                </div>
                <c:if test="${operacao == 'Cadastro'}">
                    <div class="pagetitle icon-48-article-add"><h2>${operacao} de TCC</h2></div>
                </c:if>
                <c:if test="${operacao == 'Edição'}">
                    <div class="pagetitle icon-48-article-edit"><h2>${operacao} de TCC</h2></div>
                </c:if>
            </div>
        </div>
        <c:if test="${not empty errors}">
            <div class="ui-widget">
                <div class="ui-state-error ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                    <p>
                        <span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
                        <strong>Situações não permitidas:</strong>
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li style="color: #cd0a0a">${error.message}</li>
                        </c:forEach>        
                    </ul>
                    </p>
                </div>
            </div>   
        </c:if>

        <form id="formTccTcc" name="formTccTcc" method="POST" action="<c:url value="/tcctcc"/>"> 
            <p>
                <c:if test="${not empty tccTcc.id}">
                    <input type="hidden" name="tccTcc.id" value="${tccTcc.id}"/>
                    <input type="hidden" name="_method" value="put"/>
                </c:if>
                <input type="hidden" name="tccTcc.aluno.id" value="${aluno.id}"/>
                <input type="hidden" name="tccTcc.periodo.id" value="${idPeriodo}"/>
                <input type="hidden" name="tccTcc.estado" value="${tccTcc.estado}"/>
                
            </p> 
            <p>
                <label for="temas">Temas:</label><br/>
                <select id="temas" <c:if test = "${not podeSalvarTema}"> disabled="true" </c:if>>
                    <option value="" >Outro não listado</option>
                    <c:forEach var="temasl" items="${temaList}">
                        <option  value="${temasl.id}" >${temasl}</option>
                    </c:forEach>
                </select>
                <input type="button" id="aproveitar" value="Aproveitar" <c:if test = "${not podeSalvarTema}"> disabled="true" </c:if>>
                <br/>
            </p>
            <p>
                <label for="titulo" >Título*:</label>
                <input type="text" id="titulo" name="tccTcc.titulo" value="${tccTcc.titulo}" size="100" <c:if test = "${not podeSalvarTema}"> disabled="true" </c:if>/>
            </p>
            <p>
                <label for="descricao">Descrição*:</label>
                <input type="text" id="descricao" name="tccTcc.descricao" value="${tccTcc.descricao}" size="100" <c:if test = "${not podeSalvarTema}"> disabled="true" </c:if>/>
            </p>
            <p>
                <label for="orientador">Orientador*:</label>
                <select id="orientador" name="tccTcc.professor.id" value="${tccTcc.professor}" <c:if test = "${not podeSalvarTema}"> disabled="true" </c:if>>
                    <option value="">Selecione um orientador</option>
                    <c:forEach var="professor" items="${professorList}">
                        <option  value="${professor.id}" <c:if test = "${professor.id == tccTcc.professor.id}"> selected="true" </c:if>>${professor}</option>
                    </c:forEach>
                </select><br/>
            </p>
            <p>
                <label for="campo-arquivo">Anexos (PDF, PNG, JPG)*:</label>
                <div id="download-anexos">
                    <ul id="lista">
                        <c:forEach items="${tcctcc.anexos}" var="anexo">
                            <li><a href="${pageContext.request.contextPath}/tcctcc/download/${anexo}" target="_blank" >${anexo}</a></li>                    
                        </c:forEach>                        
                    </ul>
                    <a id="deleteAll" href="javascript:hide_download_anexos()">Excluir todos os anexos</a><br/><br/>                
                </div>
                <div id="upload-anexos">
                    <input id="campo-anexo" type="file" name="anexos[]" multiple/>
                </div>
            </p>
            <p>
                <label for="estado1">Estado*:</label>
                <input id="estado1" type="text" name="campo-estado" value="${tccTcc.estado}" size="30" disabled="true"/>
            </p>
            <table id="temat" hidden>
                <tr>
                    <td> 0 </td>
                    <td>  </td>
                    <td>  </td>
                    <td>  </td>
                </tr>
                <c:forEach items="${temaList}" var="temaTab">
                    <tr>
                        <td>${temaTab.id}</td>
                        <td>${temaTab.descricao}</td>
                        <td>${temaTab.titulo}</td>
                        <td>${temaTab.professor.usuario.nome}</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </body>
</html>