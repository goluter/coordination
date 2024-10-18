'use client'

import React from 'react';
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
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  Box
} from '@mui/material';
import { LowestPriceByCategoryModel } from '@/services/products/model';
import { fetchLowestPricesByCategory } from '@/services/products/repository.ts';

export default function LowestPriceByCategoryPage() {
  const { data, isLoading, isError } = useQuery<LowestPriceByCategoryModel, Error>({
    queryKey: ['lowestPricesByCategory'],
    queryFn: () => fetchLowestPricesByCategory(),
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
        <Typography variant="h4" color="error">Error loading lowest prices by category</Typography>
      </Container>
    );
  }

  return (
    <Container>
      <Typography variant="h2" component="h1" gutterBottom>
        카테고리별 최저가격 브랜드
      </Typography>
      {data && (
        <>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>Category</TableCell>
                  <TableCell>Brand</TableCell>
                  <TableCell align="right">Price</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {data.categories.map((item) => (
                  <TableRow key={item.category}>
                    <TableCell component="th" scope="row">
                      {item.category}
                    </TableCell>
                    <TableCell>{item.brand}</TableCell>
                    <TableCell align="right">{item.price.toLocaleString()} 원</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Typography variant="h6" sx={{ mt: 2 }}>
            Total Price: {data.totalPrice.toLocaleString()} 원
          </Typography>
        </>
      )}
    </Container>
  );
}