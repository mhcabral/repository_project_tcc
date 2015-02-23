package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoCadMonDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.PeriodoCadMon;
import java.util.List;
import javax.persistence.EntityTransaction;

@Resource
public class PeriodoCadMonController {

    private Result result;
    private Validator validator;
    private PeriodoCadMonDAO periodoDAO;    
    private CursoDAO cursoDAO;

    public PeriodoCadMonController(PeriodoCadMonDAO periodoDAO, CursoDAO cursoDAO, Result result, Validator validator) {
        this.result = result;
        this.validator = validator;
        this.periodoDAO = periodoDAO;
        this.cursoDAO = cursoDAO;
    }

    @Get("/periodos")
    @Permission(Perfil.COORDENADORACAD)
    public List<PeriodoCadMon> index() {
        
        return periodoDAO.findAll();
    }

    @Get("/periodos/create")
    @Permission(Perfil.COORDENADORACAD)
    public PeriodoCadMon create() {
        
        result.include("cursoList", cursoDAO.findAll());
        result.include("operacao", "Cadastro");
        return new PeriodoCadMon();
    }

    @Get("/periodos/{id}/edit")
    @Permission(Perfil.COORDENADORACAD)
    public PeriodoCadMon edit(Long id) {
        
        result.include("cursoList", this.cursoDAO.findAll());
        result.include("operacao", "Edição");

        return this.periodoDAO.findById(id);
    }

    @Get("/periodos/{id}/show")
    @Permission(Perfil.COORDENADORACAD)
    public PeriodoCadMon show(Long id) {
        return periodoDAO.findById(id);
    }

    @Get("/periodos/{id}/remove")
    @Permission(Perfil.COORDENADORACAD)
    public void remove(Long id) {
        PeriodoCadMon periodo = this.periodoDAO.findById(id);

        if (periodo == null) {
            validator.add(new ValidationMessage("Desculpe! Período não encontrado", "periodo.id"));
        }
//        else if (!(periodoDAO.findByCursoId(id).isEmpty() && [tem dependencias?] )) {
//            validator.add(new ValidationMessage("Desculpe! Período não pôde ser removido, existem coisas associadas a ele", "periodo.codigo"));
//            result.include("periodo", periodo);
//        }

        this.validator.validate(periodo);

        validator.onErrorUsePageOf(this).show(id);

        this.periodoDAO.delete(periodo);

        this.result.include("success", "removido");

        this.result.redirectTo(this).index();
    }

    @Post("/periodos")
    public void cadastrar(PeriodoCadMon periodoCadMon) {                
        
        validator.validate(periodoCadMon);
        EntityTransaction ts = periodoDAO.getEntityManager().getTransaction();
        
        ts.begin();                
        
        if (periodoDAO.exists(periodoCadMon)) {
            
            validator.add(new ValidationMessage("Já existe um período cadastrado com estas características.", "periodo.periodo"));
            result.include("operacao", "Cadastro");
            ts.rollback();
            
        }else{
        
            if (periodoDAO.checkConflict(periodoCadMon)) {
                validator.add(new ValidationMessage("O intervalo de datas conflita com outro período ativo cadastrado para este curso.", "periodo.periodo"));
                result.include("operacao", "Cadastro");
                ts.rollback();
            }else{
            
                periodoDAO.create(periodoCadMon);
                ts.commit();
                
            }        
        }           
        
        validator.onErrorForwardTo(this).create();

        result.include("success", "cadastrado");        
        result.redirectTo(this).index();
    }

    @Put("/periodos") 
    public void altera(PeriodoCadMon periodoCadMon) {
               
        validator.validate(periodoCadMon);        

        EntityTransaction ts = periodoDAO.getEntityManager().getTransaction();
        
        ts.begin();                        
        
        if (periodoDAO.exists(periodoCadMon)) {
                validator.add(new ValidationMessage("Já existe um Período cadastrado com essas características", "periodo.periodo"));
                result.include("operacao", "Edição");                
                ts.rollback();
        }else{
        
            if (periodoDAO.checkConflict(periodoCadMon)){
                validator.add(new ValidationMessage("O intervalo de datas conflita com outro período ativo cadastrado para este curso.", "periodo.periodo"));
                result.include("operacao", "Edição");
                ts.rollback();
            }else{
                
                periodoDAO.update(periodoCadMon);
                ts.commit();
                
            }
        }        
        
        validator.onErrorForwardTo(this).edit(periodoCadMon.getId());        

        result.include("success", "alterado");
        result.redirectTo(this).index();
    }
}