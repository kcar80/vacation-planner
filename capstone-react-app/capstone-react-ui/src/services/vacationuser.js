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
    if (response.status === 201) {
        return await response.json();
    }
    return Promise.reject("not 201 Created");
}