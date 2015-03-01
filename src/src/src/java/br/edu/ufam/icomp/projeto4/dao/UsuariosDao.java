package br.edu.ufam.icomp.projeto4.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Leo
 */
@Component
public class UsuariosDao extends DAOImpl<Usuario> {

    public UsuariosDao(EntityManager entityManager) {
        super(Usuario.class, entityManager);
    }

    
    //Função para criar hash da senha informada
    private String md5(String senha) {
        MessageDigest md = null;       
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String seed = "M@h 0i3¡ 0lh4 @ s3m3nt1nh@ :D";        
        String password = seed+senha;
        BigInteger hash = new BigInteger(1, md.digest( password.getBytes()));
        String sen = hash.toString(16);
        return sen;
    }     
    
    @Override
    public void create(Usuario t) {   
        
        this.getEntityManager().getTransaction().begin();
        
        t.setPwd(md5(t.getPwd())); 
        
        System.out.println(t.getPwd());
        
        this.getEntityManager().persist(t);
        
        this.getEntityManager().getTransaction().commit();
        
    }
    
    
    public Usuario find(Usuario usuario) {
        String cripto = md5(usuario.getPwd());
        //String cripto = "85106358420d80b994bdaf5812b1c811";
        usuario.setPwd(cripto);   
        
        System.out.println(usuario.getPwd());
        System.out.println(">>>>> Senha = " + md5("123456"));
        
        Query query = this.getEntityManager().createNamedQuery("Usuario.login");                
        
        query
                .setParameter("cpf", usuario.getCpf())
                .setParameter("pwd", usuario.getPwd());

        if (!query.getResultList().isEmpty()) {
            return (Usuario) query.getSingleResult();
        } else {
            return null;
        }
    }

    public boolean exists(Usuario usuario) {
        Query query = this.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf");
        query.setParameter("cpf", usuario.getCpf());
        return !query.getResultList().isEmpty();
    }

    public Usuario findByEmail(String email) {
        Query query = this.getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
        query.setParameter("email", email);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }

    public Usuario findByCPF(String cpf) {
        Query query = this.getEntityManager().createNamedQuery("Usuario.cpf");
        query.setParameter("cpf", cpf);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }

    public Usuario findByCPFAndAtivo(String cpf, Boolean ativo) {
        Query query = this.getEntityManager().createNamedQuery("Usuario.cpfAndAtivo");

        query.setParameter("cpf", cpf);
        query.setParameter("ativo", ativo);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }
    
    public Usuario findByEmailAndAtivo(String email, Boolean ativo) {
        Query query = this.getEntityManager().createNamedQuery("Usuario.emailAndAtivo");

        query.setParameter("email", email);
        query.setParameter("ativo", ativo);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException n) {
            return null;
        }
    }

    public Usuario findByEmailAndPerfil(String email, Perfil perfil) {
        Query query = this.getEntityManager().createNamedQuery("Usuario.emailAndPerfil");

        query.setParameter("email", email);
        query.setParameter("role", perfil);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public Usuario findByCPFAndPerfil(String cpf, Perfil perfil) {
        Query query = this.getEntityManager().createNamedQuery("Usuario.cpfAndPerfil");

        query.setParameter("cpf", cpf);
        query.setParameter("role", perfil);

        try {
            return (Usuario) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
