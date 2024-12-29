import {
  Component,
  inject,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartSidebarComponent } from "./cart-sidebar/cart-sidebar.component";
import { CartService } from "./cart-sidebar/cart.service";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, CartSidebarComponent, CommonModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  

  cartService = inject(CartService);
  cartItems$ = this.cartService.cartItems$;
  openCart() {
    this.cartService.toggleSidebar();
  }
}
