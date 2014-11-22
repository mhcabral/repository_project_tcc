package br.edu.ufam.icomp.projeto4;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.edu.ufam.icomp.projeto4.model.PeriodoCadMon;
import br.edu.ufam.icomp.projeto4.model.PeriodoInsMon;
import br.edu.ufam.icomp.projeto4.model.PeriodoLetivo;
import br.edu.ufam.icomp.projeto4.model.Usuario;
import java.io.Serializable;

/**
 *
 * @author Janderson
 */
@Component
@SessionScoped
public class SessionData implements Serializable{

    private Usuario logado;
    private PeriodoLetivo letivoAtual;
    private PeriodoLetivo periodoEstagio;
    private PeriodoLetivo periodoMatricula;
    private PeriodoCadMon periodoCadMon;
    private PeriodoInsMon periodoInsMon;

    public void setUsuario(Usuario usuario) {
        this.logado = usuario;
    }
    
    public Usuario getUsuario() {
        return logado;
    }

    public String getNome() {
        return logado.getNome();
    }

    public boolean getLogado() {
        return logado != null;
    }

    public void logout() {
        this.logado = null;
    }

    public PeriodoLetivo getLetivoAtual() {
        return letivoAtual;
    }

    public void setLetivoAtual(PeriodoLetivo letivoAtual) {
        this.letivoAtual = letivoAtual;
    }

    public PeriodoLetivo getPeriodoEstagio() {
        return periodoEstagio;
    }

    public void setPeriodoEstagio(PeriodoLetivo periodoEstagio) {
        this.periodoEstagio = periodoEstagio;
    }

    public PeriodoLetivo getPeriodoMatricula() {
        return periodoMatricula;
    }

    public void setPeriodoMatricula(PeriodoLetivo periodoMatricula) {
        this.periodoMatricula = periodoMatricula;
    }

    public PeriodoCadMon getPeriodoCadMon() {
        return periodoCadMon;
    }

    public void setPeriodoCadMon(PeriodoCadMon periodoCadMon) {
        this.periodoCadMon = periodoCadMon;
    }

    public PeriodoInsMon getPeriodoInsMon() {
        return periodoInsMon;
    }

    public void setPeriodoInsMon(PeriodoInsMon periodoInsMon) {
        this.periodoInsMon = periodoInsMon;
    }
}
