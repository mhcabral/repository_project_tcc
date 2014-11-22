package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.RegraGrupoInfo;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.AtividadeDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.GrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraGrupoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.Regra;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
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
public class RegraController {

    private final RegraDAO regraDAO;
    private final Result result;
    private final Validator validator;
    private AtividadeDAO atividadeDAO;
    private GrupoDAO grupoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private Usuario usuario;
    private AlunoDAO alunoDAO;
    private RegraGrupoInfo regraGrupoInfo;
    private ServletContext context;
    private RegraGrupoDAO regraGrupoDAO;

    public RegraController(RegraDAO regraDAO, Result result, Validator validator, CursoDAO cursoDAO, AlunoDAO alunoDAO,
            AtividadeDAO atividadeDAO, GrupoDAO grupoDAO, CoordenadorCursoDAO coordenadorCursoDAO,
            SessionData session, RegraGrupoInfo regraGrupoInfo, ServletContext context, RegraGrupoDAO regraGrupoDAO) {
        this.regraDAO = regraDAO;
        this.result = result;
        this.validator = validator;
        this.atividadeDAO = atividadeDAO;
        this.grupoDAO = grupoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        usuario = session.getUsuario();
        this.alunoDAO = alunoDAO;
        this.regraGrupoInfo = regraGrupoInfo;
        this.context = context;
        this.regraGrupoDAO = regraGrupoDAO;
    }

    @Permission(Perfil.COORDENADOR)
    @Get("/regras")
    public List<Regra> index() {        
        List<Regra> regras = new ArrayList<Regra>();

        if (this.regraGrupoInfo.getRegraGrupo() == null) {            
            this.result.redirectTo(RegraGrupoController.class).index();
        } else {
            Curso curso = this.regraGrupoInfo.getRegraGrupo().getCurso();
            Grupo grupo = this.regraGrupoInfo.getRegraGrupo().getGrupo();

            regras = regraDAO.findByCursoAndGrupo(curso.getId(), grupo.getId());
        }

        return regras;
    }

    @Permission(Perfil.COORDENADOR)
    @Get("/regras/{id}/edit")
    public Regra edit(Long id) {
        Grupo grupo = this.regraGrupoInfo.getRegraGrupo().getGrupo();
        List<Atividade> atividades = this.atividadeDAO.findByGroupId(grupo.getId());

        result.include("atividadeList", atividades);
        result.include("operacao", "Edição");

        return this.regraDAO.findById(id);
    }

    @Permission(Perfil.COORDENADOR)
    @Get("/regras/{id}/remove")
    public void remove(Long id) {
        Regra regra = this.regraDAO.findById(id);

        this.regraDAO.delete(regra);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }

    @Permission(Perfil.COORDENADOR)
    @Get("/regras/create")
    public Regra create() {
        Grupo grupo = this.regraGrupoInfo.getRegraGrupo().getGrupo();
        List<Atividade> atividades = this.atividadeDAO.findByGroupId(grupo.getId());

        result.include("atividadeList", atividades);
        result.include("operacao", "Cadastro");

        return new Regra();
    }

    @Permission(Perfil.COORDENADOR)
    @Get("/regras/{id}/show")
    public Regra show(Long id) {
        return this.regraDAO.findById(id);
    }

    @Put("/regras")
    public void altera(Regra regra) {
        if (regra.getAtividade() == null || regra.getAtividade().getId() == null) {
            validator.add(new ValidationMessage("Selecione uma atividade", "regra.atividade.id"));
        } else {

            int grupoHora = this.regraGrupoInfo.getRegraGrupo().getMaximoHoras();
            if (regra.getMaximoHoras() > grupoHora) {
                validator.add(new ValidationMessage("O máximo de horas para essa atividade é: " + grupoHora, "regra.atividade.id"));
            }
        }

        regra.setCurso(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());
        
        Regra busca = this.regraDAO.findByAtividadeCurso(regra.getAtividade().getId(), regra.getCurso().getId());
        
        if (busca != null && busca.getId() != regra.getId()) {
            validator.add(new ValidationMessage("Já existe um regra cadastrada com essa atividade", "regra.atividade.id"));
        }

        this.validator.validate(regra);

        validator.onErrorRedirectTo(this).edit(regra.getId());

        this.regraDAO.update(regra);

        result.include("success", "alterada");

        this.result.redirectTo(this).index();
    }

    @Post("/regras")
    public void cadastrar(final Regra regra) {
        if (regra.getAtividade() == null || regra.getAtividade().getId() == null) {
            validator.add(new ValidationMessage("Selecione uma atividade", "regra.atividade.id"));
        } else {
            int grupoHora = this.regraGrupoInfo.getRegraGrupo().getMaximoHoras();

            if (regra.getMaximoHoras() > grupoHora) {
                validator.add(new ValidationMessage("O máximo de horas para essa atividade é: " + grupoHora, "regra.atividade.id"));
            }
        }

        regra.setCurso(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());

        if (this.regraDAO.existeRegra(regra)) {
            validator.add(new ValidationMessage("Já existe um regra cadastrada com esse curso e atividade", "regra.atividade.id"));
        }

        this.validator.validate(regra);

        validator.onErrorRedirectTo(this).create();

        this.regraDAO.create(regra);

        result.include("success", "cadastrada");

        this.result.redirectTo(RegraController.class).index();
    }

    @Get
    @Path("/regras/buscaAtividade.json")
    public void buscaAtividadeJson(Long id) {
        List<Atividade> atividades = this.atividadeDAO.findByGroupId(id);
        result.use(Results.json()).withoutRoot().from(atividades).serialize();
    }

    @Get("/regras/buscaAtividadePeloId.json")
    public void buscaAtividade(Long id) {
        Aluno aluno = alunoDAO.findByIdUsuario(usuario.getId());
        Regra regra = this.regraDAO.findByAtividadeCurso(id, aluno.getCurso().getId());
        result.use(Results.json()).withoutRoot().from(regra).serialize();
    }

    @Get("/regras/buscaAtividadePorAtividadeCurso.json")
    public void buscaAtividade2(Long id, Long idSolicitante) {
        Aluno aluno = alunoDAO.findById(idSolicitante);
        Regra regra = this.regraDAO.findByAtividadeCurso(id, aluno.getCurso().getId());
        result.use(Results.json()).withoutRoot().from(regra).serialize();
    }

    @Get
    @Path("/regras/buscaGrupo.json")
    public void buscaGrupoJson(Long id) {
        Grupo grupo = this.grupoDAO.findById(id);
        CoordenadorCurso cc = coordenadorCursoDAO.findByUsuario(usuario.getId());
        
        RegraGrupo regra = regraGrupoDAO.findByCursoAndGrupo(cc.getCurso(), grupo);
        
        result.use(Results.json()).withoutRoot().from(regra).serialize();
    }        
    
    @Post("regras/relatorio")
    public Download relatorioRegras(Long id) throws JRException {                                
                
        InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/DescricaoAtividadesExtracurriculares.jrxml");
        
        List<Regra> regras = this.regraDAO.findByCurso(id);
                
        JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

        Map parameters = new HashMap();
        parameters.put("nomeCurso", "Sistemas de Informação");
        parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
        parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(regras));


        // exportacao do relatorio para outro formato, no caso PDF

        JRExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream exported = new ByteArrayOutputStream();
        
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

        // Export the PDF file
        exporter.exportReport();

        byte[] content = exported.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(content);

        return new InputStreamDownload(inputStream, "application/pdf", "RelatorioDescricaoAC.pdf");
    }
}