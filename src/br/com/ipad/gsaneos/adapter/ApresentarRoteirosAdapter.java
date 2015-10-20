package br.com.ipad.gsaneos.adapter;

import java.util.List;
import br.com.ipad.gsaneos.bean.OrdemServicoExecucaoOS;
import br.com.ipad.gsaneos.ui.R;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * [UC1496] - Apresentar Roteiro para Execu��o de OS de Cobran�a.
 * 
 * @author Jonathan Marcos
 * @date 14/06/2013
 */
public class ApresentarRoteirosAdapter extends BaseAdapter {
	private List<OrdemServicoExecucaoOS> listaOrdemServicoExecucaoOs;
	private LayoutInflater layoutInflater;
	
	public ApresentarRoteirosAdapter(Context context,List<OrdemServicoExecucaoOS> lista){
		this.listaOrdemServicoExecucaoOs = lista;
		layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaOrdemServicoExecucaoOs.size();
	}

	@Override
	public Object getItem(int position) {
		return listaOrdemServicoExecucaoOs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int color = (position % 2 == 0) ? Color.TRANSPARENT : Color.parseColor("#5D5F5F");
		OrdemServicoExecucaoOS objetoOrdemServicoExecucaoOs = listaOrdemServicoExecucaoOs.get(position);
		convertView = layoutInflater.inflate(R.layout.apresentar_roteiros_adapter_row, null);
		convertView.setBackgroundColor(color);
		ImageView imagem = (ImageView)convertView.findViewById(R.id.imagemSituacao);

		TextView ordem = (TextView)convertView.findViewById(R.id.ordem);
		ordem.setGravity(Gravity.CENTER_VERTICAL);

		TextView endereco = (TextView)convertView.findViewById(R.id.endereco);
		if(objetoOrdemServicoExecucaoOs.getIconeAtual() == null)
			imagem.setImageResource(R.drawable.tostart);
		else
			imagem.setImageResource(objetoOrdemServicoExecucaoOs.getIconeAtual());
		
		ordem.setText(objetoOrdemServicoExecucaoOs.getNumeroSequencial().toString());
		endereco.setText(objetoOrdemServicoExecucaoOs.getEndereco()+"\n"+objetoOrdemServicoExecucaoOs.getInscricao());
		convertView.setTag(getItem(position));
		return convertView;
	}
	
	public void atualizarLista(List<OrdemServicoExecucaoOS> listaOrdemServicoExecucaoOs) {
	    this.listaOrdemServicoExecucaoOs = listaOrdemServicoExecucaoOs;
	    notifyDataSetChanged();
	}
	
}
