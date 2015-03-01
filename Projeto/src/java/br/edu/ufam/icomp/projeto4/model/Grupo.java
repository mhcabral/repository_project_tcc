package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Thiago Santos
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Grupo.listAll", query = "SELECT g FROM Grupo g "),
    @NamedQuery(name = "Grupo.findById", query = "SELECT g FROM Grupo g WHERE g.id= :id"),
    @NamedQuery(name = "Grupo.findByCodigoGrupo", query = "SELECT g FROM Grupo g WHERE g.codigo = :codigo AND g.nome = :nome"),
    @NamedQuery(name = "Grupo.listGrupoNotIN", query = "SELECT g FROM Grupo g WHERE g NOT IN (:grupoList) ORDER BY g.codigo")
})
public class Grupo implements Serializable {

    @Id
    @SequenceGenerator(name = "id_grupo", sequenceName = "grupo_id_seq", initialValue = 1)
    @GeneratedValue(generator = "id_grupo", strategy = GenerationType.AUTO)
    private Long id;
    private String codigo;
    @Column(nullable = false, unique = true)
    private String nome;
    @OneToMany(mappedBy = "grupo")
    private List<Atividade> atividades;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
    
    

    @Override
    public String toString() {
        return "Grupo " + codigo + " - " + nome;
    }
}
