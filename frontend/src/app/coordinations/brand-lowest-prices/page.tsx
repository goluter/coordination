'use client'

import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import {
  Container,
  Typography,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  CircularProgress,
  Box,
  Select,
  MenuItem,
  FormControl,
  InputLabel
} from '@mui/material';
import { LowestPriceByBrandModel } from '@/services/products/model';
import { fetchLowestPricesByBrand } from '@/services/products/repository.ts';
import { BrandModel } from '@/services/brands/brand-model';
import { fetchBrands } from '@/services/brands/brand-repository';

export default function LowestPriceByBrandPage() {
  const [selectedBrand, setSelectedBrand] = useState<string>('');

  const { data: brands, isLoading: isBrandsLoading, isError: isBrandsError } = useQuery<BrandModel[], Error>({
    queryKey: ['brands'],
    queryFn: fetchBrands
  });

  const { 
    data: priceData, 
    isError: isPriceError,
  } = useQuery<LowestPriceByBrandModel, Error>({
    queryKey: ['lowestPricesByBrand', selectedBrand],
    queryFn: () => fetchLowestPricesByBrand(selectedBrand),
    enabled: !!selectedBrand,
  });

  if (isBrandsLoading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <CircularProgress />
      </Container>
    );
  }

  if (isBrandsError || isPriceError) {
    return (
      <Container>
        <Typography variant="h4" color="error">Error loading data</Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h2" component="h1" gutterBottom>
        브랜드별 최저가격 카테고리
      </Typography>
      <Box sx={{ mb: 2 }}>
        <FormControl fullWidth sx={{ mr: 1 }}>
          <InputLabel id="brand-select-label">Brand</InputLabel>
          <Select
            labelId="brand-select-label"
            value={selectedBrand}
            label="Brand"
            onChange={(e) => setSelectedBrand(e.target.value as string)}
          >
            {brands?.map((brand) => (
              <MenuItem key={brand.id} value={brand.name}>{brand.name}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>
      {priceData && (
        <>
          <Typography variant="h4" gutterBottom>
            Brand: {priceData.brand}
          </Typography>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Category</TableCell>
                  <TableCell align="right">Price</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {priceData.categories.map((item) => (
                  <TableRow key={item.category}>
                    <TableCell component="th" scope="row">
                      {item.category}
                    </TableCell>
                    <TableCell align="right">{item.price.toLocaleString()} 원</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Typography variant="h6" sx={{ mt: 2 }}>
            Total Price: {priceData.totalPrice.toLocaleString()} 원
          </Typography>
        </>
      )}
    </Container>
  );
}