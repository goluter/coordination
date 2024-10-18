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
import { PriceRangeByCategoryModel } from '@/services/products/model';
import { fetchPriceRangeByCategory } from '@/services/products/repository.ts';

const categories = ["상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"];

export default function PriceRangeByCategoryPage() {
  const [selectedCategory, setSelectedCategory] = useState<string>('');

  const { 
    data, 
    isLoading, 
    isError,
  } = useQuery<PriceRangeByCategoryModel, Error>({
    queryKey: ['priceRangeByCategory', selectedCategory],
    queryFn: () => fetchPriceRangeByCategory(selectedCategory),
    enabled: !!selectedCategory,
  });

  if (isLoading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <CircularProgress />
      </Container>
    );
  }

  if (isError) {
    return (
      <Container>
        <Typography variant="h4" color="error">Error loading price range for category</Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h2" component="h1" gutterBottom>
        카테고리별 가격 범위
      </Typography>
      <Box sx={{ mb: 2 }}>
        <FormControl fullWidth sx={{ mr: 1 }}>
          <InputLabel id="category-select-label">Category</InputLabel>
          <Select
            labelId="category-select-label"
            value={selectedCategory}
            label="Category"
            onChange={(e) => setSelectedCategory(e.target.value as string)}
          >
            {categories.map((category) => (
              <MenuItem key={category} value={category}>{category}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>
      {data && (
        <>
          <Typography variant="h4" gutterBottom>
            Category: {data.category}
          </Typography>
          <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
            <TableContainer component={Paper} sx={{ width: '48%' }}>
              <Typography variant="h6" sx={{ p: 2 }}>Lowest Prices</Typography>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Brand</TableCell>
                    <TableCell align="right">Price</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data.lowestPrice.map((item) => (
                    <TableRow key={item.brand}>
                      <TableCell component="th" scope="row">
                        {item.brand}
                      </TableCell>
                      <TableCell align="right">{item.price.toLocaleString()} 원</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
            <TableContainer component={Paper} sx={{ width: '48%' }}>
              <Typography variant="h6" sx={{ p: 2 }}>Highest Prices</Typography>
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Brand</TableCell>
                    <TableCell align="right">Price</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data.highestPrice.map((item) => (
                    <TableRow key={item.brand}>
                      <TableCell component="th" scope="row">
                        {item.brand}
                      </TableCell>
                      <TableCell align="right">{item.price.toLocaleString()} 원</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Box>
        </>
      )}
    </Container>
  );
}