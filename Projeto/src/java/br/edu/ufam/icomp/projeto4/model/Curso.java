package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author janderson
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Curso.listAll", query = "SELECT c FROM Curso c "),
    @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id= :id"),
    @NamedQuery(name = "Curso.findByCodigoCurso", query = "SELECT c FROM Curso c WHERE c.codigo = :codigo AND c.curso = :nome"),
    @NamedQuery(name = "Curso.listCursoNotIn", query = "SELECT c FROM Curso c WHERE c NOT IN(:cursoList)"),    
})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_curso", sequenceName = "curso_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_curso", strategy = GenerationType.AUTO)    
    private Long id;
    
    @NotNull(message="Desculpe! O código do curso é obrigatório.")
    @Column(nullable = false)   
    private String codigo;
    
    @NotNull(message="Desculpe! O nome do curso é obrigatório.")
    @Length(min=1, message="Desculpe! O nome do curso é obrigatório.")
    @Column(name = "tx_curso", length = 150, nullable = false)
    private String curso;            
    
    private int qntd_horas;

    public Curso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getQntd_horas() {
        return qntd_horas;
    }

    public void setQntd_horas(int qntd_horas) {
        this.qntd_horas = qntd_horas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 47 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
        hash = 47 * hash + (this.curso != null ? this.curso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Curso other = (Curso) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        if ((this.curso == null) ? (other.curso != null) : !this.curso.equals(other.curso)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigo + " - " + curso;
    }
}
