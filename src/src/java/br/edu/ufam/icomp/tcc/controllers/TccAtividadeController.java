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
    
    @Get("tccAtividade/{id}/edit")
    public TccAtividade edit(Long id) {
        TccAtividade tccAtividade = tccAtividadeDAO.findById(id);

        if (tccAtividade == null) {
            this.validator.add(new ValidationMessage("Desculpe! A Atividade não foi encontrada.", "tccAtividade.id"));
        }
        //Tem que mudar isso porque passei uma constante e o certo seria o periodo corrente
        this.validator.onErrorRedirectTo(TccAtividadeController.class).index(1L);

        result.include("operacao", "Edição");

        return tccAtividade;
    }
    
}
