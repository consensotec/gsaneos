package br.com.ipad.gsaneos.repositorios;

import java.util.List;

import br.com.ipad.gsaneos.bean.Foto;
import br.com.ipad.gsaneos.excecoes.RepositorioException;

public interface IRepositorioFoto {

	public List<Foto> pesquisarFotosNaoTransmitidas(Integer idOS)
			throws RepositorioException;

	/**
	 * [UC1510] Registrar Fotos de OS de Cobrança
	 * 
	 * @author Mariana Victor
	 * @date 27/06/2013
	 */
	public Foto pesquisarFoto(Integer idOrdemServico, Integer idTipoFoto)
			throws RepositorioException;

	/**
	 * [UC1508] Manter Dados Aba Supressão de Ramal
	 * 
	 * @author Mariana Victor
	 * @date 28/06/2013
	 */
	public boolean verificarFotosInformadas(Integer idOrdemServico)
			throws RepositorioException;

}