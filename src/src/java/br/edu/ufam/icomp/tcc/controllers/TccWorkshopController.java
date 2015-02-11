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
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.tcc.dao.TccAvaliadorDAO;
import br.edu.ufam.icomp.tcc.dao.TccLocaisDAO;
import br.edu.ufam.icomp.tcc.dao.TccWorkshopDAO;
import br.edu.ufam.icomp.tcc.model.TccAvaliador;
import br.edu.ufam.icomp.tcc.model.TccLocais;
import br.edu.ufam.icomp.tcc.model.TccWorkshop;
import java.util.List;

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccWorkshopController {
    private final Result result;
    private final Validator validator;
    private final TccWorkshopDAO tccWorkshopDAO;
    private final TccLocaisDAO tccLocaisDAO;
    private final TccAvaliadorDAO tccAvaliadorDAO;
    
    public TccWorkshopController (Result result,TccWorkshopDAO tccWorkshopDAO, Validator validator, TccLocaisDAO tccLocaisDAO,
            TccAvaliadorDAO tccAvaliadorDAO){
        this.result = result;
        this.validator = validator;
        this.tccWorkshopDAO = tccWorkshopDAO;
        this.tccLocaisDAO = tccLocaisDAO;
        this.tccAvaliadorDAO = tccAvaliadorDAO;
    }


    @Get("/tccworkshop")
    public void index() {
             
        List<TccWorkshop> tccWorkshop = this.tccWorkshopDAO.findAll();
        
        this.result.include("tccWorkshopList", tccWorkshop);
    }
    
    @Get("/tccworkshop/{id}/edit")
    public TccWorkshop edit(Long id) {
        List<TccLocais> tccLocais = this.tccLocaisDAO.findIfAtivo();
        List<TccAvaliador> tccAvaliadores = this.tccAvaliadorDAO.findAll();

        TccWorkshop tccWorkshop = tccWorkshopDAO.findById(id);
        
        if (tccWorkshop == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Workshop não foi encontrado.", "tccWorkshop.id"));
        }
        this.validator.onErrorRedirectTo(TccWorkshopController.class).index();
        
        this.result.include("tccLocais", tccLocais);
        this.result.include("tccAvaliadores", tccAvaliadores);
        this.result.include("operacao", "Edição");

        return tccWorkshop;
    }
    
    @Get("/tccworkshop/create")
    public void create() {
        List<TccLocais> tccLocais = this.tccLocaisDAO.findIfAtivo();
        List<TccAvaliador> tccAvaliadores = this.tccAvaliadorDAO.findAll();
        
        this.result.include("tccLocais", tccLocais);
        this.result.include("tccAvaliadores", tccAvaliadores);
        this.result.include("operacao", "Cadastro");
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
    
    @Get("/tccworkshop/{id}/remove")
    public void remove(Long id) {
        TccWorkshop tccWorkshop = this.tccWorkshopDAO.findById(id);

        if (tccWorkshop == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Workshop não foi encontrado.", "tccWorkshop.id"));
        }

        this.validator.onErrorRedirectTo(TccWorkshopController.class).index();

        this.tccWorkshopDAO.delete(tccWorkshop);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Post("/tccworkshop")
    public void cadastrar(TccWorkshop tccWorkshop) {
                
        this.tccWorkshopDAO.create(tccWorkshop);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccWorkshopController.class).index();
    }
    
    @Put("/tccworkshop")
    public void altera(TccWorkshop tccWorkshop) {
        this.tccWorkshopDAO.update(tccWorkshop);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccWorkshopController.class).index();
    }

}