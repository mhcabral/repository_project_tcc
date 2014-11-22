package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Bruna
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Frequencia.findById", query = "SELECT f FROM Frequencia f WHERE f.id = :id"),
    @NamedQuery(name = "Frequencia.findByDataId", query = "SELECT f FROM Frequencia f WHERE f.data = :data AND f.inscricao.id = :id"),
    @NamedQuery(name = "Frequencia.listByInscricao", query = "SELECT f FROM Frequencia f WHERE f.inscricao.id = :idInscricao"),
    @NamedQuery(name = "Frequencia.listByDataInicioFim", query = "SELECT f FROM Frequencia f WHERE f.inscricao.id = :id AND f.data BETWEEN :dtInicio AND :dtFim"),
    @NamedQuery(name = "Frequencia.sumByDataInicioFim", query = "SELECT SUM(f.cargaHoraria) FROM Frequencia f WHERE f.inscricao.id = :id AND f.data BETWEEN :dtInicio AND :dtFim GROUP BY f.inscricao.id")
})
public class Frequencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_frequencia", sequenceName = "frequencia_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_frequencia", strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "dt_frequencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;
    @Column
    private Integer cargaHoraria;
    @ManyToOne
    private InscricaoMonitoria inscricao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public InscricaoMonitoria getInscricao() {
        return inscricao;
    }

    public void setInscricao(InscricaoMonitoria inscricao) {
        this.inscricao = inscricao;
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
        if (!(object instanceof Frequencia)) {
            return false;
        }
        Frequencia other = (Frequencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Frequencia{" + "id=" + id + ", data=" + data + ", cargaHoraria=" + cargaHoraria + ", inscricao=" + inscricao + '}';
    }
}