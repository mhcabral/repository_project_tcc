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
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.tcc.dao.TccAtividadeDAO;
import br.edu.ufam.icomp.tcc.dao.TccSolicitacaoDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.dao.TccTemaDAO;
import br.edu.ufam.icomp.tcc.model.TccSolicitacao;
import br.edu.ufam.icomp.tcc.model.TccTcc;
import br.edu.ufam.icomp.tcc.model.TccTema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author andre
 */

@Resource
@Permission({Perfil.ALUNO})
public class TccTccController {
    private final Result result;
    private final Validator validator;
    private final TccTccDAO tccTccDAO;
    private final CursoDAO cursoDAO;
    private final ProfessorDAO professorDAO;
    private SessionData sessionData;
    private final AlunoDAO alunoDAO;
    private final TccTemaDAO tccTemaDAO;
    private final TccAtividadeDAO tccAtividadeDAO;
    private final TccSolicitacaoDAO tccSolicitacaoDAO;
    
    public TccTccController (Result result,TccTccDAO tccTccDAO, Validator validator, CursoDAO cursoDAO,
            ProfessorDAO professorDAO, SessionData sessionData, AlunoDAO alunoDAO, TccTemaDAO tccTemaDAO,
            TccAtividadeDAO tccAtividadeDAO, TccSolicitacaoDAO tccSolicitacaoDAO){
        this.result = result;
        this.validator = validator;
        this.tccTccDAO = tccTccDAO;
        this.cursoDAO = cursoDAO;
        this.professorDAO = professorDAO;
        this.sessionData = sessionData;
        this.alunoDAO = alunoDAO;
        this.tccTemaDAO = tccTemaDAO;
        this.tccAtividadeDAO = tccAtividadeDAO;
        this.tccSolicitacaoDAO = tccSolicitacaoDAO;
    }


    @Get("/tcctcc")
    public void index() {
        Aluno aluno = alunoDAO.findByIdUsuario(sessionData.getUsuario().getId());
        TccTcc tccTcc = this.tccTccDAO.findByAluno(aluno.getId());
        
        if (tccTcc == null) {
            this.result.redirectTo(TccTccController.class).create();
        } else this.result.redirectTo(TccTccController.class).edit(tccTcc.getId());
    }
    
    @Get("/tcctcc/{id}/edit")
    public TccTcc edit(Long id) {
        Date dataTema = tccAtividadeDAO.findDataTema(sessionData.getLetivoAtual().getId());
        TccTcc tccTcc = tccTccDAO.findById(id);
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<TccTema> listTema = tccTemaDAO.findAll();
        Aluno aluno = tccTcc.getAluno();
        Long idPeriodo = tccTcc.getPeriodo().getId();
        
        Boolean podeSalvarTema = (!(dataTema == null || !(tccTcc.getSolicitacaoTema().getEstado().equals("Solicitado"))));

        if (tccTcc == null) {
            this.validator.add(new ValidationMessage("Desculpe!O Tcc não foi encontrado.", "tccTcc.id"));
        }
        
        this.validator.onErrorRedirectTo(TccTccController.class).index();

        this.result.include("operacao", "Edição");
        this.result.include("professorList", listProfessor);
        this.result.include("aluno", aluno);
        this.result.include("temaList", listTema);
        this.result.include("idPeriodo", idPeriodo);
        this.result.include("podeSalvarTema", podeSalvarTema);

        return tccTcc;
    }
    
    @Get("/tcctcc/create")
    public void create() {
        Date dataTema = tccAtividadeDAO.findDataTema(sessionData.getLetivoAtual().getId());
        Aluno aluno = alunoDAO.findByIdUsuario(sessionData.getUsuario().getId());
        List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
        perfisEncontrar.add(Perfil.PROFESSOR);
        List<Professor> listProfessor = professorDAO.findByPerfisAndAtivo(perfisEncontrar, true);
        List<TccTema> listTema = tccTemaDAO.findAll();
        Long idPeriodo = sessionData.getLetivoAtual().getId();
        
        Boolean podeSalvarTema = !(dataTema == null);
        
        this.result.include("operacao", "Cadastro");
        this.result.include("professorList", listProfessor);
        this.result.include("aluno", aluno);
        this.result.include("temaList", listTema);
        this.result.include("idPeriodo", idPeriodo);
        this.result.include("podeSalvarTema", podeSalvarTema);
    }
    
    @Get("/tcctcc/{id}/show")
    public TccTcc show(Long id) {
        TccTcc tccTcc = this.tccTccDAO.findById(id);

        if (tccTcc == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tcc não foi encontrado.", "tccTcc.id"));
        }

        this.validator.onErrorRedirectTo(TccTccController.class).index();
        
        return tccTcc;
    }
    
    @Get("/tcctcc/{id}/remove")
    public void remove(Long id) {
        TccTcc tccTcc = this.tccTccDAO.findById(id);

        if (tccTcc == null) {
            this.validator.add(new ValidationMessage("Desculpe! O Tcc não foi encontrado.", "tccTcc.id"));
        }

        this.validator.onErrorRedirectTo(TccTccController.class).index();

        this.tccTccDAO.delete(tccTcc);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();
    }
    
    @Post("/tcctcc")
    public void cadastrar(TccTcc tccTcc) {
        TccSolicitacao tccSolicitacao = new TccSolicitacao();
        if (tccTcc.getProfessor().getId() == null || tccTcc.getProfessor().getId() == null) {
            validator.add(new ValidationMessage("Um professor deve ser selecionado", "tccTcc.professor.id"));
        }
               
        tccTcc.setSolicitacaoTema(null);
        this.tccTccDAO.create(tccTcc);
        
        tccSolicitacao.setEstado("Solicitado");
        tccSolicitacao.setTipo("Definição de Tema");
        tccSolicitacao.setTccTcc(tccTcc);
        this.tccSolicitacaoDAO.create(tccSolicitacao);

        this.result.include("success", "cadastrada");
        
        this.result.redirectTo(TccController.class).main();
    }
    
    @Put("/tcctcc")
    public void altera(TccTcc tccTcc) {
        if (tccTcc.getProfessor().getId() == null || tccTcc.getProfessor().getId() == null) {
            validator.add(new ValidationMessage("Um professor deve ser selecionado", "tccTcc.professor.id"));
        }
        this.tccTccDAO.update(tccTcc);

        this.result.include("success", "alterada");

        this.result.redirectTo(TccController.class).main();
    }
    
}
