package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.FrequenciaDAO;
import br.edu.ufam.icomp.projeto4.dao.FrequenciaMensalDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.SolicitacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Frequencia;
import br.edu.ufam.icomp.projeto4.model.FrequenciaJson;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensal;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Resource
public class FrequenciaController {

    private final Result result;
    private final InscricaoMonitoriaDAO inscricaoDAO;
    private AlunoDAO alunoDAO;
    private SessionData session;
    private Usuario usuario;
    private FrequenciaDAO frequenciaDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private FrequenciaMensalDAO frequenciaMensalDAO;
    private final Validator validator;
    private Notificador notificador;
    private SolicitacaoDAO solicitacaoDAO;

    public FrequenciaController(InscricaoMonitoriaDAO inscricaoDAO, Result result, PeriodoLetivoDAO periodoLetivoDAO, Validator validator,
            AlunoDAO alunoDAO, SessionData session, FrequenciaDAO frequenciaDAO, FrequenciaMensalDAO frequenciaMensalDAO, Notificador notificador,
            SolicitacaoDAO solicitacaoDAO) {
        this.result = result;
        this.inscricaoDAO = inscricaoDAO;
        this.alunoDAO = alunoDAO;
        this.session = session;
        this.usuario = session.getUsuario();
        this.frequenciaDAO = frequenciaDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.frequenciaMensalDAO = frequenciaMensalDAO;
        this.validator = validator;
        this.notificador = notificador;
        this.solicitacaoDAO = solicitacaoDAO;
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/frequencia")
    public void enviaFrequencia() {
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
        result.include("monitoriaList", inscricaoDAO.listByAlunoStatus(aluno.getId(), Status.DEFERIDA));
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/frequencia")
    public void preencheFrequencia(Long id) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        PeriodoLetivo periodoAtual = session.getLetivoAtual();

        if (inscricao == null) {
            this.result.redirectTo(this).enviaFrequencia();
        }

        Date atual = new Date();

        if (inscricao.isBolsista()) {
            if (atual.getDate() < 25) {
                this.result.include("bloqueioFreq", true);
            }
        } else {
            if (periodoAtual.getDtFim().getMonth() != atual.getMonth() || atual.getDate() < 25) {
                this.result.include("bloqueioFreq", true);
            }
        }



        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();

        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());
        this.result.include("inscricao", inscricao);
    }

    @Permission(Perfil.ALUNO)
    @Post("/frequencia/inclui.json")
    public void inclui(Long id, Date start, int title) {
        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);

        Frequencia frequencia = new Frequencia();
        frequencia.setCargaHoraria(title);
        frequencia.setData(start);
        frequencia.setInscricao(inscricao);

        if (frequenciaDAO.findByData(frequencia.getData(), inscricao.getId()) == null && validaData(start)) {
            frequenciaDAO.create(frequencia);
            FrequenciaMensal freqMensal = findByMesInscricao(start.getMonth(), inscricao.getFrequenciasMensais());

            if (freqMensal == null) {
                freqMensal = new FrequenciaMensal();

                freqMensal.setInscricao(inscricao);
                freqMensal.setMes(start.getMonth());
                freqMensal.setStatus(Status.SALVA);
                freqMensal.addFrequencia(frequencia);
                frequenciaMensalDAO.create(freqMensal);
                inscricao.add(freqMensal);
                inscricaoDAO.update(inscricao);
            } else {
                freqMensal.addFrequencia(frequencia);
                frequenciaMensalDAO.update(freqMensal);
            }
        }

        result.redirectTo(this).preencheFrequencia(id);
    }

    //@Permission(Perfil.ALUNO)
    @Get("/frequencia/list.json/{id}")
    public void getFrequencias(Long id) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        if (inscricao == null) {
            this.result.redirectTo(this).enviaFrequencia();
        }

        List<Frequencia> frequencias = frequenciaDAO.listByInscricao(inscricao.getId());

        List<FrequenciaJson> frequenciasJson = new ArrayList<FrequenciaJson>();

        for (Frequencia frequencia : frequencias) {
            FrequenciaJson frequenciaJson = new FrequenciaJson(frequencia);
            frequenciasJson.add(frequenciaJson);
        }

        result.use(Results.json()).withoutRoot().from(frequenciasJson).serialize();
    }

    @Permission(Perfil.ALUNO)
    @Post("/frequencia/exclui.json")
    public void exclui(int idFrequencia, Long id) {
        Frequencia frequencia = frequenciaDAO.findById(idFrequencia);

        InscricaoMonitoria inscricao = inscricaoDAO.findById(frequencia.getInscricao().getId());
        FrequenciaMensal freqMensal = findByMesInscricao(frequencia.getData().getMonth(), inscricao.getFrequenciasMensais());
        freqMensal.removeFrequencia(frequencia);
        frequenciaMensalDAO.update(freqMensal);

        frequenciaDAO.delete(frequencia);
        result.redirectTo(this).preencheFrequencia(id);
    }

    @Permission(Perfil.ALUNO)
    @Get("/frequencia/enviar/{id}")
    public void enviar(Long id) {
        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);
        String mes[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        if (inscricao.isBolsista()) {
            int mes_atual = new Date().getMonth();
            FrequenciaMensal freqMensal = findByMesInscricao(mes_atual, inscricao.getFrequenciasMensais());

            if (freqMensal == null) {
                validator.add(new ValidationMessage("Nenhuma frequência foi preenchida", ""));
            } else {
                if (freqMensal.totalValido()) {
                    freqMensal.setStatus(Status.NOVA);
                    frequenciaMensalDAO.update(freqMensal);
                    result.include("success", "enviada");
                    enviaEmail(inscricao, mes_atual);
                    result.forwardTo(this).enviaFrequencia();
                } else {
                    validator.add(new ValidationMessage("O total de carga horária do mês " + mes[freqMensal.getMes()] + " é inferior a 48 horas", ""));
                }
            }
        } else if (!inscricao.isBolsista()) {
            boolean valida = true;
            PeriodoLetivo letivoAtual = periodoLetivoDAO.findLetivoAtivo();
            int qntd_meses = letivoAtual.getDtFim().getMonth() - letivoAtual.getDtInicio().getMonth() + 1;
            if (qntd_meses == inscricao.getFrequenciasMensais().size()) {
                List<FrequenciaMensal> freqMensais = inscricao.getFrequenciasMensais();
                for (FrequenciaMensal frequenciaMensal : freqMensais) {
                    if (!frequenciaMensal.totalValido()) {
                        valida = false;
                        validator.add(new ValidationMessage("O total de carga horária do mês " + mes[frequenciaMensal.getMes()] + " é inferior a 48 horas", ""));
                    }
                }
                if (valida == true) {
                    for (FrequenciaMensal frequenciaMensal : freqMensais) {
                        frequenciaMensal.setStatus(Status.NOVA);
                        frequenciaMensalDAO.update(frequenciaMensal);
                    }
                    result.include("success", "enviadas");
                    result.include("s", "s");
                    enviaEmail(inscricao, 0);
                    //enviar email professor
                    result.forwardTo(this).enviaFrequencia();
                }
            } else {
                validator.add(new ValidationMessage("Não forams preenchidas as frequências de todos os meses", ""));
            }
        }
        validator.onErrorForwardTo(this).preencheFrequencia(id);
        //result.redirectTo(this).preencheFrequencia(id);
    }

    @Post("/analise/frequencia")
    public void analisar(Long idInscricao, int mes, String analise) {
        System.out.println("+++++++++++++++++++++++");
        System.out.println(mes + " - " + idInscricao + " - " + analise);
        Status status;
        if (analise.equals("aprova")) {
            status = Status.APROVADA;
        } else {
            status = Status.REPROVADA;
        }

        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(idInscricao);

        List<FrequenciaMensal> freqMensais = inscricao.getFrequenciasMensais();

        if (inscricao.isBolsista()) {
            FrequenciaMensal frequencia = findByMesInscricao(mes, freqMensais);
            frequencia.setStatus(status);
            frequenciaMensalDAO.update(frequencia);
            result.include("success", analise + "da");
            //result.include("analisada", true);
            result.redirectTo(this).analiseFrequencia(idInscricao, mes);
        } else {
            for (FrequenciaMensal frequenciaMensal : freqMensais) {
                frequenciaMensal.setStatus(status);
                frequenciaMensalDAO.update(frequenciaMensal);
            }
            result.include("s", "s");
            result.include("success", analise + "da");
            //result.include("analisada", true);
            result.redirectTo(this).analiseFrequencia(idInscricao, mes);
        }
    }

//    @Get("/analise/frequencia/{id}/{mes}")
    @Post("/analise/frequencia/aluno")
    public void analiseFrequencia(Long id, int mes) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        if (inscricao == null) {
            this.result.redirectTo(this).enviaFrequencia();
        }

        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();

        if (inscricao.isBolsista()) {
            FrequenciaMensal freq = findByMesInscricao(mes, inscricao.getFrequenciasMensais());
            List<Frequencia> frequencias = freq.getFrequencias();
            Date date = frequencias.get(0).getData();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", mes);

            if (freq.analisada()) {
                result.include("analisada", true);
            }
        } else {
            FrequenciaMensal freq = inscricao.getFrequenciasMensais().get(0);
            List<Frequencia> frequencias = freq.getFrequencias();
            Date date = frequencias.get(0).getData();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("s", "s");

            if (freq.analisada()) {
                result.include("analisada", true);
            }
        }

        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());
        this.result.include("inscricao", inscricao);
    }

    //@Get("/frequencia/list.json/{id}")
    public void getFrequencias(Long id, int mes) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        if (inscricao == null) {
            this.result.redirectTo(this).enviaFrequencia();
        }

        List<Frequencia> frequencias = frequenciaDAO.listByInscricao(inscricao.getId());

        List<FrequenciaJson> frequenciasJson = new ArrayList<FrequenciaJson>();

        for (Frequencia frequencia : frequencias) {
            FrequenciaJson frequenciaJson = new FrequenciaJson(frequencia);
            frequenciasJson.add(frequenciaJson);
        }

        result.use(Results.json()).withoutRoot().from(frequenciasJson).serialize();
    }

    private boolean validaData(Date start) {
        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();
        if (start.before(letivo.getDtInicio()) || start.after(letivo.getDtFim())) {
            return false;
        }

        return true;
    }

    private FrequenciaMensal findByMesInscricao(int mes, List<FrequenciaMensal> frequencias) {
        for (FrequenciaMensal frequenciaMensal : frequencias) {
            if (frequenciaMensal.getMes() == mes) {
                return frequenciaMensal;
            }
        }
        return null;
    }

    private void enviaEmail(InscricaoMonitoria inscricao, Integer mes) {
        Professor professor = inscricao.getMonitoria().getProfessor();

        String conteudo =
                "<html>\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h2>Caro(a) " + professor.getUsuario().getNome() + ", </h2><br/>"
                + "        Um pedido de avaliação de Frequência de Monitoria foi encaminhado por <b>" + inscricao.getInscrito().getUsuario().getNome() + "</b>:<br/>"
                + "        <form id=\"formEnvio\" name=\"formEnvio\" method=\"post\" action=\"http://localhost:8080/Projeto/analise/frequencia/aluno\">\n"
                + "            <input type=\"hidden\" name=\"id\" value=\"" + inscricao.getId() + "\"/>\n"
                + "            <input type=\"hidden\" name=\"mes\" value=\"" + mes + "\"/>\n"
                + "            <button id=\"acessar\" type=\"submit\">Acessar Solicitação de Análise de Frequencia de Monitoria.</a>\n"
                + "        </form>\n"
                + "    </body>\n"
                + "</html>";


        this.notificador.notifica(professor.getUsuario(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Frequência de Monitoria", "Um pedido de avaliação de Frequência de Monitoria foi encaminhado", Boolean.TRUE);
    }
}
