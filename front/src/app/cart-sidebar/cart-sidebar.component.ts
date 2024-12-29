
import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs';
import { CartItem, CartService } from './cart.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart-sidebar',
  standalone: true,
  imports: [
    CommonModule,
  ],
  templateUrl: './cart-sidebar.component.html',
  styleUrl: './cart-sidebar.component.css'
})
export class CartSidebarComponent implements OnInit {
  cartItems$: Observable<CartItem[]>;
  isVisible$: Observable<boolean>;

  constructor(private cartService: CartService) {
    this.cartItems$ = this.cartService.cartItems$;
    this.isVisible$ = this.cartService.sidebarVisible$;
  }


  ngOnInit() {}

  closeSidebar() {
    this.cartService.toggleSidebar();
  }

  removeItem(item: CartItem) {
    this.cartService.removeFromCart(item.name);
  }

  updateQuantity(item: CartItem, newQuantity: number) {
    if (newQuantity > 0 && newQuantity <= item.quantity) {
      this.cartService.updateQuantity(item.name, newQuantity);
    }
  }

  getTotal(): number {
    return this.cartService.getTotal();
  }
}