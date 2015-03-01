package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author janderson
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Monitoria.listByIdPeriodo", query = "SELECT c FROM Monitoria c WHERE c.periodo.id= :id"),
    @NamedQuery(name = "Monitoria.listByDisciplina", query = "SELECT c FROM Monitoria c WHERE c.disciplina.id = :idDisciplina"),
    @NamedQuery(name = "Monitoria.listByCurso", query = "SELECT c FROM Monitoria c WHERE c.curso.id = :idCurso"),
    @NamedQuery(name = "Monitoria.listByCursos", query = "SELECT c FROM Monitoria c WHERE c.curso IN (:cursos)")
})
public class Monitoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(optional = false)
    @NotNull(message = "O campo Curso é obrigatório.")
    private Curso curso;
    @ManyToOne(optional = false)
    @NotNull(message = "O campo Professor é obrigatório.")
    private Professor professor;
    @ManyToOne(optional = false)
    @NotNull(message = "O campo Disciplina é obrigatório.")
    private Disciplina disciplina;
    @Column(nullable = false)
    @NotNull(message = "O campo Turma é obrigatório.")
    @Min(value = 1, message = "O valor do campo Turma deve ser maior que zero.")
    private Integer turma;
    @Column(nullable = false)
    @NotNull(message = "O campo Vagas é obrigatório.")
    @Min(value = 1, message = "O valor do campo Vagas deve ser maior que zero.")
    private Integer vagas;
    @JoinColumn(name = "id_periodo")
    @ManyToOne
    private PeriodoLetivo periodo;
    @OneToMany
    private List<HorarioTurma> horario;

    /**
     * @return the Curso.id
     */
    public Long getId() {
        return id;
    }

    public PeriodoLetivo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoLetivo periodo) {
        this.periodo = periodo;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     * @return the professor
     */
    public Professor getProfessor() {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    /**
     * @return the disciplina
     */
    public Disciplina getDisciplina() {
        return disciplina;
    }

    /**
     * @param disciplina the disciplina to set
     */
    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    /**
     * @return the turma
     */
    public Integer getTurma() {
        return turma;
    }

    /**
     * @param turma the turma to set
     */
    public void setTurma(Integer turma) {
        this.turma = turma;
    }

    /**
     * @return the vagas
     */
    public Integer getVagas() {
        return vagas;
    }

    /**
     * @param vagas the vagas to set
     */
    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public List<HorarioTurma> getHorario() {
        return horario;
    }

    public void setHorario(List<HorarioTurma> horario) {
        this.horario = horario;
    }

    /**
     * @return the hash code of object
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * @param vagas the object to be compared
     * @return the boolean result of comparison
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monitoria)) {
            return false;
        }
        Monitoria other = (Monitoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the object string representation
     */
//    @Override
//    public String toString() {
//        return curso + " - " + disciplina + " - " + professor;
//    }
}
