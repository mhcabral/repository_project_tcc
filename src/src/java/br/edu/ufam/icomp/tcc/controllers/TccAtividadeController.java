/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author andre
 */

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccAtividadeController {
    private final Result result;
    private final TccAtividadeDAO tccAtividadeDAO;
    private final Validator validator;
    private SessionData sessionData;
    private PeriodoLetivoDAO periodoLetivoDAO;
    
    public TccAtividadeController (Result result,TccAtividadeDAO tccAtividadeDAO, Validator validator,
    PeriodoLetivoDAO periodoLetivoDAO, SessionData sessionData){
        this.result = result;
        this.tccAtividadeDAO = tccAtividadeDAO;
        this.validator = validator;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.sessionData = sessionData;
    }


    @Get("/tccAtividade/{idperiodo}/index")
    public void index(Long idperiodo) {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        System.out.println("Periodo: "+idperiodo);
        if (idperiodo > 0)
            periodoAtual = periodoLetivoDAO.findById(idperiodo);
        
        List<TccAtividade> tccAtividade = this.tccAtividadeDAO.findByPeriodo(periodoAtual.getId());
        List<PeriodoLetivo> periodosLetivos = periodoLetivoDAO.listLetivoAnterior();
        periodosLetivos.add(0, sessionData.getLetivoAtual());
        
        this.result.include("tccAtividadeList", tccAtividade);
        this.result.include("periodoLetivoList", periodosLetivos);
        this.result.include("idPeriodo", idperiodo);
    }
    
    @Get("tccAtividade/create")
    public void create() {
        ArrayList<String> listopt = new ArrayList<String>();
        listopt.add("Aluno");
        listopt.add("Orientador");
        listopt.add("Coordenador");
        listopt.add("Aluno e Orientador");
        listopt.add("Todos");
        
        result.include("optList", listopt);

        result.include("operacao", "Cadastro");
    }
    
    @Get("tccAtividade/{id}/edit")
    public TccAtividade edit(Long id) {
        TccAtividade tccAtividade = tccAtividadeDAO.findById(id);
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();

        if (tccAtividade == null) {
            this.validator.add(new ValidationMessage("Desculpe! A Atividade não foi encontrada.", "tccAtividade.id"));
        }
        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(periodoAtual.getId());
        
        List<String> listopt = new ArrayList<>();
        listopt.add("Aluno");
        listopt.add("Orientador");
        listopt.add("Coordenador");
        listopt.add("Aluno e Orientador");
        listopt.add("Todos");
        
        result.include("operacao", "Edição");
        result.include("optList", listopt);

        return tccAtividade;
    }
    
    @Get("/tccAtividade/{id}/show")
    public TccAtividade show(Long id) {
        TccAtividade tccAtividade = this.tccAtividadeDAO.findById(id);

        if (tccAtividade == null) {
            this.validator.add(new ValidationMessage("Desculpe! A atividade não foi encontrada.", "tccAtividade.id"));
        }

        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(0L);

        return tccAtividade;
    }
    
    @Get("/tccAtividade/{id}/remove")
    public void remove(Long id) {
        TccAtividade tccAtividade = this.tccAtividadeDAO.findById(id);

        if (tccAtividade == null) {
            this.validator.add(new ValidationMessage("Desculpe! A atividade não foi encontrada.", "tccAtividade.id"));
        }

        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(0L);

        this.tccAtividadeDAO.delete(tccAtividade);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index(0L);
    }
    
}
