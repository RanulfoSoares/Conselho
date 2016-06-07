package teste;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TesteGenericos {
	
	@Test
	public void testeMaior(){		
		
	 SimpleDateFormat formato = new SimpleDateFormat("dd/MM/");
		
		Calendar dataAtual = Calendar.getInstance();
		Integer ano = (dataAtual.get(Calendar.YEAR) - 18);						
		System.out.println("data Menor de 18: "+formato.format(new Date())+ano );
		
		
	}
	

}
