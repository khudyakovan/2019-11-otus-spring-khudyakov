import {OrderItem} from "./order-item";

export interface OrderDetails {
    orderNumber: number;
    status: string;
    orderItems: OrderItem[];
}
