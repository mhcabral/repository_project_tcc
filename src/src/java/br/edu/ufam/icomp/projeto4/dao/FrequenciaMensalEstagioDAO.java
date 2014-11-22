package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.FrequenciaMensalEstagio;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruna
 */
@Component
public class FrequenciaMensalEstagioDAO extends DAOImpl<FrequenciaMensalEstagio> {
    public FrequenciaMensalEstagioDAO(EntityManager entityManager) {
        super(FrequenciaMensalEstagio.class, entityManager);
    }
}