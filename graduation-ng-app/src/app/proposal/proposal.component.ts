import {Component, OnInit} from '@angular/core';
import {Order} from "../orders/model/order";
import {DataService} from "../shared/data.service";
import {ApiService} from "../shared/api.service";
import {OrdersApiService} from "../shared/orders-api.service";

@Component({
    selector: 'app-proposal',
    templateUrl: './proposal.component.html',
    styleUrls: ['./proposal.component.css']
})
export class ProposalComponent implements OnInit {

    orders: Order[] = [];
    badge: string;
    currentUser: string

    constructor(private data: DataService,
                private api: ApiService,
                private orderApiService: OrdersApiService) {
    }

    ngOnInit(): void {
        this.data.itemsInCart.subscribe(message => this.badge = message);
        this.currentUser = this.api.currentUser;
        this.getOrdersByPhone(this.currentUser);
    }

    private getOrdersByPhone(phone: string) {
        this.orderApiService.getOrdersByMobilePhone(phone).subscribe(
            res => {
                this.orders = res;
            },
            err => {
                alert('An Error Has Occurred!');
            }
        )
    }

    cancelOrder(order: Order): void {
        if (confirm("Отменить заказ?")) {
            this.orderCancellation(order);
            this.getOrdersByPhone(this.currentUser);
        }
    }

    private orderCancellation(order: Order): void{
        this.orderApiService.orderCancellation(order).subscribe(
            res=>{},
            err => {
                alert('An Error Has Occurred!');
            }
        )
    }
}
