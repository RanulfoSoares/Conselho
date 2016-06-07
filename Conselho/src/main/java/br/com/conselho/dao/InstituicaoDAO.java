package br.com.conselho.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import br.com.conselho.domain.Instituicao;
import br.com.conselho.util.HibernateUtil;

public class InstituicaoDAO {
	
	public void salvar(Instituicao ins) throws ConstraintViolationException, Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;		
		
		try{
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(ins);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (ConstraintViolationException e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..."+e.getMessage());
			throw new ConstraintViolationException(e.getMessage(), null, null);
		}catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO..."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}			
		
	}
	
	public List<Instituicao> listaEscola() throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Instituicao> lista = null;
		
		try{
			String[] restricao = new String[]{"Escola pública","Escola particular","Creche pública","Creche particular"};
			Criteria criteria = sessao.createCriteria(Instituicao.class);
			criteria.createAlias("tipoInstituicao", "t");
			criteria.add(Restrictions.in("t.nome", restricao));					
			criteria.addOrder(Order.asc("fantasia"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	
	public List<Instituicao> listaInstituicao(String nome) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Instituicao> lista = new ArrayList<Instituicao>();
		
		try{
			Criteria criteria = sessao.createCriteria(Instituicao.class);
			
			if (!"".equals(nome)) {				
				criteria.add(Restrictions.ilike("fantasia", "%" + nome + "%"));
			}
			
			criteria.addOrder(Order.asc("fantasia"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<Instituicao> listaCNPJ(String cnpj) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Instituicao> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(Instituicao.class);						
			criteria.add(Restrictions.ilike("cnpj", cnpj));			
						
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	
	public List<Instituicao> listaCIE(String cie) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Instituicao> lista = new ArrayList<Instituicao>();
		
		try{
			Criteria criteria = sessao.createCriteria(Instituicao.class);						
			criteria.add(Restrictions.ilike("cie", cie));			
						
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;
		
	}
	
	public void excluir(Instituicao instituicao) throws Exception, ConstraintViolationException {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(instituicao);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (ConstraintViolationException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO..." + e.getMessage());
			throw new ConstraintViolationException(e.getMessage(), null, null);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO..." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}		
	}
	
	
	
	public Instituicao buscaInstituicao(Long id) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Instituicao> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Instituicao.class);
			criteria.add(Restrictions.idEq(id));
			
			//criteria.addOrder(Order.asc("nome"));
			lista = criteria.list();
			if(!lista.isEmpty()){
				return lista.get(0);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
	}
	
}
