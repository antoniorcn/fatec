package cpb.cursos.services;

import cpb.cursos.model.Elemento;
import cpb.cursos.model.Secao;
import cpb.cursos.repository.ElementoRepository;
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
