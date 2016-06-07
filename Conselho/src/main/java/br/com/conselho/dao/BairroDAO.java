package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.domain.Bairro;
import br.com.conselho.domain.Cidade;
import br.com.conselho.domain.Estado;
import br.com.conselho.util.HibernateUtil;

public class BairroDAO {
	
	public void salvar(Bairro bairro) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		
		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(bairro);
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
	
	public void excluir(Bairro bairro) throws Exception{
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(bairro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO AO EXCLUIR... Registro de bairro esta sendo utilizado em logradouro");
			throw new Exception("Registro de bairro esta sendo utilizado em logradouro");		
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
	
	public List<Bairro> busca(String nome, Cidade cidade) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Bairro> listaBairro = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Bairro.class);
			
			if(!"".equals(nome)){
				criteria.add(Restrictions.like("nome", "%"+nome+"%").ignoreCase());
			}
			
			if(cidade != null){
				criteria.add(Restrictions.eq("cidade", cidade));
			}
			
			criteria.addOrder(Order.asc("nome"));
			listaBairro =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaBairro;		
	}
	
	public List<Bairro> buscaBairro(String nome, Cidade cidade) throws Exception {
				
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Bairro> listaBairro = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Bairro.class);						
			criteria.add(Restrictions.eq("nome", nome).ignoreCase());
			criteria.add(Restrictions.eq("cidade", cidade));
			criteria.addOrder(Order.asc("nome"));
			listaBairro =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaBairro;
		
	}
	
	public List<Bairro> buscaBairro(Bairro bairro) throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Bairro> listaBairro = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Bairro.class);
			criteria.createAlias("estado", "e");
			criteria.add(Restrictions.eq("e.id", bairro.getId()));			
			criteria.addOrder(Order.asc("nome"));
			listaBairro =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaBairro;		
	}

	public List<Bairro> buscaBairro(Cidade cidade) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Bairro> listaBairro = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Bairro.class);									
			criteria.add(Restrictions.eq("cidade", cidade));
			criteria.addOrder(Order.asc("nome"));
			listaBairro =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
		}
		
		return listaBairro;
	}

}
