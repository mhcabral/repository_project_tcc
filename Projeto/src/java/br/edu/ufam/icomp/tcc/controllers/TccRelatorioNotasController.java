/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.controllers;

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
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import br.edu.ufam.icomp.tcc.dao.TccNotasDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
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
public class TccRelatorioNotasController {

    private ServletContext context;
    private final SessionData sessionData;
    private CursoDAO cursoDAO;
    private AlunoDAO alunoDAO;
    private CoordenadorCursoDAO coordenadorCursoDAO;
    private CoordenadorAcademicoDAO coordenadorAcademicoDAO;
    private SecretariaDAO secretariaDAO;
    private Result result;
    private Validator validator;
    private final TccNotasDAO tccnotasDAO;
    private final PeriodoLetivoDAO periodoletivoDAO;
    private final TccTccDAO tcctccDAO;
    private final ProfessorDAO professorDAO;

    public TccRelatorioNotasController(ServletContext context, TccNotasDAO tccnotasDAO, SessionData sessionData, CursoDAO cursoDAO, AlunoDAO alunoDAO,
            CoordenadorCursoDAO coordenadorCursoDAO, CoordenadorAcademicoDAO coordenadorAcademicoDAO, SecretariaDAO secretariaDAO, Result result, 
            Validator validator, PeriodoLetivoDAO periodoletivoDAO, TccTccDAO tccTccDAO, ProfessorDAO professorDAO) {
        this.context = context;
        this.tccnotasDAO = tccnotasDAO;
        this.sessionData = sessionData;
        this.cursoDAO = cursoDAO;
        this.alunoDAO = alunoDAO;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.coordenadorAcademicoDAO = coordenadorAcademicoDAO;
        this.secretariaDAO = secretariaDAO;
        this.result = result;
        this.validator = validator;
        this.periodoletivoDAO = periodoletivoDAO;
        this.tcctccDAO = tccTccDAO;
        this.professorDAO = professorDAO;
    }

    @Get("/tcc/relatorioNotas")
    public Download tccRelatorioNotas() throws SQLException {
       
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();

        
        List<TccTcc> tccTccs = tcctccDAO.findByPeriodo(periodoAtual.getId());
        //deixei tcctccDAO.findAll pq não tenho o tcctccDAO.findByPeriodo.
        
        if (tccTccs.isEmpty()) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi registrado nenhuma nota para o periodo " + periodoAtual.toString(), "."));
        }

        this.validator.onErrorRedirectTo(IndexController.class).index();
        
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/tcc/jasper/reportnotas.jrxml");

            JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

            Map parameters = new HashMap();
            parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
            parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));
            
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(tccTccs));
            //coloquei tcctcc mais tem que ser uma lista contendo o resultado das colunas a serem exibidas no relatorio
            // exemplo: nome_do_aluno_de_usuario / tcctcc_titulo / tccnotas_nota1 / tccnotas_nota2 / tccnotas_nota3

            // exportacao do relatorio para outro formato, no caso PDF

            JRExporter exporter = new JRPdfExporter();

            ByteArrayOutputStream exported = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            // Export the PDF file
            exporter.exportReport();

            byte[] content = exported.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(content);

            return new InputStreamDownload(inputStream, "application/pdf", "RelatorioNotas.pdf");
        } catch (JRException ex) {
            Logger.getLogger(TccRelatorioNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    @Get("/tcc/formularioavaliacao")
    public Download tccFormularioAvaliacao() throws SQLException {
        
        Usuario usuario = sessionData.getUsuario();
        Professor professor = professorDAO.findByUsuario(usuario.getId());
       
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        
        List<TccTcc> tccTccs = tcctccDAO.findTccByProfessor(professor.getId(), periodoAtual.getId());
        
        if (tccTccs.isEmpty()) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi registrado nenhum workshop para o periodo " + periodoAtual.toString(), "."));
        }

        this.validator.onErrorRedirectTo(IndexController.class).index();
        
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/tcc/jasper/tccAvaliacao.jrxml");

            JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

            Map parameters = new HashMap();
            parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
            parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));
            
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(tccTccs));
            //coloquei tcctcc mais tem que ser uma lista contendo o resultado das colunas a serem exibidas no relatorio
            // exemplo: nome_do_aluno_de_usuario / tcctcc_titulo / tccnotas_nota1 / tccnotas_nota2 / tccnotas_nota3

            // exportacao do relatorio para outro formato, no caso PDF

            JRExporter exporter = new JRPdfExporter();

            ByteArrayOutputStream exported = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            // Export the PDF file
            exporter.exportReport();

            byte[] content = exported.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(content);

            return new InputStreamDownload(inputStream, "application/pdf", "RelatorioNotas.pdf");
        } catch (JRException ex) {
            Logger.getLogger(TccRelatorioNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    @Get("/tcc/listapresenca")
    public Download tccListaPresenca() throws SQLException {
        
        Usuario usuario = sessionData.getUsuario();
        Professor professor = professorDAO.findByUsuario(usuario.getId());
       
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        
        List<TccTcc> tccTccs = tcctccDAO.findTccByProfessor(professor.getId(), periodoAtual.getId());
        
        if (tccTccs.isEmpty()) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi registrado nenhum workshop para o periodo " + periodoAtual.toString(), "."));
        }

        this.validator.onErrorRedirectTo(IndexController.class).index();
        
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/tcc/jasper/tcclistapresenca.jrxml");

            JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

            Map parameters = new HashMap();
            parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
            parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));
            
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(tccTccs));
            //coloquei tcctcc mais tem que ser uma lista contendo o resultado das colunas a serem exibidas no relatorio
            // exemplo: nome_do_aluno_de_usuario / tcctcc_titulo / tccnotas_nota1 / tccnotas_nota2 / tccnotas_nota3

            // exportacao do relatorio para outro formato, no caso PDF

            JRExporter exporter = new JRPdfExporter();

            ByteArrayOutputStream exported = new ByteArrayOutputStream();

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

            // Export the PDF file
            exporter.exportReport();

            byte[] content = exported.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(content);

            return new InputStreamDownload(inputStream, "application/pdf", "RelatorioNotas.pdf");
        } catch (JRException ex) {
            Logger.getLogger(TccRelatorioNotasController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
