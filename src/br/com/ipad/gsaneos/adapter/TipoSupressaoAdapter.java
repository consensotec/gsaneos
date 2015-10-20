package br.com.ipad.gsaneos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.SupressaoTipo;
import br.com.ipad.gsaneos.ui.R;

public class TipoSupressaoAdapter extends BaseAdapter {
	List<SupressaoTipo> listaTipoSupressao = new ArrayList<SupressaoTipo>();
	private LayoutInflater layoutInflaterTipoSupressao;
	private Context c;
	
	public TipoSupressaoAdapter(Context context, List<SupressaoTipo> lista){
		
		c = context;
		this.listaTipoSupressao = lista;
		SupressaoTipo objReason = new SupressaoTipo();
		objReason.setId(0);
		this.listaTipoSupressao.add(0, objReason);
		layoutInflaterTipoSupressao = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaTipoSupressao.size();
	}

	@Override
	public Object getItem(int position) {
		return listaTipoSupressao.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SupressaoTipo supressaoTipo = listaTipoSupressao.get(position);
		convertView = layoutInflaterTipoSupressao.inflate(R.layout.motivo_encerramento_adapter, null);
		TextView supressaoTipoDescricao = (TextView)convertView.findViewById(R.id.motivoEncerramentoDescricao);
		if(position==0){
			supressaoTipoDescricao.setText("");
		}else{
			supressaoTipoDescricao.setText(supressaoTipo.getDescricao());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}

}
