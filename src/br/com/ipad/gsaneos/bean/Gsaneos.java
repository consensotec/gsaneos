package br.com.ipad.gsaneos.bean;

import android.app.Application;

public class Gsaneos extends Application {

	private AgenteComercial usuarioLogado;

	public AgenteComercial getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(AgenteComercial usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
}
