package com.br.alura.microservice.fornecedor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.alura.microservice.fornecedor.model.InfoFornecedor;
import com.br.alura.microservice.fornecedor.repository.InfoRepository;

@Service
public class InfoService {
	
	@Autowired
	private InfoRepository infoRepository;

	public InfoFornecedor getInfoPorEstado(String estado) {
		
		return infoRepository.findByEstado(estado);
		
	}

}
