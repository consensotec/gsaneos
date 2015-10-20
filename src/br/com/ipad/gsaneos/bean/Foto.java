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
public class Foto extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "foto";
	
	private Integer id;
	private OrdemServicoExecucaoOS ordemServicoExecucaoOS;
	private FotoTipo fotoTipo;
	private String caminho;
	private Integer indicadorTransmitido;
	private Date ultimaAlteracao;
	
	
	
	public Foto(ArrayList<String> obj) {
		insertFromFile(obj);
	}

	public Foto() {}
	
	public Foto(Integer id) {
		this.id = id;
	}

	
	public static String[] colunas = new String[] { 
		Fotos.ID, Fotos.ORDEM_SERVICO_EXECUCAO_OS, Fotos.FOTO_TIPO, 
		Fotos.CAMINHO, Fotos.INDICADOR_TRANSMITIDO, 
		Fotos.ULTIMA_ALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class Fotos implements BaseColumns {
		public static final String ID = "FOTO_ID";
		public static final String ORDEM_SERVICO_EXECUCAO_OS = "ORSE_ID";
		public static final String FOTO_TIPO = "FOTP_ID";
		public static final String CAMINHO = "FOTO_PATH";
		public static final String INDICADOR_TRANSMITIDO = "FOTO_ICTRANMITIDO";
		public static final String ULTIMA_ALTERACAO = "FOTO_TMULTIMAALTERACAO";
	}
	
	public final class FotosTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String ORDEM_SERVICO_EXECUCAO_OS = " INTEGER UNSIGNED NOT NULL";
		public final String FOTO_TIPO = " INTEGER NOT NULL";
		public final String CAMINHO = " TEXT NULL";
		public final String INDICADOR_TRANSMITIDO = " SMALLINT UNSIGNED NOT NULL";
		public final String ULTIMA_ALTERACAO = " TIMESTAMP NOT NULL";
		
		private String[] tipos = new String[] {
				ID, ORDEM_SERVICO_EXECUCAO_OS, FOTO_TIPO, 
				CAMINHO, INDICADOR_TRANSMITIDO, ULTIMA_ALTERACAO
		};	
		
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
		
		if(getOrdemServicoExecucaoOS() != null)
			values.put(Fotos.ORDEM_SERVICO_EXECUCAO_OS,getOrdemServicoExecucaoOS().getId() );
		if(getFotoTipo() != null)
			values.put(Fotos.FOTO_TIPO, getFotoTipo().getId());

		values.put(Fotos.ID, getId());
		values.put(Fotos.CAMINHO, getCaminho());
		values.put(Fotos.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		values.put(Fotos.ULTIMA_ALTERACAO, Util.convertDateToDateStr(Util.getCurrentDateTime()));
		return values;
	}

	/**
	 * Método usado em selects, preenche uma lista de acordo com o que tem no banco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Foto> preencherObjetos(Cursor cursor) {		
			
		int id = cursor.getColumnIndex(Fotos.ID);
		int ordemServicoExecucaoOS = cursor.getColumnIndex(Fotos.ORDEM_SERVICO_EXECUCAO_OS);
		int fotoTipo = cursor.getColumnIndex(Fotos.FOTO_TIPO);
		int caminho = cursor.getColumnIndex(Fotos.CAMINHO);
		int indicadorTransmitido = cursor.getColumnIndex(Fotos.INDICADOR_TRANSMITIDO);
		int ultimaAlteracao = cursor.getColumnIndex(Fotos.ULTIMA_ALTERACAO);
		
		
		ArrayList<Foto> Fotos = new ArrayList<Foto>();
		do {	
			Foto Foto = new Foto();
			Foto.setId(cursor.getInt(id));
			Foto.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoExecucaoOS));
			Foto.setFotoTipo(cursor.getInt(fotoTipo));
			Foto.setCaminho(cursor.getString(caminho));
			Foto.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
			Foto.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			
			Fotos.add(Foto);
			
		} while (cursor.moveToNext());
		
		return Fotos;
	}
	
	/**
	 * Seta objeto para ser inserido de acordo com os dados vindo do arquivo de ida
	 * @param obj
	 */
	private void insertFromFile(ArrayList<String> obj){
		setId(obj.get(1));	
		setOrdemServicoExecucaoOS(obj.get(2));
		setFotoTipo(obj.get(3));
		setCaminho(obj.get(5));
		setIndicadorTransmitido(Util.verificarNuloInt(obj.get(8)));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = Util.verificarNuloInt(id);
	}

	public OrdemServicoExecucaoOS getOrdemServicoExecucaoOS() {
		return ordemServicoExecucaoOS;
	}

	public void setOrdemServicoExecucaoOS(
			OrdemServicoExecucaoOS ordemServicoExecucaoOS) {
		this.ordemServicoExecucaoOS = ordemServicoExecucaoOS;
	}
	
	public void setOrdemServicoExecucaoOS(Integer id) {
		OrdemServicoExecucaoOS os = new OrdemServicoExecucaoOS(id);
		this.ordemServicoExecucaoOS = os;
	}
	
	public void setOrdemServicoExecucaoOS(String id) {
		OrdemServicoExecucaoOS os = new OrdemServicoExecucaoOS(id);
		this.ordemServicoExecucaoOS = os;
	}

	public FotoTipo getFotoTipo() {
		return fotoTipo;
	}

	public void setFotoTipo(FotoTipo fotoTipo) {
		this.fotoTipo = fotoTipo;
	}
	
	public void setFotoTipo(Integer id) {
		FotoTipo foto = new FotoTipo(id);
		this.fotoTipo = foto;
	}
	
	public void setFotoTipo(String id) {
		FotoTipo foto = new FotoTipo(id);
		this.fotoTipo = foto;
	}
	
	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}
}