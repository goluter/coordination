import Providers from './providers';
import './globals.css';
import { Inter } from 'next/font/google';
import Layout from '@/components/Layout';

export const metadata = {
  title: 'Coordination',
  description: 'Coordination',
}

const inter = Inter({ subsets: ['latin'] });

export default async function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body className={inter.className}>
        <Providers>
          <Layout>
            {children}
          </Layout>
        </Providers>
      </body>
    </html>
  );
}