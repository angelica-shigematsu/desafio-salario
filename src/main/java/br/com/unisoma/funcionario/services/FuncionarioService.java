package br.com.unisoma.funcionario.services;

import br.com.unisoma.funcionario.dto.FuncionarioDTO;
import br.com.unisoma.funcionario.models.Funcionario;
import br.com.unisoma.funcionario.repositories.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario create(FuncionarioDTO funcionarioDTO) {

        Funcionario funcionario = convertEntity(funcionarioDTO);
        return this.repository.save(funcionario);
    }

    private Funcionario convertEntity(FuncionarioDTO dto) {
        Funcionario funcionario= new Funcionario();
        funcionario.setFullname(dto.getFullname());
        funcionario.setCpf(dto.getCpf());
        funcionario.setBirthDate(dto.getBirthDate());
        funcionario.setPhone(dto.getPhone());
        funcionario.setAddress(dto.getAddress());
        funcionario.setSalary(dto.getSalary());
        return funcionario;
    }

    public Object updateSalary(String cpf) {
        String message = "";
        double newSalary, oldSalary, reajustSalary = 0;
        String salary = "";
        try {
            if (cpf.isEmpty()) return message = "Campo vazio";

            Funcionario funcionario = this.repository.findByCpf(cpf);

            if (funcionario == null) return message = "Não foi possível achar esse cpf";

            int percentual = this.findPercentual(funcionario.getSalary());

            newSalary = this.calculateSalary(funcionario.getSalary(), percentual);

            oldSalary = funcionario.getSalary();

            reajustSalary = newSalary - oldSalary;

            DecimalFormat df =  new DecimalFormat();

            df.setMaximumFractionDigits(2);

            salary = df.format(reajustSalary);

            Optional<Object> updatedSalary = this.repository.findById(funcionario.getId()).map(data -> {
                data.setSalary(newSalary);
                return this.repository.save(data);
            });

            if (updatedSalary == null) return message = "Não foi possível atualizar";

            Map<String,String> showData = new HashMap<>();


            showData.put( "CPF", cpf);
            showData.put( "Novo salário", Double.toString(newSalary));
            showData.put( "Reajuste de ganho", salary);
            showData.put( "Em percetual", Double.toString(percentual));

            return showData;

        }catch(Error error) {
            return error;
        }
    }

    private int findPercentual(double salary) {
        int percentual = 0;
        if (salary <= 400.0) {
            percentual = 15;
        } else if (salary <= 800.0) {
            percentual= 12;
        } else if (salary <= 1200.0) {
            percentual = 10;
        } else if (salary <= 2000.0) {
            percentual = 7;
        } else {
            percentual = 4;
        }

        return percentual;
    }
    private double calculateSalary(double salary, int percentual) {
        return salary *= (1 + (percentual/100.0));
    }

    public Object printValueTax(String cpf) {
        String message = "";
        if (cpf.isEmpty()) return "Campo vazio";

        Funcionario funcionario = this.repository.findByCpf(cpf);

        if (funcionario == null) return "Não foi possível achar esse cpf";

        double amountTax = this.calculateTax(funcionario.getSalary());

        if (amountTax == 0.0) message = "Insento";

        if (amountTax > 0.0) message = "R$ " + amountTax;

        Map<String,String> showData = new HashMap<>();


        showData.put( "CPF", cpf);
        showData.put( "Imposto:", message);

        return showData;
    }


    private double calculateTax(double salary) {

        double valueTax = 0.0;

        double valueExceed = 0.0;

        double valueFloor = 0.0;

        if (salary <= 2000) {
            valueTax = 0.0;
        } else if (salary <= 3000){
            valueTax = (salary - 2000) * 0.08;
        } else if (salary <= 4500) {
            valueFloor = (3000 - 2000) * 0.08;
            valueExceed = (salary - 3000) * 0.18;
            valueTax = valueFloor + valueExceed;
        } else {
             valueFloor = (4500 - 2000) * 0.18;
             valueExceed = (salary - 4500) * 0.28;
             valueTax = valueFloor + valueExceed;
        }

        return valueTax;
    }

}
