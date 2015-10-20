package br.com.ipad.gsaneos.ui;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import br.com.ipad.gsaneos.bean.DebitoOrdemServico;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.fachada.Fachada;
import br.com.ipad.gsaneos.ui.R.style;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

/**
 * [UC1570] Apresentar Dados Aba Débito
 * 
 * @author Mariana Victor
 * @date 23/10/2013
 */
public class ApresentarDadosAbaDebitosActivity extends BaseActivity {
	
	private Intent intent;
	
	private OrdemServicoExecucaoOS ordemServicoExecOS;
	private Integer quantidadeOSExecutadas;
	private Integer quantidadeOS;

	TableLayout tabelaDebitos;
	TextView valorTotalContas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		intent = getIntent();
		ordemServicoExecOS = (OrdemServicoExecucaoOS)intent.getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		setContentView(R.layout.apresentar_dados_aba_debitos_activity);

		tabelaDebitos = (TableLayout) findViewById(R.id.tabelaDebitos);
		valorTotalContas = (TextView) findViewById(R.id.valorTotalContas);
		
		List<DebitoOrdemServico> colecaoDebitoOrdemServico = Fachada.getInstance()
				.pesquisarDebitosOS(this.ordemServicoExecOS.getId());
		
		if (colecaoDebitoOrdemServico != null 
				&& !colecaoDebitoOrdemServico.isEmpty()) {
			
			BigDecimal valorTotal = new BigDecimal("0");
			
			Iterator<DebitoOrdemServico> iterator = colecaoDebitoOrdemServico.iterator();
			
			while(iterator.hasNext()) {
				DebitoOrdemServico debitoOrdemServico = (DebitoOrdemServico) iterator.next();
				
				BigDecimal valorContaFormatada = Util.convertStringToBigDecimal(
						debitoOrdemServico.getValorConta().replace(".", "").replace(",", "."));
				valorTotal = valorTotal.add(valorContaFormatada);
						
				TextView anoMes = new TextView(this, null, style.row);
				anoMes.setText(debitoOrdemServico.getAnoMesConta());
				anoMes.setGravity(Gravity.CENTER); 
				anoMes.setPadding(4, 0, 0, 0);
	
				TextView vencimento = new TextView(this, null, style.row);
				vencimento.setText(debitoOrdemServico.getDataVencimentoConta());
				vencimento.setGravity(Gravity.CENTER); 
				
				TextView situacao = new TextView(this, null, style.row);
				situacao.setText(debitoOrdemServico.getSituacaoConta());
				situacao.setGravity(Gravity.CENTER); 
				
				TextView valorConta = new TextView(this, null, style.row);
				valorConta.setText(debitoOrdemServico.getValorConta());
				valorConta.setGravity(Gravity.RIGHT); 
				valorConta.setPadding(0, 0, 4, 0);
				
				TableRow table_row = new TableRow(this);
				table_row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				table_row.setGravity(Gravity.CENTER);
				table_row.addView(anoMes);
				table_row.addView(vencimento);
				table_row.addView(situacao);
				table_row.addView(valorConta);

				tabelaDebitos.addView(table_row);
			}
			
			valorTotalContas.setText(getString(R.string.valor_total_contas,Util.formatarMoedaReal(valorTotal)));
			
		}
		
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ApresentarDadosAbaDebitosActivity.this,
                                    RegistrarFotosActivity.class);
        		intent.putExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS, ordemServicoExecOS);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS, quantidadeOSExecutadas);
        		intent.putExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS, quantidadeOS);
                break;
        }

        startActivity(intent);

        return super.onMenuItemSelected(featureId, item);
    }
	
}
