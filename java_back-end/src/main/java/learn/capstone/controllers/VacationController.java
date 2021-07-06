package learn.capstone.controllers;

import learn.capstone.domain.Result;
import learn.capstone.domain.VacationService;
import learn.capstone.models.Vacation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/vacation")
public class VacationController {

    private final VacationService service;

    public VacationController(VacationService service){
        this.service = service;
    }

    @GetMapping
    public List<Vacation> findAll(){
        return service.findAll();
    }

    @GetMapping("/vid/{vacationId}")
    public Vacation findById(@PathVariable int vacationId){
        return service.findById(vacationId);
    }

    @GetMapping("/uid/{userId}")
    public List<Vacation> findByUserId(@PathVariable int userId){
        return service.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Object> add(
            @RequestBody @Valid Vacation vacation,
            BindingResult result){


        if(result.hasErrors()){
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<Vacation> vacationResult= service.add(vacation);
        if(!vacationResult.isSuccess()){
            return ErrorResponse.build(vacationResult);
        }
        return new ResponseEntity<>(vacationResult.getPayload(), HttpStatus.CREATED);
    }

    @PutMapping("/{vacationId}")
    public ResponseEntity<Object> update(
            @PathVariable int vacationId,
            @RequestBody @Valid Vacation vacation,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(vacation, HttpStatus.CREATED);
    }

    @DeleteMapping("/{vacationId}")
    public ResponseEntity<Void> deleteById(@PathVariable int vacationId){
        if(service.deleteById(vacationId)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
