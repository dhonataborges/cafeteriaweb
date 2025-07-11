import { IngredienteAdicionalModel } from "./IngredienteAdicionalModel";
import { IngredienteBaseModel } from "./IngredienteBaseModel";

export interface TokenModel {
  id: number;
  bebidaCriada: string;
  ingredientesBaseModel: IngredienteBaseModel[];
  ingredientesAdicionalModel: IngredienteAdicionalModel[];
}
