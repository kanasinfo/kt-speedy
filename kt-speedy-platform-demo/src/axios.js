import axios from 'axios';

let conf = {
    // baseURL: baseURL,
    // timeout: 2000,
    withCredentials: true
};
axios.defaults.withCredentials = true
const http = axios.create(conf)
export default http;
