/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.IndexController;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorAcademicoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraDAO;
import br.edu.ufam.icomp.projeto4.dao.RegraGrupoDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.CoordenadorAcademico;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.Regra;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Thiago Santos
 */
@Resource
public class RelatorioAtividade {

    private ServletContext context;
    private RegraDAO regraDAO;
    private SessionData session;
    private CursoDAO cursoDAO;
    private AlunoDAO alunoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private CoordenadorAcademicoDAO coordenadorAcademicoDAO;
    private SecretariaDAO secretariaDAO;
    private Result result;
    private Validator validator;
    private RegraGrupoDAO regraGrupoDAO;

    public RelatorioAtividade(ServletContext context, RegraDAO regraDAO, SessionData session, CursoDAO cursoDAO, AlunoDAO alunoDAO,
            CoordenadorCursoDAO coordenadorCursoDAO, CoordenadorAcademicoDAO coordenadorAcademicoDAO, SecretariaDAO secretariaDAO, Result result, Validator validator, RegraGrupoDAO regraGrupoDAO) {
        this.context = context;
        this.regraDAO = regraDAO;
        this.session = session;
        this.cursoDAO = cursoDAO;
        this.alunoDAO = alunoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.coordenadorAcademicoDAO = coordenadorAcademicoDAO;
        this.secretariaDAO = secretariaDAO;
        this.result = result;
        this.validator = validator;
        this.regraGrupoDAO = regraGrupoDAO;
    }

    @Get("regras/relatorioAtividade/selecao")
    public void selectCurso() {

        List<Curso> cursos = new ArrayList<Curso>();

        if (session.getLogado()) {
            if (session.getUsuario().getRole().equals(Perfil.ALUNO)) {
                Aluno aluno = this.alunoDAO.findByIdUsuario(session.getUsuario().getId());
                this.result.redirectTo(RelatorioAtividade.class).relatorioRegras(aluno.getCurso().getId());
            } else if (session.getUsuario().getRole().equals(Perfil.COORDENADOR)) {
                CoordenadorCurso coordenadorCurso = this.coordenadorCursoDAO.findByUsuario(session.getUsuario().getId());
                this.result.redirectTo(RelatorioAtividade.class).relatorioRegras(coordenadorCurso.getCurso().getId());
            } else if (session.getUsuario().getRole().equals(Perfil.COORDENADORACAD)) {
                CoordenadorAcademico coordenadorAcademico = this.coordenadorAcademicoDAO.findByUsuario(session.getUsuario().getId());
                cursos.addAll(coordenadorAcademico.getCursos());
            } else if (session.getUsuario().getRole().equals(Perfil.SECRETARIA)) {
                Secretaria secretaria = this.secretariaDAO.findByUsuario(session.getUsuario().getId());
                cursos.addAll(secretaria.getCursos());
            }

            this.result.include("cursoList", cursos);
        } else {
            this.result.redirectTo(IndexController.class).index();
        }
    }

    @Get("regras/relatorioAtividade/{idCurso}")
    public Download relatorioRegras(Long idCurso) {

        Curso curso = this.cursoDAO.findById(idCurso);

        List<Regra> regras = this.regraDAO.findByCurso(idCurso);
        
        if (regras.isEmpty()) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi registrado nenhuma atividade para o curso de " + curso.getCurso(), "."));
        }

        this.validator.onErrorRedirectTo(IndexController.class).index();

        List<Grupo> grupos = new ArrayList<Grupo>();

        for (Regra regra : regras) {
            RegraGrupo regraGrupo = this.regraGrupoDAO.findByCursoAndGrupo(curso, regra.getAtividade().getGrupo());

            if (!grupos.contains(regraGrupo.getGrupo())) {
                String nome = regra.getAtividade().getGrupo().getNome();
                regra.getAtividade().getGrupo().setNome(nome + " - CHM: " + regraGrupo.getMaximoHoras() + "h");

                grupos.add(regraGrupo.getGrupo());
            }
        }
        
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/projeto4/jasper/DescricaoAtividadesExtracurriculares.jrxml");

            JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

            Map parameters = new HashMap();
            parameters.put("nomeCurso", curso.getCurso());
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
        } catch (JRException ex) {
            Logger.getLogger(RelatorioAtividade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
