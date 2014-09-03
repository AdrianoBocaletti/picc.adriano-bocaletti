package br.com.cast.treinamento.app;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;
import br.com.cast.treinamento.app.service.ContatoService;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class ContatoActivity extends LifeCicleActivity {

	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar ratingBarRelevancia;
	private Button btnSalvar;
	private Contato contato;
	
	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		
		bindingElemtentosLayout();
		
		contato = (Contato) getIntent().getSerializableExtra("CHAVE CONTATO");
		if (contato == null) {
			contato = new Contato();
		}else{
			carregarElementosLayout();
		}
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				populaContato();
				try {
					ContatoService.getInstancia().salvar(contato);
					ContatoActivity.this.finish();
				} catch (ExcecaoNegocio excecao) {
					Drawable drawable = getResources().getDrawable(R.drawable.ic_alerts_and_states_warning);
					drawable.setBounds(0,0,50,50);
					for (Entry<Integer, Integer> erro : excecao.getMapaErros().entrySet()) {
						EditText campoErro = (EditText)findViewById(erro.getKey());
						campoErro.setError(getString(erro.getValue()), drawable);
					}
				}
			}

		});
		
	}

	private void populaContato() {
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setRelevancia(ratingBarRelevancia.getRating());
	}
	
	private void carregarElementosLayout() {
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());
		ratingBarRelevancia.setRating(contato.getRelevancia());
	}

	private void bindingElemtentosLayout() {
		txtNome = (EditText) findViewById(R.id.nome);
		txtEndereco = (EditText) findViewById(R.id.endereco);
		txtSite = (EditText) findViewById(R.id.site);
		txtTelefone = (EditText) findViewById(R.id.telefone);
		ratingBarRelevancia = (RatingBar) findViewById(R.id.ratingBarRelevancia);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
	}

}
