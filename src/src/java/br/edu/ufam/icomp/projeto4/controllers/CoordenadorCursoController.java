package br.edu.ufam.icomp.projeto4.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.edu.ufam.icomp.projeto4.Notificador;
import br.edu.ufam.icomp.projeto4.dao.CoordenadorCursoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.CoordenadorCurso;
import br.edu.ufam.icomp.projeto4.model.Curso;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.List;

/**
 *
 * @author Bruna
 */
@Resource
public class CoordenadorCursoController {

    private final Result result;
    private final Validator validator;
    private final CoordenadorCursoDAO coordenadorCursoDAO;
    private ProfessorDAO professorDAO;
    private CursoDAO cursoDAO;
    private UsuariosDao usuarioDAO;
    private Notificador notificador;

    public CoordenadorCursoController(Result result, Validator validator, CoordenadorCursoDAO coordenadorCursoDAO,
            ProfessorDAO professorDAO, CursoDAO cursoDAO, UsuariosDao usuarioDAO, Notificador notificador) {
        this.result = result;
        this.validator = validator;
        this.coordenadorCursoDAO = coordenadorCursoDAO;
        this.professorDAO = professorDAO;
        this.cursoDAO = cursoDAO;
        this.usuarioDAO = usuarioDAO;
        this.notificador = notificador;
    }

    @Get("/coordCurso")
    @Permission(Perfil.ROOT)
    public void index() {

        result.include("coordenadorCursoList", coordenadorCursoDAO.findByAtivo(Boolean.TRUE));
    }

    @Get("/coordCurso/create")
    @Permission(Perfil.ROOT)
    public CoordenadorCurso create() {
        result.include("operacao", "Cadastro");
        /* Adiciona somente os professores que não são coordenadores de curso
         */
        result.include("professorList", professorDAO.listProfessorNotIn(coordenadorCursoDAO.listProfessores(), Perfil.PROFESSOR, true));
        /* Adiciona somente os cursos que não possuem coordenador de curso
         */
        result.include("cursoList", cursoDAO.listCursoNotIn(coordenadorCursoDAO.listCursos()));
        return new CoordenadorCurso();
    }

    @Post("/coordCurso")
    @Permission(Perfil.ROOT)
    public void cadastrar(final CoordenadorCurso coordenadorCurso) {
        this.coordenadorCursoDAO.create(coordenadorCurso);

        Professor professor = professorDAO.findById(coordenadorCurso.getProfessor().getId());

        Usuario usuario = professor.getUsuario();

        usuario.setRole(Perfil.COORDENADOR);
        usuario.setPwd(geraSenha());
        this.usuarioDAO.update(usuario);

        Curso curso = this.cursoDAO.findById(coordenadorCurso.getCurso().getId());

        String conteudo =
                "<html>"
                + "    <head>"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>"
                + "    <body>"
                + "        <h2>Caro(a) " + professor.getUsuario().getNome() + ", </h2"
                + "        <p>Foi configurado seu perfil de Coordenador(a) do Curso de " + curso.getCurso() + " para acesso no Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"
                + "        <p>"
                + "         <b>Seus dados para acesso são:<b><br/>"
                + "         Seu CPF: " + professor.getUsuario().getCpf() + "<br/>"
                + "         Senha: " + professor.getUsuario().getPwd() + "<br/>"
                + "        </p>"
                + "    </body>"
                + "</html>";

        this.notificador.enviarEmail(professor.getUsuario().getEmail(), conteudo, "Sistema Atividades Extracurriculares] Dados para acesso no sistema.");

        result.include("success", "cadastrado");

        this.result.redirectTo(CoordenadorCursoController.class).index();
    }

    @Get("/coordCurso/{id}/edit")
    @Permission(Perfil.ROOT)
    public CoordenadorCurso edit(Long id) {
        CoordenadorCurso coordenadoCurso = coordenadorCursoDAO.findById(id);

        if (coordenadoCurso == null) {
            validator.add(new ValidationMessage("Desculpe! Coordenador de curso não encontrado", "coordenadoCurso.id"));
        }

        this.validator.validate(coordenadoCurso);

        validator.onErrorRedirectTo(this).index();

        result.include("operacao", "Edição");
        /* Adiciona somente os professores que não são coordenadores de curso + professor editado
         */
        List<Professor> professoresNaoCoordenadores = professorDAO.listProfessorNotIn(coordenadorCursoDAO.listProfessores(), Perfil.PROFESSOR, true);
        professoresNaoCoordenadores.add(coordenadoCurso.getProfessor());
        result.include("professorList", professoresNaoCoordenadores);
        /* Adiciona somente os cursos que não possuem coordenador de curso + curso editado
         */
        List<Curso> cursoNaoCoordenados = cursoDAO.listCursoNotIn(coordenadorCursoDAO.listCursos());
        cursoNaoCoordenados.add(coordenadoCurso.getCurso());
        result.include("cursoList", cursoNaoCoordenados);

        return coordenadoCurso;
    }

    @Put("/coordCurso")
    @Permission(Perfil.ROOT)
    public void altera(CoordenadorCurso coordenadorCurso) {
        CoordenadorCurso coordCursoAntigo = coordenadorCursoDAO.findById(coordenadorCurso.getId());

        /*
         * Caso o professor tenha sido trocado,
         * o usuario que anteriormente era CC passa a ser somente Professor
         * o usuario que agora é CC passa a ser CC
         */
        if (!coordCursoAntigo.getProfessor().equals(coordenadorCurso.getProfessor())) {
            Professor professorAntigo = professorDAO.findById(coordCursoAntigo.getProfessor().getId());
            Professor professorNovo = professorDAO.findById(coordenadorCurso.getProfessor().getId());

            Usuario usuarioAntigo = professorAntigo.getUsuario();
            Usuario usuarioNovo = professorNovo.getUsuario();

            usuarioAntigo.setRole(Perfil.PROFESSOR);
            usuarioNovo.setRole(Perfil.COORDENADOR);
            usuarioNovo.setPwd(geraSenha());

            this.usuarioDAO.update(usuarioAntigo);
            this.usuarioDAO.update(usuarioNovo);
            
            Curso curso = this.cursoDAO.findById(coordenadorCurso.getCurso().getId());

            String conteudo =
                    "<html>"
                    + "    <head>"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "    </head>"
                    + "    <body>"
                    + "        <h2>Caro(a) " + professorNovo.getUsuario().getNome() + ", </h2"
                    + "        <p>Foi configurado seu perfil de Coordenador(a) do Curso de " + curso.getCurso() + " para acesso no Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"
                    + "        <p>"
                    + "         <b>Seus dados para acesso são:<b><br/>"
                    + "         Seu CPF: " + professorNovo.getUsuario().getCpf() + "<br/>"
                    + "         Senha: " + professorNovo.getUsuario().getPwd() + "<br/>"
                    + "        </p>"
                    + "    </body>"
                    + "</html>";

            this.notificador.enviarEmail(professorNovo.getUsuario().getEmail(), conteudo, "Sistema Atividades Extracurriculares] Dados para acesso no sistema.");
        }

        this.coordenadorCursoDAO.update(coordenadorCurso);

        result.include("success", "alterado");

        this.result.redirectTo(this).index();
    }

    @Get("/coordCurso/{id}/show")
    @Permission(Perfil.ROOT)
    public CoordenadorCurso show(Long id) {
        CoordenadorCurso coordenadoCurso = coordenadorCursoDAO.findById(id);

        if (coordenadoCurso == null) {
            validator.add(new ValidationMessage("Desculpe! Coordenador de curso não encontrado", "coordenadoCurso.id"));
        }

        this.validator.validate(coordenadoCurso);

        validator.onErrorRedirectTo(this).index();

        return coordenadoCurso;
    }

    @Get("/coordCurso/{id}/remove")
    @Permission(Perfil.ROOT)
    public void remove(Long id) {
        CoordenadorCurso coordenadorCurso = coordenadorCursoDAO.findById(id);

        if (coordenadorCurso == null) {
            validator.add(new ValidationMessage("Desculpe! Coordenador de curso não encontrado", "coordenadoCurso.id"));
        }

        this.validator.validate(coordenadorCurso);

        validator.onErrorRedirectTo(this).index();

        Professor professor = professorDAO.findById(coordenadorCurso.getProfessor().getId());

        Usuario usuario = professor.getUsuario();

        usuario.setRole(Perfil.PROFESSOR);

        this.usuarioDAO.update(usuario);

        coordenadorCursoDAO.delete(coordenadorCurso);

        result.include("success", "removido");

        this.result.redirectTo(this).index();
    }

    public String geraSenha() {
        String[] carct = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        String senha = "";

        for (int x = 0; x < 10; x++) {
            int j = (int) (Math.random() * carct.length);
            senha += carct[j];

        }
        return senha;
    }
}