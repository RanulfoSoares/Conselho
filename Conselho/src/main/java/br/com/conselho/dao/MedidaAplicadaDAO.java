package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.DireitoViolado;
import br.com.conselho.domain.MedidaAplicada;
import br.com.conselho.util.HibernateUtil;

public class MedidaAplicadaDAO {
	
	public List<MedidaAplicada> busca(DireitoViolado direitoViolado, String aplicacao) throws Exception {		
		
			Session sessao = HibernateUtil.getSessionFactory().openSession();
			List<MedidaAplicada> listaCidade = null;
			
			try{
				Criteria criteria = sessao.createCriteria(MedidaAplicada.class);								
				criteria.add(Restrictions.eq("direitoViolado", direitoViolado));
				criteria.createAlias("aplicacao", "ap");
				criteria.add(Restrictions.eq("ap.nome", aplicacao));
				criteria.addOrder(Order.asc("nome"));
				listaCidade =  criteria.list();			
			}catch(Exception e){
				System.out.println("ERRO...."+e.getMessage());
				throw new Exception();
			}finally{
				sessao.close();
		}
		
		return listaCidade;
		
	}
	
}
