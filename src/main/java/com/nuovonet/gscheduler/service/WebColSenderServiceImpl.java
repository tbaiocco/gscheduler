package com.nuovonet.gscheduler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.grupotoniato.extranet.server_php.AdicionaStatusDocument;
import br.com.grupotoniato.extranet.server_php.AdicionaStatusResponseDocument;

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
	
	public RetornoTratado enviaPosicao() {
		LOGGER.info("Enviando >>> WebColServiceImpl.enviaPosicao()");
		
		com.nuovonet.ws.client.WebColService service = null;

		try {
			service = new com.nuovonet.ws.client.WebColService(url, token);
			
			//buscar posicoes a enviar
			
			//montar XML
			//XMLUtil.montaInteriorXML(dthratualizacao, codigoocorrencia, longitude, latitude, localizacao, dataentrega, dataprevisaoentrega, numerofiscal, cnpjremetente, cnpjtransportador)
			List<String> notas = new ArrayList<String>();
			notas.add(XMLUtil.montaInteriorXML("03/03/2018 08:21", "00", "-46.84210", "-23.32610", "0.97 km de FRIGORÍFICO PRIETO LTDA - CAJAMAR/SP", "", "06/03/2018", "63469", "61064929007696", "87283164000232"));
			
			String xml = XMLUtil.retornaXML((String[]) notas.toArray());
			
			//montar request
			AdicionaStatusDocument request = service.montaDados(xml);
			AdicionaStatusResponseDocument response = service.adicionaStatus(request);
			
			//tratar retorno
			RetornoTratado retorno = service.trataRetorno(response);
			
			System.out.println("retorno> " + retorno.getResult() + ":" + retorno.getError());
			for(Entry<Integer, String> entry : retorno.getReport().entrySet())
				System.out.println("detalhes: " + entry.getKey() + ": " + entry.getValue());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
