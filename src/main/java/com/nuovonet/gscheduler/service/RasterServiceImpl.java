package com.nuovonet.gscheduler.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuovonet.gscheduler.model.PosicoesVeiculos;
import com.nuovonet.gscheduler.repo.PosicoesVeiculosRepository;
import com.nuovonet.rest.RasterExecutor;
import com.nuovonet.rest.impl.RasterExecutorImpl;
import com.nuovonet.rest.model.BaseRetorno;
import com.nuovonet.rest.model.Posicao;

@Service
@Transactional
public class RasterServiceImpl implements RasterService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RasterServiceImpl.class);

	@Autowired
	PosicoesVeiculosRepository posicoesVeiculosRepository;
	
	public void executaAtualizacao() {
		LOGGER.info("executou >>> RasterServiceImpl.executaAtualizacao()");
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
				PosicoesVeiculos ed = new PosicoesVeiculos();
				ed.setCodPosicao(pos.getCodPosicao());
				
				ed.setPlaca(limpaCampo(pos.getPlaca()));
//System.out.println(ed.getPlaca());
				ed.setCodTerminal(pos.getCodTerminal());
				ed.setTipoRastreador(pos.getTipoRastreador());
				ed.setDataHoraPos(pos.getDataHoraPos());
				ed.setDistUltPosicao(pos.getDistUltPosicao());
				ed.setIgnicao(pos.getIgnicao());
				ed.setLatitude(pos.getLatitude());
				ed.setLongitude(pos.getLongitude());
				ed.setPosReferencia(pos.getPosReferencia());
				ed.setVelocidade(pos.getVelocidade());
				ed.setVeloMediaCalc(pos.getVeloMediaCalc());
				ed.setCidade(pos.getCidade());
				ed.setuF(pos.getuF());
				ed.setPais(pos.getPais());
//				esses sao das mensagens
//				ed.setCodMensagem(msg.getCodMensagem());
//				ed.setCodTecnologia(msg.getCodTecnologia());
//				ed.setDataHoraMsg(msg.getDataHoraMsg());
//				ed.setNrMacro(msg.getNrMacro());
//				ed.setTexto(msg.getTexto());
				if(posicoesVeiculosRepository.countByCodPosicao(pos.getCodPosicao()) <= 0){
					//não duplicar posicoes pelo código
					posicoesVeiculosRepository.save(ed);
				}
			}

	}
	
	public void verificaBanco() {
		LOGGER.info("Verificando posicoes cadastradas >>> RasterServiceImpl.verificaBanco()");
		for (PosicoesVeiculos pos : posicoesVeiculosRepository.findAll()) {
			LOGGER.info("codPosicao:"+pos.getCodPosicao());
			LOGGER.info("placa:"+pos.getPlaca());
			LOGGER.info("dataHoraPos:"+pos.getDataHoraPos());
			LOGGER.info("codTerminal:"+pos.getCodTerminal());
			LOGGER.info("local:"+pos.getPosReferencia());
			LOGGER.info("--------------------------------------------------------------");
		}

	}
	
	private String limpaCampo(String str) {

	      String ret="";

	      if (str != null) {
	              char[] strChar = str.toCharArray();
	              char[] novaStr = new char[strChar.length];
	              int j=0;
	              for (int i=0;i<strChar.length;i++) {
	                      if ( (strChar[i]!='/') && (strChar[i]!='.') && (strChar[i]!='-') && (strChar[i]!=',') && (strChar[i]!=' '))	{
	                              novaStr[j] = strChar[i];
	                              j++;
	                      }
	              }
	              char[] cRet = new char[j];

	              System.arraycopy(novaStr,0,cRet,0,j);

	              ret = new String(cRet);
	      }
	      return ret;
	}

}
