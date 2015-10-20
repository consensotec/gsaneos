package br.com.ipad.gsaneos.ui;

import android.app.TabActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import br.com.ipad.gsaneos.fachada.Fachada;

/**
 * @author Hugo Azevedo
 */
public class BaseTabActivity extends TabActivity  implements TabHost.TabContentFactory{

	protected boolean execute = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       if(Fachada.getInstance().isOrientacaoLandscape(this)){
	    	   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	       }	        
	}

	@Override
	public View createTabContent(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, 1, 0, R.string.menu_photos);
        return super.onCreateOptionsMenu(menu);
    }
}
