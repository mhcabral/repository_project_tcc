package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Estagio;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensal;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensalEstagio;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Status;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Janderson
 */
@Resource
public class AnaliseFinalController {

    private SessionData session;
    private Result result;
    private Validator validator;
    private InscricaoMonitoriaDAO inscricaoDAO;
    private CursoDAO cursoDAO;
    private DisciplinaDAO disciplinaDAO;
    private ProfessorDAO professorDAO;
    private Anexo pastaDeAnexos;

    public AnaliseFinalController(SessionData session, Result result, Validator validator, InscricaoMonitoriaDAO inscricaoDAO, CursoDAO cursoDAO, DisciplinaDAO disciplinaDAO, ProfessorDAO professorDAO, Anexo pastaDeAnexos) {
        this.session = session;
        this.result = result;
        this.validator = validator;
        this.inscricaoDAO = inscricaoDAO;
        this.cursoDAO = cursoDAO;
        this.disciplinaDAO = disciplinaDAO;
        this.professorDAO = professorDAO;
        this.pastaDeAnexos = pastaDeAnexos;
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseFinal")
    public List<InscricaoMonitoria> index(Long idCurso, Long idDisciplina, Long idProfessor) {

        result.include("cursoList", cursoDAO.findAll());
        result.include("disciplinaList", disciplinaDAO.findAll());
        result.include("professorList", professorDAO.findAll());


        if (idCurso == null) {

            idCurso = 0L;

        }

        if (idDisciplina == null) {

            idDisciplina = 0L;

        }

        if (idProfessor == null) {

            idProfessor = 0L;

        }

        result.include("idCurso", idCurso);
        result.include("idDisciplina", idDisciplina);
        result.include("idProfessor", idProfessor);


        List<InscricaoMonitoria> listInscricao = inscricaoDAO.search(idCurso, idDisciplina, idProfessor);

        if ((idCurso > 0) && (idDisciplina > 0) && (idProfessor > 0)) {

            if (!listInscricao.isEmpty()) {

                InscricaoMonitoria umaInscricao = listInscricao.get(0);
                Monitoria monitoria = umaInscricao.getMonitoria();
                result.include("monitoria", monitoria);
            }

        }

        return listInscricao;

    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseFinal/{idCurso}/{idDisciplina}/{idProfessor}/search")
    public void search(Long idCurso, Long idDisciplina, Long idProfessor) {

        this.result.forwardTo(this).index(idCurso, idDisciplina, idProfessor);

    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseFinal/{id}/show")
    public InscricaoMonitoria show(Long id) {

        PeriodoLetivo letivo = session.getLetivoAtual();
        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        int mes_inicio = letivo.getDtInicio().getMonth();
        int mes_fim = letivo.getDtFim().getMonth();

        List<FrequenciaMensal> frequencias = inscricao.getFrequenciasMensais();
        List<FrequenciaMensal> resultado = new ArrayList<FrequenciaMensal>();

        System.out.println("++++++++++" + mes_inicio + "  " + mes_fim);

        for (int i = mes_inicio; i <= mes_fim; i++) {
            FrequenciaMensal freq = findByMesInscricao(i, frequencias);
            if (freq == null) {
                freq = new FrequenciaMensal();
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

        return inscricaoDAO.findById(id);
    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseFinal/{id}/defere")
    public void defere(Long id) {

        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        if (inscricao == null) {

            validator.add(new ValidationMessage("Inscrição não encontrada!", "inscricao.id"));

        }

        validator.validate(inscricao);
        validator.onErrorForwardTo(this).index(null, null, null);

        inscricao.setStatusAtual(Status.APROVADA);

        inscricaoDAO.update(inscricao);

        this.result.include("success", "aprovada");

        this.result.redirectTo(this).index(null, null, null);


    }

    @Permission(Perfil.COORDENADORACAD)
    @Get("/analiseFinal/{id}/indefere")
    public void indefere(Long id) {

        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        if (inscricao == null) {

            validator.add(new ValidationMessage("Inscrição não encontrada!", "inscricao.id"));

        }

        validator.validate(inscricao);
        validator.onErrorForwardTo(this).index(null, null, null);

        inscricao.setStatusAtual(Status.REPROVADA);

        inscricaoDAO.update(inscricao);

        this.result.include("success", "reprovada");

        this.result.redirectTo(this).index(null, null, null);

    }

    @Get("/analiseFinal/download/{anexo}")
    public File download(String anexo) {

        File file = pastaDeAnexos.mostrar(anexo);

        return file;
    }
    
    private FrequenciaMensal findByMesInscricao(int mes, List<FrequenciaMensal> frequencias) {
        for (FrequenciaMensal frequenciaMensal : frequencias) {
            if (frequenciaMensal.getMes() == mes) {
                return frequenciaMensal;
            }
        }
        return null;
    }
}
