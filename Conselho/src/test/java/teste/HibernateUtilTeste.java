package teste;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.util.HibernateUtil;

public class HibernateUtilTeste {
	
	@Ignore
	public void testeConexao() {
		try {
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			System.out.println("Sucesso!!");
		} catch (Exception e) {
			System.out.println("Erro!!!..." + e.getMessage());
		}
	}
	
	@Test
	@Ignore
	public void testeConexao2() {
		try {
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			System.out.println("Sucesso!!");
		} catch (Exception e) {
			System.out.println("Erro!!!..." + e.getMessage());
		}
	}

}
