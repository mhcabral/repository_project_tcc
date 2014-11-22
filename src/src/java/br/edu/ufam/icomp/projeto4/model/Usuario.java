package br.edu.ufam.icomp.projeto4.model;

import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * @author janderson
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.login", query = "SELECT c FROM Usuario c WHERE c.cpf= :cpf and c.pwd=:pwd"),
    //@NamedQuery(name = "Usuario.login", query = "SELECT c FROM Usuario c WHERE c.cpf= :cpf"),
    @NamedQuery(name = "Usuario.cpf", query = "SELECT u FROM Usuario u WHERE u.cpf= :cpf"),
    @NamedQuery(name="Usuario.emailAndPerfil",query="SELECT u FROM Usuario u WHERE u.email = :email AND u.role = :role"),
    @NamedQuery(name="Usuario.cpfAndPerfil",query="SELECT u FROM Usuario u WHERE u.cpf = :cpf AND u.role = :role"),
    @NamedQuery(name="Usuario.cpfAndAtivo",query="SELECT u FROM Usuario u WHERE u.cpf = :cpf AND u.ativo = :ativo"),
    @NamedQuery(name="Usuario.emailAndAtivo",query="SELECT u FROM Usuario u WHERE u.email = :email AND u.ativo = :ativo")      
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "id_usuario", sequenceName = "usuario_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "id_usuario", strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private Long id;
    
    @Column(name="tx_nome",length=255, nullable=false)
    @NotBlank(message = "O campo nome é obrigatório.")
    @Length(max = 255, message = "O campo nome deve conter no máximo 255 caracteres.")    
    private String nome;

    @Column(name = "tx_cpf", length = 11, nullable = false)
    @NotBlank(message = "O campo cpf é obrigatório.")
    @Length(max = 15, message = "O campo cpf deve conter no máximo 11 caracteres.")
    private String cpf;
    
    @Column(name = "tx_email", length = 255, nullable = false)
    @NotBlank(message = "O campo e-mail é obrigatório.")
    @Length(max = 255, message = "O campo e-mail deve conter no máximo 255 caracteres.")        
    private String email;
    
    @Column(name = "tx_perfil", nullable = false)
    @Enumerated(EnumType.STRING)
    private Perfil role;
    
    @Column(name = "tx_pwd", length = 64, nullable = false)
    @NotBlank(message = "O campo senha é obrigatório.")
    @Length(max = 64, message = "A senha deve conter no máximo 64 caracteres.")            
    private String pwd;
    
    @Column(nullable = false)
    private Boolean ativo;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }        

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Perfil getRole() {
        return role;
    }

    public Integer getPerfil() {

        return role.ordinal();
    }

    public void setRole(Perfil role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {        
        this.pwd = pwd;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
