package br.com.ipad.gsaneos.ui;

import java.io.File;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import br.com.ipad.gsaneos.bean.SistemaParametros;
import br.com.ipad.gsaneos.excecoes.FachadaException;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.repositorios.DBConnection;
import br.com.ipad.gsaneos.repositorios.RepositorioBasico;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;
import br.com.ipad.gsaneos.util.WebServerConexao;

public class DownloadArquivoActivity extends BaseActivity implements OnClickListener {

    ProgressDialog mProgressDialog;
    final KeyEvent event = new KeyEvent(KeyEvent.KEYCODE_BACK,
                                        KeyEvent.KEYCODE_BACK);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         if ( true ) {
//         String AppInst = ConstantesSistema.CAMINHO_GSANEOS+ "/GSANEOS.apk";
//         Intent intent = new Intent(Intent.ACTION_VIEW);
//         intent.setDataAndType(Uri.fromFile(new File(AppInst)),
//         "application/vnd.android.package-archive");
//         startActivity(intent);
//         }

        if ( !existeSistemaParametrosNoBancoDados() ) {
        	Fachada.setContext(this);
            mProgressDialog = new ProgressDialog(DownloadArquivoActivity.this) {

                @Override
                public void onBackPressed() {
                }

                @Override
                public boolean onSearchRequested() {
                    return false;
                }

            };
            
            
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setTitle(getString(R.string.lbl_downloading_file));
            mProgressDialog.setMessage(getString(R.string.lbl_loading_file));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIcon(R.drawable.transfer);       
            mProgressDialog.show();
            
            AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {

                    WebServerConexao downloadFile = new WebServerConexao(DownloadArquivoActivity.this);

                    try{
                    
	                    if (downloadFile.fileOperation(ConstantesSistema.DOWNLOAD_ARQUIVO, mProgressDialog)) {
	                    		
	                        File photosDir = new File(ConstantesSistema.CAMINHO_FOTOS);
	                        Util.apagarArquivos(photosDir);
	
	                        Intent i = new Intent(DownloadArquivoActivity.this,
	                                              LoginActivity.class);
	
	                        mProgressDialog.dismiss();
	                        DownloadArquivoActivity.this.startActivity(i);
	                        finish();
	                    } else {
	                        RepositorioBasico.getInstance().apagarBanco();
	                        mProgressDialog.dismiss();
	                    }
	
	                    return true;
                    }
                    
                    catch(Exception e){
                    	return false;
                    }
                }

                @Override
                protected void onPostExecute(Object result) {
                    mProgressDialog.dismiss();

                    // Informamos ao usuário que foi tudo certo
                    AlertDialog alertDialog = 
                    		new AlertDialog.Builder(DownloadArquivoActivity.this)
                    					.setTitle(getString(R.string.error_carregar_arquivo))
                                        .setMessage(R.string.str_conexao_falha_transferir_offline)
                                        .setIcon(R.drawable.tostart)
                                        .setCancelable(false)
                                        .setNeutralButton(ConstantesSistema.ALERT_OK,
                                                          new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog,int which) {
                                                                  dialog.dismiss();      
                                                              }
                                                          }).show();                                  
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent i = new Intent(DownloadArquivoActivity.this,
                                                  SelecionarArquivoActivity.class);

                            startActivityForResult(i, 1);
                            RepositorioBasico.getInstance().apagarBanco();
                            finish();
                        }
                    });

                }
            };

            taskDownloadFile.execute();

        } else {
        	Fachada.setContext(this);
            Intent intent = new Intent(this,
                                       LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public boolean onSearchRequested() {
    	RepositorioBasico.getInstance().apagarBanco();
        finish();
        return false;
    }
    
    private boolean existeSistemaParametrosNoBancoDados(){
		boolean retorno = false;
		try {

			   Fachada.setContext(DownloadArquivoActivity.this);
				//Instancia do objeto SIstema Parametros para ser utilizado no pesquisar generico
			   SistemaParametros sistemaParametros = Fachada.getInstance().obterSistemaParametros();
			   if(sistemaParametros != null)
				   return true;
			    
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, "Erro ao pesquisar SistemaParametros",fe);
		}
		
		return retorno;
	}

}
