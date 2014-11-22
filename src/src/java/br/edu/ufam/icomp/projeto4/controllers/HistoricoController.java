package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.dao.SolicitacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @author Bruna
 */
@Resource
public class HistoricoController {

    private final AlunoDAO alunoDAO;
    private final Result result;
    private final Validator validator;
    private SolicitacaoDAO solicitacaoDAO;
    private ProfessorDAO professorDAO;
    private SecretariaDAO secretariaDAO;
    private SessionData session;
    private Usuario usuario;
    private ServletContext context;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private CursoDAO cursoDAO;

    public HistoricoController(AlunoDAO alunoDAO, Result result, Validator validator, SolicitacaoDAO solicitacaoDAO, PeriodoLetivoDAO periodoLetivoDAO, CursoDAO cursoDAO,
            ProfessorDAO professorDAO, SecretariaDAO secretariaDAO, SessionData session, ServletContext context, CoordenadorCursoDAO coordenadorCursoDAO) {
        this.alunoDAO = alunoDAO;
        this.result = result;
        this.validator = validator;
        this.solicitacaoDAO = solicitacaoDAO;
        this.professorDAO = professorDAO;
        this.secretariaDAO = secretariaDAO;
        this.session = session;
        usuario = session.getUsuario();
        this.context = context;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.cursoDAO = cursoDAO;
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico")
    public List<Aluno> index(Long idPeriodoLetivo, Long idCurso) {
        PeriodoLetivo ativo = periodoLetivoDAO.findLetivoAtivo();

        if (idCurso == null) {
            idCurso = 0L;
        }
        if (idPeriodoLetivo == null) {
            idPeriodoLetivo = 0L;
            if (ativo != null) {
                idPeriodoLetivo = ativo.getId();
            }
        }

        List<Aluno> alunos = new ArrayList<Aluno>();

        if (usuario.getRole().equals(Perfil.COORDENADOR)) {
            idCurso = coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso().getId();
            alunos = alunoDAO.findByCursoId(idCurso);
        } else {
            alunos = alunoDAO.findByCurso(secretariaDAO.findByUsuario(usuario.getId()).getCursos());
        }
        
        if (idCurso != 0) {
            alunos = alunoDAO.findByCursoId(idCurso);
        }

        if(!alunos.isEmpty()){
            alunos = solicitacaoDAO.findHistoricoCategoria(alunos, idPeriodoLetivo);
        }

        List<PeriodoLetivo> periodosLetivos = periodoLetivoDAO.listLetivoAnterior();

        periodosLetivos.add(0, ativo);

        result.include("periodoLetivoList", periodosLetivos);

        if (idPeriodoLetivo == 0L) {
            idPeriodoLetivo = periodosLetivos.get(0).getId();
        }

        this.result.include("idPeriodoLetivo", idPeriodoLetivo);
        this.result.include("idCurso", idCurso);
        this.result.include("alunoList", alunos);

        if (usuario.getRole().equals(Perfil.SECRETARIA)) {
            List<Curso> cursos = secretariaDAO.findByUsuario(usuario.getId()).getCursos();
            result.include("cursoList", cursos);
        }
        return alunos;
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico/{id}/show")
    public Download show(Long id) throws JRException {
        List<Solicitacao> solicitacoes = solicitacaoDAO.findBySolicitante(id);

        List<Solicitacao> historico = this.solicitacaoDAO.findHistorico(id, Status.DEFERIDA);

        InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/HistoricoAC.jrxml");

        JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

        Aluno aluno = this.alunoDAO.findById(id);

        Map parameters = new HashMap();
        parameters.put("nomeAluno", aluno.getUsuario().getNome());
        parameters.put("curso", aluno.getCurso().getCodigo() + " - " + aluno.getCurso().getCurso());
        parameters.put("anoIngresso", aluno.getDataIngresso());
        parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
        parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(historico));


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

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico/periodo")
    public void historicoPeriodo(Long idPeriodoLetivo) {

        /**
         * Caso nenhum filtro tenha sido selecionado, será feita a busca pelo
         * periodo letivo anterior
         */
        if (idPeriodoLetivo == null) {
            PeriodoLetivo periodoAnterior = periodoLetivoDAO.findLetivoAnterior();
            if (periodoAnterior != null) {
                idPeriodoLetivo = periodoAnterior.getId();
                result.include("idAtual", idPeriodoLetivo);
            } else {
                result.include("periodoVazio", true);
                result.include("idPeriodo", "");
                return;
            }
        }

        /**
         * id periodo que será selecionado no combox-box
         */
        result.include("idPeriodo", idPeriodoLetivo);
        result.include("periodoList", periodoLetivoDAO.listLetivoAnterior());
        List<Atividade> atividades = solicitacaoDAO.listByPeriodo(idPeriodoLetivo);

        result.include("total", getTotal(atividades));

    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico/periodo/buscaAtividades")
    public void atividadesPeriodo(Long idPeriodoLetivo) {
        List<Atividade> atividades = solicitacaoDAO.listByPeriodo(idPeriodoLetivo);
        result.include("total", getTotal(atividades));
        result.use(Results.json()).withoutRoot().from(atividades).serialize();
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico/periodo/{idPeriodo}/search")
    public void search(Long idPeriodo) {
        this.result.redirectTo(this).historicoPeriodo(idPeriodo);
    }

    private int getTotal(List<Atividade> atividades) {
        int total = 0;
        for (Atividade atividade : atividades) {
            total += atividade.getTotalSolicitada();
        }
        return total;
    }

    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historico/{idPeridoLetivo}/{idCurso}/search")
    public void search(Long idPeridoLetivo, Long idCurso) {
        this.result.forwardTo(this).index(idPeridoLetivo, idCurso);
    }
}