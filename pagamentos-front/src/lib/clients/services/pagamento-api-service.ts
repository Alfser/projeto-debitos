import { geraPagamentoClient, processaPagamentoClient } from "../..";
import { ErrorResponse, PagamentoCreateDTO, PagamentoDTO, RequestParams } from "../gera-pagamento/GeraPagamentoApi";
import type {pagamentoListParams, ServiceResponse, ServiceResponsePagination } from "./types";

export async function pagamentoList(
    query?: pagamentoListParams,
	options?: RequestParams,
): Promise<ServiceResponsePagination<PagamentoDTO, ErrorResponse>> {
	const response = await geraPagamentoClient.pagamento.listarPagamentos(
		{...query },
		options,
	);

	if (!response.ok) {
		return {
            success: false,
            error: response.error,
            statusCode: response.status
        }
	}
	return {
        success: true,
        results: response.data.results,
        page: response.data.pagination,
        statusCode: response.status
    }
}

export async function salvarPagamento(data: PagamentoCreateDTO): Promise<ServiceResponse<PagamentoDTO, ErrorResponse>>{
    const response = await geraPagamentoClient.pagamento.salvarPagamento(data)
    if(!response.ok){
        return{
            success: false,
            error: response.error,
            statusCode: response.status
        }
    }
    return {
        success: true,
        result: response.data,
        statusCode: response.status,
    }
}

export async function desativarPagamento(id: string): Promise<ServiceResponse<void, ErrorResponse>> {
    const response = await geraPagamentoClient.pagamento.desativarPagamento(id)
    if(!response.ok){
        return {
            success: false,
            error: response.error,
            statusCode: response.status
        }
    }
    return{
        success: true,
        statusCode: response.status,
    }
}

export async function processarPagamento(idPagamento: number): Promise<ServiceResponse<void, ErrorResponse>> {
    const response = await processaPagamentoClient
        .processaPagamento
        .processarPagamentosPendentesViaIdPagamaento({idPagamento: idPagamento})
    if(!response.ok){
        return {
            success: false,
            error: response.error
        }
    }
    return{
        success: true,
        result: response.data,
        statusCode: response.status
    }
}