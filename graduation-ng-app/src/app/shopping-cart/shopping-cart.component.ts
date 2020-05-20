import {Component, OnInit} from '@angular/core';
import {GlobalConstants} from "../shared/global-constants";
import {ApiService} from "../shared/api.service";
import {DataService} from "../shared/data.service";
import {CartItem} from "../shared/cart-item";

@Component({
    selector: 'app-shopping-cart',
    templateUrl: './shopping-cart.component.html',
    styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

    shopId: string = GlobalConstants.shopId;
    shoppingCartItems = localStorage.getItem(this.shopId) ? JSON.parse(localStorage.getItem(this.shopId)) : [];
    summary: number;
    totalSummary: number;

    constructor(private apiService: ApiService, private data: DataService) {
    }

    ngOnInit(): void {
        this.itemsCountInShoppingCart();
    }

    public itemsCountInShoppingCart() {
        let count = this.shoppingCartItems.reduce((accumulator, item) => accumulator + item.quantity, 0);
        localStorage.setItem(this.shopId, JSON.stringify(this.shoppingCartItems));
        this.data.emitItemsChange(count);
        this.setTotalSummary();
    }

    public deleteItemFromShoppingCart(item: CartItem){
        let index = this.shoppingCartItems.indexOf(item);
        this.shoppingCartItems.splice(index, 1);
        this.itemsCountInShoppingCart();
    }

    public getItemSummary(item: CartItem) {
        this.summary = item.quantity * item.product.price;
    }

    private setTotalSummary() {
        this.totalSummary = this.shoppingCartItems.reduce((accumulator, item) => accumulator + (item.quantity * item.product.price), 0);
    }
}
