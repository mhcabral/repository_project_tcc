package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.AtividadeDAO;
import br.edu.ufam.icomp.projeto4.dao.GrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Categoria;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import java.util.List;

/**
 *
 * @author Thiago Santos
 */
@Resource
public class AtividadeController {

    private final AtividadeDAO atividadeDAO;
    private final Result result;
    private final Validator validator;
    private GrupoDAO grupoDAO;
    private RegraDAO regraDAO;

    public AtividadeController(AtividadeDAO atividadeDAO, Result result, Validator validator, GrupoDAO grupoDAO, RegraDAO regraDAO) {
        this.atividadeDAO = atividadeDAO;
        this.result = result;
        this.validator = validator;
        this.grupoDAO = grupoDAO;
        this.regraDAO = regraDAO;
    }

    @Get("/atividades")
    @Permission(Perfil.ROOT)
    public List<Atividade> index() {
        List<Atividade> atividades = this.atividadeDAO.findAllOrderByGrupoAndAtividade();

        return atividades;
    }

    @Post("/atividades")
    @Permission(Perfil.ROOT)
    public void cadastrar(final Atividade atividade) {

        if (atividade.getGrupo().getId() == null || atividade.getGrupo().getId() == null) {
            validator.add(new ValidationMessage("Um grupo deve ser selecionado", "atividade.grupo.id"));
        }

        List<Atividade> atividades = this.atividadeDAO.findByCodigo(atividade.getCodigo());

        for (Atividade atividade1 : atividades) {
            if (atividade1.getCategoria() == atividade.getCategoria()
                    && atividade1.getGrupo().getId() == atividade.getGrupo().getId()
                    && atividade1.getNome().equals(atividade.getNome())) {
                validator.add(new ValidationMessage("Já existe uma outra atividade com o código, nome, grupo e categoria informados", "atividade.id"));
            }
        }

        List<Grupo> grupoList = this.grupoDAO.findAll();
        result.include("grupoList", grupoList);
        result.include("categoriaList", Categoria.values());
        result.include("operacao", "Cadastro");

        this.validator.validate(atividade);

        validator.onErrorRedirectTo(this).create();

        this.atividadeDAO.create(atividade);

        result.include("success", "cadastrada");

        this.result.redirectTo(AtividadeController.class).index();
    }

    @Get("/atividades/{id}/edit")
    @Permission(Perfil.ROOT)
    public Atividade edit(Long id) {
        List<Grupo> grupoList = this.grupoDAO.findAll();
        result.include("grupoList", grupoList);
        result.include("categoriaList", Categoria.values());
        result.include("operacao", "Edição");

        if (atividadeDAO.findById(id) == null) {
            this.result.redirectTo(AtividadeController.class).index();
        }

        return this.atividadeDAO.findById(id);
    }

    @Get("/atividades/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        Atividade atividade = this.atividadeDAO.findById(id);
        if (atividadeDAO.findById(id) == null) {
            validator.add(new ValidationMessage("Desculpe! Atividade não encontrada", "atividade.id"));
        } else if (!regraDAO.findByAtividade(id).isEmpty()) {
            validator.add(new ValidationMessage("Desculpe! Atividade não pôde ser removida, existem regras associadas a ela", "atividade.codigo"));
            result.include("atividade", atividade);
        }

        this.validator.validate(atividade);

        validator.onErrorUsePageOf(this).show(id);

        this.atividadeDAO.delete(atividade);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }

    @Put("/atividades")
    public void altera(Atividade atividade) {

        if (atividade.getGrupo().getId() == null || atividade.getGrupo().getId() == null) {
            validator.add(new ValidationMessage("Um grupo deve ser selecionado", "atividade.grupo.id"));
        }

        List<Atividade> atividades = this.atividadeDAO.findByCodigo(atividade.getCodigo());

        for (Atividade atividade1 : atividades) {
            if (atividade1.getId() != atividade.getId()
                    && atividade1.getCategoria() == atividade.getCategoria()
                    && atividade1.getGrupo().getId() == atividade.getGrupo().getId()
                    && atividade1.getNome().equals(atividade.getNome())) {
                validator.add(new ValidationMessage("Já existe uma outra atividade com o código, nome, grupo e categoria informados", "atividade.id"));
            }
        }

        List<Grupo> grupoList = this.grupoDAO.findAll();
        result.include("grupoList", grupoList);
        result.include("categoriaList", Categoria.values());
        result.include("operacao", "Edição");

        this.validator.validate(atividade);

        validator.onErrorRedirectTo(this).edit(atividade.getId());

        this.atividadeDAO.update(atividade);

        result.include("success", "alterada");

        this.result.redirectTo(this).index();
    }

    @Get("/atividades/create")
    @Permission(Perfil.ROOT)
    public List<Grupo> create() {
        List<Grupo> grupos = this.grupoDAO.findAll();
        result.include("categoriaList", Categoria.values());
        result.include("operacao", "Cadastro");

        return grupos;
    }

    @Get("/atividades/{id}/show")
    @Permission(Perfil.ROOT)
    public Atividade show(Long id) {
        Atividade atividade = atividadeDAO.findById(id);
        if (atividade == null) {
            this.result.redirectTo(AtividadeController.class).index();
        }

        return atividade;
    }
}
