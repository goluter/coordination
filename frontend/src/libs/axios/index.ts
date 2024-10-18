import { BASE_URL } from '@/config';
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: BASE_URL,
});

export default axiosInstance;