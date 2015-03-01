package br.edu.ufam.icomp.tcc.controllers;

/**
 *
 * @author mhcabral
 */

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AtividadeDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.dao.TccAvaliadorDAO;
import br.edu.ufam.icomp.tcc.dao.TccLocaisDAO;
import br.edu.ufam.icomp.tcc.dao.TccWorkshopDAO;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import br.edu.ufam.icomp.tcc.model.TccAvaliador;
import br.edu.ufam.icomp.tcc.model.TccLocais;
import br.edu.ufam.icomp.tcc.model.TccWorkshop;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccWorkshopController {
    private final Result result;
    private final Validator validator;
    private final SessionData sessionData;
    private final TccWorkshopDAO tccWorkshopDAO;
    private final TccLocaisDAO tccLocaisDAO;
    private final TccAvaliadorDAO tccAvaliadorDAO;
    private final TccAtividadeDAO tccAtividadeDAO;
    
    public TccWorkshopController (Result result,TccWorkshopDAO tccWorkshopDAO, Validator validator, SessionData sessionData, 
            TccLocaisDAO tccLocaisDAO, TccAvaliadorDAO tccAvaliadorDAO, TccAtividadeDAO tccAtividadeDAO){
        this.result = result;
        this.validator = validator;
        this.sessionData = sessionData;
        this.tccWorkshopDAO = tccWorkshopDAO;
        this.tccLocaisDAO = tccLocaisDAO;
        this.tccAvaliadorDAO = tccAvaliadorDAO;
        this.tccAtividadeDAO = tccAtividadeDAO;
    }


    @Get("/tccworkshop")
    public void index() {
             
        List<TccWorkshop> tccWorkshop = this.tccWorkshopDAO.findAll();
        
        this.result.include("tccWorkshopList", tccWorkshop);
    }
    
    @Get("/tccworkshop/{id}/edit")
    public TccWorkshop edit(Long id) {
        PeriodoLetivo periodoAtual = this.sessionData.getLetivoAtual();
        List<TccLocais> tccLocais = this.tccLocaisDAO.findIfAtivo();
        List<TccAvaliador> tccAvaliadores = this.tccAvaliadorDAO.findAll();
        List<TccAtividade> tccAtividades = this.tccAtividadeDAO.findByPeriodo(periodoAtual.getId());

        TccWorkshop tccWorkshop = tccWorkshopDAO.findById(id);
        
        //Valida Workshop
        if (tccWorkshop == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Workshop não foi encontrado.", "tccWorkshop.id"));
        }
        this.validator.onErrorRedirectTo(TccWorkshopController.class).index();
        
        //Valida Data Workshop
        if (tccAtividades.get(10).getDatalimite() == null || tccAtividades.get(11).getDatalimite() == null ) {
            this.validator.add(new ValidationMessage("É preciso uma data na atividade de workshop para continuar.", ""));
        }
        this.validator.onErrorRedirectTo(TccWorkshopController.class).index();
        
        //Lista de Datas de Workshop
        List<Date> datasWorkshop = new ArrayList();
        datasWorkshop.add(tccAtividades.get(10).getDatalimite());
        datasWorkshop.add(tccAtividades.get(11).getDatalimite());
        
        List<String> horasWorkshop = new ArrayList();
        horasWorkshop.add("07:30");
        horasWorkshop.add("08:30");
        horasWorkshop.add("09:30");
        horasWorkshop.add("10:30");
        horasWorkshop.add("11:30");
        horasWorkshop.add("12:30");
        horasWorkshop.add("13:30");
        horasWorkshop.add("14:30");
        horasWorkshop.add("15:30");
        horasWorkshop.add("16:30");
        horasWorkshop.add("17:30");
        horasWorkshop.add("18:30");
        horasWorkshop.add("19:30");
        horasWorkshop.add("20:30");
        horasWorkshop.add("21:30");
               
        this.result.include("tccLocais", tccLocais);
        this.result.include("tccAvaliadores", tccAvaliadores);
        this.result.include("datasWorkshop", datasWorkshop);
        this.result.include("horasWorkshop", horasWorkshop);
        this.result.include("operacao", "Edição");

        return tccWorkshop;
    }
    
    @Get("/tccworkshop/{id}/show")
    public TccWorkshop show(Long id) {
        TccWorkshop tccWorkshop = this.tccWorkshopDAO.findById(id);

        if (tccWorkshop == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Workshop não foi encontrado.", "tccWorkshop.id"));
        }

        this.validator.onErrorRedirectTo(TccWorkshopController.class).index();
        
        return tccWorkshop;
    }
    
    @Put("/tccworkshop")
    public void altera(TccWorkshop tccWorkshop) {
        List<TccWorkshop> tccWorkshops = this.tccWorkshopDAO.findOcupado(tccWorkshop.getData(), tccWorkshop.getTcclocais().getId(), tccWorkshop.getHora());
        
         //Valida Ocupação
        if (tccWorkshops.size() > 0 && tccWorkshop.getId() != tccWorkshops.get(0).getId()) {
            this.validator.add(new ValidationMessage("Desculpe, A seleção de local, data e hora já ocupada", "tccWorkshop.id"));
        }
        this.validator.onErrorRedirectTo(TccWorkshopController.class).edit(tccWorkshop.getId());
        
        this.tccWorkshopDAO.update(tccWorkshop);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccWorkshopController.class).index();
    }

}