package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.tcc.dao.TccTemaDAO;
import br.edu.ufam.icomp.tcc.model.TccTema;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author andre
 */

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccTemaController {
    private final Result result;
    private final HttpServletRequest request;
    private final Validator validator;
    private final TccTemaDAO tccTemaDAO;
    private final CursoDAO cursoDAO;
    
    public TccTemaController (Result result,TccTemaDAO tccTemaDAO, Validator validator, CursoDAO cursoDAO, HttpServletRequest request){
        this.result = result;
        this.validator = validator;
        this.tccTemaDAO = tccTemaDAO;
        this.cursoDAO = cursoDAO;
        this.request = request;
    }


    @Get("/tccTema/index")
    public void index() {
             
        List<TccTema> tccTema = this.tccTemaDAO.findAll();
        
        this.result.include("tccTemaList", tccTema);
    }
    
    @Get("tccTema/{id}/edit")
    public TccTema edit(Long id) {
        TccTema tccTema = tccTemaDAO.findById(id);

        if (tccTema == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Tema não foi encontrado.", "tccTema.id"));
        }
        
        this.validator.onErrorRedirectTo(TccTemaController.class).index();

        result.include("operacao", "Edição");

        return tccTema;
    }
    
    @Get("/tccTema/create")
    public void create() {
        result.include("operacao", "Cadastro");
        List<Curso> cursos = cursoDAO.listAll();
        
        this.result.include("cursosList", cursos);
    }
    
    @Get("/tccTema/{id}/show")
    public TccTema show(Long id) {
        TccTema tccTema = this.tccTemaDAO.findById(id);

        if (tccTema == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tema não foi encontrado.", "tccTema.id"));
        }

        this.validator.onErrorRedirectTo(TccTemaController.class).index();
        
        return tccTema;
    }
    
    @Get("/tccTema/{id}/remove")
    public void remove(Long id) {
        TccTema tccTema = this.tccTemaDAO.findById(id);

        if (tccTema == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tema não foi encontrado.", "tccTema.id"));
        }

        this.validator.onErrorRedirectTo(TccTemaController.class).index();

        this.tccTemaDAO.delete(tccTema);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Post("/tccTema/salvar")
    public void cadastrar(final TccTema tccTema) {
        String[] cursos;
        cursos = this.request.getParameterValues("tema-area");
        
        System.out.println(cursos[0]);

        //this.tccTemaDAO.create(tccTema);

        //this.result.include("success", "cadastrada");

        this.result.redirectTo(TccTemaController.class).index();
    }
    
}
