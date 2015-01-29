package br.edu.ufam.icomp.tcc.controllers;   

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.tcc.dao.TccNotasDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.model.TccNotas;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import java.util.List;

@Resource
@Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT}) 
public class TccNotasController {
    private final Result result;
    private final Validator validator;
    private final TccNotasDAO tccNotasDAO;
    private SessionData session;
    private final TccTccDAO tccTccDAO;
    private final ProfessorDAO professorDAO;
    
    public TccNotasController (Result result,TccNotasDAO tccNotasDAO, Validator validator,SessionData session,TccTccDAO tcctccDAO,ProfessorDAO professorDAO){
        this.result = result;
        this.validator = validator;
        this.tccNotasDAO = tccNotasDAO;
        this.session = session;
        this.tccTccDAO = tcctccDAO;
        this.professorDAO = professorDAO;
    }


    @Get("/tccnotas")
    public void index() {
        List<TccNotas> tccNotas = null;
        if (session.getUsuario().getRole().equals(Perfil.PROFESSOR)) { 
            Professor professor = this.professorDAO.findById(session.getUsuario().getId());
            List<TccTcc> tcctcc = this.tccTccDAO.findTccByProfessor(professor.getId());
            int i;
            for(i=0;i<tcctcc.size();i++){
                tccNotas.add(this.tccNotasDAO.findByTcc(tcctcc.get(i).getId()));
            }
        }
        
        this.result.include("tccNotasList", tccNotas);
    }
    
    @Get("/tccnotas/{id}/edit")
    public TccNotas edit(Long id) {

        TccNotas tccNotas = tccNotasDAO.findById(id);
        
        if (tccNotas == null) {
            this.validator.add(new ValidationMessage("Notas do aluno não foram encontradas.", "tccNotas.id")); 
        }
        
        this.validator.onErrorRedirectTo(TccNotasController.class).index();

        return tccNotas;
    }
    
    @Get("/tccnotas/create")
    public void create() {
        
        this.result.include("operacao", "Lançar notas"); 
    }
    
    @Get("/tccnotas/{id}/show")
    public TccNotas show(Long id) {
        TccNotas tccNotas = this.tccNotasDAO.findById(id);

        if (tccNotas == null) {
            this.validator.add(new ValidationMessage("Notas do aluno não foram encontradas.", "tccNotas.id"));
        }

        this.validator.onErrorRedirectTo(TccNotasController.class).index();
        
        return tccNotas;
    }
    /*
    @Get("/tccnotas/{id}/remove")
    public void remove(Long id) {
        TccNotas tccNotas = this.tccNotasDAO.findById(id);

        if (tccNotas == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Local não foi encontrado.", "tccNotas.id"));
        }

        this.validator.onErrorRedirectTo(TccNotasController.class).index();

        this.tccNotasDAO.delete(tccNotas);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }  */
    
    @Post("/tccnotas")
    public void cadastrar(TccNotas tccNotas) {
                
        this.tccNotasDAO.create(tccNotas);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccNotasController.class).index();
    }
    
    @Put("/tccnotas")
    public void altera(TccNotas tccNotas) {
        this.tccNotasDAO.update(tccNotas);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccNotasController.class).index();
    }
}