import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Level} from '../store/model/level';
import {Observable} from 'rxjs';
import {Product} from "../store/model/product";
import {CheckoutItem} from "../checkout/model/checkout-item";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL = '/api/v1';
  private SHOP_ID_URL = `${this.BASE_URL}/shop/id`;
  private ALL_LEVELS_URL = `${this.BASE_URL}/levels`;
  private CHILD_LEVELS_URL = `${this.BASE_URL}/levels/parent/`;
  private SHOWCASE_URL =  `${this.BASE_URL}/goods/showcase`;
  private GOODS_URL =  `${this.BASE_URL}/goods/`;
  private HANDLE_CHECKOUT_URL =  `${this.BASE_URL}/checkout`;

  constructor(private http: HttpClient) { }

  getAllLevels(): Observable<Level[]>{
    return this.http.get<Level[]>(this.ALL_LEVELS_URL);
  }

  getLevelById(id: string): Observable<Level>{
    //console.log(id);
    return this.http.get<Level>(this.ALL_LEVELS_URL +"/"+ id);
  }

  getChildLevels(parentId: string): Observable<Level[]>{
    return this.http.get<Level[]>(this.CHILD_LEVELS_URL + parentId);
  }

  getShowcase(): Observable<Product []>{
    return this.http.get<Product[]>(this.SHOWCASE_URL);
  }

  getShowcaseByHierarchyLevelId(levelId: string): Observable<Product[]>{
    return this.http.get<Product[]>(this.GOODS_URL+levelId);
  }

  getShopId(): Observable<string>{
    return this.http.get<string>(this.SHOP_ID_URL);
  }

  handleCheckout(item: CheckoutItem): Observable<CheckoutItem>{
    return this.http.post<any>(this.HANDLE_CHECKOUT_URL, item);
  }
  
}
