const url = "http://localhost:8080/api/location";

export async function findAllLocations() {
    const response = await fetch(url);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findById(locationId) {
    const response = await fetch(`${url}/id/${locationId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findByLocationId(locationId) {
    const response = await fetch(`${url}/id/${locationId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findByDescription(description) {
    const response = await fetch(`${url}/description/${description}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function add(location) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(location)
    }
    const response = await fetch(url, init);
    if (response.status === 201) {
        return await response.json();
    }

    const errors = await response.json();
    return Promise.reject(errors);
}

export async function update(location) {
    const init = {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(location)
    }
    const response = await fetch(`${url}/${location.id}`, init);
    if (response.status !== 204) {
        const errors = await response.json();
        return Promise.reject(errors);
    }
}

export async function deleteById(locationId, jwt) {
    const init = {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${jwt}`
        }
    }
    const response = await fetch(`${url}/${locationId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}
