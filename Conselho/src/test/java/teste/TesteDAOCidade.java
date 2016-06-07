package teste;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;

public class TesteDAOCidade {
	
	@Test
	@Ignore
	public void salvarCidade(){
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		Estado estado = new Estado();
		estado.setId(1L);
		estado.setNome("sao Paulo Editado");
		
		Cidade cidade = new Cidade();		
		cidade.setNome("Pedreira");
		cidade.setEstado(estado);
		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(cidade);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..."+e.getMessage());
		}finally{
			sessao.close();
		}
		
	}

}
