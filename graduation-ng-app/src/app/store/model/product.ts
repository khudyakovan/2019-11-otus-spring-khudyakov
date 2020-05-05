import { ParentLevel } from './parent-level';

export class Product {
  id: string;
  name: string;
  parent: ParentLevel;
  price: number;
  stock: number;
}
