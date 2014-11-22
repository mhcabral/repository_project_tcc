package br.edu.ufam.icomp.projeto4.model;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Bruna
 */
public class FrequenciaJson {

    int id;
    String title;
    String start;
    String end;
    boolean allDay;

    public FrequenciaJson() {
    }

    public FrequenciaJson(Frequencia frequencia) {
        Date date = frequencia.getData();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.id = frequencia.getId();
        this.title = frequencia.getCargaHoraria().toString();
        this.start = "y-M-d h:m";
        this.start = this.fillParams(this.start, cal);
        this.end = this.start;
        this.allDay = true;
    }
    
    public FrequenciaJson(FrequenciaEstagio frequencia) {
        Date date = frequencia.getData();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        this.id = frequencia.getId();
        this.title = frequencia.getCargaHoraria().toString() + "h: " + frequencia.getDescricao();
        this.start = "y-M-d h:m";
        this.start = this.fillParams(this.start, cal);
        this.end = this.start;
        this.allDay = true;
    }

    private String fillParams(String parString, Calendar cal) {
        String newString = parString;

        newString = newString.replace("y", String.valueOf(cal.get(Calendar.YEAR)));
        newString = newString.replace("M", String.valueOf(cal.get(Calendar.MONTH)+1));
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
        return "FrequenciaJson{" + "id=" + id + ", title=" + title + ", start=" + start + ", end=" + end + ", allDay=" + allDay + '}';
    }
}