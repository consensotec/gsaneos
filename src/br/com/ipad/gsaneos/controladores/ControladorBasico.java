/*
* Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
* GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
* Copyright (C) <2007> 
* Adriano Britto Siqueira
* Alexandre Santos Cabral
* Ana Carolina Alves Breda
* Ana Maria Andrade Cavalcante
* Aryed Lins de Ara�jo
* Bruno Leonardo Rodrigues Barros
* Carlos Elmano Rodrigues Ferreira
* Cl�udio de Andrade Lira
* Denys Guimar�es Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fab�ola Gomes de Ara�jo
* Fernanda Vieira de Barros Almeida
* Fl�vio Leonardo Cavalcanti Cordeiro
* Francisco do Nascimento J�nior
* Homero Sampaio Cavalcanti
* Ivan S�rgio da Silva J�nior
* Jos� Edmar de Siqueira
* Jos� Thiago Ten�rio Lopes
* K�ssia Regina Silvestre de Albuquerque
* Leonardo Luiz Vieira da Silva
* M�rcio Roberto Batista da Silva
* Maria de F�tima Sampaio Leite
* Micaela Maria Coelho de Ara�jo
* Nelson Mendon�a de Carvalho
* Newton Morais e Silva
* Pedro Alexandre Santos da Silva Filho
* Rafael Corr�a Lima e Silva
* Rafael Francisco Pinto
* Rafael Koury Monteiro
* Rafael Palermo de Ara�jo
* Raphael Veras Rossiter
* Roberto Sobreira Barbalho
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* S�vio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Th�lio dos Santos Lins de Ara�jo
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
*
* Este programa � software livre; voc� pode redistribu�-lo e/ou
* modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
* publicada pela Free Software Foundation; vers�o 2 da
* Licen�a.
* Este programa � distribu�do na expectativa de ser �til, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
* COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
* PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
* detalhes.
* Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
* junto com este programa; se n�o, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
* 02111-1307, USA.
*/  
package br.com.ipad.gsaneos.controladores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import br.com.ipad.gsaneos.bean.ObjetoBasico;
import br.com.ipad.gsaneos.bean.SistemaParametros;
import br.com.ipad.gsaneos.excecoes.ControladorException;
import br.com.ipad.gsaneos.excecoes.RepositorioException;
import br.com.ipad.gsaneos.repositorios.CarregaBD;
import br.com.ipad.gsaneos.repositorios.RepositorioBasico;
import br.com.ipad.gsaneos.ui.R;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

public class ControladorBasico implements IControladorBasico {
	
	private static ControladorBasico instance;
	protected static Context context;

		
	public static Context getContext() {
		return context;
	}

	//Controladores			
	protected RepositorioBasico repositorioBasico;
	
	public void resetarInstancia(){
		instance = null;
	}
	
	protected ControladorBasico(){
		super();		
	}
	
	public static ControladorBasico getInstance(){
		if ( instance == null ){
			instance =  new ControladorBasico();				
		}		
		return instance;		
	}
	
	private RepositorioBasico getRepositorioBasico() {		
		if(repositorioBasico==null){
			repositorioBasico = RepositorioBasico.getInstance();
		}				
		return repositorioBasico;
	}

	public void setContext( Context ctx ) {
		context = ctx;
		RepositorioBasico.setContext(ctx);
//		instance.repositorioBasico = RepositorioBasico.getInstance();
	}
	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void atualizar(ObjetoBasico objeto,Integer id) throws ControladorException {
		try {
			getRepositorioBasico().atualizar(objeto,id);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	/**
	 * Remover objeto do BD
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws ControladorException {
		try {
			getRepositorioBasico().remover(objeto);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws ControladorException {
		try {
			return getRepositorioBasico().inserir(objeto);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws ControladorException {
		try {
			return getRepositorioBasico().pesquisarPorId(id, objetoTipo);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * [UC1496] - Apresentar Roteiro para Execução de OS de Cobrança.
	 * 
	 * @author Jonathan Marcos
	 * @date 14/06/2013
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws ControladorException {
		try {
			return getRepositorioBasico().pesquisar(objetoTipo);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , "Erro ao pesquisar");
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public boolean verificarExistenciaBancoDeDados() {
		return getRepositorioBasico().verificarExistenciaBancoDeDados();		
	}
	
	public void carregaLinhaParaBD(String line) throws ControladorException {
		try {
			CarregaBD.getInstance().carregaLinhaParaBD(line);
		} catch (RepositorioException ex) {
			ex.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , ex.getMessage());
			throw new ControladorException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	public void apagarBanco() {
		RepositorioBasico.getInstance().apagarBanco();
		
		//Apaga todas as subpasta de isc menos a sub pasta carregamento.
		File diretorio = new File(ConstantesSistema.CAMINHO_GSANEOS);                        	
		Util.deletarPastas( diretorio );
	}
	

	public static void copiaArquivoBkp(File arquivo, Long getTime){
		
	    if (arquivo.exists())  {
	    	File pastaBackup = new File(ConstantesSistema.CAMINHO_GSANEOS+"/backupRetorno");
			if(!pastaBackup.isDirectory()){
				pastaBackup.mkdirs();
			}
		    FileChannel sourceChannel = null;  
		     FileChannel destinoChannel = null;  
		     
		     File backup = new File(ConstantesSistema.CAMINHO_BACKUP_RETORNO+"/"+getTime+arquivo.getName());
		     	  
		     try {  
		         try {
		        	 sourceChannel = new FileInputStream(arquivo).getChannel();
				
			         destinoChannel = new FileOutputStream(backup).getChannel();  
			         sourceChannel.transferTo(0, sourceChannel.size(),  
		                 destinoChannel);  
		         } catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}  
				
		     } finally {  
		         if (sourceChannel != null && sourceChannel.isOpen())
					try {
						sourceChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		         if (destinoChannel != null && destinoChannel.isOpen())
					try {
						destinoChannel.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
		    
			}  
	    }
	}
	
	/**
	 * [UC1504] - Manter Dados Aba Cliente.
	 * 
	 * @author Jonathan Marcos
	 * @date 19/06/2013
	 */
	@Override
    public Cursor cursor(String id, String descricao,String descricaoSigla,String times, String nomeTabela) throws ControladorException {
        Cursor cursor = null;

        try {
            cursor = getRepositorioBasico().cursor(id, descricao,descricaoSigla,times,nomeTabela);
        } catch (RepositorioException re) {
            throw new ControladorException(re.getMessage());
        }

        return cursor;
    }
	
	
	
	public SistemaParametros obterSistemaParametros(){
		SistemaParametros retorno = new SistemaParametros();	
		try {
			List<SistemaParametros> listaSP = this.pesquisar(retorno);
			if(listaSP != null && listaSP.size() > 0)
				retorno = listaSP.get(0);
			else
				retorno = null;
		} catch (ControladorException e) {
			return null;
		}
		return retorno;
	}
	
}