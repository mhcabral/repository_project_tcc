package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Professor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class CoordenadorCursoDAO extends DAOImpl<CoordenadorCurso> {

    public CoordenadorCursoDAO(EntityManager entityManager) {
        super(CoordenadorCurso.class, entityManager);
    }

    public CoordenadorCurso findByUsuario(Long idUsuario) {
        try {
            Query query = getEntityManager().createNamedQuery("CoordenadorCurso.findByUsuario");

            query.setParameter("idUsuario", idUsuario);

            return (CoordenadorCurso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CoordenadorCurso findByProfessor(Long idProfessor) {
        try {
            Query query = getEntityManager().createNamedQuery("CoordenadorCurso.findByProfessor");

            query.setParameter("idProfessor", idProfessor);

            return (CoordenadorCurso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public CoordenadorCurso findByCurso(Long idCurso) {
        try {
            Query query = getEntityManager().createNamedQuery("CoordenadorCurso.findByCurso");

            query.setParameter("idCurso", idCurso);

            return (CoordenadorCurso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Curso> listCursos() {
        Query query = getEntityManager().createNamedQuery("CoordenadorCurso.listCursos");

        return query.getResultList();
    }

    public List<Professor> listProfessores() {
        Query query = getEntityManager().createNamedQuery("CoordenadorCurso.listProfessores");

        return query.getResultList();
    }

    public CoordenadorCurso findByProfessorCurso(Long idProfessor, Long idCurso) {
        try {
            Query query = getEntityManager().createNamedQuery("CoordenadorCurso.findByProfessorCurso");

            query.setParameter("idCurso", idCurso);
            query.setParameter("idProfessor", idProfessor);

            return (CoordenadorCurso) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<CoordenadorCurso> findByAtivo (Boolean ativo){
        Query query = getEntityManager().createNamedQuery("CoordenadorCurso.findByAtivo");
        
        query.setParameter("ativo", ativo);

        return query.getResultList();        
    }
}