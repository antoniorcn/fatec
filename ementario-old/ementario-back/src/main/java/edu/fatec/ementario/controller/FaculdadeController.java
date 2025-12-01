package edu.fatec.ementario.controller;

import edu.fatec.ementario.model.Faculdade;
import edu.fatec.ementario.repository.FaculdadeRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faculdade")
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "If-Match")
public class FaculdadeController extends ControllerBase<Faculdade, Long, FaculdadeRepository>{

    public FaculdadeController(FaculdadeRepository faculdadeRepository) {
        super(faculdadeRepository);
    }

}
