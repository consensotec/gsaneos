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

import br.com.ipad.gsaneos.bean.OrdemServicoFiscalizacao.OrdensServicoFiscalizacao;
import br.com.ipad.gsaneos.excecoes.RepositorioException;
import br.com.ipad.gsaneos.repositorios.RepositorioOrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.util.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Mariana Victor
 * @since 23/10/2013
 */
public class DebitoOrdemServico extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String NOME_TABELA = "debito_ordem_servico";
	
	private Integer id;
	private String anoMesConta;
	private String dataVencimentoConta;
	private String situacaoConta;
	private String valorConta;
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOS;
	
	public DebitoOrdemServico(ArrayList<String> obj) {
		insertFromFile(obj);
	}

	public DebitoOrdemServico() {}
	
	public DebitoOrdemServico(Integer id) {
		setId(id);
	}

	public DebitoOrdemServico(String id) {
		setIdString(id);
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

	private static String[] colunas = new String[] { DebitosOrdemServico.ID, DebitosOrdemServico.ORDEM_SERVICO_EXECUCAO_OS, DebitosOrdemServico.ANO_MES_CONTA,
		DebitosOrdemServico.DATA_VENCIMENTO_CONTA, DebitosOrdemServico.SITUACAO_CONTA, DebitosOrdemServico.VALOR_CONTA};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class DebitosOrdemServico implements BaseColumns {
		public static final String ID = "DBOS_ID";
		public static final String ORDEM_SERVICO_EXECUCAO_OS = "ORSE_ID";
		public static final String ANO_MES_CONTA = "DBOS_AMCONTA";
		public static final String DATA_VENCIMENTO_CONTA = "DBOS_DTVENCIMENTOCONTA";
		public static final String SITUACAO_CONTA = "DBOS_DSSITUACAO_CONTA";
		public static final String VALOR_CONTA = "DBOS_VLCONTA";
	}
	
	public final class DebitosOrdemServicoTipos {
		public final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT ";
		public final String ORDEM_SERVICO_EXECUCAO_OS = " INTEGER UNSIGNED NOT NULL ";
		public final String ANO_MES_CONTA = " VARCHAR(7) NOT NULL ";
		public final String DATA_VENCIMENTO_CONTA = " VARCHAR(10) NOT NULL ";
		public final String SITUACAO_CONTA = " VARCHAR(30) NOT NULL ";
		public final String VALOR_CONTA = " VARCHAR(15) NOT NULL ";
		
		private String[] tipos = new String[] {ID, ORDEM_SERVICO_EXECUCAO_OS, ANO_MES_CONTA, 
				DATA_VENCIMENTO_CONTA, SITUACAO_CONTA, VALOR_CONTA};	
		
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

		values.put(DebitosOrdemServico.ID, getId());
		values.put(OrdensServicoFiscalizacao.ORDEM_SERVICO_EXECUCAO_OS, getOrdemServicoExecucaoOS().getId());
		values.put(DebitosOrdemServico.ANO_MES_CONTA, getAnoMesConta());
		values.put(DebitosOrdemServico.DATA_VENCIMENTO_CONTA, getDataVencimentoConta());
		values.put(DebitosOrdemServico.SITUACAO_CONTA, getSituacaoConta());
		values.put(DebitosOrdemServico.VALOR_CONTA, getValorConta());
		return values;
	}

	/**
	 * Método usado em selects, preenche uma lista de acordo com o que tem no banco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DebitoOrdemServico> preencherObjetos(Cursor cursor) {		

		int codigo = cursor.getColumnIndex(DebitosOrdemServico.ID);
		int ordemServicoExecucaoOS = cursor.getColumnIndex(DebitosOrdemServico.ORDEM_SERVICO_EXECUCAO_OS);
		int anoMesConta = cursor.getColumnIndex(DebitosOrdemServico.ANO_MES_CONTA);
		int dataVencimentoConta = cursor.getColumnIndex(DebitosOrdemServico.DATA_VENCIMENTO_CONTA);
		int situacaoConta = cursor.getColumnIndex(DebitosOrdemServico.SITUACAO_CONTA);
		int valorConta = cursor.getColumnIndex(DebitosOrdemServico.VALOR_CONTA);
		
		ArrayList<DebitoOrdemServico> debitosOrdemServico = new ArrayList<DebitoOrdemServico>();
		do {	
			DebitoOrdemServico debitoOrdemServico = new DebitoOrdemServico();
			debitoOrdemServico.setId(cursor.getInt(codigo));
			debitoOrdemServico.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoExecucaoOS));
			debitoOrdemServico.setAnoMesConta(cursor.getString(anoMesConta));
			debitoOrdemServico.setDataVencimentoConta(cursor.getString(dataVencimentoConta));
			debitoOrdemServico.setSituacaoConta(cursor.getString(situacaoConta));
			debitoOrdemServico.setValorConta(cursor.getString(valorConta));
			
			debitosOrdemServico.add(debitoOrdemServico);
			
		} while (cursor.moveToNext());
		
		return debitosOrdemServico;
	}
	
	/**
	 * Seta objeto para ser inserido de acordo com os dados vindo do arquivo de ida
	 * @param obj
	 */
	private void insertFromFile(ArrayList<String> obj){
		setOrdemServicoExecucaoOS(obj.get(1));	
		setAnoMesConta(obj.get(2));	
		setDataVencimentoConta(obj.get(3));	
		setSituacaoConta(obj.get(4));	
		setValorConta(obj.get(5));
	}
	
	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}

	public String getAnoMesConta() {
		return anoMesConta;
	}

	public void setAnoMesConta(String anoMesConta) {
		this.anoMesConta = anoMesConta;
	}

	public String getDataVencimentoConta() {
		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(String dataVencimentoConta) {
		this.dataVencimentoConta = dataVencimentoConta;
	}

	public String getSituacaoConta() {
		return situacaoConta;
	}

	public void setSituacaoConta(String situacaoConta) {
		this.situacaoConta = situacaoConta;
	}

	public String getValorConta() {
		return valorConta;
	}

	public void setValorConta(String valorConta) {
		this.valorConta = valorConta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public OrdemServicoExecucaoOS getOrdemServicoExecucaoOS() {
		return ordemServicoExecucaoOS;
	}
	
	public void setOrdemServicoExecucaoOS(
			OrdemServicoExecucaoOS ordemServicoExecucaoOS) {
		this.ordemServicoExecucaoOS = ordemServicoExecucaoOS;
	}
	
	public void setOrdemServicoExecucaoOS(Integer id) {
		try {
			OrdemServicoExecucaoOS os = RepositorioOrdemServicoExecucaoOS.getInstance()
					.pesquisarOrdemServicoExecucaoOS(id);
			this.ordemServicoExecucaoOS = os;
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}
	
	public void setOrdemServicoExecucaoOS(String id) {
		try {
			OrdemServicoExecucaoOS os = RepositorioOrdemServicoExecucaoOS.getInstance()
					.pesquisarOrdemServicoExecucaoOS(Util.verificarNuloInt(id));
			this.ordemServicoExecucaoOS = os;
		} catch (RepositorioException e) {
			e.printStackTrace();
		}
	}
	
	
}