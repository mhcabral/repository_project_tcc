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
import br.edu.ufam.icomp.projeto4.dao.CoordenadorAcademicoDAO;
import br.edu.ufam.icomp.projeto4.dao.CursoDAO;
import br.edu.ufam.icomp.projeto4.dao.ProfessorDAO;
import br.edu.ufam.icomp.projeto4.dao.UsuariosDao;
import br.edu.ufam.icomp.projeto4.interceptor.Perfil;
import br.edu.ufam.icomp.projeto4.interceptor.Permission;
import br.edu.ufam.icomp.projeto4.model.CoordenadorAcademico;
import br.edu.ufam.icomp.projeto4.model.Professor;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thiago Santos
 */
@Permission(Perfil.ROOT)
@Resource
public class CoordenadorAcademicoController {

    private final Result result;
    private final Validator validator;
    private final CoordenadorAcademicoDAO coordenadorAcademicoDAO;
    private ProfessorDAO professorDAO;
    private CursoDAO cursoDAO;
    private UsuariosDao usuariosDao;
    private Notificador notificador;

    public CoordenadorAcademicoController(Result result, Validator validator, CoordenadorAcademicoDAO coordenadorAcademicoDAO, ProfessorDAO professorDAO, CursoDAO cursoDAO, UsuariosDao usuariosDao, Notificador notificador) {
        this.result = result;
        this.validator = validator;
        this.coordenadorAcademicoDAO = coordenadorAcademicoDAO;
        this.professorDAO = professorDAO;
        this.cursoDAO = cursoDAO;
        this.usuariosDao = usuariosDao;
        this.notificador = notificador;
    }

    @Get("/coordAcademico")
    public void index() {
        result.include("coordenadorAcademicoList", coordenadorAcademicoDAO.findAll());
    }

    @Get("/coordAcademico/create")
    public void create() {
        result.include("operacao", "Cadastro");

        List<Professor> listProfessores = coordenadorAcademicoDAO.listProfessores();

        if (listProfessores.isEmpty()) {
            List<Perfil> perfisEncontrar = new ArrayList<Perfil>();
            perfisEncontrar.add(Perfil.PROFESSOR);

            result.include("professorList", professorDAO.findByPerfisAndAtivo(perfisEncontrar, true));
        } else {
            result.include("professorList", professorDAO.listProfessorNotIn(listProfessores, Perfil.PROFESSOR, true));
        }

        result.include("cursoList", cursoDAO.findAll());
    }

    @Post("/coordAcademico")
    public void cadastrar(final CoordenadorAcademico coordenadorAcademico) {

        this.coordenadorAcademicoDAO.create(coordenadorAcademico);

        Professor professor = this.professorDAO.findById(coordenadorAcademico.getProfessor().getId());

        Usuario usuario = professor.getUsuario();

        usuario.setAtivo(true);

        usuario.setRole(Perfil.COORDENADORACAD);

        usuariosDao.update(usuario);

        String conteudo =
                "<html>"
                + "    <head>"
                + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                + "    </head>"
                + "    <body>"
                + "        <h2>Caro(a) " + professor.getUsuario().getNome() + ", </h2"
                + "        <p>"
                + "           Foi configurado seu perfil de Coordenador Acadêmico para acesso no "
                + "           Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"
                + "        <p>"
                + "         <b>Seus dados para acesso são:<b><br/>"
                + "         Seu CPF: " + professor.getUsuario().getCpf() + "<br/>"
                + "         Senha: " + professor.getUsuario().getPwd() + "<br/>"
                + "        </p>"
                + "    </body>"
                + "</html>";

        this.notificador.enviarEmail(professor.getUsuario().getEmail(), conteudo, "Sistema Atividades Extracurriculares] Dados para acesso no sistema.");

        result.include("success", "cadastrado");

        this.result.redirectTo(CoordenadorAcademicoController.class).index();
    }

    @Get("/coordAcademico/{id}/edit")
    public CoordenadorAcademico edit(Long id) {
        CoordenadorAcademico coordenadorAcademico = coordenadorAcademicoDAO.findById(id);

        if (coordenadorAcademico == null) {
            this.validator.add(new ValidationMessage("Desculpe! Coordenador Acadêmico não encontrado", "coordenadorAcademico.id"));
        }

        this.validator.onErrorRedirectTo(this).index();

        result.include("operacao", "Edição");

        List<Professor> professoresNaoCoordenadores = professorDAO.listProfessorNotIn(coordenadorAcademicoDAO.listProfessores(), Perfil.PROFESSOR, true);
        professoresNaoCoordenadores.add(coordenadorAcademico.getProfessor());

        result.include("professorList", professoresNaoCoordenadores);

        result.include("cursoList", cursoDAO.findAll());

        return coordenadorAcademico;
    }

    @Put("/coordAcademico")
    public void altera(CoordenadorAcademico coordenadorAcademico) {

        CoordenadorAcademico coordAcademicoAntigo = coordenadorAcademicoDAO.findById(coordenadorAcademico.getId());

        if (!coordAcademicoAntigo.getProfessor().equals(coordenadorAcademico.getProfessor())) {
            Professor professorAntigo = professorDAO.findById(coordAcademicoAntigo.getProfessor().getId());
            Professor professorNovo = professorDAO.findById(coordenadorAcademico.getProfessor().getId());

            Usuario usuarioAntigo = professorAntigo.getUsuario();
            Usuario usuarioNovo = professorNovo.getUsuario();

            usuarioAntigo.setRole(Perfil.PROFESSOR);
            usuarioNovo.setRole(Perfil.COORDENADOR);

            usuarioAntigo.setAtivo(true);
            usuarioNovo.setAtivo(true);

            this.usuariosDao.update(usuarioAntigo);
            this.usuariosDao.update(usuarioNovo);

            String conteudo =
                    "<html>"
                    + "    <head>"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "    </head>"
                    + "    <body>"
                    + "        <h2>Caro(a) " + professorNovo.getUsuario().getNome() + ", </h2"
                    + "        <p>"
                    + "           Foi configurado seu perfil de Coordenador Acadêmico para acesso no "
                    + "           Sistema de Gerenciamento de Atividades Extracurriculares (SGAE) pertencente à Universidade Federal do Amazonas.</b>"
                    + "        <p>"
                    + "         <b>Seus dados para acesso são:<b><br/>"
                    + "         Seu CPF: " + professorNovo.getUsuario().getCpf() + "<br/>"
                    + "         Senha: " + professorNovo.getUsuario().getPwd() + "<br/>"
                    + "        </p>"
                    + "    </body>"
                    + "</html>";

            this.notificador.enviarEmail(professorNovo.getUsuario().getEmail(), conteudo, "Sistema Atividades Extracurriculares] Dados para acesso no sistema.");
        }

        this.coordenadorAcademicoDAO.update(coordenadorAcademico);

        result.include("success", "alterado");

        this.result.redirectTo(this).index();
    }

    @Get("/coordAcademico/{id}/show")
    public CoordenadorAcademico show(Long id) {
        CoordenadorAcademico coordenadorAcademico = coordenadorAcademicoDAO.findById(id);

        if (coordenadorAcademico == null) {
            validator.add(new ValidationMessage("Desculpe! Coordenador Acadêmico não encontrado", "coordenadorAcademico.id"));
        }

        validator.onErrorRedirectTo(this).index();

        return coordenadorAcademicoDAO.findById(id);
    }

    @Get("/coordAcademico/{id}/remove")
    public void remove(Long id) {
        CoordenadorAcademico coordenadorAcademico = coordenadorAcademicoDAO.findById(id);


        if (coordenadorAcademico == null) {
            validator.add(new ValidationMessage("Desculpe! Coordenador Acadêmico não encontrado", "coordenadorAcademico.id"));
        }

        validator.onErrorRedirectTo(this).index();

        Professor professor = professorDAO.findById(coordenadorAcademico.getProfessor().getId());

        Usuario usuario = professor.getUsuario();

        usuario.setRole(Perfil.PROFESSOR);

        usuario.setAtivo(true);

        this.usuariosDao.update(usuario);

        coordenadorAcademicoDAO.delete(coordenadorAcademico);

        result.include("success", "removido");

        this.result.redirectTo(this).index();
    }
}