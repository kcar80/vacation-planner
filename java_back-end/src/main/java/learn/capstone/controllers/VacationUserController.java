package learn.capstone.controllers;

import learn.capstone.domain.Result;
import learn.capstone.domain.VacationService;
import learn.capstone.models.VacationUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/vacation/user")
public class VacationUserController {

    private final VacationService service;

    public VacationUserController(VacationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody VacationUser vacationUser) {
        Result<Void> result = service.addUser(vacationUser);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{vacationId}/{userId}")
    public ResponseEntity<Object> update(@RequestBody VacationUser vacationUser) {
        Result<Void> result = service.updateUser(vacationUser);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{vacationId}/{userId}")
    public ResponseEntity<Void> deleteLocationByKey(@PathVariable int vacationId, @PathVariable int userId) {
        if (service.deleteLocationByKey(vacationId, userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
