package br.com.ipad.gsaneos.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import br.com.ipad.gsaneos.adapter.ApresentarRoteirosAdapter;
import br.com.ipad.gsaneos.bean.AgenteComercial;
import br.com.ipad.gsaneos.bean.Foto;
import br.com.ipad.gsaneos.bean.Gsaneos;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.SistemaParametros;
import br.com.ipad.gsaneos.excecoes.FachadaException;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.repositorios.RepositorioBasico;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.ExportBancoDados;
import br.com.ipad.gsaneos.util.Util;

/**
 * [UC1496] - Apresentar Roteiro para Execu��o de OS de Cobran�a.
 * 
 * @author Jonathan Marcos
 * @date 14/06/2013
 */
public class ApresentarRoteirosActivity extends BaseActivity implements IRetornoAsync{
	
	private ListView listViewOrdemServico;
	private Collection<TransmitirOnlineOSCobrancaAsync> colecaoTasks;
	private ApresentarRoteirosAdapter apresentarRoteirosAdapter;
	private int aux;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		colecaoTasks = new ArrayList<TransmitirOnlineOSCobrancaAsync>();
		apresentarTela();
		
	}

	private void apresentarTela() {
		setContentView(R.layout.apresentar_roteiros_activity);
		OrdemServicoExecucaoOS ordemServicoExecucaoOs = new OrdemServicoExecucaoOS();
		listViewOrdemServico = (ListView)findViewById(R.id.listaOrdemServicoOs);
		apresentarRoteirosAdapter = new ApresentarRoteirosAdapter(this, Fachada.getInstance().pesquisar(ordemServicoExecucaoOs));
		listViewOrdemServico.setAdapter(apresentarRoteirosAdapter);
		listViewOrdemServico.setOnItemClickListener(new OnItemClickListener() {
			
			  @Override
			  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				  OrdemServicoExecucaoOS ordemServExecOS = (OrdemServicoExecucaoOS)view.getTag();
				  List<Foto> fotos = Fachada.getInstance().pesquisarFotosNaoTransmitidas(ordemServExecOS.getId());
				  
				  //[FE0001] Verificar Situação da Ordem de Serviço
				  if(ordemServExecOS.getIndicadorTransmitido().compareTo(ConstantesSistema.SIM) != 0 || 
						  (fotos != null && fotos.size() > 0)){	  
					  
					if(ordemServExecOS.getIconeAtual() != null && 
							ordemServExecOS.getIconeAtual().intValue() == android.R.drawable.stat_sys_warning){
							
							//Atualizando o ícone
							atualizarStatusIconeOS(ordemServExecOS,android.R.drawable.stat_notify_sync);
							
							//Chamando a thread para reenviar
							TransmitirOnlineOSCobrancaAsync transmitir = new TransmitirOnlineOSCobrancaAsync(ApresentarRoteirosActivity.this,ordemServExecOS);
				    		transmitir.retornoAsync = ApresentarRoteirosActivity.this;
							transmitir.execute(getUsuarioLogado());
					}
					else if(ordemServExecOS.getIconeAtual().intValue() != android.R.drawable.stat_notify_sync){  
					  Intent i = new Intent(ApresentarRoteirosActivity.this, ApresentarRoteiroTabsActivity.class);
					  i.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServExecOS);
					  i.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, apresentarRoteirosAdapter.getCount());
					  startActivityForResult(i, 1);
					}
				  }
				  else{
					  Util.exibirMsgAlerta(R.string.erro_verificar_sit_os, ApresentarRoteirosActivity.this);
				  }
				  
			  }
			
		});
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
        	
        	OrdemServicoExecucaoOS os = (OrdemServicoExecucaoOS)data.getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
        	
        	//Atualizando o ícone
			atualizarStatusIconeOS(os,android.R.drawable.stat_notify_sync);
        	
    		TransmitirOnlineOSCobrancaAsync transmitir = new TransmitirOnlineOSCobrancaAsync(ApresentarRoteirosActivity.this,os);
    		transmitir.retornoAsync = this;
			transmitir.execute(getUsuarioLogado());
			colecaoTasks.add(transmitir);
			
        }
        
    } 
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add(0, 1, 0, R.string.string_finalizar_roteiro);
        menuItem.setIcon(R.drawable.finalizar_roteiro);
        
        menuItem = menu.add(0, 2, 0, R.string.str_menu_exportar_bd_legenda);
        menuItem.setIcon(R.drawable.export_database);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case 1:

                final CharSequence[] items = {
                        "Transmitir o arquivo online",
                        "Gerar o arquivo offline"
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Finalização de roteiro:");
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        aux = item;
                    }
                });
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Finalizar roteiro on line
                        if (aux == 0) {
                            Intent i = new Intent(ApresentarRoteirosActivity.this,
                                                  FinalizarRoteiroOSCobrancaActivity.class);
                            startActivity(i);
                            finish();
                        } else if (aux == 1) {
                        	// Finalizar roteiro off line
                            gerarArquivoOffline();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
                
                
            case 2:
            	
            	new ExportBancoDados().exportarBancoNovoNome();
                AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
                alert1.setMessage(getString(R.string.str_alert_exportar_bd));
                alert1.setCancelable(false);
                alert1.setNeutralButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alert1.show();
           
         }

        return super.onMenuItemSelected(featureId, item);
    }
    
    
    /**
     * Metodo responsavel por gerar o arquivo e as fotos de retorno.
     * 
     * @author Arthur Carvalho
     */
    private void gerarArquivoOffline() {

        // Pesquisa sistema parametros
        SistemaParametros sistemaParametros = Fachada.getInstance().obterSistemaParametros();
        AgenteComercial usuarioLogado = ((Gsaneos)getApplicationContext()).getUsuarioLogado();
        int qtdOsPendentes = Fachada.getInstance().pesquisarQuantidadeOSPendentes();

        // Gera nome do arquivo
        String dataFormatada = Util.convertDateToDateStrFile();
        String nomeArquivo = sistemaParametros.getIdArquivo() + "_" + dataFormatada;
        Integer indicadorFinalizado = ConstantesSistema.TIPO_GERACAO_ARQUIVO_COMPLETO;
        
        // Gera o arquivo de retorno
        File caminho = new File(ConstantesSistema.CAMINHO_RETORNO);
        if(!caminho.exists())
        	caminho.mkdirs();
        File arquivo = new File(caminho, nomeArquivo + ".txt");
        StringBuilder sb = new StringBuilder("");
        
        //Recuperando as ordens de serviço de execução
        List<OrdemServicoExecucaoOS> ordensServico = Fachada.getInstance().pesquisarOSExecutadasNaoTransmitidas();
        if(ordensServico == null || ordensServico.size() == 0){
        	sb.append(Fachada.getInstance().gerarArquivoRetorno(sistemaParametros,qtdOsPendentes));
        }
        
        else{
	        Iterator<OrdemServicoExecucaoOS> iteratorOrdensServico = ordensServico.iterator();
	        boolean primeiroAcesso = true;
	        
	        while (iteratorOrdensServico.hasNext()) {
	        	 OrdemServicoExecucaoOS os = (OrdemServicoExecucaoOS) iteratorOrdensServico.next();
	        	 sb.append(Fachada.getInstance().gerarArquivoRetorno(os.getId(), indicadorFinalizado, usuarioLogado,primeiroAcesso));
	        	 if(primeiroAcesso)
	        		 primeiroAcesso = false;
	       }
      }
      //-----------------------------------------------------------------------------------------------------
        
            // Escreve no arquivo de retorno criado
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(arquivo);
                fileOut.write(sb.toString().getBytes());
                fileOut.flush();
                fileOut.close();

                stopService(new Intent("SERVICE_REPLAY_PENDING"));
                
                // Em caso de sucesso, apagamos o banco e a pasta das fotos
                File diretorio = new File(ConstantesSistema.CAMINHO_FOTOS);
                Util.apagarArquivos(diretorio);

                RepositorioBasico.getInstance().apagarBanco();

                // Informamos ao usuário que foi tudo certo
                AlertDialog alertDialog = new AlertDialog
                								.Builder(ApresentarRoteirosActivity.this)
                									.setTitle("Rota Encerrada")
	                                                 .setMessage(getText(R.string.msg_gerar_offline_concluido))
	                                                 .setIcon(R.drawable.done)
	                                                 .setCancelable(false)
	                                                 .setNeutralButton(getText(R.string.str_ok),
	                                                       new DialogInterface.OnClickListener() {
	                                                           @Override
	                                                           public void onClick(
	                                                                   DialogInterface dialog,
	                                                                   int which) {
	                                                              
	                                                           }
	                                                       }) 
	                                                       .setOnKeyListener(new DialogInterface.OnKeyListener() {
	               										    @Override
	            										    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
	            										        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
	            										            return true; // Pretend we processed it
	            										        }
	            										        return false; // Any other keys are still processed as normal
	            										    }
	            										}).show();
                
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    	Intent i = new Intent(ApresentarRoteirosActivity.this,
                    							DownloadArquivoActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            } catch (FileNotFoundException e) {
                Log.e(ConstantesSistema.LOG_TAG, "Erro: FileNotFound.");
            } catch (IOException e) {
                Log.e(ConstantesSistema.LOG_TAG,  "Erro: IOException.");
            }

    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if(existeSistemaParametrosNoBancoDados())
    		apresentarTela();
    	else{
    		Intent i = new Intent(ApresentarRoteirosActivity.this,ApkActivity.class);
    		startActivity(i);
    		finish();
    	}
    }
    
    private boolean existeSistemaParametrosNoBancoDados(){
		boolean retorno = false;
		try {

			   Fachada.setContext(ApresentarRoteirosActivity.this);
				//Instancia do objeto SIstema Parametros para ser utilizado no pesquisar generico
			   SistemaParametros sistemaParametros = Fachada.getInstance().obterSistemaParametros();
			   if(sistemaParametros != null)
				   return true;
			    
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, "Erro ao pesquisar SistemaParametros",fe);
		}
		
		return retorno;
	}
    
    
    
    //Atualiza o ícone da lista e manda a lista recarregar
    private void atualizarStatusIconeOS(OrdemServicoExecucaoOS os, int idDrawable){
    	os.setIconeAtual(idDrawable);
        Fachada.getInstance().atualizar(os, os.getId());
        this.apresentarRoteirosAdapter.atualizarLista(Fachada.getInstance().pesquisar(new OrdemServicoExecucaoOS()));
    	
    }
    
    public void removerTask(Integer idOS){
    	Iterator it = colecaoTasks.iterator();
    	while(it.hasNext()){
    		TransmitirOnlineOSCobrancaAsync task = (TransmitirOnlineOSCobrancaAsync)it.next();
    		if(task.getOrdemServicoExecucaoOS().getId().compareTo(idOS) == 0){
    			it.remove();
    			break;
    		}
    	}
    }
    

	@Override
	public void finalizarProcesso(HashMap<String, Object> result) {
		Integer msg = (Integer)result.get("retorno"); 	
		OrdemServicoExecucaoOS os = (OrdemServicoExecucaoOS)result.get(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		switch(msg){
			case ConstantesSistema.SUCESSO:
				atualizarStatusIconeOS(os,R.drawable.done);
				break;
			case ConstantesSistema.ERROR_GENERICO:
				atualizarStatusIconeOS(os,android.R.drawable.stat_sys_warning);
				break;
			default:
				atualizarStatusIconeOS(os,android.R.drawable.stat_sys_warning);
				break;
		}
		removerTask(os.getId());
	}
    
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		//Cancelando as tasks pendentes
		if(colecaoTasks != null && !colecaoTasks.isEmpty()){
			Iterator it = colecaoTasks.iterator();
			while(it.hasNext()){
				TransmitirOnlineOSCobrancaAsync task = (TransmitirOnlineOSCobrancaAsync)it.next();
				task.cancel(true);
				it.remove();
			}
		}
		
		//Atualizando a lista, caso esteja com status de sincronizando
		int start = this.listViewOrdemServico.getFirstVisiblePosition();
    	for(int i=start, j=this.listViewOrdemServico.getLastVisiblePosition();i<=j;i++){
    		OrdemServicoExecucaoOS osLista = (OrdemServicoExecucaoOS)this.listViewOrdemServico.getItemAtPosition(i);
    		if(osLista.getIconeAtual().compareTo(android.R.drawable.stat_notify_sync) == 0){
    			osLista.setIconeAtual(android.R.drawable.stat_sys_warning);
    	        Fachada.getInstance().atualizar(osLista, osLista.getId());
    		} 		
    	}
	}
	
}