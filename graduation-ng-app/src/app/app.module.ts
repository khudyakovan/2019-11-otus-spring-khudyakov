import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {StoreComponent} from './store/store.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {CheckoutComponent} from './checkout/checkout.component';
import {FormsModule} from "@angular/forms";
import {ShoppingCartComponent} from './shopping-cart/shopping-cart.component';
import {LoginComponent} from './login/login.component';
import {JwtModule} from "@auth0/angular-jwt";
import {AuthGuard} from "./shared/auth.guard";
import {OrdersComponent} from './orders/orders.component';
import {AuthInterceptor} from "./shared/auth.interceptor";
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ProposalComponent} from './proposal/proposal.component';

export function tokenGetter() {
    return localStorage.getItem('access_token');
}

@NgModule({
    declarations: [
        AppComponent,
        NavigationComponent,
        NotFoundComponent,
        StoreComponent,
        CheckoutComponent,
        ShoppingCartComponent,
        LoginComponent,
        OrdersComponent,
        ProposalComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        JwtModule.forRoot({
            config: {
                tokenGetter: tokenGetter,
                whitelistedDomains: ['localhost:8080'],
                blacklistedRoutes: ['localhost:8080/api/v1/auth']
            }
        }),
        NgbModule
    ],
    providers: [
        AuthGuard,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true
        }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
