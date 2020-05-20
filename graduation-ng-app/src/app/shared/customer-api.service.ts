import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Order} from "../orders/model/order";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class CustomerApiService {
    private BASE_PROPOSALS_URLS = '/api/v1/proposals'

    constructor(private http: HttpClient) {
    }

    getOrdersByMobilePhone(mobileNumber: string): Observable<Order[]> {
        let params = new HttpParams().set("mobile-number", mobileNumber);
        return this.http.get<Order[]>(this.BASE_PROPOSALS_URLS, {params});
    }

    save(order: Order): Observable<Order> {
        return this.http.put<Order>(this.BASE_PROPOSALS_URLS, order);
    }
}
