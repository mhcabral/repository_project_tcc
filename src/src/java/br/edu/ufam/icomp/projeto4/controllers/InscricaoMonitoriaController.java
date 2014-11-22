package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.ListaHorarios;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.MonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author leonardo
 */
@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class InscricaoMonitoriaController {

    private final Result result;
    private final Validator validator;
    private final InscricaoMonitoriaDAO inscricaoDAO;
    private AlunoDAO alunoDAO;
    private MonitoriaDAO monitoriaDAO;
    private Anexo pastaDeAnexos;
    private SessionData session;
    private Usuario usuario;
    private ServletContext context;
    private Aluno aluno;
    private PeriodoLetivoDAO periodoDAO;
    private CriteriaBuilder criteriaBuilder;
    private ListaHorarios listaHorarios;

    public InscricaoMonitoriaController(ProfessorDAO professorDAO, CursoDAO cursoDAO, PeriodoLetivoDAO periodoDAO, MonitoriaDAO monitoriaDAO,
            InscricaoMonitoriaDAO inscricaoDAO, Result result, Validator validator, DisciplinaDAO disciplinaDAO,ListaHorarios listaHorarios,
            AlunoDAO alunoDAO, Anexo pastaDeAnexos, SessionData session, ServletContext context, CriteriaBuilder criteriaBuilder) {
        this.result = result;
        this.validator = validator;
        this.inscricaoDAO = inscricaoDAO;

        this.alunoDAO = alunoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.session = session;
        this.usuario = session.getUsuario();
        this.context = context;
        this.aluno = alunoDAO.findByIdUsuario(usuario.getId());
        this.monitoriaDAO = monitoriaDAO;
        this.periodoDAO = periodoDAO;
        this.criteriaBuilder = criteriaBuilder;
        this.listaHorarios = listaHorarios;
    }

//    public InscricaoMonitoriaController(InscricaoMonitoriaDAO inscricaoDAO, Result result, Validator validator) {
//        this.result = result;
//        this.validator = validator;
//        this.inscricaoDAO = inscricaoDAO;
//        
//    }
    @Get("/inscricoes/index")
    public void main() {
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/nova")
    public void create(Long id) {
        InscricaoMonitoria inscricao = new InscricaoMonitoria();

        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
        Monitoria monitoria = monitoriaDAO.findById(id);

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        inscricao.setMonitoria(monitoria);
        inscricao.setInscrito(aluno);
        
        listaHorarios.clean();

        if (!monitoria.getHorario().isEmpty()) {
            listaHorarios.setHorarios(monitoria.getHorario());
            System.out.println("========================" + monitoria.getHorario().size());
            Date date = listaHorarios.getHorarios().get(0).getInicio();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        } else {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        }

//        this.result.include("dataIngresso", formatador.format(aluno.getDataIngresso()));
//        this.result.include("atividadeList", this.regraDAO.findAtividadeFromCurso(aluno.getCurso().getId()));
        this.result.include("operacao", "Inscrição");
//        this.result.include("horaMaxima", this.regraDAO.findByAtividadeCurso(solicitacao.getAtividade().getId(), aluno.getCurso().getId()).getMaximoHoras());
        this.result.include("monitoriaId", id);
        System.out.println("antes " + inscricao.toString());
    }

    @Permission(Perfil.ALUNO)
    @Get("/InsMonitoria")
    public void index() {
        List<Monitoria> monitoriasDisponiveis = new ArrayList<Monitoria>();
        try {
            Long idPeriodoAtivo = this.periodoDAO.findLetivoAtivo().getId();
            monitoriasDisponiveis = this.monitoriaDAO.listByIdPeriodo(idPeriodoAtivo);
        } catch (Exception e) {
            System.out.println("erro!!!!!!!!!!!!!!!!!!!!!!");
        }
        this.result.include("monitoriaDisponivelList", monitoriasDisponiveis);
        this.result.include("monitoriaSolicitadaList", this.inscricaoDAO.findByInscrito(aluno.getId()));
        this.result.include("totalComputado", this.inscricaoDAO.countByInscrito(aluno.getId(), Status.CONCLUIDA));

    }

    private List<Monitoria> getMonitoriasDisponiveis(List<Monitoria> monitoriasDisponiveis) {
        List<Monitoria> monitorias = new ArrayList<Monitoria>();

        for (Monitoria monitoria : monitoriasDisponiveis) {
            monitorias.add(monitoria);
        }

        return monitorias;
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/show")
    public void show(Long id) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        if (inscricao == null) {
            this.result.redirectTo(InscricaoMonitoriaController.class).index();
        }
        
        listaHorarios.clean();
        Monitoria monitoria = inscricao.getMonitoria();
        

        if (!monitoria.getHorario().isEmpty()) {
            listaHorarios.setHorarios(monitoria.getHorario());
            System.out.println("========================" + monitoria.getHorario().size());
            Date date = listaHorarios.getHorarios().get(0).getInicio();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        } else {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        }

        this.result.include("inscricao", inscricao);
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/edit")
    public void edit(Long id) {
        InscricaoMonitoria inscricao = inscricaoDAO.findById(id);
        if (inscricao == null) {
            this.result.redirectTo(InscricaoMonitoriaController.class).index();
        } else {
            Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
            this.result.include("operacao", "Edição");
        }
        
        Monitoria monitoria = inscricao.getMonitoria();
        
        listaHorarios.clean();

        if (!monitoria.getHorario().isEmpty()) {
            listaHorarios.setHorarios(monitoria.getHorario());
            System.out.println("========================" + monitoria.getHorario().size());
            Date date = listaHorarios.getHorarios().get(0).getInicio();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        } else {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            this.result.include("ano", cal.get(Calendar.YEAR));
            this.result.include("mes", cal.get(Calendar.MONTH));
            this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));
        }
        
        this.result.include("inscricao", inscricao);
    }

    @Permission(Perfil.ALUNO)
    @Get("/inscricoes/{id}/remove")
    public void remove(Long id) {
        InscricaoMonitoria inscricao = this.inscricaoDAO.findById(id);

        if (inscricao == null) {
            this.result.redirectTo(InscricaoMonitoriaController.class).index();
        } else {
            if (this.aluno.getId() == inscricao.getInscrito().getId()) {
                if (!(inscricao.getStatusAtual() == Status.NOVA)) {
                    validator.add(new ValidationMessage("Desculpe! Inscrição não pôde ser removida, já foi analisada ou encaminhada para análise", "inscricao.id"));
                    result.include("inscricao", inscricao);

                    this.validator.validate(inscricao);

                    this.validator.onErrorRedirectTo(this).show(id);
                } else {
                    this.inscricaoDAO.delete(inscricao);

                    result.include("success", "removida");

                    this.result.redirectTo(this).index();
                }
            } else {
                validator.add(new ValidationMessage("A inscrição não foi feita pelo seu usuário", "inscricao.id"));
                this.validator.onErrorRedirectTo(this).index();
            }
        }
    }

    @Put("/alteracao")
    public void altera(InscricaoMonitoria inscricao, UploadedFile comprovante, UploadedFile historico, boolean apagar) {
        InscricaoMonitoria editada = this.inscricaoDAO.findById(inscricao.getId());
        editada.setBolsista(inscricao.isBolsista());
        if (!editada.isBolsista()) {
            editada.setBanco(null);
            editada.setAgencia(null);
            editada.setConta(null);
        } else {
            editada.setBanco(inscricao.getBanco());
            editada.setAgencia(inscricao.getAgencia());
            editada.setConta(inscricao.getConta());
        }
        if (!(editada.getAgencia() == null
                && editada.getBanco() == null
                && editada.getConta() == null)
                && !(editada.getAgencia() != null
                && editada.getBanco() != null
                && editada.getConta() != null)) {
            this.validator.add(new ValidationMessage("Faltam informações bancárias", "bolsista"));
        }

        if (comprovante != null) {
            System.out.println("+++++++++++++++++++++++++++" + comprovante.getFileName());
            if (!comprovante.getContentType().equals("application/pdf") && !comprovante.getContentType().equals("application/msword")
                    && !comprovante.getContentType().equals("application/vnd.oasis.opendocument.text") && !comprovante.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    && !comprovante.getContentType().equals("image/png") && !comprovante.getContentType().equals("image/jpeg")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + comprovante.getFileName() + "] não é aceita no sistema", "comprovante", comprovante));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                pastaDeAnexos.salva(comprovante, nomeAleatorio);
                editada.setComprovane(nomeAleatorio);
            }
        }

        if (historico != null) {
            System.out.println("-------------------------------" + historico.getFileName());
            if (!historico.getContentType().equals("application/pdf") && !historico.getContentType().equals("application/msword")
                    && !historico.getContentType().equals("application/vnd.oasis.opendocument.text") && !historico.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    && !historico.getContentType().equals("image/png") && !historico.getContentType().equals("image/jpeg")) {
                this.validator.add(new ValidationMessage("A extensão do arquivo [" + historico.getFileName() + "] não é aceita no sistema", "historico[]", historico));
            } else {
                String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
                pastaDeAnexos.salva(historico, nomeAleatorio);
                editada.setHistorico(nomeAleatorio);
            }
        }
        
        this.validator.validate(inscricao);
        this.validator.onErrorRedirectTo(this).edit(inscricao.getId());
        this.inscricaoDAO.update(editada);
        result.include("success", "alterada");
        this.result.redirectTo(this).index();
    }

    @Post("/inscricoes/{id}")
    public void cadastrar(final InscricaoMonitoria inscricao, UploadedFile comprovante, UploadedFile historico, Long id) {


        if (comprovante == null) {
            this.validator.add(new ValidationMessage("Adicione o comprovante de matrícula", "anexo"));
        }

        if (historico == null) {
            this.validator.add(new ValidationMessage("Adicione o histório escola", "anexo"));
        }

        if (this.inscricaoDAO.exist(id, this.aluno.getId())) {
            this.validator.add(new ValidationMessage("Você já se inscreveu para esta monitoria", "Inscricao"));
        }

        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
        Monitoria monitoria = monitoriaDAO.findById(id);

        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        inscricao.setMonitoria(monitoria);
        inscricao.setInscrito(aluno);
//        inscricao.setStatusAtual(Status.SALVA);

        inscricao.setStatusAtual(Status.NOVA);

        if (inscricao.getAgencia() == null
                && inscricao.getBanco() == null
                && inscricao.getConta() == null) {
            inscricao.setBolsista(false);
        } else if (inscricao.getAgencia() != null
                && inscricao.getBanco() != null
                && inscricao.getConta() != null) {
            inscricao.setBolsista(true);
        } else {
            this.validator.add(new ValidationMessage("Faltam informações bancárias", "anexo"));
        }

//        Mudanca mudanca = mudancaController.create(usuario, Status.SALVA, "");
//
//        solicitacao.adicionaMudanca(mudanca);
//        solicitacao.setSolicitante(aluno);
//        solicitacao.setStatusAtual(Status.SALVA);


        if (!comprovante.getContentType().equals("application/pdf") && !comprovante.getContentType().equals("application/msword")
                && !comprovante.getContentType().equals("application/vnd.oasis.opendocument.text") && !comprovante.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                && !comprovante.getContentType().equals("image/png") && !comprovante.getContentType().equals("image/jpeg")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + comprovante.getFileName() + "] não é aceita no sistema", "comprovante", comprovante));
        } else {
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            pastaDeAnexos.salva(comprovante, nomeAleatorio);
            inscricao.setComprovane(nomeAleatorio);
        }
        if (!historico.getContentType().equals("application/pdf") && !historico.getContentType().equals("application/msword")
                && !historico.getContentType().equals("application/vnd.oasis.opendocument.text") && !historico.getContentType().equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                && !historico.getContentType().equals("image/png") && !historico.getContentType().equals("image/jpeg")) {
            this.validator.add(new ValidationMessage("A extensão do arquivo [" + historico.getFileName() + "] não é aceita no sistema", "historico[]", historico));
        } else {
            String nomeAleatorio = pastaDeAnexos.nomeAleatorio();
            pastaDeAnexos.salva(historico, nomeAleatorio);
            inscricao.setHistorico(nomeAleatorio);
        }
        this.validator.validate(inscricao);
        this.validator.onErrorRedirectTo(this).create(id);
        this.inscricaoDAO.create(inscricao);
        result.include("success", "cadastrada");
        this.result.redirectTo(InscricaoMonitoriaController.class).index();
    }

    @Get("/historico/relatorio")
    public Download show() throws JRException {

        InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/MonitoriasPorAluno.jrxml");

        JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

        System.out.println("\n\n\n\n\nUsuario: " + usuario.getId() + "\n\n\n\n\n\n");

        Aluno aluno = this.alunoDAO.findByIdUsuario(usuario.getId());

        System.out.println("Aluno: " + aluno.getId());

        List<InscricaoMonitoria> findByInscrito = inscricaoDAO.findByInscrito(aluno.getId());

        Map parameters = new HashMap();
        parameters.put("nomeAluno", aluno.getUsuario().getNome());
        parameters.put("curso", aluno.getCurso().getCodigo() + " - " + aluno.getCurso().getCurso());
        parameters.put("anoIngresso", aluno.getDataIngresso());
        parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
        parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(findByInscrito));

        // exportacao do relatorio para outro formato, no caso PDF

        JRExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream exported = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

        // Export the PDF file
        exporter.exportReport();

        byte[] content = exported.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(content);

        return new InputStreamDownload(inputStream, "application/pdf", "RelatorioClientes.pdf");
    }
}