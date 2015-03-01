package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
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
public class PeriodoLetivoDAO extends DAOImpl<PeriodoLetivo> {

    public PeriodoLetivoDAO(EntityManager entityManager) {
        super(PeriodoLetivo.class, entityManager);
    }

    public PeriodoLetivo findByCodigo(String codigo) {
        try {
            EntityManager entityManager = this.getEntityManager();
            Query query = entityManager.createNamedQuery("PeriodoLetivo.findByCodigo");
            query.setParameter("codigo", codigo);

            return (PeriodoLetivo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PeriodoLetivo> exists(PeriodoLetivo periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.exists");
        query.setParameter("dtInicio", periodo.getDtInicio());
        query.setParameter("dtFim", periodo.getDtFim());

        return query.getResultList();
    }

    public List<PeriodoLetivo> existsMatricula(PeriodoLetivo periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.existsMatricula");
        query.setParameter("dtInicioMatricula", periodo.getDtInicioMatricula());
        query.setParameter("dtFimMatricula", periodo.getDtFimMatricula());

        return query.getResultList();
    }
    
    public List<PeriodoLetivo> existsEstagio(PeriodoLetivo periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.existsEstagio");
        query.setParameter("dtInicioEstagio", periodo.getDtInicioEstagio());
        query.setParameter("dtFimEstagio", periodo.getDtFimEstagio());

        return query.getResultList();
    }    

    public PeriodoLetivo findLetivoAtivo() {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoLetivo.findLetivoAtivo");
            query.setParameter("dtAtual", new Date());

            return (PeriodoLetivo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PeriodoLetivo findMatriculaAtivo() {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoLetivo.findMatriculaAtivo");
            query.setParameter("dtAtual", new Date());

            return (PeriodoLetivo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PeriodoLetivo> findProximoPeriodo() {

        EntityManager entityManager = this.getEntityManager();

        //Query query = entityManager.createNamedQuery("PeriodoLetivo.findAll").setFirstResult(2);
        Query query = entityManager.createNamedQuery("PeriodoLetivo.findProximoPeriodo");
        query.setParameter("dtAtual", new Date());

        return query.getResultList();
    }

    public List<PeriodoLetivo> checkLetivo(PeriodoLetivo periodo) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.checkLetivo");
        query.setParameter("dtInicio", periodo.getDtInicio());
        query.setParameter("dtFim", periodo.getDtFim());

        return query.getResultList();
    }

    public PeriodoLetivo findLetivoAnterior() {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.listLetivoAnterior");
        query.setParameter("dtAtual", new Date());

        List<PeriodoLetivo> periodos = query.getResultList();

        if (periodos.isEmpty()) {
            return null;
        } else {
            return periodos.get(0);
        }
    }
    
    public List<PeriodoLetivo> listLetivoAnterior(){
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.listLetivoAnterior");
        query.setParameter("dtAtual", new Date());

        return query.getResultList();
    }
    
    public List<PeriodoLetivo> listLetivoIniciado(){
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.listLetivoIniciado");
        query.setParameter("dtAtual", new Date());

        return query.getResultList();
    }
    
    public PeriodoLetivo findEstagioAtivo() {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoLetivo.findEstagioAtivo");
            query.setParameter("dtAtual", new Date());

            return (PeriodoLetivo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PeriodoLetivo findByData(Date data) {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoLetivo.findByData");
            query.setParameter("data", data);

            return (PeriodoLetivo) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<PeriodoLetivo> findAllOrderByCodigo(){
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoLetivo.findAllOrderByCodigo");        

        return query.getResultList();
    }
}