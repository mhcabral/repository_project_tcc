package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Monitoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Janderson
 */
@Component
public class MonitoriaDAO extends DAOImpl<Monitoria> {

    public MonitoriaDAO(EntityManager entityManager) {
        super(Monitoria.class, entityManager);
    }    

    public List<Monitoria> listByIdPeriodo(Long id) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Monitoria.listByIdPeriodo");
        query.setParameter("id", id);

        return query.getResultList();
    }
    
    public List<Monitoria> listByDisciplina(Long id) {

        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Monitoria.listByDisciplina");
        query.setParameter("idDisciplina", id);

        return query.getResultList();
    }
    
    public List<Monitoria> listByCurso(Long id) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Monitoria.listByCurso");
        query.setParameter("idCurso", id);

        return query.getResultList();
    }
    
    public List<Monitoria> listByCursos(List<Curso> cursos) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Monitoria.listByCursos");
        query.setParameter("cursos", cursos);

        return query.getResultList();
    }
}
