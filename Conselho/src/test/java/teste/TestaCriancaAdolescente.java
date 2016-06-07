package teste;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.dao.CriancaAdolescenteDAO;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Responsavel;

public class TestaCriancaAdolescente {
	
	@Test
	@Ignore
	public void testaBusca(){
		
		CriancaAdolescenteDAO adolescenteDAO = new CriancaAdolescenteDAO();
		try {
			List<CriancaAdolescente> lista = adolescenteDAO.buscaCrianca("Fabrício");
			
			for (CriancaAdolescente criancaAdolescente : lista) {
				System.out.println("Nome: "+criancaAdolescente.getPessoa().getNomeCompleto());
				System.out.println("Idade: "+criancaAdolescente.getPessoa().getIdade());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	
	@Test
	public void testaBuscaCodigo(){
		
		CriancaAdolescenteDAO adolescenteDAO = new CriancaAdolescenteDAO();
		try {
			CriancaAdolescente crianca = adolescenteDAO.buscaCrianca(45L);
			
			for (Parente parente : crianca.getListaParente()) {
				System.out.println("Parente: "+parente.getParente().getNomeCompleto());
			}
			
			for (Responsavel parente : crianca.getListaResponsavel()) {
				System.out.println("Responsavel: "+parente.getPessoa().getNomeCompleto());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

}
