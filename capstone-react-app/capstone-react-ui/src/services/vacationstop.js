// const url = "http://localhost:8080/api/vacation/user";

// export async function addVacationStop(vacationStop) {
//     const init = {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//             "Accept": "application/json"
//         },
//         body: JSON.stringify(vacationuser)
//     }

//     const response = await fetch(url, init);
//     if (response.status === 201) {
//         return await response.json();
//     }
//     return Promise.reject("not 201 Created");
// }


// export async function updateVacationStop(vacationId, locationId) {
//     const init = {
//         method: "PUT",
//         headers: {
//             "Content-Type": "application/json",
//             "Accept": "application/json"
//         },
//         body: JSON.stringify(location)
//     }
//     const response = await fetch(`${url}/${vacationId}/${locationId}`, init);
//     if (response.status !== 204) {
//         const errors = await response.json();
//         return Promise.reject(errors);
//     }
// }

// export async function deleteVacationStopById(vacationId, userId, jwt) {
//     const init = {
//         method: "DELETE",
//         headers: {
//             "Authorization": `Bearer ${jwt}`
//         }
//     }
//     const response = await fetch(`${url}/${vacation.id}/${user.id}`, init);
//     if (response.status !== 204) {
//         return Promise.reject("not 204 No Content");
//     }
// }