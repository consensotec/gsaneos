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

package br.com.ipad.gsaneos.repositorios;

import java.util.List;

import android.database.Cursor;
import br.com.ipad.gsaneos.bean.Foto;
import br.com.ipad.gsaneos.bean.Foto.Fotos;
import br.com.ipad.gsaneos.excecoes.RepositorioException;
import br.com.ipad.gsaneos.util.ConstantesSistema;


public class RepositorioFoto extends RepositorioBasico implements IRepositorioFoto {
	
	private static RepositorioFoto instancia;	
	
	public void resetarInstancia(){
		instancia = null;
	}

	public static RepositorioFoto getInstance(){
		if ( instancia == null ){
			instancia =  new RepositorioFoto();
		} 
		return instancia;
	}	
	
	/* (non-Javadoc)
	 * @see br.com.ipad.gsaneos.repositorios.IRepositorioFoto#pesquisarFotosNaoTransmitidas(java.lang.Integer)
	 */
	@Override
	public List<Foto> pesquisarFotosNaoTransmitidas(Integer idOS) throws RepositorioException {
		
        Cursor cursor = null;
        List<Foto> fotos = null;
        Foto foto = new Foto();
        String selecao;
        String[] selecaoArgs;
        
        if(idOS != null){
        	selecao = Fotos.ORDEM_SERVICO_EXECUCAO_OS + "=? AND " +Fotos.INDICADOR_TRANSMITIDO + "=?";
        	selecaoArgs = new String[] {idOS.toString(),ConstantesSistema.INDICADOR_NAO_TRANSMITIDO+""};
        }
        else{
        	selecao = Fotos.INDICADOR_TRANSMITIDO + "=?";
	        selecaoArgs = new String[] {ConstantesSistema.INDICADOR_NAO_TRANSMITIDO+""};
        }
        

        try {
            	cursor = db.query(foto.getNomeTabela(),
            					  foto.getColunas(),
                                         selecao,
                                         selecaoArgs,
                                         null,
                                         null,
                                         null,
                                         null);
            
            if(cursor.moveToNext())
            	fotos = foto.preencherObjetos(cursor);	
            	
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return fotos;
    }

	/**
	 * [UC1510] Registrar Fotos de OS de Cobrança
	 * 
	 * @author Mariana Victor
	 * @date 27/06/2013
	 */
	public Foto pesquisarFoto(Integer idOrdemServico, Integer idTipoFoto)
			throws RepositorioException {

		Cursor cursor = null;
		Foto foto = new Foto();
		
		String selecao = Fotos.ORDEM_SERVICO_EXECUCAO_OS + "=? AND " 
				+ Fotos.FOTO_TIPO + "=?";
		
		String[] selecaoArgs = new String[] {
				idOrdemServico.toString(),
				idTipoFoto.toString()};

		try {
			cursor = db.query(foto.getNomeTabela(),
					foto.getColunas(),
					selecao,
					selecaoArgs,
					null,
					null,
					null,
					null);

			if (cursor.moveToFirst()) {
				int idIndex = cursor.getColumnIndex(Fotos.ID);
				int ordemServicoIndex = cursor.getColumnIndex(Fotos.ORDEM_SERVICO_EXECUCAO_OS);
				int fotoTipoIndex = cursor.getColumnIndex(Fotos.FOTO_TIPO);
				int caminhoIndex = cursor.getColumnIndex(Fotos.CAMINHO);
				int indicadorTransmiticoIndex = cursor.getColumnIndex(Fotos.INDICADOR_TRANSMITIDO);
				int ultimaAlteracaoIndex = cursor.getColumnIndex(Fotos.ULTIMA_ALTERACAO);

				foto.setId(cursor.getInt(idIndex));
				foto.setOrdemServicoExecucaoOS(cursor.getInt(ordemServicoIndex));
				foto.setFotoTipo(cursor.getInt(fotoTipoIndex));
				foto.setCaminho(cursor.getString(caminhoIndex));
				foto.setIndicadorTransmitido(cursor.getInt(indicadorTransmiticoIndex));
				foto.setUltimaAlteracao(cursor.getString(ultimaAlteracaoIndex));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return foto;
	}

	/**
	 * [UC1508] Manter Dados Aba Supressão de Ramal
	 * 
	 * @author Mariana Victor
	 * @date 28/06/2013
	 */
	public boolean verificarFotosInformadas(Integer idOrdemServico)
			throws RepositorioException {

		Cursor cursor = null;
		boolean retorno = false;
		
		String selecao = Fotos.ORDEM_SERVICO_EXECUCAO_OS + " = ? ";

		String[] selecaoArgs = new String[] {
				idOrdemServico.toString()};
		
		try {
			
			cursor = db.query(Foto.NOME_TABELA,
					Foto.colunas,
					selecao,
					selecaoArgs,
					null,
					null,
					null,
					null);
			
			Integer quantidade = cursor.getCount();
			
			if (quantidade != null 
					&& quantidade.compareTo(Integer.valueOf("4")) == 0) {
				retorno = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return retorno;
	}

	
}