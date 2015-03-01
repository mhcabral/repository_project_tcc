package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Disciplina;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Janderson
 */
@Component
public class DisciplinaDAO extends DAOImpl<Disciplina> {

    public DisciplinaDAO(EntityManager entityManager) {
        super(Disciplina.class, entityManager);
    }

    public Disciplina findByCodigo(String codigo) {
        try {
            Query query = getEntityManager().createNamedQuery("Disciplina.findByCodigo");

            query.setParameter("codigo", codigo);

            return (Disciplina) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Disciplina findByNome(String nome) {
        try {
            Query query = getEntityManager().createNamedQuery("Disciplina.findByNome");

            query.setParameter("nome", nome);

            return (Disciplina) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}