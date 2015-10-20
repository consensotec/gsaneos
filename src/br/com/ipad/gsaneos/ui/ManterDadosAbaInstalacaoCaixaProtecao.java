package br.com.ipad.gsaneos.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.ipad.gsaneos.adapter.AtendimentoMotivoEncerramentoAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroLocalInstalacaoAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroProtecaoAdapter;
import br.com.ipad.gsaneos.bean.AtendimentoMotivoEncerramento;
import br.com.ipad.gsaneos.bean.Hidrometro;
import br.com.ipad.gsaneos.bean.HidrometroLocalInstalacao;
import br.com.ipad.gsaneos.bean.HidrometroProtecao;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.OrdemServicoInstalacaoCaixaProtecao;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.ui.R.string;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

@SuppressLint("SimpleDateFormat")
public class ManterDadosAbaInstalacaoCaixaProtecao  extends BaseActivity {

	private OrdemServicoExecucaoOS ordemServicoExecucaoOs;
	private Integer quantidadeOSExecutadas;
	private Integer quantidadeOS;
	
	private Intent intent;

	private EditText tipoHidrometro;
	private EditText numeroHidrometro;
	private EditText tipoMedicao;
	private Spinner localInstalacao;
	private Spinner protecao;
	private RadioGroup grupoTrocaProtecao;
	private RadioButton simNaoTrocaProtecao;
	private RadioGroup grupoTrocaRegistro;
	private RadioButton simNaoTrocaRegistro;
	private RadioGroup grupoCavalete;
	private RadioButton comSemCavalete;
	private RadioButton comSemCavaleteAuxiliar;
	private Spinner motivoEncerramento;
	private TextView dataExecucao;
	private TextView horaExecucao;
	private EditText parecer;
	private Button botaoEncerrarOSInstalacaoCaixaProtecao;
	
	private TextView tipoHidrometroTextCaixa;
	private TextView numeroHidrometroTextCaixa;
	private TextView tipoMedicaoTextCaixa;
	private TextView localInstalacaoTextCaixa;
	private TextView protecaoTextCaixa;
	private TextView tipoProtecaoTextCaixa;
	private TextView tipoTrocaRegistro;
	private TextView caveleteTextCaixa;
	
	private SimpleDateFormat simpleDateFormat;
	private Date data;
	
	private HidrometroLocalInstalacao hidrometroLocalInstalacao;
	private HidrometroProtecao hidrometroProtecao;
	private AtendimentoMotivoEncerramento atendimentoMotivoEncerramento;
	
	private List<HidrometroLocalInstalacao> listaHidrometroLocalInstalacao;
	private List<HidrometroProtecao> listaHidrometroProtecao;
	private List<AtendimentoMotivoEncerramento> listaAtendimentoMotivoEncerramento;
	
	private AlertDialog alertaConfirmacaoEncerramentoOs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manter_dados_aba_instalacao_caixa_protecao);
	
		ordemServicoExecucaoOs = (OrdemServicoExecucaoOS) getIntent().getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		parecer = (EditText)findViewById(R.id.parecerInstalacaoCaixaProtecao);
		
		//TIPO HIDROMETRO
		tipoHidrometro = (EditText)findViewById(R.id.tipoHidrometroInstalacaoCaixaProtecao);
		if(ordemServicoExecucaoOs.getTipoHidrometro()!=null)
		{
			
			if(ordemServicoExecucaoOs.getTipoHidrometro().compareTo(ConstantesSistema.SIM)==0)
			{
				
				tipoHidrometro.setText(Hidrometro.MACROMEDIDOR.toString());
				
				/*
				 * MUDAR DESCRICAO PARA TOMBAMENTO HIDROMETRO
				 * POIS TEM COMO DEFAULT NUMERO HIDROMETRO
				 */
				numeroHidrometroTextCaixa = (EditText)findViewById(R.id.numeroHidrometroTextCaixa);
				numeroHidrometroTextCaixa.setText(string.string_tombamento_hidrometro);
				
			}else
			{
				
				tipoHidrometro.setText(Hidrometro.MICROMEDIDOR.toString());
				
			}
			
			//DESABILITA O COMPO
			tipoHidrometro.setEnabled(false);
		}
				
		//NUMERO HIDROMETRO
		numeroHidrometro = (EditText)findViewById(R.id.numeroHidrometroInstalacaoCaixaProtecao);
		if(ordemServicoExecucaoOs.getNumeroHidrometro()!=null)
		{
			
			numeroHidrometro.setText(ordemServicoExecucaoOs.getNumeroHidrometro().toString());
					
			//DESABILITA O CAMPO
			numeroHidrometro.setEnabled(false);
		}
		
		//TIPO MEDICAO
		tipoMedicao = (EditText)findViewById(R.id.tipoMedicaoInstalacaoCaixaProtecao);
		if(ordemServicoExecucaoOs.getTipoMedicao()!=null)
		{
			
			if(ordemServicoExecucaoOs.getTipoMedicao().compareTo(ConstantesSistema.SIM)==0)
			{
				
				tipoMedicao.setText(Hidrometro.LIGACAO_AGUA.toString());
				
			}else
			{
				
				tipoMedicao.setText(Hidrometro.POCO.toString());
				
			}
			
			//DESABILITA O COMPO
			tipoMedicao.setEnabled(false);
		}
		
		//HIDROMETRO LOCAL INSTALACAO
		localInstalacao = (Spinner)findViewById(R.id.hidrometroLocalInstalacaoCaixaProtecao);
		hidrometroLocalInstalacao = new HidrometroLocalInstalacao();
		listaHidrometroLocalInstalacao = Fachada.getInstance().pesquisar(hidrometroLocalInstalacao);
		HidrometroLocalInstalacaoAdapter hidrometroLocalInstalacaoAdapter = new HidrometroLocalInstalacaoAdapter(this, listaHidrometroLocalInstalacao);
		localInstalacao.setAdapter(hidrometroLocalInstalacaoAdapter);
		
		//HIDROMETRO PROTECAO
		protecao = (Spinner)findViewById(R.id.hidrometroProtecaoInstalacaoCaixaProtecao);
		hidrometroProtecao = new HidrometroProtecao();
		listaHidrometroProtecao = Fachada.getInstance().pesquisar(hidrometroProtecao);
		HidrometroProtecaoAdapter hidrometroProtecaoAdapter = new HidrometroProtecaoAdapter(this, listaHidrometroProtecao);
		protecao.setAdapter(hidrometroProtecaoAdapter);
		
		//INDICADOR TROCA PROTECAO
		grupoTrocaProtecao = (RadioGroup)findViewById(R.id.grupoTrocaProtecao);
		
		//INDICADOR TROCA REGISTRO
		grupoTrocaRegistro = (RadioGroup)findViewById(R.id.grupoTrocaRegistro);
		
		//INDICADOR CAVALETE
		grupoCavalete = (RadioGroup)findViewById(R.id.grupoCavalete);
		
		if(ordemServicoExecucaoOs.getIndicadorCavaleteHidrometro().compareTo(ConstantesSistema.SIM)==0)
		{
			comSemCavalete = (RadioButton)findViewById(R.id.comCavalete);
			comSemCavaleteAuxiliar = (RadioButton)findViewById(R.id.semCavalete);
			comSemCavalete.setChecked(true);
			comSemCavaleteAuxiliar.setEnabled(false);
		}else
		{
			
			comSemCavalete = (RadioButton)findViewById(R.id.semCavalete);
			comSemCavaleteAuxiliar = (RadioButton)findViewById(R.id.comCavalete);
			comSemCavalete.setChecked(true);
			comSemCavaleteAuxiliar.setEnabled(false);
			
		}
		
		//ATENDIMENTO MOTIVO ENCERRAMENTO
		motivoEncerramento = (Spinner)findViewById(R.id.atendimentoMotivoEncerramentoInstalacaoCaixaProtecao);
		atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
		listaAtendimentoMotivoEncerramento = Fachada.getInstance().pesquisar(atendimentoMotivoEncerramento);
		AtendimentoMotivoEncerramentoAdapter atendimentoMotivoEncerramentoAdapter = new AtendimentoMotivoEncerramentoAdapter(this,listaAtendimentoMotivoEncerramento);
		motivoEncerramento.setAdapter(atendimentoMotivoEncerramentoAdapter);
		
		//[FE0003] VERIFICA MOTIVO ENCERRAMENTO SELECIONADO
		motivoEncerramento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
				
				if (motivoEncerramento.getSelectedItem().toString() != null) {
					AtendimentoMotivoEncerramento motivoEncerramentoSelecionado = (AtendimentoMotivoEncerramento)
							motivoEncerramento.getAdapter().getItem(motivoEncerramento.getSelectedItemPosition());
					
					tipoHidrometroTextCaixa = (TextView)findViewById(R.id.tipoHidrometroTextCaixa);
					numeroHidrometroTextCaixa = (TextView)findViewById(R.id.numeroHidrometroTextCaixa);
					tipoMedicaoTextCaixa = (TextView)findViewById(R.id.tipoMedicaoTextCaixa);
					localInstalacaoTextCaixa = (TextView)findViewById(R.id.localInstalacaoTextCaixa);
					protecaoTextCaixa = (TextView)findViewById(R.id.protecaoTextCaixa);
					tipoProtecaoTextCaixa = (TextView)findViewById(R.id.tipoProtecaoTextCaixa);
					tipoTrocaRegistro = (TextView)findViewById(R.id.tipoTrocaRegistro);
					caveleteTextCaixa = (TextView)findViewById(R.id.caveleteTextCaixa);
					
					if (motivoEncerramentoSelecionado.getIndicadorExecucao()
							.compareTo(ConstantesSistema.NAO) == 0) 
					{
					
						tipoHidrometro.setVisibility(View.GONE);
						numeroHidrometro.setVisibility(View.GONE);
						tipoMedicao.setVisibility(View.GONE);
						localInstalacao.setVisibility(View.GONE);
						protecao.setVisibility(View.GONE);
						grupoTrocaProtecao.setVisibility(View.GONE);
						grupoTrocaRegistro.setVisibility(View.GONE);
						grupoCavalete.setVisibility(View.GONE);
						
						tipoHidrometroTextCaixa.setVisibility(View.GONE);
						numeroHidrometroTextCaixa.setVisibility(View.GONE);
						tipoMedicaoTextCaixa.setVisibility(View.GONE);
						localInstalacaoTextCaixa.setVisibility(View.GONE);
						protecaoTextCaixa.setVisibility(View.GONE);
						tipoProtecaoTextCaixa.setVisibility(View.GONE);
						tipoTrocaRegistro.setVisibility(View.GONE);
						caveleteTextCaixa.setVisibility(View.GONE);
						
					}else
					{
						
						tipoHidrometro.setVisibility(View.VISIBLE);
						numeroHidrometro.setVisibility(View.VISIBLE);
						tipoMedicao.setVisibility(View.VISIBLE);
						localInstalacao.setVisibility(View.VISIBLE);
						protecao.setVisibility(View.VISIBLE);
						grupoTrocaProtecao.setVisibility(View.VISIBLE);
						grupoTrocaRegistro.setVisibility(View.VISIBLE);
						grupoCavalete.setVisibility(View.VISIBLE);
						
						tipoHidrometroTextCaixa.setVisibility(View.VISIBLE);
						numeroHidrometroTextCaixa.setVisibility(View.VISIBLE);
						tipoMedicaoTextCaixa.setVisibility(View.VISIBLE);
						localInstalacaoTextCaixa.setVisibility(View.VISIBLE);
						protecaoTextCaixa.setVisibility(View.VISIBLE);
						tipoProtecaoTextCaixa.setVisibility(View.VISIBLE);
						tipoTrocaRegistro.setVisibility(View.VISIBLE);
						caveleteTextCaixa.setVisibility(View.VISIBLE);
					
					}
				} 
				
	        }
	
	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub
	        }

		});
		
		
		//DATA E HORA DA EXECUCAO
		dataExecucao = (TextView)findViewById(R.id.dataExecucaoInstalacaoCaixaProtecao);
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		data = new Date();
		dataExecucao.setText(" "+simpleDateFormat.format(data));
		
		horaExecucao = (TextView)findViewById(R.id.horaExecucaoInstalacaoCaixaProtecao);
		simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		horaExecucao.setText(" "+simpleDateFormat.format(data));
		
		//BOTAO ENCERRAR ORDEM SERVICO INSTALACAO CAIXA PROTECAO
		botaoEncerrarOSInstalacaoCaixaProtecao = (Button)findViewById(R.botao.encerrarOSInstalacaoCaixaProtecao);
		botaoEncerrarOSInstalacaoCaixaProtecao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ManterDadosAbaInstalacaoCaixaProtecao.this);
				alert.setTitle("Encerrar OS");
				alert.setMessage("Confirma a conclusão da Ordem de Serviço?");
				
				alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Fachada fachada = Fachada.getInstance();
						
						//[FE0001] VERIFICAR CAMPOS OBRIGATORIOS
							
							//CASO 1
							if(motivoEncerramento.getSelectedItemPosition()>0)
							{
								
								atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento)motivoEncerramento.
										getItemAtPosition(motivoEncerramento.getSelectedItemPosition());
								
								//[FE0003] VERIFICAR MOTIVO ENCERRAMENTO SELECIONADO
								if(atendimentoMotivoEncerramento.getIndicadorExecucao().compareTo(ConstantesSistema.SIM)==0)
								{
									/*
									 * [FE0001] VERIFICAR CAMPOS OBRIGATORIOS
									 * CASO 2
									 * CASO 3
									 */
									
									//CASO 2
									if(localInstalacao.getSelectedItemPosition()>0)
									{
										
										//CASO 3
										if(protecao.getSelectedItemPosition()>0)
										{
											
											//[IT0008] PESQUISAR FOTOS
											if(fachada.verificarFotosInformadas(ordemServicoExecucaoOs.getId()))
											{
												
												//[IT0006] ATUALIZAR DADOS ENCERRAMENTO OS
												
												//MONTANDO OBJETO ORDEMSERVICOOS CASO 1
												atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento)motivoEncerramento.
														getItemAtPosition(motivoEncerramento.getSelectedItemPosition());
												ordemServicoExecucaoOs.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
												ordemServicoExecucaoOs.setDataEncerramento(data);
												ordemServicoExecucaoOs.setParecer(parecer.getText().toString());
												ordemServicoExecucaoOs.setUltimaAlteracao(new Date());
											
												fachada.atualizar(ordemServicoExecucaoOs, ordemServicoExecucaoOs.getId());
												
												//MONTANDO OBJETO ORDEMSERVICOINSTALACAOCAIXAPROTECAO
												OrdemServicoInstalacaoCaixaProtecao ordemServicoInstalacaoCaixaProtecao = new OrdemServicoInstalacaoCaixaProtecao();
												
												//ORDEM SERVICO
												ordemServicoInstalacaoCaixaProtecao.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs.getId());
												
												//HIDROMETRO LOCAL INSTALACAO
												hidrometroLocalInstalacao = (HidrometroLocalInstalacao)localInstalacao.
														getItemAtPosition(localInstalacao.getSelectedItemPosition());
												ordemServicoInstalacaoCaixaProtecao.setHidrometroLocalInstalacao(hidrometroLocalInstalacao.getId());
												
												
												//HIDROMETRO PROTECAO
												hidrometroProtecao = (HidrometroProtecao)protecao.
														getItemAtPosition(protecao.getSelectedItemPosition());
												ordemServicoInstalacaoCaixaProtecao.setHidrometroProtecao(hidrometroProtecao.getId());
														
												
												//INDICADOR TROCA PROTECAO
												int intTrocaProtecao  = grupoTrocaProtecao.getCheckedRadioButtonId();
												simNaoTrocaProtecao = (RadioButton)findViewById(intTrocaProtecao);
												if(simNaoTrocaProtecao.getText().toString().toUpperCase().equals("SIM"))
												{
													
													ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaProtecao(ConstantesSistema.SIM);
													
												}else
												{
													
													ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaProtecao(ConstantesSistema.NAO);
													
												}
												
												//INDICADOR TROCA REGISTRO
												int intTrocaRegistro = grupoTrocaRegistro.getCheckedRadioButtonId();
												simNaoTrocaRegistro = (RadioButton)findViewById(intTrocaRegistro);
												if(simNaoTrocaRegistro.getText().toString().toUpperCase().equals("SIM"))
												{
													
													ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaRegistro(ConstantesSistema.SIM);
													
												}else
												{
													
													ordemServicoInstalacaoCaixaProtecao.setIndicadorTrocaRegistro(ConstantesSistema.NAO);
													
												}
											
												//ULTIMA ALTERACAO
												simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
												ordemServicoInstalacaoCaixaProtecao.setUltimaAlteracao(simpleDateFormat.format(new Date()));
												
												fachada.inserir(ordemServicoInstalacaoCaixaProtecao);
												
												//RETORNA ORDEM SERVICO A SER TRANSMITIDA
												Intent intent = new Intent();
											    intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecucaoOs);
											    if (getParent() == null) {
											    	setResult(Activity.RESULT_OK, intent);
											    } else {
											    	getParent().setResult(Activity.RESULT_OK, intent);
											    }
												//Encerrando a tela
												finish();
												
											} else {
												
												Util.exibirMsgAlerta(R.string.erro_verificar_foto_informada, ManterDadosAbaInstalacaoCaixaProtecao.this);
											
											}
											
										} else {
											
											Util.exibirMsgAlerta(R.string.string_protecao_hidrometro_nao_informado, ManterDadosAbaInstalacaoCaixaProtecao.this);
										}
									} else {
										Util.exibirMsgAlerta(R.string.string_local_instalacao_nao_informado, ManterDadosAbaInstalacaoCaixaProtecao.this);
									}
								} else {
									//[IT0006] ATUALIZAR DADOS ENCERRAMENTO OS
									
									//MONTANDO OBJETO ORDEMSERVICOOS CASO 1
									atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento)motivoEncerramento.
											getItemAtPosition(motivoEncerramento.getSelectedItemPosition());
									ordemServicoExecucaoOs.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
									ordemServicoExecucaoOs.setDataEncerramento(data);
									ordemServicoExecucaoOs.setParecer(parecer.getText().toString());
									ordemServicoExecucaoOs.setUltimaAlteracao(new Date());

									fachada.atualizar(ordemServicoExecucaoOs, ordemServicoExecucaoOs.getId());
									
									//RETORNA ORDEM SERVICO A SER TRANSMITIDA
									Intent intent = new Intent();
								    intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecucaoOs);
								    if (getParent() == null) {
								    	setResult(Activity.RESULT_OK, intent);
								    }else {
								    	getParent().setResult(Activity.RESULT_OK, intent);
								    }
									//Encerrando a tela
									finish();
								}
							}else {
								
								Util.exibirMsgAlerta(R.string.alert_motivo_encerramento_nao_informado, ManterDadosAbaInstalacaoCaixaProtecao.this);
							
							}
					}
				});
				
				alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});

				alertaConfirmacaoEncerramentoOs = alert.create();
				alertaConfirmacaoEncerramentoOs.show();
	
			}
		});
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ManterDadosAbaInstalacaoCaixaProtecao.this,
                                    RegistrarFotosActivity.class);
        		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecucaoOs);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, quantidadeOS);
                break;
        }

        startActivity(intent);

        return super.onMenuItemSelected(featureId, item);
	}
}
