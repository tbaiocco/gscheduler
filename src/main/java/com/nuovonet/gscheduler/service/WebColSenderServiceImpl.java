package com.nuovonet.gscheduler.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import br.com.grupotoniato.extranet.server_php.AdicionaStatusDocument;
import br.com.grupotoniato.extranet.server_php.AdicionaStatusResponseDocument;

import com.nuovonet.gscheduler.model.PosicoesVeiculos;
import com.nuovonet.gscheduler.repo.PosicoesVeiculosRepository;
import com.nuovonet.ws.client.RetornoTratado;
import com.nuovonet.ws.client.XMLUtil;

@Service
@Transactional
public class WebColSenderServiceImpl implements WebColSenderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebColSenderServiceImpl.class);
	
	@Value("${webcol.url:}")
	String url;
	@Value("${webcol.token:}")
	String token;
	
	@Autowired
	PosicoesVeiculosRepository posicoesVeiculosRepository;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public RetornoTratado enviaPosicao() {
		LOGGER.info("Enviando >>> WebColServiceImpl.enviaPosicao()");
		
		com.nuovonet.ws.client.WebColService service = null;

		try {
			service = new com.nuovonet.ws.client.WebColService(url, token);
			
			//montar XML
			//XMLUtil.montaInteriorXML(dthratualizacao, codigoocorrencia, longitude, latitude, localizacao, dataentrega, dataprevisaoentrega, numerofiscal, cnpjremetente, cnpjtransportador)
			List<String> notas = new ArrayList<String>();
			
			//buscar posicoes a enviar
			List<PosicoesVeiculos> posicoes = posicoesVeiculosRepository.findByEnviadoIsNull();
			LOGGER.info("quantos achou:"+posicoes.size());
			for(PosicoesVeiculos position : posicoes) {
				//obter notas dos manifestos dos cnpjs
				//descobrir as posicoes de cada um dos manifestos, descartar as já enviadas
				//popula o registro de posicoes para cada nf do manifesto
				String query = "select notas_fiscais.nr_nota_fiscal, notas_fiscais.oid_pessoa, notas_fiscais.oid_pessoa_destinatario, unidades.oid_pessoa, manifestos.dt_previsao_chegada " +
								" from manifestos, viagens, conhecimentos, conhecimentos_notas_fiscais, notas_fiscais, unidades " +
								" where manifestos.oid_manifesto = viagens.oid_manifesto " +
								" and viagens.oid_conhecimento = conhecimentos.oid_conhecimento " +
								" and conhecimentos.oid_conhecimento = conhecimentos_notas_fiscais.oid_conhecimento " +
								" and conhecimentos_notas_fiscais.oid_nota_fiscal = notas_fiscais.oid_nota_fiscal " +
								" and manifestos.oid_unidade = unidades.oid_unidade " +
								" and "
								+ "(notas_fiscais.oid_pessoa in ('61064929000330','61064929007696','61064929009206') "
//								+ " or notas_fiscais.oid_pessoa_destinatario in ('61064929000330','61064929007696','61064929009206')"
								+ ") " +
								" and manifestos.oid_manifesto = '" + position.getManifesto() + "'";
				try {
					SqlRowSet rows = jdbcTemplate.queryForRowSet(query);
					int rowCount = 0;
					while(rows.next()) {
						//acertar datas
						Date previsao = rows.getDate(5);
						LOGGER.info(position.getDataHoraPos());
						if(position.getDataHoraPos() != null && !position.getDataHoraPos().isEmpty()) {
							Date dtPos = new Date();
							dtPos = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(position.getDataHoraPos());
							notas.add(
									XMLUtil.montaInteriorXML(
											new SimpleDateFormat("dd/MM/yyy' 'HH:mm").format(dtPos), 
											"00", 
											position.getLongitude().toString(), 
											position.getLatitude().toString(), 
											position.getPosReferencia(), 
											"", 
											new SimpleDateFormat("dd/MM/yyyy").format(previsao), 
											rows.getString(1), 
											rows.getString(2), 
											rows.getString(4)));
							rowCount++;
						}
					}
//					LOGGER.info("qtos:"+rowCount);
					position.setEnviado("S");
					posicoesVeiculosRepository.saveAndFlush(position);
					
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("nao montou xml de status webCOL... "+e.getMessage());
					return null;
				}
				//Marcar posicoes como enviadas
				
			}
			
			String xml = XMLUtil.retornaXML(notas);
			LOGGER.info(xml);
			if(notas.size()>0) {
				//montar request
				AdicionaStatusDocument request = service.montaDados(xml);
				AdicionaStatusResponseDocument response = service.adicionaStatus(request);
				
				//tratar retorno
				RetornoTratado retorno = service.trataRetorno(response);
				
				LOGGER.info("ENVIO WEBCOL retorno> " + retorno.getResult() + ":" + retorno.getError());
				for(Entry<Integer, String> entry : retorno.getReport().entrySet())
					LOGGER.info("detalhes: " + entry.getKey() + ": " + entry.getValue());
		
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
