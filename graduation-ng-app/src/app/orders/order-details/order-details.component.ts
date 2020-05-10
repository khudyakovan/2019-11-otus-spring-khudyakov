import {Component, OnInit} from '@angular/core';
import {OrderDetails} from "../model/order-details";
import {OrdersApiService} from "../../shared/orders-api.service";
import {DataService} from "../../shared/data.service";
import {ActivatedRoute, Router} from "@angular/router";
import {OrderItem} from "../model/order-item";

@Component({
    selector: 'app-order-details',
    templateUrl: './order-details.component.html',
    styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent implements OnInit {

    orderDetails: OrderDetails;
    orderNumber: number;
    itemsCount: number;

    constructor(private ordersApi: OrdersApiService,
                private data: DataService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.activatedRoute.paramMap.subscribe(params => {
            this.orderNumber = parseInt(params.get('orderNumber'));
        });
        this.getDetails();
    }

    getItemsCount() {
        this.itemsCount = this.orderDetails.orderItems
            .reduce((accumulator, item) => accumulator + item.quantity, 0);
    }

    deleteFromOrder(item: OrderItem): void {
        let index = this.orderDetails.orderItems.indexOf(item);
        this.orderDetails.orderItems.splice(index, 1);
        this.getItemsCount();
    }

    closeOrder(order: OrderDetails): void {
        if (confirm("Заказ полностью скомплектован?")) {
            order.status = 'READY';
            this.saveChanges(order);
        }
    }

    deleteOrder(order: OrderDetails): void {
        if (confirm("Заказ раскомплектован?")) {
            order.status = 'DELETED';
            this.saveChanges(order);
        }
    }

    private getDetails(): void {
        this.ordersApi.getOrderDetails(this.orderNumber).subscribe(
            res => {
                this.orderDetails = res;
                this.getItemsCount();
            },
            err => {
                alert('An Error Has Occurred!');
            }
        );
    }

    private saveChanges(order: OrderDetails): void {
        this.ordersApi.save(order).subscribe(
            res => {
                this.router.navigate(["/orders"]).then();
            },
            err => {
                alert('An Error Has Occurred!');
            }
        )
    }

}
