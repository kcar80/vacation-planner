const url = "http://localhost:8080/api/vacation";

export async function findVacationsByUser(userId) {
    const response = await fetch(`${url}/uid/${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}