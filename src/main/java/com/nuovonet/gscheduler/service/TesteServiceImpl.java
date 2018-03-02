package com.nuovonet.gscheduler.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nuovonet.rest.RasterExecutor;
import com.nuovonet.rest.impl.RasterExecutorImpl;
import com.nuovonet.rest.model.BaseRetorno;
import com.nuovonet.rest.model.Posicao;

@Service("testeService")
public class TesteServiceImpl implements TesteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TesteServiceImpl.class);
	
	public void fazAlgo() {
		// TODO Auto-generated method stub
		LOGGER.info("executou >>> TesteServiceImpl.fazAlgo()");
		RasterExecutor rasterExecutor = new RasterExecutorImpl();
		BaseRetorno retorno = rasterExecutor.getPosicoes(null,null);
		if (retorno.getPosicoes() != null)
			for (Posicao pos : retorno.getPosicoes()) {
				LOGGER.info("codPosicao:"+pos.getCodPosicao());
				LOGGER.info("placa:"+pos.getPlaca());
				LOGGER.info("dataHoraPos:"+pos.getDataHoraPos());
				LOGGER.info("codTerminal:"+pos.getCodTerminal());
				LOGGER.info("local:"+pos.getPosReferencia());
				LOGGER.info("--------------------------------------------------------------");
			}
				
	}

}
