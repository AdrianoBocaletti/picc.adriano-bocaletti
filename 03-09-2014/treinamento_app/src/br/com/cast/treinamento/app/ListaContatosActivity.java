package br.com.cast.treinamento.app;

import java.util.List;

import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaContatosActivity extends LifeCicleActivity {
	
	private ListView listViewContatos;
	
	private Contato selecionado;
	
	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_lista_contatos);
		
		listViewContatos = (ListView) super.findViewById(R.id.listViewContatos);
		
		super.registerForContextMenu(listViewContatos); 

		listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {
			
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
			selecionado = (Contato)adapter.getItemAtPosition(posicao);	
			return false;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		switch (id) {
		case R.id.action_novo:
			Intent intent = new Intent(this,ContatoActivity.class);
			super.startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		carregarListView();
	}

	private void carregarListView() {
		List<Contato> contatos = ContatoService.getInstancia().listarTodos();
		ContatoAdapter adapter = new ContatoAdapter(this, contatos);
		listViewContatos.setAdapter(adapter);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contatos_context, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
int id = item.getItemId();
		
		switch (id) {
		case R.id.action_editar:
			Intent intent = new Intent(this,ContatoActivity.class);
			intent.putExtra("CHAVE CONTATO", selecionado);
			super.startActivity(intent);
			break;
			
		case R.id.action_excluir:
			new AlertDialog.Builder(this)
				.setTitle(R.string.confirmacao)
				.setMessage(getString(R.string.dialog_mensagem,selecionado.getNome()))
				.setPositiveButton(R.string.dialog_sim, new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ContatoService.getInstancia().excluir(selecionado);
						carregarListView();
					}
				})
				.setNeutralButton(R.string.dialog_nao, null)
				.create().show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
