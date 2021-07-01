package learn.capstone.domain;

import learn.capstone.data.VacationRepository;
import learn.capstone.data.VacationStopsRepository;
import learn.capstone.data.VacationUserRepository;
import learn.capstone.models.Vacation;
import learn.capstone.models.VacationStops;
import learn.capstone.models.VacationUser;
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
    private final VacationUserRepository vacationUserRepository;





    public VacationService(VacationRepository vacationRepository, VacationStopsRepository vacationStopsRepository, VacationUserRepository vacationUserRepository) {
        this.vacationRepository = vacationRepository;
        this.vacationStopsRepository = vacationStopsRepository;
        this.vacationUserRepository = vacationUserRepository;

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
        vacation = vacationRepository.add(vacation);
        result.setPayload(vacation);
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

    public Result<Void> addUser(VacationUser vacationUser){
        Result<Void> result = validate(vacationUser);
        if (!result.isSuccess()){
            return result;
        }
        if(!vacationUserRepository.add(vacationUser)){
            result.addMessage("user not added", ResultType.INVALID);
        }
        return result;
    }

    public Result<Void> updateUser(VacationUser vacationUser){
        Result<Void> result = validate(vacationUser);
        if (!result.isSuccess()) {
            return result;
        }

        if (!vacationUserRepository.update(vacationUser)) {
            String msg = String.format("update failed for vacation id %s, user id %s: not found",
                    vacationUser.getVacationId(),
                    vacationUser.getUser().getUserId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;

    }

    public boolean deleteUserByKey(int vacationId, int userId) {
        return vacationStopsRepository.deleteByKey(vacationId, userId);
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

    private Result<Void> validate(VacationUser vacationUser) {
        Result<Void> result = new Result<>();
        if (vacationUser == null) {
            result.addMessage("vacationUser cannot be null", ResultType.INVALID);
            return result;
        }

        if (vacationUser.getUser() == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
        }

        if (Validations.isNullOrBlank(vacationUser.getIdentifier())) {
            result.addMessage("identifier is required", ResultType.INVALID);
        }

        return result;
    }

}
