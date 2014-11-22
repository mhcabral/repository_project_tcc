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
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import java.util.List;

/**
 *
 * @author Bruna
 */

@Resource
public class GrupoController {
    private final Result result;
    private final Validator validator;
    private final GrupoDAO grupoDAO;
    private AtividadeDAO atividadeDAO;
    
    public GrupoController(Result result, Validator validator, GrupoDAO grupoDAO, AtividadeDAO atividadeDAO){
        this.result = result;
        this.validator = validator;
        this.grupoDAO = grupoDAO;
        this.atividadeDAO = atividadeDAO;
    }
    
    @Permission(Perfil.ROOT)
    @Get("/grupos")
    public List<Grupo> index() {
        List<Grupo> grupos = this.grupoDAO.findAll();

        return grupos;
    }
    
    @Permission(Perfil.ROOT)
    @Get("/grupos/create")
    public Grupo create() {
        result.include("operacao", "Cadastro");
        return new Grupo();
    }
    
    @Permission(Perfil.ROOT)
    @Get("/grupos/{id}/edit")
    public Grupo edit(Long id) {
        result.include("operacao", "Edição");
        
        if (grupoDAO.findById(id) == null) {
            this.result.redirectTo(GrupoController.class).index();
        }

        return this.grupoDAO.findById(id);
    }

    @Permission(Perfil.ROOT)
    @Get("/grupos/{id}/show")
    public Grupo show(Long id) {
        if (grupoDAO.findById(id) == null) {
            this.result.redirectTo(GrupoController.class).index();
        }
        
        return this.grupoDAO.findById(id);
    }

    @Permission(Perfil.ROOT)
    @Get("/grupos/{id}/remove")
    public void remove(Long id) {
        Grupo grupo = this.grupoDAO.findById(id);
        
        if(grupoDAO.findById(id) == null){
            validator.add(new ValidationMessage("Desculpe! Grupo não encontrado", "grupo.codigo"));
        } else if(!atividadeDAO.findByGroupId(id).isEmpty()){
            validator.add(new ValidationMessage("Desculpe! Grupo não pôde ser removido, existem atividades associadas a ele", "grupo.codigo"));
            result.include("grupo", grupo);
        }
        
        this.validator.validate(grupo);

        validator.onErrorUsePageOf(this).show(id);
        
        this.grupoDAO.delete(grupo);
        
        this.result.include("success", "removido");

        this.result.redirectTo(this).index();
    }
    
    @Post("/grupos")
    public void cadastrar(final Grupo grupo) {
        List<Grupo> grupoBuscado = grupoDAO.SelecionaPorCodigo(grupo.getCodigo(), grupo.getNome());

        if (!grupoBuscado.isEmpty()) {
            validator.add(new ValidationMessage("Já existe um Grupo cadastrado com esse código e nome", "grupo.nome"));
            result.include("operacao", "Cadastro");
        }

        this.validator.validate(grupo);

        validator.onErrorUsePageOf(this).create();

        this.grupoDAO.create(grupo);

        result.include("success", "cadastrado");

        this.result.redirectTo(GrupoController.class).index();
    }

    @Put("/grupos")
    public void altera(Grupo grupo) {
        List<Grupo> grupoBuscado = grupoDAO.SelecionaPorCodigo(grupo.getCodigo(), grupo.getNome());

        if(grupoDAO.findById(grupo.getId()) == null){
            validator.add(new ValidationMessage("Desculpe! Grupo não encontrado", "grupo.codigo"));
        } else if (!grupoBuscado.isEmpty()) {
            for (Grupo grupo1 : grupoBuscado) {
                if (grupo1.getId() != grupo.getId()) {
                    validator.add(new ValidationMessage("Já existe um Grupo cadastrado com esse código e nome", "grupo.nome"));
                    result.include("operacao", "Edição");
                }
            }
        }
        
        this.validator.validate(grupo);

        validator.onErrorUsePageOf(this).edit(grupo.getId());

        this.grupoDAO.update(grupo);

        result.include("success", "alterado");

        this.result.redirectTo(this).index();
    }
}
