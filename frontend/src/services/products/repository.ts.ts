import axiosInstance from "@/libs/axios";
import { ProductModel, LowestPriceByCategoryModel, LowestPriceByBrandModel, PriceRangeByCategoryModel } from "./model";

const fetchProducts = async (): Promise<ProductModel[]> => {
    const response = await axiosInstance.get(`/products`);
    if (!response.data) {
        throw new Error('Network response was not ok');
    }
    return response.data;
}

const fetchLowestPricesByCategory = async (): Promise<LowestPriceByCategoryModel> => {
  const response = await axiosInstance.get(`/coordination/category-lowest-price`);
  return response.data;
};

const fetchLowestPricesByBrand = async (brand: string): Promise<LowestPriceByBrandModel> => {
  const response = await axiosInstance.get(`/coordination/brand-lowest-prices`, {
    params: { brand }
  });
  return response.data;
};

export const fetchPriceRangeByCategory = async (category: string): Promise<PriceRangeByCategoryModel> => {
  const response = await axiosInstance.get(`/coordination/price-range`, {
    params: { category }
  });
  return response.data;
};

export { fetchProducts, fetchLowestPricesByCategory, fetchLowestPricesByBrand,  };