package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.model.HorarioTurma;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruna
 */
@Component
public class HorarioTurmaDAO extends DAOImpl<HorarioTurma> {
    public HorarioTurmaDAO(EntityManager entityManager) {
        super(HorarioTurma.class, entityManager);
    }
}