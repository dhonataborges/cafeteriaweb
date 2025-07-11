import { IngredienteAdicionalModel } from "../IngredienteAdicionalModel";
import { IngredienteBaseModel } from "../IngredienteBaseModel";

export interface LoginInput {
  id?: number;
  email: string;
  senha: string;
}
