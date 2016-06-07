package br.com.conselho.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.conselho.domain.VioladorRegistroMedidaAplicada;
import br.com.conselho.util.HibernateUtil;

public class VioladorRegistroMedidaAplicadaDAO {
	
	public void salvar(VioladorRegistroMedidaAplicada violador) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(violador);
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
