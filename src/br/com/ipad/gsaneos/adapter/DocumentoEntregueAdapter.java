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
import br.com.ipad.gsaneos.bean.DocumentoEntregue;
import br.com.ipad.gsaneos.ui.R;

public class DocumentoEntregueAdapter extends BaseAdapter {

	List<DocumentoEntregue> listaDocumentoEntregue = new ArrayList<DocumentoEntregue>();
	LayoutInflater layoutInflaterDocumentoEntregue;
	private Context c;
	
	public DocumentoEntregueAdapter(Context context, List<DocumentoEntregue> lista){
		
		c = context;
		this.listaDocumentoEntregue = lista;
		DocumentoEntregue objReason = new DocumentoEntregue();
		objReason.setId(0);
		this.listaDocumentoEntregue.add(0, objReason);
		layoutInflaterDocumentoEntregue = (LayoutInflater) c
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	
	@Override
	public int getCount() {
		return listaDocumentoEntregue.size();
	}

	@Override
	public Object getItem(int position) {
		return listaDocumentoEntregue.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DocumentoEntregue documentoEntregue = listaDocumentoEntregue.get(position);
		convertView = layoutInflaterDocumentoEntregue.inflate(R.layout.documento_entregue_adapter, null);
		TextView documentoEntregueDescricao = (TextView)convertView.findViewById(R.id.documentoEntregueDescricao);
		if(position==0){
			documentoEntregueDescricao.setText("");
		}else{
			documentoEntregueDescricao.setText(documentoEntregue.getDocumento());
		}
		convertView.setTag(getItem(position));
		return convertView;
	}
}
