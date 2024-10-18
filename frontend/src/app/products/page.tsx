'use client'

import { useQuery } from '@tanstack/react-query'
import { 
  Container, 
  Typography, 
  Grid, 
  Card, 
  CardContent, 
  CircularProgress
} from '@mui/material'
import { ProductModel } from '@/services/products/model'
import { fetchProducts } from '@/services/products/repository.ts'

export default function ProductsPage() {
  const { data: products, isLoading, isError } = useQuery<ProductModel[], Error>({
    queryKey: ['products'],
    queryFn: () => fetchProducts(),
  })
  if (isLoading) {
    return (
      <Container sx={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <CircularProgress />
      </Container>
    )
  }

  if (isError) {
    return (
      <Container>
        <Typography variant="h4" color="error">Error loading products</Typography>
      </Container>
    )
  }

  return (
    <Container>
      <Typography variant="h2" component="h1" gutterBottom>
        Products
      </Typography>
      <Grid container spacing={3}>
        {products?.map((product) => (
          <Grid item xs={12} sm={6} md={4} key={product.id}>
            <Card>
              <CardContent>
                <Typography variant="h5" component="div">
                  {product.name}
                </Typography>
                <Typography color="text.secondary">
                  {product.category}
                </Typography>
                <Typography variant="body2">
                  Price: ${product.price}
                </Typography>
                <Typography variant="body2">
                  Brand: {product.brandName}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  )
}