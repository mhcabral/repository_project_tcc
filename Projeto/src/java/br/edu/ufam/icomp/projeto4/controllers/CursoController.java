package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Curso;
import java.util.List;

@Resource
public class CursoController {

    private final Result result;
    private final Validator validator;
    private final CursoDAO cursoDAO;
    private AlunoDAO alunoDAO;
    private RegraDAO regraDAO;

    public CursoController(CursoDAO cursoDAO, Result result, Validator validator, AlunoDAO alunoDAO, RegraDAO regraDAO) {
        this.result = result;
        this.validator = validator;
        this.cursoDAO = cursoDAO;
        this.alunoDAO = alunoDAO;
        this.regraDAO = regraDAO;
    }

    @Get("/cursos")
    @Permission(Perfil.ROOT)
    public List<Curso> index() {
        List<Curso> cursos = this.cursoDAO.findAll();

        return cursos;
    }

    @Get("/cursos/create")
    @Permission(Perfil.ROOT)
    public Curso create() {
        result.include("operacao", "Cadastro");
        return new Curso();
    }

    @Get("/cursos/{id}/edit")
    @Permission(Perfil.ROOT)
    public Curso edit(Long id) {
        List<Curso> grupoList = this.cursoDAO.findAll();
        result.include("operacao", "Edição");

        return this.cursoDAO.findById(id);
    }

    @Get("/cursos/{id}/show")
    @Permission(Perfil.ROOT)
    public Curso show(Long id) {
        return this.cursoDAO.findById(id);
    }

    @Get
    @Path("/regras/buscaCurso.json")
    public void buscaACursoJson(Long id) {
        List<Curso> cursos = this.cursoDAO.findAll();
        result.use(Results.json()).withoutRoot().from(cursos).serialize();
    }
    
    @Get("/cursos/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        Curso curso = this.cursoDAO.findById(id);

        if (curso == null) {
            validator.add(new ValidationMessage("Desculpe! Curso não encontrado", "curso.codigo"));
        } else if (!(alunoDAO.findByCursoId(id).isEmpty() && regraDAO.findByCurso(id).isEmpty())) {
            validator.add(new ValidationMessage("Desculpe! Curso não pôde ser removido, existem coisas associadas a ele", "curso.codigo"));
            result.include("curso", curso);
        }

        this.validator.validate(curso);

        validator.onErrorUsePageOf(this).show(id);

        this.cursoDAO.delete(curso);

        this.result.include("success", "removido");

        this.result.redirectTo(this).index();
    }

    @Post("/cursos")
    public void cadastrar(final Curso curso) {
        List<Curso> cursoBuscado = cursoDAO.SelecionaPorCodigo(curso.getCodigo(), curso.getCurso());

        if (!cursoBuscado.isEmpty()) {
            validator.add(new ValidationMessage("Já existe um Curso cadastrado com esse código e nome", "curso.curso"));
            result.include("operacao", "Cadastro");
        }

        this.validator.validate(curso);

        validator.onErrorUsePageOf(this).create();

        this.cursoDAO.create(curso);

        result.include("success", "cadastrado");

        this.result.redirectTo(CursoController.class).index();
    }

    @Put("/cursos")
    public void altera(Curso curso) {
        List<Curso> cursoBuscado = cursoDAO.SelecionaPorCodigo(curso.getCodigo(), curso.getCurso());

        if (!cursoBuscado.isEmpty()) {
            for (Curso curso1 : cursoBuscado) {
                if (curso1.getId() != curso.getId()) {
                    validator.add(new ValidationMessage("Já existe um Curso cadastrado com esse código e nome", "curso.curso"));
                    result.include("operacao", "Edição");
                }
            }
        }

        this.validator.validate(curso);

        validator.onErrorUsePageOf(this).edit(curso.getId());

        this.cursoDAO.update(curso);

        result.include("success", "alterado");

        this.result.redirectTo(this).index();
    }
}