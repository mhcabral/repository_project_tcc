package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.tcc.dao.TccTemaDAO;
import br.edu.ufam.icomp.tcc.model.TccTema;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */

@Resource
@Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.COORDENADORACAD, Perfil.PROFESSOR, Perfil.ROOT, Perfil.SECRETARIA})
public class TccTemaController {
    private final Result result;
    private final Validator validator;
    private final TccTemaDAO tccTemaDAO;
    private final CursoDAO cursoDAO;
    private final ProfessorDAO professorDAO;
    
    public TccTemaController (Result result,TccTemaDAO tccTemaDAO, Validator validator, CursoDAO cursoDAO,
            ProfessorDAO professorDAO){
        this.result = result;
        this.validator = validator;
        this.tccTemaDAO = tccTemaDAO;
        this.cursoDAO = cursoDAO;
        this.professorDAO = professorDAO;
    }


    @Get("/tcctemas")
    public void index() {
             
        List<TccTema> tccTema = this.tccTemaDAO.findAll();
        
        this.result.include("tccTemaList", tccTema);
    }
    
    @Get("/tcctemas/{id}/edit")
    public TccTema edit(Long id) {
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        List<Curso> cursos = cursoDAO.listAll();
        TccTema tccTema = tccTemaDAO.findById(id);
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<String> listArea = new ArrayList();
        
        listArea.add("Banco de Dados e Recuperação de Informação");
        listArea.add("Engenharia de Software");
        listArea.add("Informática na Educação");
        listArea.add("Inteligência Artificial");
        listArea.add("Otimização, Algoritmos e Complexidade Computacional");
        listArea.add("Redes de Computadores e Sistemas Duistribuídos");
        listArea.add("Sistemas Embarcados");
        listArea.add("Visão Computacional e Robótica");
        listArea.add("Outra");

        if (tccTema == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Tema não foi encontrado.", "tccTema.id"));
        }
        
        this.validator.onErrorRedirectTo(TccTemaController.class).index();

        this.result.include("operacao", "Edição");
        this.result.include("areaList", listArea);
        this.result.include("cursosList", cursos);
        this.result.include("professorList", listProfessor);

        return tccTema;
    }
        
    @Get("/tcctemas/create")
    public void create() {
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        perfisEncontrar.add(Perfil.COORDENADOR);
        List<Curso> cursos = cursoDAO.listAll();
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<String> listArea = new ArrayList();
        
        listArea.add("Banco de Dados e Recuperação de Informação");
        listArea.add("Engenharia de Software");
        listArea.add("Informática na Educação");
        listArea.add("Inteligência Artificial");
        listArea.add("Otimização, Algoritmos e Complexidade Computacional");
        listArea.add("Redes de Computadores e Sistemas distribuídos");
        listArea.add("Sistemas Embarcados");
        listArea.add("Visão Computacional e Robótica");
        listArea.add("Outra");
        
        this.result.include("operacao", "Cadastro");            
        this.result.include("cursosList", cursos);
        this.result.include("areaList", listArea);
        this.result.include("professorList", listProfessor);
    }
    
    @Get("/tcctemas/{id}/show")
    public TccTema show(Long id) {
        TccTema tccTema = this.tccTemaDAO.findById(id);

        if (tccTema == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tema não foi encontrado.", "tccTema.id"));
        }

        this.validator.onErrorRedirectTo(TccTemaController.class).index();
        
        return tccTema;
    }
    
    @Get("/tcctemas/{id}/remove")
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
    
    @Post("/tcctemas")
    public void cadastrar(TccTema tccTema) {
        
        if (tccTema.getProfessor().getId() == null || tccTema.getProfessor().getId() == null) {
            validator.add(new ValidationMessage("Um professor deve ser selecionado", "tccTema.professor.id"));
        }
                
        this.tccTemaDAO.create(tccTema);

        this.result.include("success", "cadastrada");

        this.result.redirectTo(TccTemaController.class).index();
    }
    
    @Put("/tcctemas")
    public void altera(TccTema tccTema) {
        this.tccTemaDAO.update(tccTema);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccTemaController.class).index();
    }
    
}
