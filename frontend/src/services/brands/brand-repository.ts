import axiosInstance from "@/libs/axios";
import { BrandModel } from "./brand-model";

const fetchBrands = async (): Promise<BrandModel[]> => {
    const response = await axiosInstance.get(`/brands`);
    if (!response.data) {
        throw new Error('Network response was not ok');
    }
    return response.data;
}

export { fetchBrands };