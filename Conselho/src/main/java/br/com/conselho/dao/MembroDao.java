package br.com.conselho.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Instituicao;
import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.util.HibernateUtil;

@SuppressWarnings("serial")
public class MembroDao implements Serializable {
	
	public Membro salvar(Membro membro) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		
		try {												
			
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(membro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
			return membro;							
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui Membro..."+e.getMessage());
			throw new Exception("ERROO Não consegui salvar Membro... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}
	
	public Membro buscaMembro(Pessoa pessoa) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Membro membro = null;

		try {
			Criteria criteria = sessao.createCriteria(Membro.class);						
			criteria.add(Restrictions.eq("pessoa", pessoa));
			criteria.add(Restrictions.isNull("dataDesvinculo"));
			membro = (Membro) criteria.uniqueResult();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return membro;
		
	}
	
	public List<Membro> buscaMembroEmUmtimaFamilia(Pessoa pessoa) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Membro> listaMembro = null;

		try {
			Criteria criteria = sessao.createCriteria(Membro.class);						
			criteria.add(Restrictions.eq("pessoa", pessoa));
			criteria.add(Restrictions.isNotNull("familia"));
			criteria.add(Restrictions.isNotNull("dataDesvinculo"));
			criteria.addOrder(Order.desc("dataDesvinculo"));
			listaMembro = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return listaMembro;
		
	}
	
	public List<Membro> buscaMembro(Familia familia) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Membro> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Membro.class);						
			criteria.add(Restrictions.eq("familia", familia));
			criteria.add(Restrictions.isNull("dataDesvinculo"));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
		return lista;		
	}
	
	
	public List<Membro> buscaMembro(Instituicao instituicao) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Membro> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(Membro.class);						
			criteria.add(Restrictions.eq("instituicao", instituicao));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
		return lista;
		
	}
	
	public void excluir(Membro membro) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(membro);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
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
	
	public Membro buscaCodigo(Long id)throws Exception{
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Criteria criteria = sessao.createCriteria(Membro.class);			
			criteria.add(Restrictions.idEq(id));
			return (Membro) criteria.uniqueResult();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}finally{
			sessao.close();
		}					
	}

}
