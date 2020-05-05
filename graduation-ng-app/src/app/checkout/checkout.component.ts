import {Component, OnInit} from '@angular/core';
import {GlobalConstants} from "../common/global-constants";
import {ApiService} from "../shared/api.service";
import {DataService} from "../shared/data.service";
import {CartItem} from "../shared/cart-item";

@Component({
    selector: 'app-checkout',
    templateUrl: './checkout.component.html',
    styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

    shopId: string = GlobalConstants.shopId;
    shoppingCartItems = localStorage.getItem(this.shopId) ? JSON.parse(localStorage.getItem(this.shopId)) : [];
    summary: number;

    constructor(private apiService: ApiService, private data: DataService) {
    }

    ngOnInit(): void {
        this.itemsCountInShoppingCart();
    }

    public itemsCountInShoppingCart(){
        let count = this.shoppingCartItems.reduce((accumulator, item) => accumulator + item.quantity, 0);
        this.data.changeMessage(count);
    }

    public getItemSummary(item: CartItem){
        this.summary = item.quantity * item.product.price;
    }
}
