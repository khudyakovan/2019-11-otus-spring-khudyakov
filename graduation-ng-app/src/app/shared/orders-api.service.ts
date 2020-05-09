import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Order} from "../orders/model/order";

@Injectable({
    providedIn: 'root'
})
export class OrdersApiService {

    private BASE_ORDERS_URLS = '/api/v1/orders'
    private BASE_PROPOSALS_URLS = '/api/v1/proposals'

    constructor(private http: HttpClient) {
    }

    getActiveOrders(): Observable<Order[]> {
        return this.http.get<Order[]>(this.BASE_ORDERS_URLS + '/active');
    }

    getOrdersByMobilePhone(mobileNumber: string): Observable<Order[]> {
        let params = new HttpParams().set("mobile-number", mobileNumber);
        return this.http.get<Order[]>(this.BASE_PROPOSALS_URLS, {params});
    }

    orderCancellation(order: Order): Observable<Order>{
        return this.http.put<Order>(this.BASE_PROPOSALS_URLS, order);
    }
}
