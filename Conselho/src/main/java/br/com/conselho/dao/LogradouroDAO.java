package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Logradouro;
import br.com.conselho.util.HibernateUtil;

public class LogradouroDAO {
	
	public void salvar(Logradouro logradouro) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		
		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(logradouro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}			
	}
	
	public List<Logradouro> buscaLogradouro (Bairro bairro, String nome)  throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Logradouro> lista;
		
		try{
			Criteria criteria = sessao.createCriteria(Logradouro.class);			
			if(!"".equals(nome)){
				criteria.add(Restrictions.like("nome", "%"+nome+"%").ignoreCase());
			}
			criteria.createAlias("bairro", "b");
			criteria.add(Restrictions.eq("b.id", bairro.getId()));
			criteria.addOrder(Order.asc("nome"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public void excluir(Logradouro logradouro) throws Exception{
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(logradouro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO AO EXCLUIR... Este logradouro esta sendo utilizado em algum cadastro");
			throw new Exception("Este logradouro esta sendo utilizado em algum cadastro");		
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
	}
	
	public List<Logradouro> buscaLogradouro (Bairro bairro)  throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Logradouro> lista;
		
		try{
			Criteria criteria = sessao.createCriteria(Logradouro.class);						
			criteria.createAlias("bairro", "b");
			criteria.add(Restrictions.eq("b.id", bairro.getId()));
			criteria.addOrder(Order.asc("nome"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}

}
