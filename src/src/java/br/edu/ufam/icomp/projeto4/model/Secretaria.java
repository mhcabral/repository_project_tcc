package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruna
 */
@Entity
@Table(name = "secretaria")
@NamedQueries({
    @NamedQuery(name = "Secretaria.findByUsuario", query = "SELECT s FROM Secretaria s WHERE s.usuario.id = :sId"),
    @NamedQuery(name="Secretaria.findByPerfilAndAtivo", query="SELECT s FROM Secretaria s WHERE s.usuario.role = :role AND s.usuario.ativo = :ativo"),
    @NamedQuery(name = "Secretaria.findByCPFAndAtivo", query = "SELECT s FROM Secretaria s WHERE s.usuario.id = :sId")    
})
public class Secretaria implements Serializable {

    @Id
    @SequenceGenerator(name = "id_secretaria", sequenceName = "secretaria_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_secretaria", strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "id_usuario", nullable = false)
    @OneToOne
    private Usuario usuario;
    @ManyToMany
    private List<Curso> cursos;

    public Secretaria() {
        cursos = new ArrayList<Curso>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void addCurso(Curso curso) {
        cursos.add(curso);
    }
}