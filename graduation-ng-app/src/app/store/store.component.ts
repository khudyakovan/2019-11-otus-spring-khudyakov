import {Component, OnInit} from '@angular/core';
import {Level} from './model/level';
import {ApiService} from '../shared/api.service';
import {Product} from "./model/product";
import {DataService} from "../shared/data.service";
import {GlobalConstants} from "../common/global-constants";
import {CartItem} from "../shared/cart-item";

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {

  shopId: string = GlobalConstants.shopId;
  levels: Level[] = [];
  levelId: string;
  products: Product[] = [];
  currentLevel: Level;
  shoppingCartItems = localStorage.getItem(this.shopId) ? JSON.parse(localStorage.getItem(this.shopId)) : [];

  constructor(private apiService: ApiService, private data: DataService) { }

  ngOnInit(): void {
    this.getAllLevels();
    this.getShowcase();
    this.itemsCountInShoppingCart();
  }

  public addToShoppingCart(product: Product){
    const index = this.shoppingCartItems.findIndex(el => el.product.id === product.id);
    if(index != -1){
      this.shoppingCartItems[index].quantity = this.shoppingCartItems[index].quantity+1;
    }else{
      let cartItem = new CartItem();
      cartItem.quantity = 1;
      cartItem.product = product;
      this.shoppingCartItems.push(cartItem);
    }
    localStorage.setItem(this.shopId, JSON.stringify(this.shoppingCartItems));
    this.itemsCountInShoppingCart();
  }

  private itemsCountInShoppingCart(){
    let count = this.shoppingCartItems.reduce((accumulator, item) => accumulator + item.quantity, 0);
    this.data.emitItemsChange(count);
  }

  public onLevelClick(currentLevel : Level){
    this.levelId = currentLevel.id;
    if(currentLevel.id.endsWith("0")) {
      this.getChildLevels(currentLevel.id);
    }
    this.getShowcaseByLevelId(currentLevel.id);
  }

  public onBackClick(id: string){
    this.getLevelById(id);
    this.getChildLevels(this.currentLevel.parent.id);
  }

  private getAllLevels(){
    this.apiService.getAllLevels().subscribe(
      res => {
        this.levels = res;
      },
      err => {
        alert('An Error Has Occurred!');
      }
    );
  }

  private getLevelById(id: string){
    this.apiService.getLevelById(id).subscribe(
        res => {
          this.currentLevel = res;
        },
        err => {
          alert('An Error Has Occurred!');
        }
    )
  }

  private getChildLevels(currentId: string){
    this.apiService.getChildLevels(currentId).subscribe(
      res => {
        this.levels = res;
      },
      err => {
        alert('An Error Has Occurred!');
      }
    );
  }

  private getShowcase(){
    this.apiService.getShowcase().subscribe(
        res => {
          this.products = res;
        },
        err => {
          alert('An Error Has Occurred!');
        }
    );
  }

  private getShowcaseByLevelId(levelId: string){
    this.apiService.getShowcaseByHierarchyLevelId(levelId).subscribe(
        res => {
          this.products = res.slice(0,40);
        },
        err => {
          alert('An Error Has Occurred!');
        }
    );
  }

}
