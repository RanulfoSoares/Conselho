package teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Testes {
	
		public static void main(String[] args) throws ParseException {	
			Date inicio = new SimpleDateFormat("dd/MM/yyyy").parse("15/01/2016");
			Date fim = new SimpleDateFormat("dd/MM/yyyy").parse("23/01/2016");
			Date data = new SimpleDateFormat("dd/MM/yyyy").parse("23/01/2016");

			if (data.equals(inicio) || data.equals(fim) || (data.after(inicio) && data.before(fim))) {
				System.out.println(true);
			} else {
				System.out.println(false);
			}

		}	

}
