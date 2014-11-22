package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.Notificacao;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Janderson
 */
@Component
public class NotificacaoDAO extends DAOImpl<Notificacao> {

    public NotificacaoDAO( EntityManager entityManager) {
        super(Notificacao.class, entityManager);
    }
    
    public List<Notificacao> listByUser(Long usuarioID){
                
        Query query = getEntityManager().createNamedQuery("Notificacao.FindByUser");
        query.setParameter("idUsuario", usuarioID);
        
        return query.getResultList();
        
    }
    
    
}
