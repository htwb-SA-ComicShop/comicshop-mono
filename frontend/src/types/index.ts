export interface NavItem {
  label: string;
  subLabel?: string;
  href: string;
}

export interface Product {
  id: string;
  name: string;
  author: string;
  publisher: string;
  pages: number;
  price: number;
  description: string;
  imgUrl?: string;
}

export interface ProductGridProps {
  products: Product[];
}

export interface AuthStatus {
  isLoggedIn: boolean;
  isAdmin: boolean;
  username?: string;
  token?: string;
}

export interface LoginCredentials {
  username: string;
  password: string;
}
