package br.com.unisoma.funcionario.controllers;

import br.com.unisoma.funcionario.dto.FuncionarioDTO;
import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.services.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value="api")
public class FuncionarioController {
    @Autowired
    private FuncionarioService service;
    @PostMapping(value="adicionar/funcionario")
    public ResponseEntity<Funcionario> createFuncionario(@RequestBody FuncionarioDTO dto) {
        Funcionario funcionario = service.create(dto);

        return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
    }

    @PutMapping(value="calculateSalario/{cpf}")
    public ResponseEntity<Object> showNewSalary(@PathVariable String cpf) {

        Object updateSalary = service.updateSalary(cpf);

        if (updateSalary instanceof String) return new ResponseEntity<>(updateSalary, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(updateSalary, HttpStatus.OK);
    }

    @GetMapping(value="calculateImposto/{cpf}")
    public ResponseEntity<Object> showValueTax(@PathVariable String cpf) {
        Object result = service.printValueTax(cpf);

       if (result instanceof String) {
           return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
