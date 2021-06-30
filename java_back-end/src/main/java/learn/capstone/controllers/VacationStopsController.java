package learn.capstone.controllers;

import learn.capstone.domain.Result;
import learn.capstone.domain.VacationService;
import learn.capstone.models.VacationStops;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/vacation/location")
public class VacationStopsController {

    private final VacationService service;

    public VacationStopsController(VacationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody VacationStops vacationStops) {
        Result<Void> result = service.addLocation(vacationStops);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody VacationStops vacationStops) {
        Result<Void> result = service.updateLocation(vacationStops);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{vacationId}/{locationId}")
    public ResponseEntity<Void> deleteLocationByKey(@PathVariable int vacationId, @PathVariable int locationId) {
        if (service.deleteLocationByKey(vacationId, locationId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
