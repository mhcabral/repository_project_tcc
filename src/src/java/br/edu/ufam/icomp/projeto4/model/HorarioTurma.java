package br.edu.ufam.icomp.projeto4.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;

/**
 *
 * @author Bruna
 */
@Entity
public class HorarioTurma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_horario", sequenceName = "horario_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_horario", strategy = GenerationType.AUTO)
    private Integer id;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date inicio;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fim;
    
    public HorarioTurma() {
        id = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    @Override
    public String toString() {
        return "HorarioTurma{" + "id=" + id + ", inicio=" + inicio + ", fim=" + fim + '}';
    }

    public void ajustaData(Date data) {
        int dia_semana_data = data.getDay();
        int dia_inicio = inicio.getDay();
        int dia_fim = fim.getDay();
           
        int dif_inicio = dia_inicio - dia_semana_data;
        int dif_fim =  dia_fim - dia_semana_data;
        
        inicio = new Date(inicio.getTime()+((1000*24*60*60)*dif_inicio)); 
        fim = new Date(fim.getTime()+((1000*24*60*60)*dif_fim)); 
    }
}