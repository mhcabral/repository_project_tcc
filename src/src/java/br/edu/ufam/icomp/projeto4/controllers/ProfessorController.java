package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thiago
 */
@Permission(Perfil.ROOT)
@Resource
public class ProfessorController {

    private final Result result;
    private final Validator validator;
    private final ProfessorDAO professorDAO;
    private final UsuariosDao usuarioDAO;

    public ProfessorController(Result result, Validator validator, ProfessorDAO professorDAO, UsuariosDao usuariosDao) {
        this.result = result;
        this.validator = validator;
        this.professorDAO = professorDAO;
        this.usuarioDAO = usuariosDao;
    }

    @Get("/professores")
    public void index() {
        List<Perfil> perfis = new ArrayList<Perfil>();
        
        perfis.add(Perfil.COORDENADOR);
        perfis.add(Perfil.COORDENADORACAD);
        perfis.add(Perfil.PROFESSOR);        
        
        List<Professor> professores = this.professorDAO.findByPerfisAndAtivo(perfis, true);

        result.include("ProfessorList", professores);
    }

    @Get("/professores/create")
    public void create() {
        result.include("operacao", "Cadastro");
    }

    @Get("/professores/{id}/edit")
    public Professor edit(Long id) {
        result.include("operacao", "Edição");

        Professor professor = professorDAO.findById(id);

        if (professor == null) {
            this.validator.add(new ValidationMessage("Desculpe! Professor não foi encontrado.", "professor.id"));
        }

        this.validator.onErrorRedirectTo(ProfessorController.class).show(id);

        return professor;
    }

    @Get("/professores/{id}/show")
    public Professor show(Long id) {
        if (professorDAO.findById(id) == null) {
            this.result.redirectTo(ProfessorController.class).index();
        }

        return this.professorDAO.findById(id);
    }

    @Get("/professores/{id}/remove")
    public void remove(Long id) {
        Professor professor = this.professorDAO.findById(id);

        if (professorDAO.findById(id) == null) {
            validator.add(new ValidationMessage("Desculpe! Professor não encontrado", "professor.id"));
        }

        validator.onErrorUsePageOf(this).show(id);

        professor.getUsuario().setAtivo(false);

        this.usuarioDAO.update(professor.getUsuario());

        this.result.include("success", "removido");

        this.result.redirectTo(ProfessorController.class).index();
    }

    @Post("/professores")
    public void cadastrar(Professor professor) {

        Usuario usuarioCPF = this.usuarioDAO.findByCPFAndAtivo(professor.getUsuario().getCpf(), true);

        Usuario usuarioEmail = this.usuarioDAO.findByEmailAndAtivo(professor.getUsuario().getEmail(), true);       

        if (usuarioCPF != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse CPF.", "aluno.usuario.cpf"));
        } else {
            professor.getUsuario().setRole(Perfil.PROFESSOR);
            
            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];
            }

            professor.getUsuario().setPwd(senha);
            
            professor.getUsuario().setAtivo(true);
        }
        
        if (usuarioEmail != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse Email.", "aluno.usuario.email"));
        }

        this.validator.onErrorRedirectTo(this).create();

        this.usuarioDAO.create(professor.getUsuario());

        this.professorDAO.create(professor);

        result.include("success", "cadastrado");

        this.result.redirectTo(ProfessorController.class).index();
    }

    @Put("/professores")
    public void altera(Professor professor) {
        Professor professorEncontrado = this.professorDAO.findById(professor.getId());

        if (professorEncontrado == null) {
            validator.add(new ValidationMessage("Desculpe! Professor não encontrado.", "professor.id"));
        }

        this.validator.onErrorRedirectTo(ProfessorController.class).edit(professor.getId());

        Usuario usuarioCPF = this.usuarioDAO.findByCPFAndAtivo(professor.getUsuario().getCpf(), true);
        Usuario usuarioEmail = this.usuarioDAO.findByEmailAndAtivo(professor.getUsuario().getEmail(), true);

        if (usuarioCPF != null && professor.getUsuario().getId() != usuarioCPF.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já  existe um usuário cadastrado com o cpf informado.", "professor.usuario.cpf"));
        } else {
            professor.getUsuario().setAtivo(true);
            
            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];
            }

            professor.getUsuario().setPwd(senha);
            
            professor.getUsuario().setRole(Perfil.PROFESSOR);
        }

        if (usuarioEmail != null && professor.getUsuario().getId() != usuarioEmail.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já  existe um usuário cadastrado com o email informado.", "professor.usuario.email"));
        }

        this.validator.onErrorRedirectTo(ProfessorController.class).edit(professor.getId());

        this.usuarioDAO.update(professor.getUsuario());

        this.professorDAO.update(professor);

        this.result.include("success", "editado");

        this.result.redirectTo(ProfessorController.class).index();
    }
}