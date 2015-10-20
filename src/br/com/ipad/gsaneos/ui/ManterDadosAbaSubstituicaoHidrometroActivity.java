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
import br.com.ipad.gsaneos.adapter.HidrometroAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroLocalArmazenagemAdapter;
import br.com.ipad.gsaneos.adapter.HidrometroSituacaoAdapter;
import br.com.ipad.gsaneos.bean.AtendimentoMotivoEncerramento;
import br.com.ipad.gsaneos.bean.Hidrometro;
import br.com.ipad.gsaneos.bean.HidrometroLocalArmazenagem;
import br.com.ipad.gsaneos.bean.HidrometroSituacao;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.OrdemServicoSubstituicaoHidrometro;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.ui.R.string;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

@SuppressLint("SimpleDateFormat")
public class ManterDadosAbaSubstituicaoHidrometroActivity 
					extends BaseActivity {
	
	private OrdemServicoExecucaoOS ordemServicoExecucaoOs;
	private Integer quantidadeOSExecutadas;
	private Integer quantidadeOS;
	
	private Intent intent;
	
	//DADOS ATUAIS DO HIDROMETRO
	private EditText tipoHidrometroAtual;
	private EditText numeroHidrometroAtual;
	private EditText tipoMedicaoAtual;
	private EditText numeroLeituraAtual;
	private Spinner situacaoHidrometroAtual;
	private Spinner localArmazanagemAtual;
	
	private TextView dadosAtuaisHidrometro;
	private TextView tipoHidrometroText;
	private TextView numeroHidrometroText;
	private TextView tipoMedicaoText;
	private TextView numeroLeituraText;
	private TextView situacaoHidrometroText;
	private TextView localArmazenagemText;
	
	//DADOS DO NOVO HIDROMETRO
	private Spinner numeroHidrometroNovo;
	private EditText localInstalacaoNovo;
	private EditText protecaoNovo;
	private EditText leituraInstalacaoNovo;
	private EditText numeroSeloNovo;
	private RadioGroup grupoCavaleteNovo;
	private RadioButton comSem;
	private RadioGroup grupoTipoHidrometroNovo;
	private RadioButton micromedidorMacromedidor;
	private Spinner atendimentoMotivoEncerramentoNovo;
	private TextView dataExecucao;
	private TextView horaExecucao;
	private EditText parecer;
	
	private TextView dadosHidrometroNovoText;
	private TextView tipoHidrometroNovoText;
	private TextView numeroHidrometroNovoText;
	private TextView localInstalacaoText;
	private TextView protecaoText;
	private TextView leituraInstalacaoNovoText;
	private TextView numeroSeloNovoText;
	private TextView cavaleteText;
	
	private Button botaoEncerrarOSsubstituicaoHidrometro;
	
	private SimpleDateFormat simpleDateFormat;
	private Date data;
	
	private AlertDialog alertaConfirmacaoEncerramentoOs;
	
	private HidrometroSituacao hidrometroSituacao;
	private HidrometroLocalArmazenagem hidrometroLocalArmazenagem;
	private Hidrometro hidrometro;
	private AtendimentoMotivoEncerramento atendimentoMotivoEncerramento;
	
	private List<HidrometroSituacao> listaHidrometroSituacao;
	private List<HidrometroLocalArmazenagem> listaHidrometroLocalArmazenagem;
	private List<Hidrometro> listaHidrometro;
	private List<AtendimentoMotivoEncerramento> listaAtendimentoMotivoEncerramento;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manter_dados_aba_substituicao_hidrometro_activity);
		
		ordemServicoExecucaoOs = (OrdemServicoExecucaoOS) getIntent().getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		//INICIO CARREGAMENTO DADOS ATUAIS HIDROMETRO
			
			//TIPO HIDROMETRO ATUAL
			tipoHidrometroAtual = (EditText)findViewById(R.id.tipoHidrometroAtual);
			if(ordemServicoExecucaoOs.getTipoHidrometro()!=null)
			{
				if(ordemServicoExecucaoOs.getTipoHidrometro().compareTo(ConstantesSistema.SIM)==0)
				{
					
					tipoHidrometroAtual.setText(Hidrometro.MACROMEDIDOR);
					
					/*
					 * ALTERAR TEXTVIEW PARA TOMBAMENTO HIDROMETRO
					 * POR DEFAULT JA VEM COM A DESCRICAO NUMERO DO 
					 * HIDROMETRO
					 */
					numeroHidrometroText  = (TextView)findViewById(R.id.numeroHidrometroText);
					numeroHidrometroText.setText(string.string_tombamento_hidrometro);
					
				}else if(ordemServicoExecucaoOs.getTipoHidrometro().compareTo(ConstantesSistema.NAO)==0)
				{
				
					tipoHidrometroAtual.setText(Hidrometro.MICROMEDIDOR);
					
				}
				
				//DESABILITA O CAMPO
				tipoHidrometroAtual.setEnabled(false);
			}
			
			//NUMERO HIDROMETRO ATUAL
			numeroHidrometroAtual = (EditText)findViewById(R.id.numeroHidrometroAtual);
			if(ordemServicoExecucaoOs.getNumeroHidrometro()!=null)
			{
				numeroHidrometroAtual.setText(ordemServicoExecucaoOs.getNumeroHidrometro().toString());
				
				//DESABILITA O CAMPO
				numeroHidrometroAtual.setEnabled(false);
			}
			
			//TIPO MEDICAO ATUAL
			tipoMedicaoAtual = (EditText)findViewById(R.id.tipoMedicaoAtual);
			if(ordemServicoExecucaoOs.getTipoMedicao()!=null)
			{
				if(ordemServicoExecucaoOs.getTipoMedicao().compareTo(ConstantesSistema.SIM)==0)
				{
					
					tipoMedicaoAtual.setText(Hidrometro.LIGACAO_AGUA);
				
				}else if(ordemServicoExecucaoOs.getTipoMedicao().compareTo(ConstantesSistema.NAO)==0)
				{
				
					tipoMedicaoAtual.setText(Hidrometro.POCO);
				
				}
				
				//DESABILITA O CAMPO
				tipoMedicaoAtual.setEnabled(false);
			}
			
			//NUMERO LEITURA ATUAL
			numeroLeituraAtual = (EditText)findViewById(R.id.numeroLeituraAtual);
			if(ordemServicoExecucaoOs.getNumeroLeituraHidrometro()!=null)
			{
				
				numeroLeituraAtual.setText(ordemServicoExecucaoOs.getNumeroLeituraHidrometro().toString());
			
			}
			
			//SITUACAO HIDROMETRO ATUAL
			situacaoHidrometroAtual = (Spinner)findViewById(R.id.situacaoHidrometroAtual);
			hidrometroSituacao = new HidrometroSituacao();
			situacaoHidrometroAtual = (Spinner)findViewById(R.id.situacaoHidrometroAtual);
			listaHidrometroSituacao = Fachada.getInstance().pesquisar(hidrometroSituacao);
			HidrometroSituacaoAdapter hidrometroSituacaoAdapter = new HidrometroSituacaoAdapter(this, listaHidrometroSituacao);
			situacaoHidrometroAtual.setAdapter(hidrometroSituacaoAdapter);
			for(int a = 0; a<listaHidrometroSituacao.size();a++){
				if(listaHidrometroSituacao.get(a).getId().equals(ordemServicoExecucaoOs.getHidrometroSituacao().getId())){
					situacaoHidrometroAtual.setSelection(a);
				}
			}
			
			//LOCAL ARMAZENAGEM ATUAL
			localArmazanagemAtual = (Spinner)findViewById(R.id.localArmazenagemAtual);
			hidrometroLocalArmazenagem = new HidrometroLocalArmazenagem();
			localArmazanagemAtual = (Spinner)findViewById(R.id.localArmazenagemAtual);
			listaHidrometroLocalArmazenagem = Fachada.getInstance().pesquisar(hidrometroLocalArmazenagem);
			HidrometroLocalArmazenagemAdapter hidrometroLocalArmazenagemAdapter = new HidrometroLocalArmazenagemAdapter(this, listaHidrometroLocalArmazenagem);
			localArmazanagemAtual.setAdapter(hidrometroLocalArmazenagemAdapter);
			
		//FIM CARREGAMENTO DADOS ATUAIS HIDROMETROS
		
		//INICIO CARREGAMENTO DADOS NOVOS HIDROMETRO
			
			//LOCAL HISTALACAO NOVO
			localInstalacaoNovo = (EditText)findViewById(R.id.localInstalacaoNovo);
			if(ordemServicoExecucaoOs.getDescricaoLocalHistalacaoHidrometro()!=null)
			{
			
				localInstalacaoNovo.setText(ordemServicoExecucaoOs.getDescricaoLocalHistalacaoHidrometro());
				
				//DESABILITA O CAMPO
				localInstalacaoNovo.setEnabled(false);
			}
			
			//PROTECAO NOVO
			protecaoNovo = (EditText)findViewById(R.id.protecaoNovo);
			if(ordemServicoExecucaoOs.getDescricaoHidrometroProtecao()!=null)
			{
				protecaoNovo.setText(ordemServicoExecucaoOs.getDescricaoHidrometroProtecao());
				
				//DESABILITA O CAMPO
				protecaoNovo.setEnabled(false);
			}
			
			//TIPO HIDROMETRO NOVO
			grupoTipoHidrometroNovo = (RadioGroup)findViewById(R.id.grupoTipoHidrometroNovo);
			
			//NUMERO HIDROMETRO NOVO
			numeroHidrometroNovo = (Spinner)findViewById(R.id.numeroHidrometroNovo);
			hidrometro = new Hidrometro();
			listaHidrometro = Fachada.getInstance().pesquisar(hidrometro);
			int contador = 0;
			int contadorAuxiliarLista = listaHidrometro.size();
			int contadorAuxiliarListaAnteriorRemocao = listaHidrometro.size();
			
			while(contador<contadorAuxiliarLista)
			{
				contadorAuxiliarListaAnteriorRemocao = listaHidrometro.size();
				
				if(listaHidrometro.get(contador).getIndicadorHidrometroUtilizado().compareTo(ConstantesSistema.SIM)==0
						|| listaHidrometro.get(contador).getDescricaoNumeroTombamento()!=null)
				{
					
					listaHidrometro.remove(contador);
				
					contadorAuxiliarLista = listaHidrometro.size();
		
				}
				
				if(contadorAuxiliarLista==contadorAuxiliarListaAnteriorRemocao){
					
					contador++;
					
				}else{
					
					contador = 0;	
					
				}
				
			}
			
			HidrometroAdapter hidrometroAdapter = new HidrometroAdapter(this, listaHidrometro,true);
			numeroHidrometroNovo.setAdapter(hidrometroAdapter);
			
			//LEITURA INSTALACAO
			leituraInstalacaoNovo = (EditText)findViewById(R.id.leituraInstalacaoNovo);
			
			//NUMERO DO SELO
			numeroSeloNovo = (EditText)findViewById(R.id.numeroSeloNovo);
			
			//TIPO CAVALETE
			grupoCavaleteNovo = (RadioGroup)findViewById(R.id.grupoCavaleteNovo);
			
			//PARECER
			parecer = (EditText)findViewById(R.id.parecerSubstituicao);
			
			//ATENDIMENTO MOTIVO ENCERRAMENTO NOVO
			atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
			atendimentoMotivoEncerramentoNovo = (Spinner)findViewById(R.id.atendimentoMotivoEncerramentoNovo);
			listaAtendimentoMotivoEncerramento = Fachada.getInstance().pesquisar(atendimentoMotivoEncerramento);
			AtendimentoMotivoEncerramentoAdapter motivoEncerramentoAdapter = new AtendimentoMotivoEncerramentoAdapter(this, listaAtendimentoMotivoEncerramento);
			atendimentoMotivoEncerramentoNovo.setAdapter(motivoEncerramentoAdapter);
			
			//[FE0003] VERIFICAR MOTIVO ENCERRAMENTO SELECIONADO
			atendimentoMotivoEncerramentoNovo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		                long arg3) {
		        	
		        	dadosAtuaisHidrometro = (TextView)findViewById(R.id.dadosAtuaisHidrometro);
		        	tipoHidrometroText = (TextView)findViewById(R.id.tipoHidrometroText);
		        	numeroHidrometroText = (TextView)findViewById(R.id.numeroHidrometroText);
		        	tipoMedicaoText = (TextView)findViewById(R.id.tipoMedicaoText);
		        	numeroLeituraText = (TextView)findViewById(R.id.numeroLeituraText);
		        	situacaoHidrometroText = (TextView)findViewById(R.id.situacaoHidrometroText);
		        	localArmazenagemText =  (TextView)findViewById(R.id.localArmazenagemText);
		        	
		        	dadosHidrometroNovoText = (TextView)findViewById(R.id.dadosHidrometroNovoText);
		        	tipoHidrometroNovoText = (TextView)findViewById(R.id.tipoHidrometroNovoText);
		        	numeroHidrometroNovoText = (TextView)findViewById(R.id.numeroHidrometroNovoText);
		        	localInstalacaoText = (TextView)findViewById(R.id.localInstalacaoText);
		        	protecaoText = (TextView)findViewById(R.id.protecaoText);
		        	leituraInstalacaoNovoText = (TextView)findViewById(R.id.leituraInstalacaoNovoText);
		        	numeroSeloNovoText = (TextView)findViewById(R.id.numeroSeloNovoText);
		        	cavaleteText = (TextView)findViewById(R.id.cavaleteText);
					
					if (atendimentoMotivoEncerramentoNovo.getSelectedItem().toString() != null) {
						AtendimentoMotivoEncerramento motivoEncerramentoSelecionado = (AtendimentoMotivoEncerramento)
								atendimentoMotivoEncerramentoNovo.getAdapter().getItem(atendimentoMotivoEncerramentoNovo.getSelectedItemPosition());
						
						if (motivoEncerramentoSelecionado.getIndicadorExecucao()
								.compareTo(ConstantesSistema.NAO) == 0) {
							tipoHidrometroAtual.setVisibility(View.GONE);
							numeroHidrometroAtual.setVisibility(View.GONE);
							tipoMedicaoAtual.setVisibility(View.GONE);
							numeroLeituraAtual.setVisibility(View.GONE);
							situacaoHidrometroAtual.setVisibility(View.GONE);
							localArmazanagemAtual.setVisibility(View.GONE);
							
							numeroHidrometroNovo.setVisibility(View.GONE);
							localInstalacaoNovo.setVisibility(View.GONE);
							protecaoNovo.setVisibility(View.GONE);
							leituraInstalacaoNovo.setVisibility(View.GONE);
							numeroSeloNovo.setVisibility(View.GONE);
							grupoCavaleteNovo.setVisibility(View.GONE);
							grupoTipoHidrometroNovo.setVisibility(View.GONE);
						
							dadosAtuaisHidrometro.setVisibility(View.GONE);
							tipoHidrometroText.setVisibility(View.GONE);
							numeroHidrometroText.setVisibility(View.GONE);
							tipoMedicaoText.setVisibility(View.GONE);
							numeroLeituraText.setVisibility(View.GONE);
							situacaoHidrometroText.setVisibility(View.GONE);
							localArmazenagemText.setVisibility(View.GONE);
							
							dadosHidrometroNovoText.setVisibility(View.GONE);
							tipoHidrometroNovoText.setVisibility(View.GONE);
							numeroHidrometroNovoText.setVisibility(View.GONE);
							localInstalacaoText.setVisibility(View.GONE);
							protecaoText.setVisibility(View.GONE);
							leituraInstalacaoNovoText.setVisibility(View.GONE);
							numeroSeloNovoText.setVisibility(View.GONE);
							cavaleteText.setVisibility(View.GONE);
							
						} else {
							tipoHidrometroAtual.setVisibility(View.VISIBLE);
							numeroHidrometroAtual.setVisibility(View.VISIBLE);
							tipoMedicaoAtual.setVisibility(View.VISIBLE);
							numeroLeituraAtual.setVisibility(View.VISIBLE);
							situacaoHidrometroAtual.setVisibility(View.VISIBLE);
							localArmazanagemAtual.setVisibility(View.VISIBLE);
							
							numeroHidrometroNovo.setVisibility(View.VISIBLE);
							localInstalacaoNovo.setVisibility(View.VISIBLE);
							protecaoNovo.setVisibility(View.VISIBLE);
							leituraInstalacaoNovo.setVisibility(View.VISIBLE);
							numeroSeloNovo.setVisibility(View.VISIBLE);
							grupoCavaleteNovo.setVisibility(View.VISIBLE);
							grupoTipoHidrometroNovo.setVisibility(View.VISIBLE);
							
							dadosAtuaisHidrometro.setVisibility(View.VISIBLE);
							tipoHidrometroText.setVisibility(View.VISIBLE);
							numeroHidrometroText.setVisibility(View.VISIBLE);
							tipoMedicaoText.setVisibility(View.VISIBLE);
							numeroLeituraText.setVisibility(View.VISIBLE);
							situacaoHidrometroText.setVisibility(View.VISIBLE);
							localArmazenagemText.setVisibility(View.VISIBLE);
							
							dadosHidrometroNovoText.setVisibility(View.VISIBLE);
							tipoHidrometroNovoText.setVisibility(View.VISIBLE);
							numeroHidrometroNovoText.setVisibility(View.VISIBLE);
							localInstalacaoText.setVisibility(View.VISIBLE);
							protecaoText.setVisibility(View.VISIBLE);
							leituraInstalacaoNovoText.setVisibility(View.VISIBLE);
							numeroSeloNovoText.setVisibility(View.VISIBLE);
							cavaleteText.setVisibility(View.VISIBLE);
							
						}
					} 
					
		        }
		
		        @Override
		        public void onNothingSelected(AdapterView<?> arg0) {
		            // TODO Auto-generated method stub
		        }

			});
			
			//DATA E HORA DA EXECUCAO
			dataExecucao = (TextView)findViewById(R.id.dataExecucaoSubstituicaoHidrometro);
			simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			data = new Date();
			dataExecucao.setText(" "+simpleDateFormat.format(data));
			
			horaExecucao = (TextView)findViewById(R.id.horaExecucaoSubstituicaoHidrometro);
			simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			horaExecucao.setText(" "+simpleDateFormat.format(data));
		
		//FIM CARREGAMENTO DADOS NOVOS HIDROMETROS
		
		//BOTAO ENCERRAR OS SUBSTITUICAO HIDROMETRO
		botaoEncerrarOSsubstituicaoHidrometro = (Button)findViewById(R.botao.encerrarOSSubstituicaoHidrometro);
		botaoEncerrarOSsubstituicaoHidrometro.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alert = new AlertDialog.Builder(ManterDadosAbaSubstituicaoHidrometroActivity.this);
				alert.setTitle("Encerrar OS");
				alert.setMessage("Confirma a conclusão da Ordem de Serviço?");
				
				alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Fachada fachada = Fachada.getInstance();
						
							//[FE0001] VERIFICAR CAMPOS OBRIGATORIOS CASO 1
							if(atendimentoMotivoEncerramentoNovo.getSelectedItemPosition()!=0)
							{
								atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento)
										atendimentoMotivoEncerramentoNovo.getItemAtPosition(atendimentoMotivoEncerramentoNovo.getSelectedItemPosition());
								/*[FE0001] VERIFICAR CAMPOS OBRIGATORIOS
								 *CASO 2
								 *CASO 3
								 *CASO 4
								 *CASO 5
								 *CASO 6
								 *CASO 7
								 */
								if(atendimentoMotivoEncerramento.getIndicadorExecucao().equals(ConstantesSistema.SIM))
								{
									//CASO 2
									if(numeroLeituraAtual.getText().length()>0)
									{
										
										//CASO 3
										if(situacaoHidrometroAtual.getSelectedItemPosition()>0)
										{
											
											hidrometroSituacao = (HidrometroSituacao)
													situacaoHidrometroAtual.getItemAtPosition(situacaoHidrometroAtual.getSelectedItemPosition());
											//CASO 4
											if(!(hidrometroSituacao.getId().equals(HidrometroSituacao.INSTALADO)))
											{
												
												//CASO 5
												if(localArmazanagemAtual.getSelectedItemPosition()>0)
												{
									
													//CASO 7
													if(numeroHidrometroNovo.getSelectedItemPosition()>0)
													{
															
														if(fachada.verificarFotosInformadas(ordemServicoExecucaoOs.getId()))
														{
																
															//[IT0007] ATUALIZAR DADOS ENCERRAR ORDEM SERVICO
															
																//MONTANDO OBJETO ORDEMSERVICOOS CASO 1
																	ordemServicoExecucaoOs.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
																	ordemServicoExecucaoOs.setDataEncerramento(data);
																	ordemServicoExecucaoOs.setParecer(parecer.getText().toString());
																	ordemServicoExecucaoOs.setUltimaAlteracao(new Date());
																
																	fachada.atualizar(ordemServicoExecucaoOs, ordemServicoExecucaoOs.getId());
																	
																//MONTANDO OBJETO ORDEMSERVICOSUBSTITUICAOHIDROMETRO CASO 2
																	OrdemServicoSubstituicaoHidrometro ordemServicoSubstituicaoHidrometro = new OrdemServicoSubstituicaoHidrometro();
																	
																	//ORDEM SERVICO
																	ordemServicoSubstituicaoHidrometro.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs.getId());
																	
																	//NUMERO LEITURA
																	ordemServicoSubstituicaoHidrometro.setNumeroLeituraHidrometro(Integer.valueOf(numeroLeituraAtual.getText().toString()));
																	
																	//HIDROMETRO SITUACAO
																	ordemServicoSubstituicaoHidrometro.setHidrometroSituacao(hidrometroSituacao.getId());
																	
																	//HIDROMETRO LOCAL ARMAZANAGEM
																	hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem)localArmazanagemAtual.
																			getItemAtPosition(localArmazanagemAtual.getSelectedItemPosition());
																	ordemServicoSubstituicaoHidrometro.setHidrometroLocalArmazenagem(hidrometroLocalArmazenagem.getId());
																	
																	//TIPO HIDROMETRO
																	int intMicromedidorMacromedidor = grupoTipoHidrometroNovo.getCheckedRadioButtonId();
																	
																	micromedidorMacromedidor = (RadioButton)findViewById(intMicromedidorMacromedidor);
																	
																	if(micromedidorMacromedidor.getText().toString().trim().toUpperCase().equals(Hidrometro.MICROMEDIDOR))
																	{
																		
																		ordemServicoSubstituicaoHidrometro.setTipoHidrometro(ConstantesSistema.NAO);
																		
																	}else
																	{
																		
																		ordemServicoSubstituicaoHidrometro.setTipoHidrometro(ConstantesSistema.SIM);
																		
																	}
																	
																	//HIDROMETRO
																	hidrometro = (Hidrometro)numeroHidrometroNovo.getItemAtPosition(numeroHidrometroNovo.getSelectedItemPosition());
																	ordemServicoSubstituicaoHidrometro.setHidrometro(hidrometro.getId());
																	
																	//LEITURA INSTALACAO
																	leituraInstalacaoNovo = (EditText)findViewById(R.id.leituraInstalacaoNovo);
																	
																	if(!leituraInstalacaoNovo.getText().toString().equals("")){
																		
																		ordemServicoSubstituicaoHidrometro.setLeituraInstalacao(Integer.valueOf(
																				leituraInstalacaoNovo.getText().toString()));
																		
																	}
																	
																	//NUMERO DO SELO
																	numeroSeloNovo = (EditText)findViewById(R.id.numeroSeloNovo);
																	
																	if(!numeroSeloNovo.getText().toString().equals("")){
																		
																		ordemServicoSubstituicaoHidrometro.setNumeroSelo(numeroSeloNovo.getText().toString());
																		
																	}
																	
																	//INDICADOR CAVALETE
																	int a = grupoCavaleteNovo.getCheckedRadioButtonId();
																	comSem = (RadioButton)findViewById(a);
																	if(comSem.getText().toString().equals("COM"))
																	{
																		
																		ordemServicoSubstituicaoHidrometro.setIndicadorCavalete(ConstantesSistema.SIM);
																	
																	}else
																	{
																		
																		ordemServicoSubstituicaoHidrometro.setIndicadorCavalete(ConstantesSistema.NAO);
																		
																	}
																	
																	//ULTIMA ALTERACAO
																	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																	ordemServicoSubstituicaoHidrometro.setUltimaAlteracao(simpleDateFormat.format(new Date()));
																	
																	fachada.inserir(ordemServicoSubstituicaoHidrometro);
																	
																//ALTERANDO OBJETO HIDROMETRO CASO 2.1
																	hidrometro.setIndicadorHidrometroUtilizado(ConstantesSistema.SIM);
																	fachada.atualizar(hidrometro, hidrometro.getId());
																	
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
																
															} else {
																Util.exibirMsgAlerta(R.string.erro_verificar_foto_informada, ManterDadosAbaSubstituicaoHidrometroActivity.this);
															}
														} else {
															Util.exibirMsgAlerta(R.string.alerta_numero_hidrometro_novo_nao_informado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
														}
												} else {
													Util.exibirMsgAlerta(R.string.alerta_local_armazenagem_nao_informado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
												}
											} else {
												Util.exibirMsgAlerta(R.string.alerta_situacao_hidrometro_instalado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
											}
										} else {
											Util.exibirMsgAlerta(R.string.alerta_situacao_hidrometro_nao_informado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
										}
									}else {
										Util.exibirMsgAlerta(R.string.alerta_numero_leitura_nao_informado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
									}
								}else {
									//[IT0007] ATUALIZAR DADOS ENCERRAR ORDEM SERVICO
									
									//MONTANDO OBJETO ORDEMSERVICOOS
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
								Util.exibirMsgAlerta(R.string.alert_motivo_encerramento_nao_informado, ManterDadosAbaSubstituicaoHidrometroActivity.this);
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
	
	public void onRadioButtonClicked(View view){
		
		numeroHidrometroNovoText = (TextView)findViewById(R.id.numeroHidrometroNovoText);
		numeroHidrometroNovo = (Spinner)findViewById(R.id.numeroHidrometroNovo);
		hidrometro = new Hidrometro();
		listaHidrometro = Fachada.getInstance().pesquisar(hidrometro);
		boolean numeroHidrometro = true;
		HidrometroAdapter hidrometroAdapter;
		
		int contador = 0;
		int contadorAuxiliarLista = listaHidrometro.size();
		int contadorAuxiliarListaAnteriorRemocao = listaHidrometro.size();
		
		switch (view.getId()) {
		case R.id.micromedidor:
			
			numeroHidrometroNovoText.setText(string.string_numero_hidrometro);
			
			while(contador<contadorAuxiliarLista)
			{
				contadorAuxiliarListaAnteriorRemocao = listaHidrometro.size();
				
				if(listaHidrometro.get(contador).getIndicadorHidrometroUtilizado().compareTo(ConstantesSistema.SIM)==0
						|| listaHidrometro.get(contador).getDescricaoNumeroTombamento()!=null)
				{
					
					listaHidrometro.remove(contador);
				
					contadorAuxiliarLista = listaHidrometro.size();
		
				}
				
				if(contadorAuxiliarLista==contadorAuxiliarListaAnteriorRemocao){
					
					contador++;
					
				}else{
					
					contador = 0;	
					
				}
				
			}
				
			numeroHidrometro = true;
	
			hidrometroAdapter = new HidrometroAdapter(this, listaHidrometro,numeroHidrometro);
			numeroHidrometroNovo.setAdapter(hidrometroAdapter);
			
			break;
		case R.id.macromedidor:
			
			numeroHidrometroNovoText.setText(string.string_tombamento_hidrometro);
			
			while(contador<contadorAuxiliarLista)
			{
				contadorAuxiliarListaAnteriorRemocao = listaHidrometro.size();
				
				if(listaHidrometro.get(contador).getIndicadorHidrometroUtilizado().compareTo(ConstantesSistema.SIM)==0
						|| listaHidrometro.get(contador).getDescricaoNumeroHidrometro()!=null)
				{
					
					listaHidrometro.remove(contador);
				
					contadorAuxiliarLista = listaHidrometro.size();
		
				}
				
				if(contadorAuxiliarLista==contadorAuxiliarListaAnteriorRemocao){
					
					contador++;
					
				}else{
					
					contador = 0;	
					
				}
				
			}
				
			numeroHidrometro = false;
		
			hidrometroAdapter = new HidrometroAdapter(this, listaHidrometro,numeroHidrometro);
			numeroHidrometroNovo.setAdapter(hidrometroAdapter);
			
			break;
		default:
			break;
		}
		
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ManterDadosAbaSubstituicaoHidrometroActivity.this,
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
