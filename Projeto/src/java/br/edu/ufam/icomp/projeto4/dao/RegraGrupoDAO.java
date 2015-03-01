/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import br.edu.ufam.icomp.projeto4.model.RegraGrupo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Thiago Santos
 */
@Component
public class RegraGrupoDAO extends DAOImpl<RegraGrupo> {

    public RegraGrupoDAO(EntityManager entityManager) {
        super(RegraGrupo.class, entityManager);
    }

    public RegraGrupo findByCursoAndGrupo(Curso curso, Grupo grupo) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("RegraGrupo.findByCursoAndGrupo");

        query.setParameter("idCurso", curso.getId());
        query.setParameter("idGrupo", grupo.getId());

        try {
            return (RegraGrupo) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }

    }
    
    public List<Grupo> listGrupos(Long idCurso){
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("RegraGrupo.listGrupos");
        
        query.setParameter("idCurso", idCurso);
        
        return query.getResultList();
    }
    
    public List<RegraGrupo> findByCurso(Long idCurso){
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("RegraGrupo.findByCurso");
        
        query.setParameter("idCurso", idCurso);
        
        return query.getResultList();
    }
}
