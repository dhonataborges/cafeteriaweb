import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa diretivas comuns como *ngFor, *ngIf
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

  // Lista de ingredientes base disponíveis
  ingredientesBase: IngredienteBaseModel[] = [];
  // Ingredientes base selecionados pelo usuário (máx. 3)
  selecionadosBase: IngredienteBaseModel[] = [];

  // Lista de ingredientes adicionais disponíveis
  ingredientesAdicional: IngredienteAdicionalModel[] = [];
  // Ingredientes adicionais selecionados (máx. 2)
  selecionadosSaborAdicional: IngredienteAdicionalModel[] = [];

  // Objeto usado para criar ou verificar uma bebida
  bebida: BebidaInput = {
    bebidaCriada: '',
    ingredientesBase: [],
    ingredientesAdicional: []
  };

  constructor(
    private ingredienteBaseService: IngredienteBaseService,
    private ingredienteAdicionalService: IngredienteAdicionalService,
    private bebidaService: BebidaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Carrega ingredientes base e adicionais ao iniciar o componente
    this.ingredienteBaseService.findAll().subscribe(data => this.ingredientesBase = data);
    this.ingredienteAdicionalService.findAll().subscribe(data => this.ingredientesAdicional = data);
  }

  // Adiciona ou remove um ingrediente base da seleção
  selecionarBase(ingrediente: IngredienteBaseModel) {
    const index = this.selecionadosBase.indexOf(ingrediente);

    if (index !== -1) {
      // Já selecionado → remove
      this.selecionadosBase.splice(index, 1);
    } else if (this.selecionadosBase.length < 3) {
      // Adiciona até o limite de 3
      this.selecionadosBase.push(ingrediente);
    }
  }

  // Desabilita ingredientes base caso o limite tenha sido atingido
  desabilitaBase(ingrediente: IngredienteBaseModel): boolean {
    return (
      this.selecionadosBase.length >= 3 && !this.selecionadosBase.includes(ingrediente)
    );
  }

  // Adiciona ou remove um ingrediente adicional da seleção
  selecionarSaborAdicional(ingrediente: IngredienteAdicionalModel) {
    const index = this.selecionadosSaborAdicional.indexOf(ingrediente);

    if (index !== -1) {
      // Já selecionado → remove
      this.selecionadosSaborAdicional.splice(index, 1);
    } else if (this.selecionadosSaborAdicional.length < 2) {
      // Adiciona até o limite de 2
      this.selecionadosSaborAdicional.push(ingrediente);
    }
  }

  // Desabilita ingredientes adicionais se limite for atingido
  desabilitaAdicional(ingrediente: IngredienteAdicionalModel): boolean {
    return (
      this.selecionadosSaborAdicional.length >= 2 &&
      !this.selecionadosSaborAdicional.includes(ingrediente)
    );
  }

  // Apenas imprime no console os ingredientes base selecionados
  confirmarBases() {
    console.log('Ingredientes base selecionados:', this.selecionadosBase);
  }

  // Apenas imprime no console os ingredientes adicionais selecionados
  adicionarSaborAdicional() {
    console.log('Ingredientes adicionais selecionados:', this.selecionadosSaborAdicional);
  }

  // Envia os ingredientes para verificar se a bebida já existe e retorna o nome criado
  verificarBebida(): void {
    const bebidaInput: BebidaInput = {
      ingredientesBase: this.selecionadosBase.map(i => i.id),
      ingredientesAdicional: this.selecionadosSaborAdicional.map(i => i.id)
    } as BebidaInput;

    this.bebidaService.verificarBebida(bebidaInput).subscribe(apiBebidaCriada => {
      console.log('Bebida criada com sucesso:', apiBebidaCriada.bebidaCriada);
      this.bebida.bebidaCriada = apiBebidaCriada.bebidaCriada;
    });
  }

  // Envia a bebida para criação e reseta o estado do formulário
  criarBebida(): void {
    const bebidaInput: BebidaInput = {
      ingredientesBase: this.selecionadosBase.map(i => i.id),
      ingredientesAdicional: this.selecionadosSaborAdicional.map(i => i.id)
    } as BebidaInput;

    this.bebidaService.create(bebidaInput).subscribe({
      next: (apiBebidaCriada) => {
        this.bebidaService.message('Sua bebida ' + apiBebidaCriada.bebidaCriada + ' foi criada com sucesso!');

        // Reseta os dados após criação
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
