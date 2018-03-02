package com.nuovonet.gscheduler.repo.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;

public class DefaultSpecification<T> implements Specification<T> {

	protected final Logger logger = Logger.getLogger(getClass().getName());

//	private final PaginacaoSearchParamVO queryVO;
//	
//	public DefaultSpecification (PaginacaoSearchParamVO queryVO) {
//		this.queryVO = queryVO;
//	}
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicatesAnd = new ArrayList<Predicate>();
		List<Predicate> predicatesOr = new ArrayList<Predicate>();
//		if(queryVO != null) {
//			for (Entry<String, Integer> campo: queryVO.getTiposPesquisa().entrySet()) {
//				try {
//					if (campo.getValue() == CampoPesquisaUtils.AVULSA) {
//						
//					}
//					if (campo.getValue() == CampoPesquisaUtils.STRING) {
//						String campoPesquisa = queryVO.getCamposPesquisa().get(campo.getKey());
//						if (campoPesquisa != null && !StringUtils.isEmptyOrNull(campoPesquisa) && queryVO.getCampoPesquisa() != null && !StringUtils.isEmptyOrNull((String) queryVO.getCampoPesquisa())) {
//							predicatesOr.add(cb.like(cb.lower(root.get(campoPesquisa).as(String.class)), "%" + String.valueOf(queryVO.getCampoPesquisa()).toLowerCase() + "%"));
//						}
//					} else if (campo.getValue() == CampoPesquisaUtils.LONG) {
//						long valorBusca = 0L;
//						if ((queryVO.getCampoPesquisa() != null) && (!queryVO.getCampoPesquisa().toString().isEmpty())) {
//							valorBusca = Long.parseLong((String) queryVO.getCampoPesquisa());
//						}
//						if (valorBusca != 0L) {
//							predicatesOr.add(cb.equal(root.get(campo.getKey()).as(Long.class), valorBusca));
//						}
//					} else if (campo.getValue() == CampoPesquisaUtils.INTEGER) {
//						if ((queryVO.getCampoPesquisa() != null) && (!queryVO.getCampoPesquisa().toString().isEmpty())) {
//							Integer valor = Integer.parseInt((String) queryVO.getCampoPesquisa());
//							if (valor != 0) {
//								predicatesOr.add(cb.equal(root.get(campo.getKey()).as(Integer.class), valor));
//							}
//						}
//					}
//				} catch (Exception e) {
//					//do nothing
//				}
//			}
//			
//			String campoPesquisa = (String) queryVO.getCampoPesquisa();
//			
//			if(StringUtil.doValida(campoPesquisa)){
//				for (String campo: queryVO.getColunasParaBusca()) {
//					if(StringUtil.doValida(campo)) {
//						predicatesOr.add(cb.like(cb.upper(root.get(campo).as(String.class)), "%" + String.valueOf(queryVO.getCampoPesquisa()).toUpperCase() + "%"));
//					} 
//				}
////				queryVO.setColunasParaBusca(new ArrayList<String>(queryVO.getCamposPesquisa().keySet()));
//				for (String campo: queryVO.getCamposPesquisa().keySet()) {
//					if(StringUtil.doValida(campo)) {
//						String[] parts = campo.split("[.]");
//						if(parts.length > 1){
//							predicatesOr.add(cb.like(cb.upper(root.get(parts[0]).get(parts[1]).as(String.class)), "%" + String.valueOf(queryVO.getCampoPesquisa()).toUpperCase() + "%"));
//						} else {
//							predicatesOr.add(cb.like(cb.upper(root.get(campo).as(String.class)), "%" + String.valueOf(queryVO.getCampoPesquisa()).toUpperCase() + "%"));
//						}
//					} 
//				}
				predicatesAnd.add(orTogether(predicatesOr, cb));
//			}
			
//		}
		return andTogether(predicatesAnd, cb);
	}
	
	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
	    return cb.and(predicates.toArray(new Predicate[0]));
	  }
	
	private Predicate orTogether(List<Predicate> predicates, CriteriaBuilder cb) {
	    return cb.or(predicates.toArray(new Predicate[0]));
	  }

//	public PaginacaoSearchParamVO getQueryVO() {
//		return queryVO;
//	}

}
