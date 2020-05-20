import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from './not-found/not-found.component';
import {StoreComponent} from './store/store.component'
import {CheckoutComponent} from "./checkout/checkout.component";
import {ShoppingCartComponent} from "./shopping-cart/shopping-cart.component";
import {LoginComponent} from "./login/login.component";
import {OrdersComponent} from "./orders/orders.component";
import {AuthGuard} from "./shared/auth.guard";
import {ProposalComponent} from "./proposal/proposal.component";
import {OrderDetailsComponent} from "./orders/order-details/order-details.component";
import {PrintBillComponent} from "./orders/print-bill/print-bill.component";

const routes: Routes = [
  {
    path: '',
    component: StoreComponent,
    pathMatch: 'full'
  },
  {
    path: 'cart',
    component: ShoppingCartComponent,
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'proposals',
    component: ProposalComponent,canActivate: [AuthGuard]
  },
  {
    path: 'orders',
    component: OrdersComponent,canActivate: [AuthGuard]
  },
  {
    path: 'order-details/:orderNumber',
    component: OrderDetailsComponent,canActivate: [AuthGuard]
  },
  {
    path: 'print-bill/:orderNumber',
    component: PrintBillComponent,canActivate: [AuthGuard]
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes, {enableTracing: false}), CommonModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
