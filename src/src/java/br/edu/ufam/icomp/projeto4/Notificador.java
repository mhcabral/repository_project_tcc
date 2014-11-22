package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.ioc.Component;
import br.edu.ufam.icomp.projeto4.dao.NotificacaoDAO;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Notificacao;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Janderson
 */
@Component
public class Notificador {

    SessionData sessionData;
    NotificacaoDAO notificacaoDAO;
    Result result;

    public Notificador(SessionData sessionData, NotificacaoDAO notificacaoDAO, Result result) {
        this.sessionData = sessionData;
        this.notificacaoDAO = notificacaoDAO;
    }

    public void enviarEmail(String email, String mensagem, String assunto) {
        SendMail sendMail = new SendMail();
        sendMail.setAssunto(assunto);
        sendMail.setPara(email);
        sendMail.setMensagem(mensagem);
        sendMail.start();
    }

    public void notifica(Usuario usuario, String descricao, String assunto, String assuntNotificacao, Boolean enviaEmail) {

        Notificacao notificacao = new Notificacao();

        notificacao.setUsuario(usuario);

        notificacao.setNotificacao(assuntNotificacao);

        notificacao.setDtinclusao(new Date());

        if (enviaEmail) {

            SendMail sendMail = new SendMail();
            sendMail.setAssunto(assunto);
            sendMail.setPara(usuario.getEmail());
            sendMail.setMensagem(descricao);
            sendMail.start();

        }

        notificacaoDAO.create(notificacao);
    }

    /**
     *
     * @param usuario
     * @param notificacao
     * @param enviaEmail
     */
    public void notifica(Usuario usuario, String descricao, Boolean enviaEmail) {

        Notificacao notificacao = new Notificacao();

        notificacao.setUsuario(usuario);

        notificacao.setNotificacao(descricao);

        notificacao.setDtinclusao(new Date());

        if (enviaEmail) {

            SendMail sendMail = new SendMail();

            sendMail.setAssunto("Atividades Complementares Icomp");
            sendMail.setPara(usuario.getEmail());
            sendMail.setMensagem(descricao);
            sendMail.start();

        }

        notificacaoDAO.create(notificacao);
    }

    /**
     *
     * @param usuario
     * @param notificacao
     */
    public void notifica(Usuario usuario, String descricao) {

        notifica(usuario, descricao, Boolean.FALSE);

    }

    /**
     *
     * @param id
     */
    @Permission({Perfil.ALUNO, Perfil.COORDENADOR, Perfil.SECRETARIA, Perfil.ROOT, Perfil.COORDENADORACAD})
    public void removeNotificacao(Long id) {

        Notificacao notificacao = notificacaoDAO.findById(id);


        System.out.print("$>>>" + notificacao.getNotificacao());



        if (notificacao.getUsuario().getId() == sessionData.getUsuario().getId()) {

            notificacaoDAO.delete(notificacao);
        }


    }

    /**
     *
     * @return List<Notificacao>
     */
    public List<Notificacao> getNotificacaoList() {

        return notificacaoDAO.listByUser(sessionData.getUsuario().getId());

    }
}
