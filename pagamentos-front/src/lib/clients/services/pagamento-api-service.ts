import { geraPagamentoClient, processaPagamentoClient } from "../..";
import { ContentType, ErrorResponse, PagamentoCreateDTO, PagamentoDTO, RequestParams } from "../gera-pagamento/GeraPagamentoApi";
import type {PagamentoListParams, ServiceResponse, ServiceResponsePagination } from "./types";

export async function pagamentoList(
    query?: PagamentoListParams,
	options?: RequestParams,
): Promise<ServiceResponsePagination<PagamentoDTO, ErrorResponse>> {
	const response = await geraPagamentoClient.pagamento.listarPagamentos(
		{...query },
		{...options, format: 'json'},
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
    const response = await geraPagamentoClient.pagamento.salvarPagamento(data, {format: 'json', type: ContentType.Json})
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
    const response = await geraPagamentoClient.pagamento.desativarPagamento(id, {format:'json', type: ContentType.Json})
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
        .processarPagamentosPendentesViaIdPagamaento({idPagamento: idPagamento}, {format: 'json', type: ContentType.Json})
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