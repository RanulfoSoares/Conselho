package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.util.HibernateUtil;

public class IrmaoDAO {

	public void salvar(CriancaAdolescente crianca) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(crianca);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
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
	
	public void excluir(CriancaAdolescente criancaAdolescente) throws Exception {
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.delete(criancaAdolescente);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
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

	public List<CriancaAdolescente> buscaCrianca(String nome) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<CriancaAdolescente> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(CriancaAdolescente.class);			
			criteria.createAlias("pessoa", "p");
			if (!"".equals(nome)) {
				
				criteria.add(Restrictions.ilike("p.nomeCompleto", "%" + nome + "%"));
			}
			criteria.addOrder(Order.asc("p.nomeCompleto"));
			lista = criteria.list();
						
		} catch (Exception e) {
			System.out.println("ERRO...." + e.getMessage());
			throw new Exception();
		} finally {
			sessao.close();
		}
		
		return lista;
		
	}
	
	public CriancaAdolescente buscaCrianca(Long id) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<CriancaAdolescente> lista = null;

		try {
			Criteria criteria = sessao.createCriteria(CriancaAdolescente.class);
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
