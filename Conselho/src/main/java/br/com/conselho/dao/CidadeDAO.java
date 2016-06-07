package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;

public class CidadeDAO {
	
	public void salvar(Cidade cidade) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		
		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(cidade);
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
	
	public void excluir(Cidade cidade) throws Exception{
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(cidade);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO AO EXCLUIR... Resgistro de cidade esta sendo utilizado em bairro");
			throw new Exception("Registro de cidade esta sendo utilizado em bairro");		
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
	
	public List<Cidade> buscaCidade(String nome) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Cidade> listaCidade = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Cidade.class);
			
			if(!"".equals(nome)){
				criteria.add(Restrictions.like("nome", "%"+nome+"%").ignoreCase());
			}
			criteria.addOrder(Order.asc("nome"));
			listaCidade =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaCidade;		
	}
	
	public List<Cidade> buscaCidade(String nome, Estado estado) throws Exception {
				
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Cidade> listaCidade = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Cidade.class);						
			criteria.add(Restrictions.eq("nome", nome).ignoreCase());
			criteria.add(Restrictions.eq("estado", estado));
			criteria.addOrder(Order.asc("nome"));
			listaCidade =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaCidade;
		
	}
	
	public List<Cidade> buscaCidade(Estado estado) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Cidade> listaCidade = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Cidade.class);
			criteria.createAlias("estado", "e");
			criteria.add(Restrictions.eq("e.id", estado.getId()));			
			criteria.addOrder(Order.asc("nome"));
			listaCidade =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaCidade;		
	}

}
