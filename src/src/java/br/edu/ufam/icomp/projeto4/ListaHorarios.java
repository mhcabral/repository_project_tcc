package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.edu.ufam.icomp.projeto4.model.HorarioTurma;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Component
@SessionScoped
public class ListaHorarios {

    List<HorarioTurma> horarios;

    public ListaHorarios() {
        horarios = new ArrayList<HorarioTurma>();
    }

    public List<HorarioTurma> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioTurma> horarios) {
        this.horarios = horarios;
    }

    public void add(HorarioTurma horarioTurma) {
        this.horarios.add(horarioTurma);
    }

    public HorarioTurma remove(int id) {
        for (int i = 0; i < horarios.size(); i++) {
            if (horarios.get(i).getId() == id) {
                HorarioTurma h = horarios.get(i);
                horarios.remove(i);
                return h;
            }
        }
        return null;
    }

    public int next() {
        if (horarios.isEmpty()) {
            return 1;
        } else {
            return horarios.get(horarios.size() - 1).getId() + 1;
        }
    }

    public void clean() {
        horarios = new ArrayList<HorarioTurma>();
    }
}