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
import javax.persistence.Temporal;

/**
 *
 * @author janderson
 */


@Entity
@NamedQueries({
    @NamedQuery(name = "Notificacao.FindByUser", query = "SELECT c FROM Notificacao c WHERE c.usuario.id = :idUsuario")})
public class Notificacao implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(optional=false)
    private Usuario usuario;
    
    @Column(length=150, nullable=false)
    private String notificacao;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dtinclusao;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the notificacao
     */
    public String getNotificacao() {
        return notificacao;
    }

    /**
     * @param notificacao the notificacao to set
     */
    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    /**
     * @return the dtinclusao
     */
    public Date getDtinclusao() {
        return dtinclusao;
    }

    /**
     * @param dtinclusao the dtinclusao to set
     */
    public void setDtinclusao(Date dtinclusao) {
        this.dtinclusao = dtinclusao;
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
        if (!(object instanceof Notificacao)) {
            return false;
        }
        Notificacao other = (Notificacao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuario.getNome()+": "+notificacao;
    }

    
}
