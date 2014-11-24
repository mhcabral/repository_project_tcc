/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Curso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Thiago Santos
 */
@Component
public class CursoDAO extends DAOImpl<Curso> {

    public CursoDAO(EntityManager entityManager) {
        super(Curso.class, entityManager);
    }

    public List<Curso> SelecionaPorCodigo(String codigo, String curso) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Curso.findByCodigoCurso");

        query.setParameter("codigo", codigo);
        query.setParameter("nome", curso);

        return query.getResultList();
    }

    public List<Curso> listCursoNotIn(List<Curso> cursos) {
        if(cursos.isEmpty()){
            return (this).findAll();
        }
        
        Query query = getEntityManager().createNamedQuery("Curso.listCursoNotIn");

        query.setParameter("cursoList", cursos);

        return query.getResultList();
    }
    
    
    public List<Curso> listAll() {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Curso.listAll");

        return query.getResultList();
    }
}