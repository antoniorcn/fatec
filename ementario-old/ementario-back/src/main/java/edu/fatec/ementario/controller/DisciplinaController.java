package edu.fatec.ementario.controller;

import edu.fatec.ementario.model.Curso;
import edu.fatec.ementario.model.Disciplina;
import edu.fatec.ementario.model.Faculdade;
import edu.fatec.ementario.model.dto.CursoDto;
import edu.fatec.ementario.model.dto.DisciplinaDto;
import edu.fatec.ementario.repository.CursoRepository;
import edu.fatec.ementario.repository.DisciplinaRepository;
import edu.fatec.ementario.repository.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disciplina")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "If-Match")
public class DisciplinaController {


    @Autowired
    protected DisciplinaRepository repository;

    @Autowired
    protected CursoRepository cursoRepository;

    @Autowired
    protected FaculdadeRepository faculdadeRepository;

    public DisciplinaController(DisciplinaRepository repository, CursoRepository cursoRepository, FaculdadeRepository faculdadeRepository) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    public Disciplina disciplinaFromDto(DisciplinaDto disciplina) {
        Disciplina d = new Disciplina();
        d.setId(disciplina.id);
        d.setNome(disciplina.nome);
        d.setObjetivo(disciplina.objetivo);
        d.setEmenta(disciplina.ementa);
        d.setMetodologiaProposta(disciplina.metodologiaProposta);
        d.setInstrumentosAvaliacao(disciplina.instrumentosAvaliacao);
        d.setBibliografiaBasica(disciplina.bibliografiaBasica);
        d.setBibliografiaComplementar(disciplina.bibliografiaComplementar);
        d.setDescricao(disciplina.descricao);
        Optional<Faculdade> f = faculdadeRepository.findById(disciplina.faculdade.getId());
        Optional<Curso> c = cursoRepository.findById(disciplina.curso.getId());
        f.ifPresent(d::setFaculdade);
        c.ifPresent(d::setCurso);
        return d;
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Disciplina> getAll() {
        List<Disciplina> entidades = repository.findAll();
        return entidades;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody DisciplinaDto entidade) {
        try {
            repository.save(disciplinaFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody DisciplinaDto entidade) {
        try {
            repository.save(disciplinaFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@RequestBody DisciplinaDto entidade) {
        try {
            repository.delete(disciplinaFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
