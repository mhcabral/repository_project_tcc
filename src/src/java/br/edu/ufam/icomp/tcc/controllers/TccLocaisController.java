/**
 *
 * @author mhcabral
 */
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
import br.edu.ufam.icomp.tcc.dao.TccLocaisDAO;
import br.edu.ufam.icomp.tcc.model.TccLocais;
import java.util.List;

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccLocaisController {
    private final Result result;
    private final Validator validator;
    private final TccLocaisDAO tccLocaisDAO;
    
    public TccLocaisController (Result result,TccLocaisDAO tccLocaisDAO, Validator validator){
        this.result = result;
        this.validator = validator;
        this.tccLocaisDAO = tccLocaisDAO;
    }


    @Get("/tcclocais")
    public void index() {
             
        List<TccLocais> tccLocais = this.tccLocaisDAO.findAll();
        
        this.result.include("tccLocaisList", tccLocais);
    }
    
    @Get("/tcclocais/{id}/edit")
    public TccLocais edit(Long id) {

        TccLocais tccLocais = tccLocaisDAO.findById(id);
        
        if (tccLocais == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Local não foi encontrado.", "tccLocais.id"));
        }
        
        this.validator.onErrorRedirectTo(TccLocaisController.class).index();

        return tccLocais;
    }
    
    @Get("/tcclocais/create")
    public void create() {
        
        this.result.include("operacao", "Cadastro");
    }
    
    @Get("/tcclocais/{id}/show")
    public TccLocais show(Long id) {
        TccLocais tccLocais = this.tccLocaisDAO.findById(id);

        if (tccLocais == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tema não foi encontrado.", "tccLocais.id"));
        }

        this.validator.onErrorRedirectTo(TccLocaisController.class).index();
        
        return tccLocais;
    }
    
    @Get("/tcclocais/{id}/remove")
    public void remove(Long id) {
        TccLocais tccLocais = this.tccLocaisDAO.findById(id);

        if (tccLocais == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Local não foi encontrado.", "tccLocais.id"));
        }

        this.validator.onErrorRedirectTo(TccLocaisController.class).index();

        this.tccLocaisDAO.delete(tccLocais);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Post("/tcclocais")
    public void cadastrar(TccLocais tccLocais) {
        tccLocais.setEstado("Ativo");
                
        this.tccLocaisDAO.create(tccLocais);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccLocaisController.class).index();
    }
    
    @Put("/tcclocais")
    public void altera(TccLocais tccLocais) {
        this.tccLocaisDAO.update(tccLocais);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccLocaisController.class).index();
    }
    
}
