package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.model.Professor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Bruna
 */
@Component
public class ProfessorDAO extends DAOImpl<Professor>{
    public ProfessorDAO(EntityManager entityManager) {
        super(Professor.class, entityManager);
    }
    
    public Professor findByUsuario(Long idUsuario){
        Query query = this.getEntityManager().createNamedQuery("Professor.findByUsuario");
        
        query.setParameter("pId", idUsuario);
        
        return (Professor) query.getSingleResult();        
    }
    
    public List<Professor> listProfessorNotIn(List<Professor> professores, Perfil perfil, boolean ativo){
        if(professores.isEmpty()){
            return (this).findAll();
        }
        
        Query query = getEntityManager().createNamedQuery("Professor.listProfessorNotIn");

        query.setParameter("professorList", professores);
        query.setParameter("role", perfil);
        query.setParameter("ativo", ativo);

        return query.getResultList();
    }
    
    public List<Professor> findByPerfisAndAtivo(List<Perfil> perfis, boolean ativo){
        EntityManager entityManager = this.getEntityManager();
        Query query = entityManager.createNamedQuery("Professor.findByPerfisAndAtivo");
        
        query.setParameter("roles", perfis);
        query.setParameter("ativo", ativo);
        
        return query.getResultList();
    }
}