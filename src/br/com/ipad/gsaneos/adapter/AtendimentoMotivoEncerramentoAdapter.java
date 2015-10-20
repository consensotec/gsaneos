package br.com.ipad.gsaneos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.AtendimentoMotivoEncerramento;
import br.com.ipad.gsaneos.ui.R;

public class AtendimentoMotivoEncerramentoAdapter extends BaseAdapter {
	List<AtendimentoMotivoEncerramento> listaMotivoEncerramento = new ArrayList<AtendimentoMotivoEncerramento>();
	private LayoutInflater layoutInflaterMotivoEncerramento;
	private Context c;
	
	public AtendimentoMotivoEncerramentoAdapter(Context context, List<AtendimentoMotivoEncerramento> lista){
		
		c = context;
		this.listaMotivoEncerramento = lista;
		AtendimentoMotivoEncerramento objReason = new AtendimentoMotivoEncerramento();
		objReason.setId(0);
		this.listaMotivoEncerramento.add(0, objReason);
		layoutInflaterMotivoEncerramento = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaMotivoEncerramento.size();
	}

	@Override
	public Object getItem(int position) {
		return listaMotivoEncerramento.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AtendimentoMotivoEncerramento motivoEncerramento = listaMotivoEncerramento.get(position);
		convertView = layoutInflaterMotivoEncerramento.inflate(R.layout.motivo_encerramento_adapter, null);
		TextView motivoEncerramentoDescricao = (TextView)convertView.findViewById(R.id.motivoEncerramentoDescricao);
		if(position==0){
			motivoEncerramentoDescricao.setText("");
		}else{
			motivoEncerramentoDescricao.setText(motivoEncerramento.getDescricao());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}

}
