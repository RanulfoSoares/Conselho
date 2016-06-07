package br.com.conselho.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.conselho.domain.Adendo;
import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.Atribuicao;
import br.com.conselho.util.HibernateUtil;

public class AtribuicaoDAO {
	
	public Atribuicao salvar(Atribuicao atribuicao) throws Exception {
		
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;			
		
		try {												
			
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(atribuicao);
			transaction.commit();
			System.out.println("Sucesso!!!!...");
			return atribuicao;							
			
		} catch (Exception e) {		
			if(transaction != null){
				transaction.rollback();
			}
			System.out.println("ERROO Não consegui salvar Nucleo Familiar..."+e.getMessage());
			throw new Exception("ERROO Não consegui salvar Nucleo Familiar... "+e.getMessage());
		}finally{
			sessao.close();
		}			
	}

}
