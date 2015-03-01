package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import br.edu.ufam.icomp.projeto4.model.Status;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Bruna
 */
@Component
public class SolicitacaoDAO extends DAOImpl<Solicitacao> {
    
    public SolicitacaoDAO(EntityManager entityManager) {
        super(Solicitacao.class, entityManager);
    }
    
    public List<Solicitacao> findBySolicitante(Long idSolicitante) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Solicitacao.findBySolicitante");
        
        query.setParameter("pId", idSolicitante);        
        
        return query.getResultList();
    }
    
    public long countBySolicitante(Long id, Status status) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Solicitacao.countBySolicitante");
        
        query.setParameter("pId", id);
        query.setParameter("status", status);
        
        List resultList = query.getResultList();                
        
        if (query.getSingleResult() != null) {
            return (Long) query.getSingleResult();
        } else {
            return 0;
        }
    }
    
    public List<Solicitacao> findByCurso(Long id) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Solicitacao.findByCurso");
        
        query.setParameter("idCurso", id);
        
        return query.getResultList();
    }
    
    public List<Solicitacao> findByCursos(List<Curso> cursos) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Solicitacao.findByCursos");
        
        query.setParameter("cursoList", cursos);        
        
        return query.getResultList();
    }
    
    public long countByGrupo(Long idAluno, Long idGrupo, Status status) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Solicitacao.countByGrupo");
        
        query.setParameter("idAluno", idAluno);
        query.setParameter("idGrupo", idGrupo);
        query.setParameter("status", status);
        
        
        if (query.getSingleResult() != null) {
            return (Long) query.getSingleResult();
        } else {
            return 0;
        }
    }    
    
    public List<Solicitacao> findHistorico(Long idALuno, Status status) {
        EntityManager entityManager = this.getEntityManager();        
        
        Query query = entityManager.createNamedQuery("Solicitacao.findHistorico");
        
        query.setParameter("idAluno", idALuno);
        query.setParameter("statusAtual", status);
        
        List<Object[]> resultado = query.getResultList();
        
        List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
        
        for (Object[] objects : resultado) {
            Atividade atividade = new Atividade();
            atividade.setCodigo((String) objects[1]);            
            atividade.setNome((String) objects[2]);            
            
            Grupo grupo = new Grupo();
            grupo.setCodigo((String) objects[3]);            
            grupo.setNome((String) objects[4]);            
            
            atividade.setGrupo(grupo);
            
            Solicitacao solicitacao = new Solicitacao();
            
            solicitacao.setAtividade(atividade);
            solicitacao.setHorasComputadas(Integer.parseInt(objects[0].toString()));            
            
            PeriodoLetivo periodoLetivo = new PeriodoLetivo();
            periodoLetivo.setCodigo((String) objects[5]);
            
            solicitacao.setPeriodo(periodoLetivo);
            
            solicitacoes.add(solicitacao);
        }
                                        
        return solicitacoes;
    }
    
    public List<Solicitacao> findByPeriodo(PeriodoLetivo periodo){
        EntityManager entityManager = this.getEntityManager();        
        
        Query query = entityManager.createNamedQuery("Solicitacao.findByPeriodo");
        
        query.setParameter("idPeriodo", periodo.getId());
        
        return query.getResultList();
    }
    
    public List<Atividade> listByPeriodo(Long idPeriodo){
        EntityManager entityManager = this.getEntityManager();        
        
        Query query = entityManager.createNamedQuery("Solicitacao.listByPeriodo");
        
        query.setParameter("idPeriodo", idPeriodo);
        
        List<Object[]> resultado = query.getResultList();
        
        List<Atividade> atividades = new ArrayList<Atividade>();

        for (Object[] objects : resultado) {
            Grupo grupo = new Grupo();
            grupo.setCodigo((String)objects[3]);
            grupo.setNome((String)objects[4]);
            
            Atividade atividade = new Atividade();
            atividade.setCodigo((String)objects[0]);
            atividade.setNome((String)objects[1]);
            atividade.setTotalSolicitada(Integer.parseInt(objects[2].toString()));
            atividade.setGrupo(grupo);
            
            atividades.add(atividade);
        }

        return atividades;
    }   

    public List<Solicitacao> findByCursoOrPeriodoOrAtividadeOrAlunoAndStatus(List<Curso> curso, Long idPeriodoLetivo, Long idAtividade, Long idAluno, List<Status> status) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Solicitacao> c = cb.createQuery(Solicitacao.class);

        //Selects
        Root<Solicitacao> root = c.from(Solicitacao.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (!curso.isEmpty()) {
            predicates.add(
                    root.get("solicitante").get("curso").in(curso));
        }
        if (idPeriodoLetivo > 0) {
            predicates.add(
                    cb.equal(root.get("periodo").get("id"), idPeriodoLetivo));
        }
        if (idAtividade > 0) {
            predicates.add(
                    cb.equal(root.get("atividade").get("id"), idAtividade));
        }
        if (!status.isEmpty()) {
            
            predicates.add(root.get("statusAtual").in(status));            
        }        
        if (idAluno > 0) {
            
            predicates.add(cb.equal(root.get("solicitante").get("id"), idAluno));            
        }

        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Solicitacao> query = em.createQuery(c);


        return query.getResultList();
    }
    
    public List<Aluno> findHistoricoCategoria (List<Aluno> alunoList, Long idPeriodo){
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Solicitacao.findHistoricoCategoria");

        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("alunos", alunoList);

        List<Object[]> resultado = query.getResultList();
        
        List<Aluno> alunoResult = new ArrayList<Aluno>();
        
        for (Object[] object : resultado) {
            Usuario usuario = new Usuario();
            usuario.setId(Long.parseLong(object[1].toString()));
            usuario.setNome(object[3].toString());
            
            Aluno aluno = new Aluno();
            aluno.setUsuario(usuario);
            aluno.setId(Long.parseLong(object[0].toString()));
            aluno.setMatricula(object[2].toString());
            aluno.setEnsino(Integer.parseInt(object[4].toString()));
            aluno.setPesquisa(Integer.parseInt(object[5].toString()));
            aluno.setExtensao(Integer.parseInt(object[6].toString()));
            
            
            Curso curso = new Curso();
            curso.setCodigo(object[7].toString());
            curso.setCurso(object[8].toString());
            curso.setId(Long.parseLong(object[9].toString()));
            
            aluno.setCurso(curso);
            
            alunoResult.add(aluno);
        }
        
        return alunoResult;
    }
}
