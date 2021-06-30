package learn.capstone.domain;

import learn.capstone.data.VacationRepository;
import learn.capstone.data.VacationStopsRepository;
import learn.capstone.models.Vacation;
import learn.capstone.models.VacationStops;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

@Service
public class VacationService {
    private final VacationRepository vacationRepository;
    private final VacationStopsRepository vacationStopsRepository;

    public VacationService(VacationRepository vacationRepository, VacationStopsRepository vacationStopsRepository) {
        this.vacationRepository = vacationRepository;
        this.vacationStopsRepository = vacationStopsRepository;
    }

    public List<Vacation> findAll(){
        return vacationRepository.findAll();
    }

    public Vacation findById(int vacationId){
        return vacationRepository.findById(vacationId);
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
        result.setPayload(vacationRepository.add(vacation));
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
        if (!vacationRepository.update(vacation)) {
            String msg = String.format("vacationId: %s, not found", vacation.getVacationId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean deleteById(int vacationId){
        return vacationRepository.deleteById(vacationId);
    }


    public Result<Void> addLocation(VacationStops vacationStops) {
        Result<Void> result = validate(vacationStops);
        if (!result.isSuccess()) {
            return result;
        }

        if (!vacationStopsRepository.add(vacationStops)) {
            result.addMessage("location not added", ResultType.INVALID);
        }

        return result;
    }

    public Result<Void> updateLocation(VacationStops vacationStops) {
        Result<Void> result = validate(vacationStops);
        if (!result.isSuccess()) {
            return result;
        }

        if (!vacationStopsRepository.update(vacationStops)) {
            String msg = String.format("update failed for vacation id %s, location id %s: not found",
                    vacationStops.getVacationId(),
                    vacationStops.getLocation().getLocationId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteLocationByKey(int vacationId, int locationId) {
        return vacationStopsRepository.deleteByKey(vacationId, locationId);
    }

    private Result<Vacation> validate(Vacation vacation) {
        Result<Vacation> result = new Result<>();
        if (vacation == null) {
            result.addMessage("vacation cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(vacation.getDescription())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if (vacation.getLeisureLevel() <= 0) {
            result.addMessage("leisure level needs to be greater than 0", ResultType.INVALID);
        }

        return result;
    }

    private Result<Void> validate(VacationStops vacationStops) {
        Result<Void> result = new Result<>();
        if (vacationStops == null) {
            result.addMessage("vacationStops cannot be null", ResultType.INVALID);
            return result;
        }

        if (vacationStops.getLocation() == null) {
            result.addMessage("location cannot be null", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(vacationStops.getIdentifier())) {
            result.addMessage("identifier is required", ResultType.INVALID);
        }

        if (vacationStops.getStartDate() == null) {
            result.addMessage("start date is required", ResultType.INVALID);
        }

        if (vacationStops.getEndDate() == null) {
            result.addMessage("end date is required", ResultType.INVALID);
        }

        return result;
    }
}
