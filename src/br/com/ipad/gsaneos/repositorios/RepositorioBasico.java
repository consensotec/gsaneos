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

import java.io.File;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import br.com.ipad.gsaneos.bean.ObjetoBasico;
import br.com.ipad.gsaneos.controladores.ControladorBasico;
import br.com.ipad.gsaneos.controladores.ControladorTipoServico;
import br.com.ipad.gsaneos.excecoes.RepositorioException;
import br.com.ipad.gsaneos.ui.R;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.SQLiteHelper;

public class RepositorioBasico implements IRepositorioBasico {

	protected static Context context;
	private static RepositorioBasico instancia;


	
	private static final int VERSAO_BANCO = 1;

	protected static SQLiteDatabase db;
	private static SQLiteHelper dbHelper;

	
	private void abrirBanco() {

			try {			
				fecharBanco();
				
				if ( (!registrarBanco() || db == null)  
						|| (db != null && !db.isOpen()) ) {
					
					BDScript bDScript = new BDScript();
					
					dbHelper = new SQLiteHelper(context,ConstantesSistema.NOME_BANCO,VERSAO_BANCO,
												bDScript.obterScriptBanco(),null);
					if(dbHelper!=null){
						synchronized (dbHelper){
							db = dbHelper.getWritableDatabase();
						}
					}
				}
				
			} catch (RepositorioException e) {
				e.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
			}
		}

	public static void setContext(Context c) {
		context = c;
	}

	public void fecharBanco() throws RepositorioException {
		if (db != null) {
			try {
				db.close();
			} catch (SQLException sqe) {
				sqe.printStackTrace();
				Log.e(ConstantesSistema.CATEGORIA, sqe.getMessage());
				throw new RepositorioException(context.getResources().getString(R.string.db_erro));
			}
		}
		if (dbHelper != null) {
			dbHelper.close();
		}
	}

	protected RepositorioBasico() {
		super();

		if (db == null || (db != null && !db.isOpen())) {
			abrirBanco();
		}
	}

	public static RepositorioBasico getInstance() {
		if (instancia == null) {
			instancia = new RepositorioBasico();
		}
		return instancia;
	}

	public static boolean registrarBanco() {
		SQLiteDatabase registrarDb = null;

		try {
			registrarDb = SQLiteDatabase.openDatabase(ConstantesSistema.CAMINHO_BD + ConstantesSistema.NOME_BANCO, null, SQLiteDatabase.OPEN_READONLY);
			registrarDb.close();
		} catch (SQLiteException e) {
			e.printStackTrace();
			Log.d(ConstantesSistema.CATEGORIA, "RepositorioBasico.existeBanco(): Não");
		}

		return registrarDb != null ? true : false;
	}

	public void apagarBanco() {
		
		try {
			
			fecharBanco();

			instancia = null;
						
			File file = new File(ConstantesSistema.CAMINHO_BD+ConstantesSistema.NOME_BANCO+"-journal" );
			
			if ( file.exists() ){
				file.delete();
			}
			
			if (context.deleteDatabase(ConstantesSistema.NOME_BANCO)) {
				Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados deletado.");
			} else {
				Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados não deletado.");
			}

		} catch (RepositorioException e) {
			Log.d(ConstantesSistema.CATEGORIA, "apagarBanco(): Banco de dados não deletado.");
		} finally {
			db = null;
			dbHelper = null;
			CarregaBD.CONTADOR_IMOVEL = 0;
		}
	
	}

	public void resetarInstancias() {
		
		RepositorioTipoServico.getInstance().resetarInstancia();
				
		ControladorBasico.getInstance().resetarInstancia();
		ControladorTipoServico.getInstance().resetarInstancia();
	}
	
	public boolean verificarExistenciaBancoDeDados() {

		boolean retorno = false;	  

//        if(registrarBanco()){
//        	SistemaParametros.resetarInstancia();
//	    	if(SistemaParametros.getInstancia() != null && SistemaParametros.getInstancia().getIndicadorBancoCarregado().equals(ConstantesSistema.SIM)){
//	    		retorno = true;
//	    	}
//        }
	    return retorno;
	}	
	
	/**
	 * Atualiza todos os campos do objeto no banco de dados
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	@Override
	public void atualizar(ObjetoBasico objeto,Integer id) throws RepositorioException {
		ContentValues values = objeto.preencherValues();
		String where = objeto.getNameId() + "=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		
		try {
			db.update(objeto.getNomeTabela(), values, where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e(ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}		
	}
	
	/**
	 * Remover objeto do BD
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public void remover(ObjetoBasico objeto) throws RepositorioException {
		String _id = String.valueOf(objeto.getNameId());

		String where = objeto.getNameId() + "=?";
		String[] whereArgs = new String[] { _id };

		try {
			db.delete(objeto.getNomeTabela(), where, whereArgs);
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		}
	}
	
	/**
	 * Insere objeto no BD e retorna id gerado
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public long inserir(ObjetoBasico objeto) throws RepositorioException {
		try {
			long idObjetoInserido = ConstantesSistema.ERRO_INSERIR_REGISTRO_BD;
			ContentValues values = objeto.preencherValues();
			
			idObjetoInserido = db.insert(objeto.getNomeTabela(), null, values);
				
			if(idObjetoInserido!=ConstantesSistema.ERRO_INSERIR_REGISTRO_BD){
				return idObjetoInserido;
			}else{
				throw new RepositorioException(context.getResources().getString(
						R.string.db_erro_inserir_registro));
			}
			
		}catch (SQLException sqe) {
			sqe.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , sqe.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro_inserir_registro));
		}
	}
	
	/**
	 * Pesquisa objeto com base no id 
	 * Recebe como parametro objeto de tipo igual ao seu
	 * @author Amelia Pessoa
	 * @param objeto
	 * @throws RepositorioException
	 */
	public <T extends ObjetoBasico> T pesquisarPorId(Integer id, T objetoTipo) throws RepositorioException {
		Cursor cursor = null;
		ArrayList<T> colecao = null;

		try {
			cursor = db.query(objetoTipo.getNomeTabela(), objetoTipo.getColunas(), 
					objetoTipo.getNameId() + "=" + id , null,
					null, null, null, null);

			if (cursor.moveToFirst()) {
				colecao = objetoTipo.preencherObjetos(cursor); 
				if(colecao != null){
					return colecao.get(0);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , e.getMessage());
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * [UC1496] - Apresentar Roteiro para Execu��o de OS de Cobran�a.
	 * 
	 * @author Jonathan Marcos
	 * @date 14/06/2013
	 */
	public <T extends ObjetoBasico>  ArrayList<T> pesquisar(T objetoTipo) throws RepositorioException {
		Cursor cursor = null;
		try {
			cursor = db.query(objetoTipo.getNomeTabela(), objetoTipo.getColunas(), null, null,
					null, null, null, null);
			if (cursor.moveToFirst()) {
				return objetoTipo.preencherObjetos(cursor);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e( ConstantesSistema.CATEGORIA , "Erro ao pesquisar");
			throw new RepositorioException(context.getResources().getString(
					R.string.db_erro));
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * [UC1504] - Manter Dados Aba Cliente.
	 * 
	 * @author Jonathan Marcos
	 * @date 19/06/2013
	 */
	@Override
    public Cursor cursor(String id, String descricao,String descricaoSigla,String times, String nomeTabela) throws RepositorioException {
		String sql = null;
		if(descricaoSigla == null){
			 sql = "select " + id + " as _id," + descricao + " as descricao," + times + " as times" 
		                + " from " + nomeTabela + " order by " + descricao;
		} else {
			 sql = "select " + id + " as _id," + descricao + " as descricao," +descricaoSigla+ " as descricaoSigla," + times + " as times" 
		                + " from " + nomeTabela + " order by " + descricao;
		}
       return db.rawQuery(sql, null);
    }
}