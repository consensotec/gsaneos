/*
* Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
*
* This file is part of GSAN, an integrated service management system for Sanitation
*
* GSAN is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License.
*
* GSAN is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
*/

/*
* GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Araújo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Cláudio de Andrade Lira
* Denys Guimarães Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fabíola Gomes de Araújo
* Fernanda Vieira de Barros Almeida
* Flávio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento Júnior
* Homero Sampaio Cavalcanti
* Ivan Sérgio da Silva Júnior
* José Edmar de Siqueira
* José Thiago Tenório Lopes
* Kássia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* Márcio Roberto Batista da Silva
* Maria de Fátima Sampaio Leite
* Micaela Maria Coelho de Araújo
* Nelson Mendonça de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corrêa Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Araújo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* Sávio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Thúlio dos Santos Lins de Araújo
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
*
* Este programa é software livre; você pode redistribuí-lo e/ou
* modificá-lo sob os termos de Licença Pública Geral GNU, conforme
* publicada pela Free Software Foundation; versão 2 da
* Licença.
* Este programa é distribuído na expectativa de ser útil, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia implícita de
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
* detalhes.
* Você deve ter recebido uma cópia da Licença Pública Geral GNU
* junto com este programa; se não, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/  

package br.com.ipad.gsaneos.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import br.com.ipad.gsaneos.util.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * [GSANAS] Basic Class - FotoTipo
 * 
 * @author Fernanda Almeida
 * @since 13/04/2012
 */
public class FotoTipo extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String NOME_TABELA = "foto_tipo";
	
	private Integer id;
	private String descricao;
	private Date ultimaAlteracao;	
	
	public FotoTipo(ArrayList<String> obj) {
		insertFromFile(obj);
	}

	public FotoTipo() {}
	
	public FotoTipo(Integer id) {
		setId(id);
	}
	
	public FotoTipo(String id) {
		setId(id);
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}	
	
	public void setId(String id) {
		this.id = Util.verificarNuloInt(id);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	private static String[] colunas = new String[] { FotosTipos.ID, FotosTipos.DESCRICAO,
		FotosTipos.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class FotosTipos implements BaseColumns {
		public static final String ID = "FOTP_ID";
		public static final String DESCRICAO = "FOTP_DSFOTOTIPO";
		public static final String ULTIMAALTERACAO = "FOTP_TMULTIMAALTERACAO";
	}
	
	public final class FotosTiposTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String DESCRICAO = " VARCHAR(255) NULL";
		public final String ULTIMAALTERACAO = " TIMESTAMP NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO,ULTIMAALTERACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
	
	/**
	 * Método usado em operações insert e update
	 */
	@Override
	public ContentValues preencherValues() {
		ContentValues values = new ContentValues();
		
		values.put(FotosTipos.ID, getId());
		values.put(FotosTipos.DESCRICAO, getDescricao());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(FotosTipos.ULTIMAALTERACAO, dataStr);
		return values;
	}

	/**
	 * Método usado em selects, preenche uma lista de acordo com o que tem no banco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<FotoTipo> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(FotosTipos.ID);
		int descricao = cursor.getColumnIndex(FotosTipos.DESCRICAO);
		int ultimaAlteracao = cursor.getColumnIndex(FotosTipos.ULTIMAALTERACAO);
		
		ArrayList<FotoTipo> fotosTipos = new ArrayList<FotoTipo>();
		do {	
			FotoTipo fotoTipo = new FotoTipo();
			fotoTipo.setId(cursor.getInt(codigo));
			fotoTipo.setDescricao(cursor.getString(descricao));
			fotoTipo.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			fotosTipos.add(fotoTipo);
			
		} while (cursor.moveToNext());
		
		return fotosTipos;
	}
	
	/**
	 * Seta objeto para ser inserido de acordo com os dados vindo do arquivo de ida
	 * @param obj
	 */
	private void insertFromFile(ArrayList<String> obj){
		setId(obj.get(1));	
		setDescricao(obj.get(2));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
	}
	
	public String toString(){
		return this.descricao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
}