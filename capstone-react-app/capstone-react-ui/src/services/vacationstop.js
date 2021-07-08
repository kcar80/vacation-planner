const url = "http://localhost:8080/api/vacation/location";

export async function addVacationStop(vacationstop) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacationstop)
    }

    const response = await fetch(url, init);
    if (response.status !== 201) {
         return Promise.reject("not 201 Created");
    }
   
}


export async function updateVacationStop(vacationstop) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacationstop)
    }
    const response = await fetch(`${url}/${vacationstop.vacationId}/${vacationstop.location.locationId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 Created");
    }
}

export async function deleteVacationStopById(vacationId, locationId, jwt) {
    const init = {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${jwt}`
        }
    }
    const response = await fetch(`${url}/${vacationId}/${locationId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}