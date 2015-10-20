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

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import br.com.ipad.gsaneos.util.Util;


/**
 * [GSANAS] Basic Class - OrdemServicoSupressao
 * 
 * @author Fernanda Almeida
 * @since 13/04/2012
 */
public class ClienteInformadoEmCampo extends ObjetoBasico implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String NOME_TABELA = "cliente_informado_em_campo";
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOS;
	private OrgaoExpedidorRG orgaoExpeditorRG;
	private UnidadeFederacao unidadeFederacao;
	private String nomeCliente;
	private String cpf;
	private String rg;
	private String cnpj;
	private String dddTelefone;
	private String telefone;
	private String ramalTelefone;
	private Date ultimaAlteracao;
	
	public ClienteInformadoEmCampo(ArrayList<String> obj) {
		insertFromFile(obj);
	}
	public ClienteInformadoEmCampo() {}
	
	public ClienteInformadoEmCampo(Integer id) {
		setOrdemServicoExecucaoOS(id);
	}

	private static String[] colunas = new String[] {
		
		ClientesInformadosEmCampo.ORDEM_SERVICO_EXECUCAO_OS,
		ClientesInformadosEmCampo.ORGAO_EXPEDITOR_RG,
		ClientesInformadosEmCampo.UNIDADE_FEDERACAO,
		ClientesInformadosEmCampo.NOME_CLIENTE,
		ClientesInformadosEmCampo.CPF,
		ClientesInformadosEmCampo.RG,
		ClientesInformadosEmCampo.CNPJ,
		ClientesInformadosEmCampo.DDD_TELEFONE,
		ClientesInformadosEmCampo.TELEFONE,
		ClientesInformadosEmCampo.RAMAL_TELEFONE,
		ClientesInformadosEmCampo.ULTIMA_ALTERACAO
	};
	
	public String[] getColunas(){
		return colunas;
	}	
	
	public static final class ClientesInformadosEmCampo implements BaseColumns {
		public static final String ORDEM_SERVICO_EXECUCAO_OS = "ORSE_ID";
		public static final String ORGAO_EXPEDITOR_RG = "OERG_ID";
		public static final String UNIDADE_FEDERACAO = "UNFE_ID";
		public static final String NOME_CLIENTE = "CICM_NMCLIENTE";
		public static final String CPF = "CICM_DSCPF";
		public static final String RG = "CICM_DSRG";
		public static final String CNPJ = "CICM_DSCNPJ";
		public static final String DDD_TELEFONE = "CICM_DSDDDTELEFONE";
		public static final String TELEFONE = "CICM_DSTELEFONE";
		public static final String RAMAL_TELEFONE = "CICM_DSRAMALTELEFONE";
		public static final String ULTIMA_ALTERACAO = "CICM_TMULTIMAALTERACAO";
	}
	
	public final class ClientesInformadosEmCampoTipos {
		
		public final String ORDEM_SERVICO_EXECUCAO_OS = " INTEGER UNSIGNED NOT NULL";
		public final String ORGAO_EXPEDITOR_RG = " INTEGER UNSIGNED NULL";
		public final String UNIDADE_FEDERACAO = " INTEGER UNSIGNED NULL";
		public final String NOME_CLIENTE = " VARCHAR NULL";
		public final String CPF = " VARCHAR NULL";
		public final String RG = " VARCHAR NULL";
		public final String CNPJ = " VARCHAR NULL";
		public final String DDD_TELEFONE = " VARCHAR NULL";
		public final String TELEFONE = " VARCHAR NULL";
		public final String RAMAL_TELEFONE = " VARCHAR NULL";
		public final String ULTIMA_ALTERACAO = " TIMESTAMP NOT NULL";
		
		private String[] tipos = new String[] {
			ORDEM_SERVICO_EXECUCAO_OS, ORGAO_EXPEDITOR_RG, UNIDADE_FEDERACAO, NOME_CLIENTE,
			CPF, RG, CNPJ, DDD_TELEFONE, TELEFONE, RAMAL_TELEFONE, ULTIMA_ALTERACAO
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
			values.put(ClientesInformadosEmCampo.ORDEM_SERVICO_EXECUCAO_OS, getOrdemServicoExecucaoOS().getId());
		if(getOrgaoExpeditorRG() != null)
			values.put(ClientesInformadosEmCampo.ORGAO_EXPEDITOR_RG, getOrgaoExpeditorRG().getId());
		if(getUnidadeFederacao() != null)
			values.put(ClientesInformadosEmCampo.UNIDADE_FEDERACAO, getUnidadeFederacao().getId());
		
		values.put(ClientesInformadosEmCampo.NOME_CLIENTE, getNomeCliente());
		values.put(ClientesInformadosEmCampo.CPF, getCpf());
		values.put(ClientesInformadosEmCampo.RG, getRg());
		values.put(ClientesInformadosEmCampo.CNPJ, getCnpj());
		values.put(ClientesInformadosEmCampo.DDD_TELEFONE, getDddTelefone());
		values.put(ClientesInformadosEmCampo.TELEFONE, getTelefone());
		values.put(ClientesInformadosEmCampo.RAMAL_TELEFONE, getRamalTelefone());
		String dataStr = Util.convertDateToDateStr(Util.getCurrentDateTime());
		values.put(ClientesInformadosEmCampo.ULTIMA_ALTERACAO, dataStr);
		return values;
	}

	/**
	 * Método usado em selects, preenche uma lista de acordo com o que tem no banco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ClienteInformadoEmCampo> preencherObjetos(Cursor cursor) {		
			
		int ordemServicoExecucaoOS = cursor.getColumnIndex(ClientesInformadosEmCampo.ORDEM_SERVICO_EXECUCAO_OS);
		int orgaoExpeditorRg = cursor.getColumnIndex(ClientesInformadosEmCampo.ORGAO_EXPEDITOR_RG);
		int unidadeFederacao = cursor.getColumnIndex(ClientesInformadosEmCampo.UNIDADE_FEDERACAO);
		int nomeCliente = cursor.getColumnIndex(ClientesInformadosEmCampo.NOME_CLIENTE);
		int cpf = cursor.getColumnIndex(ClientesInformadosEmCampo.CPF);
		int rg = cursor.getColumnIndex(ClientesInformadosEmCampo.RG);
		int cnpj = cursor.getColumnIndex(ClientesInformadosEmCampo.CNPJ);
		int dddTelefone = cursor.getColumnIndex(ClientesInformadosEmCampo.DDD_TELEFONE);
		int telefone = cursor.getColumnIndex(ClientesInformadosEmCampo.TELEFONE);
		int ramalTelefone = cursor.getColumnIndex(ClientesInformadosEmCampo.RAMAL_TELEFONE);
		int ultimaAlteracao = cursor.getColumnIndex(ClientesInformadosEmCampo.ULTIMA_ALTERACAO);
		
		ArrayList<ClienteInformadoEmCampo> clientesInformadosEmCampo = new ArrayList<ClienteInformadoEmCampo>();
		do{
			ClienteInformadoEmCampo clienteInformadoEmCampo = new ClienteInformadoEmCampo();
			clienteInformadoEmCampo.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoExecucaoOS));
			clienteInformadoEmCampo.setOrgaoExpeditorRG(cursor.getInt(orgaoExpeditorRg));
			clienteInformadoEmCampo.setUnidadeFederacao(cursor.getInt(unidadeFederacao));
			clienteInformadoEmCampo.setNomeCliente(cursor.getString(nomeCliente));
			clienteInformadoEmCampo.setCpf(cursor.getString(cpf));
			clienteInformadoEmCampo.setRg(cursor.getString(rg));
			clienteInformadoEmCampo.setCnpj(cursor.getString(cnpj));
			clienteInformadoEmCampo.setDddTelefone(cursor.getString(dddTelefone));
			clienteInformadoEmCampo.setTelefone(cursor.getString(telefone));
			clienteInformadoEmCampo.setRamalTelefone(cursor.getString(ramalTelefone));
			clienteInformadoEmCampo.setUltimaAlteracao(cursor.getString(ultimaAlteracao));
			clientesInformadosEmCampo.add(clienteInformadoEmCampo);
		}while(cursor.moveToNext()); 
		
		return clientesInformadosEmCampo;
	}
	
	/**
	 * Seta objeto para ser inserido de acordo com os dados vindo do arquivo de ida
	 * @param obj
	 */
	private void insertFromFile(ArrayList<String> obj){
		setOrdemServicoExecucaoOS(obj.get(1));
		setOrgaoExpeditorRG(obj.get(2));
		setUnidadeFederacao(obj.get(3));
		setNomeCliente(obj.get(4));
		setCpf(obj.get(5));
		setRg(obj.get(6));
		setCnpj(obj.get(7));
		setDddTelefone(obj.get(8));
		setTelefone(obj.get(9));
		setRamalTelefone(obj.get(10));
		String date = Util.convertDateToDateStr(Util.getCurrentDateTime());	
		setUltimaAlteracao(date);			
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
	
	
	public OrgaoExpedidorRG getOrgaoExpeditorRG() {
		return orgaoExpeditorRG;
	}
	public void setOrgaoExpeditorRG(OrgaoExpedidorRG orgaoExpeditorRG) {
		this.orgaoExpeditorRG = orgaoExpeditorRG;
	}
	public void setOrgaoExpeditorRG(Integer id) {
		OrgaoExpedidorRG orgao = new OrgaoExpedidorRG(id);
		this.orgaoExpeditorRG = orgao;
	}
	public void setOrgaoExpeditorRG(String id) {
		OrgaoExpedidorRG orgao = new OrgaoExpedidorRG(Util.verificarNuloInt(id));
		this.orgaoExpeditorRG = orgao;
	}
	

	public UnidadeFederacao getUnidadeFederacao() {
		return unidadeFederacao;
	}
	public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao) {
		this.unidadeFederacao = unidadeFederacao;
	}
	public void setUnidadeFederacao(Integer id) {
		UnidadeFederacao uf = new UnidadeFederacao(id);
		this.unidadeFederacao = uf;
	}
	public void setUnidadeFederacao(String id) {
		UnidadeFederacao uf = new UnidadeFederacao(Util.verificarNuloInt(id));
		this.unidadeFederacao = uf;
	}
	
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getDddTelefone() {
		return dddTelefone;
	}
	public void setDddTelefone(String dddTelefone) {
		this.dddTelefone = dddTelefone;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getRamalTelefone() {
		return ramalTelefone;
	}
	public void setRamalTelefone(String ramalTelefone) {
		this.ramalTelefone = ramalTelefone;
	}
	
	public void setOrdemServicoExecucaoOS(String id) {
		OrdemServicoExecucaoOS os = new OrdemServicoExecucaoOS(Util.verificarNuloInt(id));
		this.ordemServicoExecucaoOS = os;
	}
	
	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}
	
	public void setUltimaAlteracao(String ultimaAlteracao) {
		this.ultimaAlteracao = Util.getData(ultimaAlteracao);
	}
	
	@Override
	public String getNomeTabela() {
		return NOME_TABELA;
	}
}