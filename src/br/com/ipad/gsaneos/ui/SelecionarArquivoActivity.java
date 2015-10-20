package br.com.ipad.gsaneos.ui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.ipad.gsaneos.adapter.FileAdapter;
import br.com.ipad.gsaneos.excecoes.RepositorioException;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.repositorios.CarregaBD;
import br.com.ipad.gsaneos.repositorios.RepositorioBasico;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;


public class SelecionarArquivoActivity extends BaseActivity {

    private File         dir;
    private List<String> filesList;
    private FileAdapter  fileAdapter;
    private String       path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_selector);
        Fachada.setContext(this);

        FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".txt");
            }
        };

        filesList = new ArrayList<String>();
        ListView listView = (ListView) findViewById(R.id.listFiles);
        dir = new File(ConstantesSistema.CAMINHO_OFFLINE);
        
        //Criar o diretório caso ele não exista
        if(!dir.exists()){
        	dir.mkdirs();
        }
        File[] files = dir.listFiles(filter);
        
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    filesList.add(file.getName());
                }
            }
        } else {
            AlertDialog alertDialog = 
            		new AlertDialog.Builder(SelecionarArquivoActivity.this)
            				.setTitle(getString(R.string.str_not_file_to_upload))
                            .setIcon(R.drawable.tostart)
                            .setCancelable(false)
                            .setNegativeButton(
                            		getString(R.string.str_sair),
                            		new DialogInterface.OnClickListener() {
	                                                           @Override
	                                                           public void onClick(
	                                                                   DialogInterface dialog,
	                                                                   int which) {
	
	                                                        	   dialog.dismiss();
	                                                           }
	                                                       }).show();
            	alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
	                @Override
	                public void onDismiss(DialogInterface dialog) {
	                	RepositorioBasico.getInstance().apagarBanco();
	                    finish();
	                }
	            });

        }

        fileAdapter = new FileAdapter(this,
                                      filesList);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parentView, View v, int position, long id) {

                v.setBackgroundColor(android.R.drawable.list_selector_background);

                // Guarda o path do diretorio selecionado
                path = (String) v.getTag();

                final ProgressDialog mProgressDialog = new ProgressDialog(SelecionarArquivoActivity.this) {

                    @Override
                    public void onBackPressed() {
                    }

                    @Override
                    public boolean onSearchRequested() {
                        return false;
                    }

                };
                
                AsyncTask<Object, Object, Boolean> taskDownloadFile = new AsyncTask<Object, Object, Boolean>() {
                	
                	public void onPreExecute() {
                		mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setTitle(getString(R.string.str_file_selector_loading));
                        mProgressDialog.setMessage(getString(R.string.lbl_loading_file));
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.setIcon(R.drawable.transfer);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.show();
                    }
                	
                    @Override
                    protected Boolean doInBackground(Object... arg0) {
                        InputStream is = null;
                        try {
                            is = new BufferedInputStream(new FileInputStream(dir + "/" + path));
                            
                            try {

                            	mProgressDialog.setMax(Util.getQuantidadeLinhasArquivo(is));
                                InputStreamReader inputReader = new InputStreamReader(is,"iso-8859-1");
                                BufferedReader reader = new BufferedReader(inputReader);
                                
                                String linha;
                                int linhaCount = 0;	
                                
                                while ((linha = reader.readLine()) != null) {
                                	CarregaBD.carregaLinhaParaBD(linha);
                            	    mProgressDialog.setProgress(linhaCount);
                            	    linhaCount++;
                                }
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                                return false;
                            } 
                            catch (RepositorioException e) {	
								e.printStackTrace();
								return false;
							}
                            
                            Intent it = new Intent(SelecionarArquivoActivity.this,
                                                   LoginActivity.class);
                            startActivity(it);
                            finish();
                            return true;
                            
                        } catch (FileNotFoundException e) {
                            Log.e(ConstantesSistema.CATEGORIA, e.getMessage());
                            return false;
                        }

                        
                    }
                    
                   @Override
                    protected void onPostExecute(final Boolean result){
                	   mProgressDialog.dismiss();
                    	if(!result.booleanValue()){
                    		Util.exibirMsgAlerta(R.string.error_carregar_arquivo, Fachada.getInstance().getContext());
                    		RepositorioBasico.getInstance().apagarBanco();
                    	}
                    }

                };

                taskDownloadFile.execute();
            }
        });

        listView.setAdapter(fileAdapter);
    }

    @Override
    public void onBackPressed() {
    	RepositorioBasico.getInstance().apagarBanco();
        finish();
    }

    @Override
    public boolean onSearchRequested() {
    	RepositorioBasico.getInstance().apagarBanco();
        finish();
        return false;
    }
}