package br.edu.ufam.icomp.projeto4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp
public class SendMail extends Thread{

    private String mailSMTPServer;
    private String mailSMTPServerPort;
    
    private String para;
    private String assunto;
    private String mensagem;
    

    /*
     * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL 
     * e a porta usada por ele
     */

    public SendMail() {
        this.mailSMTPServer = "smtp.gmail.com";
        this.mailSMTPServerPort = "465";
    }
    
    
    
    /*
     * caso queira mudar o servidor e a porta, so enviar para o contrutor
     * os valor como string
     */

    private SendMail(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }
    
    private void sendMail(String to, String subject, String message) {
        
        sendMail("passcomeback@gmail.com",to, subject, message);
    
    }

    private  void sendMail(String from, String to, String subject, String message) {
        

        Properties props = new Properties();

        // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado
                /*
         props.setProperty("proxySet","true");
         props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy
         props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy
         */

        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL
        props.put("mail.smtp.auth", "true"); //ativa autenticacao
        props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", mailSMTPServerPort); //porta
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        //Cria um autenticador que sera usado a seguir
        SimpleAuth auth = null;
        auth = new SimpleAuth("passcomeback@gmail.com", "mr1d!0t4");

        //Session - objeto que ira realizar a conexão com o servidor
		/*Como há necessidade de autenticação é criada uma autenticacao que
         * é responsavel por solicitar e retornar o usuário e senha para 
         * autenticação */
        Session session = Session.getDefaultInstance(props, auth);
        session.setDebug(false); //Habilita o LOG das ações executadas durante o envio do email

        //Objeto que contém a mensagem
        Message msg = new MimeMessage(session);

        try {
            //Setando o destinatário
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //Setando a origem do email
            msg.setFrom(new InternetAddress(from));
            //Setando o assunto
            msg.setSubject(subject);
            //Setando o conteúdo/corpo do email
            msg.setContent(message, "text/html");

        } catch (Exception e) {
            System.out.println(">> Erro: Completar Mensagem");
            e.printStackTrace();
        }
        //Objeto encarregado de enviar os dados para o email
        Transport tr;
        try {
            tr = session.getTransport("smtp"); //define smtp para transporte
			/*
             *  1 - define o servidor smtp
             *  2 - seu nome de usuario do gmail
             *  3 - sua senha do gmail
             */
            tr.connect(mailSMTPServer, "passcomeback@gmail.com", "mr1d!0t4");

            msg.saveChanges(); // don't forget this
            //envio da mensagem
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(">> Erro: Envio Mensagem");
            e.printStackTrace();
        }

    }

    @Override //Paralelizando envio em Thread
    public void run() {
        
        sendMail(para, assunto, mensagem);        
                
    }

    /**
     * @param para the para to set
     */
    public void setPara(String para) {
        this.para = para;
    }

    /**
     * @param assunto the assunto to set
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
