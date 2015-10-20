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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.ipad.gsaneos.adapter.AtendimentoMotivoEncerramentoAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroLocalInstalacaoAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroProtecaoAdapter;
import br.com.ipad.gsaneos.bean.AtendimentoMotivoEncerramento;
import br.com.ipad.gsaneos.bean.HidrometroLocalInstalacao;
import br.com.ipad.gsaneos.bean.HidrometroProtecao;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.OrdemServicoRemocaoHidrometro;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.ui.R.string;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

@SuppressLint("SimpleDateFormat")
public class ManterDadosAbaRemocaoHidrometroActivity extends BaseActivity {

	private static final int SUCESSO = 0;
	private static final int MOTIVO_NAO_INFORMADO = 1;
	private static final int LOCAL_NAO_INFORMADO = 2;
	private static final int PROTECAO_NAO_INFORMADA = 3;
	
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOs;
	private Integer quantidadeOSExecutadas;
	private Integer quantidadeOS;
	
	private TextView tvTipoHidrometroAbaRemoverHidr;
	private TextView tvNumeroHidrometroAbaRemoverHidr;
	private TextView tvTipoMedicaoAbaRemoverHidr;
	private TextView tvLocalInstalacaoAbaRemoverHidr;
	private TextView tvProtecaoHidrAbaRemoverHidr;
	private TextView tvIcCavaleteAbaRemoverHidr;
	private EditText tipoHidrometro;
	private EditText numeroHidrometro;
	private EditText tipoMedicao;
	private RadioGroup icCavalete;
	private TextView dataExecucao;
	private TextView horaExecucao;
	private EditText parecer;
	private Button botaoEncerrarOS;
	
	private AtendimentoMotivoEncerramento motivoEncerramento;
	private HidrometroLocalInstalacao localInstalacaoHidr;
	private HidrometroProtecao protecaoHidr;
	
	private AlertDialog alertaConfirmacaoEncerramentoOs;
	private Spinner motivoEncerramentoSpinner;
	private Spinner localInstalacaoHidrSpinner;
	private Spinner protecaoHidrSpinner;
	private Date data;
	private SimpleDateFormat simpleDateFormat;
	HidrometroLocalInstalacaoAdapter localInstalacaoAdapter;
	AtendimentoMotivoEncerramentoAdapter motivoEncerramentoAdapter;
	HidrometroProtecaoAdapter protecaoHidrAdapter;
	
	private Intent intent;
	private List<AtendimentoMotivoEncerramento> listaMotivoEncerramento;
	private List<HidrometroLocalInstalacao> listaLocalInstalacaoHidr;
	private List<HidrometroProtecao> listaProtecaoHidr;

	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manter_dados_aba_remocao_hidrometro_activity);
		ordemServicoExecucaoOs = (OrdemServicoExecucaoOS) getIntent().getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		tvTipoHidrometroAbaRemoverHidr = (TextView)findViewById(R.id.tvTipoHidrometroAbaRemoverHidr);
		tvNumeroHidrometroAbaRemoverHidr = (TextView)findViewById(R.id.tvNumeroHidrometroAbaRemoverHidr);
		tvTipoMedicaoAbaRemoverHidr = (TextView)findViewById(R.id.tvTipoMedicaoAbaRemoverHidr);
		tvLocalInstalacaoAbaRemoverHidr = (TextView)findViewById(R.id.tvLocalInstalacaoAbaRemoverHidr);
		tvProtecaoHidrAbaRemoverHidr = (TextView)findViewById(R.id.tvProtecaoHidrAbaRemoverHidr);
		tvIcCavaleteAbaRemoverHidr = (TextView)findViewById(R.id.tvIcCavaleteAbaRemoverHidr);
		
		//Local de Instalação
		localInstalacaoHidr = new HidrometroLocalInstalacao();
		localInstalacaoHidrSpinner = (Spinner) findViewById(R.id.localInstalacaoHidrSpinner);
		listaLocalInstalacaoHidr = Fachada.getInstance().pesquisar(localInstalacaoHidr);
		localInstalacaoAdapter = new HidrometroLocalInstalacaoAdapter(this,listaLocalInstalacaoHidr);
		localInstalacaoHidrSpinner.setAdapter(localInstalacaoAdapter);
		
		//Proteção
		protecaoHidr = new HidrometroProtecao();
		protecaoHidrSpinner = (Spinner) findViewById(R.id.protecaoHidrSpinner);
		listaProtecaoHidr = Fachada.getInstance().pesquisar(protecaoHidr);
		protecaoHidrAdapter = new HidrometroProtecaoAdapter(this,listaProtecaoHidr);
		protecaoHidrSpinner.setAdapter(protecaoHidrAdapter);
		
		//Motivo Encerramento
		motivoEncerramento = new AtendimentoMotivoEncerramento();
		motivoEncerramentoSpinner = (Spinner) findViewById(R.id.motivoEncerramentoRemocaoHidr);
		listaMotivoEncerramento = Fachada.getInstance().pesquisar(motivoEncerramento);
		motivoEncerramentoAdapter = new AtendimentoMotivoEncerramentoAdapter(this, listaMotivoEncerramento);
		motivoEncerramentoSpinner.setAdapter(motivoEncerramentoAdapter);
		
		icCavalete = (RadioGroup)findViewById(R.id.icCavalete);
		tipoHidrometro = (EditText) findViewById(R.id.tipoHidrometroAbaRemoverHidr);
		numeroHidrometro = (EditText) findViewById(R.id.numeroHidrometroAbaRemoverHidr);
		tipoMedicao = (EditText) findViewById(R.id.tipoMedicaoAbaRemoverHidr);
		
		//[IT0001] Obter Dados do Hidrômetro
		//-----------------------------------------------------------------------------------
		//1.1. Tipo de Medição
		//1.1. Caso = 1 (exibir "Ligação Água"); Caso Contrário = 2 (exibir "Poço")
		if(ordemServicoExecucaoOs.getTipoMedicao().compareTo(Integer.parseInt("1")) == 0)
			tipoMedicao.setText("Ligação Água");
		else
			tipoMedicao.setText("Poço");
		
		//1.2. Tipo de Hidrômetro
		//1.2. Caso = 1 (exibir "Macromedidor"); Caso Contrário = 2 (exibir "Micromedidor")
		if(ordemServicoExecucaoOs.getTipoHidrometro().compareTo(Integer.parseInt("1")) == 0){
			tipoHidrometro.setText("Macromedidor");
		
			/*
			 * MUDA DESCRICAO DO NUMERO DO HIDROMETRO PARA 
			 * TOMBAMENTO HIDROMETRO
			 */
			tvNumeroHidrometroAbaRemoverHidr.setText(string.string_tombamento_hidrometro);
		
		}else{
			
			tipoHidrometro.setText("Micromedidor");
		}
		
		//1.3. Numero do Hidrômetro
		numeroHidrometro.setText(ordemServicoExecucaoOs.getNumeroHidrometro());
		
		//1.4. Local de Instalação do Hidrômetro
		localInstalacaoHidrSpinner.setSelection(localInstalacaoAdapter
				.getPosition(ordemServicoExecucaoOs.getDescricaoLocalHistalacaoHidrometro()));
		
		//1.5. Proteção do Hidrômetro
		protecaoHidrSpinner.setSelection(protecaoHidrAdapter
				.getPosition(ordemServicoExecucaoOs.getDescricaoHidrometroProtecao()));
		
		//1.6.  Cavalete
		if(ordemServicoExecucaoOs.getIndicadorCavaleteHidrometro().compareTo(ConstantesSistema.SIM) == 0){
			icCavalete.check(R.id.icCavaleteCom);
		}
		else{
			icCavalete.check(R.id.icCavaleteSem);
		}
			
		
		//-----------------------------------------------------------------------------------
		data = new Date();
		dataExecucao = (TextView)findViewById(R.id.dataExecucaoFiscalizacao);
		simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dataExecucao.setText(" "+simpleDateFormat.format(data));
		
		horaExecucao = (TextView)findViewById(R.id.horaExecucaoFiscalizacao);
		simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		horaExecucao.setText(" "+simpleDateFormat.format(data));
		
		
		motivoEncerramentoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        @Override
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
				
				if (motivoEncerramentoSpinner.getSelectedItem().toString() != null) {
					AtendimentoMotivoEncerramento motivoEncerramentoSelecionado = (AtendimentoMotivoEncerramento)
							motivoEncerramentoSpinner.getAdapter().getItem(motivoEncerramentoSpinner.getSelectedItemPosition());
					
					if (motivoEncerramentoSelecionado.getIndicadorExecucao()
							.compareTo(ConstantesSistema.NAO) == 0) {
						tipoMedicao.setVisibility(View.GONE);
						tipoHidrometro.setVisibility(View.GONE);
						numeroHidrometro.setVisibility(View.GONE);
						localInstalacaoHidrSpinner.setVisibility(View.GONE);
						protecaoHidrSpinner.setVisibility(View.GONE);
						icCavalete.setVisibility(View.GONE);

						tvTipoHidrometroAbaRemoverHidr.setVisibility(View.GONE);
						tvNumeroHidrometroAbaRemoverHidr.setVisibility(View.GONE);
						tvTipoMedicaoAbaRemoverHidr.setVisibility(View.GONE);
						tvLocalInstalacaoAbaRemoverHidr.setVisibility(View.GONE);
						tvProtecaoHidrAbaRemoverHidr.setVisibility(View.GONE);
						tvIcCavaleteAbaRemoverHidr.setVisibility(View.GONE);
						
					} else {
						tipoMedicao.setVisibility(View.VISIBLE);
						tipoHidrometro.setVisibility(View.VISIBLE);
						numeroHidrometro.setVisibility(View.VISIBLE);
						localInstalacaoHidrSpinner.setVisibility(View.VISIBLE);
						protecaoHidrSpinner.setVisibility(View.VISIBLE);
						icCavalete.setVisibility(View.VISIBLE);
						
						
						tvTipoHidrometroAbaRemoverHidr.setVisibility(View.VISIBLE);
						tvNumeroHidrometroAbaRemoverHidr.setVisibility(View.VISIBLE);
						tvTipoMedicaoAbaRemoverHidr.setVisibility(View.VISIBLE);
						tvLocalInstalacaoAbaRemoverHidr.setVisibility(View.VISIBLE);
						tvProtecaoHidrAbaRemoverHidr.setVisibility(View.VISIBLE);
						tvIcCavaleteAbaRemoverHidr.setVisibility(View.VISIBLE);
					}
				} 
				
	        }
	
	        @Override
	        public void onNothingSelected(AdapterView<?> arg0) {
	            // TODO Auto-generated method stub
	        }

		});
		
		botaoEncerrarOS = (Button)findViewById(R.botao.encerrarOSFiscalizacao);	
		botaoEncerrarOS.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(ManterDadosAbaRemocaoHidrometroActivity.this);
					builder.setTitle("Encerrar OS");
					builder.setMessage("Confirma a conclusão da Ordem de Serviço?");
					builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Fachada fachada = Fachada.getInstance();													
							
							if(fachada.verificarFotosInformadas(ordemServicoExecucaoOs.getId())){
								
								motivoEncerramento = (AtendimentoMotivoEncerramento)
										motivoEncerramentoSpinner.getAdapter().getItem(motivoEncerramentoSpinner.getSelectedItemPosition());
								
								boolean conclusaoServico = false;
								if(motivoEncerramento.getIndicadorExecucao() != null && 
										motivoEncerramento.getIndicadorExecucao().compareTo(ConstantesSistema.SIM) == 0) {
									conclusaoServico = true;								
								}
								
								//[FE0001] Verificar campos obrigatórios
								int retorno = verificarCamposObrigatorios(conclusaoServico);
								
								switch(retorno){
								
									case SUCESSO:
										//ENCERRAR OS
										
										if (conclusaoServico) {
											
											protecaoHidr = (HidrometroProtecao)
													protecaoHidrSpinner.getAdapter().getItem(protecaoHidrSpinner.getSelectedItemPosition());
											
											localInstalacaoHidr = (HidrometroLocalInstalacao)
													localInstalacaoHidrSpinner.getAdapter().getItem(localInstalacaoHidrSpinner.getSelectedItemPosition());
											
											OrdemServicoRemocaoHidrometro ordemServicoRemocaoHidr = new OrdemServicoRemocaoHidrometro();
											
											ordemServicoRemocaoHidr.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs);
											ordemServicoRemocaoHidr.setProtecaoHidr(protecaoHidr);
											ordemServicoRemocaoHidr.setLocalInstalacaoHidr(localInstalacaoHidr);
											
											if (icCavalete.getCheckedRadioButtonId() == R.id.icCavaleteCom) {
												ordemServicoRemocaoHidr.setIcCavalete(1);
											} else {
												ordemServicoRemocaoHidr.setIcCavalete(2);
											}
											
											ordemServicoRemocaoHidr.setUltimaAlteracao(new Date());
											
											fachada.inserir(ordemServicoRemocaoHidr);
										}
										
										
										//OBJETO ORDEM SERVICO EXECUCAO OS
										ordemServicoExecucaoOs.setAtendimentoMotivoEncerramento(motivoEncerramento);
										ordemServicoExecucaoOs.setDataEncerramento(data);
										parecer = (EditText)findViewById(R.id.parecerFiscalizacao); 
										ordemServicoExecucaoOs.setParecer(parecer.getText().toString());
										ordemServicoExecucaoOs.setUltimaAlteracao(new Date());
										
										//CHAMAR MÉTODO PARA ATUALIZAR A TABELA ORDEM_SERVICO_EXECUCAO_OS
										Fachada.getInstance().atualizar(ordemServicoExecucaoOs, ordemServicoExecucaoOs.getId());
																	
										
										//Retornando a ordem de serviço a ser transmitida
										Intent intent = new Intent();
								        intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecucaoOs);
								        if (getParent() == null) {
								            setResult(Activity.RESULT_OK, intent);
								        }
								        else {
								            getParent().setResult(Activity.RESULT_OK, intent);
								        }
										
										//Encerrando a tela
										finish();
										break;
										
									case MOTIVO_NAO_INFORMADO:
										Util.exibirMsgAlerta(R.string.erro_motivo_nao_informado, ManterDadosAbaRemocaoHidrometroActivity.this);
										break;
										
									case LOCAL_NAO_INFORMADO:
										Util.exibirMsgAlerta(R.string.erro_local_inst_hidr_nao_informado, ManterDadosAbaRemocaoHidrometroActivity.this);
										break;
										
									case PROTECAO_NAO_INFORMADA:
										Util.exibirMsgAlerta(R.string.erro_protecao_hidr_nao_informada, ManterDadosAbaRemocaoHidrometroActivity.this);
										break;
								}
								
							}else{
								Util.exibirMsgAlerta(R.string.erro_verificar_foto_informada, ManterDadosAbaRemocaoHidrometroActivity.this);
							}
						}
					
						
					});
					builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					
					alertaConfirmacaoEncerramentoOs = builder.create();
					alertaConfirmacaoEncerramentoOs.show();
			}
		});
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId,MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ManterDadosAbaRemocaoHidrometroActivity.this,
                                    RegistrarFotosActivity.class);
        		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecucaoOs);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, quantidadeOS);
                break;
        }

        startActivity(intent);

        return super.onMenuItemSelected(featureId, item);
	}
	
	
	/**
	 * 
	 * [FE0001] Verificar campos obrigatórios
	 * 
	 */
	private int verificarCamposObrigatorios(boolean conclusaoServico) {
		
		int retorno = SUCESSO;

		
		
		//1. Caso o Motivo do Encerramento não tenha sido informado
		if(motivoEncerramentoSpinner.getSelectedItem().toString()==null){
			retorno = MOTIVO_NAO_INFORMADO;
		}
		
		//2. Caso o motivo de encerramento selecionado possua indicador de conclusão de serviço igual a "1" 
		//   e o Local de Instalação não tenha sido informado
		else if(conclusaoServico && localInstalacaoHidrSpinner.getSelectedItem().toString()==null) {
			retorno = LOCAL_NAO_INFORMADO;
		}
		
		//3. Caso o motivo de encerramento selecionado possua indicador de conclusão de serviço igual a "1" 
		//   e a Proteção do Hidrômetro não tenha sido informada
		else if(conclusaoServico && protecaoHidrSpinner.getSelectedItem().toString()==null) {
			retorno = PROTECAO_NAO_INFORMADA;
		}
		
		return retorno;
	}
}
