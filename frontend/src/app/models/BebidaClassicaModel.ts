import { IngredienteAdicionalModel } from "./IngredienteAdicionalModel";
import { IngredienteBaseModel } from "./IngredienteBaseModel";

export interface BebidaClassicaModel {
  id: number;
  nome: string;
  ingredientesBaseModel: IngredienteBaseModel[];
  ingredientesAdicionalModel: IngredienteAdicionalModel[];
}
