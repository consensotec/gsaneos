package br.com.ipad.gsaneos.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.ipad.gsaneos.bean.HidrometroProtecao;
import br.com.ipad.gsaneos.ui.R;

public class HidrometroProtecaoAdapter extends BaseAdapter {
	List<HidrometroProtecao> listaHidrometroProtecao = new ArrayList<HidrometroProtecao>();
	private LayoutInflater layoutInflaterHidrometroProtecao;
	private Context c;
	
	public HidrometroProtecaoAdapter(Context context, List<HidrometroProtecao> lista){
		
		c = context;
		this.listaHidrometroProtecao = lista;
		HidrometroProtecao objReason = new HidrometroProtecao();
		objReason.setId(0);
		this.listaHidrometroProtecao.add(0, objReason);
		layoutInflaterHidrometroProtecao = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return listaHidrometroProtecao.size();
	}

	@Override
	public Object getItem(int position) {
		return listaHidrometroProtecao.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HidrometroProtecao hidrometroProtecao = listaHidrometroProtecao.get(position);
		convertView = layoutInflaterHidrometroProtecao.inflate(R.layout.hidrometro_protecao_adapter, null);
		TextView hidrometroProtecaoDescricao = (TextView)convertView.findViewById(R.id.hidrometroProtecaoDescricao);
		if(position==0){
			hidrometroProtecaoDescricao.setText("");
		}else{
			hidrometroProtecaoDescricao.setText(hidrometroProtecao.getDescricao());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}
	
	public int getPosition(String desc){
		Iterator<HidrometroProtecao> it = listaHidrometroProtecao.iterator();
		int pos = 0;
		int cont = 0;
		while(it.hasNext()){
			HidrometroProtecao prot = it.next();
			if(prot.getDescricao() != null && prot.getDescricao().equals(desc)){
				pos = cont;
				break;
			}
			cont++;
		}
		
		return pos;
	}

}
