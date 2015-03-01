package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Resource
public class PeriodoLetivoController {

    private final Result result;
    private final Validator validator;
    private final PeriodoLetivoDAO periodoLetivoDAO;

    public PeriodoLetivoController(PeriodoLetivoDAO periodoLetivoDAO, Result result, Validator validator) {
        this.result = result;
        this.validator = validator;
        this.periodoLetivoDAO = periodoLetivoDAO;
    }

    @Get("/periodosLetivo")
    @Permission(Perfil.ROOT)
    public List<PeriodoLetivo> index() {
        return periodoLetivoDAO.findAllOrderByCodigo();
    }

    @Get("/periodosLetivo/create")
    @Permission(Perfil.ROOT)
    public PeriodoLetivo create() {
        result.include("operacao", "Cadastro");
        return new PeriodoLetivo();
    }

    @Get("/periodosLetivo/{id}/edit")
    @Permission(Perfil.ROOT)
    public PeriodoLetivo edit(Long id) {
        result.include("operacao", "Edição");
        return this.periodoLetivoDAO.findById(id);
    }

    @Get("/periodosLetivo/{id}/show")
    @Permission(Perfil.ROOT)
    public PeriodoLetivo show(Long id) {
        return periodoLetivoDAO.findById(id);
    }

    @Post("/periodosLetivo")
    @Permission(Perfil.ROOT)
    public void cadastrar(PeriodoLetivo periodoLetivo) {
        if (!periodoLetivoDAO.exists(periodoLetivo).isEmpty()) {
            validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim de período letivo.", "periodo.periodo"));
        }
        if (!periodoLetivoDAO.existsMatricula(periodoLetivo).isEmpty()) {
            validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim período de matrícula.", "periodo.periodo"));
        }
        if (!periodoLetivoDAO.existsEstagio(periodoLetivo).isEmpty()) {
            validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim período de estágio.", "periodo.periodo"));
        }

        if (!periodoLetivoDAO.checkLetivo(periodoLetivo).isEmpty()) {
            validator.add(new ValidationMessage("O intervalo de datas conflita com outro período letivo cadastrado.", "periodo.periodo"));
        }
        if(periodoLetivoDAO.findByCodigo(periodoLetivo.getCodigo()) != null){
            validator.add(new ValidationMessage("Já existe um período com este código", "periodo.periodo"));
        }
        this.validator.validate(periodoLetivo);
        validator.onErrorRedirectTo(this).create();
        this.periodoLetivoDAO.create(periodoLetivo);
        result.include("success", "cadastrado");
        this.result.redirectTo(PeriodoLetivoController.class).index();
    }

    @Put("/periodosLetivo")
    @Permission(Perfil.ROOT)
    public void altera(PeriodoLetivo periodoLetivo) {
        List<PeriodoLetivo> periodos = periodoLetivoDAO.exists(periodoLetivo);
        if (!periodos.isEmpty()) {
            for (PeriodoLetivo periodo : periodos) {
                if (periodo.getId() != periodoLetivo.getId()) {
                    validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim de período letivo.", "periodo.periodo"));
                }
            }
        }
        periodos = periodoLetivoDAO.existsMatricula(periodoLetivo);
        if (!periodos.isEmpty()) {
            for (PeriodoLetivo periodo : periodos) {
                if (periodo.getId() != periodoLetivo.getId()) {
                    validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim período de matrícula.", "periodo.periodo"));
                }
            }
        }
        
        periodos = periodoLetivoDAO.existsEstagio(periodoLetivo);
        if (!periodos.isEmpty()) {
            for (PeriodoLetivo periodo : periodos) {
                if (periodo.getId() != periodoLetivo.getId()) {
                    validator.add(new ValidationMessage("Já existe um período com estas datas de início e fim período de estágio.", "periodo.periodo"));
                }
            }
        }        
        
        Date data = new Date();
        Date hoje = new Date(data.getYear(), data.getMonth(), data.getDate());

        if (!periodoLetivo.getDtInicio().after(hoje) && !periodoLetivo.getDtInicio().equals(hoje)) {
            validator.add(new ValidationMessage("Período não pode ser editado", "periodo.id"));
        }
        periodos = periodoLetivoDAO.checkLetivo(periodoLetivo);
        if (!periodos.isEmpty()) {
            for (PeriodoLetivo periodo : periodos) {
                if (periodo.getId() != periodoLetivo.getId()) {
                    validator.add(new ValidationMessage("O intervalo de datas conflita com outro período cadastrado", "periodo.periodo"));
                }
            }
        }
        PeriodoLetivo periodo = periodoLetivoDAO.findByCodigo(periodoLetivo.getCodigo());
        if(periodo != null && periodo.getId() != periodoLetivo.getId()){
            validator.add(new ValidationMessage("Já existe um período com este código", "periodo.periodo"));
        }
        this.validator.validate(periodoLetivo);
        validator.onErrorRedirectTo(this).edit(periodoLetivo.getId());
        this.periodoLetivoDAO.update(periodoLetivo);
        result.include("success", "alterado");
        this.result.redirectTo(this).index();
    }

    @Get("/periodosLetivo/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        PeriodoLetivo periodo = this.periodoLetivoDAO.findById(id);
        Date date = new Date();
        if (periodo == null) {
            validator.add(new ValidationMessage("Desculpe! Período não encontrado", "periodo.id"));
        }
        if (periodo.getDtInicio().before(date)) {
            validator.add(new ValidationMessage("Período não pode ser removido", "periodo.id"));
        }
        this.validator.validate(periodo);
        result.include("periodoLetivo", periodo);
        validator.onErrorRedirectTo(this).index();

        this.periodoLetivoDAO.delete(periodo);
        this.result.include("success", "removido");
        this.result.redirectTo(this).index();
    }
}