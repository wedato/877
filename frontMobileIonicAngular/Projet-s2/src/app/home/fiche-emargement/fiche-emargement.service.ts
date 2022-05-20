import { Injectable } from '@angular/core';
import {FicheEmargement} from "./fiche-emargement.model";
import {AuthService} from "../../auth/auth.service";
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs/operators";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FicheEmargementService {
   idGen:number = 2;
   private host = 'http://localhost:8080'

  public listeFicheEmargement: any = [
    // {
    //   id:'1',
    //   nomCours:'WebService',
    //   imageUrl:
    //     'https://thumbs.dreamstime.com/b/vector-global-web-service-icon-isolated-black-flat-design-concept-163719580.jpg',
    //   listeEleves:['Jonathan','Louis','Clement','Mickael'],
    //   dateCours: new Date()
    // },
    // {
    //   id:'2',
    //   nomCours:'TestEtQualite',
    //   imageUrl:
    //     'https://img2.freepng.fr/20180404/lwe/kisspng-computer-icons-software-testing-quiz-5ac50f1e2e6aa2.8158265315228639021901.jpg',
    //   listeEleves:['Jonathan','Louis','Clement','Mickael'],
    //   dateCours: new Date()
    // }
  ]

  o

  constructor(private authService: AuthService, private httpClient: HttpClient) { }

  getAllFiches(): Observable<FicheEmargement[]> {
     return this.httpClient.get<FicheEmargement[]>(`${this.host}/fiche/liste`);

  }

  addFiche(nomCours:string, dateCours:Date): Observable<FicheEmargement>{
    const newFiche = new FicheEmargement();
    newFiche.nomCours = nomCours;
    newFiche.dateCours = dateCours;
    newFiche.imageUrl = 'https://picsum.photos/200'
    newFiche.listeEleves = ['']
    return this.httpClient.post<FicheEmargement>(`${this.host}/fiche/liste`,newFiche)


  }


  // ... = décomposition , il clone l'objet dans son integralité puis extrait toutes les propriété et les ajoute à un nouvel objet
  getFiche(ficheId:string){
   return { ...this.listeFicheEmargement.find(ficheEmargement => ficheEmargement.id === ficheId)};
  }

  deleteFiche(ficheId:string){
    // this.listeFicheEmargement = this.listeFicheEmargement.filter(ficheEmargement => {
    //
    //   return ficheEmargement.id !== ficheId;
    // });
    return this.httpClient.delete(`${this.host}/fiche/liste/${ficheId}`)
  }
}
