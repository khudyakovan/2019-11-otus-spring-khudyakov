import {Component, OnInit} from '@angular/core';
import {GlobalConstants} from "../common/global-constants";
import {ApiService} from "../shared/api.service";
import {DataService} from "../shared/data.service";
import {CartItem} from "../shared/cart-item";
import {User} from "./model/user";
import {CheckoutItem} from "./model/checkout-item";
import {Router} from "@angular/router";

@Component({
    selector: 'app-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

    shopId: string = GlobalConstants.shopId;
    shoppingCartItems = localStorage.getItem(this.shopId) ? JSON.parse(localStorage.getItem(this.shopId)) : [];
    badge: string;
    summary: number;
    totalSummary: number;
    checkoutItem: CheckoutItem = new CheckoutItem();
    user: User = new User();

    constructor(private apiService: ApiService,
                private router: Router,
                private data: DataService) {
    }

    ngOnInit(): void {
        this.data.itemsInCart.subscribe(message => this.badge = message)
        this.itemsCountInShoppingCart();
        this.setTotalSummary();
    }

    public itemsCountInShoppingCart() {
        let count = this.shoppingCartItems.reduce((accumulator, item) => accumulator + item.quantity, 0);
        this.data.emitItemsChange(count);
    }

    public getItemSummary(item: CartItem) {
        this.summary = item.quantity * item.product.price;
    }

    private setTotalSummary() {
        this.totalSummary = this.shoppingCartItems.reduce((accumulator, item) => accumulator + (item.quantity * item.product.price), 0);
    }

    public doCheckout() {
        let items = this.shoppingCartItems.map(val => ({
            [val.product.id]: val.quantity
        }));
        console.log(items);
        this.checkoutItem.user = this.user;
        //this.checkoutItem.items = JSON.stringify(items);
        this.checkoutItem.items = items;
        this.handleCheckout();
    }

    private handleCheckout(): void {
        this.apiService.handleCheckout(this.checkoutItem).subscribe(
            res => {
                this.router.navigate(["/"]);
            },
            err => {
                alert("An error has occurred!");
            }
        )
    }
}
