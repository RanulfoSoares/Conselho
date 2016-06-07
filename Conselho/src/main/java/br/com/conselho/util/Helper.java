package br.com.conselho.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import br.com.conselho.dao.FamiliaDAO;
import br.com.conselho.domain.Familia;

public class Helper {
	

	public static Integer executaCalculoIdade(Date data) {  
	    Calendar dataNascimento = Calendar.getInstance();  
	    dataNascimento.setTime(data);  
	    Calendar dataAtual = Calendar.getInstance();  
	  
	    Integer diferencaMes = dataAtual.get(Calendar.MONTH) - dataNascimento.get(Calendar.MONTH);  
	    Integer diferencaDia = dataAtual.get(Calendar.DAY_OF_MONTH) - dataNascimento.get(Calendar.DAY_OF_MONTH);  
	    Integer idade = (dataAtual.get(Calendar.YEAR) - dataNascimento.get(Calendar.YEAR));  
	  
	    if(diferencaMes < 0  || (diferencaMes == 0 && diferencaDia < 0)) {  
	        idade--;  
	    }  
	      
	    return idade;  
	}
	
	public static Long proximoNumeroPasta() throws Exception{
		
		 try {
				Familia familia = new FamiliaDAO().buscaUltimoRegistro();
				if(familia != null){
					Long numeroPasta = Long.parseLong(familia.getNumeroPasta());
					return numeroPasta+1;
				}else{
					return 1L;
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				throw new Exception();
			}  
	}
	
	public static SimpleDateFormat formatDate(){
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		return formataData;
	}
	
	public static String formataNumeroPasta(String numeroPasta){
		return StringUtils.leftPad(numeroPasta, 5, "0");
	}
	

}
