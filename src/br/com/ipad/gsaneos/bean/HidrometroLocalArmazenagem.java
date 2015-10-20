package br.com.ipad.gsaneos.bean;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.ipad.gsaneos.util.Util;

public class HidrometroLocalArmazenagem extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "hidrometro_local_armaz";
	
	private Integer id;
	private String descricao;
	
	public HidrometroLocalArmazenagem(){}
	
	public HidrometroLocalArmazenagem(Integer id){
		this.id = id;
	}
	
	public HidrometroLocalArmazenagem(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	
	public static String[] COLUNAS = new String[] { HidrometroLocalArmazenagens.ID, HidrometroLocalArmazenagens.DESCRICAO};
	
	public String[] getColunas(){
		return COLUNAS;
	}	
	
	public static final class HidrometroLocalArmazenagens implements BaseColumns {
		public static final String ID = "HILA_ID";
		public static final String DESCRICAO = "HILA_DSHIDRLOCALARMAZENAGEM";
	}
	
	public final class HidrometroLocalArmazenagensTipos {
		public final String ID = " INTEGER UNSIGNED NOT NULL";
		public final String DESCRICAO = " VARCHAR(45) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	@Override
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(HidrometroLocalArmazenagens.ID, getId());
		values.put(HidrometroLocalArmazenagens.DESCRICAO, getDescricao());
		return values;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<HidrometroLocalArmazenagem> preencherObjetos(Cursor cursor) {		
			
		int id = cursor.getColumnIndex(HidrometroLocalArmazenagens.ID);
		int descricao = cursor.getColumnIndex(HidrometroLocalArmazenagens.DESCRICAO);
		
		ArrayList<HidrometroLocalArmazenagem> listaHitrometroLocalArmazenagem = new ArrayList<HidrometroLocalArmazenagem>();
		do {	
			HidrometroLocalArmazenagem hidrometroLocalArmazenagem = new HidrometroLocalArmazenagem();
			hidrometroLocalArmazenagem.setId(cursor.getInt(id));
			hidrometroLocalArmazenagem.setDescricao(cursor.getString(descricao));
			
			listaHitrometroLocalArmazenagem.add(hidrometroLocalArmazenagem);
			
		} while (cursor.moveToNext());
		
		return listaHitrometroLocalArmazenagem;
	}

	private void insertFromFile(ArrayList<String> obj){
		setIdString(obj.get(1));	
		setDescricao(obj.get(2));
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
	
}
