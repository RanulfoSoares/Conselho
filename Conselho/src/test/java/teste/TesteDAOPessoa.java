package teste;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.dao.PessoaDAO;
import br.com.conselho.domain.Orgao;
import br.com.conselho.domain.Pessoa;

public class TesteDAOPessoa {
	
	@Ignore
	public void buscaCPF(){
		
		try {
			List<Pessoa> listaPessoa = new PessoaDAO().buscaCPF("36995369807");
			
			for (Pessoa pessoa : listaPessoa) {
				System.out.println("nome: "+pessoa.getNomeCompleto());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	@Ignore
	@Test
	public void buscaCertidao(){
		
		try {
			List<Pessoa> listaPessoa = new PessoaDAO().buscaCertidao("1234567");
			
			for (Pessoa pessoa : listaPessoa) {
				System.out.println("nome: "+pessoa.getNomeCompleto());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	@Ignore
	@Test
	public void buscaRG(){
		
		Orgao orgao = new  Orgao();
		orgao.setId(1L);
		
		
		try {
			List<Pessoa> listaPessoa = new PessoaDAO().buscaRG("333333333", orgao);
			
			for (Pessoa pessoa : listaPessoa) {
				System.out.println("nome: "+pessoa.getNomeCompleto());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	
	@Test
	public void testaBuscaMenor(){
		
		try {
			//List<Pessoa> lista = new PessoaDAO().buscaMaior("CPF", "36995369807", null);
			List<Pessoa> lista = new ArrayList<Pessoa>();
			if(!lista.isEmpty()){
				for (Pessoa pessoa : lista) {
					System.out.println("Pessoa selecionada: "+pessoa.getNomeCompleto());
				}
			}else{
				System.out.println("Novo Cadastro!!...");
			}
			
			
		} catch (Exception e) {
			System.out.println("ERROOOO....: "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	

}
