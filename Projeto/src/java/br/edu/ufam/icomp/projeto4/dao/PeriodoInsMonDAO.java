package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.PeriodoInsMon;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Leonardo
 */
@Component
public class PeriodoInsMonDAO extends DAOImpl<PeriodoInsMon> {

    public PeriodoInsMonDAO(EntityManager entityManager) {
        super(PeriodoInsMon.class, entityManager);
    }

    public Boolean exists(PeriodoInsMon periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("PeriodoInsMon.exists");
        query.setParameter("dtInicio", periodo.getDtInicio());
        query.setParameter("dtFim", periodo.getDtFim());

        return !query.getResultList().isEmpty();
    }

    public PeriodoInsMon findAtivo() {
        try {
            EntityManager entityManager = this.getEntityManager();

            Query query = entityManager.createNamedQuery("PeriodoInsMon.listAtivos");
            query.setParameter("dtAtual", new Date());

            return (PeriodoInsMon) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PeriodoInsMon> findProximoPeriodo() {

        EntityManager entityManager = this.getEntityManager();

        //Query query = entityManager.createNamedQuery("PeriodoInsMon.findAll").setFirstResult(2);
        Query query = entityManager.createNamedQuery("PeriodoInsMon.findProximoPeriodo");
        query.setParameter("dtAtual", new Date());

        return query.getResultList();
    }

    public Boolean checkConflict(PeriodoInsMon periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query1 = entityManager.createNamedQuery("PeriodoInsMon.checkDate7");
        query1.setParameter("dtInicio", periodo.getDtInicio());
        query1.setParameter("dtFim", periodo.getDtFim());

        Query query2 = entityManager.createNamedQuery("PeriodoInsMon.checkDate8");
        query2.setParameter("dtInicio", periodo.getDtInicio());
        query2.setParameter("dtFim", periodo.getDtFim());

        Query query3 = entityManager.createNamedQuery("PeriodoInsMon.checkDate9");
        query3.setParameter("dtInicio", periodo.getDtInicio());
        query3.setParameter("dtFim", periodo.getDtFim());

        Query query4 = entityManager.createNamedQuery("PeriodoInsMon.checkDate10");
        query4.setParameter("dtInicio", periodo.getDtInicio());
        query4.setParameter("dtFim", periodo.getDtFim());

        Query query5 = entityManager.createNamedQuery("PeriodoInsMon.checkDate11");
        query5.setParameter("dtInicio", periodo.getDtInicio());

        Query query6 = entityManager.createNamedQuery("PeriodoInsMon.checkDate12");
        query6.setParameter("dtFim", periodo.getDtFim());

        if (!query1.getResultList().isEmpty()) {
            System.out.println("1");
        }
        if (!query2.getResultList().isEmpty()) {
            System.out.println("2");
        }
        if (!query3.getResultList().isEmpty()) {
            System.out.println("3");
        }
        if (!query4.getResultList().isEmpty()) {
            System.out.println("4");
        }
        if (!query5.getResultList().isEmpty()) {
            System.out.println("5");
        }
        if (!query6.getResultList().isEmpty()) {
            System.out.println("6");
        }

        return !query1.getResultList().isEmpty() || !query2.getResultList().isEmpty() || !query3.getResultList().isEmpty() || !query4.getResultList().isEmpty() || !query5.getResultList().isEmpty() || !query6.getResultList().isEmpty();
    }

    public Boolean checkConflictEdiBoolean(PeriodoInsMon periodo) {

        EntityManager entityManager = this.getEntityManager();

        Query query1 = entityManager.createNamedQuery("PeriodoInsMon.checkDate1");
        query1.setParameter("dtInicio", periodo.getDtInicio());
        query1.setParameter("dtFim", periodo.getDtFim());
        query1.setParameter("id", periodo.getId());

        Query query2 = entityManager.createNamedQuery("PeriodoInsMon.checkDate2");
        query2.setParameter("dtInicio", periodo.getDtInicio());
        query2.setParameter("dtFim", periodo.getDtFim());
        query2.setParameter("id", periodo.getId());

        Query query3 = entityManager.createNamedQuery("PeriodoInsMon.checkDate3");
        query3.setParameter("dtInicio", periodo.getDtInicio());
        query3.setParameter("dtFim", periodo.getDtFim());
        query3.setParameter("id", periodo.getId());

        Query query4 = entityManager.createNamedQuery("PeriodoInsMon.checkDate4");
        query4.setParameter("dtInicio", periodo.getDtInicio());
        query4.setParameter("dtFim", periodo.getDtFim());
        query4.setParameter("id", periodo.getId());

        Query query5 = entityManager.createNamedQuery("PeriodoInsMon.checkDate5");
        query5.setParameter("dtInicio", periodo.getDtInicio());
        query5.setParameter("id", periodo.getId());

        Query query6 = entityManager.createNamedQuery("PeriodoInsMon.checkDate6");
        query6.setParameter("dtFim", periodo.getDtFim());
        query6.setParameter("id", periodo.getId());

        if (!query1.getResultList().isEmpty()) {
            System.out.println("1");
        }
        if (!query2.getResultList().isEmpty()) {
            System.out.println("2");
        }
        if (!query3.getResultList().isEmpty()) {
            System.out.println("3");
        }
        if (!query4.getResultList().isEmpty()) {
            System.out.println("4");
        }
        if (!query5.getResultList().isEmpty()) {
            System.out.println("5");
        }
        if (!query6.getResultList().isEmpty()) {
            System.out.println("6");
        }

        return !query1.getResultList().isEmpty() || !query2.getResultList().isEmpty() || !query3.getResultList().isEmpty() || !query4.getResultList().isEmpty() || !query5.getResultList().isEmpty() || !query6.getResultList().isEmpty();
    }
}
