package br.com.ipad.gsaneos.ui;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.TipoServico;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

public class ApresentarRoteiroTabsActivity extends BaseTabActivity {

	OrdemServicoExecucaoOS ordemServicoExecOS;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apresentar_roteiro_tabs);
		
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		//Variável Ordem de Servico selecionada
		ordemServicoExecOS = (OrdemServicoExecucaoOS) getIntent().getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		Integer qtdOrdemServicoExecOS = (Integer) getIntent().getIntExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS,0);
		
		
		//Preencher campos comuns das abas
		TextView tipoServicoRoteiroApresentacaoTv = (TextView) findViewById(R.id.tipoServicoRoteiroApresentacao);
		TextView osServicoRoteiroApresentacaoTv = (TextView) findViewById(R.id.osServicoRoteiroApresentacao);
		TextView qtdServicoRoteiroApresentacaoTv = (TextView) findViewById(R.id.qtdServicoRoteiroApresentacao);
		TextView dataServicoRoteiroApresentacaoTv = (TextView) findViewById(R.id.dataServicoRoteiroApresentacao);
		TextView ordemServicoRoteiroApresentacaoTv = (TextView) findViewById(R.id.ordemServicoRoteiroApresentacao);
		
		TipoServico tipoServico = Fachada.getInstance().pesquisarPorId(ordemServicoExecOS.getTipoServico().getId(), ordemServicoExecOS.getTipoServico());
		Integer quantidadeOSExecutadas = Fachada.getInstance().pesquisarQuantidadeOSExecutadas();
		
		tipoServicoRoteiroApresentacaoTv.setText(tipoServico.getDescricao());
		osServicoRoteiroApresentacaoTv.setText(ordemServicoExecOS.getNumeroOrdemServico().toString());
		qtdServicoRoteiroApresentacaoTv.setText(quantidadeOSExecutadas+"/"+qtdOrdemServicoExecOS.toString());
		dataServicoRoteiroApresentacaoTv.setText(Util.convertDateToDateOnlyStr(new Date()));
		ordemServicoRoteiroApresentacaoTv.setText(ordemServicoExecOS.getNumeroSequencial().toString());
		
		
		//Imóvel
		intent = new Intent().setClass(this, ApresentarDadosImovelActivity.class);
		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecOS);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, qtdOrdemServicoExecOS);
		spec = tabHost.newTabSpec("imoveis").setContent(intent).setIndicator(getText(R.string.str_imovel), null);
		tabHost.addTab(spec);
		
		//Cliente
		intent = new Intent().setClass(this, ManterDadosAbaClienteActivity.class);
		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecOS);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, qtdOrdemServicoExecOS);
		spec = tabHost.newTabSpec("clientes").setContent(intent).setIndicator(getText(R.string.str_cliente), null);
		tabHost.addTab(spec);

		boolean exibirIntentDebitos = false;
		
		//OS
		if (tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.TIPO_CORTE_LIGACAO_AGUA) == 0) {
			
			intent = new Intent().setClass(this, ManterDadosAbaCorteActivity.class);
			exibirIntentDebitos = true;
			
		} else if (tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.TIPO_RESTABELECIMENTO_LIGACAO_AGUA) == 0) {
			
			intent = new Intent().setClass(this, ManterDadosAbaRestabelecimentoActivity.class);
			
		} else if (tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.TIPO_SUPRESSAO_LIGACAO_AGUA) == 0) {
			
			intent = new Intent().setClass(this, ManterDadosAbaSupressaoActivity.class);
			exibirIntentDebitos = true;
			
		} else if (tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.TIPO_RELIGACAO_AGUA) == 0) {
			
			intent = new Intent().setClass(this, ManterDadosAbaReligacaoActivity.class);
			

		} else if (tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.TIPO_REMOCAO_HIDROMETRO) == 0) {
			
			intent = new Intent().setClass(this, ManterDadosAbaRemocaoHidrometroActivity.class);
			
		} else if(tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.SUBSTITUICAO_HIDROMETRO) == 0){
			
			intent = new Intent().setClass(this, ManterDadosAbaSubstituicaoHidrometroActivity.class);
	
		} else if(tipoServico.getNumeroCodigoConstante().compareTo(TipoServico.INSTALACAO_CAIXA_PROTECAO) == 0){
			
			intent = new Intent().setClass(this, ManterDadosAbaInstalacaoCaixaProtecao.class);
			
		} else {
			intent = new Intent().setClass(this, ManterDadosAbaFiscalizacaoActivity.class);
			exibirIntentDebitos = true;
		}
		
		if (exibirIntentDebitos) {
			//Débitos
			Intent intentDebitos = new Intent().setClass(this, ApresentarDadosAbaDebitosActivity.class);
			intentDebitos.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecOS);
			intentDebitos.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
			intentDebitos.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, qtdOrdemServicoExecOS);
			spec = tabHost.newTabSpec("debitos").setContent(intentDebitos).setIndicator(getText(R.string.str_debitos), null);
			tabHost.addTab(spec);
		}
		
		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecOS);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, qtdOrdemServicoExecOS);
		spec = tabHost.newTabSpec("clientes").setContent(intent).setIndicator(getText(R.string.str_os), null);
		tabHost.addTab(spec);
		
		
		tabHost.setCurrentTab(0);
		
	}
	
}
