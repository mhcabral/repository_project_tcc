/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.CoordenadorAcademico;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Professor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Thiago Santos
 */
@Component
public class CoordenadorAcademicoDAO extends DAOImpl<CoordenadorAcademico> {

    public CoordenadorAcademicoDAO(EntityManager entityManager) {
        super(CoordenadorAcademico.class, entityManager);
    }

    public List<Professor> listProfessores() {
        Query query = getEntityManager().createNamedQuery("CoordenadorAcademico.listProfessores");

        return query.getResultList();
    }

    public List<Curso> listCursos() {
        Query query = getEntityManager().createNamedQuery("CoordenadorAcademico.listCursos");

        return query.getResultList();
    }

    public CoordenadorAcademico findByUsuario(Long idUsuario) {
        Query query = this.getEntityManager().createNamedQuery("CoordenadorAcademico.findByUsuario");

        query.setParameter("idUsuario", idUsuario);

        try {
            return (CoordenadorAcademico) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
}