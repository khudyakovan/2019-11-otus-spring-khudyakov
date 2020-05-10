import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../orders/model/order";
import {OrderDetails} from "../orders/model/order-details";

@Injectable({
    providedIn: 'root'
})
export class OrdersApiService {

    private BASE_ORDERS_URLS = '/api/v1/orders'

    constructor(private http: HttpClient) {
    }

    getActiveOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(this.BASE_ORDERS_URLS + '/active');
    }

    getOrderDetails(orderNumber: number): Observable<OrderDetails> {
        let params = new HttpParams().set("order-number", String(orderNumber));
        return this.http.get<OrderDetails>(this.BASE_ORDERS_URLS, {params});
    }

    save(order: OrderDetails): Observable<OrderDetails> {
        return this.http.put<OrderDetails>(this.BASE_ORDERS_URLS, order);
    }
}
