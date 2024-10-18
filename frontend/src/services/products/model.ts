class ProductModel {
    id!: number;
    name!: string;
    price!: number;
    category!: string;
    brandId!: number;
    brandName!: string;
 }

 class CategoryBrandPrice {
    category!: string;
    brand!: string;
    price!: number;
}
  
class LowestPriceByCategoryModel {
    categories!: CategoryBrandPrice[];
    totalPrice!: number;
}

class CategoryPrice {
    category!: string;
    price!: number;
  }
  
class LowestPriceByBrandModel {
    brand!: string;
    categories!: CategoryPrice[];
    totalPrice!: number;
}
class BrandPrice {
    brand!: string;
    price!: number;
}
  
class PriceRangeByCategoryModel {
    category!: string;
    lowestPrice!: BrandPrice[];
    highestPrice!: BrandPrice[];
}

export {ProductModel, LowestPriceByCategoryModel, LowestPriceByBrandModel, PriceRangeByCategoryModel};