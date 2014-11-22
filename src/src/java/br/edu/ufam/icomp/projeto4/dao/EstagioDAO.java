package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Estagio;
import br.edu.ufam.icomp.projeto4.model.InscricaoMonitoria;
import br.edu.ufam.icomp.projeto4.model.Solicitacao;
import br.edu.ufam.icomp.projeto4.model.Status;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
public class EstagioDAO extends DAOImpl<Estagio> {

    public EstagioDAO(EntityManager entityManager) {
        super(Estagio.class, entityManager);
    }

    public Estagio findByAlunoPeriodo(Long idAluno, Long idPeriodo) {
        try {
            Query query = getEntityManager().createNamedQuery("Estagio.findByAlunoPeriodo");

            query.setParameter("idAluno", idAluno);
            query.setParameter("idPeriodo", idPeriodo);

            return (Estagio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Estagio findByStatusAluno(Long idAluno, Status status) {
        try {
            Query query = getEntityManager().createNamedQuery("Estagio.findByStatusAluno");

            query.setParameter("idAluno", idAluno);
            query.setParameter("status", status);

            return (Estagio) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Estagio> listByAluno(Long id) {
        Query query = getEntityManager().createNamedQuery("Estagio.listByAluno");

        query.setParameter("id", id);

        return query.getResultList();
    }

    public List<Estagio> findByStatusAndCursoAndPeriodo(Status status, Long idCurso, Long idPeriodo) {
        Query query = getEntityManager().createNamedQuery("Estagio.findByStatusAndCursoAndPeriodo");

        query.setParameter("status", status);
        query.setParameter("idPeriodo", idPeriodo);
        query.setParameter("idCurso", idCurso);

        return query.getResultList();
    }
    
    public List<Estagio> search(Long idCurso, Long idPeriodo, Long idAluno) {

        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Estagio> c = cb.createQuery(Estagio.class);

        //Selects
        Root<Estagio> root = c.from(Estagio.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (idCurso > 0) {
            predicates.add(
                    cb.equal(root.get("aluno").get("curso").get("id"), idCurso));
        }
        if (idPeriodo != null) {
            predicates.add(
                    cb.equal(root.get("periodo").get("id"), idPeriodo));
        }
        if (idAluno > 0) {
            predicates.add(
                    cb.equal(root.get("aluno").get("id"), idAluno));
        }

        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Estagio> query = em.createQuery(c);


        return query.getResultList();
    }
    
    public List<Estagio> findByCursoOrPeriodoOrAluno(List<Curso> curso, Long idPeriodoLetivo, Long idAluno) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Estagio> c = cb.createQuery(Estagio.class);

        //Selects
        Root<Estagio> root = c.from(Estagio.class);

        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();

        //Adding predicates in case of parameter not being null
        if (!curso.isEmpty()) {
            predicates.add(
                    root.get("aluno").get("curso").in(curso));
        }
        if (idPeriodoLetivo > 0) {
            predicates.add(
                    cb.equal(root.get("periodo").get("id"), idPeriodoLetivo));
        }        
        if (idAluno > 0) {
            predicates.add(cb.equal(root.get("aluno").get("id"), idAluno));            
        }

        c.select(root)
                .where(predicates.toArray(new Predicate[]{}));

        TypedQuery<Estagio> query = em.createQuery(c);


        return query.getResultList();
    }
}
