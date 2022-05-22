import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanLoad,
  Route, Router,
  RouterStateSnapshot,
  UrlSegment,
  UrlTree
} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
constructor(private authService: AuthService, private router: Router) {
}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):  boolean {
    return this.isUserLoggedIn();
  }

  private isUserLoggedIn(): boolean {
    if (this.authService.isUserLoggedIn()){
      return true;
    }
    this.router.navigate(['/auth']);
    // this.notificationService.notify(NotificationType.ERROR, `Vous devez vous connectez pour acceder à cette page`.toUpperCase() )
    return false;
  }


}
