package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Frequencia;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class FrequenciaDAO extends DAOImpl<Frequencia> {

    public FrequenciaDAO(EntityManager entityManager) {
        super(Frequencia.class, entityManager);
    }

    public List<Frequencia> listByInscricao(Long id) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("Frequencia.listByInscricao");

        query.setParameter("idInscricao", id);

        return query.getResultList();
    }

    public Frequencia findById(Integer id) {
        try {
            EntityManager em = this.getEntityManager();

            Query query = em.createNamedQuery("Frequencia.findById");

            query.setParameter("id", id);

            return (Frequencia) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Frequencia findByData(Date data, Long id) {
        try {
            EntityManager em = this.getEntityManager();

            Query query = em.createNamedQuery("Frequencia.findByDataId");

            query.setParameter("data", data);
            query.setParameter("id", id);

            return (Frequencia) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Frequencia> listByDataInicioFim(Long id, Date dtInicio, Date dtFim) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("Frequencia.listByDataInicioFim");

        query.setParameter("id", id);
        query.setParameter("dtInicio", dtInicio);
        query.setParameter("dtFim", dtFim);

        return query.getResultList();
    }

    public Long sumByDataInicioFim(Long id, Date dtInicio, Date dtFim) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("Frequencia.sumByDataInicioFim");

        query.setParameter("id", id);
        query.setParameter("dtInicio", dtInicio);
        query.setParameter("dtFim", dtFim);

        return (Long) query.getSingleResult();
    }
}