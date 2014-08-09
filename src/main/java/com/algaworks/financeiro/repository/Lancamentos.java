package com.algaworks.financeiro.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.algaworks.financeiro.model.Lancamento;

public class Lancamentos implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public List<Lancamento> filtrados(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());
		
		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}
		
		return criteria.list();
	}
	
	public int quantidadeFiltrados(FiltroLancamento filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);
		
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) criteria.uniqueResult()).intValue();
	}
	
	private Criteria criarCriteriaParaFiltro(FiltroLancamento filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Lancamento.class);
		
		if (StringUtils.isNotEmpty(filtro.getDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE));
		}
		
		return criteria;
	}

}