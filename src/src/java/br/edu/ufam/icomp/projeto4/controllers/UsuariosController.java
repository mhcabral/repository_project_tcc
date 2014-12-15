package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.IndexController;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.SendMail;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.PeriodoCadMonDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoInsMonDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.NoCache;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.interceptor.Public;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Resource
@NoCache
public class UsuariosController {

    private final SessionData sessaoDoUsuario;
    private final Result result;
    private final Validator validator;
    private UsuariosDao usuarioDAO;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private PeriodoCadMonDAO periodoCadMonDAO;
    private PeriodoInsMonDAO periodoInsMonDAO;
    private Notificador notificador;

    public UsuariosController(UsuariosDao dao, SessionData sessaoDoUsuario, Result result, Validator validator,
            Notificador notificador, PeriodoLetivoDAO periodoLetivoDAO, PeriodoCadMonDAO periodoCadMonDAO, 
            PeriodoInsMonDAO periodoInsMonDAO) {
        this.usuarioDAO = dao;
        this.sessaoDoUsuario = sessaoDoUsuario;
        this.validator = validator;
        this.result = result;
        this.notificador = notificador;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.periodoCadMonDAO = periodoCadMonDAO;
        this.periodoInsMonDAO = periodoInsMonDAO;
    }
    
         
    
    @SuppressWarnings("unchecked")
    @Permission(Perfil.ROOT)
    public List<Usuario> list() {

        return usuarioDAO.findAll();
    }

    @Permission(Perfil.ROOT)
    public void novo() {
    }

    @Permission(Perfil.ROOT)
    public void painel() {
    }

    @Public
    public void requestok() {
    }

    @Get("/login")
    public void loginForm() {
    }

    @Get("/lembrar")
    public void lembrar() {
    }

    @Path("/logout")
    public void logout() {
        sessaoDoUsuario.setUsuario(null);
        result.redirectTo(this).loginForm();
    }

    @Permission(Perfil.ROOT)
    @Post("/usuarios")
    public void adiciona(Usuario usuario) {

        if (usuarioDAO.exists(usuario)) {
            validator.add(new ValidationMessage("Login já existe",
                    usuario.getEmail()));
        }
        validator.onErrorUsePageOf(UsuariosController.class).novo();
        this.usuarioDAO.create(usuario);
        result.redirectTo(UsuariosController.class).loginForm();
    }

    @Post("/solicitarDados")
    public void solicitarDados(String email) {
        Usuario usuario = this.usuarioDAO.findByEmail(email);

        if (usuario != null) {
            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];

            }

            usuario.setPwd(senha);
            this.usuarioDAO.update(usuario);

            String destinatario = usuario.getEmail();//args[0];
            String mensagem = "Sua nova senha é: " + senha;//args[1];

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy ");
            String dia = sdf.format(new Date());

            SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
            String horario = sdf2.format(new Date());

            SendMail sm = new SendMail();
            sm.setAssunto("Recuperação de senha");
            sm.setPara(destinatario);
            sm.setMensagem(mensagem);
            sm.start();

            result.include("resultado", "encontrado");
            result.redirectTo(UsuariosController.class).loginForm();
        } else {
            System.out.println("nao encontrado");
            result.include("resultado", "não encontrado");
            result.redirectTo(UsuariosController.class).lembrar();
        }
    }

    @Post("/login")
    public void logar(Usuario usuario) {
        Usuario logado = usuarioDAO.find(usuario);        
        
        if (logado != null) {
            sessaoDoUsuario.setUsuario(logado);
            sessaoDoUsuario.setLetivoAtual(periodoLetivoDAO.findLetivoAtivo());
            sessaoDoUsuario.setPeriodoEstagio(periodoLetivoDAO.findEstagioAtivo());
            sessaoDoUsuario.setPeriodoMatricula(periodoLetivoDAO.findMatriculaAtivo());
            sessaoDoUsuario.setPeriodoInsMon(periodoInsMonDAO.findAtivo());
            sessaoDoUsuario.setPeriodoCadMon(periodoCadMonDAO.findAtivo());
            result.redirectTo(IndexController.class).index();
        } else {
            System.out.println("nullzao");
            validator.add(new ValidationMessage("Login e/ou senha inválidos",
                    "usuario.cpf"));
            result.include("error", "CPF ou senha incorretos!");
            validator.onErrorUsePageOf(UsuariosController.class).loginForm();
        }
    }

    private void naoEncontrado() {
    }
}