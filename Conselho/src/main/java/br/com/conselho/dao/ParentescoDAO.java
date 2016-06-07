package br.com.conselho.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Membro;
import br.com.conselho.domain.Parentesco;
import br.com.conselho.util.HibernateUtil;

public class ParentescoDAO {
	
	public void salvar(Parentesco Parentesco) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(Parentesco);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar Parentesco..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}
	}
	
	public Parentesco buscaParentesco(Membro membroPrincipal, Membro membroSec) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Parentesco Parentesco = null;

		try {
			Criteria criteria = sessao.createCriteria(Parentesco.class);											
			criteria.add(Restrictions.eq("parentePrincipal", membroPrincipal));
			criteria.add(Restrictions.eq("parente", membroSec));
			Parentesco = (Parentesco) criteria.uniqueResult();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
		return Parentesco;		
	}
	
	
	public void excluir(Parentesco Parentesco) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(Parentesco);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO ao excluir Parentesco..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
	}
	

}
