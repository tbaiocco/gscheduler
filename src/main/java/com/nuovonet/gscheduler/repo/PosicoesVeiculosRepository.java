package com.nuovonet.gscheduler.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nuovonet.gscheduler.model.PosicoesVeiculos;

@Repository
public interface PosicoesVeiculosRepository extends JpaRepository<PosicoesVeiculos, Long> {
	
	public long countByCodPosicaoAndPlacaAndDataHoraPos(Long codPosicao, String placa, String dataHoraPos);
	public List<PosicoesVeiculos> findByEnviado(String enviado);
	public List<PosicoesVeiculos> findByEnviadoIsNull();

}
