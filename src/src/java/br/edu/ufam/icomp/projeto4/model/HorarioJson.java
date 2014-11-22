package br.edu.ufam.icomp.projeto4.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Bruna
 */
public class HorarioJson {

    int id;
    String title;
    String start;
    String end;
    boolean allDay;

    public HorarioJson() {
    }

    public HorarioJson(HorarioTurma horario) {
        Date atual = new Date();
        Date date = ajustaData(atual, horario.getInicio());
        Date date2 = ajustaData(atual, horario.getFim());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        this.id = horario.getId();
        this.title = "";
        this.start = "y-M-d h:m";
        this.start = this.fillParams(this.start, cal);
        this.end = "y-M-d h:m";
        this.end = this.fillParams(this.end, cal2);
        this.allDay = false;
    }

    private String fillParams(String parString, Calendar cal) {
        String newString = parString;

        newString = newString.replace("y", String.valueOf(cal.get(Calendar.YEAR)));
        newString = newString.replace("M", String.valueOf(cal.get(Calendar.MONTH) + 1));
        newString = newString.replace("d", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        newString = newString.replace("h", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        newString = newString.replace("m", String.valueOf(cal.get(Calendar.MINUTE)));

        return newString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public String toString() {
        return "HorarioJson{" + "id=" + id + ", title=" + title + ", start=" + start + ", end=" + end + ", allDay=" + allDay + '}';
    }

    public Date ajustaData(Date atual, Date nova) {
        int dia_semana_data = atual.getDay();
        int dia_inicio = nova.getDay();

        int dif_inicio = dia_inicio - dia_semana_data;
        
        GregorianCalendar gc = new GregorianCalendar();
        
        gc.setTime(atual);
        
        gc.add(GregorianCalendar.DAY_OF_MONTH , dif_inicio);
        
        System.out.println(dia_inicio+" - " + dia_semana_data +" = " + dif_inicio + " -> " + gc.getTime());
        
        Date alterada = gc.getTime();
        
        alterada.setHours(nova.getHours());
        alterada.setMinutes(nova.getMinutes());

        return alterada;
    }
}
