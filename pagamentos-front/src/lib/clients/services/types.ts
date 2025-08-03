import { PaginationMetadata } from "../gera-pagamento/GeraPagamentoApi";

export interface PageParams {
	page?: number;
	size?: number;
	sort?: string;
}

export interface ErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  details?: string[];
}

export type UnwrapPromise<T> = T extends Promise<infer U> ? U : T;
export interface ServiceError<E> {
	success: boolean;
	error?: E;
}
export interface ServiceSuccess<D> {
	success: boolean;
	result?: D;
}
export interface ServiceSuccessPagination<D> {
	success: boolean;
	page?: PaginationMetadata;
	results?: D[];
}
export interface ServiceResponse<D, E>
	extends ServiceSuccess<D>,
		ServiceError<E> {
	statusCode?: number;
}
export interface ServiceResponsePagination<D, E>
	extends ServiceSuccessPagination<D>,
		ServiceError<E> {
	statusCode?: number;
}
export interface QueryParams<D> {
	pageParam?: unknown;
	enable?: boolean;
	filters: D;
}

export interface PagamentoListParams extends PageParams{
    idPagamento?: number;
    cpfCnpj?: string;
    status?:
        | "PENDENTE_PROCESSAMENTO"
        | "PROCESSADO_SUCESSO"
        | "PROCESSADO_FALHA";
    ativo?: boolean;
}