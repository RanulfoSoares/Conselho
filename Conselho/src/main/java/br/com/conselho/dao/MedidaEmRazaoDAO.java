package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.conselho.domain.MedidaEmRazao;
import br.com.conselho.util.HibernateUtil;

public class MedidaEmRazaoDAO {
	
	public List<MedidaEmRazao> busca() throws Exception {		
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<MedidaEmRazao> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(MedidaEmRazao.class);															
			criteria.addOrder(Order.asc("nome"));
			lista =  criteria.list();			
		}catch(Exception e){
			System.out.println("ERRO...."+e.getMessage());
			throw new Exception();
		}finally{
			sessao.close();
	}
	
	return lista;
	
}

}
