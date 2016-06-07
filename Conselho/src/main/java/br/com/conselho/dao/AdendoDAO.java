package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.conselho.domain.Adendo;
import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.HibernateUtil;

public class AdendoDAO {
	
	public void salvar(Adendo adendo) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(adendo);
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
	
	public List<Adendo> buscaAdendo(Atendimento atendimento) throws Exception{
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<Adendo> lista;					
		
		Criteria criteria = sessao.createCriteria(Adendo.class);
		criteria.add(Restrictions.eq("atendimento", atendimento));
		criteria.addOrder(Order.desc("dataCadastro"));
		lista = criteria.list();
		
		return lista;
				
	}

}
