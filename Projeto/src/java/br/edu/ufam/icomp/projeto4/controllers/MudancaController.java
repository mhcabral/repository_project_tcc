package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.edu.ufam.icomp.projeto4.dao.MudancaDAO;
import br.edu.ufam.icomp.projeto4.model.Mudanca;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.Date;

/**
 *
 * @author Bruna
 */
@Resource
public class MudancaController {

    private final Result result;
    private final MudancaDAO mudancaDAO;

    public MudancaController(Result result, MudancaDAO mudancaDAO) {
        this.result = result;
        this.mudancaDAO = mudancaDAO;
    }

    public Mudanca create(Usuario usuario, Status status, String observacao) {
        Mudanca mudanca = new Mudanca();
        mudanca.setObservacao(observacao);
        mudanca.setResponsavel(usuario);
        mudanca.setDataMudanca(new Date());
        mudanca.setStatus(status);

        this.mudancaDAO.create(mudanca);

        return mudanca;
    }
}