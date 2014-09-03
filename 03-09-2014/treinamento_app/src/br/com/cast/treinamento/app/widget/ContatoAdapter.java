package br.com.cast.treinamento.app.widget;

import java.util.List;

import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.domain.Contato;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContatoAdapter extends BaseAdapter {

	private List<Contato> itens;
	private Activity contexto;
	
	public ContatoAdapter(Activity contexto, List<Contato> itens) {
		super();
		this.itens = itens;
		this.contexto = contexto;
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public Contato getItem(int posicao) {
		return itens.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		Contato item = getItem(posicao);
		return item.getId();
	}

	@Override
	public View getView(int posicao, View arg1, ViewGroup viewPai) {
		Contato item = getItem(posicao);
		
		LayoutInflater layoutInflater = contexto.getLayoutInflater();
		View layoutItem  = layoutInflater.inflate(R.layout.item_list_view_contato, null);
		
		TextView lblNome = (TextView) layoutItem.findViewById(R.id.lblNome);
		lblNome.setText(item.getNome());
		
		TextView lblTelefone = (TextView) layoutItem.findViewById(R.id.lblTelefone);
		lblNome.setText(item.getTelefone());
		
		if (posicao%2 == 0) {
			layoutItem.setBackgroundColor(contexto.getResources().getColor(R.color.urso_quando_foge));
		}
		
		return layoutItem;
	}

}
