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
import br.edu.ufam.icomp.projeto4.dao.AprovacaoDAO;
import br.edu.ufam.icomp.projeto4.dao.EstagioDAO;
import br.edu.ufam.icomp.projeto4.dao.FrequenciaEstagioDAO;
import br.edu.ufam.icomp.projeto4.dao.FrequenciaMensalEstagioDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.SolicitacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aprovacao;
import br.edu.ufam.icomp.projeto4.model.Estagio;
import br.edu.ufam.icomp.projeto4.model.FrequenciaEstagio;
import br.edu.ufam.icomp.projeto4.model.FrequenciaJson;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensalEstagio;
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
public class FrequenciaEstagioController {

    private final Result result;
    private final EstagioDAO estagioDAO;
    private AlunoDAO alunoDAO;
    private SessionData session;
    private Usuario usuario;
    private FrequenciaEstagioDAO frequenciaEstagioDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private FrequenciaMensalEstagioDAO frequenciaMensalEstagioDAO;
    private final Validator validator;
    private Notificador notificador;
    private SolicitacaoDAO solicitacaoDAO;
    private AprovacaoDAO aprovacaoDAO;

    public FrequenciaEstagioController(EstagioDAO estagioDAO, Result result, PeriodoLetivoDAO periodoLetivoDAO, Validator validator,
            AlunoDAO alunoDAO, SessionData session, FrequenciaEstagioDAO frequenciaEstagioDAO, FrequenciaMensalEstagioDAO frequenciaMensalEstagioDAO, Notificador notificador,
            SolicitacaoDAO solicitacaoDAO, AprovacaoDAO aprovacaoDAO) {
        this.result = result;
        this.estagioDAO = estagioDAO;
        this.alunoDAO = alunoDAO;
        this.session = session;
        this.usuario = session.getUsuario();
        this.frequenciaEstagioDAO = frequenciaEstagioDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.frequenciaMensalEstagioDAO = frequenciaMensalEstagioDAO;
        this.validator = validator;
        this.notificador = notificador;
        this.solicitacaoDAO = solicitacaoDAO;
        this.aprovacaoDAO = aprovacaoDAO;
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/{id}/frequencia")
    public void preencheFrequencia(Long id) {
        Estagio estagio = estagioDAO.findById(id);

        if (estagio == null) {
            this.result.redirectTo(EstagioController.class).main();
        }

        if (new Date().getDate() < 0) {
            this.result.include("bloqueio", true);
        }

        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();

        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());
        this.result.include("estagio", estagio);
    }

    @Get("/estagios/frequencia/list.json/{id}")
    public void getFrequencias(Long id) {
        Estagio estagio = estagioDAO.findById(id);

        if (estagio == null) {
            this.result.redirectTo(EstagioController.class).main();
        }

        List<FrequenciaEstagio> frequencias = frequenciaEstagioDAO.listByEstagio(estagio.getId());

        List<FrequenciaJson> frequenciasJson = new ArrayList<FrequenciaJson>();

        for (FrequenciaEstagio frequencia : frequencias) {
            FrequenciaJson frequenciaJson = new FrequenciaJson(frequencia);
            frequenciasJson.add(frequenciaJson);
        }

        result.use(Results.json()).withoutRoot().from(frequenciasJson).serialize();
    }

    @Permission(Perfil.ALUNO)
    @Post("/estagios/frequencia/inclui.json")
    public void inclui(Long id, Date start, int title, String descricao) {
        Estagio estagio = estagioDAO.findById(id);

        FrequenciaEstagio busca = frequenciaEstagioDAO.findByData(start, estagio.getId());

        if (validaData(start)) {
            if (busca != null) {
                busca.setCargaHoraria(title);
                busca.setDescricao(descricao);
                frequenciaEstagioDAO.update(busca);
            } else {
                FrequenciaEstagio frequencia = new FrequenciaEstagio();
                frequencia.setCargaHoraria(title);
                frequencia.setData(start);
                frequencia.setEstagio(estagio);
                frequencia.setDescricao(descricao);
                frequenciaEstagioDAO.create(frequencia);

                FrequenciaMensalEstagio freqMensal = findByMesEstagio(start.getMonth(), estagio.getFrequenciasMensais());

                if (freqMensal == null) {
                    freqMensal = new FrequenciaMensalEstagio();

                    freqMensal.setEstagio(estagio);
                    freqMensal.setMes(start.getMonth());
                    freqMensal.setStatus(Status.SALVA);
                    freqMensal.addFrequencia(frequencia);
                    frequenciaMensalEstagioDAO.create(freqMensal);
                    estagio.add(freqMensal);
                    estagioDAO.update(estagio);
                } else {
                    freqMensal.addFrequencia(frequencia);
                    frequenciaMensalEstagioDAO.update(freqMensal);
                }
            }
        }

        result.redirectTo(this).preencheFrequencia(id);
    }

    @Permission(Perfil.ALUNO)
    @Post("/estagios/frequencia/exclui.json")
    public void exclui(int idFrequencia, Long id) {
        FrequenciaEstagio frequencia = frequenciaEstagioDAO.findById(idFrequencia);

        Estagio estagio = estagioDAO.findById(frequencia.getEstagio().getId());
        FrequenciaMensalEstagio freqMensal = findByMesEstagio(frequencia.getData().getMonth(), estagio.getFrequenciasMensais());
        freqMensal.removeFrequencia(frequencia);

        frequenciaMensalEstagioDAO.update(freqMensal);

        frequenciaEstagioDAO.delete(frequencia);
        result.redirectTo(this).preencheFrequencia(id);
    }

    private FrequenciaMensalEstagio findByMesEstagio(int mes, List<FrequenciaMensalEstagio> frequencias) {
        for (FrequenciaMensalEstagio frequenciaMensal : frequencias) {
            if (frequenciaMensal.getMes() == mes) {
                return frequenciaMensal;
            }
        }
        return null;
    }

    private boolean validaData(Date start) {
        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();
        if (start.before(letivo.getDtInicio()) || start.after(letivo.getDtFim())) {
            return false;
        }

        return true;
    }

    @Permission(Perfil.ALUNO)
    @Get("/estagios/frequencia/enviar/{id}")
    public void enviar(Long id) {
        Estagio estagio = estagioDAO.findById(id);

        int mes_atual = new Date().getMonth();
        FrequenciaMensalEstagio freqMensal = findByMesEstagio(mes_atual, estagio.getFrequenciasMensais());

        if (freqMensal == null) {
            validator.add(new ValidationMessage("Nenhuma frequência foi preenchida", ""));
        } else {
            freqMensal.setStatus(Status.NOVA);
            frequenciaMensalEstagioDAO.update(freqMensal);

            List<Aprovacao> aprovacoes = new ArrayList<Aprovacao>();

            Aprovacao aprovador1 = new Aprovacao();
            aprovador1.setEmailAprovador(estagio.getEmailSupervisor());
            aprovador1.setNomeAprovador(estagio.getNomeSupervisor());

            Aprovacao aprovador2 = new Aprovacao();
            aprovador2.setEmailAprovador(estagio.getOrientador().getUsuario().getEmail());
            aprovador2.setNomeAprovador(estagio.getOrientador().getUsuario().getNome());

            this.aprovacaoDAO.create(aprovador1);
            this.aprovacaoDAO.create(aprovador2);

            aprovacoes.add(aprovador1);
            aprovacoes.add(aprovador2);

            freqMensal.setAprovacoes(aprovacoes);

            this.frequenciaMensalEstagioDAO.update(freqMensal);

            result.include("email", "enviado");
            enviaEmail(estagio, mes_atual);
            result.forwardTo(EstagioController.class).main();
        }

        validator.onErrorForwardTo(this).preencheFrequencia(id);
    }

    @Get("estagios/buscaFrequencia.json")
    public void buscaFrequencia(Integer id) {
        if (id != null) {
            FrequenciaEstagio frequencia = frequenciaEstagioDAO.findById(id);

            result.use(Results.json()).withoutRoot().from(frequencia).serialize();
        } else {
            result.use(Results.json()).withoutRoot().from(new FrequenciaEstagio()).serialize();
        }
    }

    @Get("estagios/buscaFrequenciaByData.json")
    public void buscaFrequenciaByData(Date inicio, Long id) {
        if (inicio != null && id != null) {
            FrequenciaEstagio frequencia = frequenciaEstagioDAO.findByData(inicio, id);

            if (frequencia != null) {
                result.use(Results.json()).withoutRoot().from(frequencia).serialize();
            } else {
                result.use(Results.json()).withoutRoot().from(new FrequenciaEstagio()).serialize();
            }
        } else {
            result.use(Results.json()).withoutRoot().from(new FrequenciaEstagio()).serialize();
        }
    }

    @Post("/estagios/analise/frequencia")
    public void analisar(Long idEstagio, int mes, String analise, Usuario usuario) {
        boolean status = false;
        if (analise.equals("aprova")) {
            status = true;
        } 

        Estagio estagio = estagioDAO.findById(idEstagio);

        List<FrequenciaMensalEstagio> freqMensais = estagio.getFrequenciasMensais();

        FrequenciaMensalEstagio frequencia = findByMesEstagio(mes, freqMensais);
        
        Aprovacao aprovacaoAux = null;
        List<Aprovacao> aprovacoes = frequencia.getAprovacoes();
        for (Aprovacao aprovacao : aprovacoes) {
            if(aprovacao.getEmailAprovador().equals(usuario.getEmail())){
                aprovacaoAux = aprovacao;
            }
        }                
        
        aprovacaoAux.setAprovou(status);
        
        this.aprovacaoDAO.update(aprovacaoAux);
        
//        frequenciaMensalEstagioDAO.update(frequencia);
        result.include("success", analise + "da");
        result.include("estagio", estagio);

        result.forwardTo(this).analiseFrequencia(idEstagio, mes, usuario);
    }

    //@Get("/estagios/analise/frequencia/aluno/{id}/{mes}")
    @Post("/estagios/analise/frequencia/aluno")
    public void analiseFrequencia(Long id, int mes, Usuario usuario) {
        Estagio estagio = estagioDAO.findById(id);

        PeriodoLetivo letivo = periodoLetivoDAO.findLetivoAtivo();

        List<FrequenciaMensalEstagio> frequenciasMensais = estagio.getFrequenciasMensais();

        Aprovacao aprovacaoAux = null;

        for (FrequenciaMensalEstagio frequenciaMensalEstagio : frequenciasMensais) {
            if (frequenciaMensalEstagio.getMes() == mes) {
                List<Aprovacao> aprovacoes = frequenciaMensalEstagio.getAprovacoes();

                for (Aprovacao aprovacao : aprovacoes) {
                    if (aprovacao.getEmailAprovador().equals(usuario.getEmail())) {
                        aprovacaoAux = aprovacao;
                    }
                }
            }
        }
        
        if(aprovacaoAux == null){
            this.result.include("naoAprova", true);
        }

        FrequenciaMensalEstagio freq = findByMesEstagio(mes, estagio.getFrequenciasMensais());
        List<FrequenciaEstagio> frequencias = freq.getFrequencias();
        Date date = frequencias.get(0).getData();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.result.include("ano", cal.get(Calendar.YEAR));
        this.result.include("mes", mes);                
        
        if (aprovacaoAux != null && aprovacaoAux.getAprovou() != null) {
            result.include("analisada", true);
        }

        this.result.include("inicioPeriodo", letivo.getDtInicio());
        this.result.include("fimPeriodo", letivo.getDtFim());
        this.result.include("estagio", estagio);
        this.result.include("usuario", usuario);
    }

    private void enviaEmail(Estagio estagio, Integer mes) {
        Professor professor = estagio.getOrientador();

        /*
         * 1º Envia email para professor orientador
         */
        String conteudo =
                "<html>\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h2>Caro(a) " + professor.getUsuario().getNome() + ", </h2><br/>"
                + "        Um pedido de avaliação de Frequência de Estágio foi encaminhado por <b>" + estagio.getAluno().getUsuario().getNome() + "</b>:<br/>"
                + "        <form id=\"formEnvio\" name=\"formEnvio\" method=\"post\" action=\"http://localhost:8080/Projeto/estagios/analise/frequencia/aluno\">\n"
                + "            <input type=\"hidden\" name=\"id\" value=\"" + estagio.getId() + "\"/>\n"
                + "            <input type=\"hidden\" name=\"mes\" value=\"" + mes + "\"/>\n"
                + "            <input type=\"hidden\" name=\"usuario.email\" value=\"" + professor.getUsuario().getEmail() + "\"/>\n"
                + "            <button id=\"acessar\" type=\"submit\">Acessar Solicitação de Análise de Frequencia de Estágio.</a>\n"
                + "        </form>\n"
                + "    </body>\n"
                + "</html>";


        this.notificador.enviarEmail(professor.getUsuario().getEmail(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Frequência de Estágio");


        /*
         * 2º Envia email para supervisor - FALTA AJUSTA ENVIO DA NOTIFICAÇÃO
         */
        conteudo =
                "<html>\n"
                + "    <head>\n"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <h2>Caro(a) " + estagio.getNomeSupervisor() + ", </h2><br/>"
                + "        Um pedido de avaliação de Frequência de Estágio foi encaminhado por <b>" + estagio.getAluno().getUsuario().getNome() + "</b>:<br/>"
                + "        <form id=\"formEnvio\" name=\"formEnvio\" method=\"post\" action=\"http://localhost:8080/Projeto/estagios/analise/frequencia/aluno\">\n"
                + "            <input type=\"hidden\" name=\"id\" value=\"" + estagio.getId() + "\"/>\n"
                + "            <input type=\"hidden\" name=\"mes\" value=\"" + mes + "\"/>\n"
                + "            <input type=\"hidden\" name=\"usuario.email\" value=\"" + estagio.getEmailSupervisor() + "\"/>"
                + "            <button id=\"acessar\" type=\"submit\">Acessar Solicitação de Análise de Frequencia de Estágio.</a>\n"
                + "        </form>\n"
                + "    </body>\n"
                + "</html>";

        this.notificador.enviarEmail(estagio.getEmailSupervisor(), conteudo, "[Sistema Atividades Extracurriculares] Análise de Frequência de Estágio");
    }
}
