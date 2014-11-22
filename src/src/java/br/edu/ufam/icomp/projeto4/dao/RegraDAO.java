package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Atividade;
import br.edu.ufam.icomp.projeto4.model.Regra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class RegraDAO extends DAOImpl<Regra> {

    public RegraDAO(EntityManager entityManager) {
        super(Regra.class, entityManager);
    }

    public boolean existeRegra(Regra regra) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Regra.find");

        query.setParameter("cId", regra.getCurso().getId());
        query.setParameter("aId", regra.getAtividade().getId());

        return !query.getResultList().isEmpty();
    }

    public List<Atividade> findAtividadeFromCurso(Long idCurso) {
        Query query = this.getEntityManager().createNamedQuery("Regra.findAtividadeFromCurso");

        query.setParameter("cId", idCurso);

        return query.getResultList();
    }

    public List<Regra> findByAtividade(Long idAtividade) {
        Query query = this.getEntityManager().createNamedQuery("Regra.findByAtividade");

        query.setParameter("aId", idAtividade);

        return query.getResultList();
    }

    public Regra findByAtividadeCurso(Long idAtividade, Long idCurso) {
        try {
            Query query = this.getEntityManager().createNamedQuery("Regra.findByAtividadeCurso");

            query.setParameter("aIdAtividade", idAtividade);
            query.setParameter("aIdCurso", idCurso);

            return (Regra) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Regra> findByCurso(Long id) {
        Query query = this.getEntityManager().createNamedQuery("Regra.findByCurso");

        query.setParameter("id", id);

        return query.getResultList();
    }
    
    public List<Regra> findByCursoAndGrupo(Long idCurso, Long idGrupo){
        Query query = this.getEntityManager().createNamedQuery("Regra.findByCursoAndGrupo");
        
        query.setParameter("idCurso", idCurso);
        query.setParameter("idGrupo", idGrupo);
        
        return query.getResultList();
    }
}