package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.edu.ufam.icomp.projeto4.ListaHorarios;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorAcademicoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.DisciplinaDAO;
import br.edu.ufam.icomp.projeto4.dao.HorarioTurmaDAO;
import br.edu.ufam.icomp.projeto4.dao.InscricaoMonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.MonitoriaDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoInsMonDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.HorarioJson;
import br.edu.ufam.icomp.projeto4.model.HorarioTurma;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
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

@Resource
public class MonitoriaController {

    private Result result;
    private Validator validator;
    private MonitoriaDAO monitoriaDAO;
    private CursoDAO cursoDAO;
    private ProfessorDAO professorDAO;
    private DisciplinaDAO disciplinaDAO;
    private PeriodoInsMonDAO periodoInsMonDAO;
    private RegraDAO regraDAO;
    private InscricaoMonitoriaDAO inscricaoDAO;
    private AlunoDAO alunoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private CoordenadorAcademicoDAO coordenadorAcademicoDAO;
    private Usuario usuario;
    private CriteriaBuilder criteriaBuilder;
    private ServletContext context;
    private SecretariaDAO secretariaDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private ListaHorarios listaHorarios;
    private HorarioTurmaDAO horarioTurmaDAO;

    public MonitoriaController(CoordenadorCursoDAO coordenadorCursoDAO, CriteriaBuilder criteriaBuilder, InscricaoMonitoriaDAO inscricaoDAO,
            SessionData session, AlunoDAO alunoDAO, RegraDAO regraDAO, MonitoriaDAO monitoriaDAO, CursoDAO cursoDAO,
            ProfessorDAO professorDAO, DisciplinaDAO disciplinaDAO, CoordenadorAcademicoDAO coordenadorAcademicoDAO,
            SecretariaDAO secretariaDAO, Result result, Validator validator, PeriodoInsMonDAO periodoInsMonDAO,
            ServletContext context, PeriodoLetivoDAO periodoLetivoDAO, ListaHorarios listaHorarios, HorarioTurmaDAO horarioTurmaDAO) {
        this.result = result;
        this.validator = validator;
        this.monitoriaDAO = monitoriaDAO;
        this.cursoDAO = cursoDAO;
        this.professorDAO = professorDAO;
        this.disciplinaDAO = disciplinaDAO;
        this.periodoInsMonDAO = periodoInsMonDAO;
        this.regraDAO = regraDAO;
        this.alunoDAO = alunoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.inscricaoDAO = inscricaoDAO;
        usuario = session.getUsuario();
        this.criteriaBuilder = criteriaBuilder;
        this.coordenadorAcademicoDAO = coordenadorAcademicoDAO;
        this.secretariaDAO = secretariaDAO;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.listaHorarios = listaHorarios;
        this.horarioTurmaDAO = horarioTurmaDAO;
    }

    @Get("/monitorias")
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    public List<Monitoria> index() {
        if (usuario.getRole().equals(Perfil.COORDENADOR)) {
            return monitoriaDAO.listByCurso(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso().getId());
        } else {
            return monitoriaDAO.listByCursos(coordenadorAcademicoDAO.findByUsuario(usuario.getId()).getCursos());
        }
    }

    @Get("/monitorias/analisadas")
    @Permission({Perfil.SECRETARIA, Perfil.COORDENADORACAD, Perfil.COORDENADOR})
    public void historicoMonAnalisada() {
        this.result.include("cursoList", getCursos());

        this.result.include("professorList", professorDAO.findAll());

        this.result.include("disciplinaList", disciplinaDAO.findAll());

        List<Aluno> alunos = getAlunos();

        this.result.include("alunoList", alunos);

        this.result.include("monitoriaList", getMonitorias());

        this.result.include("inscricaoList", this.inscricaoDAO.listByAlunosCurso(alunos));
    }

    @Post("/monitorias/analisadas")
    @Permission({Perfil.SECRETARIA, Perfil.COORDENADORACAD, Perfil.COORDENADOR})
    public void historicoMonAnalisada(InscricaoMonitoria inscricao) throws JRException {
        this.result.include("cursoList", getCursos());
        this.result.include("professorList", professorDAO.findAll());
        this.result.include("disciplinaList", disciplinaDAO.findAll());

        List<Aluno> alunos = getAlunos();

        this.result.include("alunoList", alunos);

        this.result.include("inscricao", inscricao);

        this.result.include("monitoriaList", getMonitorias());

        this.result.include("inscricaoList", this.inscricaoDAO.listByAlunosCurso(alunos));
    }

    @Get("/monitorias/create")
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    public Monitoria create() {
        result.include("professorList", professorDAO.findAll());
        result.include("disciplinaList", disciplinaDAO.findAll());
        result.include("operacao", "Cadastro");
        result.include("curso", coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.result.include("ano", cal.get(Calendar.YEAR));
        this.result.include("mes", cal.get(Calendar.MONTH));
        this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));

        listaHorarios.clean();

        return new Monitoria();
    }

    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    @Post("/monitorias/inclui.json")
    public void inclui(String start, String end) throws ParseException {
        HorarioTurma horarioTurma = new HorarioTurma();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        horarioTurma.setInicio(sdf.parse(start));
        horarioTurma.setFim(sdf.parse(end));
        horarioTurma.setId(listaHorarios.next());

        this.listaHorarios.add(horarioTurma);

        result.use(Results.json()).withoutRoot().from(horarioTurma).serialize();
    }

    @Post("/monitorias/exclui.json")
    public void exclui(int idHorario) {
        HorarioTurma horarioTurma = listaHorarios.remove(idHorario);

        result.use(Results.json()).withoutRoot().from(horarioTurma).serialize();
    }

    @Get("/monitorias/list.json")
    public void getFrequencias() {
        List<HorarioTurma> horarios = this.listaHorarios.getHorarios();

        List<HorarioJson> horariosJson = new ArrayList<HorarioJson>();

        for (HorarioTurma horario : horarios) {
            HorarioJson horarioJson = new HorarioJson(horario);
            horariosJson.add(horarioJson);
            System.out.println("++++++++++++++++++++++++++++++" + horarioJson);
        }

        result.use(Results.json()).withoutRoot().from(horariosJson).serialize();
    }

    @Get("/monitorias/{id}/edit")
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    public Monitoria edit(Long id) {

        result.include("cursoList", getCursos());
        result.include("professorList", professorDAO.findAll());
        result.include("disciplinaList", disciplinaDAO.findAll());
        result.include("operacao", "Edição");

        Monitoria monitoria = this.monitoriaDAO.findById(id);

        listaHorarios.clean();

        if (!monitoria.getHorario().isEmpty()) {
            listaHorarios.setHorarios(monitoria.getHorario());
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.result.include("ano", cal.get(Calendar.YEAR));
        this.result.include("mes", cal.get(Calendar.MONTH));
        this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));


        result.include("curso", monitoria.getCurso());

        return monitoria;
    }

    @Get("/monitorias/{id}/show")
    public Monitoria show(Long id) {
        Monitoria monitoria = this.monitoriaDAO.findById(id);

        listaHorarios.clean();

        if (!monitoria.getHorario().isEmpty()) {
            listaHorarios.setHorarios(monitoria.getHorario());
            System.out.println("========================" + monitoria.getHorario().size());
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.result.include("ano", cal.get(Calendar.YEAR));
        this.result.include("mes", cal.get(Calendar.MONTH));
        this.result.include("dia", cal.get(Calendar.DAY_OF_MONTH));


        result.include("curso", monitoria.getCurso());

        return monitoria;
    }

    @Get("/monitorias/{id}/detalhes")
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    public void infoMon(Long id) {
        result.include("inscricao", inscricaoDAO.findById(id));
    }

    @Get("/monitorias/{id}/remove")
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD})
    public void remove(Long id) {
        Monitoria monitoria = this.monitoriaDAO.findById(id);

        if (monitoria == null) {
            validator.add(new ValidationMessage("Desculpe! Monitoria não encontrada", "monitoria.id"));
        }

        List<InscricaoMonitoria> inscricoes = inscricaoDAO.findByMonitoria(monitoria.getId());

        if (!inscricoes.isEmpty()) {
            validator.add(new ValidationMessage("Desculpe! Monitoria não pode ser removida, existem inscrições associadas a ela", "monitoria.id"));
        }

        validator.onErrorRedirectTo(this).index();

        monitoriaDAO.delete(monitoria);

        result.include("success", "removida");

        result.redirectTo(this).index();
    }

    @Post("/monitorias")
    public void cadastrar(Monitoria monitoria) {
        monitoria.setCurso(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());

        List<PeriodoLetivo> periodos = this.periodoLetivoDAO.findProximoPeriodo();
        if (!periodos.isEmpty()) {
            monitoria.setPeriodo(periodos.get(0));
        } else {
            validator.add(new ValidationMessage("Desculpe! Monitoria não pode ser cadastrada, não há um próximo período letivo ao qual associá-la", "monitoria.id"));
        }
        //  monitoria.setPeriodo(periodoInsMonDAO.findAll().get(0));

        validator.validate(monitoria);

        validator.onErrorRedirectTo(this).create();

        List<HorarioTurma> horarios = new ArrayList<HorarioTurma>();

        for (HorarioTurma horario : listaHorarios.getHorarios()) {
            horario.setId(null);
            horarioTurmaDAO.create(horario);
            horarios.add(horario);
            System.out.println("----------------" + horario);
        }

        monitoria.setHorario(horarios);

        monitoriaDAO.create(monitoria);

        listaHorarios.clean();

        result.include("success", "cadastrada");

        result.redirectTo(this).index();
    }

    @Put("/monitorias")
    public void altera(Monitoria monitoria) {
        validator.validate(monitoria);

        validator.onErrorRedirectTo(this).edit(monitoria.getId());

        Monitoria buscada = this.monitoriaDAO.findById(monitoria.getId());

        List<HorarioTurma> horariosAntigo = buscada.getHorario();

        List<HorarioTurma> horarios = new ArrayList<HorarioTurma>();

        List<HorarioTurma> deletar = new ArrayList<HorarioTurma>();

        List<HorarioTurma> listAlteracao = listaHorarios.getHorarios();

        for (HorarioTurma horario : horariosAntigo) {
            if (listAlteracao.indexOf(horario) == -1) {
                deletar.add(horario);
            } else {
                horarios.add(horario);
            }
        }

        for (HorarioTurma horario : listAlteracao) {
            if (horarios.indexOf(horario) == -1) {
                horario.setId(null);
                horarioTurmaDAO.create(horario);
                horarios.add(horario);
            }
        }
        monitoria.setHorario(horarios);

        monitoriaDAO.update(monitoria);

        listaHorarios.clean();

        for (HorarioTurma horario : deletar) {
            horarioTurmaDAO.delete(horario);
        }

        result.include("success", "alterada");

        result.redirectTo(this).index();
    }

    @Post("/monitorias/historico")
    public Download historico(InscricaoMonitoria inscricao) throws JRException {

        List<InscricaoMonitoria> inscricoes;

        Long idCurso = inscricao.getMonitoria().getCurso().getId();

        inscricoes = this.inscricaoDAO.search(
                idCurso,
                inscricao.getMonitoria().getDisciplina().getId(),
                inscricao.getMonitoria().getProfessor().getId(),
                inscricao.getInscrito().getId());

        //System.out.println(inscricao.getMonitoria().getCurso().getId() +" "+inscricao.getMonitoria().getDisciplina().getId());
        InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/HistoricoMon.jrxml");

        JasperReport report = JasperCompileManager.compileReport(resourceAsStream);
        Map parameters = new HashMap();

//        for(int i = 0 ; i < inscricoes.size() ; i++){
//        
//        //parameters.put("nomeAluno", inscricoes.get(i).getInscrito().getNomeAluno());  
//        parameters.put("monitoria", inscricoes.get(i));  
////        parameters.put("curso", aluno.getCurso().getCodigo() + " - " + aluno.getCurso().getCurso());  
////        parameters.put("anoIngresso", aluno.getDataIngresso());        
//        parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
//        parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));
//        
//        }

        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(inscricoes));


        // exportacao do relatorio para outro formato, no caso PDF

        JRExporter exporter = new JRPdfExporter();

        ByteArrayOutputStream exported = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

        exporter.exportReport();

        byte[] content = exported.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(content);

        return new InputStreamDownload(inputStream, "application/pdf", "RelatorioClientes.pdf");
    }

    private List<Monitoria> getMonitorias() {
        if (usuario.getRole().equals(Perfil.COORDENADOR)) {
            return monitoriaDAO.listByCurso(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso().getId());
        } else if (usuario.getRole().equals(Perfil.COORDENADORACAD)) {
            return monitoriaDAO.listByCursos(coordenadorAcademicoDAO.findByUsuario(usuario.getId()).getCursos());
        } else {
            return monitoriaDAO.listByCursos(secretariaDAO.findByUsuario(usuario.getId()).getCursos());
        }
    }

    private List<Aluno> getAlunos() {
        if (usuario.getRole().equals(Perfil.COORDENADOR)) {
            return alunoDAO.findByCursoId(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso().getId());
        } else if (usuario.getRole().equals(Perfil.COORDENADORACAD)) {
            return alunoDAO.findByCurso(coordenadorAcademicoDAO.findByUsuario(usuario.getId()).getCursos());
        } else {
            return alunoDAO.findByCurso(secretariaDAO.findByUsuario(usuario.getId()).getCursos());
        }
    }

    private List<Curso> getCursos() {
        if (usuario.getRole().equals(Perfil.COORDENADOR)) {
            List<Curso> cursos = new ArrayList<Curso>();
            cursos.add(coordenadorCursoDAO.findByUsuario(usuario.getId()).getCurso());
            return cursos;
        } else if (usuario.getRole().equals(Perfil.COORDENADORACAD)) {
            return coordenadorAcademicoDAO.findByUsuario(usuario.getId()).getCursos();
        } else {
            return secretariaDAO.findByUsuario(usuario.getId()).getCursos();
        }
    }
}
