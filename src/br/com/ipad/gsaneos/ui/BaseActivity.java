package br.com.ipad.gsaneos.ui;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import br.com.ipad.gsaneos.bean.AgenteComercial;
import br.com.ipad.gsaneos.bean.Gsaneos;
import br.com.ipad.gsaneos.fachada.Fachada;

/**
 * @author Hugo Azevedo
 */
public class BaseActivity extends Activity {

	protected boolean execute = true;
	private AgenteComercial usuarioLogado;
    
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	     savedInstanceState.putBoolean("execute", true);
	    super.onSaveInstanceState(savedInstanceState);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       this.usuarioLogado = ((Gsaneos)getApplicationContext()).getUsuarioLogado();

	       if(Fachada.getInstance().isOrientacaoLandscape(this)){
	    	   
	    	   if (savedInstanceState != null) {
	               super.onRestoreInstanceState(savedInstanceState);

	               boolean resultCode = savedInstanceState.getBoolean("execute");
	               savedInstanceState.remove("execute");
	               this.execute = resultCode;
	           } else {
	        	   execute = false;
	        	   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	           }
	       }	        
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}

	public AgenteComercial getUsuarioLogado() {
		return usuarioLogado;
	}
}
