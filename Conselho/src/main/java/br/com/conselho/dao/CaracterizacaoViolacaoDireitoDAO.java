package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.CaracterizacaoViolacaoDireito;
import br.com.conselho.domain.DireitoFundamental;
import br.com.conselho.util.HibernateUtil;

public class CaracterizacaoViolacaoDireitoDAO {
	
	public List<CaracterizacaoViolacaoDireito> lista(DireitoFundamental direitoFundamental) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		List<CaracterizacaoViolacaoDireito> lista = null;
		
		try{
			Criteria criteria = sessao.createCriteria(CaracterizacaoViolacaoDireito.class);
			criteria.add(Restrictions.eq("direitoFundamental", direitoFundamental));
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

}
