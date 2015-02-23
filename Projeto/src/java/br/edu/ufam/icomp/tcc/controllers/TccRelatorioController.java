/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.IndexController;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
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
 * @author andre
 */

@Resource
@Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccRelatorioController {
    
    private ServletContext context;
    private Validator validator;
    private final SessionData sessionData;
    private final TccAtividadeDAO tccAtividadeDAO;
    
    public TccRelatorioController (ServletContext context, Validator validator, TccAtividadeDAO tccAtividadeDAO, SessionData sessionData){
        this.context = context;
        this.validator = validator;
        this.tccAtividadeDAO = tccAtividadeDAO;
        this.sessionData = sessionData;
    }


    @Get("/tcc/relatorio/index")
    public void main() {
    }
    
    @Get("/tcc/atividades")
    public Download tccListaPresenca() throws SQLException {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        List<TccAtividade> tccAtividades = tccAtividadeDAO.findByPeriodo(periodoAtual.getId());
        
        if (tccAtividades.isEmpty()) {
            this.validator.add(new ValidationMessage("Desculpe! Não foi registrado nenhum workshop para o periodo " + periodoAtual.toString(), "."));
        }
        this.validator.onErrorRedirectTo(IndexController.class).index();
        
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/br/edu/ufam/icomp/tcc/jasper/tccatividades.jrxml");

            JasperReport report = JasperCompileManager.compileReport(resourceAsStream);

            Map parameters = new HashMap();
            parameters.put("imgUfam", context.getRealPath("/images/logo_ufam.png"));
            parameters.put("imgBrasao", context.getRealPath("/images/brasao.gif"));
            
            JasperPrint print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(tccAtividades));
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
