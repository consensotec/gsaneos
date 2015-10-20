package br.com.ipad.gsaneos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.ipad.gsaneos.util.Util;

public class OrdemServicoSubstituicaoHidrometro extends ObjetoBasico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "ordem_servico_substituicao_hidr";
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOS;
	private Integer numeroLeituraHidrometro;
	private HidrometroSituacao hidrometroSituacao;
	private HidrometroLocalArmazenagem hidrometroLocalArmazenagem;
	private Integer tipoHidrometro;
	private Hidrometro hidrometro;
	private Integer indicadorCavalete;
	private Integer leituraInstalacao;
	private String numeroSelo;
	private Date ultimaAlteracao;

	public OrdemServicoSubstituicaoHidrometro(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	
	public OrdemServicoSubstituicaoHidrometro(){}
	
	public static String[] COLUNAS = new String[]
			{
				
				OrdemServicoSubstituicaoHidrometros.ORDEM_SERVICO_EXECUCAO_OS,
				OrdemServicoSubstituicaoHidrometros.NUMERO_LEITURA_HIDROMETRO,
				OrdemServicoSubstituicaoHidrometros.HIDROMETRO_SITUACAO,
				OrdemServicoSubstituicaoHidrometros.HIDROMETRO_LOCAL_ARMAZENAGEM,
				OrdemServicoSubstituicaoHidrometros.TIPO_HIDROMETRO,
				OrdemServicoSubstituicaoHidrometros.HIDROMETRO,
				OrdemServicoSubstituicaoHidrometros.INDICADOR_CAVALETE,
				OrdemServicoSubstituicaoHidrometros.LEITURA_INSTALACAO,
				OrdemServicoSubstituicaoHidrometros.NUMERO_SELO,
				OrdemServicoSubstituicaoHidrometros.ULTIMA_ALTERACAO
		
			};
	
	public String[] getColunas(){
		return COLUNAS;
	}	
	
	public static final class OrdemServicoSubstituicaoHidrometros implements BaseColumns {
		public static final String ORDEM_SERVICO_EXECUCAO_OS = "ORSE_ID";
		public static final String NUMERO_LEITURA_HIDROMETRO = "OSSH_NNLEITURAHIDROMETRO";
		public static final String HIDROMETRO_SITUACAO = "HIST_ID";
		public static final String HIDROMETRO_LOCAL_ARMAZENAGEM = "HILA_ID";
		public static final String TIPO_HIDROMETRO = "OSSH_ICTIPOHIDROMETRO";
		public static final String HIDROMETRO = "HIDR_ID";
		public static final String INDICADOR_CAVALETE = "OSSH_ICCAVALETE";
		public static final String LEITURA_INSTALACAO = "OSSH_NNLEITURAINSTALACAO";
		public static final String NUMERO_SELO = "OSSH_NNSELO";
		public static final String ULTIMA_ALTERACAO = "OSSH_TMULTIMAALTERACAO";
	}
	
	public final class OrdemServicoSubstituicaoHidrometrosTipos {
		public final String ORDEM_SERVICO_EXECUCAO_OS = " INTEGER UNSIGNED NOT NULL";
		public final String NUMERO_LEITURA_HIDROMETRO = " INTEGER UNSIGNED NOT NULL";
		public final String HIDROMETRO_SITUACAO = " INTEGER UNSIGNED NOT NULL";
		public final String HIDROMETRO_LOCAL_ARMAZENAGEM = " INTEGER UNSIGNED NOT NULL";
		public final String TIPO_HIDROMETRO = " INTEGER UNSIGNED NOT NULL";
		public final String HIDROMETRO = " INTEGER UNSIGNED NOT NULL";
		public final String INDICADOR_CAVALETE = " SMALLINT UNSIGNED NOT NULL";
		public final String LEITURA_INSTALACAO = " INTEGER UNSIGNED NULL";
		public final String NUMERO_SELO = " VARCHAR(12) NULL";
		public final String ULTIMA_ALTERACAO = " TIMESTAMP NOT NULL";
		
		private String[] tipos = new String[] 
				{
				
					ORDEM_SERVICO_EXECUCAO_OS,
					NUMERO_LEITURA_HIDROMETRO,
					HIDROMETRO_SITUACAO,
					HIDROMETRO_LOCAL_ARMAZENAGEM,
					TIPO_HIDROMETRO,
					HIDROMETRO,
					INDICADOR_CAVALETE,
					LEITURA_INSTALACAO,
					NUMERO_SELO,
					ULTIMA_ALTERACAO
					
				};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	
	@Override
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(OrdemServicoSubstituicaoHidrometros.ORDEM_SERVICO_EXECUCAO_OS, getOrdemServicoExecucaoOS().getId());
		values.put(OrdemServicoSubstituicaoHidrometros.NUMERO_LEITURA_HIDROMETRO, getNumeroLeituraHidrometro());
		values.put(OrdemServicoSubstituicaoHidrometros.HIDROMETRO_SITUACAO, getHidrometroSituacao().getId());
		values.put(OrdemServicoSubstituicaoHidrometros.HIDROMETRO_LOCAL_ARMAZENAGEM, getHidrometroLocalArmazenagem().getId());
		values.put(OrdemServicoSubstituicaoHidrometros.TIPO_HIDROMETRO, getTipoHidrometro());
		values.put(OrdemServicoSubstituicaoHidrometros.HIDROMETRO, getHidrometro().getId());
		values.put(OrdemServicoSubstituicaoHidrometros.INDICADOR_CAVALETE, getIndicadorCavalete());
		values.put(OrdemServicoSubstituicaoHidrometros.LEITURA_INSTALACAO, getLeituraInstalacao());
		values.put(OrdemServicoSubstituicaoHidrometros.NUMERO_SELO, getNumeroSelo());
		String dataString = Util.convertDateToDateStr(getUltimaAlteracao());
		values.put(OrdemServicoSubstituicaoHidrometros.ULTIMA_ALTERACAO, dataString);
		
		return values;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<OrdemServicoSubstituicaoHidrometro> preencherObjetos(Cursor cursor) {		
			
		int ordemServicoExecucao = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.ORDEM_SERVICO_EXECUCAO_OS);
		int numeroLeituraHidrometro = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.NUMERO_LEITURA_HIDROMETRO);
		int situacao = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.HIDROMETRO_SITUACAO);
		int armazenagem = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.HIDROMETRO_LOCAL_ARMAZENAGEM);
		int tipoHidrometro = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.TIPO_HIDROMETRO);
		int hidrometroId = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.HIDROMETRO);
		int indicadorCavalete = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.INDICADOR_CAVALETE);
		int leituraInstalacao = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.LEITURA_INSTALACAO);
		int numeroSelo = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.NUMERO_SELO);
		int ultimaAlteracao = cursor.getColumnIndex(OrdemServicoSubstituicaoHidrometros.ULTIMA_ALTERACAO);
		
		ArrayList<OrdemServicoSubstituicaoHidrometro> listaOrdemServicoSubstituicaoHidrometro = new ArrayList<OrdemServicoSubstituicaoHidrometro>();
		do {	
			OrdemServicoSubstituicaoHidrometro ordemServicoSubstituicaoHidrometro = new OrdemServicoSubstituicaoHidrometro();
			
			ordemServicoSubstituicaoHidrometro.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoExecucao));
			ordemServicoSubstituicaoHidrometro.setNumeroLeituraHidrometro(cursor.getInt(numeroLeituraHidrometro));
			ordemServicoSubstituicaoHidrometro.setHidrometroSituacao(cursor.getInt(situacao));
			ordemServicoSubstituicaoHidrometro.setHidrometroLocalArmazenagem(cursor.getInt(armazenagem));
			ordemServicoSubstituicaoHidrometro.setTipoHidrometro(cursor.getInt(tipoHidrometro));
			ordemServicoSubstituicaoHidrometro.setHidrometro(cursor.getInt(hidrometroId));
			ordemServicoSubstituicaoHidrometro.setLeituraInstalacao(cursor.getInt(leituraInstalacao));
			ordemServicoSubstituicaoHidrometro.setNumeroSelo(cursor.getString(numeroSelo));
			ordemServicoSubstituicaoHidrometro.setIndicadorCavalete(cursor.getInt(indicadorCavalete));
			ordemServicoSubstituicaoHidrometro.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			listaOrdemServicoSubstituicaoHidrometro.add(ordemServicoSubstituicaoHidrometro);
			
		} while (cursor.moveToNext());
		
		return listaOrdemServicoSubstituicaoHidrometro;
	}

	private void insertFromFile(ArrayList<String> obj){
		setOrdemServicoExecucaoOS(Util.verificarNuloInt(obj.get(1)));	
		setNumeroLeituraHidrometro(Util.verificarNuloInt(obj.get(2)));
		setHidrometro(Util.verificarNuloInt(obj.get(3)));
		setHidrometroLocalArmazenagem(Util.verificarNuloInt(obj.get(4)));
		setTipoHidrometro(Util.verificarNuloInt(obj.get(5)));
		setHidrometro(Util.verificarNuloInt(obj.get(6)));
		setIndicadorCavalete(Util.verificarNuloInt(obj.get(7)));
		setLeituraInstalacao(null);
		setNumeroSelo(null);
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public OrdemServicoExecucaoOS getOrdemServicoExecucaoOS() {
		return ordemServicoExecucaoOS;
	}

	public void setOrdemServicoExecucaoOS(
			Integer ordemServicoExecucaoId) {
		OrdemServicoExecucaoOS obj = new OrdemServicoExecucaoOS(ordemServicoExecucaoId);
		this.ordemServicoExecucaoOS = obj;
	}

	public Integer getNumeroLeituraHidrometro() {
		return numeroLeituraHidrometro;
	}

	public void setNumeroLeituraHidrometro(Integer numeroLeituraHidrometro) {
		this.numeroLeituraHidrometro = numeroLeituraHidrometro;
	}

	public HidrometroSituacao getHidrometroSituacao() {
		return hidrometroSituacao;
	}

	public void setHidrometroSituacao(Integer hidrometroSituacaoId) {
		HidrometroSituacao obj = new HidrometroSituacao(hidrometroSituacaoId); 
		this.hidrometroSituacao = obj;
	}

	public HidrometroLocalArmazenagem getHidrometroLocalArmazenagem() {
		return hidrometroLocalArmazenagem;
	}

	public void setHidrometroLocalArmazenagem(
			Integer hidrometroLocalArmazenagemId) {
		HidrometroLocalArmazenagem obj = new HidrometroLocalArmazenagem(hidrometroLocalArmazenagemId);
		this.hidrometroLocalArmazenagem = obj;
	}

	public Integer getTipoHidrometro() {
		return tipoHidrometro;
	}

	public void setTipoHidrometro(Integer tipoHidrometro) {
		this.tipoHidrometro = tipoHidrometro;
	}

	public Hidrometro getHidrometro() {
		return hidrometro;
	}

	public void setHidrometro(Integer hidrometroId) {
		Hidrometro obj = new Hidrometro(hidrometroId);
		this.hidrometro = obj;
	}

	public Integer getIndicadorCavalete() {
		return indicadorCavalete;
	}

	public void setIndicadorCavalete(Integer indicadorCavalete) {
		this.indicadorCavalete = indicadorCavalete;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}

	public Integer getLeituraInstalacao() {
		return leituraInstalacao;
	}

	public void setLeituraInstalacao(Integer leituraInstalacao) {
		this.leituraInstalacao = leituraInstalacao;
	}

	public String getNumeroSelo() {
		return numeroSelo;
	}

	public void setNumeroSelo(String numeroSelo) {
		this.numeroSelo = numeroSelo;
	}

	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
}

