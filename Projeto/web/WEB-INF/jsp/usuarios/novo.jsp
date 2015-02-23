<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form id="usuariosForm" action="<c:url value='/usuarios'/>"
      method="POST">
    <fieldset>
        <legend>Criar novo usuário</legend>

        <label for="nome">Nome:</label>
        <input id="nome" class=""
               type="text" name="usuario.nome" value="${usuario.nome }"/>

        <label for="login">CPF:</label>
        <input id="login" class=""
               type="text" name="usuario.cpf" value="${usuario.cpf }"/>

        <label for="nome">e-mail:</label>
        <input id="email" class=""
               type="text" name="usuario.email" value="${usuario.email }"/>

        <label for="senha">Senha:</label>
        <input id="senha" class="" type="password"
               name="usuario.pwd"/>

        <label for="confirmacao">Confirme a senha:</label>
        <input id="confirmacao" class="" equalTo="#senha" type="password"/>

        <label for="tipo">Papel:</label>
        <select name="usuario.role">    
                <option selected value="ROOT">ROOT</option>
                <option value="COORDENADOR">COORDENADOR</option>
                <option value="SECRETARIA">SECRETARIA</option>
                <option value="COORDENADORACAD">COORDENADORACAD</option>
                <option value="ALUNO">ALUNO</option>
        </select>  

        <button type="submit">Enviar</button>
    </fieldset>
</form>