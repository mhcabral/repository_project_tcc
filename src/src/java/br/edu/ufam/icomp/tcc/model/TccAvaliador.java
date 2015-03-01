package br.edu.ufam.icomp.tcc.model;

/**
 *
 * @author TAMMY
 */
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tccavaliador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TccAvaliador.findAll", query = "SELECT a FROM TccAvaliador a"),
    @NamedQuery(name = "TccAvaliador.findById", query = "SELECT a FROM  TccAvaliador a WHERE a.id = :idAvaliador")
})
public class TccAvaliador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="id_tcc_avaliador", sequenceName="tcc_avaliador_id_seq", initialValue=1)
    @GeneratedValue(generator="id_tcc_avaliador", strategy=GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)   
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)     
    @Column(name = "email")
    private String email;

    public TccAvaliador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nome;
    }
    
}