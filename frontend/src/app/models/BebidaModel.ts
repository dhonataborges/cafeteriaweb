import { IngredienteAdicionalModel } from "./IngredienteAdicionalModel";
import { IngredienteBaseModel } from "./IngredienteBaseModel";

export interface BebidaModel {
  id: number;
  bebidaCriada: string;
  ingredientesBaseModel: IngredienteBaseModel[];
  ingredientesAdicionalModel: IngredienteAdicionalModel[];
}
