package edu.fatec.questionario.services;

import edu.fatec.questionario.model.Elemento;
import edu.fatec.questionario.model.Secao;
import edu.fatec.questionario.repository.ElementoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementoService {
    private ElementoRepository elementoRepository;

    public ElementoService(ElementoRepository elementoRepository) {
        this.elementoRepository = elementoRepository;
    }

    public List<Elemento> procurarPorSecao(Secao secao) {
        return elementoRepository.findBySecao(secao);
    }

    public List<Elemento> procurarTodos() {
        return elementoRepository.findAll();
    }
}
