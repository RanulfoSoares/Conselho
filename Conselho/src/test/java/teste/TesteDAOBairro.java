package teste;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.util.HibernateUtil;

public class TesteDAOBairro {
	
	@Test
	@Ignore
	public void salvarCidade() {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		Cidade cidade = new Cidade();
		cidade.setId(1L);

		Bairro bairro = new Bairro();
		bairro.setNome("JD Alzira");
		bairro.setCidade(cidade);

		try {
			transaction = sessao.beginTransaction();
			sessao.save(bairro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO..." + e.getMessage());
		} finally {
			sessao.close();
		}

	}

}
