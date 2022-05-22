import {EtudiantsListComponent} from "./components/etudiant/etudiants-list/etudiants-list.component";
import {EtudiantDetailsComponent} from "./components/etudiant/etudiant-details/etudiant-details.component";
import {AddEtudiantComponent} from "./components/etudiant/add-etudiant/add-etudiant.component";
import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {CoursListComponent} from "./components/cours/cours-list/cours-list.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {UserComponent} from "./components/user/user.component";
import {ListeEmargementComponent} from "./components/liste-emargement/liste-emargement.component";
import { GroupeTpComponent } from "./components/groupe-tp/groupe-tp.component";
import {AuthenticationGuard} from "./guard/authentication.guard";


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'user/management', component: UserComponent , canActivate:[AuthenticationGuard]},
  {path: 'emargement', component: ListeEmargementComponent},
  {path: 'groupe', component: GroupeTpComponent},
  {path: '', redirectTo: '/login', pathMatch:'full' },
  // {path: '' , redirectTo: 'etudiants', pathMatch:'full'},
  // {path: 'etudiants' , component: EtudiantsListComponent},
  // {path: 'etudiants/:id' , component: EtudiantDetailsComponent},
  // {path: 'addEtudiant', component: AddEtudiantComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
