package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.SessionData;
import br.edu.ufam.icomp.projeto4.dao.AlunoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.PeriodoLetivoDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.Aluno;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Permission(Perfil.ROOT)
@Resource
public class AlunoController {

    private final Validator validator;
    private final Result result;
    private final AlunoDAO alunoDAO;
    private UsuariosDao usuarioDAO;
    private CursoDAO cursoDAO;
    private Notificador notificador;
    private PeriodoLetivoDAO periodoLetivoDAO;
    private SessionData sessionData;

    public AlunoController(Validator validator, Result result, AlunoDAO alunoDAO, PeriodoLetivoDAO periodoLetivoDAO,
            UsuariosDao usuarioDAO, CursoDAO cursoDAO, Notificador notificador, SessionData sessionData) {
        this.validator = validator;
        this.result = result;
        this.alunoDAO = alunoDAO;
        this.usuarioDAO = usuarioDAO;
        this.cursoDAO = cursoDAO;
        this.notificador = notificador;
        this.periodoLetivoDAO = periodoLetivoDAO;
        this.sessionData = sessionData;
    }

    @Get("/alunos")
    public void index() {
        List<Aluno> alunos = alunoDAO.findByPerfilAndAtivo(Perfil.ALUNO, true);

        result.include("alunoList", alunos);
    }

    @Get("/alunos/create")
    public void create() {
        result.include("cursoList", cursoDAO.findAll());
        result.include("periodoList", periodoLetivoDAO.listLetivoIniciado());
        result.include("operacao", "Cadastro");
    }

    @Post("/alunos")
    public void cadastrar(final Aluno aluno, Long periodo) {

        Aluno alunoMatricula = this.alunoDAO.findByMatricula(aluno.getMatricula());
        Usuario usuarioCPF = this.usuarioDAO.findByCPFAndAtivo(aluno.getUsuario().getCpf(), true);
        Usuario usuarioEmail = this.usuarioDAO.findByEmailAndAtivo(aluno.getUsuario().getEmail(), true);

        System.out.println("------------------------");
        System.out.println(periodo);
        if (periodo != null) {

            PeriodoLetivo periodoLetivo = periodoLetivoDAO.findById(periodo);

            aluno.setDataIngresso(periodoLetivo.getDtInicio());
            System.out.println("+++++++++++++++++++++");
            System.out.println(aluno.getDataIngresso());
        }

        if (alunoMatricula != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe uma aluno cadastrado com essa matrícula.", "aluno.matricula"));
        }

        if (aluno.getCurso().getId() == null) {
            this.validator.add(new ValidationMessage("Desculpe! Um curso deve ser selecionado.", "aluno.curso.id"));
        }

        if (usuarioCPF != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse CPF.", "aluno.usuario.cpf"));
        } else {
            aluno.getUsuario().setAtivo(true);
            aluno.getUsuario().setRole(Perfil.ALUNO);

            String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
            String senha = "";

            for (int x = 0; x < 10; x++) {
                int j = (int) (Math.random() * carct.length);
                senha += carct[j];
            }

            aluno.getUsuario().setPwd(senha);
        }

        if (usuarioEmail != null) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse email.", "aluno.usuario.email"));
        }

        this.validator.validate(aluno);

        this.validator.onErrorRedirectTo(this).create();

        this.usuarioDAO.create(aluno.getUsuario());
        this.alunoDAO.create(aluno);

        Curso curso = this.cursoDAO.findById(aluno.getCurso().getId());

        String conteudo =
                "<html>"
                + "    <head>"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>"
                + "    <body>"
                + "        <h2>Caro(a) " + aluno.getUsuario().getNome() + ", </h2"
                + "        <p>Foi configurado seu perfil de Aluno do curso de " + curso.getCurso() + " para acesso no Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"
                + "        <p>"
                + "         <b>Seus dados para acesso são:<b><br/>"
                + "         Seu CPF: " + aluno.getUsuario().getCpf() + "<br/>"
                + "         Senha: " + aluno.getUsuario().getPwd() + "<br/>"
                + "        </p>"
                + "    </body>"
                + "</html>";

        this.notificador.enviarEmail(aluno.getUsuario().getEmail(), conteudo, "[Sistema Atividades Extracurriculares] Dados para acesso no sistema.");

        result.include("success", "cadastrado");

        this.result.redirectTo(this).index();
    }

    @Get("/alunos/{id}/edit")
    public Aluno edit(Long id) {
        Aluno aluno = this.alunoDAO.findById(id);

        if (aluno == null) {
            validator.add(new ValidationMessage("Desculpe! Aluno não encontrado", "aluno.id"));
        }

        validator.onErrorRedirectTo(this).index();

        PeriodoLetivo periodoLetivo = periodoLetivoDAO.findByData(aluno.getDataIngresso());

        result.include("operacao", "Edição");
        result.include("usuario", aluno.getUsuario());
        result.include("periodoList", periodoLetivoDAO.listLetivoIniciado());
        result.include("periodoLetivo", periodoLetivo);
        result.include("cursoList", cursoDAO.findAll());

        return aluno;
    }

    @Get("/alunos/{id}/show")
    public Aluno show(Long id) {
        Aluno aluno = this.alunoDAO.findById(id);

        if (aluno == null) {
            validator.add(new ValidationMessage("Desculpe! Aluno não encontrado", "aluno.id"));
        }

        this.validator.validate(aluno);

        validator.onErrorRedirectTo(this).index();

        return aluno;
    }

    @Get("/alunos/{id}/remove")
    public void remove(Long id) {
        Aluno aluno = this.alunoDAO.findById(id);

        if (aluno == null) {
            validator.add(new ValidationMessage("Desculpe! Aluno não encontrado", "aluno.id"));
        }

        validator.onErrorRedirectTo(this).index();

        Usuario usuario = aluno.getUsuario();

        usuario.setAtivo(false);

        this.usuarioDAO.update(usuario);

        this.result.include("success", "removido");

        this.result.redirectTo(this).index();
    }

    @Put("/alunos")
    public void altera(Aluno aluno, Long periodo) {

        Aluno alunoEncontrado = this.alunoDAO.findById(aluno.getId());

        if (alunoEncontrado == null) {
            this.validator.add(new ValidationMessage("Desculpe! Aluno não encontrado.'", "aluno.id"));
        }

        validator.onErrorRedirectTo(this).edit(aluno.getId());

        if (periodo != null) {
            PeriodoLetivo periodoLetivo = periodoLetivoDAO.findById(periodo);

            aluno.setDataIngresso(periodoLetivo.getDtInicio());
        }

        Aluno alunoMatricula = this.alunoDAO.findByMatricula(aluno.getMatricula());
        Usuario usuarioCPF = this.usuarioDAO.findByCPFAndAtivo(aluno.getUsuario().getCpf(), true);
        Usuario usuarioEmail = this.usuarioDAO.findByEmailAndAtivo(aluno.getUsuario().getEmail(), true);

        if (alunoMatricula != null && aluno.getId() != alunoMatricula.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe uma aluno cadastrado com essa matrícula.", "aluno.matricula"));
        }

        if (aluno.getCurso().getId() == null) {
            this.validator.add(new ValidationMessage("Desculpe! Um curso deve ser selecionado.", "aluno.curso.id"));
        }

        if (usuarioEmail != null && aluno.getUsuario().getId() != usuarioEmail.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com esse email.", "aluno.usuario.email"));
        }

        if (usuarioCPF != null && aluno.getUsuario().getId() != usuarioCPF.getId()) {
            this.validator.add(new ValidationMessage("Desculpe! Já existe um usuário cadastrado com o CPF.", "aluno.usuario.cpf"));
        } else {
            aluno.getUsuario().setAtivo(true);
            aluno.getUsuario().setRole(Perfil.ALUNO);

            aluno.getUsuario().setPwd(alunoEncontrado.getUsuario().getPwd());
        }

        this.validator.validate(aluno);

        validator.onErrorRedirectTo(this).edit(aluno.getId());

        usuarioDAO.update(aluno.getUsuario());

        this.alunoDAO.update(aluno);

        this.result.include("success", "editado");

        this.result.redirectTo(AlunoController.class).index();
    }
}