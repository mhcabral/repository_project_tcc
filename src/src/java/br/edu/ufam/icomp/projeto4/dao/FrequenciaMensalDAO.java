package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensal;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruna
 */
@Component
public class FrequenciaMensalDAO extends DAOImpl<FrequenciaMensal> {
    public FrequenciaMensalDAO(EntityManager entityManager) {
        super(FrequenciaMensal.class, entityManager);
    }
}