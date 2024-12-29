import { Injectable } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { BehaviorSubject } from 'rxjs';


export interface CartItem extends Product {
  quantity: number;
}

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems = new BehaviorSubject<CartItem[]>([]);
  cartItems$ = this.cartItems.asObservable();
  
  private sidebarVisible = new BehaviorSubject<boolean>(false);
  sidebarVisible$ = this.sidebarVisible.asObservable();

  addToCart(product: Product) {
    const currentItems = this.cartItems.value;
    const existingItem = currentItems.find(item => item.name === product.name);
    
    if (existingItem) {
      const updatedItems = currentItems.map(item => 
        item.name === product.name 
          ? { ...item, quantity: item.quantity + 1 }
          : item
      );
      this.cartItems.next(updatedItems);
    } else {
      this.cartItems.next([...currentItems, { ...product, quantity: 1 }]);
    }
    
    this.sidebarVisible.next(true);
  }

  removeFromCart(productName: string) {
    const currentItems = this.cartItems.value;
    this.cartItems.next(currentItems.filter(item => item.name !== productName));
  }

  updateQuantity(productName: string, quantity: number) {
    const currentItems = this.cartItems.value;
    const updatedItems = currentItems.map(item =>
      item.name === productName ? { ...item, quantity } : item
    );
    this.cartItems.next(updatedItems);
  }

  toggleSidebar() {
    this.sidebarVisible.next(!this.sidebarVisible.value);
  }

  getTotal(): number {
    return this.cartItems.value.reduce((total, item) => 
      total + (item.price * item.quantity), 0
    );
  }
}