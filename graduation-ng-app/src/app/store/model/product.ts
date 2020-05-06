import { ParentLevel } from './parent-level';

export interface Product {
  id: string;
  name: string;
  parent: ParentLevel;
  price: number;
  stock: number;
}
