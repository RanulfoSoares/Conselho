package br.com.conselho.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.conselho.domain.DeterminacaoAplicada;
import br.com.conselho.util.HibernateUtil;

public class DeterminacaoAplicadaDAO {
	
	public void salvar(DeterminacaoAplicada determinacaoAplicada) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(determinacaoAplicada);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			System.out.println("ERRO ao salvar responsavel..." + e.getMessage());
			throw new Exception(e);
		} finally {
			sessao.close();
		}
	}	
	
}
