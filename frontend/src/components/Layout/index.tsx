/* eslint-disable @typescript-eslint/no-explicit-any */
'use client'

import React from 'react';
import Link from 'next/link';
import { 
  Drawer, 
  List, 
  ListItem, 
  ListItemText, 
  AppBar, 
  Toolbar, 
  Typography, 
  Box, 
  styled,
  ListItemButton
} from '@mui/material';

const drawerWidth = 240;

const BlackAppBar = styled(AppBar)(({ theme }) => ({
  backgroundColor: 'white',
  color: theme.palette.primary.main,
  paddingLeft: theme.spacing(2),
  borderBottom: '1px solid #dddddd',
}));

const StyledListItem: any = styled(ListItem)(({ theme }) => ({
  '& .MuiTypography-root': {
    fontWeight: 700,
  },
  '&.Mui-selected': {
    backgroundColor: theme.palette.primary.dark,
  },
}));

const Layout = ({ children }: any) => {
  return (
    <Box sx={{ display: 'flex' }}>
      <BlackAppBar elevation={0} position="fixed" sx={{ zIndex: (theme) => theme.zIndex.drawer + 1 }}>
        <Toolbar disableGutters >
          <Typography variant="h6" fontWeight="600" noWrap component="div">
            Coordination
          </Typography>
        </Toolbar>
      </BlackAppBar>
      <Drawer
        variant="permanent"
        sx={{
          '& .MuiPaper-root ': {
            borderRight: '1px solid #dddddd',
          },
          width: drawerWidth,
          flexShrink: 0,
          [`& .MuiDrawer-paper`]: { width: drawerWidth, boxSizing: 'border-box' },
        }}
      >
        <Toolbar />
        <Box sx={{ overflow: 'auto' }}>
          <List>
            <StyledListItem component={Link} href="/products" disablePadding>
              <ListItemButton>  
                <ListItemText primary="상품" />
              </ListItemButton>
            </StyledListItem>
            <StyledListItem component={Link} href="/brands" disablePadding>
              <ListItemButton>  
                <ListItemText primary="브랜드" />
              </ListItemButton>
            </StyledListItem>
            <StyledListItem component={Link} href="/coordinations/category-lowest-prices" disablePadding>
              <ListItemButton>  
                <ListItemText primary="카테고리 별 최저가격 브랜드" />
              </ListItemButton>
            </StyledListItem>
            <StyledListItem component={Link} href="/coordinations/brand-lowest-prices" disablePadding>
              <ListItemButton>  
                <ListItemText primary="브랜드 별 카테고리 최저가" />
              </ListItemButton>
            </StyledListItem>
            <StyledListItem component={Link} href="/coordinations/price-range" disablePadding>
              <ListItemButton>  
                <ListItemText primary="특정 카테고리 최저가" />
              </ListItemButton>
            </StyledListItem>
          </List>
        </Box>
      </Drawer>
      <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
        <Toolbar />
        {children}
      </Box>
    </Box>
  );
};

export default Layout;