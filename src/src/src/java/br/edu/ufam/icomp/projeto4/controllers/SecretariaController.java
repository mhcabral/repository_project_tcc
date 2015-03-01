/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.SecretariaDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.model.Secretaria;
import br.edu.ufam.icomp.projeto4.model.Usuario;

/**
 *
 * @author Thiago Santos
 */
@Resource
public class SecretariaController {

    private final Result result;
    private final Validator validator;
    private final SecretariaDAO secretariaDAO;
    private CursoDAO cursoDAO;
    private UsuariosDao usuariosDao;
    private Notificador notificador;

    public SecretariaController(Result result, Validator validator, SecretariaDAO secretariaDAO, CursoDAO cursoDAO, UsuariosDao usuariosDao, Notificador notificador) {
        this.result = result;
        this.validator = validator;
        this.secretariaDAO = secretariaDAO;
        this.cursoDAO = cursoDAO;
        this.usuariosDao = usuariosDao;
        this.notificador = notificador;
    }

    @Get("/secretarias")
    public void index() {
        result.include("secretariaList", secretariaDAO.findAll());
    }

    @Get("/secretarias/create")
    public void create() {
        result.include("operacao", "Cadastro");

        result.include("cursoList", cursoDAO.findAll());
    }

    @Post("/secretarias")
    public void cadastrar(final Secretaria secretaria) {
        Usuario usuario = secretaria.getUsuario();

        Usuario usuarioCPF = this.usuariosDao.findByCPFAndAtivo(usuario.getCpf(), true);
        Usuario usuarioEmail = this.usuariosDao.findByEmailAndAtivo(usuario.getEmail(), true);

        if (usuarioCPF != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse CPF.", "secretaria.usuario.cpf"));
        } else {
            secretaria.getUsuario().setAtivo(true);
            secretaria.getUsuario().setRole(Perfil.SECRETARIA);

            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];
            }

            secretaria.getUsuario().setPwd(senha);
        }

        if (usuarioEmail != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse email.", "secretaria.usuario.email"));
        }

        this.validator.validate(secretaria);

        this.validator.onErrorRedirectTo(this).create();

        this.usuariosDao.create(secretaria.getUsuario());

        this.secretariaDAO.create(secretaria);
        
        String conteudo =
                "<html>"
                + "    <head>"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>"
                + "    <body>"
                + "        <h2>Caro(a) " + usuario.getNome() + ", </h2"
                + "        <p>"
                + "           Foi configurado seu perfil de Secretaria para acesso no "
                + "           Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"                
                + "        <p>"
                + "         <b>Seus dados para acesso são:<b><br/>"
                + "         Seu CPF: " + usuario.getCpf() + "<br/>"
                + "         Senha: " + usuario.getPwd() + "<br/>"
                + "        </p>"
                + "    </body>"
                + "</html>";       
        
        this.notificador.enviarEmail(usuario.getEmail(), conteudo, "Sistema Atividades Extracurriculares] Dados para acesso no sistema.");

        result.include("success", "cadastrada");

        this.result.redirectTo(SecretariaController.class).index();
    }

    @Get("/secretarias/{id}/edit")
    public Secretaria edit(Long id) {
        Secretaria secretaria = secretariaDAO.findById(id);

        if (secretaria == null) {
            this.validator.add(new ValidationMessage("Desculpe! Secretaria não encontrada", "secretaria.id"));
        }

        this.validator.onErrorRedirectTo(this).index();

        result.include("operacao", "Edição");

        result.include("cursoList", cursoDAO.findAll());

        return secretaria;
    }

    @Put("/secretarias")
    public void altera(Secretaria secretaria) {

        Secretaria secretariaEncontrada = secretariaDAO.findById(secretaria.getId());
        
        if (secretariaEncontrada == null) {
            this.validator.add(new ValidationMessage("Desculpe! Secretaria não encontrada.'", "secretaria.id"));
        }

        validator.onErrorRedirectTo(this).edit(secretaria.getId());

        Usuario usuarioCPF = this.usuariosDao.findByCPFAndAtivo(secretaria.getUsuario().getCpf(), true);
        Usuario usuarioEmail = this.usuariosDao.findByEmailAndAtivo(secretaria.getUsuario().getEmail(), true);                

        if (usuarioEmail != null && secretaria.getUsuario().getId() != usuarioEmail.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse email.", "secretaria.usuario.email"));
        }

        if (usuarioCPF != null && secretaria.getUsuario().getId() != usuarioCPF.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com o CPF.", "secretaria.usuario.cpf"));
        } else {
            secretaria.getUsuario().setAtivo(true);
            secretaria.getUsuario().setRole(Perfil.SECRETARIA);

            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];
            }

            secretaria.getUsuario().setPwd(senha);
        } 

        this.validator.validate(secretaria);

        this.validator.onErrorRedirectTo(this).edit(secretaria.getId());

        this.usuariosDao.update(secretaria.getUsuario());

        this.secretariaDAO.update(secretaria);

        this.result.include("success", "editada");

        this.result.redirectTo(this).index();                                       
    }

    @Get("/secretarias/{id}/show")
    public Secretaria show(Long id) {
        Secretaria secretaria = secretariaDAO.findById(id);

        if (secretaria == null) {
            validator.add(new ValidationMessage("Desculpe! Secretaria não encontrada", "secretaria.id"));
        }

        validator.onErrorRedirectTo(this).index();

        return secretariaDAO.findById(id);
    }

    @Get("/secretarias/{id}/remove")
    public void remove(Long id) {
        Secretaria secretaria = secretariaDAO.findById(id);


        if (secretaria == null) {
            validator.add(new ValidationMessage("Desculpe! Secretaria não encontrada", "secretaria.id"));
        }

        validator.onErrorRedirectTo(this).index();
        
        Usuario usuario = secretaria.getUsuario();

        usuario.setAtivo(false);

        this.usuariosDao.update(usuario);

        this.result.include("success", "removida");

        this.result.redirectTo(this).index();                        
    }
}