
package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Curso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class AlunoDAO extends DAOImpl<Aluno> {

    public AlunoDAO(EntityManager entityManager) {
        super(Aluno.class, entityManager);
    }

    public Aluno findByIdUsuario(Long idUsuario) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Aluno.findByIdUsuario");
        query.setParameter("id", idUsuario);
        Aluno aluno = (Aluno) query.getSingleResult();
        return aluno;
    }

    public List<Aluno> findByCurso(List<Curso> cursos) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Aluno.findByCurso");

        query.setParameter("cursoList", cursos);

        return query.getResultList();
    }

    public List<Aluno> findByCursoId(Long id) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Aluno.findByCursoId");

        query.setParameter("id", id);

        return query.getResultList();
    }

    public Aluno findByMatricula(String matricula) {
        EntityManager entityManager = this.getEntityManager();

        Query query = entityManager.createNamedQuery("Aluno.findByMatricula");

        query.setParameter("matricula", matricula);

        try {
            return (Aluno) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public List<Aluno> findByPerfilAndAtivo(Perfil perfil, boolean ativo){
        EntityManager entityManager = this.getEntityManager();
        Query query = entityManager.createNamedQuery("Aluno.findByPerfilAndAtivo");
        
        query.setParameter("role", perfil);
        query.setParameter("ativo", ativo);
        
        return query.getResultList();
    }
}