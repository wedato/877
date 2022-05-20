import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _userIsAuthenticated = false;
  private _userId = 'abc'

  constructor() { }


  get userIsAuthenticated(): boolean {
    return this._userIsAuthenticated;
  }

  get userId(): string {
    return this._userId;
  }

  login(){
    this._userIsAuthenticated = true;
  }
  logout() {
    this._userIsAuthenticated = false;
  }

  signup(){

  }

}
