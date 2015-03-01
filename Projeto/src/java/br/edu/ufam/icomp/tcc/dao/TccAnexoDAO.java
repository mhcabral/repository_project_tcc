/**
 *
 * @author andre
 */

package br.edu.ufam.icomp.tcc.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.DAOImpl;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.tcc.model.TccAnexo;
import br.edu.ufam.icomp.tcc.model.TccAtividade;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component
public class TccAnexoDAO extends DAOImpl<TccAnexo> {
    
    public TccAnexoDAO(EntityManager entityManager) {
        super(TccAnexo.class, entityManager);
    }
    
    public List<TccAnexo> findByTcc(Long idTcc) {
        EntityManager entityManager = this.getEntityManager();
        
        Query query = entityManager.createNamedQuery("TccAnexo.findByTcc");
        
        query.setParameter("id", idTcc);        
        
        return query.getResultList();
    }
    
}
