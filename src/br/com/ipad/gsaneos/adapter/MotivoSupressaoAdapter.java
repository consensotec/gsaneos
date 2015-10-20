package br.com.ipad.gsaneos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.MotivoSupressao;
import br.com.ipad.gsaneos.ui.R;

public class MotivoSupressaoAdapter extends BaseAdapter {
	List<MotivoSupressao> listaMotivoSupressao = new ArrayList<MotivoSupressao>();
	private LayoutInflater layoutInflaterMotivoSupressao;
	private Context c;
	
	public MotivoSupressaoAdapter(Context context, List<MotivoSupressao> lista){
		
		c = context;
		this.listaMotivoSupressao = lista;
		MotivoSupressao objReason = new MotivoSupressao();
		objReason.setId(0);
		this.listaMotivoSupressao.add(0, objReason);
		layoutInflaterMotivoSupressao = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaMotivoSupressao.size();
	}

	@Override
	public Object getItem(int position) {
		return listaMotivoSupressao.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MotivoSupressao motivoSupressao = listaMotivoSupressao.get(position);
		convertView = layoutInflaterMotivoSupressao.inflate(R.layout.motivo_encerramento_adapter, null);
		TextView motivoSupressaoDescricao = (TextView)convertView.findViewById(R.id.motivoEncerramentoDescricao);
		if(position==0){
			motivoSupressaoDescricao.setText("");
		}else{
			motivoSupressaoDescricao.setText(motivoSupressao.getDescricao());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}

}
