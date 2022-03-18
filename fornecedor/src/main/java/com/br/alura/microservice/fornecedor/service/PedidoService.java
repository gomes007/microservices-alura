package com.br.alura.microservice.fornecedor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alura.microservice.fornecedor.dto.ItemDoPedidoDTO;
import com.br.alura.microservice.fornecedor.model.Pedido;
import com.br.alura.microservice.fornecedor.model.PedidoItem;
import com.br.alura.microservice.fornecedor.model.Produto;
import com.br.alura.microservice.fornecedor.repository.PedidoRepository;
import com.br.alura.microservice.fornecedor.repository.ProdutoRepository;



@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;


	
	public Pedido getPedidoPorId(Long id) {
		return this.pedidoRepository.findById(id).orElse(new Pedido());
	}
	
	
	
	
	//converter para DTO e fazer link do pedido de acordo com o id do produto cadastrado
	private List<PedidoItem> toPedidoItem(List<ItemDoPedidoDTO> itens){
		
		//monta uma lista de todos os id's dos itens pedidos e guarda na variavel idsProdutos
		List<Long> idsProdutos = 
				itens
				.stream()
				.map(item -> item.getId())
				.collect(Collectors.toList());
		
		//localiza id dos produtos em uma lista de id's chamada idsProdutos
		List<Produto> produtosDoPedido = produtoRepository.findByIdIn(idsProdutos);
		
		
		//faz um map para localizar o id do pedido e pesquisa na entidade Produto o id equivalente e usa o set do id do produto na entidade PedidoItem
		List<PedidoItem> pedidoItens =
				itens
				.stream()
				.map(item -> {
					Produto produto = 
						produtosDoPedido
						.stream()
						.filter(p -> p.getId() == item.getId())
						.findFirst().get();
					
					PedidoItem pedidoItem = new PedidoItem();
					pedidoItem.setProduto(produto);
					pedidoItem.setQuantidade(item.getQuantidade());
					return pedidoItem;				
				}).collect(Collectors.toList());
		
		return pedidoItens;
	}
	
	
	
	//salvar um pedido
	public Pedido realizaPedido(List<ItemDoPedidoDTO> itens) {
		
		if(itens == null) {
			return null;
		}
		
		List<PedidoItem> pedidoItens = toPedidoItem(itens);
		Pedido pedido = new Pedido(pedidoItens);
		pedido.setTempoDePreparo(itens.size());
		return pedidoRepository.save(pedido);
	}
	
	
}
