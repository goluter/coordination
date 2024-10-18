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
import { BrandModel } from '@/services/brands/brand-model'
import { fetchBrands } from '@/services/brands/brand-repository'

export default function BrandsPage() {
  const { data: brands, isLoading, isError } = useQuery<BrandModel[], Error>({
    queryKey: ['brands'],
    queryFn: () => fetchBrands(),
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
        <Typography variant="h4" color="error">Error loading brands</Typography>
      </Container>
    )
  }

  return (
    <Container>
      <Typography variant="h2" component="h1" gutterBottom>
        Brands
      </Typography>
      <Grid container spacing={3}>
        {brands?.map((brand) => (
          <Grid item xs={12} sm={6} md={4} key={brand.id}>
            <Card>
              <CardContent>
                <Typography variant="h5" component="div">
                  {brand.name}
                </Typography>
                <Typography color="text.secondary">
                  ID: {brand.id}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Container>
  )
}