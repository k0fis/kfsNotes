export default function authHeader() {
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        return { Authorization: 'Kofis ' + user.token };
    } else {
        return {};
    }
}