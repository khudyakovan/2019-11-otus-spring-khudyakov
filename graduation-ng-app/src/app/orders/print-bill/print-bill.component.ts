import {Component, OnInit} from '@angular/core';
import {OrderDetails} from "../model/order-details";
import {OrdersApiService} from "../../shared/orders-api.service";
import {DataService} from "../../shared/data.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
    selector: 'app-print-bill',
    templateUrl: './print-bill.component.html',
    styleUrls: ['./print-bill.component.css']
})
export class PrintBillComponent implements OnInit {

    orderDetails: OrderDetails;
    orderNumber: number;
    itemsCount: number;
    ids: { [p: string]: number }[];

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

    private getDetails(): void {
        this.ordersApi.getOrderDetails(this.orderNumber).subscribe(
            res => {
                this.orderDetails = res;
                this.getItemsCount();
                this.getItemsIds();
            },
            err => {
                alert('An Error Has Occurred!');
            }
        );
    }

    getItemsCount() {
        this.itemsCount = this.orderDetails.orderItems
            .reduce((accumulator, item) => accumulator + item.quantity, 0);
    }

    getItemsIds() {
        this.ids = this.orderDetails.orderItems.map(val => ({
            [val.id]: val.quantity
        }));
    }

}
