/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufam.icomp.projeto4;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Thiago Santos
 */
public class DataUtil {
    
    public static final String PATTERN_PORTUGUESE = "ddd/MM/yyyy";
    
    public static String dateToString(Date date){
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_PORTUGUESE);
        
        String dateFormat = simpleDateFormat.format(date);
        
        return dateFormat;
    }
}
