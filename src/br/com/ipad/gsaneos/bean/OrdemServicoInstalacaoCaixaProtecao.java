package br.com.ipad.gsaneos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.ipad.gsaneos.util.Util;

public class OrdemServicoInstalacaoCaixaProtecao extends ObjetoBasico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "ordem_servico_inst_caixa_protecao";
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOS;
	private HidrometroLocalInstalacao hidrometroLocalInstalacao;
	private HidrometroProtecao hidrometroProtecao;
	private Integer indicadorTrocaProtecao;
	private Integer indicadorTrocaRegistro;
	private Date ultimaAlteracao;
	
	public OrdemServicoInstalacaoCaixaProtecao(){}	
	
	public OrdemServicoInstalacaoCaixaProtecao(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	
	public static String[] COLUNAS = new String[]
		{
		
			OrdemServicoInstalacaoCaixaProtecoes.ORDEM_SERVICO_EXECUCAO_OS,
			OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_LOCAL_INSTALACAO,
			OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_PROTECAO,
			OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_PROTECAO,
			OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_REGISTRO,
			OrdemServicoInstalacaoCaixaProtecoes.ULTIMA_ALTERACAO
	
		};
	
	public String[] getColunas(){
		return COLUNAS;
	}	
	
	public static final class OrdemServicoInstalacaoCaixaProtecoes implements BaseColumns {
		public static final String ORDEM_SERVICO_EXECUCAO_OS = "ORSE_ID";
		public static final String HIDROMETRO_LOCAL_INSTALACAO = "HILI_ID";
		public static final String HIDROMETRO_PROTECAO = "HIPR_ID";
		public static final String INDICADOR_TROCA_PROTECAO = "OSIC_ICTROCAPROTECAO";
		public static final String INDICADOR_TROCA_REGISTRO = "OSIC_ICTROCAREGISTRO";
		public static final String ULTIMA_ALTERACAO = "OSIC_TMULTIMAALTERACAO";
	}
	
	public final class OrdemServicoInstalacaoCaixaProtecoesTipos {
		public final String ORDEM_SERVICO_EXECUCAO_OS = " INTEGER UNSIGNED NOT NULL";
		public final String HIDROMETRO_LOCAL_INSTALACAO = " INTEGER UNSIGNED NOT NULL";
		public final String HIDROMETRO_PROTECAO = " INTEGER UNSIGNED NOT NULL";
		public final String INDICADOR_TROCA_PROTECAO = " SMALLINT UNSIGNED NOT NULL";
		public final String INDICADOR_TROCA_REGISTRO = " SMALLINT UNSIGNED NOT NULL";
		public final String ULTIMA_ALTERACAO = " TIMESTAMP NOT NULL";
		
		private String[] tipos = new String[] 
				{
				
					ORDEM_SERVICO_EXECUCAO_OS,
					HIDROMETRO_LOCAL_INSTALACAO,
					HIDROMETRO_PROTECAO,
					INDICADOR_TROCA_PROTECAO,
					INDICADOR_TROCA_REGISTRO,
					ULTIMA_ALTERACAO
				
				};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	
	@Override
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(OrdemServicoInstalacaoCaixaProtecoes.ORDEM_SERVICO_EXECUCAO_OS, getOrdemServicoExecucaoOS().getId());
		values.put(OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_LOCAL_INSTALACAO, getHidrometroLocalInstalacao().getId());
		values.put(OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_PROTECAO, getHidrometroProtecao().getId());
		values.put(OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_PROTECAO, getIndicadorTrocaProtecao());
		values.put(OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_REGISTRO, getIndicadorTrocaRegistro());
		String dataString = Util.convertDateToDateStr(getUltimaAlteracao());
		values.put(OrdemServicoInstalacaoCaixaProtecoes.ULTIMA_ALTERACAO, dataString);
		return values;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<OrdemServicoInstalacaoCaixaProtecao> preencherObjetos(Cursor cursor) {		
			
		int ordemServicoExecucaoOs = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.ORDEM_SERVICO_EXECUCAO_OS);
		int hidrometroLocalHistalacao = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_LOCAL_INSTALACAO);
		int hidrometroProtecao = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.HIDROMETRO_PROTECAO);
		int indicadorTrocaProtecao = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_PROTECAO);
		int indicaodrTrocaRegistro = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.INDICADOR_TROCA_REGISTRO);
		int ultimaAlteracao = cursor.getColumnIndex(OrdemServicoInstalacaoCaixaProtecoes.ULTIMA_ALTERACAO);
		
		
		ArrayList<OrdemServicoInstalacaoCaixaProtecao> listaOrdemServicoInstalacaoCaixaProtecao
								= new ArrayList<OrdemServicoInstalacaoCaixaProtecao>();
		do {	
			OrdemServicoInstalacaoCaixaProtecao ordemServicoInstalacaoCaixaProtecao = new OrdemServicoInstalacaoCaixaProtecao();
			ordemServicoInstalacaoCaixaProtecao.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoExecucaoOs));
			ordemServicoInstalacaoCaixaProtecao.setHidrometroLocalInstalacao(cursor.getInt(hidrometroLocalHistalacao));
			ordemServicoInstalacaoCaixaProtecao.setHidrometroProtecao(cursor.getInt(hidrometroProtecao));
			ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaProtecao(cursor.getInt(indicadorTrocaProtecao));
			ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaRegistro(cursor.getInt(indicaodrTrocaRegistro));
			ordemServicoInstalacaoCaixaProtecao.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			listaOrdemServicoInstalacaoCaixaProtecao.add(ordemServicoInstalacaoCaixaProtecao);
			
		} while (cursor.moveToNext());
		
		return listaOrdemServicoInstalacaoCaixaProtecao;
	}

	private void insertFromFile(ArrayList<String> obj){
		setOrdemServicoExecucaoOS(Util.verificarNuloInt(obj.get(1)));
		setHidrometroLocalInstalacao(Util.verificarNuloInt(obj.get(2)));
		setHidrometroProtecao(Util.verificarNuloInt(obj.get(3)));
		setIndicadorTrocaProtecao(Util.verificarNuloInt(obj.get(3)));
		setIndicadorTrocaRegistro(Util.verificarNuloInt(obj.get(4)));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);
	}

	public OrdemServicoExecucaoOS getOrdemServicoExecucaoOS() {
		return ordemServicoExecucaoOS;
	}

	public void setOrdemServicoExecucaoOS(
			Integer idOrdemServico) {
		OrdemServicoExecucaoOS obj = new OrdemServicoExecucaoOS(idOrdemServico);
		this.ordemServicoExecucaoOS = obj;
	}

	public HidrometroLocalInstalacao getHidrometroLocalInstalacao() {
		return hidrometroLocalInstalacao;
	}

	public void setHidrometroLocalInstalacao(
			Integer idHidrometroLocalInstalacao) {
		HidrometroLocalInstalacao obj = new HidrometroLocalInstalacao(idHidrometroLocalInstalacao);
		this.hidrometroLocalInstalacao = obj;
	}

	public HidrometroProtecao getHidrometroProtecao() {
		return hidrometroProtecao;
	}

	public void setHidrometroProtecao(Integer idHidrometroProtecao) {
		HidrometroProtecao obj = new HidrometroProtecao(idHidrometroProtecao);
		this.hidrometroProtecao = obj;
	}

	public Integer getIndicadorTrocaProtecao() {
		return indicadorTrocaProtecao;
	}

	public void setIndicadorTrocaProtecao(Integer indicadorTrocaProtecao) {
		this.indicadorTrocaProtecao = indicadorTrocaProtecao;
	}

	public Integer getIndicadorTrocaRegistro() {
		return indicadorTrocaRegistro;
	}

	public void setIndicadorTrocaRegistro(Integer indicadorTrocaRegistro) {
		this.indicadorTrocaRegistro = indicadorTrocaRegistro;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
}
