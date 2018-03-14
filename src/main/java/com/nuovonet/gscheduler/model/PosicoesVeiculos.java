package com.nuovonet.gscheduler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="POSICOES_VEICULOS")
public class PosicoesVeiculos {

	@Id
	@Column(columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long codPosicao;
	private String placa;
	private String codTerminal;
	private String tipoRastreador;
	private String dataHoraPos;
	private Double distUltPosicao;
	private String ignicao;
	private Double latitude;
	private Double longitude;
	private String posReferencia;
	private Long velocidade;
	private Long veloMediaCalc;
	private String cidade;
	private String uF;
	private String pais;
	
	private Long codMensagem;
	private String codTecnologia;
	private String dataHoraMsg;
	private Long nrMacro;
	private String texto;
	
	private String manifesto;
	private String enviado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCodPosicao() {
		return codPosicao;
	}
	public void setCodPosicao(Long codPosicao) {
		this.codPosicao = codPosicao;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getCodTerminal() {
		return codTerminal;
	}
	public void setCodTerminal(String codTerminal) {
		this.codTerminal = codTerminal;
	}
	public String getTipoRastreador() {
		return tipoRastreador;
	}
	public void setTipoRastreador(String tipoRastreador) {
		this.tipoRastreador = tipoRastreador;
	}
	public String getDataHoraPos() {
		return dataHoraPos;
	}
	public void setDataHoraPos(String dataHoraPos) {
		this.dataHoraPos = dataHoraPos;
	}
	public Double getDistUltPosicao() {
		return distUltPosicao;
	}
	public void setDistUltPosicao(Double distUltPosicao) {
		this.distUltPosicao = distUltPosicao;
	}
	public String getIgnicao() {
		return ignicao;
	}
	public void setIgnicao(String ignicao) {
		this.ignicao = ignicao;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getPosReferencia() {
		return posReferencia;
	}
	public void setPosReferencia(String posReferencia) {
		this.posReferencia = posReferencia;
	}
	public Long getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(Long velocidade) {
		this.velocidade = velocidade;
	}
	public Long getVeloMediaCalc() {
		return veloMediaCalc;
	}
	public void setVeloMediaCalc(Long veloMediaCalc) {
		this.veloMediaCalc = veloMediaCalc;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getuF() {
		return uF;
	}
	public void setuF(String uF) {
		this.uF = uF;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Long getCodMensagem() {
		return codMensagem;
	}
	public void setCodMensagem(Long codMensagem) {
		this.codMensagem = codMensagem;
	}
	public String getCodTecnologia() {
		return codTecnologia;
	}
	public void setCodTecnologia(String codTecnologia) {
		this.codTecnologia = codTecnologia;
	}
	public String getDataHoraMsg() {
		return dataHoraMsg;
	}
	public void setDataHoraMsg(String dataHoraMsg) {
		this.dataHoraMsg = dataHoraMsg;
	}
	public Long getNrMacro() {
		return nrMacro;
	}
	public void setNrMacro(Long nrMacro) {
		this.nrMacro = nrMacro;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getManifesto() {
		return manifesto;
	}
	public void setManifesto(String manifesto) {
		this.manifesto = manifesto;
	}
	public String getEnviado() {
		return enviado;
	}
	public void setEnviado(String enviado) {
		this.enviado = enviado;
	}
	
}
