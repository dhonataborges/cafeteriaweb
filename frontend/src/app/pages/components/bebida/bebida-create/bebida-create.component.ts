import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // <- Importa diretivas como *ngFor, *
import { IngredienteBaseModel } from '../../../../models/IngredienteBaseModel';
import { IngredienteAdicionalModel } from '../../../../models/IngredienteAdicionalModel';
import { IngredienteBaseService } from '../../../../services/ingrediente-base.service';
import { IngredienteAdicionalService } from '../../../../services/ingrediente-adicional.service';
import { BebidaService } from '../../../../services/bebida.service';
import { BebidaInput } from '../../../../models/input/BebidaInput';
import { Router } from '@angular/router';

@Component({
  selector: 'app-bebida-create',
  imports: [CommonModule],
  templateUrl: './bebida-create.component.html',
  styleUrl: './bebida-create.component.css'
})
export class BebidaCreateComponent implements OnInit {
  
  ingredientesBase: IngredienteBaseModel[] = [];
  selecionadosBase: IngredienteBaseModel[] = [];

  ingredientesAdicional: IngredienteAdicionalModel[] = [];
  selecionadosSaborAdicional: IngredienteAdicionalModel[] = [];

  bebida: BebidaInput = {
  bebidaCriada: '', // ou "Café com leite"
  ingredientesBase: [ ],
  ingredientesAdicional: [ ]
};

  constructor(
    private ingredienteBaseService: IngredienteBaseService,
    private ingredienteAdicionalService: IngredienteAdicionalService,
    private bebidaService: BebidaService,
    private router: Router
  ) {}

  ngOnInit(): void {
  this.ingredienteBaseService.findAll().subscribe(data => this.ingredientesBase = data);
  this.ingredienteAdicionalService.findAll().subscribe(data => this.ingredientesAdicional = data);
}

selecionarBase(ingrediente: IngredienteBaseModel) {
  const index = this.selecionadosBase.indexOf(ingrediente);

  if (index !== -1) {
    //Já estava selecionado, então desmarca
    this.selecionadosBase.splice(index, 1);

  } else if (this.selecionadosBase.length < 3) {
    // Só adiciona se ainda não atingiu o limite
    this.selecionadosBase.push(ingrediente);
  }
}

desabilitaBase(ingrediente: IngredienteBaseModel): boolean {
  return (
    this.selecionadosBase.length >=3 && !this.selecionadosBase.includes(ingrediente)
  );
}

selecionarSaborAdicional(ingrediente: IngredienteAdicionalModel) {
   const index = this.selecionadosSaborAdicional.indexOf(ingrediente);

  if (index !== -1) {
    //Já estava selecionado, então desmarca
    this.selecionadosSaborAdicional.splice(index, 1);

  } else if (this.selecionadosSaborAdicional.length < 2) {
    // Só adiciona se ainda não atingiu o limite
    this.selecionadosSaborAdicional.push(ingrediente);
  }
}

desabilitaAdicional(ingrediente: IngredienteAdicionalModel): boolean {
  return (
    this.selecionadosSaborAdicional.length >=2 && !this.selecionadosSaborAdicional.includes(ingrediente)
  );
}

confirmarBases() {
  console.log('Ingredientes base selecionados:', this.selecionadosBase);
}
adicionarSaborAdicional() {
  console.log('Ingredientes adicionais selecionados:', this.selecionadosSaborAdicional);
}

verificarBebida() : void {
  const bebidaInput : BebidaInput = {
    ingredientesBase: this.selecionadosBase.map(i => i.id),
    ingredientesAdicional: this.selecionadosSaborAdicional.map(i => i.id)   
  }as unknown as BebidaInput;;

  this.bebidaService.verificarBebida(bebidaInput).subscribe((apiBebidaCriada => {
    console.log('Bebida criada com sucesso:', apiBebidaCriada.bebidaCriada);
    this.bebida.bebidaCriada = apiBebidaCriada.bebidaCriada;
  }));

}

criarBebida(): void {
  const bebidaInput: BebidaInput = {
    ingredientesBase: this.selecionadosBase.map(i => i.id ),
    ingredientesAdicional: this.selecionadosSaborAdicional.map(i =>  i.id )
  } as unknown as BebidaInput;

  this.bebidaService.create(bebidaInput).subscribe({
    next: (apiBebidaCriada) => {

      this.bebidaService.message('Sua bebida ' + apiBebidaCriada.bebidaCriada + ' foi criada com sucesso!');

      this.selecionadosBase = [];
      this.selecionadosSaborAdicional = [];

      this.bebida = { bebidaCriada: '', ingredientesBase: [], ingredientesAdicional: [] };
      
    },
    error: (err) => {
      this.bebidaService.message('Erro ao criar a bebida: ' + (err.error?.message || 'Erro inesperado.'));
    }
  });
}

}

