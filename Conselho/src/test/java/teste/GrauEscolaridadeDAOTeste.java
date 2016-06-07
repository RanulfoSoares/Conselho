package teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.dao.GrauEscolaridadeDAO;
import br.com.conselho.domain.GrauEscolaridade;

public class GrauEscolaridadeDAOTeste {
	
	@Test
	@Ignore
	public void testeBusca(){
		
		try {
			List<GrauEscolaridade> lista = new GrauEscolaridadeDAO().listaGrauEscolaridade();
			
			for (GrauEscolaridade grauEscolaridade : lista) {
				System.out.println(grauEscolaridade.getNome());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
