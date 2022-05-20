import {Component, OnDestroy, OnInit} from '@angular/core';
import {FicheEmargement} from "./fiche-emargement.model";
import {FicheEmargementService} from "./fiche-emargement.service";

@Component({
  selector: 'app-fiche-emargement',
  templateUrl: './fiche-emargement.page.html',
  styleUrls: ['./fiche-emargement.page.scss'],
})
export class FicheEmargementPage implements OnInit, OnDestroy{

  listeFicheEmargements: FicheEmargement[];

  // le private dans le constructeur permet un acces dans toute la classe
  constructor(private ficheEmargementService: FicheEmargementService) { }

  ngOnInit() {
    this.getListeFiches();
  }

  ionViewWillEnter(){
    this.getListeFiches()
  }

  public getListeFiches(){
    this.ficheEmargementService.getAllFiches().subscribe( {
      next:(response) => {
        this.listeFicheEmargements = response;
        this.ficheEmargementService.listeFicheEmargement = this.listeFicheEmargements
      },
      error:(errorResponse) => {
      console.error(errorResponse);
    }
    })
  }

  ngOnDestroy(): void {

  }

}
