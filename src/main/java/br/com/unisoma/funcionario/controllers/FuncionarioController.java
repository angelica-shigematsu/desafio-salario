package br.com.unisoma.funcionario.controllers;

import br.com.unisoma.funcionario.dto.FuncionarioDTO;
import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.services.CalculateTaxService;
import br.com.unisoma.funcionario.services.FuncionarioService;
import br.com.unisoma.funcionario.services.UpdateSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api")
public class FuncionarioController {
    @Autowired
    private FuncionarioService service;

    @Autowired
    private CalculateTaxService taxService;

    @Autowired
    private UpdateSalaryService salaryService;

    @PostMapping(value="adicionar/funcionario")
    public ResponseEntity<Object> createFuncionario(@RequestBody FuncionarioDTO dto) {
        Funcionario funcionario = service.create(dto);

        if (funcionario == null) return new ResponseEntity<>("CPF, salário e/ou telefone inválido", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(funcionario, HttpStatus.CREATED);
    }

    @PutMapping(value="calcular-salario/{cpf}")
    public ResponseEntity<Object> showNewSalary(@PathVariable String cpf) {

        Object updateSalary = salaryService.updateSalary(cpf);

        if (updateSalary instanceof String) return new ResponseEntity<>(updateSalary, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(updateSalary, HttpStatus.OK);
    }

    @GetMapping(value="calcular-imposto/{cpf}")
    public ResponseEntity<Object> showValueTax(@PathVariable String cpf) {
        Object result = taxService.printValueTax(cpf);

       if (result instanceof String) {
           return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
       }

       return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
