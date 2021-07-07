const url = "http://localhost:8080/api/vacation/user";

export async function addVacationUser(vacationuser) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacationuser)
    }

    const response = await fetch(url, init);
    if (response.status !== 201) {
         return Promise.reject("not 201 Created");
    }
   
}


export async function updateVacationUser(vacationuser) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacationuser)
    }
    const response = await fetch(`${url}/${vacationuser.vacationId}/${vacationuser.user.userId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 Created");
    }
}

export async function deleteVacationUserById(vacationuser, jwt) {
    const init = {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${jwt}`
        }
    }
    const response = await fetch(`${url}/${vacationuser.vacationId}/${vacationuser.user.userId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}