const url = "http://localhost:8080/api/user";

export async function findById(userId) {
    const response = await fetch(`${url}/${userId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findByUsername(username) {
    const response = await fetch(`${url}/${username}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function add(user) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(user)
    }

    const response = await fetch(url, init);
    if (response.status === 201) {
        return await response.json();
    }
    return Promise.reject("not 201 Created");
}

export async function update(user) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(user)
    }
    const response = await fetch(`${url}/${user.userId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}

export async function deleteById(userId, jwt) {
    const init = {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${jwt}`
        }
    }
    const response = await fetch(`${url}/${userId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}