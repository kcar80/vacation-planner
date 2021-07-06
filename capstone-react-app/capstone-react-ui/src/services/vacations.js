const url = "http://localhost:8080/api/vacation";

export async function findAllVacations() {
    const response = await fetch(url);
export async function findVacationsByUser(userId) {
    const response = await fetch(`${url}/user=${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findById(vacationId) {
    const response = await fetch(`${url}/id/${vacationId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}


export async function add(vacation) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacation)
    }
    const response = await fetch(url, init);
    if (response.status === 201) {
        return await response.json();
    }

    const errors = await response.json();
    return Promise.reject(errors);
}

export async function update(vacation) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(vacation)
    }
    const response = await fetch(`${url}/${vacation.id}`, init);
    if (response.status !== 204) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
}

export async function deleteById(vacationId) {
const init = {
        method: "DELETE"
    }

    const response = await fetch(`${url}/${vacationId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}
