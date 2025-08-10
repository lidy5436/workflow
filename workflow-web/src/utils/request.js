import axios from "axios";

const request = axios.create({
    baseURL: "http://127.0.0.1:8888",
    timeout: 10000,
})

request.interceptors.response.use(response=> {
    return response;
},error => {
    return Promise.reject(error);
})

export default request;