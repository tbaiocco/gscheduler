package com.nuovonet.gscheduler.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void executaAtualizacao() {
		LOGGER.info("executou >>> RasterServiceImpl.executaAtualizacao()");
		RasterExecutor rasterExecutor = new RasterExecutorImpl();
		BaseRetorno retorno = rasterExecutor.getPosicoes("Ultimas","0");
		if (retorno.getPosicoes() != null)
			for (Posicao pos : retorno.getPosicoes()) {
				LOGGER.info("codPosicao:"+pos.getCodPosicao());
				LOGGER.info("placa:"+limpaCampo(pos.getPlaca()));
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
				if(posicoesVeiculosRepository.countByCodPosicaoAndPlacaAndDataHoraPos(pos.getCodPosicao(), ed.getPlaca(), pos.getDataHoraPos()) <= 0){
					
					String sqlMan = "select manifestos.oid_manifesto " +
					"from manifestos " +
					"where oid_veiculo='" + ed.getPlaca() +"' " +
					"and dt_recebimento_mdfe <= '" + ed.getDataHoraPos() + "'" +
					"and dm_status_mdfe = '100' " +
					"and ((dm_status_encerramento_mdfe = '135' and dt_recebimento_encerramento_mdfe >= '" + ed.getDataHoraPos() + "')" +
					" or dm_status_encerramento_mdfe is null) " +
					"and dm_status_cancelamento_mdfe is null";
					
					ed.setManifesto(getManifesto(sqlMan));
					
					//não duplicar posicoes pelo código
					posicoesVeiculosRepository.saveAndFlush(ed);
				}
			}

	}
	
	@SuppressWarnings("unchecked")
	private String getManifesto(String sql) {
		try {
			return (String)jdbcTemplate.queryForObject(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int line) throws SQLException {
					String aux = rs.getString("oid_manifesto");
					return aux;
				}
			});
		} catch (Exception e) {
			return null;
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
