package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.MonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoInsMonDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import br.edu.ufam.icomp.projeto4.model.PeriodoInsMon;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Leonardo
 */
@Resource
public class PeriodoInsMonController {

    private final Result result;
    private final Validator validator;
    private final PeriodoInsMonDAO periodoDAO;
    private final MonitoriaDAO monitoriaDAO;

    public PeriodoInsMonController(MonitoriaDAO monitoriaDAO, PeriodoInsMonDAO periodoDAO, Result result, Validator validator) {
        this.result = result;
        this.validator = validator;
        this.periodoDAO = periodoDAO;
        this.monitoriaDAO = monitoriaDAO;
    }

    @Get("/periodosIns")
    @Permission(Perfil.ROOT)
    public List<PeriodoInsMon> index() {
        return periodoDAO.findAll();
    }

    @Get("/periodosIns/create")
    @Permission(Perfil.ROOT)
    public PeriodoInsMon create() {
        result.include("operacao", "Cadastro");
        return new PeriodoInsMon();
    }

//    @Get("/periodosIns/{id}/edit")
//    @Permission(Perfil.ROOT)
//    public void avaliaEdicao(Long id) throws ParseException {
//        PeriodoInsMon periodoTmp = periodoDAO.findById(id);
//        if (periodoTmp.validaData().equals("depois")) {
//            this.result.redirectTo(PeriodoInsMonController.class).index();
//        }
//
//    }

    @Get("/periodosIns/{id}/edit")
    @Permission(Perfil.ROOT)
    public PeriodoInsMon edit(Long id) {
        result.include("operacao", "Edição");
        return this.periodoDAO.findById(id);
    }

    @Get("/periodosIns/{id}/show")
    @Permission(Perfil.ROOT)
    public PeriodoInsMon show(Long id) {
        return periodoDAO.findById(id);
    }

    @Post("/periodosIns")
    public void cadastrar(PeriodoInsMon periodoInsMon) {
        if (periodoDAO.exists(periodoInsMon)) {
            validator.add(new ValidationMessage("Já existe um período de inscrição com estas características.", "periodo.periodo"));
            result.include("operacao", "Cadastro");
        }
        if (periodoDAO.checkConflict(periodoInsMon)) {
            validator.add(new ValidationMessage("O intervalo de datas conflita com outro período de inscrição ativo cadastrado", "periodo.periodo"));
            result.include("operacao", "Cadastro");
        }
        this.validator.validate(periodoInsMon);
        validator.onErrorUsePageOf(this).create();
        this.periodoDAO.create(periodoInsMon);
        result.include("success", "cadastrado");
        this.result.redirectTo(PeriodoInsMonController.class).index();
    }

    @Put("/periodosIns")
    public void altera(PeriodoInsMon periodoInsMon) {
        if (periodoDAO.exists(periodoInsMon)) {
            validator.add(new ValidationMessage("Já existe um período de inscrição com estas características.", "periodo.periodo"));
            result.include("operacao", "Edição");
        }
        if (periodoDAO.checkConflictEdiBoolean(periodoInsMon)) {
            validator.add(new ValidationMessage("O intervalo de datas conflita com outro período de inscrição ativo cadastrado", "periodo.periodo"));
            result.include("operacao", "Edição");
        }
        this.validator.validate(periodoInsMon);
        validator.onErrorUsePageOf(this).edit(periodoInsMon.getId());
        this.periodoDAO.update(periodoInsMon);
        result.include("success", "alterado");
        this.result.redirectTo(this).index();
    }

    @Get("/periodosIns/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        PeriodoInsMon periodo = this.periodoDAO.findById(id);
        if (periodo == null) {
            validator.add(new ValidationMessage("Desculpe! Período não encontrado", "periodo.id"));
        }
        this.validator.validate(periodo);
        validator.onErrorUsePageOf(this).show(id);

        List<Monitoria> monitorias;
        monitorias = this.monitoriaDAO.listByIdPeriodo(id);

        for (int i = 0; i < monitorias.size(); i++) {
            monitoriaDAO.delete(monitorias.get(i));
        }

        this.periodoDAO.delete(periodo);
        this.result.include("success", "removido");
        this.result.redirectTo(this).index();
    }
}
