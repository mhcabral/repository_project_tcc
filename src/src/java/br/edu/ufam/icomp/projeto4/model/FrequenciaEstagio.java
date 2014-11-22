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
    @NamedQuery(name = "FrequenciaEstagio.findById", query = "SELECT f FROM FrequenciaEstagio f WHERE f.id = :id"),
    @NamedQuery(name = "FrequenciaEstagio.findByDataId", query = "SELECT f FROM FrequenciaEstagio f WHERE f.data = :data AND f.estagio.id = :id"),
    @NamedQuery(name = "FrequenciaEstagio.listByEstagio", query = "SELECT f FROM FrequenciaEstagio f WHERE f.estagio.id = :idEstagio"),
    @NamedQuery(name = "FrequenciaEstagio.listByDataInicioFim", query = "SELECT f FROM FrequenciaEstagio f WHERE f.estagio.id = :id AND f.data BETWEEN :dtInicio AND :dtFim"),
    @NamedQuery(name = "FrequenciaEstagio.sumByDataInicioFim", query = "SELECT SUM(f.cargaHoraria) FROM FrequenciaEstagio f WHERE f.estagio.id = :id AND f.data BETWEEN :dtInicio AND :dtFim GROUP BY f.estagio.id")
})
public class FrequenciaEstagio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_frequencia_estagio", sequenceName = "frequencia_estagio_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_frequencia_estagio", strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "dt_frequencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;
    @Column
    private Integer cargaHoraria;
    @Column
    private String descricao;
    @ManyToOne
    private Estagio estagio;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Estagio getEstagio() {
        return estagio;
    }

    public void setEstagio(Estagio estagio) {
        this.estagio = estagio;
    }

    @Override
    public String toString() {
        return "FrequenciaEstagio{" + "id=" + id + ", data=" + data + ", cargaHoraria=" + cargaHoraria + ", descricao=" + descricao + ", estagio=" + estagio + '}';
    }
}