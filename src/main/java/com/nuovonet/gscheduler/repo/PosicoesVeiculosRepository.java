package com.nuovonet.gscheduler.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nuovonet.gscheduler.model.PosicoesVeiculos;

@Repository
public interface PosicoesVeiculosRepository extends JpaRepository<PosicoesVeiculos, Long> {
	
	public long countByCodPosicao(Long codPosicao);

}
