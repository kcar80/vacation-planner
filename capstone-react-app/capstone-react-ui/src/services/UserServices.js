const url = "http://localhost:8080/api/user";
const forbidden = "forbidden";

export async function findById(userId) {
    const response = await fetch(`${url}/${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findVacationsByUser(userId) {
    const response = await fetch(`http://localhost:8080/api/vacation/${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findLocationById(locationId) {

}