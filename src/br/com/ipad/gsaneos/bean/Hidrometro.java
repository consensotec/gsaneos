package br.com.ipad.gsaneos.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.ipad.gsaneos.util.Util;

public class Hidrometro extends ObjetoBasico implements Serializable {
	
	public static String LIGACAO_AGUA = "LIGACAO AGUA";
	public static String POCO = "POCO";
	
	public static String MACROMEDIDOR = "MACROMEDIDOR";
	public static String MICROMEDIDOR = "MICROMEDIDOR";
	
	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "hidrometro";
	
	private Integer id;
	private String descricaoNumeroHidrometro;
	private String descricaoNumeroTombamento;
	private Integer indicadorHidrometroUtilizado;

	public Hidrometro(){}
	
	public Hidrometro(Integer id){
		this.id = id;
	}
	
	public Hidrometro(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	
	public static String[] COLUNAS = new String[]
			{
				Hidrometros.ID,
				Hidrometros.DESCRICAO_NUMERO_HIDROMETRO,
				Hidrometros.DESCRICAO_NUMERO_TOMBAMENTO,
				Hidrometros.INDICADOR_HIDROMETRO_UTILIZADO
			};
	
	public String[] getColunas(){
		return COLUNAS;
	}	
	
	public static final class Hidrometros implements BaseColumns {
		public static final String ID = "HIDR_ID";
		public static final String DESCRICAO_NUMERO_HIDROMETRO = "HIDR_NNHIDROMETRO";
		public static final String DESCRICAO_NUMERO_TOMBAMENTO = "HIDR_NNTOMBAMENTO";
		public static final String INDICADOR_HIDROMETRO_UTILIZADO = "HIDR_ICHIDROMETROUTILIZADO";
	}
	
	public final class HidrometrosTipos {
		public final String ID = " INTEGER UNSIGNED NOT NULL";
		public final String DESCRICAO_NUMERO_HIDROMETRO = " VARCHAR(11) NULL";
		public final String DESCRICAO_NUMERO_TOMBAMENTO = " VARCHAR(11) NULL";
		public static final String INDICADOR_HIDROMETRO_UTILIZADO = " SMALLINT UNSIGNED NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO_NUMERO_HIDROMETRO,DESCRICAO_NUMERO_TOMBAMENTO,INDICADOR_HIDROMETRO_UTILIZADO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

	
	@Override
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(Hidrometros.ID, getId());
		values.put(Hidrometros.DESCRICAO_NUMERO_HIDROMETRO, getDescricaoNumeroHidrometro());
		values.put(Hidrometros.DESCRICAO_NUMERO_TOMBAMENTO, getDescricaoNumeroTombamento());
		values.put(Hidrometros.INDICADOR_HIDROMETRO_UTILIZADO, getIndicadorHidrometroUtilizado());
		return values;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Hidrometro> preencherObjetos(Cursor cursor) {		
			
		int id = cursor.getColumnIndex(Hidrometros.ID);
		int descricaoHidrometro = cursor.getColumnIndex(Hidrometros.DESCRICAO_NUMERO_HIDROMETRO);
		int descricaoTombamento = cursor.getColumnIndex(Hidrometros.DESCRICAO_NUMERO_TOMBAMENTO);
		int indicadorHidrometroUtilizado = cursor.getColumnIndex(Hidrometros.INDICADOR_HIDROMETRO_UTILIZADO);
		
		ArrayList<Hidrometro> listaHitrometro = new ArrayList<Hidrometro>();
		do {	
			Hidrometro hidrometro = new Hidrometro();
			hidrometro.setId(cursor.getInt(id));
			hidrometro.setDescricaoNumeroHidrometro(cursor.getString(descricaoHidrometro));
			hidrometro.setDescricaoNumeroTombamento(cursor.getString(descricaoTombamento));
			hidrometro.setIndicadorHidrometroUtilizado(cursor.getInt(indicadorHidrometroUtilizado));
			
			listaHitrometro.add(hidrometro);
			
		} while (cursor.moveToNext());
		
		return listaHitrometro;
	}

	private void insertFromFile(ArrayList<String> obj){
		setIdString(obj.get(1));	
		
		if(obj.size()==4){
			
			setDescricaoNumeroHidrometro(null);
			setDescricaoNumeroTombamento(obj.get(3));
		
		}else{
			
			setDescricaoNumeroHidrometro(obj.get(2));
			setDescricaoNumeroTombamento(null);
		
		}
		
		setIndicadorHidrometroUtilizado(2);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setIdString(String id) {
		this.id = Util.verificarNuloInt(id);
	}
	
	public String getDescricaoNumeroHidrometro() {
		return descricaoNumeroHidrometro;
	}
	public void setDescricaoNumeroHidrometro(String descricaoNumeroHidrometro) {
		this.descricaoNumeroHidrometro = descricaoNumeroHidrometro;
	}
	
	public Integer getIndicadorHidrometroUtilizado() {
		return indicadorHidrometroUtilizado;
	}

	public void setIndicadorHidrometroUtilizado(Integer indicadorHidrometroUtilizado) {
		this.indicadorHidrometroUtilizado = indicadorHidrometroUtilizado;
	}

	public String getDescricaoNumeroTombamento() {
		return descricaoNumeroTombamento;
	}

	public void setDescricaoNumeroTombamento(String descricaoNumeroTombamento) {
		this.descricaoNumeroTombamento = descricaoNumeroTombamento;
	}

	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
}
