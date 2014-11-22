package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.MonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoInsMonDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Anexo;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author Bruna
 */
@Resource
public class HistoricoMonitoriasController {

    private final Result result;
    private final Validator validator;
    private final InscricaoMonitoriaDAO inscricaoDAO;
    private AlunoDAO alunoDAO;
    private MonitoriaDAO monitoriaDAO;
    private Anexo pastaDeAnexos;
    private SessionData session;
    private Usuario usuario;
    private ServletContext context;
    private DisciplinaDAO disciplinaDAO;
    private PeriodoInsMonDAO periodoDAO;
    private ProfessorDAO professorDAO;
    private SecretariaDAO secretariaDAO;
    private CursoDAO cursoDAO;

    public HistoricoMonitoriasController(PeriodoInsMonDAO periodoDAO, MonitoriaDAO monitoriaDAO, InscricaoMonitoriaDAO inscricaoDAO, Result result, Validator validator, DisciplinaDAO disciplinaDAO,
            AlunoDAO alunoDAO, Anexo pastaDeAnexos, SessionData session, ServletContext context, ProfessorDAO professorDAO, SecretariaDAO secretariaDAO, CursoDAO cursoDAO) {
        this.result = result;
        this.validator = validator;
        this.inscricaoDAO = inscricaoDAO;

        this.alunoDAO = alunoDAO;
        this.pastaDeAnexos = pastaDeAnexos;
        this.session = session;
        usuario = session.getUsuario();
        this.context = context;
        this.disciplinaDAO = disciplinaDAO;
        this.monitoriaDAO = monitoriaDAO;
        this.periodoDAO = periodoDAO;
        this.professorDAO = professorDAO;
        this.secretariaDAO = secretariaDAO;
        this.cursoDAO = cursoDAO;
    }
    
    @Permission({Perfil.COORDENADORACAD, Perfil.SECRETARIA})
    @Get("/historicoMonitorias/alunos")
    public List<Aluno> index(Long idCurso){
        List<Curso> cursos = new ArrayList<Curso>();
        
        if (this.usuario.getRole() == Perfil.COORDENADOR) {
            Professor professor = this.professorDAO.findByUsuario(this.usuario.getId());

            cursos = professor.getCursos();
        } else if (this.usuario.getRole() == Perfil.SECRETARIA) {
            Secretaria secretaria = this.secretariaDAO.findByUsuario(this.usuario.getId());

            cursos = secretaria.getCursos();
        }
        
        result.include("cursoList", cursos);
       
        if(idCurso==null){
            idCurso = 0L;    
        }             
        
        result.include("idCurso",idCurso);
        if(idCurso > 0){
            return alunoDAO.findByCursoId(idCurso);
        } else {
            return alunoDAO.findAll();
        }
        
        
    }
    
    @Permission({Perfil.COORDENADORACAD, Perfil.SECRETARIA})
    @Get("/historicoMonitorias/alunos/{id}/show")
    public void show(Long id){
        result.include("aluno", alunoDAO.findById(id));
        result.include("monitoriaList", inscricaoDAO.findByInscrito(id));
    }
    
    @Permission({Perfil.COORDENADORACAD, Perfil.SECRETARIA})
    @Post("/historicoMonitorias/alunos")
    public void search(Long curso) {
        this.result.forwardTo(this).index(curso); 
        
    }
    /* 
    @Permission({Perfil.COORDENADOR, Perfil.SECRETARIA})
    @Get("/historicoMonitorias/alunos/{id}/relatorio")
    public Download report(Long id) throws JRException {
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
    }*/
}
