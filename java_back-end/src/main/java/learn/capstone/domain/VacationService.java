package learn.capstone.domain;

import learn.capstone.data.VacationRepository;
import learn.capstone.models.Vacation;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class VacationService {
    private final VacationRepository repository;


    public VacationService(VacationRepository repository){
        this.repository=repository;
    }

    public List<Vacation> findAll(){
        return repository.findAll();
    }

    public Vacation findById(int vacationId){
        return repository.findById(vacationId);
    }

    public Result<Vacation> add(Vacation vacation){
        Result<Vacation> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Vacation>> violations = validator.validate(vacation);

        if(!violations.isEmpty()){
            for(ConstraintViolation<Vacation> violation : violations){
                result.addMessage(violation.getMessage(), ResultType.INVALID);
            }
            return result;
        }
        result.setPayload(repository.add(vacation));
        return result;
    }

    public Result<Vacation> update (Vacation vacation){
        Result<Vacation> result = new Result<>();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Vacation>> violations = validator.validate(vacation);

        if(!violations.isEmpty()){
            for(ConstraintViolation<Vacation> violation : violations){
                result.addMessage(violation.getMessage(), ResultType.NOT_FOUND);
            }
            return result;
        }

        if(vacation.getVacationId() <=0){
            result.addMessage("vacationId must be set for `update` operation", ResultType.INVALID);
            return result;
        }
        if (!repository.update(vacation)) {
            String msg = String.format("vacationId: %s, not found", vacation.getVacationId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int vacationId){
        return repository.deleteById(vacationId);
    }

}
