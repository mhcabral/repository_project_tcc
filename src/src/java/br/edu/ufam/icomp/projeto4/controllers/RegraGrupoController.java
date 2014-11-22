/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.GrupoComparator;
import br.edu.ufam.icomp.projeto4.RegraGrupoInfo;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.GrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraGrupoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Thiago Santos
 */
@Permission(Perfil.COORDENADOR)
@Resource
public class RegraGrupoController {

    private final RegraGrupoDAO regraGrupoDAO;
    private final Result result;
    private final Validator validator;
    private GrupoDAO grupoDAO;    
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private Usuario usuario;
    private RegraGrupoInfo regraGrupoInfo;

    public RegraGrupoController(RegraGrupoDAO regraGrupoDAO, Result result, Validator validator,
            GrupoDAO grupoDAO, CoordenadorCursoDAO coordenadorCursoDAO, SessionData session, RegraGrupoInfo regraGrupoInfo) {
        this.regraGrupoDAO = regraGrupoDAO;
        this.result = result;
        this.validator = validator;                
        this.grupoDAO = grupoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.usuario = session.getUsuario();        
        this.regraGrupoInfo = regraGrupoInfo;
    }

    @Get("/regras/grupos")
    public void index() {
        Curso curso = this.coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso();
        
        List<RegraGrupo> regraGrupoList = regraGrupoDAO.findByCurso(curso.getId());

        result.include("regraGrupoList", regraGrupoList);
    }

    @Get("/regras/grupos/{id}/edit")
    public RegraGrupo edit(Long id) {

        RegraGrupo regraGrupo = this.regraGrupoDAO.findById(id);

        if (regraGrupo == null) {
            this.validator.add(new ValidationMessage("Desculpe! A regra de grupo não foi encontrada.", "regraGrupo.id"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).index();

        Curso curso = this.coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso();

        List<Grupo> listGrupos = this.regraGrupoDAO.listGrupos(curso.getId());

        List<Grupo> listGrupoNotIN = this.grupoDAO.listGrupoNotIN(listGrupos);
        listGrupoNotIN.add(regraGrupo.getGrupo());
        
        Collections.sort(listGrupoNotIN, new GrupoComparator());

        result.include("grupoList", listGrupoNotIN);

        result.include("operacao", "Edição");
        result.include("curso", curso);

        return regraGrupo;
    }

    @Get("/regras/grupos/{id}/remove")
    public void remove(Long id) {
        RegraGrupo regraGrupo = this.regraGrupoDAO.findById(id);

        if (regraGrupo == null) {
            this.validator.add(new ValidationMessage("Desculpe! A regra de grupo não foi encontrada.", "regraGrupo.id"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).index();

        this.regraGrupoDAO.delete(regraGrupo);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }

    @Get("/regras/grupos/create")
    public void create() {
        Curso curso = this.coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso();

        List<Grupo> listGrupos = this.regraGrupoDAO.listGrupos(curso.getId());

        if (listGrupos.isEmpty()) {
            result.include("grupoList", this.grupoDAO.findAll());
        } else {
            result.include("grupoList", this.grupoDAO.listGrupoNotIN(listGrupos));
        }

        result.include("operacao", "Cadastro");

        result.include("curso", curso);
    }

    @Get("/regras/grupos/{id}/show")
    public RegraGrupo show(Long id) {
        RegraGrupo regraGrupo = this.regraGrupoDAO.findById(id);

        if (regraGrupo == null) {
            this.validator.add(new ValidationMessage("Desculpe! A regra de grupo não foi encontrada.", "regraGrupo.id"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).index();

        return regraGrupo;
    }

    @Put("/regras/grupos")
    public void altera(RegraGrupo regraGrupo) {
        regraGrupo.setCurso(this.coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());

        if (regraGrupo.getGrupo().getId() == null) {
            this.validator.add(new ValidationMessage("Desculpe! Selecione um grupo para a regra.", "regraGrupo.grupo.id"));
        }

        if (regraGrupo.getMaximoHoras() == null || regraGrupo.getMaximoHoras() <= 0) {
            this.validator.add(new ValidationMessage("Desculpe! O número de horas não pode ultrapassar o máximo do curso.", "regraGrupo.maximoHoras"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).edit(regraGrupo.getId());

        RegraGrupo regraGrupoEncontrado = this.regraGrupoDAO.findByCursoAndGrupo(regraGrupo.getCurso(), regraGrupo.getGrupo());

        if (regraGrupoEncontrado != null && regraGrupoEncontrado.getId() != regraGrupo.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe uma regra de grupo já cadastrada.", "regraGrupo"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).edit(regraGrupo.getId());

        this.regraGrupoDAO.update(regraGrupo);

        this.result.include("success", "alterada");

        this.result.redirectTo(RegraGrupoController.class).index();
    }

    @Post("/regras/grupos")
    public void cadastrar(final RegraGrupo regraGrupo) {
        regraGrupo.setCurso(this.coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());

        if (regraGrupo.getGrupo().getId() == null) {
            this.validator.add(new ValidationMessage("Desculpe! Selecione um grupo para a regra.", "regraGrupo.grupo.id"));
        }

        if (regraGrupo.getMaximoHoras() == null || regraGrupo.getMaximoHoras() <= 0) {
            this.validator.add(new ValidationMessage("Desculpe! O número de horas não pode ultrapassar o máximo do curso.", "regraGrupo.maximoHoras"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).create();

        RegraGrupo regraGrupoEncontrado = this.regraGrupoDAO.findByCursoAndGrupo(regraGrupo.getCurso(), regraGrupo.getGrupo());

        if (regraGrupoEncontrado != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe uma regra de grupo já cadastrada.", "regraGrupo.maximoHoras"));
        }

        this.validator.onErrorRedirectTo(RegraGrupoController.class).create();

        this.regraGrupoDAO.create(regraGrupo);

        result.include("success", "cadastrada");

        this.result.redirectTo(RegraGrupoController.class).index();
    }
    
    @Get("/regras/grupos/{idRegraGrupo}/atividades")
    public void exibirAtividades(Long idRegraGrupo){
        RegraGrupo regraGrupo = this.regraGrupoDAO.findById(idRegraGrupo);
        
        this.regraGrupoInfo.setRegraGrupo(regraGrupo);                
        
        this.result.redirectTo(RegraController.class).index();
    }
}