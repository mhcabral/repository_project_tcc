/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.EstagioDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Estagio;
import br.edu.ufam.icomp.projeto4.model.Status;
import java.io.File;
import java.util.List;

/**
 *
 * @author Thiago Santos
 */
@Permission(Perfil.COORDENADOR)
@Resource
public class AnaliseInscricaoEstagioController {

    private final Result result;
    private final Validator validator;
    private final EstagioDAO estagioDAO;    
    private Anexo pastaDeAnexos;    
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private SessionData session;

    public AnaliseInscricaoEstagioController(EstagioDAO estagioDAO, Result result, Validator validator, Anexo pastaDeAnexos, CoordenadorCursoDAO coordenadorCursoDAO, SessionData session) {
        this.result = result;
        this.validator = validator;
        this.estagioDAO = estagioDAO;       
        this.pastaDeAnexos = pastaDeAnexos;
        this.coordenadorCursoDAO = coordenadorCursoDAO;      
        this.session = session;
    }

    @Get("/analise/estagios")
    @Permission(Perfil.COORDENADOR)
    public List<Estagio> index() {
        CoordenadorCurso coord = coordenadorCursoDAO.findByUsuario(session.getUsuario().getId());
       
       return this.estagioDAO.findByStatusAndCursoAndPeriodo(Status.PENDENTE, coord.getCurso().getId(), session.getLetivoAtual().getId());
    }   

    @Get("/analise/estagios/{id}/show")
    @Permission(Perfil.COORDENADOR)
    public Estagio show(Long id) {
        return this.estagioDAO.findById(id);
    }      

    @Put("/analise/estagios/avaliacao")
    public void avaliacao(Estagio estagio, String analise) {
        
        CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(session.getUsuario().getId());                
        
        Estagio estagioEncontrado = this.estagioDAO.findById(estagio.getId());
        
        if(estagioEncontrado == null){
            this.validator.add(new ValidationMessage("Desculpe! Não existe um estágio com código informado.", "estagio.id"));
        }
        
        this.validator.onErrorRedirectTo(AnaliseInscricaoEstagioController.class).show(estagio.getId());
        
        if(analise.equals("aprovado")){
            estagioEncontrado.setStatus(Status.EMANDAMENTO);
        }else if(analise.equals("reprovado")){
            estagioEncontrado.setStatus(Status.REPROVADA);
        }else{
            this.validator.add(new ValidationMessage("Desculpe! Não é possível fazer esta análise.", "estagio.id"));
        }
        
        this.validator.onErrorRedirectTo(AnaliseInscricaoEstagioController.class).show(estagio.getId());

        this.estagioDAO.update(estagioEncontrado);

        result.include("success", analise);

        this.result.redirectTo(this).index();
    }

    @Get("analise/estagios/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }
}