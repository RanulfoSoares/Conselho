package br.com.conselho.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import br.com.conselho.domain.Atendimento;
import br.com.conselho.domain.CriancaAdolescente;
import br.com.conselho.domain.Familia;
import br.com.conselho.domain.Responsavel;
import br.com.conselho.domain.Violador;
import br.com.conselho.domain.Vitima;
import br.com.conselho.util.HibernateUtil;

public class SetorPublicoDAO {

	public void salvar(Atendimento atendimento) throws Exception {

		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = sessao.beginTransaction();
			sessao.saveOrUpdate(atendimento);
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
	
	
     
     public Long totalSemFundamento(Date inicio, Date fim) throws Exception{
  		
  		Session sessao = HibernateUtil.getSessionFactory().openSession();
  		Criteria criteria = sessao.createCriteria(Atendimento.class);
  		criteria.add(Restrictions.between("dataFato", inicio, fim)); 		
  		criteria.add(Restrictions.eq("fundamentoDenuncia", "N"));
  		criteria.setProjection(Projections.rowCount());
  		Long result = (Long) criteria.uniqueResult();
  		return result;    	
    }
                    
     
     
     
	public static void main(String[] args) {
		
		try {
			
			Familia f = new Familia();
			f.setId(9L);
			SetorPublicoDAO dao = new SetorPublicoDAO();		
//			List<Atendimento> lista = dao.buscaAtendimentos(f);
//			
//			for (Atendimento atendimento : lista) {
//				System.out.println("Atendimento: "+atendimento.getDescricaoAtendimento());
//			}
			
		} catch (Exception e) {
			System.out.println("Erro: "+e.getMessage());
		}				
		
	}
	
}
