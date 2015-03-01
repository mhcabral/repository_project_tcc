package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.MonitoriaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Disciplina;

/**
 *
 * @author Bruna
 */
@Resource
public class DisciplinaController {

    private final Result result;
    private final Validator validator;
    private final DisciplinaDAO disciplinaDAO;
    private MonitoriaDAO monitoriaDAO;

    public DisciplinaController(Result result, Validator validator, DisciplinaDAO disciplinaDAO, MonitoriaDAO monitoriaDAO) {
        this.result = result;
        this.validator = validator;
        this.disciplinaDAO = disciplinaDAO;
        this.monitoriaDAO = monitoriaDAO;
    }

    @Get("/disciplinas")
    @Permission(Perfil.ROOT)
    public void index() {
        result.include("disciplinaList", disciplinaDAO.findAll());
    }

    @Get("/disciplinas/create")
    @Permission(Perfil.ROOT)
    public Disciplina create() {
        result.include("operacao", "Cadastro");
        return new Disciplina();
    }

    @Post("/disciplinas")
    public void cadastrar(final Disciplina disciplina) {
        Disciplina buscaCod = disciplinaDAO.findByCodigo(disciplina.getCodigo());
        Disciplina buscaNome = disciplinaDAO.findByNome(disciplina.getNome());

        if (buscaCod != null) {
            validator.add(new ValidationMessage("Já existe uma Disciplina cadastrada com esse código", "disciplina.codigo"));
        }
        if (buscaNome != null) {
            validator.add(new ValidationMessage("Já existe uma Disciplina cadastrada com esse nome", "disciplina.nome"));
        }

        this.validator.validate(disciplina);

        validator.onErrorRedirectTo(DisciplinaController.class).create();

        this.disciplinaDAO.create(disciplina);

        result.include("success", "cadastrada");

        this.result.redirectTo(DisciplinaController.class).index();
    }

    @Get("/disciplinas/{id}/edit")
    @Permission(Perfil.ROOT)
    public Disciplina edit(Long id) {
        result.include("operacao", "Edição");

        Disciplina disciplina = this.disciplinaDAO.findById(id);

        if (disciplina == null) {
            validator.add(new ValidationMessage("Desculpe! Disciplina não encontrada", "disciplina.codigo"));
        }

        this.validator.validate(disciplina);

        validator.onErrorRedirectTo(this).index();

        return disciplina;
    }

    @Put("/disciplinas")
    public void altera(Disciplina disciplina) {
        Disciplina buscaCod = disciplinaDAO.findByCodigo(disciplina.getCodigo());
        Disciplina buscaNome = disciplinaDAO.findByNome(disciplina.getNome());

        if (buscaCod != null && buscaCod.getId() != disciplina.getId()) {
            validator.add(new ValidationMessage("Já existe uma Disciplina cadastrada com esse código", "disciplina.codigo"));
        }
        if (buscaNome != null && buscaNome.getId() != disciplina.getId()) {
            validator.add(new ValidationMessage("Já existe uma Disciplina cadastrada com esse nome", "disciplina.nome"));
        }

        this.validator.validate(disciplina);

        validator.onErrorRedirectTo(DisciplinaController.class).edit(disciplina.getId());

        this.disciplinaDAO.update(disciplina);

        result.include("success", "alterada");

        this.result.redirectTo(this).index();
    }
    
    @Get("/disciplinas/{id}/show")
    @Permission(Perfil.ROOT)
    public Disciplina show(Long id) {
        Disciplina disciplina = this.disciplinaDAO.findById(id);

        if (disciplina == null) {
            validator.add(new ValidationMessage("Desculpe! Disciplina não encontrada", "disciplina.codigo"));
        }

        this.validator.validate(disciplina);

        validator.onErrorRedirectTo(this).index();

        return disciplina;
    }

    @Get("/disciplinas/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        Disciplina disciplina = this.disciplinaDAO.findById(id);

        if (disciplina == null) {
            validator.add(new ValidationMessage("Desculpe! Disciplina não encontrada", "disciplina.codigo"));
        }
        if (!monitoriaDAO.listByDisciplina(id).isEmpty()) {
            validator.add(new ValidationMessage("Desculpe! Disciplina não pôde ser removida, existem monitorias associadas a ela", "disciplina.codigo"));
        }

        this.validator.validate(disciplina);

        validator.onErrorRedirectTo(this).index();

        this.disciplinaDAO.delete(disciplina);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
}