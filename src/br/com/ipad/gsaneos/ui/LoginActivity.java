package br.com.ipad.gsaneos.ui;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.AgenteComercial;
import br.com.ipad.gsaneos.bean.Gsaneos;
import br.com.ipad.gsaneos.excecoes.FachadaException;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.util.Criptografia;
import br.com.ipad.gsaneos.util.Util;


public class LoginActivity extends Activity // implements SensorEventListener
{

    private TextView tvLogin;

    private TextView tvPassword;

    private AgenteComercial     agenteComercial;

    private Intent   intent;

    private Button   btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        Fachada.setContext(this);
        
        tvLogin = (TextView) findViewById(R.id.edtLogin);
        tvPassword = (TextView) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);   

        
        btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {			
				
				String usuario = tvLogin.getText().toString();
				String senha = tvPassword.getText().toString();
				String imei = Util.getIMEI(getApplicationContext());
				String mac = Util.getEnderecoMac(getApplicationContext());
				
				//Data do celular
				Date dataCelular = Util.getCurrentDateTime();
				
				
				//[FE0001] Verificar preenchimento do login
				if(usuario == null || usuario.trim().equals("")){
					Util.exibirMsgAlerta(R.string.erro_preenchimento_login,LoginActivity.this);
				}
				
				//[FE0002] Verificar preenchimento da senha
				else if(senha == null || senha.trim().equals("")){
					Util.exibirMsgAlerta(R.string.erro_preenchimento_senha,LoginActivity.this);
				}
				
				else{
					
					//[FE0004] Verificar login e senha informados
					try{
						agenteComercial = Fachada.getInstance().logarAgenteComercial(usuario,Criptografia.encode(senha));
					}
					catch(FachadaException e){
						Util.exibirMsgAlerta(R.string.erro_excecao,LoginActivity.this);
					}
					
					if(agenteComercial != null){
						//[FE0005] Verificar IMEI do dispositivo
						if(agenteComercial.getImei().equals(imei.trim()) || 
							(agenteComercial.getMac() != null && agenteComercial.getMac().equals(mac))){
							
							//[FE0003] Verificar data do celular
							//-------------------------------------------------------------------------------------------------------
							AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
							String mensagemAlerta = getString(R.string.data_atual_confirmar,Util.convertDateToDateOnlyStr(dataCelular));
							alert.setMessage(mensagemAlerta);
							
							//Botão SIM
							alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									 startService( new Intent("SERVICE_REPLAY_PENDING") );
									 
									//Agente logado no contexto do sistema
									Gsaneos gsaneos = (Gsaneos)getApplicationContext();
									gsaneos.setUsuarioLogado(agenteComercial);
									 
									 
									intent = new Intent(LoginActivity.this,ApresentarRoteirosActivity.class);
									startActivity(intent);
									finish();
									//Toast.makeText(LoginActivity.this, "LOGOU!", Toast.LENGTH_SHORT).show();
								}
							});
							
							//Botão não
							alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent it = new Intent(Settings.ACTION_DATE_SETTINGS);
									startActivity(it);
								}
							});
							
							alert.show();
							//-------------------------------------------------------------------------------------------------------
						}
						else{
							Util.exibirMsgAlerta(R.string.erro_identificacao_invalida,LoginActivity.this);
						}
					}
					else{
						Util.exibirMsgAlerta(R.string.erro_usuario_senha_incorreto,LoginActivity.this);
					}
					
				}
			}
		});
        
    }

}