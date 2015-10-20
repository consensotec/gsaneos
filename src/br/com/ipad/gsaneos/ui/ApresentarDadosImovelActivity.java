package br.com.ipad.gsaneos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.bean.TipoServico;
import br.com.ipad.gsaneos.util.ConstantesSistema;
import br.com.ipad.gsaneos.util.Util;

public class ApresentarDadosImovelActivity extends BaseActivity {
	
	private Intent intent;
	
	OrdemServicoExecucaoOS ordemServicoExecOS;
	Integer quantidadeOSExecutadas;
	Integer quantidadeOS;
	TipoServico tipoServico;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.apresentar_dados_imovel_activity);
		
		//Variáveis
		ordemServicoExecOS = (OrdemServicoExecucaoOS) getIntent().getSerializableExtra(ConstantesSistema.ORDEM_SERVICO_EXECUCAO_OS);
		quantidadeOSExecutadas = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUTADAS);
		quantidadeOS = (Integer) getIntent().getSerializableExtra(ConstantesSistema.QTD_ORDEM_SERVICO_EXECUCAO_OS);
		
		//Preenchendo os campos da view
		TextView inscricaoTv = (TextView) findViewById(R.id.inscricao_dados_imovel);
		TextView matriculaTv = (TextView) findViewById(R.id.matricula_dados_imovel);
		TextView perfilTv = (TextView) findViewById(R.id.perfil_dados_imovel);
		TextView numEconomiasTv = (TextView) findViewById(R.id.num_economias_dados_imovel);
		TextView categoriaTv = (TextView) findViewById(R.id.categoria_dados_imovel);
		TextView grupoTv = (TextView) findViewById(R.id.grupo_dados_imovel);
		TextView enderecoTv = (TextView) findViewById(R.id.endereco_dados_imovel);
		TextView situacaoAguaTv = (TextView) findViewById(R.id.situacao_lig_agua_dados_imovel);
		TextView consumoMedioTv = (TextView) findViewById(R.id.consumo_medio_dados_imovel);
		TextView situacaoEsgotoTv = (TextView) findViewById(R.id.situacao_lig_esgoto_dados_imovel);
		TextView numeroHidrTv = (TextView) findViewById(R.id.numero_dados_imovel);
		TextView marcaHidrTv = (TextView) findViewById(R.id.marca_dados_imovel);
		TextView localInstHidrTv = (TextView) findViewById(R.id.local_inst_dados_imovel);
		TextView protecaoHidrTv = (TextView) findViewById(R.id.protecao_dados_imovel);
		TextView ultimaAlteracaoTv = (TextView) findViewById(R.id.ultima_alteracao_dados_imovel);
		
		inscricaoTv.setText(ordemServicoExecOS.getInscricao());
		matriculaTv.setText(ordemServicoExecOS.getMatricula());
		perfilTv.setText(ordemServicoExecOS.getPerfilImovel());
		numEconomiasTv.setText(ordemServicoExecOS.getNumeroEconomias().toString());
		categoriaTv.setText(ordemServicoExecOS.getCategoria());
		grupoTv.setText(ordemServicoExecOS.getGrupo());
		enderecoTv.setText(ordemServicoExecOS.getEndereco());
		situacaoAguaTv.setText(ordemServicoExecOS.getLigacaoAguaSituacao());
		situacaoEsgotoTv.setText(ordemServicoExecOS.getLigacaoEsgotoSituacao());
		consumoMedioTv.setText(ordemServicoExecOS.getConsumoMedio().toString());
		numeroHidrTv.setText(ordemServicoExecOS.getNumeroHidrometro());
		marcaHidrTv.setText(ordemServicoExecOS.getHidrometroMarca());
		localInstHidrTv.setText(ordemServicoExecOS.getDescricaoLocalHistalacaoHidrometro());
		protecaoHidrTv.setText(ordemServicoExecOS.getDescricaoHidrometroProtecao());
		ultimaAlteracaoTv.setText(Util.convertDateToDateOnlyStr(ordemServicoExecOS.getUltimaAlteracao()));
		
	}

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 1:
            	intent = new Intent(ApresentarDadosImovelActivity.this,
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
