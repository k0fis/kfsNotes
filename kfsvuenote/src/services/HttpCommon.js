import axios from "axios";

export default axios.create({

    baseURL: process.env.NODE_ENV === 'production'? "/" : "http://localhost:8202/",
    headers: {
        "Content-type": "application/json",
        "Access-Control-Allow-Origin": "*",

    }
});
