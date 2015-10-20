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
 * [GSANAS] Basic Class - FiscalizacaoSituacao
 * 
 * @author Fernanda Almeida
 * @since 13/04/2012
 */
public class FiscalizacaoSituacao extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String NOME_TABELA = "fiscalizacao_situacao";
	
	private Integer id;
	private String descricao;
	private Date ultimaAlteracao;	
	
	public FiscalizacaoSituacao(ArrayList<String> obj) {
		insertFromFile(obj);
	}

	public FiscalizacaoSituacao() {}
	
	public FiscalizacaoSituacao(Integer id) {
		this.id = id;
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

	private static String[] colunas = new String[] { FiscalizacoesSituacoes.ID, FiscalizacoesSituacoes.DESCRICAO,
		FiscalizacoesSituacoes.ULTIMAALTERACAO};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class FiscalizacoesSituacoes implements BaseColumns {
		public static final String ID = "FZST_ID";
		public static final String DESCRICAO = "FZST_DSFISCALIZACAOSITUACAO";
		public static final String ULTIMAALTERACAO = "FZST_TMULTIMAALTERACAO";
	}
	
	public final class FiscalizacoesSituacoesTipos {
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
		
		values.put(FiscalizacoesSituacoes.ID, getId());
		values.put(FiscalizacoesSituacoes.DESCRICAO, getDescricao());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(FiscalizacoesSituacoes.ULTIMAALTERACAO, dataStr);
		return values;
	}

	/**
	 * Método usado em selects, preenche uma lista de acordo com o que tem no banco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<FiscalizacaoSituacao> preencherObjetos(Cursor cursor) {		
			
		int codigo = cursor.getColumnIndex(FiscalizacoesSituacoes.ID);
		int descricao = cursor.getColumnIndex(FiscalizacoesSituacoes.DESCRICAO);
		int ultimaAlteracao = cursor.getColumnIndex(FiscalizacoesSituacoes.ULTIMAALTERACAO);
		
		ArrayList<FiscalizacaoSituacao> fiscalizacoesSituacoes = new ArrayList<FiscalizacaoSituacao>();
		do {	
			FiscalizacaoSituacao fiscalizacaoSituacao = new FiscalizacaoSituacao();
			fiscalizacaoSituacao.setId(cursor.getInt(codigo));
			fiscalizacaoSituacao.setDescricao(cursor.getString(descricao));
			fiscalizacaoSituacao.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			fiscalizacoesSituacoes.add(fiscalizacaoSituacao);
			
		} while (cursor.moveToNext());
		
		return fiscalizacoesSituacoes;
	}
	
	/**
	 * Seta objeto para ser inserido de acordo com os dados vindo do arquivo de ida
	 * @param obj
	 */
	private void insertFromFile(ArrayList<String> obj){
		setIdString(obj.get(1));	
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