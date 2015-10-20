package br.com.ipad.gsaneos.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.CorteTipo;
import br.com.ipad.gsaneos.ui.R;

public class CorteTipoAdapter extends BaseAdapter {
	List<CorteTipo> listaCorteTipo = new ArrayList<CorteTipo>();
	LayoutInflater layoutInflaterCorteTipo;
	private Context c;
	
	public CorteTipoAdapter(Context context, List<CorteTipo> lista){
		
		c = context;
		this.listaCorteTipo = lista;
		CorteTipo objReason = new CorteTipo();
		objReason.setId(0);
		this.listaCorteTipo.add(0, objReason);
		layoutInflaterCorteTipo = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return listaCorteTipo.size();
	}

	@Override
	public Object getItem(int position) {
		return listaCorteTipo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CorteTipo corteTipo = listaCorteTipo.get(position);
		convertView = layoutInflaterCorteTipo.inflate(R.layout.corte_tipo_adapter, null);
		TextView corteTipoDescricao = (TextView)convertView.findViewById(R.id.corteTipoDescricao);
		if(position==0){
			corteTipoDescricao.setText("");
		}else{
			corteTipoDescricao.setText(corteTipo.getDescricao());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}
}
