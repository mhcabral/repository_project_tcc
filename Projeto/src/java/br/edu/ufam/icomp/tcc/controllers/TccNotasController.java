package br.edu.ufam.icomp.tcc.controllers;   

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.tcc.dao.TccNotasDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.model.TccNotas;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import java.util.ArrayList;
import java.util.List;

@Resource
public class TccNotasController {
    private final Result result;
    private final Validator validator;
    private final TccNotasDAO tccNotasDAO;
    private SessionData session;
    private final TccTccDAO tccTccDAO;
    private final ProfessorDAO professorDAO;
    private final AlunoDAO alunoDAO;
    private final CoordenadorCursoDAO coordenadorDAO;
    
    public TccNotasController (Result result,TccNotasDAO tccNotasDAO, Validator validator,SessionData session,TccTccDAO tcctccDAO,ProfessorDAO professorDAO,AlunoDAO alunoDAO,CoordenadorCursoDAO coordenadorDAO){
        this.result = result;
        this.validator = validator;
        this.tccNotasDAO = tccNotasDAO;
        this.session = session;
        this.tccTccDAO = tcctccDAO;
        this.professorDAO = professorDAO;
        this.alunoDAO = alunoDAO;
        this.coordenadorDAO = coordenadorDAO;
    }

    @Get("/tccnotas/index")
    public void index() {
        PeriodoLetivo periodoAtual = session.getLetivoAtual();
        if (session.getUsuario().getRole().equals(Perfil.ALUNO)) { 
            Aluno aluno = this.alunoDAO.findByIdUsuario(session.getUsuario().getId());
            List<TccTcc> tccTcc = new ArrayList();
            tccTcc.add(this.tccTccDAO.findByAluno(aluno.getId(),periodoAtual.getId()));
            this.result.include("tccList", tccTcc);
        }
        else{
           Professor professor = this.professorDAO.findByUsuario(session.getUsuario().getId());
           List<TccTcc> tccTcc = this.tccTccDAO.findTccByProfessor(professor.getId(),periodoAtual.getId());
           this.result.include("tccList", tccTcc);
        }
    }
    
    @Permission({Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
    @Get("/tccnotas/{id}/edit")
    public TccNotas edit(Long id) {

        TccNotas tccNotas = tccNotasDAO.findById(id);
        
        if (tccNotas == null) {
            this.validator.add(new ValidationMessage("Notas do aluno não foram encontradas.", "tccNotas.id")); 
        }
        
        this.validator.onErrorRedirectTo(TccNotasController.class).index();

        return tccNotas;
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
        
    @Post("/tccnotas")
    public void cadastrar(TccNotas tccNotas) {
                
        this.tccNotasDAO.create(tccNotas);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccNotasController.class).index();
    }
    
    @Put("/tccnotas")
    public void altera(TccNotas tccNotas) {
        System.out.println(tccNotas.getNota1());
        this.tccNotasDAO.update(tccNotas);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccNotasController.class).index();
    }
    
    
}