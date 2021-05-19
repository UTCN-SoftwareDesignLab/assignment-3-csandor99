import authHeader, { BASE_URL, HTTP } from "../http";

export default {
    allConsultations() {
        return HTTP.get(BASE_URL + "/consultations", { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    allDoctorConsultations(name){
        return HTTP.get(BASE_URL + "/consultations/doctor/" + name, { headers: authHeader() }).then(
            (response) => {
                return response.data;
            }
        );
    },
    create(consultation) {
        return HTTP.post(BASE_URL + "/consultations", consultation, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    edit(consultation) {
        return HTTP.patch(BASE_URL + "/consultations", consultation, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    },
    delete(consultation) {
        return HTTP.delete(BASE_URL + "/consultations/" + consultation.id, {
            headers: authHeader(),
        }).then();
    },
    checkin(consultation){
        return HTTP.post(BASE_URL + "/consultations/checkin", consultation, {
            headers: authHeader(),
        }).then((response) => {
            return response.data;
        });
    }
};
