package com.br.alura.microservice.loja.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alura.microservice.loja.client.FornecedorClient;
import com.br.alura.microservice.loja.controller.dto.CompraDTO;
import com.br.alura.microservice.loja.controller.dto.InfoFornecedorDTO;
import com.br.alura.microservice.loja.controller.dto.InfoPedidoDTO;
import com.br.alura.microservice.loja.model.Compra;

@Service
public class CompraService {
	
	
	@Autowired
	private FornecedorClient fornecedorClient;
	

	public Compra realizaCompra(CompraDTO compra) {
		
				
		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
		
		System.out.println(info.getEndereco());
		
		
		Compra compraSalva = new Compra();
		
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		
		
		return compraSalva;
		
		
	}

}
