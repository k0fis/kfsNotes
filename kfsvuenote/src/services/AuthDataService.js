import http from './HttpCommon';

const API_URL =  "kAuth/";

class AuthDataService {
    login(user) {
        return http
            .post(API_URL + 'signIn', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                console.log("JWT token: " + JSON.stringify(response.data));
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                } else {
                    console.log("No token!");
                }

                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return http.post(API_URL + 'signUp', {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthDataService();