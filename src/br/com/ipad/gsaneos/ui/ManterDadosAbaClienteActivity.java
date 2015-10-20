package br.com.ipad.gsaneos.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.ipad.gsaneos.adapter.OrgaoExpedidorRGAdapter;
import br.com.ipad.gsaneos.adapter.UnidadeFederacaoAdapter;
import br.com.ipad.gsaneos.bean.ClienteInformadoEmCampo;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.OrgaoExpedidorRG;
import br.com.ipad.gsaneos.bean.UnidadeFederacao;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

/**
 * [UC1504] - Manter Dados Aba Cliente.
 * 
 * @author Jonathan Marcos
 * @date 19/06/2013
 */
public class ManterDadosAbaClienteActivity extends BaseActivity {
	private OrdemServicoExecucaoOS ordemServicoExecucaoOs;
	private Integer quantidadeOSExecutadas;
	private Integer quantidadeOS;
	private ClienteInformadoEmCampo clienteInformadoEmCampo;
	
	private List<OrgaoExpedidorRG> listaOrgaoExpedidorRG;
	private List<UnidadeFederacao> listaUnidadeFederacao;
	
	private OrgaoExpedidorRG orgaoExpedidorRG;
	private UnidadeFederacao unidadeFederacao;
	private Intent intent;
	private TextView nomeClienteVizualizar;
	private TextView cpfCnpjVizualizar;
	private TextView rgVizualizar;
	private TextView orgaoExpedidorVizualizar;
	private TextView ufVizualizar;
	private EditText dddVizualizar;
	private EditText telefoneVizualizar;
	private EditText ramalVizualizar;
	
	private EditText nomeClienteAlterar;
	private EditText cpfCnpjAlterar;
	private EditText rgAlterar;
	private Spinner orgaoExpedidorAlterar;
	private Spinner ufAlterar;
	private EditText dddAlterar;
	private EditText telefoneAlterar;
	private EditText ramalAlterar;
	
	private Button botaoAtualizarCliente;
	private Button botaoAtualizarClienteVizualizar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intent = getIntent();
		ordemServicoExecucaoOs = (OrdemServicoExecucaoOS)intent.getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		clienteInformadoEmCampo = new ClienteInformadoEmCampo();
		clienteInformadoEmCampo = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getId(),clienteInformadoEmCampo);
		
		if((!ordemServicoExecucaoOs.getCpf().equals("")
				&& ordemServicoExecucaoOs.getCpf()!=null)){
			setContentView(R.layout.manter_dados_aba_cliente_visualizacao_activity);
			nomeClienteVizualizar = (TextView)findViewById(R.id.nomeClienteVizualizar);
			cpfCnpjVizualizar = (TextView)findViewById(R.id.cpfCnpjVizualizar);
			rgVizualizar = (TextView)findViewById(R.id.rgVizualizar);
			orgaoExpedidorVizualizar = (TextView)findViewById(R.id.orgaoExpedidorVizualizar);
			ufVizualizar = (TextView)findViewById(R.id.ufVizualizar);
			dddVizualizar = (EditText)findViewById(R.id.dddVizualizar);
			telefoneVizualizar = (EditText)findViewById(R.id.telefoneVizualizar);
			ramalVizualizar = (EditText)findViewById(R.id.ramalVizualizar);
			
			orgaoExpedidorRG = new OrgaoExpedidorRG();
			unidadeFederacao = new UnidadeFederacao();
			
			nomeClienteVizualizar.setText(ordemServicoExecucaoOs.getCliente());
			if(ordemServicoExecucaoOs.getCpf()!=null){
				cpfCnpjVizualizar.setText(ordemServicoExecucaoOs.getCpf());
			}else{
				cpfCnpjAlterar.setText(ordemServicoExecucaoOs.getCnpj());
			}
			rgVizualizar.setText(ordemServicoExecucaoOs.getRg());
			if(ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId()!=0){
				orgaoExpedidorRG = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId(),
						orgaoExpedidorRG);
				orgaoExpedidorVizualizar.setText(String.valueOf(orgaoExpedidorRG.getDescricao()));
			}else{
				orgaoExpedidorVizualizar.setText("");
			}
			
			if(ordemServicoExecucaoOs.getUnidadeFederacao().getId()!=0){
				unidadeFederacao = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getUnidadeFederacao().getId(),
						unidadeFederacao);
				ufVizualizar.setText(String.valueOf(unidadeFederacao.getDescricaoSigla()));
			}else{
				ufVizualizar.setText("");
			}	
			
			if(clienteInformadoEmCampo==null){
				dddVizualizar.setText(ordemServicoExecucaoOs.getDddTelefone());
				telefoneVizualizar.setText(ordemServicoExecucaoOs.getTelefone());
				ramalVizualizar.setText(ordemServicoExecucaoOs.getRamalTelefone());
			}else{
				dddVizualizar.setText(clienteInformadoEmCampo.getDddTelefone());
				telefoneVizualizar.setText(clienteInformadoEmCampo.getTelefone());
				ramalVizualizar.setText(clienteInformadoEmCampo.getRamalTelefone());
			}
			
			
			/* Autor: Jonathan Marcos
			 * Data: 12/09/2013
			 * RM9457
			 */
			botaoAtualizarClienteVizualizar = (Button)findViewById(R.botao.botaoAtualizarClienteVizualizar);
			botaoAtualizarClienteVizualizar.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v) {
					clienteInformadoEmCampo = new ClienteInformadoEmCampo();
					clienteInformadoEmCampo = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getId(),clienteInformadoEmCampo);
					ClienteInformadoEmCampo clienteInformadoEmCampoInsertUpdate = new ClienteInformadoEmCampo();
					
					clienteInformadoEmCampoInsertUpdate.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs);
					clienteInformadoEmCampoInsertUpdate.setNomeCliente(nomeClienteVizualizar.getText().toString());
					clienteInformadoEmCampoInsertUpdate.setRg(rgVizualizar.getText().toString());
					
					orgaoExpedidorRG = new OrgaoExpedidorRG();
					if(ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId()!=0){
						orgaoExpedidorRG = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId(),
								orgaoExpedidorRG);
						clienteInformadoEmCampoInsertUpdate.setOrgaoExpeditorRG(orgaoExpedidorRG);
					}
					
					UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
					if(ordemServicoExecucaoOs.getUnidadeFederacao().getId()!=0){
						unidadeFederacao = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getUnidadeFederacao().getId(),
								unidadeFederacao);
						clienteInformadoEmCampoInsertUpdate.setUnidadeFederacao(unidadeFederacao);
					}
					
					if(((!telefoneVizualizar.getText().toString().equals("")
							&& !telefoneVizualizar.getText().toString().equals(" ")
							&& telefoneVizualizar.getText().toString()!=null)
							|| (!ramalVizualizar.getText().toString().equals("")
									&& !ramalVizualizar.getText().toString().equals(" ")
									&& ramalVizualizar.getText().toString()!=null))
									&& (dddVizualizar.getText().toString().equals("")
											|| dddVizualizar.getText().toString().equals(" ")
											|| dddVizualizar.getText().toString()==null)){
						Util.exibirMsgAlerta(R.string.erro_verificar_ddd, ManterDadosAbaClienteActivity.this);
					}else if(((!dddVizualizar.getText().toString().equals("")
							&& !dddVizualizar.getText().toString().equals(" ")
							&& dddVizualizar.getText().toString()!=null)
							|| (!ramalVizualizar.getText().toString().equals("")
									&& !ramalVizualizar.getText().toString().equals(" ")
									&& ramalVizualizar.getText().toString()!=null))
									&& (telefoneVizualizar.getText().toString().equals("")
											|| telefoneVizualizar.getText().toString().equals(" ")
											|| telefoneVizualizar.getText().toString()==null)){
						Util.exibirMsgAlerta(R.string.erro_verificar_telefone, ManterDadosAbaClienteActivity.this);
					}else if((!dddVizualizar.getText().toString().equals("")
							&& !dddVizualizar.getText().toString().equals(" ")
							&& dddVizualizar.getText().toString()!=null)
							&& (!telefoneVizualizar.getText().toString().equals("")
									&& !telefoneVizualizar.getText().toString().equals(" ")
									&& telefoneVizualizar.getText().toString()!=null)){
						if(dddVizualizar.getText().toString().equals("81") 
								|| dddVizualizar.getText().toString().equals("87")){
							if(telefoneVizualizar.getText().toString().length()==8){
								try {
									
									if(cpfCnpjVizualizar.getText().toString().length()==11){
										clienteInformadoEmCampoInsertUpdate.setCnpj("");
										clienteInformadoEmCampoInsertUpdate.setCpf(cpfCnpjVizualizar.getText().toString());
									}else if(cpfCnpjVizualizar.getText().toString().length()==14){
										clienteInformadoEmCampoInsertUpdate.setCpf("");
										clienteInformadoEmCampoInsertUpdate.setCnpj(cpfCnpjAlterar.getText().toString());
									}
									clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddVizualizar.getText().toString());
									clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneVizualizar.getText().toString());
									clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalVizualizar.getText().toString());
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
									clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
									if(clienteInformadoEmCampo==null){
										Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
									}else if(clienteInformadoEmCampo!=null){
										Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate, clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
									}
									new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
                                    .setIcon(R.drawable.done)
                                    .setNeutralButton("OK",
                                                      new DialogInterface.OnClickListener() {
                                                          @Override
                                                          public void onClick(
                                                                  DialogInterface dialog,
                                                                  int which) {
                                                          }
                                                      })
                                    .show();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else{
								Util.exibirMsgAlerta(R.string.erro_verifica_telefone_quantidade_digito, ManterDadosAbaClienteActivity.this);
							}
						}else{
							Util.exibirMsgAlerta(R.string.erro_verifica_ddd_81_87, ManterDadosAbaClienteActivity.this);
						}
					}else{
						try {
							
							if(cpfCnpjVizualizar.getText().toString().length()==11){
								clienteInformadoEmCampoInsertUpdate.setCnpj("");
								clienteInformadoEmCampoInsertUpdate.setCpf(cpfCnpjVizualizar.getText().toString());
							}else if(cpfCnpjVizualizar.getText().toString().length()==14){
								clienteInformadoEmCampoInsertUpdate.setCpf("");
								clienteInformadoEmCampoInsertUpdate.setCnpj(cpfCnpjAlterar.getText().toString());
							}
							clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddVizualizar.getText().toString());
							clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneVizualizar.getText().toString());
							clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalVizualizar.getText().toString());
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
							if(clienteInformadoEmCampo==null){
								Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
							}else if(clienteInformadoEmCampo!=null){
								Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate, clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
							}
							new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
                            .setIcon(R.drawable.done)
                            .setNeutralButton("OK",
                                              new DialogInterface.OnClickListener() {
                                                  @Override
                                                  public void onClick(
                                                          DialogInterface dialog,
                                                          int which) {
                                                  }
                                              })
                            .show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			
		}else{
			setContentView(R.layout.manter_dados_aba_cliente_alterar_activity);
			nomeClienteAlterar = (EditText)findViewById(R.id.nomeClienteAlterar);
			cpfCnpjAlterar = (EditText)findViewById(R.id.cpfCnpjAlterar);
			rgAlterar = (EditText)findViewById(R.id.rgAlterar);
			orgaoExpedidorAlterar = (Spinner)findViewById(R.id.orgaoExpedidorAlterar);
			ufAlterar = (Spinner)findViewById(R.id.ufAlterar);
			dddAlterar = (EditText)findViewById(R.id.dddAlterar);
			telefoneAlterar = (EditText)findViewById(R.id.telefoneAlterar);
			ramalAlterar = (EditText)findViewById(R.id.ramalAlterar);
			
			orgaoExpedidorRG = new OrgaoExpedidorRG();
			unidadeFederacao = new UnidadeFederacao();
			
			if(clienteInformadoEmCampo==null){
				if(ordemServicoExecucaoOs.getCliente()==null
						|| ordemServicoExecucaoOs.getCliente().equals("")){
					nomeClienteAlterar.setText("");
				}else{
					nomeClienteAlterar.setText(ordemServicoExecucaoOs.getCliente());
				}
				
				if((ordemServicoExecucaoOs.getCpf()==null || ordemServicoExecucaoOs.getCpf().equals("")) 
						|| (ordemServicoExecucaoOs.getCnpj()==null || ordemServicoExecucaoOs.getCnpj().equals(""))){
					if(ordemServicoExecucaoOs.getCpf()!=null && !ordemServicoExecucaoOs.getCpf().equals("")){
						cpfCnpjAlterar.setText(ordemServicoExecucaoOs.getCpf());
					}else if(ordemServicoExecucaoOs.getCnpj()!=null && !ordemServicoExecucaoOs.getCnpj().equals("")){
						cpfCnpjAlterar.setText(ordemServicoExecucaoOs.getCnpj());
					}else if((ordemServicoExecucaoOs.getCpf()==null || ordemServicoExecucaoOs.getCpf().equals(""))
							&& (ordemServicoExecucaoOs.getCnpj()==null || ordemServicoExecucaoOs.getCnpj().equals(""))){
						cpfCnpjAlterar.setText("");
					}
				}
				
				if(ordemServicoExecucaoOs.getRg()!=null && !ordemServicoExecucaoOs.getRg().equals("")){
					rgAlterar.setText(ordemServicoExecucaoOs.getRg());
				}else{
					rgAlterar.setText("");
				}
				
				if(ordemServicoExecucaoOs.getOrgaoExpeditorRG()!=null && !ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId().equals("")
						&& ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId()!=0){
					orgaoExpedidorRG = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getOrgaoExpeditorRG().getId(), orgaoExpedidorRG);
					listaOrgaoExpedidorRG = new ArrayList<OrgaoExpedidorRG>();
					listaOrgaoExpedidorRG = Fachada.getInstance().pesquisar(orgaoExpedidorRG);
					
					OrgaoExpedidorRGAdapter orgaoExpedidorRGAdapter = new OrgaoExpedidorRGAdapter(this, listaOrgaoExpedidorRG);
					orgaoExpedidorAlterar.setAdapter(orgaoExpedidorRGAdapter);
					
					for(int a = 0;a<listaOrgaoExpedidorRG.size();a++){
						if(orgaoExpedidorRG.getDescricao().equals(listaOrgaoExpedidorRG.get(a).getDescricao())){
							orgaoExpedidorAlterar.setSelection(a);
						}
					}
					
				}else{
					
					orgaoExpedidorRG = new OrgaoExpedidorRG();
					listaOrgaoExpedidorRG = Fachada.getInstance().pesquisar(orgaoExpedidorRG);
					OrgaoExpedidorRGAdapter orgaoExpedidorRGAdapter = new OrgaoExpedidorRGAdapter(this, listaOrgaoExpedidorRG);
					orgaoExpedidorAlterar.setAdapter(orgaoExpedidorRGAdapter);
				}
				
				if(ordemServicoExecucaoOs.getUnidadeFederacao()!=null && !ordemServicoExecucaoOs.getUnidadeFederacao().getId().equals("")
						&& ordemServicoExecucaoOs.getUnidadeFederacao().getId()!=0){
					unidadeFederacao = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getUnidadeFederacao().getId(), unidadeFederacao);
					listaUnidadeFederacao = new ArrayList<UnidadeFederacao>();
					listaUnidadeFederacao = Fachada.getInstance().pesquisar(unidadeFederacao);
					
					UnidadeFederacaoAdapter unidadeFederacaoAdapter = new UnidadeFederacaoAdapter(this, listaUnidadeFederacao);
					ufAlterar.setAdapter(unidadeFederacaoAdapter);
					
					for(int a = 0;a<listaUnidadeFederacao.size();a++){
						if(unidadeFederacao.getDescricao().equals(listaUnidadeFederacao.get(a).getDescricao())){
							ufAlterar.setSelection(a);
						}
					}
					
				}else{
					
					unidadeFederacao = new UnidadeFederacao();
					listaUnidadeFederacao = Fachada.getInstance().pesquisar(unidadeFederacao);
					UnidadeFederacaoAdapter unidadeFederacaoAdapter = new UnidadeFederacaoAdapter(this, listaUnidadeFederacao);
					ufAlterar.setAdapter(unidadeFederacaoAdapter);
				}
			
				if(ordemServicoExecucaoOs.getDddTelefone()!=null && !ordemServicoExecucaoOs.getDddTelefone().equals("")){
					dddAlterar.setText(ordemServicoExecucaoOs.getDddTelefone());
				}else{
					dddAlterar.setText("");
				}
				
				if(ordemServicoExecucaoOs.getTelefone()!=null && !ordemServicoExecucaoOs.getTelefone().equals("")){
					telefoneAlterar.setText(ordemServicoExecucaoOs.getTelefone());
				}else{
					telefoneAlterar.setText("");
				}
				
				if(ordemServicoExecucaoOs.getRamalTelefone()!=null && ordemServicoExecucaoOs.getRamalTelefone().equals("")){
					ramalAlterar.setText(ordemServicoExecucaoOs.getRamalTelefone());
				}else{
					ramalAlterar.setText("");
				}
			}else if(clienteInformadoEmCampo!=null){
				orgaoExpedidorRG = new OrgaoExpedidorRG();
				unidadeFederacao = new UnidadeFederacao();
				nomeClienteAlterar.setText(clienteInformadoEmCampo.getNomeCliente());
				if(!clienteInformadoEmCampo.getCpf().equals("")
						&& !clienteInformadoEmCampo.equals(" ")
						&& clienteInformadoEmCampo.getCpf()!=null){
					cpfCnpjAlterar.setText(clienteInformadoEmCampo.getCpf());
				}else if(!clienteInformadoEmCampo.getCnpj().equals("")
						&& !clienteInformadoEmCampo.getCnpj().equals(" ")
						&& clienteInformadoEmCampo.getCnpj()!=null){
					cpfCnpjAlterar.setText(clienteInformadoEmCampo.getCnpj());
				}
				rgAlterar.setText(clienteInformadoEmCampo.getRg());
				
				if(clienteInformadoEmCampo.getOrgaoExpeditorRG()!=null && !clienteInformadoEmCampo.getOrgaoExpeditorRG().getId().equals("")
						&& clienteInformadoEmCampo.getOrgaoExpeditorRG().getId()!=0){
					orgaoExpedidorRG = Fachada.getInstance().pesquisarPorId(clienteInformadoEmCampo.getOrgaoExpeditorRG().getId(), orgaoExpedidorRG);
					listaOrgaoExpedidorRG = new ArrayList<OrgaoExpedidorRG>();
					listaOrgaoExpedidorRG = Fachada.getInstance().pesquisar(orgaoExpedidorRG);
					
					OrgaoExpedidorRGAdapter orgaoExpedidorRGAdapter = new OrgaoExpedidorRGAdapter(this, listaOrgaoExpedidorRG);
					orgaoExpedidorAlterar.setAdapter(orgaoExpedidorRGAdapter);
					
					for(int a = 0;a<listaOrgaoExpedidorRG.size();a++){
						if(orgaoExpedidorRG.getDescricao().equals(listaOrgaoExpedidorRG.get(a).getDescricao())){
							orgaoExpedidorAlterar.setSelection(a);
						}
					}
					
				}else{
					
					orgaoExpedidorRG = new OrgaoExpedidorRG();
					listaOrgaoExpedidorRG = Fachada.getInstance().pesquisar(orgaoExpedidorRG);
					OrgaoExpedidorRGAdapter orgaoExpedidorRGAdapter = new OrgaoExpedidorRGAdapter(this, listaOrgaoExpedidorRG);
					orgaoExpedidorAlterar.setAdapter(orgaoExpedidorRGAdapter);
				}
				
				if(clienteInformadoEmCampo.getUnidadeFederacao()!=null && !clienteInformadoEmCampo.getUnidadeFederacao().getId().equals("")
						&& clienteInformadoEmCampo.getUnidadeFederacao().getId()!=0){
					unidadeFederacao = Fachada.getInstance().pesquisarPorId(clienteInformadoEmCampo.getUnidadeFederacao().getId(), unidadeFederacao);
					listaUnidadeFederacao = new ArrayList<UnidadeFederacao>();
					listaUnidadeFederacao = Fachada.getInstance().pesquisar(unidadeFederacao);
					
					UnidadeFederacaoAdapter unidadeFederacaoAdapter = new UnidadeFederacaoAdapter(this, listaUnidadeFederacao);
					ufAlterar.setAdapter(unidadeFederacaoAdapter);
					
					for(int a = 0;a<listaUnidadeFederacao.size();a++){
						if(unidadeFederacao.getDescricao().equals(listaUnidadeFederacao.get(a).getDescricao())){
							ufAlterar.setSelection(a);
						}
					}
					
				}else{
					
					unidadeFederacao = new UnidadeFederacao();
					listaUnidadeFederacao = Fachada.getInstance().pesquisar(unidadeFederacao);
					UnidadeFederacaoAdapter unidadeFederacaoAdapter = new UnidadeFederacaoAdapter(this, listaUnidadeFederacao);
					ufAlterar.setAdapter(unidadeFederacaoAdapter);
				}
					
				dddAlterar.setText(clienteInformadoEmCampo.getDddTelefone());
				telefoneAlterar.setText(clienteInformadoEmCampo.getTelefone());
				ramalAlterar.setText(clienteInformadoEmCampo.getRamalTelefone());
			}
			
			botaoAtualizarCliente = (Button)findViewById(R.botao.botaoAtualizarCliente);
			
			botaoAtualizarCliente.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("SimpleDateFormat")
				@Override
				public void onClick(View v) {
					clienteInformadoEmCampo = new ClienteInformadoEmCampo();
					clienteInformadoEmCampo = Fachada.getInstance().pesquisarPorId(ordemServicoExecucaoOs.getId(),clienteInformadoEmCampo);
					ClienteInformadoEmCampo clienteInformadoEmCampoInsertUpdate = new ClienteInformadoEmCampo();
					if(nomeClienteAlterar.getText().toString().equals("")
							|| nomeClienteAlterar.getText().toString().equals(" ")
							|| nomeClienteAlterar.getText().toString()==null){
						Util.exibirMsgAlerta(R.string.erro_verificar_nome_cliente, ManterDadosAbaClienteActivity.this);
					}else{
						clienteInformadoEmCampoInsertUpdate.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs);
						clienteInformadoEmCampoInsertUpdate.setNomeCliente(nomeClienteAlterar.getText().toString());
						if(cpfCnpjAlterar.getText().toString().equals("")
								|| cpfCnpjAlterar.getText().toString().equals(" ")
								|| cpfCnpjAlterar.getText().toString()==null){
							Util.exibirMsgAlerta(R.string.erro_verificar_cpf_cnpj, ManterDadosAbaClienteActivity.this);
						}else{
							if(cpfCnpjAlterar.getText().toString().length()==11){
								if(Util.validarCpf(cpfCnpjAlterar.getText().toString())){
									clienteInformadoEmCampoInsertUpdate.setCnpj("");
									clienteInformadoEmCampoInsertUpdate.setCpf(cpfCnpjAlterar.getText().toString());
									if(((ufAlterar.getSelectedItem().toString()!=null
											&& orgaoExpedidorAlterar.getSelectedItem().toString()==null)
											|| (ufAlterar.getSelectedItem().toString()==null
											&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null)
											|| (ufAlterar.getSelectedItem().toString()!=null 
											&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null))
											&& (rgAlterar.getText().toString().equals("")
													|| rgAlterar.getText().toString().equals(" ") 
													|| rgAlterar.getText().toString()==null)){
										Util.exibirMsgAlerta(R.string.erro_verificar_rg, ManterDadosAbaClienteActivity.this);
									}else{
										clienteInformadoEmCampoInsertUpdate.setRg(rgAlterar.getText().toString());
										if(((!rgAlterar.getText().toString().equals("") 
												&& !rgAlterar.getText().toString().equals(" ") 
												&& rgAlterar.getText().toString()!=null)
												|| (ufAlterar.getSelectedItem().toString()!=null))
												&& orgaoExpedidorAlterar.getSelectedItemPosition()==0){
											Util.exibirMsgAlerta(R.string.erro_verificar_orgao_expedidor, ManterDadosAbaClienteActivity.this);
										}else{
											if(((!rgAlterar.getText().toString().equals("")
													&& !rgAlterar.getText().toString().equals(" ")
													&& rgAlterar.getText().toString()!=null)
													|| orgaoExpedidorAlterar.getSelectedItemPosition()!=0)
													&& ufAlterar.getSelectedItem().toString()==null){
												Util.exibirMsgAlerta(R.string.erro_verificar_uf, ManterDadosAbaClienteActivity.this);
											}else{
												if(((!telefoneAlterar.getText().toString().equals("")
														&& !telefoneAlterar.getText().toString().equals(" ")
														&& telefoneAlterar.getText().toString()!=null)
														|| (!ramalAlterar.getText().toString().equals("")
																&& !ramalAlterar.getText().toString().equals(" ")
																&& ramalAlterar.getText().toString()!=null))
																&& (dddAlterar.getText().toString().equals("")
																		|| dddAlterar.getText().toString().equals(" ")
																		|| dddAlterar.getText().toString()==null)){
													Util.exibirMsgAlerta(R.string.erro_verificar_ddd, ManterDadosAbaClienteActivity.this);
												}else if(((!dddAlterar.getText().toString().equals("")
														&& !dddAlterar.getText().toString().equals(" ")
														&& dddAlterar.getText().toString()!=null)
														|| (!ramalAlterar.getText().toString().equals("")
																&& !ramalAlterar.getText().toString().equals(" ")
																&& ramalAlterar.getText().toString()!=null))
																&& (telefoneAlterar.getText().toString().equals("")
																		|| telefoneAlterar.getText().toString().equals(" ")
																		|| telefoneAlterar.getText().toString()==null)){
													Util.exibirMsgAlerta(R.string.erro_verificar_telefone, ManterDadosAbaClienteActivity.this);
												}else if((!dddAlterar.getText().toString().equals("")
														&& !dddAlterar.getText().toString().equals(" ")
														&& dddAlterar.getText().toString()!=null)
														&& (!telefoneAlterar.getText().toString().equals("")
																&& !telefoneAlterar.getText().toString().equals(" ")
																&& telefoneAlterar.getText().toString()!=null)){
													if(dddAlterar.getText().toString().equals("81") 
															|| dddAlterar.getText().toString().equals("87")){
														if(telefoneAlterar.getText().toString().length()==8){
																OrgaoExpedidorRG objetoOrgaoExpedidorRG = new OrgaoExpedidorRG();
																objetoOrgaoExpedidorRG = (OrgaoExpedidorRG)orgaoExpedidorAlterar.getAdapter().getItem(orgaoExpedidorAlterar.getSelectedItemPosition());
																if(objetoOrgaoExpedidorRG.getId()==0 && objetoOrgaoExpedidorRG.getDescricao()==null){
																	objetoOrgaoExpedidorRG.setId(null);
																}
																clienteInformadoEmCampoInsertUpdate.setOrgaoExpeditorRG(objetoOrgaoExpedidorRG);
																UnidadeFederacao objetoUnidadeFederacao = new UnidadeFederacao();
																objetoUnidadeFederacao = (UnidadeFederacao)ufAlterar.getAdapter().getItem(ufAlterar.getSelectedItemPosition());
																if(objetoUnidadeFederacao.getId()==0 && objetoUnidadeFederacao.getDescricao()==null){
																	objetoUnidadeFederacao.setId(null);
																}
																clienteInformadoEmCampoInsertUpdate.setUnidadeFederacao(objetoUnidadeFederacao);
																		try {
																			clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddAlterar.getText().toString());
																			clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneAlterar.getText().toString());
																			clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalAlterar.getText().toString());
																			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																			clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
																			if(clienteInformadoEmCampo==null){
																				Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
																			}else if(clienteInformadoEmCampo!=null){
																				Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate,clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
																			}
																			new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
							                                                .setIcon(R.drawable.done)
							                                                .setNeutralButton("OK",
							                                                                  new DialogInterface.OnClickListener() {
							                                                                      @Override
							                                                                      public void onClick(
							                                                                              DialogInterface dialog,
							                                                                              int which) {
							                                                                      }
							                                                                  })
							                                                .show();
																		} catch (Exception e) {
																			e.printStackTrace();
																		}
															}else{
																Util.exibirMsgAlerta(R.string.erro_verifica_telefone_quantidade_digito, ManterDadosAbaClienteActivity.this);
															}
														}else{
															Util.exibirMsgAlerta(R.string.erro_verifica_ddd_81_87, ManterDadosAbaClienteActivity.this);
														}
													}else{
														if(((ufAlterar.getSelectedItem().toString()!=null
																&& orgaoExpedidorAlterar.getSelectedItem().toString()==null)
																|| (ufAlterar.getSelectedItem().toString()==null
																&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null)
																|| (ufAlterar.getSelectedItem().toString()!=null 
																&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null))
																&& (rgAlterar.getText().toString().equals("")
																		|| rgAlterar.getText().toString().equals(" ") 
																		|| rgAlterar.getText().toString()==null)){
															Util.exibirMsgAlerta(R.string.erro_verificar_rg, ManterDadosAbaClienteActivity.this);
														}else{
															clienteInformadoEmCampoInsertUpdate.setRg(rgAlterar.getText().toString());
															if(((!rgAlterar.getText().toString().equals("") 
																	&& !rgAlterar.getText().toString().equals(" ") 
																	&& rgAlterar.getText().toString()!=null)
																	|| (ufAlterar.getSelectedItem().toString()!=null))
																	&& orgaoExpedidorAlterar.getSelectedItemPosition()==0){
																Util.exibirMsgAlerta(R.string.erro_verificar_orgao_expedidor, ManterDadosAbaClienteActivity.this);
															}else{
																if(((!rgAlterar.getText().toString().equals("")
																		&& !rgAlterar.getText().toString().equals(" ")
																		&& rgAlterar.getText().toString()!=null)
																		|| orgaoExpedidorAlterar.getSelectedItemPosition()!=0)
																		&& ufAlterar.getSelectedItem().toString()==null){
																	Util.exibirMsgAlerta(R.string.erro_verificar_uf, ManterDadosAbaClienteActivity.this);
																}else{
																	OrgaoExpedidorRG objetoOrgaoExpedidorRG = new OrgaoExpedidorRG();
																	objetoOrgaoExpedidorRG = (OrgaoExpedidorRG)orgaoExpedidorAlterar.getAdapter().getItem(orgaoExpedidorAlterar.getSelectedItemPosition());
																	if(objetoOrgaoExpedidorRG.getId()==0 && objetoOrgaoExpedidorRG.getDescricao()==null){
																		objetoOrgaoExpedidorRG.setId(null);
																	}
																	clienteInformadoEmCampoInsertUpdate.setOrgaoExpeditorRG(objetoOrgaoExpedidorRG);
																	UnidadeFederacao objetoUnidadeFederacao = new UnidadeFederacao();
																	objetoUnidadeFederacao = (UnidadeFederacao)ufAlterar.getAdapter().getItem(ufAlterar.getSelectedItemPosition());
																	if(objetoUnidadeFederacao.getId()==0 && objetoUnidadeFederacao.getDescricao()==null){
																		objetoUnidadeFederacao.setId(null);
																	}
																	clienteInformadoEmCampoInsertUpdate.setUnidadeFederacao(objetoUnidadeFederacao);
																		try {
																			clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddAlterar.getText().toString());
																			clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneAlterar.getText().toString());
																			clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalAlterar.getText().toString());
																			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																			clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
																			if(clienteInformadoEmCampo==null){
																				Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
																			}else if(clienteInformadoEmCampo!=null){
																				Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate,clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
																			}
																			new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
							                                                .setIcon(R.drawable.done)
							                                                .setNeutralButton("OK",
							                                                                  new DialogInterface.OnClickListener() {
							                                                                      @Override
							                                                                      public void onClick(
							                                                                              DialogInterface dialog,
							                                                                              int which) {
							                                                                      }
							                                                                  })
							                                                .show();
																		} catch (Exception e) {
																			e.printStackTrace();
																		}
																}
															}
														}
													}
												}//
											}//
										}//
								}else{
									Util.exibirMsgAlerta(R.string.erro_verificar_cpf_cnpj_valido_quantidade, ManterDadosAbaClienteActivity.this);
								}
							}else if(cpfCnpjAlterar.getText().toString().length()==14){
								if(Util.validateCNPJ(cpfCnpjAlterar.getText().toString())){
									if(((ufAlterar.getSelectedItem().toString()!=null
											&& orgaoExpedidorAlterar.getSelectedItem().toString()==null)
											|| (ufAlterar.getSelectedItem().toString()==null
											&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null)
											|| (ufAlterar.getSelectedItem().toString()!=null 
											&& orgaoExpedidorAlterar.getSelectedItem().toString()!=null))
											&& (rgAlterar.getText().toString().equals("")
													|| rgAlterar.getText().toString().equals(" ") 
													|| rgAlterar.getText().toString()==null)){
										Util.exibirMsgAlerta(R.string.erro_verificar_rg, ManterDadosAbaClienteActivity.this);
									}else{
										clienteInformadoEmCampoInsertUpdate.setRg(rgAlterar.getText().toString());
										if(((!rgAlterar.getText().toString().equals("") 
												&& !rgAlterar.getText().toString().equals(" ") 
												&& rgAlterar.getText().toString()!=null)
												|| (ufAlterar.getSelectedItem().toString()!=null))
												&& orgaoExpedidorAlterar.getSelectedItemPosition()==0){
											Util.exibirMsgAlerta(R.string.erro_verificar_orgao_expedidor, ManterDadosAbaClienteActivity.this);
										}else{
											if(((!rgAlterar.getText().toString().equals("")
													&& !rgAlterar.getText().toString().equals(" ")
													&& rgAlterar.getText().toString()!=null)
													|| orgaoExpedidorAlterar.getSelectedItemPosition()!=0)
													&& ufAlterar.getSelectedItem().toString()==null){
												Util.exibirMsgAlerta(R.string.erro_verificar_uf, ManterDadosAbaClienteActivity.this);
											}else{
												if(((!telefoneAlterar.getText().toString().equals("")
														&& !telefoneAlterar.getText().toString().equals(" ")
														&& telefoneAlterar.getText().toString()!=null)
														|| (!ramalAlterar.getText().toString().equals("")
																&& !ramalAlterar.getText().toString().equals(" ")
																&& ramalAlterar.getText().toString()!=null))
																&& (dddAlterar.getText().toString().equals("")
																		|| dddAlterar.getText().toString().equals(" ")
																		|| dddAlterar.getText().toString()==null)){
													Util.exibirMsgAlerta(R.string.erro_verificar_ddd, ManterDadosAbaClienteActivity.this);
												}else if(((!dddAlterar.getText().toString().equals("")
														&& !dddAlterar.getText().toString().equals(" ")
														&& dddAlterar.getText().toString()!=null)
														|| (!ramalAlterar.getText().toString().equals("")
																&& !ramalAlterar.getText().toString().equals(" ")
																&& ramalAlterar.getText().toString()!=null))
																&& (telefoneAlterar.getText().toString().equals("")
																		|| telefoneAlterar.getText().toString().equals(" ")
																		|| telefoneAlterar.getText().toString()==null)){
													Util.exibirMsgAlerta(R.string.erro_verificar_telefone, ManterDadosAbaClienteActivity.this);
												}else if((!dddAlterar.getText().toString().equals("")
														&& !dddAlterar.getText().toString().equals(" ")
														&& dddAlterar.getText().toString()!=null)
														&& (!telefoneAlterar.getText().toString().equals("")
																&& !telefoneAlterar.getText().toString().equals(" ")
																&& telefoneAlterar.getText().toString()!=null)){
													if(dddAlterar.getText().toString().equals("81") 
															|| dddAlterar.getText().toString().equals("87")){
														if(telefoneAlterar.getText().toString().length()==8){
															OrgaoExpedidorRG objetoOrgaoExpedidorRG = new OrgaoExpedidorRG();
															objetoOrgaoExpedidorRG = (OrgaoExpedidorRG)orgaoExpedidorAlterar.getAdapter().getItem(orgaoExpedidorAlterar.getSelectedItemPosition());
															if(objetoOrgaoExpedidorRG.getId()==0 && objetoOrgaoExpedidorRG.getDescricao()==null){
																objetoOrgaoExpedidorRG.setId(null);
															}
															clienteInformadoEmCampoInsertUpdate.setOrgaoExpeditorRG(objetoOrgaoExpedidorRG);
															UnidadeFederacao objetoUnidadeFederacao = new UnidadeFederacao();
															objetoUnidadeFederacao = (UnidadeFederacao)ufAlterar.getAdapter().getItem(ufAlterar.getSelectedItemPosition());
															if(objetoUnidadeFederacao.getId()==0 && objetoUnidadeFederacao.getDescricao()==null){
																objetoUnidadeFederacao.setId(null);
															}
															clienteInformadoEmCampoInsertUpdate.setUnidadeFederacao(objetoUnidadeFederacao);
															try {
																clienteInformadoEmCampoInsertUpdate.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs);
																clienteInformadoEmCampoInsertUpdate.setCpf("");
																clienteInformadoEmCampoInsertUpdate.setRg(rgAlterar.getText().toString());
																clienteInformadoEmCampoInsertUpdate.setCnpj(cpfCnpjAlterar.getText().toString());
																clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddAlterar.getText().toString());
																clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneAlterar.getText().toString());
																clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalAlterar.getText().toString());
																SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
																if(clienteInformadoEmCampo==null){
																	Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
																}else if(clienteInformadoEmCampo!=null){
																	Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate, clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
																}
																new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
						                                        .setIcon(R.drawable.done)
						                                        .setNeutralButton("OK",
						                                                          new DialogInterface.OnClickListener() {
						                                                              @Override
						                                                              public void onClick(
						                                                                      DialogInterface dialog,
						                                                                      int which) {
						                                                              }
						                                                          })
						                                        .show();
															} catch (Exception e) {
																e.printStackTrace();
															}
														}else{
															Util.exibirMsgAlerta(R.string.erro_verifica_telefone_quantidade_digito, ManterDadosAbaClienteActivity.this);
														}
													}else{
														Util.exibirMsgAlerta(R.string.erro_verifica_ddd_81_87, ManterDadosAbaClienteActivity.this);
													}
												}else{
													OrgaoExpedidorRG objetoOrgaoExpedidorRG = new OrgaoExpedidorRG();
													objetoOrgaoExpedidorRG = (OrgaoExpedidorRG)orgaoExpedidorAlterar.getAdapter().getItem(orgaoExpedidorAlterar.getSelectedItemPosition());
													if(objetoOrgaoExpedidorRG.getId()==0 && objetoOrgaoExpedidorRG.getDescricao()==null){
														objetoOrgaoExpedidorRG.setId(null);
													}
													clienteInformadoEmCampoInsertUpdate.setOrgaoExpeditorRG(objetoOrgaoExpedidorRG);
													UnidadeFederacao objetoUnidadeFederacao = new UnidadeFederacao();
													objetoUnidadeFederacao = (UnidadeFederacao)ufAlterar.getAdapter().getItem(ufAlterar.getSelectedItemPosition());
													if(objetoUnidadeFederacao.getId()==0 && objetoUnidadeFederacao.getDescricao()==null){
														objetoUnidadeFederacao.setId(null);
													}
													clienteInformadoEmCampoInsertUpdate.setUnidadeFederacao(objetoUnidadeFederacao);
													try {
														clienteInformadoEmCampoInsertUpdate.setOrdemServicoExecucaoOS(ordemServicoExecucaoOs);
														clienteInformadoEmCampoInsertUpdate.setCpf("");
														clienteInformadoEmCampoInsertUpdate.setCnpj(cpfCnpjAlterar.getText().toString());
														clienteInformadoEmCampoInsertUpdate.setDddTelefone(dddAlterar.getText().toString());
														clienteInformadoEmCampoInsertUpdate.setTelefone(telefoneAlterar.getText().toString());
														clienteInformadoEmCampoInsertUpdate.setRamalTelefone(ramalAlterar.getText().toString());
														SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
														clienteInformadoEmCampoInsertUpdate.setUltimaAlteracao(simpleDateFormat.format(new Date()));
														if(clienteInformadoEmCampo==null){
															Fachada.getInstance().inserir(clienteInformadoEmCampoInsertUpdate);
														}else if(clienteInformadoEmCampo!=null){
															Fachada.getInstance().atualizar(clienteInformadoEmCampoInsertUpdate, clienteInformadoEmCampoInsertUpdate.getOrdemServicoExecucaoOS().getId());
														}
														new AlertDialog.Builder(ManterDadosAbaClienteActivity.this).setTitle("Cliente atualizado com sucesso!")
				                                        .setIcon(R.drawable.done)
				                                        .setNeutralButton("OK",
				                                                          new DialogInterface.OnClickListener() {
				                                                              @Override
				                                                              public void onClick(
				                                                                      DialogInterface dialog,
				                                                                      int which) {
				                                                              }
				                                                          })
				                                        .show();
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
										}
									}
								}else{
									Util.exibirMsgAlerta(R.string.erro_verificar_cpf_cnpj_valido_quantidade, ManterDadosAbaClienteActivity.this);
								}
							}else if(cpfCnpjAlterar.getText().toString().length()!=11 && cpfCnpjAlterar.getText().toString().length()!=14){
								Util.exibirMsgAlerta(R.string.erro_verificar_cpf_cnpj_valido_quantidade, ManterDadosAbaClienteActivity.this);
							}
						}
					}
				}
			});
		}
    }
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ManterDadosAbaClienteActivity.this,
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
