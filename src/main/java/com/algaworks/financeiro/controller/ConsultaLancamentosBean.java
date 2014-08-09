package com.algaworks.financeiro.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.algaworks.financeiro.model.Lancamento;
import com.algaworks.financeiro.repository.FiltroLancamento;
import com.algaworks.financeiro.repository.Lancamentos;

@Named
@ViewScoped
public class ConsultaLancamentosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Lancamentos lancamentos;
	
	private FiltroLancamento filtro = new FiltroLancamento();
	private LazyDataModel<Lancamento> model;
	
	public ConsultaLancamentosBean() {
		model = new LazyDataModel<Lancamento>() {

			private static final long serialVersionUID = 1L;
			
			@Override
			public List<Lancamento> load(int first, int pageSize,
					String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				
				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);
				
				setRowCount(lancamentos.quantidadeFiltrados(filtro));
				
				return lancamentos.filtrados(filtro);
			}
			
		};
	}
	
	public FiltroLancamento getFiltro() {
		return filtro;
	}

	public LazyDataModel<Lancamento> getModel() {
		return model;
	}

}