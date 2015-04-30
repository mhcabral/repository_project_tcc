/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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
        List<PeriodoLetivo> periodos = periodoLetivoDAO.findAll();
        Long maiorPeriodo = 0L;
        for(int i=0;i<periodos.size();i++) {
            if (maiorPeriodo < periodos.get(i).getId()) {
                maiorPeriodo = periodos.get(i).getId();
            }
        }
        
        if (idperiodo > 0) {
            maiorPeriodo = idperiodo;
        } else {
            idperiodo = maiorPeriodo;
        }
        
        List<TccAtividade> tccAtividade = this.tccAtividadeDAO.findByPeriodo(maiorPeriodo);
        
        this.result.include("tccAtividadeList", tccAtividade);
        this.result.include("periodoLetivoList", periodos);
        this.result.include("idPeriodo", idperiodo);
    }
    
    @Get("/tccAtividade/{id}/create")
    public void create(Long id) {
        List<TccAtividade> tccAtividades = tccAtividadeDAO.findByPeriodo(id);
        
        if (tccAtividades.size() > 0) {
            this.validator.add(new ValidationMessage("Desculpe! Já Existem Atividades Cadastradas.", ""));
        }
        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(id);
        
        PeriodoLetivo periodo = this.periodoLetivoDAO.findById(id);
        tccAtividadeDAO.insertInPeriodo(periodo);
        
        this.result.redirectTo(TccAtividadeController.class).index(id);
    }
    
    @Get("/tccAtividade/{id}/edit")
    public TccAtividade edit(Long id) {
        this.result.include("operacao", "Edição");
        
        TccAtividade tccAtividade = tccAtividadeDAO.findById(id);
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        
        if (tccAtividade == null) {
            this.validator.add(new ValidationMessage("Desculpe! A Atividade não foi encontrada.", "tccAtividade.id"));
        }
        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(periodoAtual.getId());
        
        List<String> listResponsavel = new ArrayList();        
        
        listResponsavel.add("Aluno");
        listResponsavel.add("Orientador");
        listResponsavel.add("Coordenado");
        listResponsavel.add("Aluno e Orientador");
        listResponsavel.add("Todos");
            
        this.result.include("responsavelList", listResponsavel);

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
    
    @Post("/tccAtividade/{id}/index")
    public void cadastrar(TccAtividade tccAtividade) {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        
        //tccAtividade.setEstado("Aberto");
        //tccAtividade.setPeriodo(periodoAtual);
        
        this.tccAtividadeDAO.create(tccAtividade);

        result.include("success", "cadastrada");

        this.result.redirectTo(TccAtividadeController.class).index(periodoAtual.getId());
    }
    
    @Put("/tccAtividade/{id}/index")
    public void altera(final TccAtividade tccAtividade) {
        TccAtividade tccAtividadeEncontrado = this.tccAtividadeDAO.findById(tccAtividade.getId());
        
        System.out.println(tccAtividade.getDatalimite());
        
        if (tccAtividadeEncontrado == null) {
            validator.add(new ValidationMessage("Desculpe! Atividade não encontrada.", "tccAtividade.id"));
        }        
        
        this.validator.onErrorRedirectTo(TccAtividadeController.class).edit(tccAtividade.getId());
        
        if (tccAtividade.getResponsavel() == null) {
            this.validator.add(new ValidationMessage("Desculpe! Responsável invalido.", "formAtividade.responsavel"));
        }

        this.validator.onErrorRedirectTo(TccAtividadeController.class).edit(tccAtividade.getId());
        
        this.tccAtividadeDAO.update(tccAtividade);

        result.include("success", "alterada");

        this.result.redirectTo(TccAtividadeController.class).index(0L);
    }
    
}
