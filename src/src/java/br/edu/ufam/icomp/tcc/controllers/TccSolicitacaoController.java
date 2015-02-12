
package br.edu.ufam.icomp.tcc.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import br.edu.ufam.icomp.tcc.dao.TccSolicitacaoDAO;
import br.edu.ufam.icomp.tcc.dao.TccTccDAO;
import br.edu.ufam.icomp.tcc.model.TccSolicitacao;
import java.util.List;

/**
 *
 * @author andre
 */
@Resource
@Permission({Perfil.PROFESSOR, Perfil.COORDENADOR})
public class TccSolicitacaoController {
    private final Result result;
    private final Validator validator;
    private final TccSolicitacaoDAO tccSolicitacaoDAO;
    private final TccTccDAO tccTccDAO;
    private final SessionData sessionData;
    private final ProfessorDAO professorDAO;
    
    public TccSolicitacaoController(Result result, Validator validator, TccSolicitacaoDAO tccSolicitacaoDAO,
            TccTccDAO tccTccDAO, SessionData sessionData, ProfessorDAO professorDAO) {
        this.result = result;
        this.validator = validator;
        this.tccSolicitacaoDAO = tccSolicitacaoDAO;
        this.tccTccDAO = tccTccDAO;
        this.sessionData = sessionData;
        this.professorDAO = professorDAO;
    }
    
    @Get("/tccsolicitacoes")
    public void index(Integer idEstado, Long idAluno) {
        PeriodoLetivo periodoAtual = sessionData.getLetivoAtual();
        String aEstado[] = new String[]{"Solicitado", "Deferido", "Indeferido"};
        if (idEstado == null) {
            idEstado = 0;
        }
                
        if (idAluno == null) {
            idAluno = 0L;
        }
        String estado = aEstado[idEstado];
        
        Usuario usuario = sessionData.getUsuario();
        Professor professor = this.professorDAO.findByUsuario(usuario.getId());
        
        List<TccSolicitacao> tccSolicitacao = this.tccSolicitacaoDAO.findByProfessorEstadoAluno(professor.getId(),estado,idAluno);
        List<Aluno> aluno = tccTccDAO.findByProfessor(professor.getId(), periodoAtual.getId());
        
        this.result.include("tccSolicitacaoList", tccSolicitacao);
        this.result.include("alunoList", aluno);
        this.result.include("estadoList", aEstado);
        this.result.include("idAluno", idAluno);
        this.result.include("idEstado", idEstado);
    }
    
   
    @Get("/tccsolicitacoes/{idEstado}/{idAluno}/search")
    public void search(Integer idEstado, Long idAluno) {
        this.result.redirectTo(TccSolicitacaoController.class).index(idEstado, idAluno);
    }
    
    @Get("/tccsolicitacoes/{id}/show")
    public TccSolicitacao show(Long id) {
        TccSolicitacao tccSolicitacao = this.tccSolicitacaoDAO.findById(id);
        
        if (tccSolicitacao == null) {
            this.result.redirectTo(TccSolicitacaoController.class).index(null, null);
        }

        return tccSolicitacao;
    }
    
    @Post("/tccsolicitacoes")
    public void alterar(TccSolicitacao tccSolicitacao) {
        TccSolicitacao tccSolicitacaoEncontrado = this.tccSolicitacaoDAO.findById(tccSolicitacao.getId());
        
        if (tccSolicitacaoEncontrado == null) {
            validator.add(new ValidationMessage("Desculpe! Solicitação não encontrada.", "tccSolicitacao.id"));
        }        
        this.validator.onErrorRedirectTo(TccSolicitacaoController.class).show(tccSolicitacao.getId());
      
        this.tccSolicitacaoDAO.update(tccSolicitacao);

        result.include("success", "alterada");

        this.result.redirectTo(TccSolicitacaoController.class).index(null,null);
    }
    
}

    
