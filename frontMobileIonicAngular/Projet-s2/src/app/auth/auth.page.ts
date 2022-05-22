import { Component, OnInit } from '@angular/core';
import {AuthService} from "./auth.service";
import {Router} from "@angular/router";
import {LoadingController} from "@ionic/angular";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.page.html',
  styleUrls: ['./auth.page.scss'],
})
export class AuthPage implements OnInit {
  isLoading = false;
  isLogin = true;

  constructor(private authService: AuthService, private router:Router, private loadingCtrl: LoadingController) {
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()){
      this.router.navigateByUrl('');
    } else {
      this.router.navigateByUrl('/auth')
    }
  }

  onLogin(username:string, password:string) {
    this.isLoading = true;
    this.authService.login(username,password).subscribe({
      next:(response) => {
        const token = response.headers.get('Jwt-Token');
        this.authService.saveToken(token);
        this.authService.addUserToLocalCache(response.body);
    }
    })

    this.loadingCtrl.create({keyboardClose: true, message: 'Loggin in ...'})
      .then(loadingEl => {
        loadingEl.present();
        setTimeout(() => {
          this.isLoading = false;
          loadingEl.dismiss();
          this.router.navigateByUrl('/home/tabs/liste-fiche');
        },1500)
      })


  }

  onSignUp(username:string, password:string, firstName:string, lastName:string, email:string) {
    this.isLoading = true;
    this.authService.signup(username,password,firstName,lastName,email).subscribe({
      next:(response) => {

      }
    })




  }

  onSubmit(form:NgForm) {
    if (!form.valid) {
      return;
    }

    if (this.isLogin){
      const username = form.value.username;
      const password = form.value.password;

      this.onLogin(username,password)

    } else {
      const username = form.value.username;
      const password = form.value.password;
      const firstName = form.value.firstName;
      const lastName = form.value.lastName;
      const email = form.value.email;
      this.onSignUp(username,password,firstName,lastName,email)
      this.onLogin(username,password)
    }
  }

  onSwitchAuthMode() {
    this.isLogin = !this.isLogin;
  }

}
