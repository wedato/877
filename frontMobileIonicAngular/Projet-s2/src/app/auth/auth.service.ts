import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _userIsAuthenticated = false;
  private _userId = 'abc'
  private token: string;

  private host = 'http://localhost:8080'
  constructor(private httpClient: HttpClient) { }

  public saveToken(token: string) : void {
    this.token = token;
    //save le token dans le local cache
    localStorage.setItem('token', token);
  }

  public loadToken(): void{
    this.token = localStorage.getItem('token');
  }
  public getToken(): string {
    return this.token;
  }

  public addUserToLocalCache(user: User) : void {
    // json strigify pour transformer user en string
    localStorage.setItem('user', JSON.stringify(user));
  }

  get userIsAuthenticated(): boolean {
    return this._userIsAuthenticated;
  }

  get userId(): string {
    return this._userId;
  }

  login(username: string, password:string): Observable<HttpResponse<any>>{
    this._userIsAuthenticated = true;
    const newUser = new User();
    newUser.username = username;
    newUser.password = password;
    return this.httpClient.post<HttpResponse<any>>(`${this.host}/user/login`, newUser, {observe: "response"});
  }
  logout() {
    this.token = null;
    // le cache
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    localStorage.removeItem('users');
    this._userIsAuthenticated = false;
  }

  // signup(username:string, password:string,){
  //
  // }

}
