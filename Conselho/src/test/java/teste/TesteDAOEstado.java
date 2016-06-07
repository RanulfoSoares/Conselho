package teste;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Ignore;
import org.junit.Test;

import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;



public class TesteDAOEstado {
	
	@Test
	@Ignore
	public void salvarEstado(){
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		Estado estado = new Estado();
		
		estado.setNome("Mato Grosso do Sul");
		
		try {
			transaction = sessao.beginTransaction();
			sessao.save(estado);
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
	
	@Test
	@Ignore
	public void alterar(){
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		
		Estado estado = new Estado();
		estado.setId(1L);
		estado.setNome("Minas Gerais");
		
		try {
			transaction = sessao.beginTransaction();
			sessao.update(estado);
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			sessao.close();
		}			
	}
	
	@Test
	@Ignore
	public void buscar(){
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Estado> listaEstado = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Estado.class);
			criteria.add(Restrictions.ilike("nome", "M%"));
			listaEstado = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
		}finally{
			sessao.close();
		}
		
		if(listaEstado != null){
			for (Estado estado : listaEstado) {
				System.out.println(estado.getNome());
			}
		}
	}
	
	@Test
	@Ignore
	public void excluir(){
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Estado estado = new Estado();
		estado.setId(8L);
		estado.setNome("Minas Gerais");
		
		try {
			transaction = sessao.beginTransaction();
			sessao.delete(estado);
			transaction.commit();
			System.out.println("Suucessoo.......");
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..." +e.getMessage());
		}finally{
			sessao.close();
		}
		
		
	}
	

}
