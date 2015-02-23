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
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Janderson
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Disciplina.listAll", query = "SELECT c FROM Disciplina c "),
    @NamedQuery(name = "Disciplina.findByCodigo", query = "SELECT c FROM Disciplina c WHERE c.codigo= :codigo"),
    @NamedQuery(name = "Disciplina.findByNome", query = "SELECT c FROM Disciplina c WHERE c.nome= :nome"),
    @NamedQuery(name = "Disciplina.findById", query = "SELECT c FROM Disciplina c WHERE c.id= :id")})
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_disciplina", sequenceName = "disciplina_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_disciplina", strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 15, unique = true)
    @NotBlank(message = "O campo código é obrigatório.")
    @Length(max = 15, message = "O campo código deve conter no máximo 15 caracteres.")
    private String codigo;
    @Column(nullable = false, length = 200, unique = true)
    @NotBlank(message = "O campo nome é obrigatório.")
    @Length(max = 200, message = "O campo nome deve conter no máximo 200 caracteres.")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Disciplina)) {
            return false;
        }
        Disciplina other = (Disciplina) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * @return the object string representation
     */
    @Override
    public String toString() {
        return codigo + " - " + getNome();
    }
}