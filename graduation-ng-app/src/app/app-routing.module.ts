import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from './not-found/not-found.component';
import {StoreComponent} from './store/store.component'
import {CheckoutComponent} from "./checkout/checkout.component";

const routes: Routes = [
  {
    path: '',
    component: StoreComponent,
    pathMatch: 'full'
  },
  {
    path: 'checkout',
    component: CheckoutComponent,
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
