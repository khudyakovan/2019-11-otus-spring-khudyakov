import {Component, OnInit} from '@angular/core';
import {OrdersApiService} from "../shared/orders-api.service";
import {Order} from "./model/order";
import {DataService} from "../shared/data.service";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  orders: Order[] = [];
  badge: string;

  constructor(private ordersApi: OrdersApiService,
              private data: DataService) { }

  ngOnInit(): void {
    this.getActiveOrders();
    this.data.itemsInCart.subscribe(message => this.badge = message);
  }

  private getActiveOrders(){
    this.ordersApi.getActiveOrders().subscribe(
        res => {
          this.orders = res;
        },
        err => {
          alert('An Error Has Occurred!');
        }
    );
  }

}
