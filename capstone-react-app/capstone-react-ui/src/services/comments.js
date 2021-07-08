const url = "http://localhost:8080/api/comment"

export async function findByCommentId(commentId) {
    const response = await fetch(`${url}/cid/${commentId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function findByVacationId(vacationId) {
    const response = await fetch(`${url}/vid/${vacationId}`);
    if (response.status === 200) {
        return await response.json();
    }
    return Promise.reject("not 200 OK");
}

export async function add(comment) {
    const init = {
        method: "POST",
        headers: {
            "Content-type": "application/json",
            "Accept": "application/json"
        },
        body: JSON.stringify(comment)
    }

    const response = await fetch(url, init);
    if (response.status === 201) {
        return await response.json();
    }
    return Promise.reject("not 201 Created");
}

export async function deleteById(commentId) {
    const init = {
        method: "DELETE"
    }
    const response = await fetch(`${url}/${commentId}`, init);
    if (response.status !== 204) {
        return Promise.reject("not 204 No Content");
    }
}

export const emptyComment = {
    commentId: 0,
    text: "",
    userId: 0,
    vacationId: 0
}