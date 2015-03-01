package br.edu.ufam.icomp.tcc.controllers;

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
import br.edu.ufam.icomp.tcc.model.TccAvaliador;
import java.util.List;

/**
 *
 * @author TAMMY
 */

@Resource
@Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccAvaliadorController {
    private final Result result;
    private final Validator validator;
    private final TccAvaliadorDAO tccAvaliadorDAO;
    
    public TccAvaliadorController (Result result,TccAvaliadorDAO tccAvaliadorDAO, Validator validator){
        this.result = result;
        this.validator = validator;
        this.tccAvaliadorDAO = tccAvaliadorDAO;
    }


    @Get("/tccavaliador")
    public void index() {
             
        List<TccAvaliador> tccAvaliador = this.tccAvaliadorDAO.findAll();
        
        this.result.include("tccAvaliadorList", tccAvaliador);
    }
    
    @Get("/tccavaliador/{id}/edit")
    public TccAvaliador edit(Long id) {

        TccAvaliador tccAvaliador = tccAvaliadorDAO.findById(id);
        
        if (tccAvaliador == null) {
            this.validator.add(new ValidationMessage("A edição não foi salva.", "tccAvaliador.id"));
        }
        
        this.validator.onErrorRedirectTo(TccAvaliadorController.class).index();
        return tccAvaliador;
    }
    
    @Get("/tccavaliador/create")
    public void create() {
        
        this.result.include("operacao", "Cadastro");
    }
    
    @Get("/tccavaliador/{id}/show")
    public TccAvaliador show(Long id) {
        TccAvaliador tccAvaliador = this.tccAvaliadorDAO.findById(id);

        if (tccAvaliador == null) {
            this.validator.add(new ValidationMessage("Avaliador não cadastrado.", "tccAvaliador.id"));
        }

        this.validator.onErrorRedirectTo(TccAvaliadorController.class).index();
        
        return tccAvaliador;
    }
    
    @Get("/tccavaliador/{id}/remove")
    public void remove(Long id) {
        TccAvaliador tccAvaliador = this.tccAvaliadorDAO.findById(id);

        if (tccAvaliador == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Avaliador não foi encontrado.", "tccAvaliador.id"));
        }

        this.validator.onErrorRedirectTo(TccAvaliadorController.class).index();

        this.tccAvaliadorDAO.delete(tccAvaliador);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Post("/tccavaliador")
    public void cadastrar(TccAvaliador tccAvaliador) {
        //tccAvaliador.setEstado("Ativo");
                
        this.tccAvaliadorDAO.create(tccAvaliador);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccAvaliadorController.class).index();
    }
    
    @Put("/tccavaliador")
    public void altera(TccAvaliador tccAvaliador) {
        this.tccAvaliadorDAO.update(tccAvaliador);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccAvaliadorController.class).index();
    }
    
}
