package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.FrequenciaEstagio;
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
public class FrequenciaEstagioDAO extends DAOImpl<FrequenciaEstagio> {
    public FrequenciaEstagioDAO(EntityManager entityManager) {
        super(FrequenciaEstagio.class, entityManager);
    }
    
    public List<FrequenciaEstagio> listByEstagio(Long id) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("FrequenciaEstagio.listByEstagio");

        query.setParameter("idEstagio", id);

        return query.getResultList();
    }
    
    public FrequenciaEstagio findById(Integer id) {
        try {
            EntityManager em = this.getEntityManager();

            Query query = em.createNamedQuery("FrequenciaEstagio.findById");

            query.setParameter("id", id);

            return (FrequenciaEstagio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public FrequenciaEstagio findByData(Date data, Long id) {
        try {
            EntityManager em = this.getEntityManager();

            Query query = em.createNamedQuery("FrequenciaEstagio.findByDataId");

            query.setParameter("data", data);
            query.setParameter("id", id);

            return (FrequenciaEstagio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<FrequenciaEstagio> listByDataInicioFim(Long id, Date dtInicio, Date dtFim) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("FrequenciaEstagio.listByDataInicioFim");

        query.setParameter("id", id);
        query.setParameter("dtInicio", dtInicio);
        query.setParameter("dtFim", dtFim);

        return query.getResultList();
    }

    public Long sumByDataInicioFim(Long id, Date dtInicio, Date dtFim) {
        EntityManager em = this.getEntityManager();

        Query query = em.createNamedQuery("FrequenciaEstagio.sumByDataInicioFim");

        query.setParameter("id", id);
        query.setParameter("dtInicio", dtInicio);
        query.setParameter("dtFim", dtFim);

        return (Long) query.getSingleResult();
    }
}