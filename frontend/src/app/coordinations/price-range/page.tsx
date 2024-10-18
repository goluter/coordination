/* eslint-disable @typescript-eslint/no-explicit-any */
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
  Grid,
  Select,
  MenuItem,
  FormControl,
  InputLabel
} from '@mui/material';
import { PriceRangeByCategoryModel } from '@/services/products/model';
import { fetchPriceRangeByCategory } from '@/services/products/repository.ts';

const categories = ["상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"];

const PriceTable: React.FC<{ title: string, data: any[] }> = ({ title, data }) => (
  <TableContainer component={Paper}>
    <Typography variant="h6" sx={{ p: 2 }}>{title}</Typography>
    <Table>
      <TableHead>
        <TableRow>
          <TableCell>Brand</TableCell>
          <TableCell align="right">Price</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {data.map((item) => (
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
);

export default function PriceRangeByCategoryPage() {
  const [category, setCategory] = useState<string>('');

  const { data, isLoading, isError } = useQuery<PriceRangeByCategoryModel, Error>({
    queryKey: ['priceRangeByCategory', category],
    queryFn: () => fetchPriceRangeByCategory(category),
    enabled: !!category,
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
        카테고리 최적화
      </Typography>
      <Box sx={{ mb: 2 }}>
        <FormControl fullWidth>
          <InputLabel id="category-select-label">Category</InputLabel>
          <Select
            labelId="category-select-label"
            value={category}
            label="Category"
            onChange={(e) => setCategory(e.target.value as string)}
          >
            {categories.map((cat) => (
              <MenuItem key={cat} value={cat}>{cat}</MenuItem>
            ))}
          </Select>
        </FormControl>
      </Box>
      {data && (
        <>
          <Typography variant="h4" gutterBottom>
            Category: {data.category}
          </Typography>
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <PriceTable title="Lowest Prices" data={data.lowestPrice} />
            </Grid>
            <Grid item xs={12} md={6}>
              <PriceTable title="Highest Prices" data={data.highestPrice} />
            </Grid>
          </Grid>
        </>
      )}
    </Container>
  );
}