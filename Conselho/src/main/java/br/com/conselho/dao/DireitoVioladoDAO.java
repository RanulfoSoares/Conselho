package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.GrupoDeDireito;
import br.com.conselho.domain.RegistroDireitoViolado;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.HibernateUtil;

public class DireitoVioladoDAO {
	
	public List<DireitoViolado> lista(GrupoDeDireito grupoDeDireito) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<DireitoViolado> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(DireitoViolado.class);
			criteria.add(Restrictions.eq("grupoDeDireito", grupoDeDireito));
			criteria.addOrder(Order.asc("nome"));
			lista = criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception(e);
		}finally{
			sessao.close();
		}
		
		return lista;		
	}
	
	public List<DireitoViolado> buscaPorRegistro(Atendimento atendimento) throws Exception{
		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<DireitoViolado> lista;
		
		DetachedCriteria subCriteria = DetachedCriteria.forClass(RegistroDireitoViolado.class);
		subCriteria.createAlias("atendimento", "a");
		subCriteria.add(Restrictions.eq("a.id", atendimento.getId()));
		
		
		subCriteria.setProjection(Projections.property("atendimento.id"));		
		
		Criteria criteria = sessao.createCriteria(Atendimento.class);
		criteria.add(Subqueries.propertyIn("id", subCriteria));
		criteria.addOrder(Order.desc("dataRegistro"));
		lista = criteria.list();
		
		return lista;
				
	}		

}
