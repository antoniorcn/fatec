package edu.fatec.ementario.controller;

import edu.fatec.ementario.model.Curso;
import edu.fatec.ementario.model.Disciplina;
import edu.fatec.ementario.model.Faculdade;
import edu.fatec.ementario.model.dto.CursoDto;
import edu.fatec.ementario.repository.CursoRepository;
import edu.fatec.ementario.repository.FaculdadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curso")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "If-Match")
public class CursoController {

    @Autowired
    protected CursoRepository repository;

    @Autowired
    protected FaculdadeRepository faculdadeRepository;

    public CursoController(CursoRepository cursoRepository, FaculdadeRepository faculdadeRepository) {
        this.repository = cursoRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    public Curso cursoFromDto(CursoDto curso) {
        Curso c = new Curso();
        c.setId(curso.id);
        c.setNome(curso.nome);
        c.setDescricao(curso.descricao);
        c.setTipo(curso.tipo);
        Optional<Faculdade> f = faculdadeRepository.findById(curso.faculdade.getId());
        f.ifPresent(c::setFaculdade);
        return c;
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Curso> getAll() {
        List<Curso> entidades = repository.findAll();
        return entidades;
    }

    @GetMapping(value = "/getAllByFaculdade/{faculdadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Curso> getAll(@PathVariable("faculdadeId") Long faculdadeId) {
        List<Curso> entidades = repository.retrieveCursosByFaculdade(faculdadeId);
        return entidades;
    }

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity add(@RequestBody CursoDto entidade) {
        try {
            repository.save(cursoFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity save(@RequestBody CursoDto entidade) {
        try {
            repository.save(cursoFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity delete(@RequestBody CursoDto entidade) {
        try {
            repository.delete(cursoFromDto(entidade));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
