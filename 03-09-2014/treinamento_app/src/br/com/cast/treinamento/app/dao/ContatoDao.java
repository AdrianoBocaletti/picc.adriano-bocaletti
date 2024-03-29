package br.com.cast.treinamento.app.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.treinamento.app.domain.Contato;

public class ContatoDao {
	
	private static long SEQUENCE = 0;
	private static final List<Contato> REPOSITORIO = new ArrayList<>();
	private static final ContatoDao INSTANCIA = new ContatoDao();

	private ContatoDao(){
		super();
	}
	
	public static ContatoDao getInstancia(){
		return INSTANCIA;
	}
	
	public List<Contato> listarTodos(){
		return REPOSITORIO;
	}
	
	public void excluir(Contato contato){
		REPOSITORIO.remove(contato);
	}
	
	public void salvar(Contato contato){
		if(contato.getId() == null){
			contato.setId(SEQUENCE++);
			REPOSITORIO.add(contato);
		}else{
			REPOSITORIO.set(REPOSITORIO.indexOf(contato), contato);
		}
	}
}
