package com.br.alura.microservice.fornecedor.repository;

import org.springframework.data.repository.CrudRepository;

import com.br.alura.microservice.fornecedor.model.Pedido;

public interface PedidoRepository extends CrudRepository<Pedido, Long>{

}
