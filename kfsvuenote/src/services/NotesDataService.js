import http from "./HttpCommon";
import authHeader from "@/services/AuthHeader";

const API_URL = "kApiNotes/";

class NotesDataService {

    get(id) {
        return http.get(API_URL + `note/${id}`, {headers: authHeader()});
    }

    create(data) {
        return http.post(API_URL + "note", data, {headers: authHeader()});
    }

    update(id, data) {
        return http.put(API_URL + `note/${id}`, data, {headers: authHeader()});
    }

    delete(id) {
        return http.delete(API_URL + `note/${id}`, {headers: authHeader()});
    }

    getByTitlePaging(title, page, pageSize) {
        return http.get(API_URL + "notes", {
            params: {title: title, page: page, size: pageSize},
            headers: authHeader()
        });
    }
}

export default new NotesDataService();
