import {Component, OnInit} from '@angular/core';
import {Order} from "../orders/model/order";
import {DataService} from "../shared/data.service";
import {ApiService} from "../shared/api.service";
import {CustomerApiService} from "../shared/customer-api.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-proposal',
    templateUrl: './proposal.component.html',
    styleUrls: ['./proposal.component.css']
})
export class ProposalComponent implements OnInit {

    orders: Order[] = [];
    badge: string;
    currentUser: string

    constructor(public data: DataService,
                private api: ApiService,
                private router: Router,
                private customerApiService: CustomerApiService) {
    }

    ngOnInit(): void {
        this.data.itemsInCart.subscribe(message => this.badge = message);
        this.currentUser = this.api.currentUser;
        this.getOrdersByPhone(this.currentUser);
    }

    private getOrdersByPhone(phone: string) {
        this.customerApiService.getOrdersByMobilePhone(phone).subscribe(
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
            order.status = 'CANCELLED';
            this.saveChanges(order);
            this.getOrdersByPhone(this.currentUser);
        }
    }

    canShow(status: string): boolean {
        return !(status === 'CANCELLED'
            || status === 'DELETED'
            || status === 'COMPLETED');
    }

    private saveChanges(order: Order): void {
        this.customerApiService.save(order).subscribe(
            res => {
                this.router.navigate(["/proposals"]).then(value => {
                    this.getOrdersByPhone(this.currentUser);
                });
            },
            err => {
                alert('An Error Has Occurred!');
            }
        )
    }
}
