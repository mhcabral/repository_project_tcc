package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Thiago Santos
 */
@NamedQueries({
    @NamedQuery(name = "Regra.find", query = "select r from Regra r where r.atividade.id = :aId and r.curso.id = :cId"),
    @NamedQuery(name = "Regra.findAtividadeFromCurso", query = "SELECT r.atividade FROM Regra r WHERE r.curso.id = :cId"),
    @NamedQuery(name = "Regra.findByAtividadeCurso", query = "SELECT r FROM Regra r WHERE r.atividade.id = :aIdAtividade AND r.curso.id = :aIdCurso"),
    @NamedQuery(name = "Regra.findByAtividade", query = "SELECT r FROM Regra r WHERE r.atividade.id = :aId"),
    @NamedQuery(name = "Regra.findByCurso", query = "SELECT r FROM Regra r WHERE r.curso.id = :id ORDER BY r.atividade.grupo, r.atividade"),    
    @NamedQuery(name = "Regra.findByCursoAndGrupo", query = "SELECT r FROM Regra r WHERE r.curso.id = :idCurso AND r.atividade.grupo.id = :idGrupo ORDER BY r.atividade.codigo")    
})
@Entity
public class Regra implements Serializable {

    @Id
    @SequenceGenerator(name = "id_regra", sequenceName = "regra_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_regra", strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Atividade atividade;
    @ManyToOne
    private Curso curso;
    @Column(nullable = false)
    @NotNull(message = "O campo Máximo de Horas é obrigatório")
    private Integer maximoHoras;

    public Regra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Integer getMaximoHoras() {
        return maximoHoras;
    }

    public void setMaximoHoras(Integer maximoHoras) {
        this.maximoHoras = maximoHoras;
    }

    @Override
    public String toString() {
        return "Regra{" + "id=" + id + ", atividade=" + atividade + ", curso=" + curso + ", maximoHoras=" + maximoHoras + '}';
    }
}