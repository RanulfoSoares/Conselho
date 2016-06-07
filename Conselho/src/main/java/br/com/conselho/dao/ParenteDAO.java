package br.com.conselho.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Parente;
import br.com.conselho.domain.Pessoa;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.util.HibernateUtil;

public class ParenteDAO {
	
	public void salvar(Parente parente) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(parente);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar parente..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}
	}
	
	public Parente buscaParente(CriancaAdolescente crianca, Pessoa pessoa) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Parente parente = null;

		try {
			Criteria criteria = sessao.createCriteria(Parente.class);											
			criteria.add(Restrictions.eq("criancaAdolescente", crianca));
			criteria.add(Restrictions.eq("parente", pessoa));
			parente = (Parente) criteria.uniqueResult();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
		return parente;		
	}
	
	
	public void excluir(Parente parente) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(parente);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERROO ao excluir parente..." + e.getMessage());
			throw new Exception(e.getMessage());
		} finally {
			sessao.close();
		}
		
	}
	

}
