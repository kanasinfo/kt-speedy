import axios from 'axios';

let conf = {
    // baseURL: baseURL,
    // timeout: 2000,
    withCredentials: true
};
axios.defaults.withCredentials = true
axios.defaults.headers.common['Authorization'] = 'sss';
const http = axios.create(conf)
export default http;
