package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Grupo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Thiago Santos
 */
@Component
public class GrupoDAO extends DAOImpl<Grupo> {

    public GrupoDAO( EntityManager entityManager) {
        super(Grupo.class, entityManager);
    }

    public List<Grupo> SelecionaPorCodigo(String codigo, String nome) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Grupo.findByCodigoGrupo");
        
        query.setParameter("codigo", codigo);
        query.setParameter("nome", nome);
        
        return query.getResultList();
    }
    
    public List<Grupo> listGrupoNotIN(List<Grupo> grupoList){
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("Grupo.listGrupoNotIN");
        
        query.setParameter("grupoList", grupoList);        
        
        return query.getResultList();
    }
}
