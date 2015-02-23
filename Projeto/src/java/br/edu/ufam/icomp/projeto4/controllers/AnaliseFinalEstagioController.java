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
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensalEstagio;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Status;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Permission(Perfil.COORDENADOR)
@Resource
public class AnaliseFinalEstagioController {

    private final Result result;
    private final Validator validator;
    private final EstagioDAO estagioDAO;
    private Anexo pastaDeAnexos;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private SessionData session;

    public AnaliseFinalEstagioController(EstagioDAO estagioDAO, Result result, Validator validator,
            Anexo pastaDeAnexos, CoordenadorCursoDAO coordenadorCursoDAO, SessionData session) {
        this.result = result;
        this.validator = validator;
        this.estagioDAO = estagioDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.session = session;
    }

    @Get("/analise/final/estagios")
    public void index() {
        CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(session.getUsuario().getId());

        result.include("estagioList", estagioDAO.findByStatusAndCursoAndPeriodo(Status.EMANDAMENTO, coordenadorCurso.getCurso().getId(), session.getLetivoAtual().getId()));
    }

    @Get("/analise/final/estagios/{id}/show")
    public Estagio show(Long id) {
        PeriodoLetivo letivo = session.getLetivoAtual();
        Estagio estagio = this.estagioDAO.findById(id);

        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        int mes_inicio = letivo.getDtInicio().getMonth();
        int mes_fim = letivo.getDtFim().getMonth();

        List<FrequenciaMensalEstagio> frequencias = estagio.getFrequenciasMensais();
        List<FrequenciaMensalEstagio> resultado = new ArrayList<FrequenciaMensalEstagio>();

        System.out.println("++++++++++" + mes_inicio + "  " + mes_fim);

        for (int i = mes_inicio; i <= mes_fim; i++) {
            FrequenciaMensalEstagio freq = findByMesEstagio(i, frequencias);
            if (freq == null) {
                freq = new FrequenciaMensalEstagio();
                freq.setMes(i);
                freq.setStatus(Status.VAZIA);
            } else {
                if (freq.getStatus() == Status.NOVA) {
                    freq.setStatus(Status.PENDENTE);
                }
            }
            resultado.add(freq);
        }

        this.result.include("freqAnalisadas", resultado);
        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());

        return estagio;
    }

    @Put("/analise/final/estagios/avaliacao")
    public void avaliacao(Estagio estagio, String analise) {

        CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(session.getUsuario().getId());

        Estagio estagioEncontrado = this.estagioDAO.findById(estagio.getId());

        if (estagioEncontrado == null) {
            this.validator.add(new ValidationMessage("Desculpe! Não existe um estágio com código informado.", "estagio.id"));
        }

        this.validator.onErrorRedirectTo(this).show(estagio.getId());

        if (analise.equals("aprovado")) {
            estagioEncontrado.setStatus(Status.CONCLUIDO);
        } else if (analise.equals("reprovado")) {
            estagioEncontrado.setStatus(Status.REPROVADO);
        } else {
            this.validator.add(new ValidationMessage("Desculpe! Não é possível fazer esta análise.", "estagio.id"));
        }

        this.validator.onErrorRedirectTo(this).show(estagio.getId());

        this.estagioDAO.update(estagioEncontrado);

        result.include("success", analise);

        this.result.redirectTo(this).index();
    }

    @Get("analise/final/estagios/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }

    private FrequenciaMensalEstagio findByMesEstagio(int mes, List<FrequenciaMensalEstagio> frequencias) {
        for (FrequenciaMensalEstagio frequenciaMensal : frequencias) {
            if (frequenciaMensal.getMes() == mes) {
                return frequenciaMensal;
            }
        }
        return null;
    }
}